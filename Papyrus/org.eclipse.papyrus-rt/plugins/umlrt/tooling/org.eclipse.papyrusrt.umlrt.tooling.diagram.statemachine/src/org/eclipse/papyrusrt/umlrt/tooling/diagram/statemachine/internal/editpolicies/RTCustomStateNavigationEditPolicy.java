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

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTStateMachineDiagramUtils.getStateMachineDiagram;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.Decoration;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.editpolicies.NavigationEditPolicy;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrusrt.umlrt.common.ui.preferences.DialogPreferences;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RelativePortLocation;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.commands.CreateNestedStateMachineDiagramCommand;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.commands.RerouteTransitionsToConnectionPointsCommand;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.commands.SetVisibilityCommand;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.IRTPseudostateEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.InternalTransitionsCompartmentEditPart;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.State;

/**
 * Custom navigation edit-policy for state in RT state machine diagrams, which
 * implements default double-click handling to convert a state into a composite
 * state with a nested diagram.
 */
public class RTCustomStateNavigationEditPolicy extends NavigationEditPolicy {
	private static final String CONFIRMATION_DIALOG_ID = Activator.PLUGIN_ID + ".confirmCreateNestedSMD"; //$NON-NLS-1$

	/**
	 * Initializes me.
	 */
	public RTCustomStateNavigationEditPolicy() {
		super();
	}

	/**
	 * Override the default behaviour in the case that a state does not yet have
	 * a nested state machine diagram, to always create that diagram and open it.
	 * Thereafter, delegate to the default behaviour because the initial diagram will
	 * initially be a default navigation but the user can customize it.
	 */
	@Override
	protected Command getOpenCommand(Request request) {
		Command result;

		if (!(request instanceof SelectionRequest)) {
			return super.getOpenCommand(request);
		}
		Point location = ((SelectionRequest) request).getLocation().getCopy();
		if (getHost() instanceof IGraphicalEditPart) {
			IGraphicalEditPart editPart = (IGraphicalEditPart) getHost();
			IFigure root = FigureUtilities.getRoot(editPart.getFigure());
			if (root != null) {
				IFigure targetFigure = root.findFigureAt(location);
				if (targetFigure != null) {
					// is this the decorator for state with internal transitions?
					if (isInternalTransitionsDecorator(targetFigure)) {
						// simulate a showHideCompartmentRequest
						EditPart compartmentEditPart = editPart.getChildBySemanticHint(InternalTransitionsCompartmentEditPart.VISUAL_ID);
						if (compartmentEditPart instanceof IGraphicalEditPart) {
							View internalTransitionsView = ((IGraphicalEditPart) compartmentEditPart).getNotationView();
							if (internalTransitionsView instanceof Compartment) {
								return new ICommandProxy(new SetVisibilityCommand(getEditingDomain(), internalTransitionsView, !internalTransitionsView.isVisible()));
							}
						}


						// return GMFtoGEFCommandWrapper.wrap(new ToggleCompartmentVisibilityCommand(getGraphicalHost()));
					}
				}
			}
		}


		Diagram stateMachineDiagram = getStateMachineDiagram(getState());
		if (stateMachineDiagram != null) {
			// Just do the usual navigation
			result = super.getOpenCommand(request);
		} else {
			switch (promptToCreateNestedDiagram()) {
			case IDialogConstants.NO_ID:
				// User wants the usual navigation
				result = super.getOpenCommand(request);
				break;
			case IDialogConstants.CLIENT_ID + 1:
				// User wants to open the inherited state's diagram. Temporarily provide the
				// inherited view as ours so that the superclass open-command will find it
				IInheritableEditPart editPart = (IInheritableEditPart) getHost();
				View restore = editPart.getNotationView();
				View inherited = editPart.getRedefinedView();
				try {
					editPart.setModel(inherited);
					result = super.getOpenCommand(request);
				} finally {
					editPart.setModel(restore);
				}
				break;
			case IDialogConstants.YES_ID:
			case IDialogConstants.CLIENT_ID:
				ICommand composed;
				ICommand createDiagram = new CreateNestedStateMachineDiagramCommand(
						getEditingDomain(), getState(), getGraphicalHost(), true);
				Supplier<Diagram> newDiagram = () -> (Diagram) createDiagram.getCommandResult().getReturnValue();
				composed = createDiagram;

				ICommand rerouteTransitions = RerouteTransitionsToConnectionPointsCommand.createRerouteTransitionsCommand(
						getEditingDomain(), getState(), getGraphicalHost());
				if (rerouteTransitions != null) {
					composed = composed.compose(rerouteTransitions);
				}

				// A command to align connection points around the new diagram frame as
				// around the state
				composed = composed.compose(getConnectionPointMatchingCommand(newDiagram));

				result = GMFtoGEFCommandWrapper.wrap(composed);
				break;
			default: // CANCEL_ID
				result = null;
				break;
			}
		}

		return result;
	}

	protected boolean isInternalTransitionsDecorator(IFigure targetFigure) {
		IFigure parentFigure = targetFigure.getParent();
		while (parentFigure != null) {
			if (parentFigure instanceof Decoration) {
				return true;
			} else {
				parentFigure = parentFigure.getParent();
			}
		}
		return false;
	}

