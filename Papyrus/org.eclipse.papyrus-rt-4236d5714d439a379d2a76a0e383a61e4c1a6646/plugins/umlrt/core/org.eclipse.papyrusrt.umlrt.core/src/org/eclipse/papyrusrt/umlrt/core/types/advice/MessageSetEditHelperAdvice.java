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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.MoveElementsCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageUtils;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Operation;


/**
 * The helper advice class used for UMLRealTime::MessageSets.
 */
public class MessageSetEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getAfterEditContextCommand(GetEditContextRequest request) {
		return super.getAfterEditContextCommand(request);
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
					if (callEvent != null) {												
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

		// do not allow to create a children to MessageSets other than operations (Messages)
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
		if (request.getElementType().getId().equals(IUMLRTElementTypes.PROTOCOL_MESSAGE_IN_ID)) {
			EObject container = request.getContainer();
			Element element = (Element)container;
			org.eclipse.uml2.uml.Package pack = element.getNearestPackage();
			CreateElementRequest req = new CreateElementRequest(request.getEditingDomain(), pack, ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.CallEvent"));
		}
		return super.getAfterCreateCommand(request);
	}
}
