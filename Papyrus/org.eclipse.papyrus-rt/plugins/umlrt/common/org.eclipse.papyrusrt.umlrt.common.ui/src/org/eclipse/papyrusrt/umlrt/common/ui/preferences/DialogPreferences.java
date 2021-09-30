/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.common.ui.preferences;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrusrt.umlrt.common.ui.Activator;
import org.eclipse.papyrusrt.umlrt.common.ui.internal.preferences.ToggleDialogDelegateRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.ui.statushandlers.IStatusAdapterConstants;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Utility for working with dialog preferences, especially of the "do not
 * ask again" variety.
 */
public class DialogPreferences {

	private static final String DIALOG_TOGGLE_NODE = "dialogToggle"; //$NON-NLS-1$

	private static final String DIALOG_TOGGLE_PATH = Activator.PLUGIN_ID + '/' + DIALOG_TOGGLE_NODE;

	private static final IPreferenceStore dialogTogglePreferences = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, DIALOG_TOGGLE_PATH);

	private static ToggleDialogDelegateRegistry delegateRegistry;

	/**
	 * Not instantiable by clients.
	 */
	private DialogPreferences() {
		super();
	}

	/**
	 * Obtains the preference store to use for management of the dialog
	 * "Remember my decision" preferences. Other preferences that are more
	 * specific to a particular dialog should not use this but their own
	 * host bundle's preference store.
	 * 
	 * @return the common dialog toggle preference store. Every key in this store
	 *         is the unique identifier of a dialog
	 */
	public static IPreferenceStore getDialogTogglePreferenceStore() {
		return dialogTogglePreferences;
	}

	/**
	 * Convenience method to open a yes/no question dialog,
	 * with a "Remember my decision" toggle, returning the user's answer.
	 * The dialog is only opened if necessary (as determined by the preferences).
	 *
	 * @param parent
	 *            the parent shell of the dialog, or {@code null} if none
	 * @param title
	 *            the dialog's title, or {@code null} if none
	 * @param message
	 *            the message
	 * @param dialogID
	 *            the unique identifier of the dialog for persistence of the
	 *            toggle preference
	 * 
	 * @return the dialog result code, with or without having shown the dialog,
	 *         either {@link IDialogConstants#YES_ID} or {@link IDialogConstants#NO_ID}
	 */
	public static int yesNo(Shell parent, String title,
			String message, String dialogID) {

		return prompt(MessageDialog.QUESTION, parent, title, message, dialogID, SWT.SHEET);
	}

	/**
	 * Convenience method to open a yes/no/cancel question dialog,
	 * with a "Remember my decision" toggle, returning the user's answer.
	 * The dialog is only opened if necessary (as determined by the preferences).
	 *
	 * @param parent
	 *            the parent shell of the dialog, or {@code null} if none
	 * @param title
	 *            the dialog's title, or {@code null} if none
	 * @param message
	 *            the message
	 * @param dialogID
	 *            the unique identifier of the dialog for persistence of the
	 *            toggle preference
	 * 
	 * @return the dialog result code, with or without having shown the dialog,
	 *         one of {@link IDialogConstants#YES_ID}, {@link IDialogConstants#NO_ID,
	 *         or {@link IDialogConstants#CANCEL_ID}
	 */
	public static int yesNoCancel(Shell parent, String title,
			String message, String dialogID) {

		return prompt(MessageDialog.QUESTION_WITH_CANCEL, parent, title, message, dialogID, SWT.SHEET);
	}

	/**
	 * Convenience method to open a yes/no question dialog,
	 * with a "Remember my decision" toggle, returning the user's answer.
	 * The dialog is only opened if necessary (as determined by the preferences).
	 *
	 * @param parent
	 *            the parent shell of the dialog, or {@code null} if none
	 * @param title
	 *            the dialog's title, or {@code null} if none
	 * @param message
	 *            the message
	 * @param dialogID
	 *            the unique identifier of the dialog for persistence of the
	 *            toggle preference
	 * 
	 * @return the dialog result code, with or without having shown the dialog,
	 *         either {@link IDialogConstants#OK_ID} or {@link IDialogConstants#CANCEL_ID}
	 */
	public static int confirm(Shell parent, String title,
			String message, String dialogID) {

		return prompt(MessageDialog.CONFIRM, parent, title, message, dialogID, SWT.SHEET);
	}

	/**
	 * Convenience method to open a simple dialog of the given {@code kind},
	 * with a "Remember my decision" toggle, returning the user's answer.
	 * The dialog is only opened if necessary (as determined by the preferences).
	 *
	 * @param kind
	 *            the kind of dialog to open, one of {@link MessageDialog#ERROR},
	 *            {@link MessageDialog#INFORMATION}, {@link MessageDialog#QUESTION},
	 *            {@link MessageDialog#WARNING}, {@link MessageDialog#CONFIRM}, or
	 *            {@link MessageDialog#QUESTION_WITH_CANCEL}.
	 * @param parent
	 *            the parent shell of the dialog, or {@code null} if none
	 * @param title
	 *            the dialog's title, or {@code null} if none
	 * @param message
	 *            the message
	 * @param dialogID
	 *            the unique identifier of the dialog for persistence of the
	 *            toggle preference
	 * @param style
	 *            {@link SWT#NONE} for a default dialog, or {@link SWT#SHEET} for
	 *            a dialog with sheet behavior
	 * 
	 * @return the dialog result code, with or without having shown the dialog
	 */
	public static int prompt(int kind, Shell parent, String title,
			String message, String dialogID, int style) {

		int result = IDialogConstants.CANCEL_ID;

		IPreferenceStore store = getDialogTogglePreferenceStore();

		String preference = store.getString(dialogID);
		if (preference == null) {
			preference = MessageDialogWithToggle.PROMPT;
		}

		switch (preference) {
		case MessageDialogWithToggle.ALWAYS:
			switch (kind) {
			case MessageDialog.QUESTION:
			case MessageDialog.QUESTION_WITH_CANCEL:
				result = IDialogConstants.YES_ID;
				break;
			default:
				result = IDialogConstants.OK_ID;
				break;
			}
			break;
		case MessageDialogWithToggle.NEVER:
			switch (kind) {
			case MessageDialog.QUESTION:
			case MessageDialog.QUESTION_WITH_CANCEL:
				result = IDialogConstants.NO_ID;
				break;
			default:
				result = IDialogConstants.CANCEL_ID;
				break;
			}
			break;
		default:
			MessageDialogWithToggle dialog = MessageDialogWithToggle.open(
					kind, parent, title, message,
					null, false, store, dialogID, style);
			result = dialog.getReturnCode();
			break;
		}

		return result;
	}

	/**
	 * Convenience method to open a simple dialog of the given {@code kind},
	 * with a "Remember my decision" toggle, returning the user's answer from
	 * amongst an array of custom answer button labels.
	 * The dialog is only opened if necessary (as determined by the preferences).
	 *
	 * @param kind
	 *            the kind of dialog to open, one of {@link MessageDialog#ERROR},
	 *            {@link MessageDialog#INFORMATION}, {@link MessageDialog#QUESTION},
	 *            {@link MessageDialog#WARNING}, {@link MessageDialog#CONFIRM}, or
	 *            {@link MessageDialog#QUESTION_WITH_CANCEL}.
	 * @param parent
	 *            the parent shell of the dialog, or {@code null} if none
	 * @param title
	 *            the dialog's title, or {@code null} if none
	 * @param message
	 *            the message
	 * @param dialogID
	 *            the unique identifier of the dialog for persistence of the
	 *            toggle preference.  If {@code null}, then the "remember this decision"
	 *            toggle button is not shown
	 * @param style
	 *            {@link SWT#NONE} for a default dialog, or {@link SWT#SHEET} for
	 *            a dialog with sheet behavior
	 * @param yesishLabel
	 *            the label to use for the "yes"-ish or "OK"-ish button, according
	 *            to the specified {@code kind}
	 * @param noishLabel
	 *            the label to use for the "no"-ish button. Required for
	 *            {@link MessageDialog#CONFIRM} or {@link MessageDialog#QUESTION_WITH_CANCEL}
	 *            dialog, ignored for other dialog types. Note that the cancel button, if present,
	 *            is not customizable
	 * 
	 * @return the dialog result code, with or without having shown the dialog, which
	 *         in the case of cancellation is the standard {@link IDialogConstants#CANCEL_ID}
	 *         but otherwise will start counting from {@link IDialogConstants#CLIENT_ID}
	 */
	public static int custom(int kind, Shell parent, String title,
			String message, String dialogID, int style,
			String yesishLabel, String noishLabel) {

		int result = IDialogConstants.CANCEL_ID;

		IPreferenceStore store = getDialogTogglePreferenceStore();

		String preference = (dialogID == null) ? null : store.getString(dialogID);
		if (preference == null) {
			preference = MessageDialogWithToggle.PROMPT;
		}

		switch (preference) {
		case MessageDialogWithToggle.ALWAYS:
			result = IDialogConstants.CLIENT_ID;
			break;
		case MessageDialogWithToggle.NEVER:
			switch (kind) {
			case MessageDialog.QUESTION:
			case MessageDialog.QUESTION_WITH_CANCEL:
				result = IDialogConstants.CLIENT_ID + 1;
				break;
			default:
				result = IDialogConstants.CANCEL_ID;
				break;
			}
			break;
		default:
			String[] customButtonLabels;
			switch (kind) {
			case MessageDialog.QUESTION:
				customButtonLabels = new String[] { yesishLabel, noishLabel };
				break;
			case MessageDialog.QUESTION_WITH_CANCEL:
				customButtonLabels = new String[] { yesishLabel, noishLabel, IDialogConstants.CANCEL_LABEL };
				break;
			default:
				customButtonLabels = new String[] { yesishLabel, IDialogConstants.CANCEL_LABEL };
				break;
			}

			MessageDialog dialog;
			
			if (dialogID == null) {
				// No toggle
				dialog = new MessageDialog(parent, title, null, message, kind,
						customButtonLabels, 0) {
					{
						int sheetStyle = style & SWT.SHEET;
						setShellStyle(getShellStyle() | sheetStyle);
					}

					@Override
					protected void createButtonsForButtonBar(Composite parent) {
						String[] buttonLabels = getButtonLabels();
						Button[] buttons = new Button[buttonLabels.length];
						int defaultButtonIndex = getDefaultButtonIndex();

						int nextID = IDialogConstants.CLIENT_ID;
						for (int i = 0; i < buttonLabels.length; i++) {
							String label = buttonLabels[i];
							if (IDialogConstants.CANCEL_LABEL.equals(label)) {
								buttons[i] = createButton(parent, IDialogConstants.CANCEL_ID, label,
										i == defaultButtonIndex);
							} else {
								buttons[i] = createButton(parent, nextID, label,
										i == defaultButtonIndex);
								nextID = nextID + 1;
							}
						}
						setButtons(buttons);
					}
				};
			} else {
			dialog = new MessageDialogWithToggle(parent, title, null, message, kind,
					customButtonLabels, 0, null, false) {
				{
					int sheetStyle = style & SWT.SHEET;
					setShellStyle(getShellStyle() | sheetStyle);
				}

				@Override
				protected void buttonPressed(int buttonId) {
					setReturnCode(buttonId);
					close();

					if ((buttonId != IDialogConstants.CANCEL_ID) && getToggleState()
							&& (getPrefStore() != null) && (getPrefKey() != null)) {

						switch (buttonId) {
						case IDialogConstants.CLIENT_ID:
							getPrefStore().setValue(getPrefKey(), ALWAYS);
							break;
						case IDialogConstants.CLIENT_ID + 1:
							getPrefStore().setValue(getPrefKey(), NEVER);
							break;
						}
					}
				}

				@Override
				protected void createButtonsForButtonBar(Composite parent) {
					String[] buttonLabels = getButtonLabels();
					Button[] buttons = new Button[buttonLabels.length];
					int defaultButtonIndex = getDefaultButtonIndex();

					int nextID = IDialogConstants.CLIENT_ID;
					for (int i = 0; i < buttonLabels.length; i++) {
						String label = buttonLabels[i];
						if (IDialogConstants.CANCEL_LABEL.equals(label)) {
							buttons[i] = createButton(parent, IDialogConstants.CANCEL_ID, label,
									i == defaultButtonIndex);
						} else {
							buttons[i] = createButton(parent, nextID, label,
									i == defaultButtonIndex);
							nextID = nextID + 1;
						}
					}
					setButtons(buttons);
				}
			};

			((MessageDialogWithToggle )dialog).setPrefStore(store);
			((MessageDialogWithToggle )dialog).setPrefKey(dialogID);
			}
			
			dialog.open();
			result = dialog.getReturnCode();
		}

		return result;
	}

	/**
	 * Clears all "Remember my decision" dialog toggle settings managed through
	 * this API.
	 * 
	 * @see #getDialogTogglePreferenceStore()
	 */
	public static void clearDialogToggles() {
		IEclipsePreferences node = InstanceScope.INSTANCE.getNode(DIALOG_TOGGLE_PATH);
		if (node != null) {
			// Delete all of these dialog toggles
			try {
				node.removeNode();
			} catch (BackingStoreException e) {
				StatusAdapter adapter = new StatusAdapter(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.DialogPreferences_failedMsg, e));
				adapter.setProperty(IStatusAdapterConstants.TIMESTAMP_PROPERTY, System.currentTimeMillis());
				adapter.setProperty(IStatusAdapterConstants.TITLE_PROPERTY, Messages.DialogPreferences_failedTitle);
				StatusManager.getManager().handle(adapter, StatusManager.SHOW);
			}
		}

		// And invoke delegates
		getDelegateRegistry().getToggleDialogDelegates().forEach(d -> {
			try {
				d.clearUserDecisions();
			} catch (Exception e) {
				Activator.log.error("Uncaught exception in toggle-dialog delegate.", e); //$NON-NLS-1$
			}
		});
	}

	private static ToggleDialogDelegateRegistry getDelegateRegistry() {
		if (delegateRegistry == null) {
			delegateRegistry = new ToggleDialogDelegateRegistry();
		}

		return delegateRegistry;
	}
}
