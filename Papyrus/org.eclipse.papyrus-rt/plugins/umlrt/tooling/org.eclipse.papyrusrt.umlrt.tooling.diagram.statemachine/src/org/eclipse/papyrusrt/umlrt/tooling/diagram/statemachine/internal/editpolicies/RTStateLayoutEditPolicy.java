/*****************************************************************************
 * Copyright (c) 2017 EclipseSource GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Remi Schnekenburger - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.ExternalLabelPrimaryDragRoleEditPolicy;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry;

/**
 * Overrides the specific layout edit policy from Papyrus to avoid issues with layout of entry/Exit points.
 */
public class RTStateLayoutEditPolicy extends LayoutEditPolicy {

	/**
	 * Constructor.
	 */
	public RTStateLayoutEditPolicy() {
		super();
	}

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		View childView = (View) child.getModel();
		switch (UMLVisualIDRegistry.getVisualID(childView)) {
		case PseudostateEntryPointFloatingLabelEditPart.VISUAL_ID:
		case PseudostateExitPointFloatingLabelEditPart.VISUAL_ID:
		case PseudostateEntryPointStereotypeEditPart.VISUAL_ID:
		case PseudostateExitPointStereotypeEditPart.VISUAL_ID:
			return new ExternalLabelPrimaryDragRoleEditPolicy();
		default:
			// empty
		}
		EditPolicy result = child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
		if (result == null) {
			result = new NonResizableEditPolicy();
		}
		return result;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}

	@Override
	protected Command getMoveChildrenCommand(Request request) {
		return null;
	}

}
