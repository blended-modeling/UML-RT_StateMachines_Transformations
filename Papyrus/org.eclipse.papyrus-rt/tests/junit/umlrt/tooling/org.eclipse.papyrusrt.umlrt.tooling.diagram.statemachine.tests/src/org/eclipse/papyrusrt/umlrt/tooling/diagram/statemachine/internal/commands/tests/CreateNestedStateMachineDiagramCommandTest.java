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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.commands.CreateNestedStateMachineDiagramCommand;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPartTN;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

@PluginResource("resource/statemachine/inheritance/RedefinedCompositeStateTest.di")
@DiagramNaming(StateMachineDiagramNameStrategy.class)
@ActiveDiagram("Capsule2::StateMachine")
public class CreateNestedStateMachineDiagramCommandTest extends AbstractCanonicalTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	public CreateNestedStateMachineDiagramCommandTest() {
		super();
	}

	/**
	 * Regression test for undoing creation of a nested state machine diagram for a
	 * redefined composite state.
	 * 
	 * @see <a href="http://eclip.se/518243">bug 518243</a>
	 */
	@NeedsUIEvents
	@Test
	public void undoCreateNestedDiagramForRedefinedCompositeState() {
		UMLRTCapsule capsule = UMLRTPackage.getInstance(editor.getModel()).getCapsule("Capsule2");
		UMLRTStateMachine sm = capsule.getStateMachine();

		UMLRTState state3 = (UMLRTState) sm.getVertex("State3");
		assumeThat("State3 not found", state3, notNullValue());

		IGraphicalEditPart stateEditPart = requireEditPart(state3.toUML());
		ICommand command = new CreateNestedStateMachineDiagramCommand(editor.getEditingDomain(), state3.toUML(), stateEditPart, true);

		markForUndo();

		editor.execute(command);

		// Make sure the editor fixture knows what's active.
		// This would fail if the editor wasn't already open
		// (can't activate an editor page that doesn't exist)
		editor.activateDiagram("Capsule2..State3");

		// Look for connection point
		UMLRTConnectionPoint entry = state3.getEntryPoint(null);
		assertThat("Entry point not found", entry, notNullValue());
		IGraphicalEditPart entryEP = requireEditPart(entry.toUML());

		assertThat("Entry point shape not on the diagram frame", entryEP.getParent(),
				instanceOf(RTStateEditPartTN.class));

		undoToMark();

		assertThat("Diagram not uncreated", editor.getActiveDiagram().resolveSemanticElement(), nullValue());
	}

}
