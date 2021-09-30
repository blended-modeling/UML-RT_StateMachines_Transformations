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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;
import org.eclipse.uml2.uml.resource.UMLResource;

/**
 * Custom UML resource factory that ensures usage of {@link EFactory} instances
 * registered locally in the resource set when instantiating elements from the
 * feature type in case that that XMI Type is implied.
 */
public class UMLRTUMLResourceFactoryImpl extends UMLResourceFactoryImpl {

	public static final UMLResource.Factory INSTANCE = new UMLRTUMLResourceFactoryImpl();

	protected UMLRTUMLResourceFactoryImpl() {
		super();
	}

	@Override
	public Resource createResourceGen(URI uri) {
		UMLResource result;

		// FIXME: Require a content-type based on the UML-RT profile
		String uriString = uri.toString();
		if (uriString.startsWith(UMLResource.LIBRARIES_PATHMAP)
				|| uriString.startsWith(UMLResource.PROFILES_PATHMAP)
				|| uriString.startsWith(UMLResource.METAMODELS_PATHMAP)
				|| uriString.startsWith("pathmap://UML_RT_PROFILE/") //$NON-NLS-1$
				|| uriString.startsWith("platform:/plugin/org.eclipse.uml2")) { //$NON-NLS-1$
			result = (UMLResource) super.createResourceGen(uri);
		} else {
			result = new UMLRTUMLResourceImpl(uri);
			result.setEncoding(UMLResource.DEFAULT_ENCODING);
		}

		return result;
	}

}
