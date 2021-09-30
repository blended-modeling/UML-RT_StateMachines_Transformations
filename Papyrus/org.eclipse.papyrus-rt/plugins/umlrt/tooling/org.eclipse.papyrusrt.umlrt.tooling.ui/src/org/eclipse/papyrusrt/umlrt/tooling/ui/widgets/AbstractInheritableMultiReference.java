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

package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import org.eclipse.papyrus.infra.properties.ui.widgets.AbstractMultiReference;
import org.eclipse.papyrusrt.umlrt.tooling.ui.editors.AbstractInheritableMultipleReferenceEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * Customized multi-reference property for references that support inheritance.
 */
public abstract class AbstractInheritableMultiReference<T extends AbstractInheritableMultipleReferenceEditor> extends AbstractMultiReference<T> {

	public AbstractInheritableMultiReference(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected T createMultipleReferenceEditor(Composite parent, int style) {
		T result = doCreateMultipleReferenceEditor(parent, style);

		if (getProperty() != null) {
			result.setDialogSettingsKey(getProperty());
		}

		return result;
	}

	protected abstract T doCreateMultipleReferenceEditor(Composite parent, int style);

	@Override
	public void setProperty(String path) {
		super.setProperty(path);

		if (editor != null) {
			editor.setDialogSettingsKey(path);
		}
	}
}
