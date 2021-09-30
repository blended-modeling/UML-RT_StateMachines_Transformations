/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: 
 *   Onder Gurcan <onder.gurcan@cea.fr> - Initial API and implementation
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Extract the ConfigureElementCommand Class
 *   Christian W. Damus - bugs 467545, 510189
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.types.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.GetEditContextCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageSetUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;


/**
 * The helperadvice class used for UMLRealTime::Protocol.
 *
 * @author Onder Gurcan <onder.gurcan@cea.fr>
 *
 */
public class ProtocolEditHelperAdvice extends AbstractEditHelperAdvice implements IInheritanceEditHelperAdvice {


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
			List<IElementType> types = new ArrayList<>(Arrays.asList(type.getAllSuperTypes()));
			types.add(type);
			if (types.contains(umlRTMessageType)) {
				return true;
			} else {
				if (types.contains(ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.Generalization")) ||
						types.contains(ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.Connector"))) {
					return true;
				} else {
					return false;
				}

			}
		}
		return super.approveRequest(request);
	}

	@Override
	public ICommand getBeforeEditCommand(IEditCommandRequest request) {
		ICommand result = getInheritanceEditCommand(request);

		if (result == null) {
			result = super.getBeforeEditCommand(request);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeEditContextCommand(GetEditContextRequest request) {
		IEditCommandRequest editCommandRequest = request.getEditCommandRequest();
		if (editCommandRequest instanceof CreateElementRequest) {
			// check the element to create is a sub kind of RTMessage
			CreateElementRequest createElementRequest = ((CreateElementRequest) editCommandRequest);
			// retrieve element type from this request and check if this is a kind of UMLRT::Message
			IElementType type = createElementRequest.getElementType();

			// type should only be compatible with UMLRT::OperationAsMessages
			IElementType umlRTMessageType = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.PROTOCOL_MESSAGE_ID);
			// should not be null, otherwise, element type model is not loaded correctly. abort.
			if (umlRTMessageType == null || type == null) {
				Activator.log.debug("RTMessage element type is not accessible");
				return super.getBeforeEditContextCommand(request);
			}

			// check type is compatible with UMLRT::OperationAsMessages. If yes, allow creation
			List<IElementType> types = new ArrayList<>(Arrays.asList(type.getAllSuperTypes()));
			types.add(type);
			if (types.contains(umlRTMessageType)) {
				// return the right message set here rather than the protocol container

				GetEditContextCommand command = new GetEditContextCommand(request);
				if (request.getEditContext() instanceof Collaboration) {
					// retrieve all needed element types
					IElementType rTMessageTypeIn = UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_IN;
					IElementType rTMessageTypeOut = UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_OUT;
					IElementType rTMessageTypeInOut = UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_INOUT;
					if (types.contains(rTMessageTypeIn)) {
						command.setEditContext(ProtocolUtils.getMessageSetIn((Collaboration) request.getEditContext()));
					} else if (types.contains(rTMessageTypeOut)) {
						command.setEditContext(ProtocolUtils.getMessageSetOut((Collaboration) request.getEditContext()));
					} else if (types.contains(rTMessageTypeInOut)) {
						command.setEditContext(ProtocolUtils.getMessageSetInOut((Collaboration) request.getEditContext()));
					} else {
						return super.getBeforeEditContextCommand(request);
					}
				}
				return command;
			}

		} else if (editCommandRequest instanceof DestroyElementRequest) {
			EObject eObject = ((DestroyElementRequest) editCommandRequest).getElementToDestroy();
			if (eObject instanceof Collaboration) {
				if (ProtocolUtils.isProtocol(eObject)) {
					Package protocolContainerToDestroy = ProtocolUtils.getProtocolContainer((Collaboration) eObject);
					// if element to destroy is the protocol, the target of the request should be the protocol container instead of the protocol itself
					GetEditContextCommand command = new GetEditContextCommand(request);
					command.setEditContext(protocolContainerToDestroy);
					return command;
				}
			}
		}

		return super.getBeforeEditContextCommand(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getAfterSetCommand(SetRequest request) {
		// name edition for all dependents (protocolcontainer, messagesets, etc.)
		if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(request.getFeature())) {

			final EObject elementToEdit = request.getElementToEdit();
			if (!ProtocolUtils.isProtocol(elementToEdit)) {
				return super.getAfterSetCommand(request);
			}
			final Collaboration protocol = (Collaboration) elementToEdit;

			final String newName = (request.getValue() != null) ? request.getValue().toString() : "Protocol";

			EditElementCommand command = new EditElementCommand("Change Dependents", protocol, request) {


				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					Package protocolContainer = ProtocolUtils.getProtocolContainer(protocol);
					if (protocolContainer == null) {
						Activator.log.error("Impossible to find associated ProtocolContainer for " + protocol, null);
					}

					protocolContainer.setName(newName);
					protocol.setName(newName);

					// rename protocol, avoid dependency to avoid circular dependencies towards advices
					Interface interfaceIn = ProtocolUtils.getMessageSetIn(protocol);
					if (interfaceIn != null) {
						interfaceIn.setName(MessageSetUtils.computeInterfaceInName(newName));
					} else {
						Activator.log.error("Impossible to find associated MessageSetIn for " + protocol, null);
					}

					Interface interfaceOut = ProtocolUtils.getMessageSetOut(protocol);
					if (interfaceOut != null) {
						interfaceOut.setName(MessageSetUtils.computeInterfaceOutName(newName));
					} else {
						Activator.log.error("Impossible to find associated MessageSetOut for " + protocol, null);
					}

					Interface interfaceInOut = ProtocolUtils.getMessageSetInOut(protocol);
					if (interfaceInOut != null) {
						interfaceInOut.setName(MessageSetUtils.computeInterfaceInOutName(newName));
					} else {
						Activator.log.error("Impossible to find associated MessageSetInOut for " + protocol, null);
					}

					return CommandResult.newOKCommandResult(protocol);
				}
			};

			// check the super in case of more commands
			ICommand superCommand = super.getAfterSetCommand(request);
			if (superCommand != null) {
				return superCommand.compose(command);
			}
			return command;
		}

		return super.getAfterSetCommand(request);
	}



	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		final Collaboration protocol = (Collaboration) request.getElementToConfigure();
		final String name = NamedElementUtil.getDefaultNameWithIncrementFromBase("Protocol", protocol.eContainer().eContents());

		request.setParameter(RequestParameterConstants.NAME_TO_SET, name);


		return new ProtocolContainerConfigurationCommand(request, name, protocol);
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		/*
		 * @noname
		 * final Collaboration protocol = (Collaboration) request.getElementToConfigure();
		 * final String name = protocol.getPackage().getName();
		 * 
		 * return new ConfigureElementCommand(request) {
		 * 
		 * @Override
		 * protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		 * protocol.setName(name);
		 * return CommandResult.newOKCommandResult();
		 * }
		 * };
		 */
		return super.getAfterConfigureCommand(request);
	}

}
