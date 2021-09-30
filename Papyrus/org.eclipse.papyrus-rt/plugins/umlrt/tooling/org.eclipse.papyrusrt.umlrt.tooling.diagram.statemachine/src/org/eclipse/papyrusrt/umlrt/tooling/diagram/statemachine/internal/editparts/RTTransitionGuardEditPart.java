/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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
import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils.isVisibilityForced;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomTransitionGuardEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.InheritanceEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.uml2.uml.Transition;

/**
 * Inheritance-aware edit-part for the transition guard label.
 */
public class RTTransitionGuardEditPart extends CustomTransitionGuardEditPart implements IStateMachineInheritableEditPart {

	public RTTransitionGuardEditPart(View view) {
		super(view);
	}


	@Override
	protected void createDefaultEditPolicies() {
		// This needs to be ahead of the edit-policies that process user edit gestures
		installEditPolicy(INHERITANCE_ROLE, new InheritanceEditPolicy());

		super.createDefaultEditPolicies();
	}

	@Override
	public EObject resolveSemanticElement() {
		return EditPartInheritanceUtils.resolveSemanticElement(this, super.resolveSemanticElement());
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") java.lang.Class key) {
		return EditPartInheritanceUtils.getAdapter(this, key, super.getAdapter(key));
	}

	/**
	 * Default to visible if there is any guard condition to show.
	 */
	@Override
	protected void refreshVisibility() {
		// If visibility is hard coded to be visible or not, do not continue
		if (!(getModel() instanceof View)) {
			return;
		}

		View view = (View) getModel();
		if (isVisibilityForced(view)) {
			setVisibility(view.isVisible());
		} else if (isInherited()) {
			// Do as in the parent diagram
			View parentView = getRedefinedView();
			if (parentView != null) {
				setVisibility(parentView.isVisible());
			}
		} else {
			// let the fact that internal transitions exist or not to toggle visibility
			EObject semantic = resolveSemanticElement();
			if (semantic instanceof Transition) {
				setVisibility(view.isVisible() && (((Transition) semantic).getGuard() != null));
			}
		}
	}

}
