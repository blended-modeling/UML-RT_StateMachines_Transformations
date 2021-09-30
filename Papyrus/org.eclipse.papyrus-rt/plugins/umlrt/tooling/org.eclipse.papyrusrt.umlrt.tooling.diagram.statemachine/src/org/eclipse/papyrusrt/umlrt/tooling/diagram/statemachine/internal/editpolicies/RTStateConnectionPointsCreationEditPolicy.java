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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Supplier;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CreateViewCommand;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.SideAffixedNodesCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.command.CreateOrReuseViewCommand;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RelativePortLocation;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.commands.CreateNestedStateMachineDiagramCommand;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.commands.RerouteTransitionsToConnectionPointsCommand;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.IRTPseudostateEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPartUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateMachineDiagramVisualID;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.locator.StateBorderItemHelper;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.State;

/**
 * Default side-affixed nodes creation edit policy for RT state machine diagrams.
 * <p>
 * This will be used by top states or states in compartment to layout their connection points (entry & exit).
 * </p>
 */
class RTStateConnectionPointsCreationEditPolicy extends SideAffixedNodesCreationEditPolicy {

	private final StateBorderItemHelper<Pseudostate> pseudostateHelper = new StateBorderItemHelper<>(
			Pseudostate.class, this::getGraphicalHost, this::getPositionLocator);

	/**
	 * Initializes me.
	 */
	public RTStateConnectionPointsCreationEditPolicy() {
		super();
	}

	IGraphicalEditPart getGraphicalHost() {
		return (IGraphicalEditPart) getHost();
	}

	@Override
	protected Command getCreateElementAndViewCommand(CreateViewAndElementRequest request) {
		return false ? super.getCreateElementAndViewCommand(request) : EditPartInheritanceUtils.getCreateElementAndViewCommand((IGraphicalEditPart) getHost(),
				request, this::getCreateCommand);
	}

	/**
	 * Extend the inherited method to capture the element being added (if any).
	 */
	@Override
	protected ICommand getSetBoundsCommand(CreateViewRequest request, ViewDescriptor descriptor) {
		return pseudostateHelper.getSetBoundsCommand(request, descriptor,
				this::getSize, this::getTypeSize, super::getSetBoundsCommand);
	}

	private Optional<Dimension> getSize(Pseudostate pseudostate) {
		return getConnectionPointKind(pseudostate).map(IRTPseudostateEditPart::getDefaultSize);
	}

	private Optional<Dimension> getTypeSize(IElementType type) {
		return getConnectionPointType(type).map(IRTPseudostateEditPart::getDefaultSize);
	}

	protected Optional<PseudostateKind> getConnectionPointType(IElementType type) {
		if (ElementTypeUtils.isTypeCompatible(type, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_EXIT_POINT)) {
			return Optional.of(PseudostateKind.EXIT_POINT_LITERAL);
		} else if (ElementTypeUtils.isTypeCompatible(type, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_ENTRY_POINT)) {
			return Optional.of(PseudostateKind.ENTRY_POINT_LITERAL);
		}
		return Optional.empty();
	}

	protected Optional<PseudostateKind> getConnectionPointKind(Pseudostate pseudostate) {
		Optional<Pseudostate> pseudo = Optional.ofNullable(pseudostate)
				.filter(Pseudostate.class::isInstance).map(Pseudostate.class::cast);
		return pseudo.map(Pseudostate::getKind)
				.filter(k -> (k == PseudostateKind.ENTRY_POINT_LITERAL) || (k == PseudostateKind.EXIT_POINT_LITERAL));
	}

	@Override
	protected PortPositionLocator getPositionLocator() {
		return pseudostateHelper.getPositionLocator(
				this::getScaleFactor,
				super::getPositionLocator);
	}

