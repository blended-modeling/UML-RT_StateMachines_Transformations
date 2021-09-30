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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Event;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Specific regression test cases for transition trigger inheritance
 * in state machines.
 */
@TestModel("inheritance/guards.uml")
@Category(StateMachineTests.class)
@RunWith(Parameterized.class)
public class TriggerInheritanceTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	private final String capsuleName;

	public TriggerInheritanceTest(String capsuleName, Object __) {
		super();

		this.capsuleName = capsuleName;
	}

	@NoFacade
	@Test
	public void inheritedTriggerEvent() {
		Trigger trigger = getTrigger();
		Event event = trigger.getEvent();

		assertThat("No event", event, notNullValue());
		assertThat("Not a call event", event, instanceOf(CallEvent.class));

		CallEvent call = (CallEvent) event;
		Operation operation = call.getOperation();
		assertThat("No operation called", operation, notNullValue());
		assertThat(operation, UMLMatchers.named("greet"));
	}

	@NoFacade
	@Test
	public void inheritedTriggerPort() {
		Trigger trigger = getTrigger();
		Port port = trigger.getPort("protocol1", null);

		assertThat("Port not found", port, notNullValue());

		assertThat("Not the inherited port", port, UMLMatchers.isInherited());
		assertThat(port.getClass_(), notNullValue());
		assertThat(port.getClass_(), UMLMatchers.named(capsuleName));
	}

	@Test
	public void inheritedTriggerPortFacade() {
		UMLRTTrigger trigger = getTriggerFacade();
		UMLRTPort port = trigger.getPort();

		assertThat("Port not found", port, notNullValue());

		assertThat("Not the inherited port", port, FacadeMatchers.isInherited());
		assertThat(port.getCapsule(), notNullValue());
		assertThat(port.getCapsule(), FacadeMatchers.named(capsuleName));
	}

	//
	// Test framework
	//

	@Parameters(name = "{1}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ "Subcapsule", "redefining SM" },
				{ "Subcapsule2", "inherited SM" },
		});
	}

	Transition getTransition() {
		Region region = fixture.getElement(String.format("%s::StateMachine::Region", capsuleName), Region.class);
		return region.getTransition("initial");
	}

	Trigger getTrigger() {
		// Yes, the initial transition isn't supposed to have a trigger,
		// but it's convenient for this test
		List<Trigger> result = UMLRTExtensionUtil.getUMLRTContents(getTransition(), UMLPackage.Literals.TRANSITION__TRIGGER);
		return result.get(0);
	}

	UMLRTTransition getTransitionFacade() {
		return fixture.getRoot().getCapsule(capsuleName).getStateMachine()
				.getTransition("initial");
	}

	UMLRTTrigger getTriggerFacade() {
		// Yes, the initial transition isn't supposed to have a trigger,
		// but it's convenient for this test
		return getTransitionFacade().getTriggers().get(0);
	}
}
