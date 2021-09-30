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

package org.eclipse.papyrusrt.xtumlrt.xtext.ui.internal.preferences

import org.eclipse.jface.preference.FieldEditorPreferencePage
import org.eclipse.xtext.ui.editor.preferences.fields.LabelFieldEditor
import org.eclipse.ui.IWorkbenchPreferencePage
import org.eclipse.ui.IWorkbench

/**
 * Top-level preference page (category) for the UML-RT Xtext editor
 * preferences.
 */
class UMLRTTextEditorPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	override protected createFieldEditors() {
		new LabelFieldEditor("Expand the tree to edit preferences for a specific feature.", fieldEditorParent);
	}
	
	override init(IWorkbench workbench) {
		// Pass
	}

}
