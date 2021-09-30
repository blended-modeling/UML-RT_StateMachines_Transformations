/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 474481
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.drop;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.TransactionalDropStrategy;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.Activator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Drop strategy to create a capsule part when droping a capsule on the body of a capsule structure compartment
 */
public class CapsuleToCapsulePartDropStrategy extends TransactionalDropStrategy {

	/**
	 * Constructor.
	 */
	public CapsuleToCapsulePartDropStrategy() {
	}

	@Override
	public String getLabel() {
		return "Capsule drop to create CapsulePart";
	}

	@Override
	public String getDescription() {
		return "Capsule drop to create CapsulePart";
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public String getID() {
		return Activator.PLUGIN_ID + ".capsuleToCapsulePartDrop";
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	protected Command doGetCommand(Request request, EditPart targetEditPart) {
		if (!(request instanceof DropObjectsRequest)) {
			return null;
		}
		DropObjectsRequest dropReq = getDropObjectsRequest(request);
		if (dropReq == null) {
			return null;
		}
		List<Classifier> handledDroppedObjects = getDroppedCapsules(dropReq);
		EObject targetElement = getTargetSemanticElement(targetEditPart);

		if (!canHandleRequest(handledDroppedObjects, targetElement)) {
			return null;
		}

		// dropping a Capsule on a Capsule => creating a capsule part
		Point location = dropReq.getLocation();
		CompoundCommand compoundCommand = new CompoundCommand();
		for (EObject droppedObject : handledDroppedObjects) {
			compoundCommand.add(getCreateAndDropObjectCommand(droppedObject, (Classifier) targetElement, location, targetEditPart));
			location.performTranslate(20, 20);
		}
		return compoundCommand;

	}

	/**
	 * @param droppedObject
	 * @param targetActivity
	 * @param location
	 * @param targetEditPart
	 * @return
	 */
	protected Command getCreateAndDropObjectCommand(EObject droppedObject, Classifier targetClassifier, Point location, EditPart targetEditPart) {
		// CompositeCommand command = new CompositeCommand("Create and display CapsulePart");
		// create child command:
		// 1. create capsule part
		// 2. type it with droppped Capsule
		// 3. add it to the targetClassifier as a Property
		// 4. create the view
		CreateCapsulePartAndDisplayCommand command = new CreateCapsulePartAndDisplayCommand(targetClassifier, IUMLRTElementTypes.CAPSULE_PART_ID, UMLPackage.eINSTANCE.getNamespace_OwnedMember(), droppedObject, location, targetEditPart);
		return new ICommandProxy(command);
	}

	protected List<Classifier> getDroppedCapsules(Request req) {
		List<EObject> droppedObjects = getSourceEObjects(req);
		List<Classifier> result = new ArrayList<>();
		if (droppedObjects != null) {
			for (EObject droppedObject : droppedObjects) {
				if (droppedObject instanceof Classifier) {
					if (CapsuleUtils.isCapsule((Classifier) droppedObject)) {
						result.add((Classifier) droppedObject);
					}
				}
			}
		}
		return result;
	}

	protected boolean canHandleRequest(List<Classifier> droppedObjects, EObject targetElement) {
		boolean result = false;
		if (!droppedObjects.isEmpty()) {
			result = (targetElement instanceof Classifier && CapsuleUtils.isCapsule((Classifier) targetElement) && droppedObjects.size() > 0);
		}
		return result;
	}

}
