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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.function.Predicate;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.junit.utils.CreationUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.inheritance.tests.AbstractInheritanceTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.swt.graphics.RGB;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

@PluginResource("resource/statemachine/inheritance/model.di")
@DiagramNaming(StateMachineDiagramNameStrategy.class)
@ActiveDiagram("Capsule1::StateMachine") // Most tests start in the parent diagram
public class TransitionInheritanceTest extends AbstractInheritanceTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	/**
	 * Regression test for creation of local transitions in an inheriting state machine diagram.
	 * 
	 * @throws Exception
	 * 
	 * @see <a href="http://eclip.se/512995">bug 512995</a>
	 */
	@NeedsUIEvents
	@Test
	@ActiveDiagram("Capsule2::StateMachine")
	public void createTransitionInInheritedStateMachine() throws Exception {
		UMLRTCapsule capsule2 = UMLRTPackage.getInstance(editor.getModel()).getCapsule("Capsule2");
		UMLRTStateMachine sm = capsule2.getStateMachine();

		UMLRTVertex state2 = sm.getVertex("State2");
		assumeThat("State2 not found", state2, notNullValue());

		UMLRTVertex choice = sm.getVertices().stream()
				.filter(UMLRTPseudostate.class::isInstance).map(UMLRTPseudostate.class::cast)
				.filter(ps -> ps.getKind() == UMLRTPseudostateKind.CHOICE)
				.findAny().get();
		assumeThat("Choice point not found", choice, notNullValue());

		assumeThat("State2 not in the diagram", getEditPart(state2.toUML()), notNullValue());
		assumeThat("Choice point not in the diagram", getEditPart(choice.toUML()), notNullValue());

		markForUndo();

		Command createTransition = CreationUtils.getCreateRelationshipCommand(sm.toRegion(),
				(IHintedType) UMLElementTypes.Transition_Edge,
				choice.toUML(), state2.toUML(),
				true, editor.getEditingDomain());
		editor.execute(createTransition);

		UMLRTTransition newTransition = choice.getOutgoings().stream()
				.filter(t -> t.getTarget() == state2)
				.findAny().get();

		assertThat("No edit-part for new transition", getEditPart(newTransition.toUML()), notNullValue());

		undoToMark();

		assertThat("New transition still present in the diagram", getEditPart(newTransition), nullValue());
	}

	@Test
	public void followTransition() {
		// Add a bend-point to the transition
		UMLRTTransition transition = getTransition("Capsule1", "Initial");
		IGraphicalEditPart transitionEP = requireConnectionEditPart(transition);
		bend(transitionEP, 100, 20);

		// Check that the transition in the other diagram followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireConnectionEditPart(getRedefinition(transition));
		assertFollowed(redefEP, transitionEP, NotationPackage.Literals.RELATIVE_BENDPOINTS);

		undo();

		assertFollowed(redefEP, transitionEP, NotationPackage.Literals.RELATIVE_BENDPOINTS);

		redo();

		assertFollowed(redefEP, transitionEP, NotationPackage.Literals.RELATIVE_BENDPOINTS);
	}

	@Test
	public void unfollowTransition() {
		UMLRTTransition transition = getTransition("Capsule1", "Initial");
		IGraphicalEditPart transitionEP = requireConnectionEditPart(transition);

		activateDiagram("Capsule2");
		UMLRTTransition redef = getRedefinition(transition);
		IGraphicalEditPart redefEP = requireConnectionEditPart(redef);
		unfollow(redefEP);

		activateDiagram(transition);
		bend(transitionEP, 100, 20);

		// Check that the transition in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(transitionEP, redefEP, NotationPackage.Literals.RELATIVE_BENDPOINTS);
	}

	@Test
	public void autoUnfollowTransition() {
		UMLRTTransition transition = getTransition("Capsule1", "Initial");
		IGraphicalEditPart transitionEP = requireConnectionEditPart(transition);

		activateDiagram("Capsule2");
		UMLRTTransition redef = getRedefinition(transition);
		IGraphicalEditPart redefEP = requireConnectionEditPart(redef);

		// Add a bendpoint to the redefining transition so that it will unfollow
		bend(redefEP, 100, 100);

		activateDiagram(transition);
		bend(transitionEP, 100, 20);

		// Check that the transition in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(transitionEP, redefEP, NotationPackage.Literals.RELATIVE_BENDPOINTS);
	}

	@Test
	@NeedsUIEvents
	public void addNewTransitionToSuperclass() {
		UMLRTTransition newTransition = execute(() -> getPseudostate("Capsule1", UMLRTPseudostateKind.CHOICE)
				.createTransitionTo(getState("Capsule1", "State2")));

		// Place the new part
		IGraphicalEditPart transitionEP = requireConnectionEditPart(newTransition);
		bend(transitionEP, 100, 100);

		// Check that the transition in the subclass followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireConnectionEditPart(getRedefinition(newTransition));
		assertFollowed(transitionEP, redefEP, NotationPackage.Literals.RELATIVE_BENDPOINTS);
	}

	@Test
	@ActiveDiagram("Capsule2::StateMachine")
	public void transitionExclusion() {
		UMLRTTransition transition = getTransition("Capsule2", "Initial");
		IGraphicalEditPart transitionEP = requireConnectionEditPart(transition);

		exclude(transition);

		transitionEP = getConnectionEditPart(transition);
		assertThat(transitionEP, nullValue());
	}

	@Test
	public void transitionAppearance() {
		UMLRTTransition transition = getTransition("Capsule1", "Initial");
		IGraphicalEditPart transitionEP = requireConnectionEditPart(transition);

		IFigure figure = getCoreFigure(transitionEP);
		RGB fg = figure.getForegroundColor().getRGB();

		// Check that the transition in the subclass is colored appropriately
		activateDiagram("Capsule2");
		UMLRTTransition redef = getRedefinition(transition);
		IGraphicalEditPart redefEP = requireConnectionEditPart(redef);
		IFigure redefFig = getCoreFigure(redefEP);

		assertThat("Wrong transition color", redefFig.getForegroundColor().getRGB(), not(fg));
	}

	//
	// Test framework
	//

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

	UMLRTState getState(String name) {
		return (UMLRTState) getStateMachine().getVertex(name);
	}

	UMLRTState getState(String capsuleName, String name) {
		return getState(getStateMachine(capsuleName), name);
	}

	UMLRTState getState(UMLRTStateMachine stateMachine, String name) {
		return (UMLRTState) stateMachine.getVertex(name);
	}

	UMLRTPseudostate getPseudostate(UMLRTPseudostateKind kind) {
		return getPseudostate(getStateMachine(), kind);
	}

	UMLRTPseudostate getPseudostate(String capsuleName, UMLRTPseudostateKind kind) {
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
