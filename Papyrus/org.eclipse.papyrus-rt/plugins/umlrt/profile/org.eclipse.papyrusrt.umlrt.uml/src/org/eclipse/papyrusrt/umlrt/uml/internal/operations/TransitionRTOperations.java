/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.uml.internal.operations;

import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.redefines;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableSingleContainment;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Externalized operations for {@link Transition}s
 */
public class TransitionRTOperations extends UMLUtil {

	// Not meant to be instantiable by clients
	protected TransitionRTOperations() {
		super();
	}

	public static Constraint getGuard(InternalUMLRTTransition transition, Trigger trigger) {
		return UMLRTExtensionUtil.<Constraint> getUMLRTContents(transition, UMLPackage.Literals.NAMESPACE__OWNED_RULE)
				.stream()
				.filter(r -> r.getConstrainedElements().contains(trigger))
				.findAny().orElse(null);
	}

	/**
	 * Resolve the redefinition of an {@code inherited} vertex in the given
	 * {@code transition}'s redefinition context.
	 * 
	 * @param transition
	 *            a transition that is resolving its source or target vertex
	 * @param inherited
	 *            an inherited vertex
	 * @return the resolved redefinition, or the same {@code inherited} vertex if it is not
	 *         a member of the {@code transition}'s redefinition context
	 */
	public static Vertex resolveVertex(InternalUMLRTTransition transition, EObject inherited) {
		Vertex result = null;

		if (inherited instanceof Vertex) {
			Vertex inheritedVertex = (Vertex) inherited;
			result = inheritedVertex;

			Region container = transition.getContainer();
			if (container != null) {
				// What kind of a vertex is it?
				EReference containment = inheritedVertex.eContainmentFeature();
				if ((containment == UMLPackage.Literals.REGION__SUBVERTEX)
						|| (containment == ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX)) {

					// Its a vertex in a region. First, try the easy case
					Vertex resolved = resolveVertex(container, inheritedVertex);
					if (resolved == null) {
						// Search the region redefinition chain
						for (container = container.getExtendedRegion(); (container != null) && (resolved == null); container = container.getExtendedRegion()) {
							resolved = resolveVertex(container, inheritedVertex);
						}
					}
					if (resolved != null) {
						result = resolved;
					}
				} else if ((containment == UMLPackage.Literals.STATE_MACHINE__CONNECTION_POINT)
						|| (containment == ExtUMLExtPackage.Literals.STATE_MACHINE__IMPLICIT_CONNECTION_POINT)) {

					// It's a state machine's connection point. First, try the easy case
					StateMachine machine = transition.containingStateMachine();
					if (machine != null) {
						Vertex resolved = resolveVertex(machine, inheritedVertex);
						if ((resolved == null) && !machine.getExtendedStateMachines().isEmpty()) {
							// Search the state machine redefinition chain (UML-RT supports only single inheritance)
							for (machine = machine.getExtendedStateMachines().get(0); (machine != null) && (resolved == null);) {
								resolved = resolveVertex(machine, inheritedVertex);
								if (machine.getExtendedStateMachines().isEmpty()) {
									machine = null;
								} else {
									machine = machine.getExtendedStateMachines().get(0);
								}
							}
						}
						if (resolved != null) {
							result = resolved;
						}
					}
				} else if ((containment == UMLPackage.Literals.STATE__CONNECTION_POINT)
						|| (containment == ExtUMLExtPackage.Literals.STATE__IMPLICIT_CONNECTION_POINT)) {

					Vertex resolved = null;

					// It's a composite state's connection point. First, try the easy case
					State state = container.getState();
					if (state != null) {
						resolved = resolveVertex(state, inheritedVertex);
					}
					if (resolved == null) {
						// Find the redefinition of the composite state in my state machine
						StateMachine machine = transition.containingStateMachine();
						if (machine != null) {
							InternalUMLRTState composite = (InternalUMLRTState) ((Pseudostate) inheritedVertex).getState();
							Optional<? extends State> redefState = composite.rtDescendants()
									.filter(s -> s.containingStateMachine() == machine)
									.findAny();
							resolved = redefState
									.map(myComposite -> resolveVertex(myComposite, inheritedVertex))
									.orElse(null);
						}
					}
					if (resolved != null) {
						result = resolved;
					}
				}

				if (result == inheritedVertex) {
					// Still no luck? Must be an edge case like an internal transition or
					// some kind of not-so-valid redefinition
					StateMachine stateMachine = transition.containingStateMachine();
					if ((stateMachine != null) && (inherited instanceof InternalUMLRTElement)) {
						InternalUMLRTElement internalInherited = (InternalUMLRTElement) inherited;

						// Search all of its vertices
						for (Iterator<Vertex> allVertices = allVertices(stateMachine); allVertices.hasNext();) {
							Vertex next = allVertices.next();
							if ((next instanceof InternalUMLRTElement)
									&& ((InternalUMLRTElement) next).rtRedefines(internalInherited)) {

								result = next;
								break;
							}
						}
					}
				}
			}
		}

		return result;
	}

	protected static Vertex resolveVertex(Namespace namespace, Vertex inherited) {
		Vertex result = null;

		if (inherited instanceof State) {
			// These properly support redefinition
			result = namespace.getOwnedMembers().stream()
					.filter(State.class::isInstance).map(State.class::cast)
					.filter(state -> state.getRedefinedState() == inherited)
					.findAny().orElse(null);
		} else {
			// Pseudostates do not support redefinition
			result = (Vertex) namespace.getOwnedMembers().stream()
					.filter(inherited.eClass()::isInstance)
					.filter(InternalUMLRTElement.class::isInstance).map(InternalUMLRTElement.class::cast)
					.filter(v -> v.rtGetRedefinedElement() == inherited)
					.findAny().orElse(null);
		}

		return result;
	}

