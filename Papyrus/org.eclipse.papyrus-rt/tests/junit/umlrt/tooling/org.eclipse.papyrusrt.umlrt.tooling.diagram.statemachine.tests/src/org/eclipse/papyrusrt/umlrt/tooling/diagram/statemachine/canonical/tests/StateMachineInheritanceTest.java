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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests;

import static java.util.Collections.singletonList;
import static java.util.Spliterators.spliteratorUnknownSize;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.eclipse.papyrusrt.junit.matchers.GeometryMatchers.near;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineEditPart;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests.AbstractCanonicalTest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

@PluginResource("resource/statemachine/inheritance/simple.di")
@DiagramNaming(StateMachineDiagramNameStrategy.class)
public class StateMachineInheritanceTest extends AbstractCanonicalTest {
	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule ELEMENT_TYPES_RULE = new ElementTypesRule();

	/**
	 * Verify that the canonical contents of a redefining state machine present the inherited elements.
	 */
	@NeedsUIEvents
	@Test
	@ActiveDiagram("Capsule2::StateMachine")
	public void basicInheritedContent() {
		Class capsule2 = (Class) editor.getModel().getOwnedType("Capsule2");
		StateMachine sm = (StateMachine) capsule2.getClassifierBehavior();
		UMLRTStateMachine stateMachine = UMLRTStateMachine.getInstance(sm);

		List<UMLRTVertex> vertices = stateMachine.getVertices();
		assumeThat("No vertices to look for", vertices.size(), greaterThan(0));
		vertices.forEach(v -> requireEditPart(v.toUML()));

		List<UMLRTTransition> transitions = stateMachine.getTransitions();
		assumeThat("No transitions to look for", transitions.size(), greaterThan(0));
		transitions.forEach(t -> requireConnectionEditPart(t.toUML()));
	}

	/**
	 * Verifies that the diagram correctly presents inherited and local content
	 * when a generalization is made from the context capsule to another capsule
	 * that already has a state machine.
	 */
	@PluginResource("resource/statemachine/inheritance/bug514746.di")
	@Test
	@NeedsUIEvents
	@ActiveDiagram("Capsule2::StateMachine")
	public void createGeneralizationToRedefineStateMachine() {
		UMLRTCapsule capsule2 = UMLRTPackage.getInstance(editor.getModel()).getCapsule("Capsule2");
		UMLRTCapsule capsule1 = capsule2.getPackage().getCapsule("Capsule1");
		UMLRTStateMachine sm = capsule2.getStateMachine();

		SetRequest set = new SetRequest(editor.getEditingDomain(), capsule2.toUML(), UMLPackage.Literals.CLASS__SUPER_CLASS, singletonList(capsule1.toUML()));
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(capsule2.toUML());
		editor.execute(edit.getEditCommand(set));

		assertSingularRegion();

		Set<Object> unique = new HashSet<>();

		// Both of the initial transitions are on this inherited pseudostate
		IGraphicalEditPart initialEP = uniqueInitialState(sm);
		assertThat(unique.add(initialEP), is(true));
		List<?> connections = initialEP.getSourceConnections();
		assertThat(connections.size(), is(2));

		// And both connect to similar graphs
		connections.stream().map(ConnectionEditPart.class::cast).forEach(cep -> {
			assertSubgraph(cep, unique);
		});
	}

	/**
	 * Verifies that the diagram correctly presents inherited content instead of
	 * the default content initially created for every state machine when the
	 * default state machine becomes a redefinition of a more interesting
	 * state machine.
	 */
	@PluginResource("resource/statemachine/inheritance/bug514746.di")
	@Test
	@NeedsUIEvents
	@ActiveDiagram("Capsule3::StateMachine")
	public void makeTrivialStateMachineRedefinition() {
		UMLRTCapsule capsule3 = UMLRTPackage.getInstance(editor.getModel()).getCapsule("Capsule3");
		UMLRTCapsule capsule1 = capsule3.getPackage().getCapsule("Capsule1");
		UMLRTStateMachine sm = capsule3.getStateMachine();

		CreateRelationshipRequest create = new CreateRelationshipRequest(
				editor.getEditingDomain(),
				capsule3.toUML(), capsule1.toUML(),
				UMLElementTypes.GENERALIZATION);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(capsule3.toUML());
		editor.execute(edit.getEditCommand(create));

		assertSingularRegion();

		Set<Object> unique = new HashSet<>();

		// There is only one initial transition
		IGraphicalEditPart initialEP = uniqueInitialState(sm);
		assertThat(unique.add(initialEP), is(true));
		List<?> connections = initialEP.getSourceConnections();
		assertThat(connections.size(), is(1));

		// And it connects to an interesting graph inherited from Capsule1, not the
		// default 'State1'
		ConnectionEditPart cep = (ConnectionEditPart) connections.get(0);
		assertSubgraph(cep, unique);
	}

