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
import static org.hamcrest.CoreMatchers.nullValue;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.ProtocolTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for notifications in the protocol façade class {@link UMLRTProtocol}.
 */
@TestModel("inheritance/ports.uml")
@Category({ ProtocolTests.class, FacadeTests.class })
public class ProtocolFacadeNotificationTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public ProtocolFacadeNotificationTest() {
		super();
	}

	@Test
	public void nameNotifications() {
		UMLRTProtocol protocol = fixture.getRoot().getProtocol("Protocol1");

		fixture.expectNotification(protocol, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__NAME, Notification.SET,
				is("Protocol1"), is("NewName"), () -> protocol.toUML().setName("NewName"));
	}

	@Test
	public void inMessageNotifications() {
		messageNotifications(RTMessageKind.IN, UMLRTUMLRTPackage.Literals.PROTOCOL__IN_MESSAGE);
	}

	void messageNotifications(RTMessageKind kind, EReference reference) {
		UMLRTProtocol protocol = fixture.getRoot().getProtocol("Protocol1");

		UMLRTProtocolMessage[] newMessage = { null };
		((EObject) protocol).eGet(reference); // Make sure the list exists to notify

		fixture.expectNotification(protocol, reference, Notification.ADD,
				null, fixture.defer(() -> is(newMessage[0])),
				() -> newMessage[0] = protocol.createMessage(kind, "newMsg"));

		fixture.expectNotification(protocol, reference, Notification.REMOVE,
				is(newMessage[0]), null, newMessage[0]::destroy);
	}

	@Test
	public void outMessageNotifications() {
		messageNotifications(RTMessageKind.OUT, UMLRTUMLRTPackage.Literals.PROTOCOL__OUT_MESSAGE);
	}

	@Test
	public void inOutMessageNotifications() {
		messageNotifications(RTMessageKind.IN_OUT, UMLRTUMLRTPackage.Literals.PROTOCOL__IN_OUT_MESSAGE);
	}

	@Test
	public void setSuperProtocol() {
		UMLRTProtocol protocol1 = fixture.getRoot().getProtocol("Protocol1");
		UMLRTProtocol protocol2 = fixture.getRoot().getProtocol("Protocol2");

		fixture.expectNotification(protocol2, UMLRTUMLRTPackage.Literals.PROTOCOL__SUPER_PROTOCOL, Notification.SET,
				nullValue(), is(protocol1), () -> protocol2.toUML().createGeneralization(protocol1.toUML()));
	}
}
