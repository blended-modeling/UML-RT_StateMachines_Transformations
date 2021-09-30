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

package org.eclipse.papyrusrt.umlrt.core.tests.types.advice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import javax.inject.Named;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.Either;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Regression tests for auto-naming of new pseudostates in RT state machines.
 */
@PluginResource("/resource/TestModel.di")
public class PseudoStateAutonamingTest extends AbstractPapyrusTest {
	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();

	@Rule
	public HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public ModelSetFixture model = new UMLRTModelSetFixture();

	@Rule
	public FixtureElementRule fixtureRule = new FixtureElementRule("TestModel");

	@Named("Capsule0")
	private org.eclipse.uml2.uml.Class capsule;

	/**
	 * Initializes me.
	 */
	public PseudoStateAutonamingTest() {
		super();
	}

	@Test
	public void initialDefaultName() {
		assertNoName(getPseudostate(UMLRTPseudostateKind.INITIAL));
	}

	@Test
	public void choiceDefaultName() {
		assertNoName(createPseudostate(UMLRTPseudostateKind.CHOICE));
	}

	@Test
	public void junctionDefaultName() {
		assertNoName(createPseudostate(UMLRTPseudostateKind.JUNCTION));
	}

	@Test
	public void historyDefaultName() {
		assertNoName(createPseudostate(UMLRTPseudostateKind.HISTORY, Either.ofAlternate(getCompositeState())));
	}

	@Test
	public void entryPointDefaultName() {
		assertNoName(createConnectionPoint(UMLRTConnectionPointKind.ENTRY));
	}

	@Test
	public void exitPointDefaultName() {
		assertNoName(createConnectionPoint(UMLRTConnectionPointKind.EXIT));
	}

	//
	// Test framework
	//

	/**
	 * Every test needs a new state machine.
	 */
	@Before
	public void createStateMachine() {
		CreateElementRequest create = new CreateElementRequest(capsule, UMLRTElementTypesEnumerator.RT_STATE_MACHINE);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(capsule);
		model.execute(edit.getEditCommand(create));

		// And make the state a composite
		model.execute(new AbstractTransactionalCommand(model.getEditingDomain(), "Make State1 Composite", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				getCompositeState().setComposite(true);
				return CommandResult.newOKCommandResult();
			}
		});
	}

	UMLRTCapsule getCapsule() {
		return UMLRTCapsule.getInstance(capsule);
	}

	UMLRTStateMachine getStateMachine() {
		return getCapsule().getStateMachine();
	}

	void assertNoName(UMLRTNamedElement element) {
		assertNoName(element.toUML());
	}

	void assertNoName(NamedElement element) {
		assertThat("Element has a name", element.getName(), nullValue());
		assertThat("Name is explicitly set", element.isSetName(), is(false));
	}

	UMLRTState getCompositeState() {
		return (UMLRTState) getStateMachine().getVertex("State1");
	}

	UMLRTPseudostate getPseudostate(UMLRTPseudostateKind kind) {
		return getPseudostate(kind, Either.of(getStateMachine()));
	}

	UMLRTPseudostate getPseudostate(UMLRTPseudostateKind kind, Either<UMLRTStateMachine, UMLRTState> parent) {
		@SuppressWarnings("unchecked")
		List<UMLRTVertex> vertices = parent.map(UMLRTStateMachine::getVertices, UMLRTState::getSubvertices)
				.getEither(List.class);

		return vertices.stream()
				.sequential() // Required to sensibly take the last
				.filter(UMLRTPseudostate.class::isInstance)
				.map(UMLRTPseudostate.class::cast)
				.filter(ps -> ps.getKind() == kind)
				.reduce((first, second) -> second) // Take the last, which could be newly created
				.orElseThrow(() -> new AssertionError("Could not find pseudostate of kind " + kind));
	}

	UMLRTConnectionPoint getConnectionPoint(UMLRTConnectionPointKind kind) {
		List<UMLRTConnectionPoint> connectionPoints = (kind == UMLRTConnectionPointKind.ENTRY)
				? getCompositeState().getEntryPoints()
				: getCompositeState().getExitPoints();
		if (connectionPoints.isEmpty()) {
			fail("Could not find pseudostate of kind " + kind);
		}

		return connectionPoints.get(connectionPoints.size() - 1);
	}

	UMLRTPseudostate createPseudostate(UMLRTPseudostateKind kind) {
		return createPseudostate(kind, Either.of(getStateMachine()));
	}

	UMLRTPseudostate createPseudostate(UMLRTPseudostateKind kind, Either<UMLRTStateMachine, UMLRTState> parent) {
		IElementType type;

		switch (kind) {
		case INITIAL:
			type = UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_INITIAL;
			break;
		case CHOICE:
			type = UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_CHOICE;
			break;
		case JUNCTION:
			type = UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_JUNCTION;
			break;
		case HISTORY:
			type = UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_DEEP_HISTORY;
			break;
		default:
			throw new IllegalArgumentException(kind.name());
		}

		Region container = parent.map(UMLRTStateMachine::toRegion, UMLRTState::toRegion)
				.getEither(Region.class);
		CreateElementRequest create = new CreateElementRequest(container, type);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(container);
		model.execute(edit.getEditCommand(create));

		return getPseudostate(kind, parent);
	}

	UMLRTConnectionPoint createConnectionPoint(UMLRTConnectionPointKind kind) {
		IElementType type;

		switch (kind) {
		case ENTRY:
			type = UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_ENTRY_POINT;
			break;
		case EXIT:
			type = UMLRTElementTypesEnumerator.RT_PSEUDO_STATE_EXIT_POINT;
			break;
		default:
			throw new IllegalArgumentException(kind.name());
		}

		State composite = getCompositeState().toUML();
		CreateElementRequest create = new CreateElementRequest(composite, type);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(composite);
		model.execute(edit.getEditCommand(create));

		return getConnectionPoint(kind);
	}
}
