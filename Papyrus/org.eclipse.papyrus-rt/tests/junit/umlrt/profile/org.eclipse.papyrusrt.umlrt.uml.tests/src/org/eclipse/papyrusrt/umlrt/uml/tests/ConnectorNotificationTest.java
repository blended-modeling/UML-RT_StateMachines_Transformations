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
import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for notifications in the capsule façade class {@link UMLRTConnector}.
 */
@TestModel("inheritance/connectors.uml")
@Category({ CapsuleTests.class, FacadeTests.class })
public class ConnectorNotificationTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public ConnectorNotificationTest() {
		super();
	}

	@Test
	public void nameNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTConnector connector = capsule.getConnector("connector1");

		fixture.expectNotification(connector, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__NAME, Notification.SET,
				is("connector1"), is("newName"), () -> connector.toUML().setName("newName"));
	}

	@Test
	public void sourceNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTConnector connector = capsule.getConnector("connector1");
		UMLRTPort port = capsule.getPort("protocol2");

		fixture.expectNotification(connector, UMLRTUMLRTPackage.Literals.CONNECTOR__SOURCE, Notification.SET,
				is(connector.getSource()), is(port), () -> connector.toUML().getEnds().get(0).setRole(port.toUML()));
	}

	@Test
	public void sourcePartWithPortNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTConnector connector = capsule.getConnector("connector1");
		UMLRTCapsulePart part = capsule.getCapsulePart("nestedCapsule");

		fixture.expectNotification(connector, UMLRTUMLRTPackage.Literals.CONNECTOR__SOURCE_PART_WITH_PORT, Notification.SET,
				nullValue(), is(part), () -> connector.toUML().getEnds().get(0).setPartWithPort(part.toUML()));
	}

	@Test
	public void targetNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTConnector connector = capsule.getConnector("connector1");
		UMLRTPort port = capsule.getPort("protocol2");

		fixture.expectNotification(connector, UMLRTUMLRTPackage.Literals.CONNECTOR__TARGET, Notification.SET,
				is(connector.getTarget()), is(port), () -> connector.toUML().getEnds().get(1).setRole(port.toUML()));
	}

	@Test
	public void targetPartWithPortNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTConnector connector = capsule.getConnector("connector1");
		UMLRTCapsulePart part = capsule.getCapsulePart("nestedCapsule");

		fixture.expectNotification(connector, UMLRTUMLRTPackage.Literals.CONNECTOR__TARGET_PART_WITH_PORT, Notification.SET,
				is(part), nullValue(), () -> connector.toUML().getEnds().get(1).setPartWithPort(null));
	}

	@Test
	public void sourceReplicationNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTConnector connector = capsule.getConnector("connector1");

		fixture.expectNotification(connector, UMLRTUMLRTPackage.Literals.CONNECTOR__SOURCE_REPLICATION_FACTOR, Notification.SET,
				anything(), is(3), () -> setReplication(connector.toUML().getEnds().get(0), 3));

		fixture.expectNotification(connector, UMLRTUMLRTPackage.Literals.CONNECTOR__SOURCE_REPLICATION_FACTOR, Notification.SET,
				is(3), is(1), () -> destroyReplication(connector.toUML().getEnds().get(0)));
	}

	@Test
	public void targetReplicationNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTConnector connector = capsule.getConnector("connector1");

		fixture.expectNotification(connector, UMLRTUMLRTPackage.Literals.CONNECTOR__TARGET_REPLICATION_FACTOR, Notification.SET,
				anything(), is(3), () -> setReplication(connector.toUML().getEnds().get(1), 3));

		fixture.expectNotification(connector, UMLRTUMLRTPackage.Literals.CONNECTOR__TARGET_REPLICATION_FACTOR, Notification.SET,
				is(3), is(1), () -> destroyReplication(connector.toUML().getEnds().get(1)));
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
