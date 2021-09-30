/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 510315
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.AbstractInheritanceEditPolicy.INHERITANCE_ROLE;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.NonResizableEditPolicyEx;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.InheritanceEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTSemanticEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;

/**
 * Specific edit part for RT initial states, to override resize edit policy.
 */
public class RTPseudostateInitialEditPart extends PseudostateInitialEditPart implements IRTPseudostateEditPart {

	/**
	 * Constructor.
	 * 
	 * @param view
	 *            the view controlled by this edit part
	 */
	public RTPseudostateInitialEditPart(View view) {
		super(view);
	}

	@Override
	public EditPolicy getPrimaryDragEditPolicy() {
		return new NonResizableEditPolicyEx();
	}

	@Override
	protected void createDefaultEditPolicies() {
		// This needs to be ahead of the edit-policies that process user edit gestures
		installEditPolicy(INHERITANCE_ROLE, new InheritanceEditPolicy());

		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new RTSemanticEditPolicy());
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
}
