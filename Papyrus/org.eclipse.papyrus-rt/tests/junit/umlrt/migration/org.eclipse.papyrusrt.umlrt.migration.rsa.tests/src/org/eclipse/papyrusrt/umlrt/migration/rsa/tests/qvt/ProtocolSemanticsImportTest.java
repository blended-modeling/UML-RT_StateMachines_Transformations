/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.migration.rsa.tests.qvt;

import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * Specific test cases for import of semantics of Protocols.
 */
public class ProtocolSemanticsImportTest extends AbstractTransformationTest {

	/**
	 * Initializes me.
	 */
	public ProtocolSemanticsImportTest() {
		super();
	}

	/**
	 * Verify that call event names and interface realization names are unset,
	 * not explicit-{@code null} nor empty strings, after import.
	 * 
	 * @see <a href="http://eclip.se/495572">bug 489323</a>
	 */
	@Test
	public void protocolElementNamesUnset() throws Exception {
		simpleImport("resources/rt/ProtocolModel.emx");

		openEditor();

		org.eclipse.uml2.uml.Package container = rootPackage.getNestedPackage("MyExampleProtocol");
		Collaboration protocol = (Collaboration) container.getOwnedType("MyExampleProtocol");

		protocol.getInterfaceRealizations().forEach(ir -> assertThat(ir, isUnset(UMLPackage.Literals.NAMED_ELEMENT__NAME)));

		container.getPackagedElements().stream()
				.filter(CallEvent.class::isInstance)
				.forEach(ce -> assertThat(ce, isUnset(UMLPackage.Literals.NAMED_ELEMENT__NAME)));
	}

}
