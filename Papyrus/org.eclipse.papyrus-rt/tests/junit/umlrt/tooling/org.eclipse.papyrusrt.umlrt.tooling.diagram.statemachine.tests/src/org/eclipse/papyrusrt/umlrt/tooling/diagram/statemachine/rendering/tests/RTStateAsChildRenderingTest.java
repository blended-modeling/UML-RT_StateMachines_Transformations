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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.junit.utils.DiagramUtils;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTState;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Tests on {@link RTState} display in RT state machine diagram (layout, style, etc.)?.
 */
@ActiveDiagram("Capsule2::StateMachine")
@PluginResource("/resource/statemachine/model.di")
public class RTStateAsChildRenderingTest extends AbstractCanonicalTest {

	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule elementTypesRule = new ElementTypesRule();

	private Class capsule2;
	private StateMachine capsule2_stateMachine;
	private Region capsule2_stateMachine_region;
	private State capsule2_stateMachine_state1;
	private State capsule2_stateMachine_stateWithInternalTransitions;


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

		capsule2_stateMachine_stateWithInternalTransitions = (State) capsule2_stateMachine_region.getSubvertex("StateWithInternalTransitions", true, UMLPackage.eINSTANCE.getState(), false);
		assertNotNull(capsule2_stateMachine_stateWithInternalTransitions);
	}

	/**
	 * Constructor.
	 */
	public RTStateAsChildRenderingTest() {
		// empty
	}

	/**
	 * Checks the display of states (visible compartments, etc.)
	 */
	@Test
	public void checkChildState() throws Exception {
		View state1AsChildView = getView(capsule2_stateMachine_state1);
		assertNotNull(state1AsChildView);
		assertThat(state1AsChildView, instanceOf(Shape.class));
		assertThat(((Shape) state1AsChildView).getFillColor(), equalTo(14012867)); // blue
		assertThat(((Shape) state1AsChildView).getFontColor(), equalTo(0)); // black

		// check children
		assertThat(state1AsChildView.getChildren().size(), greaterThan(1));
		View regionCompartmentView = DiagramUtils.getChildView(state1AsChildView, NotationPackage.eINSTANCE.getBasicCompartment(), "State_RegionCompartment");
		assertThat(regionCompartmentView, notNullValue());
		assertThat(regionCompartmentView.isVisible(), is(false)); // region compartment should not be visible for state as child

	}

	/**
	 * Checks the display of states (visible compartments, etc.)
	 */
	@Test
	public void checkChildStateWithInternalTransitions() throws Exception {
		View state1AsChildView = getView(capsule2_stateMachine_stateWithInternalTransitions);
		assertNotNull(state1AsChildView);
		assertThat(state1AsChildView, instanceOf(Shape.class));
		assertThat(((Shape) state1AsChildView).getFillColor(), equalTo(14012867)); // blue
		assertThat(((Shape) state1AsChildView).getFontColor(), equalTo(0)); // black

		// check children
		assertThat(state1AsChildView.getChildren().size(), greaterThan(1));
		View regionCompartmentView = DiagramUtils.getChildView(state1AsChildView, NotationPackage.eINSTANCE.getBasicCompartment(), "State_RegionCompartment");
		assertThat(regionCompartmentView, notNullValue());
		assertThat(regionCompartmentView.isVisible(), is(false)); // region compartment should not be visible for state as child
	}
}
