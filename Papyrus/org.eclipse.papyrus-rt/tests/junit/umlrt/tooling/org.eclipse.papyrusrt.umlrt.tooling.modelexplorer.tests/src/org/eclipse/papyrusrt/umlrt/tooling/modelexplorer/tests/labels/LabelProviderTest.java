/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 467545
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.tests.labels;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.provider.UMLRTFilteredLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Testing label providers for Papryus-RT
 */
@PluginResource("resource/allelements/model.di")
public class LabelProviderTest extends AbstractPapyrusTest {

	@ClassRule
	public static final TestRule elementTypesRule = new ElementTypesRule();

	@ClassRule
	public static final HouseKeeper.Static houseKeeper = new HouseKeeper.Static();

	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	@ClassRule
	public static final PapyrusEditorFixture editor = new PapyrusRTEditorFixture();

	private static ILabelProvider labelProvider;

	private static Class capsule1;

	private static StateMachine stateMachine;

	private static Region region;

	private static Vertex deepHistory;

	private static Vertex state1;

	private static State state2;

	private static Transition transitionWithName;

	private static Transition transitionWithOneTrigger;

	private static Transition transitionWithSeveralTriggers;

	private static Transition transitionWithAnyTrigger;

	private static Image notFound;

	private static Vertex choice;

	private static Vertex entry;

	private static Vertex exit;

	private static Vertex junction;

	private static Vertex initial;

	private static Comment comment;

	private static Region state2_region;

	@BeforeClass
	public static void setup() {
		labelProvider = houseKeeper.cleanUpLater(new UMLRTFilteredLabelProvider(), ILabelProvider::dispose);
		capsule1 = (Class) editor.getModel().getOwnedType("Capsule1");
		stateMachine = (StateMachine) capsule1.getClassifierBehavior();
		region = stateMachine.getRegions().get(0);
		initial = region.getSubvertices().stream().filter(p -> (p instanceof Pseudostate && PseudostateKind.INITIAL_LITERAL == ((Pseudostate) p).getKind())).findFirst().get();
		state1 = region.getSubvertex("State1");
		state2 = (State) region.getSubvertex("State2");
		state2_region = state2.getRegions().get(0);
		transitionWithName = state2_region.getTransitions().get(0);
		transitionWithOneTrigger = state2_region.getTransitions().get(1);
		transitionWithSeveralTriggers = state2_region.getTransitions().get(2);
		transitionWithAnyTrigger = state2_region.getTransitions().get(3);
		choice = region.getSubvertex("Choice1");
		deepHistory = region.getSubvertex("DeepHistory1");
		entry = region.getSubvertex("EntryPoint1");
		exit = region.getSubvertex("ExitPoint1");
		junction = region.getSubvertex("Junction1");
		comment = region.getOwnedComments().get(0);
	}

	@Test
	public void testCapsuleIcon() throws Exception {
		testIcon(capsule1);
	}

	@Test
	public void testStateMachineIcon() throws Exception {
		testIcon(stateMachine);
	}

	@Test
	public void testRegionIcon() throws Exception {
		testIcon(region);
	}

	@Test
	public void testInitialIcon() throws Exception {
		testIcon(initial);
	}

	@Test
	public void testStateIcon() throws Exception {
		testIcon(state1);
	}

	@Test
	public void testDeepHistoryIcon() throws Exception {
		testIcon(deepHistory);
	}

	@Test
	public void testEntryIcon() throws Exception {
		testIcon(entry);
	}

	@Test
	public void testExitIcon() throws Exception {
		testIcon(exit);
	}

	@Test
	public void testChoiceIcon() throws Exception {
		testIcon(choice);
	}

	@Test
	public void testJunctionIcon() throws Exception {
		testIcon(junction);
	}

	@Test
	public void testCommentIcon() throws Exception {
		testIcon(comment);
	}

	@Test
	public void testTransitionWithName() throws Exception {
		testLabel(transitionWithName, "withName");
	}

	@Test
	public void testTransitionWithOneTrigger() throws Exception {
		testLabel(transitionWithOneTrigger, "in1");
	}

	@Test
	public void testTransitionWithSeveralTriggers() throws Exception {
		testLabel(transitionWithSeveralTriggers, "in1+");
	}

	@Test
	public void testTransitionWithAnyTrigger() throws Exception {
		testLabel(transitionWithAnyTrigger, "*");
	}

	@Test
	public void testSimpleCallEventName() throws Exception {
		testLabel(transitionWithOneTrigger.getTriggers().get(0).getEvent(), "in in1");
	}

	@Test
	public void testAnyReceiveEventName() throws Exception {
		testLabel(transitionWithAnyTrigger.getTriggers().get(0).getEvent(), "*");
	}

	@Test
	public void testSimpleCallEventTriggerName() throws Exception {
		testLabel(transitionWithOneTrigger.getTriggers().get(0), "in1");
	}

	@Test
	public void testAnyReceiveEventTriggerName() throws Exception {
		testLabel(transitionWithAnyTrigger.getTriggers().get(0), "*");
	}

	private void testIcon(Object element) throws Exception {
		Image image = labelProvider.getImage(element);
		assertThat(image, notNullValue());
		ImageDescriptor descriptor = ImageDescriptor.createFromImage(image);
		assertThat(descriptor, not(equalTo(ImageDescriptor.getMissingImageDescriptor())));
		// finally asset for the size (-1 indicates an issue also)
		assertThat(image.getBounds().height, greaterThan(10)); // should be 16, red missing icon square is 6
	}

	private void testLabel(Object element, String expected) {
		String text = labelProvider.getText(element);
		assertThat(text, equalTo(expected));

	}

}
