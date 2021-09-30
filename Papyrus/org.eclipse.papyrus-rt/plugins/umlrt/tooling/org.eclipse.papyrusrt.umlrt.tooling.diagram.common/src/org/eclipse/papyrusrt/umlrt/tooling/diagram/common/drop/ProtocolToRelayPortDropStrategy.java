/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *  Christian W. Damus - bug 510188
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.drop;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.Activator;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;

/**
 * Drop strategy to create a Relay Port when droping a protocol on a capsule
 */
public class ProtocolToRelayPortDropStrategy extends AbstractProtocolToRTPortDropStrategy {

	/**
	 * Constructor.
	 */
	public ProtocolToRelayPortDropStrategy() {
	}

	@Override
	public String getLabel() {
		return "Protocol drop to create Relay Port";
	}

	@Override
	public String getDescription() {
		return "Protocol drop to create Relay Port";
	}

	@Override
	public String getID() {
		return Activator.PLUGIN_ID + ".protocolToRelayPortDrop";//$NON-NLS-1$
	}


	@Override
	protected Command doGetCommand(Request request, EditPart targetEditPart) {
		boolean canHandle = false;

		// can handle it if its on the Edit Part(the border)
		if (!(targetEditPart instanceof DiagramEditPart) && !(targetEditPart instanceof CompartmentEditPart)) {
			canHandle = true;
		}

		return canHandle ? super.doGetCommand(request, targetEditPart) : null;
	}

	@Override
	protected UMLRTPortKind getRTPortKind() {
		return UMLRTPortKind.RELAY;
	}


}
