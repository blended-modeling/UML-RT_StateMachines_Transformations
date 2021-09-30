/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 479635, 476984, 493866, 510263
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.inject.Named;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.edit.utils.IRequestCacheEntries;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IConnectionValidationRule;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IValidationRule;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestRule;

/**
 * Generic class test for edition tests.
 */
@PluginResource("resource/TestModel.di")
public abstract class AbstractPapyrusRTCoreTest extends AbstractPapyrusTest {
	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();

	protected static final String NOT_DIRTY_AFTER_UNDO = "Model resource should not be dirty after undo";
	protected static final String NOT_DIRTY_BEFORE_TEST = "Model resource should not be dirty before test";
	@Rule
	public HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	@Rule
	public FixtureElementRule fixtureRule = new FixtureElementRule();

	@Rule
	public TestRule uiThread = new UIThreadRule(true);

	@Named("TestModel")
	protected Model rootModel;
	@Named("TestModel::Protocol0")
	protected Package protocolContainer;
	@Named("TestModel::Protocol0::Protocol0")
	protected Collaboration protocol;
	@Named("TestModel::Protocol0::Protocol0")
	protected Interface messageSetIn;
	@Named("TestModel::Protocol0::Protocol0~")
	protected Interface messageSetOut;
	@Named("TestModel::Protocol0::Protocol0::InProtocolMessage0")
	protected Operation inProtocolMessage;
	@Named("TestModel::Protocol0::Protocol0~::OutProtocolMessage0")
	protected Operation outProtocolMessage;

	@Named("TestModel::Capsule0")
	protected org.eclipse.uml2.uml.Class capsule;

	@Named("TestModel::HasStateMachine")
	protected org.eclipse.uml2.uml.Class hasStateMachine;
	@Named("TestModel::HasStateMachine::StateMachine")
	protected StateMachine stateMachine;
	@Named("TestModel::HasStateMachine::StateMachine::Region")
	protected Region region;
	@Named("TestModel::HasStateMachine::StateMachine::Region::State")
	protected State state;
	@Named("TestModel::HasStateMachine::StateMachine::Region::init")
	protected Pseudostate initialPseudoState;

	@Named("TestModel::HasEmptyStateMachine")
	protected org.eclipse.uml2.uml.Class hasEmptyStateMachine;
	@Named("TestModel::HasEmptyStateMachine::StateMachine")
	protected StateMachine emptyStateMachine;
	@Named("TestModel::HasEmptyStateMachine::StateMachine::Region")
	protected Region emptyRegion;
	@Named("TestModel::HasEmptyStateMachine::StateMachine::Region::EmptyCompositeState")
	protected State emptyCompositeState;
	@Named("TestModel::HasEmptyStateMachine::StateMachine::Region::EmptyCompositeState::Region")
	protected Region emptyCompositeStateRegion;
	@Named("TestModel::Thing")
	protected org.eclipse.uml2.uml.Class umlClass;
	@Named("TestModel::Thing::doIt")
	protected Operation umlOperation;
	@Named("TestModel::Thing::doIt::parameter1")
	protected Parameter umlParameter;

	@Named("TestModel::CapsuleWithCompositeState")
	protected org.eclipse.uml2.uml.Class hasCompositeStateMachine;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine")
	protected StateMachine compositeStateMachine;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region")
	protected Region compositeStateMachineRegion;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1")
	protected State compositeState;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1")
	protected Region compositeStateRegion;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::SubState1")
	protected State subState1;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::SubState2")
	protected State subState2;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::composite1")
	protected State composite1;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::composite2")
	protected State composite2;

	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::composite1::exit1")
	protected Pseudostate composite1_exit1;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::composite1::exit2")
	protected Pseudostate composite1_exit2;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::composite1::exit3")
	protected Pseudostate composite1_exit3;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::composite1::exit4")
	protected Pseudostate composite1_exit4;

	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::composite2::entry1")
	protected Pseudostate composite2_entry1;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::composite2::entry2")
	protected Pseudostate composite2_entry2;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::composite2::entry3")
	protected Pseudostate composite2_entry3;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::composite2::entry4")
	protected Pseudostate composite2_entry4;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::t1")
	protected Transition t1;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::t2")
	protected Transition t2;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::t3")
	protected Transition t3;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::t4")
	protected Transition t4;
	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::t5")
	protected Transition t5;

