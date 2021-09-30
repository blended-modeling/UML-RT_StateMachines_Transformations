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
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.RoundedRectangleNodePlateFigure;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTPortResizableEditPolicy;

/**
 * Specialization of the base edit-part for ports on parts.
 */
public class RTPortOnPartEditPart extends RTPortEditPart implements IRTPortEditPart {

	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public RTPortOnPartEditPart(View view) {
		super(view);
	}

	/**
	 * Override the inherited method for a smaller default size.
	 */
	@Override
	protected NodeFigure createNodePlate() {
		Dimension defaultSize = getDefaultSize();

		RoundedRectangleNodePlateFigure figure = new RoundedRectangleNodePlateFigure(
				defaultSize);

		// Get dimension from notation
		int width = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Width())).intValue();
		int height = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Height())).intValue();

		int w = (width > defaultSize.width()) ? width : defaultSize.width();
		int h = (height > defaultSize.height()) ? height : defaultSize.height();

		figure.getBounds().setSize(w, h);
		figure.setDefaultSize(w, h);

		return figure;
	}

	@Override
	public boolean isPortOnPart() {
		return true;
	}

	@Override
	public EditPolicy getPrimaryDragEditPolicy() {
		return new RTPortResizableEditPolicy();
	}
}
