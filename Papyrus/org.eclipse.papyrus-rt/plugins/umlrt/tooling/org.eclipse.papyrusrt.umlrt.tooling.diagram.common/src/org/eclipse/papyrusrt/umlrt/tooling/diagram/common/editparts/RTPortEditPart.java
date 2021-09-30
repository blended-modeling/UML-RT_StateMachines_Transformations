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
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net   - Bug  482694 : add listener on Multiplicity Bounds
 *   Christian W. Damus - bugs 489380, 483637, 496304, 495149, 467545, 507552
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.AbstractInheritanceEditPolicy.INHERITANCE_ROLE;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.RoundedRectangleNodePlateFigure;
import org.eclipse.papyrus.uml.diagram.composite.custom.edit.parts.ResizablePortEditPart;
import org.eclipse.papyrus.uml.diagram.composite.custom.edit.policies.BehaviorPortEditPolicy;
import org.eclipse.papyrusrt.umlrt.core.utils.MultiplicityElementAdapter;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPropertyUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTCommandUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.figures.RTPortFigure;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.InheritanceEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTBehaviorPortEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTPortResizableEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.RTSemanticEditPolicy;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.util.ReificationListener;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Property;

/**
 * Edit Part for Port in Papyrus RT.
 * 
 * @author Céline JANSSENS
 *
 */
public class RTPortEditPart extends ResizablePortEditPart implements IRTPortEditPart, IInheritableEditPart {

	/**
	 * 
	 */
	private static final int LAYER_OPACITY = 0;

	/**
	 * Horizontal Offset
	 */
	private static final int X_OFFSET = 2;

	/**
	 * Vertical Offset
	 */
	private static final int Y_OFFSET = X_OFFSET;

	/**
	 * Layer Number
	 */
	private static final int LAYER_NUMBER = 1;

	private MultiplicityElementAdapter multiplicityValueListener;

	private ReificationListener reificationListener = this::reificationStateChanged;



	/**
	 * 
	 * Constructor.
	 *
	 * @param view
	 *            The view created with the editPart
	 */
	public RTPortEditPart(View view) {
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

		// Install a behavior-port edit policy that understands inheritance
		installEditPolicy(BehaviorPortEditPolicy.BEHAVIOR_PORT, new RTBehaviorPortEditPolicy());
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

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart#getPrimaryShape()
	 */
	@Override
	public RTPortFigure getPrimaryShape() {

		return (RTPortFigure) primaryShape;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart#createNodeShape()
	 * 
	 */
	@Override
	protected IFigure createNodeShape() {
		primaryShape = new RTPortFigure();
		return primaryShape;
	}

	/**
	 * Override the inherited method for a smaller default size.
	 */
	@Override
	protected NodeFigure createNodePlate() {
		Dimension defaultSize = getDefaultSize();

		RoundedRectangleNodePlateFigure figure = new RoundedRectangleNodePlateFigure(
				defaultSize);

		// Get dimension from notation
		int width = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Width())).intValue();
		int height = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Height())).intValue();

		int w = (width > defaultSize.width()) ? width : defaultSize.width();
		int h = (height > defaultSize.height()) ? height : defaultSize.height();

		figure.getBounds().setSize(w, h);
		figure.setDefaultSize(w, h);

		return figure;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.composite.custom.edit.parts.CustomPortEditPart#refreshVisuals()
	 *
	 */
	@Override
	protected void refreshVisuals() {

		boolean stack = false;
		if (hasNotationView() && (getUMLElement() != null)) {
			// Reuse the RTPropertyUtils class to ask if the property is replicated or not,
			// When multiplicity = [0..1] (optional non replicated property), no stack pattern is displayed / label is displayed indeed
			if (RTPropertyUtils.isReplicated((Property) getUMLElement())) {
				stack = true;
			}
		}
		getPrimaryShape().setStack(stack);
		getPrimaryShape().setLayerNumber(LAYER_NUMBER);
		getPrimaryShape().setXOffSet(X_OFFSET);
		getPrimaryShape().setYOffSet(Y_OFFSET);
		getPrimaryShape().setLayerOpacity(LAYER_OPACITY);

		// assure that port shape is refreshed (see bug 500751)
		getPrimaryShape().repaint();
		super.refreshVisuals();
	}

	@Override
	protected void refreshBackgroundColor() {
		super.refreshBackgroundColor();

		// We present inherited ports in a lighter colour
		UMLRTEditPartUtils.updateBackgroundColor(this, getPrimaryShape());
	}

	@Override
	protected void refreshForegroundColor() {
		super.refreshForegroundColor();

		// We present inherited ports in a lighter colour
		UMLRTEditPartUtils.updateForegroundColor(this, getPrimaryShape());
	}

	@Override
	public boolean isPortOnPart() {
		return false;
	}

	@Override
	public EditPolicy getPrimaryDragEditPolicy() {
		return new RTPortResizableEditPolicy();
	}

	private void reificationStateChanged(UMLRTNamedElement element, boolean reified) {
		if (reified && !UMLRTCommandUtils.isUndoRedoInProgress(element.toUML())) {
			// TODO: Anything?
		}
	}
}
