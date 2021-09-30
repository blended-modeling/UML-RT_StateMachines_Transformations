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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.commands.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.commands.RerouteTransitionsToConnectionPointsCommand;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
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
public class RerouteTransitionsToConnectionPointsCommandTest extends AbstractCanonicalTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	private final String capsuleName;

	public RerouteTransitionsToConnectionPointsCommandTest(String capsuleName, String ignoredDesription) {
		super();

		this.capsuleName = capsuleName;
	}

	/**
	 * Regression test for creation of connection points in the refactoring of an inherited
	 * state as a composite state.
	 * 
	 * @see <a href="http://eclip.se/513195">bug 513195</a>
	 */
	@NeedsUIEvents
	@Test
	public void createTransitionInInheritedStateMachine() {
		UMLRTCapsule capsule = UMLRTPackage.getInstance(editor.getModel()).getCapsule(capsuleName);
		UMLRTStateMachine sm = capsule.getStateMachine();

		UMLRTState state2 = (UMLRTState) sm.getVertex("State2");
		assumeThat("State2 not found", state2, notNullValue());

		IGraphicalEditPart stateEditPart = requireEditPart(state2.toUML());
		ICommand command = RerouteTransitionsToConnectionPointsCommand.createRerouteTransitionsCommand(
				editor.getEditingDomain(), state2.toUML(), stateEditPart);

		markForUndo();

		editor.execute(command);

		// Look for connection points
		UMLRTConnectionPoint entry = state2.getEntryPoint(null);
		assertThat("Entry point not created", entry, notNullValue());
		UMLRTConnectionPoint exit = state2.getExitPoint(null);
		assertThat("Exit point not created", exit, notNullValue());
		IGraphicalEditPart entryEP = requireEditPart(entry.toUML());
		IGraphicalEditPart exitEP = requireEditPart(exit.toUML());

		// And, of course, they are not inherited views
		assertThat(entryEP, notInherited());
		assertThat(exitEP, notInherited());

		assertThat("Entry point has no incoming transitions",
				(List<?>) ((Node) entryEP.getNotationView()).getTargetEdges(), not(isEmpty()));
		assertThat("Exit point has no outgoing transitions",
				(List<?>) ((Node) exitEP.getNotationView()).getSourceEdges(), not(isEmpty()));

		undoToMark();

		stateEditPart = requireEditPart(state2.toUML());
		assertThat("Still have entry point view", stateEditPart.getChildBySemanticHint(PseudostateEntryPointEditPart.VISUAL_ID), nullValue());
		assertThat("Still have exit point view", stateEditPart.getChildBySemanticHint(PseudostateExitPointEditPart.VISUAL_ID), nullValue());

		redo();

		entry = state2.getEntryPoint(null);
		assertThat("Entry point not restored", entry, notNullValue());
		exit = state2.getExitPoint(null);
		assertThat("Exit point not restored", exit, notNullValue());
		entryEP = requireEditPart(entry.toUML());
		exitEP = requireEditPart(exit.toUML());
		assertThat(entryEP, notInherited());
		assertThat(exitEP, notInherited());

		assertThat("Restored entry point has no incoming transitions",
				(List<?>) ((Node) entryEP.getNotationView()).getTargetEdges(), not(isEmpty()));
		assertThat("Restored exit point has no outgoing transitions",
				(List<?>) ((Node) exitEP.getNotationView()).getSourceEdges(), not(isEmpty()));
	}

	//
	// Test framework
	//

	@Parameters(name = "{1}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ "Capsule1", "root" },
				{ "Capsule2", "redefinition" },
		});
	}

	@Before
	public void openDiagram() {
		editor.openDiagram(String.format("%s::StateMachine", capsuleName));
	}
}
