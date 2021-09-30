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
 *  Christian W. Damus - bug 493869
 *  
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.decorators;

import org.eclipse.draw2d.AbstractImageFigure;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.AbstractDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget.Direction;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.common.figure.node.ScaledImageFigure;
import org.eclipse.papyrusrt.umlrt.core.utils.StateUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateEditPart;
import org.eclipse.uml2.uml.State;

/**
 * An iconic decorator for composite states.
 */
public class CompositeStateDecorator extends AbstractDecorator {

	/** The source image size. */
	private static final int IMAGE_SIZE = 32;

	/** The rendered size of the decoration image. */
	private static final int DECORATION_SIZE = 16;

	private static final Direction DECORATION_POSITION = Direction.SOUTH_EAST;

	/** Offset of the decoration according to the Direction Corner */
	private static final int DECORATION_OFFSET = -3;

	/**
	 * Initializes me with my target.
	 * 
	 * @param decoratorTarget
	 *            my target
	 */
	public CompositeStateDecorator(IDecoratorTarget decoratorTarget) {
		super(decoratorTarget);

	}

	@Override
	public void activate() {
		refresh();
	}

	@Override
	public void refresh() {
		removeDecoration();
		final EditPart editPart = (EditPart) getDecoratorTarget().getAdapter(EditPart.class);
		if (isCompositeState(editPart)) {
			// Get the figure of the editpart
			final IFigure editPartFigure = ((GraphicalEditPart) editPart).getFigure();

			// Create a main figure with a flow layout. It will contains icons.
			final IFigure figure = new Figure();
			final FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHorizontal(true);
			flowLayout.setMinorSpacing(0);
			figure.setLayoutManager(flowLayout);

			// Add it to main figure of the decoration
			figure.add(getImage());

			// Get MapMode
			final IMapMode mm = MapModeUtil.getMapMode(editPartFigure);
			// Set the size of the decorator figure
			figure.setSize(mm.DPtoLP(DECORATION_SIZE), mm.DPtoLP(DECORATION_SIZE));

			// Set the decoration with the custom locator.
			setDecoration(getDecoratorTarget().addShapeDecoration(
					figure, DECORATION_POSITION, DECORATION_OFFSET, false));

			// Set the tool tip of the decoration
			getDecoration().setToolTip(new Label("Composite state"));
		}
	}

	/**
	 * Does an edit-part present a composite state?
	 * 
	 * @param editPart
	 *            an edit-part
	 * 
	 * @return whether it presents a composite state
	 */
	boolean isCompositeState(EditPart editPart) {
		boolean result = false;

		if (editPart instanceof RTStateEditPart) {
			EObject semantic = ((RTStateEditPart) editPart).resolveSemanticElement();
			if (semantic instanceof State) {
				result = ((State) semantic).isComposite();
			}
		}

		return result;
	}

	private AbstractImageFigure getImage() {
		ScaledImageFigure result = new ScaledImageFigure();
		result.setImage(Activator.getImage(Activator.IMG_OVR_COMPOSITE_STATE));
		result.setSize(IMAGE_SIZE, IMAGE_SIZE);
		result.setScale((double) DECORATION_SIZE / (double) IMAGE_SIZE);
		return result;
	}

	//
	// Nested types
	//

	public static class Provider extends AbstractProvider implements IDecoratorProvider {

		private static final String GMF_DECORATION_KEY = "CompositeStateDecoration"; //$NON-NLS-1$

		@Override
		public boolean provides(IOperation operation) {
			IDecoratorTarget target = ((CreateDecoratorsOperation) operation).getDecoratorTarget();
			RTStateEditPart editPart = TypeUtils.as(target.getAdapter(EditPart.class), RTStateEditPart.class);
			State state = TypeUtils.as(target.getAdapter(EObject.class), State.class);

			return (editPart != null) && (state != null) && StateUtils.isRTState(state);
		}

		@Override
		public void createDecorators(IDecoratorTarget decoratorTarget) {
			final View node = decoratorTarget.getAdapter(View.class);
			if (node != null) {
				// Install the decorator
				decoratorTarget.installDecorator(GMF_DECORATION_KEY, new CompositeStateDecorator(decoratorTarget));
			}
		}

	}

}
