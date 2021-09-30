/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;

/**
 * Common protocol for RT-specific pseudo-state edit-parts.
 */
public interface IRTPseudostateEditPart extends IGraphicalEditPart, IStateMachineInheritableEditPart {
	/**
	 * Queries the pseudostate that I present.
	 * 
	 * @return my pseudostate
	 */
	default Pseudostate getPseudostate() {
		return (Pseudostate) resolveSemanticElement();
	}

	/**
	 * Queries whether my pseudostate is a connection point.
	 * 
	 * @return whether my pseudostate is a connection point
	 */
	default boolean isConnectionPoint() {
		switch (getPseudostate().getKind()) {
		case ENTRY_POINT_LITERAL:
		case EXIT_POINT_LITERAL:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Queries whether an edit part presents a connection point.
	 * 
	 * @param editPart
	 *            an edit-part
	 * @return whether it presents a connection point
	 */
	static boolean isConnectionPoint(EditPart editPart) {
		return (editPart instanceof IRTPseudostateEditPart)
				&& ((IRTPseudostateEditPart) editPart).isConnectionPoint();
	}

	/**
	 * Obtains the default size preferred for my pseudostate,
	 * or {@code null} to inherit the size from Papyrus.
	 * 
	 * @return the preferred default size, or {@code null} to inherit from Papyrus
	 */
	default Dimension getDefaultSize() {
		return getDefaultSize(getPseudostate().getKind());
	}

	/**
	 * Obtains the default size preferred for pseudostates of the given
	 * {@code kind}, or {@code null} to inherit the size from Papyrus.
	 * 
	 * @param kind
	 *            a pseudostate kind
	 * 
	 * @return the preferred default size, or {@code null} to inherit from Papyrus
	 */
	static Dimension getDefaultSize(PseudostateKind kind) {
		switch (kind) {
		case ENTRY_POINT_LITERAL:
		case EXIT_POINT_LITERAL:
			return new Dimension(10, 10);
		case CHOICE_LITERAL:
			return new Dimension(35, 30);
		case JUNCTION_LITERAL:
			return new Dimension(12, 12);
		case DEEP_HISTORY_LITERAL:
			return new Dimension(25, 25);
		default:
			return null;
		}
	}

	/**
	 * Obtains the default border-item locator scaling factor preferred for mu
	 * connection point, or {@code null} to inherit the size from Papyrus.
	 * 
	 * @return the preferred default scaling factor
	 */
	default double getDefaultScaleFactor() {
		return getDefaultScaleFactor(getPseudostate().getKind());
	}

	/**
	 * Obtains the default border-item locator scaling factor preferred for connection
	 * points of the given {@code kind}, or {@code null} to inherit the size from Papyrus.
	 * 
	 * @param kind
	 *            a connection-point kind
	 * 
	 * @return the preferred default scaling factor, or {@code null} to inherit from Papyrus
	 */
	static double getDefaultScaleFactor(PseudostateKind kind) {
		switch (kind) {
		case ENTRY_POINT_LITERAL:
		case EXIT_POINT_LITERAL:
			return 0.5;
		default:
			return 1.0;
		}
	}
}
