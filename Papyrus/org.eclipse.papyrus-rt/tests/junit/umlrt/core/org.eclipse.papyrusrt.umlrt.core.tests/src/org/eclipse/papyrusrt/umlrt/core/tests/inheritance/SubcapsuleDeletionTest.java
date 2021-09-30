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

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.junit.utils.DeletionUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLSwitch;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
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
public class SubcapsuleDeletionTest {

	@Rule
	public final TestRule elementTypes = new ElementTypesRule();

	@Rule
	public final UMLRTModelSetFixture model = new UMLRTModelSetFixture();

	@Rule
	public final FixtureElementRule elements = new FixtureElementRule("model");

	@Named("Capsule2")
	Class capsule2;

	@Named("Capsule2::StateMachine")
	StateMachine redefiningStateMachine;

	public SubcapsuleDeletionTest() {
		super();
	}

	@Test
	public void deleteRedefiningStateMachine() {
		ICommand destroy = DeletionUtils.getDestroyCommand(redefiningStateMachine);
		assertThat(destroy, not(isExecutable()));
	}

	@Test
	public void deleteSubclassCapsule() {
		ICommand destroy = DeletionUtils.requireDestroyCommand(capsule2);
		model.execute(destroy);
		assertThat(capsule2.eResource(), nullValue());
	}

	@Test
	public void undoDeleteSubclassCapsule() {
		model.repeat(3, () -> {
			UMLRTInheritanceKind inheritance = UMLRTInheritanceKind.of(redefiningStateMachine);

			ICommand destroy = DeletionUtils.requireDestroyCommand(capsule2);
			model.execute(destroy);

			assertThat(capsule2.eResource(), nullValue());

			model.undo();

			assertThat(capsule2.eResource(), is(model.getModelResource()));

			// And the state machine is restored, too
			assertThat(redefiningStateMachine.getContext(), is(capsule2));
			verifyRedefiningStateMachine(inheritance);
		});
	}

	//
	// Test framework
	//

	void verifyRedefiningStateMachine(UMLRTInheritanceKind expectedInheritance) {
		assertThat(redefiningStateMachine.eResource(), is(model.getModelResource()));
		assertThat(redefiningStateMachine.getContext(), is(capsule2));
		assertThat(UMLRTInheritanceKind.of(redefiningStateMachine), is(expectedInheritance));
		assertThat(redefiningStateMachine, stereotypedAs("RTStateMachine"));

		// Verify that all of the root state machine's contents are represented in
		// the redefining state machine
		Set<Element> allRedefinable = redefiningStateMachine.getExtendedStateMachines().get(0).allOwnedElements().stream()
				.filter(redefinable())
				.collect(Collectors.toSet());

		assertInheritedContents(allRedefinable, redefiningStateMachine);

		assertThat("Some elements not redefined/inherited", allRedefinable, is(Collections.EMPTY_SET));
	}

	Predicate<Element> redefinable() {
		Predicate<Element> result = RedefinableElement.class::isInstance;
		result = result.or(Pseudostate.class::isInstance); // Special case for UML-RT
		return result;
	}

	void assertInheritedContents(Set<? extends Element> allRedefinable, Element owner) {
		final AtomicReference<Consumer<Element>> recursive = new AtomicReference<>();
		recursive.set(e -> assertInheritedContents(allRedefinable, e, recursive.get()));
		assertInheritedContents(allRedefinable, owner, recursive.get());
	}

	@SuppressWarnings("unchecked")
	<E extends Element> void assertInheritedContents(Set<? extends Element> allRedefinable,
			Element owner, Consumer<? super E> assertion) {

		List<EReference> containments = new UMLSwitch<List<EReference>>() {
			@Override
			public List<EReference> caseStateMachine(StateMachine object) {
				return Arrays.asList(UMLPackage.Literals.STATE_MACHINE__REGION);
			}

			@Override
			public List<EReference> caseRegion(Region object) {
				return Arrays.asList(UMLPackage.Literals.REGION__SUBVERTEX,
						UMLPackage.Literals.REGION__TRANSITION);
			}

			@Override
			public List<EReference> caseState(State object) {
				return Arrays.asList(UMLPackage.Literals.STATE__REGION,
						UMLPackage.Literals.STATE__CONNECTION_POINT,
						UMLPackage.Literals.STATE__ENTRY,
						UMLPackage.Literals.STATE__EXIT);
			}

			@Override
			public List<EReference> caseTransition(Transition object) {
				return Arrays.asList(UMLPackage.Literals.TRANSITION__EFFECT);
			}

			@Override
			public List<EReference> defaultCase(org.eclipse.emf.ecore.EObject object) {
				return Collections.emptyList();
			}
		}.doSwitch(owner);

		UMLRTExtensionUtil.getUMLRTContents(owner, containments).stream()
				.filter(Element.class::isInstance).map(Element.class::cast)
				.forEach(e -> {
					assertThat(UMLRTInheritanceKind.of(e), is(UMLRTInheritanceKind.INHERITED));
					assertThat(((InternalEObject) e).eInternalResource(), notNullValue());

					// Check that applied stereotypes were restored
					verifyStereotype(e);

					allRedefinable.remove(UMLRTExtensionUtil.getRootDefinition(e));

					if (assertion != null) {
						assertion.accept((E) e);
					}
				});
	}

	void verifyStereotype(Element e) {
		new UMLSwitch<Void>() {
			@Override
			public Void casePseudostate(Pseudostate object) {
				assertThat(object, stereotypedAs("RTPseudostate"));
				return null;
			}

			@Override
			public Void caseState(State object) {
				assertThat(object, stereotypedAs("RTState"));
				return null;
			}

			@Override
			public Void caseRegion(Region object) {
				assertThat(object, stereotypedAs("RTRegion"));
				return null;
			}

			@Override
			public Void caseConstraint(Constraint object) {
				if ((object.getNamespace() instanceof Transition)
						&& (((Transition) object.getNamespace()).getGuard() != object)) {
					assertThat(e, stereotypedAs("RTGuard"));
				}
				return null;
			}
		}.doSwitch(e);
	}

	static Matcher<Element> stereotypedAs(String name) {
		return new CustomTypeSafeMatcher<Element>("stereotyped as " + name) {
			@Override
			protected boolean matchesSafely(Element item) {
				return item.getAppliedStereotypes().stream()
						.map(NamedElement::getName)
						.filter(Objects::nonNull)
						.anyMatch(name::equals);
			}
		};
	}
}