	private OptionalDouble getScaleFactor(Pseudostate pseudostate) {
		return getConnectionPointKind(pseudostate)
				.map(kind -> OptionalDouble.of(IRTPseudostateEditPart.getDefaultScaleFactor(kind)))
				.orElseGet(OptionalDouble::empty);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Command getCreateCommand(CreateViewRequest request) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		CompositeTransactionalCommand cc = new CompositeTransactionalCommand(editingDomain, DiagramUIMessages.AddCommand_Label);

		Iterator<? extends ViewDescriptor> descriptors = request.getViewDescriptors().iterator();
		while (descriptors.hasNext()) {

			CreateViewRequest.ViewDescriptor descriptor = descriptors.next();
			// if we want to create a transition as internal transition label, we may have to transform the state into a composite state
			if (RTStateMachineDiagramVisualID.INTERNAL_TRANSITION_LABEL.equals(descriptor.getSemanticHint())) {
				// change the target container to the internal transition container
				View containerView = (View) (getHost().getModel());
				Compartment internalTransitionCompartment = RTStateEditPartUtils.getInternalTransitionCompartment(containerView);
				if (internalTransitionCompartment != null) {
					// create the transition view only if required (e.g. check if canonical policy worked yet)
					State targetState = (State) EditPartInheritanceUtils.resolveSemanticElement(containerView);
					if (!targetState.isComposite()) {
						ICommand composed = getStateAsCompositeCommand(editingDomain, targetState, (IGraphicalEditPart) getHost());
						composed = composed.compose(new CreateOrReuseViewCommand(editingDomain, descriptor, internalTransitionCompartment));
						ICommand setBoundsCommand = getSetBoundsCommand(request, descriptor);
						if (setBoundsCommand != null) {
							// Add SetBounds
							composed = composed.compose(setBoundsCommand);
						}
						cc.compose(composed);
					} else {
						ICommand createCommand = new CreateOrReuseViewCommand(editingDomain, descriptor, internalTransitionCompartment);
						ICommand setBoundsCommand = getSetBoundsCommand(request, descriptor);
						if (setBoundsCommand != null) {
							// Add SetBounds
							createCommand = CompositeCommand.compose(createCommand, setBoundsCommand);
						}
						cc.compose(createCommand);
					}
				}
			} else if (RegionEditPart.VISUAL_ID.equals(descriptor.getSemanticHint())) {
				// not possible to create a region on demand (remove region modeling assistant)
				return UnexecutableCommand.INSTANCE;
			} else {
				// default creation from super class
				ICommand createCommand = new CreateViewCommand(editingDomain, descriptor, (View) (getHost().getModel()));
				ICommand setBoundsCommand = getSetBoundsCommand(request, descriptor);
				if (setBoundsCommand != null) {
					// Add SetBounds
					createCommand = CompositeCommand.compose(createCommand, setBoundsCommand);
				}
				cc.compose(createCommand);
			}
		}

		return new ICommandProxy(cc.reduce());
	}

	private static ICommand getStateAsCompositeCommand(TransactionalEditingDomain domain, State targetState, IGraphicalEditPart stateEditPart) {
		ICommand composed;

		ICommand createDiagram = new CreateNestedStateMachineDiagramCommand(
				domain, targetState, stateEditPart, false);
		Supplier<Diagram> newDiagram = () -> (Diagram) createDiagram.getCommandResult().getReturnValue();
		composed = createDiagram;

		ICommand rerouteTransitions = RerouteTransitionsToConnectionPointsCommand.createRerouteTransitionsCommand(
				domain, targetState, stateEditPart);
		if (rerouteTransitions != null) {
			composed = composed.compose(rerouteTransitions);
		}

		// A command to align connection points around the new diagram frame as
		// around the state
		composed = composed.compose(getConnectionPointMatchingCommand(domain, targetState, stateEditPart, newDiagram));

		return composed;
	}

	private static ICommand getConnectionPointMatchingCommand(TransactionalEditingDomain domain, State state, IGraphicalEditPart stateEP, Supplier<? extends Diagram> newDiagram) {
		return new AbstractTransactionalCommand(domain, "Arrange Connection Points", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				Diagram smd = newDiagram.get();
				View frame = (smd == null) ? null : ViewUtil.getChildBySemanticHint(smd, StateEditPartTN.VISUAL_ID);
				if (frame instanceof Node) {
					// This is a new diagram and we haven't yet opened it, so it
					// will not yet have views for the connection points.
					// Create them now
					Rectangle frameRect = getBounds((Node) frame);
					Rectangle stateRect = getBounds(stateEP);
					View stateView = stateEP.getNotationView();

					for (Pseudostate connPt : state.getConnectionPoints()) {
						// We reverse the roles of "capsule-part" and "capsule" in our
						// case because we synchronize from state shape to frame
						Rectangle connPtRect = getBounds(findChildNode(stateView, connPt));
						RelativePortLocation loc = RelativePortLocation.of(connPtRect, stateRect);

						String semanticHint = (connPt.getKind() == PseudostateKind.ENTRY_POINT_LITERAL)
								? PseudostateEntryPointEditPart.VISUAL_ID
								: PseudostateExitPointEditPart.VISUAL_ID;
						Node onFrame = ViewService.getInstance().createNode(new SemanticAdapter(connPt, null),
								frame,
								semanticHint,
								ViewUtil.APPEND,
								UMLDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);

						// Move the new shape
						Point moveTo = loc.applyTo(frameRect, IRTPseudostateEditPart.getDefaultSize(connPt.getKind()));
						ViewUtil.setStructuralFeatureValue(onFrame, NotationPackage.Literals.LOCATION__X, moveTo.x());
						ViewUtil.setStructuralFeatureValue(onFrame, NotationPackage.Literals.LOCATION__Y, moveTo.y());
					}
				}

				return CommandResult.newOKCommandResult();
			}

			Rectangle getBounds(IGraphicalEditPart editPart) {
				return editPart.getFigure().getBounds().getCopy();
			}

			Node findChildNode(View parent, EObject element) {
				return ((List<?>) parent.getChildren()).stream()
						.filter(Node.class::isInstance).map(Node.class::cast)
						.filter(n -> n.getElement() == element)
						.findFirst().get(); // We know a priori that it exists
			}

			Rectangle getBounds(Node node) {
				Bounds result = (Bounds) node.getLayoutConstraint();
				return new Rectangle(result.getX(), result.getY(), result.getWidth(), result.getHeight());
			}
		};
	}

}
