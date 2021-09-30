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
 *  Christian W. Damus - bug 467545
 *  
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElement;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement.RTStereotypeModelElement;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Radio group editor for an enumeration of RT element kinds, supporting selective
 * enablement of one or more options under certain circumstances.
 */
public class RTKindEnumPropertyEditor extends AbstractPropertyEditor {

	protected RTEnumRadioValueEditor kindValueEditor;

	protected int numColumns = -1;

	protected Class<? extends Enum<?>> enumType;

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 */
	public RTKindEnumPropertyEditor(Composite parent, int style) {
		super(new RTEnumRadioValueEditor(parent, style));
		kindValueEditor = (RTEnumRadioValueEditor) valueEditor;
	}

	public void setEnumType(Class<? extends Enum<?>> enumType) {
		this.enumType = enumType;

		if ((input != null) && (propertyPath != null)) {
			// Read-only states may have changed
			applyReadOnly(readOnly);
		}
	}

	public Class<? extends Enum<?>> getEnumType() {
		return enumType;
	}

	/**
	 * Applies the readOnly state to the editor
	 *
	 * @param readOnly
	 *            Indicates if this widget should be read-only
	 */
	@Override
	protected void applyReadOnly(boolean readOnly) {
		super.applyReadOnly(readOnly);
		if (!readOnly && (enumType != null)) {
			// should be writable, but check for specific non executable commands
			ModelElement element = input.getModelElement(propertyPath);
			Element uml = getSourceUMLElement(element);
			if (uml != null) {
				for (Enum<?> enumValue : enumType.getEnumConstants()) {
					kindValueEditor.setReadOnly(enumValue, isKindEditable(uml, enumValue));
				}
			}
		}
	}

	protected Element getSourceUMLElement(ModelElement element) {
		Element result = null;

		if (element instanceof UMLModelElement) {
			result = (Element) ((UMLModelElement) element).getSource();
		} else if (element instanceof RTStereotypeModelElement) {
			result = UMLUtil.getBaseElement(((RTStereotypeModelElement) element).getSource());
		}

		return result;
	}

	protected boolean isKindEditable(Element element, Enum<?> enumValue) {
		boolean result = true;

		try {
			IStatus status = getValidator().validate(enumValue);
			result = (status == null) || (status.getSeverity() < IStatus.ERROR);
		} catch (Exception e) {
			Activator.log.error("Failed to determine validity of enum value; probable properties view configuration fault", e); //$NON-NLS-1$
		}

		return result;
	}

	/**
	 * Sets the maximum number of columns for this editor. The radio values
	 * will be distributed according to this number
	 *
	 * @param numColumns
	 */
	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
		kindValueEditor.setNumColumns(numColumns);
	}

	/**
	 * Return the maximum number of columns for this editor
	 *
	 * @return
	 * 		The number of columns for this editor
	 */
	public int getNumColumns() {
		return numColumns;
	}

	@Override
	protected void doBinding() {
		kindValueEditor.setProviders(input.getContentProvider(propertyPath), input.getLabelProvider(propertyPath));
		super.doBinding();
	}

}
