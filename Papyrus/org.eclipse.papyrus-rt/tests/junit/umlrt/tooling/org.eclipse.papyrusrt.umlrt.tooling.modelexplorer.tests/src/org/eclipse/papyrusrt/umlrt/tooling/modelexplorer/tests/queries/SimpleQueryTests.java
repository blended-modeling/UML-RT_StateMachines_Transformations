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

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.AllButStructuralFeaturesQuery;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.EmptyReferencesListQuery;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.GetGeneralizationQuery;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.IsCapsuleQuery;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.IsExcludedQuery;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.IsInRTStateMachineQuery;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.IsInheritedOrRedefinedQuery;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.IsProtocolContainerQuery;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.IsProtocolContainersContainerQuery;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.IsProtocolQuery;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries.IsRTStateMachineQuery;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.UMLPackage;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test cases for various simple queries.
 */
@PluginResource("resource/allelements/various.di")
@RunWith(Parameterized.class)
public class SimpleQueryTests extends AbstractRTQueryTest<EObject> {

	private final QueryKind kind;

	@SuppressWarnings("unchecked")
	public SimpleQueryTests(QueryKind kind) throws Exception {
		super(kind.getQuery());

		this.kind = kind;
	}

	@Test
	public void happyPath() {
		kind.subjects(model.getModel(), true).forEach(this::positiveTestTemplate);
	}

