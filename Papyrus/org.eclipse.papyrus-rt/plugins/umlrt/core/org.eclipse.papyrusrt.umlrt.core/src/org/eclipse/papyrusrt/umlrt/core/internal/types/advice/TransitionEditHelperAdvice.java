/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 510315, 517130
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.internal.types.advice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IEditHelperContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.commands.GetEditContextCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.commands.ExcludeDependentsRequest;
import org.eclipse.papyrusrt.umlrt.core.commands.IExcludeElementRequest;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.types.advice.IInheritanceEditHelperAdvice;
import org.eclipse.papyrusrt.umlrt.core.utils.StateUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.TransitionUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTCommandUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;

/**
 * {@link IEditHelperAdvice} for UML-RT {@link Transition}s.
 */
public class TransitionEditHelperAdvice extends AbstractEditHelperAdvice implements IInheritanceEditHelperAdvice {

	public TransitionEditHelperAdvice() {
		super();
	}

	@Override
	public ICommand getBeforeEditCommand(IEditCommandRequest request) {
		ICommand result = null;

		if (request instanceof IExcludeElementRequest) {
			IExcludeElementRequest exclude = (IExcludeElementRequest) request;
			if (exclude.isExclude() && (exclude.getElementToExclude() instanceof Trigger)) {
				// Delete any local guards associated with the inherited trigger
				result = getDeleteTriggerGuardCommand(exclude, (Trigger) exclude.getElementToExclude());
			}
		}

		ICommand beforeEdit = getInheritanceEditCommand(request);
		if (beforeEdit == null) {
			beforeEdit = super.getBeforeEditCommand(request);
		}

		result = UMLRTCommandUtils.flatCompose(result, beforeEdit);

		return result;
	}

	@Override
	public ICommand getExcludeDependentsCommand(ExcludeDependentsRequest request) {
		ICommand result = IInheritanceEditHelperAdvice.super.getExcludeDependentsCommand(request);

		if (!request.isExclude() && (request.getElementToExclude() instanceof Transition)) {
			// Re-inherit vertices elements, also
			UMLRTTransition transition = UMLRTTransition.getInstance((Transition) request.getElementToExclude());
			if (transition != null) {
				List<? extends Vertex> verticesToReinherit = Stream.of(transition.getSource(), transition.getTarget())
						.filter(Objects::nonNull)
						.filter(UMLRTNamedElement::isExcluded)
						.map(UMLRTVertex::toUML)
						.collect(Collectors.toList());

				if (!verticesToReinherit.isEmpty()) {
					ICommand reinheritVertices = request.getExcludeDependentsCommand(verticesToReinherit);
					if (reinheritVertices != null) {
						result = CompositeCommand.compose(result, reinheritVertices);
					}
				}
			}
		}

		return result;
	}

	/**
	 * In the case that an inherited {@code trigger} has a local guard in the context in which
	 * it is being excluded, create a command to destroy that guard.
	 * 
	 * @param request
	 *            the exclusion request for the {@code trigger}
	 * @param trigger
	 *            the trigger being excluded
	 * 
	 * @return the guard destruction command, or {@code null} if there is no guard that
	 *         needs to be destroyed
	 */
	protected ICommand getDeleteTriggerGuardCommand(IExcludeElementRequest request, Trigger trigger) {
		ICommand result = null;

		UMLRTTrigger facade = UMLRTTrigger.getInstance(trigger);
		if ((facade != null) && facade.isInherited()) {
			UMLRTGuard guard = facade.getGuard();
			if ((guard != null) && !guard.isInherited()) {
				// Local guard for inherited trigger. Destroy it
				DestroyElementRequest destroy = new DestroyElementRequest(
						request.getEditingDomain(), guard.toUML(), false);
				destroy.setClientContext(request.getClientContext());
				IElementEditService edit = ElementEditServiceUtils.getCommandProvider(
						facade.getTransition().toUML(), request.getClientContext());
				result = (edit == null)
						? UnexecutableCommand.INSTANCE // Don't exclude if we cannot destroy guard
						: edit.getEditCommand(destroy);
			}
		}

		return result;
	}

