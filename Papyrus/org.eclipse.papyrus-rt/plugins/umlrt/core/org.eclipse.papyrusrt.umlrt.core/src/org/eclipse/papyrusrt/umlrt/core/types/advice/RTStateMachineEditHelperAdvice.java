/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 493869, 510315, 514790, 514392
 *  Young-Soo Roh - bug 510024
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.edit.utils.GMFCommandUtils;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.internal.commands.CreateHeadlessStateMachineDiagramCommand;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.RegionUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.StateMachineUtils;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;

/**
 * Edit Helper Advice for UML-RT State machines.
 */
public class RTStateMachineEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * Creates a new {@link RTStateMachineEditHelperAdvice}.
	 */
	public RTStateMachineEditHelperAdvice() {
		// default ctor.
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		if (request instanceof CreateElementRequest) {
			return approveCreateElementRequest((CreateElementRequest) request);
		} else if (request instanceof MoveRequest) {
			return approveMoveRequest((MoveRequest) request);
		} else {
			return super.approveRequest(request);
		}
	}

	/**
	 * Approves the specified move request or reject it.
	 * 
	 * @param request
	 *            the request to approve.
	 * @return <code>true</code> if the specified request is approved
	 */
	protected boolean approveMoveRequest(MoveRequest request) {
		return RegionUtils.shouldApproveMoveRequest(request);
	}

	/**
	 * Approves the specified create request or reject it.
	 * 
	 * @param createElementRequest
	 *            the request to approve.
	 * @return <code>true</code> if the specified request is approved
	 */
	protected boolean approveCreateElementRequest(CreateElementRequest createElementRequest) {
		// retrieve element type from this request. No element is allowed except one and only one region.
		IElementType type = createElementRequest.getElementType();
		// impossible to create any element
		IElementType regionType = UMLElementTypes.REGION;
		if (regionType != null) {
			if (ElementTypeUtils.isTypeCompatible(type, regionType)) {
				// check the state machine does not already have one region
				EObject container = createElementRequest.getContainer();
				if (container instanceof StateMachine) {
					return ((StateMachine) container).getRegions().size() < 1;
				}
				return super.approveRequest(createElementRequest);
			} else {
				return false;
			}
		} else {
			Activator.log.error("Impossible to find Region element type", null);
		}
		return super.approveRequest(createElementRequest);
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#configureRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param request
	 */
	@Override
	public void configureRequest(IEditCommandRequest request) {
		if (request instanceof MoveRequest) {
			StateMachineUtils.retargetToRegion((MoveRequest) request);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {
		// create the region and an initial state in the region
		ICommand command = new ConfigureElementCommand(request) {

			/**
			 * {@inheritDoc}
			 */
			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) {
				EObject elementToConfigure = request.getElementToConfigure();
				if (!(elementToConfigure instanceof StateMachine)) {
					return CommandResult.newErrorCommandResult("Element to configure is not a state machine");
				}
				StateMachine stateMachine = (StateMachine) elementToConfigure;
				stateMachine.setIsReentrant(false);
				stateMachine.getContext().setClassifierBehavior(stateMachine);

				if (!stateMachine.getExtendedStateMachines().isEmpty()) {
					// Nothing to do: everything starts out inherited
					return CommandResult.newOKCommandResult(stateMachine);
				}

				try {
					Region region = createRegion(stateMachine, monitor, info, "Region");
					return CommandResult.newOKCommandResult(region);
				} catch (ExecutionException e) {
					return CommandResult.newErrorCommandResult(e);
				}
			}

		};

		ICommand superCommand = super.getBeforeConfigureCommand(request);
		if (superCommand != null) {
			// draw the diagram, with initial state displayed
			return command.compose(superCommand).reduce();
		}
		return command.reduce();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getAfterConfigureCommand(final ConfigureRequest request) {

		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {

				/* @noname String name = (request.getElementToConfigure() instanceof NamedElement) ? ((NamedElement) request.getElementToConfigure()).getName() : "smDiagram"; */
				createStateMachineDiagram(request.getElementToConfigure(), null);
				return CommandResult.newOKCommandResult(request.getElementToConfigure());
			}

		};
	}

	/**
	 * Creates a new state machine diagram for the given {@code owner}, which should
	 * be the state machine element-to-configure of a {@link ConfigureRequest}.
	 * 
	 * @param owner
	 *            the diagram owner
	 * @param name
	 *            the diagram name to set (may be {@code null})
	 * 
	 * @return the new diagram
	 */
	public static Diagram createStateMachineDiagram(EObject owner, String name) {
		ServicesRegistry registry;
		try {
			registry = ServiceUtilsForEObject.getInstance().getServiceRegistry(owner);
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
		ViewPrototype prototype = ViewPrototype.get(PolicyChecker.getFor(owner), StateMachineUtils.UMLRT_STATE_MACHINE_DIAGRAM_ID, owner, owner);
		return prototype.isUnavailable()
				? null
				: new CreateHeadlessStateMachineDiagramCommand().createDiagram(modelSet, owner, owner, prototype, null, false);
	}

	/**
	 * Create the region for the given state machine
	 * 
	 * @param stateMachine
	 *            state machine in which created region will be added
	 * @param monitor
	 *            progress monitor for the command execution
	 * @param info
	 * @param regionName
	 *            name of the region to create
	 * @return the newly created region
	 * @throws ExecutionException
	 *             execution thrown in case of issues happening in the command execution
	 */
	protected Region createRegion(StateMachine stateMachine, IProgressMonitor monitor, IAdaptable info, String regionName) throws ExecutionException {
		EObject newElement = null;
		CreateElementRequest createElementRequest = new CreateElementRequest(stateMachine, ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RT_REGION_ID));
		// get command from edit service
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(stateMachine);
		if (provider == null) {
			throw new ExecutionException("Impossible to get the provider from " + stateMachine);
		}

		ICommand createGMFCommand = provider.getEditCommand(createElementRequest);
		if (createGMFCommand != null) {
			if (createGMFCommand.canExecute()) {
				IStatus status = createGMFCommand.execute(monitor, info);
				if (status.isOK()) {
					newElement = GMFCommandUtils.getCommandEObjectResult(createGMFCommand);

					if (!(newElement instanceof Region)) {
						throw new ExecutionException("Element creation problem for " + UMLRTElementTypesEnumerator.RT_REGION.getDisplayName() + ".");
					}
					((Region) newElement).setName(regionName);
					return ((Region) newElement);
				} else {
					throw new ExecutionException("Impossible to create the region");
				}
			} else {
				throw new ExecutionException("Command to create the region is not executable");
			}
		} else {
			throw new ExecutionException("Impossible to find a command to create the region");
		}
	}
}
