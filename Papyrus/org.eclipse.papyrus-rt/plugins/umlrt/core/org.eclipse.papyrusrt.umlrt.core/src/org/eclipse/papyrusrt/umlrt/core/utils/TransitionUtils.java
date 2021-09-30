/*******************************************************************************
 * Copyright (c) 2016, 2017 Zeligsoft (2009) Limited, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *  Young-Soo Roh - Initial API and implementation
 *  CEA LIST - Additions 
 *  Christian W. Damus - bug 510315
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.utils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.Trigger;

import com.google.common.base.Strings;

/**
 * Utility methods around {@link Transition}.
 */
public final class TransitionUtils {

	/**
	 * Private constructor, as this class should not be instantiated.
	 */
	private TransitionUtils() {
		// empty
	}

	/**
	 * Return expected transition kind based on source and target vertex.
	 * 
	 * @param transition
	 *            transition
	 * @param source
	 *            source vertex
	 * @param target
	 *            target vertex
	 * @return expected transition kind
	 */
	public static TransitionKind getTransitionKind(Transition transition, EObject source, EObject target) {

		EObject sourceState = getImplicitState(source);
		EObject targetState = getImplicitState(target);
		boolean isLoop = sourceState == targetState;

		TransitionKind expectedKind = TransitionKind.EXTERNAL_LITERAL;
		if (StateUtils.isCompositeState(sourceState)) {
			if (!isLoop) {
				if (Stream.iterate(targetState, EObject::eContainer).filter(e -> e == sourceState || e.eContainer() == null).findFirst().get() == sourceState) {
					// If the source of the transition is a composite state
					// in the context of the state itself,
					// i.e. on the "inside" of the state selecting the border of
					// the state as source, and the target is a, composite or simple,
					// state nested inside the state, then a local transition shall be created
					expectedKind = TransitionKind.LOCAL_LITERAL;
				}
			}
			if (isLoop && Stream.iterate(transition, EObject::eContainer).filter(e -> e == sourceState || e.eContainer() == null).findFirst().get() == sourceState) {
				// If the source and target is the same composite state in the
				// context of the state itself, i.e. on the "inside" of the
				// state selecting the border of the state itself,
				// then an local self-transition shall be created
				// If kind was already set to internal, let it be internal.
				// => The conditions are the same as local conditions
				if (transition != null && TransitionKind.INTERNAL_LITERAL.equals(transition.getKind())) {
					expectedKind = TransitionKind.INTERNAL_LITERAL;
				} else {
					expectedKind = TransitionKind.LOCAL_LITERAL;
				}
			}
		}
		return expectedKind;
	}

	/**
	 * Return expected transition kind based on source and target vertex, and
	 * expected container. This may be used even if transition is not created yet.
	 * As it has not been created yet, there will be a confusion between local and internal in certain cases.
	 * 
	 * @param transitionContainer
	 *            the future transition container
	 * @param source
	 *            source vertex
	 * @param target
	 *            target vertex
	 * @return expected transition kind
	 */
	public static TransitionKind getTransitionKind(Region transitionContainer, EObject source, EObject target) {

		EObject sourceState = getImplicitState(source);
		EObject targetState = getImplicitState(target);
		boolean isLoop = sourceState == targetState;

		TransitionKind expectedKind = TransitionKind.EXTERNAL_LITERAL;
		if (sourceState instanceof State && ((State) sourceState).isComposite()) {
			if (!isLoop && Stream.iterate(targetState, EObject::eContainer).filter(e -> e == sourceState || e.eContainer() == null).findFirst().get() == sourceState) {
				// If the source of the transition is a composite state
				// in the context of the state itself,
				// i.e. on the "inside" of the state selecting the border of
				// the state as source, and the target is a, composite or simple,
				// state nested inside the state, then a local transition shall be created
				expectedKind = TransitionKind.LOCAL_LITERAL;
			}
			if (isLoop && transitionContainer != null && Stream.iterate(transitionContainer, EObject::eContainer).filter(e -> e == sourceState || e.eContainer() == null).findFirst().get() == sourceState) {
				// If the source and target is the same composite state in the
				// context of the state itself, i.e. on the "inside" of the
				// state selecting the border of the state itself,
				// then an local self-transition shall be created
				// impossible to check for internal as transition is not yet available.
				// => The conditions are the same as local conditions
				expectedKind = TransitionKind.LOCAL_LITERAL;
			}
		}
		return expectedKind;
	}

	/**
	 * Return container state if vertex is either entry or exit point.
	 * 
	 * @param vertex
	 *            vertex
	 * @return state
	 */
	public static EObject getImplicitState(EObject vertex) {
		if (vertex instanceof Pseudostate) {
			PseudostateKind kind = ((Pseudostate) vertex).getKind();
			if (PseudostateKind.ENTRY_POINT_LITERAL.equals(kind) || PseudostateKind.EXIT_POINT_LITERAL.equals(kind)) {
				return vertex.eContainer();
			}
		}
		return vertex;
	}

	/**
	 * Checks if the given object is a {@link Transition} with the {@link TransitionKind#INTERNAL} kind.
	 * 
	 * @param object
	 *            the object to check
	 * @return <code>true</code> if the given object is a {@link Transition} with the {@link TransitionKind#INTERNAL} kind, false otherwise.
	 */
	public static boolean isInternalTransition(EObject object) {
		return object instanceof Transition && TransitionKind.INTERNAL_LITERAL == ((Transition) object).getKind();
	}

	/**
	 * Returns <code>true</code> if a transition has a specified name, but no triggers.
	 * 
	 * @param transition
	 *            the transition to test
	 * @return <code>true</code> if the name of the transition is neither null nor empty, and it has no triggers
	 */
	public static boolean hasNameAndNoTriggers(Transition transition) {
		UMLRTTransition rt = UMLRTTransition.getInstance(transition);
		return (rt != null) && !Strings.isNullOrEmpty(rt.getName()) && rt.getTriggers().isEmpty();
	}

	public static List<Constraint> getGuards(Transition transition) {
		UMLRTTransition facade = UMLRTTransition.getInstance(transition);
		return (facade == null)
				? Collections.emptyList()
				: Stream.concat(
						Stream.of(facade.getGuard()),
						facade.getTriggers().stream().map(UMLRTTrigger::getGuard))
						.filter(Objects::nonNull)
						.map(UMLRTGuard::toUML)
						.collect(Collectors.toList());
	}

	public static Constraint getTriggerGuard(Trigger trigger) {
		return Optional.ofNullable(UMLRTTrigger.getInstance(trigger))
				.map(UMLRTTrigger::getGuard)
				.map(UMLRTGuard::toUML)
				.orElse(null);
	}
}
