/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.ElementType;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.papyrusrt.umlrt.core.Activator;

/**
 * Utility class for {@link ElementType}
 */
public class ElementTypeUtils {

	/**
	 * Checks the given element if it maches the provided Element types, found using the specified identifier
	 * 
	 * @param element
	 *            element to be checked
	 * @param elementTypeId
	 *            identifier of the element type to match
	 * @return <code>true</code> if the given element matches the given element type
	 */
	public static Boolean matches(EObject element, String elementTypeId) {
		IElementType type = ElementTypeRegistry.getInstance().getType(elementTypeId);
		if (!(type instanceof ISpecializationType)) { // check at the same time UMLRT element types are correctly loaded
			Activator.log.error("Impossible to find element type to match: " + elementTypeId, null);
			return false;
		}
		IElementMatcher matcher = ((ISpecializationType) type).getMatcher();
		if (matcher == null) {
			Activator.log.error("no matcher provided for type: " + type, null);
			return false;
		}
		if (matcher.matches(element)) {
			return true;
		}
		return false;
	}

}
