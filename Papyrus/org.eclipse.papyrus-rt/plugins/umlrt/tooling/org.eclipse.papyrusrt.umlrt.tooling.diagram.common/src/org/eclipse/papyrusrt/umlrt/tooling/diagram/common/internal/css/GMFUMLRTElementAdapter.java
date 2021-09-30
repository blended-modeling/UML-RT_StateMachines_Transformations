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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.css;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.uml.diagram.css.dom.GMFUMLElementAdapter;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.uml2.uml.NamedElement;

/**
 * UML-RT implementation of the DOM element adapter for CSS.
 */
public class GMFUMLRTElementAdapter extends GMFUMLElementAdapter {
	public static final String IS_TYPE_REDEFINED = "isTypeRedefined"; //$NON-NLS-1$

	/**
	 * Initializes me.
	 *
	 * @param view
	 *            my notation view
	 * @param engine
	 *            my CSS engine
	 */
	public GMFUMLRTElementAdapter(View view, ExtendedCSSEngine engine) {
		super(view, engine);

		// Replace the superclass helper with an inheritance-aware implementation
		helper = CSSDOMUMLRTSemanticElementHelper.getInstance();
	}

	@Override
	protected String doGetAttribute(String attr) {
		String result = super.doGetAttribute(attr);

		if ((result == null) && (semanticElement instanceof NamedElement)) {
			UMLRTNamedElement element;

			switch (attr) {
			case IS_TYPE_REDEFINED:
				element = UMLRTFactory.create((NamedElement) semanticElement);
				if (element != null) {
					result = isTypeRedefined(element);
				}
				break;
			}
		}

		return result;
	}

	/**
	 * Obtains the value of the {@link #IS_TYPE_REDEFINED isTypeRedefined} attribute
	 * for an {@code element}.
	 * 
	 * @param element
	 *            an UML-RT element
	 * @return whether it redefines its inherited type
	 */
	protected String isTypeRedefined(UMLRTNamedElement element) {
		boolean result = false;

		if (element.isRedefinition()) {
			if (element instanceof UMLRTPort) {
				UMLRTPort port = (UMLRTPort) element;
				result = port.getType() != port.getRedefinedPort().getType();
			} else if (element instanceof UMLRTCapsulePart) {
				UMLRTCapsulePart part = (UMLRTCapsulePart) element;
				result = part.getType() != part.getRedefinedPart().getType();
			}
		}

		return Boolean.toString(result);
	}
}
