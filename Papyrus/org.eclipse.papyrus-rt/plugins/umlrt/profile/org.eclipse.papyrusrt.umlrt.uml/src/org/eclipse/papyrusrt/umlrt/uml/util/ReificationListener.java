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

package org.eclipse.papyrusrt.umlrt.uml.util;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;

/**
 * Call-back that signals reification (or un-reification) of an element.
 */
public interface ReificationListener {
	/**
	 * Notifies the receiver that an {@code element} has been {@code reified}
	 * or unreified (which usually only happens in the case of undo).
	 * This notification is sent immediately upon the change in the {@code element}'s
	 * reiication state; it is not delayed in any way as perhaps by a transaction.
	 * 
	 * @param element
	 *            an UML-RT façade element
	 * @param reified
	 *            {@code true} if it changed from virtual to real state;
	 *            {@code false} if it changed from real to virtual state
	 */
	void reificationStateChanged(UMLRTNamedElement element, boolean reified);
}
