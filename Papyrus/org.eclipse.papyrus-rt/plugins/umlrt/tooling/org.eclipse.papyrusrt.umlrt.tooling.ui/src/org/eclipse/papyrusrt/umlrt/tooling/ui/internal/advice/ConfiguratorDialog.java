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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.advice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.ValidationStatusProvider;
import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.properties.ui.creation.EditionDialog;
import org.eclipse.papyrus.infra.ui.util.UIUtil;
import org.eclipse.papyrus.infra.widgets.editors.StringEditor;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.DataBindingContextTracker;
import org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.DelegatingValidationStatusProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A specialized edit dialog that supports validation to conditionally enable
 * the OK button (with presentation of a prompt to tell the user what's missing).
 */
public class ConfiguratorDialog extends EditionDialog {
	public ConfiguratorDialog(Shell parentShell, boolean allowCancel) {
		super(parentShell, allowCancel);
	}

	@Override
	public int open() {
		int[] result = { Window.CANCEL };

		// Track validation status of bindings in the dialog
		DataBindingContextTracker.trackWhile(() -> {
			hookValidation();
			result[0] = super.open();
		});

		return result[0];
	}

	@Override
	public void create() {
		super.create();

		// Ensure that the first text field, if any, is ready for the user type into it
		for (Iterator<Text> fields = UIUtil.allChildren(getShell(), Text.class); fields.hasNext();) {
			Text next = fields.next();
			if (next.getEditable() && next.isEnabled()) {
				// This is the one
				next.forceFocus();
				next.selectAll();
				break;
			}
		}
	}

	/**
	 * Track the validation statuses of all data-binding contexts created during the execution of the dialog
	 * to enable or disable the OK button accordingly.
	 */
	protected void hookValidation() {
		// The overall validation status of the dialog
		IObservableList<ValidationStatusProvider> statusProviders = WritableList.withElementType(ValidationStatusProvider.class);
		AggregateValidationStatus validationStatus = new AggregateValidationStatus(statusProviders, AggregateValidationStatus.MAX_SEVERITY);
		validationStatus.addValueChangeListener(new IValueChangeListener<IStatus>() {
			@Override
			public void handleValueChange(ValueChangeEvent<? extends IStatus> event) {
				Button okButton = getOkButton();
				if ((okButton != null) && !okButton.isDisposed()) {
					IStatus status = event.getObservableValue().getValue();
					boolean valid = (status == null) || (status.getSeverity() < IStatus.ERROR);

					okButton.setEnabled(valid);
				}
			}
		});

		// Track data-binding contexts (and their validation status providers) coming and going
		DataBindingContextTracker.getDataBindingContexts().addListChangeListener(new IListChangeListener<DataBindingContext>() {
			private final Map<DataBindingContext, ValidationStatusProvider> dbcStatusProviders = new HashMap<>();

			@SuppressWarnings("unchecked")
			@Override
			public void handleListChange(ListChangeEvent<? extends DataBindingContext> event) {
				ListChangeEvent<DataBindingContext> _event = (ListChangeEvent<DataBindingContext>) event;
				_event.diff.accept(new ListDiffVisitor<DataBindingContext>() {
					@Override
					public void handleAdd(int index, DataBindingContext element) {
						AggregateValidationStatus aggregate = new AggregateValidationStatus(element, AggregateValidationStatus.MAX_SEVERITY);
						ValidationStatusProvider provider = new DelegatingValidationStatusProvider(aggregate);
						dbcStatusProviders.put(element, provider);
						statusProviders.add(provider);
					}

					@Override
					public void handleRemove(int index, DataBindingContext element) {
						ValidationStatusProvider provider = dbcStatusProviders.remove(element);
						if (provider != null) {
							statusProviders.remove(provider);
						}
					}
				});
			}
		});

	}

	@Override
	protected void okPressed() {
		// On Mac platform, if a text editor has focus, that focus is not lost when
		// the OK button is pressed nor the Enter key. So, force the issue
		Control focus = this.getShell().getDisplay().getFocusControl();
		if ((focus instanceof Text) && (focus.getParent() instanceof StringEditor)) {
			// Hack: Trigger the an update from the text field
			((Text) focus).notifyListeners(SWT.FocusOut, new Event());
		}

		// Validation OK?
		if (getOkButton().isEnabled()) {
			super.okPressed();
		}
	}
}
