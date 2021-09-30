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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for inheritance of port stereotype features.
 */
@TestModel("inheritance/ports.uml")
@NoFacade
@Category(CapsuleTests.class)
public class StereotypePortInheritanceTest {

	@ClassRule
	public static final ModelFixture fixture = new ModelFixture();

	public StereotypePortInheritanceTest() {
		super();
	}

	@Test
	public void notificationInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);
		RTPort stereo = UMLUtil.getStereotypeApplication(p1, RTPort.class);

		assertThat(stereo.isNotification(), is(false));
		assertThat(stereo.eIsSet(UMLRealTimePackage.Literals.RT_PORT__IS_NOTIFICATION), is(false));

		Class root = fixture.getElement("RootCapsule");
		Port r1 = root.getOwnedPorts().get(0);
		UMLUtil.getStereotypeApplication(r1, RTPort.class).setIsNotification(true);

		assertThat(stereo.isNotification(), is(true));
		assertThat(stereo.eIsSet(UMLRealTimePackage.Literals.RT_PORT__IS_NOTIFICATION), is(false));
	}

	@Test
	public void publishInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);
		RTPort stereo = UMLUtil.getStereotypeApplication(p1, RTPort.class);

		assertThat(stereo.isPublish(), is(false));
		assertThat(stereo.eIsSet(UMLRealTimePackage.Literals.RT_PORT__IS_PUBLISH), is(false));

		Class root = fixture.getElement("RootCapsule");
		Port r1 = root.getOwnedPorts().get(0);
		UMLUtil.getStereotypeApplication(r1, RTPort.class).setIsPublish(true);

		assertThat(stereo.isPublish(), is(true));
		assertThat(stereo.eIsSet(UMLRealTimePackage.Literals.RT_PORT__IS_PUBLISH), is(false));
	}

	@Test
	public void wiredInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);
		RTPort stereo = UMLUtil.getStereotypeApplication(p1, RTPort.class);

		assertThat(stereo.isWired(), is(true));
		assertThat(stereo.eIsSet(UMLRealTimePackage.Literals.RT_PORT__IS_WIRED), is(false));

		Class root = fixture.getElement("RootCapsule");
		Port r1 = root.getOwnedPorts().get(0);
		UMLUtil.getStereotypeApplication(r1, RTPort.class).setIsWired(false);

		assertThat(stereo.isWired(), is(false));
		assertThat(stereo.eIsSet(UMLRealTimePackage.Literals.RT_PORT__IS_WIRED), is(false));
	}

	@Test
	public void registrationInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);
		RTPort stereo = UMLUtil.getStereotypeApplication(p1, RTPort.class);

		assertThat(stereo.getRegistration(), is(PortRegistrationType.AUTOMATIC));
		assertThat(stereo.eIsSet(UMLRealTimePackage.Literals.RT_PORT__REGISTRATION), is(false));

		Class root = fixture.getElement("RootCapsule");
		Port r1 = root.getOwnedPorts().get(0);
		UMLUtil.getStereotypeApplication(r1, RTPort.class).setRegistration(PortRegistrationType.APPLICATION);

		assertThat(stereo.getRegistration(), is(PortRegistrationType.APPLICATION));
		assertThat(stereo.eIsSet(UMLRealTimePackage.Literals.RT_PORT__REGISTRATION), is(false));
	}

	@Test
	public void registrationOverrideInheritance() {
		Class subsub = fixture.getElement("Subsubcapsule");
		Port p1 = subsub.getOwnedPorts().get(0);
		RTPort stereo = UMLUtil.getStereotypeApplication(p1, RTPort.class);

		assertThat(stereo.getRegistrationOverride(), is(""));
		assertThat(stereo.eIsSet(UMLRealTimePackage.Literals.RT_PORT__REGISTRATION_OVERRIDE), is(false));

		Class root = fixture.getElement("RootCapsule");
		Port r1 = root.getOwnedPorts().get(0);
		UMLUtil.getStereotypeApplication(r1, RTPort.class).setRegistrationOverride("foo");

		assertThat(stereo.getRegistrationOverride(), is("foo"));
		assertThat(stereo.eIsSet(UMLRealTimePackage.Literals.RT_PORT__REGISTRATION_OVERRIDE), is(false));
	}
}
