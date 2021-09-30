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

package org.eclipse.papyrusrt.umlrt.common.ui.preferences;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages for the package.
 */
class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrusrt.umlrt.common.ui.preferences.messages"; //$NON-NLS-1$

	public static String DialogPreferences_failedMsg;
	public static String DialogPreferences_failedTitle;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		super();
	}
}
