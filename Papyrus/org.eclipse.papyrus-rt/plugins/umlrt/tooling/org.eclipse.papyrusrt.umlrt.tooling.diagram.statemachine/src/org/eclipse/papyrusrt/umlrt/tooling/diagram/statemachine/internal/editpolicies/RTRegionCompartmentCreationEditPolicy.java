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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.policies.CustomRegionCompartmentCreationEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;

/**
 * An inheritance-aware specialization of the custom creation edit-policy for region compartments.
 */
public class RTRegionCompartmentCreationEditPolicy extends CustomRegionCompartmentCreationEditPolicy {

	public RTRegionCompartmentCreationEditPolicy() {
		super();
	}

	@Override
	protected Command getCreateElementAndViewCommand(CreateViewAndElementRequest request) {
		return EditPartInheritanceUtils.getCreateElementAndViewCommand((IGraphicalEditPart) getHost(),
				request, this::getCreateCommand);
	}

	// TODO: Override reparent to use correct semantic container
}
