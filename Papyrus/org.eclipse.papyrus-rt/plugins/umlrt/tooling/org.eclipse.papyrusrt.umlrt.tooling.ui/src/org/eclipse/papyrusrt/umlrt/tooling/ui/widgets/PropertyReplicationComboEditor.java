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

package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * @author as247872
 *
 */
public class PropertyReplicationComboEditor extends AbstractPropertyEditor {

	protected PropertyReplicationComboDialog editor;

	public PropertyReplicationComboEditor(Composite parent, int style) {
		editor = createReplicationDialog(parent, style);
		setEditor(editor);
	}

	protected PropertyReplicationComboDialog createReplicationDialog(Composite parent, int style) {
		return new PropertyReplicationComboDialog(parent, style);
	}

	@Override
	public void doBinding() {
		IStaticContentProvider contentProvider = input.getContentProvider(propertyPath);
		editor.setContentProvider(contentProvider);
		super.doBinding();
	}

}