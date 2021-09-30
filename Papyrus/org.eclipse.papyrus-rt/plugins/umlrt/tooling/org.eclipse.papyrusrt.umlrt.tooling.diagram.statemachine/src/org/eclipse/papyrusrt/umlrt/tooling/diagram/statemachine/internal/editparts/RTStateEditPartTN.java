/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.notation.Compartment;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateEditPartTN;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.InheritanceEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.locator.RTPortPositionLocator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.DrawFigureUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editpolicies.RTStateTNSemanticEditPolicy;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.uml2.uml.Element;

/**
 * Custom top state (frame) edit-part for UML-RT state machine diagrams.
 */
public class RTStateEditPartTN extends CustomStateEditPartTN implements IStateMachineInheritableEditPart {

	/**
	 * Initializes me with my notation view.
	 *
	 * @param view
	 *            my notation view
	 */
	public RTStateEditPartTN(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		// This needs to be ahead of the edit-policies that process user edit gestures
		installEditPolicy(INHERITANCE_ROLE, new InheritanceEditPolicy());

		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new RTStateTNSemanticEditPolicy());
	}

	// The top state of the nested state machine diagram is always a real (re-)definition,
	// as for the state machine, itself. So there's no special semantic object
	// resolution algorithm as for other inheritable edit-parts

	@Override
	public UMLRTNamedElement getRedefinitionContext(UMLRTNamedElement element) {
		Optional<UMLRTNamedElement> mine = getUMLRTElement();
		return (mine.isPresent() && mine.get().redefines(element))
				? element // The top state of the diagram is the redefinition context for the diagram
				: IStateMachineInheritableEditPart.super.getRedefinitionContext(element);
	}

	/**
	 * Overridden to install the {@link RTPortPositionLocator} as the constraint
	 * for connection point figures.
	 */
	@Override
	protected boolean addFixedChild(EditPart childEditPart) {
		boolean result;

		if (IRTPseudostateEditPart.isConnectionPoint(childEditPart)) {
			IRTPseudostateEditPart connPt = (IRTPseudostateEditPart) childEditPart;
			if (hasNotationView() && getNotationView().isSetElement()) {
				IBorderItemLocator locator = new RTPortPositionLocator(
						(Element) connPt.resolveSemanticElement(), getMainFigure(),
						PositionConstants.NONE, connPt.getDefaultScaleFactor());

				getBorderedFigure().getBorderItemContainer().add(connPt.getFigure(), locator);
			}

			result = true;
		} else {
			result = super.addFixedChild(childEditPart);
		}

		return result;
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart#refreshBackgroundColor()
	 *
	 */
	@Override
	protected void refreshBackgroundColor() {
		// customize background as being the background color if the state is empty.
		// when no compartment is displayed, then the whole state figure should use the label name color by default
		if (getPrimaryView() != null && !getPrimaryView().getVisibleChildren().stream().filter(Compartment.class::isInstance).findFirst().isPresent()) {
			String labelColor = NotationUtils.getStringValue(getPrimaryView(), NAME_BACKGROUND_COLOR, null);
			if (labelColor != null && !"-1".equals(labelColor)) {
				// Set color of the Name Label background
				setBackgroundColor(DrawFigureUtils.getColorFromString(labelColor));
			} else {
				super.refreshBackgroundColor();
			}
		} else {
			super.refreshBackgroundColor();
		}
	}

}
