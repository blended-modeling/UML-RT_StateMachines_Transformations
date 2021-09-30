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

import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.isRootDefinition;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.named;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.redefines;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.stereotypedAs;
import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.getUMLRTContents;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InternalFacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the state machine façade in {@link UMLRTStateMachine}.
 */
@TestModel("inheritance/statemachines.uml")
@Category({ StateMachineTests.class, FacadeTests.class })
public class StateMachineFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public StateMachineFacadeTest() {
		super();
	}

	@Test
	public void stateMachine() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");

		UMLRTStateMachine sm = capsule.getStateMachine();

		assertThat("No state machine façade", sm, notNullValue());

		assertThat(sm.getName(), is("StateMachine"));

		sm.setName("My Behavior");

		assertThat(sm.toUML().getName(), is("My Behavior"));
	}

	@Test
	public void createStateMachine() {
		UMLRTCapsule capsule = fixture.getRoot().createCapsule("NewCapsule");

		UMLRTStateMachine sm = capsule.createStateMachine();

		assertThat("No state machine façade", sm, notNullValue());

		assertThat(sm.getName(), is("StateMachine"));

		assertThat(sm.toUML().isReentrant(), is(false));
	}

	@Test
	public void vertices() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		List<UMLRTVertex> vertices = sm.getVertices();
		assertThat(vertices.size(), is(4));

		UMLRTVertex vertex = sm.getVertex("State1");
		assertThat(vertex, notNullValue());
		assertThat(vertex, instanceOf(UMLRTState.class));
		assertThat(vertex.getStateMachine(), is(sm));
	}

	@Test
	public void createState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		List<UMLRTVertex> vertices = sm.getVertices();
		assumeThat(vertices.size(), is(4));

		UMLRTState newState = sm.createState("NewState");
		assertThat(newState, notNullValue());
		assertThat(newState.toUML(), stereotypedAs("UMLRTStateMachines::RTState"));
		assertThat(newState.getName(), is("NewState"));

		assertThat(vertices.size(), is(5));
		assertThat(vertices, hasItem(newState));
	}

	@Test
	public void createPseudostate() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		List<UMLRTVertex> vertices = sm.getVertices();
		assumeThat(vertices.size(), is(4));

		UMLRTPseudostate newPseudostate = sm.createPseudostate(UMLRTPseudostateKind.HISTORY);
		assertThat(newPseudostate, notNullValue());
		assertThat(newPseudostate.toUML(), stereotypedAs("UMLRTStateMachines::RTPseudostate"));
		assertThat(newPseudostate.toUML().getKind(), is(PseudostateKind.DEEP_HISTORY_LITERAL));

		assertThat(vertices.size(), is(5));
		assertThat(vertices, hasItem(newPseudostate));
	}

	@Test
	public void transitions() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		List<UMLRTTransition> transitions = sm.getTransitions();
		assertThat(transitions.size(), is(4));

		UMLRTTransition transition = sm.getTransition("in");
		assertThat(transition, notNullValue());
		assertThat(transition.getStateMachine(), is(sm));
	}

	@Test
	public void redefiningStateMachine() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");

		UMLRTStateMachine sm = capsule.getStateMachine();

		assertThat("No state machine façade", sm, notNullValue());

		assertThat(sm.getRedefinedStateMachine(), notNullValue());
		assertThat(sm.getRedefinedStateMachine().getCapsule(), is(capsule.getSuperclass()));
		assertThat(sm.getInheritanceKind(), is(UMLRTInheritanceKind.REDEFINED));
	}

	@Test
	public void inheritedVertices() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		List<UMLRTVertex> vertices = sm.getVertices();
		assertThat(vertices.size(), is(4));

		UMLRTVertex vertex = sm.getVertex("State1");
		assertThat(vertex, notNullValue());
		assertThat(vertex, instanceOf(UMLRTState.class));
		assertThat(vertex.getStateMachine(), is(sm));

		vertices.forEach(v -> assertThat(v.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED)));
	}

	@Test
	public void inheritCreatedState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		List<UMLRTVertex> vertices = sm.getVertices();
		assumeThat(vertices.size(), is(4));

		sm.getRedefinedStateMachine().createState("NewState");

		UMLRTState newState = (UMLRTState) sm.getVertex("NewState");
		assertThat(newState, notNullValue());
		assertThat(newState.toUML(), stereotypedAs("UMLRTStateMachines::RTState"));
		assertThat(newState.getName(), is("NewState"));

		assertThat(vertices.size(), is(5));
		assertThat(vertices, hasItem(newState));
		assertThat(newState.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
	}

	@Test
	public void inheritCreatedPseudostate() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		List<UMLRTVertex> vertices = sm.getVertices();
		assumeThat(vertices.size(), is(4));

		UMLRTPseudostate ps = sm.getRedefinedStateMachine().createPseudostate(UMLRTPseudostateKind.HISTORY);

		UMLRTPseudostate newPseudostate = sm.getRedefinitionOf(ps);
		assertThat(newPseudostate, notNullValue());
		assertThat(newPseudostate.toUML(), stereotypedAs("UMLRTStateMachines::RTPseudostate"));
		assertThat(newPseudostate.toUML().getKind(), is(PseudostateKind.DEEP_HISTORY_LITERAL));

		assertThat(vertices.size(), is(5));
		assertThat(vertices, hasItem(newPseudostate));
		assertThat(newPseudostate.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
	}

	@Test
	public void inheritedTransitions() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		List<UMLRTTransition> transitions = sm.getTransitions();
		assertThat(transitions.size(), is(4));

		UMLRTTransition transition = sm.getTransition("in");
		assertThat(transition, notNullValue());
		assertThat(transition.getStateMachine(), is(sm));
		assertThat(transition.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
	}

	@Test
	public void inheritRedefiningStateMachine() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subsubcapsule");

		UMLRTStateMachine sm = capsule.getStateMachine();
		assertThat("No state machine inherited", sm, notNullValue());

		assertThat(sm.getRedefinedStateMachine(), notNullValue());
		assertThat(sm.getRedefinedStateMachine().getCapsule(), is(capsule.getSuperclass()));
		assertThat("State machine should be a redefinition", sm.getInheritanceKind(),
				is(UMLRTInheritanceKind.INHERITED));

		UMLRTStateMachine root = (UMLRTStateMachine) sm.getRootDefinition();
		assumeThat("No root state machine", root, notNullValue());
		assumeThat("Wrong root state machine", root.getCapsule().getName(), is("RootCapsule"));

		List<UMLRTVertex> vertices = sm.getVertices();
		assertThat(vertices.size(), is(root.getVertices().size()));

		List<UMLRTTransition> transitions = sm.getTransitions();
		assertThat(transitions.size(), is(root.getTransitions().size()));
	}

	@Test
	public void disinheritStateMachine() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subsubcapsule");

		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine inherited", sm, notNullValue());

		assumeThat(sm.getRedefinedStateMachine(), notNullValue());
		assumeThat(sm.getRedefinedStateMachine().getCapsule(), is(capsule.getSuperclass()));
		assumeThat(sm.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));

		// Disinherit
		capsule.setSuperclass(null);

		assertThat(sm.getRedefinedStateMachine(), nullValue());
		assertThat(sm, isRootDefinition());
	}

	@Test
	public void excludeTransition() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");

		UMLRTStateMachine sm = capsule.getStateMachine();

		assertThat("No state machine found", sm, notNullValue());

		List<UMLRTTransition> transitions = sm.getTransitions();
		UMLRTTransition initial = sm.getTransition("Initial");
		assumeThat(initial, notNullValue());

		initial.exclude();

		assertThat(transitions, not(hasItem(initial)));
		assertThat(sm.getTransition(initial.getName()), nullValue());

		assertThat(sm.getExcludedElement(initial.getName()), is(initial));
	}

	@Test
	public void excludeState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");

		UMLRTStateMachine sm = capsule.getStateMachine();

		assertThat("No state machine found", sm, notNullValue());

		List<UMLRTVertex> vertices = sm.getVertices();
		UMLRTState state1 = (UMLRTState) sm.getVertex("State1");
		assumeThat(state1, notNullValue());

		state1.exclude();

		assertThat(vertices, not(hasItem(state1)));
		assertThat(sm.getVertex(state1.getName()), nullValue());

		assertThat(sm.getExcludedElement(state1.getName()), is(state1));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void facadeGetVertices() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();

		List<UMLRTVertex> vertices = (List<UMLRTVertex>) ((InternalFacadeObject) sm).facadeGet(UMLRTUMLRTPackage.Literals.STATE_MACHINE__VERTEX, true);
		assertThat(vertices.size(), is(4));
		assertThat(vertices, hasItems(named("Choice1"), named("State1"), named("composite")));

		vertices.stream().filter(v -> "State1".equals(v.getName())).findAny().ifPresent(UMLRTVertex::exclude);

		assumeThat(sm.getVertices().size(), is(3));
		assertThat(sm.getVertices(), hasItems(named("Choice1"), named("composite")));
		assertThat(sm.getVertices(), not(hasItem(named("State1"))));

		vertices = (List<UMLRTVertex>) ((InternalFacadeObject) sm).facadeGet(UMLRTUMLRTPackage.Literals.STATE_MACHINE__VERTEX, true);
		assertThat(vertices.size(), is(4));
		assertThat(vertices, hasItems(named("Choice1"), named("State1"), named("composite")));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void facadeGetTransitions() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();

		List<UMLRTTransition> transitions = (List<UMLRTTransition>) ((InternalFacadeObject) sm).facadeGet(UMLRTUMLRTPackage.Literals.STATE_MACHINE__TRANSITION, true);
		assertThat(transitions.size(), is(4));
		assertThat(transitions, hasItems(named("Initial"), named("in"), named("out"), named("nestedIn")));

		transitions.stream().filter(t -> "in".equals(t.getName())).findAny().ifPresent(UMLRTTransition::exclude);

		assumeThat(sm.getTransitions().size(), is(3));
		assertThat(sm.getTransitions(), hasItems(named("Initial"), named("out"), named("nestedIn")));

		transitions = (List<UMLRTTransition>) ((InternalFacadeObject) sm).facadeGet(UMLRTUMLRTPackage.Literals.STATE_MACHINE__TRANSITION, true);
		assertThat(transitions.size(), is(4));
		assertThat(transitions, hasItems(named("Initial"), named("in"), named("out"), named("nestedIn")));
	}

	@Test
	public void deleteStateInParentStateMachine() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTCapsule subsubcapsule = fixture.getRoot().getCapsule("Subsubcapsule");

		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("Capsule has no state machine", sm, notNullValue());

		UMLRTStateMachine subSM = subcapsule.getStateMachine();
		assumeThat("Subcapsule has no state machine", subSM, notNullValue());

		UMLRTStateMachine subsubSM = subsubcapsule.getStateMachine();
		assumeThat("Subsubcapsule has no state machine", subsubSM, notNullValue());

		UMLRTVertex state1 = sm.getVertex("State1");
		assumeThat(state1, notNullValue());

		UMLRTVertex state1Redef = subSM.getVertex("State1");
		assumeThat(state1Redef, notNullValue());
		assumeThat(state1Redef, redefines(state1));

		UMLRTVertex state1RedefRedef = subsubSM.getVertex("State1");
		assumeThat(state1RedefRedef, notNullValue());
		assumeThat(state1RedefRedef, redefines(state1));

		ChangeDescription change = fixture.record(() -> {
			// Delete a state from the root capsule
			state1.destroy();
		});

		assertThat(subSM.getVertices(), not(hasItem(state1Redef)));
		assertThat(subsubSM.getVertices(), not(hasItem(state1RedefRedef)));

		fixture.undo(change);

		// The same façade objects needn't be restored, so don't test for that
		assertThat(subSM.getVertices(), hasItem(named("State1")));
		assertThat(subsubSM.getVertices(), hasItem(named("State1")));
	}

	@Test
	public void toRegion() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		Region region = sm.toRegion();
		assertThat("No region", region, notNullValue());
		assertThat("Empty region", region.getSubvertices(), hasItem(anything()));
	}

	@Test
	public void toRegionInherited() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		Region region = sm.toRegion();
		assertThat("No region", region, notNullValue());
		assertThat("Empty region", getUMLRTContents(region, UMLPackage.Literals.REGION__SUBVERTEX),
				hasItem(anything()));
	}


	@Test
	public void getRedefinitionOfRootState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();

		UMLRTState composite = (UMLRTState) sm.getVertex("composite");
		assumeThat(composite, notNullValue());
		assertThat(sm.getRedefinitionOf(composite), is(composite));

		UMLRTState nested = (UMLRTState) composite.getSubvertex("nested");
		assumeThat(nested, notNullValue());
		assertThat(composite.getRedefinitionOf(nested), is(nested));
		assertThat(sm.getRedefinitionOf(nested), is(nested));
	}

	@Test
	public void getRedefinitionOfRootStateInRedefiningStateMachine() {
		UMLRTCapsule rootCapsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine rootSM = rootCapsule.getStateMachine();

		UMLRTState composite = (UMLRTState) rootSM.getVertex("composite");
		assumeThat(composite, notNullValue());

		UMLRTState nested = (UMLRTState) composite.getSubvertex("nested");
		assumeThat(nested, notNullValue());

		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();

		UMLRTState redef = sm.getRedefinitionOf(composite);
		assumeThat(redef, notNullValue());
		assertThat(redef, not(composite));
		assertThat(redef, redefines(composite));

		// In the context of the containing composite state
		UMLRTState redefNested = redef.getRedefinitionOf(nested);
		assumeThat(redefNested, notNullValue());
		assertThat(redefNested, not(nested));
		assertThat(redefNested, redefines(nested));

		// In the context of the containing state machine
		redefNested = sm.getRedefinitionOf(nested);
		assertThat(redefNested, notNullValue());
		assertThat(redefNested, not(nested));
		assertThat(redefNested, redefines(nested));
	}

	@Test
	public void getRedefinitionOfLocalStateInRedefiningStateMachine() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();

		UMLRTState composite = sm.createState("NewComposite");
		assertThat(sm.getRedefinitionOf(composite), is(composite));

		UMLRTState nested = composite.createSubstate("nested");
		assumeThat(nested, notNullValue());
		assertThat(composite.getRedefinitionOf(nested), is(nested));
		assertThat(sm.getRedefinitionOf(nested), is(nested));
	}

	@Test
	public void allRedefinitions() {
		UMLRTStateMachine rootSM = getRootCapsuleBehavior();
		UMLRTStateMachine subSM = getSubcapsuleBehavior();
		UMLRTStateMachine subsubSM = getSubsubcapsuleBehavior();

		List<UMLRTStateMachine> expected = Arrays.asList(rootSM, subSM, subsubSM);
		List<? extends UMLRTStateMachine> actual = rootSM.allRedefinitions().collect(Collectors.toList());
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
}
