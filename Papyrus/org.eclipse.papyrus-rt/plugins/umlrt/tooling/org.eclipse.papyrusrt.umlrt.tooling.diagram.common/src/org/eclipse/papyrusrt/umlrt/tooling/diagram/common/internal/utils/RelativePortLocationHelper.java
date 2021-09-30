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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;

/**
 * An utility that computes relative locations of ports on the borders of shapes
 * and provides commands to establish correspondent locations on other shapes.
 */
public class RelativePortLocationHelper {

	private final IGraphicalEditPart editPart;

	private final Map<IGraphicalEditPart, RelativePortLocation> ports = new HashMap<>();

	/**
	 * Initializes me with the edit-part that I help.
	 * 
	 * @param editPart
	 *            my edit-part
	 */
	public RelativePortLocationHelper(EditPart editPart) {
		super();

		this.editPart = (IGraphicalEditPart) editPart;
	}

	/**
	 * Obtains the bounds of an edit-part if it is fully initialized, otherwise
	 * computes it from its figure's preferred size.
	 * 
	 * @param editPart
	 *            an edit-part
	 * @return best-effort bounds of it, relative to its parent edit-part
	 */
	private Rectangle getBounds(IGraphicalEditPart editPart) {
		// Prefer the notation layout constraint because it usually already
		// has the location (size usually defaulted) and because it is relative
		// to the parent space
		Rectangle result;

		Bounds constraint = getNotationBounds(editPart);
		if (constraint == null) {
			// No notation bounds? Odd
			result = editPart.getFigure().getBounds();
		} else if ((constraint.getWidth() <= 0) || (constraint.getHeight() <= 0)) {
			// We have default size constraint. Infer from the figure, if possible
			Dimension size = editPart.getFigure().getSize();
			result = new Rectangle(constraint.getX(), constraint.getY(),
					size.width(), size.height());
		} else {
			result = new Rectangle(constraint.getX(), constraint.getY(),
					constraint.getWidth(), constraint.getHeight());
		}

		if ((result.width() <= 0) || (result.height() <= 0)) {
			// We have default dimensions (the figure has not yet been laid out).
			// Compute the preferred size, which is anticipated to be what the
			// eventual layout will be
			result = new Rectangle(result.getLocation(), editPart.getFigure().getPreferredSize());
		}

		return result;
	}

	private Bounds getNotationBounds(IGraphicalEditPart gep) {
		Bounds result = null;

		Node node = TypeUtils.as(gep.getNotationView(), Node.class);
		if (node != null) {
			result = TypeUtils.as(node.getLayoutConstraint(), Bounds.class);
		}

		return result;
	}

	/**
	 * Obtains the visual bounds of my host edit-part.
	 * 
	 * @return my bounds
	 */
	private Rectangle getHostBounds() {
		return getBounds(editPart);
	}

	/**
	 * Computes and stores the relative location of a view corresponding to
	 * a {@code port} on the border of my host edit-part's view, on the
	 * border of that correspondent's parent edit-part.
	 * 
	 * @param port
	 *            a port child of my edit-part
	 * @param definingPortBounds
	 *            the bounds of the defining port on the defining capsule structure diagram
	 * @param capsuleBounds
	 *            the corresponding port's capsule's bounds
	 */
	public void memoizeRelativePortLocation(EditPart port, Bounds definingPortBounds, Bounds capsuleBounds) {
		RelativePortLocation location = RelativePortLocation.of(definingPortBounds, capsuleBounds);
		if (location != null) {
			ports.put((IGraphicalEditPart) port, location);
		}
	}

	/**
	 * Computes and stores the relative location of a {@code port} view on
	 * the border of my host edit-part's view.
	 * 
	 * @param port
	 *            a port child of my edit-part
	 */
	public void memoizeRelativePortLocation(EditPart port) {
		IGraphicalEditPart editPart = (IGraphicalEditPart) port;
		RelativePortLocation location = RelativePortLocation.of(getBounds(editPart), getHostBounds());
		if (location != null) {
			ports.put(editPart, location);
		}
	}

	/**
	 * Adds all of the memoized ports of an{@code other} helper to me.
	 * 
	 * @param other
	 *            another helper
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code other} helper is not
	 *             owned by the same edit-part as I
	 */
	public void addAllPorts(RelativePortLocationHelper other) {
		if (other.editPart != this.editPart) {
			throw new IllegalArgumentException("other is owned by a different edit-part"); //$NON-NLS-1$
		}

		this.ports.putAll(other.ports);
	}

	/**
	 * Obtains a command that updates the locations of all of my memoized ports around
	 * my edit-part's shape's border to account for the change in my edit-part's
	 * bounds described by the given {@code request}.
	 * 
	 * @param request
	 *            a request to change the bounds of my host edit-part's shape
	 * @return the command that updates my memoized ports' locations, or
	 *         {@code null} if I have no memoized ports or they cannot be moved
	 */
	public Command getPortUpdateCommand(ChangeBoundsRequest request) {
		return getPortUpdateCommand(request.getTransformedRectangle(getHostBounds()));
	}

	/**
	 * Obtains a command that updates the locations of all of my memoized ports around
	 * my edit-part's shape's border to account for my edit-part's current shape.
	 * 
	 * @return the command that updates my memoized ports' locations, or
	 *         {@code null} if I have no memoized ports or they cannot be moved
	 */
	public Command getPortUpdateCommand() {
		return getPortUpdateCommand(getHostBounds());
	}

	/**
	 * Obtains a command that updates the locations of all of my memoized ports around
	 * my edit-part's shape's border to fit around the specified {@code bounds}.
	 * 
	 * @poaram bounds the rectangle around which to distribute my ports
	 * 
	 * @return the command that updates my memoized ports' locations, or
	 *         {@code null} if I have no memoized ports or they cannot be moved
	 */
	private Command getPortUpdateCommand(Rectangle bounds) {
		CompoundCommand result = new CompoundCommand("Update Port Locations");

		ports.forEach((port, location) -> {
			Rectangle portBounds = getBounds(port);
			Point moveTo = location.applyTo(bounds, portBounds.getSize());

			ICommand move = new SetBoundsCommand(
					editPart.getEditingDomain(),
					"Place Port",
					new EObjectAdapter(port.getNotationView()),
					moveTo);

			result.add(new ICommandProxy(move));
		});

		return result.unwrap();
	}

	/**
	 * Forgets all of my ports, making me ready for another round.
	 */
	public void reset() {
		ports.clear();
	}

	/**
	 * Obtains an unmodifiable collection of the port edit-parts whose
	 * relative locations I have memoized.
	 * 
	 * @return my port edit-parts
	 */
	public Collection<IGraphicalEditPart> getPortEditParts() {
		return Collections.unmodifiableSet(ports.keySet());
	}
}
