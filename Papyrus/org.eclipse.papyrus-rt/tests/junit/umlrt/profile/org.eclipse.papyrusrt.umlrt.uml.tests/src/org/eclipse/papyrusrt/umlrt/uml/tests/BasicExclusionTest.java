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

import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.stereotypedAs;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTRedefinedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Basic test cases for exclusion.
 */
@TestModel("inheritance/ports.uml")
@Category({ CapsuleTests.class, FacadeTests.class })
public class BasicExclusionTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public BasicExclusionTest() {
		super();
	}

	@Test
	public void portNotExcluded() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");
		UMLRTPort rootPort = capsule.getPorts().get(0);
		UMLRTCapsule subcapsule = capsule.getSubclasses().get(0);
		UMLRTPort subPort = subcapsule.getPort(rootPort.getName());
		UMLRTCapsule subsubcapsule = subcapsule.getSubclasses().get(0);
		UMLRTPort subsubPort = subsubcapsule.getPort(rootPort.getName());

		assertThat(rootPort.isExcluded(), is(false));
		assertThat(subPort.isExcluded(), is(false));
		assertThat(subsubPort.isExcluded(), is(false));
	}

	@Test
	public void excludePort() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");
		UMLRTPort rootPort = capsule.getPorts().get(0);
		UMLRTCapsule subcapsule = capsule.getSubclasses().get(0);
		UMLRTPort subPort = subcapsule.getPort(rootPort.getName());
		UMLRTCapsule subsubcapsule = subcapsule.getSubclasses().get(0);
		UMLRTPort subsubPort = subsubcapsule.getPort(rootPort.getName());

		assumeThat(subPort.isExcluded(), is(false));
		assumeThat(subsubPort.isExcluded(), is(false));

		assertThat(subPort.exclude(), is(true));
		assertThat(getRootFragment(subPort), nullValue());
		assertThat(subPort.exclude(), is(false)); // No effect a second time
		assertThat(subPort.isExcluded(), is(true));

		// Further subclasses now don't inherit the excluded element at all
		assertThat(subsubcapsule.getPorts(), not(hasItem(subsubPort)));
	}

	@Test
	public void reinheritPort() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");
		UMLRTPort rootPort = capsule.getPorts().get(0);
		UMLRTCapsule subcapsule = capsule.getSubclasses().get(0);
		UMLRTPort subPort = subcapsule.getPort(rootPort.getName());
		UMLRTCapsule subsubcapsule = subcapsule.getSubclasses().get(0);
		UMLRTPort subsubPort = subsubcapsule.getPort(rootPort.getName());

		assumeThat(subsubPort.exclude(), is(true));
		assumeThat(subPort.exclude(), is(true));

		assertThat(subPort.reinherit(), is(true));
		assertThat(subPort.toUML(), not(stereotypedAs("UMLRealTime::RTRedefinedElement")));
		assertThat(subPort.reinherit(), is(false)); // No effect a second time
		assertThat(subPort.isExcluded(), is(false));

		// Further subclasses also re-inherit the re-inherited element
		subsubPort = subsubcapsule.getRedefinitionOf(subPort);
		assertThat(subsubPort, notNullValue());
		assertThat(subsubcapsule.getPorts(), hasItem(subsubPort));
		assertThat(subsubPort.isInherited(), is(true));
	}

	@Test
	public void excludedMembers() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");
		UMLRTPort rootPort = capsule.getPorts().get(0);
		UMLRTCapsule subcapsule = capsule.getSubclasses().get(0);
		UMLRTPort subPort = subcapsule.getPort(rootPort.getName());
		UMLRTCapsule subsubcapsule = subcapsule.getSubclasses().get(0);
		UMLRTPort subsubPort = subsubcapsule.getPort(rootPort.getName());

		assumeThat(subPort.exclude(), is(true));

		assertThat(subcapsule.getPorts(), not(hasItem(subPort)));
		assertThat(subcapsule.getPorts().stream().filter(p -> p.redefines(rootPort)).count(), is(0L));
		assertThat(subcapsule.getExcludedElements(), hasItem(subPort));
		assertThat(subcapsule.getExcludedElement("protocol1", UMLRTPort.class), is(subPort));

		// Further subclasses now don't inherit the excluded element at all
		assertThat(subsubcapsule.getPorts(), not(hasItem(subsubPort)));
		assertThat(subsubcapsule.getExcludedElements(), not(hasItem(subsubPort)));
	}

	@Test
	public void exclusionNotifications() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule subcapsule = root.getCapsule("Subcapsule");
		UMLRTPort port = subcapsule.getPort("protocol1");

		fixture.expectNotification(subcapsule.toUML(),
				ExtUMLExtPackage.Literals.ELEMENT__EXCLUDED_ELEMENT,
				Notification.ADD,
				anything(), is(port.toUML()),
				port::exclude);

		fixture.expectNotification(subcapsule.toUML(),
				ExtUMLExtPackage.Literals.ELEMENT__EXCLUDED_ELEMENT,
				Notification.REMOVE,
				is(port.toUML()), anything(),
				port::reinherit);
	}

	//
	// Test framework
	//

	RedefinableElement getRootFragment(UMLRTNamedElement element) {
		NamedElement uml = element.toUML();
		return (uml instanceof RedefinableElement)
				? getRootFragment((RedefinableElement) uml)
				: null;
	}

	RedefinableElement getRootFragment(RedefinableElement element) {
		RTRedefinedElement rtRedef = UMLUtil.getStereotypeApplication(element, RTRedefinedElement.class);
		return (rtRedef == null) ? null : rtRedef.getRootFragment();
	}

}
