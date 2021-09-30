/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * Internal protocol for multiplicity elements.
 */
public interface InternalUMLRTMultiplicityElement extends InternalUMLRTElement, MultiplicityElement {
	/**
	 * Obtains the lower value, with or without proxy resolution, according to UML semantics.
	 * 
	 * @param resolve
	 *            whether to resolve proxies
	 * 
	 * @return the UML-ish lower value (not inherited)
	 */
	ValueSpecification umlGetLowerValue(boolean resolve);

	/**
	 * Basic-sets the lower value, according to UML semantics.
	 * 
	 * @param newLowerValue
	 *            the new lower value
	 * @param msgs
	 *            an incoming notification chain to append, or {@code null} if none, yet
	 * 
	 * @return the outgoing notifications
	 */
	NotificationChain umlBasicSetLowerValue(ValueSpecification newLowerValue, NotificationChain msgs);

	/**
	 * Obtains the upper value, with or without proxy resolution, according to UML semantics.
	 * 
	 * @param resolve
	 *            whether to resolve proxies
	 * 
	 * @return the UML-ish lower value (not inherited)
	 */
	ValueSpecification umlGetUpperValue(boolean resolve);

	/**
	 * Basic-sets the upper value, according to UML semantics.
	 * 
	 * @param newUpperValue
	 *            the new upper value
	 * @param msgs
	 *            an incoming notification chain to append, or {@code null} if none, yet
	 * 
	 * @return the outgoing notifications
	 */
	NotificationChain umlBasicSetUpperValue(ValueSpecification newUpperValue, NotificationChain msgs);
}
