/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.handlers;

import org.eclipse.osgi.util.NLS;

/**
 * Externalized strings for the package.
 */
class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.handlers.messages"; //$NON-NLS-1$

	public static String GoOutsideHandler_0;
	public static String GoOutsideHandler_1;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		super();
	}
}
