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

import static java.util.stream.Collectors.toList;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers.isInherited;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Regression test cases for the inheritance of "outside" connectors on
 * ports on parts that are, themselves, inherited by redefining parts.
 */
@TestModel("inheritance/StructureInheritanceExample.uml")
@Category({ CapsuleTests.class, FacadeTests.class })
@RunWith(Parameterized.class)
public class PortOnPartConnectorInheritanceTest {

	@ClassRule
	public static final ModelFixture fixture = new ModelFixture();

	private final boolean isInheritance;
	private final boolean isPartPerspective;

	public PortOnPartConnectorInheritanceTest(InheritanceKind inheritance, PerspectiveKind perspective) {
		super();

		this.isInheritance = inheritance == InheritanceKind.with_inheritance;
		this.isPartPerspective = perspective == PerspectiveKind.part_with_port;
	}

	/**
	 * Tests that the outside connectors of ports on parts can be
	 * correctly resolved to the context of the capsule that defines the
	 * parts, from the perspective of the ports on the parts or of the
	 * parts with ports, and with or without inheritance.
	 */
	@Test
	public void outsideConnectorsOfPortOnPart() {
		UMLRTPackage package_ = fixture.getRoot().getNestedPackage(
				isInheritance ? "Implementation Capsules" : "Structure Capsules");
		UMLRTCapsule capsule = package_.getCapsule(
				isInheritance ? "StructureImplementationCapsule" : "StructureCapsule");

		List<UMLRTConnector> connectors = capsule.getConnectors();
		assumeThat("Wrong number of connectors", connectors.size(), is(2));

		if (isInheritance) {
			assumeThat("Some connector(s) on parts not inherited",
					connectors.stream().map(UMLRTConnector::toUML).collect(toList()),
					everyItem(isInherited()));
		}

		UMLRTCapsulePart partA = capsule.getCapsulePart("partA");
		UMLRTCapsulePart partB = capsule.getCapsulePart("partB");
		UMLRTCapsulePart partC = capsule.getCapsulePart("partC");

		assumeThat("Some part(s) not found", Arrays.asList(partA, partB, partC), everyItem(notNullValue()));

		if (isInheritance) {
			assumeThat("Some part(s) not inherited or redefined",
					Arrays.asList(partA.toUML(), partB.toUML(), partC.toUML()),
					everyItem(either(isInherited()).or(UMLMatchers.isRedefined())));
		}

		UMLRTPort partA_port1 = partA.getType().getPort("port1");
		UMLRTPort partB_port1 = partB.getType().getPort("port1");
		UMLRTPort partB_port2 = partB.getType().getPort("port2");
		UMLRTPort partC_port2 = partC.getType().getPort("port2");

		assumeThat("Some port(s) on parts not found",
				Arrays.asList(partA_port1, partB_port1, partB_port2, partC_port2), everyItem(notNullValue()));

		if (isInheritance) {
			assumeThat("Some port(s) on parts not inherited",
					Arrays.asList(partA_port1.toUML(), partB_port1.toUML(), partB_port2.toUML(), partC_port2.toUML()),
					everyItem(isInherited()));
		}

		List<UMLRTConnector> outsidePartAPort1;
		List<UMLRTConnector> outsidePartBPort1;
		List<UMLRTConnector> outsidePartBPort2;
		List<UMLRTConnector> outsidePartCPort2;
		List<List<UMLRTConnector>> powerList;
		if (isPartPerspective) {
			// This builds in the restriction to only the context of our capsule
			outsidePartAPort1 = partA.getConnectorsOf(partA_port1);
			outsidePartBPort1 = partB.getConnectorsOf(partB_port1);
			outsidePartBPort2 = partB.getConnectorsOf(partB_port2);
			outsidePartCPort2 = partC.getConnectorsOf(partC_port2);
			powerList = Arrays.asList(outsidePartAPort1, outsidePartBPort1, outsidePartBPort2, outsidePartCPort2);
		} else {
			outsidePartAPort1 = new ArrayList<>(partA_port1.getOutsideConnectors());
			outsidePartBPort1 = new ArrayList<>(partB_port1.getOutsideConnectors());
			outsidePartBPort2 = new ArrayList<>(partB_port2.getOutsideConnectors());
			outsidePartCPort2 = new ArrayList<>(partC_port2.getOutsideConnectors());
			powerList = Arrays.asList(outsidePartAPort1, outsidePartBPort1, outsidePartBPort2, outsidePartCPort2);

			// We are interested only in the connectors in the context of our capsule
			powerList.forEach(list -> {
				list.removeIf(c -> c.getCapsule() != capsule);
			});
		}

		assertThat("Some ports(s) on parts do not have an outside connector",
				powerList.stream().map(Collection::size).collect(toList()),
				everyItem(is(1)));

		assertThat("Ports of partA and partB do not share a connector", outsidePartAPort1, is(outsidePartBPort1));
		assertThat("Ports of partB and partC do not share a connector", outsidePartBPort2, is(outsidePartCPort2));

		assertThat("Some port(s) on parts have outside connectors not amongst the capsule's connectors",
				powerList.stream().map(list -> connectors.containsAll(list)).collect(toList()),
				everyItem(is(true)));
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}, {1}")
	public static Iterable<Object[]> parameters() {
		return Stream.of(InheritanceKind.values())
				.flatMap(inh -> Stream.of(PerspectiveKind.values())
						.map(psp -> new Object[] { inh, psp }))
				.collect(toList());
	}

	//
	// Nested types
	//

	public enum InheritanceKind {
		no_inheritance, with_inheritance;
	}

	public enum PerspectiveKind {
		port_on_part, part_with_port;
	}
}
