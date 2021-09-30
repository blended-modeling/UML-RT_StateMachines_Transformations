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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.inheritance.tests;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.function.Predicate;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.junit.framework.classification.ClassificationRunnerWithParametersFactory;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.inheritance.tests.AbstractInheritanceTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.swt.graphics.RGB;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;

@PluginResource("resource/statemachine/inheritance/model.di")
@DiagramNaming(StateMachineDiagramNameStrategy.class)
@ActiveDiagram("Capsule1::StateMachine") // Most tests start in the parent diagram
@RunWith(Parameterized.class)
@UseParametersRunnerFactory(ClassificationRunnerWithParametersFactory.class)
public class ConnectionPointInheritanceTest extends AbstractInheritanceTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	private final UMLRTConnectionPointKind kind;

	public ConnectionPointInheritanceTest(UMLRTConnectionPointKind kind) {
		super();

		this.kind = kind;
	}

	@Test
	public void followConnectionPoint() {
		// Nudge the connection point
		UMLRTConnectionPoint pseudo = getConnectionPoint("Capsule1");
		IGraphicalEditPart connEP = requireEditPart(pseudo);
		move(connEP, 20, 0);

		// Check that the connection point in the other diagram followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireEditPart(getRedefinition(pseudo));
		assertFollowed(redefEP, connEP, NotationPackage.Literals.LOCATION);

		undo();

		assertFollowed(redefEP, connEP, NotationPackage.Literals.LOCATION);

		redo();

		assertFollowed(redefEP, connEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	public void unfollowConnectionPoint() {
		UMLRTConnectionPoint pseudo = getConnectionPoint("Capsule1");
		IGraphicalEditPart connEP = requireEditPart(pseudo);

		activateDiagram("Capsule2");
		UMLRTConnectionPoint redef = getRedefinition(pseudo);
		IGraphicalEditPart redefEP = requireEditPart(redef);
		unfollow(redefEP);

		activateDiagram(pseudo);
		move(connEP, 20, 0);

		// Check that the connection point in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(connEP, redefEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	public void autoUnfollowConnectionPoint() {
		UMLRTConnectionPoint pseudo = getConnectionPoint("Capsule1");
		IGraphicalEditPart connEP = requireEditPart(pseudo);

		activateDiagram("Capsule2");
		UMLRTConnectionPoint redef = getRedefinition(pseudo);
		IGraphicalEditPart redefEP = requireEditPart(redef);

		// Nudge the redefining connection point so that it will unfollow
		move(redefEP, -20, 0);

		activateDiagram(pseudo);
		move(connEP, 20, 0);

		// Check that the connection point in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(connEP, redefEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	@NeedsUIEvents
	public void addNewConnectionPointToSuperclass() {
		UMLRTStateMachine parentSM = getStateMachine("Capsule1");

		UMLRTConnectionPoint newPseudo = execute(() -> ((UMLRTState) parentSM.getVertex("State2")).createConnectionPoint(kind));

		// Place the new ConnectionPoint
		IGraphicalEditPart connEP = requireEditPart(newPseudo);
		move(connEP, 50, 0);

		// Check that the connection point in the subclass followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireEditPart(getRedefinition(newPseudo));
		assertFollowed(connEP, redefEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	@ActiveDiagram("Capsule2::StateMachine")
	@Ignore("Exclusion of connection points is not supported in Papyrus-RT")
	public void ConnectionPointExclusion() {
		UMLRTConnectionPoint pseudo = getConnectionPoint("Capsule2");
		IGraphicalEditPart connEP = requireEditPart(pseudo);

		exclude(pseudo);

		connEP = getEditPart(pseudo);
		assertThat(connEP, nullValue());
	}

	@Test
	public void ConnectionPointAppearance() {
		UMLRTConnectionPoint pseudo = getConnectionPoint("Capsule1");
		IGraphicalEditPart connEP = requireEditPart(pseudo);

		IFigure figure = getCoreFigure(connEP);
		RGB fg = figure.getForegroundColor().getRGB();

		// Check that the state in the subclass is colored appropriately
		activateDiagram("Capsule2");
		UMLRTConnectionPoint redef = getRedefinition(pseudo);
		IGraphicalEditPart redefEP = requireEditPart(redef);
		IFigure redefFig = getCoreFigure(redefEP);

		assertThat("Wrong connection point color", redefFig.getForegroundColor().getRGB(), not(fg));
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ UMLRTConnectionPointKind.ENTRY },
				{ UMLRTConnectionPointKind.EXIT },
		});
	}

	@Override
	protected void activateDiagram(String capsuleName) {
		String diagramName = String.format("%s::StateMachine", capsuleName);
		editor.activateDiagram(diagramName);
	}

	UMLRTCapsule getCapsule() {
		return getCapsule("Capsule2");
	}

	UMLRTStateMachine getStateMachine() {
		return getCapsule().getStateMachine();
	}

	UMLRTStateMachine getStateMachine(String capsuleName) {
		return getCapsule(capsuleName).getStateMachine();
	}

	UMLRTConnectionPoint getConnectionPoint() {
		return getConnectionPoint(getStateMachine(), kind);
	}

	UMLRTConnectionPoint getConnectionPoint(String capsuleName) {
		return getConnectionPoint(getStateMachine(capsuleName), kind);
	}

	UMLRTConnectionPoint getConnectionPoint(UMLRTStateMachine stateMachine, UMLRTConnectionPointKind kind) {
		return getConnectionPoint(stateMachine, "State1", kind);
	}

	UMLRTConnectionPoint getConnectionPoint(UMLRTStateMachine stateMachine, String state, UMLRTConnectionPointKind kind) {
		return ((UMLRTState) stateMachine.getVertex(state)).getConnectionPoints().stream()
				.filter(UMLRTConnectionPoint.class::isInstance).map(UMLRTConnectionPoint.class::cast)
				.filter(ps -> ps.getKind() == kind)
				.findAny().orElse(null);
	}

	UMLRTTransition getTransition(String name) {
		return getStateMachine().getTransition(name);
	}

	UMLRTTransition getTransition(String capsuleName, String name) {
		return getStateMachine(capsuleName).getTransition(name);
	}

	UMLRTTransition getTransition(UMLRTVertex source, UMLRTVertex target) {
		return source.getOutgoings().stream()
				.filter(t -> t.getTarget() == target)
				.findAny().orElse(null);
	}

	UMLRTTransition getTransition(Predicate<? super UMLRTVertex> source, Predicate<? super UMLRTVertex> target) {
		return getStateMachine().getVertices().stream()
				.filter(source)
				.flatMap(v -> v.getOutgoings().stream())
				.filter(t -> target.test(t.getTarget()))
				.findAny().orElse(null);
	}
}
