/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.migration.rsa.tests.qvt;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTCapsuleStructureDiagramUtils;
import org.eclipse.uml2.uml.Class;
import org.junit.Test;

/**
 * Specific test cases for capsule structure diagram import.
 */
public class CapsuleStructureDiagramImportTest extends AbstractTransformationTest {

	/**
	 * Initializes me.
	 */
	public CapsuleStructureDiagramImportTest() {
		super();
	}

	/**
	 * Verify that the imported capsule structure diagram has the correct
	 * view prototype from the UML-RT Viewpoint.
	 * 
	 * @see <a href="http://eclip.se/495572">bug 495572</a>
	 */
	@Test
	public void viewPrototype() throws Exception {
		simpleImport("resources/rt/WrongViewTypeExample.emx");

		openEditor();

		Class capsule1 = (Class) rootPackage.getOwnedType("Capsule1");
		Diagram csd = UMLRTCapsuleStructureDiagramUtils.getCapsuleStructureDiagram(capsule1);
		assertThat(csd, notNullValue());

		ViewPrototype proto = ViewPrototype.get(csd);
		assertThat(proto.getRepresentationKind().getName(), is("UML-RT Capsule Structure Diagram"));
	}

	/**
	 * Verify that the imported capsule structure diagram has no name.
	 * 
	 * @see <a href="http://eclip.se/462323">bug 462323</a>
	 */
	@Test
	public void noDiagramName() throws Exception {
		simpleImport("resources/rt/WrongViewTypeExample.emx");

		openEditor();

		Class capsule1 = (Class) rootPackage.getOwnedType("Capsule1");
		Diagram csd = UMLRTCapsuleStructureDiagramUtils.getCapsuleStructureDiagram(capsule1);
		assumeThat(csd, notNullValue());

		// The Ecore default for Diagram::name is "" and it isn't unsettable
		assertThat(csd.getName(), either(nullValue()).or(is("")));
	}

	/**
	 * Verify that the imported capsule structure diagram has a name if the source had
	 * an explicit name.
	 * 
	 * @see <a href="http://eclip.se/462323">bug 462323</a>
	 */
	@Test
	public void explicitDiagramName() throws Exception {
		simpleImport("resources/rt/WrongViewTypeExampleWithNames.emx");

		openEditor();

		Class capsule1 = (Class) rootPackage.getOwnedType("Capsule1");
		Diagram csd = UMLRTCapsuleStructureDiagramUtils.getCapsuleStructureDiagram(capsule1);
		assumeThat(csd, notNullValue());

		assertThat(csd.getName(), is("My Favourite Capsule"));
	}

}
