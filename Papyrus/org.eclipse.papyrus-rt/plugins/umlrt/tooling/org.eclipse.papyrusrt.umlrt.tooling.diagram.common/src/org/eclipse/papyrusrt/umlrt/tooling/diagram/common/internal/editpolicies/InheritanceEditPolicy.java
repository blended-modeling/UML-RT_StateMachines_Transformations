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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;

/**
 * An edit policy that manages the location and size and other graphical
 * characteristics of inherited elements in a Capsule Composite Structure Diagram.
 */
public class InheritanceEditPolicy extends AbstractInheritanceEditPolicy<Node> {
	public InheritanceEditPolicy() {
		super(Node.class);
	}

	@Override
	protected boolean shouldListen(Node view, EReference containment) {
		return containment == NotationPackage.Literals.NODE__LAYOUT_CONSTRAINT;
	}

	@Override
	protected Command update(EObject inherited) {
		Command result = null;

		if (inherited instanceof Bounds) {
			ICommand bounds = getUpdateCommand((Bounds) inherited);
			if (bounds != null) {
				result = GMFtoEMFCommandWrapper.wrap(bounds);
			}
		} else if (inherited instanceof Location) {
			// As for labels, for example
			ICommand location = getUpdateCommand((Location) inherited);
			if (location != null) {
				result = GMFtoEMFCommandWrapper.wrap(location);
			}
		}

		return result;
	}

	@Override
	protected boolean isVisualEdit(Request request) {
		return RequestConstants.REQ_MOVE.equals(request.getType())
				|| RequestConstants.REQ_RESIZE.equals(request.getType());
	}

	ICommand getUpdateCommand(Bounds bounds) {
		IGraphicalEditPart host = (IGraphicalEditPart) getHost();
		Node node = getView();

		TransactionalEditingDomain domain = host.getEditingDomain();
		ICommand result = null;

		if (!EcoreUtil.equals(bounds, node.getLayoutConstraint())) {
			result = new SetBoundsCommand(domain, "Follow inherited element",
					new EObjectAdapter(node),
					new Rectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));
		}

		return ((result != null) && result.canExecute()) ? result : null;
	}

	ICommand getUpdateCommand(Location location) {
		IGraphicalEditPart host = (IGraphicalEditPart) getHost();
		Node node = getView();

		TransactionalEditingDomain domain = host.getEditingDomain();
		ICommand result = null;

		if (!EcoreUtil.equals(location, node.getLayoutConstraint())) {
			result = new SetBoundsCommand(domain, "Follow inherited element",
					new EObjectAdapter(node),
					new Point(location.getX(), location.getY()));
		}

		return ((result != null) && result.canExecute()) ? result : null;
	}
}
