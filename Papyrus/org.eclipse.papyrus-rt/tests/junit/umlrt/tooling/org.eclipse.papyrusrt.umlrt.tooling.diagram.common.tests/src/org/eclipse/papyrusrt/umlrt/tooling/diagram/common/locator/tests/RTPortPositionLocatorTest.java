/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 496304, 467545, 510188
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.locator.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.lessThan;
import static org.eclipse.papyrusrt.junit.matchers.NumberFuzzyMatcher.near;
import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.tests.PortEditingFixture.PORT_SIZE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.composite.custom.figures.BehaviorFigure;
import org.eclipse.papyrus.uml.diagram.composite.custom.figures.PortFigure;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrusrt.junit.rules.ElementTypesResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTClassCompositeEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editpolicies.tests.PortEditingFixture;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.locator.RTPortPositionLocator;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * JUnit test for Class {@link RTPortPositionLocator}
 */
@ElementTypesResource("/resource/types/umlrt-simple.elementtypesconfigurations")
@ActiveDiagram("Capsule1")
@PluginResource("/resource/ports/model.di")
public class RTPortPositionLocatorTest extends AbstractPapyrusTest {

	private static final String EXTERNAL_PORT_TYPE = "org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.RTPort_Shape";
	private static final String INTERNAL_PORT_TYPE = "org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.InternalBehaviorPort_Shape";
	private static final String SAP_PORT_TYPE = "org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.ServiceAccessPoint_Shape";
	private static final String SPP_PORT_TYPE = "org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.ServiceProvisionPoint_Shape";
	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	@ClassRule
	public static final TestRule viewpoint = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule elementTypesRule = new ElementTypesRule();

	@ClassRule
	public static final PapyrusEditorFixture editor = new PapyrusRTEditorFixture();
	private final PortEditingFixture ports = new PortEditingFixture(editor, "Capsule1");

	private Point capsuleLocation = new Point(20, 20);
	private Dimension capsuleDimension = new Dimension(201, 201);

	private Point capsuleCompartmentLocation = new Point(21, 44);

	// In the coordinate space of the capsule
	private Point exactLeftBorderLocation = new Point(0, capsuleDimension.height / 2);
	private Point exactRightBorderLocation = new Point(capsuleDimension.width - 1, capsuleDimension.height / 2);
	private Point exactTopBorderLocation = new Point(capsuleDimension.width / 2, 0);
	private Point exactBottomBorderLocation = new Point(capsuleDimension.width / 2, capsuleDimension.height - 1);

	private Point pointInCompartmentRelated = new Point(10, 10);

	private Point dropPointInCompartmentRelated = new Point(40, 10);
	private Point dropPointInCompartmentAbsolute = dropPointInCompartmentRelated.getTranslated(capsuleCompartmentLocation);

	private Point dropPointOnLeftBorderAbsolute = exactLeftBorderLocation.getTranslated(capsuleLocation);

	private Point internalToExternalLocation = new Point(24, 20 - PORT_SIZE / 2); // location of the port when switched from dropPointInCompartmentRelated to external

	private static final int TOLERANCE = 1;

	/**
	 * Creates a new {@link RTPortPositionLocatorTest}
	 */
	public RTPortPositionLocatorTest() {
		// Empty constructor.
	}

