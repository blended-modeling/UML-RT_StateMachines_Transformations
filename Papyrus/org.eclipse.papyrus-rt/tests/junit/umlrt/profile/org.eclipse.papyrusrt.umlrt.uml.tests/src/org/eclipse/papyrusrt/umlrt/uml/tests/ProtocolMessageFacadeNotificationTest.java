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
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.ProtocolTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Parameter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for notifications in the protocol façade class {@link UMLRTProtocolMessage}.
 */
@TestModel("inheritance/ports.uml")
@Category({ ProtocolTests.class, FacadeTests.class })
public class ProtocolMessageFacadeNotificationTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public ProtocolMessageFacadeNotificationTest() {
		super();
	}

	@Test
	public void nameNotifications() {
		UMLRTProtocol protocol = fixture.getRoot().getProtocol("Protocol1");
		UMLRTProtocolMessage message = protocol.getInMessage("greet");

		fixture.expectNotification(message, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__NAME, Notification.SET,
				is("greet"), is("sayHello"), () -> message.toUML().setName("sayHello"));
	}

	@Test
	public void parameterNotifications() {
		UMLRTProtocol protocol = fixture.getRoot().getProtocol("Protocol1");
		UMLRTProtocolMessage message = protocol.getInMessage("greet");

		Parameter[] newParam = { null };

		fixture.expectNotification(message, UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE__PARAMETER, Notification.ADD,
				nullValue(), fixture.defer(() -> is(newParam[0])),
				() -> newParam[0] = message.createParameter("whatToSay", null));

		fixture.expectNotification(message, UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE__PARAMETER, Notification.REMOVE,
				is(newParam[0]), nullValue(),
				() -> newParam[0].destroy());
	}

	@Test
	public void kindNotifications() {
		UMLRTProtocol protocol = fixture.getRoot().getProtocol("Protocol1");
		UMLRTProtocolMessage message = protocol.getInMessage("greet");

		Interface messageSetIn = fixture.getElement("Protocol1::Protocol1", Interface.class);
		Interface messageSetOut = fixture.getElement("Protocol1::Protocol1~", Interface.class);
		Interface messageSetInOut = fixture.getElement("Protocol1::Protocol1IO", Interface.class);

		fixture.expectNotification(message, UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE__KIND, Notification.SET,
				is(RTMessageKind.IN), is(RTMessageKind.IN_OUT),
				() -> messageSetInOut.getOwnedOperations().add(message.toUML()));

		fixture.expectNotification(message, UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE__KIND, Notification.SET,
				is(RTMessageKind.IN_OUT), is(RTMessageKind.OUT),
				() -> messageSetOut.getOwnedOperations().add(message.toUML()));

		fixture.expectNotification(message, UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE__KIND, Notification.SET,
				is(RTMessageKind.OUT), is(RTMessageKind.IN),
				() -> messageSetIn.getOwnedOperations().add(message.toUML()));
	}
}
