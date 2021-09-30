/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Martin Fleck - initial API and implementation
 *******************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.figures.CustomStateCompartmentFigure;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.figures.RTCustomStateCompartmentFigure;

/**
 * UML-RT implementation of the {@link CustomStateCompartmentEditPart} to use a custom {@link RTCustomStateCompartmentFigure}.
 * 
 * @author Martin Fleck <mfleck@eclipsesource.com>
 */
public class RTStateCompartmentEditPart extends CustomStateCompartmentEditPart {

	/**
	 * Initializes me with my notation view.
	 *
	 * @param view
	 *            my notation view
	 */
	public RTStateCompartmentEditPart(View view) {
		super(view);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Specifically, this method creates a {@link RTCustomStateCompartmentFigure} which hides it's scrollbars after expansion.
	 *
	 * @return the figure created for this edit part
	 */
	@Override
	public IFigure createFigure() {
		CustomStateCompartmentFigure result = new RTCustomStateCompartmentFigure(getCompartmentName(), getMapMode());
		return result;
	}
}
