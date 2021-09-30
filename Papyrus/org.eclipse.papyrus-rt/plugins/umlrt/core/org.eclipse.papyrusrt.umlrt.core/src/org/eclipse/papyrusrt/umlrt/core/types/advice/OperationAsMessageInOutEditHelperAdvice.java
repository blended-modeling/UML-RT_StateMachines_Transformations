/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 476984
 *   Christian W. Damus - bug 476984
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageSetUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;

/**
 * Specialization of the Operation as message in message sets, but specific to message sets with kind=INOUT
 */
public class OperationAsMessageInOutEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		boolean approve = false;
		if (request instanceof CreateElementRequest) {
			// check container.
			EObject container = ((CreateElementRequest) request).getContainer();
			// When creating a parameter, the container is an Operation and the element type is the specific one created
			if (container instanceof Operation) {
				approve = ElementTypeUtils.isTypeCompatible(((CreateElementRequest) request).getElementType(), UMLElementTypes.PARAMETER);
			} else if ((container instanceof Interface)) {
				// Approve the request if the container is an interface
				approve = true;
			}


			IElementType messageSetType = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RT_MESSAGE_SET_ID);
			if (messageSetType instanceof ISpecializationType) { // check at the same time UMLRT element types are correctly loaded
				// Approve the request only if the Container matches the Message Set Type
				if (((ISpecializationType) messageSetType).getMatcher().matches(container)) {
					approve = true;
				} else if (RTMessageKind.IN_OUT == MessageSetUtils.getMessageKind(container)) {
					// Approve the request only if the Message matches the IN_OUT type
					approve = true;
				}

			}
		} else {
			approve = super.approveRequest(request);
		}


		return approve;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeCreateCommand(CreateElementRequest request) {
		ICommand command = UnexecutableCommand.INSTANCE;
		// check container.
		EObject container = request.getContainer();
		if ((container instanceof Operation) && ElementTypeUtils.isTypeCompatible(request.getElementType(), UMLElementTypes.PARAMETER)) {
			// To avoid the Unexecutable Command
			command = null;
		} else if ((container instanceof Interface)) {
			command = null;
		}

		IElementType messageSetType = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RT_MESSAGE_SET_ID);
		if (messageSetType instanceof ISpecializationType) { // check at the same time UMLRT element types are correctly loaded
			if (((ISpecializationType) messageSetType).getMatcher().matches(container)) {
				command = null;
			} else if (RTMessageKind.IN_OUT == MessageSetUtils.getMessageKind(container)) {
				// this is really a message set. Should go for the kind
				command = super.getBeforeCreateCommand(request);
			}
		}

		// should not be possible to create except RT interface
		return command;
	}

	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		final Operation operationAsMessageIn = (Operation) request.getElementToConfigure();
		final String name = NamedElementUtil.getDefaultNameWithIncrementFromBase("InOutProtocolMessage", operationAsMessageIn.eContainer().eContents());

		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				// name
				operationAsMessageIn.setName(name);

				return CommandResult.newOKCommandResult(operationAsMessageIn);
			}

		};
	}

}
