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
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTStateMachineDiagramUtils;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.StateMachine;
import org.junit.Test;

/**
 * Specific test cases for state machine diagram import.
 */
public class StateMachineDiagramImportTest extends AbstractTransformationTest {

	/**
	 * Initializes me.
	 */
	public StateMachineDiagramImportTest() {
		super();
	}

	/**
	 * Verify that the imported state machine diagram has the correct
	 * view prototype from the UML-RT Viewpoint.
	 * 
	 * @see <a href="http://eclip.se/495572">bug 495572</a>
	 */
	@Test
	public void viewPrototype() throws Exception {
		simpleImport("resources/rt/WrongViewTypeExample.emx");

		openEditor();

		Class capsule1 = (Class) rootPackage.getOwnedType("Capsule1");
		StateMachine stateMachine = (StateMachine) capsule1.getOwnedBehavior("State Machine");

		Diagram smd = UMLRTStateMachineDiagramUtils.getStateMachineDiagram(stateMachine);
		assertThat(smd, notNullValue());

		ViewPrototype proto = ViewPrototype.get(smd);
		assertThat(proto.getRepresentationKind().getName(), is("UML-RT State Machine Diagram"));
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
		StateMachine stateMachine = (StateMachine) capsule1.getOwnedBehavior("State Machine");

		Diagram smd = UMLRTStateMachineDiagramUtils.getStateMachineDiagram(stateMachine);
		assumeThat(smd, notNullValue());

		// The Ecore default for Diagram::name is "" and it isn't unsettable
		assertThat(smd.getName(), either(nullValue()).or(is("")));
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
		StateMachine stateMachine = (StateMachine) capsule1.getOwnedBehavior("State Machine");

		Diagram smd = UMLRTStateMachineDiagramUtils.getStateMachineDiagram(stateMachine);
		assumeThat(smd, notNullValue());

		assertThat(smd.getName(), is("My Favourite Machine"));
	}

}
