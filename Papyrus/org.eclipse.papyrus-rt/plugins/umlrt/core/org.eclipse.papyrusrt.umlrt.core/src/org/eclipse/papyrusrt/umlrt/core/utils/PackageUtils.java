/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Utility class for Packages in UML-RT models
 */
public class PackageUtils {

	/**
	 * Not instantiable by clients.
	 */
	private PackageUtils() {
		super();
	}

	/**
	 * Returns the nearest package containing an object, if any.
	 * 
	 * @param eObject
	 *            an object in the model
	 * @return its nearest package, or {@code null} if the object either is not in
	 *         or is not itself a package
	 */
	public static Package getNearestPackage(EObject eObject) {
		Element element = (Element) EMFCoreUtil.getContainer(eObject, UMLPackage.Literals.ELEMENT);
		return (element == null) ? null : element.getNearestPackage();
	}
}
