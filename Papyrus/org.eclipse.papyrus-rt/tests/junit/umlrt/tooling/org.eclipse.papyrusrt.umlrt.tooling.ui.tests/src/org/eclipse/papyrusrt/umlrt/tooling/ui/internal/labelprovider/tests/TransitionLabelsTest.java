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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.labelprovider.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule.CleanUp;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.tooling.ui.labelprovider.UMLRTFilteredLabelProvider;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Tests for presentation of transition labels, with a particular emphasis on
 * transition inheritance with triggers.
 */
@PluginResource("resource/labels/transition_labels.di")
public class TransitionLabelsTest {

	@Rule
	public final HouseKeeper housekeeper = new HouseKeeper();

	@Rule
	public final UMLRTModelSetFixture model = new UMLRTModelSetFixture();

	@Rule
	public final TransitionFixture transition = new TransitionFixture();

	@CleanUp
	private UMLRTFilteredLabelProvider labels = new UMLRTFilteredLabelProvider();

	public TransitionLabelsTest() {
		super();
	}

	/**
	 * Control test verifying the label of the root transition inherited
	 * by other tests.
	 */
	@TransitionID(capsule = "Capsule1")
	@Test
	public void rootTransition_control() {
		assertThat(getTransition(), labelled("greet"));
	}

	/**
	 * Verify the label of a transition inherited as is.
	 */
	@TransitionID(capsule = "Capsule3")
	@Test
	public void inheritedTransition() {
		assertThat(getTransition(), labelled("greet"));
	}

	/**
	 * Verify the label of a transition that redefines the inherited
	 * transition to add a trigger.
	 */
	@TransitionID(capsule = "Capsule2")
	@Test
	public void transitionAddsTrigger() {
		assertThat(getTransition(), labelled("greet+"));
	}

	/**
	 * Verify the label of a transition that redefines the inherited
	 * transition to specify a trigger where the parent definition
	 * has none.
	 */
	@TransitionID(capsule = "Capsule3", target = "State3")
	@Test
	public void transitionSpecifiesTrigger() {
		assertThat(getTransition(), labelled("accept"));
	}

	/**
	 * Verify the label of a transition that redefines the inherited
	 * transition to exclude the trigger.
	 */
	@TransitionID(capsule = "Capsule4")
	@Test
	public void transitionExcludesTrigger() {
		assertThat(getTransition(), labelled(""));
	}

	/**
	 * Verify the label of a transition that redefines the inherited
	 * transition to replaces the inherited trigger with another.
	 */
	@TransitionID(capsule = "Capsule5")
	@Test
	public void transitionReplacesTrigger() {
		assertThat(getTransition(), labelled("accept"));
	}

	/**
	 * Verify the label of a transition that redefines the inherited
	 * transition to set an explicit transition name.
	 */
	@TransitionID(capsule = "Capsule6")
	@Test
	public void transitionRedefinesName() {
		assertThat(getTransition(), labelled("override"));
	}

	/**
	 * Verify the label of a transition that adds a trigger to
	 * a list of several inherited from the superclass.
	 */
	@TransitionID(capsule = "Capsule6", target = "State4")
	@Test
	public void transitionInheritsSeveralTriggers() {
		assertThat(getTransition(), labelled("query+"));
	}

	/**
	 * Verify the label of a transition that adds a trigger to
	 * a list of several inherited from the superclass when in
	 * the superclass the user reorders them to put another
	 * trigger first.
	 */
	@TransitionID(capsule = "Capsule6", target = "State4")
	@Test
	public void transitionInheritsSeveralTriggersReordered() {
		Transition root = UMLRTExtensionUtil.getRootDefinition(getTransition());
		model.execute(MoveCommand.create(model.getEditingDomain(), root, null,
				root.getTriggers().get(1), 0));
		assertThat(getTransition(), labelled("request+"));
	}

	//
	// Test framework
	//

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE, ElementType.METHOD })
	public static @interface TransitionID {
		String capsule();

		String source() default "State1";

		String target() default "State2";
	}

	Transition getTransition() {
		return this.transition.getTransition();
	}

	String getLabel(Object object) {
		return labels.getText(object);
	}

	Matcher<Object> labelled(String expectedLabel) {
		return new FeatureMatcher<Object, String>(is(expectedLabel), "label", "label") {
			@Override
			protected String featureValueOf(Object actual) {
				return getLabel(actual);
			}
		};
	}

	class TransitionFixture extends TestWatcher {
		private TransitionID id;

		@Override
		protected void starting(Description description) {
			id = JUnitUtils.getAnnotation(description, TransitionID.class);
		}

		Transition getTransition() {
			return getTransition(getStateMachine(id.capsule()), id.source(), id.target());
		}

		StateMachine getStateMachine(String capsuleName) {
			org.eclipse.uml2.uml.Class capsule = (org.eclipse.uml2.uml.Class) model.getModel().getOwnedType(capsuleName);
			return (StateMachine) capsule.getClassifierBehavior();
		}

		Region getRegion(StateMachine machine) {
			return UMLRTExtensionUtil.<Region> getUMLRTContents(machine, UMLPackage.Literals.STATE_MACHINE__REGION)
					.get(0);
		}

		Transition getTransition(StateMachine machine, String sourceName, String targetName) {
			return getRegion(machine).getSubvertex(sourceName).getOutgoings().stream()
					.filter(t -> targetName.equals(t.getTarget().getName()))
					.findAny().orElseThrow(() -> new AssertionError(String.format("No transition %s --> %s", sourceName, targetName)));
		}
	}
}
