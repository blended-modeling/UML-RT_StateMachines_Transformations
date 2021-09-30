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

package org.eclipse.papyrusrt.umlrt.common.ui.preferences.tests;

import static org.eclipse.jface.dialogs.IDialogConstants.NO_ID;
import static org.eclipse.jface.dialogs.IDialogConstants.OK_ID;
import static org.eclipse.jface.dialogs.IDialogConstants.YES_ID;
import static org.eclipse.jface.dialogs.MessageDialogWithToggle.ALWAYS;
import static org.eclipse.jface.dialogs.MessageDialogWithToggle.NEVER;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrusrt.umlrt.common.ui.preferences.DialogPreferences;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Shell;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Test cases for the {@link DialogPreferences} API.
 */
public class DialogPreferencesTest {

	@Rule
	public final TestName name = new TestName();

	/**
	 * Initializes me.
	 */
	public DialogPreferencesTest() {
		super();
	}

	@Test
	public void yesNoCancel() {
		assertDialogInteraction(ALWAYS, YES_ID);
	}

	@Test
	public void yesNo() {
		assertDialogInteraction(NEVER, NO_ID);
	}

	@Test
	public void confirm() throws Exception {
		assertDialogInteraction(ALWAYS, OK_ID);
	}

	@Test
	public void clearDialogToggles() {
		String dialogID = "$$$canary";
		IPreferenceStore store = DialogPreferences.getDialogTogglePreferenceStore();
		assertThat(store, notNullValue());

		store.putValue(dialogID, MessageDialogWithToggle.ALWAYS);

		DialogPreferences.clearDialogToggles();

		assertThat(store.getString(dialogID), either(nullValue()).or(is("")));
	}

	//
	// Test framework
	//

	MethodHandle lookupPromptMethod(String name) throws Exception {
		return MethodHandles.publicLookup().findStatic(DialogPreferences.class, name,
				MethodType.methodType(int.class, Shell.class, String.class, String.class, String.class));
	}

	void assertDialogInteraction(String preference, int expectedResult) {
		String dialogID = String.format("$$$my.%s.dialog", name.getMethodName());
		IPreferenceStore store = DialogPreferences.getDialogTogglePreferenceStore();
		assertThat(store, notNullValue());

		// Train the dialog
		store.putValue(dialogID, preference);

		try {
			// Use a method handle for nice stack traces in case of exceptions
			MethodHandle method = lookupPromptMethod(name.getMethodName());

			// We should not be showing a dialog in this case
			int result = (Integer) method.invoke(null, "Ignored", "Doesn't matter", dialogID);
			assertThat(result, is(expectedResult));
		} catch (SWTException e) {
			// We are not on the UI thread, so attempt to show the dialog would fail
			fail("Attempted to show the message dialog");
		} catch (Error e) {
			throw e;
		} catch (Throwable t) {
			t.printStackTrace();
			fail("Method invocation failed: " + t.getMessage());
		}
	}

}
