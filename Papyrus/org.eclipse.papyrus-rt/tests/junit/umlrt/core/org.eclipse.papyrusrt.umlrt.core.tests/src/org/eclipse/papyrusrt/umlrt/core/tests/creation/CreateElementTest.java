/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 434993
 *  Christian W. Damus - bugs 468071, 492565, 479635, 476984, 493866, 510263, 518290
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.creation;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.eclipse.papyrusrt.junit.matchers.ElementTypeMatchers.isA;
import static org.eclipse.papyrusrt.junit.matchers.StringMatcher.nullOrEmpty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.junit.rules.UIThread;
import org.eclipse.papyrusrt.umlrt.core.tests.AbstractPapyrusRTCoreTest;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IConnectionValidationRule;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IValidationRule;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test creation of UML elements (Creation / Undo / redo)
 */
public class CreateElementTest extends AbstractPapyrusRTCoreTest {

	@Test
	public void testNotCreateEnumerationLiteralInPackage() throws Exception {
		// create a package in the root model
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(rootModel, UMLElementTypes.PACKAGE));
		command.execute(null, null);
		EObject newPackage = command.getNewElement();
		// test not creation of EnumerationLiteral in that package
		runNotCreationTest(newPackage, UMLElementTypes.ENUMERATION_LITERAL, true);
	}

	@Test
	public void testNotCreatePortInClass() throws Exception {
		// create a package in the root model
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(rootModel, UMLElementTypes.CLASS));
		command.execute(null, null);
		EObject newClass = command.getNewElement();
		// test not creation of EnumerationLiteral in that package
		runNotCreationTest(newClass, UMLElementTypes.PORT, true);
	}

	@Test
	@FailingTest("Architecture issue on the core / tooling interaction")
	public void testCreateStateMachineInCapsule() throws Exception {
		runCreationTestWithGetContext(capsule, UMLRTElementTypesEnumerator.RT_STATE_MACHINE, true, CreateRTStateMachineOnCapsuleValidation.class);
	}

	@Test
	public void testCreateStateMachineInPassiveClass() throws Exception {
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(rootModel, UMLElementTypes.CLASS));
		command.execute(null, null);
		EObject newClass = command.getNewElement();
		// test not creation of EnumerationLiteral in that package
		runNotCreationTest(newClass, UMLRTElementTypesEnumerator.RT_STATE_MACHINE, true);
	}


	@Test
	public void testNotCreateAttributeInEnumeration() throws Exception {
		// create a package in the root model
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(rootModel, UMLElementTypes.ENUMERATION));
		command.execute(null, null);
		EObject newEnumeration = command.getNewElement();
		// test not creation of EnumerationLiteral in that package
		runNotCreationTest(newEnumeration, UMLElementTypes.PROPERTY, true);
	}

	@Test
	public void testNotCreateOperationInEnumeration() throws Exception {
		// create a package in the root model
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(rootModel, UMLElementTypes.ENUMERATION));
		command.execute(null, null);
		EObject newEnumeration = command.getNewElement();
		// test not creation of EnumerationLiteral in that package
		runNotCreationTest(newEnumeration, UMLElementTypes.OPERATION, true);
	}

	@Test
	public void testCreateProtocolInModel() throws Exception {
		runCreationTestWithGetContext(rootModel, UMLRTElementTypesEnumerator.PROTOCOL, true, CreateProtocolOnModelValidation.class);
	}

	@Test
	public void testNotCreateProtocolInClassifier() throws Exception {
		// create a package in the root model
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(rootModel, UMLElementTypes.CLASS));
		command.execute(null, null);
		EObject newClassifier = command.getNewElement();
		// test not creation of EnumerationLiteral in that package
		runNotCreationTest(newClassifier, UMLRTElementTypesEnumerator.PROTOCOL, true);
	}

	@Test
	public void testCreateProtocolMessageInMessageSet() throws Exception {
		// test creation of ProtocolMessage in messageSet
		NameValidationFactory validation = new NameValidationFactory(Collections.emptyList(), "InProtocolMessage1");

		runCreationTestWithGetContext(messageSetIn, UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_IN, true, 3, validation::next);
	}

	@Test
	public void testNotCreateProtocolMessageInProtocol() throws Exception {
		// test not creation of ProtocolMessage in protocol0
		runNotCreationTest(protocol, UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_IN, true);
	}

	@Test
	public void testCreateOneParameterInProtocolMessage() throws Exception {
		// test creation of one Parameter in a protocol Operation. Here to check for some specific undo/redo issues
		NameValidationFactory validation = new NameValidationFactory(Arrays.asList("data"), "parameter2");

		runCreationTestWithGetContext(inProtocolMessage, UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_PARAMETER, true, 1, validation::next);
	}

	@Test
	public void testCreateSeveralParametersInProtocolMessage() throws Exception {
		// test creation of a sequence of Parameters in a protocol Operation

		NameValidationFactory validation = new NameValidationFactory(Arrays.asList("data"), "parameter2");

		runCreationTestWithGetContext(inProtocolMessage, UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_PARAMETER, true, 3, validation::next);
	}

	@Test
	public void testCreateParametersInProtocolMessageAfterOthers() throws Exception {
		// test creation of a sequence of Parameters in a protocol Operation

		// There is no 'data' parameter in this scenario
		NameValidationFactory validation = new NameValidationFactory(Collections.emptyList(), "parameter2");

		runCreationTestWithGetContext(outProtocolMessage, UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_PARAMETER, true, 3, validation::next);
	}

	@Test
	public void testCreateCapsulePartInCapsule() throws Exception {
		// test creation of CapsulePart in capsule0
		runCreationTestWithGetContext(capsule, UMLRTElementTypesEnumerator.CAPSULE_PART, true, CreateCapsulePartInCapsuleValidation.class);
	}

	@Test
	public void testNotCreateCapsulePartInProtocol() throws Exception {
		// test not creation of CapsulePart in protocol0
		runNotCreationTest(protocol, UMLRTElementTypesEnumerator.CAPSULE_PART, true);
	}

	@Test
	public void testCreateRTPortInCapsule() throws Exception {
		// test creation of RTPort in capsule
		runCreationTestWithGetContext(capsule, UMLRTElementTypesEnumerator.RT_PORT, true, RTPortValidationRule.class);
	}

	@Test
	public void testCreateRTStateInRegion() throws Exception {
		// test creation of RTState in state machine
		runCreationTestWithGetContext(region, UMLRTElementTypesEnumerator.RT_STATE, true,
				IValidationRule.postEdit(State.class, (targetElement, newState) -> {
					// from Bug 4942802: isSimple=true, isComposite=false, isOrthogonal=false, doActivity unset
					assertThat(newState.getAppliedStereotype("UMLRTStateMachines::RTState"), notNullValue());
					assertThat(newState.isSimple(), is(true));
					assertThat(newState.isComposite(), is(false));
					assertThat(newState.isOrthogonal(), is(false));
					assertThat(newState.getDoActivity(), nullValue());
				}));
	}

	@Test
	public void testNotCreateRTStateInStateMachine() throws Exception {
		// test creation of RTState in state machine
		runNotCreationTest(stateMachine, UMLRTElementTypesEnumerator.RT_STATE, false);
	}

	@Test
	public void testNotCreateRTStateInState() throws Exception {
		// test creation of RTState in state
		runNotCreationTest(state, UMLRTElementTypesEnumerator.RT_STATE, false);
	}

	@Test
	public void testCreateRTRegionInRTState() throws Exception {
		// Assume creation of a state
		Command command = getCreateChildCommandWithGetEditContext(region, UMLRTElementTypesEnumerator.RT_STATE, true);
		assumeThat("Cannot create RTState", command, isExecutable());
		command.execute();
		State state = (State) command.getResult().iterator().next();

		// test creation of RTRegion in RTState
		runCreationTestWithGetContext(state, UMLRTElementTypesEnumerator.RT_REGION, true,
				IValidationRule.postEdit(Region.class, (targetElement, newRegion) -> {
					// It's configured with an initial state
					assertThat(newRegion.getSubvertices(), hasItem(isA(UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_INITIAL)));
					assertThat(newRegion.getAppliedStereotype("UMLRTStateMachines::RTRegion"), notNullValue());
				}));
	}

	@Test
	@UIThread
	public void testCannotCreateSecondRegionInRTState() throws Exception {
		// Assume creation of a composite state
		Command command = getCreateChildCommandWithGetEditContext(region, UMLRTElementTypesEnumerator.RT_STATE, true);
		assumeThat("Cannot create RTState", command, isExecutable());
		modelSet.getEditingDomain().getCommandStack().execute(command);
		State state = (State) command.getResult().iterator().next();
		command = getCreateChildCommandWithGetEditContext(state, UMLRTElementTypesEnumerator.RT_REGION, true);
		assumeThat("Cannot create RTRegion", command, isExecutable());
		modelSet.getEditingDomain().getCommandStack().execute(command);

		// test inability to create a second region in a composite RTState
		command = getCreateChildCommand(state, UMLRTElementTypesEnumerator.RT_REGION, false);
		assertThat("Can create another RTRegion", command, not(isExecutable()));
	}

	@Test
	@UIThread
	public void testCannotCreateSecondRTSMInCapsule() throws Exception {
		// test inability to create a second SM in the Capsule
		Command command = getCreateChildCommandWithGetEditContext(hasStateMachine, UMLRTElementTypesEnumerator.RT_STATE_MACHINE, false);
		assertThat("Cannot create another RTSM", command, not(isExecutable()));
	}

	@Test
	@UIThread
	public void testNotCreateClassInRTStateMachine() throws Exception {
		runNotCreationTest(stateMachine, UMLElementTypes.CLASS, false);
	}

	@Test
	public void testNotCreateAttributeInRTStateMachine() throws Exception {
		runNotCreationTest(stateMachine, UMLElementTypes.PROPERTY, false);
	}

	@Test
	public void testNotCreateEnumerationInRTStateMachine() throws Exception {
		runNotCreationTest(stateMachine, UMLElementTypes.ENUMERATION, false);
	}

	@Test
	public void testNotCreateOperationInRTStateMachine() throws Exception {
		runNotCreationTest(stateMachine, UMLElementTypes.OPERATION, false);
	}

	// checks we did not break the ability to create such element on standard classifier (e.g. umlClass)
	@Test
	public void testCreateClassInClass() throws Exception {
		runCreationTest(umlClass, UMLElementTypes.CLASS, true);
	}

	@Test
	@UIThread
	public void testCreateAttributeInClass() throws Exception {
		runCreationTest(umlClass, UMLElementTypes.PROPERTY, true);
	}

	@Test
	@UIThread
	public void testCreateEnumerationInClass() throws Exception {
		runCreationTest(umlClass, UMLElementTypes.ENUMERATION, true);
	}

	@Test
	public void testCreateOperationInClass() throws Exception {
		runCreationTest(umlClass, UMLElementTypes.OPERATION, true);
	}

	@Test
	@FailingTest("Architecture issue on the core / tooling interaction")
	public void testCreateCapsuleInModel() throws Exception {
		runCreationTest(rootModel, UMLRTElementTypesEnumerator.CAPSULE, true);
	}

	@Test
	public void testCreateProtocolMessageINOnProtocol() throws Exception {
		runCreationTestWithGetContext(protocol, UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_IN, true, CreateProtocolMessageINOnProtocolValidation.class);
	}

	@Test
	public void testCreateProtocolMessageInOnMessageSetIn() throws Exception {
		runCreationTestWithGetContext(messageSetIn, UMLRTElementTypesEnumerator.PROTOCOL_MESSAGE_IN, true, CreateProtocolMessageInOnMessageSetInValidation.class);
	}

	@Test
	public void testNotCreateAttributeOnPort() throws Exception {
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(capsule, UMLRTElementTypesEnumerator.RT_PORT));
		command.execute(null, null);
		EObject newPort = command.getNewElement();

		runNotCreationTest(newPort, UMLElementTypes.PROPERTY, false);

		DestroyElementCommand deleteCommand = new DestroyElementCommand(new DestroyElementRequest(newPort, false));
		deleteCommand.execute(null, null);
	}

	////////////////////////////////////////////////////////////////
	// test for state machine elements (pseudo states, etc.)
	////////////////////////////////////////////////////////////////

	@Test
	public void testCreateInitialStateInStateMachineRegion() throws Exception {
		// test creation of Initial pseudostate in region
		runCreationTestWithGetContext(emptyRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_INITIAL, true,
				IValidationRule.postEdit(Pseudostate.class, (targetElement, newPseudoState) -> {
					assertThat(newPseudoState.getAppliedStereotype("UMLRTStateMachines::RTPseudostate"), notNullValue());
					assertThat(newPseudoState.getName(), nullOrEmpty());
				}));
	}

	@Test
	public void testNotCreateTwoInitialStatesInStateMachineRegion() throws Exception {
		// Bug 494292: only one initial pseudo state per region
		// create 1st initial state
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(emptyRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_INITIAL));
		command.execute(null, null);
		EObject newElement = command.getNewElement();

		// test not creation of 2nd Initial pseudostate in region
		runCreationTestWithGetContext(emptyRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_INITIAL, false, IValidationRule.class);

		DestroyElementCommand deleteCommand = new DestroyElementCommand(new DestroyElementRequest(newElement, false));
		deleteCommand.execute(null, null);
	}

	@Test
	public void testCreateInitialStateInCompositeStateRegion() throws Exception {
		// test creation of Initial pseudostate in region
		runCreationTestWithGetContext(emptyCompositeStateRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_INITIAL, true,
				IValidationRule.postEdit(Pseudostate.class, (targetElement, newPseudoState) -> {
					assertThat(newPseudoState.getAppliedStereotype("UMLRTStateMachines::RTPseudostate"), notNullValue());
					assertThat(newPseudoState.getName(), nullOrEmpty());
				}));
	}

	@Test
	public void testNotCreateTwoInitialStatesInCompositeStateRegion() throws Exception {
		// Bug 494292: only one initial pseudo state per region
		// create 1st initial state
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(emptyCompositeStateRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_INITIAL));
		command.execute(null, null);
		EObject newElement = command.getNewElement();

		// test not creation of 2nd Initial pseudostate in region
		runCreationTestWithGetContext(emptyCompositeStateRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_INITIAL, false, IValidationRule.class);

		DestroyElementCommand deleteCommand = new DestroyElementCommand(new DestroyElementRequest(newElement, false));
		deleteCommand.execute(null, null);
	}

	@Test
	public void testCreateDeepHistoryStateInCompositeStateRegion() throws Exception {
		// test creation of deep history pseudostate in region
		runCreationTestWithGetContext(emptyCompositeStateRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_DEEP_HISTORY, true,
				IValidationRule.postEdit(Pseudostate.class, (targetElement, newPseudoState) -> {
					assertThat(newPseudoState.getKind(), equalTo(PseudostateKind.DEEP_HISTORY_LITERAL));
					assertThat(newPseudoState.getAppliedStereotype("UMLRTStateMachines::RTPseudostate"), notNullValue());
					assertThat(newPseudoState.getName(), nullOrEmpty());
				}));
	}

	@Test
	public void testNotCreateTwoDeepHistoryStatesInCompositeStateRegion() throws Exception {
		// Bug 494292: only one deep history pseudo state per region
		// create 1st pseudo state
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(emptyCompositeStateRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_DEEP_HISTORY));
		command.execute(null, null);
		EObject newElement = command.getNewElement();

		// test not creation of 2nd Deep history pseudostate in region
		runCreationTestWithGetContext(emptyCompositeStateRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_DEEP_HISTORY, false, IValidationRule.class);

		DestroyElementCommand deleteCommand = new DestroyElementCommand(new DestroyElementRequest(newElement, false));
		deleteCommand.execute(null, null);
	}

	@Test
	public void testNotCreateDeepHistoryStateInStateMachineRegion() throws Exception {
		// Bug 494292: deep history pseudo state only in composite states
		runCreationTestWithGetContext(emptyRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_DEEP_HISTORY, false, IValidationRule.class);
	}

	@Test
	public void testNotCreateEntryPointInStateMachineRegion() throws Exception {
		// Bug 494292: entry point pseudo state only in composite states (border of)
		runCreationTestWithGetContext(emptyRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_ENTRY_POINT, false, IValidationRule.class);
	}

	@Test
	public void testNotCreateEntryPointInStateMachine() throws Exception {
		// Bug 494292: entry point pseudo state only in composite states (border of)
		runCreationTestWithGetContext(emptyStateMachine, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_ENTRY_POINT, false, IValidationRule.class);
	}

	@Test
	public void testNotCreateEntryPointInCompositeStateRegion() throws Exception {
		// Bug 494292: entry point pseudo state only in composite states (border of)
		runCreationTestWithGetContext(emptyCompositeStateRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_ENTRY_POINT, false, IValidationRule.class);
	}

	@Test
	public void testCreateEntryPointInCompositeState() throws Exception {
		// Bug 494292: entry point pseudo state only in composite states (border of)
		runCreationTestWithGetContext(emptyCompositeState, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_ENTRY_POINT, true,
				IValidationRule.postEdit(Pseudostate.class, (targetElement, newPseudoState) -> {
					assertThat(newPseudoState.getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));
					assertThat(newPseudoState.getAppliedStereotype("UMLRTStateMachines::RTPseudostate"), notNullValue());
					assertThat(newPseudoState.getName(), nullValue());
				}));
	}

	@Test
	public void testCreateSecondEntryPointInCompositeState() throws Exception {
		// create 1st pseudo state
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(emptyCompositeState, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_ENTRY_POINT));
		command.execute(null, null);
		EObject newElement = command.getNewElement();

		// test creation of pseudostate in composite state
		runCreationTestWithGetContext(emptyCompositeState, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_ENTRY_POINT, true,
				IValidationRule.postEdit(Pseudostate.class, (targetElement, newPseudoState) -> {
					assertThat(newPseudoState.getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));
					assertThat(newPseudoState.getAppliedStereotype("UMLRTStateMachines::RTPseudostate"), notNullValue());
					assertThat(newPseudoState.getName(), nullOrEmpty());
				}));

		DestroyElementCommand deleteCommand = new DestroyElementCommand(new DestroyElementRequest(newElement, false));
		deleteCommand.execute(null, null);
	}

	@Test
	public void testNotCreateExitPointInStateMachineRegion() throws Exception {
		// Bug 494292: entry point pseudo state only in composite states (border of)
		runCreationTestWithGetContext(emptyRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_EXIT_POINT, false, IValidationRule.class);
	}

	@Test
	@UIThread
	public void testNotCreateExitPointInStateMachine() throws Exception {
		// Bug 494292: entry point pseudo state only in composite states (border of)
		runCreationTestWithGetContext(emptyStateMachine, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_EXIT_POINT, false, IValidationRule.class);
	}

	@Test
	public void testNotCreateExitPointInCompositeStateRegion() throws Exception {
		// Bug 494292: entry point pseudo state only in composite states (border of)
		runCreationTestWithGetContext(emptyCompositeStateRegion, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_EXIT_POINT, false, IValidationRule.class);
	}

	@Test
	public void testCreateExitPointInCompositeState() throws Exception {
		// Bug 494292: entry point pseudo state only in composite states (border of)
		runCreationTestWithGetContext(emptyCompositeState, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_EXIT_POINT, true,
				IValidationRule.postEdit(Pseudostate.class, (targetElement, newPseudoState) -> {
					assertThat(newPseudoState.getKind(), equalTo(PseudostateKind.EXIT_POINT_LITERAL));
					assertThat(newPseudoState.getAppliedStereotype("UMLRTStateMachines::RTPseudostate"), notNullValue());
					assertThat(newPseudoState.getName(), nullOrEmpty());
				}));
	}

	@Test
	public void testCreateSecondExitPointInCompositeState() throws Exception {
		// create 1st pseudo state
		CreateElementCommand command = new CreateElementCommand(new CreateElementRequest(emptyCompositeState, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_EXIT_POINT));
		command.execute(null, null);
		EObject newElement = command.getNewElement();

		// test creation of pseudostate in composite state
		runCreationTestWithGetContext(emptyCompositeState, UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_EXIT_POINT, true,
				IValidationRule.postEdit(Pseudostate.class, (targetElement, newPseudoState) -> {
					assertThat(newPseudoState.getKind(), equalTo(PseudostateKind.EXIT_POINT_LITERAL));
					assertThat(newPseudoState.getAppliedStereotype("UMLRTStateMachines::RTPseudostate"), notNullValue());
					assertThat(newPseudoState.getName(), nullOrEmpty());
				}));

		DestroyElementCommand deleteCommand = new DestroyElementCommand(new DestroyElementRequest(newElement, false));
		deleteCommand.execute(null, null);
	}

	@Test
	public void testCreateInternalTransition() throws Exception {
		runCreationRelationshipTestWithGetContext(emptyCompositeState, emptyCompositeState, UMLRTElementTypesEnumerator.TRANSITION_INTERNAL, true, 1,
				new IConnectionValidationRule() {

					@Override
					public void validatePostEdition(Element source, Element target, Object newElement) throws Exception {
						assertThat(newElement, instanceOf(Transition.class));
						assertThat(((Transition) newElement).getContainer(), equalTo(emptyCompositeStateRegion));
						assertThat(((Transition) newElement).getKind(), equalTo(TransitionKind.INTERNAL_LITERAL));
						assertThat(((Transition) newElement).getSource(), equalTo(source));
						assertThat(((Transition) newElement).getTarget(), equalTo(target));
					}

					@Override
					public void validatePostUndo(Element source, Element target, Object newElement) throws Exception {
						// source and target should not be set!
						assertThat(newElement, instanceOf(Transition.class));
						assertThat(((Transition) newElement).getSource(), nullValue());
						assertThat(((Transition) newElement).getTarget(), nullValue());
					}
				});

	}

	@Test
	public void testCreateInternalTransitionAsNodeOnCompositeState() throws Exception {
		runCreationTestWithGetContext(emptyCompositeState, UMLRTElementTypesEnumerator.TRANSITION_INTERNAL, true, IValidationRule.postEdit(Transition.class, (targetElement, newTransition) -> {
			assertThat(newTransition.getKind(), equalTo(TransitionKind.INTERNAL_LITERAL));
			assertThat(newTransition.getSource(), equalTo(emptyCompositeState));
			assertThat(newTransition.getTarget(), equalTo(emptyCompositeState));
			assertThat(newTransition.getContainer(), equalTo(emptyCompositeStateRegion));

		}));
	}

	@Test
	public void testCreateInternalTransitionAsNodeOnSimpleState() throws Exception {
		runCreationTestWithGetContext(state, UMLRTElementTypesEnumerator.TRANSITION_INTERNAL, true, 2, IValidationRule.postEdit(Transition.class, (targetElement, newTransition) -> {
			assertThat(newTransition.getKind(), equalTo(TransitionKind.INTERNAL_LITERAL));
			assertThat(newTransition.getSource(), equalTo(state));
			assertThat(newTransition.getTarget(), equalTo(state));
			assertThat(state.isComposite(), is(true));
			// new region should be created, and state should be a composite state
			assertThat(newTransition.getContainer(), equalTo(state.getRegions().get(0)));
		}));

		// checks at the end that the region does not exists anymore
		assertThat(state.getRegions(), isEmpty());
	}

	@Test
	public void testNotCreateUMLEntryPointInCompositeState() throws Exception {
		// Can only create RT connection point
		runCreationTestWithGetContext(emptyCompositeState, UMLDIElementTypes.PSEUDOSTATE_ENTRY_POINT_SHAPE, false, IValidationRule.class);
	}

	@Test
	public void testNotCreateUMLExitPointInCompositeState() throws Exception {
		// Can only create RT connection point
		runCreationTestWithGetContext(emptyCompositeState, UMLDIElementTypes.PSEUDOSTATE_EXIT_POINT_SHAPE, false, IValidationRule.class);
	}

	@Test
	public void testNotCreateUMLChoiceInRegion() throws Exception {
		// Can only create RT pseudostate
		runCreationTestWithGetContext(emptyCompositeStateRegion, UMLDIElementTypes.PSEUDOSTATE_CHOICE_SHAPE, false, IValidationRule.class);
	}

	@Test
	public void testNotCreateUMLJunctionInRegion() throws Exception {
		// Can only create RT pseudostate
		runCreationTestWithGetContext(emptyCompositeStateRegion, UMLDIElementTypes.PSEUDOSTATE_JUNCTION_SHAPE, false, IValidationRule.class);
	}

	@Test
	public void testNotCreateUMLDeepHistoryInCompositeStateRegion() throws Exception {
		// Can only create RT pseudostate
		runCreationTestWithGetContext(emptyCompositeStateRegion, UMLDIElementTypes.PSEUDOSTATE_DEEP_HISTORY_SHAPE, false, IValidationRule.class);
	}

	@Test
	public void testCreateRTChoiceInRegion() throws Exception {
		testCreateRTPseudostateInRegion(UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_CHOICE, PseudostateKind.CHOICE_LITERAL);
	}

	void testCreateRTPseudostateInRegion(IHintedType elementType, PseudostateKind kind) throws Exception {
		// Can only create RT pseudostate
		runCreationTestWithGetContext(emptyCompositeStateRegion, elementType, true,
				IValidationRule.postEdit(Pseudostate.class, (targetElement, newPseudostate) -> {
					assertThat(newPseudostate.getKind(), is(kind));
					assertThat((Object) UMLUtil.getStereotypeApplication(newPseudostate, RTPseudostate.class), notNullValue());
					assertThat(newPseudostate.getName(), nullOrEmpty());
				}));
	}

	@Test
	public void testCreateRTJunctionInRegion() throws Exception {
		testCreateRTPseudostateInRegion(UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_JUNCTION, PseudostateKind.JUNCTION_LITERAL);
	}

	@Test
	public void testCreateRTDeepHistoryInCompositeStateRegion() throws Exception {
		testCreateRTPseudostateInRegion(UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_DEEP_HISTORY, PseudostateKind.DEEP_HISTORY_LITERAL);
	}

	public static class RTPortValidationRule implements IValidationRule {
		@Override
		public void validatePostUndo(Element targetElement, Object[] commandResults) throws Exception {
			// empty
		}

		@Override
		public void validatePostEdition(Element targetElement, Object[] commandResults) throws Exception {
			Assert.assertEquals("comand result should not be empty", 1, commandResults.length);
			Object result = commandResults[0];
			Assert.assertTrue("new element should be an EObject", result instanceof EObject);
			Assert.assertTrue("new element should be an rtPort", ElementTypeUtils.matches((EObject) result, IUMLRTElementTypes.RT_PORT_ID));

			Port newPort = (Port) result;
			assertTrue(newPort.isOrdered());

		}
	}


}

