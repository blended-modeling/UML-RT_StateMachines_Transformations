/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 510188
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.widgets.editors.EnumRadio;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.google.common.base.Strings;

/**
 * Value Editor specific to the PortRTKind.
 */
public class RTEnumRadioValueEditor extends EnumRadio {

	public RTEnumRadioValueEditor(Composite parent, int style, String label) {
		super(parent, style, label);
	}

	public RTEnumRadioValueEditor(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.editors.EnumRadio#setProviders(org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider, org.eclipse.jface.viewers.ILabelProvider)
	 *
	 * @param contentProvider
	 * @param labelProvider
	 */
	@Override
	public void setProviders(IStaticContentProvider contentProvider, ILabelProvider labelProvider) {
		super.setProviders(contentProvider, labelProvider);

		if (labelProvider != null) {
			// Use the label provider to get localized labels for the enum values
			values.entrySet().forEach(entry -> {
				String label = labelProvider.getText(entry.getValue());
				if (!Strings.isNullOrEmpty(label)) {
					entry.getKey().setText(label);
				}
			});
		} else {
			// Capitalize first letter of each literal name and insert spaces (assumes English)
			values.keySet().forEach(b -> {
				String label = b.getText();
				label = label.substring(0, 1).toUpperCase() + label.substring(1);
				label = label.replaceAll("([a-z])(A-Z)", "$1 $2");
				b.setText(b.getText().substring(0, 1).toUpperCase() + b.getText().substring(1));
			});
		}
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		for (Button button : values.keySet()) {
			if (readOnly) {
				if (!button.isDisposed()) {
					button.setEnabled(false);
				}
			}
		}
		if (!buttonsArea.isDisposed()) {
			buttonsArea.setEnabled(!readOnly);
		}
	}

	/**
	 * Sets the read only state for a particular value of the enum radio. if the button is the selected one, no change will be applied to it
	 * 
	 * @param value
	 *            the value for which read only status should be applied
	 * @param readOnly
	 *            <code>true</code> if the element should be set read only
	 */
	public void setReadOnly(Object value, boolean readOnly) {
		for (Button button : values.keySet()) {
			// even if not read only, we should check each value if it has some specifics that apply and make it not enable
			Object object = values.get(button);
			if (object == value) {
				if (!button.isDisposed()) {
					button.setEnabled(readOnly);
				}

			}
		}
	}

	@Override
	public boolean isReadOnly() {
		for (Button button : values.keySet()) {
			if (!button.isDisposed() && button.isEnabled()) {
				return false;
			}
		}
		return true;
	}
}
