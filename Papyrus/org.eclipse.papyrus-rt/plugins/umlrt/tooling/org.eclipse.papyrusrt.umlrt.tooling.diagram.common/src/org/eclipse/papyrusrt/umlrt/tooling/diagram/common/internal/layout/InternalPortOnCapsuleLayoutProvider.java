/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 510188
 *   
 * 
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.layout;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IRTPortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.ILocationIterator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.LinearIterator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;

/**
 * A provider of layout for the internal ports on a capsule frame in its
 * composite structure diagram.
 * used to create SAP port (typed by system protocols) from the new child menu
 */
public class InternalPortOnCapsuleLayoutProvider extends AbstractPortOnCapsuleLayoutProvider {


	public InternalPortOnCapsuleLayoutProvider() {
		super();
	}

	@Override
	public boolean isValidPort(Node node) {
		// valid Port are internal ports
		Optional<UMLRTPortKind> kind = Optional.ofNullable(RTPortUtils.getPortKind(
				TypeUtils.as(node.getElement(), Port.class)));
		return kind.map(UMLRTPortKind::isInternal).orElse(false);
	}

	@Override
	public Command getPortLayoutCommand(IGraphicalEditPart capsule, List<IGraphicalEditPart> ports) {
		CompositeCommand result = new CompositeCommand("Arrange Ports");

		int stride = IRTPortEditPart.getDefaultSize(false).width * 8 / 3;

		Predicate<Point> hitPort = pt -> UMLRTEditPartUtils.findChildAt(capsule, pt)
				.map(IGraphicalEditPart::getNotationView)
				.map(View::getElement)
				.filter(Property.class::isInstance)
				.isPresent();

		// we did not have a proposed location to call RTPortPositionLocator::getPreferredLocation(Rectangle proposedLocation)
		ILocationIterator locationIterator = LinearIterator.from(new Point(0, 0), hitPort);

		for (IGraphicalEditPart next : ports) {
			result.add(new SetBoundsCommand(capsule.getEditingDomain(), result.getLabel(),
					new EObjectAdapter(next.getNotationView()),
					locationIterator.next(stride)));
		}

		return result.isEmpty() ? null : new ICommandProxy(result);
	}


}
