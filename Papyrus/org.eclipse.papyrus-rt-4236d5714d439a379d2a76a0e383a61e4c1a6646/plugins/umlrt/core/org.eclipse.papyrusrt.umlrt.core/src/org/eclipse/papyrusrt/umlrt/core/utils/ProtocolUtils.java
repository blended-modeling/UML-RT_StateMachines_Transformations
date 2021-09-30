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

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;

/**
 * Utility class for UMLRT::Protocols
 */
public class ProtocolUtils {

	/**
	 * Returns the package that corresponds to the protocol container. There should be a check here for the applied stereotype on the package.
	 * 
	 * @param protocol
	 *            the collaboration for which the protocol container is searched
	 * @return the collaboration for which the protocol container is searched or <code>null</code> if none is found
	 */
	public static Package getProtocolContainer(Collaboration protocol) {
		return protocol.getNearestPackage();
	}

	/**
	 * Returns <code>true</code> if the context element is a Protocol (Collaboration stereotyped by "protocol")
	 * 
	 * @param context
	 *            the eobject to test
	 * @return <code>true</code> if the context element is a Protocol (Collaboration stereotyped by "protocol")
	 */
	public static Boolean isProtocol(EObject context) {
		return ElementTypeUtils.matches(context, IUMLRTElementTypes.PROTOCOL_ID);
	}

	/**
	 * @param in
	 * @return
	 */
	public static List<Operation> getRTMessages(Collaboration protocol, RTMessageKind direction, boolean showInherited) {
		Package protocolContainer = getProtocolContainer(protocol);
		if(protocolContainer ==null) {
			Activator.log.error("Impossible to get the root protocol container", null);
			return Collections.emptyList();
		}
		
		return ProtocolContainerUtils.getRTMessages(protocolContainer, direction, showInherited);
	}

	/**
	 * @param editContext
	 * @return
	 */
	public static Interface getMessageSet(Collaboration editContext, RTMessageKind direction) {
		Package protocolContainer = getProtocolContainer(editContext);

		if (protocolContainer != null) {
			return ProtocolContainerUtils.getMessageSet(protocolContainer, direction);
		}
		return null;
	}

	/**
	 * @param editContext
	 * @return
	 */
	public static Interface getMessageSetIn(Collaboration editContext) {
		return getMessageSet(editContext, RTMessageKind.IN);
	}

	/**
	 * @param editContext
	 * @return
	 */
	public static Interface getMessageSetOut(Collaboration editContext) {
		return getMessageSet(editContext, RTMessageKind.OUT);
	}

	/**
	 * @param editContext
	 * @return
	 */
	public static Interface getMessageSetInOut(Collaboration editContext) {
		return getMessageSet(editContext, RTMessageKind.IN_OUT);
	}

}
