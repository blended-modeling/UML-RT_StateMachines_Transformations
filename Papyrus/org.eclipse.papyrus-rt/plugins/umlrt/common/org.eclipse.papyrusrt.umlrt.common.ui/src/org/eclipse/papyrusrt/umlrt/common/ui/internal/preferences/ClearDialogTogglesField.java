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

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.papyrusrt.umlrt.common.ui.preferences.DialogPreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

/**
 * The field-editor for the "clear dialog toggle states" button.
 */
class ClearDialogTogglesField extends FieldEditor {

	private Group group;

	/**
	 * Initializes me with my {@code parent} composite.
	 * 
	 * @param parent
	 *            my containing composite
	 */
	public ClearDialogTogglesField(Composite parent) {
		super();

		createControl(parent);
	}

	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		group = new Group(parent, SWT.NONE);
		group.setText(Messages.ClearDialogTogglesField_title);
		group.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, numColumns, 1));

		group.setLayout(new GridLayout(2, false));
		Label label = new Label(group, SWT.WRAP);
		label.setText(Messages.ClearDialogTogglesField_label);
		label.setFont(parent.getFont());
		GridData labelGD = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		labelGD.widthHint = convertHorizontalDLUsToPixels(parent, 40);
		label.setLayoutData(labelGD);

		Button button = new Button(group, SWT.PUSH);
		button.setText(Messages.ClearDialogTogglesField_button);
		button.setLayoutData(new GridData(SWT.FILL, SWT.END, false, false));
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				DialogPreferences.clearDialogToggles();
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				DialogPreferences.clearDialogToggles();
			}
		});
	}

	@Override
	public int getNumberOfControls() {
		return 1; // Just the group
	}

	@Override
	protected void adjustForNumColumns(int numColumns) {
		if (group != null) {
			((GridData) group.getLayoutData()).horizontalSpan = numColumns;
		}
	}

	@Override
	protected void doLoad() {
		// Pass
	}

	@Override
	protected void doLoadDefault() {
		// Pass
	}

	@Override
	protected void doStore() {
		// Pass
	}

}
