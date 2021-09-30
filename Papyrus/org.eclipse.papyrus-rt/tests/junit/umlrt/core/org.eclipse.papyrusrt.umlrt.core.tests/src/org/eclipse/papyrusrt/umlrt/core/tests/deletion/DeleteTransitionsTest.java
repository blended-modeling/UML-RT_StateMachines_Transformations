/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

package org.eclipse.papyrusrt.umlrt.core.tests.deletion;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.core.tests.AbstractPapyrusRTCoreTest;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;
import org.junit.Test;

/**
 * Test class for deletion of transitions. It mainly checks the deletion of entry/exit ends if the they become useless.
 */
public class DeleteTransitionsTest extends AbstractPapyrusRTCoreTest {

	/**
	 * Constructor.
	 */
	public DeleteTransitionsTest() {
		super();
	}

	@Test
	public void deleteTransitionWithPseudostates_bothEmpty() throws Exception {
		deleteTransition(t1);
	}

	@Test
	public void deleteTransitionWithPseudostates_targetEmpty() throws Exception {
		deleteTransition(t2);
	}


	@Test
	public void deleteTransitionWithPseudostates_sourceEmpty() throws Exception {
		deleteTransition(t3);
	}


	@Test
	public void deleteTransitionWithPseudostates_noneEmpty() throws Exception {
		deleteTransition(t4);
	}


	@Test
	public void deleteTransitionWithStates() throws Exception {
		deleteTransition(t5);
	}

	/**
	 * @param t1
	 * @throws Exception
	 */
	protected void deleteTransition(Transition deletedElement) throws Exception {
		Command commandDelete = getDeleteChildCommand(deletedElement, false);
		if (commandDelete != null && commandDelete.canExecute()) {
			DeleteTransitionValidationRules validationRule = new DeleteTransitionValidationRules(deletedElement);
			getCommandStack().execute(commandDelete);

			// the element should not be in the model
			validationRule.validatePostEdition();

			// undo the delete command
			getCommandStack().undo();
			// the element should be in the model
			validationRule.validatePostUndo();

			// redo the delete command
			getCommandStack().redo();
			// the element should not be in the model
			validationRule.validatePostEdition();

			// undo the delete command
			getCommandStack().undo();
			// the element should be in the model
			validationRule.validatePostUndo();

		} else {
			fail("Delete Command should be executable");
		}
	}

	/**
	 * 
	 */
	private static boolean shouldDelete(Element end, Transition transition) {
		if (!(end instanceof Pseudostate)) {
			return false;
		}

		PseudostateKind kind = ((Pseudostate) end).getKind();
		if (!(kind.equals(PseudostateKind.ENTRY_POINT_LITERAL) || kind.equals(PseudostateKind.EXIT_POINT_LITERAL))) {
			return false;
		}
		// check there are no other transition outgoing or incoming except transition to delete
		if (((Pseudostate) end).getOutgoings().stream().filter(t -> !t.equals(transition)).findAny().isPresent()) {
			return false;
		}
		if (((Pseudostate) end).getIncomings().stream().filter(t -> !t.equals(transition)).findAny().isPresent()) {
			return false;
		}
		return true;
	}

	//
	// inner types
	//

	protected static class DeleteTransitionValidationRules {

		private Transition transition;
		private Vertex source;
		private List<EObject> sourceStereotypes;
		private boolean shouldDeleteSource;
		private Vertex target;
		private List<EObject> targetStereotypes;
		private boolean shouldDeleteTarget;
		private ArrayList<Transition> sourceOutgoings;
		private ArrayList<Transition> sourceIncomings;
		private ArrayList<Transition> targetOutgoings;
		private ArrayList<Transition> targetIncomings;
		private Region region;
		private Element originalSourceOwner;
		private Element originalTargetOwner;

