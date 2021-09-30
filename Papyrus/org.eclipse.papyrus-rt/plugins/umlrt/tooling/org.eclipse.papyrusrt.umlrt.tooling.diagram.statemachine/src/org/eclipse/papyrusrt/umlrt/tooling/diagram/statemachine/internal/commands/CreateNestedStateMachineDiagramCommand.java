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

import java.util.Arrays;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.ui.util.DisplayUtils;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.helper.EditorHyperLinkHelper;
import org.eclipse.papyrusrt.umlrt.core.utils.RegionUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTStateMachineDiagramUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Create a nested state machine diagram for a state (thereby converting it, if necessary,
 * to a composite state) with default double-click navigation to the new diagram.
 */
public class CreateNestedStateMachineDiagramCommand extends AbstractTransactionalCommand {

	private final State state;
	private final IGraphicalEditPart stateEditPart;
	private final boolean openDiagram;

	private Diagram createdDiagram;

	public CreateNestedStateMachineDiagramCommand(TransactionalEditingDomain domain, State state, IGraphicalEditPart stateEditPart, boolean openDiagram) {
		super(domain, "Create State Machine Diagram", getWorkspaceFiles(Arrays.asList(state, stateEditPart.getNotationView())));

		this.state = state;
		this.stateEditPart = stateEditPart;
		this.openDiagram = openDiagram;
	}

	@Override
	public void dispose() {
		createdDiagram = null;

		super.dispose();
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		View stateView = stateEditPart.getNotationView();

		// Reify the state
		UMLRTState facade = UMLRTState.getInstance(state);
		if (facade != null) {
			facade.reify();

			// And its region, because the user will be changing stuff in there
			// and the new diagram expects to be able to find it in the usual
			// UML API way
			UMLRTExtensionUtil.<Region> getUMLRTContents(state, UMLPackage.Literals.STATE__REGION).stream()
					.forEach(UMLRTExtensionUtil::touch);
		} else {
			// Cannot proceed with an inherited state
			return CommandResult.newErrorCommandResult("Cannot redefine state");
		}

		// Let the diagram label be computed
		createdDiagram = UMLRTStateMachineDiagramUtils.createStateMachineDiagram(state, null);

		if (createdDiagram != null) {
			// Make the region an RTRegion
			UMLRTExtensionUtil.<Region> getUMLRTContents(state, UMLPackage.Literals.STATE__REGION)
					.forEach(RegionUtils::applyStereotype);

			if (openDiagram) {
				// And open the new diagram. This should not be recorded for undo,
				// so it must be explicitly undone at undo time should that happen
				CommandResult[] failure = { null };
				try {
					GMFUnsafe.write(getEditingDomain(), () -> {
						try {

							IPageManager pages = ServiceUtilsForEObject.getInstance().getIPageManager(createdDiagram);
							pages.openPage(createdDiagram);
						} catch (ServiceException e) {
							failure[0] = CommandResult.newErrorCommandResult(e);
						}
					});
				} catch (Exception e) {
					failure[0] = CommandResult.newErrorCommandResult(e);
				}

				if (failure[0] != null) {
					return failure[0];
				}
			}

			// Create the default hyperlink
			EditorHyperLinkHelper helper = new EditorHyperLinkHelper();
			Command hyperlink = helper.getCreateHyperlinkCommand(getEditingDomain(), stateView, createdDiagram);
			if ((hyperlink != null) && hyperlink.canExecute()) {
				hyperlink.execute();
			}
		}
		return (createdDiagram == null)
				? CommandResult.newCancelledCommandResult()
				: CommandResult.newOKCommandResult(createdDiagram);
	}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		// First, if the diagram is still open, close it so that the edit-part hierarchy
		// is destroyed and doesn't try to react to events (bug 518243)
		// And open the new diagram. This should not be recorded for undo,
		// so it must be explicitly undone at undo time should that happen
		IStatus[] failure = { null };
		if (createdDiagram != null) {
			try {
				GMFUnsafe.write(getEditingDomain(), () -> {
					try {

						IPageManager pages = ServiceUtilsForEObject.getInstance().getIPageManager(createdDiagram);
						if (pages.isOpen(createdDiagram)) {
							pages.closeAllOpenedPages(createdDiagram);

							if (Display.getCurrent() != null) {
								// Ensure that the edit-parts are destroyed now
								DisplayUtils.clearEventLoop();
							}
						}
					} catch (ServiceException e) {
						failure[0] = CommandResult.newErrorCommandResult(e).getStatus();
					}
				});
			} catch (Exception e) {
				failure[0] = CommandResult.newErrorCommandResult(e).getStatus();
			}
		}

		// Also attempt this, in any case
		IStatus result = super.doUndo(monitor, info);

		if (((result == null) || (result.getSeverity() < IStatus.ERROR))
				&& ((failure[0] != null) && (failure[0].getSeverity() >= IStatus.ERROR))) {

			result = failure[0];
		}

		return result;
	}
}
