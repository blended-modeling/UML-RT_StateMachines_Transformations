/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.copypaste.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTClassCompositeEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPortEditPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Port;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Class for testing Copy / paste actions of Ports in Capsule Structure Diagram
 */
@PluginResource("resource/ports/copypaste_ports.di")
@ActiveDiagram("Capsule1")
public class RTPortCopyPasteTest extends AbstractCanonicalTest {



	private static final String COPY_OF_EXTERNAL_PORT = "CopyOf_externalPort_1";
	private static final String COPY_OF_INTERNAL_PORT = "CopyOf_internalPort_1";

	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();

	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	private Class capsule1;
	private Port externalPort;
	private Port internalPort;

	//
	// Test framework
	//
	@Before
	public void getFixtures() {
		capsule1 = (Class) editor.getModel().getOwnedType("Capsule1");
		externalPort = capsule1.getOwnedPort("externalPort", null);
		internalPort = capsule1.getOwnedPort("internalPort", null);
	}

	//
	// Tests
	//


	/**
	 * Copy external port with undo
	 */
	@Test
	@NeedsUIEvents
	public void copyPortsOnCapsuleTest() {

		copyPortOnCapsule(externalPort, COPY_OF_EXTERNAL_PORT);
		copyPortOnCapsule(internalPort, COPY_OF_INTERNAL_PORT);
	}



	private void copyPortOnCapsule(Port port, String name) {
		// select view and copy the port
		// then paste on the target
		View portView = requireView(port, editor.getActiveDiagram().getNotationView());
		IGraphicalEditPart portEditPart = requireEditPart(port);
		assertThat(portEditPart, instanceOf(RTPortEditPart.class));
		GraphicalEditPart capsuleEditPart = (GraphicalEditPart) editor.getActiveDiagram().getChildBySemanticHint("Class_Shape");
		assertThat(capsuleEditPart, instanceOf(RTClassCompositeEditPart.class));

		// Remember the point to which to undo (if ELK is installed, we may get
		// execution of additional layout commands in UI async-execs)
		markForUndo();

		// paste the copied port
		execute(CopyPasteHelper.getCopyPasteCommand(portEditPart, capsuleEditPart, TransactionUtil.getEditingDomain(capsule1)));
		Port copiedPort = capsule1.getOwnedPort(name, null);
		String copyName = copiedPort.getName();
		assertThat("Copy name does not correspond to the copied element pattern", copyName, equalTo(name));
		View copiedPortView = requireView(copiedPort, editor.getActiveDiagram().getNotationView());
		// assert parent is the same as original port.
		assertThat("Property container views for both capsule parts should be identical", copiedPortView.eContainer(), equalTo(portView.eContainer()));
		IGraphicalEditPart copiedPortEditPart = requireEditPart(copiedPort);
		assertThat(copiedPortEditPart, instanceOf(RTPortEditPart.class));

		undoToMark();

		assertThat("Copied port should be removed after undo", capsule1.getOwnedPort(copyName, null), nullValue());
		assertNoView(copiedPort, editor.getActiveDiagram().getNotationView());
		Assert.assertFalse("Editor should not be dirty", editor.getEditor().isDirty());
	}

}
