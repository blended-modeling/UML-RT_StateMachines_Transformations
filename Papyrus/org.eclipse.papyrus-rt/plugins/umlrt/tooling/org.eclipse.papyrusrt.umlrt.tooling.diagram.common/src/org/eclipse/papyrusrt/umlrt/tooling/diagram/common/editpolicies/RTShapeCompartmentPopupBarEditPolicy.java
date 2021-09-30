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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies;

import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.PapyrusPopupBarEditPolicy;

/**
 * A specialized popup-bar (diagram assistant) edit-policy for shape compartments
 * in RT diagrams that locates itself at the mouse location as it would do on
 * the diagram surface because the shape compartment plays the role of diagram
 * surface inasmuch as it contains the majority of the meaningful content.
 */
public class RTShapeCompartmentPopupBarEditPolicy extends PapyrusPopupBarEditPolicy {

	public RTShapeCompartmentPopupBarEditPolicy() {
		super();
	}

	@Override
	public void activate() {
		super.activate();

		if (!getIsInstalledOnSurface() && (getHost() instanceof ShapeCompartmentEditPart)) {
			setIsInstalledOnSurface(true);
		}
	}
}
