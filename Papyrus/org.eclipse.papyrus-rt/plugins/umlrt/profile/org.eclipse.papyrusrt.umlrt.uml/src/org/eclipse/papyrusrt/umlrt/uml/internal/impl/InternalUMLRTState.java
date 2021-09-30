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

import java.util.stream.Stream;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Internal protocol for composite states.
 */
public interface InternalUMLRTState extends InternalUMLRTRedefinitionContext<InternalUMLRTState>, State {

	@Override
	default Stream<? extends InternalUMLRTState> rtDescendants() {
		return CacheAdapter.getInstance().getNonNavigableInverseReferences(this).stream()
				.filter(setting -> setting.getEStructuralFeature() == UMLPackage.Literals.STATE__REDEFINED_STATE)
				.map(EStructuralFeature.Setting::getEObject)
				.filter(InternalUMLRTState.class::isInstance)
				.filter(g -> g.eClass() == eClass())
				.map(InternalUMLRTState.class::cast);
	}

	/**
	 * Obtains the entry behavior, with or without proxy resolution, according to UML semantics.
	 * 
	 * @param resolve
	 *            whether to resolve proxies
	 * 
	 * @return the UML-ish entry behavior (not inherited)
	 */
	Behavior umlGetEntry(boolean resolve);

	/**
	 * Basic-sets the entry behavior, according to UML semantics.
	 * 
	 * @param newEntry
	 *            the new entry behavior
	 * @param msgs
	 *            an incoming notification chain to append, or {@code null} if none, yet
	 * 
	 * @return the outgoing notifications
	 */
	NotificationChain umlBasicSetEntry(Behavior newEntry, NotificationChain msgs);

	/**
	 * Obtains the exit behavior, with or without proxy resolution, according to UML semantics.
	 * 
	 * @param resolve
	 *            whether to resolve proxies
	 * 
	 * @return the UML-ish exit behavior (not inherited)
	 */
	Behavior umlGetExit(boolean resolve);

	/**
	 * Basic-sets the exit behavior, according to UML semantics.
	 * 
	 * @param newExit
	 *            the new exit behavior
	 * @param msgs
	 *            an incoming notification chain to append, or {@code null} if none, yet
	 * 
	 * @return the outgoing notifications
	 */
	NotificationChain umlBasicSetExit(Behavior newExit, NotificationChain msgs);
}
