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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.function.Function;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineEditPart;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.core.utils.Either;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.inheritance.tests.AbstractInheritanceTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.uml2.uml.Region;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@PluginResource("resource/statemachine/inheritance/model.di")
@DiagramNaming(StateMachineDiagramNameStrategy.class)
@RunWith(Parameterized.class)
public class StateMachineFrameInheritanceTest extends AbstractInheritanceTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	private final String diagramName;
	private final Function<StateMachineFrameInheritanceTest, ? extends UMLRTNamedElement> fixtureFunction;

	public StateMachineFrameInheritanceTest(String diagramName,
			Function<StateMachineFrameInheritanceTest, ? extends UMLRTNamedElement> fixtureFunction,
			String __) {

		super();

		this.diagramName = diagramName;
		this.fixtureFunction = fixtureFunction;
	}

	@Test
	@NeedsUIEvents
	public void followFrame() {
		// Nudge the state machine frame
		UMLRTNamedElement stateOrMachine = fixtureFunction.apply(this);
		IGraphicalEditPart frameEP = requireFrame(stateOrMachine);
		resize(frameEP, -50, 50);

		// Check that the frame in the other diagram followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireFrame(getRedefinition(stateOrMachine));
		assertFollowed(redefEP, frameEP, NotationPackage.Literals.SIZE);

		undoToMark();

		assertFollowed(redefEP, frameEP, NotationPackage.Literals.SIZE);

		redo();

		assertFollowed(redefEP, frameEP, NotationPackage.Literals.SIZE);
	}

	@Test
	@NeedsUIEvents
	public void followRegion() {
		// Nudge the state machine frame
		UMLRTNamedElement stateOrMachine = fixtureFunction.apply(this);
		IGraphicalEditPart frameEP = requireFrame(stateOrMachine);
		resize(frameEP, -50, 50);

		IGraphicalEditPart regionEP = requireEditPart(toRegion(stateOrMachine));

		// Check that the region in the other diagram followed it
		activateDiagram("Capsule2");
		IGraphicalEditPart redefEP = requireEditPart(getRedefinition(toRegion(stateOrMachine)));
		assertFollowed(redefEP, regionEP, NotationPackage.Literals.SIZE);

		undoToMark();

		assertFollowed(redefEP, regionEP, NotationPackage.Literals.SIZE);

		redo();

		// For some reason, the "zone" machinery in the state machine diagram
		// intercepts the assignment of the region shape's bounds on redo to
		// add again to the original width the difference between the original
		// width and the new width that we are trying to set (which actually is
		// *smaller* in this scenario), because there is no other region to the
		// right of it. So, assert only the height of the region, not its width
		assertFollowed(redefEP, regionEP, NotationPackage.Literals.SIZE__HEIGHT);
	}

	@Test
	public void unfollowFrame() {
		UMLRTNamedElement stateOrMachine = fixtureFunction.apply(this);
		IGraphicalEditPart frameEP = requireFrame(stateOrMachine);

		activateDiagram("Capsule2");
		UMLRTNamedElement redef = getRedefinition(stateOrMachine);
		IGraphicalEditPart redefEP = requireFrame(redef);
		unfollow(redefEP);

		activateDiagram(stateOrMachine);
		resize(frameEP, -50, 50);

		// Check that the frame in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(frameEP, redefEP, NotationPackage.Literals.SIZE);
	}

	@Test
	public void autoUnfollowFrame() {
		UMLRTNamedElement stateOrMachine = fixtureFunction.apply(this);
		IGraphicalEditPart frameEP = requireFrame(stateOrMachine);

		activateDiagram("Capsule2");
		UMLRTNamedElement redef = getRedefinition(stateOrMachine);
		IGraphicalEditPart redefEP = requireFrame(redef);

		// Nudge the redefining frame so that it will unfollow
		resize(redefEP, -10, 10);

		activateDiagram(stateOrMachine);
		resize(frameEP, -50, 50);

		// Check that the state in the other diagram did not follow it
		activateDiagram(redef);
		assertNotFollowed(frameEP, redefEP, NotationPackage.Literals.SIZE);
	}

	//
	// Test framework
	//

	@Parameters(name = "{2}")
	public static Iterable<Object[]> parameters() {
		Function<StateMachineFrameInheritanceTest, ? extends UMLRTNamedElement> stateMachine = test -> test.getStateMachine("Capsule1");
		Function<StateMachineFrameInheritanceTest, ? extends UMLRTNamedElement> compositeState = test -> test.getCompositeState("Capsule1");

		return Arrays.asList(new Object[][] {
				{ "::StateMachine", stateMachine, "state machine" },
				{ "..State1", compositeState, "composite state" },
		});
	}

	@Before
	public void activateInitialDiagram() {
		activateDiagram("Capsule1"); // Most tests start in the parent diagram
	}

	@Override
	protected void resize(IGraphicalEditPart editPart, int deltaWidth, int deltaHeight) {
		// The Zone machinery in these diagrams pushes the width of a state frame
		// back again to its original on the first resizing, but the second and
		// subsequent resizings take. So, do the resize in two steps

		markForUndo();
		super.resize(editPart, 1, 1);
		super.resize(editPart, deltaWidth - 1, deltaHeight - 1);
	}

	@Override
	protected void activateDiagram(String capsuleName) {
		String diagramName = String.format("%s%s", capsuleName, this.diagramName);
		editor.activateDiagram(diagramName);
	}

	UMLRTStateMachine getStateMachine(String capsuleName) {
		return getCapsule(capsuleName).getStateMachine();
	}

	UMLRTState getCompositeState(String capsuleName) {
		return (UMLRTState) getStateMachine(capsuleName).getVertex("State1");
	}

	Region toRegion(UMLRTNamedElement stateOrMachine) {
		return Either.cast(stateOrMachine, UMLRTStateMachine.class, UMLRTState.class)
				.map(UMLRTStateMachine::toRegion, UMLRTState::toRegion)
				.getEither(Region.class);
	}

	IGraphicalEditPart requireFrame(UMLRTNamedElement stateOrMachine) {
		String frameType = (stateOrMachine instanceof UMLRTState)
				? StateEditPartTN.VISUAL_ID
				: StateMachineEditPart.VISUAL_ID;

		IGraphicalEditPart result = requireEditPart(stateOrMachine).getChildBySemanticHint(frameType);
		assertThat("Diagram frame not found", result, notNullValue());
		return result;
	}
}
