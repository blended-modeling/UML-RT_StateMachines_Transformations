/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTModelImpl;

/**
 * A façade for UML {@link Resource}s that store a root UML-RT model package,
 * providing a view of the element more in keeping with the UML-RT semantics.
 */
public interface UMLRTModel {

	/**
	 * Obtains the canonical instance of the model façade for a resource.
	 * 
	 * @param resource
	 *            an UML resource containing an UML-RT model
	 * 
	 * @return its model façade, or {@code null} if the resource is not a valid UML-RT resource
	 */
	public static UMLRTModel getInstance(Resource resource) {
		return UMLRTModelImpl.getInstance(resource);
	}

	/**
	 * Obtains the UML representation of the model.
	 *
	 * @return the UML representation
	 */
	Resource toUML();

	/**
	 * Obtains the URI of the resource that I represent.
	 *
	 * @return my URI
	 */
	URI getURI();

	/**
	 * Obtains the root package of the model (or sub-model, as the case may be).
	 *
	 * @return the root package
	 */
	UMLRTPackage getRoot();


} // UMLRTModel
