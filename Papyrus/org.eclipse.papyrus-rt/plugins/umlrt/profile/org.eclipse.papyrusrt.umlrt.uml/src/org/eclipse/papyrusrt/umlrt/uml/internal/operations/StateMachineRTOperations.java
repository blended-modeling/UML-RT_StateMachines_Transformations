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

import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTStateMachine;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Externalized operations for {@link StateMachine}s
 */
public class StateMachineRTOperations extends UMLUtil {

	// Not meant to be instantiable by clients
	protected StateMachineRTOperations() {
		super();
	}

	public static boolean isRedefinitionContextValid(InternalUMLRTStateMachine self, RedefinableElement redefinedElement) {

		if (redefinedElement instanceof StateMachine) {
			StateMachine redefinedStateMachine = (StateMachine) redefinedElement;
			BehavioredClassifier context = self.getContext();
			BehavioredClassifier parentContext = redefinedStateMachine.getContext();

			return (context == null)
					? (parentContext == null) && self.allParents().contains(redefinedStateMachine)
					: (parentContext != null) && context.allParents().contains(parentContext);
		}

		return false;
	}


}
