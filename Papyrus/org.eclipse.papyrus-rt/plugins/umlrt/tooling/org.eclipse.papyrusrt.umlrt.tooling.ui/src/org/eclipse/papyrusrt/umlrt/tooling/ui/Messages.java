/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 476984
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrusrt.umlrt.tooling.ui.messages"; //$NON-NLS-1$
	public static String PortAndMsgReference_CreatePortTitle;
	public static String CreateProtocolMsgDialog_CreateProtocolMsgTitle;
	public static String EventSelectionDialog_BaseProtocolNotFound_Message;
	public static String EventSelectionDialog_BaseProtocolNotFound_Title;
	public static String MessageSetOwnedProtocolMessageValueFactory_CreateDialogTitle;
	public static String MessageSetOwnedProtocolMessageValueFactory_CreateOutProtocolMessageDialogTitle;
	public static String MessageSetOwnedProtocolMessageValueFactory_CreateInProtocolMessageDialogTitle;
	public static String MessageSetOwnedProtocolMessageValueFactory_CreateInOutProtocolMessageDialogTitle;
	public static String MessageSetOwnedProtocolMessageValueFactory_EditDialogTitle;
	public static String MessageSetOwnedProtocolMessageValueFactory_EditOutProtocolMessageDialogTitle;
	public static String MessageSetOwnedProtocolMessageValueFactory_EditInOutProtocolMessageDialogTitle;
	public static String MessageSetOwnedProtocolMessageValueFactory_EditInProtocolMessageDialogTitle;
	public static String NoTypeForTypedElement_Label;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
