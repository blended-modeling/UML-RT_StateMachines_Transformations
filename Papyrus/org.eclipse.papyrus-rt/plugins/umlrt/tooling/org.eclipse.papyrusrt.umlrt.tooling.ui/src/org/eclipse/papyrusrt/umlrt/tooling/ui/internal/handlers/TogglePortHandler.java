/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.providers.TogglePortState;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Port;

/**
 * Handler for UML/RT port menu. This class gets some values from the associated
 * State object, notably property path and isStereotype
 * Toggle command and enablement is based on the ModelElement class from Papyrus infra.properties
 */
public class TogglePortHandler extends AbstractHandler {

	protected EObject selectedEObject;

	protected TogglePortState state;

	/**
	 * Convert selected elements within model explorer or diagram to an eObject
	 */
	public void updateSelectedEObject() {
		// Retrieve selected elements
		IStructuredSelection selection = getSelection();

		if (selection != null && selection.size() == 1) {
			selectedEObject = EMFHelper.getEObject(selection.getFirstElement());
		} else {
			selectedEObject = null;
		}
	}

	public IStructuredSelection getSelection() {
		return (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
	}

	@Override
	public boolean isEnabled() {
		if (state == null) {
			// always return true, before the state object is initialized
			return true;
		}
		updateSelectedEObject();
		if (selectedEObject instanceof Port) {
			return getIsEnabled((Port) selectedEObject);
		}
		return false;
	}

	public boolean getIsEnabled(final Port port) {
		String propertyPath = state.getPropertyPath();

		ModelElement modelElement = state.getModelElement(port);
		if (modelElement != null) {
			boolean result = modelElement.isEditable(propertyPath);
			modelElement.dispose();
			return result;
		} else {
			return false;
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Object stateInfo = event.getParameters().get(TogglePortState.STATE);
		if (stateInfo instanceof TogglePortState) {
			state = (TogglePortState) stateInfo;
		} else {
			ModelElement modelElement = state.getModelElement((Port) selectedEObject);
			// all port attributes are simple values (i.e. not lists), therefore cast is always ok.
			@SuppressWarnings({ "unchecked" })
			IObservableValue<Boolean> value = (IObservableValue<Boolean>) modelElement.getObservable(state.getPropertyPath());
			value.setValue(!value.getValue());
			modelElement.dispose();
		}
		return null;
	}
}
