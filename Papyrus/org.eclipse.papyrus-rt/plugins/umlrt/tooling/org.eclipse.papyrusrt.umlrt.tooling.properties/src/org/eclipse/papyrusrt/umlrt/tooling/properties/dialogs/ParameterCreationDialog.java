/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and Others, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bug 476984
 *  
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.properties.dialogs;

import java.util.Collections;

import org.eclipse.papyrus.infra.widgets.editors.ReferenceDialog;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrusrt.umlrt.tooling.properties.widget.ParameterCreationWidget;
import org.eclipse.papyrusrt.umlrt.tooling.ui.providers.ParameterTypeContentProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * Dialog to create a new Parameter in the RealTime Context
 * 
 * Called when clicking on the "Add" button in the parameter Tables.
 * 
 * @author Céline JANSSENS
 * @see ParameterCreationWidget
 *
 */
public class ParameterCreationDialog extends ReferenceDialog {

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 *            Parent Composite owning this Dialog
	 * @param style
	 *            The Style of the Composite
	 */
	public ParameterCreationDialog(final Composite parent, final int style) {
		super(parent, style);

	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.ReferenceDialog#setContentProvider(org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider)
	 */
	@Override
	public void setContentProvider(final IStaticContentProvider provider) {
		EncapsulatedContentProvider providerForDialog = new ParameterTypeContentProvider(provider);
		dialog.setContentProvider(providerForDialog);
		if (null != getValue()) {
			setInitialSelection(Collections.singletonList(getValue()));
		}

		this.contentProvider = provider;


	}


}
