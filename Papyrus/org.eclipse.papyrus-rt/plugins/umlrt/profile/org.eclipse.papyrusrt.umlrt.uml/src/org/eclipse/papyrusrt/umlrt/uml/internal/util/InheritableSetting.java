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

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * A specialization of the Ecore feature setting protocol for single-valued
 * features that support inheritance.
 */
public interface InheritableSetting<E> extends EStructuralFeature.Setting {
	/**
	 * Obtains the (possibly inherited) value of the feature.
	 * 
	 * @return the feature value, if {@linkplain EStructuralFeature.Setting#isSet() set},
	 *         otherwise the inherited value
	 * 
	 * @see EStructuralFeature.Setting#isSet()
	 */
	E getInheritable();

	/**
	 * Inherits the given value.
	 * 
	 * @param newInherited
	 *            the inherited value, or {@code null} if my
	 *            {@linkplain EStructuralFeature.Setting#getEObject() owner} is
	 *            a root definition
	 * 
	 * @see EStructuralFeature.Setting#getEObject()
	 */
	void inherit(E newInherited);
}
