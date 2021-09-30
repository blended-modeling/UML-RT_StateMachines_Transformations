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

package org.eclipse.papyrusrt.umlrt.tooling.properties.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;
import org.eclipse.papyrus.infra.constraints.runtime.ConstraintEngine;
import org.eclipse.papyrus.infra.constraints.runtime.ConstraintFactory;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.Tab;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Regression tests for the visibility criteria of views and/or sections
 * in the UML-RT Properties Context.
 */
@PluginResource({ "resource/PingPongModel.di", "resource/pure-uml.di" })
@RunWith(Parameterized.class)
public class VisibilityConstraintTest {

	@Rule
	public final UMLRTModelSetFixture model = new UMLRTModelSetFixture();

	@Parameter(0)
	public String elementQualifiedName;

	@Parameter(1)
	public String sectionQualifiedName;

	@Parameter(2)
	public boolean shouldSectionBePresented;

	public VisibilityConstraintTest() {
		super();
	}

	@Test
	public void sectionVisibility() {
		ConstraintEngine<View> constraints = PropertiesRuntime.getConstraintEngine();

		NamedElement fixture = getFixtureModelElement();

		Set<View> viewsToDisplay = constraints.getDisplayUnits(fixture);
		Set<Section> sections = viewsToDisplay.stream()
				.flatMap(view -> view.getSections().stream())
				.filter(section -> asPerXWTSection(section, fixture))
				.collect(Collectors.toSet());

		Section expectedSection = getFixtureSection();

		if (shouldSectionBePresented) {
			assertThat("Section not presented", sections, hasItem(expectedSection));
		} else {
			assertThat("Section is presented", sections, not(hasItem(expectedSection)));
		}
	}

	//
	// Test framework
	//

	@Parameters(name = "{1} — {0}({2})")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ "PingPongModel", "uml-rt::umlrealtime::Single Package", true },
				{ "PingPongModel", "uml-rt::umlrealtime_language::Single Package Language", true },
				{ "PingPongModel::types", "uml-rt::umlrealtime::Single Package", true },
				{ "PingPongModel::types", "uml-rt::umlrealtime_language::Single Package Language", false },
				{ "pure-uml", "uml-rt::umlrealtime::Single Package", false },
				{ "pure-uml", "uml-rt::umlrealtime_language::Single Package Language", false },
				{ "PingPongModel::Controllable::StateMachine::Region::activate::onActivate", "uml-rt::umlrealtime::Single RTTrigger", true },
				{ "pure-uml::Class1::lifecycle::Region1::go::onGo", "uml-rt::umlrealtime::Single RTTrigger", false },
		});
	}

	NamedElement getFixtureModelElement() {
		Collection<NamedElement> result = UMLUtil.findNamedElements(
				model.getResourceSet(),
				elementQualifiedName);
		assertThat("No such element: " + elementQualifiedName, result, not(isEmpty()));
		return result.iterator().next();
	}

	Section getFixtureSection() {
		String[] nameParts = sectionQualifiedName.split("::");
		assertThat("Invalid section name", nameParts.length, is(3));
		String ctxName = nameParts[0];
		String tabName = nameParts[1];
		String secName = nameParts[2];

		Context ctx = PropertiesRuntime.getConfigurationManager().getContext(ctxName);
		assertThat("No such context: " + ctxName, ctx, notNullValue());

		Optional<Tab> tab = ctx.getTabs().stream()
				.filter(t -> tabName.equals(t.getId()) || tabName.equals(t.getLabel()))
				.findAny();
		Optional<Section> result = tab.map(t -> t.getSections())
				.map(List::stream)
				.map(s -> s.filter(sec -> secName.equals(sec.getName())))
				.flatMap(Stream::findAny);

		return result.orElseThrow(() -> new AssertionError("No such section: " + sectionQualifiedName));
	}

	// A predicate that selects sections matching the disjunction
	// of their constraints as in the XWTSection class of Papyrus
	boolean asPerXWTSection(Section section, NamedElement fixture) {
		boolean result = section.getConstraints().isEmpty();

		if (!result) {
			Collection<?> selection = Collections.singleton(fixture);

			for (ConstraintDescriptor constraintDesc : section.getConstraints()) {
				Constraint constraint = ConstraintFactory.getInstance().createFromModel(constraintDesc);
				if (constraint.match(selection)) {
					result = true;
					break;
				}
			}
		}

		return result;
	}
}