	protected static Iterator<Vertex> allVertices(StateMachine stateMachine) {
		@SuppressWarnings("serial")
		TreeIterator<EObject> tree = new AbstractTreeIterator<EObject>(stateMachine, false) {
			@Override
			protected Iterator<? extends EObject> getChildren(Object object) {
				Iterator<? extends EObject> result = (object instanceof Region)
						? contents((Region) object, UMLPackage.Literals.REGION__SUBVERTEX)
						: (object instanceof State)
								? contents((State) object, UMLPackage.Literals.STATE__REGION,
										UMLPackage.Literals.STATE__CONNECTION_POINT)
								: (object instanceof StateMachine)
										? contents((StateMachine) object, UMLPackage.Literals.STATE_MACHINE__REGION)
										: Collections.emptyIterator();
				return result;
			}

			private Iterator<? extends EObject> contents(EObject object, EReference reference, EReference... rest) {
				return UMLRTExtensionUtil.<EObject> getUMLRTContents(object, reference, rest).iterator();
			}
		};

		return new Iterator<Vertex>() {
			boolean done;
			Vertex prepared;

			@Override
			public boolean hasNext() {
				while (prepared == null) {
					if (!tree.hasNext()) {
						done = true;
						break;
					}
					Object next = tree.next();
					if (next instanceof Vertex) {
						prepared = (Vertex) next;
					}
				}
				return !done;
			}

			@Override
			public Vertex next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				Vertex result = prepared;
				prepared = null;
				return result;
			}
		};
	}

	static Effect getInheritableEffect(InternalUMLRTTransition owner) {
		return (Effect) EcoreUtil.getExistingAdapter(owner, Effect.class);
	}

	static Effect demandInheritableEffect(InternalUMLRTTransition owner) {
		Effect result = getInheritableEffect(owner);

		if (result == null) {
			result = new Effect(owner);
			owner.eAdapters().add(0, result);
		}

		return result;
	}

	public static Behavior getEffect(InternalUMLRTTransition owner) {
		return demandInheritableEffect(owner).getInheritable();
	}

	public static void setEffect(InternalUMLRTTransition owner, Behavior newEffect) {
		demandInheritableEffect(owner).set(newEffect);
	}

	public static boolean isSetEffect(InternalUMLRTTransition owner) {
		Effect effect = getInheritableEffect(owner);
		return (effect != null) && effect.isSet();
	}

	public static void unsetEffect(InternalUMLRTTransition owner) {
		Effect effect = getInheritableEffect(owner);
		if (effect != null) {
			effect.unset();
		}
	}

	/**
	 * UML-RT provides for the redefining transition to exclude or add triggers, and to change the guard
	 * conditions of triggers (which concept UML doesn't even have).
	 */
	public static boolean isConsistentWith(InternalUMLRTTransition self, RedefinableElement transition) {
		boolean result = false;

		if ((transition instanceof Transition) && transition.isRedefinitionContextValid(self)) {
			Transition redefining = (Transition) transition;

			// The source cannot be changed (though it may be a redefinition of the original source)
			Vertex originalSource = self.getSource();
			Vertex source = redefining.getSource();

			if ((source == null) || (originalSource == null) || !redefines(source, originalSource)) {
				// Incompatible source vertex
				return result;
			}

			// We can change or add triggers, but cannot actually remove any
			List<Trigger> originalTriggers = UMLRTExtensionUtil.getUMLRTContents(self, UMLPackage.Literals.TRANSITION__TRIGGER);
			List<Trigger> triggers = UMLRTExtensionUtil.getUMLRTContents(redefining, UMLPackage.Literals.TRANSITION__TRIGGER);
			if (triggers.size() < originalTriggers.size()) {
				// Obviously, we've removed one
				return result;
			}

			if (!originalTriggers.isEmpty() && !originalTriggers.stream().allMatch(
					t1 -> triggers.stream().anyMatch(t2 -> redefines(t2, t1)))) {
				// We don't have some kind of redefinition for one of the inherited triggers (not even
				// an exclusion, which would be okay)
				result = false;
			}

			result = true;
		}

		return result;
	}

	//
	// Nested types
	//

	private static final class Effect extends InheritableSingleContainment<Behavior> {
		Effect(InternalUMLRTTransition owner) {
			super(owner.eDerivedStructuralFeatureID(UMLPackage.TRANSITION__EFFECT, Behavior.class));
		}

		@Override
		public Object get(boolean resolve) {
			return ((InternalUMLRTTransition) getTarget()).umlGetEffect(resolve);
		}

		@Override
		protected NotificationChain basicSet(Behavior newEffect, NotificationChain msgs) {
			return ((InternalUMLRTTransition) getTarget()).umlBasicSetEffect(newEffect, msgs);
		}

		@Override
		protected Behavior createRedefinition(Behavior inherited) {
			InternalUMLRTOpaqueBehavior result = (InternalUMLRTOpaqueBehavior) super.createRedefinition(inherited);
			result.umlSetRedefinedElement((InternalUMLRTElement) inherited);
			result.handleRedefinedTransition(
					((InternalUMLRTTransition) getTarget()).rtGetRedefinedElement());
			return result;
		}
	}

}
