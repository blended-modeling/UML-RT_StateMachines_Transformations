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
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTGuard;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.GetUMLRTSingleElementQuery;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for the family of queries that get a single element via a Façade API feature.
 */
@PluginResource("resource/inheritance/statemachines.di")
@RunWith(Parameterized.class)
public class GetUMLRTSingleElementQueryTest extends AbstractRTQueryTest<NamedElement> {

	private final QueryKind kind;

	public GetUMLRTSingleElementQueryTest(QueryKind kind) throws Exception {
		super(kind.getQuery());

		this.kind = kind;
	}

	@Test
	public void getLocalElement_positive() {
		NamedElement element;
		switch (kind) {
		case TRANSITION_GUARD:
			element = getTransition("RootCapsule", "in");
			break;
		case TRIGGER_GUARD:
			element = getTrigger(getTransition("RootCapsule", "Initial"));
			break;
		default:
			throw new AssertionError(kind);
		}
		positiveTestTemplate(element, kind.rtMatcher(), false, false, kind.getFeature());
	}

	@Test
	public void getLocalElement_negative() {
		NamedElement element;
		switch (kind) {
		case TRANSITION_GUARD:
			element = getTransition("RootCapsule", "out");
			break;
		case TRIGGER_GUARD:
			element = getTrigger(getTransition("RootCapsule", "out"));
			break;
		default:
			throw new AssertionError(kind);
		}
		negativeTestTemplate(element, kind.rtMatcher(), false, false, kind.getFeature());
	}

	@Test
	public void getInheritedElement_positive() {
		NamedElement element;
		switch (kind) {
		case TRANSITION_GUARD:
			element = getTransition("Subcapsule", "in");
			break;
		case TRIGGER_GUARD:
			element = getTrigger(getTransition("Subcapsule", "Initial"));
			break;
		default:
			throw new AssertionError(kind);
		}
		positiveTestTemplate(element, kind.rtMatcher(), true, true, kind.getFeature());
	}

	@Test
	public void getInheritedElement_negative() {
		NamedElement element;
		switch (kind) {
		case TRANSITION_GUARD:
			element = getTransition("RootCapsule", "in");
			break;
		case TRIGGER_GUARD:
			element = getTrigger(getTransition("RootCapsule", "Initial"));
			break;
		default:
			throw new AssertionError(kind);
		}
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
		TRANSITION_GUARD, TRIGGER_GUARD;

		Predicate<Object> umlFilter() {
			Predicate<Object> result;

			switch (this) {
			case TRANSITION_GUARD:
			case TRIGGER_GUARD:
				result = Constraint.class::isInstance;
				break;
			default:
				throw new AssertionError(this);
			}

			return result;
		}

		Predicate<Object> rtFilter() {
			Predicate<Object> result = umlFilter();

			switch (this) {
			case TRANSITION_GUARD:
				// Pass
				break;
			case TRIGGER_GUARD:
				result = result.and(c -> UMLUtil.getStereotypeApplication((Constraint) c, RTGuard.class) != null);
				break;
			default:
				throw new AssertionError(this);
			}

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

		IJavaQuery2<? extends NamedElement, ? extends NamedElement> getQuery() {
			switch (this) {
			case TRANSITION_GUARD:
			case TRIGGER_GUARD:
				return new GetUMLRTSingleElementQuery();
			default:
				throw new AssertionError(this);
			}
		}

		@SuppressWarnings("restriction")
		EStructuralFeature getFeature() {
			switch (this) {
			case TRANSITION_GUARD:
				return org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage.Literals.TRANSITION__GUARD;
			case TRIGGER_GUARD:
				return org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage.Literals.TRIGGER__GUARD;
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
