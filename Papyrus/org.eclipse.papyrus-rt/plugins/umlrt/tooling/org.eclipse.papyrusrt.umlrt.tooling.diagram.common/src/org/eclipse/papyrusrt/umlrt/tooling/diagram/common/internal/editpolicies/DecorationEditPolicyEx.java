/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 510315
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DecorationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.decorator.DecoratorService;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoration;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecorator;
import org.eclipse.gmf.runtime.draw2d.ui.internal.figures.ImageFigureEx;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.figures.OnConnectionFixedLocator;
import org.eclipse.swt.graphics.Image;

/**
 * Specific {@link DecorationEditPolicy} that provides a decorator target on edges to a fixed distance from source rather than on a percentage basis.
 */
@SuppressWarnings("restriction")
public class DecorationEditPolicyEx extends DecorationEditPolicy {

	/**
	 * Constructor.
	 */
	public DecorationEditPolicyEx() {
		super();
	}

	@Override
	public void refresh() {
		// no call to super here
		if (decorators == null) {
			decorators = new HashMap<>();
			DecoratorService.getInstance().createDecorators(
					new TransitionDecoratorTarget());
		}
		for (Iterator<?> iter = decorators.values().iterator(); iter.hasNext();) {
			IDecorator decorator = (IDecorator) iter.next();
			decorator.refresh();
		}
	}

	@Override
	public void activate() {
		// no call to super here
		if (decorators == null) {
			decorators = new HashMap<>();
			DecoratorService.getInstance().createDecorators(
					new TransitionDecoratorTarget());
		}
		if (decorators != null) {
			for (Iterator<?> iter = decorators.values().iterator(); iter.hasNext();) {
				IDecorator decorator = (IDecorator) iter.next();
				decorator.activate();
			}
		}
	}

	/**
	 * The decoratorTarget object to be passed to the service. This serves as a
	 * wrapper around this editpolicy.
	 */
	public class TransitionDecoratorTarget
			extends DecoratorTarget {

		/**
		 * Constructor.
		 */
		public TransitionDecoratorTarget() {
			super();
		}

		/**
		 * Adds a figure as a decoration on a connection.
		 * 
		 * @param figure
		 *            the figure to be used as the decoration
		 * @param distanceFromSource
		 *            The distance of the connection length away from the
		 *            source end where the decoration
		 *            should be positioned.
		 * @param isVolatile
		 *            True if this decoration is volatile (i.e. not to be
		 *            included in the printed output of a diagram); false
		 *            otherwise.
		 * @return The decoration object, which is needed to later remove the
		 *         decoration.
		 */
		public IDecoration addFixedConnectionDecoration(IFigure figure, double distanceFromSource, boolean isVolatile) {
			return addFixedConnectionDecoration(figure, distanceFromSource, true, isVolatile);
		}

		/**
		 * Adds a figure as a decoration on a connection.
		 * 
		 * @param figure
		 *            the figure to be used as the decoration
		 * @param distanceFromTarget
		 *            The distance of the connection length away from the
		 *            target end where the decoration
		 *            should be positioned.
		 * @param isVolatile
		 *            True if this decoration is volatile (i.e. not to be
		 *            included in the printed output of a diagram); false
		 *            otherwise.
		 * @return The decoration object, which is needed to later remove the
		 *         decoration.
		 */
		public IDecoration addFixedConnectionTargetDecoration(IFigure figure, double distanceFromTarget, boolean isVolatile) {
			return addFixedConnectionDecoration(figure, distanceFromTarget, false, isVolatile);
		}

		/**
		 * Adds a figure as a decoration on a connection.
		 * 
		 * @param figure
		 *            the figure to be used as the decoration
		 * @param distance
		 *            The distance of the connection length away from the
		 *            end where the decoration should be positioned.
		 * @param isSourceEnd
		 *            {@code true} to position the decoration at the
		 *            source end; {@code false} for the target end
		 * @param isVolatile
		 *            True if this decoration is volatile (i.e. not to be
		 *            included in the printed output of a diagram); false
		 *            otherwise.
		 * @return The decoration object, which is needed to later remove the
		 *         decoration.
		 */
		public IDecoration addFixedConnectionDecoration(IFigure figure, double distance, boolean isSourceEnd, boolean isVolatile) {
			IFigure hostFigure = ((GraphicalEditPart) getAdapter(GraphicalEditPart.class)).getFigure();
			Assert.isTrue(hostFigure instanceof Connection);

			return addDecoration(figure, new OnConnectionFixedLocator(
					(Connection) hostFigure, distance, isSourceEnd), isVolatile);
		}

		/**
		 * Adds an image as a decoration on a connection.
		 * 
		 * @param image
		 *            The image to be used as the decoration.
		 * @param distanceFromSource
		 *            The distance of the connection length away from the source
		 *            end where the decoration should be
		 *            positioned.
		 * @param isVolatile
		 *            True if this decoration is volatile (i.e. not to be included
		 *            in the printed output of a diagram); false otherwise.
		 * @return The decoration object, which will be needed to remove the
		 *         decoration from the connection.
		 */
		public IDecoration addFixedDecoration(Image image, double distanceFromSource, boolean isVolatile) {
			return addFixedDecoration(image, distanceFromSource, true, isVolatile);
		}

		/**
		 * Adds an image as a decoration on a connection.
		 * 
		 * @param image
		 *            The image to be used as the decoration.
		 * @param distanceFromTarget
		 *            The distance of the connection length away from the target
		 *            end where the decoration should be
		 *            positioned.
		 * @param isVolatile
		 *            True if this decoration is volatile (i.e. not to be included
		 *            in the printed output of a diagram); false otherwise.
		 * @return The decoration object, which will be needed to remove the
		 *         decoration from the connection.
		 */
		public IDecoration addFixedTargetDecoration(Image image, double distanceFromTarget, boolean isVolatile) {
			return addFixedDecoration(image, distanceFromTarget, false, isVolatile);
		}

		/**
		 * Adds an image as a decoration on a connection.
		 * 
		 * @param image
		 *            The image to be used as the decoration.
		 * @param distance
		 *            The distance of the connection length away from the
		 *            end where the decoration should be positioned.
		 * @param isVolatile
		 *            True if this decoration is volatile (i.e. not to be included
		 *            in the printed output of a diagram); false otherwise.
		 * @return The decoration object, which will be needed to remove the
		 *         decoration from the connection.
		 */
		public IDecoration addFixedDecoration(Image image, double distance, boolean isSourceEnd, boolean isVolatile) {
			IMapMode mm = MapModeUtil.getMapMode(((GraphicalEditPart) getHost()).getFigure());
			ImageFigureEx figure = new ImageFigureEx();
			figure.setImage(image);
			figure.setSize(mm.DPtoLP(image.getBounds().width), mm
					.DPtoLP(image.getBounds().height));

			return addFixedConnectionDecoration(figure, distance, isSourceEnd, isVolatile);
		}
	}
}