	@Override
	protected ICommand getBeforeEditContextCommand(GetEditContextRequest request) {
		IEditCommandRequest editCommandRequest = request.getEditCommandRequest();
		if (editCommandRequest instanceof CreateRelationshipRequest) {
			// for creation request of a transition, the source & target should be the right ones
			IElementType type = ((CreateRelationshipRequest) editCommandRequest).getElementType();
			if (ElementTypeUtils.isTypeCompatible(type, UMLElementTypes.TRANSITION) && !ElementTypeUtils.isTypeCompatible(type, UMLRTElementTypesEnumerator.TRANSITION_INTERNAL)) {
				EObject initialSource = ((CreateRelationshipRequest) editCommandRequest).getSource();
				EObject initialTarget = ((CreateRelationshipRequest) editCommandRequest).getTarget();
				if (requiresPseudostateCreation(initialSource, initialTarget)) {
					CreateTransitionEndsContextCommand command = new CreateTransitionEndsContextCommand(request, initialSource, initialTarget);
					return command;
				}
			}
		}
		return super.getBeforeEditContextCommand(request);
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeDestroyDependentsCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {
		// Add source/target pseudo state if transition to delete was the last to come to or get out of that pseudo state
		Transition transition = TypeUtils.as(request.getElementToDestroy(), Transition.class);
		List<Vertex> toDestroy = new ArrayList<>();
		if (needsDestroyEnds(transition.getSource(), transition)) {
			toDestroy.add(transition.getSource());
		}

		if (needsDestroyEnds(transition.getTarget(), transition)) {
			toDestroy.add(transition.getTarget());
		}

		if (!toDestroy.isEmpty()) {
			return request.getDestroyDependentsCommand(toDestroy);
		}
		return null;
	}

	/**
	 * Returns the command to destroy the specific transition end.
	 * 
	 * @param vertex
	 *            the vertex to destroy
	 * @param request
	 *            the original destroy dependents
	 * @return the command to delete the specified vertex
	 */
	protected ICommand getDestroyCommand(Vertex vertex, DestroyDependentsRequest request) {
		DestroyElementRequest destroy = new DestroyElementRequest(request.getEditingDomain(), vertex, request.isConfirmationRequired());

		// propagate my parameters
		destroy.addParameters(request.getParameters());
		destroy.setParameter(DestroyElementRequest.DESTROY_DEPENDENTS_REQUEST_PARAMETER, request);
		destroy.setParameter(DestroyElementRequest.INITIAL_ELEMENT_TO_DESTROY_PARAMETER, vertex);
		try {
			destroy.setClientContext(TypeContext.getContext(vertex));
		} catch (ServiceException e1) {
			Activator.log.error(e1);
		}

		Object eHelperContext = destroy.getEditHelperContext();
		if (eHelperContext instanceof IEditHelperContext) {
			EObject o = ((IEditHelperContext) eHelperContext).getEObject();
			IElementType context = null;
			try {
				context = ElementTypeRegistry.getInstance().getElementType(o, TypeContext.getContext(o));
			} catch (ServiceException e) {
				Activator.log.error(e);
			}

			if (context != null) {
				return context.getEditCommand(destroy);
			}
		}

		return null;

	}

	/**
	 * Checks if then end of the destroyed transition needs also to be destroyed.
	 * 
	 * @param source
	 *            the end of transition to be deleted
	 * @param transition
	 *            the transition to be destroyed
	 * @return <code>true</code> if the transition was the only outgoing and/or incoming transition to that vertex, that vertex being an entry or exit point.
	 */
	protected boolean needsDestroyEnds(Vertex source, Transition transition) {
		if (!(source instanceof Pseudostate)) {
			return false;
		}
		Pseudostate ps = (Pseudostate) source;
		PseudostateKind kind = ps.getKind();
		if (!(PseudostateKind.ENTRY_POINT_LITERAL.equals(kind) || PseudostateKind.EXIT_POINT_LITERAL.equals(kind))) {
			return false;
		}
		// a connection pseudo state => now checks outgoing and incoming
		if (Stream.concat(ps.getOutgoings().stream(), ps.getIncomings().stream()).filter(t -> !t.equals(transition)).findAny().isPresent()) {
			return false;
		}
		return true;
	}

	/**
	 * Check if the source or the target of the transition creation are the real ones.
	 * 
	 * @param initialSource
	 *            initial source for the create request
	 * @param initialTarget
	 *            initial target for the create request
	 * @return <code>true</code> if one or 2 pseudostates should be created
	 */
	protected static boolean requiresPseudostateCreation(EObject initialSource, EObject initialTarget) {
		return requiresPseudoStateCreation(initialSource) || requiresPseudoStateCreation(initialTarget);
	}

	/**
	 * Checks if the given object is a composite state.
	 * 
	 * @param object
	 *            the object to check
	 * @return <code>true</code> if the object is a composite state.
	 */
	protected static boolean requiresPseudoStateCreation(EObject object) {
		return StateUtils.isCompositeState(object);
	}

	/**
	 * Returns the command to create a pseudostate for the given state, with the specified type.
	 * 
	 * @param initialTarget
	 *            the state that will contain the new connection point
	 * @param createRelationshipRequest
	 *            the request used to create the transition that will enter/exit this new pseudostate
	 * @param type
	 *            the kind of element to create
	 * @return the command to create the pseudostate
	 */
	protected static ICommand getCreatePseudostateCommand(State initialTarget, CreateRelationshipRequest createRelationshipRequest, IHintedType type) {
		return ElementEditServiceUtils.getCreateChildCommandWithContext(initialTarget, type);
	}

	/**
	 * {@link GetEditContextCommand} that creates required entry and exit pseudo states if source and target are composite states.
	 */
	public static class CreateTransitionEndsContextCommand extends GetEditContextCommand {

		/** initial source of the transition. */
		private EObject initialSource;

		/** initial target of the transition. */
		private EObject initialTarget;

		/**
		 * Constructs a new command. Automatically initializes the edit context with
		 * that carried in the <code>request</code>.
		 * 
		 * @param request
		 *            the command request
		 * @param initialTarget
		 *            initial target of the transition, may be replaced by a newly created pseudostate
		 * @param initialSource
		 *            initial target of the transition, may be replaced by a newly created pseudostate
		 * 
		 */
		public CreateTransitionEndsContextCommand(GetEditContextRequest request, EObject initialSource, EObject initialTarget) {
			super(request);
			this.initialSource = initialSource;
			this.initialTarget = initialTarget;
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			CreateRelationshipRequest createRelationshipRequest = (CreateRelationshipRequest) ((GetEditContextRequest) getRequest()).getEditCommandRequest();
			// requires source or target update?
			if (requiresPseudoStateCreation(initialSource)) {
				// should create an exit point and set it as the new source
				// pseudo state will be an entry point only if the transition is coming from a state in its own context and not targeting itself
				IHintedType type = UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_EXIT_POINT;
				List<Region> regions = UMLRTExtensionUtil.getUMLRTContents(initialSource, UMLPackage.Literals.STATE__REGION);
				if (!regions.isEmpty() && (regions.get(0) == createRelationshipRequest.getContainer())
						&& (TransitionUtils.getImplicitState(initialSource) != TransitionUtils.getImplicitState(initialTarget))) {

					type = UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_ENTRY_POINT;
				}
				ICommand sourceCommand = getCreatePseudostateCommand((State) initialSource, createRelationshipRequest, type);
				if (sourceCommand != null && sourceCommand.canExecute()) {
					IStatus status = sourceCommand.execute(monitor, info);
					if (status.isOK()) {
						Object result = sourceCommand.getCommandResult().getReturnValue();
						if (result instanceof Pseudostate) {
							Pseudostate exitPoint = (Pseudostate) result;

							// update request for transition creation
							createRelationshipRequest.setSource(exitPoint);
							createRelationshipRequest.setParameter(CreateRelationshipRequest.SOURCE, exitPoint);
						} else {
							return CommandResult.newErrorCommandResult("result is not a pseudostate");
						}
					} else {
						return new CommandResult(status);
					}
				} else {
					return CommandResult.newErrorCommandResult("Command cannot be executed");
				}
			}

			if (requiresPseudoStateCreation(initialTarget)) {
				// should create an pseudo state point and set it as the new target
				// this is usually an entry point, except the case of a transition to a state in its own context coming from another state, it is an exit in this case.
				IHintedType type = UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_ENTRY_POINT;
				List<Region> regions = UMLRTExtensionUtil.getUMLRTContents(initialTarget, UMLPackage.Literals.STATE__REGION);
				if (!regions.isEmpty() && (regions.get(0) == createRelationshipRequest.getContainer())
						&& (TransitionUtils.getImplicitState(initialTarget) != TransitionUtils.getImplicitState(initialSource))) {

					type = UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_EXIT_POINT;
				}

				ICommand targetCommand = getCreatePseudostateCommand((State) initialTarget, createRelationshipRequest, type);
				if (targetCommand != null && targetCommand.canExecute()) {
					IStatus status = targetCommand.execute(monitor, info);
					if (status.isOK()) {
						Object result = targetCommand.getCommandResult().getReturnValue();
						if (result instanceof Pseudostate) {
							Pseudostate entryPoint = (Pseudostate) result;

							// update request for transition creation
							createRelationshipRequest.setTarget(entryPoint);
							createRelationshipRequest.setParameter(CreateRelationshipRequest.TARGET, entryPoint);
						} else {
							return CommandResult.newErrorCommandResult("result is not a pseudostate");
						}
					} else {
						return new CommandResult(status);
					}
				} else {
					return CommandResult.newErrorCommandResult("Command cannot be executed");
				}
			}
			return CommandResult.newOKCommandResult(getEditContext());
		}
	}
}