	/**
	 * Test method to ensure that all parameters (essentially positions) are correct on the test machine.
	 * Layout may vary from one version to another, and from one os to another....
	 */
	@Test
	public void checkTestConfiguration() {
		EditPart part = editor.requireEditPart(editor.getActiveDiagram().getChildBySemanticHint("Class_Shape"), editor.getModel().getPackagedElement("Capsule1"));
		Rectangle bounds = ((ClassCompositeEditPart) part).getFigure().getBounds();
		Point topLeft = bounds.getTopLeft();
		assertThat("current top left coordinates do not match test parameters", topLeft, equalTo(capsuleLocation));

		Dimension capsuleDimension = bounds.getSize();
		assertThat("current capsule size do not match test parameters", capsuleDimension, equalTo(this.capsuleDimension));

		EditPart editPart = editor.getActiveDiagram().getChildBySemanticHint(ClassCompositeEditPart.VISUAL_ID);
		assertThat("Class frame not found in diagram", editPart, instanceOf(IGraphicalEditPart.class));
		IGraphicalEditPart classFrame = (IGraphicalEditPart) editPart;
		EditPart underMouse = classFrame.getViewer().findObjectAt(exactLeftBorderLocation.translate(capsuleLocation));
		assertThat("Mouse location incorrect for left border: ", underMouse, instanceOf(RTClassCompositeEditPart.class));
		underMouse = classFrame.getViewer().findObjectAt(exactRightBorderLocation.translate(capsuleLocation));
		assertThat("Mouse location incorrect for right border: ", underMouse, instanceOf(RTClassCompositeEditPart.class));
		underMouse = classFrame.getViewer().findObjectAt(exactTopBorderLocation.translate(capsuleLocation));
		assertThat("Mouse location incorrect for top border: ", underMouse, instanceOf(RTClassCompositeEditPart.class));
		underMouse = classFrame.getViewer().findObjectAt(exactBottomBorderLocation.translate(capsuleLocation));
		assertThat("Mouse location incorrect for bottom border: ", underMouse, instanceOf(RTClassCompositeEditPart.class));

	}

	@Test
	public void checkPortDecorationOnLeftBorder() {
		Port port = ports.createPortWithTool(exactLeftBorderLocation.getTranslated(capsuleLocation),
				EXTERNAL_PORT_TYPE);
		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		Location location = ports.requireLocation(editPart);
		assertThat(location.getX(), near(-PORT_SIZE / 2, TOLERANCE)); // Adjusted by the locator!
		assertThat(location.getY(), near(exactLeftBorderLocation.y() /*- PORT_SIZE / 2*/, TOLERANCE));
		ports.assertPortSize(editPart);

		// check decorator for Behavior
		PortFigure portFigure = ports.requirePortFigure(editPart);
		BehaviorFigure behaviorFigure = portFigure.getBehavior();

		// left, right, up, down? => position of the behavior figure compared to Port figure
		evaluateBehaviorPosition(portFigure, behaviorFigure, BehaviorPosition.RIGHT, TOLERANCE);
		editor.undo();
	}

	@Test
	public void checkPortDecorationOnRightBorder() {
		Port port = ports.createPortWithTool(exactRightBorderLocation.getTranslated(capsuleLocation),
				EXTERNAL_PORT_TYPE);
		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		Location location = ports.requireLocation(editPart);
		assertThat(location.getX(), near(exactRightBorderLocation.x() - PORT_SIZE / 2, TOLERANCE)); // Adjusted by the locator!
		assertThat(location.getY(), near(exactRightBorderLocation.y(), TOLERANCE));
		ports.assertPortSize(editPart);

		// check decorator for Behavior
		PortFigure portFigure = ports.requirePortFigure(editPart);
		BehaviorFigure behaviorFigure = portFigure.getBehavior();

		// left, right, up, down? => position of the behavior figure compared to Port figure
		evaluateBehaviorPosition(portFigure, behaviorFigure, BehaviorPosition.LEFT, TOLERANCE);
		editor.undo();
	}

	@Test
	public void checkPortDecorationOnTopBorder() {
		Port port = ports.createPortWithTool(exactTopBorderLocation.getTranslated(capsuleLocation),
				EXTERNAL_PORT_TYPE);
		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		Location location = ports.requireLocation(editPart);
		assertThat(location.getX(), near(exactTopBorderLocation.x(), TOLERANCE)); // Adjusted by the locator!
		assertThat(location.getY(), near(exactTopBorderLocation.y() - PORT_SIZE / 2, TOLERANCE));
		ports.assertPortSize(editPart);

		// check decorator for Behavior
		PortFigure portFigure = ports.requirePortFigure(editPart);
		BehaviorFigure behaviorFigure = portFigure.getBehavior();

		// left, right, up, down? => position of the behavior figure compared to Port figure
		evaluateBehaviorPosition(portFigure, behaviorFigure, BehaviorPosition.DOWN, TOLERANCE);
		editor.undo();
	}

