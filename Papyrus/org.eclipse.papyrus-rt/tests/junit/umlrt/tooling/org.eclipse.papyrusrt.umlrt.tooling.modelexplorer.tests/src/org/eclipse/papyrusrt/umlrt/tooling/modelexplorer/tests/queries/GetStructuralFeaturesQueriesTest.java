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

import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTConnector;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.GetRTAttributesQuery;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.GetRTConnectorsQuery;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.GetRTPortsQuery;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for the family of queries that get structural features.
 */
@PluginResource("resource/inheritance/connectors.di")
@RunWith(Parameterized.class)
public class GetStructuralFeaturesQueriesTest extends AbstractRTQueryTest<Class> {

	private final QueryKind kind;

	public GetStructuralFeaturesQueriesTest(QueryKind kind) throws Exception {
		super(kind.getQuery());

		this.kind = kind;
	}

	@Test
	public void getLocalRTFeatures_positive() {
		// This one defines local features
		positiveTestTemplate(getCapsule("RootCapsule"), kind.rtMatcher(), true, false, false);
	}

	@Test
	public void getLocalRTFeatures_negative() {
		// This one only redefines features
		negativeTestTemplate(getCapsule("Subcapsule"), kind.rtMatcher(), true, false, false);
	}

	@Test
	public void getRedefinedRTFeatures_positive() {
		// This one redefines its inherited features
		positiveTestTemplate(getCapsule("Subcapsule"), kind.rtMatcher(), true, true, false);
	}

	@Test
	public void getRedefinedRTFeatures_negative() {
		// This one redefines no features
		negativeTestTemplate(getCapsule("Subsubcapsule"), kind.rtMatcher(), true, true, false);
	}

	@Test
	public void getInheritedRTFeatures_positive() {
		// This one inherits features
		positiveTestTemplate(getCapsule("Subsubcapsule"), kind.rtMatcher(), true, true, true);
	}

	@Test
	public void getInheritedRTFeatures_negative() {
		negativeTestTemplate(getCapsule("RootCapsule"), kind.rtMatcher(), true, true, true);
	}

	@Test
	public void getNonRTFeatures_positive() {
		// This one has non-RT features
		positiveTestTemplate(getCapsule("BogusCapsule"), kind.umlMatcher(), false, false, false);
	}

	@Test
	public void getNonRTFeatures_negative() {
		// This one has only RT features
		negativeTestTemplate(getCapsule("RootCapsule"), kind.umlMatcher(), false, false, false);
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

	Class getCapsule(String name) {
		return (Class) model.getModel().getOwnedType(name, false, UMLPackage.Literals.CLASS, false);
	}

	enum QueryKind {
		PORTS, CAPSULE_PARTS, CONNECTORS;

		Predicate<Object> umlFilter() {
			Predicate<Object> result;

			switch (this) {
			case PORTS:
				result = Port.class::isInstance;
				break;
			case CAPSULE_PARTS:
				result = Property.class::isInstance;
				result = result.and(PORTS.umlFilter().negate());
				break;
			case CONNECTORS:
				result = Connector.class::isInstance;
				break;
			default:
				throw new AssertionError(this);
			}

			return result;
		}

		Predicate<Object> rtFilter() {
			Predicate<Object> result = umlFilter();

			switch (this) {
			case PORTS:
				result = result.and(p -> RTPortUtils.isRTPort((Port) p));
				break;
			case CAPSULE_PARTS:
				result = result.and(p -> CapsulePartUtils.isCapsulePart((Property) p));
				break;
			case CONNECTORS:
				result = result.and(c -> UMLUtil.getStereotypeApplication((Connector) c, RTConnector.class) != null);
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

		IJavaQuery2<Class, ? extends List<?>> getQuery() {
			switch (this) {
			case PORTS:
				return new GetRTPortsQuery();
			case CAPSULE_PARTS:
				return new GetRTAttributesQuery();
			case CONNECTORS:
				return new GetRTConnectorsQuery();
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
