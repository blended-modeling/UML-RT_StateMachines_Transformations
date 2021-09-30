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

import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.isInherited;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.replicated;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the port façade class {@link UMLRTConnector}.
 */
@TestModel("inheritance/connectors.uml")
@Category({ CapsuleTests.class, FacadeTests.class })
public class ConnectorFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public ConnectorFacadeTest() {
		super();
	}

	@Test
	public void connectorEnds() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");
		UMLRTCapsule nested = root.getCapsule("NestedCapsule");

		UMLRTConnector connector = capsule.getConnector("connector1");
		assertThat(connector, notNullValue());

		UMLRTPort port1 = connector.getSource();
		assertThat(port1, is(capsule.getPort("protocol1")));
		assertThat(connector.getSourcePartWithPort(), nullValue());

		UMLRTPort port2 = connector.getTarget();
		assertThat(port2, is(nested.getPort("protocol1")));

		UMLRTCapsulePart part = capsule.getCapsulePart("nestedCapsule");
		assumeThat(part, notNullValue());

		assertThat(connector.getTargetPartWithPort(), is(part));
	}

	@Test
	public void connectorInherited() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = root.getCapsule("Subcapsule");
		UMLRTCapsule nested = root.getCapsule("NestedCapsule");

		UMLRTConnector connector = subcapsule.getConnector("connector1");
		assertThat(connector, notNullValue());
		assertThat(connector.isVirtualRedefinition(), is(true));

		UMLRTConnector redefined = connector.getRedefinedConnector();
		assertThat(redefined, notNullValue());
		assertThat(redefined.getCapsule(), is(capsule));

		assertThat(connector.getSource(), notNullValue());
		assertThat(connector.getSource(), is(subcapsule.getPort("protocol1")));
		assertThat(connector.getTarget(), notNullValue());
		assertThat(connector.getTarget(), is(nested.getPort("protocol1")));

		assertThat(connector.getTargetPartWithPort(), notNullValue());
		assertThat(connector.getTargetPartWithPort(), is(subcapsule.getCapsulePart("nestedCapsule")));

		ConnectorEnd end = connector.toUML().getEnds().get(0);
		assertThat(UMLRTExtensionUtil.isVirtualElement(end), is(true));
		assertThat(((InternalUMLRTElement) end).rtGetRedefinedElement(), is(redefined.toUML().getEnds().get(0)));
		end = connector.toUML().getEnds().get(1);
		assertThat(UMLRTExtensionUtil.isVirtualElement(end), is(true));
		assertThat(((InternalUMLRTElement) end).rtGetRedefinedElement(), is(redefined.toUML().getEnds().get(1)));
	}

	/**
	 * Verifies that an excluded connector correctly resolves the ports and parts that
	 * it connects, even if those are, themselves, excluded.
	 */
	@Test
	public void excludedConnectorExcludedEnds() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule subcapsule = root.getCapsule("Subcapsule");

		UMLRTConnector connector = subcapsule.getConnector("connector1");
		assumeThat(connector, notNullValue());
		assumeThat(connector.isVirtualRedefinition(), is(true));

		UMLRTPort sourcePort = connector.getSource();
		UMLRTCapsulePart targetPartWithPort = connector.getTargetPartWithPort();

		connector.exclude();
		sourcePort.exclude();
		targetPartWithPort.exclude();

		assumeThat(sourcePort.isExcluded(), is(true));
		assumeThat(targetPartWithPort.isExcluded(), is(true));

		assertThat(connector.getSource(), is(sourcePort));
		assertThat(connector.getTargetPartWithPort(), is(targetPartWithPort));
	}

	@Test
	public void lookingAtInheritedConnectorDoesNotWrite() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = root.getCapsule("Subcapsule");

		UMLRTConnector connector = subcapsule.getConnector("connector1");
		assumeThat(connector, notNullValue());
		assumeThat(connector.isVirtualRedefinition(), is(true));

		List<Notification> notifications = new ArrayList<>();
		fixture.getModel().eAdapters().add(new EContentAdapter() {
			@Override
			public void notifyChanged(Notification notification) {
				super.notifyChanged(notification);

				if (notification.getEventType() != Notification.RESOLVE) {
					notifications.add(notification);
				}
			}
		});

		// Ensure initialization of the redefining ends
		UMLRTConnector redefined = connector.getRedefinedConnector();
		assumeThat(redefined, notNullValue());
		assumeThat(redefined.getCapsule(), is(capsule));

		assumeThat(connector.toUML().getEnds().size(), is(2));
		connector.toUML().getEnds().forEach(end -> {
			assumeThat(((InternalEObject) end).eInternalContainer(), is(connector.toUML()));
		});

		assertThat(notifications, not(hasItem(anything())));
	}

	@Test
	public void connectorReification() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule subcapsule = root.getCapsule("Subcapsule");

		UMLRTConnector connector = subcapsule.getConnector("connector1");
		assertThat(connector, notNullValue());
		assumeThat(connector.isVirtualRedefinition(), is(true));

		connector.setTargetReplicationFactor(2);

		// This reifies the connector and both ends
		ConnectorEnd end = connector.toUML().getEnds().get(0);
		assertThat(UMLRTExtensionUtil.isVirtualElement(end), is(false));
		end = connector.toUML().getEnds().get(1);
		assertThat(UMLRTExtensionUtil.isVirtualElement(end), is(false));
		assertThat(UMLRTExtensionUtil.isVirtualElement(connector.toUML()), is(false));
	}

	@Test
	public void connectorReplication() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule subcapsule = root.getCapsule("Subcapsule");

		UMLRTConnector connector = subcapsule.getConnector("connector1");
		assertThat(connector, notNullValue());
		assumeThat(connector.isVirtualRedefinition(), is(true));

		connector.setTargetReplicationFactor(2);

		assertThat(connector.getTargetReplicationFactor(), is(2));

		// The redefined connector isn't changed, of course
		assertThat(connector.getRedefinedConnector().getTargetReplicationFactor(), is(1));

		ConnectorEnd end = connector.toUML().getEnds().get(1);
		assertThat(end, replicated(2));
	}

	@Test
	public void reificationEvents() {
		List<UMLRTNamedElement> reified = new ArrayList<>();
		List<UMLRTNamedElement> virtualized = new ArrayList<>();

		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule subcapsule = root.getCapsule("Subcapsule");

		UMLRTConnector connector = subcapsule.getConnector("connector1");
		assertThat(connector, notNullValue());
		assumeThat(connector.isVirtualRedefinition(), is(true));

		connector.addReificationListener((e, r) -> (r ? reified : virtualized).add(e));

		// This changes only a connector end and reifies it
		connector.setTargetReplicationFactor(2);

		assertThat(reified, hasItem(connector));
		assertThat(virtualized, not(hasItem(anything())));

		reified.clear();
		virtualized.clear();

		// The only support way to re-virtualize an element
		connector.exclude();
		connector.reinherit();

		assertThat(reified, not(hasItem(anything())));
		assertThat(virtualized, hasItem(connector));

		// The ends are now virtual, too, of course
		ConnectorEnd end = connector.toUML().getEnds().get(0);
		assertThat(UMLRTExtensionUtil.isVirtualElement(end), is(true));
		end = connector.toUML().getEnds().get(1);
		assertThat(UMLRTExtensionUtil.isVirtualElement(end), is(true));
	}

	@Test
	public void portConnectors() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule capsule = root.getCapsule("RootCapsule");
		UMLRTCapsule subcapsule = root.getCapsule("Subcapsule");

		UMLRTPort redefinedPort = capsule.getPort("protocol1");
		UMLRTPort port = subcapsule.getRedefinitionOf(redefinedPort);

		UMLRTCapsulePart redefinedPart = capsule.getCapsulePart("nestedCapsule");
		UMLRTCapsulePart part = subcapsule.getRedefinitionOf(redefinedPart);

		UMLRTPort portOnPart = redefinedPart.getType().getPort("protocol1");

		// Although in UML, the ends of inherited connectors reference the root
		// port definition, we resolve connectors to the proper local capsule context
		List<UMLRTConnector> rootConnectors = redefinedPort.getInsideConnectors();
		assertThat(rootConnectors.size(), is(1)); // In UML, there are three!

		List<UMLRTConnector> connectors = port.getInsideConnectors();
		assertThat(connectors.size(), is(1));

		assertThat(connectors.get(0), not(rootConnectors.get(0)));
		assertThat(connectors.get(0).redefines(rootConnectors.get(0)), is(true));

		assertThat(redefinedPart.getConnectorsOfPorts(), is(rootConnectors));
		assertThat(part.getConnectorsOfPorts(), is(connectors));

		// This port legitimately is connected (on the outside) to all of the connectors
		// because there is only one definition of it (it is not redefined in any context)
		List<UMLRTConnector> outsideConnectors = portOnPart.getOutsideConnectors();
		assertThat(outsideConnectors, hasItems(rootConnectors.get(0), connectors.get(0)));
	}

	/**
	 * Regression test case for resolution of the inheritance of a port by
	 * the capsule type of the part-with-port.
	 * 
	 * @see <a href="http://eclip.se/513808">bug 513808</a>
	 */
	@TestModel("inheritance/InternalPortConnectedToInheritedPort.uml")
	@Test
	public void connectorEndsPortOnPartInherited() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule top = root.getCapsule("Top");
		UMLRTCapsule sub = root.getCapsule("Capsule2");

		UMLRTConnector connector = top.getConnector("RTConnector1");
		assertThat(connector, notNullValue());

		UMLRTPort port1 = connector.getSource();
		assumeThat(port1, is(top.getPort("protocol1")));
		assumeThat(connector.getSourcePartWithPort(), nullValue());

		UMLRTCapsulePart part = top.getCapsulePart("capsule2");
		assumeThat(part, notNullValue());
		assumeThat(connector.getTargetPartWithPort(), is(part));

		UMLRTPort port2 = connector.getTarget();
		assertThat(port2, is(sub.getPort("protocol1")));
	}

	/**
	 * Regression test case for persistence of references to a port inherited by
	 * the capsule type of the part-with-port.
	 * 
	 * @see <a href="http://eclip.se/513808">bug 513808</a>
	 */
	@TestModel("inheritance/InternalPortConnectedToInheritedPort.uml")
	@Test
	public void connectorEndsPortOnPartInheritedPersistence() {
		UMLRTPackage root = UMLRTPackage.getInstance(fixture.getModel());
		UMLRTCapsule top = root.getCapsule("Top");
		UMLRTCapsule sub = root.getCapsule("Capsule2");

		UMLRTConnector connector = top.getConnector("RTConnector1");
		assertThat(connector, notNullValue());

		UMLRTPort port1 = connector.getSource();
		assumeThat(port1, is(top.getPort("protocol1")));
		assumeThat(connector.getSourcePartWithPort(), nullValue());

		UMLRTCapsulePart part = top.getCapsulePart("capsule2");
		assumeThat(part, notNullValue());
		assumeThat(connector.getTargetPartWithPort(), is(part));

		// Set the "target" role
		UMLRTPort port2 = sub.getPort("protocol1");
		assumeThat(port2, isInherited());
		connector.setTarget(port2);

		// It was properly unresolved for persistence
		Port persistedRef = (Port) connector.toUML().getEnds().get(1).eGet(UMLPackage.Literals.CONNECTOR_END__ROLE, false);
		assertThat(persistedRef, notNullValue());
		assertThat("HREF to virtual element", persistedRef, not(UMLMatchers.isInherited()));
	}

	@Test
	public void allRedefinitions() {
		UMLRTCapsule rootCapsule = UMLRTCapsule.getInstance(fixture.getElement("RootCapsule"));
		UMLRTCapsule subcapsule = rootCapsule.getPackage().createCapsule("NewSubcapsule");
		UMLRTCapsule subsubcapsule = rootCapsule.getPackage().createCapsule("NewSubsubcapsule");
		subcapsule.setSuperclass(rootCapsule);
		subsubcapsule.setSuperclass(subcapsule);

		UMLRTConnector rootConnector = rootCapsule.getConnector("connector1");
		UMLRTConnector subConnector = subcapsule.getConnector("connector1");
		UMLRTConnector subsubConnector = subsubcapsule.getConnector("connector1");

		List<UMLRTConnector> expected = Arrays.asList(rootConnector, subConnector, subsubConnector);
		List<? extends UMLRTConnector> actual = rootConnector.allRedefinitions().collect(Collectors.toList());
		// There may be other redefinitions, too. Important to keep the order
		actual.retainAll(expected);
		assertThat(actual, is(expected));
	}

}
