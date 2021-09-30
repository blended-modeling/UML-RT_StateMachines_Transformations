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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.papyrus.infra.gmfdiag.canonical.internal.provider.PapyrusCanonicalEditPolicyProvider;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;

/**
 * A provider of the {@link PapyrusRTCanonicalEditPolicy} for edit-parts that need it.
 */
public class PapyrusRTCanonicalEditPolicyProvider extends PapyrusCanonicalEditPolicyProvider {

	public PapyrusRTCanonicalEditPolicyProvider() {
		super();
	}

	@Override
	public void createEditPolicies(EditPart editPart) {
		if (supportsCanonical(editPart) && isInheritable(editPart)) {
			editPart.installEditPolicy(EditPolicyRoles.CANONICAL_ROLE, new PapyrusRTCanonicalEditPolicy());
		} else {
			super.createEditPolicies(editPart);
		}
	}

	/**
	 * Queries whether an edit-part is inheritable.
	 * 
	 * @param editPart
	 *            an edit-part
	 * 
	 * @return {@code true} if the edit-part is an inheritable edit-part or is the shape compartment
	 *         of an inheritable edit-part
	 */
	protected boolean isInheritable(EditPart editPart) {
		return (editPart instanceof IInheritableEditPart)
				|| ((editPart instanceof ShapeCompartmentEditPart) && isInheritable(editPart.getParent()));
	}

}
