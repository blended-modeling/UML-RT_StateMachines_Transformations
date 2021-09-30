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

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.UMLPackage;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases verifying lazy instantiation (and destruction) of
 * extension elements.
 */
@TestModel("inheritance/ports.uml")
@Category({ CapsuleTests.class, FacadeTests.class })
public class LazyExtensionTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public LazyExtensionTest() {
		super();
	}

	@Test
	@TestModel("no_inheritance.uml")
	public void noExtensionsWhenNotNeeded() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule1 = model.getCapsule("Capsule1");
		UMLRTCapsule capsule2 = model.getCapsule("Capsule2");

		assertThat(capsule1.getSubclasses(), not(hasItem(anything())));
		assertThat(capsule2.getSubclasses(), not(hasItem(anything())));

		// There are no extensions
		assertThat(getExtensionExtent(), not(hasItem(anything())));
	}

	@Test
	public void inheritPorts() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");

		// There are no extensions, yet, for any capsule
		assertThat(getExtensionExtent(), not(hasExtension(UMLPackage.Literals.CLASS)));

		UMLRTCapsule newCapsule = model.createCapsule("NewCapsule");
		newCapsule.setSuperclass(capsule);

		// It has inherited the ports
		List<UMLRTPort> ports = newCapsule.getPorts();
		assertThat(ports.size(), is(2));
		assertThat(ports.get(0).getName(), is("protocol1"));
		assertThat(ports.get(1).getName(), is("protocol2"));

		// Now there is an extension for the capsule
		assertThat(getExtensionExtent(), hasExtension(UMLPackage.Literals.CLASS));
	}

	@Test
	public void uninheritPorts() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");
		UMLRTCapsule newCapsule = model.createCapsule("NewCapsule");
		newCapsule.setSuperclass(capsule);

		// It has inherited the ports
		List<UMLRTPort> ports = newCapsule.getPorts();
		assumeThat(ports.size(), is(2));

		// There is an extension for the capsule
		assumeThat(getExtensionExtent(), hasExtension(UMLPackage.Literals.CLASS));

		newCapsule.setSuperclass(null);

		// There is no longer any need for an extension for the capsule
		assertThat(getExtensionExtent(), not(hasExtension(UMLPackage.Literals.CLASS)));
	}

	/**
	 * Tests the feature-setting implementation for inherited (extension)
	 * features that are not yet initialized. Other tests implicitly
	 * cover the feature-setting for many-value (list) features.
	 */
	@Test
	public void inheritedSingleSetting() {
		UMLRTPackage model = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = model.getCapsule("RootCapsule");

		UMLRTCapsule newCapsule = model.createCapsule("NewCapsule");
		newCapsule.setSuperclass(capsule);

		List<UMLRTPort> ports = newCapsule.getPorts();
		assumeThat(ports.size(), is(2));
		UMLRTPort port = ports.get(1);

		EStructuralFeature.Setting setting = ((InternalEObject) port.toUML()).eSetting(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE);
		assertThat(setting.isSet(), is(false));

		// Access the implicit multiplicity
		assumeThat(port.toUML().getLower(), is(4));

		LiteralString stringValue = fixture.create(UMLPackage.Literals.LITERAL_STRING);
		stringValue.setValue("7");
		setting.set(stringValue);

		assertThat(port.toUML().getLower(), is(7));
	}

	//
	// Test framework
	//

	EList<EObject> getExtensionExtent() {
		Resource result = ExtensionResource.getExtensionExtent(fixture.getModel());
		assertThat("No extension extent", result, notNullValue());
		return result.getContents();
	}

	Matcher<EObject> extendsA(EClass extendedMetaclass) {
		return new BaseMatcher<EObject>() {
			@Override
			public void describeTo(Description description) {
				String name = extendedMetaclass.getName();
				description.appendText("extends a " + name);
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof ExtElement)
						&& extendedMetaclass.isInstance(((ExtElement) item).getExtendedElement());
			}
		};
	}

	Matcher<Iterable<? super EObject>> hasExtension(EClass extendedMetaclass) {
		return hasItem(extendsA(extendedMetaclass));
	}

	Matcher<EObject> extendsThe(Element extendedElement) {
		return new BaseMatcher<EObject>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("extends " + extendedElement);
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof ExtElement)
						&& (((ExtElement) item).getExtendedElement() == extendedElement);
			}
		};
	}

	Matcher<Iterable<? super EObject>> hasExtension(Element extendedElement) {
		return hasItem(extendsThe(extendedElement));
	}
}
