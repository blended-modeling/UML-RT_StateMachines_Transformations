/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PropertyPartEditPartCN;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.tests.PortEditingFixture;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RelativePortLocation;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Port;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test cases for creation of ports on capsule-parts by dropping protocols
 * in the Capsule Structure Diagram.
 */
@ActiveDiagram("Capsule1")
@PluginResource("/resource/capsule-parts/model.di")
public class ProtocolToPortOnPartDropStrategyTest {

	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	@ClassRule
	public static final TestRule viewpoint = new UMLRTViewpointRule();

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusRTEditorFixture();

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
	public ProtocolToPortOnPartDropStrategyTest() {
		super();
	}

	@Test
	public void dropProtocolOnPart() {
		DiagramEditPart theDiagram = editor.getActiveDiagram();

		// Offset by location of the class frame
		Port port = ports.dropProtocol(pointOnPartBorder.getTranslated(partLocation),
				"Greet");

		// Semantics of the port
		assertThat(port.getName(), is("greet"));
		assertThat(port.getType(), instanceOf(Collaboration.class));
		assertThat(port.getType().getName(), is("Greet"));
		assertThat(RTPortUtils.getPortKind(port), is(UMLRTPortKind.EXTERNAL_BEHAVIOR));

		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		Location location = ports.requireLocation(editPart);
		assertThat(location.getX(), near(pointOnPartBorder.x(), 5));
		assertThat(location.getY(), near(-10, 5)); // Adjusted by the locator!
		RelativePortLocation whereOnPart = ports.getRelativeLocation(editPart);

		// And there's a part located in a similar spot on Capsule2's frame
		editor.activateDiagram("Capsule2");
		editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		location = ports.requireLocation(editPart);
		RelativePortLocation whereOnCapsule = ports.getRelativeLocation(editPart);
		assertThat(whereOnCapsule.side(), is(whereOnPart.side())); // Same side
		assertThat(whereOnCapsule.relativePosition(), near(whereOnPart.relativePosition(), 5));

		// Undo the edit
		editor.activateDiagram(theDiagram);
		editor.undo();
		DiagramEditPartsUtil.getAllEditParts(theDiagram)
				.forEach(ep -> {
					View view = ep.getNotationView();
					if ((view != null) && PropertyPartEditPartCN.VISUAL_ID.equals(view.getType())) {
						// No ports on parts
						assertThat(ep.getChildBySemanticHint(PortEditPart.VISUAL_ID), nullValue());
					}
				});
	}

	/**
	 * Verify correct undo of dropping multiple protocols on a capsule-port.
	 * 
	 * @see <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=493343">bug 493343</a>
	 */
	@Test
	public void dropMultipleProtocolsOnPart_bug493343() {
		DiagramEditPart theDiagram = editor.getActiveDiagram();

		// Offset by location of the class frame
		List<Port> ports = this.ports.dropProtocols(pointOnPartBorder.getTranslated(partLocation),
				Arrays.asList("Greet", "Dismiss", "Bounce"));

		// Semantics of the ports
		ports.forEach(port -> {
			assertThat(port.getType(), instanceOf(Collaboration.class));
			assertThat(RTPortUtils.getPortKind(port), is(UMLRTPortKind.EXTERNAL_BEHAVIOR));
		});

		// Visuals of the ports
		List<RelativePortLocation> whereOnPart = ports.stream().map(port -> {
			EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
			RelativePortLocation where = this.ports.getRelativeLocation(editPart);
			return where;
		}).collect(Collectors.toList());

		// And there's a part located in a similar spot on Capsule2's frame
		editor.activateDiagram("Capsule2");
		IntStream.range(0, ports.size()).forEach(index -> {
			EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), ports.get(index));
			RelativePortLocation whereOnCapsule = this.ports.getRelativeLocation(editPart);
			assertThat(whereOnCapsule.side(), is(whereOnPart.get(index).side())); // Same side
			assertThat(whereOnCapsule.relativePosition(), near(whereOnPart.get(index).relativePosition(), 5));
		});

		// Undo the edit
		editor.activateDiagram(theDiagram);
		editor.undo();
		DiagramEditPartsUtil.getAllEditParts(theDiagram)
				.forEach(ep -> {
					View view = ep.getNotationView();
					if ((view != null) && PropertyPartEditPartCN.VISUAL_ID.equals(view.getType())) {
						// No ports on parts
						assertThat(ep.getChildBySemanticHint(PortEditPart.VISUAL_ID), nullValue());
					}
				});
	}

	@Test
	public void attemptDropProtocolOnPartInInterior() {
		// Offset by location of the class frame
		ports.assertCannotDropProtocol(pointInPartInterior.getTranslated(partLocation),
				"Greet");
	}

}
