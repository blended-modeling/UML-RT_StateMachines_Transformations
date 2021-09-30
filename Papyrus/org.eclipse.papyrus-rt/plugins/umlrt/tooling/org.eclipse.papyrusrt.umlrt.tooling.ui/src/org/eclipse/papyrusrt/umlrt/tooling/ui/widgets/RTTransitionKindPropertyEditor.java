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

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor;
import org.eclipse.papyrusrt.umlrt.core.utils.TransitionUtils;
import org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement.UMLRTExtModelElement;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.Vertex;

/**
 * Specific EnumRadio for Transition kind that can have read only status only
 * on some of the radio buttons.
 */
public class RTTransitionKindPropertyEditor extends AbstractPropertyEditor {

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
	public RTTransitionKindPropertyEditor(Composite parent, int style) {
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
			Transition transition = (Transition) ((UMLRTExtModelElement) element).getSource();
			Vertex source = transition.getSource();
			Vertex target = transition.getTarget();
			TransitionKind kind = transition.getKind();
			if (source instanceof Pseudostate) {
				PseudostateKind pseudoKind = ((Pseudostate) source).getKind();
				if (!PseudostateKind.ENTRY_POINT_LITERAL.equals(pseudoKind)
						&& !PseudostateKind.EXIT_POINT_LITERAL.equals(pseudoKind)) {
					// pseudo state other than entry or exit point should be external only.
					Stream.of(TransitionKind.values()).filter(k -> k != kind)
							.forEach(ev -> kindValueEditor.setReadOnly(ev, false));
					return;
				}
			}

			for (TransitionKind targetKind : TransitionKind.values()) {

				boolean isEditable = isEditable(transition, source, target, kind, targetKind);
				kindValueEditor.setReadOnly(targetKind, isEditable);
			}
		}
	}

	/**
	 * check if target kind should be editable.
	 * 
	 * @param transition
	 *            transition
	 * @param source
	 *            source vertex
	 * @param target
	 *            target vertex
	 * @param sourceKind
	 *            source kind
	 * @param targetKind
	 *            target kind
	 * @return true if editable
	 */
	private boolean isEditable(Transition transition, EObject source, EObject target, TransitionKind sourceKind, TransitionKind targetKind) {

		TransitionKind expectedKind = TransitionUtils.getTransitionKind(transition, source, target);
		return expectedKind == targetKind;
	}

	@Override
	protected void doBinding() {
		kindValueEditor.setProviders(input.getContentProvider(propertyPath), input.getLabelProvider(propertyPath));
		super.doBinding();
	}

}
