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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.providers.tests;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Named;

import org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.providers.TypeNameResolutionHelper;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test cases for the {@link TypeNameResolutionHelper}.
 */
@PluginResource({ "resource/content-assist/content-assist.di", "resource/content-assist/clib.uml" })
@RunWith(Parameterized.class)
public class TypeNameResolutionHelperTest {

	@ClassRule
	public static final ModelSetFixture modelSet = new UMLRTModelSetFixture();

	@Rule
	public final HouseKeeper housekeeper = new HouseKeeper();

	@Rule
	public final FixtureElementRule elements = new FixtureElementRule();

	@Named("model::Protocol1::Protocol1::greet::data")
	Parameter dataParameter;

	private final CompletionStrategy strategy;

	public TypeNameResolutionHelperTest(CompletionStrategy strategy) {
		super();

		this.strategy = strategy;
	}

	@Test
	public void completeOnSimpleTypeName() {
		List<Type> completions = complete("uns");

		// From non-imported models
		assertThat(completions, hasItem(named("unsigned char")));
		assertThat(completions, hasItem(named("unsigned int")));

		// From imported models
		assertThat(completions, hasItem(named("unsigned date")));
		assertThat(completions, hasItem(named("unsigned time")));

		// From the same model
		assertThat(completions, hasItem(named("unsurity")));
	}

	@Test
	public void completeOnQualifiedNamePrefixImportedModel() {
		List<Type> completions = complete("Other");

		assertThat(completions, hasItem(named("unsigned date")));
		assertThat(completions, hasItem(named("unsigned time")));
		assertThat(completions, hasItem(named("DateTime")));
	}

	@Test
	public void completeOnQualifiedNamePrefixOtherModel() {
		List<Type> completions = complete("Ansi");

		assertThat(completions, hasItem(named("unsigned char")));
		assertThat(completions, hasItem(named("unsigned int")));
		assertThat(completions, not(hasItem(named("unsurity"))));
	}

	@Test
	public void completeOnQualifiedNamePrefixSameModel() {
		List<Type> completions = complete("mod");

		assertThat(completions, not(hasItem(named("unsigned char"))));
		assertThat(completions, hasItem(named("unsurity")));
	}

	@Test
	public void completeOnIntermediateNamespace() {
		List<Type> completions = complete("typ");

		assertThat(completions, hasItem(named("unsurity")));
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}")
	public static Iterable<Object[]> parameters() {
		return Stream.of(CompletionStrategy.values())
				.map(v -> new Object[] { v })
				.collect(Collectors.toList());
	}

	List<Type> complete(String prefix) {
		switch (strategy) {
		case DIRECT:
			return completeDirectly(prefix);
		case UI_LIKE:
			return completeAsInUI(prefix);
		default:
			throw new AssertionError(strategy);
		}
	}

	List<Type> completeDirectly(String prefix) {
		INameResolutionHelper helper = new TypeNameResolutionHelper(dataParameter);
		return helper.getMatchingElements(prefix).stream()
				.map(Type.class::cast).collect(Collectors.toList());
	}

	List<Type> completeAsInUI(String prefix) {
		// The UI workflows go through a path like this:
		// 1. Get elements matching the user input
		// 2. Convert them to optimally-qualified names
		// 3. Present these names to the user
		// 4. The user chooses one
		// 5. The (partially qualified) name is resolved to elements
		// 6. One of the resolved elements is taken as the value

		INameResolutionHelper helper = new TypeNameResolutionHelper(dataParameter);
		Stream<String> names = helper.getMatchingElements(prefix).stream()
				.flatMap(o -> helper.getShortestQualifiedNames(o, false).stream())
				.distinct();
		return names.flatMap(n -> helper.getElementsByName(n).stream())
				.map(Type.class::cast).collect(Collectors.toList());
	}

	static <N extends NamedElement> Matcher<N> named(String name) {
		return new BaseMatcher<N>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("element named \"").appendText(name).appendText("\"");
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof NamedElement)
						&& Objects.equals(((NamedElement) item).getName(), name);
			}
		};
	}

	enum CompletionStrategy {
		DIRECT, UI_LIKE;

		@Override
		public String toString() {
			return name().toLowerCase().replace('_', '-');
		}
	}
}
