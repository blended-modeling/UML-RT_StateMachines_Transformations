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
 *   Christian W. Damus - bug 510315
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.command;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CreateViewCommand;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;

/**
 * Creation view command that may reuse an existing view rather than creating a new one.
 * This can be used when the canonical edit policy creates the view before the creation is effective
 */
public class CreateOrReuseViewCommand extends CreateViewCommand {

	/**
	 * Creates a new CreateOrReuseViewCommand.
	 * 
	 * @param editingDomain
	 *            the editing domain through which model changes are made
	 * @param viewDescriptor
	 *            the view descriptor associated with this command
	 * @param containerView
	 *            the view that will contain the new view
	 */
	public CreateOrReuseViewCommand(TransactionalEditingDomain editingDomain, ViewDescriptor viewDescriptor, View containerView) {
		super(editingDomain, viewDescriptor, containerView);
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		Optional<View> view = findView();
		// Checks if a view for given element already exists
		if (view.isPresent()) {
			viewDescriptor.setView(view.get());
			return CommandResult.newOKCommandResult(viewDescriptor);
		}
		return super.doExecuteWithResult(monitor, info);
	}

	/**
	 * Tries to find a view in the container view that has the same attributes of the view we want to create.
	 * 
	 * @return <code>null</code> if no view has been found, the view otherwise
	 */
	@SuppressWarnings("unchecked")
	protected Optional<View> findView() {
		return ((List<View>) containerView.getChildren()).stream()
				.map(View.class::cast)
				.filter(this::representsElement)
				.findFirst();
	}

	@Override
	public boolean canExecute() {
		// FIXMEshould be careful here that the container view is not the right one!!
		return true;
	}

	@SuppressWarnings("unchecked")
	protected boolean representsElement(View view) {
		if (!(viewDescriptor.getViewKind().isAssignableFrom(view.getClass()) && Objects.equals(view.getType(), viewDescriptor.getSemanticHint()))) {
			return false;
		}
		// check linked element
		IAdaptable elementAdapter = viewDescriptor.getElementAdapter();
		if (elementAdapter != null) {
			EObject linkedObject = elementAdapter.getAdapter(EObject.class);
			if (Objects.equals(EditPartInheritanceUtils.resolveSemanticElement(view), linkedObject)) {
				return true;
			}
		}
		return false;
	}
}
