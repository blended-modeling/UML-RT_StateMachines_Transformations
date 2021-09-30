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

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.AbstractDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.Decoration;
import org.eclipse.gmf.runtime.notation.CanonicalStyle;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.junit.utils.CreationUtils;
import org.eclipse.papyrusrt.junit.utils.DiagramUtils;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.TransitionUtils;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTState;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.decorators.StateWithInternalTransitionDecorator;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Tests on {@link RTState} display in RT state machine diagram (layout, style, etc.)?.
 */
@ActiveDiagram("Capsule2::State1")
@PluginResource("/resource/statemachine/model.di")
public class RTStateAsRootRenderingTest extends AbstractCanonicalTest {

	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule elementTypesRule = new ElementTypesRule();

	private Class capsule2;
	private StateMachine capsule2_stateMachine;
	private Region capsule2_stateMachine_region;
	private State capsule2_stateMachine_state1;
	private View state1AsDiagram;
	private Shape state1AsTopNode;

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
		state1AsDiagram = getView(capsule2_stateMachine_state1);
		// this should be the diagram edit part
		assertNotNull(state1AsDiagram);
		// get State as Top figure
		assertThat(state1AsDiagram.getChildren().size(), greaterThan(2)); // Top node + 2 stereotype comments
		assertThat(state1AsDiagram.getChildren().get(0), instanceOf(Shape.class));

		state1AsTopNode = (Shape) state1AsDiagram.getChildren().get(0);
		assertNotNull(state1AsTopNode);
	}

	/**
	 * Constructor.
	 */
	public RTStateAsRootRenderingTest() {
		// empty
	}

	/**
	 * Checks the display of states (visible compartments, etc.)
	 */
	@NeedsUIEvents
	@Test
	public void checkRootState() throws Exception {
		// this should be the diagram edit part
		assertNotNull(state1AsDiagram);
		assertThat(state1AsDiagram, instanceOf(Diagram.class));

		// get State as Top figure
		assertThat(state1AsDiagram.getChildren().size(), greaterThan(2)); // Top node + 2 stereotype comments
		assertThat(state1AsDiagram.getChildren().get(0), instanceOf(Shape.class));

		// check attributes of the shape and sub-elements
		// color and gradient
		assertThat(state1AsTopNode.getFillColor(), equalTo(14012867)); // blue
		assertThat(state1AsTopNode.getFontColor(), equalTo(0)); // black

		// check children
		assertThat(state1AsTopNode.getChildren().size(), greaterThan(1));

		View regionCompartmentView = DiagramUtils.getChildView(state1AsTopNode, null, "State_RegionCompartment_TN");
		assertThat(regionCompartmentView, notNullValue());
		assertThat(regionCompartmentView.isVisible(), is(true));
	}

	@NeedsUIEvents
	@Test
	public void checkDecorator() throws Exception {
		// check on a state without transitions at the beginning
		// add an internal transition
		// decorator should be displayed
		// remove then the transition, it should disappear
		IGraphicalEditPart state1TNEditPart = org.eclipse.papyrus.junit.utils.DiagramUtils.findEditPartforView(editor.getEditor(), state1AsTopNode, StateEditPartTN.class);
		AbstractDecorator decorator = DiagramUtils.getDecorator(state1TNEditPart, "StateWithInternalTransitionDecoration");
		assertThat(decorator, instanceOf(StateWithInternalTransitionDecorator.class));

		StateWithInternalTransitionDecorator stateWithInternalTransitionDecorator = (StateWithInternalTransitionDecorator) decorator;
		Decoration decoration = stateWithInternalTransitionDecorator.getDecoration();
		assertThat(decoration, nullValue());

		// add an internal transition => decorator should pop up
		Command createTransitionCommand = CreationUtils.getCreateChildCommand(capsule2_stateMachine_state1, UMLRTElementTypesEnumerator.TRANSITION_INTERNAL, true, editor.getEditingDomain());
		assertThat(createTransitionCommand, isExecutable());
		editor.getEditingDomain().getCommandStack().execute(createTransitionCommand);
		decoration = stateWithInternalTransitionDecorator.getDecoration();
		assertThat(decoration, notNullValue());
		// check figure
		assertThat(decoration.isEnabled(), is(true));
		assertThat(decoration.isVisible(), is(true));
		assertThat(decoration.isShowing(), is(true));

	}

	/**
	 * Checks the display of states (visible compartments, etc.)
	 */
	@NeedsUIEvents
	@Test
	public void checkInternalTransitionsCompartmentCanonical() throws Exception {
		View internalTransitionsCompartmentView = getInternalTransitionCompartment();
		assertThat(internalTransitionsCompartmentView.isVisible(), is(true)); // internal transitions compartment should not be visible for state by default

		// org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil.isCanonical(EditPart)?
		CanonicalStyle style = (CanonicalStyle) internalTransitionsCompartmentView.getStyle(NotationPackage.Literals.CANONICAL_STYLE);
		if (style != null) {
			assertThat(style.isCanonical(), is(true));
		} else {
			fail("internal transition compartment is not canonical.");
		}

		// now create an internal transition in the model => should be displayed in the compartment
		assertThat(internalTransitionsCompartmentView.getChildren().isEmpty(), is(true));

		Command createTransitionCommand = CreationUtils.getCreateChildCommand(capsule2_stateMachine_state1, UMLRTElementTypesEnumerator.TRANSITION_INTERNAL, true, editor.getEditingDomain());
		assertThat(createTransitionCommand, isExecutable());
		editor.getEditingDomain().getCommandStack().execute(createTransitionCommand);

		// get New transition, and check it has a view in the diagram
		Transition newTransition = capsule2_stateMachine_state1.getRegions().get(0).getTransitions().get(0);
		// check it is not null and internal...
		assertThat(TransitionUtils.isInternalTransition(newTransition), is(true));

		View view = requireView(newTransition, internalTransitionsCompartmentView);
		assertThat(view, notNullValue());
		assertThat(view, instanceOf(Shape.class));

		assertThat(view.isVisible(), is(true));
	}

	protected View getInternalTransitionCompartment() {
		assertThat(state1AsTopNode.getChildren().size(), greaterThan(1));
		View internalTransitionsCompartmentView = DiagramUtils.getChildView(state1AsTopNode, null, "InternalTransitions");
		assertThat(internalTransitionsCompartmentView, notNullValue());
		return internalTransitionsCompartmentView;

	}

}
