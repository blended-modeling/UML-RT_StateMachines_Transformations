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

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrusrt.umlrt.system.profile.util.SystemElementsUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.Activator;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Collaboration;

/**
 * Drop strategy to create a Internal Behavior Port when droping a protocol on a capsule.
 */
public class ProtocolToInternalBehaviorPortDropStrategy extends AbstractProtocolToRTPortDropStrategy {


	protected GraphicalEditPart dropTarget;

	/**
	 * Constructor.
	 */
	public ProtocolToInternalBehaviorPortDropStrategy() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getLabel()
	 */
	@Override
	public String getLabel() {
		return "Protocol drop to create Internal Behavior Port";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Protocol drop to create Internal Behavior Port";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getID()
	 */
	@Override
	public String getID() {
		return Activator.PLUGIN_ID + ".protocolToInternalBehaviorPortDrop";//$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.drop.AbstractProtocolToRTPortDropStrategy#doGetCommand(org.eclipse.gef.Request, org.eclipse.gef.EditPart)
	 */
	@Override
	protected Command doGetCommand(Request request, EditPart targetEditPart) {
		// initDefaultStrategy();
		boolean canHandle = false;
		if (targetEditPart instanceof GraphicalEditPart) {
			dropTarget = (GraphicalEditPart) targetEditPart;

			// In case of Compartment return the Parent'command (ClassComposite)
			if ((targetEditPart instanceof CompartmentEditPart)) {

				targetEditPart = targetEditPart.getParent();
				if (request instanceof DropObjectsRequest && !getSourceEObjects(request).isEmpty()) {
					List<Collaboration> droppedProtocols = getSourceEObjects(request).stream().filter(Collaboration.class::isInstance)
							.map(Collaboration.class::cast)
							.collect(Collectors.toList());
					if (!droppedProtocols.isEmpty()) {
						canHandle = canDrop(droppedProtocols);
					}
				}

			}
		}

		return canHandle ? super.doGetCommand(request, targetEditPart) : null;
	}

	@Override
	protected UMLRTPortKind getRTPortKind() {
		return UMLRTPortKind.INTERNAL_BEHAVIOR;
	}

	private boolean canDrop(List<Collaboration> droppedObjects) {
		return !droppedObjects.stream().anyMatch(SystemElementsUtils::isBaseProtocol);
	}

}
