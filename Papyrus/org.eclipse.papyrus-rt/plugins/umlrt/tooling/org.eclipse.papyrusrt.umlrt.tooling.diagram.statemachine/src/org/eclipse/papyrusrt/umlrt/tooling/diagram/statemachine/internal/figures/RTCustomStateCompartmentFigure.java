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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.figures;

import org.eclipse.draw2d.ScrollPane;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.figures.CustomStateCompartmentFigure;

/**
 * UML-RT implementation of the {@link CustomStateCompartmentFigure} to hide the scrollbars after their visibility has been modified through expansion.
 * 
 * @author Martin Fleck <mfleck@eclipsesource.com>
 */
public class RTCustomStateCompartmentFigure extends CustomStateCompartmentFigure {

	/**
	 * Create a new instance.
	 * 
	 * @param compartmentTitle
	 *            title of the compartment.
	 * @param mm
	 *            the <code>IMapMode</code> that is used to initialize the
	 *            default values of of the scrollpane contained inside the figure. This is
	 *            necessary since the figure is not attached at construction time and consequently
	 *            can't get access to the owned IMapMode in the parent containment hierarchy.
	 */
	public RTCustomStateCompartmentFigure(String compartmentTitle, IMapMode mm) {
		super(compartmentTitle, mm);
	}

	/**
	 * {@inheritDoc}.
	 * After the expansion, the scrollbar visibility is set to {@link ScrollPane#NEVER}.
	 */
	@Override
	public void setExpanded() {
		// independent of the initial scrollbar visibility, expansion sets the visibility to AUTOMATIC
		super.setExpanded();
		scrollPane.setHorizontalScrollBarVisibility(ScrollPane.NEVER);
		scrollPane.setVerticalScrollBarVisibility(ScrollPane.NEVER);
	}

	/**
	 * {@inheritDoc}.
	 * After the expansion, the scrollbar visibility is set to {@link ScrollPane#NEVER}.
	 */
	@Override
	public void expand() {
		// independent of the initial scrollbar visibility, expansion sets the visibility to AUTOMATIC
		super.expand();
		scrollPane.setHorizontalScrollBarVisibility(ScrollPane.NEVER);
		scrollPane.setVerticalScrollBarVisibility(ScrollPane.NEVER);
	}

}
