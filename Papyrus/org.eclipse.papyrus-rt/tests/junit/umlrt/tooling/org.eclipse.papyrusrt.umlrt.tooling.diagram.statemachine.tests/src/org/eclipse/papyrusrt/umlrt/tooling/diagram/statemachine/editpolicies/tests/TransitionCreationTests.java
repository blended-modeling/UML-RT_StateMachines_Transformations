/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.editpolicies.tests;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.editpolicies.tests.ValidationRule.postEdit;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collections;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTTransitionEditPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Series of tests around transition creation in a state machine
 */
@RunWith(Parameterized.class)
@DiagramNaming(StateMachineDiagramNameStrategy.class)
@PluginResource("/resource/statemachine/model.di")
public class TransitionCreationTests extends AbstractCreationTest {

	private Class capsule;
	private StateMachine capsule1_stateMachine;
	private Region capsule1_stateMachine_region;
	private State simple1;
	private State simple2;

	private State composite1;
	private Pseudostate composite1Entry;
	private Pseudostate composite1Exit;
	private State composite2;
	private Pseudostate composite2Entry;
	private Pseudostate composite2Exit;

	private IGraphicalEditPart simple1EditPart;

	private IGraphicalEditPart simple2EditPart;

	private IGraphicalEditPart composite1EditPart;
	private IGraphicalEditPart composite1EntryEditPart;
	private IGraphicalEditPart composite1ExitEditPart;

	private IGraphicalEditPart composite2EditPart;
	private IGraphicalEditPart composite2EntryEditPart;
	private IGraphicalEditPart composite2ExitEditPart;

	private final String capsuleName;

	/**
	 * Constructor.
	 */
	public TransitionCreationTests(String capsuleName, String ignoredDescription) {
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
		assertNotNull(capsule);
		capsule1_stateMachine = (StateMachine) capsule.getClassifierBehavior();
		assertNotNull(capsule1_stateMachine);
		capsule1_stateMachine_region = capsule1_stateMachine.getRegion("Region", false, false);
		assertNotNull(capsule1_stateMachine_region);
		simple1 = (State) capsule1_stateMachine_region.getSubvertex("simple1", true, UMLPackage.eINSTANCE.getState(), false);

		// Activate our capsule's state machine diagram
		editor.openDiagram(capsuleName + "::" + capsule1_stateMachine.getName());

		simple1EditPart = getEditPart(simple1);
		assertNotNull(simple1EditPart);
		simple2 = (State) capsule1_stateMachine_region.getSubvertex("simple2", true, UMLPackage.eINSTANCE.getState(), false);
		simple2EditPart = getEditPart(simple2);
		assertNotNull(simple2EditPart);
		composite1 = (State) capsule1_stateMachine_region.getSubvertex("composite1", true, UMLPackage.eINSTANCE.getState(), false);
		composite1EditPart = getEditPart(composite1);
		assertNotNull(composite1EditPart);
		composite1Entry = composite1.getConnectionPoint("EntryPoint1");
		composite1EntryEditPart = getEditPart(composite1Entry);
		assertNotNull(composite1EntryEditPart);
		composite1Exit = composite1.getConnectionPoint("ExitPoint1");
		composite1ExitEditPart = getEditPart(composite1Exit);
		assertNotNull(composite1ExitEditPart);

		composite2 = (State) capsule1_stateMachine_region.getSubvertex("composite2", true, UMLPackage.eINSTANCE.getState(), false);
		composite2EditPart = getEditPart(composite2);
		assertNotNull(composite2EditPart);
		composite2Entry = composite1.getConnectionPoint("EntryPoint1");
		composite2EntryEditPart = getEditPart(composite2Entry);
		assertNotNull(composite2EntryEditPart);
		composite2Exit = composite2.getConnectionPoint("ExitPoint1");
		composite2ExitEditPart = getEditPart(composite2Exit);
		assertNotNull(composite2ExitEditPart);
	}

