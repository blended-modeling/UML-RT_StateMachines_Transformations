/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus, CEA LIST, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   CEA LIST - bug 489624
 *   Christian W. Damus - bugs 496464, 510323, 512994
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.junit.utils.CreationUtils;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
@DiagramNaming(StateMachineDiagramNameStrategy.class)
@PluginResource("resource/statemachine/model.di")
public class StateMachineDiagramTest extends AbstractCanonicalTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	private Class capsule1;
	private StateMachine capsule1_stateMachine;
	private Region capsule1_stateMachine_region;
	private Pseudostate capsule1_stateMachine_initialState;
	private State capsule1_stateMachine_state1;
	private Transition capsule1_stateMachine_initialTransition;

	private final String capsuleName;

	/**
	 * Constructor.
	 */
	public StateMachineDiagramTest(String capsuleName, String ignoredDescription) {
		super();

		this.capsuleName = capsuleName;
	}

	/**
	 * Verify it is possible to add entry actions to states (Bug 498455)
	 */
	@NeedsUIEvents
	@Test
	public void createEntryState() {
		assertThat("Entry action found before creating it!", capsule1_stateMachine_state1.getEntry(), nullValue());
		execute(new CreateEntryActionHelper().getCreateEntryCommand(capsule1_stateMachine_state1));
		assertThat("Impossible to find Entry action", capsule1_stateMachine_state1.getEntry(), notNullValue());
		assertThat("Entry action not visualized", getEditPart(capsule1_stateMachine_state1.getEntry()), notNullValue());
	}

	/**
	 * Verify it is possible to create a state within the region of the state machine
	 * 
	 * @throws Exception
	 */
	@NeedsUIEvents
	@Test
	public void createStateInStateMachine() throws Exception {
		List<Vertex> initialVertices = new ArrayList<>(capsule1_stateMachine_region.getSubvertices());
		execute(CreationUtils.getCreateChildCommand(capsule1_stateMachine_region, UMLRTElementTypesEnumerator.RT_STATE, true, editor.getEditingDomain()));
		Vertex newVertex = capsule1_stateMachine_region.getSubvertices().stream().filter(e -> !initialVertices.contains(e)).findFirst().get(); // find the new state

		assertThat(newVertex, notNullValue());
		assertThat(newVertex, instanceOf(State.class));

		// get the view in the diagram that corresponds to the state.
		// if null, canonical may not be working well.
		View view = requireView(newVertex);
		assertThat(view, notNullValue());
		assertThat(view.getType(), equalTo(StateEditPart.VISUAL_ID));
	}

	/**
	 * Verify it is possible to create a state within the region of the state machine
	 * 
	 * @throws Exception
	 */
	@NeedsUIEvents
	@Test
	public void createTransitionInStateMachine() throws Exception {
		List<Vertex> initialVertices = new ArrayList<>(capsule1_stateMachine_region.getSubvertices());
		execute(CreationUtils.getCreateChildCommand(capsule1_stateMachine_region, UMLRTElementTypesEnumerator.RT_STATE, true, editor.getEditingDomain()));
		Vertex newVertex = capsule1_stateMachine_region.getSubvertices().stream().filter(e -> !initialVertices.contains(e)).findFirst().get(); // find the new state

		assertThat(newVertex, notNullValue());
		assertThat(newVertex, instanceOf(State.class));

		// get the view in the diagram that corresponds to the state.
		// if null, canonical may not be working well.
		View view = requireView(newVertex);
		assertThat(view, notNullValue());
		assertThat(view.getType(), equalTo(StateEditPart.VISUAL_ID));

		// create transition
		execute(CreationUtils.getCreateRelationshipCommand(capsule1_stateMachine_region, UMLElementTypes.TRANSITION, capsule1_stateMachine_state1, newVertex, true, editor.getEditingDomain()));
		Transition transition = capsule1_stateMachine_state1.getOutgoings().get(0);

		assertThat(transition, notNullValue());
		assertThat(transition, instanceOf(Transition.class));
		assertThat(transition.getTarget(), equalTo(newVertex));

		// get the view in the diagram that corresponds to the state.
		// if null, canonical may not be working well.
		View tview = requireView(transition);
		assertThat(tview, notNullValue());

	}

	//
	// Test framework
	//

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
		capsule1_stateMachine = (StateMachine) capsule1.getClassifierBehavior();
		capsule1_stateMachine_region = capsule1_stateMachine.getRegion("Region", false, false);
		capsule1_stateMachine_initialState = (Pseudostate) capsule1_stateMachine_region.getSubvertex(null, false, UMLPackage.eINSTANCE.getPseudostate(), false);
		capsule1_stateMachine_state1 = (State) capsule1_stateMachine_region.getSubvertex("State1", true, UMLPackage.eINSTANCE.getState(), false);
		capsule1_stateMachine_initialTransition = capsule1_stateMachine_initialState.getOutgoing("Initial");

		// Activate our capsule's state machine diagram
		editor.openDiagram(capsuleName + "::" + capsule1_stateMachine.getName());
	}

	private static class CreateEntryActionHelper {
		ICommand getCreateEntryCommand(State state) {
			IHintedType opaqueBehaviorElementType = (IHintedType) ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.OpaqueBehavior");
			assertThat("Impossible to find element type for OpaqueBehavior", opaqueBehaviorElementType, notNullValue());
			CreateElementRequest request = new CreateElementRequest(state, opaqueBehaviorElementType, UMLPackage.eINSTANCE.getState_Entry());
			final EObject target = ElementEditServiceUtils.getTargetFromContext(TransactionUtil.getEditingDomain(state), state, request);

			IElementEditService provider = ElementEditServiceUtils.getCommandProvider(target);
			if (provider == null) {
				return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;
			}
			return provider.getEditCommand(new CreateElementRequest(TransactionUtil.getEditingDomain(state), target, opaqueBehaviorElementType, UMLPackage.eINSTANCE.getState_Entry()));
		}
	}

}
