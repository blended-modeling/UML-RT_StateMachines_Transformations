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

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

/**
 * Contain parameters for creation of a node
 */
public class NodeCreationParameters {
	public IGraphicalEditPart targetEditPart;
	public Point targetLocation;
	public IElementType typeToCreate;
	public List<String> closedDiagrams;
	private List<String> openedDiagrams;

	/**
	 * Constructor.
	 */
	public NodeCreationParameters(IGraphicalEditPart targetEditPart, IElementType typeToCreate) {
		this(targetEditPart, new Point(0, 0), typeToCreate);
	}

	/**
	 * Constructor.
	 */
	public NodeCreationParameters(IGraphicalEditPart targetEditPart, Point targetLocation, IElementType typeToCreate) {
		this(targetEditPart, targetLocation, typeToCreate, Collections.emptyList(), Collections.emptyList());
	}

	/**
	 * Constructor.
	 */
	public NodeCreationParameters(IGraphicalEditPart targetEditPart, Point targetLocation, IElementType typeToCreate, List<String> closedDiagrams, List<String> openedDiagrams) {
		this.targetEditPart = targetEditPart;
		this.targetLocation = targetLocation;
		this.typeToCreate = typeToCreate;
		this.closedDiagrams = closedDiagrams;
		this.openedDiagrams = openedDiagrams;
	}

	/**
	 * @return
	 */
	public EObject getTargetElement() {
		return targetEditPart.resolveSemanticElement();
	}
}