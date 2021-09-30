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

import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.hasStereotypeValue;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.named;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the port façade class {@link UMLRTPort}.
 */
@TestModel("inheritance/ports.uml")
@Category({ CapsuleTests.class, FacadeTests.class })
public class PortFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public PortFacadeTest() {
		super();
	}

	@Test
	public void portType() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		List<UMLRTPort> ports = capsule.getPorts();
		assertThat(ports.size(), is(2));

		assertThat(ports.get(0).getType(), is(root.getProtocol("Protocol1")));
		assertThat(ports.get(1).getType(), is(root.getProtocol("Protocol2")));
	}

	@Test
	public void setPortType() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		List<UMLRTPort> ports = capsule.getPorts();
		assertThat(ports.size(), is(2));
		ports.get(0).setType(root.getProtocol("Protocol2"));

		assertThat(ports.get(0).toUML().getType().getName(), is("Protocol2"));
	}

	@Test
	public void portReplication() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTPort port1 = capsule.getPort("protocol1");
		UMLRTPort port2 = capsule.getPort("protocol2");

		assertThat(port1.isSymbolicReplicationFactor(), is(false));
		assertThat(port1.getReplicationFactor(), is(1));
		assertThat(port2.isSymbolicReplicationFactor(), is(false));
		assertThat(port2.getReplicationFactor(), is(4));

		port1.setReplicationFactor(2);

		assertThat(port1.toUML().getLowerValue(), instanceOf(LiteralInteger.class));
		assertThat(((LiteralInteger) port1.toUML().getLowerValue()).getValue(), is(2));
		assertThat(port1.toUML().getUpperValue(), instanceOf(LiteralUnlimitedNatural.class));
		assertThat(((LiteralUnlimitedNatural) port1.toUML().getUpperValue()).getValue(), is(2));
	}

	@Test
	public void portReplicationExpression() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTPort port = capsule.getPort("protocol1");
		Port umlPort = port.toUML();
		OpaqueExpression lowerValue = (OpaqueExpression) umlPort.createLowerValue(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION);
		OpaqueExpression upperValue = (OpaqueExpression) umlPort.createUpperValue(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION);

		lowerValue.getBodies().add("NUM_GREETERS");
		upperValue.getBodies().add("NUM_GREETERS");

		assertThat(port.isSymbolicReplicationFactor(), is(true));
		assertThat(port.getReplicationFactor(), is(1));

		lowerValue.getBodies().set(0, "42");
		upperValue.getBodies().set(0, "42");

		assertThat(port.isSymbolicReplicationFactor(), is(false));
		assertThat(port.getReplicationFactor(), is(42));
	}

	@Test
	public void wired() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTPort port = capsule.getPort("protocol1");
		assertThat(port.isWired(), is(true));
		port.setWired(false);
		assertThat(port.toUML(), hasStereotypeValue("UMLRealTime::RTPort", "isWired", is(false)));
	}

	@Test
	public void registration() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTPort port = capsule.getPort("protocol1");
		assertThat(port.getRegistration(), is(PortRegistrationType.AUTOMATIC));
		port.setRegistration(PortRegistrationType.APPLICATION);
		assertThat(port.toUML(), hasStereotypeValue("UMLRealTime::RTPort", "registration", is(PortRegistrationType.APPLICATION)));
	}

	@Test
	public void conjugation() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTPort port = capsule.getPort("protocol1");
		assertThat(port.isConjugated(), is(false));
		UMLRTProtocol type = port.getType();
		assertThat(type.isConjugate(), is(false));

		port.setConjugated(true);
		assertThat(port.getType().isConjugate(), is(true));
		assertThat(port.getType(), sameInstance(type.getConjugate()));

		assertThat(port.toUML().getType(), named("Protocol1"));
	}

	@Test
	public void typeConjugation() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTPort port = capsule.getPort("protocol1");
		UMLRTProtocol type = port.getType();
		port.setType(type.getConjugate());

		assertThat(port.isConjugated(), is(true));
		assertThat(port.getType().getName(), is("Protocol1~"));
	}

	@Test
	public void externalBehaviorKind() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTPort port = capsule.getPort("protocol1");
		assertThat(port.getKind(), is(UMLRTPortKind.EXTERNAL_BEHAVIOR));
	}

	@Test
	public void internalBehaviorKind() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTPort port = capsule.getPort("protocol1");
		port.setKind(UMLRTPortKind.INTERNAL_BEHAVIOR);
		assertThat(port.toUML().isService(), is(false));
		assertThat(port.getKind(), is(UMLRTPortKind.INTERNAL_BEHAVIOR));
		assertThat(port.toUML().getVisibility(), is(VisibilityKind.PROTECTED_LITERAL));
	}

	@Test
	public void relayKind() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTPort port = capsule.getPort("protocol1");
		port.setKind(UMLRTPortKind.RELAY);
		assertThat(port.toUML().isBehavior(), is(false));
		assertThat(port.getKind(), is(UMLRTPortKind.RELAY));
		assertThat(port.toUML().getVisibility(), is(VisibilityKind.PUBLIC_LITERAL));
	}

	@Test
	public void sapKind() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTPort port = capsule.getPort("protocol1");
		port.setKind(UMLRTPortKind.SAP);
		assertThat(port.toUML(), hasStereotypeValue("UMLRealTime::RTPort", "isWired", is(false)));
		assertThat(port.toUML(), hasStereotypeValue("UMLRealTime::RTPort", "isPublish", is(false)));
		assertThat(port.getKind(), is(UMLRTPortKind.SAP));
		assertThat(port.toUML().getVisibility(), is(VisibilityKind.PROTECTED_LITERAL));
	}

	@Test
	public void sppKind() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");

		UMLRTPort port = capsule.getPort("protocol1");
		port.setKind(UMLRTPortKind.SPP);
		assertThat(port.toUML(), hasStereotypeValue("UMLRealTime::RTPort", "isWired", is(false)));
		assertThat(port.toUML(), hasStereotypeValue("UMLRealTime::RTPort", "isPublish", is(true)));
		assertThat(port.getKind(), is(UMLRTPortKind.SPP));
		assertThat(port.toUML().getVisibility(), is(VisibilityKind.PUBLIC_LITERAL));
	}

	@Test
	public void inheritedElement() {
		UMLRTCapsule rootCapsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTCapsule subcapsule = rootCapsule.getPackage().createCapsule("NewSubcapsule");
		UMLRTCapsule subsubcapsule = rootCapsule.getPackage().createCapsule("NewSubsubcapsule");
		subcapsule.setSuperclass(rootCapsule);
		subsubcapsule.setSuperclass(subcapsule);

		UMLRTPort rootPort = rootCapsule.getPort("protocol1");
		UMLRTPort subPort = subcapsule.getPort("protocol1");
		UMLRTPort subsubPort = subsubcapsule.getPort("protocol1");

		assertThat(subsubPort.getInheritedElement(), is(rootPort));
		subPort.setBehavior(false); // Make a real redefinition here
		assertThat(subsubPort.getInheritedElement(), is(subPort));
	}

	@Test
	public void getRedefinitionOf() {
		UMLRTCapsule rootCapsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTCapsule subcapsule = rootCapsule.getPackage().createCapsule("NewSubcapsule");
		UMLRTCapsule subsubcapsule = rootCapsule.getPackage().createCapsule("NewSubsubcapsule");
		subcapsule.setSuperclass(rootCapsule);
		subsubcapsule.setSuperclass(subcapsule);

		UMLRTPort rootPort = rootCapsule.getPort("protocol1");
		UMLRTPort subsubPort = subsubcapsule.getPort("protocol1");

		assertThat(subsubcapsule.getRedefinitionOf(rootPort), is(subsubPort));

		// Exclusion does not change the fact that it is redefined
		assertThat(subsubPort.exclude(), is(true));
		assertThat(subsubcapsule.getRedefinitionOf(rootPort), is(subsubPort));
	}

	@TestModel("inheritance/connectors.uml")
	@Test
	public void isConnectedInside() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule subcapsule = root.getCapsule("Subcapsule");
		UMLRTCapsule nestedCapsule = root.getCapsule("NestedCapsule");

		// this one is only connected inside
		assertThat(subcapsule.getPort("protocol1").isConnectedInside(), is(true));
		// this one has no connectors
		assertThat(subcapsule.getPort("protocol2").isConnectedInside(), is(false));
		// this one is only connected outside
		assertThat(nestedCapsule.getPort("protocol1").isConnectedInside(), is(false));
	}

	@TestModel("inheritance/connectors.uml")
	@Test
	public void isConnectedOutside() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule subcapsule = root.getCapsule("Subcapsule");
		UMLRTCapsule nestedCapsule = root.getCapsule("NestedCapsule");

		// this one is only connected inside
		assertThat(subcapsule.getPort("protocol1").isConnectedOutside(), is(false));
		// this one has no connectors
		assertThat(subcapsule.getPort("protocol2").isConnectedOutside(), is(false));
		// this one is only connected outside
		assertThat(nestedCapsule.getPort("protocol1").isConnectedOutside(), is(true));
	}

	@TestModel("inheritance/connectors.uml")
	@Test
	public void deletedPortIsNotConnected_bug512183() {
		UMLRTCapsule capsule = UMLRTPackage.getInstance(fixture.getModel()).getCapsule("RootCapsule");

		UMLRTPort port = capsule.getPort("protocol1");

		assumeThat("Port is not connected", port.isConnected(), is(true));

		port.destroy();

		assertThat("Port is still connected", port.isConnected(), is(false));
		assertThat("Port is connected inside", port.isConnectedInside(), is(false));
		assertThat("Port is connected outside", port.isConnectedOutside(), is(false));

		assertThat("Port has inside connectors", port.getInsideConnectors(), not(hasItem(anything())));
		assertThat("Port has outside connectors", port.getOutsideConnectors(), not(hasItem(anything())));
		assertThat("Port has connectors", port.getConnectors(), not(hasItem(anything())));
	}

	@Test
	public void allRedefinitions() {
		UMLRTCapsule rootCapsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTCapsule subcapsule = rootCapsule.getPackage().createCapsule("NewSubcapsule");
		UMLRTCapsule subsubcapsule = rootCapsule.getPackage().createCapsule("NewSubsubcapsule");
		subcapsule.setSuperclass(rootCapsule);
		subsubcapsule.setSuperclass(subcapsule);

		UMLRTPort rootPort = rootCapsule.getPort("protocol1");
		UMLRTPort subPort = subcapsule.getPort("protocol1");
		UMLRTPort subsubPort = subsubcapsule.getPort("protocol1");

		List<UMLRTPort> expected = Arrays.asList(rootPort, subPort, subsubPort);
		List<? extends UMLRTPort> actual = rootPort.allRedefinitions().collect(Collectors.toList());
		// There may be other redefinitions, too. Important to keep the order
		actual.retainAll(expected);
		assertThat(actual, is(expected));
	}
}
