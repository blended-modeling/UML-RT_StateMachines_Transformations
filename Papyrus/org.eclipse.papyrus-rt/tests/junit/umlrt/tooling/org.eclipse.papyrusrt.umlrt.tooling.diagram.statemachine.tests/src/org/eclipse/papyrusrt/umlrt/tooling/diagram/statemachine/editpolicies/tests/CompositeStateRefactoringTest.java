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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.editpolicies.tests;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.DialogPreferenceRule;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * JUnit regression tests for the simple/composite state refactorings in the state machine
 * diagram editor.
 */
@DiagramNaming(StateMachineDiagramNameStrategy.class)
@PluginResource("resource/statemachine/model.di")
@ActiveDiagram("Capsule1::StateMachine")
public class CompositeStateRefactoringTest extends AbstractCanonicalTest {

	private static final String CONFIRMATION_DIALOG_ID = Activator.PLUGIN_ID + ".confirmCreateNestedSMD"; //$NON-NLS-1$

	@ClassRule
	public static final TestRule dialog = DialogPreferenceRule.always(CONFIRMATION_DIALOG_ID);

	public CompositeStateRefactoringTest() {
		super();
	}

	@Test
	public void undoRefactorAsCompositeStateAfterClosingNestedDiagram() {
		State state1 = (State) ((StateMachine) ((BehavioredClassifier) editor.getModel().getOwnedType("Capsule1"))
				.getClassifierBehavior())
						.getRegion("Region")
						.getSubvertex("State1");

		IGraphicalEditPart state1EP = requireEditPart(state1);

		SelectionRequest request = new SelectionRequest();
		request.setLocation(new Point());
		request.setType(RequestConstants.REQ_OPEN);
		Command command = state1EP.getCommand(request);
		assertThat("Cannot execute open command", command, isExecutable());

		editor.execute(command);

		// Now, close the nested diagram
		editor.activateDiagram("Capsule1..State1");
		editor.closeDiagram();
		editor.activateDiagram("Capsule1::StateMachine");

		// And undo
		editor.undo();

		assertThat("State1 is still composite", state1.isComposite(), is(false));
	}
}
