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
 *  Christian W. Damus - bugs 510315, 517130, 514392, 518290
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.edit.utils.GMFCommandUtils;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.commands.IExcludeElementRequest;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.RegionUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.StateUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.TransitionUtils;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;

import com.google.common.base.Strings;

/**
 * Edit Helper Advice for {@link Region} in the context of UML-RT.
 */
public class RTRegionEditHelperAdvice extends AbstractEditHelperAdvice implements IInheritanceEditHelperAdvice {

	public RTRegionEditHelperAdvice() {
		super();
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
	 * {@inheritDoc}
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		if (request instanceof CreateRelationshipRequest) {
			return approveCreateRelationshipRequest((CreateRelationshipRequest) request);
		} else if (request instanceof CreateElementRequest) {
			return approveCreateRequest((CreateElementRequest) request);
		} else if (request instanceof MoveRequest) {
			return approveMoveRequest((MoveRequest) request);
		} else if (request instanceof SetRequest) {
			return approveSetRequest((SetRequest) request);
		} else if (request instanceof IExcludeElementRequest) {
			return approveExcludeRequest((IExcludeElementRequest) request);
		}
		return super.approveRequest(request);
	}

	/**
	 * Approve or not the specified {@link CreateRelationshipRequest}.
	 * 
	 * @param request
	 *            the request to approve
	 * @return <code>true</code> if create relationship request is approved
	 */
	private boolean approveCreateRelationshipRequest(CreateRelationshipRequest request) {
		if (ElementTypeUtils.isTypeCompatible(request.getElementType(), UMLElementTypes.TRANSITION)) {
			return approveCreateTransition(request);
		}
		return super.approveRequest(request);
	}

	/**
	 * @param request
	 * @return
	 */
	protected boolean approveCreateTransition(CreateRelationshipRequest request) {
		boolean result = true;

		EObject source = request.getSource();
		EObject implicitSource = TransitionUtils.getImplicitState(source);
		EObject target = request.getTarget();
		EObject implicitTarget = TransitionUtils.getImplicitState(target);
		EObject container = request.getContainer();

		// if source is a simple state
		if (StateUtils.isSimpleState(implicitSource)) {
			// than it should be OK, until container is a region containing this state and contains also target state
			// check container contains implicit source, (and implicit target if not null)
			if (container instanceof Region) {
				result = result && ((Region) container).getOwnedElements().contains(implicitSource);
				if (implicitTarget instanceof State) {
					if (((Region) container).getOwnedElements().contains(implicitTarget)) {
						if (target instanceof Pseudostate) {
							// can not be an initial state, nor an exit state
							result = result && !(((Pseudostate) target).getKind().equals(PseudostateKind.INITIAL_LITERAL) && ((Pseudostate) target).getKind().equals(PseudostateKind.EXIT_POINT_LITERAL));
						}
					}
				} else if (implicitTarget == implicitSource) {
					if (target instanceof Pseudostate) {
						// can be an exit state
						result = result && ((Pseudostate) target).getKind().equals(PseudostateKind.EXIT_POINT_LITERAL);
					}
				}
			} else {
				result = false;
			}
		} else if (StateUtils.isCompositeState(implicitSource)) {
			// than it should be OK, until container is a region containing this state and contains also target state
			// or container could be also the region from implicit source, for an local or internal transition
			// check container contains implicit source, (and implicit target if not null)
			// or check container is contained by implicit source, and target is either source or a state contained in container
			if (container instanceof Region) {
				if (((Region) container).getOwnedElements().contains(implicitSource)) {
					// same kind as for simple state
					if (implicitTarget instanceof State) {
						if (((Region) container).getOwnedElements().contains(implicitTarget)) {
							if (source instanceof Pseudostate) {
								result = result && !(((Pseudostate) source).getKind().equals(PseudostateKind.ENTRY_POINT_LITERAL));
							}
							if (target instanceof Pseudostate) {
								// can not be an initial state, nor an exit state
								result = result && !(((Pseudostate) target).getKind().equals(PseudostateKind.INITIAL_LITERAL) || ((Pseudostate) target).getKind().equals(PseudostateKind.EXIT_POINT_LITERAL));
							}
						} else if (target != null) {
							// this should be a local transition getting out of the state
							if (target instanceof Pseudostate) {
								result = result && !(((Pseudostate) target).getKind().equals(PseudostateKind.EXIT_POINT_LITERAL));
							}
							if (source instanceof Pseudostate) {
								result = result && !(((Pseudostate) source).getKind().equals(PseudostateKind.EXIT_POINT_LITERAL));
							}
						}

					}
				} else if (((State) implicitSource).getRegions().contains(container)) {
					// than implicit target can be either contained in container or equals to implicit source
					if (implicitSource == implicitTarget) {
						// if source is a pseudo state, should be an exit point, or ok if it is the state
						if (source instanceof Pseudostate) {
							result = result && (((Pseudostate) source).getKind().equals(PseudostateKind.EXIT_POINT_LITERAL));
						}
						if (target instanceof Pseudostate) {
							result = result && (((Pseudostate) target).getKind().equals(PseudostateKind.ENTRY_POINT_LITERAL));
						}
					} else if (implicitTarget != null) {
						// implicit target != implicit source, it should be contained in source's region
						if (((Region) container).getOwnedElements().contains(implicitTarget)) {
							if (target instanceof Pseudostate) {
								// this should be an entry point
								result = result && (((Pseudostate) target).getKind().equals(PseudostateKind.ENTRY_POINT_LITERAL));
							}
						} else {
							result = false;
						}
					}

				} else {
					result = false;
				}

			} else {
				result = false;
			}


		}
		return result;
	}

