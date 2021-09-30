/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.creation;

import static org.eclipse.papyrusrt.junit.matchers.CommandMatchers.isExecutable;
import static org.eclipse.papyrusrt.junit.matchers.StringMatcher.nullOrEmpty;
import static org.eclipse.papyrusrt.umlrt.core.tests.creation.RelationshipValidationRule.postEdit;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.function.Supplier;

import javax.inject.Named;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test class for {@link Transition} creation.
 */
@PluginResource("/resource/Transitions.di")
public class CreateTransitionTests extends AbstractPapyrusTest {
	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();

	@Rule
	public HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	@Rule
	public FixtureElementRule fixtureRule = new FixtureElementRule();

	@Rule
	public TestRule uiThread = new UIThreadRule(true);

	@Named("Transitions")
	protected Package rootModel;
	@Named("Transitions::Transitions")
	private Class mainCapsule;
	@Named("Transitions::Transitions::StateMachine")
	private StateMachine stateMachine;
	@Named("Transitions::Transitions::StateMachine::Region")
	private Region region;
	@Named("Transitions::Transitions::StateMachine::Region::Simple1")
	private State simple1;
	@Named("Transitions::Transitions::StateMachine::Region::Simple2")
	private State simple2;
	@Named("Transitions::Transitions::StateMachine::Region::Composite1")
	private State composite1;
	@Named("Transitions::Transitions::StateMachine::Region::Composite1::ExitPoint1")
	private Pseudostate composite1Exit;
	@Named("Transitions::Transitions::StateMachine::Region::Composite1::EntryPoint1")
	private Pseudostate composite1Entry;
	@Named("Transitions::Transitions::StateMachine::Region::Composite2")
	private State composite2;
	@Named("Transitions::Transitions::StateMachine::Region::Composite2::ExitPoint1")
	private Pseudostate composite2Exit;
	@Named("Transitions::Transitions::StateMachine::Region::Composite2::EntryPoint1")
	private Pseudostate composite2Entry;
	@Named("Transitions::Transitions::StateMachine::Region::InsideState")
	private State insideState;
	@Named("Transitions::Transitions::StateMachine::Region::InsideState::Region1")
	private Region insideRegion;
	@Named("Transitions::Transitions::StateMachine::Region::InsideState::Region1::simple")
	private State insideSimpleState;
	@Named("Transitions::Transitions::StateMachine::Region::InsideState::Region1::composite")
	private State insideCompositeState;

	private IElementType transitionType;

	/**
	 * Init test class.
	 */
	@Before
	public void initCreateTransitionsTests() throws Exception {
		// force load of the element type registry. Will need to load in in UI thread
		// (provided by the UIThread rule) because of some advice in communication diag:
		// see org.eclipse.gmf.tooling.runtime.providers.DiagramElementTypeImages
		transitionType = UMLElementTypes.TRANSITION;
		Assert.assertNotNull("element type should not be null", transitionType);
	}

