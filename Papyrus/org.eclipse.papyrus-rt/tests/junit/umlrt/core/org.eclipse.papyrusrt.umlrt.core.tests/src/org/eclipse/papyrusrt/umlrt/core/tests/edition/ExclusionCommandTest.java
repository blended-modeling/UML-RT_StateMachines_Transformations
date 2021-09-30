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

package org.eclipse.papyrusrt.umlrt.core.tests.edition;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.core.commands.ExclusionCommand;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.NamedElement;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.google.common.collect.Lists;

/**
 * Test cases for the {@link ExclusionCommand}, including edit-helper advice for
 * exclusion/reinheritance dependencies.
 */
@PluginResource("resource/inheritance/exclusions.di")
public class ExclusionCommandTest {

	@ClassRule
	public static final TestRule elementTypes = new ElementTypesRule();

	@Rule
	public final UMLRTModelSetFixture modelSet = new UMLRTModelSetFixture();

	public ExclusionCommandTest() {
		super();
	}

	@Test
	public void excludeConnector() {
		UMLRTConnector connector = getCapsule().getConnector("RTConnector1");
		UMLRTPort source = connector.getSource();
		UMLRTCapsulePart target = connector.getTargetPartWithPort();

		exclude(connector);

		assertThat(connector, isExcluded());
		assertThat(source, notExcluded());
		assertThat(target, notExcluded());
	}

	@Test
	public void excludePort() {
		UMLRTConnector connector = getCapsule().getConnector("RTConnector1");
		UMLRTPort source = connector.getSource();
		UMLRTCapsulePart target = connector.getTargetPartWithPort();

		exclude(source);

		assertThat(source, isExcluded());
		assertThat(connector, isExcluded());
		assertThat(target, notExcluded());
	}

	@Test
	public void excludeCapsulePart() {
		UMLRTConnector connector = getCapsule().getConnector("RTConnector1");
		UMLRTPort source = connector.getSource();
		UMLRTCapsulePart target = connector.getTargetPartWithPort();

		exclude(target);

		assertThat(target, isExcluded());
		assertThat(connector, isExcluded());
		assertThat(source, notExcluded());
	}

	@Test
	public void excludeProtocolMessage() {
		UMLRTProtocolMessage message = getProtocol().getMessage("greet");

		exclude(message);

		assertThat(message, isExcluded());
	}

	@Test
	public void reinheritConnector() {
		UMLRTConnector connector = getCapsule().getConnector("RTConnector1");
		UMLRTPort source = connector.getSource();
		UMLRTCapsulePart target = connector.getTargetPartWithPort();

		exclude(connector);

		assumeThat(connector, isExcluded());

		reinherit(connector);

		assertThat(connector, notExcluded());
		assertThat(source, notExcluded());
		assertThat(target, notExcluded());
	}

	@Test
	public void reinheritConnectorWithDependents() {
		UMLRTConnector connector = getCapsule().getConnector("RTConnector1");
		UMLRTPort source = connector.getSource();
		UMLRTCapsulePart target = connector.getTargetPartWithPort();

		exclude(connector);

		assumeThat(connector, isExcluded());

		exclude(source, target);

		assumeThat(Arrays.asList(source, target), everyItem(isExcluded()));

		reinherit(connector);

		assertThat(connector, notExcluded());
		assertThat(source, notExcluded()); // This is required
		assertThat(target, notExcluded()); // This is required
	}

	@Test
	public void reinheritPort() {
		UMLRTConnector connector = getCapsule().getConnector("RTConnector1");
		UMLRTPort source = connector.getSource();
		UMLRTCapsulePart target = connector.getTargetPartWithPort();

		exclude(source);

		assumeThat(source, isExcluded());

		reinherit(source);

		assertThat(source, notExcluded());
		assertThat(connector, isExcluded()); // This is not required
		assertThat(target, notExcluded());
	}

	@Test
	public void reinheritCapsulePart() {
		UMLRTConnector connector = getCapsule().getConnector("RTConnector1");
		UMLRTPort source = connector.getSource();
		UMLRTCapsulePart target = connector.getTargetPartWithPort();

		exclude(target);

		assumeThat(target, isExcluded());

		reinherit(target);

		assertThat(target, notExcluded());
		assertThat(connector, isExcluded()); // This is not required
		assertThat(source, notExcluded());
	}

