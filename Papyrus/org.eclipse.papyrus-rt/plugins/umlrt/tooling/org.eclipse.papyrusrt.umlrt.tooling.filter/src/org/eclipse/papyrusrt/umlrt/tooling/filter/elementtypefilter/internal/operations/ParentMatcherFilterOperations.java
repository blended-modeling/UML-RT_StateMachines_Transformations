/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.internal.operations;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ParentMatcherFilter;

/**
 * Operations for {@link ParentMatcherFilter}
 */
public class ParentMatcherFilterOperations {

	/**
	 * Returns <code>true</code> if the specified input matches the element type from the filter
	 * 
	 * @param parentMatcherFilter
	 *            the filter to check
	 * @param input
	 *            the object to test
	 * @return <code>true</code> if the specified input matches the element type from the filter
	 */
	public static boolean matches(ParentMatcherFilter parentMatcherFilter, Object input) {
		if (!(input instanceof EObject)) {
			return false;
		}

		EObject parentEObject = (EObject) input;
		String elementTypeId = parentMatcherFilter.getParentType();
		boolean checkSubtypes = parentMatcherFilter.isSubtypesCheck();

		if (elementTypeId == null) {
			return false;
		}

		if (!checkSubtypes) {
			// strict matches. check equality of the element type id directly. should be avoided.
			IElementType type = ElementTypeRegistry.getInstance().getType(elementTypeId);
			if (type != null) {
				IClientContext context;
				try {
					context = TypeContext.getContext(parentEObject);
					return type.equals(ElementTypeRegistry.getInstance().getElementType(parentEObject, context));
				} catch (ServiceException e) {
					org.eclipse.papyrusrt.umlrt.core.Activator.log.error(e);
				}
				return false;
			}
		}
		return ElementTypeUtils.matches(parentEObject, elementTypeId);
	}

}
