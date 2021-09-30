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
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.stereotypedAs;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.papyrusrt.umlrt.uml.util.Pair;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Various test cases for {@code State} behaviors,
 * including inheritance, redefinition, exclusion, and reinheritance.
 */
@TestModel("inheritance/statemachines.uml")
@NoFacade
@Category(StateMachineTests.class)
public class StateBehaviorsTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public StateBehaviorsTest() {
		super();
	}

	@Test
	public void stateEntryInherited() {
		Pair<State> states = getStates();
		State redefState = states.get(0);

		Pair<OpaqueBehavior> entries = requireBehavior(states, UMLPackage.Literals.STATE__ENTRY);
		OpaqueBehavior redefining = entries.get(0);

		assertThat(redefState.eIsSet(UMLPackage.Literals.STATE__ENTRY), is(false));

		assertThat(redefining.getLanguages(), hasItem("C++"));
		assertThat(redefining.getBodies(), hasItem("this.enter();"));
	}

	@Test
	public void stateEntryRedefined() {
		Pair<State> states = getStates();
		State redefState = states.get(0);

		Pair<OpaqueBehavior> entries = getBehavior(states, UMLPackage.Literals.STATE__ENTRY);
		OpaqueBehavior redefining = entries.get(0);

		redefining.getBodies().set(0, "pass();");

		assertThat(redefining.getBodies(), is(Arrays.asList("pass();")));

		assertThat("Entry not redefined", redefining, isRedefined());
		assertThat("State not redefined", redefState, isRedefined());
		assertThat("Entry not persistent", ((InternalEObject) redefining).eInternalResource(), is(fixture.getResource()));
		assertThat("Body not set", redefining.eIsSet(UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY), is(true));
	}

	@Test
	public void stateEntryExcluded() {
		Pair<State> states = getStates();
		State redefState = states.get(0);

		Pair<OpaqueBehavior> entries = getBehavior(states, UMLPackage.Literals.STATE__ENTRY);
		OpaqueBehavior redefining = entries.get(0);

		ChangeDescription change = fixture.record(() -> exclude(redefining));

		assertThat("Entry not excluded", redefining, isExcluded());
		assertThat("State not redefined", redefState, isRedefined());
		assertThat("Entry not persistent", ((InternalEObject) redefining).eInternalResource(), is(fixture.getResource()));

		fixture.undo(change);

		assertThat("Entry not inherited", redefining, isInherited());
		assertThat("State not inherited", redefState, isInherited());
		assertThat(redefining.getBodies(), hasItem("this.enter();"));
	}

	@Test
	public void stateEntryReinherited() {
		Pair<State> states = getStates();

		Pair<OpaqueBehavior> entries = getBehavior(states, UMLPackage.Literals.STATE__ENTRY);
		OpaqueBehavior redefining = entries.get(0);

		String originalBody = redefining.getBodies().get(0);
		redefining.getBodies().set(0, "pass();");

		assumeThat("Entry not redefined", redefining, isRedefined());

		ChangeDescription change = fixture.record(() -> reinherit(redefining));

		assertThat("Entry not reinherited", redefining, isInherited());
		assertThat(redefining.getBodies(), is(Arrays.asList(originalBody)));

		fixture.undo(change);

		assertThat("Entry not redefined", redefining, isRedefined());
		assertThat(redefining.getBodies(), is(Arrays.asList("pass();")));
	}

	@Test
	public void stateExitInherited() {
		Pair<State> states = getStates();
		State redefState = states.get(0);

		Pair<OpaqueBehavior> exits = getBehavior(states, UMLPackage.Literals.STATE__EXIT);
		OpaqueBehavior redefining = exits.get(0);

		assertThat(redefState.eIsSet(UMLPackage.Literals.STATE__EXIT), is(false));

		assertThat(redefining.getLanguages(), hasItem("C++"));
		assertThat(redefining.getBodies(), hasItem("this.exit();"));
	}

	@Test
	public void stateExitRedefined() {
		Pair<State> states = getStates();
		State redefState = states.get(0);

		Pair<OpaqueBehavior> exits = getBehavior(states, UMLPackage.Literals.STATE__EXIT);
		OpaqueBehavior redefining = exits.get(0);

		redefining.getBodies().set(0, "pass();");

		assertThat(redefining.getBodies(), is(Arrays.asList("pass();")));

		assertThat("Exit not redefined", redefining, isRedefined());
		assertThat("State not redefined", redefState, isRedefined());
		assertThat("Exit not persistent", ((InternalEObject) redefining).eInternalResource(), is(fixture.getResource()));
		assertThat("Body not set", redefining.eIsSet(UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY), is(true));
	}

	@Test
	public void stateExitExcluded() {
		Pair<State> states = getStates();
		State redefState = states.get(0);

		Pair<OpaqueBehavior> exits = getBehavior(states, UMLPackage.Literals.STATE__EXIT);
		OpaqueBehavior redefining = exits.get(0);

		ChangeDescription change = fixture.record(() -> exclude(redefining));

		assertThat("Exit not excluded", redefining, isExcluded());
		assertThat("State not redefined", redefState, isRedefined());
		assertThat("Entry not persistent", ((InternalEObject) redefining).eInternalResource(), is(fixture.getResource()));

		fixture.undo(change);

		assertThat("Exit not inherited", redefining, isInherited());
		assertThat("State not inherited", redefState, isInherited());
		assertThat(redefining.getBodies(), hasItem("this.exit();"));
	}

	@Test
	public void stateExitReinherited() {
		Pair<State> states = getStates();

		Pair<OpaqueBehavior> exits = getBehavior(states, UMLPackage.Literals.STATE__EXIT);
		OpaqueBehavior redefining = exits.get(0);

		String originalBody = redefining.getBodies().get(0);
		redefining.getBodies().set(0, "pass();");

		assumeThat("Exit not redefined", redefining, isRedefined());

		ChangeDescription change = fixture.record(() -> reinherit(redefining));

		assertThat("Exit not reinherited", redefining, isInherited());

		assertThat(redefining.getBodies(), is(Arrays.asList(originalBody)));

		fixture.undo(change);

		assertThat("Exit not redefined", redefining, isRedefined());
		assertThat(redefining.getBodies(), is(Arrays.asList("pass();")));
	}

	@Test
	public void replaceInheritedStateEntry() {
		Pair<State> states = getStates();
		State redefState = states.get(0);

		Pair<OpaqueBehavior> entries = getBehavior(states, UMLPackage.Literals.STATE__ENTRY);
		OpaqueBehavior redefining = entries.get(0);
		OpaqueBehavior parent = entries.get(1);

		// Now, replace the inherited entry

		OpaqueBehavior newEntry = (OpaqueBehavior) redefState.createEntry(null, UMLPackage.Literals.OPAQUE_BEHAVIOR);
		assertThat(newEntry, notNullValue());
		assertThat("Not a new entry behavior", newEntry, not(redefining));

		assertThat(newEntry, isRedefined());
		assertThat(newEntry, redefines(parent));

		// It is a proper redefinition with inheritance
		assertThat(newEntry.getLanguages(), is(parent.getLanguages()));
		assertThat(newEntry.getBodies(), is(parent.getBodies()));
	}

	@Test
	public void replaceInheritedStateExit() {
		Pair<State> states = getStates();
		State redefState = states.get(0);

		Pair<OpaqueBehavior> exits = getBehavior(states, UMLPackage.Literals.STATE__EXIT);
		OpaqueBehavior redefining = exits.get(0);
		OpaqueBehavior parent = exits.get(1);

		// Now, replace the inherited exit

		OpaqueBehavior newExit = (OpaqueBehavior) redefState.createExit(null, UMLPackage.Literals.OPAQUE_BEHAVIOR);
		assertThat(newExit, notNullValue());
		assertThat("Not a new exit behavior", newExit, not(redefining));

		assertThat(newExit, isRedefined());
		assertThat(newExit, redefines(parent));

		// It is a proper redefinition with inheritance
		assertThat(newExit.getLanguages(), is(parent.getLanguages()));
		assertThat(newExit.getBodies(), is(parent.getBodies()));
	}

	@Test
	public void stateEntryReinheritedIndirectlyViaState() {
		Pair<State> states = getStates();
		State redefState = states.get(0);

		Pair<OpaqueBehavior> exits = getBehavior(states, UMLPackage.Literals.STATE__ENTRY);
		OpaqueBehavior redefining = exits.get(0);

		String originalBody = redefining.getBodies().get(0);
		redefining.getBodies().set(0, "pass();");

		assumeThat("Entry not redefined", redefining, isRedefined());
		assumeThat("Entry not stereotyped", redefining, stereotypedAs("UMLRealTime::RTRedefinedElement"));
		assumeThat("State is not redefined", redefState, isRedefined());

		// Re-inherit the
		reinherit(redefState);

		assertThat("Entry not reinherited", redefining, isInherited());
		assertThat(redefining.getBodies(), is(Arrays.asList(originalBody)));

		// The main purpose of this test
		assertThat("Entry is still stereotyped", redefining, not(stereotypedAs("UMLRealTime::RTRedefinedElement")));
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
	 * Gets the redefining and parent states for the test as a pair.
	 * 
	 * @return the pair of (redefinition, parent) states
	 */
	Pair<State> getStates() {
		StateMachine parentSM = getRootCapsuleBehavior();
		StateMachine extendingSM = getSubcapsuleBehavior();

		Region parentReg = parentSM.getRegion("Region");
		Region extendingReg = extendingSM.getRegion("Region");

		assumeThat(parentReg, notNullValue());

		State parentState = (State) parentReg.getSubvertex("State1");
		assumeThat(parentState, notNullValue());

		assumeThat(extendingReg, notNullValue());

		State redefState = (State) extendingReg.getSubvertex("State1");
		assumeThat(redefState, notNullValue());
		assumeThat(redefState, isInherited());

		return Pair.of(redefState, parentState);
	}

	/**
	 * Gets the entry/exit behavior of each of a pair of {@code states}.
	 * 
	 * @param states
	 *            a pair of states
	 * @param reference
	 *            the behavior reference to get
	 * @return the pair of (redefinition, parent) behaviors
	 */
	Pair<OpaqueBehavior> getBehavior(Pair<? extends State> states, EReference reference) {
		OpaqueBehavior parent = (OpaqueBehavior) states.get(1).eGet(reference);
		assumeThat(String.format("%s not found", reference.getName()), parent, notNullValue());

		OpaqueBehavior redefining = (OpaqueBehavior) states.get(0).eGet(reference);
		assumeThat(String.format("Inherited %s not found", reference.getName()), redefining, notNullValue());

		assumeThat(redefining, isInherited());
		assumeThat(redefining, redefines(parent));

		assumeThat(redefining.getBodies(), hasItem(anything()));

		return Pair.of(redefining, parent);
	}

	Pair<OpaqueBehavior> requireBehavior(Pair<? extends State> states, EReference reference) {
		OpaqueBehavior parent = (OpaqueBehavior) states.get(1).eGet(reference);
		assumeThat(String.format("%s not found", reference.getName()), parent, notNullValue());

		OpaqueBehavior redefining = (OpaqueBehavior) states.get(0).eGet(reference);
		assertThat(String.format("Inherited %s not found", reference.getName()), redefining, notNullValue());

		assertThat(redefining, isInherited());
		assertThat(redefining, redefines(parent));

		assertThat(redefining.getBodies(), hasItem(anything()));

		return Pair.of(redefining, parent);
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

	void exclude(Element element) {
		assumeThat("Element is not excludeable", element, instanceOf(InternalUMLRTElement.class));
		assertThat("Element is not excludeable", ((InternalUMLRTElement) element).rtExclude(), is(true));
	}
}
