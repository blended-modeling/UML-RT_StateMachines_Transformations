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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IndirectMaskLabelEditPolicy;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PropertyPartNameEditPartCN;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTMaskLabelEditPolicy;

/**
 * Work-around for the non-extensibility of icon image provision for
 * part shapes.
 */
public class RTPropertyPartNameEditPartCN extends PropertyPartNameEditPartCN {

	/**
	 * Initializes me with my notation view.
	 *
	 * @param view
	 *            my view
	 */
	public RTPropertyPartNameEditPartCN(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
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
