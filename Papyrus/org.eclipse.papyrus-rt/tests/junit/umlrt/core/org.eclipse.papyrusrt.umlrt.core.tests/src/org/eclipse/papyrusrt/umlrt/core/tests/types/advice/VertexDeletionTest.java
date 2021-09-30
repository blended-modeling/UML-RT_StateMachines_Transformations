/*****************************************************************************
 * Copyright (c) 2017 EclipseSource Services GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Martin Fleck (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.types.advice;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import javax.inject.Named;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrus.uml.service.types.helper.advice.VertexEditHelperAdvice;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.utils.DeletionUtils;
import org.eclipse.uml2.uml.FinalState;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Tests the {@link VertexEditHelperAdvice} that deletes all incoming and outgoing transitions when a vertex is deleted.
 * The used test model consists of a statemachine with four states and four transitions:
 * 
 * <pre>
 *              +--------------+
 *              |              v
 * [Initial]-->[S1]-->[S2]-->[Final]
 * </pre>
 * 
 * @author Martin Fleck <mfleck@eclipsesource.com>
 *
 */
@PluginResource("/resource/StateMachine.di")
public class VertexDeletionTest extends AbstractPapyrusTest {

	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();

	/** HouseKeeper to clean up resources after a test has finished. */
	@Rule
	public HouseKeeper houseKeeper = new HouseKeeper();

	/** ModelSetFixture with initialized service registry. */
	@Rule
	public ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	/** Fixture to initialize the {@link Named} model elements. */
	@Rule
	public FixtureElementRule fixtureRule = new FixtureElementRule();

	/** Region containing the states and transitions. */
	@Named("StateMachineModel::StateMachine::Region")
	private Region region;

	/** Initial State. */
	@Named("StateMachineModel::StateMachine::Region::InitialState")
	private Pseudostate initialState;

	/** State 1. */
	@Named("StateMachineModel::StateMachine::Region::S1")
	private State s1;

	/** State 2. */
	@Named("StateMachineModel::StateMachine::Region::S2")
	private State s2;

	/** Final State. */
	@Named("StateMachineModel::StateMachine::Region::FinalState")
	private FinalState finalState;

	/** Transition from Initial State to State 1. */
	@Named("StateMachineModel::StateMachine::Region::InitialToS1")
	private Transition initialToS1;

	/** Transition from State 1 to State 2. */
	@Named("StateMachineModel::StateMachine::Region::S1ToS2")
	private Transition s1ToS2;

	/** Transition from State 1 to Final State. */
	@Named("StateMachineModel::StateMachine::Region::S1ToFinal")
	private Transition s1ToFinal;

	/** Transition from State 2 to Final State. */
	@Named("StateMachineModel::StateMachine::Region::S2ToFinal")
	private Transition s2ToFinal;

	/**
	 * Default Constructor.
	 */
	public VertexDeletionTest() {
	}

	/**
	 * Asserts the structure of the model fixture with four states and four transitions.
	 */
	@Before
	public void assertFixture() {
		assertThat("Region does not exist!", region, is(notNullValue()));

		assertThat("Initial State does not exist!", initialState, is(notNullValue()));
		assertThat("Initial State not in region!", region.getSubvertices(), hasItem(initialState));

		assertThat("State 1 does not exist!", s1, is(notNullValue()));
		assertThat("State 1 not in region!", region.getSubvertices(), hasItem(s1));

		assertThat("State 2 does not exist!", s2, is(notNullValue()));
		assertThat("State 2 not in region!", region.getSubvertices(), hasItem(s2));

		assertThat("Final State does not exist!", finalState, is(notNullValue()));
		assertThat("Final State not in region!", region.getSubvertices(), hasItem(finalState));

		assertThat("Transition from Initial State to State 1 does not exist!", initialToS1, is(notNullValue()));
		assertThat("Transition from Initial State to State 1 not in region!", region.getTransitions(), hasItem(initialToS1));
		assertThat("Transition from Initial State to State 1 not connected correctly (source).", initialToS1.getSource(), is(initialState));
		assertThat("Transition from Initial State to State 1 not connected correctly (target).", initialToS1.getTarget(), is(s1));

		assertThat("Transition from State 1 to State 2 does not exist!", s1ToS2, is(notNullValue()));
		assertThat("Transition from State 1 to State 2 not in region!", region.getTransitions(), hasItem(s1ToS2));
		assertThat("Transition from State 1 to State 2 not connected correctly (source).", s1ToS2.getSource(), is(s1));
		assertThat("Transition from State 1 to State 2 not connected correctly (target).", s1ToS2.getTarget(), is(s2));

		assertThat("Transition from State 1 to Final State does not exist!", s1ToFinal, is(notNullValue()));
		assertThat("Transition from State 1 to Final State not in region!", region.getTransitions(), hasItem(s1ToFinal));
		assertThat("Transition from State 1 to Final State not connected correctly (source).", s1ToFinal.getSource(), is(s1));
		assertThat("Transition from State 1 to Final State not connected correctly (target).", s1ToFinal.getTarget(), is(finalState));

		assertThat("Transition from State 2 to Final State does not exist!", s2ToFinal, is(notNullValue()));
		assertThat("Transition from State 2 to Final State not in region!", region.getTransitions(), hasItem(s2ToFinal));
		assertThat("Transition from State 2 to Final State not connected correctly (source).", s2ToFinal.getSource(), is(s2));
		assertThat("Transition from State 2 to Final State not connected correctly (target).", s2ToFinal.getTarget(), is(finalState));
	}

