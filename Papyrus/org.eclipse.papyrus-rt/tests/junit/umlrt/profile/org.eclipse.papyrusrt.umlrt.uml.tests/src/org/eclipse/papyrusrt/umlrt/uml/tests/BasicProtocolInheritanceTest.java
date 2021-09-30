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

import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.isRedefinition;
import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.isVirtualElement;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.ProtocolTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the UML-RT protocol semantics of {@link Collaboration}.
 */
@TestModel("inheritance/ports.uml")
@NoFacade
@Category(ProtocolTests.class)
public class BasicProtocolInheritanceTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public BasicProtocolInheritanceTest() {
		super();
	}

	@Test
	public void inheritance() {
		Collaboration protocol1 = getProtocol("Protocol1");
		Collaboration protocol2 = getProtocol("Protocol2");

		protocol2.createGeneralization(protocol1);

		// Inherited messages
		Operation greet = getInMessageSet("Protocol2").getOwnedOperation("greet", null, null);
		assertThat(greet, notNullValue());
		assertThat(isVirtualElement(greet), is(true));
	}

	@Test
	public void newMessageInherited() {
		Collaboration protocol1 = getProtocol("Protocol1");
		Collaboration protocol2 = getProtocol("Protocol2");

		protocol2.createGeneralization(protocol1);

		getOutMessageSet("Protocol1").createOwnedOperation("ack", null, null);
		Operation ack = getOutMessageSet("Protocol2").getOwnedOperation("ack", null, null);
		assertThat(ack, notNullValue());
		assertThat(isVirtualElement(ack), is(true));
	}

	@Test
	public void messageRedefinition() {
		Collaboration protocol1 = getProtocol("Protocol1");
		Collaboration protocol2 = getProtocol("Protocol2");

		protocol2.createGeneralization(protocol1);

		Operation greet = getInMessageSet("Protocol2").getOwnedOperation("greet", null, null);
		assumeThat(greet, notNullValue());
		assumeThat(isVirtualElement(greet), is(true));

		Parameter data = greet.getOwnedParameter("data", null);
		assumeThat(data, notNullValue());
		assertThat(isVirtualElement(data), is(true));

		Type oldType = data.getType();
		assumeThat(oldType, notNullValue());
		assertThat(data.eIsSet(UMLPackage.Literals.TYPED_ELEMENT__TYPE), is(false));

		data.setType(protocol1); // Not really valid, but convenient

		assertThat(isVirtualElement(data), is(false));
		assertThat(isVirtualElement(greet), is(false));

		assertThat(isRedefinition(data), is(true));
		assertThat(isRedefinition(greet), is(true));
	}

	@Test
	public void messageReinheritance() {
		Collaboration protocol1 = getProtocol("Protocol1");
		Collaboration protocol2 = getProtocol("Protocol2");

		protocol2.createGeneralization(protocol1);

		Operation greet = getInMessageSet("Protocol2").getOwnedOperation("greet", null, null);
		assumeThat(greet, notNullValue());
		assumeThat(isVirtualElement(greet), is(true));

		Parameter data = greet.getOwnedParameter("data", null);
		assumeThat(data, notNullValue());

		Type oldType = data.getType();
		assumeThat(oldType, notNullValue());

		data.setType(protocol1); // Not really valid, but convenient

		assumeThat(isRedefinition(data), is(true));
		assumeThat(isRedefinition(greet), is(true));

		((InternalUMLRTElement) greet).rtReinherit();

		assertThat(isVirtualElement(greet), is(true));
		assertThat(isVirtualElement(data), is(true));

		assertThat(data.eIsSet(UMLPackage.Literals.TYPED_ELEMENT__TYPE), is(false));
		assertThat(data.getType(), is(oldType));
	}

	//
	// Test framework
	//

	Collaboration getProtocol(String name) {
		return fixture.getElement(name + NamedElement.SEPARATOR + name, Collaboration.class);
	}

	Interface getInMessageSet(String name) {
		return fixture.getElement(name + NamedElement.SEPARATOR + name, Interface.class);
	}

	Interface getOutMessageSet(String name) {
		return fixture.getElement(name + NamedElement.SEPARATOR + name + "~", Interface.class);
	}
}
