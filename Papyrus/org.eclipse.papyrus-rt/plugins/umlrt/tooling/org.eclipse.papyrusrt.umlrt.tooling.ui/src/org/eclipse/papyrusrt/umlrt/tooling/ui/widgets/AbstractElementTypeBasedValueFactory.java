/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 477819
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.properties.ui.creation.CreateInDialog;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.properties.creation.UMLPropertyEditorFactory;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Element;

/**
 * Abstract class for element type based value factories
 */
public abstract class AbstractElementTypeBasedValueFactory extends UMLPropertyEditorFactory {
	private Object context;
	private IElementType elementType;

	public AbstractElementTypeBasedValueFactory(EReference eReference) {
		super(eReference);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Object> validateObjects(Collection<Object> objectsToValidate) {
		return objectsToValidate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EObject createObjectInDifferentContainer(Control widget) {
		// Determine from the subclass the kind of element to create ...
		elementType = chooseElementType(widget);
		if (elementType == null) {
			return null;
		}

		EObject container = chooseNewContainer(widget);
		if (container == null) {
			return null;
		}

		IElementEditService service = ElementEditServiceUtils.getCommandProvider(container);
		if (service == null) {
			// Something isn't right ...
			return null;
		}

		ICommand editCommand = service.getEditCommand(new CreateElementRequest(container, elementType));
		if (editCommand != null && editCommand.canExecute()) {
			IStatus status;
			try {
				status = editCommand.execute(new NullProgressMonitor(), null);
				if (status != null && status.isOK()) {
					CommandResult result = editCommand.getCommandResult();
					if (result.getReturnValue() instanceof EObject) {
						return (EObject) result.getReturnValue();
					}
				}
			} catch (ExecutionException e) {
				Activator.log.error(e);
			}
		}
		return null;
	}

	@Override
	protected EObject simpleCreateObject(Control widget) {
		// Determine from the subclass the kind of element to create ...
		elementType = chooseElementType(widget);
		if (elementType == null) {
			return null;
		}

		EObject container = chooseNewContainer(widget);
		if (container == null) {
			return null;
		}

		IElementEditService service = ElementEditServiceUtils.getCommandProvider(container);
		if (service == null) {
			// Something isn't right ...
			return null;
		}

		ICommand editCommand = service.getEditCommand(new CreateElementRequest(container, elementType));
		if (editCommand != null && editCommand.canExecute()) {
			IStatus status;
			try {
				status = editCommand.execute(new NullProgressMonitor(), null);
				if (status != null && status.isOK()) {
					CommandResult result = editCommand.getCommandResult();
					if (result.getReturnValue() instanceof EObject) {
						return (EObject) result.getReturnValue();
					}
				}
			} catch (ExecutionException e) {
				Activator.log.error(e);
			}

		}
		return null;
	}

	/**
	 * Selection of the container of the newly created Protocol
	 * 
	 * @param widget
	 *            the widget to get access to the shell
	 * @return
	 */
	protected EObject chooseNewContainer(Control widget) {
		EObject result = null;

		if (context instanceof Element) {
			// Create the protocol in the same package as the port's capsule
			result = ((Element) context).getNearestPackage();
		}

		if (result == null) {
			EObject virtualElement = getVirtualElementToCreate();
			CreateInDialog dialog = new CreateInDialog(widget.getShell(), virtualElement);
			dialog.setProviders(containerContentProvider, referenceContentProvider, containerLabelProvider,
					referenceLabelProvider);
			dialog.setTitle(getCreationDialogTitle());
			if (dialog.open() == Window.OK) {
				result = dialog.getContainer();
			}
		}

		return result;
	}

	protected abstract EObject getVirtualElementToCreate();

	protected abstract IElementType chooseElementType(Control widget);

	@Override
	public String getCreationDialogTitle() {
		String typeName = (elementType == null) ? "Element" : elementType.getDisplayName();
		return NLS.bind("Create a New {0}", typeName);
	}

	@Override
	protected Object doCreateObject(Control widget, Object context) {
		Object result;

		// Stash the current creation context temporarily
		this.context = context;
		try {
			result = super.doCreateObject(widget, context);
		} finally {
			// Forget our stashed context
			this.context = null;
			// And the element-type
			this.elementType = null;
		}

		return result;
	}

}
