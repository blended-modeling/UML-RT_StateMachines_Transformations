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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.AbstractInheritanceEditPolicy.INHERITANCE_ROLE;

import java.util.Optional;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateMachineEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.InheritanceEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTSemanticEditPolicy;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;

/**
 * Custom state machine frame edit-part for UML-RT state machine diagrams.
 */
public class RTStateMachineEditPart extends CustomStateMachineEditPart implements IStateMachineInheritableEditPart {

	/**
	 * Initializes me with my notation view.
	 *
	 * @param view
	 *            my notation view
	 */
	public RTStateMachineEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		// This needs to be ahead of the edit-policies that process user edit gestures
		installEditPolicy(INHERITANCE_ROLE, new InheritanceEditPolicy());

		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new RTSemanticEditPolicy());
	}

	// The state machine, if it has a diagram, is always a real (re-)definition.
	// So, there's no special semantic object resolution algorithm as for other
	// inheritable edit-parts

	@Override
	public UMLRTNamedElement getRedefinitionContext(UMLRTNamedElement element) {
		Optional<UMLRTNamedElement> mine = getUMLRTElement();
		return (mine.isPresent() && mine.get().redefines(element))
				? element // The state machine is the redefinition context for the diagram
				: IStateMachineInheritableEditPart.super.getRedefinitionContext(element);
	}

}
