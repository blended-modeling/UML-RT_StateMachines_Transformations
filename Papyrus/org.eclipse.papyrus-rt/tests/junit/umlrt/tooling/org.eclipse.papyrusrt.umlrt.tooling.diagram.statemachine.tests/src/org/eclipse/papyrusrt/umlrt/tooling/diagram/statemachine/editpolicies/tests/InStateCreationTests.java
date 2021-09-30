/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 510323
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.editpolicies.tests;

import static org.eclipse.papyrusrt.junit.matchers.GeometryMatchers.near;
import static org.eclipse.papyrusrt.junit.matchers.StringMatcher.nullOrEmpty;
import static org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.editpolicies.tests.ValidationRule.postEdit;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderedNodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.figures.TransitionFigure;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateCompartmentEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.RelativePortLocation;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTPseudostateEntryPointEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTPseudostateExitPointEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPartTN;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTTransitionEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.types.RTStateMachineTypes;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.UMLPackage;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

/**
 * Series of tests around transition creation in a composite state.
 */
@SuppressWarnings("restriction")
@DiagramNaming(StateMachineDiagramNameStrategy.class)
@ActiveDiagram("TransitionCreation..InsideState")
@PluginResource("/resource/statemachine/model.di")
public class InStateCreationTests extends AbstractCreationTest {

	/**
	 * Constructor.
	 */
	public InStateCreationTests() {
		// empty constructor.
	}

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

	private State insideState;

	private Region insideState_region;

	private Pseudostate insideState_entry;

	private IGraphicalEditPart insideState_entryEditPart;

	private Pseudostate insideState_exit;

	private IGraphicalEditPart insideState_exitEditPart;

	private IGraphicalEditPart insideStateEditPart;

	private Rectangle composite1ExitFrame;

	private Rectangle composite1EntryFrame;

	private Rectangle composite2ExitFrame;

	private Rectangle composite2EntryFrame;

	private Rectangle insideState_entryFrame;

	private Rectangle insideState_exitFrame;

	private Rectangle insideStateFrame;

	private Rectangle simple1Frame;

	private Rectangle simple2Frame;

	private Rectangle composite1Frame;

	private Rectangle composite2Frame;

