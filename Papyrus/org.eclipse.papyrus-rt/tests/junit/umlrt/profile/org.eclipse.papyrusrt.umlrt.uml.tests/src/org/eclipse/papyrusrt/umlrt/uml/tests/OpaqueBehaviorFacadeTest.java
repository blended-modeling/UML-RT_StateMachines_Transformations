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
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.isRootDefinition;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.redefines;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.State;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Various test cases for {@code Transition} and {@link State} behavior façades.
 */
@TestModel("inheritance/statemachines.uml")
@Category({ StateMachineTests.class, FacadeTests.class })
public class OpaqueBehaviorFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public OpaqueBehaviorFacadeTest() {
		super();
	}

	@Test
	public void transitionEffect() {
		UMLRTStateMachine sm = getRootCapsuleBehavior();
		UMLRTTransition trans = sm.getTransition("nestedIn");
		assumeThat(trans, notNullValue());

		testRootBehavior(trans, UMLRTTransition::getEffect);

		assertThat(trans.getEffect().getTransition(), is(trans));
	}

	<O extends UMLRTNamedElement> void testRootBehavior(O owner,
			Function<? super O, UMLRTOpaqueBehavior> accessor) {

		UMLRTOpaqueBehavior behavior = accessor.apply(owner);
		assumeThat("Behavior not found", behavior, notNullValue());

		assertThat(behavior, isRootDefinition());
		assertThat(behavior.getRedefinedBehavior(), nullValue());

		assertThat(behavior.getRedefinitionContext(), is(owner));
	}

	@Test
	public void stateEntry() {
		UMLRTStateMachine sm = getRootCapsuleBehavior();
		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat(state, notNullValue());

		testRootBehavior(state, UMLRTState::getEntry);

		assertThat(state.getEntry().getEnteredState(), is(state));
	}

	@Test
	public void stateExit() {
		UMLRTStateMachine sm = getRootCapsuleBehavior();
		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat(state, notNullValue());

		testRootBehavior(state, UMLRTState::getExit);

		assertThat(state.getExit().getExitedState(), is(state));
	}

	@Test
	public void transitionEffectInherited() {
		UMLRTStateMachine parentSM = getRootCapsuleBehavior();
		UMLRTStateMachine extendingSM = getSubcapsuleBehavior();

		UMLRTTransition parentTrans = parentSM.getTransition("nestedIn");
		assumeThat(parentTrans, notNullValue());

		UMLRTTransition redefTrans = extendingSM.getTransition("nestedIn");
		assumeThat(redefTrans, notNullValue());
		assumeThat(redefTrans, isInherited());

		testInheritedBehavior(parentTrans, redefTrans, UMLRTTransition::getEffect);

		assertThat(redefTrans.getEffect().getTransition(), is(redefTrans));
	}

	<O extends UMLRTNamedElement> void testInheritedBehavior(O parentOwner,
			O redefOwner, Function<? super O, UMLRTOpaqueBehavior> accessor) {

		// Sanity
		assumeThat(redefOwner, isInherited());

		UMLRTOpaqueBehavior parent = accessor.apply(parentOwner);
		assumeThat("Redefined behavior not found", parent, notNullValue());

		UMLRTOpaqueBehavior redefining = accessor.apply(redefOwner);
		assertThat("Redefining behavior not found", redefining, notNullValue());

		assertThat(redefining, isInherited());
		assertThat(redefining, redefines(parent));
	}

	@Test
	public void stateEntryInherited() {
		UMLRTStateMachine parentSM = getRootCapsuleBehavior();
		UMLRTStateMachine extendingSM = getSubcapsuleBehavior();

		UMLRTState parentState = (UMLRTState) parentSM.getVertex("State1");
		assumeThat(parentState, notNullValue());

		UMLRTState redefState = (UMLRTState) extendingSM.getVertex("State1");
		assumeThat(redefState, notNullValue());
		assumeThat(redefState, isInherited());

		testInheritedBehavior(parentState, redefState, UMLRTState::getEntry);

		assertThat(redefState.getEntry().getEnteredState(), is(redefState));
	}

	@Test
	public void stateExitInherited() {
		UMLRTStateMachine parentSM = getRootCapsuleBehavior();
		UMLRTStateMachine extendingSM = getSubcapsuleBehavior();

		UMLRTState parentState = (UMLRTState) parentSM.getVertex("State1");
		assumeThat(parentState, notNullValue());

		UMLRTState redefState = (UMLRTState) extendingSM.getVertex("State1");
		assumeThat(redefState, notNullValue());
		assumeThat(redefState, isInherited());

		testInheritedBehavior(parentState, redefState, UMLRTState::getExit);

		assertThat(redefState.getExit().getExitedState(), is(redefState));
	}

	@Test
	public void allRedefinitions() {
		UMLRTStateMachine rootSM = getRootCapsuleBehavior();
		UMLRTStateMachine subSM = getSubcapsuleBehavior();
		UMLRTStateMachine subsubSM = getSubsubcapsuleBehavior();

		UMLRTOpaqueBehavior rootEffect = getEffect(rootSM, "nestedIn");
		UMLRTOpaqueBehavior subEffect = getEffect(subSM, "nestedIn");
		UMLRTOpaqueBehavior subsubEffect = getEffect(subsubSM, "nestedIn");

		List<UMLRTOpaqueBehavior> expected = Arrays.asList(rootEffect, subEffect, subsubEffect);
		List<? extends UMLRTOpaqueBehavior> actual = rootEffect.allRedefinitions().collect(Collectors.toList());
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

	UMLRTOpaqueBehavior getEffect(UMLRTStateMachine sm, String transitionName) {
		UMLRTTransition transition = sm.getTransition(transitionName);
		assumeThat("Transition not found", transition, notNullValue());

		UMLRTOpaqueBehavior result = transition.getEffect();
		assumeThat("Effect not found", result, notNullValue());

		return result;
	}

}
