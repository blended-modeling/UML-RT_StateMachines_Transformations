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
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EReference;
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
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Inheritance tests for the UML-RT implementation of the {@link OpaqueBehavior}.
 */
@TestModel("inheritance/statemachines.uml")
@NoFacade
@Category(StateMachineTests.class)
public class OpaqueBehaviorTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public OpaqueBehaviorTest() {
		super();
	}

	@Test
	public void nameInherited() {
		Pair<State> states = getStates();
		Pair<OpaqueBehavior> entries = getEntry(states);
		OpaqueBehavior redefining = entries.get(0);
		OpaqueBehavior parent = entries.get(1);

		parent.setName("The_Entry");
		assertThat(redefining.getName(), is("The_Entry"));
		assertThat(redefining.isSetName(), is(false));
	}

	@Test
	public void nameRedefined() {
		Pair<State> states = getStates();
		Pair<OpaqueBehavior> entries = getEntry(states);
		OpaqueBehavior redefining = entries.get(0);
		OpaqueBehavior parent = entries.get(1);

		redefining.setName("Override");

		parent.setName("The_Entry");
		assertThat(redefining.getName(), is("Override"));
		assertThat(redefining.isSetName(), is(true));
	}

	@Test
	public void excludedBehaviorName() {
		Pair<State> states = getStates();
		Pair<OpaqueBehavior> entries = getEntry(states);
		OpaqueBehavior redefining = entries.get(0);
		OpaqueBehavior parent = entries.get(1);
		parent.setName("The_Entry");

		redefining.setName("Override");

		exclude(redefining);

		assertThat(redefining.getName(), is("The_Entry"));
		assertThat(redefining.isSetName(), is(false));

		reinherit(redefining);

		assertThat(redefining.getName(), is("The_Entry"));
		assertThat(redefining.isSetName(), is(false));
	}

	@Test
	public void nameReinherited() {
		Pair<State> states = getStates();
		Pair<OpaqueBehavior> entries = getEntry(states);
		OpaqueBehavior redefining = entries.get(0);
		OpaqueBehavior parent = entries.get(1);

		redefining.setName("Override");

		parent.setName("The_Entry");
		assumeThat(redefining.getName(), is("Override"));
		assumeThat(redefining.isSetName(), is(true));

		redefining.unsetName();
		assertThat(redefining.getName(), is("The_Entry"));
		assertThat(redefining.isSetName(), is(false));
	}

	@Test
	public void excludedBehaviorBody() {
		Pair<State> states = getStates();
		Pair<OpaqueBehavior> entries = getEntry(states);
		OpaqueBehavior redefining = entries.get(0);

		List<String> expected = Collections.singletonList("this.enter();");
		assumeThat(redefining.getBodies(), is(expected));

		redefining.getBodies().set(0, "pass();");
		assertThat(redefining.getBodies(), not(expected));

		exclude(redefining);

		assertThat(redefining.getBodies(), is(expected));
		assertThat(redefining.eIsSet(UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY), is(false));

		reinherit(redefining);

		assertThat(redefining.getBodies(), is(expected));
		assertThat(redefining.eIsSet(UMLPackage.Literals.OPAQUE_BEHAVIOR__LANGUAGE), is(false));
	}

	@Test
	public void excludedBehaviorLanguage() {
		Pair<State> states = getStates();
		Pair<OpaqueBehavior> entries = getEntry(states);
		OpaqueBehavior redefining = entries.get(0);

		List<String> expected = Collections.singletonList("C++");
		assumeThat(redefining.getLanguages(), is(expected));

		redefining.getLanguages().set(0, "Java");
		assertThat(redefining.getLanguages(), not(expected));

		exclude(redefining);

		assertThat(redefining.getLanguages(), is(expected));
		assertThat(redefining.eIsSet(UMLPackage.Literals.OPAQUE_BEHAVIOR__LANGUAGE), is(false));
	}

	/**
	 * Regression test for undo/redo reinheritance of a chain of redefinitions
	 * of body expressions of opaque behaviors.
	 * 
	 * @see <a href="http://eclip.se/514454">bug 514454</a>
	 */
	@TestModel("inheritance/MultipleBodyTest.uml")
	@Test
	public void reinheritRedefinitionChain() {
		OpaqueBehavior root = getEffect("Capsule1", "t1");
		OpaqueBehavior sub1 = getEffect("Capsule2", "t1");
		OpaqueBehavior sub2 = getEffect("Capsule3", "t1");
		OpaqueBehavior sub3 = getEffect("Capsule4", "t1");

		// Initial conditions
		final String rootDef = "// Effect in Capsule1";
		final String sub1Def = "// Redefined effect in Capsule2";
		final String sub2Def = "// Redefined effect in Capsule3";
		final String sub3Def = "// Redefined effect in Capsule4";
		assumeThat("Root definition not effective", root, specifiedBy(rootDef));
		assumeThat("Redefinition not effective", sub1, specifiedBy(sub1Def));
		assumeThat("Redefinition not effective", sub2, specifiedBy(sub2Def));
		assumeThat("Redefinition not effective", sub3, specifiedBy(sub3Def));

		// Re-inherit them all
		fixture.record(() -> reinherit(sub3)); // Make sure that the recorder gets ...
		fixture.record(() -> reinherit(sub2)); // ... both of these elements
		ChangeDescription change = fixture.record(() -> reinherit(sub1));
		assertThat("Re-inherit did not inherit body", sub1, specifiedBy(rootDef));
		assertThat("Re-inherit did not inherit body", sub2, specifiedBy(rootDef));
		assertThat("Re-inherit did not inherit body", sub3, specifiedBy(rootDef));
		assertThat("Re-inherit failed", sub1, isInherited());
		assertThat("Re-inherit failed", sub2, isInherited());
		assertThat("Re-inherit failed", sub3, isInherited());

		fixture.repeat(3, () -> {
			// Undo the re-inherit of the first redefinition
			fixture.undo(change);

			assertThat("Undo did not restore redefined body", sub1, specifiedBy(sub1Def));
			assertThat("Undo did not inherit redefined body", sub2, specifiedBy(sub1Def));
			assertThat("Undo did not inherit redefined body", sub3, specifiedBy(sub1Def));
			assertThat("Undo re-inherit failed", sub1, isRedefined());
			assertThat("Undo re-inherit modified inherited element", sub2, isInherited());
			assertThat("Undo re-inherit modified inherited element", sub3, isInherited());

			// Redo the re-inherit of the first redefinition
			fixture.undo(change);
			assertThat("Redo re-inherit did not inherit body", sub1, specifiedBy(rootDef));
			assertThat("Redo re-inherit did not inherit body", sub2, specifiedBy(rootDef));
			assertThat("Redo re-inherit did not inherit body", sub3, specifiedBy(rootDef));
			assertThat("Redo reinherit failed", sub1, isInherited());
			assertThat("Redo reinherit modified inherited element", sub2, isInherited());
			assertThat("Redo reinherit modified inherited element", sub3, isInherited());
		});
	}

	/**
	 * Regression test for undo/redo reinheritance of an opaque behaviour appending
	 * a body expression each time.
	 * 
	 * @see <a href="http://eclip.se/514454">bug 514454</a>
	 */
	@TestModel("inheritance/UndoRedoOpaqueBehaviorTest.uml")
	@Test
	public void reinheritUndoRedoMaintainsSingleBody() {
		OpaqueBehavior entry = getEntry("Capsule2", "State1");

		// Initial conditions
		final String rootDef = "// Entry code";
		final String entryDef = "// Redefined entry code";
		assumeThat("Redefinition not effective", entry, specifiedBy(entryDef));

		// Re-inherit it
		ChangeDescription change = fixture.record(() -> reinherit(entry));
		assertThat("Re-inherit did not inherit body", entry, specifiedBy(rootDef));
		assertThat("Re-inherit added or removed bodies", entry.getBodies(), is(Arrays.asList(rootDef)));
		assertThat("Body attribute not unset", entry.eIsSet(UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY), is(false));

		fixture.repeat(3, () -> {
			ChangeDescription change_ = change;

			// Undo the re-inherit of the redefinition
			fixture.undo(change_);

			assertThat("Undo re-inherit did not restore redefined body", entry, specifiedBy(entryDef));
			assertThat("Undo re-inherit added or removed bodies", entry.getBodies(), is(Arrays.asList(entryDef)));
			assertThat("Body attribute not set", entry.eIsSet(UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY), is(true));

			// Redo the re-inherit of the redefinition
			fixture.undo(change_);
			assertThat("Redo re-inherit did not inherit body", entry, specifiedBy(rootDef));
			assertThat("Redo re-inherit added or removed bodies", entry.getBodies(), is(Arrays.asList(rootDef)));
			assertThat("Body attribute not unset", entry.eIsSet(UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY), is(false));
		});
	}

	/**
	 * Regression test for undo/redo redefinition of a state entry behavior.
	 * 
	 * @see <a href="http://eclip.se/512994">bug 512994</a>
	 */
	@TestModel("inheritance/UndoRedefineOpaqueBehaviorTest.uml")
	@Test
	public void undoRedoRedefineStateEntry() {
		undoRedoRedefineStateEntry((entry, body) -> entry.getBodies().set(0, body));
	}

	void undoRedoRedefineStateEntry(BiConsumer<? super OpaqueBehavior, ? super String> editAction) {
		OpaqueBehavior entry = getEntry("Capsule2", "State1");

		// Initial conditions
		final String rootDef = "// Entry code in Capsule1";
		final String entryDef = "// Redefined entry code";
		assumeThat("Entry code not inherited", entry, specifiedBy(rootDef));
		assumeThat("Body attribute not unset", entry.eIsSet(UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY), is(false));

		// Redefine it
		ChangeDescription change = fixture.record(() -> editAction.accept(entry, entryDef));
		assertThat("Redefinition not effective", entry, specifiedBy(entryDef));

		fixture.repeat(3, () -> {
			ChangeDescription change_ = change;

			// Undo the redefinition
			fixture.undo(change_);

			assertThat("Entry code not inherited", entry, specifiedBy(rootDef));
			assertThat("Body attribute not unset", entry.eIsSet(UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY), is(false));

			// Redo the redefinition
			fixture.undo(change_);
			assertThat("Redefiition not restored", entry, specifiedBy(entryDef));
			assertThat("Body attribute not set", entry.eIsSet(UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY), is(true));
		});
	}

	/**
	 * Regression test for undo/redo redefinition of a state entry behavior using the
	 * algorithm employed by the UI's {@code ExpressionList} API that destroys the
	 * entirety of {@code language} and {@code body} lists and then builds them
	 * up again from scratch.
	 * 
	 * @see <a href="http://eclip.se/512994">bug 512994</a>
	 */
	@TestModel("inheritance/UndoRedefineOpaqueBehaviorTest.uml")
	@Test
	public void undoRedoRedefineStateEntryTheUIWay() {
		undoRedoRedefineStateEntry((entry, body) -> {
			entry.getLanguages().clear();
			entry.getBodies().clear();
			entry.getLanguages().add("C++");
			entry.getBodies().add(body);
		});
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
	 * Gets the entry behavior of each of a pair of {@code states}.
	 * 
	 * @param states
	 *            a pair of states
	 * @return the pair of (redefinition, parent) entry behaviors
	 */
	Pair<OpaqueBehavior> getEntry(Pair<? extends State> states) {
		return getBehavior(states, UMLPackage.Literals.STATE__ENTRY);
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

	OpaqueBehavior getEffect(String capsuleName, String transitionName) {
		return (OpaqueBehavior) fixture.getElement(
				String.format("%s::StateMachine::Region::%s", capsuleName, transitionName), Transition.class)
				.getEffect();
	}

	OpaqueBehavior getEntry(String capsuleName, String stateName) {
		return (OpaqueBehavior) fixture.getElement(
				String.format("%s::StateMachine::Region::%s", capsuleName, stateName), State.class)
				.getEntry();
	}

	void reinherit(Element element) {
		assumeThat("Element is not re-inheritable", element, instanceOf(InternalUMLRTElement.class));
		assertThat("Element is not re-inheritable", ((InternalUMLRTElement) element).rtReinherit(), is(true));
	}

	void exclude(Element element) {
		assumeThat("Element is not excludeable", element, instanceOf(InternalUMLRTElement.class));
		assertThat("Element is not excludeable", ((InternalUMLRTElement) element).rtExclude(), is(true));
	}

	Matcher<OpaqueBehavior> specifiedBy(String body) {
		return specifiedBy("C++", body);
	}

	Matcher<OpaqueBehavior> specifiedBy(String language, String body) {
		return new CustomTypeSafeMatcher<OpaqueBehavior>(String.format("is '%s' (%s)", body, language)) {
			@Override
			protected boolean matchesSafely(OpaqueBehavior item) {
				int index = item.getLanguages().indexOf(language);
				return (index >= 0) && (item.getBodies().size() > index) && body.equals(item.getBodies().get(index));
			}
		};
	}
}