	@SuppressWarnings("unchecked")
	@Before
	public void getFixtures() {
		capsule = (Class) editor.getModel().getOwnedType("TransitionCreation");
		assertNotNull(capsule);
		capsule1_stateMachine = (StateMachine) capsule.getClassifierBehavior();
		assertNotNull(capsule1_stateMachine);
		capsule1_stateMachine_region = capsule1_stateMachine.getRegion("Region", false, false);
		assertNotNull(capsule1_stateMachine_region);

		insideState = (State) capsule1_stateMachine_region.getOwnedMember("InsideState");
		assertNotNull(insideState);
		IGraphicalEditPart root = getEditPart(insideState);
		insideStateEditPart = ((List<EditPart>) root.getChildren()).stream().filter(RTStateEditPartTN.class::isInstance).map(RTStateEditPartTN.class::cast).findAny().orElse(null);
		assertNotNull(insideStateEditPart);
		insideState_region = insideState.getRegions().get(0);
		assertNotNull(insideState_region);

		insideState_entry = insideState.getConnectionPoint("EntryPoint1");
		assertNotNull(insideState_entry);
		insideState_entryEditPart = getEditPart(insideState_entry);
		assertNotNull(insideState_entryEditPart);
		insideState_exit = insideState.getConnectionPoint("ExitPoint1");
		assertNotNull(insideState_exit);
		insideState_exitEditPart = getEditPart(insideState_exit);
		assertNotNull(insideState_exitEditPart);

		simple1 = (State) insideState_region.getSubvertex("simple1", true, UMLPackage.eINSTANCE.getState(), false);
		simple1EditPart = getEditPart(simple1);
		assertNotNull(simple1EditPart);
		simple2 = (State) insideState_region.getSubvertex("simple2", true, UMLPackage.eINSTANCE.getState(), false);
		simple2EditPart = getEditPart(simple2);
		assertNotNull(simple2EditPart);
		composite1 = (State) insideState_region.getSubvertex("composite1", true, UMLPackage.eINSTANCE.getState(), false);
		composite1EditPart = getEditPart(composite1);
		assertNotNull(composite1EditPart);
		composite1Entry = composite1.getConnectionPoint("EntryPoint1");
		composite1EntryEditPart = getEditPart(composite1Entry);
		assertNotNull(composite1EntryEditPart);
		composite1Exit = composite1.getConnectionPoint("ExitPoint1");
		composite1ExitEditPart = getEditPart(composite1Exit);
		assertNotNull(composite1ExitEditPart);

		composite2 = (State) insideState_region.getSubvertex("composite2", true, UMLPackage.eINSTANCE.getState(), false);
		composite2EditPart = getEditPart(composite2);
		assertNotNull(composite2EditPart);
		composite2Entry = composite2.getConnectionPoint("EntryPoint1");
		composite2EntryEditPart = getEditPart(composite2Entry);
		assertNotNull(composite2EntryEditPart);
		composite2Exit = composite2.getConnectionPoint("ExitPoint1");
		composite2ExitEditPart = getEditPart(composite2Exit);
		assertNotNull(composite2ExitEditPart);

		insideStateFrame = insideStateEditPart.getFigure().getBounds();
		Rectangle insideStateCompartmentFrame = insideStateEditPart.getChildBySemanticHint(StateCompartmentEditPartTN.VISUAL_ID).getFigure().getBounds();
		simple1Frame = simple1EditPart.getFigure().getBounds().getTranslated(insideStateCompartmentFrame.getLocation());
		checkFrame(simple1Frame, simple1EditPart);
		simple2Frame = simple2EditPart.getFigure().getBounds().getTranslated(insideStateCompartmentFrame.getLocation());
		checkFrame(simple2Frame, simple2EditPart);
		composite1Frame = composite1EditPart.getFigure().getBounds().getTranslated(insideStateCompartmentFrame.getLocation());
		checkFrame(composite1Frame, composite1EditPart);
		composite2Frame = composite2EditPart.getFigure().getBounds().getTranslated(insideStateCompartmentFrame.getLocation());
		checkFrame(composite2Frame, composite2EditPart);

		composite1ExitFrame = composite1ExitEditPart.getFigure().getBounds().getTranslated(insideStateCompartmentFrame.getLocation());
		checkFrame(composite1ExitFrame, composite1ExitEditPart);
		composite1EntryFrame = composite1EntryEditPart.getFigure().getBounds().getTranslated(insideStateCompartmentFrame.getLocation());
		checkFrame(composite1EntryFrame, composite1EntryEditPart);

		composite2ExitFrame = composite2ExitEditPart.getFigure().getBounds().getTranslated(insideStateCompartmentFrame.getLocation());
		checkFrame(composite2ExitFrame, composite2ExitEditPart);
		composite2EntryFrame = composite2EntryEditPart.getFigure().getBounds().getTranslated(insideStateCompartmentFrame.getLocation());
		checkFrame(composite2EntryFrame, composite2EntryEditPart);

		insideState_entryFrame = insideState_entryEditPart.getFigure().getBounds();
		checkFrame(insideState_entryFrame, insideState_entryEditPart);
		insideState_exitFrame = insideState_exitEditPart.getFigure().getBounds();
		checkFrame(insideState_exitFrame, insideState_exitEditPart);
	}

	protected void checkFrame(Rectangle frame, IGraphicalEditPart editPart) {
		assertThat(((View) editPart.getViewer().findObjectAt(frame.getCenter()).getModel()).getElement(), equalTo(editPart.resolveSemanticElement()));
		assertThat(((View) editPart.getViewer().findObjectAt(getLeftLocation(frame)).getModel()).getElement(), equalTo(editPart.resolveSemanticElement()));
		assertThat(((View) editPart.getViewer().findObjectAt(getRightLocation(frame)).getModel()).getElement(), equalTo(editPart.resolveSemanticElement()));
		assertThat(((View) editPart.getViewer().findObjectAt(getTopLocation(frame)).getModel()).getElement(), equalTo(editPart.resolveSemanticElement()));
		assertThat(((View) editPart.getViewer().findObjectAt(getBottomLocation(frame)).getModel()).getElement(), equalTo(editPart.resolveSemanticElement()));
	}

	//
	// entry point creation - state machine parent diagram opened.
	//

	@Test
	@NeedsUIEvents
	public void entryPointOnTopBorder() throws Exception {
		runEntryPointCreation(getTopLocation(insideStateFrame), false);
	}

