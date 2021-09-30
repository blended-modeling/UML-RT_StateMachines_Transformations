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

import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.receives;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.isInherited;
import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.getUMLRTContents;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Specific regression test cases for redefinition scenarios in the
 * {@link StateMachine}s, using the façade API.
 */
@TestModel("inheritance/statemachines.uml")
@Category({ StateMachineTests.class, FacadeTests.class })
public class StateMachineFacadeRedefinitionTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public StateMachineFacadeRedefinitionTest() {
		super();
	}

	/**
	 * @see <a href="http://eclip.se/513068">bug 513068</a>
	 */
	@Test
	public void addTriggerToInheritedTransition() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());
		assumeThat(transition.toUML(), isInherited());

		UMLRTTrigger trigger = transition.createTrigger(null, capsule.getPort("protocol2"));
		assertThat(trigger, notNullValue());
		assertThat(trigger, FacadeMatchers.isRootDefinition());

		assertThat(transition.getTriggers().size(), is(2));
		assertThat(transition.getTriggers(), hasItem(trigger));

		// Re-inherit (bug 513068)
		assertThat("Transition not re-inheritable", transition.reinherit(), is(true));

		assertThat(transition.getTriggers(), hasItem(receives(FacadeMatchers.named("greet"))));
		assertThat(transition.getTriggers(), not(hasItem(trigger)));
		assertThat(transition.getTriggers().size(), is(1));
	}

	/**
	 * @see <a href="http://eclip.se/513068">bug 513068</a>
	 */
	@Test
	public void addTriggerWithGuardToInheritedTransition() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());
		assumeThat(transition.toUML(), isInherited());

		UMLRTTrigger trigger = transition.createTrigger(null, capsule.getPort("protocol2"));
		assumeThat(trigger, notNullValue());
		assumeThat(trigger, FacadeMatchers.isRootDefinition());

		assumeThat(transition.getTriggers().size(), is(2));
		assumeThat(transition.getTriggers(), hasItem(trigger));

		UMLRTGuard guard = trigger.createGuard("C++", "this.canProceed()");
		assertThat(transition.toUML().getOwnedRules(), hasItem(guard.toUML()));

		// Re-inherit (bug 513068)
		assertThat("Transition not re-inheritable", transition.reinherit(), is(true));

		assertThat(transition.getTriggers(), hasItem(receives(FacadeMatchers.named("greet"))));
		assertThat(transition.getTriggers(), not(hasItem(trigger)));
		assertThat(transition.getTriggers().size(), is(1));

		assertThat(transition.toUML().getOwnedRules(), not(hasItem(guard.toUML())));

		// We do inherit a trigger guard, still
		UMLRTTrigger greet = transition.getTriggers().get(0);
		assertThat(greet.getGuard(), notNullValue());
		assertThat(getUMLRTContents(transition.toUML(), UMLPackage.Literals.NAMESPACE__OWNED_RULE),
				hasItem(greet.getGuard().toUML()));
	}

	/**
	 * @see <a href="http://eclip.se/513068">bug 513068</a>
	 */
	@Test
	public void addTriggerWithGuardToInheritedTransitionWithGuard() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("in");
		assumeThat(transition, notNullValue());
		assumeThat(transition.toUML(), isInherited());

		UMLRTTrigger trigger = transition.createTrigger(null, capsule.getPort("protocol2"));
		assumeThat(trigger, notNullValue());
		assumeThat(trigger, FacadeMatchers.isRootDefinition());

		assumeThat(transition.getTriggers().size(), is(1));
		assumeThat(transition.getTriggers(), hasItem(trigger));

		UMLRTGuard guard = trigger.createGuard("C++", "this.canProceed()");
		assumeThat(transition.toUML().getOwnedRules(), hasItem(guard.toUML()));

		// Re-inherit (bug 513068)
		assertThat("Transition not re-inheritable", transition.reinherit(), is(true));

		assertThat(transition.toUML().getOwnedRules(), not(hasItem(guard.toUML())));

		// We do inherit a trigger guard, still
		guard = transition.getGuard();
		assertThat("No inherited guard", guard, notNullValue());
		assertThat(transition.toUML().getOwnedRules(), not(hasItem(guard.toUML())));
		assertThat(getUMLRTContents(transition.toUML(), UMLPackage.Literals.NAMESPACE__OWNED_RULE),
				hasItem(guard.toUML()));
	}

	/**
	 * @see <a href="http://eclip.se/513068">bug 513068</a>
	 */
	@Test
	public void addVertexToInheritedStateMachine() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		int count = sm.getVertices().size();

		UMLRTState newState = sm.createState("NewState");

		assertThat(sm.getVertices().size(), is(count + 1));
		assertThat(sm.getVertices(), hasItem(newState));

		// Re-inherit (bug 513068)
		// FIXME: Re-inherit the state machine when it is inherited by capsules
		// assertThat("State machine not re-inheritable", sm.reinherit(), is(true));
		assertThat("Region not re-inheritable", ((InternalUMLRTElement) sm.toRegion()).rtReinherit(), is(true));

		assertThat(sm.getVertices().size(), is(count));
		assertThat(sm.getVertices(), not(hasItem(newState)));

		// Check the region in the UML
		Region region = sm.toRegion();
		assertThat("Region was destroyed", region, notNullValue());
		assertThat(region.getSubvertices(), not(hasItem(anything())));
	}

	/**
	 * @see <a href="http://eclip.se/513068">bug 513068</a>
	 */
	@Test
	public void addTransitionToInheritedStateMachine() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		int count = sm.getTransitions().size();

		UMLRTVertex choice = sm.getVertices().stream()
				.filter(UMLRTPseudostate.class::isInstance).map(UMLRTPseudostate.class::cast)
				.filter(ps -> ps.getKind() == UMLRTPseudostateKind.CHOICE)
				.findAny().get();
		UMLRTVertex state1 = sm.getVertex("State1");

		UMLRTTransition newTransition = choice.createTransitionTo(state1);

		assertThat(sm.getTransitions().size(), is(count + 1));
		assertThat(sm.getTransitions(), hasItem(newTransition));

		// Re-inherit (bug 513068)
		// FIXME: Re-inherit the state machine when it is inherited by capsules
		// assertThat("State machine not re-inheritable", sm.reinherit(), is(true));
		assertThat("Region not re-inheritable", ((InternalUMLRTElement) sm.toRegion()).rtReinherit(), is(true));

		assertThat(sm.getTransitions().size(), is(count));
		assertThat(sm.getTransitions(), not(hasItem(newTransition)));

		// Check the region in the UML
		Region region = sm.toRegion();
		assertThat("Region was destroyed", region, notNullValue());
		assertThat(region.getTransitions(), not(hasItem(anything())));
	}

	/**
	 * @see <a href="http://eclip.se/513068">bug 513068</a>
	 */
	@Test
	public void addVertexToInheritedCompositeState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState composite = (UMLRTState) sm.getVertex("composite");
		assumeThat("Inherited composite state not found", composite, notNullValue());

		int count = composite.getSubvertices().size();

		UMLRTState newState = composite.createSubstate("NewState");

		assertThat(composite.getSubvertices().size(), is(count + 1));
		assertThat(composite.getSubvertices(), hasItem(newState));

		// Re-inherit (bug 513068)
		assertThat("Composite state not re-inheritable", composite.reinherit(), is(true));

		assertThat(composite.getSubvertices().size(), is(count));
		assertThat(composite.getSubvertices(), not(hasItem(newState)));

		// Check the region in the UML
		Region region = composite.toRegion();
		assertThat("Region was destroyed", region, notNullValue());
		assertThat(region.getSubvertices(), not(hasItem(anything())));
	}

	/**
	 * @see <a href="http://eclip.se/513068">bug 513068</a>
	 */
	@Test
	public void addTransitionToInheritedCompositeState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState composite = (UMLRTState) sm.getVertex("composite");
		assumeThat("Inherited composite state not found", composite, notNullValue());

		int count = composite.getSubtransitions().size();

		UMLRTVertex nested = composite.getSubvertex("nested");
		UMLRTVertex entry = composite.getEntryPoint("entry");

		UMLRTTransition newTransition = nested.createTransitionTo(entry);
		assertThat(newTransition, notNullValue());

		assertThat(composite.getSubtransitions().size(), is(count + 1));
		assertThat(composite.getSubtransitions(), hasItem(newTransition));

		// Re-inherit (bug 513068)
		assertThat("Composite state not re-inheritable", composite.reinherit(), is(true));

		assertThat(composite.getSubtransitions().size(), is(count));
		assertThat(composite.getSubtransitions(), not(hasItem(newTransition)));

		// Check the region in the UML
		Region region = composite.toRegion();
		assertThat("Region was destroyed", region, notNullValue());
		assertThat(region.getTransitions(), not(hasItem(anything())));
	}

	/**
	 * @see <a href="http://eclip.se/513068">bug 513068</a>
	 */
	@Test
	public void addConnectionPointToInheritedCompositeState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState composite = (UMLRTState) sm.getVertex("composite");
		assumeThat("Inherited composite state not found", composite, notNullValue());

		int count = composite.getConnectionPoints().size();

		UMLRTConnectionPoint newEntry = composite.createConnectionPoint(UMLRTConnectionPointKind.ENTRY);

		assertThat(composite.getConnectionPoints().size(), is(count + 1));
		assertThat(composite.getConnectionPoints(), hasItem(newEntry));

		// Re-inherit (bug 513068)
		assertThat("Composite state not re-inheritable", composite.reinherit(), is(true));

		assertThat(composite.getConnectionPoints().size(), is(count));
		assertThat(composite.getConnectionPoints(), not(hasItem(newEntry)));
	}

}
