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

package org.eclipse.papyrusrt.umlrt.core.commands;

import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.uml2.uml.Element;


/**
 * Common protocol for element exclusion (and re-inheritance) requests.
 */
public interface IExcludeElementRequest extends IEditCommandRequest {

	/**
	 * Queries the element to exclude or reinherit.
	 * 
	 * @return the element to exclude or reinherit
	 */
	Element getElementToExclude();

	/**
	 * Sets the element to exclude or re-inherit. This is intended primarily
	 * for re-use of a request to exclude/re-inherit multiple elements.
	 * 
	 * @param element
	 *            the element to exclude or re-inherit
	 * 
	 * @throws NullPointerException
	 *             if either the {@code element} or its {@link Element#getOwner() owner} is {@code null},
	 *             because it doesn't make sense to exclude an unowned element because it cannot in that
	 *             case be inherited (besides that only packages can be unowned)
	 */
	void setElementToExclude(Element element);

	/**
	 * Queries whether the {@linkplain #getElementToExclude() element} is to be excluded.
	 * 
	 * @return {@code true} if the elemet is to be excluded or
	 *         {@code false} if it is to be reinherited
	 */
	boolean isExclude();

}