	@Before
	public void verifyContext() {
		Assert.assertNotNull(protocol);
		Assert.assertNotNull(messageSetIn);
		Assert.assertEquals("MessageSet In does not correspond to the one from utility methods", ProtocolUtils.getMessageSetIn(protocol), messageSetIn);
		Assert.assertNotNull(inProtocolMessage);
		Assert.assertNotNull(messageSetOut);
		Assert.assertEquals("MessageSet Out does not correspond to the one from utility methods", ProtocolUtils.getMessageSetOut(protocol), messageSetOut);
		Assert.assertNotNull(outProtocolMessage);
		Assert.assertNotNull(capsule);
		Assert.assertNotNull(hasStateMachine);
		Assert.assertNotNull(stateMachine);
		Assert.assertNotNull(region);
		Assert.assertNotNull(state);
		Assert.assertNotNull(hasCompositeStateMachine);
		Assert.assertNotNull(compositeStateMachine);
		Assert.assertNotNull(compositeStateMachineRegion);
		Assert.assertNotNull(compositeState);
		Assert.assertNotNull(compositeStateRegion);
		Assert.assertNotNull(subState1);
		Assert.assertNotNull(subState2);
		Assert.assertNotNull(umlClass);
		Assert.assertNotNull(umlOperation);
		Assert.assertNotNull(umlParameter);
		Assert.assertNotNull(hasEmptyStateMachine);
		Assert.assertNotNull(emptyStateMachine);
		Assert.assertNotNull(emptyRegion);
		Assert.assertNotNull(emptyCompositeState);
		Assert.assertNotNull(emptyCompositeStateRegion);
		Assert.assertNotNull(composite1);
		assertNotNull(t1);
		assertNotNull(t2);
		assertNotNull(t3);
		assertNotNull(t4);
		assertNotNull(t5);

		Assert.assertTrue(NOT_DIRTY_BEFORE_TEST, !isDirty());

		// load registry in ui if required
		if (Display.getCurrent() != null) {
			Display.getCurrent().syncExec(() -> {
				ElementTypeRegistry reg = ElementTypeRegistry.getInstance();
				assertThat(reg.getType(IUMLRTElementTypes.CAPSULE_ID), notNullValue());
			});
		} else {
			Display.getDefault().syncExec(() -> {
				ElementTypeRegistry reg = ElementTypeRegistry.getInstance();
				assertThat(reg.getType(IUMLRTElementTypes.CAPSULE_ID), notNullValue());
			});
		}
	}

