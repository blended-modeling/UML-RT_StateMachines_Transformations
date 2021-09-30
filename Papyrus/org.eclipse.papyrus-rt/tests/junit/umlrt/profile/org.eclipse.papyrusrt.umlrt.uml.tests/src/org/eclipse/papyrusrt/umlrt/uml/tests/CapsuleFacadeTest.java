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

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.stereotypedAs;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTRedefinedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InternalFacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the capsule façade class {@link UMLRTCapsule}.
 */
@Category({ CapsuleTests.class, FacadeTests.class })
public class CapsuleFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public CapsuleFacadeTest() {
		super();
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void getQualifiedName() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));

		assertThat(capsule.getQualifiedName(), is("ports::RootCapsule"));
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void getPorts() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));

		List<UMLRTPort> ports = capsule.getPorts();
		assertThat(ports.size(), is(2));
		assertThat(ports.get(0).getName(), is("protocol1"));
		assertThat(ports.get(1).getName(), is("protocol2"));
	}

	@SuppressWarnings("unchecked")
	@Test
	@TestModel("inheritance/ports.uml")
	public void facadeGetPorts() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("Subcapsule"));

		List<UMLRTPort> ports = (List<UMLRTPort>) ((InternalFacadeObject) capsule).facadeGet(UMLRTUMLRTPackage.Literals.CAPSULE__PORT, true);
		assertThat(ports.size(), is(2));
		assertThat(ports.get(0).getName(), is("protocol1"));
		assertThat(ports.get(1).getName(), is("subport"));

		ports.get(1).exclude();

		assumeThat(capsule.getPorts().size(), is(1));
		assumeThat(capsule.getPorts().get(0).getName(), is("protocol1"));

		ports = (List<UMLRTPort>) ((InternalFacadeObject) capsule).facadeGet(UMLRTUMLRTPackage.Literals.CAPSULE__PORT, true);
		assertThat(ports.size(), is(2));
		assertThat(ports.get(0).getName(), is("protocol1"));
		assertThat(ports.get(1).getName(), is("protocol2")); // Excluded port's name reverts to inherited
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void inheritPorts() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");

		UMLRTCapsule newCapsule = model.createCapsule("NewCapsule");
		newCapsule.setSuperclass(capsule);

		// It has inherited the ports
		List<UMLRTPort> ports = newCapsule.getPorts();
		assertThat(ports.size(), is(2));
		assertThat(ports.get(0).getName(), is("protocol1"));
		assertThat(ports.get(1).getName(), is("protocol2"));

		// But they are implicit redefinitions
		ports.stream().map(UMLRTPort::toUML).forEach(port -> {
			// It isn't attached to the model
			assertThat(((InternalEObject) port).eInternalResource(), not(fixture.getResource()));

			// It doesn't actually have the name set
			assertThat(port.isSetName(), is(false));
			assertThat(port.getName(), notNullValue());
		});
	}

	@Test
	@TestModel("inheritance/parts.uml")
	public void getCapsuleParts() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));

		List<UMLRTCapsulePart> parts = capsule.getCapsuleParts();
		assertThat(parts.size(), is(2));
		assertThat(parts.get(0).getName(), is("capsule2"));
		assertThat(parts.get(1).getName(), is("capsule3"));
	}

	@SuppressWarnings("unchecked")
	@Test
	@TestModel("inheritance/parts.uml")
	public void facadeGetCapsuleParts() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("Subcapsule"));

		List<UMLRTCapsulePart> parts = (List<UMLRTCapsulePart>) ((InternalFacadeObject) capsule).facadeGet(UMLRTUMLRTPackage.Literals.CAPSULE__CAPSULE_PART, true);
		assertThat(parts.size(), is(2));
		assertThat(parts.get(0).getName(), is("capsule2"));
		assertThat(parts.get(1).getName(), is("subpart"));

		parts.get(0).exclude();

		assumeThat(capsule.getCapsuleParts().size(), is(1));
		assumeThat(capsule.getCapsuleParts().get(0).getName(), is("subpart"));

		parts = (List<UMLRTCapsulePart>) ((InternalFacadeObject) capsule).facadeGet(UMLRTUMLRTPackage.Literals.CAPSULE__CAPSULE_PART, true);
		assertThat(parts.size(), is(2));
		assertThat(parts.get(0).getName(), is("capsule2"));
		assertThat(parts.get(1).getName(), is("subpart"));
	}

	@Test
	@TestModel("inheritance/parts.uml")
	public void inheritCapsuleParts() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");

		UMLRTCapsule newCapsule = model.createCapsule("NewCapsule");
		newCapsule.setSuperclass(capsule);

		// It has inherited the parts
		List<UMLRTCapsulePart> parts = newCapsule.getCapsuleParts();
		assertThat(parts.size(), is(2));
		assertThat(parts.get(0).getName(), is("capsule2"));
		assertThat(parts.get(1).getName(), is("capsule3"));

		// But they are implicit redefinitions
		parts.stream().map(UMLRTCapsulePart::toUML).forEach(part -> {
			// It isn't attached to the model
			assertThat(((InternalEObject) part).eInternalResource(), not(fixture.getResource()));

			// It doesn't actually have the name set
			assertThat(part.isSetName(), is(false));
			assertThat(part.getName(), notNullValue());
		});
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void addPortToAncestorCapsule() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = model.getCapsule("Subcapsule");
		UMLRTCapsule subsubcapsule = model.getCapsule("Subsubcapsule");

		UMLRTProtocol newProtocol = model.createProtocol("NewProtocol");
		UMLRTPort newPort = capsule.createPort(newProtocol);

		// This new port is inherited all down the line
		UMLRTPort subport = subcapsule.getPort("newProtocol");
		assertThat(subport, notNullValue());
		assertThat("port was reified", UMLRTExtensionUtil.isVirtualElement(subport), is(true));
		assertThat(subport.getRedefinedPort(), is(newPort));
		UMLRTPort subsubport = subsubcapsule.getPort("newProtocol");
		assertThat(subsubport, notNullValue());
		assertThat("port was reified", UMLRTExtensionUtil.isVirtualElement(subsubport), is(true));
		assertThat(subsubport.getRedefinedPort(), is(subport));
	}

	@Test
	@TestModel("inheritance/parts.uml")
	public void addCapsulePartToAncestorCapsule() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = model.getCapsule("Subcapsule");
		UMLRTCapsule subsubcapsule = model.getCapsule("Subsubcapsule");

		UMLRTCapsule newCapsule = model.createCapsule("NewCapsule");
		UMLRTCapsulePart newPart = capsule.createCapsulePart(newCapsule);

		// This new part is inherited all down the line
		UMLRTCapsulePart subpart = subcapsule.getCapsulePart("newCapsule");
		assertThat(subpart, notNullValue());
		assertThat("capsule-part was reified", UMLRTExtensionUtil.isVirtualElement(subpart), is(true));
		assertThat(subpart.getRedefinedPart(), is(newPart));
		UMLRTCapsulePart subsubpart = subsubcapsule.getCapsulePart("newCapsule");
		assertThat(subsubpart, notNullValue());
		assertThat("capsule-part was reified", UMLRTExtensionUtil.isVirtualElement(subsubpart), is(true));
		assertThat(subsubpart.getRedefinedPart(), is(subpart));
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void getSuperCapsule() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = model.getCapsule("Subcapsule");
		UMLRTCapsule subsubcapsule = model.getCapsule("Subsubcapsule");

		assertThat(subsubcapsule.getSuperclass(), is(subcapsule));
		assertThat(capsule.getSuperclass(), nullValue());
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void setSuperCapsule() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");
		UMLRTCapsule subsubcapsule = model.getCapsule("Subsubcapsule");

		subsubcapsule.setSuperclass(capsule);
		assertThat(subsubcapsule.toUML().getSuperClasses(), is(singletonList(capsule.toUML())));
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void isSuperTypeOf() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = model.getCapsule("Subcapsule");
		UMLRTCapsule subsubcapsule = model.getCapsule("Subsubcapsule");

		assertThat(capsule.isSuperTypeOf(capsule), is(true));
		assertThat(capsule.isSuperTypeOf(subcapsule), is(true));
		assertThat(capsule.isSuperTypeOf(subsubcapsule), is(true));
		assertThat(subcapsule.isSuperTypeOf(capsule), is(false));
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void getSubCapsules() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = model.getCapsule("Subcapsule");

		assertThat(capsule.getSubclasses(), is(singletonList(subcapsule)));
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void getHierarchy() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = model.getCapsule("Subcapsule");
		UMLRTCapsule subsubcapsule = model.getCapsule("Subsubcapsule");

		assertThat(capsule.getHierarchy().collect(toList()), is(Arrays.asList(capsule, subcapsule, subsubcapsule)));
		assertThat(subcapsule.getHierarchy().collect(toList()), is(Arrays.asList(subcapsule, subsubcapsule)));
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void getAncestry() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = model.getCapsule("Subcapsule");
		UMLRTCapsule subsubcapsule = model.getCapsule("Subsubcapsule");

		assertThat(capsule.getAncestry(),
				is(Collections.singletonList(capsule)));
		assertThat(subcapsule.getAncestry(),
				is(Arrays.asList(subcapsule, capsule)));
		assertThat(subsubcapsule.getAncestry(),
				is(Arrays.asList(subsubcapsule, subcapsule, capsule)));
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void virtualElementsAreNotStereotypedAsRTRedefinedElements() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");

		UMLRTCapsule newCapsule = model.createCapsule("NewCapsule");
		newCapsule.setSuperclass(capsule);

		// It has inherited the ports
		List<UMLRTPort> ports = newCapsule.getPorts();
		assumeThat(ports.size(), is(2));
		assumeThat(ports.get(0).getName(), is("protocol1"));
		assumeThat(ports.get(1).getName(), is("protocol2"));

		// They are not stereotyped as «RTRedefinedElement»
		ports.stream().map(UMLRTPort::toUML).forEach(port -> {
			assertThat(port, stereotypedAs("UMLRealTime::RTPort"));
			assertThat(port, not(stereotypedAs("UMLRealTime::RTRedefinedElement")));
		});
	}

	@Test
	@TestModel("inheritance/ports.uml")
	public void reifiedElementsAreStereotypedAsRTRedefinedElements() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");
		UMLRTPort rootPort = model.getCapsule("RootCapsule").getPort("protocol1");

		UMLRTCapsule newCapsule = model.createCapsule("NewCapsule");
		newCapsule.setSuperclass(capsule);

		// It has inherited the ports
		UMLRTPort newPort = newCapsule.getPort("protocol1");
		assumeThat(newPort, notNullValue());

		newPort.reify();
		assertThat(newPort.toUML(), stereotypedAs("UMLRealTime::RTRedefinedElement"));
		RTRedefinedElement rtRedef = UMLUtil.getStereotypeApplication(newPort.toUML(), RTRedefinedElement.class);

		assertThat(rtRedef.getRootFragment(), is(rootPort.toUML()));
	}

	@SuppressWarnings("unchecked")
	@Test
	@TestModel("inheritance/connectors.uml")
	public void facadeGetConnectors() {
		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("Subcapsule"));

		List<UMLRTConnector> connectors = (List<UMLRTConnector>) ((InternalFacadeObject) capsule).facadeGet(UMLRTUMLRTPackage.Literals.CAPSULE__CONNECTOR, true);
		assertThat(connectors.size(), is(1));
		assertThat(connectors.get(0).getName(), is("connector1"));

		connectors.get(0).exclude();

		assumeThat(capsule.getConnectors().size(), is(0));

		connectors = (List<UMLRTConnector>) ((InternalFacadeObject) capsule).facadeGet(UMLRTUMLRTPackage.Literals.CAPSULE__CONNECTOR, true);
		assertThat(connectors.size(), is(1));
		assertThat(connectors.get(0).getName(), is("connector1"));
	}
}
