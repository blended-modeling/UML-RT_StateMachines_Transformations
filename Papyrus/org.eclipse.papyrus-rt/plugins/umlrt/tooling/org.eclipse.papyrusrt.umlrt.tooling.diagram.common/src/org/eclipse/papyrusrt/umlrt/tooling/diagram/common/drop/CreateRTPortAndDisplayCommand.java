/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *  Christian W. Damus - bugs 492368, 510188
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.drop;

import java.util.Arrays;
import java.util.Collections;
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
import org.eclipse.gef.commands.CompoundCommand;
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
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Command to create a RTPort, and display it to the given location.
 */
// TODO: fix the location of the port
public class CreateRTPortAndDisplayCommand extends AbstractCommand {

	private static final String EMPTY_STRING = ""; // $NON-NLS-0$

	/** The target classifier. */
	protected Classifier targetClassifier;

	/** The rt port id. */
	protected String rtPortId;

	/** The classifier reference. */
	protected EReference classifierReference;

	/** The dropped object. */
	protected EObject droppedObject;

	/** The location. */
	protected Point location;

	/** The target edit part. */
	protected EditPart targetEditPart;

	/** The name. */
	private String name;

	/** The kind. */
	private UMLRTPortKind kind = UMLRTPortKind.RELAY;

	/**
	 * Instantiates a new creates the rt port and display command.
	 *
	 * @param targetClassifier
	 *            the target classifier
	 * @param rtPortId
	 *            the rt port id
	 * @param classifier_Feature
	 *            the classifier_ feature
	 * @param droppedObject
	 *            the dropped object
	 * @param location
	 *            the location
	 * @param targetEditPart
	 *            the target edit part
	 */
	public CreateRTPortAndDisplayCommand(Classifier targetClassifier, String rtPortId, EReference classifier_Feature, EObject droppedObject, Point location, EditPart targetEditPart) {
		super(EMPTY_STRING);
		this.targetClassifier = targetClassifier;
		this.rtPortId = rtPortId;
		this.classifierReference = classifier_Feature;
		this.droppedObject = droppedObject;
		this.location = new Point(location);
		this.targetEditPart = targetEditPart;
	}

	/**
	 * Do execute with result.
	 *
	 * @param progressMonitor
	 *            the progress monitor
	 * @param info
	 *            the info
	 * @return the command result
	 * @throws ExecutionException
	 *             the execution exception
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
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
		// Create the port
		Port rtPort = createRTPort(editingDomain);

		// Set the Name and the Kind of the Port
		setRTPort(editingDomain, rtPort, name, kind);
		// Bind the source protocol to the created RTPort as a Type
		setProtocolAsType(editingDomain, rtPort, droppedObject);
		// Drop the Port
		dropRTPort(editingDomain, rtPort);

		// And invoke the in-line editor for the name
		UMLRTEditPartUtils.scheduleDirectEdit(targetEditPart, rtPort);

		return CommandResult.newOKCommandResult(rtPort);
	}



	/**
	 * Sets the RTPort.
	 *
	 * @param editingDomain
	 *            the editing domain
	 * @param rtPort
	 *            the RTPort
	 */
	protected void setRTPort(TransactionalEditingDomain editingDomain, Port rtPort, String rtPortName, UMLRTPortKind rtPortKind) {
		// Set the name
		SetRequest setRequest = new SetRequest(editingDomain, rtPort, UMLPackage.eINSTANCE.getNamedElement_Name(), rtPortName);

		// Set the kind
		setRequest.addParameters(Collections.singletonMap(RTPortUtils.RTPORT_KIND_REQUEST_PARAMETER, rtPortKind));

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(rtPort);
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
	 * Sets the protocol as type.
	 *
	 * @param editingDomain
	 *            the editing domain
	 * @param rtPort
	 *            the rt port
	 * @param type
	 *            the type
	 */
	protected void setProtocolAsType(TransactionalEditingDomain editingDomain, Port rtPort, EObject type) {
		SetRequest setRequest = new SetRequest(editingDomain, rtPort, UMLPackage.eINSTANCE.getTypedElement_Type(), type);

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
	 * Drop RT Port.
	 *
	 * @param createdRTPort
	 *            the created RTPort
	 */
	protected void dropRTPort(TransactionalEditingDomain editingDomain, Port createdRTPort) {
		CompoundCommand cc = new CompoundCommand();
		DropObjectsRequest dropReq = new DropObjectsRequest();
		dropReq.setObjects(Arrays.asList(createdRTPort));
		dropReq.setLocation(location);
		Command dropReqCommand = targetEditPart.getCommand(dropReq);
		cc.add(dropReqCommand);

		if (cc != null && cc.canExecute()) {
			cc.execute();
		}


	}


	/**
	 * Creates the rt port.
	 *
	 * @param editingDomain
	 *            the editing domain
	 * @return the port
	 */
	protected Port createRTPort(TransactionalEditingDomain editingDomain) {
		Port createdPort = null;
		CreateElementRequest createElementRequest = new CreateElementRequest(editingDomain, targetClassifier, ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RT_PORT_ID));

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
				createdPort = getCreatedObject(result);
				return createdPort;
			}

		}

		return createdPort;
	}

	/**
	 * Gets the created object.
	 *
	 * @param <T>
	 *            the generic type
	 * @param commandResult
	 *            the command result
	 * @return the created object
	 */
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
							@SuppressWarnings("unchecked")
							T newElementAsT = (T) newElement;
							return newElementAsT;
						}
					}
				}
			}
		} else if (objectResult instanceof EObject) {
			// Result of the semantic creation command
			@SuppressWarnings("unchecked")
			T resultAsT = (T) objectResult;
			return resultAsT;
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

	/**
	 * Sets the RT port name.
	 *
	 * @param name
	 *            the new RT port name
	 */
	public void setRTPortName(String name) {
		this.name = name;
	}

	/**
	 * Sets the RTPort kind.
	 *
	 * @param rtPortKindEnum
	 *            the new RT port kind
	 */
	public void setRTPortKind(UMLRTPortKind rtPortKindEnum) {
		this.kind = rtPortKindEnum;
	}

}
