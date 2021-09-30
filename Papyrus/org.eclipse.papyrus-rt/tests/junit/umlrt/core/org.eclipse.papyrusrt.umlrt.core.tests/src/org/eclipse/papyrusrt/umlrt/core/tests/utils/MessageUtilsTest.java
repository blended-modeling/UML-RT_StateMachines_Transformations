/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Services GmbH
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alexandra Buzila (EclipseSource) - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.tests.utils;

import org.eclipse.papyrusrt.umlrt.core.utils.MessageUtils;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Test;

/**
 * Test class for {@link MessageUtils}.
 * 
 * @author Alexandra Buzila
 *
 */
public class MessageUtilsTest {

	/**
	 * Tests that no NPE is thrown when calling {@link MessageUtils#getCallEvent(Operation)} for an operation contained in a package that contains CallEvents without operations.
	 * See <a href=https://bugs.eclipse.org/bugs/show_bug.cgi?id=495583>Bug 495583</a>.
	 */
	@Test
	public void testGetCallEventNPENoOperation() {
		// setup
		Package package1 = UMLFactory.eINSTANCE.createPackage();
		Interface interface1 = UMLFactory.eINSTANCE.createInterface();
		Operation operation = UMLFactory.eINSTANCE.createOperation();
		CallEvent callEvent = UMLFactory.eINSTANCE.createCallEvent();
		package1.getPackagedElements().add(interface1);
		interface1.getOwnedOperations().add(operation);
		/* add CallEvent with no operation to same package as the operation */
		package1.getPackagedElements().add(callEvent);

		// the test will fail if an exception is thrown in the getCallEvent method
		MessageUtils.getCallEvent(operation);
	}
}
