/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.layout;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.AbstractLayoutEditPartProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNode;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNodeOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutType;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.uml2.uml.Port;

/**
 * A provider of layout for the ports on a capsule frame in its
 * composite structure diagram.
 */
public abstract class AbstractPortOnCapsuleLayoutProvider extends AbstractLayoutEditPartProvider {

	/**
	 * Initializes me.
	 */
	public AbstractPortOnCapsuleLayoutProvider() {
		super();
	}

	@Override
	public boolean provides(IOperation operation) {
		boolean result = false;

		if (operation instanceof ILayoutNodeOperation) {
			ILayoutNodeOperation layout = (ILayoutNodeOperation) operation;
			if (LayoutType.DEFAULT.equals(layout.getLayoutHint().getAdapter(String.class))) {
				IGraphicalEditPart container = layout.getLayoutHint().getAdapter(IGraphicalEditPart.class);
				EObject maybeCapsule = container.resolveSemanticElement();

				// Is it a capsule structure diagram frame?
				if ((maybeCapsule instanceof org.eclipse.uml2.uml.Class)
						&& CapsuleUtils.isCapsule((org.eclipse.uml2.uml.Class) maybeCapsule)
						&& ClassCompositeEditPart.VISUAL_ID.equals(container.getNotationView().getType())) {

					@SuppressWarnings("unchecked")
					List<ILayoutNode> nodes = layout.getLayoutNodes();
					result = nodes.stream()
							.map(ILayoutNode::getNode)
							.allMatch(this::isValidPort);
				}
			}
		}

		return result;
	}

	abstract protected boolean isValidPort(Node node);

	@Override
	public Command layoutEditParts(GraphicalEditPart containerEditPart, IAdaptable layoutHint) {
		IGraphicalEditPart capsule = layoutHint.getAdapter(IGraphicalEditPart.class);
		List<IGraphicalEditPart> ports = getPorts(capsule.getChildren());

		return getPortLayoutCommand(capsule, ports);
	}

	@SuppressWarnings("unchecked")
	private final List<IGraphicalEditPart> getPorts(List<?> editParts) {
		return ((List<EditPart>) editParts).stream()
				.filter(IGraphicalEditPart.class::isInstance).map(IGraphicalEditPart.class::cast)
				.filter(ep -> ep.resolveSemanticElement() instanceof Port)
				.collect(Collectors.toList());
	}

	/**
	 * @param capsule
	 *            a capsule frame
	 * @param ports
	 *            ports on the capsule
	 * 
	 * @return the layout command, or {@code null} if none
	 */
	abstract protected Command getPortLayoutCommand(IGraphicalEditPart capsule, List<IGraphicalEditPart> ports);

	@Override
	public Command layoutEditParts(@SuppressWarnings("rawtypes") List selectedObjects, IAdaptable layoutHint) {
		IGraphicalEditPart capsule = layoutHint.getAdapter(IGraphicalEditPart.class);
		List<IGraphicalEditPart> ports = getPorts(selectedObjects);

		return getPortLayoutCommand(capsule, ports);
	}

}
