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
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the port façade class {@link UMLRTCapsulePart}.
 */
@TestModel("inheritance/parts.uml")
@Category({ CapsuleTests.class, FacadeTests.class })
public class CapsulePartFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public CapsulePartFacadeTest() {
		super();
	}

	@Test
	public void partType() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		List<UMLRTCapsulePart> parts = capsule.getCapsuleParts();
		assertThat(parts.size(), is(2));

		assertThat(parts.get(0).getType(), is(root.getCapsule("Capsule2")));
		assertThat(parts.get(1).getType(), is(root.getCapsule("Capsule3")));
	}

	@Test
	public void setPartType() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		List<UMLRTCapsulePart> parts = capsule.getCapsuleParts();
		assertThat(parts.size(), is(2));
		parts.get(0).setType(root.getCapsule("Capsule4"));

		assertThat(parts.get(0).toUML().getType(), named("Capsule4"));
	}

	@Test
	public void optionality() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTCapsulePart part1 = capsule.getCapsulePart("capsule2");
		UMLRTCapsulePart part2 = capsule.getCapsulePart("capsule3");

		assertThat(part1.isOptional(), is(false));
		assertThat(part2.isOptional(), is(false));

		// Make the parts optional

		part1.setOptional(true);
		assertThat(part1.isOptional(), is(true));
		assertThat(part1.getReplicationFactor(), is(1));
		assertThat(part1.toUML(), replicated(0, 1));

		part2.setOptional(true);
		assertThat(part2.isOptional(), is(true));
		assertThat(part2.getReplicationFactor(), is(4));
		assertThat(part2.toUML(), replicated(0, 4));

		// Restore them to fixed replication

		part1.setOptional(false);
		assertThat(part1.isOptional(), is(false));
		assertThat(part1.getReplicationFactor(), is(1));
		assertThat(part1.toUML(), replicated(1));

		part2.setOptional(false);
		assertThat(part2.isOptional(), is(false));
		assertThat(part2.getReplicationFactor(), is(4));
		assertThat(part2.toUML(), replicated(4));
	}

	@Test
	public void optionalityWithReplicationFactor() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTCapsulePart part = capsule.getCapsulePart("capsule2");
		Property uml = part.toUML();
		uml.setLower(7);
		uml.setUpper(7);

		assumeThat(part.isOptional(), is(false));
		assumeThat(part.isSymbolicReplicationFactor(), is(false));
		assumeThat(part.getReplicationFactor(), is(7));

		// Make the part optional

		part.setOptional(true);
		assertThat(part.isOptional(), is(true));
		assertThat(part.getReplicationFactor(), is(7));
		assertThat(part.toUML(), replicated(0, 7));

		// Set the replication, which should not change optionality
		part.setReplicationFactor(5);
		assertThat(part.isOptional(), is(true));
		assertThat(part.toUML(), replicated(0, 5));

		// And now not optional

		part.setOptional(false);
		assertThat(part.isOptional(), is(false));
		assertThat(part.getReplicationFactor(), is(5));
		assertThat(part.toUML(), replicated(5, 5));
	}

	@Test
	public void optionalityWithSymbolicReplicationFactor() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTCapsulePart part = capsule.getCapsulePart("capsule2");
		Property uml = part.toUML();
		OpaqueExpression expr = (OpaqueExpression) uml.createLowerValue(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION);
		expr.getLanguages().add("C++");
		expr.getBodies().add("NUM_CAPSULES");
		expr = (OpaqueExpression) uml.createUpperValue(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION);
		expr.getLanguages().add("C++");
		expr.getBodies().add("NUM_CAPSULES");

		assumeThat(part.isOptional(), is(false));
		assumeThat(part.isSymbolicReplicationFactor(), is(true));
		assumeThat(part.getReplicationFactor(), is(1));

		assertThat(part.getReplicationFactorAsString(), is("NUM_CAPSULES"));

		// Make the part optional

		part.setOptional(true);
		assertThat(part.isOptional(), is(true));
		assertThat(part.getReplicationFactorAsString(), is("NUM_CAPSULES"));
		assertThat(part.toUML(), replicated(0, 1));

		// And now not optional

		part.setOptional(false);
		assertThat(part.isOptional(), is(false));
		assertThat(part.getReplicationFactorAsString(), is("NUM_CAPSULES"));
		assertThat(part.toUML(), replicated(1, 1));
		assertThat(part.toUML().getLowerValue(), notNullValue());
		assertThat(part.toUML().getLowerValue().stringValue(), is("NUM_CAPSULES"));
	}

	@Test
	public void partReplication() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTCapsulePart part1 = capsule.getCapsulePart("capsule2");
		UMLRTCapsulePart part2 = capsule.getCapsulePart("capsule3");

		assertThat(part1.isSymbolicReplicationFactor(), is(false));
		assertThat(part1.getReplicationFactor(), is(1));
		assertThat(part2.isSymbolicReplicationFactor(), is(false));
		assertThat(part2.getReplicationFactor(), is(4));

		part1.setReplicationFactor(2);

		assertThat(part1.toUML().getLowerValue(), instanceOf(LiteralInteger.class));
		assertThat(((LiteralInteger) part1.toUML().getLowerValue()).getValue(), is(2));
		assertThat(part1.toUML().getUpperValue(), instanceOf(LiteralUnlimitedNatural.class));
		assertThat(((LiteralUnlimitedNatural) part1.toUML().getUpperValue()).getValue(), is(2));
	}

	@Test
	public void portReplicationExpression() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTCapsulePart part = capsule.getCapsulePart("capsule2");
		Property umlPart = part.toUML();
		OpaqueExpression lowerValue = (OpaqueExpression) umlPart.createLowerValue(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION);
		OpaqueExpression upperValue = (OpaqueExpression) umlPart.createUpperValue(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION);

		lowerValue.getBodies().add("NUM_CAPSULES");
		upperValue.getBodies().add("NUM_CAPSULES");

		assertThat(part.isSymbolicReplicationFactor(), is(true));
		assertThat(part.getReplicationFactor(), is(1));

		lowerValue.getBodies().set(0, "42");
		upperValue.getBodies().set(0, "42");

		assertThat(part.isSymbolicReplicationFactor(), is(false));
		assertThat(part.getReplicationFactor(), is(42));
	}

	@Test
	public void fixedKind() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTCapsulePart part = capsule.getCapsulePart("capsule2");
		assertThat(part.getKind(), is(UMLRTCapsulePartKind.FIXED));
	}

	@Test
	public void optionalKind() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTCapsulePart part = capsule.getCapsulePart("capsule2");
		Property umlPart = part.toUML();

		umlPart.setLower(0);
		umlPart.setUpper(2);
		assertThat(part.getKind(), is(UMLRTCapsulePartKind.OPTIONAL));

		assertThat(part.getReplicationFactor(), is(2));
	}

	@Test
	public void pluginKind() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTCapsulePart part = capsule.getCapsulePart("capsule2");
		Property umlPart = part.toUML();

		umlPart.setLower(0);
		umlPart.setUpper(2);
		umlPart.setAggregation(AggregationKind.SHARED_LITERAL);
		assertThat(part.getKind(), is(UMLRTCapsulePartKind.PLUG_IN));

		assertThat(part.getReplicationFactor(), is(2));
	}

	@TestModel("inheritance/connectors.uml")
	@Test
	public void deletedCapsulePartIsNotConnected_bug512183() {
		UMLRTCapsule capsule = UMLRTPackage.getInstance(fixture.getModel()).getCapsule("RootCapsule");

		UMLRTCapsulePart part = capsule.getCapsulePart("nestedCapsule");

		assumeThat("Capsule part has no connectors", part.getConnectorsOfPorts(), hasItem(anything()));

		part.destroy();

		assertThat("Capsule part has connectors", part.getConnectorsOfPorts(), not(hasItem(anything())));
	}

	@Test
	public void allRedefinitions() {
		UMLRTCapsule rootCapsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTCapsule subcapsule = rootCapsule.getPackage().createCapsule("NewSubcapsule");
		UMLRTCapsule subsubcapsule = rootCapsule.getPackage().createCapsule("NewSubsubcapsule");
		subcapsule.setSuperclass(rootCapsule);
		subsubcapsule.setSuperclass(subcapsule);

		UMLRTCapsulePart rootPart = rootCapsule.getCapsulePart("capsule2");
		UMLRTCapsulePart subPart = subcapsule.getCapsulePart("capsule2");
		UMLRTCapsulePart subsubPart = subsubcapsule.getCapsulePart("capsule2");

		List<UMLRTCapsulePart> expected = Arrays.asList(rootPart, subPart, subsubPart);
		List<? extends UMLRTCapsulePart> actual = rootPart.allRedefinitions().collect(Collectors.toList());
		// There may be other redefinitions, too. Important to keep the order
		actual.retainAll(expected);
		assertThat(actual, is(expected));
	}

}
