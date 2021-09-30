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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies;

import java.util.List;

import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.PapyrusResizableShapeEditPolicy;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RelativePortLocationHelper;

/**
 * A custom resize edit-policy that ensures that the relative positions of ports
 * around the border of a shape are maintained when it is resized.
 */
public class RTResizableShapeEditPolicy extends PapyrusResizableShapeEditPolicy {
	/**
	 * Initializes me.
	 */
	public RTResizableShapeEditPolicy() {
		super();
	}

	/**
	 * Extends the inherited method with special handling of reqize requests,
	 * to ensure that ports on the border of my host edit-part remain in their
	 * relative positions and don't march around the border.
	 */
	@Override
	protected Command getResizeCommand(ChangeBoundsRequest request) {
		Command result = super.getResizeCommand(request);

		if (RequestConstants.REQ_RESIZE.equals(request.getType())
				&& (result != null) && result.canExecute()) {

			// Do we have ports to maintain?
			RelativePortLocationHelper portHelper = memoizeRelativePortLocations();
			Command portUpdate = portHelper.getPortUpdateCommand(request);
			if ((portUpdate != null) && portUpdate.canExecute()) {
				result = result.chain(portUpdate);
			}
		}

		return result;
	}

	/**
	 * Obtains a helper that remembers the relative locations of the ports
	 * around the border of my host edit-part to compute how they should
	 * be moved to remain in their relative locations when my host edit-part
	 * is resized.
	 * 
	 * @return the relative port-location helper (which may not have any ports
	 *         within it)
	 */
	RelativePortLocationHelper memoizeRelativePortLocations() {
		return ((List<?>) getHost().getChildren()).stream()
				.filter(PortEditPart.class::isInstance)
				.map(PortEditPart.class::cast)
				.collect(() -> new RelativePortLocationHelper(getHost()),
						RelativePortLocationHelper::memoizeRelativePortLocation,
						RelativePortLocationHelper::addAllPorts);
	}
}
