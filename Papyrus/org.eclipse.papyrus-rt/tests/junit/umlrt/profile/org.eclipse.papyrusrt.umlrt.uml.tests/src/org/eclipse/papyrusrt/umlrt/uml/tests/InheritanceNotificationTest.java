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
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for notifications from inherited features in redefinitions.
 */
@TestModel("inheritance/ports.uml")
@NoFacade
@Category(CapsuleTests.class)
public class InheritanceNotificationTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public InheritanceNotificationTest() {
		super();
	}

	@Test
	public void nameNotification() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);

		fixture.expectNotification(port, UMLPackage.Literals.NAMED_ELEMENT__NAME,
				Notification.SET, anything(), is("newPortName"),
				() -> redefined.setName("newPortName"));
	}

	@Test
	public void visibilityNotification() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);

		fixture.expectNotification(port, UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY,
				Notification.SET, anything(), is(VisibilityKind.PACKAGE_LITERAL),
				() -> redefined.setVisibility(VisibilityKind.PACKAGE_LITERAL));
	}

	@Test
	public void typeNotification() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);
		Type oldType = redefined.getType();

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);

		fixture.expectNotification(port, UMLPackage.Literals.TYPED_ELEMENT__TYPE,
				Notification.SET, is(oldType), nullValue(),
				() -> redefined.setType(null));
	}

	@Test
	public void replicationNotification() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);
		redefined.setUpper(2);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);
		assumeThat(port.getUpper(), is(2));

		fixture.expectNotification(port.getUpperValue(), UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE,
				Notification.SET, is(2), is(3),
				() -> redefined.setUpper(3));
	}

	@Test
	public void replicationInheritanceSymbolicString() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);
		redefined.setUpper(2);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);
		assumeThat(port.getUpperValue(), instanceOf(LiteralUnlimitedNatural.class));

		fixture.expectNotification(port, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE,
				Notification.SET, instanceOf(LiteralUnlimitedNatural.class), instanceOf(LiteralString.class),
				() -> ((LiteralString) redefined.createUpperValue(null, null, UMLPackage.Literals.LITERAL_STRING))
						.setValue("NUM_PORTS"));

		assertThat(port.getUpperValue(), instanceOf(LiteralString.class));
		assertThat(port.getUpperValue().eIsSet(UMLPackage.Literals.LITERAL_STRING__VALUE), is(false));
		assertThat(port.getUpperValue().stringValue(), is("NUM_PORTS"));
	}

	@Test
	public void replicationInheritanceSymbolicExpression() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);
		redefined.setUpper(2);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);
		assumeThat(port.getUpperValue(), instanceOf(LiteralUnlimitedNatural.class));

		fixture.expectNotification(port, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE,
				Notification.SET, instanceOf(LiteralUnlimitedNatural.class), instanceOf(OpaqueExpression.class),
				() -> {
					OpaqueExpression expr = ((OpaqueExpression) redefined.createUpperValue(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION));
					expr.getBodies().add("NUM_PORTS");
					expr.getLanguages().add("C++");
				});

		assertThat(port.getUpperValue(), instanceOf(OpaqueExpression.class));
		assertThat(port.getUpperValue().eIsSet(UMLPackage.Literals.OPAQUE_EXPRESSION__BODY), is(false));
		assertThat(port.getUpperValue().eIsSet(UMLPackage.Literals.OPAQUE_EXPRESSION__LANGUAGE), is(false));
		OpaqueExpression expr = (OpaqueExpression) port.getUpperValue();
		assertThat(expr.getBodies().size(), is(1));
		assertThat(expr.getBodies(), hasItem("NUM_PORTS"));
		assertThat(expr.getLanguages().size(), is(1));
		assertThat(expr.getLanguages(), hasItem("C++"));
	}

	@Test
	public void serviceNotification() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);
		boolean oldService = redefined.isService();

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);

		fixture.expectNotification(port, UMLPackage.Literals.PORT__IS_SERVICE,
				Notification.SET, is(oldService), is(false),
				() -> redefined.setIsService(false));
	}

	@Test
	public void behaviorNotification() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);
		boolean oldBehavior = redefined.isBehavior();

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);

		fixture.expectNotification(port, UMLPackage.Literals.PORT__IS_BEHAVIOR,
				Notification.SET, is(oldBehavior), is(false),
				() -> redefined.setIsBehavior(false));
	}

	@Test
	public void conjugatedNotification() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);
		boolean oldConjugated = redefined.isConjugated();

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);

		fixture.expectNotification(port, UMLPackage.Literals.PORT__IS_CONJUGATED,
				Notification.SET, is(oldConjugated), is(true),
				() -> redefined.setIsConjugated(true));
	}

	@Test
	public void nameRedefined() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);
		port.setName("redefining");

		fixture.expectNoNotification(port, UMLPackage.Literals.NAMED_ELEMENT__NAME,
				Notification.SET, anything(), is("newPortName"),
				() -> redefined.setName("newPortName"));
	}

	@Test
	public void visibilityRedefined() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);
		port.setVisibility(VisibilityKind.PACKAGE_LITERAL);

		fixture.expectNoNotification(port, UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY,
				Notification.SET, anything(), is(VisibilityKind.PRIVATE_LITERAL),
				() -> redefined.setVisibility(VisibilityKind.PRIVATE_LITERAL));
	}

	@Test
	public void typeRedefined() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);
		port.setType(fixture.getElement("Protocol2::Protocol2"));

		fixture.expectNoNotification(port, UMLPackage.Literals.TYPED_ELEMENT__TYPE,
				Notification.SET, anything(), nullValue(),
				() -> redefined.setType(null));
	}

	@Test
	public void serviceRedefined() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);
		port.setIsService(false);

		fixture.expectNoNotification(port, UMLPackage.Literals.PORT__IS_SERVICE,
				Notification.SET, anything(), is(false),
				() -> redefined.setIsService(false));
	}

	@Test
	public void behaviorRedefined() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);
		port.setIsBehavior(false);

		fixture.expectNoNotification(port, UMLPackage.Literals.PORT__IS_BEHAVIOR,
				Notification.SET, anything(), is(false),
				() -> redefined.setIsBehavior(false));
	}

	@Test
	public void conjugatedRedefined() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);
		port.setIsConjugated(true);

		fixture.expectNoNotification(port, UMLPackage.Literals.PORT__IS_CONJUGATED,
				Notification.SET, anything(), is(true),
				() -> redefined.setIsConjugated(true));
	}

	@Test
	public void replicationRedefined() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);
		redefined.setUpper(2);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);
		port.setUpper(4);

		fixture.expectNoNotification(port.getUpperValue(), UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE,
				Notification.SET, anything(), anything(),
				() -> redefined.setUpper(3));
	}

	@Test
	public void replicationRedefinedExplicitOnTheUpperValue() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);
		redefined.setUpper(2);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);
		((LiteralUnlimitedNatural) port.getUpperValue()).setValue(4);

		fixture.expectNoNotification(port.getUpperValue(), UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE,
				Notification.SET, anything(), anything(),
				() -> redefined.setUpper(3));
	}

	@Test
	public void redefinitionRemoved() {
		Class root = fixture.getElement("RootCapsule");
		Port redefined = root.getOwnedPorts().get(0);

		Class subsub = fixture.getElement("Subsubcapsule");
		Port port = subsub.getOwnedPorts().get(0);
		port.getRedefinedPorts().clear();

		fixture.expectNoNotification(port, UMLPackage.Literals.NAMED_ELEMENT__NAME,
				Notification.SET, anything(), is("newPortName"),
				() -> redefined.setName("newPortName"));
	}
}