	@Test
	public void negativePath() {
		kind.subjects(model.getModel(), false).forEach(this::negativeTestTemplate);
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

	void positiveTestTemplate(EObject subject) {
		// The queries we test have no parameters
		positiveTestTemplate(subject, kind.matcher(true));
	}

	void negativeTestTemplate(EObject subject) {
		// The queries we test have no parameters
		negativeTestTemplate(subject, kind.matcher(false));
	}

	enum QueryKind {
		IS_CAPSULE, IS_PROTOCOL, IS_INHERITED, IS_REDEFINED, IS_EXCLUDED, //
		IS_PROTOCOL_CONTAINER, IS_PROTOCOL_CONTAINERS_CONTAINER, //
		IS_STATE_MACHINE, IS_IN_STATE_MACHINE, //
		GET_GENERALIZATION, //
		EMPTY_REFERENCES_LIST, ALL_BUT_STRUCTURAL_FEATURES;

		IJavaQuery2<? extends EObject, ?> getQuery() {
			switch (this) {
			case IS_CAPSULE:
				return new IsCapsuleQuery();
			case IS_PROTOCOL:
				return new IsProtocolQuery();
			case IS_INHERITED:
			case IS_REDEFINED:
				return new IsInheritedOrRedefinedQuery();
			case IS_EXCLUDED:
				return new IsExcludedQuery();
			case IS_PROTOCOL_CONTAINER:
				return new IsProtocolContainerQuery();
			case IS_PROTOCOL_CONTAINERS_CONTAINER:
				return new IsProtocolContainersContainerQuery();
			case IS_STATE_MACHINE:
				return new IsRTStateMachineQuery();
			case IS_IN_STATE_MACHINE:
				return new IsInRTStateMachineQuery();
			case GET_GENERALIZATION:
				return new GetGeneralizationQuery();
			case EMPTY_REFERENCES_LIST:
				return new EmptyReferencesListQuery();
			case ALL_BUT_STRUCTURAL_FEATURES:
				return new AllButStructuralFeaturesQuery();
			default:
				throw new AssertionError(this);
			}
		}

		Stream<EObject> subjects(Package root, boolean isHappyPath) {
			switch (this) {
			case IS_CAPSULE:
				return isHappyPath
						? Stream.of(root.getOwnedType("Subcapsule"))
						: Stream.of(root.getOwnedType("PassiveClass"));
			case IS_PROTOCOL:
				return isHappyPath
						? Stream.of(root.getNestedPackage("Protocol1").getOwnedType("Protocol1"))
						: Stream.of(root.getOwnedType("DesignCollaboration"));
			case IS_INHERITED:
				return isHappyPath
						? Stream.of(((Namespace) root.getOwnedType("Subsubcapsule")).getOwnedMember("redefProtocol1"))
						: Stream.of(((Namespace) root.getOwnedType("RootCapsule")).getOwnedMember("protocol1"));
			case IS_REDEFINED:
				return isHappyPath
						? Stream.of(((Namespace) root.getOwnedType("Subcapsule")).getOwnedMember("redefProtocol1"))
						: Stream.of(((Namespace) root.getOwnedType("RootCapsule")).getOwnedMember("protocol1"));
			case IS_EXCLUDED:
				return isHappyPath
						? Stream.of(((Namespace) root.getOwnedType("Subcapsule")).getOwnedMember("excludedProtocol2"))
						: Stream.of(((Namespace) root.getOwnedType("Subcapsule")).getOwnedMember("redefProtocol1"));
			case IS_PROTOCOL_CONTAINER:
				return isHappyPath
						? Stream.of(root.getNestedPackage("Protocol1"), root.getNestedPackage("Protocol2"))
						: Stream.of(root, root.getNestedPackage("nested"));
			case IS_STATE_MACHINE:
				return isHappyPath
						? Stream.of(((Class) root.getOwnedType("NestedCapsule")).getClassifierBehavior())
						: Stream.of(((Class) root.getOwnedType("PassiveClass")).getClassifierBehavior());
			case IS_IN_STATE_MACHINE:
				return isHappyPath
						? Stream.of(((StateMachine) ((Class) root.getOwnedType("NestedCapsule")).getClassifierBehavior()).getRegions().get(0).getTransitions().get(0))
						: Stream.of(((StateMachine) ((Class) root.getOwnedType("PassiveClass")).getClassifierBehavior()).getRegions().get(0).getTransitions().get(0));
			case GET_GENERALIZATION:
				return isHappyPath
						? Stream.of(root.getOwnedType("Subcapsule"),
								root.getNestedPackage("Subprotocol").getOwnedType("Subprotocol"))
						: Stream.of(root.getOwnedType("RootCapsule"),
								root.getNestedPackage("Protocol1").getOwnedType("Protocol1"));
			case EMPTY_REFERENCES_LIST:
				return Stream.of(((StructuredClassifier) root.getOwnedType("RootCapsule")).getOwnedConnector("connector1"));
			case IS_PROTOCOL_CONTAINERS_CONTAINER:
				return isHappyPath
						? Stream.of(root)
						: Stream.of(root.getNestedPackage("Protocol1"), root.getNestedPackage("nested"));
			case ALL_BUT_STRUCTURAL_FEATURES:
				return Stream.of(root.getOwnedType("RootCapsule"));
			default:
				throw new AssertionError(this);
			}
		}

		Matcher<Object> matcher(boolean isHappyPath) {
			switch (this) {
			case IS_CAPSULE:
			case IS_PROTOCOL:
			case IS_INHERITED:
			case IS_REDEFINED:
			case IS_EXCLUDED:
			case IS_PROTOCOL_CONTAINER:
			case IS_PROTOCOL_CONTAINERS_CONTAINER:
			case IS_STATE_MACHINE:
			case IS_IN_STATE_MACHINE:
				return is(true);
			case GET_GENERALIZATION:
				return isHappyPath
						? instanceOf(Generalization.class)
						: notNullValue(); // The test framework negates this to assert a null generalization
			case EMPTY_REFERENCES_LIST:
				// This query is a bit weird inasmuch as its happy path is a failure path
				return isHappyPath
						? ignoreMatcher()
						: anything();
			case ALL_BUT_STRUCTURAL_FEATURES:
				return isHappyPath
						? is(UMLPackage.Literals.BEHAVIORED_CLASSIFIER__OWNED_BEHAVIOR)
						: either(is((Object) UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE))
								.or(is(UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR))
								.or(is(UMLPackage.Literals.ENCAPSULATED_CLASSIFIER__OWNED_PORT));
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
