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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.papyrus.infra.ui.util.UIUtil;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RelativePortLocation;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.CapsuleStructureDiagramNameStrategy;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Port;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.google.common.base.Objects;

/**
 * Test cases for creation of ports on capsule-parts in the Capsule Structure Diagram.
 */
@ElementTypesResource("/resource/types/umlrt-simple.elementtypesconfigurations")
@ActiveDiagram("Capsule1")
@PluginResource("/resource/capsule-parts/model.di")
@DiagramNaming(CapsuleStructureDiagramNameStrategy.class)
public class PortOnPartCreationTests {
	private static final String EXTERNAL_PORT_TYPE = "org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.RTPort_Shape";

	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	@ClassRule
	public static final TestRule viewpoint = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule elementTypesRule = new ElementTypesRule();

	@ClassRule
	public static final PapyrusEditorFixture editor = new PapyrusRTEditorFixture();

	private final PortEditingFixture ports = new PortEditingFixture(editor, "Capsule1", "capsule2");

	private Point capsuleCompartmentLocation = new Point(41, 70);

	// In the coordinate space of the capsule compartment
	private Point partLocation = new Point(46, 37).getTranslated(capsuleCompartmentLocation);

	// In the coordinate space of the part
	private Point pointOnPartBorder = new Point(16, 1);
	private Point pointInPartInterior = new Point(17, 15);

	/**
	 * Initializes me.
	 */
	public PortOnPartCreationTests() {
		super();
	}

	@Test
	public void createPortOnPart() {
		// Offset by location of the class frame
		Port port = ports.createPortWithTool(pointOnPartBorder.getTranslated(partLocation),
				EXTERNAL_PORT_TYPE);
		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		Location location = ports.requireLocation(editPart);
		assertThat(location.getX(), near(pointOnPartBorder.x(), 5));
		assertThat(location.getY(), near(-7, 5)); // Adjusted by the locator!
		RelativePortLocation whereOnPart = ports.getRelativeLocation(editPart);
		Size portOnPartSize = ports.requireSize(editPart);

		// The in-line editor is activated on the new port in the original diagram
		editor.flushDisplayEvents();
		Control viewerControl = editor.getActiveDiagram().getViewer().getControl();
		boolean found = false;
		TreeIterator<Control> tree = UIUtil.allChildren(viewerControl);
		while (!found && tree.hasNext()) {
			Control next = tree.next();
			String text = null;
			if (next instanceof Text) {
				text = ((Text) next).getText().trim();
			} else if (next instanceof StyledText) {
				text = ((StyledText) next).getText().trim();
			}

			found = Objects.equal(port.getName(), text);
		}
		assertThat("In-line editor not activated", found, is(true));

		// And there's a part located in a similar spot on Capsule2's frame
		editor.activateDiagram("Capsule2");
		editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		location = ports.requireLocation(editPart);
		RelativePortLocation whereOnCapsule = ports.getRelativeLocation(editPart);
		assertThat(whereOnCapsule.side(), is(whereOnPart.side())); // Same side
		assertThat(whereOnCapsule.relativePosition(), near(whereOnPart.relativePosition(), 5));

		// And the port on the part is about 2/3 the size of the port on the capsule
		Size portOnCapsuleSize = ports.requireSize(editPart);
		assertThat(portOnPartSize.getWidth(), near(portOnCapsuleSize.getWidth() * 2 / 3, 1));
		assertThat(portOnPartSize.getHeight(), near(portOnCapsuleSize.getHeight() * 2 / 3, 1));
	}

	@Test
	public void attemptPortOnPartInInterior() {
		// Offset by location of the class frame
		ports.assertCannotCreatePortWithTool(pointInPartInterior.getTranslated(partLocation),
				EXTERNAL_PORT_TYPE);
	}

}
