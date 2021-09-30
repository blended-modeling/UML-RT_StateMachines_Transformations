/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 510263, 512362
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.tests.edition;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.papyrus.infra.core.internal.clipboard.CopierFactory;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.edit.utils.IRequestCacheEntries;
import org.eclipse.papyrusrt.umlrt.core.tests.AbstractPapyrusRTCoreTest;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IValidationRule;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.State;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test moving elements inside a model, from one place to another.
 */
public class MoveElementTests extends AbstractPapyrusRTCoreTest {

	/**
	 * Constructor.
	 */
	public MoveElementTests() {
		// empty
	}

	@Test
	public void testMoveInitialStateFromOneRegionToAnother() throws Exception {
		// pseudostates should not be moved
		runMoveTestWithGetContext(emptyStateMachine, Collections.singletonList(initialPseudoState), false, IValidationRule.class);
	}

	@Test
	public void testMoveStateFromOneRegionToAnother() throws Exception {
		// states should not be allowed to move
		runMoveTestWithGetContext(compositeStateMachineRegion, Collections.singletonList(subState1), false, IValidationRule.class);
	}

	@Test
	public void testMoveTransition() throws Exception {
		// transitions should not be allowed to move
		runMoveTestWithGetContext(compositeStateMachineRegion, Collections.singletonList(t5), false, IValidationRule.class);
	}

	@Test
	public void testCopyValidTransition() throws Exception {
		// paste should work within same state machine
		List<EObject> elementsToCopy = new ArrayList<>();
		elementsToCopy.add(subState1);
		elementsToCopy.add(subState2);
		elementsToCopy.add(t5);

		List<EObject> rootElementToPaste = EcoreUtil.filterDescendants(elementsToCopy);

		// Copy all eObjects (inspired from PasteFromClipboardCommand)
		@SuppressWarnings("restriction")
		EcoreUtil.Copier copier = new CopierFactory(modelSet.getResourceSet()).get();
		copier.copyAll(rootElementToPaste);
		copier.copyReferences();

		runMoveTestWithGetContext(compositeStateMachineRegion, new ArrayList<>(copier.values()), true,
				IValidationRule.postEdit((targetElement, commandResults) -> {
					assertThat(commandResults.length, is(3));
				}));
	}

	@Test
	public void testCopyStateFromOneRegionToAnother() throws Exception {
		// paste should work within same state machine
		State copyState = EcoreUtil.copy(subState1);
		runMoveTestWithGetContext(compositeStateMachineRegion, Collections.singletonList(copyState), true,
				IValidationRule.postEdit(State.class, (targetElement, newState) -> {
					assertThat(newState, notNullValue());
				}));
	}


	@Test
	public void testNotMove2InitialStatesToRegion() throws Exception {
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(emptyCompositeStateRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_INITIAL));
		command.execute(null, null);
		EObject newElement = command.getNewElement();

		runMoveTestWithGetContext(emptyRegion, Arrays.asList(initialPseudoState, newElement), false, IValidationRule.class);