	/**
	 * Approves the specified set request.
	 * 
	 * @param request
	 *            the request to approve
	 * @return <code>true</code> if that advice accepts this request
	 */
	protected boolean approveSetRequest(SetRequest request) {
		EStructuralFeature feature = request.getFeature();
		for (Object o : request.getElementsToEdit()) {
			if (o instanceof Region &&
					(UMLPackage.Literals.REGION__SUBVERTEX.equals(feature)
							|| UMLPackage.Literals.REGION__TRANSITION.equals(feature))) {
				// prevent drag & drop of subvertex or transition to a region
				// this can happen even if region is not visible from the model explorer
				return false;
			}
		}
		return super.approveRequest(request);
	}

	/**
	 * Approves the specified move request.
	 * 
	 * @param request
	 *            the request to approve
	 * @return <code>true</code> if that advice accepts this request
	 */
	protected boolean approveMoveRequest(MoveRequest request) {
		return RegionUtils.shouldApproveMoveRequest(request);
	}

	/**
	 * Approves the specified create request.
	 * 
	 * @param request
	 *            the request to approve
	 * @return <code>true</code> if that advice accepts this request
	 */
	protected boolean approveCreateRequest(CreateElementRequest request) {
		IElementType typeToCreate = request.getElementType();
		if (!(request.getContainer() instanceof Region)) {
			return true;
		}
		Region targetRegion = (Region) request.getContainer();
		if (ElementTypeUtils.isTypeCompatible(typeToCreate, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_INITIAL)) {
			// for initial state, max one is accepted
			return targetRegion.getSubvertices().stream()
					.filter(Pseudostate.class::isInstance).map(Pseudostate.class::cast)
					.noneMatch(p -> (PseudostateKind.INITIAL_LITERAL == p.getKind()));
		} else if (ElementTypeUtils.isTypeCompatible(typeToCreate, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_DEEP_HISTORY)) {
			// for deep history state, max one is accepted, and only in composite state regions, not state machines
			// check region belongs to a composite state. If not, creation is not possible
			if (targetRegion.getState() == null) {
				return false;
			}
			return targetRegion.getSubvertices().stream()
					.filter(Pseudostate.class::isInstance).map(Pseudostate.class::cast)
					.noneMatch(p -> (PseudostateKind.DEEP_HISTORY_LITERAL == p.getKind()));
		} else if (ElementTypeUtils.isTypeCompatible(typeToCreate, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_ENTRY_POINT)) {
			// no entry point in regions
			return false;
		} else if (ElementTypeUtils.isTypeCompatible(typeToCreate, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_EXIT_POINT)) {
			// no exit point in regions
			return false;
		} else if (ElementTypeUtils.isTypeCompatible(typeToCreate, UMLElementTypes.PSEUDOSTATE)
				|| ElementTypeUtils.isTypeCompatible(typeToCreate, UMLElementTypes.STATE)) {

			// Pseudostates and actual states must be stereotyped for UML-RT
			return UMLRTElementTypesEnumerator.getAllRTTypes().stream()
					.anyMatch(rtType -> ElementTypeUtils.isTypeCompatible(typeToCreate, rtType));
		}

		return true;
	}

