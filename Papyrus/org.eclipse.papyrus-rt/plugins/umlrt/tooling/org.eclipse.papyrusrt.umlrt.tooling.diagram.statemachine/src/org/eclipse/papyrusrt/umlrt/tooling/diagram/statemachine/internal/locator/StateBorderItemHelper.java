/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.locator;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils.findView;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.snap.NodeSnapHelper;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;
import org.eclipse.papyrusrt.umlrt.core.utils.Either;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RelativePortLocation;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.locator.BorderItemHelper;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTStateMachineDiagramUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPartTN;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.State;

/**
 * BorderItemHelper for connection Pseudostate on states, that will position the pseudo states in a relative position compared to their "twin" representation on the inside/outside view for a composite state.
 * 
 * @param <E>
 *            the pseudo state to position.
 */
public class StateBorderItemHelper<E extends Pseudostate> extends BorderItemHelper<E> {

	/**
	 * Constructor.
	 */
	public StateBorderItemHelper(java.lang.Class<? extends E> semanticType,
			Supplier<? extends IGraphicalEditPart> hostSupplier,
			Supplier<? extends PortPositionLocator> positionLocatorSupplier) {

		super(semanticType, hostSupplier, positionLocatorSupplier);
	}

	/**
	 * Overrides for figures that do not have yet their bounds.
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.diagram.common.locator.BorderItemHelper#basicGetSetBoundsCommand(org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest, org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor,
	 *      org.eclipse.draw2d.geometry.Dimension)
	 *
	 * @param request
	 * @param descriptor
	 * @param size
	 * @return
	 */
	@Override
	protected ICommand basicGetSetBoundsCommand(CreateViewRequest request, CreateViewRequest.ViewDescriptor descriptor, Dimension size) {
		ICommand setBoundsCommand = null;
		IGraphicalEditPart host = hostSupplier.get();
		IFigure hostFigure = host.getFigure();
		if (hostFigure.getBounds().isEmpty()) {
			return getDefferedSetBoundsCommand(host.getEditingDomain(), request, descriptor, size);
		}
		TransactionalEditingDomain editingDomain = host.getEditingDomain();
		// Retrieve parent location
		Point parentLoc = hostFigure.getBounds().getLocation().getCopy();

		Point requestedLocation = computeLocation(request, descriptor, size);
		hostFigure.translateToRelative(requestedLocation);
		// Create proposed creation bounds and use the locator to find the expected position
		PortPositionLocator locator = positionLocatorSupplier.get();
		if (locator == null) {
			return null;
		}
		final Rectangle preferredBounds = locator.getPreferredLocation(new Rectangle(requestedLocation, size));
		Rectangle retainedBounds = preferredBounds.getCopy();
		// find the current side of the wanted position
		final Rectangle parentBounds = hostFigure.getBounds().getCopy();
		// break all!!! getHostFigure().translateToAbsolute(parentBounds);
		locator.setConstraint(preferredBounds.getCopy().translate(parentBounds.getLocation().getNegated()));
		int currentSide = locator.getCurrentSideOfParent();
		if (request.isSnapToEnabled() && currentSide != PositionConstants.NORTH_EAST && currentSide != PositionConstants.NORTH_WEST && currentSide != PositionConstants.SOUTH_EAST && currentSide != PositionConstants.SOUTH_WEST) { // request for snap port at the
																																																										// // creation
			// we find the best location with snap
			Point wantedPoint = preferredBounds.getLocation();
			hostFigure.translateToAbsolute(wantedPoint);
			Rectangle portBounds = new Rectangle(wantedPoint, size);
			NodeSnapHelper helper = new NodeSnapHelper(host.getAdapter(SnapToHelper.class), portBounds, false, false, true);
			final ChangeBoundsRequest tmpRequest = new ChangeBoundsRequest("move"); //$NON-NLS-1$
			tmpRequest.setEditParts(Collections.emptyList());
			tmpRequest.setSnapToEnabled(true);
			tmpRequest.setLocation(portBounds.getLocation());
			helper.snapPoint(tmpRequest);
			preferredBounds.translate(tmpRequest.getMoveDelta());
			switch (currentSide) {
			case PositionConstants.NORTH:
			case PositionConstants.SOUTH:
				preferredBounds.y = retainedBounds.y;
				break;
			case PositionConstants.EAST:
			case PositionConstants.WEST:
				preferredBounds.x = retainedBounds.x;
				break;
			default:
				break;
			}
		}
		// Convert the calculated preferred bounds as relative to parent location
		Rectangle creationBounds = preferredBounds.getTranslated(parentLoc.getNegated());
		setBoundsCommand = new SetBoundsCommand(editingDomain, DiagramUIMessages.SetLocationCommand_Label_Resize, descriptor, creationBounds);
		return setBoundsCommand;
	}

	/**
	 * @param request
	 * @param descriptor
	 * @param size
	 * @return
	 */
	private ICommand getDefferedSetBoundsCommand(TransactionalEditingDomain domain, CreateViewRequest request, CreateViewRequest.ViewDescriptor descriptor, Dimension size) {
		// locations are not available yet (as for example edit policy that refreshes too early)
		return new DefferedSetBoundsCommand(domain, request, descriptor, size);
	}

