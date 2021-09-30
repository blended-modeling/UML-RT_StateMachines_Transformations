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
 *  Young-Soo Roh - bug 510024
 *  Christian W. Damus - bug 512362
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.utils;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;

/**
 * State machine utils
 */
public class StateMachineUtils {

	/** View name for the RT state machine diagram */
	public static final String UMLRT_STATE_MACHINE_DIAGRAM = "UML-RT State Machine Diagram";

	/** View id for the RT state machine diagram */
	public static final String UMLRT_STATE_MACHINE_DIAGRAM_ID = "UMLRTStateMachine";

	private static final Set<PseudostateKind> CONNECTION_POINT_KINDS = EnumSet.of(
			PseudostateKind.ENTRY_POINT_LITERAL,
			PseudostateKind.EXIT_POINT_LITERAL);

	public static boolean isRTStateMachine(StateMachine stateMachine) {
		return ElementTypeUtils.matches(stateMachine, IUMLRTElementTypes.RT_STATE_MACHINE_ID);
	}

	/**
	 * Re-target move request to the first region if copied elements are vertices.
	 * 
	 * @param request
	 *            Move request
	 */
	public static void retargetToRegion(MoveRequest request) {
		// Retarget move request to first region
		EObject target = request.getTargetContainer();
		if (target instanceof StateMachine && !((StateMachine) target).getRegions().isEmpty()) {
			request.setTargetContainer(((StateMachine) target).getRegions().get(0));
		} else if (target instanceof State && !((State) target).getRegions().isEmpty()) {
			// Are any of the elements being moved connection-points?
			Collection<?> moved = request.getElementsToMove().keySet();
			if (!moved.stream().anyMatch(StateMachineUtils::isConnectionPoint)) {
				request.setTargetContainer(((State) target).getRegions().get(0));
			}
		}
	}

	/**
	 * Is an {@code object} a connection-point pseudostate?
	 * 
	 * @param object
	 *            an object
	 * @return whether it is an entry- or exit-point
	 */
	public static boolean isConnectionPoint(Object object) {
		return (object instanceof Pseudostate) && CONNECTION_POINT_KINDS.contains(((Pseudostate) object).getKind());
	}
}
