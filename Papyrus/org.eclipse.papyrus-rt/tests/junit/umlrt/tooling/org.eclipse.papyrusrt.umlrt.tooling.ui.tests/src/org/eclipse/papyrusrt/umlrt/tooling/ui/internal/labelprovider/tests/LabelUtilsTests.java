/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 510315
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.labelprovider.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Map;
import java.util.function.Predicate;

import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.UMLRTResourceSetFixture;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.labelprovider.LabelUtils;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.base.Objects;

/**
 * Tests for {@link LabelUtils}.
 */
@PluginResource("resource/labels/label-utils.uml")
public class LabelUtilsTests extends AbstractPapyrusTest {

	private static final String SMALL_EFFECT_BODY = "do.it()";

	private static final String SMALL_GUARD_BODY = "Small";

	private static final String TRANSITION_NAME = "t1";

	private static final String OPERATION_NAME = "1stOp";

	private static final String OPERATION_PARAMS_NAME = "2ndOp";

	private static final String OPERATION_NAME_PLUS = OPERATION_NAME + "+";

	private static final String CPP_LANGUAGE = "C++";

	private static final String TRIGGER_GUARD_LABEL = "trigger " + OPERATION_NAME + "()" + " " + "[" + SMALL_GUARD_BODY + "]";

	private static final String MEDIUM_GUARD_BODY = "// medium guard Body\n2\n\n4\n5th Line";

	private static final String TRIGGERS_GUARD_LABEL = TRIGGER_GUARD_LABEL + "\n" + "trigger " + OPERATION_PARAMS_NAME + "()" + " " + "[" + MEDIUM_GUARD_BODY + "]";

	@Rule
	public final UMLRTResourceSetFixture model = new UMLRTResourceSetFixture();

	private Operation protocolMessage1;

	private Operation operationWithParameter;

	private Transition simpleTransition;

	private Transition transitionWithATrigger;

	private Transition transitionWith2triggers;

	private Transition transitionWithEffect;

	private IItemLabelProvider itemLabelProvider;

	@Before
	public void setup() {
		protocolMessage1 = ((Interface) model.getModel().getNestedPackage("Protocol1")
				.getOwnedType("Protocol1", false, UMLPackage.Literals.INTERFACE, false))
						.getOwnedOperation("1stOp", null, null);

		operationWithParameter = ((Interface) model.getModel().getNestedPackage("Protocol1")
				.getOwnedType("Protocol1", false, UMLPackage.Literals.INTERFACE, false))
						.getOwnedOperation("2stOp", null, null);

		// empty transition
		simpleTransition = getTransition(PseudostateKind.INITIAL_LITERAL, "State1");

		transitionWithATrigger = getTransition("State1", "State2");

		transitionWith2triggers = getTransition("State1", "State3");

		transitionWithEffect = getTransition("State2", "State3");

		itemLabelProvider = null;
	}

	protected Transition getTransition(Object sourceMatch, Object targetMatch) {
		StateMachine sm = (StateMachine) ((org.eclipse.uml2.uml.Class) model.getModel().getOwnedType("Capsule1")).getClassifierBehavior();
		Region region = sm.getRegions().get(0);

		Vertex source = region.getSubvertices().stream().filter(vertex(sourceMatch)).findAny().get();
		Vertex target = region.getSubvertices().stream().filter(vertex(targetMatch)).findAny().get();
		return source.getOutgoings().stream().filter(t -> t.getTarget() == target).findAny().get();
	}

	protected Predicate<Vertex> vertex(Object match) {
		return v -> (match instanceof PseudostateKind)
				? (v instanceof Pseudostate && ((Pseudostate) v).getKind() == match)
				: Objects.equal(v.getName(), match);
	}

	///////////////////////////////////////////
	// test label displayed for transitions
	///////////////////////////////////////////
	@Test
	public void testTransitionLabel_noname() {
		String current = LabelUtils.getTransitionLabel(simpleTransition, itemLabelProvider);
		assertThat(current, equalTo(""));
	}

	@Test
	public void testTransitionLabel_specificname() {
		simpleTransition.setName(TRANSITION_NAME);
		String current = LabelUtils.getTransitionLabel(simpleTransition, itemLabelProvider);
		assertThat(current, equalTo(TRANSITION_NAME));
	}

	@Test
	public void testTransitionLabel_specificname_with1trigger() {
		transitionWithATrigger.setName(TRANSITION_NAME);
		String current = LabelUtils.getTransitionLabel(transitionWithATrigger, itemLabelProvider);
		assertThat(current, equalTo(TRANSITION_NAME));
	}

	@Test
	public void testTransitionLabel_noname_withAtrigger() {
		String current = LabelUtils.getTransitionLabel(transitionWithATrigger, itemLabelProvider);
		assertThat(current, equalTo(OPERATION_NAME));
	}

