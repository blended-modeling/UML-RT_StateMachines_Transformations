/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *  
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.ICommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.uml.properties.creation.UMLPropertyEditorFactory;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.types.UMLRTUIElementTypesEnumerator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A value-factory for creation of protocol message parameters.
 */
public class ProtocolMessageParameterFactory extends UMLPropertyEditorFactory {

	public ProtocolMessageParameterFactory() {
		super(UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER);
	}

	@Override
	protected Object doCreateObject(Control widget, Object context) {
		Object result;

		EObject container = PlatformHelper.getAdapter(context, EObject.class);
		IEditCommandRequest creationRequest = getCreationRequest(container);
		if (creationRequest != null) {
			IElementEditService edit = ElementEditServiceUtils.getCommandProvider(container);
			ICommand create = edit.getEditCommand(creationRequest);
			try {
				TransactionalEditingDomain domain = creationRequest.getEditingDomain();
				Command emfCreate = ICommandWrapper.wrap(create, Command.class);
				domain.getCommandStack().execute(emfCreate);
				result = emfCreate.getResult().iterator().next();
			} catch (OperationCanceledException e) {
				// Normal
				result = null;
			} catch (Exception e) {
				Activator.log.error("Failed to create new element.", e); //$NON-NLS-1$
				result = null;
			}
		} else {
			result = super.doCreateObject(widget, context);
		}

		return result;
	}

	/**
	 * Obtain a specific creation request for the new element. If provided, the editing
	 * dialog will be by-passed, so this command should take care of all initial
	 * configuration of the new element.
	 * 
	 * @param container
	 *            the owner of the element to be created
	 * @return the creation request
	 */
	protected IEditCommandRequest getCreationRequest(EObject container) {
		CreateElementRequest result = null;

		IElementType creationType = UMLRTUIElementTypesEnumerator.PROTOCOL_MESSAGE_PARAMETER_CREATION_WITH_UI;
		if (creationType != null) {
			result = new CreateElementRequest(container, creationType, UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER);
		}

		return result;
	}
}
