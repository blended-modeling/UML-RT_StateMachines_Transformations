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

import static java.util.Collections.singletonList;
import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.getRootDefinition;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.List;

import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Specific regression test cases for transition trigger exclusion
 * in state machine inheritance.
 */
@TestModel("inheritance/TriggerGuardTest.uml")
@Category(StateMachineTests.class)
public class TriggerExclusionTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public TriggerExclusionTest() {
		super();
	}

	@NoFacade
	@Test
	public void excludeTrigger() {
		Trigger trigger = getTrigger();
		exclude(trigger);

		Transition transition = getTransition();
		List<Constraint> constraints = transition.getOwnedRules();
		assertThat(constraints.size(), is(1));
		assertThat(constraints.get(0).getConstrainedElements(), is(singletonList(getRootDefinition(trigger))));
		assertThat(constraints.get(0).getSpecification(), instanceOf(LiteralBoolean.class));
	}

	@NoFacade
	@Test
	public void reinheritTrigger() {
		Trigger trigger = getTrigger();
		exclude(trigger);

		reinherit(trigger);

		// We re-inherit the original guard constraint
		Transition transition = getTransition();
		List<Constraint> constraints = UMLRTExtensionUtil.getUMLRTContents(transition, UMLPackage.Literals.NAMESPACE__OWNED_RULE);
		assertThat(constraints.size(), is(1));
		assertThat(constraints.get(0), UMLMatchers.isInherited());
		assertThat(constraints.get(0).getConstrainedElements(), is(singletonList(getRootDefinition(trigger))));
		assertThat(constraints.get(0).getSpecification(), instanceOf(OpaqueExpression.class));

		OpaqueExpression spec = (OpaqueExpression) constraints.get(0).getSpecification();
		assertThat(spec.getBodies(), is(singletonList("// Trigger guard")));
		assertThat(spec.getLanguages(), is(singletonList("C++")));
	}

	@NoFacade
	@Test
	public void undoRedoExcludeTrigger() {
		Trigger trigger = getTrigger();
		Transition transition = getTransition();

		ChangeDescription change = fixture.record(() -> exclude(trigger));

		fixture.repeat(3, which -> {
			ChangeDescription change_ = change;
			List<Constraint> constraints = transition.getOwnedRules();

			if (which == 0) {
				// First time around, make a weaker assertion because this is just like
				// the basic exclude test case above
				assumeThat(constraints.size(), is(1));
				assumeThat(constraints.get(0).getConstrainedElements(), is(singletonList(getRootDefinition(trigger))));
				assumeThat(constraints.get(0).getSpecification(), instanceOf(LiteralBoolean.class));
			} else {
				assertThat(constraints.size(), is(1));
				assertThat(constraints.get(0).getConstrainedElements(), is(singletonList(getRootDefinition(trigger))));
				assertThat(constraints.get(0).getSpecification(), instanceOf(LiteralBoolean.class));
			}

			// Undo
			fixture.undo(change_);

			// This is not like re-inherit
			constraints = transition.getOwnedRules();
			assertThat(constraints.size(), is(1));
			assertThat(constraints.get(0), UMLMatchers.isRedefined());
			assertThat(constraints.get(0).getConstrainedElements(), is(singletonList(getRootDefinition(trigger))));
			assertThat(constraints.get(0).getSpecification(), instanceOf(OpaqueExpression.class));

			OpaqueExpression spec = (OpaqueExpression) constraints.get(0).getSpecification();
			assertThat(spec.getBodies(), is(singletonList("// Redefined trigger guard")));
			assertThat(spec.getLanguages(), is(singletonList("C++")));

			// Redo
			fixture.undo(change_);
		});
	}

	@Test
	public void excludeTriggerFacade() {
		UMLRTTrigger trigger = getTriggerFacade();
		UMLRTGuard guard = trigger.getGuard();

		assumeThat("No trigger guard", guard, notNullValue());

		trigger.exclude();

		assertThat("Trigger still has a guard", trigger.getGuard(), nullValue());

		Transition transition = getTransition();
		List<Constraint> constraints = transition.getOwnedRules();
		assertThat(constraints, not(hasItem(guard.toUML())));
	}

	@Test
	public void reinheritTriggerFacade() {
		UMLRTTrigger trigger = getTriggerFacade();
		UMLRTGuard guard = trigger.getGuard();

		assumeThat("No trigger guard", guard, notNullValue());

		trigger.exclude();

		trigger.reinherit();

		assertThat("Trigger has no guard", trigger.getGuard(), notNullValue());
		assertThat("Wrong trigger guard", trigger.getGuard(), is(guard));
		assertThat(guard, FacadeMatchers.isInherited());
		assertThat(guard.getBodies().get("C++"), is("// Trigger guard"));
	}

	@Test
	public void undoRedoExcludeTriggerFacade() {
		UMLRTTrigger trigger = getTriggerFacade();
		UMLRTGuard guard = trigger.getGuard();
	
		assumeThat("No trigger guard", guard, notNullValue());
	
		ChangeDescription change = fixture.record(trigger::exclude);
	
		fixture.repeat(3, which -> {
			ChangeDescription change_ = change;
			List<Constraint> constraints = getTransition().getOwnedRules();
	
			if (which == 0) {
				// First time around, make a weaker assertion because this is just like
				// the basic exclude test case above
				assumeThat("Trigger still has a guard", trigger.getGuard(), nullValue());
				assumeThat(constraints, not(hasItem(guard.toUML())));
			} else {
				assertThat("Trigger still has a guard", trigger.getGuard(), nullValue());
				assertThat(constraints, not(hasItem(guard.toUML())));
			}
	
			// Undo
			fixture.undo(change_);
	
			// This is not like re-inherit
			assertThat(trigger.getGuard(), is(guard));
			assertThat(guard, FacadeMatchers.isRedefined());
			assertThat(guard.getBodies().get("C++"), is("// Redefined trigger guard"));
	
			// Redo
			fixture.undo(change_);
		});
	}

	//
	// Test framework
	//

	Transition getTransition() {
		Region region = fixture.getElement("Capsule2::StateMachine::Region", Region.class);
		return region.getSubvertex("State1").getOutgoings().get(0);
	}

	Trigger getTrigger() {
		List<Trigger> result = UMLRTExtensionUtil.getUMLRTContents(getTransition(), UMLPackage.Literals.TRANSITION__TRIGGER);
		return result.get(0);
	}

	UMLRTTransition getTransitionFacade() {
		return fixture.getRoot().getCapsule("Capsule2").getStateMachine()
				.getVertex("State1").getOutgoings().get(0);
	}

	UMLRTTrigger getTriggerFacade() {
		return getTransitionFacade().getTriggers().get(0);
	}

	void exclude(Element element) {
		assumeThat("Element is not excludable", element, instanceOf(InternalUMLRTElement.class));
		assertThat("Element is not excludable", ((InternalUMLRTElement) element).rtExclude(), is(true));
	}

	void reinherit(Element element) {
		assumeThat("Element is not re-inheritable", element, instanceOf(InternalUMLRTElement.class));
		assertThat("Element is not re-inheritable", ((InternalUMLRTElement) element).rtReinherit(), is(true));
	}
}
