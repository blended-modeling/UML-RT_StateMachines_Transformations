/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 476126
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.requests.UnsetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrusrt.umlrt.core.utils.IRealTimeConstants;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * The helper advice class used for CallEvent in the RealTime context.
 *
 */
public class CallEventEditHelperAdvice extends AbstractEditHelperAdvice {


	/**
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		ICommand command = new CompositeCommand("");
		// Assign the Operation in the request parameter to the Call Event.
		command.compose(getSetOperationCommand(request));
		// unset the call event name
		command.compose(getUnSetNameCommand(request));

		return command;
	}

	/**
	 * Set Operation of the Call Event
	 * 
	 * @param request
	 *        The request of Call Event Configuration
	 * @param command
	 *        The Compose command
	 * @return
	 */
	protected ICommand getSetOperationCommand(ConfigureRequest request) {
		ICommand setOperation = null;
		Operation operation = getOperation(request);

		SetRequest setOperationRequest = new SetRequest(request.getEditingDomain(), request.getElementToConfigure(), UMLPackage.eINSTANCE.getCallEvent_Operation(), operation);

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(request.getElementToConfigure());
		if(provider != null) {
			ICommand createCommand = provider.getEditCommand(setOperationRequest);

			if(null != createCommand) {
				setOperation = createCommand;
			} else {
				setOperation = super.getAfterConfigureCommand(request);
			}
		}
		return setOperation;
	}

	/**
	 * Set Name of the Call Event
	 * 
	 * @param request
	 *        The request of Call Event Configuration
	 * 
	 * @return The Command that set the Name of the CallEvent
	 */
	protected ICommand getUnSetNameCommand(ConfigureRequest request) {
		ICommand unsetName = null;

		UnsetRequest unsetNameRequest = new UnsetRequest(request.getEditingDomain(), request.getElementToConfigure(), UMLPackage.eINSTANCE.getNamedElement_Name());

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(request.getElementToConfigure());
		if(provider != null) {
			ICommand unsetNameCommand = provider.getEditCommand(unsetNameRequest);

			if(null != unsetNameCommand) {
				unsetName = unsetNameCommand;
			} else {
				unsetName = super.getAfterConfigureCommand(request);
			}
		}
		return unsetName;
	}
	
	/**
	 * Set Name of the Call Event
	 * 
	 * @param request
	 *        The request of Call Event Configuration
	 * 
	 * @return The Command that set the Name of the CallEvent
	 */
	protected ICommand getSetNameCommand(ConfigureRequest request) {
		ICommand setName = null;

		SetRequest setNameRequest = new SetRequest(request.getEditingDomain(), request.getElementToConfigure(), UMLPackage.eINSTANCE.getNamedElement_Name(), null);

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(request.getElementToConfigure());
		if(provider != null) {
			ICommand setNameCommand = provider.getEditCommand(setNameRequest);

			if(null != setNameCommand) {
				setName = setNameCommand;
			} else {
				setName = super.getAfterConfigureCommand(request);
			}
		}
		return setName;
	}

	/**
	 * Retrieve the operation into the passed request parameter
	 * 
	 * @param request
	 *        The Request in which the parameter is looking for.
	 * @return The Operation retrieve or null otherwise.
	 */
	public Operation getOperation(IEditCommandRequest request) {
		Operation operation = null;

		Object parameter = request.getParameter(IRealTimeConstants.CALL_EVENT_OPERATION_PARAMETER_NAME);
		if(parameter instanceof Operation) {
			operation = (Operation)parameter;
		}
		return operation;

	}

}
