/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *  
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateDeepHistoryEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateDeepHistoryFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJunctionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJunctionFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateCompartmentEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionGuardEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionNameEditPart;
import org.eclipse.papyrusrt.umlrt.core.utils.ModelUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.StateMachineUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTListItemEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.providers.AbstractRTEditPartProvider;
import org.eclipse.uml2.uml.StateMachine;


/**
 * Provider of custom edit-parts for the UML-RT state machine diagram.
 */
public class RTStateMachineEditPartProvider extends AbstractRTEditPartProvider {

	/**
	 * Default constructor.
	 */
	public RTStateMachineEditPartProvider() {
		super(PackageEditPart.MODEL_ID);

		// Nodes
		nodeMap.put(StateMachineEditPart.VISUAL_ID, always(RTStateMachineEditPart.class));
		nodeMap.put(PseudostateEntryPointEditPart.VISUAL_ID, always(RTPseudostateEntryPointEditPart.class));
		nodeMap.put(PseudostateExitPointEditPart.VISUAL_ID, always(RTPseudostateExitPointEditPart.class));
		nodeMap.put(PseudostateChoiceEditPart.VISUAL_ID, always(RTPseudostateChoiceEditPart.class));
		nodeMap.put(PseudostateJunctionEditPart.VISUAL_ID, always(RTPseudostateJunctionEditPart.class));
		nodeMap.put(PseudostateDeepHistoryEditPart.VISUAL_ID, always(RTPseudostateDeepHistoryEditPart.class));
		nodeMap.put(PseudostateInitialEditPart.VISUAL_ID, always(RTPseudostateInitialEditPart.class));
		nodeMap.put(StateEditPart.VISUAL_ID, always(RTStateEditPart.class));
		nodeMap.put(StateEditPartTN.VISUAL_ID, always(RTStateEditPartTN.class));
		nodeMap.put(RegionEditPart.VISUAL_ID, always(RTRegionEditPart.class));
		nodeMap.put(RegionCompartmentEditPart.VISUAL_ID, always(RTRegionCompartmentEditPart.class));
		nodeMap.put(TransitionNameEditPart.VISUAL_ID, always(RTTransitionNameEditPart.class));
		nodeMap.put(TransitionGuardEditPart.VISUAL_ID, always(RTTransitionGuardEditPart.class));
		nodeMap.put(StateCompartmentEditPart.VISUAL_ID, always(RTStateCompartmentEditPart.class));
		nodeMap.put(StateCompartmentEditPartTN.VISUAL_ID, always(RTStateCompartmentEditPartTN.class));
		nodeMap.put(StateMachineCompartmentEditPart.VISUAL_ID, always(RTStateMachineCompartmentEditPart.class));

		// Labels
		nodeMap.put(PseudostateChoiceFloatingLabelEditPart.VISUAL_ID, always(RTPseudostateChoiceLabelEditPart.class));
		nodeMap.put(PseudostateInitialFloatingLabelEditPart.VISUAL_ID, always(RTPseudostateInitialLabelEditPart.class));
		nodeMap.put(PseudostateDeepHistoryFloatingLabelEditPart.VISUAL_ID, always(RTPseudostateDeepHistoryLabelEditPart.class));
		nodeMap.put(PseudostateJunctionFloatingLabelEditPart.VISUAL_ID, always(RTPseudostateJunctionLabelEditPart.class));
		nodeMap.put(PseudostateEntryPointFloatingLabelEditPart.VISUAL_ID, always(RTPseudostateEntryPointLabelEditPart.class));
		nodeMap.put(PseudostateExitPointFloatingLabelEditPart.VISUAL_ID, always(RTPseudostateExitPointLabelEditPart.class));

		// List items
		nodeMap.put(RTStateMachineDiagramVisualID.INTERNAL_TRANSITION_LABEL, always(RTListItemEditPart.class));

		// Edges
		edgeMap.put(TransitionEditPart.VISUAL_ID, always(RTTransitionEditPart.class));
	}

	/**
	 * Is the {@code element} in a real-time state machine?
	 */
	@Override
	protected boolean accept(EObject element) {
		return isInRTStateMachine(element);
	}

	private boolean isInRTStateMachine(EObject element) {
		return ModelUtils.selfAndContainersOf(element)
				.filter(StateMachine.class::isInstance).map(StateMachine.class::cast)
				.anyMatch(StateMachineUtils::isRTStateMachine);
	}
}