	@Test
	public void testTransitionLabel_noname_with2triggers() {
		String current = LabelUtils.getTransitionLabel(transitionWith2triggers, itemLabelProvider);
		assertThat(current, equalTo(OPERATION_NAME_PLUS));
	}

	@Test
	public void testTransitionLabel_specificname_withAtrigger() {
		transitionWithATrigger.setName(TRANSITION_NAME);
		String current = LabelUtils.getTransitionLabel(transitionWithATrigger, itemLabelProvider);
		assertThat(current, equalTo(TRANSITION_NAME));
	}

	@Test
	public void testTransitionLabel_specificname_with2triggers() {
		transitionWith2triggers.setName(TRANSITION_NAME);
		String current = LabelUtils.getTransitionLabel(transitionWith2triggers, itemLabelProvider);
		assertThat(current, equalTo(TRANSITION_NAME));
	}

	///////////////////////////////////////////
	// test tooltip displayed for transitions
	///////////////////////////////////////////
	@Test
	public void testTransitionTooltip_noname() {
		String current = LabelUtils.getTransitionTooltip(simpleTransition, itemLabelProvider);
		assertThat(current, equalTo(""));
	}

	@Test
	public void testTransitionTooltip_specificname() {
		simpleTransition.setName(TRANSITION_NAME);
		String current = LabelUtils.getTransitionTooltip(simpleTransition, itemLabelProvider);
		assertThat(current, equalTo(TRANSITION_NAME));
	}

	// test guard tooltip label from transition
	@Test
	public void testTransitionGuardTooltip_noguard() {
		String current = LabelUtils.getGuardTooltip(simpleTransition);
		assertThat(current, equalTo(""));
	}

	@Test
	public void testTransitionGuardTooltip_transitionguard_noname() {
		addSimpleGuard(simpleTransition);
		String current = LabelUtils.getGuardTooltip(simpleTransition);
		assertThat(current, equalTo("transition [" + SMALL_GUARD_BODY + "]"));
	}

	@Test
	public void testTransitionGuardTooltip_transitionguard_specifiedname() {
		addSimpleGuard(simpleTransition);
		simpleTransition.setName(TRANSITION_NAME);
		String current = LabelUtils.getGuardTooltip(simpleTransition);
		assertThat(current, equalTo("transition " + TRANSITION_NAME + " [" + SMALL_GUARD_BODY + "]"));
	}

	@Test
	public void testTransitionGuardTooltip_transitionAnd2TriggersGuard() {
		addSimpleGuard(transitionWith2triggers);
		addFirstTriggerGuard(transitionWith2triggers);
		addSecondTriggerGuard(transitionWith2triggers);
		String current = LabelUtils.getGuardTooltip(transitionWith2triggers);
		assertEquals("transition " + OPERATION_NAME_PLUS + " [" + SMALL_GUARD_BODY + "]\n" + TRIGGERS_GUARD_LABEL, current);
	}

	// test guard tooltip label from transition
	@Test
	public void testTransitionGuardTooltip_transitionAndTriggerGuard() {
		addSimpleGuard(transitionWithATrigger);
		addFirstTriggerGuard(transitionWithATrigger);
		String current = LabelUtils.getGuardTooltip(transitionWithATrigger);
		assertThat(current, equalTo("transition " + OPERATION_NAME + " [" + SMALL_GUARD_BODY + "]" + "\n" + TRIGGER_GUARD_LABEL));
	}

	@Test
	public void testTransitionGuardTooltip_transitionAndTriggerGuard_specifiedname() {
		addSimpleGuard(transitionWithATrigger);
		transitionWithATrigger.setName(TRANSITION_NAME);
		addFirstTriggerGuard(transitionWithATrigger);
		String current = LabelUtils.getGuardTooltip(transitionWithATrigger);
		assertThat(current, equalTo("transition " + TRANSITION_NAME + " [" + SMALL_GUARD_BODY + "]" + "\n" + TRIGGER_GUARD_LABEL));
	}

	@Test
	public void testTransitionGuardTooltip_transitionAnd2triggersguard_specifiedname() {
		addSimpleGuard(transitionWith2triggers);
		transitionWith2triggers.setName(TRANSITION_NAME);
		addFirstTriggerGuard(transitionWith2triggers);
		addSecondTriggerGuard(transitionWith2triggers);
		String current = LabelUtils.getGuardTooltip(transitionWith2triggers);
		String expected = "transition " + TRANSITION_NAME + " [" + SMALL_GUARD_BODY + "]" + "\n" + TRIGGERS_GUARD_LABEL;
		System.err.println("c: " + current);
		System.err.println("e: " + expected);
		assertEquals(expected, current);
	}

