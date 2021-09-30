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
 *   Christian W. Damus - bug 510323, 518290
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.editpolicies.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramCommandStack;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.LabelInListCompartmentEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.types.RTStateMachineTypes;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests around RTState creation in RT state machine diagram.
 */
@RunWith(Parameterized.class)
@DiagramNaming(StateMachineDiagramNameStrategy.class)
@PluginResource("resource/statemachine/model.di")
public class StateMachineCreationTest extends AbstractCreationTest {

	private Class capsule1;
	private StateMachine capsule1_stateMachine;
	private Region capsule1_stateMachine_region;
	private State capsule1_stateMachine_state1;

	private final String capsuleName;

	/**
	 * Constructor.
	 */
	public StateMachineCreationTest(String capsuleName, String ignoredDescription) {
		super();

		this.capsuleName = capsuleName;
	}

	@Parameters(name = "{1}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ "Capsule1", "root" },
				{ "SubCapsule1", "redefinition" },
		});
	}

	@Before
	public void getFixtures() {
		capsule1 = (Class) editor.getModel().getOwnedType(capsuleName);
		assertNotNull(capsule1);
		capsule1_stateMachine = (StateMachine) capsule1.getClassifierBehavior();
		assertNotNull(capsule1_stateMachine);
		capsule1_stateMachine_region = capsule1_stateMachine.getRegion("Region", false, false);
		assertNotNull(capsule1_stateMachine_region);
		capsule1_stateMachine_state1 = (State) capsule1_stateMachine_region.getSubvertex("State1", true, UMLPackage.eINSTANCE.getState(), false);
		assertNotNull(capsule1_stateMachine_state1);

		// Activate our capsule's state machine diagram
		editor.openDiagram(capsuleName + "::" + capsule1_stateMachine.getName());
	}

	/**
	 * Checks it is not possible to create a state in a state, using palette tool in diagram.
	 * Semantic creation is already checked by CreateElementTest#testNotCreateRTStateInState()
	 */
	@Test
	public void verifyNoStateCreationInState() {
		assertThat("Region found before creating it!", capsule1_stateMachine_state1.getRegions(), isEmpty());
		IGraphicalEditPart stateEditPart = getEditPart(capsule1_stateMachine_state1);
		assertThat(stateEditPart, notNullValue());
		Command command = getNodeCreationCommand(stateEditPart, capsule1_stateMachine_state1, ElementTypeRegistry.getInstance().getType("org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.State_Shape"));
		assertThat(command, not(isExecutable()));
	}

	@Test
	public void verifyStateCreationOnRegion() {
		IGraphicalEditPart regionCompartmentEditPart = getEditPart(capsule1_stateMachine_region).getChildBySemanticHint(RegionCompartmentEditPart.VISUAL_ID);
		assertThat(regionCompartmentEditPart, notNullValue());
		List<Vertex> initialChildren = new ArrayList<>(capsule1_stateMachine_region.getSubvertices());
		Command command = getNodeCreationCommand(regionCompartmentEditPart, capsule1_stateMachine_region, ElementTypeRegistry.getInstance().getType("org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.State_Shape"));
		assertThat(command, isExecutable());

		editor.getEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));
		List<Vertex> newElements = new ArrayList<>(capsule1_stateMachine_region.getSubvertices()).stream().filter(v -> !initialChildren.contains(v)).collect(Collectors.toList());
		assertThat("There is not one and only one state added: " + newElements, newElements.size(), equalTo(1));
		assertThat(newElements.get(0), instanceOf(State.class));
		State newState = (State) newElements.get(0);

		editor.getEditingDomain().getCommandStack().undo();
		List<Vertex> afterUndo = new ArrayList<>(capsule1_stateMachine_region.getSubvertices()).stream().filter(v -> !initialChildren.contains(v)).collect(Collectors.toList());
		assertThat(afterUndo.size(), equalTo(0));

		editor.getEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));
		List<Vertex> afterRedo = new ArrayList<>(capsule1_stateMachine_region.getSubvertices()).stream().filter(v -> !initialChildren.contains(v)).collect(Collectors.toList());
		assertThat(afterRedo.size(), equalTo(1));
		assertThat(afterRedo.get(0), instanceOf(State.class));

	}

	/**
	 * Test that the creation of a plain UML state in an UML-RT region is not permitted by the
	 * edit-helpers.
	 */
	@Test
	public void verifyNoUMLStateCreationOnRegion() {
		IGraphicalEditPart regionCompartmentEditPart = getEditPart(capsule1_stateMachine_region).getChildBySemanticHint(RegionCompartmentEditPart.VISUAL_ID);
		assertThat(regionCompartmentEditPart, notNullValue());
		Command command = getNodeCreationCommand(regionCompartmentEditPart, capsule1_stateMachine_region, ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.umldi.State_Shape"));
		assertThat(command, not(isExecutable()));
	}

	@Test
	public void verifyPseudoStateCreationOnRegion() {
		IGraphicalEditPart regionCompartmentEditPart = getEditPart(capsule1_stateMachine_region).getChildBySemanticHint(RegionCompartmentEditPart.VISUAL_ID);
		assertThat(regionCompartmentEditPart, notNullValue());
		List<Vertex> initialChildren = new ArrayList<>(capsule1_stateMachine_region.getSubvertices());
		Command command = getNodeCreationCommand(regionCompartmentEditPart, capsule1_stateMachine_region, ElementTypeRegistry.getInstance().getType("org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.RTPseudoState_JunctionShape"));
		assertThat(command, isExecutable());

		editor.getEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));
		List<Vertex> newElements = new ArrayList<>(capsule1_stateMachine_region.getSubvertices()).stream().filter(v -> !initialChildren.contains(v)).collect(Collectors.toList());
		assertThat("There is not one and only one state added: " + newElements, newElements.size(), equalTo(1));
		assertThat(newElements.get(0), instanceOf(Pseudostate.class));

		editor.getEditingDomain().getCommandStack().undo();
		List<Vertex> afterUndo = new ArrayList<>(capsule1_stateMachine_region.getSubvertices()).stream().filter(v -> !initialChildren.contains(v)).collect(Collectors.toList());
		assertThat(afterUndo.size(), equalTo(0));

		editor.getEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));
		List<Vertex> afterRedo = new ArrayList<>(capsule1_stateMachine_region.getSubvertices()).stream().filter(v -> !initialChildren.contains(v)).collect(Collectors.toList());
		assertThat(afterRedo.size(), equalTo(1));
		// assertThat(afterRedo.get(0), instanceOf(State.class));

	}

	/**
	 * Checks creation of Internal Transitions
	 */
	@Test
	public void verifyFirstInternalTransitionOnEmptyState() {
		assertThat("Region found before creating it", capsule1_stateMachine_state1.getRegions(), isEmpty());
		IGraphicalEditPart stateEditPart = getEditPart(capsule1_stateMachine_state1);
		assertThat(stateEditPart, notNullValue());
		Command command = getNodeCreationCommand(stateEditPart, capsule1_stateMachine_state1, RTStateMachineTypes.INTERNAL_TRANSITION_LABEL.getType());

		// check operation
		checkFirstInternalTransition(stateEditPart, capsule1_stateMachine_state1, null, command, false);
	}

	/**
	 * Checks creation of Internal Transition - on composite state
	 */
	@Test
	public void verifyFirstInternalTransitionOnCompositeState() throws Exception {
		IGraphicalEditPart stateEditPart = getEditPart(capsule1_stateMachine_state1);
		assertThat(stateEditPart, notNullValue());

		// create region
		CreateElementCommand regionCommand = new CreateElementCommand(new CreateElementRequest(capsule1_stateMachine_state1, UMLElementTypes.REGION));
		regionCommand.execute(null, null);
		assertThat(capsule1_stateMachine_state1.getRegions().size(), equalTo(1));
		Region region = capsule1_stateMachine_state1.getRegions().get(0);

		Command command = getNodeCreationCommand(stateEditPart, capsule1_stateMachine_state1, RTStateMachineTypes.INTERNAL_TRANSITION_LABEL.getType());
		checkFirstInternalTransition(stateEditPart, capsule1_stateMachine_state1, region, command, false);
	}

	/**
	 * Checks creation of Internal Transition - on composite state.
	 * This relies on the creation tools, and such expect that a direct editor should be created at the end.
	 */
	@Test
	@NeedsUIEvents
	public void verifyFirstInternalTransitionOnCompositeStateByTool() throws Exception {
		IGraphicalEditPart stateEditPart = getEditPart(capsule1_stateMachine_state1);
		assertThat(stateEditPart, notNullValue());

		// create region
		// CreateElementCommand regionCommand = new CreateElementCommand(new CreateElementRequest(capsule1_stateMachine_state1, UMLElementTypes.REGION));
		// regionCommand.execute(null, null);
		// assertThat(capsule1_stateMachine_state1.getRegions().size(), equalTo(1));
		// Region region = capsule1_stateMachine_state1.getRegions().get(0);
		// waitForUIEvents();

		Point mouse = new Point(10, 10); // not very useful for that test
		stateEditPart.getFigure().translateToAbsolute(mouse);
		Command createTransition = getCreationToolCommand(stateEditPart, capsule1_stateMachine_state1, mouse, RTStateMachineTypes.INTERNAL_TRANSITION_LABEL.getIdentifier());
		checkFirstInternalTransition(stateEditPart, capsule1_stateMachine_state1, null, createTransition, true);
	}

	private void checkFirstInternalTransition(IGraphicalEditPart stateEditPart, State targetState, Region containerRegion, Command creationCommand, boolean expectDirectEdit) {
		markForUndo();
		assertThat(creationCommand, isExecutable());
		editor.getEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(creationCommand));

		waitForUIEvents();

		// checks
		assertThat(capsule1_stateMachine_state1.getRegions().size(), equalTo(1));
		Region region = capsule1_stateMachine_state1.getRegions().get(0);

		// check transition
		assertThat(region.getTransitions().size(), equalTo(1));
		Transition t = region.getTransitions().get(0);
		assertThat(t.getName(), nullValue());
		assertThat(t.getSource(), equalTo(capsule1_stateMachine_state1));
		assertThat(t.getTarget(), equalTo(capsule1_stateMachine_state1));
		assertThat(t.getKind(), equalTo(TransitionKind.INTERNAL_LITERAL));

		if (expectDirectEdit) {
			Collection<Object> results = DiagramCommandStack.getReturnValues(creationCommand);
			List editparts = new ArrayList(1);
			for (Iterator i = results.iterator(); i.hasNext();) {
				Object o = i.next();
				EditPart editpart = null;
				if (o instanceof IAdaptable) {
					IAdaptable adapter = (IAdaptable) o;
					View view = adapter.getAdapter(View.class);
					if (view != null)
						editpart = (EditPart) stateEditPart.getRoot().getViewer().getEditPartRegistry()
								.get(view);
				} else if (o instanceof EObject) {
					editpart = stateEditPart.findEditPart(stateEditPart, (EObject) o);
				}
				if (editpart != null)
					editparts.add(editpart);
			}
			assertThat(editparts.size(), equalTo(1));
			assertThat(editparts.get(0), instanceOf(LabelInListCompartmentEditPart.class));
			LabelInListCompartmentEditPart newPart = (LabelInListCompartmentEditPart) editparts.get(0);
			assertThat(((View) newPart.getModel()).getType(), equalTo("InternalTransition_Label"));
			// Control directEditor = UIUtils.getDirectEditor(editor.getActiveDiagram().getViewer().getControl(), "");
			// assertThat(directEditor, notNullValue());
		}

		// check undo
		editor.undo();
		// check we are back to non dirty, and no created element left
		assertThat(capsule1_stateMachine_state1.getRegions().size(), equalTo(containerRegion == null ? 0 : 1));
		assertThat(t.getOwner(), nullValue());
		assertThat(t.getSource(), nullValue());
		assertThat(t.getTarget(), nullValue());

		assertThat(getUndoPosition(), equalTo(getUndoMark()));
		// check redo
		editor.redo();
		assertThat(capsule1_stateMachine_state1.getRegions().size(), equalTo(1));
		region = capsule1_stateMachine_state1.getRegions().get(0);

		// check transition
		assertThat(region.getTransitions().size(), equalTo(1));
		t = region.getTransitions().get(0);
		assertThat(t.getName(), nullValue());
		assertThat(t.getSource(), equalTo(capsule1_stateMachine_state1));
		assertThat(t.getTarget(), equalTo(capsule1_stateMachine_state1));
		assertThat(t.getKind(), equalTo(TransitionKind.INTERNAL_LITERAL));

	}

}
