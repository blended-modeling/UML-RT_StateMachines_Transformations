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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.drop.tests;

import static org.eclipse.papyrusrt.junit.matchers.NumberFuzzyMatcher.near;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PropertyPartEditPartCN;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.tests.CapsulePartEditingFixture;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.CapsuleStructureDiagramNameStrategy;
import org.eclipse.uml2.uml.Property;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test cases for creation of capsuleParts on capsule-parts by dropping protocols
 * in the Capsule Structure Diagram.
 */
@ActiveDiagram("Capsule1")
@PluginResource("/resource/capsules/model.di")
@DiagramNaming(CapsuleStructureDiagramNameStrategy.class)
public class CapsuleToCapsulePartOnCapsuleDropStrategyTest {

	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	@ClassRule
	public static final TestRule viewpoint = new UMLRTViewpointRule();

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusRTEditorFixture();

	private final CapsulePartEditingFixture capsuleParts = new CapsulePartEditingFixture(editor, "Capsule1", "capsule2");

	private Point capsuleCompartmentLocation = new Point(41, 70);

	private Point dropLocation = new Point(46, 37);
	// In the coordinate space of the capsule compartment
	private Point pointOnCompartmentLocation = dropLocation.getTranslated(capsuleCompartmentLocation);



	/**
	 * Initializes me.
	 */
	public CapsuleToCapsulePartOnCapsuleDropStrategyTest() {
		super();
	}

	@Test
	public void dropCapsuleOnCapsule() {
		DiagramEditPart theDiagram = editor.getActiveDiagram();

		// Offset by location of the class frame
		Property property = capsuleParts.dropCapsule(pointOnCompartmentLocation,
				"Capsule2");

		// Semantics of the port
		assertThat(property.getName(), is("capsule2"));
		assertThat(property.getType(), instanceOf(org.eclipse.uml2.uml.Class.class));
		assertThat(property.getType().getName(), is("Capsule2"));
		Assert.assertTrue(CapsulePartUtils.isCapsulePart(property));

		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), property);
		Location location = capsuleParts.requireLocation(editPart);
		assertThat(location.getX(), near(dropLocation.x(), 5));
		assertThat(location.getY(), near(dropLocation.y(), 5));

		// Undo the edit
		editor.activateDiagram(theDiagram);
		editor.undo();
		DiagramEditPartsUtil.getAllEditParts(theDiagram)
				.forEach(ep -> {
					View view = ep.getNotationView();
					if ((view != null) && ClassCompositeCompartmentEditPart.VISUAL_ID.equals(view.getType())) {
						// No capsuleParts on parts
						assertThat(ep.getChildBySemanticHint(PropertyPartEditPartCN.VISUAL_ID), nullValue());
					}
				});
	}

}
