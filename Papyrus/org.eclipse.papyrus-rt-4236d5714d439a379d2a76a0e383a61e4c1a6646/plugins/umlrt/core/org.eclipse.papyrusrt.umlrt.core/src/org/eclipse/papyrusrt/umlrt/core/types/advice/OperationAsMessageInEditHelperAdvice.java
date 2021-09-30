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
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageSetUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.uml2.uml.Interface;

/**
 * Specialization of the Operation as message in message sets, but specific to message sets with kind=IN
 */
public class OperationAsMessageInEditHelperAdvice extends OperationAsMessageEditHelperAdvice {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		if (request instanceof CreateElementRequest) {
			// check container.
			EObject container = ((CreateElementRequest) request).getContainer();
			if (!(container instanceof Interface)) {
				return false;
			}

			IElementType messageSetType = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RT_MESSAGE_SET_ID);
			if (messageSetType instanceof ISpecializationType) { // check at the same time UMLRT element types are correctly loaded
				if (!((ISpecializationType) messageSetType).getMatcher().matches(container)) {
					return false;
				}

				// this is really a message set. Should go for the kind
				if (RTMessageKind.IN == MessageSetUtils.getMessageKind(container)) {
					return true;
				}
			}

			// should not be possible to create except RT interface
			return false;
		}


		return super.approveRequest(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeCreateCommand(CreateElementRequest request) {
		// check container.
		EObject container = request.getContainer();
		if (!(container instanceof Interface)) {
			return UnexecutableCommand.INSTANCE;
		}

		IElementType messageSetType= ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RT_MESSAGE_SET_ID);
		if (messageSetType instanceof ISpecializationType) { // check at the same time UMLRT element types are correctly loaded
			if (!((ISpecializationType) messageSetType).getMatcher().matches(container)) {
				return UnexecutableCommand.INSTANCE;
			}

			// this is really a message set. Should go for the kind
			if (RTMessageKind.IN == MessageSetUtils.getMessageKind(container)) {
				return super.getBeforeCreateCommand(request);
			}
		}

		// should not be possible to create except RT interface
		return UnexecutableCommand.INSTANCE;
	}

}