		/**
		 * Constructor.
		 *
		 * @param deletedElement
		 * @throws Exception
		 */
		public DeleteTransitionValidationRules(Transition deletedElement) throws Exception {
			this.transition = deletedElement;
			this.region = transition.getContainer();
			this.source = transition.getSource();
			this.originalSourceOwner = getOwner(source);
			this.sourceOutgoings = new ArrayList<>(source.getOutgoings());
			this.sourceIncomings = new ArrayList<>(source.getIncomings());
			this.sourceStereotypes = new ArrayList<>(source.getStereotypeApplications());
			this.shouldDeleteSource = shouldDelete(source, deletedElement);

			this.target = transition.getTarget();
			this.originalTargetOwner = getOwner(target);
			this.targetOutgoings = new ArrayList<>(target.getOutgoings());
			this.targetIncomings = new ArrayList<>(target.getIncomings());
			this.targetStereotypes = new ArrayList<>(target.getStereotypeApplications());
			this.shouldDeleteTarget = shouldDelete(target, deletedElement);

		}

		public void validatePostEdition() throws Exception {
			assertThat(transition.getContainer(), nullValue());
			assertThat(transition.getSource(), nullValue());
			assertThat(transition.getTarget(), nullValue());

			if (shouldDeleteSource) {
				assertThat(source.getOwner(), nullValue());
				assertThat(source.getIncomings(), isEmpty());
				assertThat(source.getOutgoings(), isEmpty());
				assertThat(source.getStereotypeApplications(), isEmpty());
				EObject stApp = sourceStereotypes.stream().filter(st -> st.eResource() != null).findAny().orElse(null);
				assertThat(stApp, nullValue());

			} else {
				// ensure the target is unchanged, expect for the deleted transition
				List<Transition> expectedOutgoings = new ArrayList<>(sourceOutgoings);
				expectedOutgoings.remove(transition);
				assertThat(source.getOutgoings(), equalTo(expectedOutgoings));
				List<Transition> expectedIncomings = new ArrayList<>(sourceIncomings);
				expectedIncomings.remove(transition);
				assertThat(source.getIncomings(), equalTo(expectedIncomings));
			}

			if (shouldDeleteTarget) {
				assertThat(target.getOwner(), nullValue());
				assertThat(target.getIncomings(), isEmpty());
				assertThat(target.getOutgoings(), isEmpty());
				assertThat(target.getStereotypeApplications(), isEmpty());
				EObject stApp = targetStereotypes.stream().filter(st -> st.eResource() != null).findAny().orElse(null);
				assertThat(stApp, nullValue());
			} else {
				// ensure the target is unchanged, expect for the deleted transition
				List<Transition> expectedOutgoings = new ArrayList<>(targetOutgoings);
				expectedOutgoings.remove(transition);
				assertThat(target.getOutgoings(), equalTo(expectedOutgoings));
				List<Transition> expectedIncomings = new ArrayList<>(targetIncomings);
				expectedIncomings.remove(transition);
				assertThat(target.getIncomings(), equalTo(expectedIncomings));
			}
		}

		public void validatePostUndo() throws Exception {
			assertThat(transition.getContainer(), equalTo(region));
			assertThat(transition.getSource(), equalTo(source));
			assertThat(transition.getTarget(), equalTo(target));

			// check initial values
			assertThat(source.getOwner(), equalTo(originalSourceOwner));
			assertThat(source.getIncomings(), equalTo(sourceIncomings));
			assertThat(source.getOutgoings(), equalTo(sourceOutgoings));

			assertThat(target.getOwner(), equalTo(originalTargetOwner));
			assertThat(target.getIncomings(), equalTo(targetIncomings));
			assertThat(target.getOutgoings(), equalTo(targetOutgoings));
		}
	}

	/**
	 * @param source
	 * @return
	 * @throws Exception
	 */
	protected static Element getOwner(Vertex source) throws Exception {
		if (source instanceof State) {
			return ((State) source).getContainer();
		}
		if (source instanceof Pseudostate) {
			return ((Pseudostate) source).getState() != null ? ((Pseudostate) source).getState() : ((Pseudostate) source).getContainer();
		}
		throw new Exception("unexpected");
	}
}
