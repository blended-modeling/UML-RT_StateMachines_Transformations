/*****************************************************************************
 * Copyright (c) 2016, 2017 Zeligsoft (2009) Limited, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *   Young-Soo Roh - Bug#502424
 *   Christian W. Damus - bugs 510188, 511465
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.advice;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.service.types.utils.RequestParameterUtils;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;


/**
 * Edit-helper advice that checks for {@link RTConnector} ends compatibility,
 * both on creation and on re-orient of existing connectors.
 */
public class ConnectorEndPortsCompatibilityAdvice extends AbstractEditHelperAdvice {

	/**
	 * Constructor.
	 *
	 */
	public ConnectorEndPortsCompatibilityAdvice() {
	}

	@Override
	protected ICommand getBeforeCreateRelationshipCommand(CreateRelationshipRequest request) {
		// Only care about connectors
		if (request.getContainmentFeature() != UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR) {
			return null;
		}

		if (!(request.getSource() instanceof Port) || !(request.getTarget() instanceof Port)) {
			return null;
		}

		return getConfirmCompatibilityCommand(request, (Port) request.getSource(), (Port) request.getTarget());
	}

	@Override
	protected ICommand getBeforeReorientRelationshipCommand(ReorientRelationshipRequest request) {
		// Only care about connectors
		if (!(request.getRelationship() instanceof Connector)) {
			return null;
		}

		Connector connector = (Connector) request.getRelationship();
		if (connector.getEnds().size() != 2) {
			// Only binary connectors
			return null;
		}

		// By graphical convention, the 'source' is the first end
		ConnectableElement otherEnd = connector.getEnds().get(
				(request.getDirection() == ReorientRequest.REORIENT_TARGET) ? 0 : 1)
				.getRole();
		if (!(otherEnd instanceof Port) || !(request.getNewRelationshipEnd() instanceof Port)) {
			// Only care about compatibility of ports
			return null;
		}

		return getConfirmCompatibilityCommand(request, (Port) otherEnd, (Port) request.getNewRelationshipEnd());
	}

	/**
	 * Obtains a command that, on execution, confirms compatibility of the ports to be connected, whether
	 * in a new connector or re-orientation of an existing connector. This check is delayed to command
	 * execution because it may interactively prompt the user to proceed.
	 * 
	 * @param request
	 *            the creation or re-orientation request
	 * @param port1
	 *            the port that, by convention, is the 'target' end of a creation request or the end of a
	 *            re-orient request that is being changed
	 * @param port2
	 *            the port that, by convention, is the 'source' end of a creation request or the end of a
	 *            re-orient request that is remaining fixed
	 * @return the confirmation command
	 */
	protected ICommand getConfirmCompatibilityCommand(IEditCommandRequest request, Port port1, Port port2) {
		return new AbstractTransactionalCommand(request.getEditingDomain(), "Check Connection", Collections.EMPTY_LIST) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				boolean canConnect = true;

				// check port type compatibility.
				if (port1.getType() == null || !port1.getType().equals(port2.getType())) {
					canConnect = false;
				}

				if (canConnect) {
					canConnect = checkConjugationCompatibility(request, port1, port2);
				}

				if (!canConnect && TypeUtils.as(request.getParameter(RequestParameterConstants.USE_GUI), true)) {
					Shell shell = Display.getCurrent().getActiveShell();
					if (shell != null) {
						// ask user if still want to connect
						canConnect = MessageDialog.openConfirm(shell, "Incompatible Ports",
								"The two ports are not compatible. Do you still want to create a connector?");
					}
				}

				return canConnect ? CommandResult.newOKCommandResult() : CommandResult.newCancelledCommandResult();
			}
		};
	}

	/**
	 * Check two ports have compatible conjugation.
	 * 
	 * @param request
	 *            the edit-command request
	 * @param port1
	 *            the port that, by convention, is the 'target' end of a creation request or the end of a
	 *            re-orient request that is being changed
	 * @param port2
	 *            the port that, by convention, is the 'source' end of a creation request or the end of a
	 *            re-orient request that is remaining fixed
	 * 
	 * @return true if compatible
	 */
	private boolean checkConjugationCompatibility(final IEditCommandRequest request, final Port port1, final Port port2) {
		boolean result = true;

		View port1View = null;
		View port2View = null;

		if (request instanceof CreateRelationshipRequest) {
			port1View = (View) request.getParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_TARGET_VIEW);
			port2View = (View) request.getParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_SOURCE_VIEW);
		} else {
			// It's a re-orient of existing connector
			Edge reorientedEdge = RequestParameterUtils.getReconnectedEdge(request);

			if (reorientedEdge != null) {
				View newEndView = RequestParameterUtils.getReconnectedEndView(request);
				if (((ReorientRelationshipRequest) request).getDirection() == ReorientRelationshipRequest.REORIENT_SOURCE) {
					port1View = reorientedEdge.getTarget();
					port2View = newEndView;
				} else {
					port1View = reorientedEdge.getSource();
					port2View = newEndView;
				}
			}
		}

		if (port2View != null && port2View.eContainer() != null
				&& port1View != null && port1View.eContainer() != null) {
			EObject port1Container = ((View) port2View.eContainer()).getElement();
			EObject port2Container = ((View) port1View.eContainer()).getElement();

			UMLRTPortKind port1Kind = RTPortUtils.getPortKind(port1);
			UMLRTPortKind port2Kind = RTPortUtils.getPortKind(port2);

			// Connection from or to port of the class should be delegation unless the port is internal behaviour port
			boolean isDelegation = (ElementTypeUtils.matches(port1Container, IUMLRTElementTypes.CAPSULE_ID) && port1.isService())
					|| (ElementTypeUtils.matches(port2Container, IUMLRTElementTypes.CAPSULE_ID) && port2.isService());

			if (!isDelegation) {
				if (port1.isConjugated() == port2.isConjugated()) {
					// assembly connector must have different conjugation
					result = false;
				}
			} else {
				if (port1Container == port2Container && port1Kind == UMLRTPortKind.RELAY && port2Kind == UMLRTPortKind.RELAY) {
					// pass-through connector should have different conjugation.
					if (port1.isConjugated() == port2.isConjugated()) {
						result = false;
					}
				} else if (port1.isConjugated() != port2.isConjugated()) {
					// all delegation connector except pass-through connector must have same conjugation
					result = false;
				}
			}
		}
		return result;
	}
}
