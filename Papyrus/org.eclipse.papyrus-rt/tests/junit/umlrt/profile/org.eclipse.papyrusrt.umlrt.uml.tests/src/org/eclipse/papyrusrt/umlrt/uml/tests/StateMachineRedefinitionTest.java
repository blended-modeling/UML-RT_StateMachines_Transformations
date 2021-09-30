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
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.isRedefined;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.named;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.redefines;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the basic redefinition support in {@link StateMachine}s.
 */
@TestModel("inheritance/statemachines.uml")
@NoFacade
@Category(StateMachineTests.class)
public class StateMachineRedefinitionTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public StateMachineRedefinitionTest() {
		super();
	}

	@Test
	public void regionRedefined() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parent = parentSM.getRegion("Region");
		Region extending = extendingSM.getRegion("Region");

		assumeThat(parent, notNullValue());
		assumeThat(extending, notNullValue());
		assumeThat(extending, redefines(parent));
		assumeThat(extending.getExtendedRegion(), is(parent));
		assumeThat(extending, isInherited());

		// Add a vertex to the inherited region to redefine it
		extending.createSubvertex("NewState", UMLPackage.Literals.STATE);

		assertThat("Region not redefined", extending, isRedefined());
		assertThat("Region not attached to model", ((InternalEObject) extending).eInternalContainer(),
				is(extendingSM));
		assertThat("Other vertices were reified", extending.getSubvertex("State1"), isInherited());
	}

	@Test
	public void stateRedefined() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		State parent = (State) parentReg.getSubvertex("State1");
		assumeThat(parent, notNullValue());

		assumeThat(extendingReg, notNullValue());

		State redefining = (State) extendingReg.getSubvertex("State1");
		assumeThat(redefining, notNullValue());
		assumeThat(redefining, redefines(parent));
		assumeThat(redefining.getRedefinedState(), is(parent));
		assumeThat(redefining, isInherited());

		// Add a connection point to the inherited state to redefine it
		redefining.createConnectionPoint("entry");

		assertThat("State not redefined", redefining, isRedefined());
		assertThat("State not attached to model", ((InternalEObject) redefining).eInternalContainer(),
				is(extendingReg));
		assertThat("Container not redefined", extendingReg, isRedefined());
	}

	@Test
	public void pseudostateNotRedefinable() {
		// Pseudostates are not redefinable: changing the incoming/outgoing transitions
		// isn't a change to the vertex, itself
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Pseudostate parent = getPseudostate(parentReg, PseudostateKind.INITIAL_LITERAL);
		assumeThat(parent, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Pseudostate redefining = getPseudostate(extendingReg, PseudostateKind.INITIAL_LITERAL);
		assumeThat(redefining, notNullValue());
		assumeThat(redefining, redefines(parent));
		assumeThat(redefining, isInherited());

		redefining.setName("redefine me");
		assertThat("Pseudostate was redefined", redefining, isInherited());
		assertThat(((InternalEObject) redefining).eInternalContainer(), not(extendingReg));
	}

	@Test
	public void transitionRedefined() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parent = parentReg.getTransition("out");
		assumeThat(parent, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefining = extendingReg.getTransition("out");
		assumeThat(redefining, notNullValue());
		assumeThat(redefining, redefines(parent));
		assumeThat(redefining, isInherited());

		State state1 = (State) extendingReg.getSubvertex("State1");
		assumeThat(state1, notNullValue());

		redefining.setTarget(state1);

		assertThat("Transition not redefined", redefining, isRedefined());
		assertThat("Transition not attached to model", ((InternalEObject) redefining).eInternalContainer(),
				is(extendingReg));
		assertThat("Container not redefined", extendingReg, isRedefined());

		assertThat("Target state does not have transition incoming", state1.getIncomings(), hasItem(redefining));
		assertThat("Root state has transition incoming", state1.getRedefinedState().getIncomings(), not(hasItem(redefining)));
		assertThat("Target state was redefined", state1, isInherited());
	}


	@Test
	public void compositeStateRedefined() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(extendingReg, notNullValue());

		State parentComposite = (State) parentReg.getSubvertex("composite");
		parentReg = parentComposite.getRegions().get(0);
		State parentNested = (State) parentReg.getSubvertex("nested");

		State redefComposite = (State) extendingReg.getSubvertex("composite");
		assumeThat(redefComposite, notNullValue());
		assumeThat(redefComposite.getOwnedMembers(), hasItem(named("Region1")));
		extendingReg = redefComposite.getRegion("Region1");
		assumeThat(extendingReg, notNullValue());
		State redefNested = (State) extendingReg.getSubvertex("nested");
		assumeThat(redefNested, notNullValue());
		assumeThat(redefComposite, isInherited());
		assumeThat(redefComposite, redefines(parentComposite));
		assumeThat(extendingReg, isInherited());
		assumeThat(extendingReg, redefines(parentReg));
		assumeThat(redefNested, isInherited());
		assumeThat(redefNested, redefines(parentNested));

		// Redefine the state by creating a do activity
		redefNested.createDoActivity("doIt", UMLPackage.Literals.OPAQUE_BEHAVIOR);

		assertThat("Nested state not redefined", redefNested, isRedefined());
		assertThat("Nested state not attached to model", ((InternalEObject) redefNested).eInternalContainer(),
				is(extendingReg));
		assertThat("Container not redefined", extendingReg, isRedefined());

		assertThat("Composite state not redefined", redefComposite, isRedefined());
	}

	@Test
	public void triggerNotRedefinable() {
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
		assumeThat("Trigger not found", redefining, notNullValue());

		assumeThat(redefining, isInherited());

		redefining.setEvent(null);
		assertThat("Trigger was redefined", redefining, isInherited());
		assertThat(((InternalEObject) redefining).eInternalContainer(), not(redefTrans));
	}

	@Test
	public void redefineStateMachine() {
		StateMachine leafSM = getSubsubcapsuleBehavior();

		assumeThat(leafSM, isInherited());
		Region region = leafSM.getRegion("Region");
		assumeThat(region, notNullValue());

		ChangeDescription change = fixture.record(() -> region.createSubvertex("NewState", UMLPackage.Literals.STATE));
		assertThat(leafSM, isRedefined());

		fixture.undo(change);
		assertThat(leafSM, isInherited());
		assertThat("Lost the classifier-behaviorness in undo", getSubsubcapsuleBehavior(), is(leafSM));

		fixture.undo(change);
		assertThat(leafSM, isRedefined());
		assertThat("Lost the classifier-behaviorness in redo", getSubsubcapsuleBehavior(), is(leafSM));
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

	StateMachine getSubsubcapsuleBehavior() {
		return fixture.getElement("Subsubcapsule::StateMachine");
	}

	Pseudostate getPseudostate(Region region, PseudostateKind kind) {
		return region.getOwnedMembers().stream()
				.filter(Pseudostate.class::isInstance).map(Pseudostate.class::cast)
				.filter(ps -> ps.getKind() == kind)
				.findAny().orElse(null);
	}
}