	@Test
	public void reinheritProtocolMessage() {
		UMLRTProtocolMessage message = getProtocol().getMessage("greet");

		exclude(message);

		assumeThat(message, isExcluded());

		reinherit(message);

		assertThat(message, notExcluded());
	}

	@PluginResource("resource/inheritance/statemachines.di")
	@Test
	public void excludedStateNotInherited() {
		UMLRTStateMachine parentSM = getCapsule("Subcapsule").getStateMachine();
		UMLRTStateMachine extendingSM = modelSet.execute(() -> getCapsule("Subsubcapsule").createStateMachine());

		UMLRTState parent = (UMLRTState) parentSM.getVertex("State1");
		assumeThat(parent, notNullValue());

		// Exclude the parent state
		exclude(parent);
		assertThat("Parent state not excluded from inheriting context", parent, isExcluded());

		UMLRTState redefining = (UMLRTState) extendingSM.getVertex("State1");
		assertThat("Redefining state not excluded from inheriting context", redefining, nullValue());
		assertThat("Inheriting container is redefined", UMLRTInheritanceKind.of(extendingSM.toRegion()),
				is(UMLRTInheritanceKind.INHERITED));
	}

	@PluginResource("resource/inheritance/statemachines.di")
	@Test
	public void excludedStateRedefinitionAlreadyExcluded() {
		UMLRTStateMachine parentSM = getCapsule("Subcapsule").getStateMachine();
		UMLRTStateMachine extendingSM = modelSet.execute(() -> getCapsule("Subsubcapsule").createStateMachine());

		UMLRTState parent = (UMLRTState) parentSM.getVertex("State1");
		assumeThat(parent, notNullValue());

		UMLRTState redefining = (UMLRTState) extendingSM.getVertex("State1");
		assumeThat(redefining, notNullValue());

		// Exclude the redefining state
		exclude(redefining);
		assumeThat("Redefining state not excluded from inheriting context", redefining, isExcluded());

		// Exclude the parent state
		exclude(parent);
		assertThat("Parent state not excluded from inheriting context", parent, isExcluded());

		redefining = (UMLRTState) extendingSM.getVertex("State1");
		assertThat("Redefining state re-inherited in inheriting context", redefining, nullValue());
		assertThat("Container is not redefined", UMLRTInheritanceKind.of(extendingSM.toRegion()),
				is(UMLRTInheritanceKind.REDEFINED));
	}

	/**
	 * Verify that the re-inheritance of a composite state that redefines a
	 * simple state correctly updates transitions to account for the removal
	 * of connection points.
	 */
	@PluginResource("resource/inheritance/composite_state.di")
	@Test
	public void reinheritCompositeStateToSimpleState() {
		UMLRTStateMachine stateMachine = getCapsule("Capsule2").getStateMachine();

		// Transitions incoming and outgoing the composite state via connection points
		UMLRTTransition incoming = stateMachine.getVertex("State1").getOutgoings().get(0);
		UMLRTTransition outgoing = stateMachine.getVertex("State3").getIncomings().get(0);

		// And the composite state
		UMLRTState state = (UMLRTState) stateMachine.getVertex("State2");

		// Reinherit it
		reinherit(state);

		assertThat("State should be simple", state.isSimple(), is(true));

		// The connection points are gone, so the transitions now have to connect to the state
		assertThat(incoming.getTarget(), is(state));
		assertThat(outgoing.getSource(), is(state));
	}

