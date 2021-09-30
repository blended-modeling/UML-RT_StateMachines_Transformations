/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Utilities for inspecting and working with states in an UML-RT model.
 */
public final class StateUtils {

	/**
	 * Constructor.
	 */
	private StateUtils() {
		// empty
	}

	/**
	 * Queries whether a {@code state} is stereotyped as an UML-RT state.
	 * 
	 * @param state
	 *            a state
	 * 
	 * @return whether it is an <tt>RTState</tt>
	 */
	public static boolean isRTState(State state) {
		return ElementTypeUtils.matches(state, IUMLRTElementTypes.RT_STATE_ID);
	}

	/**
	 * Obtains a command that ensures that a {@code state} is stereotyped as an
	 * UML-RT state.
	 * 
	 * @param state
	 *            a state
	 * 
	 * @return the command
	 */
	public static ICommand getConfigureAsRTStateCommand(State state) {
		return getConfigureAsCommand(UMLRTElementTypesEnumerator.RT_STATE, state);
	}

	/**
	 * Checks if a given state has internal transitions in his regions.
	 * 
	 * @param state
	 *            the state to check
	 * @return <code>true</code> if the state is composite and has at least one transition in one of his regions
	 */
	public static boolean hasInternalTransitions(State state) {
		if (state == null) {
			return false;
		}
		if (!state.isComposite()) {
			return false;
		}

		return UMLRTExtensionUtil.<Region> getUMLRTContents(state, UMLPackage.Literals.STATE__REGION).stream()
				.flatMap(r -> UMLRTExtensionUtil.<Transition> getUMLRTContents(r, UMLPackage.Literals.REGION__TRANSITION).stream())
				.anyMatch(TransitionUtils::isInternalTransition);

	}

	/**
	 * Checks if the given object is a state and if it is a composite state.
	 * 
	 * @param object
	 *            the object to check
	 * @return <code>true</code> if the specified object is a composite state
	 */
	public static boolean isCompositeState(Object object) {
		return object instanceof State && ((State) object).isComposite();
	}

	/**
	 * Checks if the given object is a state and if it is not a composite state.
	 * 
	 * @param object
	 *            the object to check
	 * @return <code>true</code> if the specified object is a composite state
	 */
	public static boolean isSimpleState(Object object) {
		return object instanceof State && !((State) object).isComposite();
	}
}
