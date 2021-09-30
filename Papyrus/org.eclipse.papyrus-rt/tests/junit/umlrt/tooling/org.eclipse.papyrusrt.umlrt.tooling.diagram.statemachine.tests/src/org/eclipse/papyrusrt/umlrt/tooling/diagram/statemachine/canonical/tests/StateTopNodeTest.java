/*****************************************************************************
 * Copyright (c) 2017 EclipseSource GmbH, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource GmbH - Initial API and implementation
 *   Christian W. Damus - bug 510323
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTPseudostateEntryPointEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTPseudostateExitPointEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTTransitionEditPart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test for canonical tests on composite States represented as top nodes.
 */
@RunWith(Parameterized.class)
@DiagramNaming(StateMachineDiagramNameStrategy.class)
@PluginResource("resource/statemachine/model.di")
public class StateTopNodeTest extends AbstractCanonicalTest {

	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule elementTypesRule = new ElementTypesRule();

	private Class capsule;
	private StateMachine capsule_stateMachine;
	private Region capsule_stateMachine_region;
	private State insideState;
	private Region insideState_region;
	private State simple1;
	private State simple2;
	private State composite1;
	private State composite2;
	private Pseudostate insideState_entry;
	private Pseudostate insideState_exit;

	private View simple1View;
	private View simple2View;

	private final String capsuleName;

	/**
	 * Constructor.
	 */
	public StateTopNodeTest(String capsuleName, String ignoredDescription) {
		super();

		this.capsuleName = capsuleName;
	}

