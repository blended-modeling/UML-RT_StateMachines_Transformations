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

import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.redefines;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.isInherited;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.stereotypedAs;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.List;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
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
import org.eclipse.uml2.uml.PseudostateKind;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the composite state façades in {@link UMLRTState}.
 */
@TestModel("inheritance/statemachines.uml")
@Category({ StateMachineTests.class, FacadeTests.class })
public class CompositeStateFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public CompositeStateFacadeTest() {
		super();
	}

	@Test
	public void compositeState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());
		UMLRTState composite = (UMLRTState) sm.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		assertThat("State is not composite", composite.isComposite(), is(true));
	}

	@Test
	public void compositeStateNotSimple() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());
		UMLRTState composite = (UMLRTState) sm.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		assertThat("Composite state is simple", composite.isSimple(), is(false));
	}

	@Test
	public void inheritedCompositeStateNotSimple() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());
		UMLRTState composite = (UMLRTState) sm.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		assertThat("Composite state is simple", composite.isSimple(), is(false));
	}

	@Test
	public void setComposite() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());
		UMLRTState composite = (UMLRTState) sm.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		assumeThat("State is not composite", composite.isComposite(), is(true));

		composite.setComposite(false);
		assertThat("State is still composite", composite.isComposite(), is(false));
		assertThat(composite.toUML().getRegions(), not(hasItem(anything())));

		UMLRTState state1 = (UMLRTState) sm.getVertex("State1");
		assumeThat(state1, notNullValue());
		state1.setComposite(true);
		assertThat("State is not composite", state1.isComposite(), is(true));
		assertThat(state1.toUML().getRegions(), hasItem(stereotypedAs("UMLRTStateMachines::RTRegion")));
	}

	@Test
	public void vertices() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTVertex> vertices = composite.getSubvertices();
		assertThat(vertices.size(), is(1)); // The connection points are not subvertices

		UMLRTVertex vertex = composite.getSubvertex("nested");
		assertThat(vertex, notNullValue());
		assertThat(vertex, instanceOf(UMLRTState.class));
		assertThat(vertex.getState(), is(composite));
	}

	@Test
	public void connectionPoints() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTConnectionPoint> connPts = composite.getConnectionPoints();
		assertThat(connPts.size(), is(2));

		UMLRTConnectionPoint connpt = composite.getConnectionPoint("exit");
		assertThat(connpt, notNullValue());
		assertThat(connpt.getKind(), is(UMLRTConnectionPointKind.EXIT));
		assertThat(connpt.getState(), is(composite));
	}

	@Test
	public void entryPoints() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTConnectionPoint> entries = composite.getEntryPoints();
		assertThat(entries.size(), is(1));

		entries.forEach(entry -> assertThat(entry.getKind(), is(UMLRTConnectionPointKind.ENTRY)));
	}

	@Test
	public void exitPoints() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTConnectionPoint> exits = composite.getExitPoints();
		assertThat(exits.size(), is(1));

		exits.forEach(exit -> assertThat(exit.getKind(), is(UMLRTConnectionPointKind.EXIT)));
	}

	@Test
	public void createState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTVertex> vertices = composite.getSubvertices();
		assumeThat(vertices.size(), is(1));

		UMLRTState newState = composite.createSubstate("NewState");
		assertThat(newState, notNullValue());
		assertThat(newState.toUML(), stereotypedAs("UMLRTStateMachines::RTState"));
		assertThat(newState.getName(), is("NewState"));

		assertThat(vertices.size(), is(2));
		assertThat(vertices, hasItem(newState));
	}

	@Test
	public void createPseudostate() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTVertex> vertices = composite.getSubvertices();
		assumeThat(vertices.size(), is(1));

		UMLRTPseudostate newPseudostate = composite.createSubpseudostate(UMLRTPseudostateKind.HISTORY);
		assertThat(newPseudostate, notNullValue());
		assertThat(newPseudostate.toUML(), stereotypedAs("UMLRTStateMachines::RTPseudostate"));
		assertThat(newPseudostate.toUML().getKind(), is(PseudostateKind.DEEP_HISTORY_LITERAL));

		assertThat(vertices.size(), is(2));
		assertThat(vertices, hasItem(newPseudostate));
	}

	@Test
	public void createConnectionPoint() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTConnectionPoint> connPts = composite.getConnectionPoints();
		assumeThat(connPts.size(), is(2));
		List<UMLRTConnectionPoint> exits = composite.getExitPoints();
		assumeThat(exits.size(), is(1));

		UMLRTConnectionPoint newExit = composite.createConnectionPoint(UMLRTConnectionPointKind.EXIT);
		assertThat(newExit, notNullValue());
		assertThat(newExit.toUML(), stereotypedAs("UMLRTStateMachines::RTPseudostate"));
		assertThat(newExit.toUML().getKind(), is(PseudostateKind.EXIT_POINT_LITERAL));

		assertThat(connPts.size(), is(3));
		assertThat(connPts, hasItem(newExit));
		assertThat(exits.size(), is(2));
		assertThat(exits, hasItem(newExit));
	}

	@Test
	public void transitions() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTTransition> transitions = composite.getSubtransitions();
		assertThat(transitions.size(), is(2));

		UMLRTTransition transition = composite.getSubtransition("nestedOut");
		assertThat(transition, notNullValue());
		assertThat(transition.getState(), is(composite));
	}

	@Test
	public void redefiningCompositeState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		assertThat(composite.getRedefinedState(), notNullValue());
		assertThat(composite.getRedefinedState().containingStateMachine(), is(m.getRedefinedStateMachine()));
		assertThat(composite.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));

		composite.setName("redefined");
		assertThat(composite.getInheritanceKind(), is(UMLRTInheritanceKind.REDEFINED));
	}

	@Test
	public void inheritedVertices() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTVertex> vertices = composite.getSubvertices();
		assertThat(vertices.size(), is(1));

		UMLRTVertex vertex = composite.getSubvertex("nested");
		assertThat(vertex, notNullValue());
		assertThat(vertex, instanceOf(UMLRTState.class));
		assertThat(vertex.getState(), is(composite));

		vertices.forEach(v -> assertThat(v.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED)));
	}

	@Test
	public void inheritedConnectionPoints() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTConnectionPoint> connPts = composite.getConnectionPoints();
		assertThat(connPts.size(), is(2));

		UMLRTConnectionPoint connpt = composite.getConnectionPoint("exit");
		assertThat(connpt, notNullValue());
		assertThat(connpt.getKind(), is(UMLRTConnectionPointKind.EXIT));
		assertThat(connpt.getState(), is(composite));
		assertThat(connpt.toUML(), isInherited());
	}

	@Test
	public void inheritedEntryPoints() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTConnectionPoint> entries = composite.getEntryPoints();
		assertThat(entries.size(), is(1));

		entries.forEach(entry -> assertThat(entry.getKind(), is(UMLRTConnectionPointKind.ENTRY)));
		entries.forEach(entry -> assertThat(entry.toUML(), isInherited()));
	}

	@Test
	public void inheritedExitPoints() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTConnectionPoint> exits = composite.getExitPoints();
		assertThat(exits.size(), is(1));

		exits.forEach(exit -> assertThat(exit.getKind(), is(UMLRTConnectionPointKind.EXIT)));
		exits.forEach(exit -> assertThat(exit.toUML(), isInherited()));
	}

	@Test
	public void inheritCreatedState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTVertex> vertices = composite.getSubvertices();
		assumeThat(vertices.size(), is(1));

		composite.getRedefinedState().createSubstate("NewState");

		UMLRTState newState = (UMLRTState) composite.getSubvertex("NewState");
		assertThat(newState, notNullValue());
		assertThat(newState.toUML(), stereotypedAs("UMLRTStateMachines::RTState"));
		assertThat(newState.getName(), is("NewState"));

		assertThat(vertices.size(), is(2));
		assertThat(vertices, hasItem(newState));
		assertThat(newState.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
	}

	@Test
	public void inheritCreatedPseudostate() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTVertex> vertices = composite.getSubvertices();
		assumeThat(vertices.size(), is(1));

		UMLRTPseudostate ps = composite.getRedefinedState().createSubpseudostate(UMLRTPseudostateKind.HISTORY);

		UMLRTPseudostate newPseudostate = composite.getRedefinitionOf(ps);
		assertThat(newPseudostate, notNullValue());
		assertThat(newPseudostate.toUML(), stereotypedAs("UMLRTStateMachines::RTPseudostate"));
		assertThat(newPseudostate.toUML().getKind(), is(PseudostateKind.DEEP_HISTORY_LITERAL));

		assertThat(vertices.size(), is(2));
		assertThat(vertices, hasItem(newPseudostate));
		assertThat(newPseudostate.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
	}

	@Test
	public void inheritCreatedConnectionPoint() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTConnectionPoint> connPts = composite.getConnectionPoints();
		assumeThat(connPts.size(), is(2));
		List<UMLRTConnectionPoint> exits = composite.getExitPoints();
		assumeThat(exits.size(), is(1));

		UMLRTConnectionPoint newExit = composite.getRedefinedState().createConnectionPoint(UMLRTConnectionPointKind.EXIT);
		assertThat(newExit, notNullValue());
		assertThat(newExit.toUML(), stereotypedAs("UMLRTStateMachines::RTPseudostate"));
		assertThat(newExit.toUML().getKind(), is(PseudostateKind.EXIT_POINT_LITERAL));

		assertThat(connPts.size(), is(3));
		assertThat(connPts, hasItem(redefines(newExit)));
		assertThat(exits.size(), is(2));
		assertThat(exits, hasItem(redefines(newExit)));
	}

	@Test
	public void inheritedTransitions() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTTransition> transitions = composite.getSubtransitions();
		assertThat(transitions.size(), is(2));

		UMLRTTransition transition = composite.getSubtransition("nestedOut");
		assertThat(transition, notNullValue());
		assertThat(transition.getState(), is(composite));
		assertThat(transition.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
	}

	@Test
	public void excludeTransition() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTTransition> transitions = composite.getSubtransitions();
		UMLRTTransition transition = composite.getSubtransition("nestedOut");
		assumeThat(transition, notNullValue());

		transition.exclude();

		assertThat(transitions, not(hasItem(transition)));
		assertThat(composite.getSubtransition(transition.getName()), nullValue());

		assertThat(composite.getExcludedElement(transition.getName()), is(transition));
	}

	@Test
	public void excludeState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine m = capsule.getStateMachine();
		assumeThat("No state machine façade", m, notNullValue());
		UMLRTState composite = (UMLRTState) m.getVertex("composite");
		assumeThat("Composite state not found", composite, notNullValue());

		List<UMLRTVertex> vertices = composite.getSubvertices();
		UMLRTState nested = (UMLRTState) composite.getSubvertex("nested");
		assumeThat(nested, notNullValue());

		nested.exclude();

		assertThat(vertices, not(hasItem(nested)));
		assertThat(composite.getSubvertex(nested.getName()), nullValue());

		assertThat(composite.getExcludedElement(nested.getName()), is(nested));
	}
}
