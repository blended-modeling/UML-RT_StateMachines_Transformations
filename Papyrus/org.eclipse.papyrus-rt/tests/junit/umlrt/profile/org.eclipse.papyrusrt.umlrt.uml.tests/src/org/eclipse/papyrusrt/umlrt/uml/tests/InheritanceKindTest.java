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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.CapsuleTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test suite for the {@link UMLRTInheritanceKind} enumeration.
 */
@TestModel("inheritance/connectors.uml")
@Category({ CapsuleTests.class, FacadeTests.class })
public class InheritanceKindTest {

	@ClassRule
	public static final ModelFixture model = new ModelFixture();

	public InheritanceKindTest() {
		super();
	}

	@Test
	public void noneKind() {
		assertThat(UMLRTInheritanceKind.of(local()), is(UMLRTInheritanceKind.NONE));

		// Not an inheritable thing
		assertThat(UMLRTInheritanceKind.of(getCapsule()), is(UMLRTInheritanceKind.NONE));

		// No element
		assertThat(UMLRTInheritanceKind.of((NamedElement) null), is(UMLRTInheritanceKind.NONE));
	}

	@Test
	public void inheritedKind() {
		assertThat(UMLRTInheritanceKind.of(inherited()), is(UMLRTInheritanceKind.INHERITED));
	}

	@Test
	public void redefinedKind() {
		assertThat(UMLRTInheritanceKind.of(redefined()), is(UMLRTInheritanceKind.REDEFINED));
	}

	@Test
	public void excludedKind() {
		assertThat(UMLRTInheritanceKind.of(excluded()), is(UMLRTInheritanceKind.EXCLUDED));
	}

	@Test
	public void ordering() {
		// This order is backwards to what it should be
		List<UMLRTNamedElement> elements = new ArrayList<>(Arrays.asList(
				local(), excluded(), redefined(), inherited()));

		Collections.sort(elements, UMLRTInheritanceKind.facadeComparator());

		assertThat(elements, is(Arrays.asList(inherited(), redefined(), excluded(), local())));
	}

	@Test
	public void depthwiseOrderingOfInherited() {
		UMLRTCapsule subsubcapsule = model.getRoot().getCapsule("Subsubcapsule");
		UMLRTPort depth2 = subsubcapsule.getPort("protocol2");
		UMLRTPort depth1 = subsubcapsule.getPort("newProtocol");

		// This order is backwards to what it should be
		List<UMLRTNamedElement> elements = new ArrayList<>(Arrays.asList(
				depth1, depth2));

		Collections.sort(elements, UMLRTInheritanceKind.facadeComparator());

		assertThat(elements, is(Arrays.asList(depth2, depth1)));
	}

	//
	// Test framework
	//

	/** Get a capsule that inherits and locally defines stuff. */
	static UMLRTCapsule getCapsule() {
		return model.getRoot().getCapsule("Subcapsule");
	}

	/** A locally-defined element. */
	static UMLRTNamedElement local() {
		return getCapsule().getPort("newProtocol");
	}

	/** An excluded element. */
	static UMLRTNamedElement excluded() {
		return getCapsule().getExcludedElement("connector1");
	}

	/** A redefined element. */
	static UMLRTNamedElement redefined() {
		return getCapsule().getPort("protocol1");
	}

	/** An inherited element. */
	static UMLRTNamedElement inherited() {
		return getCapsule().getCapsulePart("nestedCapsule");
	}

	@BeforeClass
	public static void setupRedefinitions() {
		UMLRTCapsule subcapsule = getCapsule();
		UMLRTProtocol newProtocol = model.getRoot().createProtocol("NewProtocol");

		// A locally-defined element
		subcapsule.createPort(newProtocol);

		// A redefined element
		subcapsule.getPort("protocol1").setReplicationFactor(2);

		// An excluded element
		subcapsule.getConnector("connector1").exclude();
	}
}
