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

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Proxy;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManagerFactory;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.EFacetFactory;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.ParameterValue;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueListFactory2;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.hamcrest.Matcher;
import org.junit.AssumptionViolatedException;
import org.junit.ClassRule;
import org.junit.rules.TestRule;

import com.google.common.base.Objects;

/**
 * A convenient superclass for facet query tests.
 */
public abstract class AbstractRTQueryTest<T extends EObject> {

	/** Some of the tests depend on element-type matchers. */
	@ClassRule
	public static final TestRule elementTypes = new ElementTypesRule();

	@ClassRule
	public static final UMLRTModelSetFixture model = new UMLRTModelSetFixture();

	private static IFacetManager facetManager;

	@SuppressWarnings("unchecked")
	private static final Matcher<Object> IGNORE_MATCHER = (Matcher<Object>) Proxy.newProxyInstance(AbstractRTQueryTest.class.getClassLoader(),
			new java.lang.Class<?>[] { Matcher.class },
			(proxy, method, args) -> {
				if (method.getDeclaringClass() == Object.class) {
					switch (method.getName()) {
					case "equals":
						return args[0] == proxy;
					case "hashCode":
						return System.identityHashCode(proxy);
					case "toString":
						return "IgnoreMatcher";
					default:
						// No other methods of class Object can be proxied, so fall through to assert the weirdness
					}
				}
				throw new AssertionError("Attempt to use ignore matcher");
			});

	private final IJavaQuery2<T, ?> fixture;

	@SuppressWarnings("unchecked")
	public AbstractRTQueryTest(IJavaQuery2<? extends T, ?> fixture) throws Exception {
		super();

		this.fixture = (IJavaQuery2<T, ?>) fixture;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void positiveTestTemplate(T subject, Matcher<Object> queryResultMatcher, Object... queryParameter) {
		ignoreCheck(queryResultMatcher, true);

		String failure = "Query result not matched";

		Object result = evaluate(subject, queryParameter);

		if (result instanceof Iterable<?>) {
			failure = "No matching results found";
			queryResultMatcher = (Matcher) hasItem(queryResultMatcher);
		}

		assertThat(failure, result, queryResultMatcher);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void negativeTestTemplate(T subject, Matcher<Object> queryResultMatcher, Object... queryParameter) {
		ignoreCheck(queryResultMatcher, false);

		String failure = "Query result matched";

		Object result = evaluate(subject, queryParameter);

		if (result instanceof Iterable<?>) {
			failure = "At least one result found";
			queryResultMatcher = (Matcher) hasItem(queryResultMatcher);
		}

		assertThat(failure, result, not(queryResultMatcher));
	}

	private void ignoreCheck(Matcher<Object> queryResultMatcher, boolean isHappyPath) {
		if (queryResultMatcher == IGNORE_MATCHER) {
			throw new AssumptionViolatedException(
					String.format("There is no %s path for this test case", isHappyPath ? "happy" : "failure"));
		}
	}

	/** Obtains a matcher that signals that the particular test case is to be ignored. */
	protected static Matcher<Object> ignoreMatcher() {
		return IGNORE_MATCHER;
	}

	Object evaluate(T source, Object... queryParameter) {
		Object result = null;

		try {
			result = fixture.evaluate(source, parameters(queryParameter), facetManager());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Query invocation failed: " + e.getMessage());
		}

		return result;
	}

	IParameterValueList2 parameters(Object... param) {
		return IParameterValueListFactory2.INSTANCE.createParameterValueList(
				Stream.of(param)
						.map(this::createParameter)
						.toArray(ParameterValue[]::new));
	}

	<V> ParameterValue createParameter(V value) {
		ParameterValue result = EFacetFactory.eINSTANCE.createParameterValue();
		result.setValue(value);
		return result;
	}

	static IFacetManager facetManager() {
		if (facetManager == null) {
			facetManager = IFacetManagerFactory.DEFAULT.getOrCreateFacetManager(new ResourceSetImpl());
		}

		return facetManager;
	}

	Class getCapsule(String name) {
		return (Class) model.getModel().getOwnedType(name, false, UMLPackage.Literals.CLASS, false);
	}

	StateMachine getStateMachine(String capsuleName) {
		return (StateMachine) getCapsule(capsuleName).getClassifierBehavior();
	}

	Region getRegion(StateMachine stateMachine) {
		return UMLRTExtensionUtil.<Region> getUMLRTContents(stateMachine, UMLPackage.Literals.STATE_MACHINE__REGION)
				.get(0);
	}

	Transition getTransition(String capsuleName, String transitionName) {
		Region region = getRegion(getStateMachine(capsuleName));
		return getTransition(region, transitionName);
	}

	Transition getTransition(String capsuleName, Object sourceMatch, Object targetMatch) {
		Region region = getRegion(getStateMachine(capsuleName));
		return getTransition(region, sourceMatch, targetMatch);
	}

	Transition getTransition(Region region, String name) {
		return region.getTransition(name);
	}

	Transition getTransition(Region region, Object sourceMatch, Object targetMatch) {
		Vertex source = UMLRTExtensionUtil.<Vertex> getUMLRTContents(region, UMLPackage.Literals.REGION__SUBVERTEX)
				.stream().filter(vertex(sourceMatch)).findAny().get();
		Vertex target = UMLRTExtensionUtil.<Vertex> getUMLRTContents(region, UMLPackage.Literals.REGION__SUBVERTEX)
				.stream().filter(vertex(targetMatch)).findAny().get();

		// In case the sought-after transition is excluded
		Optional<Transition> excluded = UMLRTExtensionUtil.<Transition> getUMLRTContents(region, UMLPackage.Literals.REGION__TRANSITION).stream()
				.filter(t -> t.getSource() == source)
				.filter(t -> t.getTarget() == target)
				.findFirst();

		// Preferred methods for non-excluded transition
		Transition result = source.getOutgoings().stream().filter(t -> t.getTarget() == target)
				.findAny().orElseGet(excluded::get);

		return result;
	}

	Predicate<Vertex> vertex(Object match) {
		return v -> (match instanceof PseudostateKind)
				? (v instanceof Pseudostate && ((Pseudostate) v).getKind() == match)
				: Objects.equal(v.getName(), match);
	}

	Trigger getTrigger(Transition transition) {
		return UMLRTExtensionUtil.<Trigger> getUMLRTContents(transition, UMLPackage.Literals.TRANSITION__TRIGGER)
				.get(0);
	}
}
