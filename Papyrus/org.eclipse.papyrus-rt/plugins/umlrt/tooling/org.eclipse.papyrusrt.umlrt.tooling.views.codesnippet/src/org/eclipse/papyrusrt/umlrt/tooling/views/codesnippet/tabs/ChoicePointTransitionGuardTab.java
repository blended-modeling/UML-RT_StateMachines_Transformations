/*******************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.views.codesnippet.tabs;

import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;

/**
 * Code snippet tab for transition guard for choice point.
 * 
 * @author ysroh
 *
 */
public class ChoicePointTransitionGuardTab extends TransitionGuardTab {

	/**
	 * Choice point.
	 */
	protected Pseudostate choice;

	/**
	 * 
	 * Constructor.
	 *
	 * @param choice
	 *            Choice
	 */
	public ChoicePointTransitionGuardTab(Pseudostate choice) {
		this.choice = choice;
	}

	@Override
	public String getTitle() {
		Transition transition = (Transition) input;
		String name = transition.getName();
		if (!UML2Util.isEmpty(name)) {
			return name;
		}

		// transition has no name so use target information.
		String transitionIdentifier = " --> ";

		// check target name
		Vertex target = transition.getTarget();
		String targetName = target.getName();
		if (!UML2Util.isEmpty(targetName)) {
			return transitionIdentifier + targetName;
		}

		// use eClass name buy default.
		targetName = target.eClass().getName();
		if (target instanceof Pseudostate) {
			// use pseudo state kind for target name
			PseudostateKind kind = ((Pseudostate) target).getKind();
			targetName = kind.getName();
			targetName = targetName.substring(0, 1).toUpperCase() + targetName.substring(1);

			if ((PseudostateKind.ENTRY_POINT_LITERAL.equals(kind) || PseudostateKind.EXIT_POINT_LITERAL.equals(kind))) {
				State containingState = ((Pseudostate) target).getState();
				if (containingState.isComposite()) {
					// This is transition to a composite state so use composite
					// state name
					targetName = containingState.getName();
					if (!UML2Util.isEmpty(targetName)) {
						return transitionIdentifier + targetName;
					}
					targetName = "CompositeState";
				}
			}
		}

		return transitionIdentifier + "<" + targetName + ">";
	}

	@Override
	protected void updateLabel() {
		super.updateLabel();
		if (choice != null) {
			label.setImage(getLabelProvider().getImage(choice));
			label.setText(getLabelProvider().getText(choice));
		}
	}
}
