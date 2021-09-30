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
 *   Christian W. Damus - bugs 494367, 497742
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import static org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils.isProtocolContainer;
import static org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils.isProtocol;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * The helper advice class used for UMLRealTime::ProtocolContainer.
 */
public class PackageEditHelperAdvice extends AbstractEditHelperAdvice {

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

		// type should only be compatible with UML::EnumerationLiteral
		IElementType umlEnumerationLiteralType = ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.EnumerationLiteral");
		// should not be null, otherwise, element type model is not loaded correctly. abort.
		if (umlEnumerationLiteralType != null) {
			if (ElementTypeUtils.isTypeCompatible(type, umlEnumerationLiteralType)) {
				return false;
			}
		} else {
			Activator.log.debug("Impossible to find element type for UML EnumerationLiteral");
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
			if (elementToMove instanceof EnumerationLiteral) {
				return false;
			}
		}
		return super.approveRequest(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeMoveCommand(final MoveRequest request) {
		ICommand result = null;

		CompositeCommand compositeMoveCommand = new CompositeCommand("Composite Move Command");

		Map<?, ?> elementsToMove = request.getElementsToMove();
		if (!elementsToMove.isEmpty()) {
			for (Object elementToMove : elementsToMove.keySet()) {
				if (elementToMove instanceof Collaboration) {
					final Element protocol = (Element) elementToMove;
					final Package protocolContainer = protocol.getNearestPackage();
					if (protocolContainer != null) {
						// create the command for moving the protocol container
						ICommand command = ProtocolContainerUtils.createMoveProtocolContainerCommand(request, protocolContainer);
						compositeMoveCommand.add(command);
						// prevent the protocol to be moved independently from the protocol container
						request.setParameter(IEditCommandRequest.REPLACE_DEFAULT_COMMAND, true);
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

	@Override
	protected ICommand getBeforeSetCommand(SetRequest request) {
		ICommand result;

		if (!isProtocolContainer(request.getElementToEdit())
				&& (request.getFeature() instanceof EReference)
				&& (request.getValue() instanceof List<?>)
				&& ((EReference) (request.getFeature())).getEReferenceType().isSuperTypeOf(UMLPackage.Literals.PACKAGE)
				&& ((List<?>) request.getValue()).stream().anyMatch(o -> isProtocol((EObject) o))) {

			// Model Explorer is trying to drop at least one protocol into this package.
			// Drop the protocol container, instead

			// This cast is safe because the feature is a reference and we will
			// not try to replace a protocol with a kind of object that can't
			// also be contained by the destination
			@SuppressWarnings("unchecked")
			List<EObject> newValue = ((List<EObject>) request.getValue()).stream()
					// Replace all protocols by their containers
					.map(o -> ProtocolUtils.isProtocol(o)
							? ProtocolUtils.getProtocolContainer((Collaboration) o)
							: o)
					.collect(Collectors.toList());

			SetRequest newRequest = new SetRequest(request.getEditingDomain(),
					request.getElementToEdit(), request.getFeature(),
					newValue);
			result = new SetValueCommand(newRequest);

			// This command replaces the default set behaviour
			request.setParameter(IEditCommandRequest.REPLACE_DEFAULT_COMMAND, true);
		} else {
			result = super.getBeforeSetCommand(request);
		}

		return result;
	}
}
