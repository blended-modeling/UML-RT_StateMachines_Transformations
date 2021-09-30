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
import static org.eclipse.papyrusrt.umlrt.uml.tests.util.FacadeMatchers.named;
import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.getUMLRTContents;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
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
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InternalFacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.FacadeTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.categories.StateMachineTests;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.ModelFixture;
import org.eclipse.papyrusrt.umlrt.uml.tests.util.TestModel;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test cases for the state façade in {@link UMLRTState} that are not covered
 * by the {@link VertexFacadeTest}.
 */
@TestModel("inheritance/statemachines.uml")
@Category({ StateMachineTests.class, FacadeTests.class })
public class StateFacadeTest {

	@Rule
	public final ModelFixture fixture = new ModelFixture();

	public StateFacadeTest() {
		super();
	}

	@Test
	public void entryPoints() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState composite = (UMLRTState) sm.getVertex("composite");

		List<UMLRTConnectionPoint> entryPoints = composite.getEntryPoints();
		assertThat(entryPoints.size(), is(1));
		assertThat(entryPoints, hasItem(named("entry")));
		assertThat(entryPoints.get(0).getKind(), is(UMLRTConnectionPointKind.ENTRY));
	}

	@Test
	public void exitPoints() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState composite = (UMLRTState) sm.getVertex("composite");

		List<UMLRTConnectionPoint> exitPoints = composite.getExitPoints();
		assertThat(exitPoints.size(), is(1));
		assertThat(exitPoints, hasItem(named("exit")));
		assertThat(exitPoints.get(0).getKind(), is(UMLRTConnectionPointKind.EXIT));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void facadeGetSubvertices() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState composite = (UMLRTState) sm.getVertex("composite");

		List<UMLRTVertex> vertices = (List<UMLRTVertex>) ((InternalFacadeObject) composite).facadeGet(UMLRTUMLRTPackage.Literals.STATE__SUBVERTEX, true);
		assertThat(vertices.size(), is(1));
		assertThat(vertices, hasItem(named("nested")));

		vertices.stream().filter(v -> "nested".equals(v.getName())).findAny().ifPresent(UMLRTVertex::exclude);

		assumeThat(composite.getSubvertices().size(), is(0));

		vertices = (List<UMLRTVertex>) ((InternalFacadeObject) composite).facadeGet(UMLRTUMLRTPackage.Literals.STATE__SUBVERTEX, true);
		assertThat(vertices.size(), is(1));
		assertThat(vertices, hasItem(named("nested")));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void facadeGetSubtransitions() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState composite = (UMLRTState) sm.getVertex("composite");

		List<UMLRTTransition> transitions = (List<UMLRTTransition>) ((InternalFacadeObject) composite).facadeGet(UMLRTUMLRTPackage.Literals.STATE__SUBTRANSITION, true);
		assertThat(transitions.size(), is(2));
		assertThat(transitions, hasItems(named("nestedOut"), named("internal")));

		transitions.stream().filter(t -> "internal".equals(t.getName())).findAny().ifPresent(UMLRTTransition::exclude);

		assumeThat(composite.getSubtransitions().size(), is(1));
		assertThat(transitions, hasItem(named("nestedOut")));

		transitions = (List<UMLRTTransition>) ((InternalFacadeObject) composite).facadeGet(UMLRTUMLRTPackage.Literals.STATE__SUBTRANSITION, true);
		assertThat(transitions.size(), is(2));
		assertThat(transitions, hasItems(named("nestedOut"), named("internal")));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void facadeGetConnectionPoints() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState composite = (UMLRTState) sm.getVertex("composite");

		List<UMLRTConnectionPoint> connPts = (List<UMLRTConnectionPoint>) ((InternalFacadeObject) composite).facadeGet(UMLRTUMLRTPackage.Literals.STATE__CONNECTION_POINT, true);
		assertThat(connPts.size(), is(2));
		assertThat(connPts, hasItems(named("entry"), named("exit")));

		connPts.stream().filter(v -> "entry".equals(v.getName())).findAny().ifPresent(UMLRTConnectionPoint::exclude);

		assumeThat(composite.getConnectionPoints().size(), is(1));
		assertThat(composite.getConnectionPoints(), hasItem(named("exit")));

		connPts = (List<UMLRTConnectionPoint>) ((InternalFacadeObject) composite).facadeGet(UMLRTUMLRTPackage.Literals.STATE__CONNECTION_POINT, true);
		assertThat(connPts.size(), is(2));
		assertThat(connPts, hasItems(named("entry"), named("exit")));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void facadeGetEntryPoints() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState composite = (UMLRTState) sm.getVertex("composite");

		List<UMLRTConnectionPoint> connPts = (List<UMLRTConnectionPoint>) ((InternalFacadeObject) composite).facadeGet(UMLRTUMLRTPackage.Literals.STATE__ENTRY_POINT, true);
		assertThat(connPts.size(), is(1));
		assertThat(connPts, hasItems(named("entry")));

		connPts.get(0).exclude();

		assumeThat(composite.getEntryPoints().size(), is(0));

		connPts = (List<UMLRTConnectionPoint>) ((InternalFacadeObject) composite).facadeGet(UMLRTUMLRTPackage.Literals.STATE__ENTRY_POINT, true);
		assertThat(connPts.size(), is(1));
		assertThat(connPts, hasItems(named("entry")));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void facadeGetExitPoints() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState composite = (UMLRTState) sm.getVertex("composite");

		List<UMLRTConnectionPoint> connPts = (List<UMLRTConnectionPoint>) ((InternalFacadeObject) composite).facadeGet(UMLRTUMLRTPackage.Literals.STATE__EXIT_POINT, true);
		assertThat(connPts.size(), is(1));
		assertThat(connPts, hasItems(named("exit")));

		connPts.get(0).exclude();

		assumeThat(composite.getExitPoints().size(), is(0));

		connPts = (List<UMLRTConnectionPoint>) ((InternalFacadeObject) composite).facadeGet(UMLRTUMLRTPackage.Literals.STATE__EXIT_POINT, true);
		assertThat(connPts.size(), is(1));
		assertThat(connPts, hasItems(named("exit")));
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
	public void entry() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat(state, notNullValue());

		UMLRTOpaqueBehavior entry = state.getEntry();
		assertThat(entry, notNullValue());
		assertThat(entry.getBodies().get("C++"), is("this.enter();"));
	}

	@Test
	public void inheritedEntry() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat(state, notNullValue());
		assumeThat(state, isInherited());

		UMLRTOpaqueBehavior entry = state.getEntry();
		assertThat(entry, isInherited());
		assertThat(entry, notNullValue());
		assertThat(entry.getBodies().get("C++"), is("this.enter();"));
		assertThat("Entry was reified by façade EMap initialization", entry, isInherited());
	}

	@Test
	public void redefinedEntry() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat(state, notNullValue());
		assumeThat(state, isInherited());

		UMLRTOpaqueBehavior entry = state.getEntry();
		assumeThat(entry, isInherited());
		assertThat(entry, notNullValue());
		entry.getBodies().put("C++", "this.idle();");

		assertThat(entry.toUML().getBodies(), is(Collections.singletonList("this.idle();")));
		assertThat("Entry was not reified by façade EMap change", entry, isRedefined());
		assertThat("State was not reified by façade EMap change", state, isRedefined());
	}

	@Test
	public void exit() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat(state, notNullValue());

		UMLRTOpaqueBehavior exit = state.getExit();
		assertThat(exit, notNullValue());
		assertThat(exit.getBodies().get("C++"), is("this.exit();"));
	}

	@Test
	public void inheritedExit() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat(state, notNullValue());
		assumeThat(state, isInherited());

		UMLRTOpaqueBehavior exit = state.getExit();
		assertThat(exit, isInherited());
		assertThat(exit, notNullValue());
		assertThat(exit.getBodies().get("C++"), is("this.exit();"));
		assertThat("Exit was reified by façade EMap initialization", exit, isInherited());
	}

	@Test
	public void redefinedExit() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat(state, notNullValue());
		assumeThat(state, isInherited());

		UMLRTOpaqueBehavior exit = state.getExit();
		assumeThat(exit, isInherited());
		assertThat(exit, notNullValue());
		exit.getBodies().put("C++", "this.idle();");

		assertThat(exit.toUML().getBodies(), is(Collections.singletonList("this.idle();")));
		assertThat("Exit was not reified by façade EMap change", exit, isRedefined());
		assertThat("State was not reified by façade EMap change", state, isRedefined());
	}

	@Test
	public void toRegionSimpleState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat("State not found", state, notNullValue());

		Region region = state.toRegion();
		assertThat("Simple state has a region", region, nullValue());
	}

	@Test
	public void toRegionCompositeState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState state = (UMLRTState) sm.getVertex("composite");
		assumeThat("State not found", state, notNullValue());

		Region region = sm.toRegion();
		assertThat("No region in composite state", region, notNullValue());
		assertThat("Empty region", region.getSubvertices(), hasItem(anything()));
	}

	@Test
	public void toRegionInheritedSimpleState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat("State not found", state, notNullValue());

		Region region = state.toRegion();
		assertThat("Simple state has a region", region, nullValue());
	}

	@Test
	public void toRegionInheritedCompositeState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState state = (UMLRTState) sm.getVertex("composite");
		assumeThat("State not found", state, notNullValue());

		Region region = sm.toRegion();
		assertThat("No region in composite state", region, notNullValue());
		assertThat("Empty region", getUMLRTContents(region, UMLPackage.Literals.REGION__SUBVERTEX),
				hasItem(anything()));
	}

	@Test
	public void createEntry() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState state = sm.createState("NewState");
		assumeThat(state, notNullValue());
		assumeThat(state.getEntry(), nullValue());

		UMLRTOpaqueBehavior entry = state.createEntry("C++", "this.doIt()");

		assertThat(entry, notNullValue());
		assertThat(entry.getBodies().get("C++"), is("this.doIt()"));

		OpaqueBehavior uml = (OpaqueBehavior) state.toUML().getEntry();
		assertThat(uml.getLanguages(), is(singletonList("C++")));
		assertThat(uml.getBodies(), is(singletonList("this.doIt()")));

		assertThat("Entry is re-entrant", uml.isReentrant(), is(false));
	}

	@Test
	public void createExit() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		UMLRTState state = sm.createState("NewState");
		assumeThat(state, notNullValue());
		assumeThat(state.getEntry(), nullValue());

		UMLRTOpaqueBehavior exit = state.createExit("C++", "this.doIt()");

		assertThat(exit, notNullValue());
		assertThat(exit.getBodies().get("C++"), is("this.doIt()"));

		OpaqueBehavior uml = (OpaqueBehavior) state.toUML().getExit();
		assertThat(uml.getLanguages(), is(singletonList("C++")));
		assertThat(uml.getBodies(), is(singletonList("this.doIt()")));

		assertThat("Exit is re-entrant", uml.isReentrant(), is(false));
	}

	@Test
	public void simpleState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());
		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat("State not found", state, notNullValue());

		assertThat("State is not simple", state.isSimple(), is(true));
	}

	@Test
	public void simpleStateNotComposite() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("RootCapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());
		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat("State not found", state, notNullValue());

		assertThat("Simple state is composite", state.isComposite(), is(false));
	}

	@Test
	public void inheritedSimpleState() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());
		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat("State not found", state, notNullValue());

		assertThat("State is not simple", state.isSimple(), is(true));
	}

	@Test
	public void inheritedSimpleStateNotComposite() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());
		UMLRTState state = (UMLRTState) sm.getVertex("State1");
		assumeThat("State not found", state, notNullValue());

		assertThat("Simple state is composite", state.isComposite(), is(false));
	}

	@Test
	public void redefinedEntryUndoRedo() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		testRedefinedCodeUndoRedo((UMLRTState) sm.getVertex("State1"), UMLRTState::getEntry,
				UMLRTOpaqueBehavior::getBodies, "C++", "this.enter();", "pass();");
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
	public void redefinedExitUndoRedo() {
		UMLRTCapsule capsule = fixture.getRoot().getCapsule("Subcapsule");
		UMLRTStateMachine sm = capsule.getStateMachine();
		assumeThat("No state machine façade", sm, notNullValue());

		testRedefinedCodeUndoRedo((UMLRTState) sm.getVertex("State1"), UMLRTState::getExit,
				UMLRTOpaqueBehavior::getBodies, "C++", "this.exit();", "pass();");
	}

	@Test
	public void allRedefinitions() {
		UMLRTStateMachine rootSM = getRootCapsuleBehavior();
		UMLRTStateMachine subSM = getSubcapsuleBehavior();
		UMLRTStateMachine subsubSM = getSubsubcapsuleBehavior();

		UMLRTState rootState = getState(rootSM, "State1");
		UMLRTState subState = getState(subSM, "State1");
		UMLRTState subsubState = getState(subsubSM, "State1");

		List<UMLRTState> expected = Arrays.asList(rootState, subState, subsubState);
		List<? extends UMLRTState> actual = rootState.allRedefinitions().collect(Collectors.toList());
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

	UMLRTState getState(UMLRTStateMachine sm, String name) {
		UMLRTVertex result = sm.getVertex(name);
		assumeThat("State not found", result, instanceOf(UMLRTState.class));

		return (UMLRTState) result;
	}

}
