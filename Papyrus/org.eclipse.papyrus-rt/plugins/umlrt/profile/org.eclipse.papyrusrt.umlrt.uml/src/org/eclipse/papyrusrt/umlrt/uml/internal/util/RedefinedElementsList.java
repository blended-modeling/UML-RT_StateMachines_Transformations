/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.uml.internal.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;

/**
 * A specialized {@link EList} protocol for lists that implement the
 * primary redefinition feature of a model element.
 */
public interface RedefinedElementsList<E> extends EList<E> {

	/**
	 * Sets the single redefined element, as per UML-RT semantics.
	 * 
	 * @param redefinedElement
	 *            my redefined element, or {@code null} if I should have none
	 */
	void setRedefinedElement(E redefinedElement);

	/**
	 * Obtains the UML-RT element that is my owner.
	 * 
	 * @return my redefinable UML-RT owner
	 */
	InternalUMLRTElement getRedefinableOwner();
}
