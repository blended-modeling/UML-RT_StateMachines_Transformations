/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *  Christian W. Damus - bugs 492368, 496304, 496649, 510188
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.drop;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.TransactionalDropStrategy;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IRTPortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.BorderMarchIterator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.ILocationIterator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.LinearIterator;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Drop strategy to create a RTPort when droping a Protocol on a capsule.
 */
public abstract class AbstractProtocolToRTPortDropStrategy extends TransactionalDropStrategy {


	private static final String EMPTY_STRING = "";// $NON-NLS-0$

	/**
	 * Instantiates a new abstract protocol to rt port drop strategy.
	 */
	public AbstractProtocolToRTPortDropStrategy() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getImage()
	 */
	@Override
	public Image getImage() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getPriority()
	 */
	@Override
	public int getPriority() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.TransactionalDropStrategy#doGetCommand(org.eclipse.gef.Request, org.eclipse.gef.EditPart)
	 */
	@Override
	protected Command doGetCommand(Request request, EditPart targetEditPart) {

		Command command = null;
		if ((request instanceof DropObjectsRequest)) {
			DropObjectsRequest dropReq = getDropObjectsRequest(request);
			if (dropReq != null) {
				List<Classifier> handledDroppedObjects = getDroppedProtocol(dropReq);
				EObject targetElement = getTargetSemanticElement(targetEditPart);
				if (canHandleRequest(handledDroppedObjects, targetElement) && canHandleDropPosition(dropReq.getLocation())) {
					// dropping a protocol on a Capsule => creating a rt port
					Point location = dropReq.getLocation();

					if (handledDroppedObjects.size() == 1) {
						// Simple case of creating a single port
						command = getCreateAndDropObjectCommand(handledDroppedObjects.get(0),
								(Classifier) targetElement, location, targetEditPart);
					} else {
						// Dropping multiple protocols to create a port for each
						CompoundCommand compound = new CompoundCommand();
						ILocationIterator locationIterator;
						int stride = IRTPortEditPart.getDefaultSize(false).width * 8 / 3;

						if (getRTPortKind().isExternal()) {
							// If we are creating external ports, they need to
							// march around the border of the target shape.
							IFigure figure = ((IGraphicalEditPart) targetEditPart).getFigure();
							Rectangle bounds = figure.getBounds().getCopy();
							figure.translateToAbsolute(bounds);

							locationIterator = BorderMarchIterator.from(location, bounds);
						} else {
							locationIterator = LinearIterator.from(location);
						}

						for (EObject droppedObject : handledDroppedObjects) {
							// Create this port
							Command createPort = getCreateAndDropObjectCommand(droppedObject, (Classifier) targetElement, location, targetEditPart);
							if (createPort != null) {
								compound.add(createPort);

								// March along with a healthy space in between
								// to position the next port
								location = locationIterator.next(stride);
							}

							command = compound;
						}
					}
				}
			}
		}
		return command;

	}

	/**
	 * Gets the creates the and drop object command.
	 *
	 * @param droppedObject
	 *            the dropped object
	 * @param targetClassifier
	 *            the target classifier
	 * @param location
	 *            the location
	 * @param targetEditPart
	 *            the target edit part
	 * @return the creates the and drop object command
	 */
	protected Command getCreateAndDropObjectCommand(EObject droppedObject, Classifier targetClassifier, Point location, EditPart targetEditPart) {
		// create RTport part
		CreateRTPortAndDisplayCommand command = new CreateRTPortAndDisplayCommand(targetClassifier, IUMLRTElementTypes.RT_PORT_ID, UMLPackage.eINSTANCE.getNamespace_OwnedMember(), droppedObject, location, targetEditPart);
		// set Name
		command.setRTPortName(getName(droppedObject));
		// Set kind
		command.setRTPortKind(getRTPortKind());
		return new ICommandProxy(command);
	}


	/**
	 * Gets the name of the dropped object.
	 *
	 * @param droppedObject
	 *            the dropped object
	 * @return the name
	 */
	protected String getName(EObject droppedObject) {
		String name = EMPTY_STRING;
		if (droppedObject instanceof NamedElement) {
			String elementName = ((NamedElement) droppedObject).getName();
			if (elementName.length() > 0) {
				name = Character.toLowerCase(elementName.charAt(0)) + elementName.substring(1);
			}
		}
		return name;

	}


	/**
	 * Can handle drop position.
	 *
	 * @param point
	 *            the point
	 * @return true, if successful
	 */
	protected boolean canHandleDropPosition(Point point) {
		return true;
	}

	/**
	 * Gets the dropped protocol.
	 *
	 * @param req
	 *            the req
	 * @return the dropped protocol
	 */
	protected List<Classifier> getDroppedProtocol(Request req) {
		List<EObject> droppedObjects = getSourceEObjects(req);
		List<Classifier> result = new ArrayList<>();
		if (droppedObjects != null) {
			for (EObject droppedObject : droppedObjects) {
				if (droppedObject instanceof Classifier) {
					if (ProtocolUtils.isProtocol(droppedObject)) {
						result.add((Classifier) droppedObject);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Can handle request.
	 *
	 * @param droppedObjects
	 *            the dropped objects
	 * @param targetElement
	 *            the target element
	 * @return true, if successful
	 */
	protected boolean canHandleRequest(List<Classifier> droppedObjects, EObject targetElement) {
		boolean result = false;
		if (!droppedObjects.isEmpty()) {
			result = (targetElement instanceof Classifier && CapsuleUtils.isCapsule((Classifier) targetElement) && droppedObjects.size() > 0);
		}
		return result;
	}

	/**
	 * Gets the kind of the RTPort.
	 *
	 * @return the kind of the RTPort.
	 */
	abstract protected UMLRTPortKind getRTPortKind();

}