	////////////////////////////////////////////////////////////
	// Test trigger labels.
	////////////////////////////////////////////////////////////
	@Test
	public void testTriggerTooltipLabel_smallGuard() {
		addFirstTriggerGuard(transitionWith2triggers);
		Trigger trg1 = transitionWith2triggers.getTriggers().get(0);
		String current = LabelUtils.getTriggerGuardTooltip(trg1);
		String expected = OPERATION_NAME + "() [" + SMALL_GUARD_BODY + "]";
		assertEquals(expected, current);
	}

	@Test
	public void testTriggerTooltipLabel_mediumGuard() {
		addSecondTriggerGuard(transitionWith2triggers);
		Trigger trg1 = transitionWith2triggers.getTriggers().get(1);
		String current = LabelUtils.getTriggerGuardTooltip(trg1);
		String expected = OPERATION_PARAMS_NAME + "() [" + MEDIUM_GUARD_BODY + "]";
		assertEquals(expected, current);
	}

	@Test
	public void testTriggerTooltipLabel_noGuard() {
		Trigger trg = simpleTransition.createTrigger(null);
		CallEvent ce = model.getModel().getNestedPackage("Protocol1").getPackagedElements().stream()
				.filter(CallEvent.class::isInstance).map(CallEvent.class::cast)
				.filter(event -> event.getOperation() == protocolMessage1)
				.findAny().get();
		trg.setEvent(ce);
		String current = LabelUtils.getTriggerGuardTooltip(trg);
		assertThat(current, nullValue());
	}

	////////////////////////////////////////////////////////////
	// TEST Abbreviate lines
	////////////////////////////////////////////////////////////
	@Test
	public void testAbbreviate_short() {
		String base = "1\n2\n\n4";
		String current = LabelUtils.abbreviateLines(base, 5, "[...]");
		assertThat(current, equalTo(base));
	}

	@Test
	public void testAbbreviate_long() {
		String base = "1\n2\n\n4";
		String current = LabelUtils.abbreviateLines(base, 2, "[...]");
		assertThat(current, equalTo("1\n[...]"));
	}

	@Test
	public void testAbbreviate_longIntermediateNL() {
		String base = "1\n2\n\n4";
		String current = LabelUtils.abbreviateLines(base, 4, "[...]");
		assertThat(current, equalTo("1\n2\n\n[...]"));
	}

	////////////////////////////////////////////////////////////
	// UTILITIES TO INITIATE MODEL
	////////////////////////////////////////////////////////////

	/**
	 * Creates a new OpaqueBehavior with the specified body/language couples.
	 * 
	 * @param language2body
	 *            couple of bodies/language for that behavior
	 * @return the new {@link OpaqueBehavior}
	 */
	private OpaqueBehavior createOpaqueBehavior(Transition owner, Map<String, String> language2body) {
		OpaqueBehavior behavior = (OpaqueBehavior) owner.createEffect(null, UMLPackage.Literals.OPAQUE_BEHAVIOR);
		behavior.getBodies().addAll(language2body.keySet());
		behavior.getLanguages().addAll(language2body.values());
		return behavior;
	}

	/**
	 * Returns a constraint with OpaqueExpression as specification, with body/language couples as specified.
	 * 
	 * @param language2body
	 *            constraint bodies/languages couple.
	 * @return the new constraint as specified
	 */
	protected Constraint createConstraint(Namespace owner, Map<String, String> language2body) {
		Constraint constraint = owner.createOwnedRule(null);
		OpaqueExpression spec = (OpaqueExpression) constraint.createSpecification(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION);
		spec.getBodies().addAll(language2body.values());
		spec.getLanguages().addAll(language2body.keySet());
		return constraint;
	}

	private Constraint addSimpleGuard(Transition transition) {
		Constraint result = createConstraint(transition, Collections.singletonMap(CPP_LANGUAGE, SMALL_GUARD_BODY));
		transition.setGuard(result);
		return result;
	}

	private Constraint addSecondTriggerGuard(Transition transition) {
		Constraint ct = createConstraint(transition, Collections.singletonMap(CPP_LANGUAGE, MEDIUM_GUARD_BODY));
		ct.getConstrainedElements().add(transition.getTriggers().get(1));
		ct.applyStereotype(ct.getApplicableStereotype("UMLRTStateMachines::RTGuard"));
		return ct;
	}

	private Constraint addFirstTriggerGuard(Transition transition) {
		Constraint ct = createConstraint(transition, Collections.singletonMap(CPP_LANGUAGE, SMALL_GUARD_BODY));
		ct.getConstrainedElements().add(transition.getTriggers().get(0));
		ct.applyStereotype(ct.getApplicableStereotype("UMLRTStateMachines::RTGuard"));
		return ct;
	}

}
