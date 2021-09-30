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
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 476126
 *   Christian W. Damus - bugs 467545, 512666
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.types.advice;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.MoveElementsCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.composite.providers.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.IRealTimeConstants;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageUtils;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * The helper advice class used for UMLRealTime::MessageSets.
 */
public class MessageSetEditHelperAdvice extends AbstractEditHelperAdvice implements IInheritanceEditHelperAdvice {

	/**
	 * Label of the CALL Event creation command
	 */
	private static final String CALL_EVENT_COMMAND_LABEL = "Call Event Update";
	/**
	 * Parameter's name of the owned operation for the CallEvent creation
	 * request
	 */
	private static final String OWNED_OPERATION = IRealTimeConstants.CALL_EVENT_OPERATION_PARAMETER_NAME;

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
	protected ICommand getAfterMoveCommand(final MoveRequest request) {
		ICommand result = null;

		CompositeCommand compositeMoveCommand = new CompositeCommand("Composite Move Command");

		Map<?, ?> elementsToMove = request.getElementsToMove();
		if (!elementsToMove.isEmpty()) {
			for (Object elementToMove : elementsToMove.keySet()) {
				if (elementToMove instanceof Operation) {
					final Operation operation = (Operation) elementToMove;
					final CallEvent callEvent = MessageUtils.getCallEvent(operation);
					if (null != callEvent) {
						MoveElementsCommand command = MessageUtils.createMoveCallEventCommand(request, callEvent);
						compositeMoveCommand.add(command);
					}
				}
			}
		}

		if (compositeMoveCommand.isEmpty()) {
			result = super.getAfterMoveCommand(request);
		} else {
			result = compositeMoveCommand;
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {

		// do not allow to create a children to MessageSets other than
		// operations (Messages)
		if (request instanceof CreateElementRequest) {
			CreateElementRequest createElementRequest = ((CreateElementRequest) request);
			// retrieve element type from this request and check if this is a
			// kind of UMLRT::Message
			IElementType type = createElementRequest.getElementType();

			// type should only be compatible with UMLRT::OperationAsMessages
			IElementType umlRTMessageType = ElementTypeRegistry.getInstance()
					.getType(IUMLRTElementTypes.PROTOCOL_MESSAGE_ID);
			// should not be null, otherwise, element type model is not loaded
			// correctly. abort.
			if (null == umlRTMessageType) {
				Activator.log.debug("RTMessage element type is not accessible");
				return super.approveRequest(request);
			}

			// check type is compatible with UMLRT::OperationAsMessages
			List<IElementType> types = Arrays.asList(type.getAllSuperTypes());
			if (!types.contains(umlRTMessageType)) {
				return false;
			}
			return super.approveRequest(request);
		}
		return super.approveRequest(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getAfterCreateCommand(CreateElementRequest request) {
		return super.getAfterCreateCommand(request);
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterSetCommand(org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest)
	 *
	 */
	@Override
	protected ICommand getAfterSetCommand(SetRequest request) {
		ICommand command = null;
		// in case the request address the Interface Owned Operation feature ,
		// create the call event
		if (UMLPackage.eINSTANCE.getInterface_OwnedOperation().equals(request.getFeature())) {

			command = getCreateCallEventCommand(request);
			if (null == command) {
				command = super.getAfterSetCommand(request);
			}

		} else {
			command = super.getAfterSetCommand(request);
		}

		return command;
	}

	/**
	 * Get the command that generates all the Call Events in the Set list
	 * 
	 * @param request
	 *            The SetRequest
	 * @return The Composite command that contains the command creation of each
	 *         Call Event
	 */
	protected ICommand getCreateCallEventCommand(SetRequest request) {
		ICompositeCommand callEventCommands = new CompositeCommand(CALL_EVENT_COMMAND_LABEL);

		if ((request.getValue() instanceof LinkedList)) {
			LinkedList<?> newOperationList = ((LinkedList<?>) request.getValue());
			// For each message (operation) create a Call Event
			if (request.getElementToEdit() instanceof Interface) {
				ICommand createCommand = createNewCallEvent(request, newOperationList);
				callEventCommands.compose(createCommand);

			}

		}

		return callEventCommands.isEmpty() ? null : callEventCommands;
	}

	/**
	 * Create Call events if not existing
	 * 
	 * @param request
	 * @param callEventCommands
	 * @param newOperationList
	 */
	private ICommand createNewCallEvent(SetRequest request, LinkedList<?> newOperationList) {

		ICommand singleCallEvent = null;
		for (Object operation : newOperationList) {
			if ((operation instanceof Operation) && (request.getElementToEdit() instanceof Classifier)) {
				singleCallEvent = getCallEventCommand((Classifier) request.getElementToEdit(), (Operation) operation);

			}
		}

		return singleCallEvent;
	}

	/**
	 * Get the command that generate a Call Event
	 * 
	 * @param targetClassifier
	 *            Classifier of the Operation (usually an Interface representing
	 *            a RTMessageSet )
	 * @param operation
	 *            The operation that has to be added to the create request in
	 *            order to link the call event to it
	 * @return The command that creates the Call Event
	 */
	protected ICommand getCallEventCommand(Classifier targetClassifier, Operation operation) {
		ICommand command = null;
		// Create the call event only if it doens't exist already
		if (!callEventExist(targetClassifier.eContainer(), operation)) {
			try {
				TransactionalEditingDomain domain = ServiceUtilsForEObject.getInstance()
						.getTransactionalEditingDomain(targetClassifier);
				CreateElementRequest createElementRequest = new CreateElementRequest(domain,
						targetClassifier.eContainer(), UMLElementTypes.CallEvent_Shape);
				// Add the operation as parameter
				createElementRequest.setParameter(OWNED_OPERATION, operation);
				IElementEditService provider = ElementEditServiceUtils.getCommandProvider(targetClassifier);
				if (null != provider) {
					ICommand createCommand = provider.getEditCommand(createElementRequest);

					if (null != createCommand) {
						command = createCommand;
					}
				}
			} catch (ServiceException e) {
				Activator.log.error(e);

			}
		}
		return command;
	}

	/**
	 * Define if the CallEvent For the related operation (Protocol Message)
	 * exists
	 * 
	 * @param container
	 *            The Container of the Call Event (usually a Package
	 *            representing the ProtocolContainer)
	 * @param operation
	 *            the operation for which the callEVent is created
	 * @return True if a Call event referencing the operation exists already,
	 *         false otherwise.
	 */
	protected boolean callEventExist(final Object container, final Operation operation) {
		return (null != getRelatedCallEvent(container, operation));
	}

	/**
	 * Get the Call Event in a Container related to a specific Operation.
	 * 
	 * @param container
	 *            The Container of the CallEvent researched.
	 * @param operation
	 *            Operation of the Call Event that is Related to.
	 * @return The Associated Call Event.
	 */
	protected static CallEvent getRelatedCallEvent(final Object container, final Operation operation) {
		CallEvent callEvent = null;
		if (container instanceof org.eclipse.uml2.uml.Package) {
			// List all the children of the Container
			EList<Element> containment = ((org.eclipse.uml2.uml.Package) container).getOwnedElements();
			Iterator<Element> elements = containment.iterator();

			while (null == callEvent && elements.hasNext()) {
				Element element = elements.next();
				// if the child is a callEvent check the operation
				if ((element instanceof CallEvent) && (null != operation)) {
					if (operation.equals(((CallEvent) element).getOperation())) {
						callEvent = (CallEvent) element;
					}
				}
			}
		}
		return callEvent;

	}

}
