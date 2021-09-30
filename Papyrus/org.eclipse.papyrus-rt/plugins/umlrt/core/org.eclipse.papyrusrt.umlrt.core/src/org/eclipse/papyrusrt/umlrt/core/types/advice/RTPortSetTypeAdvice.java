/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 474481, 510188
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrusrt.umlrt.core.utils.NewElementUtil;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.system.profile.util.SystemElementsUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.google.common.base.Strings;

/**
 * Advice for {@link RTPort} on the setting of its
 * {@link UMLPackage.Literals#TYPED_ELEMENT__TYPE type}.
 */
public class RTPortSetTypeAdvice extends AbstractEditHelperAdvice {

	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		boolean result = false;
		// check that the type of the RTPort is a protocol, and nothing else
		if (request instanceof SetRequest) {
			SetRequest setRequest = (SetRequest) request;
			EStructuralFeature feature = setRequest.getFeature();
			if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(feature)) {
				// new value should be a protocol or null
				Object newValue = ((SetRequest) request).getValue();
				if (newValue instanceof EObject) {
					if (ProtocolUtils.isProtocol((EObject) newValue)) {
						result = true;
					} else {
						result = false;
					}
				} else {
					result = false;
				}
			} else {
				result = super.approveRequest(setRequest);
			}
		} else {
			result = super.approveRequest(request);
		}

		return result;
	}

	/**
	 * React to the setting of the initial type of an unnamed port by initializing
	 * its name.
	 */
	@Override
	protected ICommand getAfterSetCommand(SetRequest request) {

		CompositeCommand compositeCommand = new CompositeCommand("Set RTPort Name and Kind");
		EStructuralFeature feature = request.getFeature();

		if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(feature)) {
			// if element is unnamed or is itself a new element, set a name
			// according to the new Type name
			Object newValue = request.getValue();
			EObject elementToEdit = request.getElementToEdit();
			if ((newValue instanceof Type) && (elementToEdit instanceof Port)) {

				Port port = (Port) elementToEdit;

				// Set the Port kind according to the protocol type : if it is a System Protocol set kind to SAP otherwise, keep the external behavior kind
				if (SystemElementsUtils.isSystemProtocol((Collaboration) newValue)) {

					ICommand cmd = getSetSAPKindCommand(port);
					if (null != cmd) {
						compositeCommand.add(cmd);
					}
				}

				// set the a new name of the Port
				if (Strings.isNullOrEmpty(((Port) elementToEdit).getName())
						|| NewElementUtil.isCreatedElement(elementToEdit)) {

					String name = ((Type) newValue).getName();
					if (!Strings.isNullOrEmpty(name)) {
						String newName = NewElementUtil.getUniqueName(port, (Type) newValue);
						ICommand setNameCommand = new SetValueCommand(new SetRequest(request.getElementToEdit(), UMLPackage.eINSTANCE.getNamedElement_Name(), newName));
						if (null != setNameCommand) {
							compositeCommand.add(setNameCommand);
						}
					}
				}
			}
		}

		return compositeCommand.isEmpty() ? super.getAfterSetCommand(request) : compositeCommand;
	}

	protected ICommand getSetSAPKindCommand(final Port serviceAccessPoint) {

		return RTPortUtils.getChangePortKindCommand(serviceAccessPoint, UMLRTPortKind.SAP);
	}


}
