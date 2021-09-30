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

import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.isInherited;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.isRedefined;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.isRootDefinition;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.named;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.redefines;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the transition façade in {@link UMLRTTransition}.
 */
@TestModel("inheritance/statemachines.uml")
@Category({ StateMachineTests.class, FacadeTests.class })
public class TriggerFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public TriggerFacadeTest() {
		super();
	}

	@Test
	public void getPorts() {
		UMLRTTrigger trigger = getTrigger("RootCapsule");

		assertThat(trigger.getPorts().size(), is(1));
		assertThat(trigger.getPorts().get(0), named("protocol1"));
	}

	@Test
	public void addPort() {
		UMLRTTrigger trigger = getTrigger("RootCapsule");

		assumeThat(trigger.getPorts().size(), is(1));
		UMLRTPort first = trigger.getPorts().get(0);
		assumeThat(first, named("protocol1"));
		trigger.getPorts().add(first.getCapsule().getPort("protocol2"));

		assertThat(trigger.toUML().getPorts().size(), is(2));
		assertThat(trigger.toUML().getPorts().get(1), UMLMatchers.named("protocol2"));
	}

	@Test
	public void removePort() {
		UMLRTTrigger trigger = getTrigger("RootCapsule");

		assumeThat(trigger.getPorts().size(), is(1));
		UMLRTPort first = trigger.getPorts().get(0);
		assumeThat(first, FacadeMatchers.named("protocol1"));
		trigger.getPorts().remove(first);

		assertThat(trigger.toUML().getPorts().size(), is(0));
	}

	@Test
	public void getPort() {
		UMLRTTrigger trigger = getTrigger("RootCapsule");

		assertThat(trigger.getPort(), notNullValue());
		assertThat(trigger.getPort(), named("protocol1"));
	}

	@Test
	public void setPort() {
		UMLRTTrigger trigger = getTrigger("RootCapsule");

		UMLRTPort port = trigger.getPort();
		assumeThat(port, notNullValue());
		assumeThat(port, named("protocol1"));

		trigger.setPort(port.getCapsule().getPort("protocol2"));

		assertThat(trigger.toUML().getPorts().size(), is(1));
		assertThat(trigger.toUML().getPort("protocol2", null), notNullValue());
	}

	@Test
	public void setPortNull() {
		UMLRTTrigger trigger = getTrigger("RootCapsule");

		UMLRTPort port = trigger.getPort();
		assumeThat(port, notNullValue());
		assumeThat(port, named("protocol1"));

		trigger.setPort(null);

		assertThat(trigger.toUML().getPorts().size(), is(0));

		assertThat(trigger.getPort(), nullValue());
	}

	@Test
	public void setPortWasMultiple() {
		UMLRTTrigger trigger = getTrigger("RootCapsule");

		UMLRTPort port = trigger.getPort();
		assumeThat(port, notNullValue());
		assumeThat(port, named("protocol1"));

		trigger.getPorts().add(port.getCapsule().getPort("protocol2"));
		assumeThat(trigger.toUML().getPorts().size(), is(2));

		trigger.setPort(port.getCapsule().getPort("protocol2"));

		assertThat(trigger.toUML().getPorts().size(), is(1));
		assertThat(trigger.toUML().getPort("protocol2", null), notNullValue());
	}

	@Test
	public void setPortNullWasMultiple() {
		UMLRTTrigger trigger = getTrigger("RootCapsule");

		UMLRTPort port = trigger.getPort();
		assumeThat(port, notNullValue());
		assumeThat(port, named("protocol1"));

		trigger.getPorts().add(port.getCapsule().getPort("protocol2"));
		assumeThat(trigger.toUML().getPorts().size(), is(2));

		trigger.setPort(null);

		assertThat(trigger.toUML().getPorts().size(), is(0));
	}

	@Test(expected = IllegalStateException.class)
	public void getPortMultiple() {
		UMLRTTrigger trigger = getTrigger("RootCapsule");

		UMLRTPort port = trigger.getPort();
		assumeThat(port, notNullValue());
		assumeThat(port, named("protocol1"));

		trigger.getPorts().add(port.getCapsule().getPort("protocol2"));
		assumeThat(trigger.toUML().getPorts().size(), is(2));

		port = trigger.getPort();
	}

	@Test
	public void undoRedoDelete() {
		UMLRTTrigger trigger = getTrigger("RootCapsule");

		assumeThat(trigger.getPort(), notNullValue());
		assumeThat(trigger.getPort(), named("protocol1"));

		ChangeDescription change = fixture.record(() -> fixture.destroy(trigger.toUML()));

		fixture.repeat(3, () -> {
			// Undo
			fixture.undo(change);

			assertThat(trigger.getPort(), notNullValue());
			assertThat(trigger.getPort(), named("protocol1"));
			assertThat(trigger.getPort().getType(), is(fixture.getRoot().getProtocol("Protocol1")));

			// Redo delete for the next iteration
			fixture.undo(change);
		});
	}

	@Test
	public void reinheritDoesNotUnsetRedefinedTriggerPorts() {
		UMLRTTrigger trigger = getTrigger("Subcapsule");
		assumeThat(trigger.getPort(), notNullValue());
		assumeThat(trigger.getPort(), named("protocol1"));

		UMLRTTrigger root = trigger.getRedefinedTrigger();
		assumeThat(root, notNullValue());
		assumeThat(root.getPort(), notNullValue());
		assumeThat(trigger.getPort(), redefines(root.getPort()));

		UMLRTGuard guard = trigger.getGuard();
		assumeThat("No trigger guard", guard, notNullValue());

		// Redefine the guard
		guard.getBodies().put("C++", "true");
		assumeThat(guard, isRedefined());

		// Re-inherit the transition
		ChangeDescription change = fixture.record(guard::reinherit);

		fixture.repeat(3, () -> {
			assertThat(guard, isInherited());
			assertThat(trigger.getPort(), notNullValue());

			assumeThat(root, isRootDefinition());
			assertThat(root.getPort(), notNullValue());
			assertThat(trigger.getPort(), redefines(root.getPort()));

			// Undo
			fixture.undo(change);

			assertThat(guard, isRedefined());
			assertThat(trigger.getPort(), notNullValue());

			assumeThat(root, isRootDefinition());
			assertThat(root.getPort(), notNullValue());
			assertThat(trigger.getPort(), redefines(root.getPort()));

			// Redo re-inherit for the next iteration
			fixture.undo(change);
		});
	}

	@Test
	public void allRedefinitions() {
		fixture.getRoot().getCapsule("Subsubcapsule").createStateMachine();

		UMLRTTrigger rootTrigger = getTrigger("RootCapsule");
		UMLRTTrigger subTrigger = getTrigger("Subcapsule");
		UMLRTTrigger subsubTrigger = getTrigger("Subsubcapsule");

		List<UMLRTTrigger> expected = Arrays.asList(rootTrigger, subTrigger, subsubTrigger);
		List<? extends UMLRTTrigger> actual = rootTrigger.allRedefinitions().collect(Collectors.toList());
		// There may be other redefinitions, too. Important to keep the order
		actual.retainAll(expected);
		assertThat(actual, is(expected));
	}

	//
	// Test framework
	//

	UMLRTTrigger getTrigger(String capsuleName) {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule(capsuleName);
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());
		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());
		assumeThat(transition.getTriggers().isEmpty(), is(false));

		return transition.getTriggers().get(0);
	}

}
