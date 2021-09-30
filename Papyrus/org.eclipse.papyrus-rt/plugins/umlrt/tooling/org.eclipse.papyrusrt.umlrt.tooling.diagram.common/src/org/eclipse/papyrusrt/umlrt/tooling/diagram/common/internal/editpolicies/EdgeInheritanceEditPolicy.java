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

import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.SetConnectionAnchorsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.RoutingStyle;
import org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;

import com.google.common.collect.ImmutableSet;

/**
 * An edit policy that manages the location and size and other graphical
 * characteristics of inherited elements in a Capsule Composite Structure Diagram.
 */
public class EdgeInheritanceEditPolicy extends AbstractInheritanceEditPolicy<Edge> {
	private static final Set<EReference> CONTAINMENTS = ImmutableSet.of(
			NotationPackage.Literals.EDGE__SOURCE_ANCHOR,
			NotationPackage.Literals.EDGE__TARGET_ANCHOR,
			NotationPackage.Literals.EDGE__BENDPOINTS);

	public EdgeInheritanceEditPolicy() {
		super(Edge.class);
	}

	@Override
	protected boolean shouldListen(Edge view, EReference containment) {
		return CONTAINMENTS.contains(containment);
	}

	@Override
	protected Command update(EObject inherited) {
		ICommand result = null;

		if (inherited instanceof IdentityAnchor) {
			IdentityAnchor anchor = (IdentityAnchor) inherited;
			result = getUpdateCommand(anchor,
					anchor.eContainmentFeature() == NotationPackage.Literals.EDGE__TARGET_ANCHOR);
		} else if (inherited instanceof RelativeBendpoints) {
			RelativeBendpoints bendpoints = (RelativeBendpoints) inherited;
			result = getUpdateCommand(bendpoints);
		} else if (inherited instanceof RoutingStyle) {
			RoutingStyle routing = (RoutingStyle) inherited;
			result = getUpdateCommand(routing);
		}

		return (result == null) ? null : GMFtoEMFCommandWrapper.wrap(result);
	}

	@Override
	protected boolean isVisualEdit(Request request) {
		return RequestConstants.REQ_CREATE_BENDPOINT.equals(request.getType())
				|| RequestConstants.REQ_MOVE_BENDPOINT.equals(request.getType());
	}

	ICommand getUpdateCommand(IdentityAnchor anchor, boolean isTarget) {
		IGraphicalEditPart host = (IGraphicalEditPart) getHost();
		Edge edge = getView();

		TransactionalEditingDomain domain = host.getEditingDomain();
		ICommand result = null;

		if (!EcoreUtil.equals(anchor, isTarget ? edge.getTargetAnchor() : edge.getSourceAnchor())) {
			SetConnectionAnchorsCommand command = new SetConnectionAnchorsCommand(domain, "Follow inherited anchor");
			command.setEdgeAdaptor(new EObjectAdapter(edge));
			if (isTarget) {
				command.setNewTargetTerminal(anchor.getId());
			} else {
				command.setNewSourceTerminal(anchor.getId());
			}
			result = command;
		}

		return ((result != null) && result.canExecute()) ? result : null;
	}

	ICommand getUpdateCommand(RelativeBendpoints bendpoints) {
		IGraphicalEditPart host = (IGraphicalEditPart) getHost();
		Edge edge = getView();

		TransactionalEditingDomain domain = host.getEditingDomain();
		Command result = null;

		if (!EcoreUtil.equals(bendpoints, edge.getBendpoints())) {
			result = SetCommand.create(domain, edge.getBendpoints(), NotationPackage.Literals.RELATIVE_BENDPOINTS__POINTS, bendpoints.getPoints());
		}

		return ((result != null) && result.canExecute())
				? EMFtoGMFCommandWrapper.wrap(result)
				: null;
	}

	ICommand getUpdateCommand(RoutingStyle routing) {
		IGraphicalEditPart host = (IGraphicalEditPart) getHost();
		Edge edge = getView();

		TransactionalEditingDomain domain = host.getEditingDomain();
		Command result = null;

		RoutingStyle myRouting = (RoutingStyle) edge.getStyle(NotationPackage.Literals.ROUTING_STYLE);
		if (myRouting != null) {
			CompoundCommand cc = new CompoundCommand();

			if (myRouting.getRouting() != routing.getRouting()) {
				cc.append(SetCommand.create(domain, myRouting, NotationPackage.Literals.ROUTING_STYLE__ROUTING, routing.getRouting()));
			}
			if (myRouting.getJumpLinkStatus() != routing.getJumpLinkStatus()) {
				cc.append(SetCommand.create(domain, myRouting, NotationPackage.Literals.ROUTING_STYLE__JUMP_LINK_STATUS, routing.getJumpLinkStatus()));
			}
			if (myRouting.getJumpLinkType() != routing.getJumpLinkType()) {
				cc.append(SetCommand.create(domain, myRouting, NotationPackage.Literals.ROUTING_STYLE__JUMP_LINK_TYPE, routing.getJumpLinkType()));
			}
			if (myRouting.isJumpLinksReverse() != routing.isJumpLinksReverse()) {
				cc.append(SetCommand.create(domain, myRouting, NotationPackage.Literals.ROUTING_STYLE__JUMP_LINKS_REVERSE, routing.isJumpLinksReverse()));
			}
			if (myRouting.isAvoidObstructions() != routing.isAvoidObstructions()) {
				cc.append(SetCommand.create(domain, myRouting, NotationPackage.Literals.ROUTING_STYLE__AVOID_OBSTRUCTIONS, routing.isAvoidObstructions()));
			}
			if (myRouting.isClosestDistance() != routing.isClosestDistance()) {
				cc.append(SetCommand.create(domain, myRouting, NotationPackage.Literals.ROUTING_STYLE__CLOSEST_DISTANCE, routing.isClosestDistance()));
			}
			if (myRouting.getRoundedBendpointsRadius() != routing.getRoundedBendpointsRadius()) {
				cc.append(SetCommand.create(domain, myRouting, NotationPackage.Literals.ROUTING_STYLE__ROUNDED_BENDPOINTS_RADIUS, routing.getRoundedBendpointsRadius()));
			}
			if (myRouting.getSmoothness() != routing.getSmoothness()) {
				cc.append(SetCommand.create(domain, myRouting, NotationPackage.Literals.ROUTING_STYLE__SMOOTHNESS, routing.getSmoothness()));
			}

			result = cc.unwrap();
		}

		return ((result != null) && result.canExecute())
				? EMFtoGMFCommandWrapper.wrap(result)
				: null;
	}
}
