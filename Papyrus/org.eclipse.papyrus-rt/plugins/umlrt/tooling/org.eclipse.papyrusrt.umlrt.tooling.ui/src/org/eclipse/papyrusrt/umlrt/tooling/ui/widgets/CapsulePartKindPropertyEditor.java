/*******************************************************************************
 * Copyright (c) 2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *  Young-Soo Roh - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement.RTStereotypeModelElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Property;

/**
 * Capsule part kind editor.
 */
public class CapsulePartKindPropertyEditor extends AbstractPropertyEditor {

	/**
	 * 
	 */
	protected RTEnumRadioValueEditor kindValueEditor;

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 */
	public CapsulePartKindPropertyEditor(Composite parent, int style) {
		super(new RTEnumRadioValueEditor(parent, style));
		kindValueEditor = (RTEnumRadioValueEditor) valueEditor;
	}

	/**
	 * Applies the readOnly state to the editor.
	 * 
	 * @param readOnly
	 *            Indicates if this widget should be read-only
	 */
	@Override
	protected void applyReadOnly(boolean readOnly) {
		super.applyReadOnly(readOnly);
		if (!readOnly) {
			ModelElement element = input.getModelElement(propertyPath);
			CapsulePart part = (CapsulePart) ((RTStereotypeModelElement) element).getSource();
			Property baseProperty = null;
			if (part != null) {
				baseProperty = part.getBase_Property();
			}
			if (baseProperty != null && baseProperty.getLower() == 0 && baseProperty.getUpper() == -1) {
				kindValueEditor.setReadOnly(UMLRTCapsulePartKind.FIXED, false);
			} else {
				kindValueEditor.setReadOnly(UMLRTCapsulePartKind.FIXED, true);
			}
		}
	}

	@Override
	protected void doBinding() {
		kindValueEditor.setProviders(input.getContentProvider(propertyPath), input.getLabelProvider(propertyPath));
		super.doBinding();
	}
}
