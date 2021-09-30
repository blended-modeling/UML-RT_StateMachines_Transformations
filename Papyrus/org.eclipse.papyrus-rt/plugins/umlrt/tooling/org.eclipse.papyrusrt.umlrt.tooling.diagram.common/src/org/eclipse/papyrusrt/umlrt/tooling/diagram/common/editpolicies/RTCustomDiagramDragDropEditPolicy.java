/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bug 496304
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.ShapeCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;
import org.eclipse.papyrus.uml.diagram.composite.custom.edit.policies.CustomDiagramDragDropEditPolicy;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IRTPortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPropertyPartEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.locator.RTPortPositionLocator;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.EncapsulatedClassifier;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.util.UMLUtil;


/**
 * The Class RTCustomDiagramDragDropEditPolicy. Installed on RTClassCompositeEditPart to manage the location of RTPort.
 */
public class RTCustomDiagramDragDropEditPolicy extends CustomDiagramDragDropEditPolicy {

	/**
	 * PortPositionLocator is RTPortPositionLocator to allow port to be inside the figure when Internal.
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.composite.custom.edit.policies.CustomDiagramDragDropEditPolicy#dropAffixedNode(org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest, org.eclipse.uml2.uml.Element, int)
	 *
	 */
	@Override
	protected Command dropAffixedNode(DropObjectsRequest dropRequest, Element droppedElement, String nodeVISUALID) {
		// The dropped element must be a Port or Parameter
		if (!((droppedElement instanceof Port))) {
			Activator.log.error(new Exception("Incorrect parameter type (droppedElement should be a Port or Parameter)"));
			return UnexecutableCommand.INSTANCE;
		}


		// Manage Element drop in compartment
		Boolean isCompartmentTarget = false; // True if the target is a
												// ShapeCompartmentEditPart
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();

		// Default drop location
		Point dropLocation = dropRequest.getLocation().getCopy();

		// Detect if the drop target is a compartment
		if (graphicalParentEditPart instanceof ShapeCompartmentEditPart) {
			isCompartmentTarget = true;

			// Replace compartment edit part by its parent EditPart
			graphicalParentEditPart = (GraphicalEditPart) graphicalParentEditPart.getParent();

			// Translate Port expected location according to the compartment
			// location
			Point targetLocation = graphicalParentEditPart.getContentPane().getBounds().getLocation();
			ShapeCompartmentFigure compartmentFigure = (ShapeCompartmentFigure) getHostFigure();

			// Retrieve ViewPort location = the area where compartment children
			// are located
			// Retrieve ViewPort view location = the relative location of the
			// viewed compartment
			// depending on the current scroll bar state
			Viewport compartmentViewPort = compartmentFigure.getScrollPane().getViewport();
			Point compartmentViewPortLocation = compartmentViewPort.getLocation();
			Point compartmentViewPortViewLocation = compartmentViewPort.getViewLocation();

			// Calculate the delta between the targeted element position for
			// drop (the Composite figure)
			// and the View location with eventual scroll bar.
			Point delta = compartmentViewPortLocation.translate(targetLocation.negate());
			delta = delta.translate(compartmentViewPortViewLocation.negate());

			// Translate the requested drop location (relative to parent)
			dropLocation = dropRequest.getLocation().getTranslated(delta);
		}
		// Manage Element drop in compartment

		// Create proposed creation bounds and use the locator to find the
		// expected position
		Point parentLoc = graphicalParentEditPart.getFigure().getBounds().getLocation().getCopy();
		boolean isPortOnPart = graphicalParentEditPart instanceof RTPropertyPartEditPart;
		PortPositionLocator locator = new RTPortPositionLocator(droppedElement, graphicalParentEditPart.getFigure(),
				PositionConstants.NONE, IRTPortEditPart.getDefaultScaleFactor(isPortOnPart));

		Rectangle proposedBounds = new Rectangle(dropLocation, IRTPortEditPart.getDefaultSize(isPortOnPart));
		// proposedBounds = proposedBounds.getTranslated(parentLoc.getNegated());
		Rectangle preferredBounds = locator.getPreferredLocation(proposedBounds);

		if (null != preferredBounds) {
			// Convert the calculated preferred bounds as relative to parent
			// location
			// Rectangle creationBounds = preferredBounds.getTranslated(parentLoc);
			dropLocation = preferredBounds.getLocation();

			EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();

			if ((graphicalParentObject instanceof EncapsulatedClassifier) && (((EncapsulatedClassifier) graphicalParentObject).getAllAttributes().contains(droppedElement))) {
				// Drop Port on StructuredClassifier
				if (isCompartmentTarget) {
					return getDropAffixedNodeInCompartmentCommand(nodeVISUALID, dropLocation, droppedElement);
				}
				return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, dropLocation, droppedElement));
			} else if ((graphicalParentObject instanceof Property) && (null != UMLUtil.getStereotypeApplication((Property) graphicalParentObject, CapsulePart.class))) {
				// Drop Port on StructuredClassifier
				if (isCompartmentTarget) {
					return getDropAffixedNodeInCompartmentCommand(nodeVISUALID, dropLocation, droppedElement);
				}
				return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, dropLocation, droppedElement));

			} else if (graphicalParentObject instanceof ConnectableElement) {
				// Drop Port on Part
				Type type = ((ConnectableElement) graphicalParentObject).getType();

				if ((type != null) && (type instanceof EncapsulatedClassifier) && (((EncapsulatedClassifier) type).getAllAttributes().contains(droppedElement))) {
					if (isCompartmentTarget) {
						return getDropAffixedNodeInCompartmentCommand(nodeVISUALID, dropLocation, droppedElement);
					}
					return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, dropLocation, droppedElement));
				}

			} else if ((graphicalParentObject instanceof Behavior) && (((Behavior) graphicalParentObject).getOwnedParameters().contains(droppedElement))) {
				// Drop Parameter on Behavior
				if (isCompartmentTarget) {
					return getDropAffixedNodeInCompartmentCommand(nodeVISUALID, dropLocation, droppedElement);
				}
				return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, dropLocation, droppedElement));
			}
		}

		return UnexecutableCommand.INSTANCE;
	}

}

