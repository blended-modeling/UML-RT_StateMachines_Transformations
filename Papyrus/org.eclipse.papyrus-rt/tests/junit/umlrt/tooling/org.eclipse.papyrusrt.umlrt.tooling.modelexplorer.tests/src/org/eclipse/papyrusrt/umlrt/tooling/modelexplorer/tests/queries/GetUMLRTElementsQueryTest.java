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

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTState;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.GetUMLRTElementsQuery;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for the family of queries that get a list of elements via a Façade API feature.
 */
@PluginResource("resource/inheritance/statemachines.di")
@RunWith(Parameterized.class)
public class GetUMLRTElementsQueryTest extends AbstractRTQueryTest<NamedElement> {

	private final QueryKind kind;

	public GetUMLRTElementsQueryTest(QueryKind kind) throws Exception {
		super(kind.getQuery());

		this.kind = kind;
	}

	@Test
	public void getLocalElement_positive() {
		NamedElement element;
		switch (kind) {
		case STATE_MACHINE_VERTICES:
			element = getStateMachine("RootCapsule");
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
		case STATE_MACHINE_VERTICES:
			element = getStateMachine("Subcapsule");
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
		case STATE_MACHINE_VERTICES:
			element = getStateMachine("Subcapsule");
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
		case STATE_MACHINE_VERTICES:
			element = getStateMachine("RootCapsule");
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
		STATE_MACHINE_VERTICES;

		Predicate<Object> umlFilter() {
			Predicate<Object> result;

			switch (this) {
			case STATE_MACHINE_VERTICES:
				result = Vertex.class::isInstance;
				break;
			default:
				throw new AssertionError(this);
			}

			return result;
		}

		Predicate<Object> rtFilter() {
			Predicate<Object> result = umlFilter();

			switch (this) {
			case STATE_MACHINE_VERTICES:
				Predicate<Object> hasPseudo = v -> UMLUtil.getStereotypeApplication((Vertex) v, RTPseudostate.class) != null;
				Predicate<Object> hasState = v -> UMLUtil.getStereotypeApplication((Vertex) v, RTState.class) != null;
				result = result.and(hasPseudo.or(hasState));
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

		IJavaQuery2<? extends NamedElement, ? extends List<? extends NamedElement>> getQuery() {
			switch (this) {
			case STATE_MACHINE_VERTICES:
				return new GetUMLRTElementsQuery();
			default:
				throw new AssertionError(this);
			}
		}

		@SuppressWarnings("restriction")
		EStructuralFeature getFeature() {
			switch (this) {
			case STATE_MACHINE_VERTICES:
				return org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage.Literals.STATE_MACHINE__VERTEX;
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
