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
package org.eclipse.papyrusrt.umlrt.core.tests.deletion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IDeleteValidationRule;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IValidationRule;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Protocol;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.junit.Assert;

/**
 * {@link IValidationRule} for {@link Protocol} deletion
 */
public class DeleteProtocolFromModelValidation implements IDeleteValidationRule {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validatePostEdition(Element containerElement, Element deletedObject, Object[] commandResults) throws Exception {
		// post edition => element is deleted, also the parent protocolcontainer
		Assert.assertEquals("command result should not be empty", 1, commandResults.length);
		Assert.assertTrue(commandResults[0] instanceof List<?> && ((List<Object>) (commandResults[0])).isEmpty());

		// target element is the deleted element
		EObject container = deletedObject.eContainer();
		assertFalse(container instanceof Package); // current issue on redo, where the parent is the change description

		// browse model content and check no protocol container is left
		for (Package nestedPackage : ((Model) containerElement).getNestedPackages()) {
			if (ProtocolContainerUtils.isProtocolContainer(nestedPackage)) {
				// check it has a collaboration
				assertNotNull("This protocol container should have a protocol. Protocol deletion may have an issue", ProtocolContainerUtils.getProtocol(nestedPackage));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validatePostUndo(Element containerElement, Element deletedObject, Object[] commandResults) throws Exception {
		// target element is the deleted element
		assertNotNull(deletedObject.eContainer());// protocol container should not be null
		assertNotNull(deletedObject.eContainer().eContainer()); // model should not be null

		for (Package nestedPackage : ((Model) containerElement).getNestedPackages()) {
			if (ProtocolContainerUtils.isProtocolContainer(nestedPackage)) {
				// check it has a collaboration
				assertNotNull("This protocol container should have a protocol. Protocol deletion undo may have an issue", ProtocolContainerUtils.getProtocol(nestedPackage));
			}
		}


	}

}
