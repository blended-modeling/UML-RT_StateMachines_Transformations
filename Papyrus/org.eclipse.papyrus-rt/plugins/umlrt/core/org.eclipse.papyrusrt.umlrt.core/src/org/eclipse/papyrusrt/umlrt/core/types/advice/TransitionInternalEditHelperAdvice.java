/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.GetEditContextCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.RegionUtils;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Edit helper advice to create transition with the "internal" kind.
 */
public class TransitionInternalEditHelperAdvice extends AbstractEditHelperAdvice {
	/**
	 * Constructor.
	 */
	public TransitionInternalEditHelperAdvice() {
		// empty
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeEditContextCommand(GetEditContextRequest request) {
		IEditCommandRequest editCommandRequest = request.getEditCommandRequest();
		if (editCommandRequest instanceof CreateElementRequest) {
			// for creation request of an internal transition, delegates to the owned region of the state (if possible)
			IElementType type = ((CreateElementRequest) editCommandRequest).getElementType();
			if (ElementTypeUtils.isTypeCompatible(type, UMLRTElementTypesEnumerator.TRANSITION_INTERNAL)) {
				// in this case, target container should be the region owned by the state, if one exists, and the source/target should be the state itself
				EObject container = ((CreateElementRequest) editCommandRequest).getContainer();
				State state = (container instanceof State) ? (State) container : null;
				if (state == null) {
					return super.getBeforeEditContextCommand(request);
				}
				if (state.getRegions().size() > 0) {
					GetEditContextCommand command = new GetEditContextCommand(request);
					if (request.getEditContext() instanceof State) {
						// this line is very important
						// change the context is ok, but the feature must be change
						((CreateElementRequest) editCommandRequest).setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Transition());
						((CreateElementRequest) editCommandRequest).setContainer(state.getRegions().get(0)); // region owned by source/target state should be the owner
						// not a create relationship request, but Source & Target parameter are still of interest.
						((CreateElementRequest) editCommandRequest).setParameter(CreateRelationshipRequest.SOURCE, state);
						((CreateElementRequest) editCommandRequest).setParameter(CreateRelationshipRequest.TARGET, state);

						// update also the source and target to be the original container of the target request
						command.setEditContext(state.getRegions().get(0));
						return command;
					}
				} else {
					GetEditContextCommand command = new CreateRegionContextCommand(request);
					((CreateElementRequest) editCommandRequest).setParameter(CreateRelationshipRequest.SOURCE, state);
					((CreateElementRequest) editCommandRequest).setParameter(CreateRelationshipRequest.TARGET, state);
					((CreateElementRequest) editCommandRequest).setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Transition());
					command.setEditContext(UMLRTElementTypesEnumerator.RT_REGION); // edit context will be a region (later...)
					return command;
				}
			}
		}
		return super.getBeforeEditContextCommand(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		final EObject element = request.getElementToConfigure();
		if (element instanceof Transition) {
			return new ConfigureElementCommand(request) {
				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
					// All operations of a capsule are guarded.
					((Transition) element).setKind(TransitionKind.INTERNAL_LITERAL);
					return CommandResult.newOKCommandResult(element);
				}
			};
		}
		return super.getAfterConfigureCommand(request);
	}

	public static class CreateRegionContextCommand extends GetEditContextCommand {

		/**
		 * Constructs a new command. Automatically initializes the edit context with
		 * that carried in the <code>request</code>.
		 * 
		 * @param request
		 *            the command request
		 */
		public CreateRegionContextCommand(GetEditContextRequest request) {
			super(request);
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			EObject objectToEdit = ((CreateElementRequest) ((GetEditContextRequest) getRequest()).getEditCommandRequest()).getContainer();
			if (objectToEdit instanceof State && ((State) objectToEdit).getRegions().size() == 0) {
				// create a new RT-Region and attach it to the state
				IElementEditService commandProvider = ElementEditServiceUtils.getCommandProvider(objectToEdit);
				if (commandProvider != null) {
					ICommand createRegion = commandProvider.getEditCommand(new CreateElementRequest(getEditingDomain(), objectToEdit, UMLElementTypes.REGION, UMLPackage.Literals.STATE__REGION));
					if (createRegion != null && createRegion.canExecute()) {
						IStatus status = createRegion.execute(monitor, info);
						if (!status.isOK()) {
							return CommandResult.newErrorCommandResult(status.getMessage());
						} else {
							Region result = ((State) objectToEdit).getRegions().get(0);
							RegionUtils.applyStereotype(result);
							return CommandResult.newOKCommandResult(result);
						}
					}
				}
			}
			// really create the region here, do not use the element type
			return CommandResult.newOKCommandResult(getEditContext());
		}

	}
}
