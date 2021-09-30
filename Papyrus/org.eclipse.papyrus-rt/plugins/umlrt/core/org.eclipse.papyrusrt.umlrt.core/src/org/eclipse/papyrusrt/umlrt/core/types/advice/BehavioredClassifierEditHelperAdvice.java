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

import java.util.Arrays;
import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;

/**
 * Edit helper advice to forbid the creation of OperationAsMessages in Any behaviored classifier except the MessageSet kinds
 */
public class BehavioredClassifierEditHelperAdvice extends AbstractEditHelperAdvice {

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
			// element to create is not a rt message, no need to do something on that part...
			if (!types.contains(umlRTMessageType)) {
				return super.approveRequest(request);
			}
			// element to create is a RT message. The behaviored classifier should be a MessageSet...
			IElementType messageSetType = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RT_MESSAGE_SET_ID);
			if (!(messageSetType instanceof ISpecializationType)) {
				return super.approveRequest(request);
			} else {
				if(!((ISpecializationType)messageSetType).getMatcher().matches(createElementRequest.getContainer())) {
					return false;
				}
			}
			return super.approveRequest(request);
		}

		return super.approveRequest(request);
	}
}
