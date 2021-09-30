/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.rendering.tests;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.eclipse.papyrusrt.junit.matchers.FigureMatchers.isShowing;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.FigureUtils;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.common.commands.ShowHideCompartmentRequest;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrusrt.junit.matchers.CommandMatchers;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.junit.utils.CreationUtils;
import org.eclipse.papyrusrt.junit.utils.DeletionUtils;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.TransitionUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.InternalTransitionsCompartmentEditPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Tests for internal transition visibility
 * 
 * requirements are:
 * if forced & visible, this should be displayed, whatever the number of internal transitions
 * if forced & set to not visible (should not happen), this should be hidden
 * if set to not visible, this should be not visible, whatever the number of internal transitions
 * if set to visible, this should be visible if there is one or more internal transitions
 */
@ActiveDiagram("Capsule2::StateMachine")
@PluginResource("/resource/statemachine/model.di")
public class InternalTransitionCompartmentAsChildVisibilityTest extends AbstractCanonicalTest {

	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule elementTypesRule = new ElementTypesRule();

	private Class capsule2;
	private StateMachine capsule2_stateMachine;
	private Region capsule2_stateMachine_region;
	private State capsule2_stateMachine_state1;
	private StateEditPart state1AsChildEditPart;
	private IFigure state1AsChildFigure;

	private State capsule2_stateMachine_stateWithInternalTransitions;
	private View capsule2_stateMachine_stateWithInternalTransitionsView;

	@Before
	public void getFixtures() {
		capsule2 = (Class) editor.getModel().getOwnedType("Capsule2");
		assertNotNull(capsule2);
		capsule2_stateMachine = (StateMachine) capsule2.getClassifierBehavior();
		assertNotNull(capsule2_stateMachine);
		capsule2_stateMachine_region = capsule2_stateMachine.getRegion("Region", false, false);
		assertNotNull(capsule2_stateMachine_region);
		capsule2_stateMachine_state1 = (State) capsule2_stateMachine_region.getSubvertex("State1", true, UMLPackage.eINSTANCE.getState(), false);
		assertNotNull(capsule2_stateMachine_state1);
		// this should be the child view
		assertThat(getEditPart(capsule2_stateMachine_state1), instanceOf(StateEditPart.class));
		state1AsChildEditPart = (StateEditPart) getEditPart(capsule2_stateMachine_state1);
		state1AsChildFigure = state1AsChildEditPart.getPrimaryShape();
		assertNotNull(state1AsChildFigure);

		capsule2_stateMachine_stateWithInternalTransitions = (State) capsule2_stateMachine_region.getSubvertex("StateWithInternalTransitions", true, UMLPackage.eINSTANCE.getState(), false);
		assertNotNull(capsule2_stateMachine_stateWithInternalTransitions);
		capsule2_stateMachine_stateWithInternalTransitionsView = getView(capsule2_stateMachine_stateWithInternalTransitions);
		// this should be the child view
		assertNotNull(capsule2_stateMachine_stateWithInternalTransitionsView);
	}

