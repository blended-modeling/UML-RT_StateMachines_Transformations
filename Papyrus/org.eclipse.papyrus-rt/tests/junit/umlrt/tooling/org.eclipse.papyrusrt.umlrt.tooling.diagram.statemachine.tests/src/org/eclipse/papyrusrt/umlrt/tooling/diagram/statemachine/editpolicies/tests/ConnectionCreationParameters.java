/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.editpolicies.tests;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.uml2.uml.Element;

/**
 * Contain parameters for creation of the connexion
 */
public class ConnectionCreationParameters {
	public Element container;
	public IGraphicalEditPart sourceEditPart;
	public IGraphicalEditPart targetEditPart;
	public Point sourceLocation;
	public Point targetLocation;

	/**
	 * Constructor.
	 */
	public ConnectionCreationParameters(Element container, IGraphicalEditPart sourceEditPart, IGraphicalEditPart targetEditPart) {
		this(container, sourceEditPart, targetEditPart, new Point(0, 0), new Point(0, 0));
	}

	/**
	 * Constructor.
	 */
	public ConnectionCreationParameters(Element container, IGraphicalEditPart sourceEditPart, IGraphicalEditPart targetEditPart, Point sourceLocation, Point targetLocation) {
		this.container = container;
		this.sourceEditPart = sourceEditPart;
		this.targetEditPart = targetEditPart;
		this.sourceLocation = sourceLocation;
		this.targetLocation = targetLocation;
	}

	/**
	 * @return
	 */
	public Element getSource() {
		return (Element) sourceEditPart.resolveSemanticElement();
	}

	public Element getTarget() {
		return (Element) targetEditPart.resolveSemanticElement();
	}
}