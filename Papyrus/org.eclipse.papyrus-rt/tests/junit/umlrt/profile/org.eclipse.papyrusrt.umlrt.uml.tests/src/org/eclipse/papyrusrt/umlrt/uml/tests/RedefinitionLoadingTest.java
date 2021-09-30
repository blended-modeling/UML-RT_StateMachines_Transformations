/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for loading redefinition relationships from persistent storage
 * (that inheritance is correctly resolved).
 */
@Category({ CapsuleTests.class, StateMachineTests.class })
@TestModel("inheritance/redef_persistence.uml")
@NoFacade
public class RedefinitionLoadingTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	private Class capsule2;
	private StateMachine sm2;
	private Region region2;
	private Interface protocol2Out;
	private Class capsule3;
	private StateMachine sm3;
	private Region region3;
	private Interface protocol3Out;

	public RedefinitionLoadingTest() {
		super();
	}

	@Test
	public void port() {
		assertRedefinition(capsule3.getOwnedPort("protocol2", null), is(capsule2.getOwnedPort("protocol2", null)));
	}

	@Test
	public void capsulePart() {
		assertRedefinition(capsule3.getOwnedAttribute("nested", null), is(capsule2.getOwnedAttribute("nested", null)));
	}

	@Test
	public void connector() {
		assertRedefinition(capsule3.getOwnedConnector("RTConnector1"), is(capsule2.getOwnedConnector("RTConnector1")));
	}

	@Test
	public void stateMachine() {
		assertRedefinition(sm3, is(sm2));
	}

	@Test
	public void region() {
		assertRedefinition(region3, is(region2));
	}

	@Test
	public void state() {
		assertRedefinition((State) region3.getSubvertex("State2"), is((State) region2.getSubvertex("State2")));
	}

	@Test
	public void transition() {
		assertRedefinition(region3.getTransition("Initial"), is(region2.getTransition("Initial")));
	}

	@Test
	public void protocolMessage() {
		assertRedefinition(protocol3Out.getOwnedOperation("ack", null, null),
				is(protocol2Out.getOwnedOperation("ack", null, null)));
	}

	//
	// Test framework
	//

	@Before
	public void getFixtures() {
		capsule2 = getCapsule("Capsule2");
		sm2 = (StateMachine) capsule2.getClassifierBehavior();
		region2 = sm2.getRegion("Region");
		protocol2Out = fixture.getElement("Protocol2::Protocol2~", Interface.class);
		capsule3 = getCapsule("Capsule3");
		sm3 = (StateMachine) capsule3.getClassifierBehavior();
		region3 = sm3.getRegion("Region");
		protocol3Out = fixture.getElement("Protocol3::Protocol3~", Interface.class);
	}

	@After
	public void cleanup() {
		capsule2 = null;
		sm2 = null;
		region2 = null;
		capsule3 = null;
		sm3 = null;
		region3 = null;
	}

	Class getCapsule(String name) {
		return fixture.getElement(name, Class.class);
	}

	<R extends RedefinableElement> void assertRedefinition(R element, Matcher<R> assertion) {
		assertThat(((InternalUMLRTElement) element).rtGetRedefinedElement(), assertion);
	}
}
