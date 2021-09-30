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
import org.eclipse.papyrus.junit.framework.classification.NotImplemented;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.inheritance.tests.AbstractInheritanceTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.swt.graphics.RGB;
import org.junit.ClassRule;
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
public class PseudostateInheritanceTest extends AbstractInheritanceTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	private final UMLRTPseudostateKind kind;

	public PseudostateInheritanceTest(UMLRTPseudostateKind kind) {
		super();

		this.kind = kind;
	}

	@Test
	public void followPseudostate() {
		// Nudge the state
		UMLRTPseudostate pseudo = getPseudostate("Capsule1");
		IGraphicalEditPart stateEP = requireEditPart(pseudo);
		move(stateEP, 20, 20);

		// Check that the state in the other diagram followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireEditPart(getRedefinition(pseudo));
		assertFollowed(redefEP, stateEP, NotationPackage.Literals.LOCATION);

		undo();

		assertFollowed(redefEP, stateEP, NotationPackage.Literals.LOCATION);

		redo();

		assertFollowed(redefEP, stateEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	public void unfollowPseudostate() {
		UMLRTPseudostate pseudo = getPseudostate("Capsule1");
		IGraphicalEditPart stateEP = requireEditPart(pseudo);

		activateDiagram("Capsule2");
		UMLRTPseudostate redef = getRedefinition(pseudo);
		IGraphicalEditPart redefEP = requireEditPart(redef);
		unfollow(redefEP);

		activateDiagram(pseudo);
		move(stateEP, 20, 20);

		// Check that the state in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(stateEP, redefEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	public void autoUnfollowPseudostate() {
		UMLRTPseudostate pseudo = getPseudostate("Capsule1");
		IGraphicalEditPart stateEP = requireEditPart(pseudo);

		activateDiagram("Capsule2");
		UMLRTPseudostate redef = getRedefinition(pseudo);
		IGraphicalEditPart redefEP = requireEditPart(redef);

		// Nudge the redefining state so that it will unfollow
		move(redefEP, 10, 10);

		activateDiagram(pseudo);
		move(stateEP, 20, -20);

		// Check that the state in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(stateEP, redefEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	@NeedsUIEvents
	public void addNewPseudostateToSuperclass() {
		UMLRTStateMachine parentSM = getStateMachine("Capsule1");

		UMLRTPseudostate newPseudo = execute(() -> parentSM.createPseudostate(kind));

		// Place the new pseudostate
		IGraphicalEditPart stateEP = requireEditPart(newPseudo);
		move(stateEP, 50, 50);

		// Check that the state in the subclass followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireEditPart(getRedefinition(newPseudo));
		assertFollowed(stateEP, redefEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	@ActiveDiagram("Capsule2::StateMachine")
	@NotImplemented("Exclusion of pseudostates is not supported in Papyrus-RT")
	public void pseudostateExclusion() {
		UMLRTPseudostate pseudo = getPseudostate("Capsule2");
		IGraphicalEditPart stateEP = requireEditPart(pseudo);

		exclude(pseudo);

		stateEP = getEditPart(pseudo);
		assertThat(stateEP, nullValue());
	}

	@Test
	public void pseudostateAppearance() {
		UMLRTPseudostate pseudo = getPseudostate("Capsule1");
		IGraphicalEditPart stateEP = requireEditPart(pseudo);

		IFigure figure = getCoreFigure(stateEP);
		RGB fg = figure.getForegroundColor().getRGB();

		// Check that the state in the subclass is colored appropriately
		activateDiagram("Capsule2");
		UMLRTPseudostate redef = getRedefinition(pseudo);
		IGraphicalEditPart redefEP = requireEditPart(redef);
		IFigure redefFig = getCoreFigure(redefEP);

		assertThat("Wrong pseudostate color", redefFig.getForegroundColor().getRGB(), not(fg));
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ UMLRTPseudostateKind.INITIAL },
				{ UMLRTPseudostateKind.CHOICE },
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

	UMLRTPseudostate getPseudostate() {
		return getPseudostate(getStateMachine(), kind);
	}

	UMLRTPseudostate getPseudostate(String capsuleName) {
		return getPseudostate(getStateMachine(capsuleName), kind);
	}

	UMLRTPseudostate getPseudostate(UMLRTStateMachine stateMachine, UMLRTPseudostateKind kind) {
		return stateMachine.getVertices().stream()
				.filter(UMLRTPseudostate.class::isInstance).map(UMLRTPseudostate.class::cast)
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