	@Test
	public void checkInternalTransitionFigureVisibility() throws Exception {
		// compartment visibility not forced => state1
		// initially => not showing
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), not(isShowing()));

		// add a transition => visible
		Command createTransitionCommand = CreationUtils.getCreateChildCommand(capsule2_stateMachine_state1, UMLRTElementTypesEnumerator.TRANSITION_INTERNAL, true, editor.getEditingDomain());
		assertThat(createTransitionCommand, isExecutable());
		editor.getEditingDomain().getCommandStack().execute(createTransitionCommand);
		Transition newTransition = capsule2_stateMachine_state1.getRegions().get(0).getTransitions().get(0);
		assertThat(TransitionUtils.isInternalTransition(newTransition), is(true));
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), isShowing());
		// check undo
		editor.getEditingDomain().getCommandStack().undo();
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), not(isShowing()));

		editor.getEditingDomain().getCommandStack().redo();
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), isShowing());

		// remove the transition => not visible
		Command deleteCommand = DeletionUtils.getDeleteCommand(newTransition, true, editor.getEditingDomain());
		assertThat(deleteCommand, CommandMatchers.isExecutable());
		editor.getEditingDomain().getCommandStack().execute(deleteCommand);
		editor.flushDisplayEvents();
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), not(isShowing()));
	}

	public void checkInternalTransitionFigureVisibility_forcedVisible() throws Exception {
		// force visibility
		View compartmentView = state1AsChildEditPart.getChildViewBySemanticHint(InternalTransitionsCompartmentEditPart.VISUAL_ID);
		assertThat(compartmentView, notNullValue());
		ShowHideCompartmentRequest request = new ShowHideCompartmentRequest(true, compartmentView);
		Command command = GEFtoEMFCommandWrapper.wrap(state1AsChildEditPart.getCommand(request));
		assertThat(command, CommandMatchers.isExecutable());
		editor.getEditingDomain().getCommandStack().execute(command);

		// no transition but forced visible => should be visible
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), isShowing());

		// undo (should be unvisible again)
		editor.getEditingDomain().getCommandStack().undo();
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), not(isShowing()));

		// redo (should be visible)
		editor.getEditingDomain().getCommandStack().redo();
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), isShowing());

		// add a transition (should still be visible)
		Command createTransitionCommand = CreationUtils.getCreateChildCommand(capsule2_stateMachine_state1, UMLRTElementTypesEnumerator.TRANSITION_INTERNAL, true, editor.getEditingDomain());
		assertThat(createTransitionCommand, isExecutable());
		editor.getEditingDomain().getCommandStack().execute(createTransitionCommand);
		Transition newTransition = capsule2_stateMachine_state1.getRegions().get(0).getTransitions().get(0);
		assertThat(TransitionUtils.isInternalTransition(newTransition), is(true));
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), isShowing());

		// undo (should still be visible, forced)
		editor.getEditingDomain().getCommandStack().undo();
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), isShowing());

		// redo (should be visible)
		editor.getEditingDomain().getCommandStack().redo();
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), isShowing());

		// delete (should still be visible)
		Command deleteCommand = DeletionUtils.getDeleteCommand(newTransition, true, editor.getEditingDomain());
		assertThat(deleteCommand, isExecutable());
		editor.getEditingDomain().getCommandStack().execute(deleteCommand);
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), isShowing());

		// undo (should still be visible, forced)
		editor.getEditingDomain().getCommandStack().undo();
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), isShowing());
	}

	public void checkInternalTransitionFigureVisibility_forcedNotVisible() throws Exception {
		// add a transition (should be visible)
		Command createTransitionCommand = CreationUtils.getCreateChildCommand(capsule2_stateMachine_state1, UMLRTElementTypesEnumerator.TRANSITION_INTERNAL, true, editor.getEditingDomain());
		assertThat(createTransitionCommand, isExecutable());
		editor.getEditingDomain().getCommandStack().execute(createTransitionCommand);
		Transition newTransition = capsule2_stateMachine_state1.getRegions().get(0).getTransitions().get(0);
		assertThat(TransitionUtils.isInternalTransition(newTransition), is(true));
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), isShowing());

		// force visibility to false
		View compartmentView = state1AsChildEditPart.getChildViewBySemanticHint(InternalTransitionsCompartmentEditPart.VISUAL_ID);
		assertThat(compartmentView, notNullValue());
		ShowHideCompartmentRequest request = new ShowHideCompartmentRequest(false, compartmentView);
		Command command = GEFtoEMFCommandWrapper.wrap(state1AsChildEditPart.getCommand(request));
		assertThat(command, CommandMatchers.isExecutable());
		editor.getEditingDomain().getCommandStack().execute(command);

		// transition but forced not visible => should be not visible
		assertThat(state1AsChildFigure, isShowing());
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), not(isShowing()));

		// undo (should be visible again)
		editor.getEditingDomain().getCommandStack().undo();
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), isShowing());

		// redo (should be not visible)
		editor.getEditingDomain().getCommandStack().redo();
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), not(isShowing()));

		// add a new transition (should be not visible - still forced visible false)
		createTransitionCommand = CreationUtils.getCreateChildCommand(capsule2_stateMachine_state1, UMLRTElementTypesEnumerator.TRANSITION_INTERNAL, true, editor.getEditingDomain());
		assertThat(createTransitionCommand, isExecutable());
		editor.getEditingDomain().getCommandStack().execute(createTransitionCommand);
		newTransition = capsule2_stateMachine_state1.getRegions().get(0).getTransitions().get(1);
		assertThat(TransitionUtils.isInternalTransition(newTransition), is(true));
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), not(isShowing()));

		// undo (should be not visible )
		editor.getEditingDomain().getCommandStack().undo();
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), not(isShowing()));

		// redo (should be not visible)
		editor.getEditingDomain().getCommandStack().redo();
		assertThat(getInternalTransitionCompartmentFigure(state1AsChildFigure), not(isShowing()));

	}

	/**
	 * @param state1AsChildFigure2
	 * @return
	 */
	private ResizableCompartmentFigure getInternalTransitionCompartmentFigure(IFigure containerFigure) throws Exception {
		List<ResizableCompartmentFigure> compartments = FigureUtils.findChildFigureInstances(containerFigure, ResizableCompartmentFigure.class);
		return compartments.stream()
				.filter(f -> "InternalTransitions".equals(f.getCompartmentTitle()))
				.findAny().orElse(null);
	}

	/**
	 * After a possibly failed test, unwinds the undo stack as much as possible
	 * to try to return to a clean editor.
	 */
	@After
	public void unwindUndoStack() {
		CommandStack stack = editor.getEditingDomain().getCommandStack();
		while (stack.canUndo()) {
			stack.undo();
		}
	}
}
