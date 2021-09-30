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
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for inheritance of basic capsule-part features.
 */
@TestModel("inheritance/parts.uml")
@NoFacade
@Category(CapsuleTests.class)
public class BasicCapsulePartInheritanceTest {

	@ClassRule
	public static final ModelFixture fixture = new ModelFixture();

	public BasicCapsulePartInheritanceTest() {
		super();
	}

	@Test
	public void typeInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Property p1 = subsub.getOwnedAttributes().get(0);
		Property p2 = subsub.getOwnedAttributes().get(1);

		assertThat(p1.getType(), named("Capsule2"));
		assertThat(p2.getType(), named("Capsule4"));
	}

	@Test
	public void nameInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Property p1 = subsub.getOwnedAttributes().get(0);
		Property p2 = subsub.getOwnedAttributes().get(1);

		assertThat(p1, named("capsule2"));
		assertThat(p2, named("subpart"));
	}

	@Test
	public void visibilityInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Property p1 = subsub.getOwnedAttributes().get(0);

		assertThat(p1.getVisibility(), is(VisibilityKind.PROTECTED_LITERAL));
		assertThat(p1.isSetVisibility(), is(false));

		Property root = fixture.getElement("RootCapsule", Class.class).getOwnedAttributes().get(0);
		root.setVisibility(VisibilityKind.PACKAGE_LITERAL);

		assertThat(p1.getVisibility(), is(VisibilityKind.PACKAGE_LITERAL));
		assertThat(p1.isSetVisibility(), is(false));
	}

	@Test
	public void aggregationInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Property p1 = subsub.getOwnedAttributes().get(0);

		assertThat(p1.getAggregation(), is(AggregationKind.COMPOSITE_LITERAL));
		assertThat(p1.eIsSet(UMLPackage.Literals.PROPERTY__AGGREGATION), is(false));

		Property root = fixture.getElement("RootCapsule", Class.class).getOwnedAttributes().get(0);
		root.setAggregation(AggregationKind.SHARED_LITERAL);

		assertThat(p1.getAggregation(), is(AggregationKind.SHARED_LITERAL));
		assertThat(p1.eIsSet(UMLPackage.Literals.PROPERTY__AGGREGATION), is(false));
	}

	@Test
	public void replicationInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Property p1 = subsub.getOwnedAttributes().get(0);
		Property p2 = subsub.getOwnedAttributes().get(1);

		assertThat(p1, replicated(1));
		assertThat(p2, replicated(4, 6));
	}
}
