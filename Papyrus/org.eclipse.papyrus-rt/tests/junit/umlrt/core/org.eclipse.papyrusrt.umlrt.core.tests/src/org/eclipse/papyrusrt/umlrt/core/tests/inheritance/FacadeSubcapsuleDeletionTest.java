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

package org.eclipse.papyrusrt.umlrt.core.tests.inheritance;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.junit.utils.DeletionUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTModel;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Regression test cases for deletion of redefining state machines in subclass
 * capsules and deletion of the subclass capsule, itself.
 * 
 * @see <a href="http://eclip.se/512872">bug 512872</a>
 */
@PluginResource("resource/inheritance/delete_subcapsule.di")
public class FacadeSubcapsuleDeletionTest {

	@Rule
	public final TestRule elementTypes = new ElementTypesRule();

	@Rule
	public final UMLRTModelSetFixture model = new UMLRTModelSetFixture();

	@Rule
	public final FixtureElementRule elements = new FixtureElementRule("model");

	@Named("Capsule2")
	UMLRTCapsule capsule2;

	@Named("Capsule2::StateMachine")
	UMLRTStateMachine redefiningStateMachine;

	public FacadeSubcapsuleDeletionTest() {
		super();
	}

	/**
	 * This is a counterpoint to the similar test case in the {@link SubcapsuleDeletionTest}
	 * class, which tests specifically the behaviour of the <em>Façade API</em> in undo.
	 */
	@Test
	public void undoDeleteSubclassCapsule() {
		model.repeat(3, () -> {
			UMLRTInheritanceKind inheritance = redefiningStateMachine.getInheritanceKind();

			ICommand destroy = DeletionUtils.requireDestroyCommand(capsule2.toUML());
			model.execute(destroy);

			assertThat(capsule2.getModel(), nullValue());

			model.undo();

			assertThat(capsule2.getModel(), is(UMLRTModel.getInstance(model.getModelResource())));

			// And the state machine is restored, too
			assertThat(redefiningStateMachine.getCapsule(), is(capsule2));
			verifyRedefiningStateMachine(inheritance);
		});
	}

	@Test
	public void undoRedoDeleteSubclassCapsule() {
		UMLRTInheritanceKind inheritance = redefiningStateMachine.getInheritanceKind();

		ICommand destroy = DeletionUtils.requireDestroyCommand(capsule2.toUML());
		model.execute(destroy);

		model.repeat(3, () -> {
			assertThat(capsule2.getModel(), nullValue());

			model.undo();

			assertThat(capsule2.getModel(), is(UMLRTModel.getInstance(model.getModelResource())));

			// And the state machine is restored, too
			assertThat(redefiningStateMachine.getCapsule(), is(capsule2));
			verifyRedefiningStateMachine(inheritance);

			model.redo();
		});
	}

	//
	// Test framework
	//

	void verifyRedefiningStateMachine(UMLRTInheritanceKind expectedInheritance) {
		assertThat(redefiningStateMachine.getModel(), is(UMLRTModel.getInstance(model.getModelResource())));
		assertThat(redefiningStateMachine.getCapsule(), is(capsule2));
		assertThat(redefiningStateMachine.getInheritanceKind(), is(expectedInheritance));

		// Verify that all of the root state machine's contents are represented in
		// the redefining state machine
		Set<UMLRTNamedElement> allRedefinable = redefiningStateMachine.getRedefinedStateMachine().getRedefinableElements().stream()
				.collect(Collectors.toSet());

		assertInheritedContents(allRedefinable, redefiningStateMachine);

		assertThat("Some elements not redefined/inherited", allRedefinable, is(Collections.EMPTY_SET));
	}

	void assertInheritedContents(Set<? extends UMLRTNamedElement> allRedefinable, UMLRTNamedElement owner) {
		final AtomicReference<Consumer<UMLRTNamedElement>> recursive = new AtomicReference<>();
		recursive.set(e -> assertInheritedContents(allRedefinable, e, recursive.get()));
		assertInheritedContents(allRedefinable, owner, recursive.get());
	}

	@SuppressWarnings("unchecked")
	<E extends UMLRTNamedElement> void assertInheritedContents(Set<? extends UMLRTNamedElement> allRedefinable,
			UMLRTNamedElement owner, Consumer<? super E> assertion) {

		owner.getRedefinableElements().forEach(e -> {
			assertThat(e.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
			assertThat(((InternalEObject) e.toUML()).eInternalResource(), notNullValue());

			allRedefinable.remove(e.getRootDefinition());

			if (assertion != null) {
				assertion.accept((E) e);
			}
		});
	}
}