		DestroyElementCommand deleteCommand = new DestroyElementCommand(new DestroyElementRequest(newElement, false));
		deleteCommand.execute(null, null);
	}

	@Test
	public void testNotMoveInitialStateToRegionWithExisting() throws Exception {
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(emptyCompositeStateRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_INITIAL));
		command.execute(null, null);
		EObject newElement = command.getNewElement();

		runMoveTestWithGetContext(region, Arrays.asList(newElement), false, IValidationRule.class);

		DestroyElementCommand deleteCommand = new DestroyElementCommand(new DestroyElementRequest(newElement, false));
		deleteCommand.execute(null, null);
	}

	/**
	 * @see <a href="http://eclip.se/512362">bug 512362</a>
	 */
	@Test
	public void testCopyPasteEntryPoint() throws Exception {
		Pseudostate entryPointCopy = EcoreUtil.copy(compositeState.getConnectionPoints().stream()
				.filter(ps -> ps.getKind() == PseudostateKind.ENTRY_POINT_LITERAL)
				.findAny().orElseThrow(() -> new AssertionError("No entry-point in composite state")));
		runMoveTestWithGetContext(composite1, Collections.singletonList(entryPointCopy), true,
				IValidationRule.postEdit(Pseudostate.class, (targetElement, newPseudo) -> {
					assertThat(newPseudo, is(entryPointCopy));
					assertThat(newPseudo.getState(), is(composite1));
				}));
	}

	/**
	 * @see <a href="http://eclip.se/512362">bug 512362</a>
	 */
	@Test
	public void testCopyPasteExitPoint() throws Exception {
		Pseudostate exitPointCopy = EcoreUtil.copy(compositeState.getConnectionPoints().stream()
				.filter(ps -> ps.getKind() == PseudostateKind.EXIT_POINT_LITERAL)
				.findAny().orElseThrow(() -> new AssertionError("No exit-point in composite state")));
		runMoveTestWithGetContext(composite2, Collections.singletonList(exitPointCopy), true,
				IValidationRule.postEdit(Pseudostate.class, (targetElement, newPseudo) -> {
					assertThat(newPseudo, is(exitPointCopy));
					assertThat(newPseudo.getState(), is(composite2));
				}));
	}


	protected void runMoveTestWithGetContext(Element target, List<?> objectsToMove, boolean canMove, Class<? extends IValidationRule> validationClass) throws Exception {
		runMoveTestWithGetContext(target, objectsToMove, canMove, () -> {
			try {
				return validationClass.newInstance();
			} catch (Exception e) {
				throw new AssertionError(e);
			}
		});
	}

	protected void runMoveTestWithGetContext(Element target, List<?> objectsToMove, boolean canMove, Supplier<? extends IValidationRule> validationSupplier) throws Exception {
		Command command = getMoveCommandWithGetEditContext(target, objectsToMove, canMove);
		IValidationRule validationRule = null;
		// command has been tested when created. Runs the test if it is possible
		if (command != null && canMove) {
			getCommandStack().execute(command);
			validationRule = validationSupplier.get();
			validationRule.validatePostEdition(target, objectsToMove.toArray());
		} else {
			if (canMove) {
				fail("Command is executable while it should not be");
			}
		}

		if (!canMove) {
			// all possible tests for non executable commands have been done.
			return;
		}

		// assert editor is dirty
		Assert.assertTrue("Editor should be dirty after execute", isDirty());

		getCommandStack().undo();
		validationRule.validatePostUndo(target, objectsToMove.toArray());

		// assert editor is not dirty
		Assert.assertTrue("Editor should not be dirty after undo", !isDirty());

		getCommandStack().redo();
		validationRule.validatePostEdition(target, objectsToMove.toArray());

		// assert editor is dirty
		Assert.assertTrue("Editor should be dirty after redo", isDirty());

		getCommandStack().undo();
		validationRule.validatePostUndo(target, objectsToMove.toArray());

		// assert editor is not dirty
		Assert.assertTrue("Editor should not be dirty after undo", !isDirty());
	}

	protected Command getMoveCommandWithGetEditContext(EObject target, List<?> objectsToMove, boolean canCreate) throws Exception {
		GetEditContextRequest editContextRequest = new GetEditContextRequest(modelSet.getEditingDomain(), new MoveRequest(modelSet.getEditingDomain(), target, objectsToMove), target);

		editContextRequest.setParameter(IRequestCacheEntries.Cache_Maps, new HashMap<>());
		editContextRequest.setEditContext(target);
		editContextRequest.setClientContext(TypeContext.getContext(target));

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(target);
		if (provider == null) {
			Assert.fail("Impossible to get the editing domain provider");
		}

		EObject realTarget = target;
		Object result = null;
		final ICommand getEditContextCommand = provider.getEditCommand(editContextRequest);
		if (getEditContextCommand != null) {
			IStatus status = null;
			try {
				// this command could run in an unprotected transaction, it is not supposed to modify the model
				InternalTransactionalEditingDomain domain = (InternalTransactionalEditingDomain) modelSet.getEditingDomain();
				Map<String, Object> options = new HashMap<>();
				options.put(Transaction.OPTION_NO_NOTIFICATIONS, true);
				options.put(Transaction.OPTION_NO_VALIDATION, true);
				options.put(Transaction.OPTION_NO_TRIGGERS, true);
				Transaction transaction = domain.startTransaction(false, options);
				try {
					status = getEditContextCommand.execute(null, null);
				} finally {
					transaction.commit();
				}
			} catch (InterruptedException e) {
				Assert.fail(e.getMessage());
			} catch (ExecutionException e) {
				Assert.fail(e.getMessage());
			} catch (RollbackException e) {
				Assert.fail(e.getMessage());
			}
			if (!(status == null || !status.isOK())) {
				result = getEditContextCommand.getCommandResult().getReturnValue();
			}
			if (result instanceof EObject) {
				realTarget = (EObject) result;
			}
		}

		provider = ElementEditServiceUtils.getCommandProvider(target);
		if (provider == null) {
			return UnexecutableCommand.INSTANCE;
		}

		ICommand moveGMFCommand = provider.getEditCommand(new MoveRequest(modelSet.getEditingDomain(), realTarget, objectsToMove));
		if (!canCreate) {
			// command should not be executable: either it should be null or it should be not executable
			if (moveGMFCommand != null && moveGMFCommand.canExecute()) {
				fail("Move command is executable but it was expected as not executable");
			}
		} else {
			// command should be executable in this case
			assertNotNull("Command should not be null", moveGMFCommand);
			assertTrue("Command should be executable", moveGMFCommand.canExecute());
			// command is executable, and it was expected to => run the creation
			Command emfCommand = GMFtoEMFCommandWrapper.wrap(moveGMFCommand);
			return emfCommand;
		}
		return null;
	}

	/**
	 * Returns the command to move the list of elements to the specified target.
	 *
	 * @param target
	 *            target for the move
	 * @param canCreate
	 *            <code>true</code> if new element can be created in the specified owner
	 * @param objectsToMove
	 *            list of object to move
	 * @return the command to move the elements or <code>null</code>
	 */
	protected Command getMoveCommand(EObject target, List<?> objectsToMove, boolean canCreate) throws Exception {
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(target);
		ICommand command = elementEditService.getEditCommand(new MoveRequest(target, objectsToMove));

		if (!canCreate) {
			// command should not be executable: either it should be null or it should be not executable
			if (command != null && command.canExecute()) {
				fail("Move command is executable but it was expected as not executable");
			}
		} else {
			// command should be executable in this case
			assertNotNull("Command should not be null", command);
			assertTrue("Command should be executable", command.canExecute());
			// command is executable, and it was expected to => run the creation
			Command emfCommand = GMFtoEMFCommandWrapper.wrap(command);
			return emfCommand;
		}
		return null;
	}
}
