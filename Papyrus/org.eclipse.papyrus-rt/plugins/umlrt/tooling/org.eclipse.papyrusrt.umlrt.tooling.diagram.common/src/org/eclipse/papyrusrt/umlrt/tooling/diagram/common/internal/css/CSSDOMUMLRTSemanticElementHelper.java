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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.css.helper.CSSDOMUMLSemanticElementHelper;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Element;

/**
 * A specialized semantic-element helper that knows how to resolve view references to
 * inherited semantic elements in the context of the subclass capsule.
 */
public class CSSDOMUMLRTSemanticElementHelper extends CSSDOMUMLSemanticElementHelper {

	private static final CSSDOMUMLRTSemanticElementHelper INSTANCE = new CSSDOMUMLRTSemanticElementHelper();

	protected CSSDOMUMLRTSemanticElementHelper() {
		super();
	}

	public static CSSDOMUMLRTSemanticElementHelper getInstance() {
		return INSTANCE;
	}

	@Override
	public EObject findSemanticElement(EObject notationElement) {
		EObject result = super.findSemanticElement(notationElement);

		if ((result instanceof Element)
				&& UMLRTExtensionUtil.hasUMLRTExtension((Element) result)
				&& (notationElement instanceof View)) {

			// Resolve the inherited/redefined element
			result = EditPartInheritanceUtils.resolveSemanticElement((View) notationElement);
		}

		return result;
	}
}