	/**
	 * Prompts the user to confirm creation of a nested diagram.
	 * 
	 * @return {@link IDialogConstants#YES_ID} if the user assents to the nested diagram,
	 *         {@link IDialogConstants#NO_ID} if the user wants the default double-click action,
	 *         or {@link IDialogConstants#CANCEL_ID} if the user wants to cancel the double-click
	 */
	int promptToCreateNestedDiagram() {
		Shell parentShell = getHost().getViewer().getControl().getShell();

		boolean isCompositeState = getState().isComposite();
		boolean isInheritedCompositeState = isCompositeState
				&& UMLRTExtensionUtil.isVirtualElement(getState());
		boolean isRedefinition = UMLRTExtensionUtil.isRedefinition(getState());
		boolean isRedefinitionOfCompositeState = isRedefinition
				&& Optional.of(getState())
						.map(UMLRTExtensionUtil::getRedefinedElement)
						.map(State::isComposite)
						.orElse(false);

		return isInheritedCompositeState
				? DialogPreferences.custom(MessageDialog.QUESTION_WITH_CANCEL, parentShell,
						Messages.RTCustomStateNavigationEditPolicy_0,
						Messages.RTCustomStateNavigationEditPolicy_1,
						null, SWT.SHEET,
						Messages.RTCustomStateNavigationEditPolicy_2,
						Messages.RTCustomStateNavigationEditPolicy_3)
				: isCompositeState
						? isRedefinition
								? isRedefinitionOfCompositeState
										? DialogPreferences.custom(MessageDialog.QUESTION_WITH_CANCEL, parentShell,
												Messages.RTCustomStateNavigationEditPolicy_4,
												Messages.RTCustomStateNavigationEditPolicy_5,
												null, SWT.SHEET,
												Messages.RTCustomStateNavigationEditPolicy_6,
												Messages.RTCustomStateNavigationEditPolicy_3)
										: DialogPreferences.yesNoCancel(parentShell,
												Messages.RTCustomStateNavigationEditPolicy_4,
												Messages.RTCustomStateNavigationEditPolicy_7,
												CONFIRMATION_DIALOG_ID)
								: DialogPreferences.yesNoCancel(parentShell,
										Messages.RTCustomStateNavigationEditPolicy_4,
										Messages.RTCustomStateNavigationEditPolicy_12,
										CONFIRMATION_DIALOG_ID)
						: DialogPreferences.yesNoCancel(parentShell,
								Messages.RTCustomStateNavigationEditPolicy_8,
								Messages.RTCustomStateNavigationEditPolicy_9,
								CONFIRMATION_DIALOG_ID);
	}

	IGraphicalEditPart getGraphicalHost() {
		return (IGraphicalEditPart) getHost();
	}

	TransactionalEditingDomain getEditingDomain() {
		return getGraphicalHost().getEditingDomain();
	}

	State getState() {
		return (State) getGraphicalHost().resolveSemanticElement();
	}

	private ICommand getConnectionPointMatchingCommand(Supplier<? extends Diagram> newDiagram) {
		return new AbstractTransactionalCommand(getEditingDomain(), Messages.RTCustomStateNavigationEditPolicy_10, null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				Diagram smd = newDiagram.get();
				View frame = (smd == null) ? null : ViewUtil.getChildBySemanticHint(smd, StateEditPartTN.VISUAL_ID);
				if (frame instanceof Node) {
					// This is a new diagram and we haven't yet opened it, so it
					// will not yet have views for the connection points.
					// Create them now
					Rectangle frameRect = getBounds((Node) frame);
					IGraphicalEditPart stateEP = getGraphicalHost();
					Rectangle stateRect = getBounds(stateEP);
					View stateView = stateEP.getNotationView();

					for (Pseudostate connPt : getState().getConnectionPoints()) {
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
		};
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

	@Override
	protected Command getShortCutOpenCommand(Request request) {
		IGraphicalEditPart host = (IGraphicalEditPart) getHost();
		EObject element = host.resolveSemanticElement();
		if ((element instanceof Diagram) && (element.eResource() != null)) {
			OpenDiagramCommand openDiagramCommand = new OpenDiagramCommand(host.getEditingDomain(), (Diagram) element);
			return new ICommandProxy(openDiagramCommand);
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}

	//
	// Nested types
	//

	/**
	 * Command to open a diagram.
	 */
	// Copied from the superclass because it is private in the superclass
	protected static class OpenDiagramCommand extends AbstractTransactionalCommand {

		/** The diagram to open. */
		private Diagram diagramToOpen = null;

		/**
		 * Instantiates a new open diagram command.
		 *
		 * @param domain
		 *            the domain
		 * @param diagram
		 *            the diagram
		 */
		public OpenDiagramCommand(TransactionalEditingDomain domain, Diagram diagram) {
			super(domain, Messages.RTCustomStateNavigationEditPolicy_11, null);
			diagramToOpen = diagram;
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			try {
				IPageManager pageMngr = ServiceUtilsForEObject.getInstance().getService(IPageManager.class, diagramToOpen);
				if (pageMngr.isOpen(diagramToOpen)) {
					pageMngr.selectPage(diagramToOpen);
				} else {
					pageMngr.openPage(diagramToOpen);
				}
				return CommandResult.newOKCommandResult();
			} catch (Exception e) {
				throw new ExecutionException("Can't open diagram", e); //$NON-NLS-1$
			}
		}
	}

}
