/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.canonical;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPartUtils.isInRegionCompartment;
import static org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPartUtils.isInternalTransitionCompartment;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.BasicDecorationNode;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLSemanticChildrenStrategy;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrusrt.umlrt.core.utils.Either;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPartUtils;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.util.Pair;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTSwitch;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Canonical edit-policy specializations for RT state machine diagrams.
 */
public class RTStateMachineSemanticChildrenStrategy extends DefaultUMLSemanticChildrenStrategy {

	/**
	 * Initializes me.
	 */
	public RTStateMachineSemanticChildrenStrategy() {
		super();
	}

	@Override
	public List<? extends EObject> getCanonicalSemanticChildren(EObject semanticFromEditPart, View viewFromEditPart) {
		Stream<? extends Element> result;

		UMLRTNamedElement subject = resolveLocalSemanticElement(semanticFromEditPart, viewFromEditPart);

		result = new UMLRTSwitch<Stream<? extends Element>>() {
			@Override
			public Stream<? extends Element> caseStateMachine(UMLRTStateMachine stateMachine) {
				// We want to show the region compartment of a state machine
				return (semanticFromEditPart instanceof Region)
						? toUML(stateMachine.getVertices().stream())
						: UMLRTExtensionUtil.<Region> getUMLRTContents(stateMachine.toUML(),
								UMLPackage.Literals.STATE_MACHINE__REGION).stream();
			}

			@Override
			public Stream<? extends Element> caseState(UMLRTState state) {
				Stream<? extends Element> result = Stream.empty();
				Predicate<UMLRTTransition> isInternal = UMLRTTransition::isInternal;

				if (state.isComposite() && RTStateEditPartUtils.isInternalTransitionCompartment(viewFromEditPart)) {
					// Simplest case is the internal-transitions compartment
					result = state.getSubtransitions().stream()
							.filter(isInternal)
							.map(UMLRTTransition::toUML);
				} else if (semanticFromEditPart instanceof Region) {
					// Show the contents of the region
					result = toUML(state.getSubvertices().stream());
				} else {
					// For a state, we always want to show the connection points, except not
					// in a compartment
					if (!(viewFromEditPart instanceof BasicDecorationNode)) {
						result = toUML(state.getConnectionPoints());
					}

					// Show regions if it's not a state in a region (in which case,
					// it's a composite state sub-diagram)
					if (!isStateInRegion(viewFromEditPart)) {
						result = Stream.concat(result,
								UMLRTExtensionUtil.<Region> getUMLRTContents(state.toUML(),
										UMLPackage.Literals.STATE__REGION).stream());
					}
				}

				// Does the state have entry/exit behaviours?
				UMLRTOpaqueBehavior entry = state.getEntry();
				UMLRTOpaqueBehavior exit = state.getExit();
				if ((entry != null) || (exit != null)) {
					List<? extends Element> behaviors = (entry == null)
							? Collections.singletonList(exit.toUML())
							: (exit == null)
									? Collections.singletonList(entry.toUML())
									: Pair.of(entry.toUML(), exit.toUML());

					result = Stream.concat(result, behaviors.stream());
				}

				return result;
			}
		}.doSwitch(subject);

		return (result == null)
				? null
				: result.map(UMLRTExtensionUtil::getRootDefinition).filter(Objects::nonNull).collect(Collectors.toList());
	}

	/**
	 * Resolve the local semantic element for which canonical queries are being
	 * made, accounting for inheritance.
	 * 
	 * @param semanticFromEditPart
	 *            the semantic element as understood by the <em>Canonical Edit Policy</em>
	 * @param viewFromEditPart
	 *            the diagram view providing the local inheritance context
	 * 
	 * @return the local semantic element, as a façade
	 */
	protected UMLRTNamedElement resolveLocalSemanticElement(EObject semanticFromEditPart, View viewFromEditPart) {
		UMLRTNamedElement result;

		StateMachine stateMachine = getContextualStateMachine(viewFromEditPart);
		if (semanticFromEditPart instanceof Region) {
			// This has no façade of its own
			result = resolveRedefinition(stateMachine,
					(UMLRTNamedElement) UMLRTFactory.create(((Region) semanticFromEditPart).getOwner()));
		} else {
			result = resolveRedefinition(stateMachine,
					(UMLRTNamedElement) UMLRTFactory.create(semanticFromEditPart));
		}

		return result;
	}

	StateMachine getContextualStateMachine(View view) {
		return Optional.ofNullable(EditPartInheritanceUtils.getContextStateMachine(view))
				.map(UMLRTStateMachine::toUML)
				.orElse(null);
	}

	StateMachine getContextualStateMachine(IGraphicalEditPart editPart) {
		return getContextualStateMachine(editPart.getNotationView());
	}

	<T extends UMLRTNamedElement> T resolveRedefinition(StateMachine context, T facade) {
		T result = facade;

		// The state machine, itself, trivially resolves to itself, of course
		if ((facade != null) && !(facade instanceof UMLRTStateMachine)) {
			UMLRTState compositeState = null;
			if (facade instanceof UMLRTVertex) {
				compositeState = ((UMLRTVertex) facade).getState();
			} else if (facade instanceof UMLRTTransition) {
				compositeState = ((UMLRTTransition) facade).getState();
			}

			if (compositeState != null) {
				// Resolve in this context
				compositeState = resolveRedefinition(context, compositeState);
				result = compositeState.getRedefinitionOf(facade);
			} else {
				// It's in the state machine context
				UMLRTStateMachine stateMachine = UMLRTStateMachine.getInstance(context);
				result = stateMachine.getRedefinitionOf(facade);
			}
		}

		return result;
	}

	Stream<Element> toUML(List<? extends FacadeObject> facades) {
		return facades.stream().map(FacadeObject::toUML);
	}

