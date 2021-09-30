/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.layout;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IRTPortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.ILocationIterator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.PortLayoutIterator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Port;

/**
 * A provider of layout for the external ports on a capsule frame in its
 * composite structure diagram.
 */
public class PortOnCapsuleFrameLayoutProvider extends AbstractPortOnCapsuleLayoutProvider {

	/**
	 * Initializes me.
	 */
	public PortOnCapsuleFrameLayoutProvider() {
		super();
	}



	@Override
	public boolean isValidPort(Node node) {
		// valid Port are external ports
		Optional<UMLRTPortKind> kind = Optional.ofNullable(RTPortUtils.getPortKind(
				TypeUtils.as(node.getElement(), Port.class)));
		return kind.map(UMLRTPortKind::isExternal).orElse(false);
	}

	/**
	 * Obtains a command that lays out {@code ports} around a {@code capsule}
	 * <ul>
	 * <li>down the left side, starting from the middle, then</li>
	 * <li>down the right side, starting from the middle, then</li>
	 * <li>up the left side, starting from the middle, then</li>
	 * <li>up the right side, starting from the middle, then</li>
	 * <li>across the bottom, starting from the left, then</li>
	 * <li>across the top, starting from the left, then</li>
	 * <li>wherever</li>
	 * </ul>
	 * 
	 * @param capsule
	 *            a capsule frame
	 * @param ports
	 *            ports on the capsule
	 * 
	 * @return the layout command, or {@code null} if none
	 */
	@Override
	public Command getPortLayoutCommand(IGraphicalEditPart capsule, List<IGraphicalEditPart> ports) {
		CompositeCommand result = new CompositeCommand("Arrange Ports");

		View frame = capsule.getNotationView();
		if (frame instanceof Node) {
			Bounds frameBounds = (Bounds) ((Node) frame).getLayoutConstraint();
			Rectangle frameRect = new Rectangle(0, 0, frameBounds.getWidth(), frameBounds.getHeight());
			Dimension portSize = IRTPortEditPart.getDefaultSize(false);

			// The 7/5 stride matches RSA visually, which is important for layout
			// of imported diagrams
			double stride = portSize.width() * 7.0 / 5.0;

			Predicate<Point> hitPort = pt -> UMLRTEditPartUtils.findChildAt(capsule, pt)
					.map(IGraphicalEditPart::getNotationView)
					.map(View::getElement)
					.filter(Port.class::isInstance)
					.isPresent();

			// An offset accounting for centering the ports on the iterator's locations
			Dimension offset = portSize.getScaled(0.5).negate();

			ILocationIterator locationIterator = PortLayoutIterator.on(frameRect, hitPort);
			for (IGraphicalEditPart next : ports) {
				result.add(new SetBoundsCommand(capsule.getEditingDomain(), result.getLabel(),
						new EObjectAdapter(next.getNotationView()),
						locationIterator.next(stride).getTranslated(offset)));
			}

		}

		return result.isEmpty() ? null : new ICommandProxy(result);
	}

}
