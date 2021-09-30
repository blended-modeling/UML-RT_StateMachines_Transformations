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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.canonical;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLVisualChildrenStrategy;

/**
 * Specialization of the default visual children strategy that accounts for the difference
 * between the resolved semantic element and the referenced inherited semantic element.
 */
public class RTInheritableVisualChildrenStrategy extends DefaultUMLVisualChildrenStrategy {

	public RTInheritableVisualChildrenStrategy() {
		super();
	}

	@Override
	public List<? extends View> getCanonicalChildren(EditPart editPart, View view) {
		List<? extends View> result = super.getCanonicalChildren(editPart, view);

		// Just filter out all children that inherit the semantic element
		result.removeIf(v -> !v.isSetElement());

		return result;
	}

}
