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
 *   Christian W. Damus - bugs 513067, 510323
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.decorators.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.Decoration;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.junit.framework.classification.ClassificationRunnerWithParametersFactory;
import org.eclipse.papyrus.junit.framework.classification.NotImplemented;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTTransitionEditPart;
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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;

/**
 * Tests for the decorations for transitions as edges.
 */
@RunWith(Parameterized.class)
@DiagramNaming(StateMachineDiagramNameStrategy.class)
@PluginResource("/resource/statemachine/model.di")
@UseParametersRunnerFactory(ClassificationRunnerWithParametersFactory.class)
public class TransitionDecoratorsTests extends AbstractCanonicalTest {

	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule elementTypesRule = new ElementTypesRule();

	private Class capsule;
	private StateMachine stateMachine;
	private Region region;
	private State source;
	private State target;
	private Transition t0;
	private Transition t1;
	private Transition t2;
	private Transition t3;
	private Transition t4;
	private Transition t5;

	private final String capsuleName;

	/**
	 * Constructor.
	 */
	public TransitionDecoratorsTests(String capsuleName, String ignoredDescription) {
		super();

		this.capsuleName = capsuleName;
	}

	@Parameters(name = "{1}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ "Transitions", "root" },
				{ "SubTransitions", "redefinition" },
		});
	}

	@Before
	public void getFixtures() {
		capsule = (Class) editor.getModel().getOwnedType(capsuleName);
		assertNotNull(capsule);
		stateMachine = (StateMachine) capsule.getClassifierBehavior();
		assertNotNull(stateMachine);
		region = stateMachine.getRegion("Region", false, false);
		assertNotNull(region);
		source = (State) region.getSubvertex("Source", true, UMLPackage.eINSTANCE.getState(), false);
		assertNotNull(source);
		target = (State) region.getSubvertex("Target", true, UMLPackage.eINSTANCE.getState(), false);
		assertNotNull(target);

		// t0 = no decoration
		t0 = source.getOutgoings().get(0);
		assertNotNull(t0);
		assertThat(t0.getName(), nullValue());

		// t1 is named, but no trigger => check warning deco
		t1 = source.getOutgoings().get(1);
		assertNotNull(t1);
		assertThat(t1.getName(), equalTo("t1"));

		// t2 is named, but has a trigger => no warning?
		t2 = source.getOutgoings().get(2);
		assertNotNull(t2);
		assertThat(t2.getName(), equalTo("t2"));

		// t3 is named, no trigger and a guard => should display warning & shield
		t3 = source.getOutgoings().get(3);
		assertNotNull(t3);
		assertThat(t3.getName(), equalTo("t3"));

		// t4 is named, no trigger, a guard and an effect => 3 decorations are expected
		t4 = source.getOutgoings().get(4);
		assertNotNull(t4);
		assertThat(t4.getName(), equalTo("t4"));

		// t5 is named, has a trigger with guard and no effect => shield expected
		t5 = source.getOutgoings().get(5);
		assertNotNull(t5);
		assertThat(t5.getName(), equalTo("t5"));

		// Activate our capsule's state machine diagram
		editor.openDiagram(capsuleName + "::" + stateMachine.getName());
	}

	/**
	 * Checks the display of first transition decorators
	 */
	@Test
	public void checkNoDecoration() throws Exception {
		IGraphicalEditPart t0editpart = getEditPart(t0);
		assertThat(t0editpart, instanceOf(RTTransitionEditPart.class));
		View t0View = getView(t0);
		assertThat(t0View, instanceOf(Edge.class));

		List<Decoration> decorations = retrieveDecorations(t0editpart);
		assertThat(decorations, isEmpty());
	}

	/**
	 * Checks the display of transition with warning decorator
	 */
	@Test
	@NotImplemented("Trigger warning decorator disabled by bug 515492 until bug 511102 have been resolved")
	public void checkWarningDecoration() throws Exception {
		IGraphicalEditPart transitionEditpart = getEditPart(t1);
		assertThat(transitionEditpart, instanceOf(RTTransitionEditPart.class));

		List<Decoration> decorations = retrieveDecorations(transitionEditpart);
		assertThat(decorations.size(), equalTo(1));
	}

	/**
	 * Checks the display of transition with a name and a trigger (no deco)
	 */
	@Test
	public void checkNamedButTriggeredDecoration() throws Exception {
		IGraphicalEditPart transitionEditpart = getEditPart(t2);
		assertThat(transitionEditpart, instanceOf(RTTransitionEditPart.class));

		List<Decoration> decorations = retrieveDecorations(transitionEditpart);
		assertThat(decorations.size(), equalTo(0));
	}

	/**
	 * Checks the display of transition with a name and a trigger (no deco)
	 */
	@Test
	@NotImplemented("Trigger warning decorator disabled by bug 515492 until bug 511102 have been resolved")
	public void checkWarningAndShieldDecoration() throws Exception {
		IGraphicalEditPart transitionEditpart = getEditPart(t3);
		assertThat(transitionEditpart, instanceOf(RTTransitionEditPart.class));

		List<Decoration> decorations = retrieveDecorations(transitionEditpart);
		assertThat(decorations.size(), equalTo(2));
	}

	/**
	 * Checks the display of transition with a name and a trigger (no deco)
	 */
	@Test
	@NotImplemented("Trigger warning decorator disabled by bug 515492 until bug 511102 have been resolved")
	public void checkAllDecorationsDecoration() throws Exception {
		IGraphicalEditPart transitionEditpart = getEditPart(t4);
		assertThat(transitionEditpart, instanceOf(RTTransitionEditPart.class));

		List<Decoration> decorations = retrieveDecorations(transitionEditpart);
		assertThat(decorations.size(), equalTo(3));
	}

	/**
	 * Checks the display of transition with a name and a trigger (no deco)
	 */
	@Test
	public void checkTriggerGuardAndEffectDecoration() throws Exception {
		IGraphicalEditPart transitionEditpart = getEditPart(t5);
		assertThat(transitionEditpart, instanceOf(RTTransitionEditPart.class));

		List<Decoration> decorations = retrieveDecorations(transitionEditpart);
		assertThat(decorations.size(), equalTo(2));
	}
}
