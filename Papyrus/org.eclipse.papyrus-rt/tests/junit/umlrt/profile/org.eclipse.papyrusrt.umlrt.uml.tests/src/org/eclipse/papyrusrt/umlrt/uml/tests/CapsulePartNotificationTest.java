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
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for notifications in the capsule façade class {@link UMLRTCapsulePart}.
 */
@TestModel("inheritance/parts.uml")
@Category({ CapsuleTests.class, FacadeTests.class })
public class CapsulePartNotificationTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public CapsulePartNotificationTest() {
		super();
	}

	@Test
	public void nameNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTCapsulePart part = capsule.getCapsulePart("capsule2");

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__NAME, Notification.SET,
				is("capsule2"), is("newName"), () -> part.toUML().setName("newName"));
	}

	@Test
	public void typeNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTCapsule capsule4 = UMLRTCapsule.getInstance(fixture.getElement("Capsule4"));
		UMLRTCapsulePart part = capsule.getCapsulePart("capsule2");

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.CAPSULE_PART__TYPE, Notification.SET,
				is(part.getType()), is(capsule4), () -> part.toUML().setType(capsule4.toUML()));
	}

	@Test
	public void optionalNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTCapsulePart part = capsule.getCapsulePart("capsule2");

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.CAPSULE_PART__OPTIONAL, Notification.SET,
				is(false), is(true), () -> part.toUML().setLower(0));

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.CAPSULE_PART__OPTIONAL, Notification.SET,
				is(true), is(false), () -> part.toUML().setLower(1));

		fixture.expectNoNotification(part, UMLRTUMLRTPackage.Literals.CAPSULE_PART__OPTIONAL, Notification.SET,
				anything(), anything(), () -> destroyReplication(part.toUML()));
	}

	@Test
	public void replicationNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTCapsulePart part = capsule.getCapsulePart("capsule2");

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR, Notification.SET,
				anything(), is(3), () -> setReplication(part.toUML(), 3));

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR, Notification.SET,
				is(3), is(1), () -> setReplication(part.toUML(), 1));

		fixture.expectNoNotification(part, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR, Notification.SET,
				anything(), anything(), () -> destroyReplication(part.toUML()));
	}

	@Test
	public void replicationAsStringNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTCapsulePart part = capsule.getCapsulePart("capsule2");

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING, Notification.SET,
				anything(), is("3"), () -> setReplication(part.toUML(), 3));

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING, Notification.SET,
				is("3"), is("1"), () -> setReplication(part.toUML(), 1));

		fixture.expectNoNotification(part, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING, Notification.SET,
				anything(), anything(), () -> destroyReplication(part.toUML()));
	}

	@Test
	public void symbolicReplicationNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTCapsulePart part = capsule.getCapsulePart("capsule2");

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING, Notification.SET,
				anything(), is("MAX_PARTS"), () -> setReplication(part.toUML(), "MAX_PARTS"));

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING, Notification.SET,
				is("MAX_PARTS"), is("NUM_PARTS"), () -> setReplication(part.toUML(), "NUM_PARTS"));

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING, Notification.SET,
				is("NUM_PARTS"), is("1"), () -> destroyReplication(part.toUML()));
	}

	@Test
	public void kindNotifications() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTCapsulePart part = capsule.getCapsulePart("capsule2");

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.CAPSULE_PART__KIND, Notification.SET,
				is(UMLRTCapsulePartKind.FIXED), is(UMLRTCapsulePartKind.OPTIONAL),
				() -> part.toUML().setLower(0));

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.CAPSULE_PART__KIND, Notification.SET,
				is(UMLRTCapsulePartKind.OPTIONAL), is(UMLRTCapsulePartKind.PLUG_IN),
				() -> part.toUML().setAggregation(AggregationKind.SHARED_LITERAL));

		fixture.expectNotification(part, UMLRTUMLRTPackage.Literals.CAPSULE_PART__KIND, Notification.SET,
				is(UMLRTCapsulePartKind.PLUG_IN), is(UMLRTCapsulePartKind.FIXED),
				() -> destroyReplication(part.toUML()));

		// Fixed doesn't care about aggregation
		fixture.expectNoNotification(part, UMLRTUMLRTPackage.Literals.CAPSULE_PART__KIND, Notification.SET,
				anything(), anything(),
				() -> part.toUML().setAggregation(AggregationKind.COMPOSITE_LITERAL));
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