	@Override
	protected Point computeLocation(CreateViewRequest request, CreateViewRequest.ViewDescriptor descriptor, Dimension size) {
		Point location = super.computeLocation(request, descriptor, size);

		// if location is still default location (-1, -1), try to compute location according to canonical mode and equivalent pseudo state view location on the other view of the state.
		// getElement should not be null, as the element should be already created (canonical display of an existing view)
		if (getElement() != null && UMLRTEditPartUtils.isCanonicalRequest(request)) {
			// retrieve equivalent view on other side and get its view position. if top node, look at diagram of super state(machine). If child node, look at inside diagram
			IGraphicalEditPart part = hostSupplier.get();
			@SuppressWarnings("unchecked")
			Optional<View> refStateView = Either.cast(part, RTStateEditPart.class, RTStateEditPartTN.class)
					.map(this::findStateViewAsTopState, this::findStateViewAsChildState)
					.orElse(Optional.class, Optional.empty());

			if (refStateView.isPresent()) {
				Bounds refStateBounds = TypeUtils.as(((Node) refStateView.get()).getLayoutConstraint(), Bounds.class);
				View refConnectionView = findView(refStateView.get(), descriptor.getSemanticHint(), getElement());
				if (refConnectionView == null) {
					// still provide a default location?
					return location;
				}
				Bounds refConnectionBounds = TypeUtils.as(((Node) refConnectionView).getLayoutConstraint(), Bounds.class);
				Bounds hostBounds = TypeUtils.as(((Node) part.getNotationView()).getLayoutConstraint(), Bounds.class);
				if (refConnectionBounds != null && refStateBounds != null && hostBounds != null) {
					Rectangle refState = new Rectangle(refStateBounds.getX(), refStateBounds.getY(), refStateBounds.getWidth(), refStateBounds.getHeight());

					// ref connection location must be relative to parent
					Rectangle refConnection = new Rectangle(refConnectionBounds.getX(), refConnectionBounds.getY(), refConnectionBounds.getWidth(), refConnectionBounds.getHeight());
					Rectangle updatedState = new Rectangle(hostBounds.getX(), hostBounds.getY(), hostBounds.getWidth(), hostBounds.getHeight());

					RelativePortLocation loc = RelativePortLocation.of(refConnection, refState);

					// compute equivalent location for the other representation
					location = loc.applyTo(updatedState, size);
					// move to "absolute location"
					location.translate(updatedState.getTopLeft());
					part.getFigure().translateToAbsolute(location);
				}
			}
		}
		return location;
	}

	protected Optional<View> findStateViewAsTopState(RTStateEditPart part) {
		return Optional.of(EditPartInheritanceUtils.resolveSemanticElement(part, State.class))
				.map(UMLRTStateMachineDiagramUtils::getStateMachineDiagram)
				.map(d -> ViewUtil.getChildBySemanticHint(d, StateEditPartTN.VISUAL_ID));
	}

	protected Optional<View> findStateViewAsChildState(RTStateEditPartTN part) {
		UMLRTState state = UMLRTState.getInstance(EditPartInheritanceUtils.resolveSemanticElement(part, State.class));
		if (state == null) {
			return Optional.empty();
		}
		Diagram diagram = Either.of(Optional.ofNullable(state.getState()), Optional.ofNullable(state.getStateMachine()))
				.map(UMLRTState::toUML, UMLRTStateMachine::toUML)
				.map(UMLRTStateMachineDiagramUtils::getStateMachineDiagram, UMLRTStateMachineDiagramUtils::getStateMachineDiagram)
				.getEither(Diagram.class);

		Optional<View> view = Optional.of(diagram).map(d -> findView(d, StateEditPart.VISUAL_ID, state.toUML()));

		return view;

	}

	protected class DefferedSetBoundsCommand extends SetBoundsCommand implements Runnable {

		private CreateViewRequest request;
		private CreateViewRequest.ViewDescriptor descriptor;
		private Dimension dimension;

		/**
		 * Constructor.
		 *
		 * @param editingDomain
		 * @param label
		 * @param adapter
		 * @param location
		 */
		public DefferedSetBoundsCommand(TransactionalEditingDomain editingDomain, CreateViewRequest request, CreateViewRequest.ViewDescriptor descriptor, Dimension dimension) {
			super(editingDomain, "Deferred Command", descriptor, dimension);
			this.request = request;
			this.descriptor = descriptor;
			this.dimension = dimension;
		}

		/**
		 * @see org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
		 *
		 * @param monitor
		 * @param info
		 * @return
		 * @throws ExecutionException
		 */
		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			DiagramHelper.asyncExec(hostSupplier.get(), this);
			return CommandResult.newOKCommandResult();
		}

		/**
		 * @see java.lang.Runnable#run()
		 *
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			// retrieve the new pseudo state edit part and move it to the right place.
			EObject newE = descriptor.getElementAdapter().getAdapter(EObject.class);
			if (newE instanceof Pseudostate) {
				element = (E) newE;
			}
			EditPart pseudoStateEP = hostSupplier.get().findEditPart(hostSupplier.get(), newE);

			// new location
			Point location = computeLocation(request, descriptor, dimension);
			// translate back again to relative...
			hostSupplier.get().getFigure().translateToRelative(location);
			location.translate(hostSupplier.get().getFigure().getBounds().getTopLeft().getNegated());

			SetBoundsCommand command = new SetBoundsCommand(getEditingDomain(), getLabel(), pseudoStateEP, location);
			if (command != null && command.canExecute()) {
				try {
					command.execute(new NullProgressMonitor(), null);
				} catch (ExecutionException e) {
					Activator.log.error(e);
				}
			}
		}
	}
}

