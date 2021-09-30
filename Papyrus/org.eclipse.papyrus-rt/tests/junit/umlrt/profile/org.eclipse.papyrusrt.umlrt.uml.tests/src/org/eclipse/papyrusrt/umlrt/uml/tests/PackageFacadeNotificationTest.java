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
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for notifications in the façade class {@link UMLRTPackage}.
 */
@TestModel("inheritance/ports.uml")
@Category({ CapsuleTests.class, FacadeTests.class })
public class PackageFacadeNotificationTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public PackageFacadeNotificationTest() {
		super();
	}

	@Test
	public void nameNotifications() {
		UMLRTPackage package_ = fixture.getRoot();

		fixture.expectNotification(package_, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__NAME, Notification.SET,
				is("ports"), is("NewName"), () -> package_.toUML().setName("NewName"));
	}

	@Test
	public void nestedPackageNotifications() {
		UMLRTPackage package_ = fixture.getRoot();

		UMLRTPackage[] newPackage = { null };
		package_.getNestedPackages(); // Make sure the list exists to notify

		fixture.expectNotification(package_, UMLRTUMLRTPackage.Literals.PACKAGE__NESTED_PACKAGE, Notification.ADD,
				null, fixture.defer(() -> is(newPackage[0])), () -> newPackage[0] = package_.createNestedPackage("NewPackage"));

		fixture.expectNotification(package_, UMLRTUMLRTPackage.Literals.PACKAGE__NESTED_PACKAGE, Notification.REMOVE,
				is(newPackage[0]), null, newPackage[0]::destroy);
	}

	@Test
	public void capsuleNotifications() {
		UMLRTPackage package_ = fixture.getRoot();

		UMLRTCapsule[] newCapsule = { null };
		package_.getCapsules(); // Make sure the list exists to notify

		fixture.expectNotification(package_, UMLRTUMLRTPackage.Literals.PACKAGE__CAPSULE, Notification.ADD,
				null, fixture.defer(() -> is(newCapsule[0])), () -> newCapsule[0] = package_.createCapsule("NewCapsule"));

		fixture.expectNotification(package_, UMLRTUMLRTPackage.Literals.PACKAGE__CAPSULE, Notification.REMOVE,
				is(newCapsule[0]), null, newCapsule[0]::destroy);
	}

	@Test
	public void protocolNotifications() {
		UMLRTPackage package_ = fixture.getRoot();

		UMLRTProtocol[] newProtocol = { null };
		package_.getProtocols(); // Make sure the list exists to notify

		fixture.expectNotification(package_, UMLRTUMLRTPackage.Literals.PACKAGE__PROTOCOL, Notification.ADD,
				null, fixture.defer(() -> is(newProtocol[0])), () -> newProtocol[0] = package_.createProtocol("NewProtocol"));

		fixture.expectNotification(package_, UMLRTUMLRTPackage.Literals.PACKAGE__PROTOCOL, Notification.REMOVE,
				is(newProtocol[0]), null, newProtocol[0]::destroy);
	}
}
