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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;

/**
 * Common protocol for RT-specific port edit-parts.
 */
public interface IRTPortEditPart extends IGraphicalEditPart {
	/**
	 * Queries the port that I present.
	 * 
	 * @return my port
	 */
	default Port getPort() {
		return (Port) resolveSemanticElement();
	}

	/**
	 * Queries whether my port is a port on a capsule-part.
	 * 
	 * @return whether my port is a port-on-part
	 */
	default boolean isPortOnPart() {
		boolean result = false;

		EditPart parent = getParent();
		if (parent instanceof IGraphicalEditPart) {
			EObject parentElement = ((IGraphicalEditPart) parent).resolveSemanticElement();
			result = (parentElement instanceof Property)
					&& CapsulePartUtils.isCapsulePart((Property) parentElement);
		}

		return result;
	}

	/**
	 * Queries whether my port is a port on a capsule frame.
	 * 
	 * @return whether my port is a port on a capsule
	 */
	default boolean isPortOnCapsule() {
		return (getPort() != null) && !isPortOnPart();
	}

	/**
	 * Queries whether an edit part presents a port on a capsule-part.
	 * 
	 * @param editPart
	 *            an edit-part
	 * @return whether it presents a port-on-part
	 */
	static boolean isPortOnPart(EditPart editPart) {
		return (editPart instanceof IRTPortEditPart)
				&& ((IRTPortEditPart) editPart).isPortOnPart();
	}

	/**
	 * Queries whether an edit part presents a port on a capsule frame.
	 * 
	 * @param editPart
	 *            an edit-part
	 * @return whether it presents a port on a capsule frame
	 */
	static boolean isPortOnCapsule(EditPart editPart) {
		return (editPart instanceof IRTPortEditPart)
				&& ((IRTPortEditPart) editPart).isPortOnCapsule();
	}

	/**
	 * Obtains the default size preferred for my port,
	 * or {@code null} to inherit the size from Papyrus.
	 * 
	 * @return the preferred default size, or {@code null} to inherit from Papyrus
	 */
	default Dimension getDefaultSize() {
		return getDefaultSize(isPortOnPart());
	}

	/**
	 * Obtains the default size preferred for the given {@code port}
	 * or {@code null} to inherit the size from Papyrus.
	 * 
	 * @param portOnPart
	 *            whether the port is a port on a capsule-part (otherwise on a capsule)
	 * 
	 * @return the preferred default size, or {@code null} to inherit from Papyrus
	 */
	static Dimension getDefaultSize(boolean portOnPart) {
		return portOnPart ? new Dimension(11, 11) : new Dimension(16, 16);
	}

	/**
	 * Obtains the default border-item locator scaling factor preferred for my
	 * port, or {@code null} to inherit the size from Papyrus.
	 * 
	 * @return the preferred default scaling factor
	 */
	default double getDefaultScaleFactor() {
		return getDefaultScaleFactor(isPortOnPart());
	}

	/**
	 * Obtains the default border-item locator scaling factor preferred for ports
	 * on capsules or parts, or {@code null} to inherit the size from Papyrus.
	 * 
	 * @param portOnPart
	 *            whether the port is a port on a capsule-part (otherwise on a capsule)
	 * 
	 * @return the preferred default scaling factor, or {@code null} to inherit from Papyrus
	 */
	static double getDefaultScaleFactor(boolean portOnPart) {
		return portOnPart ? 11.0 / 20.0 : 16.0 / 20.0;
	}
}
