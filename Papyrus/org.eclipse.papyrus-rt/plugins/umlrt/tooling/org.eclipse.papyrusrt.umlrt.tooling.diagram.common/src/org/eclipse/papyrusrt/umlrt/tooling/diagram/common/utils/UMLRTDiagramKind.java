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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.common.util.CacheAdapter;

/**
 * Enumeration of UML-RT specific diagram kinds. Any diagram that is not
 * an UML-RT diagram is of the {@link #OTHER} kind.
 * 
 * @see UMLRTCapsuleStructureDiagramUtils#isCapsuleStructureDiagram(Diagram)
 * @see UMLRTStateMachineDiagramUtils#isRTStateMachineDiagram(Diagram)
 */
public enum UMLRTDiagramKind {
	/** An UML-RT capsule structure diagram. */
	CAPSULE_STRUCTURE,
	/** An UML-RT state machine diagram. */
	STATE_MACHINE,
	/** Any other kind of diagram, including plain UML diagrams and the {@code null} diagram. */
	OTHER;

	public boolean isUMLRTDiagram() {
		return this != OTHER;
	}

	public boolean isOtherDiagram() {
		return this == OTHER;
	}

	/**
	 * Queries the kind of a {@code diagram}.
	 * 
	 * @param diagram
	 *            a diagram
	 * @return its kind (never {@code null})
	 */
	public static UMLRTDiagramKind getKind(Diagram diagram) {
		CacheAdapter cache = CacheAdapter.getCacheAdapter(diagram);
		UMLRTDiagramKind result = (cache == null)
				? null
				: (UMLRTDiagramKind) cache.get(diagram, UMLRTDiagramKind.class);
		if (result == null) {
			result = (diagram == null)
					? OTHER
					: UMLRTCapsuleStructureDiagramUtils.isCapsuleStructureDiagram(diagram)
							? CAPSULE_STRUCTURE
							: UMLRTStateMachineDiagramUtils.isRTStateMachineDiagram(diagram)
									? STATE_MACHINE
									: OTHER;
			if (cache != null) {
				cache.put(diagram, UMLRTDiagramKind.class, result);
			}
		}

		return result;
	}

	/**
	 * Queries the kind of diagram containing a notation {@code view}.
	 * 
	 * @param view
	 *            a view in a diagram
	 * @return the diagram's kind (never {@code null})
	 */
	public static UMLRTDiagramKind getDiagramKind(View view) {
		return (view == null) ? OTHER : getKind(view.getDiagram());
	}

	/**
	 * Queries the kind of diagram containing an {@code editPart}.
	 * 
	 * @param editPart
	 *            an edit-part in a diagram
	 * @return the diagram's kind (never {@code null})
	 */
	public static UMLRTDiagramKind getDiagramKind(EditPart editPart) {
		return (editPart instanceof IGraphicalEditPart)
				? getDiagramKind(((IGraphicalEditPart) editPart).getNotationView())
				: OTHER;
	}
}
