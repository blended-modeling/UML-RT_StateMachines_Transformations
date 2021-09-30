/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 510315, 512846, 510323
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils.resolveSemanticElement;
import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils.isCanonicalRequest;

import java.util.Iterator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FeedbackHelper;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.util.StringStatics;
import org.eclipse.gmf.runtime.diagram.core.commands.SetConnectionAnchorsCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.SetConnectionEndsCommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IDiagramPreferenceSupport;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.internal.commands.SetConnectionBendpointsCommand;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticElementAdapter;
import org.eclipse.papyrus.infra.gmfdiag.common.service.visualtype.VisualTypeService;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.PortPositionEnum;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTLinksLFEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.StateMachineViewUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPartTN;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;

/**
 * Specific {@link GraphicalNodeEditPolicy} to allow transitions to be connected to states via Pseudostates Entry/exit Points.
 */
public class RTStateGraphicalNodeEditPolicy extends RTLinksLFEditPolicy {

	/**
	 * Constructor.
	 */
	public RTStateGraphicalNodeEditPolicy() {
		super();
	}

	@Override
	protected void showCreationFeedback(CreateConnectionRequest request) {
		Point p = new Point(request.getLocation());

		EditPart part = getHost().getViewer().findObjectAt(p);
		if (part != null) {
			part = part.getTargetEditPart(request);
		}
		IFigure figure = null;
		if (part instanceof RTStateEditPartTN) {
			figure = ((RTStateEditPartTN) part).getBorderedFigure().getMainFigure();
		} else if (part instanceof RTStateEditPart) {
			figure = ((RTStateEditPart) part).getBorderedFigure().getMainFigure();
		}
		if (figure != null) {
			FeedbackHelper helper = getFeedbackHelper(request);
			PortPositionLocator locator = new PortPositionLocator(figure);
			locator.setPosition(PortPositionEnum.ONLINE);
			p = locator.getPreferredLocation(new Rectangle(p.x(), p.y(), 0, 0)).getLocation();
			helper.update(new XYAnchor(p), p);
		} else {
			super.showCreationFeedback(request);
		}
	}

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		if (!(request instanceof CreateConnectionViewRequest)) {
			return null;
		}
		CreateConnectionViewRequest req = (CreateConnectionViewRequest) request;
		CompositeCommand cc = new CompositeCommand(DiagramUIMessages.Commands_CreateCommand_Connection_Label);
		Diagram diagramView = ((View) getHost().getModel()).getDiagram();
		TransactionalEditingDomain editingDomain = getEditingDomain();
		CreateCommand createCommand = new CreateCommand(editingDomain, req.getConnectionViewDescriptor(), diagramView.getDiagram());
		setViewAdapter((IAdaptable) createCommand.getCommandResult().getReturnValue());
		SetConnectionEndsCommand sceCommand = new SetConnectionEndsCommand(editingDomain, StringStatics.BLANK);
		sceCommand.setEdgeAdaptor(getViewAdapter());
		if (!isCanonicalRequest(req) && StateMachineViewUtils.getTransitionHint().equals(req.getConnectionViewDescriptor().getSemanticHint())) {
			// handle redirection of transition to exit Pseudostate if state is composite and target is the state
			sceCommand.setNewSourceAdaptor(getTransitionSourceViewAdapter(request));
		} else {
			sceCommand.setNewSourceAdaptor(new EObjectAdapter(getView()));
		}
		ConnectionAnchor sourceAnchor = getConnectableEditPart().getSourceConnectionAnchor(request);
		SetConnectionAnchorsCommand scaCommand = new SetConnectionAnchorsCommand(editingDomain, StringStatics.BLANK);
		scaCommand.setEdgeAdaptor(getViewAdapter());
		scaCommand.setNewSourceTerminal(getConnectableEditPart().mapConnectionAnchorToTerminal(sourceAnchor));
		SetConnectionBendpointsCommand sbbCommand = new SetConnectionBendpointsCommand(editingDomain);
		sbbCommand.setEdgeAdapter(getViewAdapter());
		cc.compose(createCommand);
		cc.compose(sceCommand);
		cc.compose(scaCommand);
		cc.compose(sbbCommand);
		Command c = new ICommandProxy(cc);
		request.setStartCommand(c);
		return c;
	}

	/**
	 * Returns a command that will create the connection.
	 *
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 * @param request
	 *            the request for creating the connection
	 * @return the command
	 */
	@Override
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		ICommandProxy proxy = (ICommandProxy) request.getStartCommand();
		if (proxy == null) {
			return null;
		}
		// reset the target edit-part for the request
		INodeEditPart targetEP = getConnectionCompleteEditPart(request);
		if (targetEP == null) {
			return null;
		}
		CompositeCommand cc = (CompositeCommand) proxy.getICommand();
		ConnectionAnchor targetAnchor = targetEP.getTargetConnectionAnchor(request);
		Iterator commandItr = cc.iterator();
		commandItr.next(); // 0
		SetConnectionEndsCommand sceCommand = (SetConnectionEndsCommand) commandItr.next(); // 1
		if (!isCanonicalRequest(request) && (request instanceof CreateConnectionViewRequest) && StateMachineViewUtils.getTransitionHint().equals(((CreateConnectionViewRequest) request).getConnectionViewDescriptor().getSemanticHint())) {
			// handle redirection of Transition on entry pseudostate
			sceCommand.setNewTargetAdaptor(getTransitionTargetViewAdapter(request));
		} else {
			sceCommand.setNewTargetAdaptor(new EObjectAdapter(((IGraphicalEditPart) targetEP).getNotationView()));
		}
		SetConnectionAnchorsCommand scaCommand = (SetConnectionAnchorsCommand) commandItr.next(); // 2
		scaCommand.setNewTargetTerminal(targetEP.mapConnectionAnchorToTerminal(targetAnchor));
		setViewAdapter(sceCommand.getEdgeAdaptor());
		INodeEditPart sourceEditPart = (INodeEditPart) request.getSourceEditPart();
		ConnectionAnchor sourceAnchor = sourceEditPart.mapTerminalToConnectionAnchor(scaCommand.getNewSourceTerminal());
		PointList pointList = new PointList();
		if (request.getLocation() == null) {
			pointList.addPoint(sourceAnchor.getLocation(targetAnchor.getReferencePoint()));
			pointList.addPoint(targetAnchor.getLocation(sourceAnchor.getReferencePoint()));
		} else {
			pointList.addPoint(sourceAnchor.getLocation(request.getLocation()));
			pointList.addPoint(targetAnchor.getLocation(request.getLocation()));
		}
		SetConnectionBendpointsCommand sbbCommand = (SetConnectionBendpointsCommand) commandItr.next(); // 3
		sbbCommand.setNewPointList(pointList, sourceAnchor.getReferencePoint(), targetAnchor.getReferencePoint());
		return request.getStartCommand();
	}

	/**
	 * Get the adapter to recover the connector source view.
	 *
	 * @param request
	 *            connection creation request
	 * @return adapter
	 */
	private IAdaptable getTransitionSourceViewAdapter(final CreateConnectionRequest request) {
		return new ExtremityViewAdaptable(request.getLocation(), true);
	}

	/**
	 * Get the adapter to recover the connector target view.
	 *
	 * @param request
	 *            connection creation request
	 * @return adapter
	 */
	private IAdaptable getTransitionTargetViewAdapter(final CreateConnectionRequest request) {
		return new ExtremityViewAdaptable(request.getLocation(), false);
	}

	/**
	 * Try and get an extremity view of a transition contained in this host.
	 *
	 * @param isStartEnd
	 *            true if view is the object flow start end, false for the
	 *            target end
	 * @return the view or null
	 */
	protected View getTransitionExtremityView(boolean isStartEnd, Point location) {
		Object transitionView = getViewAdapter().getAdapter(Connector.class);
		if (transitionView instanceof Connector) {
			EObject transition = ((Connector) transitionView).getElement();
			if (transition instanceof Transition) {
				Vertex vertex = null;
				if (isStartEnd) {
					vertex = ((Transition) transition).getSource();
				} else {
					vertex = ((Transition) transition).getTarget();
				}
				if (vertex != null) {
					EditPart host = getHost();
					if (host.getModel() instanceof View) {
						View view = (View) getHost().getModel();
						Vertex ourVertex = resolveSemanticElement(host, Vertex.class);

						if ((vertex == ourVertex) || UMLRTExtensionUtil.redefines(ourVertex, vertex)) {
							return view;
						} else {
							// the host should be the state, but it's not the end of transition. The target is probably a pseudostate to be displayed if not displayed currently.
							view = createPseudoStateView(vertex, (Connector) transitionView, view, location);
							return view;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * @param vertex
	 * @param transitionView
	 * @param view
	 * @return
	 */
	protected View createPseudoStateView(Vertex vertex, Connector transitionView, View view, Point location) {
		// try initially to get view? no need here, it should have been targeted before if present already.
		// so create the view for the given vertex
		IAdaptable adapter = new SemanticElementAdapter(vertex);
		PreferencesHint prefs = PreferencesHint.USE_DEFAULTS;
		if (getHost().getRoot() instanceof IDiagramPreferenceSupport) {
			prefs = ((IDiagramPreferenceSupport) getHost().getRoot()).getPreferencesHint();
		}

		String hint = VisualTypeService.getInstance().getNodeType(view, vertex);

		ViewDescriptor descriptor = new ViewDescriptor(adapter, Node.class, hint, prefs);
		CreateViewRequest request = new CreateViewRequest(descriptor);
		request.setLocation(location);
		Command command = getHost().getCommand(request);
		if ((command != null) && command.canExecute()) {
			command.execute();
		}

		return (Node) descriptor.getAdapter(Node.class);
	}

	protected class ExtremityViewAdaptable implements IAdaptable {

		private Point location;
		private boolean isSource;

		/**
		 * Constructor.
		 */
		public ExtremityViewAdaptable(Point location, boolean isSource) {
			this.location = location;
			this.isSource = isSource;
		}

		@Override
		public Object getAdapter(Class adapter) {
			if (adapter != null && adapter.isAssignableFrom(ShapeImpl.class)) {
				return getTransitionExtremityView(isSource, location);
			}
			return null;
		}

	}

}
