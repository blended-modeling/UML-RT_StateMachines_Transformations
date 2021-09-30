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
package org.eclipse.papyrusrt.umlrt.core.tests.creation;

import static org.junit.Assert.fail;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IValidationRule;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageSetUtils;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;

/**
 * Validation rules for {@link CreateElementTest#testCreateProtocolMessageOnMessageSetIn()}
 */
public class CreateProtocolMessageInOnMessageSetInValidation implements IValidationRule {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validatePostEdition(Element targetContainer, Object[] commandResults) throws Exception {
		// check operation creation
		Operation newOperation = getOperation(targetContainer, commandResults);

		// check message set really has the operation
		Interface messageSetIn = ((Interface) targetContainer);
		if (!messageSetIn.getOperations().contains(newOperation)) {
			fail("MessageSetIn has not been modified");
		}

		// check the call event associated
		Package protocolContainer = MessageSetUtils.getProtocolContainer(messageSetIn);
		CallEvent operationCallEvent = null;
		for (PackageableElement packageableElement : protocolContainer.getPackagedElements()) {
			if (packageableElement instanceof CallEvent) {
				// check the operation
				if (newOperation.equals(((CallEvent) packageableElement).getOperation())) {
					// assert there is not already a operation call event found
					if (operationCallEvent != null) {
						fail("Several call events are found for the same protocol message");
					}
					operationCallEvent = (CallEvent) packageableElement;
				}
			}
		}
		Assert.assertNotNull("No CallEvent was found for the new message set", operationCallEvent);
		// check its name: it should be null
		// Assert.assertNull("CallEvent should be left unnamed (See bug 469813).", operationCallEvent.getName());
		Assert.assertFalse("CallEvent name should be left unset (See bug 484638).", operationCallEvent.eIsSet(UMLPackage.eINSTANCE.getNamedElement().getEStructuralFeature ("name")));


	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validatePostUndo(Element targetContainer, Object[] commandResults) throws Exception {
		Operation newOperation = getOperation(targetContainer, commandResults);

		// check message set really has the operation
		Interface messageSetIn = ((Interface) targetContainer);
		if (messageSetIn.getOperations().contains(newOperation)) {
			fail("MessageSetIn should not have the new operation, as command has been undone");
		}

		// check the call event associated
		Package protocolContainer = MessageSetUtils.getProtocolContainer(messageSetIn);
		for (PackageableElement packageableElement : protocolContainer.getPackagedElements()) {
			if (packageableElement instanceof CallEvent) {
				// check the operation
				if (newOperation.equals(((CallEvent) packageableElement).getOperation())) {
					// assert there is not already a operation call event found
					fail("Call event for undo created element is still present.");
				}
			}
		}
	}

	protected Operation getOperation(Element targetContainer, Object[] commandResults) {
		if (!(targetContainer instanceof Interface)) {
			fail("targetcontainer is supposed to be an Interface");
		}
		// there should be the operation in the command result
		EObject result = null;
		if (commandResults.length > 0 && commandResults[0] instanceof EObject) {
			result = (EObject) commandResults[0];
		} else {
			fail("impossible to get result from the command");
		}
		Assert.assertTrue("Result should be an operation", result instanceof Operation);
		return (Operation) result;
	}

}
