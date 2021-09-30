/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 491473
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import org.eclipse.papyrus.infra.properties.ui.widgets.ReferenceDialog;
import org.eclipse.swt.widgets.Composite;

/**
 * Specific Reference editor for RTPort Type
 */
public class CapsulePartTypeEditor extends ReferenceDialog {

	public CapsulePartTypeEditor(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Creates the reference dialog.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 * @return the reference dialog.
	 */
	@Override
	protected org.eclipse.papyrus.infra.widgets.editors.ReferenceDialog createReferenceDialog(Composite parent, int style) {
		return new CapsulePartTypeDialog(parent, style);
	}
}
