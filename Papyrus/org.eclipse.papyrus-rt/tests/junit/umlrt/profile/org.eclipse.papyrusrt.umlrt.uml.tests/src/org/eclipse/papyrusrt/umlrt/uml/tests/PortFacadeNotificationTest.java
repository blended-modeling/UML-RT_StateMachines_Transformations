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

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for notifications in the capsule façade class {@link UMLRTPort}.
 */
@TestModel("inheritance/ports.uml")
@Category({ CapsuleTests.class, FacadeTests.class })
public class PortFacadeNotificationTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public PortFacadeNotificationTest() {
		super();
	}

	@Test
	public void nameNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__NAME, Notification.SET,
				is("protocol1"), is("newName"), () -> port.toUML().setName("newName"));
	}

	@Test
	public void typeNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");
		UMLRTProtocol protocol2 = capsule.getPackage().getProtocol("Protocol2");

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__TYPE, Notification.SET,
				is(port.getType()), is(protocol2), () -> port.toUML().setType(protocol2.toUML()));
	}

	@Test
	public void replicationNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR, Notification.SET,
				anything(), is(3), () -> setReplication(port.toUML(), 3));

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR, Notification.SET,
				is(3), is(1), () -> setReplication(port.toUML(), 1));

		fixture.expectNoNotification(port, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR, Notification.SET,
				anything(), anything(), () -> destroyReplication(port.toUML()));
	}

	@Test
	public void replicationAsStringNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING, Notification.SET,
				anything(), is("3"), () -> setReplication(port.toUML(), 3));

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING, Notification.SET,
				is("3"), is("1"), () -> setReplication(port.toUML(), 1));

		fixture.expectNoNotification(port, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING, Notification.SET,
				anything(), anything(), () -> destroyReplication(port.toUML()));
	}

	@Test
	public void symbolicReplicationNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING, Notification.SET,
				anything(), is("MAX_PARTS"), () -> setReplication(port.toUML(), "MAX_PARTS"));

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING, Notification.SET,
				is("MAX_PARTS"), is("NUM_PARTS"), () -> setReplication(port.toUML(), "NUM_PARTS"));

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING, Notification.SET,
				is("NUM_PARTS"), is("1"), () -> destroyReplication(port.toUML()));
	}

	@Test
	public void conjugateNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__CONJUGATED, Notification.SET,
				is(port.isConjugated()), is(!port.isConjugated()),
				() -> port.toUML().setIsConjugated(!port.toUML().isConjugated()));
	}

	@Test
	public void conjugateTypeNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__TYPE, Notification.SET,
				is(port.getType()), is(port.getType().getConjugate()),
				() -> port.toUML().setIsConjugated(!port.toUML().isConjugated()));
	}

	@Test
	public void behaviorNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__BEHAVIOR, Notification.SET,
				is(port.isBehavior()), is(!port.isBehavior()),
				() -> port.toUML().setIsBehavior(!port.toUML().isBehavior()));
	}

	@Test
	public void serviceNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__SERVICE, Notification.SET,
				is(port.isService()), is(!port.isService()),
				() -> port.toUML().setIsService(!port.toUML().isService()));
	}

	@Test
	public void wiredNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");
		RTPort stereo = UMLUtil.getStereotypeApplication(port.toUML(), RTPort.class);

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__WIRED, Notification.SET,
				is(port.isWired()), is(!port.isWired()),
				() -> stereo.setIsWired(!stereo.isWired()));
	}

	@Test
	public void publishNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");
		RTPort stereo = UMLUtil.getStereotypeApplication(port.toUML(), RTPort.class);

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__PUBLISH, Notification.SET,
				is(port.isPublish()), is(!port.isPublish()),
				() -> stereo.setIsPublish(!stereo.isPublish()));
	}

	@Test
	public void notificationNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");
		RTPort stereo = UMLUtil.getStereotypeApplication(port.toUML(), RTPort.class);

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__NOTIFICATION, Notification.SET,
				is(port.isNotification()), is(!port.isNotification()),
				() -> stereo.setIsNotification(!stereo.isNotification()));
	}

	@Test
	public void registrationNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");
		RTPort stereo = UMLUtil.getStereotypeApplication(port.toUML(), RTPort.class);

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__REGISTRATION, Notification.SET,
				is(stereo.getRegistration()), is(PortRegistrationType.AUTOMATIC_LOCKED),
				() -> stereo.setRegistration(PortRegistrationType.AUTOMATIC_LOCKED));
	}

	@Test
	public void registrationOverrideNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");
		RTPort stereo = UMLUtil.getStereotypeApplication(port.toUML(), RTPort.class);

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__REGISTRATION_OVERRIDE, Notification.SET,
				is(""), is("whatever"),
				() -> stereo.setRegistrationOverride("whatever"));
	}

	@Test
	public void kindNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTPort port = capsule.getPort("protocol1");
		RTPort stereo = UMLUtil.getStereotypeApplication(port.toUML(), RTPort.class);

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__KIND, Notification.SET,
				is(UMLRTPortKind.EXTERNAL_BEHAVIOR), is(UMLRTPortKind.RELAY),
				() -> port.toUML().setIsBehavior(false));

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__KIND, Notification.SET,
				is(UMLRTPortKind.RELAY), is(UMLRTPortKind.EXTERNAL_BEHAVIOR),
				() -> port.toUML().setIsBehavior(true));

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__KIND, Notification.SET,
				is(UMLRTPortKind.EXTERNAL_BEHAVIOR), is(UMLRTPortKind.INTERNAL_BEHAVIOR),
				() -> port.toUML().setIsService(false));

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__KIND, Notification.SET,
				is(UMLRTPortKind.INTERNAL_BEHAVIOR), is(UMLRTPortKind.SAP),
				() -> stereo.setIsWired(false));

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__KIND, Notification.SET,
				is(UMLRTPortKind.SAP), is(UMLRTPortKind.SPP),
				() -> stereo.setIsPublish(true));

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__KIND, Notification.SET,
				is(UMLRTPortKind.SPP), is(UMLRTPortKind.NULL),
				() -> stereo.setIsWired(true));

		// The isService is still false!
		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__KIND, Notification.SET,
				is(UMLRTPortKind.NULL), is(UMLRTPortKind.INTERNAL_BEHAVIOR),
				() -> stereo.setIsPublish(false));

		fixture.expectNotification(port, UMLRTUMLRTPackage.Literals.PORT__KIND, Notification.SET,
				is(UMLRTPortKind.INTERNAL_BEHAVIOR), is(UMLRTPortKind.EXTERNAL_BEHAVIOR),
				() -> port.toUML().setIsService(true));
	}

	//
	// Test framework
	//

	void setReplication(MultiplicityElement mult, int replication) {
		mult.setLower(replication);
		mult.setUpper(replication);
	}

	void setReplication(MultiplicityElement mult, String replication) {
		if (mult.getLowerValue() instanceof OpaqueExpression) {
			List<String> body = ((OpaqueExpression) mult.getLowerValue()).getBodies();
			if (body.isEmpty()) {
				body.add(replication);
			} else {
				body.set(0, replication);
			}
		} else {
			((OpaqueExpression) mult.createLowerValue(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION))
					.getBodies().add(replication);
		}

		if (mult.getUpperValue() instanceof OpaqueExpression) {
			List<String> body = ((OpaqueExpression) mult.getUpperValue()).getBodies();
			if (body.isEmpty()) {
				body.add(replication);
			} else {
				body.set(0, replication);
			}
		} else {
			((OpaqueExpression) mult.createUpperValue(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION))
					.getBodies().add(replication);
		}
	}

	void destroyReplication(MultiplicityElement mult) {
		if (mult.getLowerValue() != null) {
			mult.getLowerValue().destroy();
		}
		if (mult.getUpperValue() != null) {
			mult.getUpperValue().destroy();
		}
	}
}
