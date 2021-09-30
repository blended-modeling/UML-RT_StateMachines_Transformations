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

import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.isInherited;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the state machine façade in {@link UMLRTStateMachine}.
 */
@TestModel("inheritance/statemachines.uml")
@Category({ StateMachineTests.class, FacadeTests.class })
public class VertexFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public VertexFacadeTest() {
		super();
	}

	@Test
	public void incomings() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTVertex vertex = sm.getVertex("State1");
		assumeThat(vertex, notNullValue());

		List<UMLRTTransition> incomings = vertex.getIncomings();
		assertThat(incomings.size(), is(1));
		assertThat(incomings.get(0).getTarget(), is(vertex));
	}

	@Test
	public void outgoings() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTVertex vertex = sm.getVertex("State1");
		assumeThat(vertex, notNullValue());

		List<UMLRTTransition> outgoings = vertex.getOutgoings();
		assertThat(outgoings.size(), is(1));
		assertThat(outgoings.get(0).getSource(), is(vertex));
	}

	@Test
	public void redirectIncoming() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTVertex vertex = sm.getVertex("State1");
		assumeThat(vertex, notNullValue());

		List<UMLRTTransition> incomings = vertex.getIncomings();
		assumeThat(incomings.size(), is(1));
		UMLRTTransition transition = incomings.get(0);
		assumeThat(transition.getSource(), not(vertex));

		vertex.getOutgoings().add(transition);
		assertThat(transition.getSource(), is(vertex));
	}

	@Test
	public void redirectOutgoing() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTVertex vertex = sm.getVertex("State1");
		assumeThat(vertex, notNullValue());

		List<UMLRTTransition> outgoings = vertex.getOutgoings();
		assertThat(outgoings.size(), is(1));
		assertThat(outgoings.get(0).getSource(), is(vertex));
		UMLRTTransition transition = outgoings.get(0);
		assumeThat(transition.getTarget(), not(vertex));

		vertex.getIncomings().add(transition);
		assertThat(transition.getTarget(), is(vertex));
	}

	@Test
	public void createTransition() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTVertex choice1 = sm.getVertex("Choice1");
		assumeThat(choice1, notNullValue());
		UMLRTVertex state1 = sm.getVertex("State1");
		assumeThat(state1, notNullValue());

		UMLRTTransition transition = state1.createTransitionTo(choice1);
		assertThat(transition.toUML().getSource(), is(state1.toUML()));
		assertThat(transition.toUML().getTarget(), is(choice1.toUML()));
		assertThat(transition.getStateMachine(), is(sm));
		assertThat(transition.toUML().getContainer().getStateMachine(), is(sm.toUML()));

		assertThat(state1.getOutgoings(), hasItem(transition));
		assertThat(choice1.getIncomings(), hasItem(transition));
	}

	@Test
	public void inheritedIncomings() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTVertex vertex = sm.getVertex("State1");
		assumeThat(vertex, notNullValue());

		List<UMLRTTransition> incomings = vertex.getIncomings();
		assertThat(incomings.size(), is(1));
		UMLRTTransition incoming = incomings.get(0);
		assertThat(incoming.getTarget(), is(vertex));
		assertThat(incoming.toUML(), isInherited());
	}

	@Test
	public void inheritedOutgoings() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTVertex vertex = sm.getVertex("State1");
		assumeThat(vertex, notNullValue());

		List<UMLRTTransition> outgoings = vertex.getOutgoings();
		assertThat(outgoings.size(), is(1));
		UMLRTTransition outgoing = outgoings.get(0);
		assertThat(outgoing.getSource(), is(vertex));
		assertThat(outgoing.toUML(), isInherited());
	}

	@Test
	public void pseudostateAllRedefinitions() {
		UMLRTStateMachine rootSM = getRootCapsuleBehavior();
		UMLRTStateMachine subSM = getSubcapsuleBehavior();
		UMLRTStateMachine subsubSM = getSubsubcapsuleBehavior();

		UMLRTPseudostate rootPseudostate = getPseudostate(rootSM, UMLRTPseudostateKind.INITIAL);
		UMLRTPseudostate subPseudostate = getPseudostate(subSM, UMLRTPseudostateKind.INITIAL);
		UMLRTPseudostate subsubPseudostate = getPseudostate(subsubSM, UMLRTPseudostateKind.INITIAL);

		List<UMLRTPseudostate> expected = Arrays.asList(rootPseudostate, subPseudostate, subsubPseudostate);
		List<? extends UMLRTPseudostate> actual = rootPseudostate.allRedefinitions().collect(Collectors.toList());
		// There may be other redefinitions, too. Important to keep the order
		actual.retainAll(expected);
		assertThat(actual, is(expected));
	}

	@Test
	public void connectionPointAllRedefinitions() {
		UMLRTStateMachine rootSM = getRootCapsuleBehavior();
		UMLRTStateMachine subSM = getSubcapsuleBehavior();
		UMLRTStateMachine subsubSM = getSubsubcapsuleBehavior();

		UMLRTConnectionPoint rootConnectionPoint = getConnectionPoint(rootSM, "composite", UMLRTConnectionPointKind.EXIT);
		UMLRTConnectionPoint subConnectionPoint = getConnectionPoint(subSM, "composite", UMLRTConnectionPointKind.EXIT);
		UMLRTConnectionPoint subsubConnectionPoint = getConnectionPoint(subsubSM, "composite", UMLRTConnectionPointKind.EXIT);

		List<UMLRTConnectionPoint> expected = Arrays.asList(rootConnectionPoint, subConnectionPoint, subsubConnectionPoint);
		List<? extends UMLRTConnectionPoint> actual = rootConnectionPoint.allRedefinitions().collect(Collectors.toList());
		// There may be other redefinitions, too. Important to keep the order
		actual.retainAll(expected);
		assertThat(actual, is(expected));
	}

	//
	// Test framework
	//

	UMLRTStateMachine getRootCapsuleBehavior() {
		return fixture.getRoot().getCapsule("RootCapsule").getStateMachine();
	}

	UMLRTStateMachine getSubcapsuleBehavior() {
		return fixture.getRoot().getCapsule("Subcapsule").getStateMachine();
	}

	UMLRTStateMachine getSubsubcapsuleBehavior() {
		return fixture.getRoot().getCapsule("Subsubcapsule").createStateMachine();
	}

	UMLRTPseudostate getPseudostate(UMLRTStateMachine sm, UMLRTPseudostateKind kind) {
		UMLRTPseudostate result = sm.getVertices().stream()
				.filter(UMLRTPseudostate.class::isInstance).map(UMLRTPseudostate.class::cast)
				.filter(ps -> ps.getKind() == kind)
				.findAny().orElse(null);
		assumeThat("Pseudostate not found", result, notNullValue());

		return result;
	}

	UMLRTState getState(UMLRTStateMachine sm, String name) {
		UMLRTVertex result = sm.getVertex(name);
		assumeThat("State not found", result, instanceOf(UMLRTState.class));

		return (UMLRTState) result;
	}

	UMLRTConnectionPoint getConnectionPoint(UMLRTStateMachine sm, String stateName, UMLRTConnectionPointKind kind) {
		UMLRTState state = getState(sm, stateName);
		UMLRTConnectionPoint result = state.getConnectionPoints().stream()
				.filter(cp -> cp.getKind() == kind)
				.findAny().orElse(null);
		assumeThat("Connection point not found", result, notNullValue());

		return result;
	}
}
