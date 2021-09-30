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

import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageSet;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Utility class on {@link RTMessageSet}
 */
public class MessageSetUtils {

	/**
	 * Retrieves the direction for a given element
	 * 
	 * @param object
	 *            the object for which direction is looked after
	 * @return the di
	 */
	public static RTMessageKind getMessageKind(Object object) {
		if (object instanceof RTMessageSet) {
			return ((RTMessageSet) object).getRtMsgKind();
		}

		if (object instanceof Element) {
			RTMessageSet set = UMLUtil.getStereotypeApplication((Element) object, RTMessageSet.class);
			if (set != null) {
				return set.getRtMsgKind();
			}
		}

		return null;
	}

	/**
	 * @param newName
	 * @return
	 */
	public static String computeInterfaceInName(String protocolName) {
		return protocolName;
	}

	public static String computeInterfaceOutName(String protocolName) {
		return protocolName + "~";
	}

	public static String computeInterfaceInOutName(String protocolName) {
		return protocolName + "IO";
	}

	/**
	 * @param owner
	 * @return
	 */
	public static boolean isRTMessageSet(Element owner) {
		return ElementTypeUtils.matches(owner, IUMLRTElementTypes.RT_MESSAGE_SET_ID);
	}

	public static Package getProtocolContainer(Interface messageSet) {
		return messageSet.getNearestPackage();
	}

	public static Collaboration getProtocol(Interface messageSet) {
		Package nearestPackage = getProtocolContainer(messageSet);
		if (nearestPackage != null && ProtocolContainerUtils.isProtocolContainer(nearestPackage)) {
			return ProtocolContainerUtils.getProtocol(nearestPackage);
		}
		return null;
		
	}

}
