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

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.AbstractInheritanceEditPolicy.INHERITANCE_ROLE;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.RoundedRectangleNodePlateFigure;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomPseudostateExitPointEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.InheritanceEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies.BorderItemNotResizableEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies.RTPseudostateSemanticEditPolicy;

/**
 * RT specialization of the exit-point edit part.
 */
public class RTPseudostateExitPointEditPart extends CustomPseudostateExitPointEditPart implements IRTPseudostateEditPart {

	/**
	 * Initializes me with my notation view.
	 *
	 * @param view
	 *            my notation view
	 */
	public RTPseudostateExitPointEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		// This needs to be ahead of the edit-policies that process user edit gestures
		installEditPolicy(INHERITANCE_ROLE, new InheritanceEditPolicy());

		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new RTPseudostateSemanticEditPolicy());
	}

	@Override
	public EObject resolveSemanticElement() {
		return EditPartInheritanceUtils.resolveSemanticElement(this, super.resolveSemanticElement());
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") java.lang.Class key) {
		return EditPartInheritanceUtils.getAdapter(this, key, super.getAdapter(key));
	}

	@Override
	protected void refreshForegroundColor() {
		super.refreshForegroundColor();

		// We present inherited pseudostates in a lighter colour
		UMLRTEditPartUtils.updateForegroundColor(this, getPrimaryShape());
	}

	@Override
	protected void refreshBackgroundColor() {
		super.refreshBackgroundColor();

		// We present inherited pseudostates in a lighter colour
		UMLRTEditPartUtils.updateBackgroundColor(this, getPrimaryShape());
	}

	@Override
	protected NodeFigure createNodePlate() {
		Dimension defaultSize = getDefaultSize();
		RoundedRectangleNodePlateFigure result = new RoundedRectangleNodePlateFigure(defaultSize.width(), defaultSize.height());
		return result;
	}

	@Override
	protected void refreshBounds() {
		super.refreshBounds();

		IBorderItemLocator locator = getBorderItemLocator();
		if (locator != null) {
			locator.relocate(getFigure());
		}
	}

	@Override
	public EditPolicy getPrimaryDragEditPolicy() {
		return new BorderItemNotResizableEditPolicy();
	}

	@Override
	public void installEditPolicy(Object key, EditPolicy editPolicy) {
		if (EditPolicy.PRIMARY_DRAG_ROLE.equals(key)) {
			// prevent its parents from overriding this policy
			if (editPolicy instanceof BorderItemNotResizableEditPolicy) {
				super.installEditPolicy(key, editPolicy);
			}
		} else {
			super.installEditPolicy(key, editPolicy);
		}
	}
}
