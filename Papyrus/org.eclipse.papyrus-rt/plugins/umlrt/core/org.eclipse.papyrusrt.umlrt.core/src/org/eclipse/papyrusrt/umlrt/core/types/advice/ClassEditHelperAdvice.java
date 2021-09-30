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
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Capsule;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * The helper advice class used for UMLRealTime::ProtocolContainer.
 */
public class ClassEditHelperAdvice extends AbstractEditHelperAdvice {

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
		// retrieve element type from this request and check if this is a kind of UML::Port
		IElementType type = createElementRequest.getElementType();
		// is it UMLRT::Capsule
		Element container = (Element) createElementRequest.getContainer();

		// check if UML-RT profile is applied to avoid influence on the standard UML diagrams
		if (!UMLRTProfileUtils.isUMLRTProfileApplied(container)) {
			return super.approveRequest(createElementRequest);
		}

		Capsule capsule = UMLUtil.getStereotypeApplication(container, Capsule.class);
		boolean isCapsule = (capsule != null);

		IElementType rtStateMachineType = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RT_STATE_MACHINE_ID);
		if (rtStateMachineType != null) {
			if (ElementTypeUtils.isTypeCompatible(type, rtStateMachineType)) {
				if (isCapsule) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			Activator.log.debug("Impossible to find element type for RT StateMachine");
		}


		// type should only be compatible with UML::Port
		IElementType umlPortType = ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.Port");
		// should not be null, otherwise, element type model is not loaded correctly. abort.
		if (umlPortType != null) {
			if (ElementTypeUtils.isTypeCompatible(type, umlPortType)) {
				if (isCapsule) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			Activator.log.debug("Impossible to find element type for UML Port");
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
			// disapprove moving port from one container to another
			// if the port container is null, the move request correspond to a request asked in the paste command
			if (elementToMove instanceof Port && ((Port) elementToMove).eContainer() != null) {
				return false;
			}
		}
		return super.approveRequest(request);
	}
}
