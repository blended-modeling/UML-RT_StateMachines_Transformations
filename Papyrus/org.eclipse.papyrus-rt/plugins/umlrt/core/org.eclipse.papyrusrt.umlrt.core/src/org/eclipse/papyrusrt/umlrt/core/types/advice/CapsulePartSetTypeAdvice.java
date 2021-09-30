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
 *  Christian W. Damus - bug 474481
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
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ModelUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.NewElementUtil;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.google.common.base.Strings;

/**
 * Advice for {@link CapsulePart} on the setting of its
 * {@link UMLPackage.Literals#TYPED_ELEMENT__TYPE type}.
 */
public class CapsulePartSetTypeAdvice extends AbstractEditHelperAdvice {

	public CapsulePartSetTypeAdvice() {
		super();
	}

	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		// check that the type of the CapsulePart is a capsule, and nothing else
		if (request instanceof SetRequest) {
			SetRequest setRequest = (SetRequest) request;
			EStructuralFeature feature = setRequest.getFeature();
			if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(feature)) {
				// new value should be a protocol or null
				Object newValue = ((SetRequest) request).getValue();
				if (newValue instanceof Classifier) {
					if (CapsuleUtils.isCapsule((Classifier) newValue)) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
			return super.approveRequest(setRequest);

		}
		return super.approveRequest(request);
	}

	/**
	 * React to the setting of the initial type of an unnamed capsule-part by
	 * initializing its name.
	 */
	@Override
	protected ICommand getAfterSetCommand(SetRequest request) {

		CompositeCommand compositeCommand = new CompositeCommand("Set CapsulePart Type");
		EStructuralFeature feature = request.getFeature();
		
		if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(feature)) {

			Object newValue = request.getValue();
			EObject elementToEdit = request.getElementToEdit();


			if (elementToEdit instanceof Property && newValue instanceof Type) {

				Property property = (Property) elementToEdit;
				Type newType = (Type) newValue;

				// set the kind to optional if a cycle is detected
				if (CapsulePartUtils.completesCycle(newType, property)) {

					LiteralInteger newLowerValue = (LiteralInteger) ModelUtils.create(property, UMLPackage.Literals.LITERAL_INTEGER);
					newLowerValue.setValue(0);
					ICommand setLowerBoundsCommand = new SetValueCommand(new SetRequest(request.getElementToEdit(), UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue(), newLowerValue));
					compositeCommand.add(setLowerBoundsCommand);
				}

				// if element is unnamed or is itself a new element, set a name
				// according to the new Type name
				if ((Strings.isNullOrEmpty((property).getName()) || NewElementUtil.isCreatedElement(property))) {

					String name = newType.getName();
					if (!Strings.isNullOrEmpty(name)) {
						String newName = NewElementUtil.getUniqueName(property, newType);
						ICommand setNameCommand = new SetValueCommand(new SetRequest(request.getElementToEdit(), UMLPackage.eINSTANCE.getNamedElement_Name(), newName));
						compositeCommand.add(setNameCommand);
					}
				}
			}
		}

		return compositeCommand.isEmpty() ? super.getAfterSetCommand(request) : compositeCommand;

	}

}
