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
package org.eclipse.papyrusrt.umlrt.core.utils;

import java.util.List;

import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;

/**
 * Utility class to manage InterfaceRealization
 */
public class InterfaceRealizationUtils {

	/**
	 * Returns <code>true</code> if the interface realization comes from a Protocol
	 * 
	 * @param interfaceRealization
	 *            the interface realization to check
	 * @return
	 */
	public static boolean isInterfaceRealizationFromProtocol(InterfaceRealization interfaceRealization) {
		List<NamedElement> clients = interfaceRealization.getClients();
		if (clients.size() != 1) {
			return false;
		}
		NamedElement client = clients.get(0);
		return ProtocolUtils.isProtocol(client);
	}

	public static Interface getMessageSet(InterfaceRealization interfaceRealization) {
		return interfaceRealization.getContract();
	}


}
