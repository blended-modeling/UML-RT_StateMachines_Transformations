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
 *  Christian W. Damus - bugs 474481, 492368
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.drop;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Command to create a Capsule Part, add it to a parent capsule and display it to the given location
 */
public class CreateCapsulePartAndDisplayCommand extends AbstractCommand {

	protected Classifier targetClassifier;
	protected String capsulePartId;
	protected EReference classifier_Feature;
	protected EObject droppedObject;
	protected Point location;
	protected EditPart targetEditPart;
	final protected boolean directEdit;

	public CreateCapsulePartAndDisplayCommand(Classifier targetClassifier, String capsulePartId, EReference classifier_Feature, EObject droppedObject, Point location, EditPart targetEditPart) {
		this(targetClassifier, capsulePartId, classifier_Feature, droppedObject, location, targetEditPart, true);
	}

	public CreateCapsulePartAndDisplayCommand(Classifier targetClassifier, String capsulePartId, EReference classifier_Feature, EObject droppedObject, Point location, EditPart targetEditPart, boolean directEdit) {
		super("");
		this.targetClassifier = targetClassifier;
		this.capsulePartId = capsulePartId;
		this.classifier_Feature = classifier_Feature;
		this.droppedObject = droppedObject;
		this.location = new Point(location);
		this.targetEditPart = targetEditPart;
		this.directEdit = directEdit;
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param progressMonitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		TransactionalEditingDomain editingDomain = null;
		try {
			editingDomain = ServiceUtilsForEObject.getInstance().getTransactionalEditingDomain(targetClassifier);
		} catch (ServiceException e) {
			Activator.log.error(e);
			return CommandResult.newErrorCommandResult(e);
		}
		Property capsulePart = createCapsulePart(editingDomain);
		setCapsuleAsType(editingDomain, capsulePart, droppedObject);
		dropCapsulePart(capsulePart);

		if (directEdit) {
			// And invoke the in-line editor for the name
			UMLRTEditPartUtils.scheduleDirectEdit(targetEditPart, capsulePart);
		}

		return CommandResult.newOKCommandResult(capsulePart);
	}

	protected void setCapsuleAsType(TransactionalEditingDomain editingDomain, Property capsulePart, EObject type) {
		SetRequest setRequest = new SetRequest(editingDomain, capsulePart, UMLPackage.eINSTANCE.getTypedElement_Type(), type);

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(targetClassifier);
		if (provider != null) {
			ICommand setCommand = provider.getEditCommand(setRequest);

			if (setCommand != null && setCommand.canExecute()) {
				try {
					setCommand.execute(new NullProgressMonitor(), null);
				} catch (ExecutionException e) {
					Activator.log.error(e);
				}
			}
		}
	}

	/**
	 * @param createdNode
	 */
	protected void dropCapsulePart(Property createdCapsulePart) {
		DropObjectsRequest dropReq = new DropObjectsRequest();
		dropReq.setObjects(Arrays.asList(createdCapsulePart));
		dropReq.setLocation(location);
		Command dropReqCommand = targetEditPart.getCommand(dropReq);
		if (dropReqCommand != null && dropReqCommand.canExecute()) {
			dropReqCommand.execute();
		}
	}


	/**
	 * 
	 */
	protected Property createCapsulePart(TransactionalEditingDomain editingDomain) {
		Property createdProperty = null;
		CreateElementRequest createElementRequest = new CreateElementRequest(editingDomain, targetClassifier, ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.CAPSULE_PART_ID));

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(targetClassifier);
		if (provider != null) {
			ICommand createCommand = provider.getEditCommand(createElementRequest);

			if (createCommand != null && createCommand.canExecute()) {
				try {
					createCommand.execute(new NullProgressMonitor(), null);
				} catch (ExecutionException e) {
					Activator.log.error(e);
					return null;
				}
				CommandResult result = createCommand.getCommandResult();
				createdProperty = getCreatedObject(result);
				return createdProperty;
			}

		}

		return createdProperty;
	}

	public static <T extends EObject> T getCreatedObject(CommandResult commandResult) {
		Object objectResult = commandResult.getReturnValue();
		if (objectResult instanceof List) {
			// Result of the semantic + graphical creation command
			List<?> listResult = (List<?>) objectResult;
			for (Object elementResult : listResult) {
				if (elementResult instanceof CreateElementRequestAdapter) {
					CreateElementRequest request = (CreateElementRequest) ((CreateElementRequestAdapter) elementResult).getAdapter(CreateElementRequest.class);
					if (request != null) {
						EObject newElement = request.getNewElement();
						if (newElement instanceof EObject) {
							return (T) newElement;
						}
					}
				}
			}
		} else if (commandResult.getReturnValue() instanceof EObject) {
			// Result of the semantic creation command
			return (T) commandResult.getReturnValue();
		}

		return null;
	}

	@Override
	protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		return null;
	}

	@Override
	protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		return null;
	}

}
