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

package org.eclipse.papyrusrt.umlrt.uml.util.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.NoFacade;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Rule;
import org.junit.Test;

/**
 * Specific regression test cases for the {@link UMLRTExtensionUtil} class which,
 * of course, is also indirectly covered by many tests.
 */
@TestModel("inheritance/ports.uml")
@NoFacade
public class UMLRTExtensionUtilTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public UMLRTExtensionUtilTest() {
		super();
	}

	@Test
	public void touch() {
		Class rootCapsule = fixture.getElement("RootCapsule");
		Class newCapsule = fixture.createElement("NewCapsule", Class.class);
		newCapsule.createGeneralization(rootCapsule);

		Port inherited = (Port) newCapsule.getMember("protocol1");
		assumeThat("Inherited port not found", inherited, notNullValue());
		assumeThat("Inherited port not virtual", UMLRTExtensionUtil.isVirtualElement(inherited), is(true));

		UMLRTExtensionUtil.touch(inherited);
		assertThat("Inherited port still virtual", UMLRTExtensionUtil.isVirtualElement(inherited), is(false));
		assertThat(((InternalEObject) inherited).eInternalContainer(), is(newCapsule));
	}

	@Test
	public void getRedefinedElement() {
		Class rootCapsule = fixture.getElement("RootCapsule");
		Port rootPort = rootCapsule.getOwnedPort("protocol1", null);
		Class newCapsule = fixture.createElement("NewCapsule", Class.class);
		newCapsule.createGeneralization(rootCapsule);

		// Control case
		assertThat(UMLRTExtensionUtil.getRedefinedElement(rootPort), nullValue());

		Port inherited = (Port) newCapsule.getMember("protocol1");
		assumeThat("Inherited port not found", inherited, notNullValue());

		assertThat(UMLRTExtensionUtil.getRedefinedElement(inherited), is(rootPort));
	}

	@Test
	public void getRootDefinition() {
		Class rootCapsule = fixture.getElement("RootCapsule");
		Port rootPort = rootCapsule.getOwnedPort("protocol1", null);
		Class newCapsule = fixture.createElement("NewCapsule", Class.class);
		newCapsule.createGeneralization(rootCapsule);
		Class newNewCapsule = fixture.createElement("NewNewCapsule", Class.class);
		newNewCapsule.createGeneralization(newCapsule);

		// Control case
		assertThat(UMLRTExtensionUtil.getRootDefinition(rootPort), is(rootPort));

		Port inherited = (Port) newNewCapsule.getMember("protocol1");
		assumeThat("Inherited port not found", inherited, notNullValue());

		assertThat(UMLRTExtensionUtil.getRootDefinition(inherited), is(rootPort));
	}

	@Test
	public void redefines() {
		Class rootCapsule = fixture.getElement("RootCapsule");
		Port rootPort = rootCapsule.getOwnedPort("protocol1", null);
		Class newCapsule = fixture.createElement("NewCapsule", Class.class);
		newCapsule.createGeneralization(rootCapsule);
		Class newNewCapsule = fixture.createElement("NewNewCapsule", Class.class);
		newNewCapsule.createGeneralization(newCapsule);

		// Control case
		assertThat(UMLRTExtensionUtil.redefines(rootPort, rootPort), is(true));

		Port inherited = (Port) newCapsule.getMember("protocol1");
		assumeThat("Inherited port not found", inherited, notNullValue());

		Port bottomInherited = (Port) newNewCapsule.getMember("protocol1");
		assumeThat("Inherited port not found", bottomInherited, notNullValue());

		assertThat(UMLRTExtensionUtil.redefines(bottomInherited, inherited), is(true));
		assertThat(UMLRTExtensionUtil.redefines(bottomInherited, rootPort), is(true));
	}

	@NoFacade(false)
	@Test
	public void inheritanceSafeContentsList() {
		UMLRTProtocol protocol1 = fixture.getRoot().getProtocol("Protocol1");
		UMLRTProtocol protocol2 = fixture.getRoot().getProtocol("Protocol2");
		protocol2.setSuperProtocol(protocol1);

		Operation greet = protocol2.getMessage("greet").toUML();

		assertThat(greet.eIsSet(UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER), is(false));

		List<Parameter> params = UMLRTExtensionUtil.getUMLRTContents(greet, UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER);
		assertThat(params.isEmpty(), is(false)); // Sensitive to unset features
		assertThat(params.size(), is(1)); // Sensitive to unset features
		assertThat(params.get(0).getName(), is("data"));

		// And same for the other variant of the API
		params = UMLRTExtensionUtil.getUMLRTContents(greet, Collections.singletonList(UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER));
		assertThat(params.isEmpty(), is(false)); // Sensitive to unset features
		assertThat(params.size(), is(1)); // Sensitive to unset features
		assertThat(params.get(0).getName(), is("data"));
	}

	@NoFacade(false)
	@Test
	public void contentsListForAttributes() {
		UMLRTProtocol protocol1 = fixture.getRoot().getProtocol("Protocol1");
		UMLRTProtocol protocol2 = fixture.getRoot().getProtocol("Protocol2");
		protocol2.setSuperProtocol(protocol1);

		List<?> values = UMLRTExtensionUtil.getUMLRTContents(protocol2.toUML(),
				UMLPackage.Literals.NAMED_ELEMENT__NAME,
				UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY,
				UMLPackage.Literals.CLASSIFIER__IS_ABSTRACT);

		assertThat(values.size(), is(3));
		assertThat(values, is(Arrays.asList("Protocol2", VisibilityKind.PUBLIC_LITERAL, false)));
	}

	@NoFacade(false)
	@Test
	public void contentsListForCrossReferences() {
		UMLRTProtocol protocol1 = fixture.getRoot().getProtocol("Protocol1");
		UMLRTProtocol protocol2 = fixture.getRoot().getProtocol("Protocol2");
		protocol2.setSuperProtocol(protocol1);

		List<?> refs = UMLRTExtensionUtil.getUMLRTContents(protocol2.toUML(),
				UMLPackage.Literals.CLASSIFIER__GENERAL,
				UMLPackage.Literals.NAMED_ELEMENT__NAMESPACE);

		assertThat(refs.size(), is(2));
		assertThat(refs, is(Arrays.asList(protocol1.toUML(), protocol2.toUML().getNearestPackage())));
	}

	@NoFacade(false)
	@Test
	@TestModel("inheritance/connectors.uml")
	public void contentsListForInheritedCrossReferences() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTConnector connector = capsule.getConnector("connector1");
		UMLRTCapsule nested = fixture.getRoot().getCapsule("NestedCapsule");

		List<?> refs = UMLRTExtensionUtil.getUMLRTContents(connector.toUML().getEnds().get(1),
				UMLPackage.Literals.CONNECTOR_END__ROLE,
				UMLPackage.Literals.CONNECTOR_END__PART_WITH_PORT);

		assertThat(refs.size(), is(2));
		assertThat(refs, is(Arrays.asList(nested.getPort("protocol1").toUML(),
				capsule.getCapsulePart("nestedCapsule").toUML())));
	}
}
