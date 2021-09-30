/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrusrt.umlrt.core.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Operation;

/**
 * Utility class on {@link Operation} that are RTMessage
 */
public class RTMessageUtils {

	/**
	 * Retrieves the direction for a given element
	 * 
	 * @param object
	 *            the object for which direction is looked after
	 * @return the di
	 */
	public static RTMessageKind getMessageKind(Object object) {
		if (object instanceof Operation) {
			// get Owner of the operation, and check if this is a messageSET
			Element owner = ((Operation)object).getOwner();
			RTMessageKind kind = MessageSetUtils.getMessageKind(owner);
			return kind;
		}
		return null;
	}

	/**
	 * @param eObject
	 * @return
	 */
	public static boolean isRTMessage(EObject operation) {
		if (operation instanceof Operation) {
			// get Owner of the operation, and check if this is a messageSET
			Element owner = ((Operation) operation).getOwner();
			return MessageSetUtils.isRTMessageSet(owner);
		}
		return false;
	}

	/**
	 * @param eObject
	 * @param in
	 * @return
	 */
	public static boolean isRTMessage(EObject operation, RTMessageKind in) {
		if (operation instanceof Operation) {
			// get Owner of the operation, and check if this is a messageSET
			Element owner = ((Operation) operation).getOwner();
			RTMessageKind kind = MessageSetUtils.getMessageKind(owner);
			if (kind == in) {
				return true;
			}
		}
		return false;
	}

}
