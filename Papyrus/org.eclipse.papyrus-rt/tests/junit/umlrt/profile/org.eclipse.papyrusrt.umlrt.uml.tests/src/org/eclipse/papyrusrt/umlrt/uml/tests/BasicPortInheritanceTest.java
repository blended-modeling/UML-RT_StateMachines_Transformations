/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.uml.tests;

import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.named;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.replicated;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for inheritance of basic port features.
 */
@TestModel("inheritance/ports.uml")
@NoFacade
@Category(CapsuleTests.class)
public class BasicPortInheritanceTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public BasicPortInheritanceTest() {
		super();
	}

	@Test
	public void typeInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);
		Port p2 = subsub.getOwnedPorts().get(1);

		assertThat(p1.getType(), named("Protocol1"));
		assertThat(p2.getType(), named("Protocol1"));
	}

	@Test
	public void nameInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);
		Port p2 = subsub.getOwnedPorts().get(1);

		assertThat(p1, named("protocol1"));
		assertThat(p2, named("subport"));
	}

	@Test
	public void visibilityInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);

		assertThat(p1.getVisibility(), is(VisibilityKind.PUBLIC_LITERAL));
		assertThat(p1.isSetVisibility(), is(false));

		Port root = fixture.getElement("RootCapsule", Class.class).getOwnedPorts().get(0);
		root.setVisibility(VisibilityKind.PACKAGE_LITERAL);

		assertThat(p1.getVisibility(), is(VisibilityKind.PACKAGE_LITERAL));
		assertThat(p1.isSetVisibility(), is(false));
	}

	@Test
	public void aggregationInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);

		assertThat(p1.getAggregation(), is(AggregationKind.COMPOSITE_LITERAL));
		assertThat(p1.eIsSet(UMLPackage.Literals.PROPERTY__AGGREGATION), is(false));

		Port root = fixture.getElement("RootCapsule", Class.class).getOwnedPorts().get(0);
		root.setAggregation(AggregationKind.SHARED_LITERAL);

		assertThat(p1.getAggregation(), is(AggregationKind.SHARED_LITERAL));
		assertThat(p1.eIsSet(UMLPackage.Literals.PROPERTY__AGGREGATION), is(false));
	}

	@Test
	public void replicationInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);
		Port p2 = subsub.getOwnedPorts().get(1);

		assertThat(p1, replicated(1));
		// The bound-values aren't implicitly created if not needed
		assertThat(p1.getLowerValue(), nullValue());
		assertThat(p1.getUpperValue(), nullValue());

		assertThat(p2, replicated(4, 6));
		// The bound-values are implicitly created if needed
		assertThat(p2.getLowerValue(), notNullValue());
		assertThat(p2.getUpperValue(), notNullValue());
	}

	@Test
	public void replicationRedefined() {
		Class capsule = fixture.getElement("RootCapsule");
		Port r1 = capsule.getOwnedPorts().get(0);
		Port r2 = capsule.getOwnedPorts().get(1);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);
		Port p2 = subsub.getOwnedPorts().get(1);

		assumeThat(p1, replicated(1));
		assumeThat(p2, replicated(4, 6));

		p1.setUpper(2);
		p2.setUpper(10);

		assertThat(p1, replicated(1, 2));
		assertThat(p2, replicated(4, 10));

		r1.setUpper(3);
		r2.setUpper(8);

		// Redefinition is unaffected
		assertThat(p1, replicated(1, 2));
		assertThat(p2, replicated(4, 10));
	}

	@Test
	public void replicationRedefinedDifferentKindOfValue() {
		Class capsule = fixture.getElement("RootCapsule");
		Port r1 = capsule.getOwnedPorts().get(0);
		Port r2 = capsule.getOwnedPorts().get(1);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);
		Port p2 = subsub.getOwnedPorts().get(1);

		assumeThat(p1, replicated(1));
		assumeThat(p2, replicated(4, 6));

		p1.createUpperValue(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION);
		((OpaqueExpression) p1.getUpperValue()).getBodies().add("NUM_PORTS");
		p2.createUpperValue(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION);
		((OpaqueExpression) p2.getUpperValue()).getBodies().add("MAX");

		assertThat(p1, replicated(1, 1)); // OpaqueExpression derives as 1
		assertThat(p2, replicated(4, 1)); // OpaqueExpression derives as 1

		r1.setUpper(3);
		r2.setUpper(8);

		// Redefinition is unaffected
		assertThat(p1, replicated(1, 1));
		assertThat(p2, replicated(4, 1));
	}

	@Test
	public void replicationUnsetRootDefinition() {
		Class capsule = fixture.getElement("RootCapsule");
		Port port = capsule.getOwnedPorts().get(1);

		assumeThat(port, replicated(4));

		port.eUnset(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE);
		port.eUnset(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE);

		assertThat(port, replicated(1));
		assertThat(port.eIsSet(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE), is(false));
		assertThat(port.eIsSet(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE), is(false));
		assertThat(port.getLowerValue(), nullValue());
		assertThat(port.getUpperValue(), nullValue());
	}

	@Test
	public void serviceInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);

		assertThat(p1.isService(), is(true));
	}

	@Test
	public void behaviorInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);

		assertThat(p1.isBehavior(), is(true));
	}

	@Test
	public void conjugatedInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);

		// Not much of a test, admittedly
		assertThat(p1.isConjugated(), is(false));
		assertThat(p1.eIsSet(UMLPackage.Literals.PORT__IS_CONJUGATED), is(false));
	}
}
