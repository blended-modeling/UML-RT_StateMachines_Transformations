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

package org.eclipse.papyrusrt.umlrt.uml.tests;

import static java.util.Collections.singletonList;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.isInherited;
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.isRedefined;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.CrossReferenceCheck;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.UMLMatchers;
import org.eclipse.uml2.uml.AnyReceiveEvent;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the transition façade in {@link UMLRTTransition}.
 */
@TestModel("inheritance/statemachines.uml")
@Category({ StateMachineTests.class, FacadeTests.class })
public class TransitionFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public TransitionFacadeTest() {
		super();
	}

	@Test
	public void source() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());

		assertThat(transition.getSource(), instanceOf(UMLRTPseudostate.class));
		UMLRTPseudostate initial = (UMLRTPseudostate) transition.getSource();
		assertThat(initial.toUML(), is(transition.toUML().getSource()));
	}

	@Test
	public void target() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());

		assertThat(transition.getTarget(), instanceOf(UMLRTState.class));
		UMLRTState state1 = (UMLRTState) transition.getTarget();
		assertThat(state1.toUML(), is(transition.toUML().getTarget()));
	}

	@Test
	public void kind() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		// The internal transition is an "incoming" even though it is strictly internal
		UMLRTTransition transition = sm.getVertex("composite").getIncoming("internal");
		assumeThat(transition, notNullValue());

		assertThat(transition.getKind(), is(TransitionKind.INTERNAL_LITERAL));
		assertThat(transition.isInternal(), is(true));

		transition.setKind(TransitionKind.LOCAL_LITERAL);
		assertThat(transition.toUML().getKind(), is(TransitionKind.LOCAL_LITERAL));
		assertThat(transition.isInternal(), is(false));
	}

	@Test
	public void setSource() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());

		transition.setSource(transition.getTarget());
		assertThat(transition.toUML().getSource(), is(transition.toUML().getTarget()));
	}

	@Test
	public void setTarget() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());

		transition.setTarget(transition.getSource());
		assertThat(transition.toUML().getTarget(), is(transition.toUML().getSource()));
	}

	@Test
	public void guard() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("in");
		assumeThat(transition, notNullValue());

		UMLRTGuard guard = transition.getGuard();
		assertThat(guard, notNullValue());
		assertThat(guard.getBodies().get("C++"), is("this.isConsistent()"));

		OpaqueExpression spec = (OpaqueExpression) guard.toUML().getSpecification();

		guard.getBodies().put("C++", "true");
		assertThat(spec.getBodies().get(0), is("true"));

		guard.getBodies().put("OCL", "self.isConsistent");
		assertThat(spec.getBodies().size(), is(2));
		assertThat(spec.getBodies().get(1), is("self.isConsistent"));
		assertThat(spec.getLanguages().size(), is(2));
		assertThat(spec.getLanguages().get(1), is("OCL"));
	}

	@Test
	public void guardUpdate() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("in");
		assumeThat(transition, notNullValue());

		UMLRTGuard guard = transition.getGuard();
		assertThat(guard, notNullValue());
		assertThat(guard.getBodies().get("C++"), is("this.isConsistent()"));

		OpaqueExpression spec = (OpaqueExpression) guard.toUML().getSpecification();

		spec.getLanguages().add("OCL");
		spec.getBodies().add("self.isConsistent");
		assertThat(guard.getBodies().size(), is(2));
		assertThat(guard.getBodies().get("OCL"), is("self.isConsistent"));

		spec.getBodies().set(1, "true");
		assertThat(guard.getBodies().get("OCL"), is("true"));

		spec.getLanguages().set(1, "Java");
		assertThat(guard.getBodies().get("Java"), is("true"));

		spec.getLanguages().remove(1);
		spec.getBodies().remove(1);
		assertThat(guard.getBodies().size(), is(1));
		assertThat(guard.getBodies().keySet(), is(Collections.singleton("C++")));

		// And the other way around
		spec.getBodies().remove(0);
		spec.getLanguages().remove(0);
		assertThat(guard.getBodies().isEmpty(), is(true));
	}

	@Test
	public void triggers() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());

		UMLRTTrigger trigger = transition.getTrigger("greet");
		assertThat(trigger, notNullValue());
		assertThat(trigger.getProtocolMessage(), notNullValue());
		assertThat(trigger.getProtocolMessage().getName(), is("greet"));
		assertThat(trigger.getProtocolMessage().getKind(), is(RTMessageKind.IN));

		assertThat(trigger.getPort(), notNullValue());
		assertThat(trigger.getPort().getName(), is("protocol1"));
	}

	@Test
	public void wildcardTriggers() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());

		UMLRTTrigger trigger = transition.getTrigger("greet");
		assumeThat(trigger, notNullValue());
		assumeThat(trigger.getProtocolMessage(), notNullValue());

		assertThat(trigger.isReceiveAnyMessage(), is(false));

		trigger.setReceiveAnyMessage(true);

		assertThat(trigger.toUML().getEvent(), instanceOf(AnyReceiveEvent.class));
		assertThat(trigger.toUML().getEvent().getNearestPackage(), UMLMatchers.named("Protocol1"));
		assertThat(trigger.getProtocolMessage(), nullValue());

		trigger.setReceiveAnyMessage(false);

		// This one happens to be the first
		assertThat(trigger.getProtocolMessage(), notNullValue());
		assertThat(trigger.getProtocolMessage(), FacadeMatchers.named("greet"));
	}

	@Test
	public void triggerGuard() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());

		UMLRTTrigger trigger = transition.getTrigger("greet");
		assumeThat(trigger, notNullValue());

		UMLRTGuard guard = trigger.getGuard();
		assertThat(guard, notNullValue());

		assertThat(guard.getBodies().get("C++"), is("this.isReady()"));
	}

	@Test
	public void inheritedSource() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());
		assumeThat(transition, isInherited());

		assertThat(transition.getSource(), instanceOf(UMLRTPseudostate.class));
		UMLRTPseudostate initial = (UMLRTPseudostate) transition.getSource();
		assertThat(initial.toUML(), is(transition.toUML().getSource()));
		assertThat("Source does not resolve to inherited vertex", initial, isInherited());
	}

	@Test
	public void inheritedTarget() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());
		assumeThat(transition, isInherited());

		assertThat(transition.getTarget(), instanceOf(UMLRTState.class));
		UMLRTState state1 = (UMLRTState) transition.getTarget();
		assertThat(state1.toUML(), is(transition.toUML().getTarget()));
		assertThat("Target does not resolve to inherited vertex", state1, isInherited());
	}

	@Test
	public void inheritedKind() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		// The internal transition is an "incoming" even though it is strictly internal
		UMLRTTransition transition = sm.getVertex("composite").getIncoming("internal");
		assumeThat(transition, notNullValue());
		assumeThat(transition, isInherited());

		assertThat(transition.getKind(), is(TransitionKind.INTERNAL_LITERAL));
		assertThat(transition.isInternal(), is(true));
		assertThat(transition.toUML().eIsSet(UMLPackage.Literals.TRANSITION__KIND), is(false));
	}

	@Test
	public void inheritedGuard() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("in");
		assumeThat(transition, notNullValue());
		assumeThat(transition, isInherited());

		UMLRTGuard guard = transition.getGuard();
		assertThat(guard, notNullValue());
		assertThat(guard, isInherited());
		assertThat(guard.getBodies().get("C++"), is("this.isConsistent()"));
		assertThat("Guard was reified by façade EMap initialization", guard, isInherited());
	}

	@Test
	public void inheritedTrigger() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());
		assumeThat(transition, isInherited());

		UMLRTTrigger trigger = transition.getTrigger("greet");
		assertThat(trigger, notNullValue());
		assertThat(trigger, isInherited());
		assertThat(trigger.getProtocolMessage(), notNullValue());
		assertThat(trigger.getProtocolMessage().getName(), is("greet"));
		assertThat(trigger.getProtocolMessage().getKind(), is(RTMessageKind.IN));

		assertThat(trigger.getPort(), notNullValue());
		assertThat(trigger.getPort().getName(), is("protocol1"));
	}

	@Test
	public void inheritedTriggerGuard() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());
		assumeThat(transition, isInherited());

		UMLRTTrigger trigger = transition.getTrigger("greet");
		assumeThat(trigger, notNullValue());
		assumeThat(trigger, isInherited());

		UMLRTGuard guard = trigger.getGuard();
		assertThat(guard, notNullValue());
		assertThat(guard, isInherited());

		assertThat(guard.getBodies().get("C++"), is("this.isReady()"));
	}

	@Test
	public void effect() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("nestedIn");
		assumeThat(transition, notNullValue());

		UMLRTOpaqueBehavior effect = transition.getEffect();
		assertThat(effect, notNullValue());
		assertThat(effect.getBodies().get("C++"), is("this.doTheThing();"));

		OpaqueBehavior uml = effect.toUML();

		effect.getBodies().put("C++", "true");
		assertThat(uml.getBodies().get(0), is("true"));

		effect.getBodies().put("OCL", "self.imperativeOcl?()");
		assertThat(uml.getBodies().size(), is(2));
		assertThat(uml.getBodies().get(1), is("self.imperativeOcl?()"));
		assertThat(uml.getLanguages().size(), is(2));
		assertThat(uml.getLanguages().get(1), is("OCL"));
	}

	@Test
	public void effectUpdate() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("nestedIn");
		assumeThat(transition, notNullValue());

		UMLRTOpaqueBehavior effect = transition.getEffect();
		assertThat(effect, notNullValue());
		assertThat(effect.getBodies().get("C++"), is("this.doTheThing();"));

		OpaqueBehavior uml = effect.toUML();

		uml.getLanguages().add("OCL");
		uml.getBodies().add("self.imperativeOcl?()");
		assertThat(effect.getBodies().size(), is(2));
		assertThat(effect.getBodies().get("OCL"), is("self.imperativeOcl?()"));

		uml.getBodies().set(1, "true");
		assertThat(effect.getBodies().get("OCL"), is("true"));

		uml.getLanguages().set(1, "Java");
		assertThat(effect.getBodies().get("Java"), is("true"));

		uml.getLanguages().remove(1);
		uml.getBodies().remove(1);
		assertThat(effect.getBodies().size(), is(1));
		assertThat(effect.getBodies().keySet(), is(Collections.singleton("C++")));

		// And the other way around
		uml.getBodies().remove(0);
		uml.getLanguages().remove(0);
		assertThat(effect.getBodies().isEmpty(), is(true));
	}

	@Test
	public void inheritedEffect() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("nestedIn");
		assumeThat(transition, notNullValue());
		assumeThat(transition, isInherited());

		UMLRTOpaqueBehavior effect = transition.getEffect();
		assertThat(effect, notNullValue());
		assertThat(effect, isInherited());
		assertThat(effect.getBodies().get("C++"), is("this.doTheThing();"));
		assertThat("Effect was reified by façade EMap initialization", effect, isInherited());
	}

	@Test
	public void redefinedEffect() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("nestedIn");
		assumeThat(transition, notNullValue());
		assumeThat(transition, isInherited());

		UMLRTOpaqueBehavior effect = transition.getEffect();
		assertThat(effect, isInherited());
		assertThat(effect, notNullValue());
		assertThat(effect.getBodies().get("C++"), is("this.doTheThing();"));
		assertThat("Effect was reified by façade EMap initialization", effect, isInherited());

		effect.getBodies().put("C++", "this.idle();");

		assertThat(effect.toUML().getBodies(), is(Collections.singletonList("this.idle();")));
		assertThat("Effect was not reified by façade EMap change", effect, isRedefined());
		assertThat("Transition was not reified by façade EMap change", transition, isRedefined());
	}

	@Test
	public void createGuard() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("out");
		assumeThat(transition, notNullValue());
		assumeThat(transition.getGuard(), nullValue());

		UMLRTGuard guard = transition.createGuard("C++", "this.isOK()");

		assertThat(guard, notNullValue());
		assertThat(guard.getBodies().get("C++"), is("this.isOK()"));

		OpaqueExpression spec = (OpaqueExpression) guard.toUML().getSpecification();
		assertThat(spec.getLanguages(), is(singletonList("C++")));
		assertThat(spec.getBodies(), is(singletonList("this.isOK()")));
	}

	@Test
	public void createEffect() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("out");
		assumeThat(transition, notNullValue());
		assumeThat(transition.getEffect(), nullValue());

		UMLRTOpaqueBehavior effect = transition.createEffect("C++", "this.doIt()");

		assertThat(effect, notNullValue());
		assertThat(effect.getBodies().get("C++"), is("this.doIt()"));

		OpaqueBehavior uml = (OpaqueBehavior) transition.toUML().getEffect();
		assertThat(uml.getLanguages(), is(singletonList("C++")));
		assertThat(uml.getBodies(), is(singletonList("this.doIt()")));

		assertThat("Effect is re-entrant", uml.isReentrant(), is(false));
	}

	@Test
	public void setGuard() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("in");
		assumeThat(transition, notNullValue());

		UMLRTGuard guard = transition.getGuard();
		assumeThat(guard, notNullValue());

		UMLRTTransition other = sm.getTransition("Initial");
		other.setGuard(guard);

		assertThat(guard.getTransition(), is(other));
		assertThat(guard.toUML().getContext(), is(other.toUML()));
		assertThat(other.getGuard(), is(guard));
		assertThat(guard.toUML().getConstrainedElements(), not(hasItem(instanceOf(Trigger.class))));
	}

	@Test
	public void setGuardNull() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("in");
		assumeThat(transition, notNullValue());

		UMLRTGuard guard = transition.getGuard();
		assumeThat(guard, notNullValue());

		transition.setGuard(null);

		assertThat(guard.getTransition(), nullValue());
		assertThat(guard.toUML().getContext(), nullValue());
		assertThat(transition.getGuard(), nullValue());
		assertThat(guard.toUML().getConstrainedElements(), not(hasItem(instanceOf(Trigger.class))));
	}

	@Test
	public void setTriggerGuard() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());

		UMLRTTrigger trigger = transition.getTrigger("greet");
		assumeThat(trigger, notNullValue());
		UMLRTGuard guard = trigger.getGuard();
		assumeThat(guard, notNullValue());

		UMLRTTransition otherTrans = sm.getTransition("out");
		UMLRTTrigger other = otherTrans.createTrigger(trigger.getProtocolMessage(), trigger.getPort());
		other.setGuard(guard);

		assertThat(trigger.getGuard(), nullValue());
		assertThat(other.getGuard(), is(guard));
		assertThat(guard.getTrigger(), is(other));
		assertThat(guard.toUML().getContext(), is(otherTrans.toUML()));
		assertThat(guard.toUML().getConstrainedElements(), hasItem(other.toUML()));
		assertThat(guard.toUML().getConstrainedElements(), not(hasItem(trigger.toUML())));
	}

	@Test
	@CrossReferenceCheck(false)
	public void setTriggerGuardNull() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());

		UMLRTTrigger trigger = transition.getTrigger("greet");
		assumeThat(trigger, notNullValue());
		UMLRTGuard guard = trigger.getGuard();
		assumeThat(guard, notNullValue());

		// This leaves a dangling stereotype that would fail the cross-reference check
		trigger.setGuard(null);

		assertThat(trigger.getGuard(), nullValue());
		assertThat(guard.getTrigger(), nullValue());
		assertThat(guard.toUML().getContext(), nullValue());
		assertThat(guard.toUML().getConstrainedElements(), not(hasItem(trigger.toUML())));
	}

	@Test
	public void redefinedGuardUndoRedo() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		testRedefinedCodeUndoRedo(sm.getTransition("in"), UMLRTTransition::getGuard,
				UMLRTGuard::getBodies, "C++", "this.isConsistent()", "this.isOK()");
	}

	<O extends UMLRTNamedElement, T extends UMLRTNamedElement> void testRedefinedCodeUndoRedo(O owner,
			Function<? super O, ? extends T> accessor,
			Function<? super T, Map<String, String>> bodiesAccessor,
			String lang, String oldBody, String newBody) {

		// Sanity
		assumeThat(owner, notNullValue());
		assumeThat(owner, isInherited());

		// Get the owned element
		T code = accessor.apply(owner);
		assumeThat(code, notNullValue());
		assumeThat(code, isInherited());

		// Get the bodies
		Map<String, String> bodies = bodiesAccessor.apply(code);
		assumeThat(bodies.get(lang), is(oldBody));

		// Redefine the code
		ChangeDescription change = fixture.record(() -> bodies.put(lang, newBody));

		assertThat(code, isRedefined());
		assertThat(bodies.get(lang), is(newBody));

		// Undo
		change = fixture.undo(change);

		assertThat(accessor.apply(owner), is(code));
		assertThat(code, isInherited());
		assertThat(bodies.get(lang), is(oldBody));

		// Redo
		change = fixture.undo(change);

		assertThat(accessor.apply(owner), is(code));
		assertThat(code, isRedefined());
		assertThat(bodies.get(lang), is(newBody));
	}

	@Test
	public void redefinedEffectUndoRedo() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		testRedefinedCodeUndoRedo(sm.getTransition("nestedIn"), UMLRTTransition::getEffect,
				UMLRTOpaqueBehavior::getBodies, "C++", "this.doTheThing();", "pass();");
	}

	@Test
	public void redefinedTriggerGuardUndoRedo() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat("No initial transition", transition, notNullValue());
		assumeThat(transition, isInherited());

		testRedefinedCodeUndoRedo(transition.getTrigger("greet"), UMLRTTrigger::getGuard,
				UMLRTGuard::getBodies, "C++", "this.isReady()", "this.isOK()");
	}

	@Test
	public void redefinedTriggerGuard() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTTransition transition = sm.getTransition("Initial");
		assumeThat(transition, notNullValue());
		assumeThat(transition, isInherited());

		UMLRTTrigger trigger = transition.getTrigger("greet");
		assumeThat(trigger, notNullValue());
		assumeThat(trigger, isInherited());

		UMLRTGuard guard = trigger.getGuard();
		assumeThat(guard, notNullValue());
		assumeThat(guard, isInherited());

		ChangeDescription change = fixture.record(() -> guard.getBodies().put("C++", "this.isOK()"));

		assertThat(guard, isRedefined());
		assertThat("Trigger not implicitly redefined", trigger, isRedefined());

		// Undo
		fixture.undo(change);
		assertThat("Trigger still implicitly redefined", trigger, isInherited());

		// Redo
		fixture.undo(change);
		assertThat("Trigger not implicitly redefined", trigger, isRedefined());
	}

	@Test
	public void allRedefinitions() {
		UMLRTStateMachine rootSM = getRootCapsuleBehavior();
		UMLRTStateMachine subSM = getSubcapsuleBehavior();
		UMLRTStateMachine subsubSM = getSubsubcapsuleBehavior();

		UMLRTTransition rootTransition = getTransition(rootSM, "in");
		UMLRTTransition subTransition = getTransition(subSM, "in");
		UMLRTTransition subsubTransition = getTransition(subsubSM, "in");

		List<UMLRTTransition> expected = Arrays.asList(rootTransition, subTransition, subsubTransition);
		List<? extends UMLRTTransition> actual = rootTransition.allRedefinitions().collect(Collectors.toList());
		// There may be other redefinitions, too. Important to keep the order
		actual.retainAll(expected);
		assertThat(actual, is(expected));
	}

	//
	// Test framework
	//

	UMLRTStateMachine getRootCapsuleBehavior() {
		return fixture.getRoot().getCapsule("RootCapsule").getStateMachine();
	}

	UMLRTStateMachine getSubcapsuleBehavior() {
		return fixture.getRoot().getCapsule("Subcapsule").getStateMachine();
	}

	UMLRTStateMachine getSubsubcapsuleBehavior() {
		return fixture.getRoot().getCapsule("Subsubcapsule").createStateMachine();
	}

	UMLRTTransition getTransition(UMLRTStateMachine sm, String transitionName) {
		UMLRTTransition result = sm.getTransition("in");
		assumeThat("Transition not found", result, notNullValue());

		return result;
	}

}