	/**
	 * Verify that all outgoing transitions from the initial state are deleted.
	 */
	@Test
	public void deleteInitialState() throws Exception {
		testDeleteVertex(initialState, initialToS1);
	}

	/**
	 * Verify that all incoming and outgoing transitions from state 1 are deleted.
	 */
	@Test
	public void deleteS1() throws Exception {
		testDeleteVertex(s1, initialToS1, s1ToS2, s1ToFinal);
	}

	/**
	 * Verify that all incoming and outgoing transitions from state 2 are deleted.
	 */
	@Test
	public void deleteS2() throws Exception {
		testDeleteVertex(s2, s1ToS2, s2ToFinal);
	}

	/**
	 * Verify that all incoming transitions from the final state are deleted.
	 */
	@Test
	public void deleteFinalState() throws Exception {
		testDeleteVertex(finalState, s1ToFinal, s2ToFinal);
	}

	/**
	 * Deletes the given vertex and asserts that the given transitions are automatically deleted as well.
	 * 
	 * @param vertexToDelete
	 *            vertex element to delete.
	 * @param deletedTransitions
	 *            transitions that should be deleted together with the vertex.
	 * @throws ServiceException
	 */
	protected void testDeleteVertex(Vertex vertexToDelete, Transition... deletedTransitions) throws ServiceException {
		deleteElement(vertexToDelete);
		assertVertexDeleted(vertexToDelete);
		assertTransitionsDeleted(Lists.newArrayList(deletedTransitions));
	}

	/**
	 * Asserts that all given transitions are deleted from the model and that all other transitions are unchanged.
	 * 
	 * @param deletedTransitions
	 *            transitions that should have been deleted from the model.
	 */
	protected void assertTransitionsDeleted(List<Transition> deletedTransitions) {
		List<Transition> allTransitions = Lists.newArrayList(initialToS1, s1ToS2, s1ToFinal, s2ToFinal);
		for (Transition transition : allTransitions) {
			if (deletedTransitions.contains(transition)) {
				assertTransitionDeleted(transition);
			} else {
				assertTransitionNotDeleted(transition);
			}
		}
	}

	/**
	 * Asserts that the given vertex was deleted from the model.
	 * 
	 * @param vertex
	 *            vertex that should have been deleted.
	 */
	protected void assertVertexDeleted(Vertex vertex) {
		assertThat("Deleted vertex still has incoming transitions.", vertex.getIncomings().isEmpty());
		assertThat("Deleted vertex still has outgoing transitions.", vertex.getOutgoings().isEmpty());
		assertThat("Deleted vertex still in its container.", vertex.eContainer(), is(nullValue()));
		assertThat("Deleted vertex still in region.", region.getSubvertices(), not(hasItem(vertex)));
	}

	/**
	 * Asserts that the given transition was deleted from the model.
	 * 
	 * @param transition
	 *            transition that should have been deleted.
	 */
	protected void assertTransitionDeleted(Transition transition) {
		assertThat("Deleted transition still connected to its source.", transition.getSource(), is(nullValue()));
		assertThat("Deleted transition still connected to its target.", transition.getTarget(), is(nullValue()));
		assertThat("Deleted transition still in its container.", transition.eContainer(), is(nullValue()));
		assertThat("Deleted transition still in region.", region.getTransitions(), not(hasItem(transition)));
	}

	/**
	 * Asserts that the given transition was NOT deleted from the model.
	 * 
	 * @param transition
	 *            transition that should be left unchanged.
	 */
	protected void assertTransitionNotDeleted(Transition transition) {
		assertThat("Not deleted transition lost its source.", transition.getSource(), is(notNullValue()));
		assertThat("Not deleted transition lost its target.", transition.getTarget(), is(notNullValue()));
		assertThat("Not deleted transition lost its container.", transition.eContainer(), is(region));
		assertThat("Not deleted transition not in region.", region.getTransitions(), hasItem(transition));
	}

	/**
	 * Deletes the given element using {@link DeletionUtils}.
	 * 
	 * @param elementToDelete
	 *            element that should be deleted.
	 * @throws ServiceException
	 */
	protected void deleteElement(EObject elementToDelete) throws ServiceException {
		Command deleteCommand = DeletionUtils.getDeleteCommand(elementToDelete, true, modelSet.getEditingDomain());
		if (deleteCommand != null && deleteCommand.canExecute()) {
			modelSet.getEditingDomain().getCommandStack().execute(deleteCommand);
		} else {
			fail("Delete Command cannot be executed! Command: " + deleteCommand + ".");
		}
	}
}
