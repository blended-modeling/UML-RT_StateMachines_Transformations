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

import static org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils.isCapsulePart;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.AbstractLayoutEditPartProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNode;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNodeOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutType;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RelativePortLocationHelper;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTCapsuleStructureDiagramUtils;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;

/**
 * A provider of layout for the ports on a capsule part.
 */
public class PortOnCapsulePartLayoutProvider extends AbstractLayoutEditPartProvider {

	/**
	 * Initializes me.
	 */
	public PortOnCapsulePartLayoutProvider() {
		super();
	}

	@Override
	public boolean provides(IOperation operation) {
		boolean result = false;

		if (operation instanceof ILayoutNodeOperation) {
			ILayoutNodeOperation layout = (ILayoutNodeOperation) operation;
			if (LayoutType.DEFAULT.equals(layout.getLayoutHint().getAdapter(String.class))) {
				IGraphicalEditPart container = layout.getLayoutHint().getAdapter(IGraphicalEditPart.class);
				EObject maybeCapsulePart = container.resolveSemanticElement();

				if ((maybeCapsulePart instanceof Property) && isCapsulePart((Property) maybeCapsulePart)) {
					@SuppressWarnings("unchecked")
					List<ILayoutNode> nodes = layout.getLayoutNodes();
					result = nodes.stream()
							.map(ILayoutNode::getNode)
							.allMatch(this::isRTPort);
				}
			}
		}

		return result;
	}

	private boolean isRTPort(Node node) {
		return (node.getElement() instanceof Port)
				&& RTPortUtils.isRTPort(node.getElement());
	}

	@Override
	public Command layoutEditParts(GraphicalEditPart containerEditPart, IAdaptable layoutHint) {
		IGraphicalEditPart capsulePart = layoutHint.getAdapter(IGraphicalEditPart.class);
		List<IGraphicalEditPart> ports = getPorts(capsulePart.getChildren());

		return getRelativePortLayoutCommand(capsulePart, ports);
	}

	@SuppressWarnings("unchecked")
	private final List<IGraphicalEditPart> getPorts(List<?> editParts) {
		return ((List<EditPart>) editParts).stream()
				.filter(IGraphicalEditPart.class::isInstance).map(IGraphicalEditPart.class::cast)
				.filter(ep -> ep.resolveSemanticElement() instanceof Port)
				.collect(Collectors.toList());
	}

	/**
	 * Obtains a command that lays out {@code ports} around a {@code capsulePart}
	 * according to their arrangement around the defining capsule's structure diagram.
	 * 
	 * @param capsulePart
	 *            a capsule-part
	 * @param ports
	 *            ports on the capsule-part
	 * 
	 * @return the layout command, or {@code null} if none
	 */
	private Command getRelativePortLayoutCommand(IGraphicalEditPart capsulePart, List<IGraphicalEditPart> ports) {
		// Don't let the edit part resolve its semantic element because
		// that may return a redefinition in the capsule context
		Optional<Diagram> capsuleStructureDiagram = Optional.ofNullable(capsulePart.getNotationView())
				.map(View::getElement)
				// These casts are safe because we checked the operation before accepting it
				.map(Property.class::cast)
				.map(Property::getType)
				.map(org.eclipse.uml2.uml.Class.class::cast)
				.map(UMLRTCapsuleStructureDiagramUtils::getCapsuleStructureDiagram);

		return capsuleStructureDiagram.map(diagram -> {
			Command result = null;

			View frame = ViewUtil.getChildBySemanticHint(diagram, ClassCompositeEditPart.VISUAL_ID);
			if (frame instanceof Node) {
				Bounds frameBounds = (Bounds) ((Node) frame).getLayoutConstraint();
				RelativePortLocationHelper helper = new RelativePortLocationHelper(capsulePart);

				@SuppressWarnings("unchecked")
				List<View> frameChildren = frame.getChildren();

				for (IGraphicalEditPart portOnPart : ports) {
					// Don't let the edit part resolve its semantic element because
					// that may return a redefinition in the capsule context
					View view = portOnPart.getNotationView();
					Port port = (Port) ((view == null) ? null : view.getElement());
					Optional<Node> portOnCapsule = frameChildren.stream()
							.filter(Node.class::isInstance).map(Node.class::cast)
							.filter(node -> node.getElement() == port)
							.findAny();
					portOnCapsule.ifPresent(p -> helper.memoizeRelativePortLocation(
							portOnPart, (Bounds) p.getLayoutConstraint(), frameBounds));
				}

				result = helper.getPortUpdateCommand();
			}

			return result;
		}).orElse(null);
	}

	@Override
	public Command layoutEditParts(@SuppressWarnings("rawtypes") List selectedObjects, IAdaptable layoutHint) {
		IGraphicalEditPart capsulePart = layoutHint.getAdapter(IGraphicalEditPart.class);
		List<IGraphicalEditPart> ports = getPorts(selectedObjects);

		return getRelativePortLayoutCommand(capsulePart, ports);
	}

}
