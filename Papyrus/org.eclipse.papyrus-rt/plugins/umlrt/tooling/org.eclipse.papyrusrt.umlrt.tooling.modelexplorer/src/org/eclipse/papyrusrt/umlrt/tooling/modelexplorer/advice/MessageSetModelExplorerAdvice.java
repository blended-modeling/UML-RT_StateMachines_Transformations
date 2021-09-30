/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 514322
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.INonDirtying;
import org.eclipse.papyrus.views.modelexplorer.ModelExplorerPageBookView;
import org.eclipse.papyrus.views.modelexplorer.ModelExplorerView;
import org.eclipse.papyrus.views.modelexplorer.matching.IMatchingItem;
import org.eclipse.papyrus.views.modelexplorer.matching.ModelElementItemMatchingItem;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageSetUtils;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;


/**
 * Work around to expand Protocol Menu when creating a new Protocol Message
 * See Bug 480403
 * 
 * @author as247872
 *
 */
public class MessageSetModelExplorerAdvice extends AbstractEditHelperAdvice {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterCreateCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterCreateCommand(CreateElementRequest request) {

		// get the protocol (the selected element to create the operation)
		final Collaboration protocol = MessageSetUtils.getProtocol(((Interface) request.getContainer()));
		// return non EMF non dirtying command
		return new ExpandMenuCommand("Expand Protocol Message", protocol);

	}

	private ModelExplorerView getActiveModelExplorerPage() {
		ModelExplorerView activeView = null;
		// Get Model Explorer view part
		// assure that it is called from an UI Thread
		if (PlatformUI.isWorkbenchRunning() && (PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null)) {
			IViewPart modelExplorerView = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ModelExplorerPageBookView.VIEW_ID);

			if (modelExplorerView instanceof ModelExplorerPageBookView) {
				ModelExplorerPageBookView pageBook = (ModelExplorerPageBookView) modelExplorerView;
				activeView = (ModelExplorerView) pageBook.getActiveView();
			}
		}
		return activeView;
	}

	private class ExpandMenuCommand extends AbstractCommand implements INonDirtying {

		protected Collaboration protocol;

		public ExpandMenuCommand(String label, Collaboration protocol) {
			super(label);
			this.protocol = protocol;
		}


		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			ModelExplorerView modelExplorerView = getActiveModelExplorerPage();
			if (modelExplorerView != null) {
				IMatchingItem itemToExpand = new ModelElementItemMatchingItem(protocol);
				modelExplorerView.getCommonViewer().expandToLevel(itemToExpand, 1);
			}
			return CommandResult.newOKCommandResult(protocol);
		}


		@Override
		protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			return CommandResult.newOKCommandResult(protocol);
		}


		@Override
		protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			return CommandResult.newOKCommandResult(protocol);
		}

	}
}
