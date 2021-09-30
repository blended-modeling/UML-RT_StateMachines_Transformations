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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IDeleteValidationRule;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IValidationRule;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageUtils;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageableElement;

/**
 * {@link IValidationRule} for deletion of a ProtocolMessage from a MessageSet (in)
 */
public class DeleteProtocolMessageFromMessageSetInValidation implements IDeleteValidationRule {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validatePostEdition(Element containerElement, Element deletedObject, Object[] commandResults) throws Exception {
		// protocol message has been deleted, there should not be a callevent related to this deleted protocolmessage
		EObject container = deletedObject.eContainer();
		assertNotEquals(containerElement, container);

		CallEvent event = MessageUtils.getCallEvent((Operation) (deletedObject));
		for (PackageableElement pe : containerElement.getNearestPackage().getPackagedElements()) {
			if (pe instanceof CallEvent) {
				assertNotNull(((CallEvent) pe).getOperation());
				assertNotEquals(deletedObject, ((CallEvent) pe).getOperation());
				assertNotEquals(pe, event);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validatePostUndo(Element containerElement, Element deletedObject, Object[] commandResults) throws Exception {
		// protocol message has been deleted, there should not be a callevent related to this deleted protocolmessage
		EObject container = deletedObject.eContainer();
		assertEquals(containerElement, container);

		CallEvent event = MessageUtils.getCallEvent((Operation) (deletedObject));
		assertNotNull(event);
		assertEquals(deletedObject, event.getOperation());
	}

}
