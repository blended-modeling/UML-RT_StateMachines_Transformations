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

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;

/**
 * The helper advice class used for UMLRealTime::ProtocolContainer.
 */
public class EnumerationEditHelperAdvice extends AbstractEditHelperAdvice {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		if (request instanceof CreateElementRequest) {
			return approveCreateElementRequest(request);
		} else if (request instanceof MoveRequest) {
			return approveMoveRequest(request);
		} else {
			return super.approveRequest(request);
		}
	}

	protected boolean approveCreateElementRequest(IEditCommandRequest request) {
		CreateElementRequest createElementRequest = ((CreateElementRequest) request);
		// retrieve element type from this request and check if this is a kind of UML::EnumerationLiteral
		IElementType type = createElementRequest.getElementType();

		Element container = (Element) createElementRequest.getContainer();

		// check if UML-RT profile is applied to avoid influence on the standard UML diagrams
		if (!UMLRTProfileUtils.isUMLRTProfileApplied(container)) {
			return super.approveRequest(createElementRequest);
		}

		// type should only be compatible with UML::Attribute
		IElementType umlAttributeType = ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.Property");		
		// should not be null, otherwise, element type model is not loaded correctly. abort.
		if (umlAttributeType != null) {
			if (ElementTypeUtils.isTypeCompatible(type, umlAttributeType)) {
				return false;
			}
		} else {
			Activator.log.debug("Impossible to find element type for UML Attribute");
		}
		
		IElementType umlOperationType = ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.Operation");
		if (umlOperationType != null) {
			if (ElementTypeUtils.isTypeCompatible(type, umlOperationType)) {
				return false;
			}
		} else {
			Activator.log.debug("Impossible to find element type for UML Operation");
		} 
		
		return super.approveRequest(request);
	}
	
	protected boolean approveMoveRequest(IEditCommandRequest request) {
		MoveRequest moveRequest = (MoveRequest) request;
		EObject targetContainer = moveRequest.getTargetContainer();
		if (targetContainer instanceof Element) {
			if (!UMLRTProfileUtils.isUMLRTProfileApplied((Element) targetContainer)) {
				return super.approveRequest(request);
			}
		}
		Map<?, ?> elementsToMove = moveRequest.getElementsToMove();
		for (Object elementToMove : elementsToMove.keySet()) {
			if ((elementToMove instanceof Property)||(elementToMove instanceof Operation)) {
				return false;
			}
		}
		return super.approveRequest(request);
	}
}
