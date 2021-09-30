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
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Usage;

/**
 * Utility class for {@link Usage}
 */
public class UsageUtils {

	public static Interface getMessageSet(Usage usage) {
		List<NamedElement> suppliers = usage.getSuppliers();
		if (suppliers.size() != 1) {
			return null;
		}
		NamedElement supplier = suppliers.get(0);
		if (MessageSetUtils.isRTMessageSet(supplier)) {
			return (Interface) supplier;
		}
		return null;
	}

	/**
	 * Returns <code>true</code> if the interface realization comes from a Protocol
	 * 
	 * @param usage
	 *            the interface realization to check
	 * @return
	 */
	public static boolean isUsageFromProtocol(Usage usage) {
		List<NamedElement> clients = usage.getClients();
		if (clients.size() != 1) {
			return false;
		}
		NamedElement client = clients.get(0);
		return ProtocolUtils.isProtocol(client);
	}

}
