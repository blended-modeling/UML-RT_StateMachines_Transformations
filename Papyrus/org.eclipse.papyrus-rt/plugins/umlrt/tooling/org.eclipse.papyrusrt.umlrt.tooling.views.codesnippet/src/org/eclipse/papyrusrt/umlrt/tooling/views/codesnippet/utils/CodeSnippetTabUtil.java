/*******************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.papyrusrt.umlrt.core.utils.StateMachineUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.StateUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;

/**
 * Utility class for code snippet tab.
 * 
 * @author ysroh
 *
 */
public final class CodeSnippetTabUtil {
	/**
	 * 
	 * Constructor.
	 *
	 */
	private CodeSnippetTabUtil() {
	}

	/**
	 * Return state for the given context.
	 * 
	 * @param context
	 *            input
	 * @return state or null
	 */
	public static State getState(EObject context) {
		State state = null;
		if (context instanceof State) {
			state = (State) context;
		} else if (context instanceof OpaqueBehavior && ((OpaqueBehavior) context).getOwner() instanceof State) {
			State s = (State) ((OpaqueBehavior) context).getOwner();
			// check if this is entry or exit of state
			if (s.getEntry() == context || s.getExit() == context) {
				state = s;
			}
		}
		if (state != null) {
			if (StateUtils.isRTState(state)) {
				return state;
			}
		}
		return null;
	}

	/**
	 * Return transition for the given context.
	 * 
	 * @param context
	 *            context
	 * @return transition or null
	 */
	public static Transition getTransition(EObject context) {
		Transition transition = null;
		if (context instanceof Transition) {
			transition = (Transition) context;
		} else if ((context instanceof OpaqueBehavior || context instanceof Constraint)
				&& ((Element) context).getOwner() instanceof Transition) {
			Transition t = (Transition) ((Element) context).getOwner();
			// check if the context is either effect or guard of transition
			if (t.getEffect() == context || t.getGuard() == context) {
				transition = t;
			}
		}
		if (transition != null) {
			StateMachine sm = transition.containingStateMachine();
			if (StateMachineUtils.isRTStateMachine(sm)) {
				return transition;
			}
		}
		return null;
	}

	/**
	 * Get trigger for the given context.
	 * 
	 * @param context
	 *            context
	 * @return trigger
	 */
	public static Trigger getTrigger(EObject context) {
		Trigger trigger = null;
		if (context instanceof Trigger) {
			Transition transition = (Transition) context.eContainer();
			StateMachine sm = transition.containingStateMachine();
			if (StateMachineUtils.isRTStateMachine(sm)) {
				trigger = (Trigger) context;
			}
		}

		if (context instanceof Constraint) {
			// user selected trigger guard directly.
			UMLRTGuard guard = UMLRTGuard.getInstance((Constraint) context);
			if (guard != null && guard.getTrigger() != null) {
				trigger = guard.getTrigger().toUML();
			}
		}
		return trigger;
	}

	/**
	 * Get operation for given context.
	 * 
	 * @param context
	 *            context
	 * @return Operation
	 */
	public static Operation getOperation(EObject context) {
		Operation op = null;
		if (!(context instanceof Element) || !UMLRTProfileUtils.isUMLRTProfileApplied((Element) context)) {
			return op;
		}
		if (context instanceof Operation && context.eContainer() instanceof org.eclipse.uml2.uml.Class) {
			op = (Operation) context;
		}
		if (context instanceof OpaqueBehavior) {
			// user selected method directly and this behaviour is not
			// necessarily belong to the same owner as operation so check the
			// inverse reference.
			op = UML2Util.getInverseReferences(context).stream().map(Setting::getEObject)
					.filter(Operation.class::isInstance).map(Operation.class::cast).filter(o -> {
						return o.getMethods().contains(context);
					}).findFirst().orElse(null);
		}
		return op;
	}
}