	@Test
	@NeedsUIEvents
	public void entryPointOnTopLeftCorner() throws Exception {
		runEntryPointCreation(getTopLeftLocation(insideStateFrame), false);
	}

	@Test
	@NeedsUIEvents
	public void entryPointOnLeftBorder() throws Exception {
		runEntryPointCreation(getLeftLocation(insideStateFrame), false);
	}

	@Test
	@NeedsUIEvents
	public void entryPointOnBottomLeftBorder() throws Exception {
		runEntryPointCreation(getBottomLeftLocation(insideStateFrame), false);
	}

	@Test
	@NeedsUIEvents
	public void entryPointOnBottomBorder() throws Exception {
		runEntryPointCreation(getBottomLocation(insideStateFrame), false);
	}

	@Test
	@NeedsUIEvents
	public void entryPointOnBottomRightBorder() throws Exception {
		runEntryPointCreation(getBottomRightLocation(insideStateFrame), false);
	}

	@Test
	@NeedsUIEvents
	public void entryPointOnRightBorder() throws Exception {
		runEntryPointCreation(getRightLocation(insideStateFrame), false);
	}

	@Test
	@NeedsUIEvents
	@FailingTest("top right is not exact (on the left of the corner")
	public void entryPointOnTopRightBorder() throws Exception {
		runEntryPointCreation(getTopRightLocation(insideStateFrame), false);
	}

	//
	// entry point creation - state machine parent diagram closed.
	//

	@Test
	@NeedsUIEvents
	public void entryPointOnTopBorderClosed() throws Exception {
		runEntryPointCreation(getTopLocation(insideStateFrame), true);
	}

	@Test
	@NeedsUIEvents
	public void entryPointOnTopLeftCornerClosed() throws Exception {
		runEntryPointCreation(getTopLeftLocation(insideStateFrame), true);
	}

	@Test
	@NeedsUIEvents
	public void entryPointOnLeftBorderClosed() throws Exception {
		runEntryPointCreation(getLeftLocation(insideStateFrame), true);
	}

	@Test
	@NeedsUIEvents
	public void entryPointOnBottomLeftBorderClosed() throws Exception {
		runEntryPointCreation(getBottomLeftLocation(insideStateFrame), true);
	}

	@Test
	@NeedsUIEvents
	public void entryPointOnBottomBorderClosed() throws Exception {
		runEntryPointCreation(getBottomLocation(insideStateFrame), true);
	}

	@Test
	@NeedsUIEvents
	public void entryPointOnBottomRightBorderClosed() throws Exception {
		runEntryPointCreation(getBottomRightLocation(insideStateFrame), true);
	}

	@Test
	@NeedsUIEvents
	public void entryPointOnRightBorderClosed() throws Exception {
		runEntryPointCreation(getRightLocation(insideStateFrame), true);
	}

	@Test
	@NeedsUIEvents
	@FailingTest("top right is not exact (on the left of the corner")
	public void entryPointOnTopRightBorderClosed() throws Exception {
		runEntryPointCreation(getTopRightLocation(insideStateFrame), true);
	}

