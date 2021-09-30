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

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.ConnectorKind;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for inheritance of basic connector features.
 */
@TestModel("inheritance/connectors.uml")
@NoFacade
@Category(CapsuleTests.class)
public class BasicConnectorInheritanceTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public BasicConnectorInheritanceTest() {
		super();
	}

	@Test
	public void connectorInherited() {
		Class capsule = fixture.getElement("RootCapsule");
		Class subcapsule = fixture.getElement("Subcapsule");
		Class nested = fixture.getElement("NestedCapsule");

		Connector connector = subcapsule.getOwnedConnector("connector1");
		assertThat(connector, notNullValue());
		assertThat(UMLRTExtensionUtil.isVirtualElement(connector), is(true));
		assertThat(connector.getOwner(), is(subcapsule));

		Connector redefined = connector.getRedefinedConnector("connector1");
		assertThat(connector, notNullValue());
		assertThat(redefined.getOwner(), is(capsule));

		EList<ConnectorEnd> ends = connector.getEnds();
		assertThat(ends.size(), is(2));
		ConnectorEnd end1 = ends.get(0);
		ConnectorEnd end2 = ends.get(1);

		assertThat(end1.eIsSet(UMLPackage.Literals.CONNECTOR_END__ROLE), is(false));
		assertThat(end1.eIsSet(UMLPackage.Literals.CONNECTOR_END__PART_WITH_PORT), is(false));
		assertThat(end1.eIsSet(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE), is(false));
		assertThat(end1.eIsSet(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE), is(false));
		assertThat(end2.eIsSet(UMLPackage.Literals.CONNECTOR_END__ROLE), is(false));
		assertThat(end2.eIsSet(UMLPackage.Literals.CONNECTOR_END__PART_WITH_PORT), is(false));
		assertThat(end2.eIsSet(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE), is(false));
		assertThat(end2.eIsSet(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE), is(false));

		assertThat(end1.getRole(), is(subcapsule.getOwnedPort("protocol1", null)));
		assertThat(end1.getPartWithPort(), nullValue());
		assertThat(end2.getRole(), is(nested.getOwnedPort("protocol1", null)));
		assertThat(end2.getPartWithPort(), is(subcapsule.getOwnedAttribute("nestedCapsule", null)));
	}

	@Test
	public void nameInheritance() {
		Class capsule = fixture.getElement("RootCapsule");
		Class subcapsule = fixture.getElement("Subcapsule");

		Connector connector = subcapsule.getOwnedConnector("connector1");
		Connector redefined = capsule.getOwnedConnector("connector1");

		String oldName = redefined.getName();
		assertThat(connector.getName(), is(oldName));

		fixture.expectNotification(connector, UMLPackage.Literals.NAMED_ELEMENT__NAME, Notification.SET, is(oldName), is("foo"), () -> {
			redefined.setName("foo");
			assertThat(connector.getName(), is("foo"));
		});
	}

	@Test
	public void visibilityInheritance() {
		Class capsule = fixture.getElement("RootCapsule");
		Class subcapsule = fixture.getElement("Subcapsule");

		Connector connector = subcapsule.getOwnedConnector("connector1");
		Connector redefined = capsule.getOwnedConnector("connector1");

		VisibilityKind oldVis = redefined.getVisibility();
		assertThat(connector.getVisibility(), is(oldVis));

		fixture.expectNotification(connector, UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY, Notification.SET, is(oldVis), is(VisibilityKind.PACKAGE_LITERAL), () -> {
			redefined.setVisibility(VisibilityKind.PACKAGE_LITERAL);
			assertThat(connector.getVisibility(), is(VisibilityKind.PACKAGE_LITERAL));
		});
	}

	@Test
	public void roleInheritance() {
		Class capsule = fixture.getElement("RootCapsule");
		Class subcapsule = fixture.getElement("Subcapsule");

		Connector connector = subcapsule.getOwnedConnector("connector1");
		Connector redefined = capsule.getOwnedConnector("connector1");

		Port port1 = capsule.getOwnedPort("protocol1", null);
		assumeThat(port1, notNullValue());
		Port subport1 = subcapsule.getOwnedPort("protocol1", null);
		assumeThat(subport1, notNullValue());
		Port port2 = capsule.getOwnedPort("protocol2", null);
		assumeThat(port2, notNullValue());
		Port subport2 = subcapsule.getOwnedPort("protocol2", null);
		assumeThat(subport2, notNullValue());

		ConnectorEnd end1 = connector.getEnds().get(0);
		ConnectorEnd redefEnd = redefined.getEnds().get(0);

		assumeThat(end1.getRole(), is(subport1));

		fixture.expectNotification(end1, UMLPackage.Literals.CONNECTOR_END__ROLE, Notification.SET, is(subport1), is(subport2), () -> {
			redefEnd.setRole(port2);
			assertThat(end1.getRole(), is(subport2));
		});
	}

	@Test
	public void partWithPortInheritance() {
		Class capsule = fixture.getElement("RootCapsule");
		Class subcapsule = fixture.getElement("Subcapsule");

		Connector connector = subcapsule.getOwnedConnector("connector1");
		Connector redefined = capsule.getOwnedConnector("connector1");

		Property nested = capsule.getOwnedAttribute("nestedCapsule", null);
		assumeThat(nested, notNullValue());
		Property subnested = subcapsule.getOwnedAttribute("nestedCapsule", null);
		assumeThat(subnested, notNullValue());
		Property part2 = capsule.createOwnedAttribute("part2", nested.getType());
		part2.setAggregation(AggregationKind.COMPOSITE_LITERAL);
		part2.setVisibility(VisibilityKind.PROTECTED_LITERAL);
		part2.applyStereotype(part2.getApplicableStereotype("UMLRealTime::CapsulePart"));
		Property subpart2 = subcapsule.getOwnedAttribute("part2", null);
		assumeThat(subpart2, notNullValue());

		ConnectorEnd end2 = connector.getEnds().get(1);
		ConnectorEnd redefEnd = redefined.getEnds().get(1);

		assumeThat(end2.getPartWithPort(), is(subnested));

		fixture.expectNotification(end2, UMLPackage.Literals.CONNECTOR_END__PART_WITH_PORT, Notification.SET, is(subnested), is(subpart2), () -> {
			redefEnd.setPartWithPort(part2);
			assertThat(end2.getPartWithPort(), is(subpart2));
		});
	}

	@Test
	public void lowerBoundInheritance() {
		Class capsule = fixture.getElement("RootCapsule");
		Class subcapsule = fixture.getElement("Subcapsule");

		Connector connector = subcapsule.getOwnedConnector("connector1");
		Connector redefined = capsule.getOwnedConnector("connector1");

		ConnectorEnd end1 = connector.getEnds().get(0);
		ConnectorEnd redefEnd = redefined.getEnds().get(0);
		redefEnd.setLower(1);

		int oldBound = redefEnd.getLower();
		assumeThat(end1.getLower(), is(oldBound));

		fixture.expectNotification(end1.getLowerValue(), UMLPackage.Literals.LITERAL_INTEGER__VALUE, Notification.SET, is(oldBound), is(0), () -> {
			redefEnd.setLower(0);
			assertThat(end1.getLower(), is(0));
		});
	}

	@Test
	public void upperBoundInheritance() {
		Class capsule = fixture.getElement("RootCapsule");
		Class subcapsule = fixture.getElement("Subcapsule");

		Connector connector = subcapsule.getOwnedConnector("connector1");
		Connector redefined = capsule.getOwnedConnector("connector1");

		ConnectorEnd end1 = connector.getEnds().get(0);
		ConnectorEnd redefEnd = redefined.getEnds().get(0);
		redefEnd.setUpper(1);

		assumeThat(end1.getUpper(), is(redefEnd.getUpper()));

		fixture.expectNotification(end1.getUpperValue(), UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE, Notification.SET, anything(), is(7), () -> {
			redefEnd.setUpper(7);
			assertThat(end1.getUpper(), is(7));
		});
	}

	@TestModel("validation/connectors.uml")
	@Test
	public void connectorKind_delegationToPart() {
		Connector connector = fixture.getElement("Capsule1::RTConnector1", Connector.class);
		assertThat(connector.getKind(), is(ConnectorKind.DELEGATION_LITERAL));
	}

	@TestModel("validation/connectors.uml")
	@Test
	public void connectorKind_delegationToInternalBehavior() {
		Connector connector = fixture.getElement("Capsule1::RTConnector2", Connector.class);
		assertThat(connector.getKind(), is(ConnectorKind.DELEGATION_LITERAL));
	}

	@TestModel("validation/connectors.uml")
	@Test
	public void connectorKind_assembly() {
		Connector connector = fixture.getElement("Capsule1::RTConnector4", Connector.class);
		assertThat(connector.getKind(), is(ConnectorKind.ASSEMBLY_LITERAL));
	}

	@TestModel("validation/connectors.uml")
	@Test
	public void connectorKind_passThrough() {
		Connector connector = fixture.getElement("Capsule1::RTConnector3", Connector.class);
		assertThat(connector.getKind(), is(ConnectorKind.ASSEMBLY_LITERAL));
	}
}
