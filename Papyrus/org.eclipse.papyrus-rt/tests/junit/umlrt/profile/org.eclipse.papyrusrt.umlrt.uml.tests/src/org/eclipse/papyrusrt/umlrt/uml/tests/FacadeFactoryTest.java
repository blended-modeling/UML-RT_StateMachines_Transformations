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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTModel;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test cases for the façade factory API {@link UMLRTFactory}.
 */
@RunWith(Parameterized.class)
@Category(FacadeTests.class)
public class FacadeFactoryTest {

	@Rule
	public final ModelFixture fixture;

	private final String elementName;
	private final java.lang.Class<?> factoryClass;
	private final java.lang.Class<? extends UMLRTNamedElement> facadeClass;

	public FacadeFactoryTest(String modelPath, String elementName,
			FactoryKind factory, FacadeKind facade) {

		super();

		this.elementName = elementName;
		this.factoryClass = factory.factoryClass;
		this.facadeClass = facade.facadeClass;

		this.fixture = new ModelFixture(modelPath);
	}

	@Test
	public void create() {
		NamedElement element = fixture.getElement(elementName);
		UMLRTNamedElement expected = getFacade(element);
		UMLRTNamedElement actual = createFacade(element);

		if (expected == null) {
			assertThat(actual, nullValue());
		} else {
			assertThat(actual, sameInstance(expected));
			assertModel(element);
		}
	}

	//
	// Test framework
	//

	@Parameters(name = "{2} ==> {3}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ "inheritance/ports.uml", "RootCapsule", FactoryKind.UMLRTFactory, FacadeKind.Capsule },
				{ "inheritance/ports.uml", "RootCapsule::protocol1", FactoryKind.UMLRTFactory, FacadeKind.Port },
				{ "inheritance/parts.uml", "RootCapsule::capsule2", FactoryKind.UMLRTFactory, FacadeKind.CapsulePart },
				{ "inheritance/connectors.uml", "RootCapsule::connector1", FactoryKind.UMLRTFactory, FacadeKind.Connector },
				{ "inheritance/ports.uml", "Protocol1::Protocol1", FactoryKind.UMLRTFactory, FacadeKind.Protocol },
				{ "inheritance/ports.uml", "Protocol1::Protocol1::greet", FactoryKind.UMLRTFactory, FacadeKind.ProtocolMessage },
				{ "inheritance/ports.uml", "nested", FactoryKind.UMLRTFactory, FacadeKind.Package },

				{ "inheritance/ports.uml", "RootCapsule", FactoryKind.CapsuleFactory, FacadeKind.Capsule },
				{ "inheritance/ports.uml", "RootCapsule::protocol1", FactoryKind.CapsuleFactory, FacadeKind.Port },
				{ "inheritance/parts.uml", "RootCapsule::capsule2", FactoryKind.CapsuleFactory, FacadeKind.CapsulePart },
				{ "inheritance/connectors.uml", "RootCapsule::connector1", FactoryKind.CapsuleFactory, FacadeKind.Connector },

				{ "inheritance/ports.uml", "Protocol1::Protocol1", FactoryKind.ProtocolFactory, FacadeKind.Protocol },
				{ "inheritance/ports.uml", "Protocol1::Protocol1::greet", FactoryKind.ProtocolFactory, FacadeKind.ProtocolMessage },
		});
	}

	UMLRTNamedElement getFacade(NamedElement element) {
		UMLRTNamedElement result = null;

		if (facadeClass != null) {
			for (Method next : facadeClass.getMethods()) {
				if (Modifier.isStatic(next.getModifiers())
						&& "getInstance".equals(next.getName())
						&& (next.getParameterTypes().length == 1)
						&& next.getParameterTypes()[0].isInstance(element)) {

					try {
						result = (UMLRTNamedElement) next.invoke(null, element);
					} catch (Exception e) {
						e.printStackTrace();
						fail("Failed to get facade instance: " + e.getMessage());
					}
					break;
				}
			}
		}

		return result;
	}

	UMLRTNamedElement createFacade(NamedElement element) {
		UMLRTNamedElement result = null;

		for (Method next : factoryClass.getMethods()) {
			if (Modifier.isStatic(next.getModifiers())
					&& "create".equals(next.getName())
					&& (next.getParameterTypes().length == 1)
					&& next.getParameterTypes()[0].isInstance(element)) {

				try {
					result = (UMLRTNamedElement) next.invoke(null, element);
				} catch (Exception e) {
					e.printStackTrace();
					fail("Failed to create facade instance: " + e.getMessage());
				}
				break;
			}
		}

		assertThat("No factory method found", result, notNullValue());

		return result;
	}

	void assertModel(NamedElement uml) {
		UMLRTModel expected = UMLRTModel.getInstance(uml.eResource());
		assertThat(UMLRTFactory.create(uml).getModel(), sameInstance(expected));
	}

	//
	// Nested types
	//

	enum FactoryKind {
		UMLRTFactory(UMLRTFactory.class), //
		CapsuleFactory(UMLRTFactory.CapsuleFactory.class), //
		ProtocolFactory(UMLRTFactory.ProtocolFactory.class);

		final Class<?> factoryClass;

		private FactoryKind(Class<?> factoryClass) {
			this.factoryClass = factoryClass;
		}
	}

	enum FacadeKind {
		Capsule(UMLRTCapsule.class), //
		Port(UMLRTPort.class), //
		CapsulePart(UMLRTCapsulePart.class), //
		Connector(UMLRTConnector.class), //
		Protocol(UMLRTProtocol.class), //
		ProtocolMessage(UMLRTProtocolMessage.class), //
		Package(UMLRTPackage.class);

		final Class<? extends UMLRTNamedElement> facadeClass;

		private FacadeKind(Class<? extends UMLRTNamedElement> facadeClass) {
			this.facadeClass = facadeClass;
		}
	}
}
