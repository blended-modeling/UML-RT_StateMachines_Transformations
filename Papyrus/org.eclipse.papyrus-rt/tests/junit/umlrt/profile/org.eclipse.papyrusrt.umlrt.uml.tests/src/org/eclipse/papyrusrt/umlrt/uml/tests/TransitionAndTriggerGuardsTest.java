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
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.redefines;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTGuard;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers;
import org.eclipse.papyrusrt.umlrt.uml.util.Pair;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.EncapsulatedClassifier;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Various test cases for {@code Transition} and {@link Trigger} guard constraints,
 * including inheritance, redefinition, exclusion, and reinheritance.
 */
@TestModel("inheritance/statemachines.uml")
@NoFacade
@Category(StateMachineTests.class)
public class TransitionAndTriggerGuardsTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public TransitionAndTriggerGuardsTest() {
		super();
	}

	@Test
	public void transitionGuardInherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parentTrans = parentReg.getTransition("in");
		assumeThat(parentTrans, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefTrans = extendingReg.getTransition("in");
		assumeThat(redefTrans, notNullValue());
		assumeThat(redefTrans, isInherited());

		Constraint parent = parentTrans.getGuard();
		assumeThat("Guard not found", parent, notNullValue());

		Constraint redefining = redefTrans.getGuard();
		assertThat("Inherited guard not found", redefining, notNullValue());

		assertThat(redefining, isInherited());
		assertThat(redefining, redefines(parent));
		assertThat(redefTrans.eIsSet(UMLPackage.Literals.TRANSITION__GUARD), is(false));

		assertThat(redefining.getSpecification(), instanceOf(OpaqueExpression.class));
		OpaqueExpression spec = (OpaqueExpression) redefining.getSpecification();
		assertThat(spec.getLanguages(), hasItem("C++"));
		assertThat(spec.getBodies(), hasItem("this.isConsistent()"));
	}

	@Test
	public void transitionGuardRedefined() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parentTrans = parentReg.getTransition("in");
		assumeThat(parentTrans, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefTrans = extendingReg.getTransition("in");
		assumeThat(redefTrans, notNullValue());
		assumeThat(redefTrans, isInherited());

		Constraint parent = parentTrans.getGuard();
		assumeThat("Guard not found", parent, notNullValue());

		Constraint redefining = redefTrans.getGuard();
		assumeThat("Inherited guard not found", redefining, notNullValue());

		assumeThat(redefining, isInherited());
		assumeThat(redefining, redefines(parent));

		assumeThat(redefining.getSpecification(), instanceOf(OpaqueExpression.class));
		OpaqueExpression spec = (OpaqueExpression) redefining.getSpecification();
		assumeThat(spec.getBodies(), hasItem(anything()));

		spec.getBodies().set(0, "true");

		assertThat(spec.getBodies(), is(Arrays.asList("true")));

		assertThat("Guard not redefined", redefining, isRedefined());
		assertThat("Transition not redefined", redefTrans, isRedefined());
		assertThat("Guard not persistent", ((InternalEObject) redefining).eInternalResource(), is(fixture.getResource()));
		assertThat("Specification not persistent", ((InternalEObject) spec).eInternalResource(), is(fixture.getResource()));
		assertThat("Body not set", spec.eIsSet(UMLPackage.Literals.OPAQUE_EXPRESSION__BODY), is(true));
	}

	// Transitions do not support excluding their guards

	@Test
	public void transitionGuardReinherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parentTrans = parentReg.getTransition("in");
		assumeThat(parentTrans, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefTrans = extendingReg.getTransition("in");
		assumeThat(redefTrans, notNullValue());
		assumeThat(redefTrans, isInherited());

		Constraint parent = parentTrans.getGuard();
		assumeThat("Guard not found", parent, notNullValue());

		Constraint redefining = redefTrans.getGuard();
		assumeThat("Inherited guard not found", redefining, notNullValue());

		assumeThat(redefining, isInherited());
		assumeThat(redefining, redefines(parent));

		assumeThat(redefining.getSpecification(), instanceOf(OpaqueExpression.class));
		OpaqueExpression spec = (OpaqueExpression) redefining.getSpecification();
		assumeThat(spec.getBodies(), hasItem(anything()));

		String originalBody = spec.getBodies().get(0);
		spec.getBodies().set(0, "true");

		assumeThat("Guard not redefined", redefining, isRedefined());

		reinherit(redefining);

		assertThat("Guard not reinherited", redefining, isInherited());

		assertThat(spec.getBodies(), is(Arrays.asList(originalBody)));
	}

	@Test
	public void triggerGuardInherited() {
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

		Trigger trigger = parentTrans.getTrigger("greet");
		Constraint parent = getGuard(parentTrans, trigger);
		assumeThat("Trigger guard not found", parent, notNullValue());

		Constraint redefining = getGuard(redefTrans, trigger);
		assertThat("Inherited trigger guard not found", redefining, notNullValue());

		assertThat(redefining, isInherited());
		assertThat(redefining, redefines(parent));
		assertThat(redefTrans.eIsSet(UMLPackage.Literals.TRANSITION__GUARD), is(false));

		assertThat(redefining.getSpecification(), instanceOf(OpaqueExpression.class));
		OpaqueExpression spec = (OpaqueExpression) redefining.getSpecification();
		assertThat(spec.getLanguages(), hasItem("C++"));
		assertThat(spec.getBodies(), hasItem("this.isReady()"));
	}

	@Test
	public void triggerGuardRedefined() {
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

		Trigger trigger = parentTrans.getTrigger("greet");
		Constraint parent = getGuard(parentTrans, trigger);
		assumeThat("Trigger guard not found", parent, notNullValue());

		Constraint redefining = getGuard(redefTrans, trigger);
		assumeThat("Inherited trigger guard not found", redefining, notNullValue());

		assumeThat(redefining, isInherited());
		assumeThat(redefining, redefines(parent));

		assumeThat(redefining.getSpecification(), instanceOf(OpaqueExpression.class));
		OpaqueExpression spec = (OpaqueExpression) redefining.getSpecification();
		assumeThat(spec.getBodies(), hasItem(anything()));

		spec.getBodies().set(0, "true");

		assertThat(spec.getBodies(), is(Arrays.asList("true")));

		assertThat("Trigger guard not redefined", redefining, isRedefined());
		assertThat("Transition not redefined", redefTrans, isRedefined());
		assertThat("Guard not persistent", ((InternalEObject) redefining).eInternalResource(), is(fixture.getResource()));
		assertThat("Specification not persistent", ((InternalEObject) spec).eInternalResource(), is(fixture.getResource()));
		assertThat("Body not set", spec.eIsSet(UMLPackage.Literals.OPAQUE_EXPRESSION__BODY), is(true));

		// We don't reference the inherited trigger
		assertThat(((InternalEList<Element>) redefining.getConstrainedElements()).basicList(), hasItem(trigger));
	}

	// Transitions do not support excluding their trigger guards

	@Test
	public void triggerGuardReinherited() {
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

		Trigger trigger = parentTrans.getTrigger("greet");
		Constraint parent = getGuard(parentTrans, trigger);
		assumeThat("Trigger guard not found", parent, notNullValue());

		Constraint redefining = getGuard(redefTrans, trigger);
		assumeThat("Inherited trigger guard not found", redefining, notNullValue());

		assumeThat(redefining, isInherited());
		assumeThat(redefining, redefines(parent));

		assumeThat(redefining.getSpecification(), instanceOf(OpaqueExpression.class));
		OpaqueExpression spec = (OpaqueExpression) redefining.getSpecification();
		assumeThat(spec.getBodies(), hasItem(anything()));

		spec.getBodies().set(0, "true");

		assumeThat("Guard not redefined", redefining, isRedefined());

		reinherit(redefining);

		assertThat("Guard not reinherited", redefining, isInherited());

		assertThat(spec.getBodies(), is(Arrays.asList("this.isReady()")));
	}

	@Test
	public void transitionEffectInherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parentTrans = parentReg.getTransition("nestedIn");
		assumeThat(parentTrans, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefTrans = extendingReg.getTransition("nestedIn");
		assumeThat(redefTrans, notNullValue());
		assumeThat(redefTrans, isInherited());

		OpaqueBehavior parent = (OpaqueBehavior) parentTrans.getEffect();
		assumeThat("Effect not found", parent, notNullValue());

		OpaqueBehavior redefining = (OpaqueBehavior) redefTrans.getEffect();
		assertThat("Inherited effect not found", redefining, notNullValue());

		assertThat(redefining, isInherited());
		assertThat(redefining, redefines(parent));
		assertThat(redefTrans.eIsSet(UMLPackage.Literals.TRANSITION__EFFECT), is(false));

		assertThat(redefining.getLanguages(), hasItem("C++"));
		assertThat(redefining.getBodies(), hasItem("this.doTheThing();"));
	}

	@Test
	public void transitionEffectRedefined() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parentTrans = parentReg.getTransition("nestedIn");
		assumeThat(parentTrans, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefTrans = extendingReg.getTransition("nestedIn");
		assumeThat(redefTrans, notNullValue());
		assumeThat(redefTrans, isInherited());

		OpaqueBehavior parent = (OpaqueBehavior) parentTrans.getEffect();
		assumeThat("Effect not found", parent, notNullValue());

		OpaqueBehavior redefining = (OpaqueBehavior) redefTrans.getEffect();
		assumeThat("Inherited effect not found", redefining, notNullValue());

		assumeThat(redefining, isInherited());
		assumeThat(redefining, redefines(parent));

		assumeThat(redefining.getBodies(), hasItem(anything()));

		redefining.getBodies().set(0, "pass();");

		assertThat(redefining.getBodies(), is(Arrays.asList("pass();")));

		assertThat("Effect not redefined", redefining, isRedefined());
		assertThat("Transition not redefined", redefTrans, isRedefined());
		assertThat("Effect not persistent", ((InternalEObject) redefining).eInternalResource(), is(fixture.getResource()));
		assertThat("Body not set", redefining.eIsSet(UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY), is(true));
	}

	// Transitions do not support excluding their effects

	@Test
	public void transitionEffectReinherited() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parentTrans = parentReg.getTransition("nestedIn");
		assumeThat(parentTrans, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefTrans = extendingReg.getTransition("nestedIn");
		assumeThat(redefTrans, notNullValue());
		assumeThat(redefTrans, isInherited());

		OpaqueBehavior parent = (OpaqueBehavior) parentTrans.getEffect();
		assumeThat("Effect not found", parent, notNullValue());

		OpaqueBehavior redefining = (OpaqueBehavior) redefTrans.getEffect();
		assumeThat("Inherited effect not found", redefining, notNullValue());

		assumeThat(redefining, isInherited());
		assumeThat(redefining, redefines(parent));

		assumeThat(redefining.getBodies(), hasItem(anything()));

		String originalBody = redefining.getBodies().get(0);
		redefining.getBodies().set(0, "pass();");

		assumeThat("Effect not redefined", redefining, isRedefined());

		reinherit(redefining);

		assertThat("Effect not reinherited", redefining, isInherited());

		assertThat(redefining.getBodies(), is(Arrays.asList(originalBody)));
	}

	@Test
	public void newTriggerGuardInherited() {
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

		Trigger trigger = parentTrans.createTrigger("newTrigger");
		Constraint parent = parentTrans.createOwnedRule(null);
		parent.getConstrainedElements().add(trigger);
		StereotypeApplicationHelper.getInstance(parent).applyStereotype(parent, UMLRTStateMachinesPackage.Literals.RT_GUARD);

		Constraint redefining = getGuard(redefTrans, trigger);
		assertThat("Inherited trigger guard not found", redefining, notNullValue());

		assertThat(redefining, isInherited());
		assertThat(redefining, redefines(parent));
	}

	/**
	 * Verify that the redefinition relationship of a redefining transition guard
	 * is inferred from the model's persisted state, which doesn't actually have
	 * any explicit redefined-element reference.
	 */
	@TestModel("inheritance/guards.uml")
	@Test
	public void transitionGuardPersistence() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parentTrans = parentReg.getTransition("next");
		assumeThat(parentTrans, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefTrans = extendingReg.getTransition("next");
		assumeThat(redefTrans, notNullValue());

		assumeThat(redefTrans, isRedefined());
		assumeThat(redefTrans, UMLMatchers.redefines(parentTrans));

		Constraint parent = parentTrans.getGuard();
		assumeThat(parent, notNullValue());

		Constraint redefining = redefTrans.getGuard();
		assumeThat(redefining, notNullValue());

		assertThat("Guard redefinition status not inferred", redefTrans, isRedefined());
		assertThat("Guard redefinition relationship not inferred", redefTrans,
				UMLMatchers.redefines(parentTrans));
	}

	/**
	 * Verify that the redefinition relationship of a redefining trigger guard
	 * is inferred from the model's persisted state, which doesn't actually have
	 * any explicit redefined-element reference.
	 */
	@TestModel("inheritance/guards.uml")
	@Test
	public void triggerGuardPersistence() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parentTrans = parentReg.getTransition("initial");
		assumeThat(parentTrans, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefTrans = extendingReg.getTransition("initial");
		assumeThat(redefTrans, notNullValue());

		assumeThat(redefTrans, isRedefined());
		assumeThat(redefTrans, UMLMatchers.redefines(parentTrans));

		Trigger parentTrigger = parentTrans.getTriggers().get(0);
		Constraint parent = getGuard(parentTrans, parentTrigger);
		assumeThat(parent, notNullValue());

		// The inherited trigger is not persisted because it isn't redefined
		Trigger redefTrigger = UMLRTExtensionUtil.<Trigger> getUMLRTContents(redefTrans, UMLPackage.Literals.TRANSITION__TRIGGER).get(0);
		Constraint redefining = getGuard(redefTrans, redefTrigger);
		assumeThat(redefining, notNullValue());

		assertThat("Trigger guard redefinition status not inferred", redefining, isRedefined());
		assertThat("Trigger guard redefinition relationship not inferred", redefining,
				UMLMatchers.redefines(parent));
	}

	@TestModel("inheritance/guards.uml")
	@Test
	public void triggerPortsInheritance() {
		StateMachine extendingSM = getSubcapsuleBehavior();

		Port port = ((EncapsulatedClassifier) extendingSM.getContext()).getOwnedPort("protocol1", null);
		assumeThat(port, notNullValue()); // This port is inherited (a virtual redefinition)
		assumeThat(port, isInherited());

		Region extendingReg = extendingSM.getRegion("Region");
		assumeThat(extendingReg, notNullValue());

		Transition redefTrans = extendingReg.getTransition("initial");
		assumeThat(redefTrans, notNullValue());

		Trigger trigger = redefTrans.createTrigger(null);

		List<Port> ports = trigger.getPorts();
		ports.add(port);

		// The normal view of the ports list resolves to the inherited element
		assertThat(ports.contains(port), is(true));
		assertThat(ports.indexOf(port), is(0));
		assertThat(ports.get(0), is(port));
		assertThat(ports.iterator().next(), is(port));
		assertThat(ports.listIterator().next(), is(port));
		assertThat(ports.listIterator(1).previous(), is(port));
		assertThat(ports, is(Arrays.asList(port)));
		assertThat(ports.toArray(), is(new Object[] { port }));
		assertThat(ports.toArray(new Port[1]), is(new Port[] { port }));
		assertThat(ports.toArray(new Port[0]), is(new Port[] { port }));
		assertThat(ports.toArray(new Port[] { null, port }), is(new Port[] { port, null }));

		ports.remove(port);
		assertThat(ports, not(hasItem(anything())));
	}

	/**
	 * Verify that a trigger added to a redefined transition correctly references
	 * its port as the root definition, not the (possibly virtual) redefinition.
	 */
	@TestModel("inheritance/guards.uml")
	@Test
	public void triggerPortPersistence() {
		StateMachine extendingSM = getSubcapsuleBehavior();

		Port port = ((EncapsulatedClassifier) extendingSM.getContext()).getOwnedPort("protocol1", null);
		assumeThat(port, notNullValue()); // This port is inherited (a virtual redefinition)
		assumeThat(port, isInherited());
		Port rootPort = UMLRTExtensionUtil.getRootDefinition(port);
		assumeThat(rootPort, notNullValue());

		Region extendingReg = extendingSM.getRegion("Region");
		assumeThat(extendingReg, notNullValue());

		Transition redefTrans = extendingReg.getTransition("initial");
		assumeThat(redefTrans, notNullValue());

		Trigger trigger = redefTrans.createTrigger(null);
		trigger.getPorts().add(port);

		InternalEList<Port> ports = (InternalEList<Port>) trigger.getPorts();

		// Assume the inherited view of the ports list
		assumeThat(ports.contains(port), is(true));
		assumeThat(ports.indexOf(port), is(0));
		assumeThat(ports.get(0), is(port));
		assumeThat(ports, is(Arrays.asList(port)));

		assertThat("Trigger port persisted as virtual element reference (basicGet)",
				ports.basicGet(0), is(rootPort));
		assertThat("Trigger port persisted as virtual element reference (basicList)",
				ports.basicList(), is(Arrays.asList(rootPort)));
	}

	/**
	 * Test that persistent data not lost when undoing the reinherit of a redefined trigger guard.
	 * 
	 * @see <a href="http://eclip.se/514639">bug 514639</a>
	 */
	@Test
	public void undoReinheritRedefinedTriggerGuard() {
		Pair<Constraint> guards = getTriggerGuards("Initial", "greet");
		Constraint parent = guards.get(1);
		Constraint redefining = guards.get(0);

		assumeThat(redefining.eIsSet(UMLPackage.Literals.CONSTRAINT__CONSTRAINED_ELEMENT), is(false));

		assumeThat(redefining.getSpecification(), instanceOf(OpaqueExpression.class));
		OpaqueExpression spec = (OpaqueExpression) redefining.getSpecification();
		assumeThat(spec.getBodies(), hasItem(anything()));

		spec.getBodies().set(0, "true");

		assumeThat("Trigger guard not redefined", redefining, isRedefined());

		assumeThat(((InternalEList<Element>) redefining.getConstrainedElements()).basicList(),
				is(parent.getConstrainedElements()));

		// This needs to be set so that it will be persisted in the model (it determines
		// which trigger this constraint guards)
		assertThat(redefining.eIsSet(UMLPackage.Literals.CONSTRAINT__CONSTRAINED_ELEMENT), is(true));

		ChangeDescription change = fixture.record(() -> reinherit(redefining));

		assertThat("Trigger guard was not re-inherited", redefining, isInherited());

		// This should be unset again
		assertThat(redefining.eIsSet(UMLPackage.Literals.CONSTRAINT__CONSTRAINED_ELEMENT), is(false));

		// We still correctly reference the trigger
		assertThat(redefining.getConstrainedElements(), is(parent.getConstrainedElements()));

		// Undo
		fixture.undo(change);

		assertThat("Trigger guard is not redefined again", redefining, isRedefined());

		// We (still) correctly reference the parent trigger
		assertThat(((InternalEList<Element>) redefining.getConstrainedElements()).basicList(),
				is(parent.getConstrainedElements()));

		// And this needs to support persistence
		assertThat(redefining.eIsSet(UMLPackage.Literals.CONSTRAINT__CONSTRAINED_ELEMENT), is(true));
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

	/**
	 * Gets the redefining and parent transitions for the test as a pair.
	 * 
	 * @param name
	 *            the transition name
	 * @return the pair of (redefinition, parent) transitions
	 */
	Pair<Transition> getTransitions(String name) {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		Transition parentTransition = parentReg.getTransition(name);
		assumeThat("Parent transition not found", parentTransition, notNullValue());

		assumeThat(extendingReg, notNullValue());

		Transition redefTransition = extendingReg.getTransition(name);
		assumeThat("Inherited transition not found", redefTransition, notNullValue());
		assumeThat(redefTransition, isInherited());

		return Pair.of(redefTransition, parentTransition);
	}

	Pair<Trigger> getTriggers(String transitionName, String triggerName) {
		Pair<Transition> transitions = getTransitions(transitionName);
		Trigger parentTrigger = transitions.get(1).getTrigger(triggerName);
		assumeThat("Parent trigger not found", parentTrigger, notNullValue());
		Trigger redefTrigger = transitions.get(0).getTrigger(triggerName);
		assumeThat("Inherited trigger not found", redefTrigger, notNullValue());

		assumeThat(redefTrigger, isInherited());
		assumeThat(redefTrigger, redefines(parentTrigger));

		return Pair.of(redefTrigger, parentTrigger);
	}

	Pair<Constraint> getTriggerGuards(String transitionName, String triggerName) {
		Pair<Transition> transitions = getTransitions(transitionName);
		Trigger parentTrigger = transitions.get(1).getTrigger(triggerName);
		assumeThat("Parent trigger not found", parentTrigger, notNullValue());
		Trigger redefTrigger = transitions.get(0).getTrigger(triggerName);
		assumeThat("Inherited trigger not found", redefTrigger, notNullValue());
		Constraint parentGuard = getGuard(transitions.get(1), parentTrigger);
		assumeThat("Parent guard not found", parentGuard, notNullValue());
		Constraint redefGuard = getGuard(transitions.get(0), redefTrigger);
		assumeThat("Inherited guard not found", redefGuard, notNullValue());

		assumeThat(redefGuard, isInherited());
		assumeThat(redefGuard, redefines(parentGuard));

		return Pair.of(redefGuard, parentGuard);
	}

	Constraint getGuard(Transition transition, Trigger trigger) {
		return UMLRTExtensionUtil.<Constraint> getUMLRTContents(transition, UMLPackage.Literals.NAMESPACE__OWNED_RULE)
				.stream()
				.filter(r -> UMLUtil.getStereotypeApplication(r, RTGuard.class) != null)
				.filter(r -> r.getConstrainedElements().contains(trigger)
						|| r.getConstrainedElements().contains(UMLRTExtensionUtil.getRootDefinition(trigger)))
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

	void reinherit(Element element) {
		assumeThat("Element is not re-inheritable", element, instanceOf(InternalUMLRTElement.class));
		assertThat("Element is not re-inheritable", ((InternalUMLRTElement) element).rtReinherit(), is(true));
	}
}
