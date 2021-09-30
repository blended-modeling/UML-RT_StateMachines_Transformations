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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.providers;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.State;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement.RTStereotypeModelElement;
import org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement.UMLRTExtModelElement;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * State class for UML/RT port popup
 */
public class TogglePortState extends State implements IExecutableExtension {

	/**
	 * constant within parameter hash
	 */
	public static final String STATE = "state"; //$NON-NLS-1$

	/**
	 * Constant to retrieve commandID from parameter
	 */
	private static final String COMMAND_ID = "commandID"; //$NON-NLS-1$

	public static final String UML_REAL_TIME_RT_PORT = "UMLRealTime::RTPort"; //$NON-NLS-1$

	/**
	 * parameter ID for property path
	 */
	public static final String PROPERTY_PATH = "propertyPath"; //$NON-NLS-1$

	/**
	 * parameter ID for is-stereotype information
	 */
	public static final String IS_STEREOTYPE = "isStereotype"; //$NON-NLS-1$


	protected EObject selectedEObject;

	Hashtable<String, String> parameterHash;

	@SuppressWarnings("unchecked")
	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		if (data instanceof Hashtable) {
			parameterHash = (Hashtable<String, String>) data;
			ICommandService commandService = PlatformUI.getWorkbench().getService(ICommandService.class);
			String commandId = parameterHash.get(COMMAND_ID);
			if (commandId != null) {
				Command command = commandService.getCommand(commandId);
				if (command != null) {
					IHandler handler = command.getHandler();
					// pass reference to handler via a command execution (obtained handler is not a direct reference)
					try {
						Map<Object, Object> map = new HashMap<>();
						map.put(STATE, this);
						handler.execute(new ExecutionEvent(command, map, null, null));
					} catch (ExecutionException e) {
					}
				}
			}
		}
	}

	/**
	 * Convert selected elements within model explorer or diagram to an eObject
	 */
	public void updateSelectedEObject() {
		// Retrieve selected elements
		IStructuredSelection selection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();

		if (selection != null && selection.size() == 1) {
			selectedEObject = EMFHelper.getEObject(selection.getFirstElement());
		}
	}

	/**
	 * Get the state (active or not) of a port attribute. Implementation is based on
	 * ModelElement and propertyPath
	 *
	 * @see org.eclipse.core.commands.State#getValue()
	 *
	 * @return true or false in form of a Boolean object
	 */
	@Override
	public Object getValue() {
		updateSelectedEObject();

		if (selectedEObject instanceof Port) {
			Port port = (Port) selectedEObject;

			ModelElement modelElement = getModelElement(port);
			// all port attributes are simple values (i.e. not lists), therefore cast is always ok.
			@SuppressWarnings("rawtypes")
			IObservableValue value = (IObservableValue) modelElement.getObservable(getPropertyPath());
			Object result = value.getValue();
			modelElement.dispose();
			return result;
		}
		return false;
	}

	/**
	 * Get the ModelElement that is associated with a UML element (in this case a port)
	 * (operation is public for enabling access from handler)
	 *
	 * @param port
	 *            a UML port
	 * @return the associated ModelElement (must be disposed after use)
	 */
	public ModelElement getModelElement(Port port) {
		EditingDomain domain = EMFHelper.resolveEditingDomain(port);
		RTPort rtPort = UMLUtil.getStereotypeApplication(port, RTPort.class);
		if (isStereotypeProperty()) {
			Stereotype rtPortStereotype = port.getApplicableStereotype(UML_REAL_TIME_RT_PORT);
			return new RTStereotypeModelElement(rtPort, rtPortStereotype, domain);
		} else {
			return new UMLRTExtModelElement(port, domain);
		}
	}

	/**
	 * @return property path (operation is public for enabling access from handler)
	 * @return
	 */
	public String getPropertyPath() {
		return parameterHash.get(PROPERTY_PATH);
	}

	/**
	 * @return whether stereotype property (operation is public for enabling access from handler)
	 */
	public boolean isStereotypeProperty() {
		return Boolean.valueOf(parameterHash.get(IS_STEREOTYPE));
	}
}
