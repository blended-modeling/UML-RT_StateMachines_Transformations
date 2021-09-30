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

import static org.eclipse.papyrusrt.junit.matchers.NumberFuzzyMatcher.near;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTPortEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.CapsuleStructureDiagramNameStrategy;
import org.eclipse.uml2.uml.Port;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test cases for creation of ports in the Capsule Structure Diagram.
 */
@ElementTypesResource("/resource/types/umlrt-simple.elementtypesconfigurations")
@ActiveDiagram("Capsule2")
@PluginResource("/resource/capsules/model.di")
@DiagramNaming(CapsuleStructureDiagramNameStrategy.class)
public class PortCreationTests {
	private static final String EXTERNAL_PORT_TYPE = "org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.RTPort_Shape";
	private static final String INTERNAL_PORT_TYPE = "org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.InternalBehaviorPort_Shape";

	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	@ClassRule
	public static final TestRule viewpoint = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule elementTypesRule = new ElementTypesRule();

	@ClassRule
	public static final PapyrusEditorFixture editor = new PapyrusRTEditorFixture();

	private final PortEditingFixture ports = new PortEditingFixture(editor, "Capsule2");

	private Point capsuleFrameLocation = new Point(40, 40);

	// In the coordinate space of the capsule frame
	private Point pointOnCapsuleBorder = new Point(0, 150);
	private Point pointInCapsuleInterior = new Point(350, 175);
	private Point anotherPointOnCapsuleBorder = new Point(0, 30);

	/**
	 * Initializes me.
	 */
	public PortCreationTests() {
		super();
	}

	@Test
	public void createExternalBehaviorPort() {
		// Offset by location of the class frame
		Port port = ports.createPortWithTool(pointOnCapsuleBorder.getTranslated(capsuleFrameLocation),
				EXTERNAL_PORT_TYPE);
		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		assertThat(editPart, instanceOf(RTPortEditPart.class));
		Location location = ports.requireLocation(editPart);
		assertThat(location.getX(), near(-10, 5)); // Adjusted by the locator!
		assertThat(location.getY(), near(pointOnCapsuleBorder.y(), 5));

		// And it's full size
		ports.assertPortSize(editPart);
	}

	@Test
	public void createInternalBehaviorPort() {
		// Offset by location of the class frame
		Port port = ports.createPortWithTool(pointInCapsuleInterior.getTranslated(capsuleFrameLocation),
				INTERNAL_PORT_TYPE);
		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		assertThat(editPart, instanceOf(RTPortEditPart.class));
		Location location = ports.requireLocation(editPart);
		assertThat(location.getX(), near(pointInCapsuleInterior.x(), 5));
		assertThat(location.getY(), near(pointInCapsuleInterior.y(), 5));

		// And it's full size
		ports.assertPortSize(editPart);
	}

	@Test
	public void attemptExternalBehaviorPortInInterior() {
		// Offset by location of the class frame
		ports.assertCannotCreatePortWithTool(pointInCapsuleInterior.getTranslated(capsuleFrameLocation),
				EXTERNAL_PORT_TYPE);
	}

	@Test
	public void attemptInternalBehaviorPortOnBorder() {
		// Offset by location of the class frame
		ports.assertCannotCreatePortWithTool(anotherPointOnCapsuleBorder.getTranslated(capsuleFrameLocation),
				INTERNAL_PORT_TYPE);
	}

}
