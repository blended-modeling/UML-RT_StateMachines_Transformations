/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts;

import java.util.Objects;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTCapsuleStructureDiagramUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;

/**
 * Protocol implemented by a subordinate, such as a compartment or list item,
 * of an inheritable shape edit-part.
 */
public interface ISubordinateInheritableEditPart extends IInheritableEditPart {

	/**
	 * The nearest inheritable containing shape determins the redefinition context.
	 */
	@Override
	default UMLRTNamedElement getRedefinitionContext(UMLRTNamedElement element) {
		for (EditPart next = getParent(); next instanceof IGraphicalEditPart; next = next.getParent()) {
			if (next instanceof ISubordinateInheritableEditPart) {
				return ((ISubordinateInheritableEditPart) next).getRedefinitionContext(element);
			}
		}

		return element.getRedefinitionContext();
	}

	/**
	 * The nearest inheritable containing shape determins the primary diagram.
	 */
	@Override
	default Diagram getPrimaryDiagram(UMLRTNamedElement context) {
		for (EditPart next = getParent(); next instanceof IGraphicalEditPart; next = next.getParent()) {
			if (next instanceof ISubordinateInheritableEditPart) {
				return ((ISubordinateInheritableEditPart) next).getPrimaryDiagram(context);
			}
		}

		return UMLRTCapsuleStructureDiagramUtils.getDiagram(context.toUML(), Objects::nonNull);
	}
}
