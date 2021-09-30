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

package org.eclipse.papyrusrt.umlrt.core.tests.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTProfileUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Test cases for the {@link UMLRTProfileUtils} API.
 */
@PluginResource("resource/inheritance/trigger_reinherit.di")
public class UMLRTProfileUtilsTest {

	@ClassRule
	public static final UMLRTModelSetFixture model = new UMLRTModelSetFixture();

	public UMLRTProfileUtilsTest() {
		super();
	}

	@Test
	public void canExclude_true() {
		UMLRTTransition noGuard = getStateMachine().getTransition("noGuard");
		assertThat(UMLRTProfileUtils.canExclude(noGuard.toUML()), is(true));
	}

	@Test
	public void canExclude_false() {
		UMLRTTransition initial = getStateMachine().getTransition("Initial");
		UMLRTVertex initialPseudostate = initial.getSource();
		assertThat(UMLRTProfileUtils.canExclude(initialPseudostate.toUML()), is(false));
	}

	@Test
	public void canRedefine_true() {
		UMLRTTransition initial = getStateMachine().getTransition("Initial");
		UMLRTTransition hasGuard = getStateMachine().getTransition("hasGuard");
		UMLRTTrigger trigger = hasGuard.getTriggers().get(0);
		UMLRTGuard guard = trigger.getGuard();

		model.execute(() -> assumeThat("Could not reinherit guard to prepare test",
				guard.reinherit(), is(true)));

		try {
			assertThat(UMLRTProfileUtils.canRedefine(initial.toUML()), is(true));
			assertThat(UMLRTProfileUtils.canRedefine(trigger.getGuard().toUML()), is(true));
		} finally {
			// Restore the clean model state
			model.undo();
		}
	}

	@Test
	public void canRedefine_false() {
		UMLRTTransition initial = getStateMachine().getTransition("Initial");
		UMLRTTransition hasGuard = getStateMachine().getTransition("hasGuard");
		UMLRTVertex initialPseudostate = initial.getSource();
		assertThat(UMLRTProfileUtils.canRedefine(initialPseudostate.toUML()), is(false));
		// Already redefined
		assertThat(UMLRTProfileUtils.canRedefine(hasGuard.toUML()), is(false));
		// Triggers are not redefinable
		assertThat(UMLRTProfileUtils.canRedefine(hasGuard.getTriggers().get(0).toUML()), is(false));
	}

	@Test
	public void canReinherit_true() {
		UMLRTTransition hasGuard = getStateMachine().getTransition("hasGuard");
		UMLRTTrigger trigger = hasGuard.getTriggers().get(0);
		UMLRTGuard triggerGuard = trigger.getGuard();

		assertThat(UMLRTProfileUtils.canReinherit(hasGuard.toUML()), is(true));
		// Special case for triggers that are not actually redefinable
		assertThat(UMLRTProfileUtils.canReinherit(triggerGuard.toUML()), is(true));
		// The actual element to reinherit
		assertThat(UMLRTProfileUtils.canReinherit(triggerGuard.toUML()), is(true));
	}

	@Test
	public void canReInherit_false() {
		UMLRTTransition initial = getStateMachine().getTransition("Initial");

		// Not redefined
		assertThat(UMLRTProfileUtils.canReinherit(initial.toUML()), is(false));
		// Must be a redefinition (for the sake of the diagram)
		assertThat(UMLRTProfileUtils.canReinherit(initial.containingStateMachine().toUML()), is(false));
	}

	//
	// Test framework
	//

	UMLRTStateMachine getStateMachine(boolean root) {
		return UMLRTPackage.getInstance(model.getModel())
				.getCapsule(root ? "Capsule1" : "Capsule2")
				.getStateMachine();
	}

	UMLRTStateMachine getStateMachine() {
		return getStateMachine(false);
	}
}
