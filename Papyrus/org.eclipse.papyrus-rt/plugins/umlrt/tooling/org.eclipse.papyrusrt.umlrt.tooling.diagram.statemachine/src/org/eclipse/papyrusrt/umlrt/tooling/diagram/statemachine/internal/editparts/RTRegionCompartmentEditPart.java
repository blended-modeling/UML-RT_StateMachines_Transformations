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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomRegionCompartmentEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTCompartmentSemanticEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies.RTRegionCompartmentCreationEditPolicy;
import org.eclipse.uml2.uml.Region;

/**
 * UML-RT implementation of the compartment edit-part for inheritable {@link Region}s in state machines.
 */
public class RTRegionCompartmentEditPart extends CustomRegionCompartmentEditPart {

	/**
	 * Initializes me with my notation {@code view}.
	 *
	 * @param view
	 *            my notation view
	 */
	public RTRegionCompartmentEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new RTCompartmentSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new RTRegionCompartmentCreationEditPolicy());
	}

	@Override
	public EObject resolveSemanticElement() {
		return EditPartInheritanceUtils.resolveSemanticElement(getNotationView());
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") java.lang.Class key) {
		return EditPartInheritanceUtils.getAdapter(this, key, super.getAdapter(key));
	}

	@Override
	protected void handleNotificationEvent(Notification notification) {
		EditPartInheritanceUtils.handleNotificationEvent(this, notification,
				super::handleNotificationEvent);
	}

}
