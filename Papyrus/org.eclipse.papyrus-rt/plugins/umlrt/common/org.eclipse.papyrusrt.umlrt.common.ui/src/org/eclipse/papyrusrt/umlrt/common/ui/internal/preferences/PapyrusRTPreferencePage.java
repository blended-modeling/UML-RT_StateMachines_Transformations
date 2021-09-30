/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.common.ui.internal.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The top-level preference page for Papyrus-RT.
 */
public class PapyrusRTPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 * Initializes me.
	 */
	public PapyrusRTPreferencePage() {
		super(Messages.PapyrusRTPreferencePage_title, FLAT);
	}

	@Override
	protected void createFieldEditors() {
		addField(new ClearDialogTogglesField(getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
		// Pass
	}

}
