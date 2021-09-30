/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.handlers;

import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;
import org.eclipse.papyrusrt.umlrt.core.utils.Either;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTStateMachineDiagramUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.IStatusAdapterConstants;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;

/**
 * Handler for the "Go Outside" menu action in the state machine diagram.
 */
public class GoOutsideHandler extends AbstractHandler {

	public GoOutsideHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IGraphicalEditPart editPart = getSelectedEditPart(event);

		Optional<Diagram> diagram = getOutsideDiagram(editPart);
		diagram.ifPresent(this::openPage);

		return null;
	}

	/**
	 * Get the diagram that is "outside" the context of the given edit-part
	 * {@code selection}, being the diagram for the element that owns it.
	 * 
	 * @param selection
	 *            an edit-part representing the composite state that is
	 *            the context of the current diagram or its canonical region (because that
	 *            region edit-part occupies much of the canvas)
	 * 
	 * @return the diagram of the "outside" context, or {@code null} if for
	 *         some reason that context has no diagram
	 */
	protected Optional<Diagram> getOutsideDiagram(IGraphicalEditPart selection) {
		Optional<UMLRTState> compositeState = Optional.ofNullable(selection)
				.filter(this::isDiagramOrFrameOrRegion)
				.map(IGraphicalEditPart::resolveSemanticElement)
				.map(this::getState)
				.filter(UMLRTState::isComposite);

		Either<State, StateMachine> parent = Either.or(
				compositeState.map(UMLRTState::getState),
				() -> compositeState.map(UMLRTState::getStateMachine))
				.map(UMLRTState::toUML, UMLRTStateMachine::toUML);

		return Optional.ofNullable(parent.flatMap(
				state -> Optional.ofNullable(UMLRTStateMachineDiagramUtils.getStateMachineDiagram(state)),
				sm -> Optional.ofNullable(UMLRTStateMachineDiagramUtils.getStateMachineDiagram(sm)))
				.orElse(Diagram.class, null));
	}

	protected IGraphicalEditPart getSelectedEditPart(ExecutionEvent event) {
		return getSelectedEditPart(event.getApplicationContext());
	}

	protected IGraphicalEditPart getSelectedEditPart(Object evaluationContext) {
		IStructuredSelection sel = TypeUtils.as(
				HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME),
				StructuredSelection.EMPTY);

		return ((sel != null) && !sel.isEmpty())
				? PlatformHelper.getAdapter(sel.getFirstElement(), IGraphicalEditPart.class)
				: null;
	}

	/**
	 * Queries whether an edit-part is either the diagram surface, the composite
	 * state frame of the diagram, or the region within that frame
	 * 
	 * @param editPart
	 *            an edit-part
	 * @return whether it represents the composite state (perhaps indirectly as its
	 *         canonical region)
	 */
	protected boolean isDiagramOrFrameOrRegion(IGraphicalEditPart editPart) {
		View view = editPart.getNotationView();

		return (view != null)
				&& ((view instanceof Diagram)
						|| StateEditPartTN.VISUAL_ID.equals(view.getType())
						|| RegionEditPart.VISUAL_ID.equals(view.getType()));
	}

	protected UMLRTState getState(EObject semanticElement) {
		if (semanticElement instanceof Region) {
			return getState(((Region) semanticElement).getState());
		}

		Optional<State> result = Optional.ofNullable(TypeUtils.as(semanticElement, State.class));

		return result.map(UMLRTState::getInstance).orElse(null);
	}

	/**
	 * Ensures that the given page is open and activated in its editor context.
	 * 
	 * @param pageIdentifier
	 *            a page to show (in practice, a diagram)
	 */
	protected void openPage(EObject pageIdentifier) {
		try {
			IPageManager pagemgr = ServiceUtilsForEObject.getInstance().getIPageManager(pageIdentifier);
			if (pagemgr.isOpen(pageIdentifier)) {
				pagemgr.selectPage(pageIdentifier);
			} else {
				pagemgr.openPage(pageIdentifier);
			}
		} catch (ServiceException e) {
			StatusAdapter status = new StatusAdapter(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					Messages.GoOutsideHandler_0, e));
			status.setProperty(IStatusAdapterConstants.TITLE_PROPERTY, Messages.GoOutsideHandler_1);
			status.setProperty(IStatusAdapterConstants.TIMESTAMP_PROPERTY, System.currentTimeMillis());

			StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
		}
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		IGraphicalEditPart editPart = getSelectedEditPart(evaluationContext);
		Optional<Diagram> diagram = getOutsideDiagram(editPart);
		setBaseEnabled(diagram.isPresent());
	}
}