	/**
	 * Verifies that the diagram correctly presents inherited content instead of
	 * the default content initially created for every state machine when the
	 * default state machine becomes a redefinition of a newly created state
	 * machine in the existing superclass.
	 */
	@PluginResource("resource/statemachine/inheritance/bug514746.di")
	@Test
	@NeedsUIEvents
	@ActiveDiagram("Capsule5::StateMachine")
	public void makeTrivialStateMachineRedefinitionOfNewStateMachine() {
		UMLRTCapsule capsule5 = UMLRTPackage.getInstance(editor.getModel()).getCapsule("Capsule5");
		UMLRTCapsule capsule6 = capsule5.getPackage().getCapsule("Capsule6");
		UMLRTStateMachine sm = capsule5.getStateMachine();

		CreateElementRequest create = new CreateElementRequest(
				editor.getEditingDomain(),
				capsule6.toUML(),
				UMLRTElementTypesEnumerator.RT_STATE_MACHINE,
				UMLPackage.Literals.BEHAVIORED_CLASSIFIER__OWNED_BEHAVIOR);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(capsule6.toUML());
		editor.execute(edit.getEditCommand(create));

		assertSingularRegion();

		Set<Object> unique = new HashSet<>();

		// There is only one initial transition
		IGraphicalEditPart initialEP = uniqueInitialState(sm);
		assertThat(unique.add(initialEP), is(true));
		List<?> connections = initialEP.getSourceConnections();
		assertThat(connections.size(), is(1));

		// And it connects just per the default
		ConnectionEditPart cep = (ConnectionEditPart) connections.get(0);
		assertDefaultSubgraph(cep, unique);
	}

	/**
	 * Verifies that the diagram correctly presents inherited default content
	 * in addition to its own non-default content when the state machine
	 * becomes a redefinition of a newly created state machine in the
	 * existing superclass.
	 */
	@PluginResource("resource/statemachine/inheritance/bug514746.di")
	@Test
	@NeedsUIEvents
	@ActiveDiagram("Capsule1::StateMachine")
	public void inheritNewStateMachineIntoNonTrivialSM() {
		UMLRTCapsule capsule1 = UMLRTPackage.getInstance(editor.getModel()).getCapsule("Capsule1");
		UMLRTCapsule capsule4 = capsule1.getPackage().getCapsule("Capsule4");
		UMLRTStateMachine sm = capsule1.getStateMachine();

		CreateElementRequest create = new CreateElementRequest(
				editor.getEditingDomain(),
				capsule4.toUML(),
				UMLRTElementTypesEnumerator.RT_STATE_MACHINE,
				UMLPackage.Literals.BEHAVIORED_CLASSIFIER__OWNED_BEHAVIOR);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(capsule4.toUML());
		editor.execute(edit.getEditCommand(create));

		assertSingularRegion();

		Set<Object> unique = new HashSet<>();

		// Both of the initial transitions are on this inherited pseudostate
		IGraphicalEditPart initialEP = uniqueInitialState(sm);
		assertThat(unique.add(initialEP), is(true));
		List<?> connections = initialEP.getSourceConnections();
		assertThat(connections.size(), is(2));

		// And each connects to a different-looking graphs
		connections.stream().map(ConnectionEditPart.class::cast)
				.filter(cep -> ((IInheritableEditPart) cep).isInherited())
				.forEach(cep -> {
					assertDefaultSubgraph(cep, unique);
				});
		connections.stream().map(ConnectionEditPart.class::cast)
				.filter(cep -> !((IInheritableEditPart) cep).isInherited())
				.forEach(cep -> {
					assertSubgraph(cep, unique);
				});
	}

	/**
	 * Verifies that the diagram correctly presents inherited complex content
	 * in the state machine diagram created for a capsule that didn't previously
	 * have a state machine at the time that it adds a generalization to a
	 * capsule that has a non-trivial (non-default) state machine.
	 */
	@PluginResource("resource/statemachine/inheritance/bug514746.di")
	@Test
	@NeedsUIEvents
	@ActiveDiagram("Capsule1::StateMachine")
	public void inheritStateMachineIntoNewSubclass() {
		UMLRTCapsule capsule1 = UMLRTPackage.getInstance(editor.getModel()).getCapsule("Capsule1");
		UMLRTCapsule capsule6 = capsule1.getPackage().getCapsule("Capsule6");

		CreateRelationshipRequest create = new CreateRelationshipRequest(
				editor.getEditingDomain(),
				capsule6.toUML(), capsule1.toUML(),
				UMLElementTypes.GENERALIZATION);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(capsule6.toUML());
		editor.execute(edit.getEditCommand(create));

		UMLRTStateMachine sm = capsule6.getStateMachine();
		assumeThat("Capsule6 did not inherit a state machine", sm, notNullValue());

		assertSingularRegion();

		editor.openDiagram("Capsule6::StateMachine");

		Set<Object> unique = new HashSet<>();

		// There is only one initial transition
		IGraphicalEditPart initialEP = uniqueInitialState(sm);
		assertThat(unique.add(initialEP), is(true));
		List<?> connections = initialEP.getSourceConnections();
		assertThat(connections.size(), is(1));

		// And it connects to an interesting graph inherited from Capsule1, not the
		// default 'State1'
		ConnectionEditPart cep = (ConnectionEditPart) connections.get(0);
		assertSubgraph(cep, unique);
	}

