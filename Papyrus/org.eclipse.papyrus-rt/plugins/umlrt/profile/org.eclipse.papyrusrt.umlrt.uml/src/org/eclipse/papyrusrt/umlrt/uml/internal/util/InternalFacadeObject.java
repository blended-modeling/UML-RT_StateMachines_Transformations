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

package org.eclipse.papyrusrt.umlrt.uml.internal.util;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;

/**
 * Internal protocol for access to the Façade-specific reflection capabilities
 * of façade objects.
 */
public interface InternalFacadeObject extends FacadeObject, InternalEObject {
	/**
	 * Obtains the value of a {@link reference} with the option of
	 * taking also excluded elements.
	 * 
	 * @param reference
	 *            the reference to access
	 * @param withExclusions
	 *            whether to take excluded elements, also
	 * 
	 * @return the reference value or values (which would be a list),
	 *         with or without exclusions as specified
	 */
	default Object facadeGet(EReference reference, boolean withExclusions) {
		return withExclusions ? facadeGetAll(reference) : eGet(reference);
	}

	/**
	 * Obtains the value of a {@link reference} with also all of the
	 * excluded elements.
	 * 
	 * @param reference
	 *            the reference to access
	 * 
	 * @return the reference value or values (which would be a list),
	 *         with exclusions
	 */
	Object facadeGetAll(EReference reference);
}
