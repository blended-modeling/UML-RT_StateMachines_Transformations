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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.inheritance.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.function.BiFunction;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.junit.framework.classification.ClassificationRunnerWithParametersFactory;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.CapsuleStructureDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.hamcrest.Matcher;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;

/**
 * Port inheritance test cases for the Capsule Structure Diagram.
 */
@ActiveDiagram("Capsule1") // We usually start the test with some action in the superclass diagram
@RunWith(Parameterized.class)
@UseParametersRunnerFactory(ClassificationRunnerWithParametersFactory.class)
@DiagramNaming(CapsuleStructureDiagramNameStrategy.class)
public class CapsuleStructureDiagramPortInheritanceTest extends AbstractInheritanceTest {

	private final BiFunction<AbstractInheritanceTest, String, UMLRTPort> getPort;
	private final boolean portSameColor;

	public CapsuleStructureDiagramPortInheritanceTest(String modelPath, PortTestKind testKind) {
		super(modelPath);

		this.getPort = testKind.portFunction();
		this.portSameColor = testKind.isPortSameColor();
	}

	@Test
	public void followPort() {
		// Nudge the port up along the edge
		UMLRTPort port = getPort.apply(this, "protocol1");
		IGraphicalEditPart portEP = requireEditPart(port);
		move(portEP, 0, -50);

		// Check that the port in the other diagram followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireEditPart(getRedefinition(port));
		assertFollowed(redefEP, portEP, NotationPackage.Literals.LOCATION);

		undo();

		assertFollowed(redefEP, portEP, NotationPackage.Literals.LOCATION);

		redo();

		assertFollowed(redefEP, portEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	public void unfollowPort() {
		UMLRTPort port = getPort.apply(this, "protocol1");
		IGraphicalEditPart portEP = requireEditPart(port);

		activateDiagram("Capsule2");
		UMLRTPort redef = getRedefinition(port);
		IGraphicalEditPart redefEP = requireEditPart(redef);
		unfollow(redefEP);

		activateDiagram(port);
		move(portEP, 0, -50);

		// Check that the port in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(portEP, redefEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	public void autoUnfollowPort() {
		UMLRTPort port = getPort.apply(this, "protocol1");
		IGraphicalEditPart portEP = requireEditPart(port);

		activateDiagram("Capsule2");
		UMLRTPort redef = getRedefinition(port);
		IGraphicalEditPart redefEP = requireEditPart(redef);

		// Move the redefining port so that it will unfollow
		move(redefEP, 0, 10);

		activateDiagram(port);
		move(portEP, 0, -50);

		// Check that the port in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(portEP, redefEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	public void followPortLabel() {
		// Nudge the port label
		UMLRTPort port = getPort.apply(this, "protocol1");
		IGraphicalEditPart portLabel = requireLabel(port);
		move(portLabel, 10, -10);

		// Check that the port label in the other diagram followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefLabel = requireLabel(getRedefinition(port));
		assertFollowed(redefLabel, portLabel, NotationPackage.Literals.LOCATION);

		undo();

		assertFollowed(redefLabel, portLabel, NotationPackage.Literals.LOCATION);

		redo();

		assertFollowed(redefLabel, portLabel, NotationPackage.Literals.LOCATION);
	}

	@Test
	public void unfollowPortLabel() {
		UMLRTPort port = getPort.apply(this, "protocol1");
		IGraphicalEditPart portLabel = requireLabel(port);

		activateDiagram("Capsule2");
		UMLRTPort redef = getRedefinition(port);
		IGraphicalEditPart redefLabel = requireLabel(redef);

		// Following of the label goes as following of the port
		unfollow(requireEditPart(redef));

		activateDiagram(port);
		move(portLabel, 10, -10);

		// Check that the port in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(portLabel, redefLabel, NotationPackage.Literals.LOCATION);
	}

	@Test
	public void autoUnfollowPortLabel() {
		UMLRTPort port = getPort.apply(this, "protocol1");
		IGraphicalEditPart portLabel = requireLabel(port);

		activateDiagram("Capsule2");
		UMLRTPort redef = getRedefinition(port);
		IGraphicalEditPart redefLabel = requireLabel(redef);

		// Move the redefining port label so that it will unfollow
		move(redefLabel, 10, 10);

		activateDiagram(port);
		move(portLabel, 10, -10);

		// Check that the port label in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(portLabel, redefLabel, NotationPackage.Literals.LOCATION);
	}

	@Test
	@NeedsUIEvents
	public void addNewPortToSuperclass() {
		UMLRTPort initialPort = getPort.apply(this, "protocol1");
		UMLRTCapsule owner = initialPort.getCapsule();

		UMLRTPort newPort = execute(() -> owner.createPort(owner.getPackage().createProtocol("NewProtocol")));

		// Place the new port
		IGraphicalEditPart portEP = requireEditPart(newPort);
		move(portEP, 0, 50);

		// Check that the port in the subclass followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireEditPart(getRedefinition(newPort));
		assertFollowed(portEP, redefEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	@ActiveDiagram("Capsule2")
	public void portExclusion() {
		Assume.assumeThat("Exclusion test case only applicable to port on capsule", portSameColor, is(false));

		UMLRTPort port = getCapsule("Capsule2").getPort("protocol1");
		IGraphicalEditPart partEP = requireEditPart(port);

		exclude(port);

		partEP = getEditPart(port.toUML());
		assertThat(partEP, nullValue());
	}

	@Test
	public void portAppearance() {
		UMLRTPort port = getPort.apply(this, "protocol1");
		IGraphicalEditPart portEP = requireEditPart(port);

		IFigure figure = getCoreFigure(portEP);
		RGB fg = figure.getForegroundColor().getRGB();

		// Check that the port in the subclass is colored appropriately
		activateDiagram("Capsule2");
		UMLRTPort redefPort = getRedefinition(port);
		IGraphicalEditPart redefEP = requireEditPart(redefPort);
		IFigure redefFig = getCoreFigure(redefEP);

		Matcher<RGB> matcher = portSameColor ? is(fg) : not(fg);
		assertThat("Wrong port color", redefFig.getForegroundColor().getRGB(), matcher);

		// This only makes sense for ports that actually are redefined. Our port-on-part
		// scenario does not have an inherited port, but a port on an inherited part
		if (!portSameColor) {
			// Inherited port has normal label
			IGraphicalEditPart redefLabel = requireLabel(redefPort);
			IFigure redefLabelFig = getCoreFigure(redefLabel);
			FontData fontData = redefLabelFig.getFont().getFontData()[0];
			assertThat(fontData.getStyle() & SWT.BOLD, is(0));

			// But a redefined port that overrides the inherited type has a bold label
			execute(() -> redefPort.setType(redefPort.getType().getPackage().createProtocol("NewProtocol")));
			fontData = redefLabelFig.getFont().getFontData()[0];
			assertThat(fontData.getStyle() & SWT.BOLD, is(SWT.BOLD));
		}
	}

	//
	// Test framework
	//

	@Parameters(name = "{1}")
	public static Iterable<Object[]> parameters() {

		return Arrays.asList(new Object[][] {
				{ "resource/inheritance/ports.di", PortTestKind.PORT },
				{ "resource/inheritance/parts.di", PortTestKind.PORT_ON_PART },
		});
	}

	enum PortTestKind {
		PORT("Port"), PORT_ON_PART("Port on Part");

		private final String label;

		private PortTestKind(String label) {
			this.label = label;
		}

		BiFunction<AbstractInheritanceTest, String, UMLRTPort> portFunction() {
			switch (this) {
			case PORT:
				return AbstractInheritanceTest::getPort;
			case PORT_ON_PART:
				return (test, name) -> test.getPortOnPart("capsule3", name);
			default:
				throw new AssertionError("Invalid port test kind: " + this);
			}
		}

		boolean isPortSameColor() {
			return (this == PortTestKind.PORT_ON_PART);
		}

		@Override
		public String toString() {
			return label;
		}
	}
}
