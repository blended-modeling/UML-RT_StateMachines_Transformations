/*******************************************************************************
 * Copyright (c) 2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *  Young-Soo Roh - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.tests.types.advice;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import javax.inject.Named;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test suite for default kind of transition kind.
 */
@PluginResource("/resource/TestModel.di")
public class TransitionDefaultKindTest extends AbstractPapyrusTest {
	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();

	@Rule
	public HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	@Rule
	public FixtureElementRule fixtureRule = new FixtureElementRule();

	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region")
	private Region region;

	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1")
	private State state1;

	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1")
	private Region region1;

	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::entryPoint")
	private Vertex entryPoint;

	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::exitPoint")
	private Vertex exitPoint;

	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::SubState1")
	private Vertex subState1;

	@Named("TestModel::CapsuleWithCompositeState::StateMachine::Region::State1::Region1::SubState2")
	private Vertex subState2;

	/**
	 * Initializes me.
	 */
	public TransitionDefaultKindTest() {
		super();
	}

	/**
	 * Verify the default value of local transition.
	 */
	@Test
	public void entryPointToSubStateDefaultKindTest() throws Exception {
		Transition transition = createTransition(region, entryPoint, subState1);
		assertThat("Composite entry point to substate transition kind must be set to local", transition.getKind(), equalTo(TransitionKind.LOCAL_LITERAL));
	}


	/**
	 * Verify the default transition kind is set to External for simple to containing composite state.
	 */
	@Test
	public void subStateToExitPointDefaultKindTest() throws Exception {

		Transition transition = createTransition(region1, subState1, exitPoint);
		assertThat("Sub state to exit point of contaning composite transition kind must be set to external", transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));
	}

	/**
	 * Verify the default transition kind is set to External bewteen two simple states.
	 */
	@Test
	public void betweenTwoSimpleStatesDefaultKindTest() throws Exception {
		Transition transition = createTransition(region1, subState1, subState2);
		assertThat("Transition kind of two simple states must be set to external", transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));
	}

	/**
	 * Verify the default transition kind is set to External for self transition loop.
	 */
	@Test
	public void externalSelfTransitonDefaultKindTest() throws Exception {
		Transition transition = createTransition(region, exitPoint, entryPoint);
		assertThat("External self transition kind must be set to external", transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));
	}

	/**
	 * Create relationship command.
	 * 
	 * @param editingDomain
	 * @param owner
	 * @param hintedType
	 * @param source
	 * @param target
	 * @return
	 */
	public static ICommand getCreateRelationshipCommand(TransactionalEditingDomain editingDomain, EObject owner, IHintedType hintedType, EObject source, EObject target) {
		TransactionalEditingDomain transactionalEditingDomain = TransactionUtil.getEditingDomain(owner);
		CreateRelationshipRequest request = new CreateRelationshipRequest(editingDomain, source, target, hintedType);
		final EObject targetContext = ElementEditServiceUtils.getTargetFromContext(transactionalEditingDomain, owner,
				request);

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(targetContext);
		if (provider == null) {
			return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;
		}

		ICommand createGMFCommand = provider.getEditCommand(new CreateRelationshipRequest(editingDomain, owner, source, target, hintedType, UMLPackage.Literals.REGION__TRANSITION));
		return createGMFCommand;
	}
	//
	// Test framework
	//

	/**
	 * 
	 * @param owner
	 * @param source
	 * @param target
	 * @return
	 */
	Transition createTransition(EObject owner, final Vertex source, final Vertex target) {


		ICommand createCmd = getCreateRelationshipCommand(modelSet.getEditingDomain(), owner, UMLElementTypes.TRANSITION, source, target);
		modelSet.execute(createCmd);

		Object returnValue = createCmd.getCommandResult().getReturnValue();
		if (returnValue instanceof List) {
			returnValue = ((List) returnValue).get(0);
		}
		Transition result = (Transition) returnValue;
		assertThat("Transition creation failed", result, notNullValue());
		return result;
	}
}