	/**
	 * Verifies that the region view that, in a subclass, was for a region of
	 * a root state machine is updated correctly to reference the new root
	 * definition of that region since a generalization was added, on first
	 * opening of the diagram. Moreover, that the sizing of the region view
	 * is appropriate to the overall state machine frame (not shrunken in the
	 * corner and showing a scroll bar).
	 */
	@PluginResource("resource/statemachine/inheritance/zoneroo.di")
	@Test
	@NeedsUIEvents
	@ActiveDiagram("Capsule1::StateMachine")
	public void initialLayoutOfRegionBecomeRedefinition() {
		// We started by opening the parent capsule's diagram to lay it out

		// Now, open the subclass's diagram
		editor.openDiagram("Capsule2::StateMachine");

		Region region = UMLRTPackage.getInstance(editor.getModel()).getCapsule("Capsule2").getStateMachine().getRedefinedStateMachine().toRegion();
		IGraphicalEditPart regionEP = requireEditPart(region);
		View regionView = regionEP.getNotationView();
		Region semantic = (Region) regionView.getElement();
		assertThat("View references redefinition", UMLRTExtensionUtil.isInherited(semantic), is(false));

		// Parent of the state machine regions compartment
		IGraphicalEditPart frameEP = (IGraphicalEditPart) regionEP.getParent().getParent();
		assumeThat("Didn't find the diagram frame", frameEP.getNotationView().getType(),
				is(StateMachineEditPart.VISUAL_ID));
		Dimension frameSize = frameEP.getFigure().getSize();
		Dimension regionSize = regionEP.getFigure().getSize();

		// Account for the usual 20 pixels or so of name label
		frameSize.shrink(0, 20);

		// Don't expect exact sizing, but something very near (within ±5)
		assertThat("Bad region layout", new Point(regionSize.width(), regionSize.height()),
				near(frameSize.width(), frameSize.height(), 5));
	}

	//
	// Test framework
	//

	IGraphicalEditPart uniqueInitialState(UMLRTStateMachine sm) {
		// There is only one initial pseudostate
		List<UMLRTPseudostate> initialStates = sm.getVertices().stream()
				.filter(UMLRTPseudostate.class::isInstance).map(UMLRTPseudostate.class::cast)
				.filter(p -> p.getKind() == UMLRTPseudostateKind.INITIAL)
				.collect(Collectors.toList());
		assertThat(initialStates.size(), is(1));
		UMLRTPseudostate initialState = initialStates.get(0);

		// And it is inherited
		assertThat(initialState.getInheritanceKind(), is(UMLRTInheritanceKind.INHERITED));

		return requireEditPart(initialState.toUML());
	}

	void assertSingularRegion() {
		// There is exactly one region shape
		assertThat("Wrong number of region edit-parts",
				StreamSupport.stream(spliteratorUnknownSize(DiagramEditPartsUtil.getAllContents(getDiagramEditPart(), false), Spliterator.NONNULL | Spliterator.ORDERED | Spliterator.DISTINCT), false)
						.filter(RegionEditPart.class::isInstance)
						.count(),
				is(1L));
	}

	private void assertSubgraph(ConnectionEditPart cep, Set<Object> unique) {
		assertThat(unique.add(cep), is(true));

		IGraphicalEditPart targetEP = (IGraphicalEditPart) cep.getTarget();
		assertThat(unique.add(targetEP), is(true));

		EObject target = targetEP.resolveSemanticElement();
		assertThat(target, instanceOf(State.class));

		List<?> outgoing = targetEP.getSourceConnections();
		assertThat(outgoing.size(), is(1));
		assertThat(unique.add(outgoing.get(0)), is(true));

		targetEP = (IGraphicalEditPart) ((ConnectionEditPart) outgoing.get(0)).getTarget();
		assertThat(unique.add(targetEP), is(true));
		target = targetEP.resolveSemanticElement();
		assertThat(target, instanceOf(Pseudostate.class));
		assertThat(((Pseudostate) target).getKind(), is(PseudostateKind.CHOICE_LITERAL));

		outgoing = targetEP.getSourceConnections();
		assertThat(outgoing.size(), is(2));

		outgoing.stream().map(ConnectionEditPart.class::cast).forEach(transEP -> {
			assertThat(unique.add(transEP), is(true));

			IGraphicalEditPart stateEP = (IGraphicalEditPart) transEP.getTarget();
			assertThat(unique.add(stateEP), is(true));

			EObject state = stateEP.resolveSemanticElement();
			assertThat(state, instanceOf(State.class));
		});
	}

	private void assertDefaultSubgraph(ConnectionEditPart cep, Set<Object> unique) {
		assertThat(unique.add(cep), is(true));

		IGraphicalEditPart targetEP = (IGraphicalEditPart) cep.getTarget();
		EObject target = targetEP.resolveSemanticElement();
		assertThat(target, instanceOf(State.class));
		assertThat(((State) target).getName(), is("State1"));
	}
}
