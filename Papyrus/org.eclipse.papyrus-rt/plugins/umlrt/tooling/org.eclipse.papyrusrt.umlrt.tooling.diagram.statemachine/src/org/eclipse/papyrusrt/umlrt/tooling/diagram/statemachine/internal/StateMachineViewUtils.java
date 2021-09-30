/*****************************************************************************
 * Copyright (c) 2017 EclipseSource GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Remi Schnekenburger - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPartTN;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;

/**
 * Utility class for graphical representation of RT state machines.
 */
public final class StateMachineViewUtils {

	/**
	 * Constructor.
	 */
	private StateMachineViewUtils() {
		// utility class
	}

	/**
	 * Get the semantic hint for a transition as edge.
	 *
	 * @return semantic hint the semantic hint for {@link Transition} viewed as edges.
	 */
	public static String getTransitionHint() {
		IHintedType type = (IHintedType) UMLElementTypes.Transition_Edge;
		return type.getSemanticHint();
	}

	/**
	 * Returns the main region (and the only region) represented in the specified state machine diagram.
	 * 
	 * @param diagram
	 *            the diagram for which the region is looked for.
	 * @return the only region represented in the diagram or <code>null</code> if none was found.
	 */
	@SuppressWarnings("unchecked")
	public static Region getRepresentedRegion(Diagram diagram) {
		Optional<View> mainView = diagram.getChildren().stream().map(View.class::cast).filter(e -> StateMachineEditPart.VISUAL_ID.equals(((View) e).getType())).findAny();
		if (mainView.isPresent()) {
			EObject main = mainView.get().getElement();
			if (main instanceof StateMachine) {
				// should compare here the root definition of the state machine
				return UMLRTExtensionUtil.getRootDefinition((StateMachine) main).getRegions().get(0);
			}
		} else {
			mainView = diagram.getChildren().stream().map(View.class::cast).filter(e -> RTStateEditPartTN.VISUAL_ID.equals(((View) e).getType())).findAny();
			if (mainView.isPresent()) {
				EObject main = mainView.get().getElement();
				if (main instanceof State) {
					return UMLRTExtensionUtil.getRootDefinition((State) main).getRegions().get(0);
				}
			}
		}
		return null;
	}

}
