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

package org.eclipse.papyrusrt.umlrt.core.tests.edition;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.core.commands.ExclusionCommand;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.uml2.uml.NamedElement;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.google.common.collect.Lists;

/**
 * Test cases for the exclusion and re-inheritance support in the edit-helper
 * advice for state machine elements.
 */
@PluginResource("resource/inheritance/statemachines.di")
public class StateMachineExclusionTest {

	@ClassRule
	public static final TestRule elementTypes = new ElementTypesRule();

	@Rule
	public final UMLRTModelSetFixture modelSet = new UMLRTModelSetFixture();

	public StateMachineExclusionTest() {
		super();
	}

	@Test
	public void excludeTransition() {
		UMLRTTransition transition = getStateMachine().getTransition("Initial");
		UMLRTVertex source = transition.getSource();
		UMLRTVertex target = transition.getTarget();

		exclude(transition);

		assertThat(transition, isExcluded());
		assertThat(source, notExcluded());
		assertThat(target, notExcluded());
	}

	@Test
	public void excludeState() {
		UMLRTTransition transition = getStateMachine().getTransition("Initial");
		UMLRTVertex source = transition.getSource();
		UMLRTVertex target = transition.getTarget();

		exclude(target); // It's a state and so excludeable

		assertThat(target, isExcluded());
		assertThat("Dependent transition not excluded", transition, isExcluded());
		assertThat(source, notExcluded());
	}

	@Test
	public void reinheritTransition() {
		UMLRTTransition transition = getStateMachine().getTransition("Initial");
		UMLRTVertex source = transition.getSource();
		UMLRTVertex target = transition.getTarget();

		exclude(transition);

		assumeThat(transition, isExcluded());

		reinherit(transition);

		assertThat(transition, notExcluded());
		assertThat(source, notExcluded());
		assertThat(target, notExcluded());
	}

	@Test
	public void reinheritTransitionWithDependents() {
		UMLRTTransition transition = getStateMachine().getTransition("Initial");
		UMLRTVertex source = transition.getSource();
		UMLRTVertex target = transition.getTarget();

		exclude(transition, target);

		assumeThat(Arrays.asList(transition, target), everyItem(isExcluded()));

		reinherit(transition);

		assertThat(transition, notExcluded());
		assertThat(source, notExcluded()); // This wasn't excluded, anyways
		assertThat(target, notExcluded()); // This is required
	}

	@Test
	public void reinheritState() {
		UMLRTTransition transition = getStateMachine().getTransition("Initial");
		UMLRTVertex source = transition.getSource();
		UMLRTVertex target = transition.getTarget();

		exclude(target);

		assumeThat(target, isExcluded());

		reinherit(target);

		assertThat(target, notExcluded());
		assertThat(transition, isExcluded()); // This is not required
		assertThat(source, notExcluded()); // Wasn't excluded, anyways
	}

	@Test
	public void excludeCompositeState() {
		UMLRTTransition transition = getStateMachine().getTransition("out");
		UMLRTConnectionPoint source = (UMLRTConnectionPoint) transition.getSource();
		UMLRTVertex target = transition.getTarget();
		UMLRTState composite = source.getState();

		exclude(composite);

		assumeThat(composite, isExcluded());

		assertThat(transition, isExcluded()); // Its source was transitively excluded
		assertThat(target, notExcluded()); // This is unaffected
	}

	//
	// Test framework
	//

	UMLRTPackage getRoot() {
		return UMLRTPackage.getInstance(modelSet.getModel());
	}

	/** Get the subtype (inheriting) state machine. */
	UMLRTStateMachine getStateMachine() {
		return getCapsule().getStateMachine();
	}

	/** Get the subtype (inheriting) capsule. */
	UMLRTCapsule getCapsule() {
		return getCapsule("Subcapsule");
	}

	UMLRTCapsule getCapsule(String name) {
		return getRoot().getCapsule(name);
	}

	void exclude(UMLRTNamedElement element) {
		ICommand exclude = ExclusionCommand.getExclusionCommand(element.toUML(), true);
		assertThat(exclude, isExecutable());
		modelSet.execute(exclude);
	}

	void exclude(UMLRTNamedElement element, UMLRTNamedElement element2, UMLRTNamedElement... rest) {
		List<NamedElement> elements = Lists.asList(element, element2, rest).stream()
				.map(UMLRTNamedElement::toUML)
				.collect(Collectors.toList());
		ICommand exclude = ExclusionCommand.getExclusionCommand(elements, true);
		assertThat(exclude, isExecutable());
		modelSet.execute(exclude);
	}

	void reinherit(UMLRTNamedElement element) {
		ICommand reinherit = ExclusionCommand.getExclusionCommand(element.toUML(), false);
		assertThat(reinherit, isExecutable());
		modelSet.execute(reinherit);
	}

	void reinherit(UMLRTNamedElement element, UMLRTNamedElement element2, UMLRTNamedElement... rest) {
		List<NamedElement> elements = Lists.asList(element, element2, rest).stream()
				.map(UMLRTNamedElement::toUML)
				.collect(Collectors.toList());
		ICommand reinherit = ExclusionCommand.getExclusionCommand(elements, false);
		assertThat(reinherit, isExecutable());
		modelSet.execute(reinherit);
	}

	Matcher<UMLRTNamedElement> isExcluded() {
		return isExcluded(true);
	}

	Matcher<UMLRTNamedElement> notExcluded() {
		return isExcluded(false);
	}

	Matcher<UMLRTNamedElement> isExcluded(boolean expected) {
		return new BaseMatcher<UMLRTNamedElement>() {
			@Override
			public void describeTo(Description description) {
				description.appendText(expected ? "is excluded" : "not excluded");
			}

			@Override
			public void describeMismatch(Object item, Description description) {
				if (item == null) {
					super.describeMismatch(item, description);
				} else {
					description.appendText(expected ? "was not excluded" : "was excluded");
				}
			}

			@Override
			public boolean matches(Object item) {
				boolean result = false;

				if (item instanceof UMLRTNamedElement) {
					UMLRTNamedElement element = (UMLRTNamedElement) item;
					result = element.isExcluded() == expected;
				}

				return result;
			}
		};
	}
}
