/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 467545
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.service.types.utils.RequestParameterConstants;
import org.eclipse.papyrusrt.umlrt.core.commands.ExcludeDependentsRequest;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Connector;

/**
 * Edit Helper Advices for RT connector
 * 
 * @author ysroh bug#474244
 */
public class RTConnectorEditHelperAdvice extends AbstractEditHelperAdvice implements IInheritanceEditHelperAdvice {

	@Override
	protected ICommand getBeforeCreateRelationshipCommand(CreateRelationshipRequest request) {
		return super.getBeforeCreateRelationshipCommand(request);
	}

	@Override
	protected ICommand getBeforeCreateCommand(CreateElementRequest request) {
		return super.getBeforeCreateCommand(request);
	}

	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		if (request instanceof ReorientRelationshipRequest) {
			EObject relationship = ((ReorientRelationshipRequest) request).getRelationship();
			if (ElementTypeUtils.matches(relationship, IUMLRTElementTypes.RT_CONNECTOR_ID)) {
				Connector connector = (Connector) relationship;
				EObject target = ((ReorientRelationshipRequest) request).getNewRelationshipEnd();
				View targetView = (View) request.getParameter(RequestParameterConstants.EDGE_REORIENT_REQUEST_END_VIEW);
				if (targetView != null && targetView.eContainer() != null) {
					EObject partWithTargetPort = ((View) targetView.eContainer()).getElement();
					EObject oldEnd = ((ReorientRelationshipRequest) request).getOldRelationshipEnd();
					EObject source = connector.getEnds().get(0).getRole();
					EObject partWithSourcePort = connector.getEnds().get(0).getPartWithPort();
					if (oldEnd.equals(source)) {
						source = connector.getEnds().get(1).getRole();
						partWithSourcePort = connector.getEnds().get(1).getPartWithPort();
					}
					return canConnect(source, partWithSourcePort, target, partWithTargetPort);
				}
			}
			return super.approveRequest(request);
		}
		if (request instanceof GetEditContextRequest) {
			if (((GetEditContextRequest) request).getEditCommandRequest() instanceof CreateRelationshipRequest) {
				CreateRelationshipRequest editRequest = (CreateRelationshipRequest) ((GetEditContextRequest) request).getEditCommandRequest();
				View targetView = (View) editRequest.getParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_TARGET_VIEW);
				View sourceView = (View) editRequest.getParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_SOURCE_VIEW);
				if (sourceView != null && sourceView.eContainer() != null && targetView != null && targetView.eContainer() != null) {
					// check to see if the port is from capsule part rather than from the capsule
					EObject partWithSourcePort = ((View) sourceView.eContainer()).getElement();
					EObject partWithTargetPort = ((View) targetView.eContainer()).getElement();
					return canConnect(editRequest.getSource(), partWithSourcePort, editRequest.getTarget(), partWithTargetPort);
				}
			}
		}

		return super.approveRequest(request);
	}

	/**
	 * Query if the connection can be made between two ends.
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	protected boolean canConnect(final EObject source, final EObject partWithSourcePort, final EObject target, final EObject partWithTargetPort) {

		if (source == target || !checkEnd(source) || !checkEnd(target)) {
			return false;
		}

		// And there is one (implicitly) invalid case that is not prohibited (which has been discussed extensively in
		// Bug 492737 and which was marked a duplicate of this one), and that is the case with an external behavior port,
		// which still is possible to connect to a port on a capsule part. An external behavior should never be allowed
		// to be connected "on the inside". Such a port is only allowed to be connected "on the outside".

		if ((partWithSourcePort == null || !ElementTypeUtils.matches(partWithSourcePort, IUMLRTElementTypes.CAPSULE_PART_ID))
				&& ElementTypeUtils.matches(source, IUMLRTElementTypes.EXTERNAL_BEHAVIOR_PORT_ID)) {
			return false;
		}

		if ((partWithTargetPort == null || !ElementTypeUtils.matches(partWithTargetPort, IUMLRTElementTypes.CAPSULE_PART_ID))
				&& ElementTypeUtils.matches(target, IUMLRTElementTypes.EXTERNAL_BEHAVIOR_PORT_ID)) {
			return false;
		}

		return true;
	}

	/**
	 * Check to see if the end is valid.
	 * 
	 * @param newEnd
	 * @return
	 */
	protected boolean checkEnd(EObject newEnd) {

		if (newEnd != null && ElementTypeUtils.matches(newEnd, IUMLRTElementTypes.RT_PORT_ID)) {
			// It should never be allowed to create a connector from/to an unwired port, i.e. SAP or SPP.
			if (ElementTypeUtils.matches(newEnd, IUMLRTElementTypes.SERVICE_ACCESS_POINT_ID)) {
				return false;
			} else if (ElementTypeUtils.matches(newEnd, IUMLRTElementTypes.SERVICE_PROVISION_POINT_ID)) {
				return false;
			}
			return true;
		}

		return false;
	}

	@Override
	protected ICommand getBeforeReorientRelationshipCommand(ReorientRelationshipRequest request) {
		return super.getBeforeReorientRelationshipCommand(request);
	}

	@Override
	public ICommand getBeforeEditCommand(IEditCommandRequest request) {
		ICommand result = getInheritanceEditCommand(request);

		if (result == null) {
			result = super.getBeforeEditCommand(request);
		}

		return result;
	}

	@Override
	public ICommand getExcludeDependentsCommand(ExcludeDependentsRequest request) {
		ICommand result = IInheritanceEditHelperAdvice.super.getExcludeDependentsCommand(request);

		if (!request.isExclude() && (request.getElementToExclude() instanceof Connector)) {
			// Re-inherit connected elements, also
			UMLRTConnector connector = UMLRTConnector.getInstance((Connector) request.getElementToExclude());
			if (connector != null) {
				List<? extends ConnectableElement> connectablesToReinherit = Stream.of(connector.getSource(), connector.getSourcePartWithPort(),
						connector.getTarget(), connector.getTargetPartWithPort())
						.filter(Objects::nonNull)
						.filter(UMLRTNamedElement::isExcluded)
						.map(UMLRTReplicatedElement::toUML)
						.collect(Collectors.toList());

				if (!connectablesToReinherit.isEmpty()) {
					ICommand excludeConnectors = request.getExcludeDependentsCommand(connectablesToReinherit);
					if (excludeConnectors != null) {
						result = CompositeCommand.compose(result, excludeConnectors);
					}
				}
			}
		}

		return result;
	}
}
