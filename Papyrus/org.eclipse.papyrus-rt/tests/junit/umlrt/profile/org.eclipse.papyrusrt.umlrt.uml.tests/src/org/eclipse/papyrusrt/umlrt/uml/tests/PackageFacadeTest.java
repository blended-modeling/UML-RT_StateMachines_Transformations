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
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.List;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTModel;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.ProtocolTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the package façade class {@link UMLRTPackage}.
 */
@TestModel("inheritance/ports.uml")
@Category({ CapsuleTests.class, ProtocolTests.class, FacadeTests.class })
public class PackageFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public PackageFacadeTest() {
		super();
	}

	@Test
	public void root() {
		UMLRTPackage root = UMLRTModel.getInstance(fixture.getResource()).getRoot();
		assertThat(root, notNullValue());
		assertThat(root.getName(), is("ports"));
	}

	@Test
	public void capsules() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());

		assertThat(model.getCapsules().size(), is(3));

		UMLRTCapsule capsule = UMLRTCapsule.getInstance(fixture.getElement("Subcapsule"));
		assertThat(model.getCapsules(), hasItem(capsule));
	}

	@Test
	public void getCapsule() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());

		UMLRTCapsule capsule = model.getCapsule("Subcapsule");
		assertThat(capsule, notNullValue());
		assertThat(model.getCapsules(), hasItem(capsule));
	}

	@Test
	public void createCapsule() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());

		UMLRTCapsule capsule = model.createCapsule("NewCapsule");
		assertThat(capsule, notNullValue());
		assertThat(model.getCapsules(), hasItem(capsule));
		assertThat(capsule.toUML().isActive(), is(true));
	}

	@Test
	public void nestedPackages() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());

		// Protocol containers are not packages
		assertThat(model.getNestedPackages().size(), is(1));
		assertThat(model.getNestedPackages().get(0).getName(), is("nested"));
	}

	@Test
	public void getPackage() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());

		UMLRTPackage nested = model.getNestedPackage("nested");
		assertThat(nested, notNullValue());
		assertThat(model.getNestedPackages(), hasItem(nested));
	}

	@Test
	public void createPackage() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());

		UMLRTPackage newPackage = model.createNestedPackage("newpackage");
		assertThat(newPackage, notNullValue());
		assertThat(model.getNestedPackages(), hasItem(newPackage));
	}

	@Test
	public void protocols() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());

		List<UMLRTProtocol> protocols = model.getProtocols();
		assertThat(protocols.size(), is(2));
		assertThat(protocols.get(0).getName(), is("Protocol1"));
		assertThat(protocols.get(1).getName(), is("Protocol2"));
	}

	@Test
	public void getProtocol() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());

		UMLRTProtocol protocol2 = model.getProtocol("Protocol2");
		assertThat(protocol2, notNullValue());
		assertThat(model.getProtocols(), hasItem(protocol2));
	}

	@Test
	public void createProtocol() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());

		UMLRTProtocol newProtocol = model.createProtocol("NewProtocol");
		assertThat(newProtocol, notNullValue());
		assertThat(model.getProtocols(), hasItem(newProtocol));

		// The collaboration and three message-set interfaces
		assertThat(newProtocol.toUML().getNearestPackage().getOwnedTypes().size(), is(4));
		assertThat(newProtocol.toUML().getNearestPackage().getOwnedTypes(),
				hasItem(stereotypedAs("UMLRealTime::RTMessageSet")));
	}

	@Test
	public void otherTypes() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());

		Enumeration yesno = fixture.getElement("YesNo");
		assumeThat(yesno, notNullValue());
		Class passive = fixture.getElement("Passivity");
		assumeThat(passive, notNullValue());

		assertThat(model.getCapsule(yesno.getName()), nullValue());
		assertThat(model.getCapsule(passive.getName()), nullValue());
	}
}
