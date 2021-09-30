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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.tests;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;

import javax.inject.Named;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Regression tests for scenario of dropping a port or part that is already
 * visualized in a capsule structure diagram.
 */
@ActiveDiagram("Capsule1")
@PluginResource("/resource/capsule-parts/model.di")
public class DropExistingElementsOnCapsuleTest {

	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	@ClassRule
	public static final TestRule viewpoint = new UMLRTViewpointRule();

	@ClassRule
	public static final PapyrusEditorFixture editor = new PapyrusRTEditorFixture();

	@Rule
	public final FixtureElementRule elements = new FixtureElementRule();

	@Named("model::Capsule1")
	private Class capsule1;

	/**
	 * Initializes me.
	 */
	public DropExistingElementsOnCapsuleTest() {
		super();
	}

	@Test
	public void attemptDropExistingPort() {
		DiagramEditPart theDiagram = editor.getActiveDiagram();
		EditPart composite = theDiagram.getChildBySemanticHint(ClassCompositeEditPart.VISUAL_ID);

		Port greeter = capsule1.getOwnedPort("greeter", null);

		DropObjectsRequest request = new DropObjectsRequest();
		request.setObjects(Collections.singletonList(greeter));
		request.setLocation(new Point(40, 40)); // The top left corner

		Command drop = composite.getCommand(request);
		assertThat("Drop is permitted", drop, not(isExecutable()));
	}

	@Test
	public void attemptDropExistingPart() {
		DiagramEditPart theDiagram = editor.getActiveDiagram();
		EditPart composite = theDiagram.getChildBySemanticHint(ClassCompositeEditPart.VISUAL_ID);
		EditPart compartment = ((IGraphicalEditPart) composite).getChildBySemanticHint(ClassCompositeCompartmentEditPart.VISUAL_ID);
		Property capsule2Part = capsule1.getOwnedAttribute("capsule2", null);

		DropObjectsRequest request = new DropObjectsRequest();
		request.setObjects(Collections.singletonList(capsule2Part));
		request.setLocation(new Point(200, 100)); // Interior, away from the existing part

		Command drop = compartment.getCommand(request);
		assertThat("Drop is permitted", drop, not(isExecutable()));
	}

	/**
	 * Verifies that an internal port of the capsule that types a part cannot
	 * be drop onto that part, despite that the internal port is not presented
	 * on that part (because it doesn't make sense).
	 * 
	 * @see <a href="http://eclip.se/494295">bug 494295</a>
	 */
	@Test
	public void attemptDropInternalPortOnPart() {
		DiagramEditPart theDiagram = editor.getActiveDiagram();
		EditPart composite = theDiagram.getChildBySemanticHint(ClassCompositeEditPart.VISUAL_ID);
		EditPart compartment = ((IGraphicalEditPart) composite).getChildBySemanticHint(ClassCompositeCompartmentEditPart.VISUAL_ID);
		Property capsule2Part = capsule1.getOwnedAttribute("capsule2", null);
		Class capsule2 = (Class) capsule2Part.getType();
		Port port = capsule2.getOwnedPort("bounce", null);
		EditPart capsule2PartEP = UMLRTEditPartUtils.getChild((IGraphicalEditPart) compartment, capsule2Part).get();

		DropObjectsRequest request = new DropObjectsRequest();
		request.setObjects(Collections.singletonList(port));
		request.setLocation(new Point(120, 120)); // Within the capsule-part

		Command drop = capsule2PartEP.getCommand(request);
		assertThat("Drop is permitted", drop, not(isExecutable()));
	}

}
