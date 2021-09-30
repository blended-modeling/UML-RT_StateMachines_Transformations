/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Onder Gurcan <onder.gurcan@cea.fr> - Initial API and implementation
 *   Christian W. Damus - bugs 493869, 467545, 514392
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.uml.service.types.utils.RequestParameterConstants;
import org.eclipse.papyrusrt.umlrt.core.internal.commands.CreateHeadlessCapsuleStructureDiagramCommand;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.UMLPackage;


/**
 * The helperadvice class used for UMLRealTime::Capsule.
 *
 * @author Onder Gurcan <onder.gurcan@cea.fr>
 *
 */
public class CapsuleEditHelperAdvice extends AbstractEditHelperAdvice implements IInheritanceEditHelperAdvice {

	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		if (request instanceof CreateRelationshipRequest) {
			return approveCreateRelationshipRequest(request);
		} else if (request instanceof CreateElementRequest) {
			return approveCreateElementRequest((CreateElementRequest) request);
		} else {
			return super.approveRequest(request);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configureRequest(IEditCommandRequest request) {
		if (request instanceof CreateElementRequest) {
			IElementType typeToCreate = ((CreateElementRequest) request).getElementType();
			// check this is a rtstatemachine or subtype
			if (ElementTypeUtils.isTypeCompatible(typeToCreate, ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RT_STATE_MACHINE_ID))) {
				// check the containement feature is set, or set it to ownedbehavior
				EReference containmentRef = ((CreateElementRequest) request).getContainmentFeature();
				if (containmentRef == null) {
					((CreateElementRequest) request).setContainmentFeature(UMLPackage.eINSTANCE.getBehavioredClassifier_OwnedBehavior());
				}
				Object nameToSet = request.getParameter(RequestParameterConstants.NAME_TO_SET);
				if (nameToSet == null) { // name was not already customized. Customize it to "StateMachine".
					request.setParameter(RequestParameterConstants.NAME_TO_SET, "StateMachine");
				}
			}
		}
	}

	/**
	 * Disallow creation of another RT SM in a Capsule: Capsule should
	 * have exactly one RT SM.
	 * 
	 */
	private Boolean approveCreateElementRequest(CreateElementRequest request) {
		Boolean result = super.approveRequest(request);

		IElementType typeToCreate = request.getElementType();
		if (ElementTypeUtils.isTypeCompatible(typeToCreate, ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RT_STATE_MACHINE_ID))) {
			// Creating an RT SM for the Capsule? Check that it is the first RT SM for that Capsule
			// otherwise, disapprove
			if (request.getContainer() instanceof BehavioredClassifier) {
				BehavioredClassifier stateToEdit = (BehavioredClassifier) request.getContainer();
				// it is not the first RT SM: disapprove
				if (stateToEdit.getClassifierBehavior() != null && ElementTypeUtils.matches(stateToEdit.getClassifierBehavior(), IUMLRTElementTypes.RT_STATE_MACHINE_ID)) {
					result = false;
				} else {
					result = true;
				}
			}
		}

		return result;
	}

	protected boolean approveCreateRelationshipRequest(IEditCommandRequest request) {
		IElementType type = ((CreateRelationshipRequest) request).getElementType();
		if (type != null && ElementTypeUtils.isTypeCompatible(type, ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RT_CONNECTOR_ID))) {
			return checkSourceAndTargetForRTConnector(((CreateRelationshipRequest) request));
		}
		return super.approveRequest(request);
	}

	protected boolean checkSourceAndTargetForRTConnector(CreateRelationshipRequest createRelationshipRequest) {
		EObject source = createRelationshipRequest.getSource();
		EObject target = createRelationshipRequest.getTarget();

		if (source != null) {
			if (ElementTypeUtils.matches(source, IUMLRTElementTypes.SERVICE_ACCESS_POINT_ID)) {
				return false; // cannot connect a connector to a SAP
			} else if (ElementTypeUtils.matches(source, IUMLRTElementTypes.SERVICE_PROVISION_POINT_ID)) {
				return false;
			}
		}

		if (target != null) {
			if (ElementTypeUtils.matches(target, IUMLRTElementTypes.SERVICE_ACCESS_POINT_ID)) {
				return false; // cannot connect a connector to a SAP
			} else if (ElementTypeUtils.matches(target, IUMLRTElementTypes.SERVICE_PROVISION_POINT_ID)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ICommand getBeforeEditCommand(IEditCommandRequest request) {
		ICommand result = getInheritanceEditCommand(request);

		if (result == null) {
			result = super.getBeforeEditCommand(request);
		}

		return result;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		final Class capsule = (Class) request.getElementToConfigure();

		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				capsule.setIsActive(true);

				createCompositeDiagram();

				return CommandResult.newOKCommandResult(capsule);
			}

			protected Diagram createCompositeDiagram() {
				ServicesRegistry registry;
				try {
					registry = ServiceUtilsForEObject.getInstance().getServiceRegistry(capsule);
				} catch (ServiceException ex) {
					Activator.log.error(ex);
					return null;
				}
				ModelSet modelSet;
				try {
					modelSet = registry.getService(ModelSet.class);
				} catch (ServiceException ex) {
					Activator.log.error(ex);
					return null;
				}

				// It is okay for the view prototype to be unavailable, such as when running
				// in an environment that does not have the diagram bundle installed
				ViewPrototype prototype = ViewPrototype.get(PolicyChecker.getFor(capsule), CapsuleUtils.UML_RT_CAPSULE_DIAGRAM, capsule, capsule);
				return prototype.isUnavailable()
						? null
						: new CreateHeadlessCapsuleStructureDiagramCommand().createDiagram(modelSet, capsule, capsule, prototype, null, false);
			}

		};
	}
}
