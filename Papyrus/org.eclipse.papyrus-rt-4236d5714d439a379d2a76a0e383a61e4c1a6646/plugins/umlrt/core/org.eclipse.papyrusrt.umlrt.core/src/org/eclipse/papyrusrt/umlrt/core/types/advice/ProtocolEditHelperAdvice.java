/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Onder Gurcan <onder.gurcan@cea.fr>
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.types.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.GetEditContextCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.commands.wrappers.EMFtoGMFCommandWrapper;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageSetUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageSet;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.util.UMLUtil;


/**
 * The helperadvice class used for UMLRealTime::Protocol.
 *
 * @author Onder Gurcan <onder.gurcan@cea.fr>
 *
 */
public class ProtocolEditHelperAdvice extends AbstractEditHelperAdvice {

	private enum Relation {
		CHILD, SIBLING, PARENT;
	}

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
			List<IElementType> types = new ArrayList<IElementType>(Arrays.asList(type.getAllSuperTypes()));
			types.add(type);
			if (types.contains(umlRTMessageType)) {
				return true;
			} else {
				if (types.contains(ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.Generalization"))) {
					return true;
				} else {
					return false;
				}

				// return super.approveRequest(createElementRequest);
			}
		}
		return super.approveRequest(request);
	}

	// @Override
	// public void configureRequest(IEditCommandRequest request) {
	// if (request instanceof DestroyElementRequest) {
	// // this advice will move the destroy command from the protocol to the protocol container
	// EObject elementToDestroy = ((DestroyElementRequest) request).getElementToDestroy();
	// if (ProtocolUtils.isProtocol(elementToDestroy)) {
	// ((DestroyElementRequest) request).setElementToDestroy((ProtocolUtils.getProtocolContainer((Collaboration) elementToDestroy)));
	// }
	// }
	// super.configureRequest(request);
	// }
	
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
			List<IElementType> types = new ArrayList<IElementType>(Arrays.asList(type.getAllSuperTypes()));
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
			if(eObject instanceof Collaboration) {
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
		if(UMLPackage.eINSTANCE.getNamedElement_Name().equals(request.getFeature() ) ){

			final EObject elementToEdit = request.getElementToEdit();
			if (!(elementToEdit instanceof Collaboration)) {
				return super.getAfterSetCommand(request);
			}
			final Collaboration protocol = (Collaboration) elementToEdit;
			
			final String newName = (request.getValue() != null) ? request.getValue().toString() : "Protocol";
		
			RecordingCommand command = new RecordingCommand(request.getEditingDomain(), "Change Dependents", "Change the names of the dependents of the Protocol") {

				@Override
				protected void doExecute() {
					Package protocolContainer = ProtocolUtils.getProtocolContainer(protocol);
					if(protocolContainer ==null) {
						return;
					}
					
					protocolContainer.setName(newName);
					protocol.setName(newName);
					
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

		return new ConfigureElementCommand(request) {
			private IProgressMonitor progressMonitor;
			private IAdaptable info;

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				this.progressMonitor = progressMonitor;
				this.info = info;

				// Create the UMLRealTime::ProtocolContainer package
				createElement(protocol, name, UMLRTElementTypesEnumerator.PROTOCOL_CONTAINER, Relation.PARENT);

				// Create the incoming UMLRealTime::RTMessageSet interface
				String nameIn = MessageSetUtils.computeInterfaceInName(name);
				Interface rtMessageSetInt = (Interface) createElement(protocol, nameIn, UMLRTElementTypesEnumerator.RT_MESSAGE_SET, Relation.SIBLING);
				setRtMsgKind(rtMessageSetInt, RTMessageKind.IN);
				createInterfaceRealization(protocol, nameIn, rtMessageSetInt);

				// Create the outgoing UMLRealTime::RTMessageSet interface
				String nameOut = MessageSetUtils.computeInterfaceOutName(name);
				Interface rtMessageSetOutInt = (Interface) createElement(protocol, nameOut, UMLRTElementTypesEnumerator.RT_MESSAGE_SET, Relation.SIBLING);
				setRtMsgKind(rtMessageSetOutInt, RTMessageKind.OUT);
				createUsage(protocol, nameOut, rtMessageSetOutInt);

				createElement(protocol, "*", UMLElementTypes.ANY_RECEIVE_EVENT, Relation.SIBLING); //$NON-NLS-1$

				// Create the in-out UMLRealTime::RTMessageSet interface
				String nameInOut = MessageSetUtils.computeInterfaceInOutName(name);
				Interface rtMessageSetInOutInt = (Interface) createElement(protocol, nameInOut, UMLRTElementTypesEnumerator.RT_MESSAGE_SET, Relation.SIBLING);
				setRtMsgKind(rtMessageSetInOutInt, RTMessageKind.IN_OUT);
				createInterfaceRealization(protocol, nameInOut, rtMessageSetInOutInt);
				createUsage(protocol, nameInOut, rtMessageSetInOutInt);

				protocol.setName(name);

				return CommandResult.newOKCommandResult(protocol);
			}

			/**
			 * Creates a UML::Usage relation between protocol and rtMessageSet with given name.
			 *
			 * @param protocol
			 * @param name
			 * @param rtMessageSet
			 * @throws ExecutionException
			 */
			private void createUsage(final Collaboration protocol, final String name, Interface rtMessageSet) throws ExecutionException {
				Usage usageOut = (Usage) createElement(protocol, name, UMLElementTypes.USAGE, Relation.SIBLING);
				usageOut.getClients().add(protocol);
				usageOut.getSuppliers().add(rtMessageSet);
			}

			/**
			 *  Creates an UML::InterfaceRealization relation between protocol and rtMessageSet with given name.
			 *
			 * @param protocol
			 * @param name
			 * @param rtMessageSet
			 * @throws ExecutionException
			 */
			private void createInterfaceRealization(final Collaboration protocol, final String name, Interface rtMessageSetInt) throws ExecutionException {
				InterfaceRealization realization = (InterfaceRealization) createElement(protocol, name, UMLElementTypes.INTERFACE_REALIZATION, Relation.CHILD);
				realization.setContract(rtMessageSetInt);
				realization.setImplementingClassifier(protocol);
			}

			/**
			 *
			 * @param referenceElement
			 * @param name
			 * @param elementType
			 * @param relation
			 * @return created element as EObject
			 * @throws ExecutionException
			 */
			private EObject createElement(Collaboration referenceElement, String name, IElementType elementType, Relation relation) throws ExecutionException {
				if ((referenceElement == null) || (name == null)) {
					throw new ExecutionException("Either the referenceElement or the name parameter is null. ");
				}

				EObject newElement = null;

				CreateElementRequest createElementRequest = new CreateElementRequest(referenceElement.getNearestPackage(), elementType);
				CreateElementCommand command = new CreateElementCommand(createElementRequest);
				command.execute(progressMonitor, info);
				newElement = command.getNewElement();

				if (newElement == null) {
					throw new ExecutionException("Element creation problem for " + elementType.getDisplayName() + ".");
				}

				((NamedElement)newElement).setName(name);

				if (relation == Relation.CHILD) { // if newElement is an owned element of protocol
					if (elementType == UMLElementTypes.INTERFACE_REALIZATION) {
						referenceElement.getInterfaceRealizations().add((InterfaceRealization) newElement);
					} else {
						referenceElement.createOwnedAttribute(name, (Type) newElement);
					}
				} else if (relation == Relation.SIBLING) { // if newElement is a sibling of protocol
					Package nearestPackage = referenceElement.getNearestPackage();
					nearestPackage.getPackagedElements().add((PackageableElement) newElement);
				} else if (relation == Relation.PARENT) { // otherwise newElement is a container element of protocol
					Package container = (Package) newElement;
					EList<PackageableElement> packagedElements = container.getPackagedElements();
					packagedElements.add(referenceElement);
				}

				return newElement;
			}

			private void setRtMsgKind(Interface rtMessageSetInt, RTMessageKind kind){
				RTMessageSet rtMessageSet = UMLUtil.getStereotypeApplication(rtMessageSetInt, RTMessageSet.class);
				rtMessageSet.setRtMsgKind(kind);
			}
		};
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		final Collaboration protocol = (Collaboration) request.getElementToConfigure();
		final String name = protocol.getPackage().getName();

		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				protocol.setName(name);
				return CommandResult.newOKCommandResult();
			}
		};
	}

}
