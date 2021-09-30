/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Utility class for retrieving shell and buttons of a JFace Dialog
 *
 */
public class DialogUtils {

	/**
	 * Find shell of dialog
	 * 
	 * @param control
	 *            a control within the dialog
	 * @return the shell of the dialog, if it exists, null otherwise
	 */
	public static Shell getDialogShell(Control control) {
		// rather ugly way to find current okButton within dialog
		Control shell = control;
		while (shell != null) {
			shell = shell.getParent();
			if (shell instanceof Shell) {
				// if shell corresponds to global shell, we are not in a dialog
				if (shell != Display.getCurrent().getActiveShell()) {
					return (Shell) shell;
				}
			}
		}
		return null;
	}

	/**
	 * Find ok button within a control
	 * 
	 * @param control
	 *            a control
	 * @return ok Button, if it exists, null otherwise
	 */
	public static Button findOkButton(Control control) {
		if (control instanceof Button) {
			Button button = (Button) control;
			if (button.getText().equals(IDialogConstants.OK_LABEL)) {
				return button;
			}
		} else if (control instanceof Composite) {
			for (Control child : ((Composite) control).getChildren()) {
				Button button = findOkButton(child);
				if (button != null) {
					return button;
				}
			}
		}
		return null;
	}

}
