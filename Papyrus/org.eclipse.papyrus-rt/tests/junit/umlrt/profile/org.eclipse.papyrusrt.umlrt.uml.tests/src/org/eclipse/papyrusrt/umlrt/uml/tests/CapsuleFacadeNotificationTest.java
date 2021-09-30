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

package org.eclipse.papyrusrt.umlrt.uml.tests;

import static org.hamcrest.CoreMatchers.is;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for notifications in the capsule façade class {@link UMLRTCapsule}.
 */
@TestModel("inheritance/ports.uml")
@Category({ CapsuleTests.class, FacadeTests.class })
public class CapsuleFacadeNotificationTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public CapsuleFacadeNotificationTest() {
		super();
	}

	@Test
	public void nameNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));

		fixture.expectNotification(capsule, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__NAME, Notification.SET,
				is("RootCapsule"), is("NewName"), () -> capsule.toUML().setName("NewName"));
	}

	@Test
	public void portNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));

		UMLRTPort[] newPort = { null };
		capsule.getPorts(); // Make sure the list exists to notify

		fixture.expectNotification(capsule, UMLRTUMLRTPackage.Literals.CAPSULE__PORT, Notification.ADD,
				null, fixture.defer(() -> is(newPort[0])), () -> newPort[0] = capsule.createPort(capsule.getPackage().getProtocol("Protocol1")));

		fixture.expectNotification(capsule, UMLRTUMLRTPackage.Literals.CAPSULE__PORT, Notification.REMOVE,
				is(newPort[0]), null, newPort[0]::destroy);
	}

	@Test
	@TestModel("inheritance/parts.uml")
	public void capsulePartNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));

		UMLRTCapsulePart[] newPart = { null };
		capsule.getCapsuleParts(); // Make sure the list exists to notify

		fixture.expectNotification(capsule, UMLRTUMLRTPackage.Literals.CAPSULE__CAPSULE_PART, Notification.ADD,
				null, fixture.defer(() -> is(newPart[0])), () -> newPart[0] = capsule.createCapsulePart(capsule.getPackage().getCapsule("Capsule2")));

		fixture.expectNotification(capsule, UMLRTUMLRTPackage.Literals.CAPSULE__CAPSULE_PART, Notification.REMOVE,
				is(newPart[0]), null, newPart[0]::destroy);
	}

	@Test
	@TestModel("inheritance/connectors.uml")
	public void connectorNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPorts().get(0);
		UMLRTCapsulePart part = capsule.getCapsuleParts().get(0);
		UMLRTCapsule nested = part.getType();

		UMLRTConnector[] newConnector = { null };
		capsule.getConnectors(); // Make sure the list exists to notify

		fixture.expectNotification(capsule, UMLRTUMLRTPackage.Literals.CAPSULE__CONNECTOR, Notification.ADD,
				null, fixture.defer(() -> is(newConnector[0])),
				() -> newConnector[0] = capsule.createConnector("NewConnector", port, null, nested.getPorts().get(0), part));

		fixture.expectNotification(capsule, UMLRTUMLRTPackage.Literals.CAPSULE__CONNECTOR, Notification.REMOVE,
				is(newConnector[0]), null, newConnector[0]::destroy);
	}

	@Test
	public void setSuperCapsule() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = model.getCapsule("Subcapsule");
		UMLRTCapsule subsubcapsule = model.getCapsule("Subsubcapsule");

		fixture.expectNotification(subsubcapsule, UMLRTUMLRTPackage.Literals.CAPSULE__SUPERCLASS, Notification.SET,
				is(subcapsule), is(capsule), () -> subsubcapsule.toUML().getGeneralizations().get(0).setGeneral(capsule.toUML()));
	}
}
