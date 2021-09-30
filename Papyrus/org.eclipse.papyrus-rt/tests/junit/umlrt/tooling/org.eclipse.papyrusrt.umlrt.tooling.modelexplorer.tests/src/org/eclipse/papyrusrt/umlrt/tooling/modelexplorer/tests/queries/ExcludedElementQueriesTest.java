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

package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.tests.queries;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.GetUMLRTElementsQuery;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.GetUMLRTSingleElementQuery;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Trigger;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Specific test cases for queries accessing excluded elements.
 */
@PluginResource("resource/inheritance/exclude_trigger.di")
@RunWith(Parameterized.class)
public class ExcludedElementQueriesTest extends AbstractRTQueryTest<NamedElement> {

	private final QueryKind kind;

	public ExcludedElementQueriesTest(QueryKind kind) throws Exception {
		super(kind.getQuery());

		this.kind = kind;
	}

	/**
	 * Verify that children of excluded elements are never shown, even when
	 * the inherited-elements facet is engaged.
	 * 
	 * @see <a href="http://eclip.se/513178">bug 513178</a>
	 */
	@Test
	public void childrenOfExcludedElementsNotShown() {
		NamedElement element = getTransition("Capsule2", "State1", "State2");
		negativeTestTemplate(element, kind.rtMatcher(), true, true, kind.getFeature());
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}")
	public static Iterable<Object[]> parameters() {
		return Stream.of(QueryKind.values())
				.map(v -> new Object[] { v })
				.collect(Collectors.toList());
	}

	enum QueryKind {
		SINGLE_CHILD, MULTIPLE_CHILDREN;

		Predicate<Object> umlFilter() {
			Predicate<Object> result;

			switch (this) {
			case SINGLE_CHILD:
				result = OpaqueBehavior.class::isInstance;
				break;
			case MULTIPLE_CHILDREN:
				result = Trigger.class::isInstance;
				break;
			default:
				throw new AssertionError(this);
			}

			return result;
		}

		Predicate<Object> rtFilter() {
			Predicate<Object> result = umlFilter();

			return result;
		}

		Matcher<Object> umlMatcher() {
			return new BaseMatcher<Object>() {
				@Override
				public void describeTo(Description description) {
					description.appendText("matches UML " + QueryKind.this);
				}

				@Override
				public boolean matches(Object item) {
					return umlFilter().test(item);
				}
			};
		}

		Matcher<Object> rtMatcher() {
			return new BaseMatcher<Object>() {
				@Override
				public void describeTo(Description description) {
					description.appendText("matches RT " + QueryKind.this);
				}

				@Override
				public boolean matches(Object item) {
					return rtFilter().test(item);
				}
			};
		}

		IJavaQuery2<? extends NamedElement, ?> getQuery() {
			switch (this) {
			case SINGLE_CHILD:
				return new GetUMLRTSingleElementQuery();
			case MULTIPLE_CHILDREN:
				return new GetUMLRTElementsQuery();
			default:
				throw new AssertionError(this);
			}
		}

		@SuppressWarnings("restriction")
		EStructuralFeature getFeature() {
			switch (this) {
			case SINGLE_CHILD:
				return org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage.Literals.TRANSITION__EFFECT;
			case MULTIPLE_CHILDREN:
				return org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage.Literals.TRANSITION__TRIGGER;
			default:
				throw new AssertionError(this);
			}

		}

		@Override
		public String toString() {
			return name().toLowerCase().replace('_', ' ');
		}
	}
}
