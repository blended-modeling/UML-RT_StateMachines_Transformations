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
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.atLeast;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.atMost;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the interaction of capsule generalization creation/deletion
 * and state machine creation in the façade API.
 */
@TestModel("inheritance/statemachines.uml")
@Category({ CapsuleTests.class, StateMachineTests.class, FacadeTests.class })
public class CapsuleGeneralizationWithStateMachinesTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public CapsuleGeneralizationWithStateMachinesTest() {
		super();
	}

	@Test
	public void createCapsuleGeneralizationThenStateMachine() {
		UMLRTCapsule subcapsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTCapsule newCapsule = fixture.getRoot().createCapsule("NewCapsule");

		newCapsule.setSuperclass(subcapsule);

		UMLRTStateMachine sm = newCapsule.createStateMachine();

		assertThat("State machine is not a redefinition", sm.getRedefinedStateMachine(), notNullValue());
		assertThat("Invalid state machine redefinition", sm.getRedefinedStateMachine().getCapsule(), is(subcapsule));

		assertInitialContents(sm);
		assertThat("Some content not inherited", sm.toUML().getRegions().size(), is(0));

		assertThat(sm.getVertices(), everyItem(isInherited()));
		assertThat(sm.getTransitions(), everyItem(isInherited()));
	}

	@Test
	public void createStateMachineThenCapsuleGeneralization() {
		UMLRTCapsule subcapsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTCapsule newCapsule = fixture.getRoot().createCapsule("NewCapsule");

		UMLRTStateMachine sm = newCapsule.createStateMachine();

		assertInitialContents(sm);

		newCapsule.setSuperclass(subcapsule);

		assertThat("State machine is not a redefinition", sm.getRedefinedStateMachine(), notNullValue());
		assertThat("Invalid state machine redefinition", sm.getRedefinedStateMachine().getCapsule(), is(subcapsule));

		assertInitialContents(sm);
		assertThat("Some content not inherited", sm.toUML().getRegions().size(), is(0));

		assertThat(sm.getVertices(), everyItem(isInherited()));
		assertThat(sm.getTransitions(), everyItem(isInherited()));
	}

	@Test
	public void createStateMachineWithStuffThenCapsuleGeneralization() {
		UMLRTCapsule subcapsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTCapsule newCapsule = fixture.getRoot().createCapsule("NewCapsule");

		UMLRTStateMachine sm = newCapsule.createStateMachine();

		assertInitialContents(sm);

		UMLRTPseudostate initial = requirePseudostate(sm, UMLRTPseudostateKind.INITIAL);
		UMLRTState newState = sm.createState("NewState");
		initial.createTransitionTo(newState).setName("new-transition");

		newCapsule.setSuperclass(subcapsule);

		assertThat("State machine is not a redefinition", sm.getRedefinedStateMachine(), notNullValue());
		assertThat("Invalid state machine redefinition", sm.getRedefinedStateMachine().getCapsule(), is(subcapsule));

		assertInitialContents(sm); // Still has this

		assertThat("Custom content lost", sm.toUML().getRegions().size(), is(1));

		UMLRTTransition newTransition = sm.getTransition("new-transition");
		assertThat("New transition lost", newTransition, notNullValue());
		assertThat("New transition source not changed", newTransition.getSource(), not(initial));
		assertThat("New transition target changed", newTransition.getTarget(), is(newState));

		assertThat("Too many initial pseudostates",
				sm.getVertices().stream()
						.filter(UMLRTPseudostate.class::isInstance).map(UMLRTPseudostate.class::cast)
						.filter(ps -> ps.getKind() == UMLRTPseudostateKind.INITIAL)
						.count(),
				atMost(1L));

		initial = requirePseudostate(sm, UMLRTPseudostateKind.INITIAL);
		assertThat("Wrong source for new transition", newTransition.getSource(), is(initial));
	}

	@TestModel("inheritance/ports.uml") // Existing hierarchy without any state machines
	@Test
	public void addStateMachineToSuperclass() {
		UMLRTCapsule rootCapsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTCapsule subsubcapsule = fixture.getRoot().getCapsule("Subsubcapsule");

		assumeThat("Root capsule already has a state machine", rootCapsule.getStateMachine(), nullValue());
		UMLRTStateMachine root = rootCapsule.createStateMachine();

		UMLRTStateMachine sub = subcapsule.getStateMachine();
		assertThat(sub, notNullValue());
		assertThat(sub, isInherited());
		assertThat(sub.getRedefinedElement(), is(root));

		UMLRTStateMachine subsub = subsubcapsule.getStateMachine();
		assertThat(subsub, notNullValue());
		assertThat(subsub, isInherited());
		assertThat(subsub.getRedefinedElement(), is(sub));
	}

	@TestModel("inheritance/ports.uml") // Existing hierarchy without any state machines
	@Test
	public void deleteStateMachineFromSuperclass() {
		UMLRTCapsule rootCapsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTCapsule subsubcapsule = fixture.getRoot().getCapsule("Subsubcapsule");

		assumeThat("Root capsule already has a state machine", rootCapsule.getStateMachine(), nullValue());
		UMLRTStateMachine root = rootCapsule.createStateMachine();

		UMLRTStateMachine sub = subcapsule.getStateMachine();
		assumeThat(sub, notNullValue());
		UMLRTStateMachine subsub = subsubcapsule.getStateMachine();
		assumeThat(subsub, notNullValue());

		root.destroy();

		assertThat(sub.getCapsule(), nullValue());
		assertThat(subsub.getCapsule(), nullValue());

		assertThat(subcapsule.getStateMachine(), nullValue());
		assertThat(subsubcapsule.getStateMachine(), nullValue());
	}

	//
	// Test framework
	//

	Optional<UMLRTPseudostate> getPseudostate(UMLRTStateMachine sm, UMLRTPseudostateKind kind) {
		return sm.getVertices().stream()
				.filter(UMLRTPseudostate.class::isInstance).map(UMLRTPseudostate.class::cast)
				.filter(ps -> ps.getKind() == kind)
				.findAny();
	}

	UMLRTPseudostate requirePseudostate(UMLRTStateMachine sm, UMLRTPseudostateKind kind) {
		return getPseudostate(sm, kind).orElseThrow(() -> new AssertionError("No such pseudostate: " + kind));
	}

	Optional<UMLRTState> getState(UMLRTStateMachine sm, String name) {
		return Optional.ofNullable(sm.getVertex(name))
				.filter(UMLRTState.class::isInstance).map(UMLRTState.class::cast);
	}

	UMLRTState requireState(UMLRTStateMachine sm, String name) {
		return getState(sm, name).orElseThrow(() -> new AssertionError("No such state: " + name));
	}

	Stream<UMLRTTransition> transitions(UMLRTVertex from, UMLRTVertex to) {
		return from.getOutgoings().stream().filter(t -> t.getTarget() == to);
	}

	List<UMLRTTransition> requireTransitions(UMLRTVertex from, UMLRTVertex to) {
		List<UMLRTTransition> result = transitions(from, to).collect(Collectors.toList());
		assertThat("No transitions", result, hasItem(anything()));
		return result;
	}

	UMLRTTransition requireUniqueTransition(UMLRTVertex from, UMLRTVertex to) {
		List<UMLRTTransition> all = requireTransitions(from, to);
		assertThat("More than one transition", all.size(), is(1));
		return all.get(0);
	}

	void assertInitialContents(UMLRTStateMachine sm) {
		List<UMLRTVertex> vertices = sm.getVertices();
		assertThat("Too few initial vertices", vertices.size(), atLeast(2));

		UMLRTPseudostate initial = requirePseudostate(sm, UMLRTPseudostateKind.INITIAL);
		UMLRTState state1 = requireState(sm, "State1");

		requireUniqueTransition(initial, state1);
	}

}
