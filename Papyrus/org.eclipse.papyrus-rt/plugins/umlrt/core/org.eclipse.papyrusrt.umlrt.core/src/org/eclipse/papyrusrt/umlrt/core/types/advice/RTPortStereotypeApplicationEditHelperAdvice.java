/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.uml2.uml.Port;

/**
 * Edit Helper Advice for {@link RTPort} element.
 */
public class RTPortStereotypeApplicationEditHelperAdvice extends AbstractEditHelperAdvice {

	@Override
	protected ICommand getAfterSetCommand(SetRequest request) {
		EStructuralFeature feature = request.getFeature();
		EObject elementToEdit = request.getElementToEdit();

		if (UMLRealTimePackage.eINSTANCE.getRTPort_IsWired().equals(feature) && elementToEdit instanceof RTPort && ((RTPort) elementToEdit).getBase_Port() != null && request.getValue() instanceof Boolean) {
			Port portToEdit = ((RTPort) elementToEdit).getBase_Port();
			// set also the rtport 'derived' value isPublish
			boolean newIsWired = (Boolean)request.getValue(); 
			boolean isService = portToEdit.isService();

			boolean isPublish = !newIsWired && isService;
			
			SetRequest setPublishRequest = new SetRequest(request.getEditingDomain(), elementToEdit, UMLRealTimePackage.eINSTANCE.getRTPort_IsPublish(), isPublish);
			IElementEditService serviceEdit = ElementEditServiceUtils.getCommandProvider(elementToEdit);
			ICommand command = serviceEdit.getEditCommand(setPublishRequest);
			if (command != null) {
				if (newIsWired) {
					// Ensure that registration and registrationOverride always is reset to default whenever the port is changed 
					// to a wired port. It only makes sense to have registration and registrationOverride for unwired ports. 
					SetRequest setRegistrationRequest = new SetRequest(request.getEditingDomain(), elementToEdit, UMLRealTimePackage.eINSTANCE.getRTPort_Registration(), PortRegistrationType.AUTOMATIC);
					SetRequest setRegistrationOverrideRequest = new SetRequest(request.getEditingDomain(), elementToEdit, UMLRealTimePackage.eINSTANCE.getRTPort_RegistrationOverride(), "");
					ICommand setRegistrationCommand = serviceEdit.getEditCommand(setRegistrationRequest);
					ICommand setRegistrationOverrideCommand = serviceEdit.getEditCommand(setRegistrationOverrideRequest);
					if (setRegistrationCommand != null && setRegistrationOverrideCommand != null) {
						command = command.compose(setRegistrationCommand).compose(setRegistrationOverrideCommand);
					}
				}
				return command.compose(super.getAfterSetCommand(request)).reduce();
			}
			return super.getAfterSetCommand(request);
		}
		return super.getAfterSetCommand(request);
	}
}
