/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.creation;

import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IValidationRule;
import org.eclipse.papyrusrt.umlrt.core.utils.RegionUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Capsule;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTRegion;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTStateMachine;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Assert;

/**
 * {@link IValidationRule} for {@link RTStateMachine} created on {@link Capsule}
 */
public class CreateRTStateMachineOnCapsuleValidation implements IValidationRule {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validatePostEdition(Element targetContainer, Object[] commandResults) throws Exception {
		// state machine should be created with one region, one initial state, and a diagram of RT state machine should also be created
		// 1. check state machine creation
		// 1.1 check name
		// 1.2 check stereotype application
		// 1.3 check the constraint on isrentrant
		// 1.4 check the state machine is not passive (RTStateMachine.isPassive = false)
		// 1.5 check the behavior classifier containement feature from Capsule
		StateMachine newStateMachine = getStateMachine(targetContainer, commandResults);
		Assert.assertEquals("State machine name is wrong", "StateMachine", newStateMachine.getName());
		Assert.assertNotNull("State machine should have RTStateMachine stereotype applied", UMLUtil.getStereotypeApplication(newStateMachine, RTStateMachine.class));
		Assert.assertFalse("State machine should not be reentrant", newStateMachine.isReentrant());
		Assert.assertFalse("State machine should not be passive", UMLUtil.getStereotypeApplication(newStateMachine, RTStateMachine.class).isPassive());
		Assert.assertTrue("State machine should be contained via the ownedBehavior feature", ((org.eclipse.uml2.uml.Class) targetContainer).getOwnedBehaviors().contains(newStateMachine));
		Assert.assertEquals("State machine should be referenced in the behaviorClassifier feature", newStateMachine, ((org.eclipse.uml2.uml.Class) targetContainer).getClassifierBehavior());

		// 2. check region is there, with stereotype RTRegion
		// 2.1 check presence and unicity of the region
		// 2.2 check the name of the new region
		// 2.3 check the stereotype application of RTRegion
		Assert.assertEquals("State machine should have one and only one region", 1, newStateMachine.getRegions().size());
		Region region = newStateMachine.getRegions().get(0);
		Assert.assertEquals("Region name is wrong", "Region", region.getName());
		Assert.assertNotNull("Region should be stererotyped by RTRegion", UMLUtil.getStereotypeApplication(region, RTRegion.class));

		// 3. check initial state (pseudo State) in the region unamed pseudo state
		Assert.assertEquals("Region should have one initial pseudoState", 1, RegionUtils.getInitialPseudoStates(region).size());
		Vertex initialPseudoState = RegionUtils.getInitialPseudoStates(region).get(0);
		Assert.assertTrue("Element should be an initial state", initialPseudoState instanceof Pseudostate);
		Assert.assertEquals("Initial state should be named initial", null, ((Pseudostate) initialPseudoState).getName());

		// 4. check initial state (State) in the region named "State1"
		Assert.assertEquals("Region should have one and only one initial state", 1, RegionUtils.getStates(region).size());
		State state = RegionUtils.getStates(region).get(0);
		Assert.assertTrue("Element should be an initial state", state instanceof State);
		Assert.assertEquals("Initial state should be named initial", "State1", state.getName());

		// 5. check initial transition in the region named "initial" from pseudoState initial --> first state "State1"
		Assert.assertEquals("Region should have one transition", 1, region.getTransitions().size());
		Transition transition = region.getTransitions().get(0);
		Assert.assertTrue("Initial Pseudo State is the source of the initial transition", transition.getSource().equals(initialPseudoState));
		Assert.assertTrue("Initial State State1 is the target of the initial transition", transition.getTarget().equals(state));
		Assert.assertEquals("Initial transition should be named initial", "Initial", transition.getName());


		// 6. check a diagram has been created on the state machine
		// 6.1. check the diagram is unique
		// 6.2. check its name
		// 6.3. check its type - to be implemented
		// 6.4. check the content - to be implemented
		List<Diagram> diagrams = DiagramUtils.getAssociatedDiagrams(newStateMachine, newStateMachine.eResource().getResourceSet());
		Assert.assertEquals("There should be one diagram representing the state machine", 1, diagrams.size());
		Diagram diagram = diagrams.get(0);
		Assert.assertEquals("RTState machine diagram has wrong name", null, diagram.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validatePostUndo(Element targetContainer, Object[] commandResults) throws Exception {
		StateMachine newStateMachine = getStateMachine(targetContainer, commandResults);
		Assert.assertNotEquals("State machine should be contained via the behaviorClassifier feature", newStateMachine, ((org.eclipse.uml2.uml.Class) targetContainer).getClassifierBehavior());

	}

	protected StateMachine getStateMachine(Element targetContainer, Object[] commandResults) {
		if (!(targetContainer instanceof org.eclipse.uml2.uml.Class)) {
			fail("targetcontainer is supposed to be a Class");
		}
		// there should be the operation in the command result
		EObject result = null;
		if (commandResults.length > 0 && commandResults[0] instanceof EObject) {
			result = (EObject) commandResults[0];
		} else {
			fail("impossible to get result from the command");
		}
		Assert.assertTrue("Result should be a state machine", result instanceof StateMachine);
		return (StateMachine) result;
	}

}
