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
 *  Christian W. Damus - bugs 489939, 494310
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.locator;

import static java.lang.Math.ceil;

import java.util.Iterator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.papyrus.uml.diagram.common.figure.node.SubCompartmentLayoutManager;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;

/**
 * The Class RTPortPositionLocator is a specific Port Position Locator for the Real Time Context,
 * to allow RTPort to be inside a ClassComposite.
 */
public class RTPortPositionLocator extends PortPositionLocator {


	/** The port. */
	private final Element port;

	/** Size scale factor (such as for ports on parts). */
	private final double scaleFactor;

	/**
	 * Instantiates a new RT port position locator with unit scale factor.
	 *
	 * @param droppedElement
	 *            the dropped element
	 * @param parentFigure
	 *            the parent figure
	 * @param preferredSide
	 *            the preferred side
	 */
	public RTPortPositionLocator(Element droppedElement, IFigure parentFigure, int preferredSide) {
		this(droppedElement, parentFigure, preferredSide, 1.0);
	}

	/**
	 * Instantiates a new RT port position locator.
	 *
	 * @param droppedElement
	 *            the dropped element
	 * @param parentFigure
	 *            the parent figure
	 * @param preferredSide
	 *            the preferred side
	 * @param scaleFactory
	 *            scale factor for preferred sizing of the port shape
	 */
	public RTPortPositionLocator(Element droppedElement, IFigure parentFigure, int preferredSide, double scaleFactor) {
		super(parentFigure, preferredSide);

		this.port = droppedElement;
		this.scaleFactor = scaleFactor;

		if (scaleFactor != 1.0) {
			setBorderItemOffset((int) ceil(getBorderItemOffset() * scaleFactor));
		}
	}

	/**
	 * Calculates the preferred location according to the RTPort type.
	 * Only Internal and SAP Kind can be located internally.
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator#getPreferredLocation(org.eclipse.draw2d.geometry.Rectangle)
	 */
	@Override
	public Rectangle getPreferredLocation(Rectangle proposedLocation) {

		Rectangle newLocation = null;
		if (port instanceof Port) {
			if (RTPortUtils.isService((Port) port)) {
				newLocation = getPreferredBorderedLocation(proposedLocation);
			} else {
				newLocation = getPreferredInternalLocation(proposedLocation);
			}
		} else {
			newLocation = getPreferredBorderedLocation(proposedLocation);
		}
		return newLocation;
	}

	/**
	 * Gets the preferred internal location. As a Standard port is not internal,
	 * this method calculates the right position based on the Compartment bounds and the target Position.
	 * 
	 *
	 * @param proposedLocation
	 *            the proposed location
	 * @return the preferred internal location
	 */
	private Rectangle getPreferredInternalLocation(Rectangle proposedLocation) {
		// Initialize port location with proposed location
		// and resolve the bounds of it graphical parent
		Rectangle realLocation = new Rectangle(proposedLocation);
		// Retrieve the bound of the Compartment to avoid the Name Label area.
		IFigure compartment = getCompositeCompartmentFigure(parentFigure);
		Rectangle compartmentBounds;
		if (null != compartment) {
			compartmentBounds = compartment.getBounds().getCopy();

			// Calculate Max position around the graphical parent (1/2 size or the port around
			// the graphical parent bounds.
			// this is an intra rectangle
			int xMin = compartmentBounds.x + borderItemOffset;
			int xMax = compartmentBounds.x + compartmentBounds.width - borderItemOffset - realLocation.width;
			int yMin = compartmentBounds.y + borderItemOffset;
			int yMax = compartmentBounds.y + compartmentBounds.height - borderItemOffset - realLocation.height;

			// Modify Port location if MAX X or Y are exceeded
			if (realLocation.x < xMin) {
				realLocation.x = xMin;
			}

			if (realLocation.x > xMax) {
				realLocation.x = xMax;
			}

			if (realLocation.y < yMin) {
				realLocation.y = yMin;
			}

			if (realLocation.y > yMax) {
				realLocation.y = yMax;
			}
		}
		return realLocation;
	}

	/**
	 * Retrieve the Compartment of the Composite Class.
	 *
	 * @param parentFigure
	 *            the parent figure
	 * @return the composite compartment figure
	 */
	private IFigure getCompositeCompartmentFigure(IFigure parentFigure) {
		IFigure composite = null;
		Iterator<?> childrenIterator = parentFigure.getChildren().iterator();
		while (null == composite && childrenIterator.hasNext()) {
			Object child = childrenIterator.next();
			if (child instanceof IFigure) {
				// The compartment figure should have the Compartment Layout Manager
				if (((IFigure) child).getLayoutManager() instanceof SubCompartmentLayoutManager) {
					composite = (IFigure) child;
				}

				if (null == composite) {
					composite = getCompositeCompartmentFigure((IFigure) child);
				}
			}

		}
		return composite;
	}

	/**
	 * Gets the preferred bordered location.
	 *
	 * @param proposedLocation
	 *            the proposed location
	 * @return the preferred bordered location
	 */
	private Rectangle getPreferredBorderedLocation(Rectangle proposedLocation) {
		return super.getPreferredLocation(proposedLocation);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator#setConstraint(org.eclipse.draw2d.geometry.Rectangle)
	 *
	 * @param constraint
	 */
	@Override
	public void setConstraint(Rectangle constraint) {
		// Set the default size in constraint
		if ((scaleFactor != 1.0) && (constraint.getSize().equals(-1, -1))) {
			int size = (int) Math.ceil(20.0 * scaleFactor);
			constraint.setSize(size, size);
		}
		super.setConstraint(constraint);
	}
}