	Stream<Element> toUML(Stream<? extends FacadeObject> facades) {
		return facades.map(FacadeObject::toUML);
	}

	@Override
	public Collection<? extends EObject> getCanonicalDependents(EObject semanticFromEditPart, View viewFromEditPart) {
		// We must listen for changes to the appropriate redefinition or inherited element
		// in the context of this diagram, not the root definition that the notation view
		// references
		UMLRTNamedElement subject = resolveLocalSemanticElement(semanticFromEditPart, viewFromEditPart);

		if ((subject instanceof UMLRTState) && ((UMLRTState) subject).isComposite() && isInternalTransitionCompartment(viewFromEditPart)) {
			return UMLRTExtensionUtil.getUMLRTContents(subject.toUML(), UMLPackage.Literals.STATE__REGION);
		} else if ((subject instanceof UMLRTVertex) && isInRegionCompartment(viewFromEditPart)) {
			return Collections.singletonList(subject.toUML().getOwner());
		} else if (subject instanceof UMLRTConnectionPoint) {
			UMLRTConnectionPoint connPt = (UMLRTConnectionPoint) subject;
			UMLRTState onState = connPt.getState();
			Region region;

			// retrieve the parent state, then we should listen to the content of the region owning the state parent.
			// Unless we are on the border of a nested state diagram, in which case we need to listen to its region
			// for internal connections
			Diagram diagram = viewFromEditPart.getDiagram();
			State state = TypeUtils.as(EditPartInheritanceUtils.resolveSemanticElement(diagram), State.class);
			UMLRTState stateContext = (state == null) ? null : UMLRTState.getInstance(state);
			if ((stateContext != null) && onState.redefines(stateContext)) {
				// We are a connection point of the diagram's state frame.
				region = onState.toRegion();
			} else {
				// We are a connection point on a state in the larger context.
				// Get the region of that context
				region = Either.or(onState.getState(), onState.getStateMachine())
						.map(UMLRTState::toRegion, UMLRTStateMachine::toRegion)
						.getEither(Region.class);
			}

			return (region == null) ? Collections.emptyList() : Collections.singletonList(region);
		} else {
			return super.getCanonicalDependents(semanticFromEditPart, viewFromEditPart);
		}
	}

	@Override
	public List<? extends EObject> getCanonicalSemanticConnections(EObject semanticFromEditPart, View viewFromEditPart) {
		List<? extends EObject> result = null;

		UMLRTNamedElement element = resolveLocalSemanticElement(semanticFromEditPart, viewFromEditPart);
		if (element instanceof UMLRTVertex) {
			UMLRTVertex vertex = (UMLRTVertex) element;

			List<UMLRTTransition> transitions = new UniqueEList.FastCompare<>(vertex.getIncomings());
			transitions.addAll(vertex.getOutgoings());
			Predicate<UMLRTTransition> internal = UMLRTTransition::isInternal;

			Either<UMLRTState, UMLRTStateMachine> context = EditPartInheritanceUtils.getStateMachineContext(viewFromEditPart);
			Region represented = context.map(UMLRTState::toRegion, UMLRTStateMachine::toRegion)
					.getEither(Region.class);
			Predicate<Transition> inRepresentedRegion = transition -> transition.getContainer() == represented;

			result = transitions.stream()
					.filter(internal.negate())
					.map(UMLRTTransition::toUML)
					.filter(inRepresentedRegion)
					.map(UMLRTExtensionUtil::getRootDefinition)
					.filter(Objects::nonNull)
					.distinct() // incomings and outoings both include self-transitions
					.collect(Collectors.toList());
		}

		return result;
	}

	protected boolean isStateInRegion(View notationView) {
		boolean result = false;

		// Look for shape containing the compartment, if a compartment
		while ((notationView instanceof BasicDecorationNode) && (notationView.eContainer() instanceof View)) {
			notationView = (View) notationView.eContainer();
		}

		// Is it a state?
		if (StateEditPart.VISUAL_ID.equals(notationView.getType())) {
			// In a compartment?
			result = notationView.eContainer() instanceof BasicDecorationNode;
		}

		return result;
	}

	@Override
	public IGraphicalEditPart resolveSourceEditPart(EObject connectionElement, IGraphicalEditPart context) {
		if (connectionElement instanceof Transition) {
			// Resolve it in our inheriting StateMachine context
			connectionElement = resolveTransition((Transition) connectionElement, context);
		}

		return super.resolveSourceEditPart(connectionElement, context);
	}

	@Override
	public IGraphicalEditPart resolveTargetEditPart(EObject connectionElement, IGraphicalEditPart context) {
		if (connectionElement instanceof Transition) {
			// Resolve it in our inheriting StateMachine context
			connectionElement = resolveTransition((Transition) connectionElement, context);
		}

		return super.resolveTargetEditPart(connectionElement, context);
	}

	private Transition resolveTransition(Transition umlTransition, IGraphicalEditPart context) {
		Transition result = umlTransition;

		UMLRTTransition transition = UMLRTTransition.getInstance(umlTransition);
		StateMachine stateMachine = getContextualStateMachine(context);
		if ((transition != null) && (stateMachine != null)) {
			result = resolveRedefinition(stateMachine, transition).toUML();
		}

		return result;
	}

	@Override
	public Object getSource(EObject connectionElement) {
		Object result = super.getSource(connectionElement);

		if (connectionElement instanceof Transition) {
			Transition transition = (Transition) connectionElement;
			result = transition.getSource();
		}

		return result;
	}

	@Override
	public Object getTarget(EObject connectionElement) {
		Object result = super.getTarget(connectionElement);

		if (connectionElement instanceof Transition) {
			Transition transition = (Transition) connectionElement;
			result = transition.getTarget();
		}

		return result;
	}
}