	protected boolean approveExcludeRequest(IExcludeElementRequest request) {
		// It does not make sense to exclude pseudostates
		return !(request.getElementToExclude() instanceof Pseudostate);
	}

	@Override
	protected ICommand getAfterCreateRelationshipCommand(CreateRelationshipRequest request) {
		if (UMLPackage.Literals.REGION__TRANSITION.equals(request.getContainmentFeature())) {
			return new AbstractTransactionalCommand(request.getEditingDomain(), "Edit Transition", Collections.EMPTY_LIST) {
				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					CommandResult result = CommandResult.newOKCommandResult();
					Transition transition = (Transition) request.getNewElement();
					Vertex source = transition.getSource();
					Vertex target = transition.getTarget();
					if (source instanceof Pseudostate && !PseudostateKind.ENTRY_POINT_LITERAL.equals(((Pseudostate) source).getKind())
							&& !PseudostateKind.EXIT_POINT_LITERAL.equals(((Pseudostate) source).getKind())) {
						// pseudo state other than entry or exit point should be external only.
						transition.setKind(TransitionKind.EXTERNAL_LITERAL);
						return result;
					}

					transition.setKind(TransitionUtils.getTransitionKind(transition, source, target));

					return result;
				}
			};
		}
		return super.getAfterCreateRelationshipCommand(request);
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
				if (!(elementToConfigure instanceof Region)) {
					return CommandResult.newErrorCommandResult("Element to configure is not a region");
				}
				Region region = (Region) elementToConfigure;
				try {
					Pseudostate pseudoState = createInitialState(region, monitor, info, null);
					State initialState = createFirstSimpleState(region, monitor, info, "State1");
					createInitialTransition(region, pseudoState, initialState, monitor, info, "Initial");
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
	 * Creates the initial state in the given region.
	 * 
	 * @param region
	 *            the region in which to create the pseudo state
	 * @param monitor
	 *            the progress monitor for the creation command exexcution
	 * @param info
	 *            the IAdaptable (or <code>null</code>) provided by the
	 *            caller in order to supply UI information for prompting the
	 *            user if necessary. When this parameter is not
	 *            <code>null</code>, it should minimally contain an adapter
	 *            for the org.eclipse.swt.widgets.Shell.class.
	 * @param pseudoStateName
	 *            the name of the new pseudo state or null if the name should not be set
	 * @return the newly created pseudo state
	 * @throws ExecutionException
	 *             exception thrown if the command execution encouter issues
	 */
	protected Pseudostate createInitialState(Region region, IProgressMonitor monitor, IAdaptable info, String pseudoStateName) throws ExecutionException {
		EObject newElement = null;
		CreateElementRequest createElementRequest = new CreateElementRequest(region, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_INITIAL);
		// get command from edit service
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(region);
		if (provider == null) {
			throw new ExecutionException("Impossible to get the provider from " + region);
		}

		ICommand createGMFCommand = provider.getEditCommand(createElementRequest);
		if (createGMFCommand != null) {
			if (createGMFCommand.canExecute()) {
				IStatus status = createGMFCommand.execute(monitor, info);
				if (status.isOK()) {
					newElement = GMFCommandUtils.getCommandEObjectResult(createGMFCommand);

					if (!(newElement instanceof Pseudostate)) {
						throw new ExecutionException("Element creation problem for PseudoState.");
					}

					Pseudostate pseudostate = (Pseudostate) newElement;

					if (Strings.isNullOrEmpty(pseudoStateName)) {
						pseudostate.unsetName();
					} else {
						pseudostate.setName(pseudoStateName);
					}

					return pseudostate;

				} else {
					throw new ExecutionException("Impossible to create the pseudo state");
				}
			} else {
				throw new ExecutionException("Command to create the pseudo state is not executable");
			}
		} else {
			throw new ExecutionException("Impossible to find a command to create the Pseudostate");
		}

	}

	/**
	 * Creates the fist state in region.
	 * 
	 * @param region
	 *            the region to configure
	 * @param monitor
	 *            the progress monitor (or <code>null</code>) to use for
	 *            reporting progress to the user.
	 * @param info
	 *            the IAdaptable (or <code>null</code>) provided by the
	 *            caller in order to supply UI information for prompting the
	 *            user if necessary. When this parameter is not
	 *            <code>null</code>, it should minimally contain an adapter
	 *            for the org.eclipse.swt.widgets.Shell.class.
	 * @param firstStateName
	 *            the name for the initial state
	 * @return
	 * 		the new state created
	 * @throws ExecutionException
	 */
	protected State createFirstSimpleState(Region region, IProgressMonitor monitor, IAdaptable info, String firstStateName) throws ExecutionException {
		EObject newElement = null;
		CreateElementRequest createElementRequest = new CreateElementRequest(region, UMLRTElementTypesEnumerator.RT_STATE);
		// get command from edit service
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(region);
		if (provider == null) {
			throw new ExecutionException("Impossible to get the provider from " + region);
		}

		ICommand createGMFCommand = provider.getEditCommand(createElementRequest);
		if (createGMFCommand != null) {
			if (createGMFCommand.canExecute()) {
				IStatus status = createGMFCommand.execute(monitor, info);
				if (status.isOK()) {
					newElement = GMFCommandUtils.getCommandEObjectResult(createGMFCommand);

					if (!(newElement instanceof State)) {
						throw new ExecutionException("Element creation problem for State.");
					}

					((State) newElement).setName(firstStateName);
					return ((State) newElement);

				} else {
					throw new ExecutionException("Impossible to create the first simple state");
				}
			} else {
				throw new ExecutionException("Command to create the first simple state is not executable");
			}
		} else {
			throw new ExecutionException("Impossible to find a command to create the first simple state");
		}

	}

	/**
	 * Creates the first transition between the initial state and the first state, when creating the region.
	 * 
	 * @param region
	 *            the region owning source and target elements
	 * @param source
	 *            the source of the created transition
	 * @param target
	 *            the target of the created transition
	 * @param monitor
	 *            the progress monitor (or <code>null</code>) to use for
	 *            reporting progress to the user.
	 * @param info
	 *            the IAdaptable (or <code>null</code>) provided by the
	 *            caller in order to supply UI information for prompting the
	 *            user if necessary. When this parameter is not
	 *            <code>null</code>, it should minimally contain an adapter
	 *            for the org.eclipse.swt.widgets.Shell.class.
	 * @param firstTransitionName
	 *            name for the created transition
	 * @return the created transition
	 * @throws ExecutionException
	 */
	protected Transition createInitialTransition(Region region, Vertex source, Vertex target, IProgressMonitor monitor, IAdaptable info, String firstTransitionName) throws ExecutionException {
		EObject newElement = null;
		CreateElementRequest createElementRequest = new CreateElementRequest(region, UMLElementTypes.TRANSITION);
		// get command from edit service
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(region);
		if (provider == null) {
			throw new ExecutionException("Impossible to get the provider from " + region);
		}

		ICommand createGMFCommand = provider.getEditCommand(createElementRequest);
		if (createGMFCommand != null) {
			if (createGMFCommand.canExecute()) {
				IStatus status = createGMFCommand.execute(monitor, info);
				if (status.isOK()) {
					newElement = GMFCommandUtils.getCommandEObjectResult(createGMFCommand);

					if (!(newElement instanceof Transition)) {
						throw new ExecutionException("Element creation problem for Transition " + ".");
					}

					((Transition) newElement).setName(firstTransitionName);
					if (source != null) {
						((Transition) newElement).setSource(source);
					}
					if (target != null) {
						((Transition) newElement).setTarget(target);
					}
					return ((Transition) newElement);

				} else {
					throw new ExecutionException("Impossible to create the initial Transition");
				}
			} else {
				throw new ExecutionException("Command to create initial Transition is not executable");
			}
		} else {
			throw new ExecutionException("Impossible to find a command to create the initial Transition");
		}

	}
}
