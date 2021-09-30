/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *  
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.utils;

import static org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils.getConfigureAsCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTRegion;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;

/**
 * Utilities for inspecting and working with regions in an UML-RT model.
 */
public class RegionUtils {

	/**
	 * Queries whether a {@code region} is stereotyped as an UML-RT region.
	 * 
	 * @param region
	 *            a region
	 * 
	 * @return whether it is an <tt>RTRegion</tt>
	 */
	public static boolean isRTRegion(Region region) {
		return ElementTypeUtils.matches(region, IUMLRTElementTypes.RT_REGION_ID);
	}

	/**
	 * Obtains a command that ensures that a {@code region} is stereotyped as an
	 * UML-RT region.
	 * 
	 * @param region
	 *            a region
	 * 
	 * @return the command
	 */
	public static ICommand getConfigureAsRTRegionCommand(Region region) {
		return getConfigureAsCommand(UMLRTElementTypesEnumerator.RT_REGION, region);
	}

	/**
	 * Applies the {@link RTRegion} stereotype to a {@code region}, if it is
	 * not already applied.
	 * 
	 * @param region
	 *            a region
	 * @return the region with the stereotype applied
	 */
	public static Region applyStereotype(Region region) {
		return UMLRTProfileUtils.ensureStereotype(region,
				UMLRTStateMachinesPackage.Literals.RT_REGION);
	}

	public static List<Pseudostate> getInitialPseudoStates(Region region) {
		List<Pseudostate> pseudoStates = new ArrayList<>();
		for (Vertex vertex : region.getSubvertices()) {
			if (isInitialPseudoState(vertex)) {
				pseudoStates.add((Pseudostate) vertex);
			}
		}
		return pseudoStates;
	}

	public static List<State> getStates(Region region) {
		List<State> states = new ArrayList<>();
		for (Vertex vertex : region.getSubvertices()) {
			if (vertex instanceof State) {
				states.add((State) vertex);
			}
		}
		return states;
	}

	public static boolean isInitialPseudoState(Vertex vertex) {
		if (vertex instanceof Pseudostate) {
			Pseudostate ps = (Pseudostate) vertex;
			if (ps.getKind() == PseudostateKind.INITIAL_LITERAL) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Approves the specified move request.
	 * 
	 * @param request
	 *            the request to approve
	 * @return <code>true</code> if move should be approved
	 */
	public static boolean shouldApproveMoveRequest(MoveRequest request) {
		Region targetRegion = null;
		if (!(request.getTargetContainer() instanceof Region)) {
			return false;
		}
		targetRegion = (Region) request.getTargetContainer();

		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=510024#c9
		for (Object o : request.getElementsToMove().keySet()) {
			if (!(o instanceof EObject) ||
					((EObject) o).eContainer() != null) {
				// do not allow move of any sorts
				// transition should not be copied or moved either
				// internal copy for paste operation should have null container
				return false;
			}
			if (o instanceof Transition) {
				Transition transition = (Transition) o;
				if (transition.getSource() == null || transition.getSource().eContainer() != null) {
					// if source vertex is not a copy
					return false;
				}
				if (transition.getTarget() == null || transition.getTarget().eContainer() != null) {
					// if target vertex is not a copy
					return false;
				}
			}
		}

		// extract all the pseudo states in the list
		// check for each constraint kind
		@SuppressWarnings("unchecked")
		List<Pseudostate> pseudostatesToMove = (List<Pseudostate>) request.getElementsToMove().keySet().stream().filter(Pseudostate.class::isInstance).map(Pseudostate.class::cast).collect(Collectors.toList());
		List<Pseudostate> pseudostatesInRegion = targetRegion.getSubvertices().stream().filter(Pseudostate.class::isInstance).map(Pseudostate.class::cast).collect(Collectors.toList());

		// case of initial state being dropped
		long nuInitialStatesToMove = pseudostatesToMove.stream().filter(p -> PseudostateKind.INITIAL_LITERAL == p.getKind()).count();
		// more than one to move or 1 to move, and there are already some in target region => impossible to move
		if (nuInitialStatesToMove > 1 ||
				nuInitialStatesToMove == 1 && pseudostatesInRegion.stream().anyMatch(p -> PseudostateKind.INITIAL_LITERAL == p.getKind())) {
			return false;
		}

		List<Pseudostate> deepHistoryToMove = pseudostatesToMove.stream().filter(p -> PseudostateKind.DEEP_HISTORY_LITERAL == p.getKind()).collect(Collectors.toList());
		if (deepHistoryToMove.size() > 1) {
			return false;
		} else if (deepHistoryToMove.size() == 1) {
			// region should belong to a composite state
			if (targetRegion.getState() == null) {
				return false;
			}
			// there should be non already in region
			if (pseudostatesInRegion.stream().anyMatch(p -> p.getKind() == PseudostateKind.DEEP_HISTORY_LITERAL)) {
				return false;
			}
		}

		if (pseudostatesToMove.stream().anyMatch(p -> p.getKind() == PseudostateKind.ENTRY_POINT_LITERAL || p.getKind() == PseudostateKind.EXIT_POINT_LITERAL)) {
			return false;
		}

		return true;
	}
}
