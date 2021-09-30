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

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTStateMachineDiagramUtils.getStateMachineDiagram;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.List;
import java.util.function.Predicate;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.Decoration;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.core.commands.ExclusionCommand;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.inheritance.tests.AbstractInheritanceTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

@PluginResource("resource/statemachine/inheritance/model.di")
@DiagramNaming(StateMachineDiagramNameStrategy.class)
@ActiveDiagram("Capsule1::StateMachine") // Most tests start in the parent diagram
public class StateInheritanceTest extends AbstractInheritanceTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	@Test
	@ActiveDiagram("Capsule2::StateMachine")
	public void internalTransitionLabels() {
		UMLRTState state1 = getState("State1");
		UMLRTTransition internal = state1.getSubtransition("internal");
		assumeThat(internal, notNullValue());

		IGraphicalEditPart stateEP = requireEditPart(state1);
		assumeThat(stateEP.resolveSemanticElement(), instanceOf(State.class));
		assumeThat(stateEP.resolveSemanticElement(), is(state1.toUML()));

		IGraphicalEditPart transitionEP = getEditPart(internal, stateEP);
		assertThat("Internal transition not found", transitionEP, notNullValue());
		assertThat(transitionEP.resolveSemanticElement(), instanceOf(Transition.class));
		assumeThat(transitionEP.resolveSemanticElement(), is(internal.toUML()));

		IFigure transitionFigure = transitionEP.getFigure();
		assertThat(transitionFigure, instanceOf(WrappingLabel.class));
		WrappingLabel label = (WrappingLabel) transitionFigure;

		assertThat("No transition icon image", label.getIcon(), notNullValue());
		assertThat("Wrong transition label", label.getText(), is("internal"));
	}

	@Test
	@ActiveDiagram("Capsule2::StateMachine")
	public void internalTransitionRedefinitionDecorators() {
		UMLRTState state1 = getState("State1");
		UMLRTTransition internal = state1.getSubtransition("internal");
		assumeThat(internal, notNullValue());

		// Make a redefinition
		execute(() -> internal.createEffect("C++", "this.doIt();"));

		IGraphicalEditPart stateEP = requireEditPart(state1);
		IGraphicalEditPart transitionEP = getEditPart(internal, stateEP);
		assertThat("Internal transition not found", transitionEP, notNullValue());

		List<Decoration> decorations = retrieveDecorations(transitionEP);
		assertThat("Internal transition has decorations", decorations, isEmpty());
	}

	@Test
	public void followState() {
		// Nudge the state
		UMLRTState state1 = getState("Capsule1", "State1");
		IGraphicalEditPart stateEP = requireEditPart(state1);
		move(stateEP, 20, 20);

		// Check that the state in the other diagram followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireEditPart(getRedefinition(state1));
		assertFollowed(redefEP, stateEP, NotationPackage.Literals.LOCATION);

		undo();

		assertFollowed(redefEP, stateEP, NotationPackage.Literals.LOCATION);

		redo();

		assertFollowed(redefEP, stateEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	public void unfollowState() {
		UMLRTState state1 = getState("Capsule1", "State1");
		IGraphicalEditPart stateEP = requireEditPart(state1);

		activateDiagram("Capsule2");
		UMLRTState redef = getRedefinition(state1);
		IGraphicalEditPart redefEP = requireEditPart(redef);
		unfollow(redefEP);

		activateDiagram(state1);
		move(stateEP, 20, 20);

		// Check that the state in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(stateEP, redefEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	public void autoUnfollowState() {
		UMLRTState state1 = getState("Capsule1", "State1");
		IGraphicalEditPart stateEP = requireEditPart(state1);

		activateDiagram("Capsule2");
		UMLRTState redef = getRedefinition(state1);
		IGraphicalEditPart redefEP = requireEditPart(redef);

		// Nudge the redefining state so that it will unfollow
		move(redefEP, 10, 10);

		activateDiagram(state1);
		move(stateEP, 20, -20);

		// Check that the state in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(stateEP, redefEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	@NeedsUIEvents
	public void addNewStateToSuperclass() {
		UMLRTStateMachine parentSM = getStateMachine("Capsule1");

		UMLRTState newState = execute(() -> parentSM.createState("NewState"));

		// Place the new state
		IGraphicalEditPart stateEP = requireEditPart(newState);
		move(stateEP, 50, 50);

		// Check that the state in the subclass followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireEditPart(getRedefinition(newState));
		assertFollowed(stateEP, redefEP, NotationPackage.Literals.LOCATION);
	}

	@Test
	@ActiveDiagram("Capsule2::StateMachine")
	public void stateExclusion() {
		UMLRTState state1 = getState("State1");
		IGraphicalEditPart stateEP = requireEditPart(state1);

		exclude(state1);

		stateEP = getEditPart(state1);
		assertThat(stateEP, nullValue());
	}

	@Test
	public void stateAppearance() {
		UMLRTState state1 = getState("Capsule1", "State1");
		IGraphicalEditPart stateEP = requireEditPart(state1);

		IFigure figure = getCoreFigure(stateEP);
		RGB fg = figure.getForegroundColor().getRGB();

		// Check that the state in the subclass is colored appropriately
		activateDiagram("Capsule2");
		UMLRTState redefState = getRedefinition(state1);
		IGraphicalEditPart redefEP = requireEditPart(redefState);
		IFigure redefFig = getCoreFigure(redefEP);

		assertThat("Wrong state color", redefFig.getForegroundColor().getRGB(), not(fg));
	}


	/**
	 * Verify that the re-inheritance of a composite state that redefines a
	 * simple state correctly updates transitions to account for the removal
	 * of connection points and deletes the state's nested diagram.
	 */
	@PluginResource("resource/statemachine/inheritance/composite_state.di")
	@Test
	@ActiveDiagram("Capsule2::StateMachine")
	public void reinheritCompositeStateToSimpleState() {
		UMLRTStateMachine stateMachine = getCapsule("Capsule2").getStateMachine();

		// Transitions incoming and outgoing the composite state via connection points
		UMLRTTransition incoming = stateMachine.getVertex("State1").getOutgoings().get(0);
		UMLRTTransition outgoing = stateMachine.getVertex("State3").getIncomings().get(0);

		// And the composite state
		UMLRTState state = (UMLRTState) stateMachine.getVertex("State2");

		// Reinherit it
		reinherit(state);

		assertThat("State should be simple", state.isSimple(), is(true));

		// The connection points are gone, so the transitions now have to connect to the state
		assertThat(incoming.getTarget(), is(state));
		assertThat(outgoing.getSource(), is(state));

		// The state should not have any associated diagram
		assertThat("State has nested diagram", getStateMachineDiagram(state.toUML()), nullValue());

		// And the views of the transitions look right
		ConnectionEditPart incomingEP = (ConnectionEditPart) requireConnectionEditPart(incoming);
		assertThat(incomingEP.getTarget(), is(requireEditPart(state)));

		ConnectionEditPart outgoingEP = (ConnectionEditPart) requireConnectionEditPart(outgoing);
		assertThat(outgoingEP.getSource(), is(requireEditPart(state)));
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

	void reinherit(UMLRTNamedElement element) {
		ICommand reinherit = ExclusionCommand.getExclusionCommand(element.toUML(), false);
		assertThat(reinherit, isExecutable());
		editor.execute(reinherit);
	}
}
