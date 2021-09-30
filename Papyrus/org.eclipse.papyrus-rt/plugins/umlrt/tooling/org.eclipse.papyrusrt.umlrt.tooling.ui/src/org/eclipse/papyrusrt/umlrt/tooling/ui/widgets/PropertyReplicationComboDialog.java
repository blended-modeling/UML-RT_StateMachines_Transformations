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

package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.tools.databinding.AggregatedObservable;
import org.eclipse.papyrus.infra.widgets.editors.StringCombo;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.UnchangedObject;
import org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Property;

/**
 * @author as247872
 *
 */
public class PropertyReplicationComboDialog extends StringCombo {


	private Button browseButton;

	private TreeSelectorDialog dialog;

	private Timer timer;

	private TimerTask changeColorTask;

	public PropertyReplicationComboDialog(Composite parent, int style) {
		super(parent, style);
		((GridLayout) getLayout()).numColumns = 2;
		browseButton = factory.createButton(this, "Browse...", SWT.PUSH);
		GridData gridData = new GridData();

		browseButton.setLayoutData(gridData);
		browseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Object property = getContextElement();
				ResourceSet resSet = EMFHelper.getResourceSet((EObject) property);
				dialog = new TreeSelectorDialog(getShell());
				if (labelText != null) {
					dialog.setTitle(labelText);
				}

				dialog.setInput(resSet);
				ConstantContentProvider contentProvider = new ConstantContentProvider();
				dialog.setContentProvider(contentProvider);

				dialog.setLabelProvider(new UMLLabelProvider());

				int code = dialog.open();
				if (code == Window.OK) {
					Object[] result = dialog.getResult();
					if (result.length > 0) {
						Object constant = result[0];
						if (constant instanceof Property) {
							setResult((Property) constant);
						}
					}
				}
			}


			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Nothing
			}

		});


	}

	protected void setResult(Property cst) {
		modelProperty.setValue(cst.getQualifiedName());
		updateControls();
	}


	@Override
	protected IObservableValue getObservableValue() {
		return new CapsuleComboObservableValue();
	}

	private void cancelChangeColorTask() {
		if (changeColorTask != null) {
			changeColorTask.cancel();
			changeColorTask = null;
		}
	}

	private void changesColorField() {

		if (binding != null) {

			if (timer == null) {
				timer = new Timer(true);
			}

			cancelChangeColorTask();
			changeColorTask = new TimerTask() {

				@Override
				public void run() {
					if (PropertyReplicationComboDialog.this.isDisposed()) {
						return;
					}
					PropertyReplicationComboDialog.this.getDisplay().syncExec(new Runnable() {

						@Override
						public void run() {
							combo.setBackground(DEFAULT);
							combo.update();
						}
					});
				}
			};

			if (errorBinding) {
				combo.setBackground(ERROR);
				combo.update();
			} else {
				IStatus status = (IStatus) binding.getValidationStatus().getValue();
				switch (status.getSeverity()) {
				case IStatus.OK:
					timer.schedule(changeColorTask, 600);
					combo.setBackground(VALID);
					combo.update();
					break;
				case IStatus.ERROR:
					combo.setBackground(ERROR);
					combo.update();
					break;
				default:
					combo.setBackground(DEFAULT);
					combo.update();
					break;
				}
			}
		}
	}

	class CapsuleComboObservableValue extends AbstractObservableValue implements SelectionListener, KeyListener, FocusListener {

		private String previousValue;

		public CapsuleComboObservableValue() {
			previousValue = combo.getText();
			combo.addSelectionListener(this); // Selection change
			combo.addKeyListener(this); // Enter pressed
			combo.addFocusListener(this); // Focus lost
		}

		@Override
		public Object getValueType() {
			return String.class;
		}

		@Override
		protected String doGetValue() {
			return combo.getText();
		}

		@Override
		protected void doSetValue(Object value) {
			if (modelProperty instanceof AggregatedObservable && ((AggregatedObservable) modelProperty).hasDifferentValues()) {
				combo.setText(UnchangedObject.instance.toString());
			} else if (value instanceof String) {
				// This is the new baseline value (coming from the model) against which to compare a future edit by the user
				previousValue = (String) value;
				combo.setText(previousValue);
			}
		}

		// Enter pressed
		@Override
		public void keyReleased(KeyEvent e) {
			if ((e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) && e.stateMask == SWT.NONE) {
				maybeFireChange();
				changesColorField();
				e.doit = false; // Stops the propagation of the event

			}
		}

		// Selection change
		@Override
		public void widgetSelected(SelectionEvent e) {
			maybeFireChange();
		}

		// Focus lost
		@Override
		public void focusLost(FocusEvent e) {
			maybeFireChange();

		}

		void maybeFireChange() {
			// Only report a change if there is actually a change, otherwise we get a no-op command that dirties the editor
			final String currentValue = doGetValue();
			if ((currentValue == null) ? previousValue != null : !currentValue.equals(previousValue)) {
				doFireChange();
			}
		}

		private void doFireChange() {
			final String oldValue = previousValue;
			final String currentValue = doGetValue();
			previousValue = currentValue;
			fireValueChange(new ValueDiff() {

				@Override
				public Object getOldValue() {
					return oldValue;
				}

				@Override
				public Object getNewValue() {
					return currentValue;
				}
			});
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// Nothing
		}

		@Override
		public void focusGained(FocusEvent e) {
			// Nothing
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// Nothing
		}
	}

}