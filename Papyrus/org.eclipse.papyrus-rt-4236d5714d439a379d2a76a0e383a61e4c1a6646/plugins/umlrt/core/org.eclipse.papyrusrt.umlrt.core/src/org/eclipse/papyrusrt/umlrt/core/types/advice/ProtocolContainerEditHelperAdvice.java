/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: CEA LIST
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.GetEditContextCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.commands.wrappers.EMFtoGMFCommandWrapper;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageSetUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * The helper advice class used for UMLRealTime::ProtocolContainer.
 */
public class ProtocolContainerEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		if (request instanceof CreateElementRequest) {
			CreateElementRequest createElementRequest = ((CreateElementRequest) request);
			// retrieve element type from this request and check if this is a kind of UMLRT::Message
			IElementType type = createElementRequest.getElementType();

			// type should only be compatible with UMLRT::OperationAsMessages
			IElementType umlRTMessageType = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.PROTOCOL_MESSAGE_ID);
			// should not be null, otherwise, element type model is not loaded correctly. abort.
			if (umlRTMessageType == null) {
				Activator.log.debug("RTMessage element type is not accessible");
				return super.approveRequest(request);
			}

			// check type is compatible with UMLRT::OperationAsMessages. If yes, allow creation
			List<IElementType> types = Arrays.asList(type.getAllSuperTypes());
			if (types.contains(umlRTMessageType)) {
				return true;
			} else {
				// return false;
				return super.approveRequest(createElementRequest);
			}
		}
		return super.approveRequest(request);
	}


	@Override
	public void configureRequest(IEditCommandRequest request) {
		if (request instanceof DestroyElementRequest) {
			// this advice will move the destroy command from the protocol to the protocol container
			EObject elementToDestroy = ((DestroyElementRequest) request).getElementToDestroy();
			if (ProtocolUtils.isProtocol(elementToDestroy)) {
				// check his parent was not the initial element to destroy (it's not a delete dependant request)
				Object o = request.getParameter(DestroyElementRequest.INITIAL_ELEMENT_TO_DESTROY_PARAMETER);
				if (o == null) {
					((DestroyElementRequest) request).setElementToDestroy((ProtocolUtils.getProtocolContainer((Collaboration) elementToDestroy)));
				} else {
					// that could be something coming from a multi selection => try to check what is the official initial element
					// this is caused by the handler from model explorer reusing the parameters of the request, for each selected element
					// another check could be done in the foreach in the model explorer, to avoid resuing initial element to destroy
					// in this case, it is preferable to limit the impact (post-Mars SR0) and to check here for the parameters
					if (!(o.equals(ProtocolUtils.getProtocolContainer((Collaboration) elementToDestroy)))) {
						if (o instanceof EObject && ProtocolContainerUtils.isProtocolContainer((EObject) o)) {
							((DestroyElementRequest) request).setElementToDestroy((ProtocolUtils.getProtocolContainer((Collaboration) elementToDestroy)));
							request.setParameter(DestroyElementRequest.INITIAL_ELEMENT_TO_DESTROY_PARAMETER, ProtocolUtils.getProtocolContainer((Collaboration) elementToDestroy));
						}
					}

				}
			}
		}
		super.configureRequest(request);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeEditContextCommand(GetEditContextRequest request) {
		IEditCommandRequest editCommandRequest = request.getEditCommandRequest();
		if(editCommandRequest instanceof CreateElementRequest) {
			// check the element to create is a sub kind of RTMessage
			CreateElementRequest createElementRequest = ((CreateElementRequest) editCommandRequest);
			// retrieve element type from this request and check if this is a kind of UMLRT::Message
			IElementType type = createElementRequest.getElementType();

			// type should only be compatible with UMLRT::OperationAsMessages
			IElementType umlRTMessageType = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.PROTOCOL_MESSAGE_ID);
			// should not be null, otherwise, element type model is not loaded correctly. abort.
			if (umlRTMessageType == null) {
				Activator.log.debug("RTMessage element type is not accessible");
				return super.getBeforeEditContextCommand(request);
			}

			// check type is compatible with UMLRT::OperationAsMessages. If yes, allow creation
			List<IElementType> types = Arrays.asList(type.getAllSuperTypes());
			if (types.contains(umlRTMessageType)) {
				// return the right message set here rather than the protocol container

				GetEditContextCommand command = new GetEditContextCommand(request);
				if (request.getEditContext() instanceof Package) {
					command.setEditContext(ProtocolContainerUtils.getMessageSetIn((Package) request.getEditContext()));
				}
				return command;
			}

		}
		
		return super.getBeforeEditContextCommand(request);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getAfterSetCommand(SetRequest request) {
		// for change name request, changes the name of the protocol and its interfaces
		if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(request.getFeature())) {

			final EObject elementToEdit = request.getElementToEdit();
			if (!(elementToEdit instanceof Package)) {
				return super.getBeforeSetCommand(request);
			}

			final String newName = (request.getValue() != null) ? request.getValue().toString() : "Protocol";

			final Package protocolContainer = (Package) elementToEdit;
			RecordingCommand command = new RecordingCommand(request.getEditingDomain(), "Change Dependents", "Change the names of the dependents of the ProtocolContainer") {

				@Override
				protected void doExecute() {
					// rename protocol, avoid dependency to avoid circular dependencies towards advices
					Interface interfaceIn = ProtocolContainerUtils.getMessageSetIn(protocolContainer);
					if (interfaceIn != null) {
						interfaceIn.setName(MessageSetUtils.computeInterfaceInName(newName));
					}

					Interface interfaceOut = ProtocolContainerUtils.getMessageSetOut(protocolContainer);
					if (interfaceOut != null) {
						interfaceOut.setName(MessageSetUtils.computeInterfaceOutName(newName));
					}

					Interface interfaceInOut = ProtocolContainerUtils.getMessageSetInOut(protocolContainer);
					if (interfaceInOut != null) {
						interfaceInOut.setName(MessageSetUtils.computeInterfaceInOutName(newName));
					}

					Collaboration collaboration = ProtocolContainerUtils.getCollaboration(protocolContainer);
					if (collaboration != null) {
						collaboration.setName(newName);
					}
				}
			};
			// check the super in case of more commands
			ICommand superCommand = super.getAfterSetCommand(request);
			if (superCommand != null) {
				return superCommand.compose(new EMFtoGMFCommandWrapper(command));
			}
			return new EMFtoGMFCommandWrapper(command);
		}
		return super.getAfterSetCommand(request);
	}
}
