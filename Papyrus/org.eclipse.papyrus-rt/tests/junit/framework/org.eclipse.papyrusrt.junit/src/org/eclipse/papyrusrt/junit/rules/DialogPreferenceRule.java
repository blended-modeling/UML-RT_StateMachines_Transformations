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

package org.eclipse.papyrusrt.junit.rules;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * A JUnit rule for suppression of preference-managed confirmation dialogs
 * by temporarily setting the preference to always return some answer.
 */
public class DialogPreferenceRule extends TestWatcher {

	private static final String DIALOG_TOGGLE_PATH = "org.eclipse.papyrusrt.umlrt.common.ui/dialogToggle"; //$NON-NLS-1$

	private final String dialogID;
	private final String preferenceValue;

	private String valueToRestore;

	/**
	 * Initializes me with the dialog to override and the preference value
	 * to configure for it.
	 *
	 * @param dialogID
	 *            the dialog to suppress
	 * @param preferenceValue
	 *            its preference value (usually one of the
	 *            {@link MessageDialogWithToggle#ALWAYS MessageDialogWithToggle} constants)
	 */
	public DialogPreferenceRule(String dialogID, String preferenceValue) {
		super();

		this.dialogID = dialogID;
		this.preferenceValue = preferenceValue;
	}

	/**
	 * Creates a rule that configures the <em>always</em> answer for the given dialog.
	 * 
	 * @param dialogID
	 *            the dialog to suppress
	 * @return the always rule
	 */
	public static DialogPreferenceRule always(String dialogID) {
		return new DialogPreferenceRule(dialogID, "always"); //$NON-NLS-1$
	}

	/**
	 * Creates a rule that configures the <em>never</em> answer for the given dialog.
	 * 
	 * @param dialogID
	 *            the dialog to suppress
	 * @return the never rule
	 */
	public static DialogPreferenceRule never(String dialogID) {
		return new DialogPreferenceRule(dialogID, "never"); //$NON-NLS-1$
	}

	@Override
	protected void starting(Description description) {
		IPreferenceStore store = getPreferenceStore();

		valueToRestore = store.getString(dialogID);
		store.setValue(dialogID, preferenceValue);
	}

	@Override
	protected void finished(Description description) {
		IPreferenceStore store = getPreferenceStore();

		if (valueToRestore != null) {
			store.setValue(dialogID, valueToRestore);
		} else {
			store.setToDefault(dialogID);
		}
	}

	IPreferenceStore getPreferenceStore() {
		return new ScopedPreferenceStore(
				InstanceScope.INSTANCE, DIALOG_TOGGLE_PATH);
	}
}
