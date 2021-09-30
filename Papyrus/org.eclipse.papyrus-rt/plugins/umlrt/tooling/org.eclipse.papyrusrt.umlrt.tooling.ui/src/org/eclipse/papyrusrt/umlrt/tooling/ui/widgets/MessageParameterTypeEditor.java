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

package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import org.eclipse.papyrus.infra.properties.ui.widgets.ReferenceDialog;
import org.eclipse.swt.widgets.Composite;

/**
 * Reference "dialog" property-sheet editor for the type of a message parameter.
 * It uses the same pop-up menu as the parameters table widget for setting the type
 * of a parameter (so, not exactly a dialog).
 * 
 * @see MessageParameterTypeDialog
 */
public class MessageParameterTypeEditor extends ReferenceDialog {

	public MessageParameterTypeEditor(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Creates the reference "dialog" widget.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 * @return the reference "dialog"
	 */
	@Override
	protected org.eclipse.papyrus.infra.widgets.editors.ReferenceDialog createReferenceDialog(Composite parent, int style) {
		return new MessageParameterTypeDialog(parent, style);
	}
}
