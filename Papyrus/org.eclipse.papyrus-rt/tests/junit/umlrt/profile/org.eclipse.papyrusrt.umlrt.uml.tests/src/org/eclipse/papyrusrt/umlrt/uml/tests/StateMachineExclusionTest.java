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

import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.isExcluded;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.isInherited;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.isRedefined;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.redefines;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTGuard;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the basic exclusion support in {@link StateMachine}s.
 */
@TestModel("inheritance/statemachines.uml")
@NoFacade
@Category(StateMachineTests.class)
public class StateMachineExclusionTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public StateMachineExclusionTest() {
		super();
	}

	@Test
	public void regionExcluded() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parent = parentSM.getRegion("Region");
		Region extending = extendingSM.getRegion("Region");

		assumeThat(parent, notNullValue());
		assumeThat(extending, notNullValue());
		assumeThat(extending, redefines(parent));
		assumeThat(extending.getExtendedRegion(), is(parent));
		assumeThat(extending, isInherited());

		exclude(extending);

		assertThat("Region not excluded", extending, isExcluded());
	}

	@Test
	public void stateExcluded() {
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

		exclude(redefining);

		assertThat("State not excluded", redefining, isExcluded());
		assertThat("Container not redefined", extendingReg, isRedefined());
	}

	@Test
	public void pseudostateExcluded() {
		// Pseudostates are not redefinable but can be excluded by the
		// UML-RT's exceptional exclusion mechanism employing a constraint
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Pseudostate parent = getPseudostate(parentReg, PseudostateKind.CHOICE_LITERAL);
		assumeThat(parent, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Pseudostate redefining = getPseudostate(extendingReg, PseudostateKind.CHOICE_LITERAL);
		assumeThat(redefining, notNullValue());
		assumeThat(redefining, redefines(parent));
		assumeThat(redefining, isInherited());

		exclude(redefining);

		assertThat("Pseudostate not excluded", redefining, isExcluded());
		assertThat("Container not redefined", extendingReg, isRedefined());
	}

	@Test
	public void transitionExcluded() {
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

		exclude(redefining);

		assertThat("Transition not excluded", redefining, isExcluded());
		assertThat("Container not redefined", extendingReg, isRedefined());
	}

	@Test
	public void triggerExcluded() {
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

		exclude(redefining);

		assertThat("Trigger not excluded", redefining, isExcluded());
		assertThat("Transition not redefined", redefTrans, isRedefined());

		Constraint exclusion = redefTrans.getOwnedRules().stream()
				.filter(c -> c.getConstrainedElements().contains(parent))
				.findAny().orElseThrow(AssertionError::new);

		RTGuard guardStereo = UMLUtil.getStereotypeApplication(exclusion, RTGuard.class);
		assertThat("Exclusion constraint is not stereotyped as «RTGuard»", guardStereo, notNullValue());
		assertThat("Stereotype application not persistent", guardStereo.eResource(), is(fixture.getResource()));
	}

	@Test
	public void excludeStateMachine() {
		StateMachine leafSM = getSubsubcapsuleBehavior();
		assumeThat(leafSM, isInherited());

		ChangeDescription change = fixture.record(() -> exclude(leafSM));
		assertThat(leafSM, isExcluded());

		fixture.undo(change);
		assertThat(leafSM, isInherited());
		assertThat("Lost the classifier-behaviorness in undo", getSubsubcapsuleBehavior(), is(leafSM));

		fixture.undo(change);
		assertThat(leafSM, isExcluded());
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

	void exclude(Element element) {
		assumeThat("Element is not excludeable", element, instanceOf(InternalUMLRTElement.class));
		assertThat("Element is not excludeable", ((InternalUMLRTElement) element).rtExclude(), is(true));
	}
}
