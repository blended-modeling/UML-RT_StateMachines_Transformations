/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net  - Initial API and implementation
 *   Christian W. Damus - bugs 477819, 482599, 472885, 489939, 496304, 500743, 467545, 507552
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.AbstractInheritanceEditPolicy.INHERITANCE_ROLE;
import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.DrawFigureUtils.getLighterAlpha;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PropertyPartEditPartCN;
import org.eclipse.papyrusrt.umlrt.core.types.advice.CapsulePartEditHelperAdvice;
import org.eclipse.papyrusrt.umlrt.core.utils.MultiplicityElementAdapter;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPropertyUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTCommandUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.providers.RTEditPartProvider;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.RTPropertyPartFigure;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.InheritanceEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.PortContainerEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTSemanticEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.locator.RTPortPositionLocator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.util.ReificationListener;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Property;

/**
 * This Class redefines the PropertyPartEditPartCN for the Real Time Context
 * This editPart is used to control the CapsulePart.
 *
 * @see {@link RTEditPartProvider}
 *
 * @author Céline JANSSENS
 *
 */
public class RTPropertyPartEditPart extends PropertyPartEditPartCN implements IInheritableEditPart {

	/**
	 *
	 */
	private static final int LAYER_OPACITY = 255;

	/**
	 * Horizontal Offset
	 */
	private static final int X_OFFSET = 3;

	/**
	 * Vertical Offset
	 */
	private static final int Y_OFFSET = X_OFFSET;

	/**
	 * Number of Layer
	 */
	private static final int LAYER_NUMBER = 1;

	/**
	 * Path of the hash pattern to use
	 */
	private static final String PATTERN_PATH = "/icons/hash_pattern_grey.png";//$NON-NLS-1$

	private MultiplicityElementAdapter multiplicityValueListener;

	private ReificationListener reificationListener = this::reificationStateChanged;


	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public RTPropertyPartEditPart(final View view) {
		super(view);
	}

	@Override
	public EObject resolveSemanticElement() {
		return EditPartInheritanceUtils.resolveSemanticElement(this, super.resolveSemanticElement());
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") java.lang.Class key) {
		return EditPartInheritanceUtils.getAdapter(this, key, super.getAdapter(key));
	}

	@Override
	protected void createDefaultEditPolicies() {
		// This needs to be ahead of the edit-policies that process user edit gestures
		installEditPolicy(INHERITANCE_ROLE, new InheritanceEditPolicy());

		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new RTSemanticEditPolicy());

		// Custom arrange behaviour
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new PortContainerEditPolicy());
	}

	@Override
	protected void addSemanticListeners() {
		super.addSemanticListeners();

		if (hasNotationView() && getNotationView().isSetElement()) {
			getMultiplicityListener().adapt(getUMLElement());
		}

		getUMLRTElement().ifPresent(rt -> rt.addReificationListener(reificationListener));
	}


	@Override
	protected void removeSemanticListeners() {
		getUMLRTElement().ifPresent(rt -> rt.removeReificationListener(reificationListener));

		super.removeSemanticListeners();

		if (multiplicityValueListener != null) {
			MultiplicityElement target = multiplicityValueListener.getTarget();
			if (target != null) {
				multiplicityValueListener.unadapt(target);
			}
			multiplicityValueListener = null;
		}
	}


	/**
	 * Retrieve the listener for Multiplicity Bounds
	 * When the ValueSpecification is created , a listener is put on the lower and the Upper Value
	 * When the bounds value are set, we refresh the visual
	 * 
	 * {@link CapsulePartEditHelperAdvice#getBeforeConfigureCommand()}
	 */
	protected MultiplicityElementAdapter getMultiplicityListener() {
		if (multiplicityValueListener == null) {
			multiplicityValueListener = new MultiplicityElementAdapter() {
				@Override
				protected void handleMultiplicityChanged(Notification notification) {
					refreshVisuals();
				}
			};
		}

		return multiplicityValueListener;
	}

	@Override
	protected IFigure createNodeShape() {
		primaryShape = new RTPropertyPartFigure();
		return primaryShape;
	}

	@Override
	public RTPropertyPartFigure getPrimaryShape() {
		return (RTPropertyPartFigure) primaryShape;
	}

	@Override
	protected void refreshVisuals() {

		boolean stack = false;
		if (hasNotationView() && (getUMLElement() != null)) {
			MultiplicityElement mult = (MultiplicityElement) getUMLElement();
			boolean isHash = 0 == mult.getLower();
			getPrimaryShape().setHashed(isHash);

			// Reuse the RTPropertyUtils class to ask if the property is replicated or not,
			// When multiplicity = [0..1] (optional non replicated property), no stack pattern is displayed / label is displayed indeed
			if (RTPropertyUtils.isReplicated((Property) getUMLElement())) {
				stack = true;
			}


			getPrimaryShape().setStack(stack);
		}
		getPrimaryShape().setLayerNumber(LAYER_NUMBER);
		getPrimaryShape().setXOffSet(X_OFFSET);
		getPrimaryShape().setYOffSet(Y_OFFSET);
		getPrimaryShape().setPathPattern(PATTERN_PATH);
		getPrimaryShape().setLayerOpacity(isSemanticInherited()
				? getLighterAlpha(LAYER_OPACITY)
				: LAYER_OPACITY);

		super.refreshVisuals();

	}

	@Override
	protected void refreshBackgroundColor() {
		super.refreshBackgroundColor();

		// We present inherited parts in a lighter colour
		UMLRTEditPartUtils.updateBackgroundColor(this, getPrimaryShape());
	}

	@Override
	protected void refreshForegroundColor() {
		super.refreshForegroundColor();

		// We present inherited parts in a lighter colour
		UMLRTEditPartUtils.updateForegroundColor(this, getPrimaryShape());
	}

	/**
	 * Overridden to install the {@link RTPortPositionLocator} as the constraint for port figures.
	 */
	@Override
	protected boolean addFixedChild(EditPart childEditPart) {
		boolean result;

		if (IRTPortEditPart.isPortOnPart(childEditPart)) {
			IRTPortEditPart portOnPart = (IRTPortEditPart) childEditPart;
			if (hasNotationView() && getNotationView().isSetElement()) {
				IBorderItemLocator locator = new RTPortPositionLocator(
						portOnPart.getPort(), getMainFigure(),
						PositionConstants.NONE, portOnPart.getDefaultScaleFactor());

				getBorderedFigure().getBorderItemContainer().add(portOnPart.getFigure(), locator);
			}

			result = true;
		} else {
			result = super.addFixedChild(childEditPart);
		}

		return result;
	}

	private void reificationStateChanged(UMLRTNamedElement element, boolean reified) {
		if (reified && !UMLRTCommandUtils.isUndoRedoInProgress(element.toUML())) {
			// TODO: Anything?
		}
	}
}
