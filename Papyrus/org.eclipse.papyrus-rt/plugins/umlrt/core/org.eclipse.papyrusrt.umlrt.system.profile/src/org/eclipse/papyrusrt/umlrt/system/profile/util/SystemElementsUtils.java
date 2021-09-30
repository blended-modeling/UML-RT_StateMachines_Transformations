/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.system.profile.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Protocol;
import org.eclipse.papyrusrt.umlrt.system.profile.systemelements.BaseProtocol;
import org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemClass;
import org.eclipse.papyrusrt.umlrt.system.profile.systemelements.SystemProtocol;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.util.UMLUtil;


/**
 * Utilities to returns SystemElements defined in a specific RTS library
 * based on the default language framework
 */
public class SystemElementsUtils {

	/**
	 * return all System protocols defined in the library attached to the default language
	 * 
	 * @param resource
	 *            the library resource
	 * 
	 */
	public static Set<Collaboration> getSystemProtocols(Resource resource) {

		if (resource != null && resource.getContents().size() > 0) {
			Set<Collaboration> result = new HashSet<>();
			TreeIterator<EObject> allContents = resource.getAllContents();
			while (allContents.hasNext()) {
				EObject eObject = allContents.next();
				if (eObject instanceof Collaboration) {
					Collaboration collaboration = (Collaboration) eObject;
					if (isProtocol(collaboration) && isSystemProtocol(collaboration)) {
						result.add(collaboration);
					}
				}
			}
			return result;
		}
		return Collections.emptySet();
	}

	/**
	 * return the base protocol defined in the library attached to the default language
	 * return the first base protocol found in the library, assume that there is only one Base Protocol
	 * 
	 * @param resource:
	 *            the library resource
	 * 
	 */
	public static Collaboration getBaseProtocol(Resource resource) {
		if (resource != null && resource.getContents().size() > 0) {
			TreeIterator<EObject> allContents = resource.getAllContents();
			while (allContents.hasNext()) {
				EObject eObject = allContents.next();
				if (eObject instanceof Collaboration) {
					Collaboration collaboration = (Collaboration) eObject;
					if (isProtocol(collaboration) && isBaseProtocol((Collaboration) eObject)) {
						return (Collaboration) eObject;
					}
				}
			}

		}
		return null;
	}

	/**
	 * all System protocols defined in the library attached to the default language
	 * 
	 * @param resource:
	 *            the library resource
	 */
	public static Set<Class> getSystemClasss(Resource resource) {

		if (resource != null && resource.getContents().size() > 0) {
			Set<Class> result = new HashSet<>();
			TreeIterator<EObject> allContents = resource.getAllContents();
			while (allContents.hasNext()) {
				EObject eObject = allContents.next();
				if (eObject instanceof Class) {
					if (isSystemClass((Class) eObject)) {
						result.add((Class) eObject);
					}
				}
			}
			return result;
		}
		return Collections.emptySet();
	}

	/**
	 * @param element
	 * @return <code>true</code> if the model element is a Protocol (Collaboration stereotyped by Protocol stereotype)
	 */
	public static boolean isProtocol(Collaboration element) {
		return UMLUtil.getStereotypeApplication(element, Protocol.class) != null;
	}


	/**
	 * @param element
	 * @return <code>true</code> if the model element is a SystemProtocol (Collaboration stereotyped by SystemProtocol stereotype)
	 */
	public static boolean isSystemProtocol(Collaboration element) {

		return UMLUtil.getStereotypeApplication(element, SystemProtocol.class) != null;
	}


	/**
	 * @param element
	 * @return <code>true</code> if the model element is a BaseProtocol (Collaboration stereotyped by BaseProtocol stereotype)
	 */
	public static boolean isBaseProtocol(Collaboration element) {
		return UMLUtil.getStereotypeApplication(element, BaseProtocol.class) != null;
	}

	/**
	 * @param element
	 * @return <code>true</code> if the model element is a SystemClass (Class stereotyped by SystemClass stereotype)
	 */
	public static boolean isSystemClass(Class element) {
		return UMLUtil.getStereotypeApplication(element, SystemClass.class) != null;
	}
}
