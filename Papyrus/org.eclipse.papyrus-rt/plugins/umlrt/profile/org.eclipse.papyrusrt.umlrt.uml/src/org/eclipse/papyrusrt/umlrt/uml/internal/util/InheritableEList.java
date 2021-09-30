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

package org.eclipse.papyrusrt.umlrt.uml.internal.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;

/**
 * A specialized EMF list that supports inheritance from
 * a list-valued feature in a redefined element. It maintains
 * corresponding redefinitions of the elements in the inherited
 * list. An inheritable list does not have to inherit anything,
 * in which case it behaves as a normal EMF list storing the
 * mutable values of a multi-valued feature.
 */
public interface InheritableEList<E> extends InternalEList.Unsettable<E> {
	/**
	 * Queries whether I am set, in which case I override
	 * any potentially {@linkplain #inherit(EList) inherited}
	 * values.
	 * 
	 * @return whether I am explicitly set
	 */
	@Override
	boolean isSet();

	/**
	 * Unsets me, in which case I revert to
	 * {@linkplain #inherit(EList) inheriting} the values
	 * of my owner's redefined element, or else am just
	 * cleared out as with any normal EMF list.
	 */
	@Override
	void unset();

	/**
	 * Sets the list from which I inherit values.
	 * 
	 * @param inherited
	 *            the list from which I inherit, or {@code null}
	 *            if I am the value of a feature of a root definition
	 *            (there is nothing from which to inherit)
	 */
	void inherit(EList<E> inherited);

	/**
	 * If I am {@link #isSet() unset}, makes me set and ensures
	 * that my owner is then {@link InternalUMLRTElement#rtReify() reified}.
	 */
	void touch();
}