	@Test
	public void checkPortDecorationOnBottomBorder() {
		Port port = ports.createPortWithTool(exactBottomBorderLocation.getTranslated(capsuleLocation),
				EXTERNAL_PORT_TYPE);
		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		Location location = ports.requireLocation(editPart);
		assertThat(location.getX(), near(exactBottomBorderLocation.x(), TOLERANCE)); // Adjusted by the locator!
		assertThat(location.getY(), near(exactBottomBorderLocation.y() - PORT_SIZE / 2, TOLERANCE));
		ports.assertPortSize(editPart);

		// check decorator for Behavior
		PortFigure portFigure = ports.requirePortFigure(editPart);
		BehaviorFigure behaviorFigure = portFigure.getBehavior();

		// left, right, up, down? => position of the behavior figure compared to Port figure
		evaluateBehaviorPosition(portFigure, behaviorFigure, BehaviorPosition.UP, TOLERANCE);
		editor.undo();
	}

	@Test
	public void checkPortDecorationOnInternalComponent() {
		Port port = ports.createPortWithTool(pointInCompartmentRelated.getTranslated(capsuleLocation),
				INTERNAL_PORT_TYPE);
		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		Location location = ports.requireLocation(editPart);
		assertThat(location.getX(), near(pointInCompartmentRelated.x(), TOLERANCE)); // Adjusted by the locator!
		assertThat(location.getY(), near(pointInCompartmentRelated.y(), TOLERANCE));
		ports.assertPortSize(editPart);

		// check decorator for Behavior
		PortFigure portFigure = ports.requirePortFigure(editPart);
		BehaviorFigure behaviorFigure = portFigure.getBehavior();

		// left, right, up, down? => position of the behavior figure compared to Port figure
		evaluateBehaviorPosition(portFigure, behaviorFigure, BehaviorPosition.UP, TOLERANCE);
		editor.undo();
	}

	@Test
	public void checkPortDecorationOnDropInternal() {
		Port port = ports.dropProtocolOnCapsule(dropPointInCompartmentAbsolute,
				"Protocol1");
		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		Rectangle bounds = ((IGraphicalEditPart) editPart).getFigure().getBounds();
		assertThat(bounds.x, near(dropPointInCompartmentAbsolute.x, TOLERANCE)); // Adjusted by the locator!
		assertThat(bounds.y, near(dropPointInCompartmentAbsolute.y, TOLERANCE));
		ports.assertPortSize(bounds);

		// check decorator for Behavior
		PortFigure portFigure = ports.requirePortFigure(editPart);
		BehaviorFigure behaviorFigure = portFigure.getBehavior();

		// left, right, up, down? => position of the behavior figure compared to Port figure
		evaluateBehaviorPosition(portFigure, behaviorFigure, BehaviorPosition.UP, TOLERANCE);
		editor.undo();
	}

	@Test
	public void checkPortDecorationOnDropLeftBorder() {
		Port port = ports.dropProtocolOnCapsule(dropPointOnLeftBorderAbsolute,
				"Protocol1");
		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		Rectangle bounds = ((IGraphicalEditPart) editPart).getFigure().getBounds();
		assertThat(bounds.x, near(dropPointOnLeftBorderAbsolute.x - bounds.width / 2, TOLERANCE)); // middle placed on the capsule border
		assertThat(bounds.y, near(dropPointOnLeftBorderAbsolute.y, TOLERANCE));
		ports.assertPortSize(bounds);

		// check decorator for Behavior
		PortFigure portFigure = ports.requirePortFigure(editPart);
		BehaviorFigure behaviorFigure = portFigure.getBehavior();

		// left, right, up, down? => position of the behavior figure compared to Port figure
		evaluateBehaviorPosition(portFigure, behaviorFigure, BehaviorPosition.RIGHT, TOLERANCE);
		editor.undo();
	}