	/**
	 * Deletes the element in the given owner element, undo and redo the action.
	 *
	 * @param elementToDestroy
	 *            the element to delete
	 * @param canCreate
	 *            <code>true</code> if new element can be created in the
	 *            specified owner
	 */
	protected Command getDeleteChildCommand(EObject elementToDestroy, boolean confirmationRequired) throws Exception {
		GetEditContextRequest editContextRequest = new GetEditContextRequest(modelSet.getEditingDomain(), new DestroyElementRequest(modelSet.getEditingDomain(), elementToDestroy, confirmationRequired), elementToDestroy);

		editContextRequest.setParameter(IRequestCacheEntries.Cache_Maps, new HashMap<>());
		editContextRequest.setEditContext(elementToDestroy);
		try {
			editContextRequest.setClientContext(TypeContext.getContext(elementToDestroy));
		} catch (ServiceException e) {
			Assert.fail(e.getMessage());
		}

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(elementToDestroy);
		if (provider == null) {
			Assert.fail("Impossible to get the editing domain provider");
		}

		EObject target = elementToDestroy;
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
				target = (EObject) result;
			}
		}

		provider = ElementEditServiceUtils.getCommandProvider(target);
		if (provider == null) {
			return UnexecutableCommand.INSTANCE;
		}
		DestroyElementRequest realRequest = new DestroyElementRequest(modelSet.getEditingDomain(), target, confirmationRequired);
		realRequest.setClientContext(TypeContext.getContext(modelSet.getEditingDomain()));
		ICommand deleteGMFCommand = provider.getEditCommand(realRequest);
		assertNotNull("Command should not be null", deleteGMFCommand);
		assertTrue("Command should be executable", deleteGMFCommand.canExecute());
		// command is executable, and it was expected to => run the creation
		Command emfCommand = new GMFtoEMFCommandWrapper(deleteGMFCommand);
		return emfCommand;
	}

	protected boolean isDirty() {
		return modelSet.getEditingDomain().getCommandStack().canUndo();
	}

	/**
	 * @return
	 */
	protected CommandStack getCommandStack() {
		return modelSet.getEditingDomain().getCommandStack();
	}

	/**
	 * After a possibly failed test, unwinds the undo stack as much as possible
	 * to try to return to a clean editor.
	 */
	@After
	public void unwindUndoStack() {
		CommandStack stack = getCommandStack();
		while (stack.canUndo()) {
			stack.undo();
		}
	}

	protected void runCreationTest(EObject owner, IHintedType hintedType, boolean canCreate) throws Exception {
		Command command = getCreateChildCommand(owner, hintedType, canCreate);

		// command has been tested when created. Runs the test if it is possible
		if (command != null && canCreate) {
			getCommandStack().execute(command);
			if (getCommandStack().canUndo()) {
				getCommandStack().undo();
			} else {
				fail("impossible to undo after creation");
			}
			Assert.assertTrue(NOT_DIRTY_AFTER_UNDO, !isDirty());
			if (getCommandStack().canRedo()) {
				getCommandStack().redo();
			} else {
				fail("impossible to redo");
			}
			if (getCommandStack().canUndo()) {
				getCommandStack().undo();
			} else {
				fail("impossible to undo after redo");
			}
			// assert editor is not dirty
			Assert.assertTrue(NOT_DIRTY_AFTER_UNDO, !isDirty());
		}
	}

	protected void runNotCreationTest(EObject owner, IHintedType hintedType, boolean canCreate) throws Exception {
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(owner);
		ICommand command = elementEditService.getEditCommand(new CreateElementRequest(owner, hintedType));
		Assert.assertNull(command);
	}

	protected void runCreationTestWithGetContext(Element owner, IHintedType hintedType, boolean canCreate, Class<? extends IValidationRule> validationClass) throws Exception {
		runCreationTestWithGetContext(owner, hintedType, canCreate, 1, () -> {
			try {
				return validationClass.newInstance();
			} catch (Exception e) {
				throw new AssertionError(e);
			}
		});
	}

	protected void runCreationTestWithGetContext(Element owner, IHintedType hintedType, boolean canCreate, Supplier<? extends IValidationRule> validationSupplier) throws Exception {
		runCreationTestWithGetContext(owner, hintedType, canCreate, 1, validationSupplier);
	}

	protected void runCreationTestWithGetContext(Element owner, IHintedType hintedType, boolean canCreate, int iterations, Supplier<? extends IValidationRule> validationSupplier) throws Exception {
		List<Command> commands = new ArrayList<>(iterations);
		List<IValidationRule> validations = new ArrayList<>(iterations);
		List<Object[]> results = new ArrayList<>(iterations);
		for (int i = 0; i < iterations; i++) {
			Command command = getCreateChildCommandWithGetEditContext(owner, hintedType, canCreate);
			commands.add(command);

			// command has been tested when created. Runs the test if it is possible
			if (command != null && canCreate) {
				getCommandStack().execute(command);
				IValidationRule validationRule = validationSupplier.get();
				validations.add(validationRule);
				Object[] commandResults = command.getResult().toArray();
				results.add(commandResults);
				validationRule.validatePostEdition(owner, commandResults);
			} else {
				if (canCreate) {
					fail("Command is executable while it should not be");
				}
			}
		}

		if (!canCreate) {
			// all possible tests for non executable commands have been done.
			return;
		}

		// assert editor is dirty
		Assert.assertTrue("Editor should be dirty after execute", isDirty());

		for (int i = iterations - 1; i >= 0; i--) {
			getCommandStack().undo();
			validations.get(i).validatePostUndo(owner, results.get(i));
		}

		// assert editor is not dirty
		Assert.assertTrue(NOT_DIRTY_AFTER_UNDO, !isDirty());

		for (int i = 0; i < iterations; i++) {
			getCommandStack().redo();
			validations.get(i).validatePostEdition(owner, results.get(i));
		}

		// assert editor is dirty
		Assert.assertTrue("Editor should be dirty after redo", isDirty());

		for (int i = iterations - 1; i >= 0; i--) {
			getCommandStack().undo();
			validations.get(i).validatePostUndo(owner, results.get(i));
		}

		// assert editor is not dirty
		Assert.assertTrue(NOT_DIRTY_AFTER_UNDO, !isDirty());
	}

	/**
	 * Creates the element in the given owner element, undo and redo the action
	 *
	 * @param owner
	 *            owner of the new element
	 * @param hintedType
	 *            type of the new element
	 * @param canCreate
	 *            <code>true</code> if new element can be created in the specified owner
	 */
	protected Command getCreateChildCommand(EObject owner, IHintedType hintedType, boolean canCreate) throws Exception {
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(owner);
		ICommand command = elementEditService.getEditCommand(new CreateElementRequest(owner, hintedType));

		if (!canCreate) {
			// command should not be executable: either it should be null or it should be not executable
			if (command != null && command.canExecute()) {
				fail("Creation command is executable but it was expected as not executable");
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

	protected Command getCreateChildCommandWithGetEditContext(EObject owner, IHintedType hintedType, boolean canCreate) throws Exception {
		GetEditContextRequest editContextRequest = new GetEditContextRequest(modelSet.getEditingDomain(), new CreateElementRequest(modelSet.getEditingDomain(), owner, hintedType), owner);

		editContextRequest.setParameter(IRequestCacheEntries.Cache_Maps, new HashMap<>());
		editContextRequest.setEditContext(owner);
		editContextRequest.setClientContext(TypeContext.getContext(owner));

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(owner);
		if (provider == null) {
			Assert.fail("Impossible to get the editing domain provider");
		}

		EObject target = owner;
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
				target = (EObject) result;
			}
		}

		provider = ElementEditServiceUtils.getCommandProvider(target);
		if (provider == null) {
			return UnexecutableCommand.INSTANCE;
		}

		ICommand createGMFCommand = provider.getEditCommand(new CreateElementRequest(modelSet.getEditingDomain(), target, hintedType));
		if (!canCreate) {
			// command should not be executable: either it should be null or it should be not executable
			if (createGMFCommand != null && createGMFCommand.canExecute()) {
				fail("Creation command is executable but it was expected as not executable");
			}
		} else {
			// command should be executable in this case
			assertNotNull("Command should not be null", createGMFCommand);
			assertTrue("Command should be executable", createGMFCommand.canExecute());
			// command is executable, and it was expected to => run the creation
			Command emfCommand = GMFtoEMFCommandWrapper.wrap(createGMFCommand);
			return emfCommand;
		}
		return null;
	}

	protected static class NameValidationFactory implements Iterator<IValidationRule> {
		private final Iterator<String> expectedNames;

		/**
		 * Initializes me with an initial sequence of expected names and, optionally,
		 * a seed and operator to generate more (potentially infinite) names.
		 * 
		 * @param initialSequence
		 *            the initial sequence of names to expect
		 * @param seedForRest
		 *            a seed for additional names to be generated from, or {@code null} if no additional names are expected
		 * @param additionalNameGenerator
		 *            an operator on the {@code seedForRest} that generates additional names, or {@code null} if none
		 */
		NameValidationFactory(List<String> initialSequence, String seedForRest, UnaryOperator<String> additionalNameGenerator) {
			super();

			Stream<String> expectedNames = initialSequence.stream();
			if ((seedForRest != null) && (additionalNameGenerator != null)) {
				expectedNames = Stream.concat(expectedNames, Stream.iterate(seedForRest, additionalNameGenerator));
			}

			this.expectedNames = expectedNames.iterator();
		}

		/**
		 * Initializes me with a sequence of expected names.
		 * 
		 * @param sequenceOfNames
		 *            the sequence of names to expect
		 */
		public NameValidationFactory(List<String> sequenceOfNames) {
			this(sequenceOfNames, null, null);
		}

		/**
		 * Initializes me with an initial sequence of expected names and, optionally,
		 * additional names based on incrementing the numeric suffix of a given seed.
		 * 
		 * @param initialSequence
		 *            the initial sequence of names to expect
		 * @param seedForRest
		 *            a seed with a numeric suffix for additional names to be generated
		 *            from, or {@code null} if no additional names are expected
		 */
		public NameValidationFactory(List<String> initialSequence, String seedForRest) {
			this(initialSequence, seedForRest, incrementer());
		}

		private static UnaryOperator<String> incrementer() {
			Matcher suffix = Pattern.compile("\\d+$").matcher("");

			return previous -> {
				suffix.reset(previous);
				if (!suffix.find()) {
					throw new IllegalStateException("Seed name has no numeric suffix");
				} else {
					int next = Integer.parseInt(suffix.group()) + 1;
					return previous.substring(0, suffix.start()) + next;
				}
			};
		}

		@Override
		public boolean hasNext() {
			return expectedNames.hasNext();
		}

		@Override
		public IValidationRule next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			String expectedName = expectedNames.next();

			IValidationRule result = new IValidationRule() {
				private NamedElement element;

				@Override
				public void validatePostEdition(Element targetElement, Object[] commandResults) throws Exception {
					assertThat("No element created", commandResults.length, greaterThan(0));
					assertThat("Non-element created", commandResults[0], instanceOf(NamedElement.class));
					element = (NamedElement) commandResults[0];
					assertThat("Wrong element name", element.getName(), is(expectedName));
				}

				@Override
				public void validatePostUndo(Element targetElement, Object[] commandResults) throws Exception {
					assertThat("Element still attached to the model",
							element.eContainer(), not(instanceOf(Element.class)));
				}
			};

			return result;
		}
	}

	protected void runCreationRelationshipTestWithGetContext(Element source, Element target, IHintedType hintedType, boolean canCreate, Class<? extends IConnectionValidationRule> validationClass) throws Exception {
		runCreationRelationshipTestWithGetContext(source, target, hintedType, canCreate, 1, validationClass.newInstance());
	}

	protected void runCreationRelationshipTestWithGetContext(Element source, Element target, IHintedType hintedType, boolean canCreate, int iterations, IConnectionValidationRule validationRule) throws Exception {
		List<Command> commands = new ArrayList<>(iterations);
		List<IConnectionValidationRule> validations = new ArrayList<>(iterations);
		for (int i = 0; i < iterations; i++) {
			Command command = getCreateConnectionCommand(source, target, hintedType, canCreate);
			commands.add(command);

			// command has been tested when created. Runs the test if it is possible
			if (command != null && canCreate) {
				getCommandStack().execute(command);
				validations.add(validationRule);
				Element newElement = getElementFromCommandResult(command);
				validationRule.validatePostEdition(source, target, newElement);
			} else {
				if (canCreate) {
					fail("Command is executable while it should not be");
				}
			}
		}

		if (!canCreate) {
			// all possible tests for non executable commands have been done.
			return;
		}

		// assert editor is dirty
		Assert.assertTrue("Editor should be dirty after execute", isDirty());

		for (int i = iterations - 1; i >= 0; i--) {
			getCommandStack().undo();
			validations.get(i).validatePostUndo(source, target, getElementFromCommandResult(commands.get(i)));
		}

		// assert editor is not dirty
		Assert.assertTrue(NOT_DIRTY_AFTER_UNDO, !isDirty());

		for (int i = 0; i < iterations; i++) {
			getCommandStack().redo();
			validations.get(i).validatePostEdition(source, target, getElementFromCommandResult(commands.get(i)));
		}

		// assert editor is dirty
		Assert.assertTrue("Editor should be dirty after redo", isDirty());

		for (int i = iterations - 1; i >= 0; i--) {
			getCommandStack().undo();
			validations.get(i).validatePostUndo(source, target, getElementFromCommandResult(commands.get(i)));
		}

		// assert editor is not dirty
		Assert.assertTrue(NOT_DIRTY_AFTER_UNDO, !isDirty());
	}

	/**
	 * @param source
	 * @param target
	 * @param hintedType
	 * @param canCreate
	 * @return
	 * @throws ServiceException
	 */
	protected Command getCreateConnectionCommand(Element source, Element target, IHintedType hintedType, boolean canCreate) throws ServiceException {
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(source.eContainer()); // container for the transition?
		CreateRelationshipRequest request = new CreateRelationshipRequest(source, target, hintedType);
		// set Parameters
		// could be required?!
		// request.setParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_SOURCE_VIEW, sourceView);
		// request.setParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_TARGET_VIEW, targetView);

		ICommand command = elementEditService.getEditCommand(request);
		if (!canCreate) {
			// command should not be executable: either it should be null or it should be not executable
			if (command != null && command.canExecute()) {
				fail("Creation command is executable but it was expected as not executable");
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

	/**
	 * @param command
	 * @return
	 */
	protected Element getElementFromCommandResult(Command command) {
		Assert.assertEquals("command result should not be empty", 1, command.getResult().toArray().length);
		if (command.getResult().toArray()[0] instanceof List<?>) {
			List<?> results = (List<?>) command.getResult().toArray()[0];
			Assert.assertEquals("command result should not be empty", 1, results.size());
			assertThat(results.get(0), notNullValue());
			assertThat(results.get(0), instanceOf(Element.class));
			return (Element) results.get(0);
		} else {
			assertThat(command.getResult().toArray()[0], instanceOf(Element.class));
			return (Element) command.getResult().toArray()[0];
		}
	}

}
