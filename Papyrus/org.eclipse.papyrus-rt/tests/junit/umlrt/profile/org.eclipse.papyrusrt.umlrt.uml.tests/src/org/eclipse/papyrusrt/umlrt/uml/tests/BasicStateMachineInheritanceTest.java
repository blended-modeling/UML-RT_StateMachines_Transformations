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
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.named;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.redefines;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the basic inheritance support in {@link StateMachine}s.
 */
@TestModel("inheritance/statemachines.uml")
@NoFacade
@Category(StateMachineTests.class)
public class BasicStateMachineInheritanceTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public BasicStateMachineInheritanceTest() {
		super();
	}

	@Test
	public void regionInherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parent = parentSM.getRegion("Region");
		Region extending = extendingSM.getRegion("Region");

		assumeThat(parent, notNullValue());
		assertThat(extending, notNullValue());
		assertThat(extending, redefines(parent));
		assertThat(extending.getExtendedRegion(), is(parent));

		assertThat(extending, isInherited());
	}

	@Test
	public void stateInherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		State parent = (State) parentReg.getSubvertex("State1");
		assumeThat(parent, notNullValue());

		assumeThat(extendingReg, notNullValue());

		State redefining = (State) extendingReg.getSubvertex("State1");
		assertThat(redefining, notNullValue());

		assertThat(redefining, redefines(parent));
		assertThat(redefining.getRedefinedState(), is(parent));

		assertThat(redefining, isInherited());
	}

	@Test
	public void pseudostateInherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Pseudostate parent = getPseudostate(parentReg, PseudostateKind.INITIAL_LITERAL);
		assumeThat(parent, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Pseudostate redefining = getPseudostate(extendingReg, PseudostateKind.INITIAL_LITERAL);
		assertThat(redefining, notNullValue());

		assertThat(redefining, redefines(parent));

		assertThat(redefining, isInherited());
		assertThat(redefining.getStereotypeApplications(), hasItem(instanceOf(RTPseudostate.class)));
		redefining.getStereotypeApplications().forEach(a -> assertThat(a.eResource(), not(fixture.getResource())));
	}

	@Test
	public void transitionInherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parent = parentReg.getTransition("Initial");
		assumeThat(parent, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefining = extendingReg.getTransition("Initial");
		assertThat(redefining, notNullValue());

		assertThat(redefining, redefines(parent));

		assertThat(redefining, isInherited());
	}

	@Test
	public void inheritedTransitionSource() {
		// This one is a pseudostate in the region
		inheritedTransitionEnd(Transition::getSource, UMLPackage.Literals.TRANSITION__SOURCE);
	}

	@Test
	public void inheritedTransitionTarget() {
		// This one is a state in the region
		inheritedTransitionEnd(Transition::getTarget, UMLPackage.Literals.TRANSITION__TARGET);
	}

	private void inheritedTransitionEnd(Function<Transition, Vertex> getEnd, EReference endReference) {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parent = parentReg.getTransition("Initial");
		assumeThat(parent, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefining = extendingReg.getTransition("Initial");
		assumeThat(redefining, notNullValue());
		assumeThat(redefining, isInherited());

		Vertex vertex = getEnd.apply(redefining);
		assertThat(vertex, notNullValue());
		assertThat(endReference.getName() + " not resolved", vertex, isInherited());
		assertThat(redefining.eIsSet(endReference), is(false));


		// Set the reference and see what would be serialized
		redefining.eSet(endReference, vertex);
		Vertex superVertex = ((InternalUMLRTElement) vertex).rtGetRedefinedElement();
		assertThat(endReference.getName() + " persists virtual element",
				redefining.eGet(endReference, false), // The 'basic get' accesses the persisted reference
				is(superVertex));
	}

	@Test
	public void compositeState() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(extendingReg, notNullValue());

		State parentComposite = (State) parentReg.getSubvertex("composite");
		parentReg = parentComposite.getRegions().get(0);
		State parentNested = (State) parentReg.getSubvertex("nested");

		State redefComposite = (State) extendingReg.getSubvertex("composite");
		assertThat(redefComposite, notNullValue());
		assertThat(redefComposite.getOwnedMembers(), hasItem(named("Region1")));
		extendingReg = redefComposite.getRegion("Region1");
		assertThat(extendingReg, notNullValue());
		State redefNested = (State) extendingReg.getSubvertex("nested");
		assertThat(redefNested, notNullValue());

		assertThat(redefComposite, isInherited());
		assertThat(redefComposite, redefines(parentComposite));

		assertThat(extendingReg, isInherited());
		assertThat(extendingReg, redefines(parentReg));

		assertThat(redefNested, isInherited());
		assertThat(redefNested, redefines(parentNested));
	}

	@Test
	public void stateConnectionPoints() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");

		State composite = (State) parentReg.getSubvertex("composite");
		Pseudostate entry = composite.getConnectionPoint("entry");
		Pseudostate exit = composite.getConnectionPoint("exit");

		Pseudostate redefEntry = getRedefinition(entry, extendingSM);
		assertThat(redefEntry, notNullValue());
		assertThat(redefEntry, redefines(entry));

		Pseudostate redefExit = getRedefinition(exit, extendingSM);
		assertThat(redefExit, notNullValue());
		assertThat(redefExit, redefines(exit));
	}

	@Test
	public void stateConnectionPointTransitions() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");

		State composite = (State) parentReg.getSubvertex("composite");
		Pseudostate entry = composite.getConnectionPoint("entry");
		Pseudostate exit = composite.getConnectionPoint("exit");
		Transition in = entry.getIncoming("in");
		Transition nestedIn = entry.getOutgoing("nestedIn");
		Transition nestedOut = exit.getIncoming("nestedOut");
		Transition out = exit.getOutgoing("out");

		Pseudostate redefEntry = getRedefinition(entry, extendingSM);
		assumeThat(redefEntry, notNullValue());
		Pseudostate redefExit = getRedefinition(exit, extendingSM);
		assumeThat(redefExit, notNullValue());
		Transition redefIn = getRedefinition(in, extendingSM);
		assumeThat(redefIn, notNullValue());
		Transition redefNestedIn = getRedefinition(nestedIn, extendingSM);
		assumeThat(redefNestedIn, notNullValue());
		Transition redefNestedOut = getRedefinition(nestedOut, extendingSM);
		assumeThat(redefNestedOut, notNullValue());
		Transition redefOut = getRedefinition(out, extendingSM);
		assumeThat(redefOut, notNullValue());

		assertThat(redefIn.getTarget(), is(redefEntry));
		assertThat(redefNestedIn.getSource(), is(redefEntry));
		assertThat(redefNestedOut.getTarget(), is(redefExit));
		assertThat(redefOut.getSource(), is(redefExit));

		// Set the source and target to be the redefinitions
		redefIn.setTarget(redefEntry);
		redefNestedIn.setSource(redefEntry);
		redefNestedOut.setTarget(redefExit);
		redefOut.setSource(redefExit);

		// Verify that the root definitions are what we persist
		assertThat(redefIn.eGet(UMLPackage.Literals.TRANSITION__TARGET, false), is(entry));
		assertThat(redefNestedIn.eGet(UMLPackage.Literals.TRANSITION__SOURCE, false), is(entry));
		assertThat(redefNestedOut.eGet(UMLPackage.Literals.TRANSITION__TARGET, false), is(exit));
		assertThat(redefOut.eGet(UMLPackage.Literals.TRANSITION__SOURCE, false), is(exit));
	}

	@Test
	public void pseudostateIncomings() {
		Pseudostate entry = fixture.getElement("RootCapsule::StateMachine::Region::composite::entry");
		Transition transition = fixture.getElement("RootCapsule::StateMachine::Region::in");
		vertexTransitions(entry, transition, Vertex::getIncomings);
	}

	@Test
	public void stateIncomings() {
		State state1 = fixture.getElement("RootCapsule::StateMachine::Region::State1");
		Transition transition = fixture.getElement("RootCapsule::StateMachine::Region::Initial");
		vertexTransitions(state1, transition, Vertex::getIncomings);
	}

	@Test
	public void pseudostateOutgoings() {
		Pseudostate exit = fixture.getElement("RootCapsule::StateMachine::Region::composite::exit");
		Transition transition = fixture.getElement("RootCapsule::StateMachine::Region::out");
		vertexTransitions(exit, transition, Vertex::getOutgoings);
	}

	@Test
	public void stateOutgoings() {
		State state1 = fixture.getElement("RootCapsule::StateMachine::Region::State1");
		Transition transition = fixture.getElement("RootCapsule::StateMachine::Region::in");
		vertexTransitions(state1, transition, Vertex::getOutgoings);
	}

	private void vertexTransitions(Vertex vertex, Transition transition, Function<Vertex, List<Transition>> transitionsFunction) {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Vertex redefVertex = getRedefinition(vertex, extendingSM);
		assumeThat("Inheritance of vertex not found", redefVertex, notNullValue());

		List<Transition> transitions = transitionsFunction.apply(vertex);
		assertThat("Transition not found on vertex", transitions, hasItem(transition));

		Transition redefTransition = getRedefinition(transition, extendingSM);
		assumeThat("Inheritance of transition not found", redefTransition, notNullValue());

		List<Transition> redefTransitions = transitionsFunction.apply(redefVertex);
		assertThat("Inherited transition not found on inherited vertex", redefTransitions, hasItem(redefTransition));
		assertThat("Inherited vertex has non-inherited transitions", redefTransitions, everyItem(isInherited()));

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Pseudostate parent = getPseudostate(parentReg, PseudostateKind.INITIAL_LITERAL);
		assumeThat(parent, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Pseudostate redefining = getPseudostate(extendingReg, PseudostateKind.INITIAL_LITERAL);
		assertThat(redefining, notNullValue());

		assertThat(redefining, redefines(parent));

		assertThat(redefining, isInherited());
	}

	@Test
	public void triggerInherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parentTrans = parentReg.getTransition("Initial");
		assumeThat(parentTrans, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefTrans = extendingReg.getTransition("Initial");
		assumeThat(redefTrans, notNullValue());
		assumeThat(redefTrans, isInherited());

		Trigger parent = parentTrans.getTrigger("greet");
		assumeThat("Trigger not found", parent, notNullValue());

		Trigger redefining = redefTrans.getTrigger("greet");
		assertThat("Trigger not found", redefining, notNullValue());

		assertThat(redefining, isInherited());
		assertThat(redefining, redefines(parent));

		assertThat(redefining.getEvent(), is(parent.getEvent()));
		assertThat(redefining.eIsSet(UMLPackage.Literals.TRIGGER__EVENT), is(false));

		Port parentPort = parent.getPort("protocol1", null);
		assumeThat(parentPort, notNullValue());

		Port redefPort = redefining.getPort("protocol1", null);
		assertThat(redefPort, notNullValue());
		assertThat(redefPort, not(parentPort));
		assertThat(redefPort, redefines(parentPort));
		assertThat(redefining.eIsSet(UMLPackage.Literals.TRIGGER__PORT), is(false));
	}

	@Test
	public void newRegionInherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region region = parentSM.getRegion("Region");
		Region extRegion = extendingSM.getRegion("Region");
		assumeThat(extRegion, notNullValue());

		// Make the State1 into a composite by adding a region
		State state1 = (State) region.getSubvertex("State1");

		State redefState1 = (State) extRegion.getSubvertex("State1");
		assumeThat("Redefining state not found", redefState1, notNullValue());

		Region newRegion = state1.createRegion("CompositeRegion");
		newRegion.applyStereotype(newRegion.getApplicableStereotype("UMLRTStateMachines::RTRegion"));

		extRegion = redefState1.getRegion("CompositeRegion");
		assertThat("New extended region not found", extRegion, notNullValue());
		assertThat("New extended region not inherited", extRegion, isInherited());
		assertThat("New extended region not a valid redefinition", extRegion.getExtendedRegion(), is(newRegion));
	}

	@Test
	public void newStateInherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region region = parentSM.getRegion("Region");
		Region extRegion = extendingSM.getRegion("Region");
		assumeThat(extRegion, notNullValue());

		State newState = (State) region.createSubvertex("NewState", UMLPackage.Literals.STATE);
		newState.applyStereotype(newState.getApplicableStereotype("UMLRTStateMachines::RTState"));

		State redefState = (State) extRegion.getSubvertex("NewState");
		assertThat("New redefining state not found", redefState, notNullValue());
		assertThat("New redefining state not inherited", redefState, isInherited());
		assertThat("New redefining state not a valid redefinition", redefState.getRedefinedState(), is(newState));
	}

	@Test
	public void newPseudostateInherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region region = parentSM.getRegion("Region");
		Region extRegion = extendingSM.getRegion("Region");
		assumeThat(extRegion, notNullValue());

		Pseudostate newHistory = (Pseudostate) region.createSubvertex("history", UMLPackage.Literals.PSEUDOSTATE);
		newHistory.setKind(PseudostateKind.DEEP_HISTORY_LITERAL);
		newHistory.applyStereotype(newHistory.getApplicableStereotype("UMLRTStateMachines::RTPseudostate"));

		Pseudostate redefState = (Pseudostate) extRegion.getSubvertex("history");
		assertThat("New redefining pseudostate not found", redefState, notNullValue());
		assertThat("New redefining pseudostate not inherited", redefState, isInherited());
		assertThat("New redefining pseudostate not a valid redefinition", redefState, redefines(newHistory));
	}

	@Test
	public void newTransitionInherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extRegion = extendingSM.getRegion("Region");
		assumeThat(extRegion, notNullValue());

		Pseudostate choice = (Pseudostate) parentReg.getSubvertex("Choice1");
		State state1 = (State) parentReg.getSubvertex("State1");

		Transition newTransition = parentReg.createTransition("NewTransition");
		// Do this the weird way
		choice.getOutgoings().add(newTransition);
		state1.getIncomings().add(newTransition);

		Transition redefTransition = extRegion.getTransition("NewTransition");
		assertThat("New redefining transition not found", redefTransition, notNullValue());
		assertThat("New redefining transition not inherited", redefTransition, isInherited());
		assertThat("New redefining transition not a valid redefinition",
				redefTransition.getRedefinedTransition(), is(newTransition));

		assertThat(redefTransition.getSource(), instanceOf(Pseudostate.class));
		assertThat((Pseudostate) redefTransition.getSource(), redefines(choice));
		assertThat(redefTransition.getTarget(), instanceOf(State.class));
		assertThat((State) redefTransition.getTarget(), redefines(state1));
		assertThat(redefTransition.eGet(UMLPackage.Literals.TRANSITION__SOURCE, false), nullValue());
		assertThat(redefTransition.eGet(UMLPackage.Literals.TRANSITION__TARGET, false), nullValue());
	}

	@Test
	public void newTriggerInherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parentTrans = parentReg.getTransition("Initial");
		assumeThat(parentTrans, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefTrans = extendingReg.getTransition("Initial");
		assumeThat(redefTrans, notNullValue());
		assumeThat(redefTrans, isInherited());

		CallEvent respondRecv = fixture.getModel().getNestedPackage("Protocol1").getPackagedElements().stream()
				.filter(CallEvent.class::isInstance).map(CallEvent.class::cast)
				.filter(e -> "respond".equals(e.getOperation().getName()))
				.findAny().orElse(null);
		Trigger newTrigger = parentTrans.createTrigger("respond");
		newTrigger.setEvent(respondRecv);
		Port parentPort = fixture.getElement("RootCapsule", Class.class).getOwnedPort("protocol1", null);
		newTrigger.getPorts().add(parentPort);

		Trigger redefining = redefTrans.getTrigger("respond");
		assertThat("Trigger not found", redefining, notNullValue());

		assertThat(redefining, isInherited());
		assertThat(redefining, redefines(newTrigger));

		assertThat(redefining.getEvent(), is(respondRecv));
		assertThat(redefining.eIsSet(UMLPackage.Literals.TRIGGER__EVENT), is(false));

		Port redefPort = redefining.getPort("protocol1", null);
		assertThat(redefPort, notNullValue());
		assertThat(redefPort, not(parentPort));
		assertThat(redefPort, redefines(parentPort));
		assertThat(redefining.eIsSet(UMLPackage.Literals.TRIGGER__PORT), is(false));
	}

	//
	// Test framework
	//

	StateMachine getRootCapsuleBehavior() {
		return fixture.getElement("RootCapsule::StateMachine");
	}

	StateMachine getSubcapsuleBehavior() {
		return fixture.getElement("Subcapsule::StateMachine");
	}

	Pseudostate getPseudostate(Region region, PseudostateKind kind) {
		return region.getOwnedMembers().stream()
				.filter(Pseudostate.class::isInstance).map(Pseudostate.class::cast)
				.filter(ps -> ps.getKind() == kind)
				.findAny().orElse(null);
	}

	@SuppressWarnings("unchecked")
	<T extends Element> T getRedefinition(T element, StateMachine inMachine) {
		assumeThat("Attempt to get redefinition of null element", element, notNullValue());

		T result = null;

		out: for (Element next : inMachine.allOwnedElements()) {
			if (next instanceof InternalUMLRTElement) {
				for (InternalUMLRTElement rt = (InternalUMLRTElement) next; rt != null; rt = rt.rtGetRedefinedElement()) {
					if (rt.rtGetRedefinedElement() == element) {
						result = (T) rt;
						break out;
					}
				}
			}
		}

		return result;
	}
}