	@Test
	public void checkPortDecorationOnChangeFromInternalToExternal() {
		Port port = ports.dropProtocolOnCapsule(dropPointInCompartmentAbsolute,
				"Protocol1");

		Command command = GMFtoEMFCommandWrapper.wrap(RTPortUtils.getChangePortKindCommand(port, UMLRTPortKind.EXTERNAL_BEHAVIOR));
		assertThat("Command is not executable", command != null && command.canExecute());
		editor.execute(command);
		assertThat(RTPortUtils.getPortKind(port), equalTo(UMLRTPortKind.EXTERNAL_BEHAVIOR));


		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);

		// check decorator for Behavior
		PortFigure portFigure = ports.requirePortFigure(editPart);
		BehaviorFigure behaviorFigure = portFigure.getBehavior();
		// left, right, up, down? => position of the behavior figure compared to Port figure
		evaluateBehaviorPosition(portFigure, behaviorFigure, BehaviorPosition.DOWN, TOLERANCE);
		Rectangle bounds = ((IGraphicalEditPart) editPart).getFigure().getBounds();
		assertThat(bounds.x, near(internalToExternalLocation.x, TOLERANCE));
		assertThat(bounds.y, near(internalToExternalLocation.y, TOLERANCE));
		ports.assertPortSize(bounds);

