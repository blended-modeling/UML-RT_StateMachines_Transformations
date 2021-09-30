/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 500743
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
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTClassCompositeCompartmentEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTClassCompositeEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPropertyPartEditPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Class for testing Copy / paste actions of Capsule Parts in Capsule Structure Diagram
 */
@PluginResource("resource/capsule-parts/model.di")
@ActiveDiagram("Capsule1")
public class CapsulePartCopyPasteTest extends AbstractCanonicalTest {


	/**
	 * 
	 */
	private static final String COPY_OF_CAPSULE2_1 = "CopyOf_capsule2_1";

	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	private Class capsule1;
	private Class capsule2;
	private Property capsule2Part;

	//
	// Test framework
	//
	@Before
	public void getFixtures() {
		capsule1 = (Class) editor.getModel().getOwnedType("Capsule1");
		capsule2 = (Class) editor.getModel().getOwnedType("Capsule2");
		capsule2Part = capsule1.getOwnedAttribute(null, capsule2);
	}

	//
	// Tests
	//

	/**
	 * Verify that on initial opening of a capsule structure diagram, only service ports
	 * are shown on capsule parts.
	 */
	@Test
	@NeedsUIEvents
	public void copyCapsulePartOnCapsule() {
		// select view and copy capsulepart
		// then paste on the target
		View capsule2PartView = requireView(capsule2Part, editor.getActiveDiagram().getNotationView());
		IGraphicalEditPart partEditPart = requireEditPart(capsule2Part);
		assertThat(partEditPart, instanceOf(RTPropertyPartEditPart.class));
		GraphicalEditPart capsuleEditPart = (GraphicalEditPart) editor.getActiveDiagram().getChildBySemanticHint("Class_Shape");
		assertThat(capsuleEditPart, instanceOf(RTClassCompositeEditPart.class));

		// Remember the point to which to undo (if ELK is installed, we may get
		// execution of additional layout commands in UI async-execs)
		markForUndo();

		execute(CopyPasteHelper.getCopyPasteCommand(partEditPart, capsuleEditPart, TransactionUtil.getEditingDomain(capsule1)));
		Property copiedCapsule2Part = capsule1.getOwnedAttribute(COPY_OF_CAPSULE2_1, capsule2);
		String copyName = copiedCapsule2Part.getName();
		assertThat("Copy name does not correspond to the copied element pattern", copyName, equalTo(COPY_OF_CAPSULE2_1));
		View copiedPartView = requireView(copiedCapsule2Part, editor.getActiveDiagram().getNotationView());
		// assert parent is the same as capsule part.
		assertThat("Property container views for both capsule parts should be identical", copiedPartView.eContainer(), equalTo(capsule2PartView.eContainer()));
		IGraphicalEditPart copiedPartEditPart = requireEditPart(copiedCapsule2Part);
		assertThat(copiedPartEditPart, instanceOf(RTPropertyPartEditPart.class));

		undoToMark();

		assertThat("Copied part should be removed after undo", capsule1.getOwnedAttribute(copyName, null), nullValue());
		assertNoView(copiedCapsule2Part, editor.getActiveDiagram().getNotationView());
		Assert.assertFalse("Editor should not be dirty", editor.getEditor().isDirty());
	}


	/**
	 * Verify that on initial opening of a capsule structure diagram, only service ports
	 * are shown on capsule parts.
	 */
	@Test
	@NeedsUIEvents
	public void copyCapsulePartOnCapsuleCompartment() {
		// select view and copy capsulepart
		// then paste on the target
		View capsule2PartView = requireView(capsule2Part, editor.getActiveDiagram().getNotationView());
		IGraphicalEditPart partEditPart = requireEditPart(capsule2Part);
		assertThat(partEditPart, instanceOf(RTPropertyPartEditPart.class));
		GraphicalEditPart capsuleEditPart = (GraphicalEditPart) editor.getActiveDiagram().getChildBySemanticHint("Class_Shape");
		assertThat(capsuleEditPart, instanceOf(RTClassCompositeEditPart.class));
		GraphicalEditPart capsuleCompartmentEditPart = (GraphicalEditPart) capsuleEditPart.getChildBySemanticHint("Class_StructureCompartment");
		assertThat(capsuleCompartmentEditPart, instanceOf(RTClassCompositeCompartmentEditPart.class));

		// Remember the point to which to undo (if ELK is installed, we may get
		// execution of additional layout commands in UI async-execs)
		markForUndo();

		execute(CopyPasteHelper.getCopyPasteCommand(partEditPart, capsuleCompartmentEditPart, TransactionUtil.getEditingDomain(capsule1)));
		Property copiedCapsule2Part = capsule1.getOwnedAttribute(COPY_OF_CAPSULE2_1, capsule2);
		String copyName = copiedCapsule2Part.getName();
		assertThat("Copy name does not correspond to the copied element pattern", copyName, equalTo(COPY_OF_CAPSULE2_1));
		View copiedPartView = requireView(copiedCapsule2Part, editor.getActiveDiagram().getNotationView());
		// assert parent is the same as capsule part.
		assertThat("Property container views for both capsule parts should be identical", copiedPartView.eContainer(), equalTo(capsule2PartView.eContainer()));
		IGraphicalEditPart copiedPartEditPart = requireEditPart(copiedCapsule2Part);
		assertThat(copiedPartEditPart, instanceOf(RTPropertyPartEditPart.class));

		undoToMark();

		assertThat("Copied part should be removed after undo", capsule1.getOwnedAttribute(copyName, null), nullValue());
		assertNoView(copiedCapsule2Part, editor.getActiveDiagram().getNotationView());
		Assert.assertFalse("Editor should not be dirty", editor.getEditor().isDirty());
	}



}
