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

package org.eclipse.papyrusrt.umlrt.core.types.matcher;

import static org.eclipse.papyrusrt.umlrt.core.utils.StateMachineUtils.isRTStateMachine;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;

/**
 * Matcher for transition triggers in UML-RT state machines.
 */
public class TriggerMatcher implements IElementMatcher {

	public TriggerMatcher() {
		super();
	}

	@Override
	public boolean matches(EObject eObject) {
		boolean result = false;

		if (eObject instanceof Trigger) {
			Trigger trigger = (Trigger) eObject;
			if (trigger.getNamespace() instanceof Transition) {
				Transition transition = (Transition) trigger.getNamespace();
				StateMachine stateMachine = transition.containingStateMachine();

				result = (stateMachine != null) && isRTStateMachine(stateMachine);
			}
		}

		return result;
	}
}