	@Parameters(name = "{1}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ "TransitionCreation", "root" },
				{ "SubTransitionCreation", "redefinition" },
		});
	}

	@Before
	public void getFixtures() {
		capsule = (Class) editor.getModel().getOwnedType(capsuleName);
		capsule_stateMachine = (StateMachine) capsule.getClassifierBehavior();
		capsule_stateMachine_region = capsule_stateMachine.getRegions().get(0);
		insideState = (State) capsule_stateMachine_region.getSubvertex("InsideState", false, UMLPackage.eINSTANCE.getState(), false);
		insideState_region = insideState.getRegions().get(0);
		assertThat(insideState_region, notNullValue());

		// Activate our capsule's state machine diagram
		editor.openDiagram(capsuleName + ".." + insideState.getName());

		simple1 = (State) insideState_region.getOwnedMember("Simple1", true, UMLPackage.Literals.STATE);
		simple1View = requireView(simple1);
		simple2 = (State) insideState_region.getOwnedMember("Simple2", true, UMLPackage.Literals.STATE);
		simple2View = requireView(simple2);
		composite1 = (State) insideState_region.getOwnedMember("Composite1", true, UMLPackage.Literals.STATE);
		composite2 = (State) insideState_region.getOwnedMember("Composite2", true, UMLPackage.Literals.STATE);
		insideState_exit = insideState.getConnectionPoint("ExitPoint1");
		insideState_entry = insideState.getConnectionPoint("EntryPoint1");
		assertThat(insideState_exit, notNullValue());
		assertThat(insideState_entry, notNullValue());
	}

	@Test
	public void checkSimpleState() throws Exception {
		// create a state (only semantic) in the region of inside state and check a view is created
		State newState = execute(() -> {
			return UMLRTState.getInstance(insideState).createSubstate("newState").toUML();
		});
		waitForUIEvents();
		requireEditPart(newState);
		requireView(newState);
	}

	@Test
	public void checkSimpleEntry() throws Exception {
		// create a state (only semantic) in the region of inside state and check a view is created
		Pseudostate newPseudoState = execute(() -> {
			return UMLRTState.getInstance(insideState).createConnectionPoint(UMLRTConnectionPointKind.ENTRY).toUML();
		});
		waitForUIEvents();
		requireEditPart(newPseudoState);
		requireView(newPseudoState);

		// check a few bits around creation? size and position?
	}

	@Test
	@NeedsUIEvents
	public void checkSimpleTransition() throws Exception {
		// create a state (only semantic) in the region of inside state and check a view is created
		Transition newTransition = execute(() -> {
			Transition t = UMLRTState.getInstance(simple1).createTransitionTo(UMLRTState.getInstance(simple2)).toUML();
			t.setContainer(insideState_region);
			return t;
		});
		waitForUIEvents();
		requireConnectionEditPart(newTransition);
		requireEdge(simple1View, simple2View);
	}

	@Test
	@NeedsUIEvents
	public void checkTransitionWithCreatedEnds() throws Exception {
		// create a state (only semantic) in the region of inside state and check a view is created
		// case 6 with ends to be created
		Transition newTransition = execute(() -> {
			UMLRTConnectionPoint exit = UMLRTState.getInstance(composite1).createConnectionPoint(UMLRTConnectionPointKind.EXIT);
			UMLRTConnectionPoint entry = UMLRTState.getInstance(composite2).createConnectionPoint(UMLRTConnectionPointKind.ENTRY);
			return exit.createTransitionTo(entry).toUML();
		});
		waitForUIEvents();
		assertThat(newTransition.getSource(), instanceOf(Pseudostate.class));
		assertThat(((Pseudostate) newTransition.getSource()).getKind(), equalTo(PseudostateKind.EXIT_POINT_LITERAL));
		assertThat(newTransition.getTarget(), instanceOf(Pseudostate.class));
		assertThat(((Pseudostate) newTransition.getTarget()).getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));

		IGraphicalEditPart editPart = requireConnectionEditPart(newTransition);
		assertThat(editPart, instanceOf(RTTransitionEditPart.class));
		assertThat(((RTTransitionEditPart) editPart).getSource(), instanceOf(RTPseudostateExitPointEditPart.class));
		assertThat(((RTTransitionEditPart) editPart).getTarget(), instanceOf(RTPseudostateEntryPointEditPart.class));
	}

	@Test
	@NeedsUIEvents
	public void checkTransitionFromBorderToState() throws Exception {
		// case 7: from Border of Composite (inside) to a state inside the composite source
		Transition newTransition = execute(() -> {
			UMLRTConnectionPoint entry = UMLRTState.getInstance(insideState).createConnectionPoint(UMLRTConnectionPointKind.ENTRY);
			UMLRTState target = UMLRTState.getInstance(simple1);
			UMLRTTransition transition = entry.createTransitionTo(target);
			transition.setKind(TransitionKind.LOCAL_LITERAL);
			Transition t = transition.toUML();
			t.setContainer(insideState_region);
			return t;
		});
		waitForUIEvents();
		assertThat(newTransition.getSource(), instanceOf(Pseudostate.class));
		assertThat(((Pseudostate) newTransition.getSource()).getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));
		assertThat(newTransition.getTarget(), instanceOf(State.class));

		IGraphicalEditPart editPart = requireConnectionEditPart(newTransition);
		assertThat(editPart, instanceOf(RTTransitionEditPart.class));
		assertThat(((RTTransitionEditPart) editPart).getSource(), instanceOf(RTPseudostateEntryPointEditPart.class));
		assertThat(((RTTransitionEditPart) editPart).getTarget(), instanceOf(RTStateEditPart.class));
	}

	@Test
	@NeedsUIEvents
	public void checkTransitionFromStateToBorder() throws Exception {
		// case 8: from a state inside the composite target to Border of Composite (inside)
		Transition newTransition = execute(() -> {
			UMLRTState source = UMLRTState.getInstance(simple1);
			UMLRTConnectionPoint target = UMLRTState.getInstance(insideState).createConnectionPoint(UMLRTConnectionPointKind.EXIT);
			UMLRTTransition transition = source.createTransitionTo(target);
			transition.setKind(TransitionKind.EXTERNAL_LITERAL);
			Transition t = transition.toUML();
			t.setContainer(insideState_region);
			return t;
		});
		waitForUIEvents();
		assertThat(newTransition.getTarget(), instanceOf(Pseudostate.class));
		assertThat(newTransition.getSource(), instanceOf(State.class));

		IGraphicalEditPart editPart = requireConnectionEditPart(newTransition);
		assertThat(editPart, instanceOf(RTTransitionEditPart.class));
		assertThat(((RTTransitionEditPart) editPart).getSource(), instanceOf(RTStateEditPart.class));
		assertThat(((RTTransitionEditPart) editPart).getTarget(), instanceOf(RTPseudostateExitPointEditPart.class));
	}

	@Test
	@NeedsUIEvents
	public void checkLocalTransition() throws Exception {
		// create a state (only semantic) in the region of inside state and check a view is created
		// case 6 with already created source/target pseudostates
		Transition newTransition = execute(() -> {
			Transition t = UMLRTConnectionPoint.getInstance(insideState_exit).createTransitionTo(UMLRTConnectionPoint.getInstance(insideState_entry)).toUML();
			t.setContainer(insideState_region);
			t.setKind(TransitionKind.LOCAL_LITERAL);
			return t;
		});
		waitForUIEvents();
		assertThat(newTransition, notNullValue());
		assertThat(newTransition.getSource(), equalTo(insideState_exit));
		assertThat(newTransition.getTarget(), equalTo(insideState_entry));
		IGraphicalEditPart editPart = requireConnectionEditPart(newTransition);
		assertThat(editPart, instanceOf(RTTransitionEditPart.class));
		assertThat(((RTTransitionEditPart) editPart).getSource(), instanceOf(RTPseudostateExitPointEditPart.class));
		assertThat(((RTTransitionEditPart) editPart).getTarget(), instanceOf(RTPseudostateEntryPointEditPart.class));
	}

	@Test
	@NeedsUIEvents
	public void checkExternalTransition() throws Exception {
		// should not be possible to visualize on the inside view external transitions
		// case 5 with already created source/target pseudostates
		Transition newTransition = execute(() -> {
			Transition t = UMLRTConnectionPoint.getInstance(insideState_exit).createTransitionTo(UMLRTConnectionPoint.getInstance(insideState_entry)).toUML();
			t.setContainer(capsule_stateMachine_region);
			t.setKind(TransitionKind.EXTERNAL_LITERAL);
			return t;
		});
		waitForUIEvents();
		IGraphicalEditPart editPart = getEditPart(newTransition);
		assertThat(editPart, nullValue());

		editor.openDiagram(capsuleName + "::StateMachine");
		waitForUIEvents();
		requireView(newTransition);
	}
}
