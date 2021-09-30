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

import static org.eclipse.uml2.uml.util.UMLUtil.getStereotypeApplication;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Class;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for notifications from inherited features in stereotypes of redefinitions.
 */
@TestModel("inheritance/ports.uml")
@NoFacade
@Category(CapsuleTests.class)
public class StereotypeInheritanceNotificationTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public StereotypeInheritanceNotificationTest() {
		super();
	}

	@Test
	public void notificationNotification() {
		Class root = fixture.getElement("RootCapsule");
		RTPort redefined = getStereotypeApplication(root.getOwnedPorts().get(0), RTPort.class);
		boolean oldNotification = redefined.isNotification();

		Class subsub = fixture.getElement("Subsubcapsule");
		RTPort port = getStereotypeApplication(subsub.getOwnedPorts().get(0), RTPort.class);

		fixture.expectNotification(port, UMLRealTimePackage.Literals.RT_PORT__IS_NOTIFICATION,
				Notification.SET, is(oldNotification), is(true),
				() -> redefined.setIsNotification(true));
	}

	@Test
	public void publishNotification() {
		Class root = fixture.getElement("RootCapsule");
		RTPort redefined = getStereotypeApplication(root.getOwnedPorts().get(0), RTPort.class);
		boolean oldPublish = redefined.isPublish();

		Class subsub = fixture.getElement("Subsubcapsule");
		RTPort port = getStereotypeApplication(subsub.getOwnedPorts().get(0), RTPort.class);

		fixture.expectNotification(port, UMLRealTimePackage.Literals.RT_PORT__IS_PUBLISH,
				Notification.SET, is(oldPublish), is(true),
				() -> redefined.setIsPublish(true));
	}

	@Test
	public void wiredNotification() {
		Class root = fixture.getElement("RootCapsule");
		RTPort redefined = getStereotypeApplication(root.getOwnedPorts().get(0), RTPort.class);
		boolean oldWired = redefined.isWired();

		Class subsub = fixture.getElement("Subsubcapsule");
		RTPort port = getStereotypeApplication(subsub.getOwnedPorts().get(0), RTPort.class);

		fixture.expectNotification(port, UMLRealTimePackage.Literals.RT_PORT__IS_WIRED,
				Notification.SET, is(oldWired), is(false),
				() -> redefined.setIsWired(false));
	}

	@Test
	public void registrationNotification() {
		Class root = fixture.getElement("RootCapsule");
		RTPort redefined = getStereotypeApplication(root.getOwnedPorts().get(0), RTPort.class);
		PortRegistrationType oldRegistration = redefined.getRegistration();

		Class subsub = fixture.getElement("Subsubcapsule");
		RTPort port = getStereotypeApplication(subsub.getOwnedPorts().get(0), RTPort.class);

		fixture.expectNotification(port, UMLRealTimePackage.Literals.RT_PORT__REGISTRATION,
				Notification.SET, is(oldRegistration), is(PortRegistrationType.APPLICATION),
				() -> redefined.setRegistration(PortRegistrationType.APPLICATION));
	}

	@Test
	public void registrationOverrideNotification() {
		Class root = fixture.getElement("RootCapsule");
		RTPort redefined = getStereotypeApplication(root.getOwnedPorts().get(0), RTPort.class);
		String oldRegistrationOverride = redefined.getRegistrationOverride();

		Class subsub = fixture.getElement("Subsubcapsule");
		RTPort port = getStereotypeApplication(subsub.getOwnedPorts().get(0), RTPort.class);

		fixture.expectNotification(port, UMLRealTimePackage.Literals.RT_PORT__REGISTRATION_OVERRIDE,
				Notification.SET, is(oldRegistrationOverride), is("foo"),
				() -> redefined.setRegistrationOverride("foo"));
	}

	@Test
	public void notificationRedefined() {
		Class root = fixture.getElement("RootCapsule");
		RTPort redefined = getStereotypeApplication(root.getOwnedPorts().get(0), RTPort.class);

		Class subsub = fixture.getElement("Subsubcapsule");
		RTPort port = getStereotypeApplication(subsub.getOwnedPorts().get(0), RTPort.class);
		port.setIsNotification(true);

		fixture.expectNoNotification(port, UMLRealTimePackage.Literals.RT_PORT__IS_NOTIFICATION,
				Notification.SET, anything(), is(true),
				() -> redefined.setIsNotification(true));
	}

	@Test
	public void publishRedefined() {
		Class root = fixture.getElement("RootCapsule");
		RTPort redefined = getStereotypeApplication(root.getOwnedPorts().get(0), RTPort.class);

		Class subsub = fixture.getElement("Subsubcapsule");
		RTPort port = getStereotypeApplication(subsub.getOwnedPorts().get(0), RTPort.class);
		port.setIsPublish(true);

		fixture.expectNoNotification(port, UMLRealTimePackage.Literals.RT_PORT__IS_PUBLISH,
				Notification.SET, anything(), is(true),
				() -> redefined.setIsPublish(true));
	}

	@Test
	public void wiredRedefined() {
		Class root = fixture.getElement("RootCapsule");
		RTPort redefined = getStereotypeApplication(root.getOwnedPorts().get(0), RTPort.class);

		Class subsub = fixture.getElement("Subsubcapsule");
		RTPort port = getStereotypeApplication(subsub.getOwnedPorts().get(0), RTPort.class);
		port.setIsWired(false);

		fixture.expectNoNotification(port, UMLRealTimePackage.Literals.RT_PORT__IS_WIRED,
				Notification.SET, anything(), is(false),
				() -> redefined.setIsWired(false));
	}

	@Test
	public void registrationRedefined() {
		Class root = fixture.getElement("RootCapsule");
		RTPort redefined = getStereotypeApplication(root.getOwnedPorts().get(0), RTPort.class);

		Class subsub = fixture.getElement("Subsubcapsule");
		RTPort port = getStereotypeApplication(subsub.getOwnedPorts().get(0), RTPort.class);
		port.setRegistration(PortRegistrationType.AUTOMATIC_LOCKED);

		fixture.expectNoNotification(port, UMLRealTimePackage.Literals.RT_PORT__REGISTRATION,
				Notification.SET, anything(), is(PortRegistrationType.APPLICATION),
				() -> redefined.setRegistration(PortRegistrationType.APPLICATION));
	}

	@Test
	public void registrationOverrideRedefined() {
		Class root = fixture.getElement("RootCapsule");
		RTPort redefined = getStereotypeApplication(root.getOwnedPorts().get(0), RTPort.class);

		Class subsub = fixture.getElement("Subsubcapsule");
		RTPort port = getStereotypeApplication(subsub.getOwnedPorts().get(0), RTPort.class);
		port.setRegistrationOverride("bar");

		fixture.expectNoNotification(port, UMLRealTimePackage.Literals.RT_PORT__REGISTRATION_OVERRIDE,
				Notification.SET, anything(), is("foo"),
				() -> redefined.setRegistrationOverride("foo"));
	}

	@Test
	public void redefinitionRemoved() {
		Class root = fixture.getElement("RootCapsule");
		RTPort redefined = getStereotypeApplication(root.getOwnedPorts().get(0), RTPort.class);

		Class subsub = fixture.getElement("Subsubcapsule");
		RTPort port = getStereotypeApplication(subsub.getOwnedPorts().get(0), RTPort.class);
		port.getBase_Port().getRedefinedPorts().clear();

		fixture.expectNoNotification(port, UMLRealTimePackage.Literals.RT_PORT__REGISTRATION,
				Notification.SET, anything(), is(PortRegistrationType.APPLICATION),
				() -> redefined.setRegistration(PortRegistrationType.APPLICATION));
	}
}