	@Test
	@NeedsUIEvents
	public void transitionFromSimpleToOtherSimple() throws Exception {
		// REQ 1) Source and target are both simple states. Then an external transitions is created with the selected states as source and target.
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(insideState_region, simple1EditPart, simple2EditPart, simple1Frame.getBottom().getTranslated(0, -4), simple2Frame.getTop());
		runCreationConnexionTest(creationParameters, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE), postEdit((transition) -> {
			// check semantic
			assertThat(transition, instanceOf(Transition.class));
			assertThat(((Transition) transition).getContainer(), equalTo(creationParameters.container));
			assertThat(((Transition) transition).getSource(), equalTo(creationParameters.getSource()));
			assertThat(((Transition) transition).getTarget(), equalTo(creationParameters.getTarget()));
			assertThat(((Transition) transition).getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));

			// check edit parts
			ConnectionEditPart transitionEP = (ConnectionEditPart) getConnectionEditPart(transition);
			assertThat(transitionEP, instanceOf(RTTransitionEditPart.class));
			assertThat((IGraphicalEditPart) transitionEP.getSource(), equalTo(creationParameters.sourceEditPart));
			assertThat((IGraphicalEditPart) transitionEP.getTarget(), equalTo(creationParameters.targetEditPart));

			// check location
			assertThat(transitionEP.getFigure(), instanceOf(TransitionFigure.class));
			TransitionFigure edge = (TransitionFigure) transitionEP.getFigure();
			assertThat(edge.getStart(), near(creationParameters.sourceLocation, TOLERANCE));
			assertThat(edge.getEnd(), near(creationParameters.targetLocation, TOLERANCE));
		}));
	}

	@Test
	@NeedsUIEvents
	public void transitionFromSimpleToItself() throws Exception {
		// REQ 4) If source and target is the same simple state, then an external self-transition should be created.
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(insideState_region, simple1EditPart, simple1EditPart, getBottomLocation(simple1Frame), getRightLocation(simple1Frame));
		runCreationConnexionTest(creationParameters, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE), postEdit((transition) -> {
			assertThat(((Transition) transition).getSource(), equalTo(creationParameters.getSource()));
			assertThat(((Transition) transition).getTarget(), equalTo(creationParameters.getTarget()));
			assertThat(((Transition) transition).getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));
			assertThat(((Transition) transition).getContainer(), equalTo(creationParameters.container));

			// check edit parts
			ConnectionEditPart transitionEP = (ConnectionEditPart) getConnectionEditPart(transition);
			assertThat(transitionEP, instanceOf(RTTransitionEditPart.class));
			assertThat((IGraphicalEditPart) transitionEP.getSource(), equalTo(creationParameters.sourceEditPart));
			assertThat((IGraphicalEditPart) transitionEP.getTarget(), equalTo(creationParameters.targetEditPart));

			// no check of location, edges from a node to the same node is not working properly in general...
			assertThat(transitionEP.getFigure(), instanceOf(TransitionFigure.class));
			// TransitionFigure edge = (TransitionFigure) transitionEP.getFigure();
			// assertThat(edge.getStart(), near(creationParameters.sourceLocation, TOLERANCE));
			// assertThat(edge.getEnd(), near(creationParameters.targetLocation, TOLERANCE));
		}));
	}

	@Test
	@NeedsUIEvents
	public void transitionFromCompositeToSimple() throws Exception {
		// REQ 2) If the selected source is a composite state, then an exit point pseudo state for that composite state
		// should be automatically be created and be used as the source for the transition.
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(insideState_region, composite1EditPart, simple1EditPart, getLeftLocation(composite1Frame), getRightLocation(simple1Frame));
		runCreationConnexionTest(creationParameters, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE), postEdit((transition) -> {
			// ensure the current diagram is the correct one.
			editor.activateDiagram(capsule.getName() + ".." + insideState.getName());
			// check semantic part
			assertThat(((Transition) transition).getContainer(), equalTo(creationParameters.container));
			assertThat(((Transition) transition).getTarget(), equalTo(creationParameters.getTarget()));
			assertThat(((Transition) transition).getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));

			State source = (State) creationParameters.getSource();
			Pseudostate sourceExitPoint = findExitPoint(source, (Transition) transition);
			assertThat(sourceExitPoint, notNullValue());

			// check graphical part
			ConnectionEditPart transitionEP = (ConnectionEditPart) getConnectionEditPart(transition);
			assertThat(transitionEP, instanceOf(RTTransitionEditPart.class));

			// source
			IGraphicalEditPart sourceEP = (IGraphicalEditPart) transitionEP.getSource();
			assertThat(sourceEP, instanceOf(PseudostateExitPointEditPart.class));
			assertThat(ViewUtil.resolveSemanticElement(((View) sourceEP.getModel())), equalTo(sourceExitPoint));

			// target
			assertThat((IGraphicalEditPart) transitionEP.getTarget(), equalTo(simple1EditPart));

			// check location. Warning, size of pseudo-state may influence
			assertThat(transitionEP.getFigure(), instanceOf(TransitionFigure.class));
			TransitionFigure edge = (TransitionFigure) transitionEP.getFigure();
			assertThat(edge.getStart(), near(creationParameters.sourceLocation, TOLERANCE));
			assertThat(edge.getEnd(), near(creationParameters.targetLocation, TOLERANCE));

			Rectangle stateAsChild = composite1EditPart.getFigure().getBounds().getCopy();
			Rectangle pseudoStateAsChild = sourceEP.getFigure().getBounds().getCopy();
			pseudoStateAsChild.translate(stateAsChild.getLocation().getNegated());
			RelativePortLocation locAsChild = RelativePortLocation.of(pseudoStateAsChild, stateAsChild);

			// now activate the "inside" diagram of composite state, and check relative location of Pseudostate
			editor.openDiagram(capsule.getName() + ".." + composite1.getName());
			// check location of the exit pseudo state
			IGraphicalEditPart pseudoStateEP = requireEditPart(sourceExitPoint);
			assertThat(pseudoStateEP, instanceOf(RTPseudostateExitPointEditPart.class));
			IGraphicalEditPart compositeEP = requireEditPart(composite1).getChildBySemanticHint(StateEditPartTN.VISUAL_ID); // real state ep as top node, not the diagram itself
			assertThat(compositeEP, notNullValue());
			Rectangle stateAsTop = compositeEP.getFigure().getBounds().getCopy();
			Rectangle pseudoStateInTop = pseudoStateEP.getFigure().getBounds().getCopy();
			pseudoStateInTop.translate(stateAsTop.getLocation().getNegated());

			RelativePortLocation locAsTop = RelativePortLocation.of(pseudoStateInTop, stateAsTop);

			assertThat(locAsTop, nearLoc(locAsChild, TOLERANCE));

			// get back to original diagram
			editor.activateDiagram(capsule.getName() + ".." + insideState.getName());
		}));
	}

	@Test
	@NeedsUIEvents
	public void transitionFromSimpleToComposite() throws Exception {
		/*
		 * If the selected target is a composite state, then an entry point pseudo state for
		 * that composite state should be automatically be created and be used as the target for the transition
		 */
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(insideState_region, simple1EditPart, composite1EditPart, getRightLocation(simple1Frame), getLeftLocation(composite1Frame));
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

			// check location. Warning, size of pseudo-state may influence
			assertThat(transitionEP.getFigure(), instanceOf(TransitionFigure.class));
			TransitionFigure edge = (TransitionFigure) transitionEP.getFigure();
			assertThat(edge.getStart(), near(creationParameters.sourceLocation, TOLERANCE));
			assertThat(edge.getEnd(), near(creationParameters.targetLocation, TOLERANCE));

			Rectangle stateAsChild = composite1EditPart.getFigure().getBounds().getCopy();
			Rectangle pseudoStateAsChild = targetEP.getFigure().getBounds().getCopy();
			pseudoStateAsChild.translate(stateAsChild.getLocation().getNegated());
			RelativePortLocation locAsChild = RelativePortLocation.of(pseudoStateAsChild, stateAsChild);

			// now activate the "inside" diagram of composite state, and check relative location of Pseudostate
			editor.openDiagram(capsule.getName() + ".." + composite1.getName());

			// check location of the exit pseudo state
			IGraphicalEditPart pseudoStateEP = requireEditPart(targetEntryPoint);
			assertThat(pseudoStateEP, instanceOf(RTPseudostateEntryPointEditPart.class));
			IFigure fig = ((RTPseudostateEntryPointEditPart) pseudoStateEP).getFigure();
			IGraphicalEditPart compositeEP = requireEditPart(composite1).getChildBySemanticHint(StateEditPartTN.VISUAL_ID);

			Rectangle stateAsTop = compositeEP.getFigure().getBounds().getCopy();
			Rectangle pseudoStateInTop = pseudoStateEP.getFigure().getBounds().getCopy();
			pseudoStateInTop.translate(stateAsTop.getLocation().getNegated());

			RelativePortLocation locAsTop = RelativePortLocation.of(pseudoStateInTop, stateAsTop);
			assertThat(locAsTop, nearLoc(locAsChild, TOLERANCE));

			// get back to original diagram
			editor.activateDiagram("TransitionCreation..InsideState");
		}));
	}

	@Test
	@NeedsUIEvents
	public void transitionFromExitToSelfEntry() throws Exception {
		/*
		 * If the selected target is a composite state, then an entry point pseudo state for
		 * that composite state should be automatically be created and be used as the target for the transition
		 */
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(insideState_region, composite1ExitEditPart, composite1EntryEditPart, composite1ExitFrame.getCenter(), composite1EntryFrame.getCenter());
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
			assertThat(ViewUtil.resolveSemanticElement(((View) sourceEP.getModel())), equalTo(sourceExitPoint));

			assertThat((IGraphicalEditPart) transitionEP.getTarget(), equalTo(composite1EntryEditPart));
			IGraphicalEditPart targetEP = (IGraphicalEditPart) transitionEP.getTarget();
			assertThat(targetEP, instanceOf(PseudostateEntryPointEditPart.class));
			assertThat(ViewUtil.resolveSemanticElement(((View) targetEP.getModel())), equalTo(targetEntryPoint));

			// check location. Warning, size of pseudo-state may influence
			assertThat(transitionEP.getFigure(), instanceOf(TransitionFigure.class));
			TransitionFigure edge = (TransitionFigure) transitionEP.getFigure();
			assertThat(edge.getStart(), near(creationParameters.sourceLocation, TOLERANCE));
			assertThat(edge.getEnd(), near(creationParameters.targetLocation, TOLERANCE));
		}));
	}

	@Test
	public void transitionFromEntryToSelfEntry() throws Exception {
		/*
		 * If the selected target is a composite state, then an entry point pseudo state for
		 * that composite state should be automatically be created and be used as the target for the transition
		 */
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(insideState_region, composite1EntryEditPart, composite1EntryEditPart);
		runNotCreationConnexionTest(creationParameters, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE));
	}

	@Test
	@NeedsUIEvents
	public void transitionFromBorderExitToSelfBorder() throws Exception {
		/*
		 * 6) If the source and target is the same composite state in the context of the state itself,
		 * i.e. on the "inside" of the state selecting the border of the state itself, then an local
		 * self-transition shall be created and exit point and entry point pseudo states shall
		 * automatically be created for the composite state, and used as source respectively target of
		 * the transition, i.e. corresponding to a combination of case 2 and 3.
		 * Here, we already have source and target pseudostates
		 */
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(insideState_region, insideState_exitEditPart, insideStateEditPart, getTopLocation(insideState_exitFrame), getTopLocation(insideStateFrame));
		runCreationConnexionTest(creationParameters, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE), postEdit((transition) -> {
			Pseudostate sourceExitPoint = (Pseudostate) creationParameters.getSource();
			assertThat(((Transition) transition).getSource(), equalTo(sourceExitPoint));

			Pseudostate targetEntryPoint = findEntryPoint((State) creationParameters.getTarget(), (Transition) transition);
			assertThat(targetEntryPoint, notNullValue());
			assertThat(targetEntryPoint.getName(), nullOrEmpty());

			assertThat(((Transition) transition).getKind(), equalTo(TransitionKind.LOCAL_LITERAL));
			assertThat(((Transition) transition).getContainer(), equalTo(creationParameters.container));

			ConnectionEditPart transitionEP = (ConnectionEditPart) getConnectionEditPart(transition);
			assertThat(transitionEP, instanceOf(RTTransitionEditPart.class));

			assertThat((IGraphicalEditPart) transitionEP.getSource(), equalTo(insideState_exitEditPart));
			IGraphicalEditPart sourceEP = (IGraphicalEditPart) transitionEP.getSource();
			assertThat(sourceEP, instanceOf(PseudostateExitPointEditPart.class));
			assertThat(ViewUtil.resolveSemanticElement(((View) sourceEP.getModel())), equalTo(sourceExitPoint));

			IGraphicalEditPart targetEP = (IGraphicalEditPart) transitionEP.getTarget();
			assertThat(targetEP, instanceOf(PseudostateEntryPointEditPart.class));
			assertThat(ViewUtil.resolveSemanticElement(((View) targetEP.getModel())), equalTo(targetEntryPoint));

			// check location. Warning, size of pseudo-state may influence
			assertThat(transitionEP.getFigure(), instanceOf(TransitionFigure.class));
			TransitionFigure edge = (TransitionFigure) transitionEP.getFigure();
			assertThat(edge.getStart(), near(creationParameters.sourceLocation, TOLERANCE));
			assertThat(edge.getEnd(), near(creationParameters.targetLocation, TOLERANCE));
		}));
	}

	@Test
	@NeedsUIEvents
	public void transitionFromBorderToBorder() throws Exception {
		/*
		 * 6) If the source and target is the same composite state in the context of the state itself,
		 * i.e. on the "inside" of the state selecting the border of the state itself, then an local
		 * self-transition shall be created and exit point and entry point pseudo states shall
		 * automatically be created for the composite state, and used as source respectively target of
		 * the transition, i.e. corresponding to a combination of case 2 and 3.
		 * Here, we do not have source and target pseudostates
		 */
		ConnectionCreationParameters creationParameters = new ConnectionCreationParameters(insideState_region, insideStateEditPart, insideStateEditPart, getBottomLocation(insideStateFrame), getRightLocation(insideStateFrame));
		runCreationConnexionTest(creationParameters, Collections.singletonList(UMLDIElementTypes.TRANSITION_EDGE), postEdit((transition) -> {
			Pseudostate source = (Pseudostate) ((Transition) transition).getSource();
			assertThat(source.getKind(), equalTo(PseudostateKind.EXIT_POINT_LITERAL));

			Pseudostate target = (Pseudostate) ((Transition) transition).getTarget();
			assertThat(target.getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));

			assertThat(((Transition) transition).getKind(), equalTo(TransitionKind.LOCAL_LITERAL));
			assertThat(((Transition) transition).getContainer(), equalTo(creationParameters.container));

			ConnectionEditPart transitionEP = (ConnectionEditPart) getConnectionEditPart(transition);
			assertThat(transitionEP, instanceOf(RTTransitionEditPart.class));

			IGraphicalEditPart sourceEP = getEditPart(source);
			IGraphicalEditPart targetEP = getEditPart(target);

			assertThat((IGraphicalEditPart) transitionEP.getSource(), equalTo(sourceEP));
			assertThat(sourceEP, instanceOf(PseudostateExitPointEditPart.class));
			assertThat(ViewUtil.resolveSemanticElement(((View) sourceEP.getModel())), equalTo(source));

			assertThat((IGraphicalEditPart) transitionEP.getTarget(), equalTo(targetEP));
			assertThat(targetEP, instanceOf(PseudostateEntryPointEditPart.class));
			assertThat(ViewUtil.resolveSemanticElement(((View) targetEP.getModel())), equalTo(target));

			// check location.
			assertThat(transitionEP.getFigure(), instanceOf(TransitionFigure.class));
			assertThat(sourceEP.getFigure().getBounds().getCenter(), near(creationParameters.sourceLocation, TOLERANCE));
			assertThat(targetEP.getFigure().getBounds().getCenter(), near(creationParameters.targetLocation, TOLERANCE));
		}));
	}


	//
	// shared test methods
	//
	private void runEntryPointCreation(Point location, boolean parentDiagClosed) throws Exception {
		// create entry point on the top location of the composite state
		List<String> closedDiagrams;
		String currentDiagName = capsule.getName() + ".." + insideState.getName();
		String parentDiagName = capsule.getName() + "::" + capsule1_stateMachine.getName();
		if (parentDiagClosed) {
			closedDiagrams = Arrays.asList(parentDiagName);
			editor.closeDiagram(parentDiagName);
			editor.activateDiagram(currentDiagName);
		} else {
			closedDiagrams = Collections.emptyList();
			editor.openDiagram(parentDiagName);
			editor.activateDiagram(currentDiagName);
		}
		NodeCreationParameters creationParameters = new NodeCreationParameters(insideStateEditPart, location, RTStateMachineTypes.PSEUDOSTATE_ENTRY_NODE.getType(), closedDiagrams, Collections.emptyList());
		runCreationNodeTest(creationParameters, postEdit((element) -> {
			// check semantic
			;
			assertThat(element, instanceOf(Pseudostate.class));
			assertThat(((Pseudostate) element).getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));
			assertThat(insideState.getConnectionPoints(), hasItem((Pseudostate) element));

			// check edit parts
			IGraphicalEditPart editPart = requireEditPart(element);
			assertThat(editPart, instanceOf(RTPseudostateEntryPointEditPart.class));

			// check location
			assertThat(editPart.getFigure(), instanceOf(BorderedNodeFigure.class));
			BorderedNodeFigure figure = (BorderedNodeFigure) editPart.getFigure();
			assertThat(figure.getBounds().getCenter(), near(creationParameters.targetLocation, TOLERANCE));

			// check location in parent diagram
			editor.openDiagram(parentDiagName);
			// check location from pseudostate
			IGraphicalEditPart editPartAsChild = requireEditPart(element);
			assertThat(editPartAsChild, instanceOf(RTPseudostateEntryPointEditPart.class));

			// check location
			assertThat(editPartAsChild.getFigure(), instanceOf(BorderedNodeFigure.class));
			BorderedNodeFigure figureAsChild = (BorderedNodeFigure) editPartAsChild.getFigure();

			// insidestate as Child
			// check location from pseudostate
			IGraphicalEditPart stateAsChild = requireEditPart(creationParameters.getTargetElement());
			assertThat(stateAsChild, instanceOf(RTStateEditPart.class));

			// check location
			IFigure stateAsChildFigure = stateAsChild.getFigure();

			// assert relative location is similar
			Rectangle absoluteFigureAsChild = figureAsChild.getBounds().getCopy();
			Point stateLocation = stateAsChildFigure.getBounds().getCopy().getLocation();
			Rectangle relativeLocation = absoluteFigureAsChild.getTranslated(stateLocation.getNegated());
			RelativePortLocation asChild = RelativePortLocation.of(relativeLocation, stateAsChildFigure.getBounds());

			RelativePortLocation inTop = RelativePortLocation.of(figure.getBounds().getTranslated(insideStateFrame.getLocation().getNegated()), insideStateFrame);
			assertThat(asChild, nearLoc(inTop, TOLERANCE));

			// switch back to original diagram
			editor.activateDiagram(currentDiagName);
			// if test is done diagram closed, close diagram after checks
			if (creationParameters.closedDiagrams.contains(parentDiagName)) {
				editor.closeDiagram(parentDiagName);
			}
		}));
	}


	//
	// Utility to be ported to rt junit utils
	//
	/**
	 * Obtains a matcher that tests whether a point is within a tolerance of an
	 * expected location.
	 * 
	 * @param x
	 *            the x coördinate of the expected location
	 * @param y
	 *            the y coördinate of the expected location
	 * @param plusOrMinus
	 *            the tolerance for error
	 * 
	 * @return the matcher
	 */
	public static Matcher<RelativePortLocation> nearLoc(RelativePortLocation loc, int plusOrMinus) {
		return new BaseMatcher<RelativePortLocation>() {
			@Override
			public void describeTo(Description description) {
				description.appendText(loc.toString());
			}

			@Override
			public boolean matches(Object item) {
				boolean result = false;

				if (item instanceof RelativePortLocation) {
					RelativePortLocation relativePortLocation = (RelativePortLocation) item;
					result = (Math.abs(relativePortLocation.relativePosition() - loc.relativePosition()) <= plusOrMinus
							&& relativePortLocation.side() == loc.side());
					if (!result) {
						// may happen near 0% or 100%, siwtch to another side
						if (relativePortLocation.relativePosition() < plusOrMinus || relativePortLocation.relativePosition() > 100 - plusOrMinus) {
							// check the nearby side...
							result = checkNearBySide(relativePortLocation);
						}
					}

				}
				return result;
			}

			private boolean checkNearBySide(RelativePortLocation relativePortLocation) {
				int relativePosition = relativePortLocation.relativePosition();
				int side = relativePortLocation.side();

				switch (loc.side()) {
				case PositionConstants.NORTH:
					if (relativePosition < plusOrMinus) {
						return (Math.abs(relativePosition) <= plusOrMinus
								&& side == PositionConstants.WEST);
					} else if (relativePosition > 100 - plusOrMinus) {
						return (Math.abs(relativePosition) <= plusOrMinus
								&& side == PositionConstants.EAST);
					}
					break;
				case PositionConstants.EAST:
					if (relativePosition < plusOrMinus) {
						return (Math.abs(relativePosition) > 100 - plusOrMinus
								&& side == PositionConstants.NORTH);
					} else if (relativePosition > 100 - plusOrMinus) {
						return (Math.abs(relativePosition) > 100 - plusOrMinus
								&& side == PositionConstants.SOUTH);
					}
					break;
				case PositionConstants.SOUTH:
					if (relativePosition < plusOrMinus) {
						return (Math.abs(relativePosition) > 100 - plusOrMinus
								&& side == PositionConstants.WEST);
					} else if (relativePosition > 100 - plusOrMinus) {
						return (Math.abs(relativePosition) > 100 - plusOrMinus
								&& side == PositionConstants.EAST);
					}
					break;
				case PositionConstants.WEST:
					if (relativePosition < plusOrMinus) {
						return (Math.abs(relativePosition) <= plusOrMinus
								&& side == PositionConstants.NORTH);
					} else if (relativePosition > 100 - plusOrMinus) {
						return (Math.abs(relativePosition) <= plusOrMinus
								&& side == PositionConstants.SOUTH);
					}
					break;
				default:
					throw new RuntimeException("impossible to compute near");
				}
				return false;
			}
		};
	}

}
