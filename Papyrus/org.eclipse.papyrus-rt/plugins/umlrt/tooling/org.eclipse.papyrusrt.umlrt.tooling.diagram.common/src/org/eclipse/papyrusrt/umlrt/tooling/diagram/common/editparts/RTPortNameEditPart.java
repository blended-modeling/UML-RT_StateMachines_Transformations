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

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.AbstractInheritanceEditPolicy.INHERITANCE_ROLE;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IndirectMaskLabelEditPolicy;
import org.eclipse.papyrus.uml.diagram.composite.custom.edit.parts.CustomPortNameEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.InheritanceEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTMaskLabelEditPolicy;

/**
 * Custom name label edit part for ports to exclude certain multiplicity
 * values from display.
 */
public class RTPortNameEditPart extends CustomPortNameEditPart implements IInheritableEditPart {

	/**
	 * Initializes me with my notation view.
	 *
	 * @param view
	 *            my view
	 */
	public RTPortNameEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		// This needs to be ahead of the edit-policies that process user edit gestures
		installEditPolicy(INHERITANCE_ROLE, new InheritanceEditPolicy());

		super.createDefaultEditPolicies();

		// Override the label presentation
		installEditPolicy(IndirectMaskLabelEditPolicy.INDRIRECT_MASK_MANAGED_LABEL,
				new RTMaskLabelEditPolicy());
	}

	@Override
	public EObject resolveSemanticElement() {
		EObject result;

		View view = getNotationView();
		if ((view == null) || !view.isSetElement()) {
			result = ((IGraphicalEditPart) getParent()).resolveSemanticElement();
		} else {
			result = super.resolveSemanticElement();
		}

		return result;
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") java.lang.Class key) {
		Object result = super.getAdapter(key);

		if (result instanceof EObject) {
			View view = getNotationView();
			if (result == view.getElement()) {
				result = resolveSemanticElement();
			}
		}

		return result;
	}

}
