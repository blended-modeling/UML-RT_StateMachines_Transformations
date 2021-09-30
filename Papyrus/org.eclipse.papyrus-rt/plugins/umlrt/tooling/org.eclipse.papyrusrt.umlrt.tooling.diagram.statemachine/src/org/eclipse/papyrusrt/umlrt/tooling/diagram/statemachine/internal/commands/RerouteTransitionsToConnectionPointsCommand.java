/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.commands;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.AbstractPointListShape;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IDiagramPreferenceSupport;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticElementAdapter;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Re-route all transitions incoming to a state to a new entry-point and transitions
 * outgoing the state from an exit-point.
 */
public class RerouteTransitionsToConnectionPointsCommand {

	/**
	 * Not instantiable by clients.
	 */
	private RerouteTransitionsToConnectionPointsCommand() {
		super();
	}

	/**
	 * Obtains a command that re-routes incoming transitions into a {@code state} to
	 * entry points and outgoing transitions from a {@code state} to exit points, if any.
	 * 
	 * @param domain
	 *            the contextual editing domain
	 * @param state
	 *            a state
	 * @param stateEditPart
	 *            its edit-part in the diagram
	 * 
	 * @return the transition re-routing command, or {@code null} if there are no
	 *         transitions incoming or outgoing the {@code state}
	 */
	public static ICommand createRerouteTransitionsCommand(TransactionalEditingDomain domain, State state, IGraphicalEditPart stateEditPart) {
		ICommand result = null;

		UMLRTState rtState = UMLRTState.getInstance(state);
		if (rtState == null) {
			return result;
		}

		List<UMLRTTransition> incomingTransitions = rtState.getIncomings();
		List<UMLRTTransition> outgoingTransitions = rtState.getOutgoings();

		if (!incomingTransitions.isEmpty() || !outgoingTransitions.isEmpty()) {
			// Have some work to do
			CompositeTransactionalCommand composite = new CompositeTransactionalCommand(domain, "Refactor Transitions");

			// Note that some transitions could be in both lists! They will each need
			// an entry and an exit point
			Stream.concat(
					incomingTransitions.stream().map(in -> rerouteTransition(domain,
							in, rtState, stateEditPart, UMLRTConnectionPointKind.ENTRY)),
					outgoingTransitions.stream().map(out -> rerouteTransition(domain,
							out, rtState, stateEditPart, UMLRTConnectionPointKind.EXIT)))
					.filter(Objects::nonNull)
					.forEach(composite::compose);

			result = composite.isEmpty() ? null : composite.reduce();
		}

		return result;
	}

	private static ICommand rerouteTransition(TransactionalEditingDomain domain, UMLRTTransition transition, UMLRTState state, IGraphicalEditPart stateEditPart, UMLRTConnectionPointKind kind) {
		ICommand result = null;

		ConnectionEditPart connection = findConnection(transition, stateEditPart);
		if (connection != null) {
			// Where on the state does it connect?
			Point anchor = getAnchorPoint(connection, stateEditPart, kind == UMLRTConnectionPointKind.EXIT);
			if (anchor != null) {
				result = new AbstractTransactionalCommand(domain, "Refactor Transition", null) {

					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
						// Put a connection point of the appropriate kind there
						UMLRTConnectionPoint connectionPoint = createConnectionPoint(domain, state, kind);
						if (connectionPoint == null) {
							return CommandResult.newCancelledCommandResult();
						}

						Node connPtView = createNode(domain, connectionPoint, stateEditPart, anchor);
						Edge transitionView = (Edge) connection.getModel();

						// Re-route the transition to the connection point
						switch (kind) {
						case ENTRY:
							transition.setTarget(connectionPoint);
							transitionView.setTarget(connPtView);
							break;
						default:
							transition.setSource(connectionPoint);
							transitionView.setSource(connPtView);
							break;
						}
						// do not add the connectionPoint in the result to avoid selecting them at the end, when creating new element
						return CommandResult.newOKCommandResult();
					}
				};
			}
		}

		return result;
	}

	/**
	 * Determines the anchor point on a {@code state} at which a {@code transition} connects.
	 * 
	 * @param transition
	 *            a transition
	 * @param state
	 *            a state that it is incoming to or outgoing from
	 * @param source
	 *            whether to get the transition's source or target anchor
	 * 
	 * @return the anchor point on the {@code state}, or {@code null} if it cannot be determined
	 */
	private static Point getAnchorPoint(ConnectionEditPart transition, IGraphicalEditPart state, boolean source) {
		Point result = null;
		final IFigure figure = transition.getFigure();

		if (figure instanceof AbstractPointListShape) {
			result = source
					? ((AbstractPointListShape) figure).getStart().getCopy()
					: ((AbstractPointListShape) figure).getEnd().getCopy();
			figure.translateToAbsolute(result); // Mouse coordinates
		}

		return result;
	}

	private static ConnectionEditPart findConnection(UMLRTTransition transition, IGraphicalEditPart stateEditPart) {
		Stream<IGraphicalEditPart> connections = Stream.concat(
				((List<?>) stateEditPart.getTargetConnections()).stream(),
				((List<?>) stateEditPart.getSourceConnections()).stream())
				.filter(ConnectionEditPart.class::isInstance)
				.filter(IGraphicalEditPart.class::isInstance)
				.map(IGraphicalEditPart.class::cast);
		return connections.filter(ep -> ep.resolveSemanticElement() == transition.toUML())
				.findAny().map(ConnectionEditPart.class::cast).orElse(null);
	}

	private static UMLRTConnectionPoint createConnectionPoint(TransactionalEditingDomain domain, UMLRTState owner, UMLRTConnectionPointKind kind) throws ExecutionException {
		Pseudostate result = null;

		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(owner.toUML());
		IElementType typeToCreate = kind == UMLRTConnectionPointKind.ENTRY
				? UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_ENTRY_POINT
				: UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_EXIT_POINT;
		CreateElementRequest create = new CreateElementRequest(domain, owner.toUML(), typeToCreate);
		create.setContainmentFeature(UMLPackage.Literals.STATE__CONNECTION_POINT);
		ICommand command = edit.getEditCommand(create);
		if ((command != null) && command.canExecute()) {
			command.execute(new NullProgressMonitor(), null);
			result = (Pseudostate) command.getCommandResult().getReturnValue();
		}

		return (result == null) ? null : UMLRTConnectionPoint.getInstance(result);
	}

	private static Node createNode(TransactionalEditingDomain domain, UMLRTConnectionPoint connectionPoint, IGraphicalEditPart owner, Point location) {
		IAdaptable adapter = new SemanticElementAdapter(connectionPoint.toUML());
		PreferencesHint prefs = PreferencesHint.USE_DEFAULTS;
		if (owner.getRoot() instanceof IDiagramPreferenceSupport) {
			prefs = ((IDiagramPreferenceSupport) owner.getRoot()).getPreferencesHint();
		}
		String hint = connectionPoint.getKind() == UMLRTConnectionPointKind.ENTRY
				? PseudostateEntryPointEditPart.VISUAL_ID
				: PseudostateExitPointEditPart.VISUAL_ID;

		ViewDescriptor descriptor = new ViewDescriptor(adapter, Node.class, hint, prefs);
		CreateViewRequest request = new CreateViewRequest(descriptor);
		request.setLocation(location);
		Command command = owner.getCommand(request);
		if ((command != null) && command.canExecute()) {
			command.execute();
		}

		return (Node) descriptor.getAdapter(Node.class);
	}
}
