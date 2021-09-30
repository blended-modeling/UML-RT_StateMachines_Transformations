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

package org.eclipse.papyrusrt.umlrt.core.tests.deletion;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Specific test cases for the deletion of triggers, for example in the case
 * that they have guard constraints (which they do not own).
 */
@PluginResource("resource/inheritance/trigger_reinherit.di")
public class DeleteTriggerTest {

	@ClassRule
	public static final TestRule elementTypes = new ElementTypesRule();

	@Rule
	public final UMLRTModelSetFixture modelSet = new UMLRTModelSetFixture();

	public DeleteTriggerTest() {
		super();
	}

	/**
	 * Verify that the deletion of a trigger also deletes its associated guard constraint.
	 */
	@Test
	public void deleteTriggerWithGuard() {
		UMLRTStateMachine stateMachine = getCapsule("Capsule1").getStateMachine();
		UMLRTTransition transition = stateMachine.getTransition("hasGuard");
		UMLRTTrigger trigger = transition.getTriggers().get(0);

		assumeThat(trigger.getInheritanceKind(), is(UMLRTInheritanceKind.NONE));

		// Delete it
		delete(trigger);


		// Verify the transition and all of its redefinitions
		transition.allRedefinitions().forEach(t -> {
			assertThat(t.getTriggers(), not(hasItem(anything())));
			assertThat(UMLRTExtensionUtil.getUMLRTContents(t.toUML(), UMLPackage.Literals.NAMESPACE__OWNED_RULE),
					not(hasItem(anything())));
		});
	}

	/**
	 * Verify that the deletion of a trigger that has no associated guard constraint
	 * nonetheless ensures that all of its redefinitions' guards (if any) are deleted.
	 */
	@Test
	public void deleteTriggerNoGuard() {
		UMLRTStateMachine stateMachine = getCapsule("Capsule1").getStateMachine();
		UMLRTTransition transition = stateMachine.getTransition("noGuard");
		UMLRTTrigger trigger = transition.getTriggers().get(0);

		assumeThat(trigger.getInheritanceKind(), is(UMLRTInheritanceKind.NONE));

		// Delete it
		delete(trigger);

		// Verify the transition and all of its redefinitions
		transition.allRedefinitions().forEach(t -> {
			assertThat(t.getTriggers(), not(hasItem(anything())));
			assertThat(UMLRTExtensionUtil.getUMLRTContents(t.toUML(), UMLPackage.Literals.NAMESPACE__OWNED_RULE),
					not(hasItem(anything())));
		});
	}

	//
	// Test framework
	//

	UMLRTPackage getRoot() {
		return UMLRTPackage.getInstance(modelSet.getModel());
	}

	UMLRTCapsule getCapsule(String name) {
		return getRoot().getCapsule(name);
	}

	void delete(UMLRTNamedElement element) {
		DestroyElementRequest request = new DestroyElementRequest(modelSet.getEditingDomain(), element.toUML(), false);
		try {
			request.setClientContext(TypeContext.getContext(modelSet.getEditingDomain()));
		} catch (ServiceException e) {
			fail(e.getMessage());
		}

		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(request.getContainer());
		ICommand destroy = edit.getEditCommand(request);
		assertThat(destroy, isExecutable());

		modelSet.execute(destroy);
	}
}