	@Test
	public void fromSimpleToSimple() throws Exception {
		// REQ 1) Source and target are both simple states. Then an external transitions is created with the selected states as source and target.

		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, simple1, simple2, region);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getSource(), equalTo(creationParameters.source));
			assertThat(transition.getTarget(), equalTo(creationParameters.target));
			assertThat(transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));
			assertThat(transition.getContainer(), equalTo(creationParameters.container));
		}));
	}

	@Test
	public void fromSimpleToItself() throws Exception {
		// REQ 4) If source and target is the same simple state, then an external self-transition should be created.
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, simple1, simple1, region);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getSource(), equalTo(creationParameters.source));
			assertThat(transition.getTarget(), equalTo(creationParameters.target));
			assertThat(transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));
			assertThat(transition.getContainer(), equalTo(creationParameters.container));
		}));
	}

	@Test
	public void fromCompositeToSimple() throws Exception {
		// REQ 2) If the selected source is a composite state, then an exit point pseudo state for that composite state should be
		// automatically be created and be used as the source for the transition.
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, composite1, simple1, region);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getContainer(), equalTo(creationParameters.forcedContainer));
			assertThat(transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));

			assertThat(transition.getSource(), instanceOf(Pseudostate.class));
			Pseudostate sourcePseudoState = (Pseudostate) transition.getSource();
			assertThat(sourcePseudoState.eContainer(), equalTo(creationParameters.source));
			assertThat(sourcePseudoState.getKind(), equalTo(PseudostateKind.EXIT_POINT_LITERAL));
			assertThat(sourcePseudoState.getName(), nullOrEmpty());

			assertThat(transition.getTarget(), equalTo(creationParameters.target));
		}));
	}

	@Test
	public void fromSimpleToComposite() throws Exception {
		// REQ 3) If the selected target is a composite state, then an entry point pseudo state for that composite state should be
		// automatically be created and be used as the target for the transition.
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, simple1, composite1, region);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getContainer(), equalTo(creationParameters.forcedContainer));
			assertThat(transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));

			assertThat(transition.getSource(), equalTo(creationParameters.source));

			assertThat(transition.getTarget(), instanceOf(Pseudostate.class));
			Pseudostate targetPseudoState = (Pseudostate) transition.getTarget();
			assertThat(targetPseudoState.eContainer(), equalTo(creationParameters.target));
			assertThat(targetPseudoState.getName(), nullOrEmpty());
			assertThat(targetPseudoState.getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));
		}));
	}

	@Test
	public void fromCompositeToComposite() throws Exception {
		// REQ 2) If the selected source is a composite state, then an exit point pseudo state for that composite state should be
		// automatically be created and be used as the source for the transition.
		// REQ 3) If the selected target is a composite state, then an entry point pseudo state for that composite state should be
		// automatically be created and be used as the target for the transition.
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, composite1, composite2, region);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getContainer(), equalTo(creationParameters.forcedContainer));
			assertThat(transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));

			assertThat(transition.getSource(), instanceOf(Pseudostate.class));
			Pseudostate sourcePseudoState = (Pseudostate) transition.getSource();
			assertThat(sourcePseudoState.eContainer(), equalTo(creationParameters.source));
			assertThat(sourcePseudoState.getKind(), equalTo(PseudostateKind.EXIT_POINT_LITERAL));


			assertThat(transition.getTarget(), instanceOf(Pseudostate.class));
			Pseudostate targetPseudoState = (Pseudostate) transition.getTarget();
			assertThat(targetPseudoState.eContainer(), equalTo(creationParameters.target));
			assertThat(targetPseudoState.getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));
		}));
	}

	@Test
	public void fromCompositeToItselfFromOutside() throws Exception {
		// 5) If source and target is the same composite state in the context of its parent, i.e. on the "outside"
		// of the state, then an external self-transition shall be created and exit point and entry point pseudo states
		// shall automatically be created for the composite state, and used as source respectively target of
		// the transition, i.e. corresponding to a combination of case 2 and 3.
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, composite1, composite1, region);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getContainer(), equalTo(creationParameters.forcedContainer));
			assertThat(transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));

			assertThat(transition.getSource(), instanceOf(Pseudostate.class));
			Pseudostate sourcePseudoState = (Pseudostate) transition.getSource();
			assertThat(sourcePseudoState.eContainer(), equalTo(creationParameters.source));
			assertThat(sourcePseudoState.getKind(), equalTo(PseudostateKind.EXIT_POINT_LITERAL));

			assertThat(transition.getTarget(), instanceOf(Pseudostate.class));
			Pseudostate targetPseudoState = (Pseudostate) transition.getTarget();
			assertThat(targetPseudoState.eContainer(), equalTo(creationParameters.target));
			assertThat(targetPseudoState.getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));
		}));
	}

	@Test
	public void fromCompositeToItselfFromInside() throws Exception {
		// 6) If the source and target is the same composite state in the context of the state itself, i.e. on the "inside"
		// of the state selecting the border of the state itself, then an local self-transition shall be created and
		// exit point and entry point pseudo states shall automatically be created for the composite state, and used
		// as source respectively target of the transition, i.e. corresponding to a combination of case 2 and 3.
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(insideRegion, insideState, insideState, insideRegion);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getContainer(), equalTo(creationParameters.forcedContainer));
			assertThat(transition.getKind(), equalTo(TransitionKind.LOCAL_LITERAL));

			assertThat(transition.getSource(), instanceOf(Pseudostate.class));
			Pseudostate sourcePseudoState = (Pseudostate) transition.getSource();
			assertThat(sourcePseudoState.eContainer(), equalTo(creationParameters.source));
			assertThat(sourcePseudoState.getKind(), equalTo(PseudostateKind.EXIT_POINT_LITERAL));

			assertThat(transition.getTarget(), instanceOf(Pseudostate.class));
			Pseudostate targetPseudoState = (Pseudostate) transition.getTarget();
			assertThat(targetPseudoState.eContainer(), equalTo(creationParameters.target));
			assertThat(targetPseudoState.getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));
		}));
	}

	@Test
	public void fromCompositeToInternalStateFromInside() throws Exception {
		// 7) If the source of the transition is a composite state in the context of the state itself,
		// i.e. on the "inside" of the state selecting the border of the state as source, and the target
		// is a, composite or simple, state nested inside the state, then a local transition shall be
		// created. An entry point pseudo state shall be create automatically as the source for the
		// transition. If the target state is a composite, then case 3 is of course applicable.
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(insideRegion, insideState, insideSimpleState, insideRegion);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getContainer(), equalTo(creationParameters.container));
			assertThat(transition.getKind(), equalTo(TransitionKind.LOCAL_LITERAL));

			assertThat(transition.getSource(), instanceOf(Pseudostate.class));
			Pseudostate sourcePseudoState = (Pseudostate) transition.getSource();
			assertThat(sourcePseudoState.eContainer(), equalTo(creationParameters.source));
			assertThat(sourcePseudoState.getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));

			assertThat(transition.getTarget(), equalTo(creationParameters.target));
		}));
	}

	@Test
	public void fromCompositeToInternalCompositeStateFromInside() throws Exception {
		// 7) If the source of the transition is a composite state in the context of the state itself,
		// i.e. on the "inside" of the state selecting the border of the state as source, and the target
		// is a, composite or simple, state nested inside the state, then a local transition shall be
		// created. An entry point pseudo state shall be create automatically as the source for the
		// transition. If the target state is a composite, then case 3 is of course applicable.
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(insideRegion, insideState, insideCompositeState, insideRegion);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getContainer(), equalTo(creationParameters.container));
			assertThat(transition.getKind(), equalTo(TransitionKind.LOCAL_LITERAL));

			assertThat(transition.getSource(), instanceOf(Pseudostate.class));
			Pseudostate sourcePseudoState = (Pseudostate) transition.getSource();
			assertThat(sourcePseudoState.eContainer(), equalTo(creationParameters.source));
			assertThat(sourcePseudoState.getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));

			assertThat(transition.getTarget(), instanceOf(Pseudostate.class));
			Pseudostate targetPseudoState = (Pseudostate) transition.getTarget();
			assertThat(targetPseudoState.eContainer(), equalTo(creationParameters.target));
			assertThat(targetPseudoState.getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));
		}));
	}

	@Test
	public void fromInternalStateToCompositeFromInside() throws Exception {
		// 8) If the target of the transition is a composite state in the context of the state itself,
		// i.e. on the "inside" of the state selecting the border of the state as target, and the source
		// is a, composite or simple, state nested inside the state, then a local transition shall be
		// created. An exit point pseudo state shall be created automatically as the target of the
		// transition. If the source state is a composite, then case 2 is of course applicable.
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(insideRegion, insideSimpleState, insideState, insideRegion);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getContainer(), equalTo(creationParameters.container));
			assertThat(transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));

			assertThat(transition.getSource(), equalTo(creationParameters.source));

			assertThat(transition.getTarget(), instanceOf(Pseudostate.class));
			Pseudostate targetPseudoState = (Pseudostate) transition.getTarget();
			assertThat(targetPseudoState.eContainer(), equalTo(creationParameters.target));
			assertThat(targetPseudoState.getKind(), equalTo(PseudostateKind.EXIT_POINT_LITERAL));
		}));
	}

	@Test
	public void fromInternalCompositeStateToCompositeFromInside() throws Exception {
		// 8) If the target of the transition is a composite state in the context of the state itself,
		// i.e. on the "inside" of the state selecting the border of the state as target, and the source
		// is a, composite or simple, state nested inside the state, then a local transition shall be
		// created. An exit point pseudo state shall be created automatically as the target of the
		// transition. If the source state is a composite, then case 2 is of course applicable.
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(insideRegion, insideCompositeState, insideState, insideRegion);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getContainer(), equalTo(creationParameters.container));
			assertThat(transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));

			assertThat(transition.getSource(), instanceOf(Pseudostate.class));
			Pseudostate sourcePseudoState = (Pseudostate) transition.getSource();
			assertThat(sourcePseudoState.eContainer(), equalTo(creationParameters.source));
			assertThat(sourcePseudoState.getKind(), equalTo(PseudostateKind.EXIT_POINT_LITERAL));

			assertThat(transition.getTarget(), instanceOf(Pseudostate.class));
			Pseudostate targetPseudoState = (Pseudostate) transition.getTarget();
			assertThat(targetPseudoState.eContainer(), equalTo(creationParameters.target));
			assertThat(targetPseudoState.getKind(), equalTo(PseudostateKind.EXIT_POINT_LITERAL));
		}));
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// Existing Pseudo states
	/////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void fromExitPointToSelfEntryPointFromOutside() throws Exception {
		// 5) If source and target is the same composite state in the context of its parent, i.e. on the "outside"
		// of the state, then an external self-transition shall be created and exit point and entry point pseudo states
		// shall automatically be created for the composite state, and used as source respectively target of
		// the transition, i.e. corresponding to a combination of case 2 and 3.
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, composite1Exit, composite1Entry, region);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getContainer(), equalTo(creationParameters.container));
			assertThat(transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));

			assertThat(transition.getSource(), instanceOf(Pseudostate.class));
			Pseudostate sourcePseudoState = (Pseudostate) transition.getSource();
			assertThat(sourcePseudoState, equalTo(creationParameters.source));

			assertThat(transition.getTarget(), instanceOf(Pseudostate.class));
			Pseudostate targetPseudoState = (Pseudostate) transition.getTarget();
			assertThat(targetPseudoState, equalTo(creationParameters.target));
		}));
	}

	@Test
	public void fromExitPointToSelfFromOutside() throws Exception {
		// 5) If source and target is the same composite state in the context of its parent, i.e. on the "outside"
		// of the state, then an external self-transition shall be created and exit point and entry point pseudo states
		// shall automatically be created for the composite state, and used as source respectively target of
		// the transition, i.e. corresponding to a combination of case 2 and 3.
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, composite1Exit, composite1, region);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getContainer(), equalTo(creationParameters.container));
			assertThat(transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));

			assertThat(transition.getSource(), instanceOf(Pseudostate.class));
			Pseudostate sourcePseudoState = (Pseudostate) transition.getSource();
			assertThat(sourcePseudoState, equalTo(creationParameters.source));

			assertThat(transition.getTarget(), instanceOf(Pseudostate.class));
			Pseudostate targetPseudoState = (Pseudostate) transition.getTarget();
			assertThat(targetPseudoState.eContainer(), equalTo(creationParameters.target));
			assertThat(targetPseudoState.getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));
		}));
	}

	@Test
	public void fromExitPointToEntryPointFromOutside() throws Exception {
		// REQ 2) If the selected source is a composite state, then an exit point pseudo state for that composite state should be
		// automatically be created and be used as the source for the transition.
		// REQ 3) If the selected target is a composite state, then an entry point pseudo state for that composite state should be
		// automatically be created and be used as the target for the transition.
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, composite1Exit, composite2Entry, region);
		runRelationshipCreationTest(creationParameters, transitionType, true, postEdit((relationship, parameters) -> {
			assertThat(relationship, instanceOf(Transition.class));
			Transition transition = (Transition) relationship;
			assertThat(transition.getContainer(), equalTo(creationParameters.container));
			assertThat(transition.getKind(), equalTo(TransitionKind.EXTERNAL_LITERAL));

			assertThat(transition.getSource(), instanceOf(Pseudostate.class));
			Pseudostate sourcePseudoState = (Pseudostate) transition.getSource();
			assertThat(sourcePseudoState, equalTo(creationParameters.source));
			assertThat(sourcePseudoState.getKind(), equalTo(PseudostateKind.EXIT_POINT_LITERAL));

			assertThat(transition.getTarget(), instanceOf(Pseudostate.class));
			Pseudostate targetPseudoState = (Pseudostate) transition.getTarget();
			assertThat(targetPseudoState, equalTo(creationParameters.target));
			assertThat(targetPseudoState.getKind(), equalTo(PseudostateKind.ENTRY_POINT_LITERAL));
		}));
	}

	@Test
	public void fromEntryPointToSelfExitPointFromOutside() throws Exception {
		// 5) If source and target is the same composite state in the context of its parent, i.e. on the "outside"
		// of the state, then an external self-transition shall be created and exit point and entry point pseudo states
		// shall automatically be created for the composite state, and used as source respectively target of
		// the transition, i.e. corresponding to a combination of case 2 and 3.
		// should not be possible, as we are exiting an entry here
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, composite1Entry, composite1Exit, region);
		runRelationshipCreationTest(creationParameters, transitionType, false, null);
	}

	@Test
	public void fromEntryPointToSelfFromOutside() throws Exception {
		// 5) If source and target is the same composite state in the context of its parent, i.e. on the "outside"
		// of the state, then an external self-transition shall be created and exit point and entry point pseudo states
		// shall automatically be created for the composite state, and used as source respectively target of
		// the transition, i.e. corresponding to a combination of case 2 and 3.
		// should not be possible, as we are exiting an entry here
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, composite1Entry, composite1, region);
		runRelationshipCreationTest(creationParameters, transitionType, false, null);
	}

	@Test
	public void fromEntryPointToExitPointFromOutside() throws Exception {
		// REQ 2) If the selected source is a composite state, then an exit point pseudo state for that composite state should be
		// automatically be created and be used as the source for the transition.
		// REQ 3) If the selected target is a composite state, then an entry point pseudo state for that composite state should be
		// automatically be created and be used as the target for the transition.
		// should not be possible here, due to kind
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, composite1Entry, composite2Exit, region);
		runRelationshipCreationTest(creationParameters, transitionType, false, null);
	}

	@Test
	public void fromEntryPointToEntryPointFromOutside() throws Exception {
		// REQ 2) If the selected source is a composite state, then an exit point pseudo state for that composite state should be
		// automatically be created and be used as the source for the transition.
		// REQ 3) If the selected target is a composite state, then an entry point pseudo state for that composite state should be
		// automatically be created and be used as the target for the transition.
		// should not be possible here, due to kind
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, composite1Entry, composite2Entry, region);
		runRelationshipCreationTest(creationParameters, transitionType, false, null);
	}

	@Test
	public void fromExitPointToExitPointFromOutside() throws Exception {
		// REQ 2) If the selected source is a composite state, then an exit point pseudo state for that composite state should be
		// automatically be created and be used as the source for the transition.
		// REQ 3) If the selected target is a composite state, then an entry point pseudo state for that composite state should be
		// automatically be created and be used as the target for the transition.
		// should not be possible here, due to kind
		RelationshipCreationParameters creationParameters = new RelationshipCreationParameters(region, composite1Exit, composite2Exit, region);
		runRelationshipCreationTest(creationParameters, transitionType, false, null);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// Utilities
	//////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @param creationParameters
	 * @param transitionType2
	 * @param b
	 * @param postEdit
	 * @throws Exception
	 */
	protected void runRelationshipCreationTest(RelationshipCreationParameters creationParameters, IElementType type, boolean canCreate, Supplier<RelationshipValidationRule> postEdit) throws Exception {
		Assert.assertTrue("Editor should not be dirty before test", !isDirty());
		Command command = getCreateRelationshipCommand(creationParameters, type, canCreate);

		// command has been tested when created. Runs the test if it is possible
		if (canCreate) {
			assertThat(command, isExecutable());
			RelationshipValidationRule rule = postEdit.get();
			getCommandStack().execute(command);
			Element newRelationship = getExecutionResult(command, Element.class);
			rule.validatePostEdition(newRelationship, creationParameters);

			getCommandStack().undo();
			Assert.assertTrue("Editor should not be dirty after undo", !isDirty());

			getCommandStack().redo();
			rule.validatePostEdition(newRelationship, creationParameters);

		} else {
			assertThat(command, not(isExecutable()));
		}
	}

	/**
	 * @return
	 */
	protected boolean isDirty() {
		return getCommandStack().canUndo();
	}

	/**
	 * @return
	 */
	protected CommandStack getCommandStack() {
		return modelSet.getEditingDomain().getCommandStack();
	}

	@SuppressWarnings("unchecked")
	protected <T> T getExecutionResult(Command command, java.lang.Class<T> expected) {
		Collection<?> results = command.getResult();
		if (results.size() > 0) {
			for (Object result : results) {
				if (TypeUtils.as(result, expected) != null) {
					return (T) result;
				}
			}
		}
		return null;
	}

	protected Command getCreateRelationshipCommand(RelationshipCreationParameters parameters, IElementType type, boolean canCreate) throws Exception {
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(parameters.source);
		CreateRelationshipRequest request = new CreateRelationshipRequest(modelSet.getEditingDomain(), parameters.forcedContainer, parameters.source, parameters.target, type, null);
		// set Parameters

		ICommand command = elementEditService.getEditCommand(request);
		if (command != null) {
			return GMFtoEMFCommandWrapper.wrap(command);
		}
		return null;
	}

	/**
	 * After a possibly failed test, unwinds the undo stack as much as possible
	 * to try to return to a clean editor.
	 */
	@After
	public void unwindUndoStack() {
		CommandStack stack = getCommandStack();
		while (stack.canUndo()) {
			stack.undo();
		}
	}

}
