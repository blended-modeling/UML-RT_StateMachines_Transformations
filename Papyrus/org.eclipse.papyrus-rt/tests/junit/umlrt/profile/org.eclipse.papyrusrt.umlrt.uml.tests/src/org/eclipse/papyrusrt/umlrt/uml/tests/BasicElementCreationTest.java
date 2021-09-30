/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.uml.tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for basic element creation.
 */
@TestModel("inheritance/ports.uml")
@NoFacade
@Category(CapsuleTests.class)
public class BasicElementCreationTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public BasicElementCreationTest() {
		super();
	}

	@Test
	public void createCapsule() {
		Class newCapsule = fixture.getModel().createOwnedClass("NewCapsule", false);
		assertThat(newCapsule, instanceOf(InternalUMLRTElement.class));
	}

	@Test
	public void createCapsuleAsOwnedType() {
		Class newCapsule = (Class) fixture.getModel().createOwnedType("NewCapsule", UMLPackage.Literals.CLASS);
		assertThat(newCapsule, instanceOf(InternalUMLRTElement.class));
	}

	@Test
	public void createPort() {
		Class capsule = fixture.getElement("RootCapsule");
		Port newPort = capsule.createOwnedPort("newPort", null);
		assertThat(newPort, instanceOf(InternalUMLRTElement.class));
	}

	@Test
	public void createPart() {
		Class capsule = fixture.getElement("RootCapsule");
		Property newPart = capsule.createOwnedAttribute("sub", fixture.getElement("Subcapsule"));
		assertThat(newPart, instanceOf(InternalUMLRTElement.class));
	}

	@Test
	public void createConnector() {
		Class capsule = fixture.getElement("RootCapsule");
		Connector newConnector = capsule.createOwnedConnector(null);
		assertThat(newConnector, instanceOf(InternalUMLRTElement.class));

		assertThat(newConnector.createEnd(), instanceOf(InternalUMLRTElement.class));
	}

}