	@Test
	@NeedsUIEvents
	public void fromSimpleToOtherSimple() throws Exception {
		// REQ 1) Source and target are both simple states. Then an external transitions is created with the selected states as source and target.
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(capsule1_stateMachine_region, simple1EditPart, simple2EditPart);
		runCreationConnexionTest(creationParameters, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE), postEdit((transition) -> {
			assertThat(transition, instanceOf(Transition.class));
			assertThat(((Transition) transition).getSource(), equalTo(creationParameters.getSource()));
			assertThat(((Transition) transition).getTarget(), equalTo(creationParameters.getTarget()));
			assertThat(((Transition) transition).getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));
			assertThat(((Transition) transition).getContainer(), equalTo(creationParameters.container));
		}));
	}

	@Test
	@NeedsUIEvents
	public void fromSimpleToItself() throws Exception {
		// REQ 4) If source and target is the same simple state, then an external self-transition should be created.
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(capsule1_stateMachine_region, simple1EditPart, simple1EditPart);
		runCreationConnexionTest(creationParameters, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE), postEdit((transition) -> {
			assertThat(((Transition) transition).getSource(), equalTo(creationParameters.getSource()));
			assertThat(((Transition) transition).getTarget(), equalTo(creationParameters.getTarget()));
			assertThat(((Transition) transition).getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));
			assertThat(((Transition) transition).getContainer(), equalTo(creationParameters.container));
		}));
	}

	@Test
	@NeedsUIEvents
	public void fromCompositeToSimple() throws Exception {
		// REQ 2) If the selected source is a composite state, then an exit point pseudo state for that composite state
		// should be automatically be created and be used as the source for the transition.
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(capsule1_stateMachine_region, composite1EditPart, simple1EditPart);
		runCreationConnexionTest(creationParameters, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE), postEdit((transition) -> {
			State source = (State) creationParameters.getSource();
			Pseudostate sourceExitPoint = findExitPoint(source, (Transition) transition);
			assertThat(sourceExitPoint, notNullValue());
			assertThat(((Transition) transition).getTarget(), equalTo(creationParameters.getTarget()));
			assertThat(((Transition) transition).getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));
			assertThat(((Transition) transition).getContainer(), equalTo(creationParameters.container));


			ConnectionEditPart transitionEP = (ConnectionEditPart) getConnectionEditPart(transition);
			assertThat(transitionEP, instanceOf(RTTransitionEditPart.class));
			assertThat((IGraphicalEditPart) transitionEP.getTarget(), equalTo(simple1EditPart));
			IGraphicalEditPart sourceEP = (IGraphicalEditPart) transitionEP.getSource();
			assertThat(sourceEP, instanceOf(PseudostateExitPointEditPart.class));
			assertThat(ViewUtil.resolveSemanticElement(((View) sourceEP.getModel())), equalTo(sourceExitPoint));
		}));
	}

	@Test
	@NeedsUIEvents
	public void fromSimpleToComposite() throws Exception {
		/*
		 * If the selected target is a composite state, then an entry point pseudo state for
		 * that composite state should be automatically be created and be used as the target for the transition
		 */
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(capsule1_stateMachine_region, simple1EditPart, composite1EditPart);
		runCreationConnexionTest(creationParameters, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE), postEdit((transition) -> {
			State source = (State) creationParameters.getSource();
			assertThat(((Transition) transition).getSource(), equalTo(source));

			Pseudostate targetEntryPoint = findEntryPoint((State) creationParameters.getTarget(), (Transition) transition);
			assertThat(targetEntryPoint, notNullValue());
			assertThat(((Transition) transition).getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));
			assertThat(((Transition) transition).getContainer(), equalTo(creationParameters.container));


			ConnectionEditPart transitionEP = (ConnectionEditPart) getConnectionEditPart(transition);
			assertThat(transitionEP, instanceOf(RTTransitionEditPart.class));
			assertThat((IGraphicalEditPart) transitionEP.getSource(), equalTo(simple1EditPart));
			IGraphicalEditPart targetEP = (IGraphicalEditPart) transitionEP.getTarget();
			assertThat(targetEP, instanceOf(PseudostateEntryPointEditPart.class));
			assertThat(ViewUtil.resolveSemanticElement(((View) targetEP.getModel())), equalTo(targetEntryPoint));
		}));
	}

	@Test
	@NeedsUIEvents
	public void fromExitToSelfEntry() throws Exception {
		/*
		 * If the selected target is a composite state, then an entry point pseudo state for
		 * that composite state should be automatically be created and be used as the target for the transition
		 */
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(capsule1_stateMachine_region, composite1ExitEditPart, composite1EntryEditPart);
		runCreationConnexionTest(creationParameters, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE), postEdit((transition) -> {
			Pseudostate sourceExitPoint = (Pseudostate) creationParameters.getSource();
			assertThat(((Transition) transition).getSource(), equalTo(sourceExitPoint));

			Pseudostate targetEntryPoint = (Pseudostate) creationParameters.getTarget();
			assertThat(((Transition) transition).getTarget(), equalTo(targetEntryPoint));

			assertThat(((Transition) transition).getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));
			assertThat(((Transition) transition).getContainer(), equalTo(creationParameters.container));

			ConnectionEditPart transitionEP = (ConnectionEditPart) getConnectionEditPart(transition);
			assertThat(transitionEP, instanceOf(RTTransitionEditPart.class));

			assertThat((IGraphicalEditPart) transitionEP.getSource(), equalTo(composite1ExitEditPart));
			IGraphicalEditPart sourceEP = (IGraphicalEditPart) transitionEP.getSource();
			assertThat(sourceEP, instanceOf(PseudostateExitPointEditPart.class));
			assertThat(sourceEP.resolveSemanticElement(), equalTo(sourceExitPoint));

			assertThat((IGraphicalEditPart) transitionEP.getTarget(), equalTo(composite1EntryEditPart));
			IGraphicalEditPart targetEP = (IGraphicalEditPart) transitionEP.getTarget();
			assertThat(targetEP, instanceOf(PseudostateEntryPointEditPart.class));
			assertThat(targetEP.resolveSemanticElement(), equalTo(targetEntryPoint));
		}));
	}

	@Test
	public void fromEntryToSelfEntry() throws Exception {
		/*
		 * If the selected target is a composite state, then an entry point pseudo state for
		 * that composite state should be automatically be created and be used as the target for the transition
		 */
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(capsule1_stateMachine_region, composite1EntryEditPart, composite1EntryEditPart);
		runNotCreationConnexionTest(creationParameters, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE));
	}
}
