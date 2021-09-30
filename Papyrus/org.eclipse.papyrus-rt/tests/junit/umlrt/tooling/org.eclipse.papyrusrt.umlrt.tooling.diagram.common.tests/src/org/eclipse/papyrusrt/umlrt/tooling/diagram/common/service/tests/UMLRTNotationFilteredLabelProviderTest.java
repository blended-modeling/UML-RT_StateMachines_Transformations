/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.service.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Named;

import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationModel;
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.service.UMLRTNotationFilteredLabelProvider;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@link UMLRTNotationFilteredLabelProvider} class.
 */
@PluginResource("/resource/diagramlabels/model.di")
public class UMLRTNotationFilteredLabelProviderTest {
	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public final ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	@Rule
	public final AnnotationRule<Integer> diagramIndex = AnnotationRule.create(DiagramIndex.class, 0);

	@Rule
	public final FixtureElementRule elementRule = new FixtureElementRule();

	@Named("test::Capsule1")
	private Class capsule1;

	@Named("test::Capsule2")
	private Class capsule2;

	@Named("test::Capsule2::StateMachine")
	private StateMachine stateMachine;

	@Named("test::Capsule2::StateMachine::Region::Ready")
	private State state;

	private Diagram diagram;

	private ILabelProvider labelProvider;

	/**
	 * Initializes me.
	 */
	public UMLRTNotationFilteredLabelProviderTest() {
		super();
	}

	/**
	 * Tests that the label provider notifies when the diagram name computed from
	 * its context changes.
	 */
	@Test
	public void labelProviderNotifiesOnComputedName() {
		boolean[] notified = { false };

		assumeThat(labelProvider.getText(diagram), is("Capsule1"));

		labelProvider.addListener(__ -> notified[0] = true);
		modelSet.execute(SetCommand.create(modelSet.getEditingDomain(), capsule1, UMLPackage.Literals.NAMED_ELEMENT__NAME, "Foo"));

		assertThat("Label provider did not notify", notified[0], is(true));
	}

	/**
	 * Tests that the label provider notifies when the diagram name specified
	 * for it (custom name) by the user changes.
	 */
	@Test
	public void labelProviderNotifiesOnSpecifiedName() {
		// Configure a specified name
		modelSet.execute(SetCommand.create(modelSet.getEditingDomain(), diagram, NotationPackage.Literals.DIAGRAM__NAME, "MyDiagram"));

		boolean[] notified = { false };

		assumeThat(labelProvider.getText(diagram), is("MyDiagram"));

		labelProvider.addListener(__ -> notified[0] = true);
		modelSet.execute(SetCommand.create(modelSet.getEditingDomain(), diagram, NotationPackage.Literals.DIAGRAM__NAME, "Foo"));

		assertThat("Label provider did not notify", notified[0], is(true));
	}

	/**
	 * Tests that the label provider notifies when the diagram name computed from
	 * its context changes when that context is a behavior whose context classifier's
	 * name is changed.
	 */
	@Test
	@DiagramIndex(2)
	public void labelProviderNotifiesOnComputedName_behaviorContext() {
		boolean[] notified = { false };

		assumeThat(labelProvider.getText(diagram), is("Capsule2::StateMachine"));

		labelProvider.addListener(__ -> notified[0] = true);
		modelSet.execute(SetCommand.create(modelSet.getEditingDomain(), capsule2, UMLPackage.Literals.NAMED_ELEMENT__NAME, "Foo"));

		assertThat("Label provider did not notify", notified[0], is(true));
	}

	/**
	 * Tests that the label provider computes the correct name for a nested
	 * state machine diagram (a diagram for a composite state).
	 */
	@Test
	@DiagramIndex(3)
	public void labelProviderNestedStateMachineDiagram() {
		assertThat(labelProvider.getText(diagram), is("Capsule2..Ready"));
	}

	/**
	 * Tests that the label provider notifies when the diagram name computed from
	 * its context changes when that context is a composite state whose context
	 * classifier's name is changed.
	 */
	@Test
	@DiagramIndex(3)
	public void labelProviderNotifiesOnComputedName_nestedStateMachineDiagram() {
		boolean[] notified = { false };

		assumeThat(labelProvider.getText(diagram), is("Capsule2..Ready"));

		labelProvider.addListener(__ -> notified[0] = true);
		modelSet.execute(SetCommand.create(modelSet.getEditingDomain(), state, UMLPackage.Literals.NAMED_ELEMENT__NAME, "Unready"));

		assertThat("Label provider did not notify", notified[0], is(true));

		assertThat(labelProvider.getText(diagram), is("Capsule2..Unready"));

		// And on changing the capsule name
		notified[0] = false; // reset

		modelSet.execute(SetCommand.create(modelSet.getEditingDomain(), capsule2, UMLPackage.Literals.NAMED_ELEMENT__NAME, "Ethelred"));

		assertThat("Label provider did not notify", notified[0], is(true));

		assertThat(labelProvider.getText(diagram), is("Ethelred..Unready"));
	}

	//
	// Test framework
	//

	/**
	 * Annotation indicating which diagram to access for a test.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface DiagramIndex {
		int value();
	}

	@Before
	public void setup() {
		NotationModel notation = (NotationModel) modelSet.getResourceSet().getModel(NotationModel.MODEL_ID);
		diagram = (Diagram) notation.getResource().getContents().get(diagramIndex.get().intValue());
		labelProvider = houseKeeper.cleanUpLater(new UMLRTNotationFilteredLabelProvider(), ILabelProvider::dispose);
	}
}
