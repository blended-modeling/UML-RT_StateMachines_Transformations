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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.GetRTMessagesQuery;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.UMLPackage;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for the RT protocol messages query.
 */
@PluginResource("resource/inheritance/protocols.di")
@RunWith(Parameterized.class)
public class GetRTMessagesQueryTest extends AbstractRTQueryTest<EObject> {

	private final QueryKind kind;

	public GetRTMessagesQueryTest(QueryKind kind) throws Exception {
		super(kind.getQuery());

		this.kind = kind;
	}

	@Test
	public void getLocalMessages_positive() {
		// This one defines local messages
		positiveTestTemplate(getProtocol("RootProtocol"), false, false);
	}

	@Test
	public void getLocalMessages_negative() {
		// This one only redefines messages
		negativeTestTemplate(getProtocol("Subprotocol"), false, false);
	}

	@Test
	public void getRedefinedMessages_positive() {
		// This one redefines its inherited messages
		positiveTestTemplate(getProtocol("Subprotocol"), true, false);
	}

	@Test
	public void getRedefinedMessages_negative() {
		// This one redefines no messages
		negativeTestTemplate(getProtocol("Subsubprotocol"), true, false);
	}

	@Test
	public void getInheritedMessages_positive() {
		// This one inherits messages
		positiveTestTemplate(getProtocol("Subsubprotocol"), true, true);
	}

	@Test
	public void getInheritedMessages_negative() {
		negativeTestTemplate(getProtocol("RootProtocol"), true, true);
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

	Collaboration getProtocol(String name) {
		return (Collaboration) model.getModel().getNestedPackage(name).getOwnedType(name, false, UMLPackage.Literals.COLLABORATION, false);
	}

	void positiveTestTemplate(Collaboration protocol, boolean inherited, boolean includeVirtual) {
		positiveTestTemplate(protocol, kind.rtMatcher(), kind.direction().getLiteral(), inherited, includeVirtual);
	}

	void negativeTestTemplate(Collaboration protocol, boolean inherited, boolean includeVirtual) {
		negativeTestTemplate(protocol, kind.rtMatcher(), kind.direction().getLiteral(), inherited, includeVirtual);
	}

	enum QueryKind {
		IN, OUT, IN_OUT;

		Predicate<Object> umlFilter() {
			return Operation.class::isInstance;
		}

		Predicate<Object> rtFilter() {
			Predicate<Object> result = umlFilter();
			result = result.and(o -> RTMessageUtils.isRTMessage((Operation) o, direction()));
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

		RTMessageKind direction() {
			return RTMessageKind.values()[ordinal()];
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

		IJavaQuery2<EObject, ? extends List<?>> getQuery() {
			return new GetRTMessagesQuery();
		}

		@Override
		public String toString() {
			return name().toLowerCase().replace('_', ' ');
		}
	}
}