	/**
	 * Verify that the re-inheritance of a a trigger that is implicitly redefined
	 * by redefinition of its guard correctly re-inherits the guard.
	 */
	@PluginResource("resource/inheritance/trigger_reinherit.di")
	@Test
	public void reinheritTriggerWithRedefiningGuard() {
		UMLRTStateMachine stateMachine = getCapsule("Capsule2").getStateMachine();

		UMLRTTransition transition = stateMachine.getTransition("hasGuard");
		UMLRTTrigger trigger = transition.getTriggers().get(0);

		assumeThat(trigger.getInheritanceKind(), is(UMLRTInheritanceKind.REDEFINED));

		// Reinherit it
		reinherit(trigger);

		assertThat(trigger.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
		assertThat(trigger.getGuard(), notNullValue());
		assertThat(trigger.getGuard().getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
		assertThat(trigger.getGuard().getBodies().get("C++"), is("isReady()"));
	}

	/**
	 * Verify that the re-inheritance of a trigger that is implicitly redefined
	 * by addition of a guard where the parent definition had none
	 * correctly eliminates the guard.
	 */
	@PluginResource("resource/inheritance/trigger_reinherit.di")
	@Test
	public void reinheritTriggerWithLocalGuard() {
		UMLRTStateMachine stateMachine = getCapsule("Capsule2").getStateMachine();

		UMLRTTransition transition = stateMachine.getTransition("noGuard");
		UMLRTTrigger trigger = transition.getTriggers().get(0);

		assumeThat(trigger.getInheritanceKind(), is(UMLRTInheritanceKind.REDEFINED));
		assumeThat(trigger.getGuard(), notNullValue());

		// Reinherit it
		reinherit(trigger);

		assertThat(trigger.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
		assertThat(trigger.getGuard(), nullValue());
		assertThat(trigger.getTransition().toUML().getOwnedRules(), not(hasItem(anything())));
	}

	/**
	 * Verify that the re-inheritance of a composite state that redefines a
	 * simple state re-inherits incoming and outgoing transitions, also, that
	 * become indistinguishable from the parent definitions.
	 */
	@PluginResource("resource/inheritance/composite_state.di")
	@Test
	public void reinheritCompositeStateImpliesTransitions() {
		UMLRTStateMachine stateMachine = getCapsule("Capsule2").getStateMachine();

		// Transitions incoming and outgoing the composite state via connection points
		UMLRTTransition incoming = stateMachine.getVertex("State1").getOutgoings().get(0);
		UMLRTTransition outgoing = stateMachine.getVertex("State3").getIncomings().get(0);

		// And the composite state
		UMLRTState state = (UMLRTState) stateMachine.getVertex("State2");

		// Reinherit it
		reinherit(state);

		// The transitions also should be re-inherited
		assertThat(incoming.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
		assertThat(outgoing.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
	}

	/**
	 * Verify that the re-inheritance of a composite state that redefines a
	 * simple state does not re-inherit incoming and outgoing transitions that
	 * have other redefining changes besides just connecting via connection
	 * points.
	 */
	@PluginResource("resource/inheritance/composite_state.di")
	@Test
	public void reinheritCompositeStateWithOtherwiseRedefinedTransitions() {
		UMLRTStateMachine stateMachine = getCapsule("Capsule4").getStateMachine();

		// Transitions incoming and outgoing the composite state via connection points
		UMLRTTransition incoming = stateMachine.getVertex("State1").getOutgoings().get(0);
		UMLRTTransition outgoing = stateMachine.getVertex("State3").getIncomings().get(0);

		// And the composite state
		UMLRTState state = (UMLRTState) stateMachine.getVertex("State2");

		// Reinherit it
		reinherit(state);

		// The transitions must not be re-inherited as they are deliberately redefined
		// in their target end ('incoming' transition) and source end ('outgoing').
		// In the parent state machine they target and source, respectively, State4,
		// but in the redefining state machine they take the place of excluded transitions
		// that in the parent state machine connect to State2.
		assertThat(incoming.getInheritanceKind(), is(UMLRTInheritanceKind.REDEFINED));
		assertThat(outgoing.getInheritanceKind(), is(UMLRTInheritanceKind.REDEFINED));

		// They still connect to State2, not State4 as in the superclass
		assertThat(incoming.getTarget(), notNullValue());
		assertThat(incoming.getTarget().getName(), is("State2"));
		assertThat(outgoing.getSource(), notNullValue());
		assertThat(outgoing.getSource().getName(), is("State2"));
	}

	/**
	 * Verify that the re-inheritance of a composite state that redefines a
	 * composite state deletes local transitions on local connection points
	 * because connection points are required for composite states and
	 * deleting them therefore must delete the local transitions that they
	 * connect.
	 */
	@PluginResource("resource/inheritance/composite_state.di")
	@Test
	public void reinheritCompositeStateWithLocalTransitions() {
		UMLRTStateMachine stateMachine = getCapsule("Capsule6").getStateMachine();

		// Local transitions incoming and outgoing the composite state via local connection points
		UMLRTTransition incoming = stateMachine.getVertex("State3").getOutgoings().get(0);
		UMLRTTransition outgoing = stateMachine.getVertex("State5").getIncomings().get(0);

		// Inherited transitions that should not be changed in any way
		UMLRTTransition inhIncoming = stateMachine.getVertex("State1").getOutgoings().get(0);
		UMLRTInheritanceKind inhIncomingKind = inhIncoming.getInheritanceKind();
		UMLRTTransition inhOutgoing = stateMachine.getVertex("State4").getIncomings().get(0);
		UMLRTInheritanceKind inhOutgoingKind = inhOutgoing.getInheritanceKind();

		// And the composite state
		UMLRTState state = (UMLRTState) stateMachine.getVertex("State2");

		// Reinherit it
		reinherit(state);

		// The inherited transitions are unchanged
		assertThat(inhIncoming.getInheritanceKind(), is(inhIncomingKind));
		assertThat(inhIncoming.getSource().getName(), is("State1"));
		assertThat(inhIncoming.getTarget(), instanceOf(UMLRTConnectionPoint.class));
		assertThat(inhOutgoing.getInheritanceKind(), is(inhOutgoingKind));
		assertThat(inhOutgoing.getSource(), instanceOf(UMLRTConnectionPoint.class));
		assertThat(inhOutgoing.getTarget().getName(), is("State4"));

		// These transitions are locally defined but the re-inheritance of the composite
		// state deletes the connection-points that they connect to, so they also
		// must be deleted
		assertThat(incoming.getModel(), nullValue());
		assertThat(outgoing.getModel(), nullValue());

		assertThat(stateMachine.getVertex("State3").getOutgoings(), not(hasItem(incoming)));
		assertThat(stateMachine.getVertex("State5").getIncomings(), not(hasItem(outgoing)));
	}

	/**
	 * Verify that the re-inheritance of a composite state that redefines a
	 * composite state re-inherits the source/target references of redefined transitions
	 * that connect on local connection points because connection points are required for
	 * composite states and so the inherited transitions cannot simply connect directly
	 * to the inherited state.
	 */
	@PluginResource("resource/inheritance/composite_state.di")
	@Test
	public void reinheritCompositeStateWithInheritedTransitions() {
		UMLRTStateMachine stateMachine = getCapsule("Capsule8").getStateMachine();

		// Inherited transitions incoming and outgoing the composite state via local connection points
		UMLRTTransition incoming = stateMachine.getVertex("State3").getOutgoings().get(0);
		UMLRTTransition outgoing = stateMachine.getVertex("State4").getIncomings().get(0);

		// Inherited transitions that should not be changed in any way
		UMLRTTransition inhIncoming = stateMachine.getVertex("State1").getOutgoings().get(0);
		UMLRTInheritanceKind inhIncomingKind = inhIncoming.getInheritanceKind();
		UMLRTTransition inhOutgoing = stateMachine.getVertex("State5").getIncomings().get(0);
		UMLRTInheritanceKind inhOutgoingKind = inhOutgoing.getInheritanceKind();

		// And the composite state
		UMLRTState state = (UMLRTState) stateMachine.getVertex("State2");

		// Reinherit it
		reinherit(state);

		// These inherited transitions are unchanged
		assertThat(inhIncoming.getInheritanceKind(), is(inhIncomingKind));
		assertThat(inhIncoming.getSource().getName(), is("State1"));
		assertThat(inhIncoming.getTarget(), instanceOf(UMLRTConnectionPoint.class));
		assertThat(inhOutgoing.getInheritanceKind(), is(inhOutgoingKind));
		assertThat(inhOutgoing.getSource(), instanceOf(UMLRTConnectionPoint.class));
		assertThat(inhOutgoing.getTarget().getName(), is("State5"));

		// These transitions are inherited but the re-inheritance of the composite
		// state deletes the connection-points that they connect to, so they must
		// re-inherit the vertex ends that connected to those connection-points.
		// Moreover, as it happens to be that these transitions now would be just
		// like the transitions that they redefined, they are actually re-inherited
		assertThat(incoming.getModel(), notNullValue());
		assertThat(outgoing.getModel(), notNullValue());

		assertThat(incoming.getTarget().getName(), is("State1"));
		assertThat(outgoing.getSource().getName(), is("State1"));
		assertThat(incoming.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
		assertThat(outgoing.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));
	}

	/**
	 * Verify that the exclusion of an inherited trigger that is implicitly
	 * redefined by local definition of a guard constraint deletes the guard
	 * that is no longer needed and, moreover, that subsequent re-inheritance
	 * of the trigger does not somehow restore the guard.
	 * 
	 * @see <a href="http://eclip.se/517130">bug 517130</a>
	 */
	@PluginResource("resource/inheritance/ExcludeTriggerTest.di")
	@Test
	public void excludeTriggerWithLocalGuard() {
		UMLRTStateMachine stateMachine = getCapsule("Capsule2").getStateMachine();
		UMLRTTransition transition = stateMachine.getVertex("State2").getIncomings().get(0);
		UMLRTTrigger trigger = transition.getTriggers().get(0);

		assumeThat("Trigger is not a redefinition", trigger.getInheritanceKind(),
				is(UMLRTInheritanceKind.REDEFINED));
		assumeThat("Trigger guard not found", trigger.getGuard(), notNullValue());

		// Exclude the trigger
		exclude(trigger);
		assumeThat("Trigger not excluded from inheriting context", trigger, isExcluded());

		// The only constraint must be the exclusion constraint of the trigger
		assertThat("Trigger guard still exists", transition.toUML().getOwnedRules().size(), is(1));
		assertThat("Not an exclusion constraint",
				transition.toUML().getOwnedRules().get(0).getSpecification(),
				instanceOf(LiteralBoolean.class));
		assertThat("Trigger still knows its guard", trigger.getGuard(), nullValue());

		// Re-inherit the trigger
		reinherit(trigger);
		assumeThat("Trigger not re-inherited", trigger.getInheritanceKind(),
				is(UMLRTInheritanceKind.INHERITED));

		// Now the exclusion constraint is removed, so there must not be any constraint at all
		assertThat("Trigger guard is restored", transition.toUML().getOwnedRules().size(), is(0));
		assertThat("Trigger found a guard", trigger.getGuard(), nullValue());
	}

	//
	// Test framework
	//

	UMLRTPackage getRoot() {
		return UMLRTPackage.getInstance(modelSet.getModel());
	}

	/** Get the subtype (inheriting) capsule. */
	UMLRTCapsule getCapsule() {
		return getCapsule("Capsule3");
	}

	UMLRTCapsule getCapsule(String name) {
		return getRoot().getCapsule(name);
	}

	/** Get the subtype (inheriting) protocol. */
	UMLRTProtocol getProtocol() {
		return getProtocol("Protocol2");
	}

	UMLRTProtocol getProtocol(String name) {
		return getRoot().getProtocol(name);
	}

	void exclude(UMLRTNamedElement element) {
		ICommand exclude = ExclusionCommand.getExclusionCommand(element.toUML(), true);
		assertThat(exclude, isExecutable());
		modelSet.execute(exclude);
	}

	void exclude(UMLRTNamedElement element, UMLRTNamedElement element2, UMLRTNamedElement... rest) {
		List<NamedElement> elements = Lists.asList(element, element2, rest).stream()
				.map(UMLRTNamedElement::toUML)
				.collect(Collectors.toList());
		ICommand exclude = ExclusionCommand.getExclusionCommand(elements, true);
		assertThat(exclude, isExecutable());
		modelSet.execute(exclude);
	}

	void reinherit(UMLRTNamedElement element) {
		ICommand reinherit = ExclusionCommand.getExclusionCommand(element.toUML(), false);
		assertThat(reinherit, isExecutable());
		modelSet.execute(reinherit);
	}

	void reinherit(UMLRTNamedElement element, UMLRTNamedElement element2, UMLRTNamedElement... rest) {
		List<NamedElement> elements = Lists.asList(element, element2, rest).stream()
				.map(UMLRTNamedElement::toUML)
				.collect(Collectors.toList());
		ICommand reinherit = ExclusionCommand.getExclusionCommand(elements, false);
		assertThat(reinherit, isExecutable());
		modelSet.execute(reinherit);
	}

	Matcher<UMLRTNamedElement> isExcluded() {
		return isExcluded(true);
	}

	Matcher<UMLRTNamedElement> notExcluded() {
		return isExcluded(false);
	}

	Matcher<UMLRTNamedElement> isExcluded(boolean expected) {
		return new BaseMatcher<UMLRTNamedElement>() {
			@Override
			public void describeTo(Description description) {
				description.appendText(expected ? "is excluded" : "not excluded");
			}

			@Override
			public void describeMismatch(Object item, Description description) {
				if (item == null) {
					super.describeMismatch(item, description);
				} else {
					description.appendText(expected ? "was not excluded" : "was excluded");
				}
			}

			@Override
			public boolean matches(Object item) {
				boolean result = false;

				if (item instanceof UMLRTNamedElement) {
					UMLRTNamedElement element = (UMLRTNamedElement) item;
					result = element.isExcluded() == expected;
				}

				return result;
			}
		};
	}
}
