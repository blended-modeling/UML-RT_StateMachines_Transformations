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
import org.eclipse.papyrus.uml.diagram.css.dom.GMFUMLElementProvider;
import org.w3c.dom.Element;

/**
 * A specialized CSS DOM element provider that knows how to resolve view references to
 * inherited semantic elements in the context of the subclass capsule.
 */
public class GMFUMLRTElementProvider extends GMFUMLElementProvider {

	public GMFUMLRTElementProvider() {
		super();
	}

	@Override
	public Element getElement(Object element, @SuppressWarnings("restriction") org.eclipse.e4.ui.css.core.engine.CSSEngine engine) {
		if (!(element instanceof View)) {
			throw new IllegalArgumentException("Unknown element : " + element); //$NON-NLS-1$
		}

		if (!(engine instanceof ExtendedCSSEngine)) {
			throw new IllegalArgumentException("Invalid CSS Engine : " + engine); //$NON-NLS-1$
		}

		return new GMFUMLRTElementAdapter((View) element, (ExtendedCSSEngine) engine);
	}
}
