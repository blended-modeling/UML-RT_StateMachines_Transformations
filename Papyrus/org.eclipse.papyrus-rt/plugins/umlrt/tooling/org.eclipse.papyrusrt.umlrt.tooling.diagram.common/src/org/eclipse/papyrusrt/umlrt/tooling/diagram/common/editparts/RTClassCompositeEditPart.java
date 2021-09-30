/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bugs 472885, 496304, 500743, 467545
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.AbstractInheritanceEditPolicy.INHERITANCE_ROLE;

import java.util.Optional;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeNameEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.InheritanceEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.PortContainerEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.locator.RTPortPositionLocator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTCapsuleStructureDiagramUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;


/**
 * The Class RTClassCompositeEditPart defines how an affixed Child node can be added in the Real Time Context.
 * A port can be added inside the Bounds instead of exclusively on the boundaries;
 */
public class RTClassCompositeEditPart extends ClassCompositeEditPart implements IInheritableEditPart {

	/**
	 * Instantiates a new RT class composite edit part.
	 *
	 * @param view
	 *            the view
	 */
	public RTClassCompositeEditPart(View view) {
		super(view);

	}

	@Override
	protected void createDefaultEditPolicies() {
		// This needs to be ahead of the edit-policies that process user edit gestures
		installEditPolicy(INHERITANCE_ROLE, new InheritanceEditPolicy());

		super.createDefaultEditPolicies();

		// Custom arrange behaviour
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new PortContainerEditPolicy());
	}

	/**
	 * Redefine the way the Affixed Child note is added to the EditPart by specifying the Port Position Locator as {@link RTPortPositionLocator}.
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart#addFixedChild(org.eclipse.gef.EditPart)
	 *
	 * @param childEditPart
	 * @return
	 */
	@Override
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof ClassCompositeNameEditPart) {
			((ClassCompositeNameEditPart) childEditPart).setLabel(getPrimaryShape().getNameLabel());
			return true;
		}
		if (childEditPart instanceof ClassCompositeCompartmentEditPart) {
			IFigure pane = getPrimaryShape().getCompositeCompartmentFigure();
			setupContentPane(pane); // FIXME each compartment should handle his content pane in his own way
			pane.add(((ClassCompositeCompartmentEditPart) childEditPart).getFigure());
			return true;
		}
		// Papyrus Gencode :Affixed Port locator
		if (IRTPortEditPart.isPortOnCapsule(childEditPart)) {
			IRTPortEditPart port = (IRTPortEditPart) childEditPart;
			if (hasNotationView() && getNotationView().isSetElement()) {
				IBorderItemLocator locator = new RTPortPositionLocator(
						port.getPort(), getMainFigure(),
						PositionConstants.NONE, port.getDefaultScaleFactor());

				getBorderedFigure().getBorderItemContainer().add(port.getFigure(), locator);
			}

			return true;
		}
		return false;
	}

	/**
	 * I can inherit if I have a superclass.
	 */
	@Override
	public boolean canInherit() {
		org.eclipse.uml2.uml.Class class_ = TypeUtils.as(resolveSemanticElement(), org.eclipse.uml2.uml.Class.class);
		return Optional.ofNullable(class_)
				.map(UMLRTCapsule::getInstance)
				.map(UMLRTCapsule::getSuperclass)
				.isPresent();
	}

	/**
	 * My "redefined view" is actually a redefinition, but my superclass.
	 */
	@Override
	public View getRedefinedView() {
		View result = null;

		if (isInherited()) {
			org.eclipse.uml2.uml.Class class_ = TypeUtils.as(resolveSemanticElement(), org.eclipse.uml2.uml.Class.class);
			result = Optional.ofNullable(class_)
					.map(UMLRTCapsule::getInstance)
					.map(UMLRTCapsule::getSuperclass)
					.map(UMLRTCapsule::toUML)
					.map(UMLRTCapsuleStructureDiagramUtils::getCapsuleStructureDiagram)
					.map(d -> UMLRTEditPartUtils.findView(d, VISUAL_ID, d.getElement()))
					.orElse(null);
		}

		return result;
	}
}
