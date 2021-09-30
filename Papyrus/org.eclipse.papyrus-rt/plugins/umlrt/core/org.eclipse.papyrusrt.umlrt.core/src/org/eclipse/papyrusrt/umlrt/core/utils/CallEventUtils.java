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

import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Operation;

/**
 * Utility class for UML-RT call events (the ones used together with ProtocolMessages)
 */
public class CallEventUtils {

	/**
	 * Returns <code>true</code> if the specified object is a call event linked to a ProtocolMessage
	 * 
	 * @param callEvent
	 *            the object to test
	 * @return <code>true</code> if the specified object is a call event linked to a ProtocolMessage
	 */
	public static boolean isProtocolMessageCallEvent(CallEvent callEvent) {
		Operation operation = callEvent.getOperation();
		if(RTMessageUtils.isRTMessage(operation)) {
			return true;
		}
		return false;
	}


}