		editor.undo(); // undo port change
		editor.undo(); // delete port

	}

	@Test
	public void checkPortDecorationWhenMovingFromRightSideToBottomSide() {

		// create port on the right side
		Port port = ports.createPortWithTool(exactRightBorderLocation.getTranslated(capsuleLocation),
				EXTERNAL_PORT_TYPE);

		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);

		PortFigure portFigure = ports.requirePortFigure(editPart);

		BehaviorFigure behaviorFigure = portFigure.getBehavior();


		// verify the original adornment (for Right side port, behavior figure is oriented to left)
		evaluateBehaviorPosition(portFigure, behaviorFigure, BehaviorPosition.LEFT, TOLERANCE);


		// move the port on the bottom side

		Rectangle updatedRect = new Rectangle(portFigure.getBounds().getTransposed());

		ICommand moveCommand = new SetBoundsCommand(((IGraphicalEditPart) editPart).getEditingDomain(), "Move RT Port", new EObjectAdapter((View) editPart.getModel()), updatedRect);

		assertThat("Command is not executable", moveCommand != null && moveCommand.canExecute());
		editor.execute(moveCommand);

		Location location = ports.requireLocation(editPart);
		assertThat(location.getX(), near(exactBottomBorderLocation.getTranslated(capsuleLocation).x(), TOLERANCE)); // Adjusted by the locator!
		assertThat(location.getY(), near(exactBottomBorderLocation.getTranslated(capsuleLocation).y() - PORT_SIZE / 2, TOLERANCE));
		ports.assertPortSize(editPart);

		// verify the new adornment (for Bottom side port, behavior figure is oriented above)
		evaluateBehaviorPosition(portFigure, behaviorFigure, BehaviorPosition.UP, TOLERANCE);
		editor.undo(); // undo move port
		editor.undo(); // undo create port


	}

	@Test
	public void checkUndoPortMove() {

		// create port on the right side
		Port port = ports.createPortWithTool(exactRightBorderLocation.getTranslated(capsuleLocation),
				EXTERNAL_PORT_TYPE);
		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);
		PortFigure portFigure = ports.requirePortFigure(editPart);

		// move the port on the bottom side (here we know that x and y are not the same, so it is a good example to test position change)
		Rectangle oldPosition = new Rectangle(portFigure.getBounds());
		Rectangle newPosition = new Rectangle(portFigure.getBounds().getTransposed());

		ICommand moveCommand = new SetBoundsCommand(((IGraphicalEditPart) editPart).getEditingDomain(), "Move RT Port", new EObjectAdapter((View) editPart.getModel()), newPosition);

		assertThat("Command is not executable", moveCommand != null && moveCommand.canExecute());
		editor.execute(moveCommand);


		Rectangle movedPosition = ports.requirePortFigure(editPart).getBounds();
		// assert that new location is different from the old location
		assertThat("Port Figure should change location", movedPosition.x != oldPosition.x && movedPosition.y != oldPosition.y);
		editor.undo(); // undo move port


		// check that the port location is back to the old location
		Rectangle undoPosition = ports.requirePortFigure(editPart).getBounds();
		assertThat("Port Figure should be at the initial location after Undo Move Element", undoPosition.x == oldPosition.x && undoPosition.y == oldPosition.y);
		ports.assertPortSize(editPart);
		editor.undo(); // undo create port


	}

	@Test
	public void checkLegacySPPansSAPportLocation() {

		// simulate legacy SPP port and check correct location
		checkLegacyPortLocation(exactRightBorderLocation, SPP_PORT_TYPE, false);

		// simulate legacy SAP port and check correct location
		checkLegacyPortLocation(dropPointInCompartmentRelated, SAP_PORT_TYPE, true);
	}


	public void checkLegacyPortLocation(Point position, String portType, Boolean isService) {

		// create an SPP port on the right side
		Port port = ports.createPortWithTool(position.getTranslated(capsuleLocation),
				portType);
		EditPart editPart = editor.requireEditPart(editor.getActiveDiagram(), port);


		// chnage the isService property to simulate a legacy port
		ICommand setisService = new SetValueCommand(new SetRequest(port, UMLPackage.eINSTANCE.getPort_IsService(), isService));

		assertThat("Command is not executable", setisService != null && setisService.canExecute());
		editor.execute(setisService);

		Rectangle newPosition = ports.requirePortFigure(editPart).getBounds();

		if (portType == SPP_PORT_TYPE) {
			// assert that for SPP port with isService = false, port Figure go inside the capsule
			assertThat("SPP Port with isService = false should go inside the capsule ", newPosition.x, lessThan(exactRightBorderLocation.getTranslated(capsuleLocation).x));
		} else if (portType == SAP_PORT_TYPE) {
			// assert that for SAP port with isService = true, port Figure go on the border of the capsule
			assertThat(" SAP Port with isService = true should be on the border of the capsule ", newPosition.y == capsuleLocation.y - newPosition.height / 2);
		}

		editor.undo(); // undo set isServise
		editor.undo(); // undo create the port

	}



	public static enum BehaviorPosition {
		LEFT, RIGHT, UP, DOWN
	}

	private void evaluateBehaviorPosition(PortFigure portFigure, BehaviorFigure behaviorFigure, BehaviorPosition expectedPosition, int plusOrMinus) {
		double portCenterX = portFigure.getBounds().preciseX() + portFigure.getBounds().preciseWidth() / 2;
		double portCenterY = portFigure.getBounds().preciseY() + portFigure.getBounds().preciseHeight() / 2;

		double behaviorCenterX = behaviorFigure.getBounds().preciseX() + behaviorFigure.getBounds().preciseWidth() / 2;
		double behaviorCenterY = behaviorFigure.getBounds().preciseY() + behaviorFigure.getBounds().preciseHeight() / 2;

		// check position
		// right => behavior X > port X, Y equals
		// left => behavior X < port X, Y equals
		// top => etc.

		switch (expectedPosition) {
		case RIGHT:
			assertThat(behaviorCenterX, greaterThan(portCenterX));
			assertThat(behaviorCenterY, near(portCenterY, plusOrMinus));
			break;
		case DOWN:
			assertThat(behaviorCenterY, greaterThan(portCenterY));
			assertThat(behaviorCenterX, near(portCenterX, plusOrMinus));
			break;
		case LEFT:
			assertThat(portCenterX, greaterThan(behaviorCenterX));
			assertThat(behaviorCenterY, near(portCenterY, plusOrMinus));
			break;
		case UP:
			assertThat(portCenterY, greaterThan(behaviorCenterY));
			assertThat(behaviorCenterX, near(portCenterX, plusOrMinus));
			break;
		}

	}

}
