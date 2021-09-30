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
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.named;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.redefines;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.function.Predicate;

import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the basic re-inherit support in {@link StateMachine}s.
 */
@TestModel("inheritance/statemachines.uml")
@NoFacade
@Category(StateMachineTests.class)
public class StateMachineReinheritTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public StateMachineReinheritTest() {
		super();
	}

	@Test
	public void regionReinherited() {
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

		assumeThat("Region not excluded", extending, isExcluded());

		reinherit(extending);

		assertThat("Region not reinherited", extending, isInherited());
		assertThat("Region still stereotyped as «RTRedefinedElement»", extending.getAppliedStereotypes(),
				not(hasItem(named("RTRedefinableElement"))));
	}

	@Test
	public void stateReinherited() {
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

		assumeThat("State not excluded", redefining, isExcluded());

		reinherit(redefining);

		assertThat("State not reinherited", redefining, isInherited());
		assertThat("State still stereotyped as «RTRedefinedElement»", redefining.getAppliedStereotypes(),
				not(hasItem(named("RTRedefinableElement"))));
	}

	@Test
	public void pseudostateReinherited() {
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

		assumeThat("Pseudostate not excluded", redefining, isExcluded());

		reinherit(redefining);

		assertThat("Pseudostate not reinherited", redefining, isInherited());
		assertThat("Constraint remains", extendingReg.getOwnedRules(), not(hasItem(anything())));
	}

	@Test
	public void transitionReinherited() {
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

		reinherit(redefining);

		assertThat("Transition not reinherited", redefining, isInherited());
		assertThat("Transition still stereotyped as «RTRedefinedElement»", redefining.getAppliedStereotypes(),
				not(hasItem(named("RTRedefinableElement"))));
	}

	@Test
	public void triggerReinherited() {
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

		assumeThat("Trigger not excluded", redefining, isExcluded());

		reinherit(redefining);

		assertThat("Trigger not reinherited", redefining, isInherited());
		assertThat("Constraint remains", redefTrans.getOwnedRules(), not(hasItem(anything())));
	}

	@Test
	public void stateReinheritedIndirectlyByRegion() {
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

		assumeThat("State not excluded", redefining, isExcluded());

		assumeThat("Region not redefined", extendingReg, isRedefined());
		reinherit(extendingReg);

		assertThat("State not reinherited", redefining, isInherited());
		assertThat("State still stereotyped as «RTRedefinedElement»", redefining.getAppliedStereotypes(),
				not(hasItem(named("RTRedefinableElement"))));
	}

	@TestModel("inheritance/RedoReinheritTest.uml")
	@Test
	public void redoReinheritTransitionWithTrigger() {
		StateMachine stateMachine = fixture.getElement("Capsule2::StateMachine", StateMachine.class);

		Region region = stateMachine.getRegion("Region");
		assumeThat(region, notNullValue());
		Transition transition = getTransition(region, named("State1"), named("State2"));
		assumeThat(transition, notNullValue());

		fixture.getResourceSet().eAdapters().add(new ECrossReferenceAdapter());
		ChangeDescription change = fixture.record(() -> reinherit(transition));
		assumeThat(transition, isInherited());

		// Undo
		fixture.undo(change);
		assertThat(transition, isRedefined());

		// Redo
		fixture.undo(change);
		assertThat(transition, isInherited());
	}

	@Test
	public void reinheritStateMachine() {
		StateMachine leafSM = getSubsubcapsuleBehavior();

		assumeThat(leafSM, isInherited());
		Region region = leafSM.getRegion("Region");
		assumeThat(region, notNullValue());

		region.createSubvertex("NewState", UMLPackage.Literals.STATE);
		assumeThat("State machine not redefined", leafSM, isRedefined());

		ChangeDescription change = fixture.record(() -> reinherit(leafSM));
		assertThat(leafSM, isInherited());
		assertThat("Lost the classifier-behaviorness in re-inherit", getSubsubcapsuleBehavior(), is(leafSM));

		fixture.undo(change);
		assertThat(leafSM, isRedefined());
		assertThat("Lost the classifier-behaviorness in undo", getSubsubcapsuleBehavior(), is(leafSM));

		fixture.undo(change);
		assertThat(leafSM, isInherited());
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

	Transition getTransition(Region region, Matcher<? super Vertex> source, Matcher<? super Vertex> target) {
		return getTransition(region, source::matches, target::matches);
	}

	Transition getTransition(Region region, Predicate<? super Vertex> source, Predicate<? super Vertex> target) {
		Predicate<Transition> source_ = (source == null) ? __ -> true : t -> source.test(t.getSource());
		Predicate<Transition> target_ = (target == null) ? __ -> true : t -> target.test(t.getTarget());
		return region.getOwnedMembers().stream()
				.filter(Transition.class::isInstance).map(Transition.class::cast)
				.filter(source_.and(target_))
				.findAny().orElse(null);
	}

	void exclude(Element element) {
		assumeThat("Element is not excludeable", element, instanceOf(InternalUMLRTElement.class));
		assertThat("Element is not excludeable", ((InternalUMLRTElement) element).rtExclude(), is(true));
	}

	void reinherit(Element element) {
		assumeThat("Element is not re-inheritable", element, instanceOf(InternalUMLRTElement.class));
		assertThat("Element is not re-inheritable", ((InternalUMLRTElement) element).rtReinherit(), is(true));
	}
}
