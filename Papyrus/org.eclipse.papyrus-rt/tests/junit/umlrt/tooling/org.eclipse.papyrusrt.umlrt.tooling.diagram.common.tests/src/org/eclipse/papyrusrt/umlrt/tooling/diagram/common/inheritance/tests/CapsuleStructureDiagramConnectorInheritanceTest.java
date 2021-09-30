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

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.swt.graphics.RGB;
import org.junit.Test;

/**
 * Connector inheritance test cases for the Capsule Structure Diagram.
 */
@PluginResource("resource/inheritance/connectors.di")
@ActiveDiagram("Capsule1") // We usually start the test with some action in the superclass diagram
public class CapsuleStructureDiagramConnectorInheritanceTest extends AbstractInheritanceTest {

	public CapsuleStructureDiagramConnectorInheritanceTest() {
		super();
	}

	@Test
	public void followConnector() {
		// Add a bend-point to the connector
		UMLRTConnector connector = getConnector("RTConnector1");
		IGraphicalEditPart connectorEP = requireEditPart(connector);
		bend(connectorEP, 100, 20);

		// Check that the port in the other diagram followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireEditPart(getRedefinition(connector));
		assertFollowed(redefEP, connectorEP, NotationPackage.Literals.RELATIVE_BENDPOINTS);

		undo();

		assertFollowed(redefEP, connectorEP, NotationPackage.Literals.RELATIVE_BENDPOINTS);

		redo();

		assertFollowed(redefEP, connectorEP, NotationPackage.Literals.RELATIVE_BENDPOINTS);
	}

	@Test
	public void unfollowConnector() {
		UMLRTConnector connector = getConnector("RTConnector1");
		IGraphicalEditPart connectorEP = requireEditPart(connector);

		activateDiagram("Capsule2");
		UMLRTConnector redef = getRedefinition(connector);
		IGraphicalEditPart redefEP = requireEditPart(redef);
		unfollow(redefEP);

		activateDiagram(connector);
		bend(connectorEP, 100, 20);

		// Check that the part in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(connectorEP, redefEP, NotationPackage.Literals.RELATIVE_BENDPOINTS);
	}

	@Test
	public void autoUnfollowConnector() {
		UMLRTConnector connector = getConnector("RTConnector1");
		IGraphicalEditPart connectorEP = requireEditPart(connector);

		activateDiagram("Capsule2");
		UMLRTConnector redef = getRedefinition(connector);
		IGraphicalEditPart redefEP = requireEditPart(redef);

		// Add a bendpoint to the redefining connector so that it will unfollow
		bend(redefEP, 100, 100);

		activateDiagram(connector);
		bend(connectorEP, 100, 20);

		// Check that the connector in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(connectorEP, redefEP, NotationPackage.Literals.RELATIVE_BENDPOINTS);
	}

	@Test
	@NeedsUIEvents
	public void addNewConnectorToSuperclass() {
		UMLRTConnector initialConnector = getConnector("RTConnector1");
		UMLRTCapsule owner = initialConnector.getCapsule();

		UMLRTConnector newConnector = execute(() -> owner.createConnector("RTConnector2",
				getPort("protocol2"), null,
				getPortOnPart("capsule3", "protocol2"), getCapsulePart("capsule3")));

		// Place the new part
		IGraphicalEditPart connectorEP = requireEditPart(newConnector);
		bend(connectorEP, 100, 100);

		// Check that the connector in the subclass followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireEditPart(getRedefinition(newConnector));
		assertFollowed(connectorEP, redefEP, NotationPackage.Literals.RELATIVE_BENDPOINTS);
	}

	@Test
	@ActiveDiagram("Capsule2")
	public void connectorExclusion() {
		UMLRTConnector connector = getCapsule("Capsule2").getConnector("RTConnector1");
		IGraphicalEditPart partEP = requireEditPart(connector);

		exclude(connector);

		partEP = getEditPart(connector.toUML());
		assertThat(partEP, nullValue());
	}

	@Test
	public void connectorAppearance() {
		UMLRTConnector connector = getConnector("RTConnector1");
		IGraphicalEditPart connectorEP = requireEditPart(connector);

		IFigure figure = getCoreFigure(connectorEP);
		RGB fg = figure.getForegroundColor().getRGB();

		// Check that the port in the subclass is colored appropriately
		activateDiagram("Capsule2");
		UMLRTConnector redefConnector = getRedefinition(connector);
		IGraphicalEditPart redefEP = requireEditPart(redefConnector);
		IFigure redefFig = getCoreFigure(redefEP);

		assertThat("Wrong connector color", redefFig.getForegroundColor().getRGB(), not(fg));
	}
}
