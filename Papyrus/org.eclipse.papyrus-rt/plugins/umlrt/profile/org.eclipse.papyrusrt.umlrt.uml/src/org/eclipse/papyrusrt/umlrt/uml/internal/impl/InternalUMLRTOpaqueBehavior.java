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

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;

/**
 * Internal protocol for opaque behaviors serving as {@link Transition} effects
 * and {@link State} entry/exit behaviors.
 */
public interface InternalUMLRTOpaqueBehavior extends InternalUMLRTElement, OpaqueBehavior {
	/**
	 * Notifies me that my owning transition's redefinition
	 * status has changed (I am its {@linkplain Transition#getEffect() effect}.
	 * 
	 * @param transition
	 *            my owner's new redefined element, or {@code null}
	 *            if it is now not a redefinition
	 */
	void handleRedefinedTransition(Transition transition);

	/**
	 * Notifies me that my owning state's redefinition
	 * status has changed (I am its {@linkplain State#getEntry() entry} or
	 * {@linkplain State#getExit() exit behavior}).
	 * 
	 * @param state
	 *            my owner's new redefined element, or {@code null}
	 *            if it is now not a redefinition
	 */
	void handleRedefinedState(State state);
}
