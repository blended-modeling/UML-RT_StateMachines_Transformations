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
import java.util.stream.Collectors;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Trigger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Various test cases for {@code Transition} and {@link Trigger} guard façades.
 */
@TestModel("inheritance/statemachines.uml")
@Category({ StateMachineTests.class, FacadeTests.class })
public class GuardFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public GuardFacadeTest() {
		super();
	}

	@Test
	public void triggerGuard() {
		UMLRTStateMachine sm = getRootCapsuleBehavior();
		UMLRTTransition trans = sm.getTransition("Initial");
		assumeThat(trans, notNullValue());

		UMLRTTrigger trigger = trans.getTrigger("greet");
		assumeThat(trigger, notNullValue());
		UMLRTGuard guard = trigger.getGuard();
		assumeThat("Trigger guard not found", guard, notNullValue());

		assertThat(guard, isRootDefinition());
		assertThat(guard.getRedefinedGuard(), nullValue());

		assertThat(guard.getRedefinitionContext(), is(trigger));
	}

	@Test
	public void transitionGuard() {
		UMLRTStateMachine sm = getRootCapsuleBehavior();
		UMLRTTransition trans = sm.getTransition("in");
		assumeThat(trans, notNullValue());

		UMLRTGuard guard = trans.getGuard();
		assumeThat("Guard not found", guard, notNullValue());

		assertThat(guard, isRootDefinition());
		assertThat(guard.getRedefinedGuard(), nullValue());

		assertThat(guard.getRedefinitionContext(), is(trans));
	}

	@Test
	public void triggerGuardInherited() {
		UMLRTStateMachine parentSM = getRootCapsuleBehavior();
		UMLRTStateMachine extendingSM = getSubcapsuleBehavior();

		UMLRTTransition parentTrans = parentSM.getTransition("Initial");
		assumeThat(parentTrans, notNullValue());

		UMLRTTransition redefTrans = extendingSM.getTransition("Initial");
		assumeThat(redefTrans, notNullValue());
		assumeThat(redefTrans, FacadeMatchers.isInherited());

		UMLRTTrigger parentTrigger = parentTrans.getTrigger("greet");
		assumeThat(parentTrigger, notNullValue());
		UMLRTGuard parent = parentTrigger.getGuard();
		assumeThat("Trigger guard not found", parent, notNullValue());

		UMLRTTrigger redefTrigger = redefTrans.getTrigger("greet");
		assumeThat(redefTrigger, notNullValue());
		UMLRTGuard redefining = redefTrigger.getGuard();
		assertThat("Inherited trigger guard not found", redefining, notNullValue());

		assertThat(redefining, isInherited());
		assertThat(redefining, redefines(parent));
	}

	@Test
	public void transitionGuardInherited() {
		UMLRTStateMachine parentSM = getRootCapsuleBehavior();
		UMLRTStateMachine extendingSM = getSubcapsuleBehavior();

		UMLRTTransition parentTrans = parentSM.getTransition("in");
		assumeThat(parentTrans, notNullValue());

		UMLRTTransition redefTrans = extendingSM.getTransition("in");
		assumeThat(redefTrans, notNullValue());
		assumeThat(redefTrans, FacadeMatchers.isInherited());

		UMLRTGuard parent = parentTrans.getGuard();
		assumeThat("Guard not found", parent, notNullValue());

		UMLRTGuard redefining = redefTrans.getGuard();
		assertThat("Inherited guard not found", redefining, notNullValue());

		assertThat(redefining, isInherited());
		assertThat(redefining, redefines(parent));
	}

	@Test
	public void allRedefinitions() {
		UMLRTStateMachine rootSM = getRootCapsuleBehavior();
		UMLRTStateMachine subSM = getSubcapsuleBehavior();
		UMLRTStateMachine subsubSM = getSubsubcapsuleBehavior();

		UMLRTGuard rootGuard = getGuard(rootSM, "in");
		UMLRTGuard subGuard = getGuard(subSM, "in");
		UMLRTGuard subsubGuard = getGuard(subsubSM, "in");

		List<UMLRTGuard> expected = Arrays.asList(rootGuard, subGuard, subsubGuard);
		List<? extends UMLRTGuard> actual = rootGuard.allRedefinitions().collect(Collectors.toList());
		// There may be other redefinitions, too. Important to keep the order
		actual.retainAll(expected);
		assertThat(actual, is(expected));
	}

	/**
	 * Verifies that a guard's façade correctly reports its
	 * trigger in the case of no inheritance (control test).
	 * 
	 * @see <a href="http://eclip.se/516745">bug 516745</a>
	 */
	@Test
	public void rootGuardTrigger() {
		guardTriggerTest(getRootCapsuleBehavior());
	}

	void guardTriggerTest(UMLRTStateMachine stateMachine) {
		UMLRTTransition transition = stateMachine.getTransition("Initial");
		assumeThat(transition, notNullValue());

		UMLRTTrigger trigger = transition.getTrigger("greet");
		assumeThat(trigger, notNullValue());
		UMLRTGuard guard = trigger.getGuard();
		assertThat("Trigger guard not found", guard, notNullValue());

		assertThat(guard.getTrigger(), is(trigger));
	}

	/**
	 * Verifies that an inherited or redefining guard's façade correctly
	 * reports its trigger as the (implicitly) redefining trigger.
	 * 
	 * @see <a href="http://eclip.se/516745">bug 516745</a>
	 */
	@Test
	public void inheritedGuardTrigger() {
		guardTriggerTest(getSubcapsuleBehavior());
	}

	/**
	 * Verifies that an inherited or redefining guard's façade correctly
	 * reports its transition as the redefining transition.
	 * 
	 * @see <a href="http://eclip.se/516745">bug 516745</a>
	 */
	@Test
	public void inheritedGuardTransition() {
		UMLRTStateMachine extendingSM = getSubcapsuleBehavior();

		UMLRTTransition redefTrans = extendingSM.getTransition("in");
		assumeThat(redefTrans, notNullValue());
		assumeThat(redefTrans, FacadeMatchers.isInherited());

		UMLRTGuard redefining = redefTrans.getGuard();
		assertThat("Inherited guard not found", redefining, notNullValue());

		assertThat(redefining.getTransition(), is(redefTrans));
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

	UMLRTGuard getGuard(UMLRTStateMachine sm, String transitionName) {
		UMLRTTransition transition = sm.getTransition(transitionName);
		assumeThat("Transition not found", transition, notNullValue());

		UMLRTGuard result = transition.getGuard();
		assumeThat("Guard not found", result, notNullValue());

		return result;
	}
}
