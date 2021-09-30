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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.ExternalReferenceEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IRTPortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPropertyPartEditPart;

/**
 * Provider of an external-reference edit policy that extends the Papyrus implementation
 * to support inherited elements.
 */
public class RTExternalReferenceEditPolicyProvider extends AbstractProvider implements IEditPolicyProvider {

	public RTExternalReferenceEditPolicyProvider() {
		super();
	}

	@Override
	public boolean provides(IOperation operation) {
		CreateEditPoliciesOperation epOperation = (CreateEditPoliciesOperation) operation;

		try {
			ServicesRegistry registry = ServiceUtilsForEditPart.getInstance().getServiceRegistry(epOperation.getEditPart());
			if (registry == null) {
				return false;
			}

			// Do I recognize this edit-part?
			EditPart editPart = epOperation.getEditPart();
			return (editPart instanceof RTPortEditPart)
					|| (editPart instanceof RTPropertyPartEditPart);
		} catch (ServiceException e) {
			return false;
		}
	}

	@Override
	public void createEditPolicies(EditPart editPart) {
		if (editPart instanceof IRTPortEditPart && ((IRTPortEditPart) editPart).isPortOnPart()) {
			// We will never need the decoration on these because of course the parent
			// view is different to the parent element's view
			editPart.removeEditPolicy(ExternalReferenceEditPolicy.EDIT_POLICY_ROLE);
		} else {
			editPart.installEditPolicy(ExternalReferenceEditPolicy.EDIT_POLICY_ROLE,
					new RTExternalReferenceEditPolicy());
		}
	}

}
