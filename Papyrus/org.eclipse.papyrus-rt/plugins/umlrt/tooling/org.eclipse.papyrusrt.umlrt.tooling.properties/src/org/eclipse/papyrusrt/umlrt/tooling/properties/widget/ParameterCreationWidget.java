/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and Others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.properties.widget;


import org.eclipse.papyrus.infra.properties.ui.widgets.ReferenceDialog;
import org.eclipse.papyrusrt.umlrt.tooling.properties.dialogs.ParameterCreationDialog;
import org.eclipse.swt.widgets.Composite;


/**
 * Parameter Creation Widget to create new Parameter in a Protocol Message in Papyrus RT
 * Reduce the number of field to set. only Type and Name are required.
 * 
 * @see ParameterCreationDialog
 * 
 * @author Céline JANSSENS
 *
 */
public class ParameterCreationWidget extends ReferenceDialog {

	ParameterCreationDialog editor;

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 *            The Parent Composite
	 * @param style
	 *            The Style of the Widget
	 */
	public ParameterCreationWidget(final Composite parent, final int style) {
		super(parent, style);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.views.properties.widgets.ReferenceDialog#createReferenceDialog(org.eclipse.swt.widgets.Composite, int)
	 *
	 */
	@Override
	protected org.eclipse.papyrus.infra.widgets.editors.ReferenceDialog createReferenceDialog(final Composite parent, final int style) {
		editor = new ParameterCreationDialog(parent, style);
		return editor;
	}


}
