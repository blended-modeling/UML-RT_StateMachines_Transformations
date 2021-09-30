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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.inheritance.tests;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.junit.utils.DeletionUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

@PluginResource("resource/statemachine/inheritance/delete_subcapsule.di")
@DiagramNaming(StateMachineDiagramNameStrategy.class)
public class StateMachineDeletionTest extends AbstractCanonicalTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	@NeedsUIEvents
	@Test
	@FailingTest("Issue with cairo")
	@ActiveDiagram("Capsule2::StateMachine")
	public void deleteStateFromParentDiagram() {
		// Start with the redefining state machine diagram open to initialize it

		Class capsule1 = (Class) editor.getModel().getOwnedType("Capsule1");
		StateMachine sm = (StateMachine) capsule1.getClassifierBehavior();

		State state1 = (State) sm.getRegions().get(0).getSubvertex("State1");
		Transition initial = state1.getIncoming("Initial");

		// Switch to the parent diagram and delete the state
		editor.openDiagram("Capsule1::StateMachine");
		editor.execute(DeletionUtils.requireDestroyCommand(state1));

		// This much is assumed to be okay (not inheritance)
		assumeThat(getConnectionEditPart(initial), nullValue());
		assumeThat(getEditPart(state1), nullValue());

		// Back to the redefining state machine diagram for verification
		editor.openDiagram("Capsule2::StateMachine");

		assertNoView(initial);
		assertNoView(state1);

		IGraphicalEditPart stateShape = StreamSupport.stream(Spliterators.spliteratorUnknownSize(allContents(getDiagramEditPart(), false), Spliterator.NONNULL | Spliterator.ORDERED | Spliterator.DISTINCT), false)
				.filter(gep -> gep.getNotationView() != null && StateEditPart.VISUAL_ID.equals(gep.getNotationView().getType()))
				.findAny().orElse(null);
		assertThat("Found remnant of the deleted state", stateShape, nullValue());

		editor.undo();

		requireEditPart(state1);
		requireConnectionEditPart(initial);
	}

}
