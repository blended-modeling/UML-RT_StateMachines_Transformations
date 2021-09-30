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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.figures;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;

/**
 * {@link ConnectionLocator} that places element on a given fixed distance (not percentage of)
 * the element from the end of the connection.
 */
public class OnConnectionFixedLocator extends ConnectionLocator {

	/** Distance from the end point. */
	private double distanceFromEnd;
	private boolean isSourceEnd;

	/**
	 * Constructor.
	 *
	 * @param connection
	 *            the connection on which to locate the element.
	 * @param distanceFromSource
	 *            the distance from the source in pixel
	 */
	public OnConnectionFixedLocator(Connection connection, double distanceFromSource) {
		this(connection, distanceFromSource, true);
	}

	/**
	 * Constructor.
	 *
	 * @param connection
	 *            the connection on which to locate the element.
	 * @param distanceFromEnd
	 *            the distance from the end in pixels
	 * @param isSourceEnd
	 *            whether to position relative to the source end ({@code true})
	 *            or target end ({@code false})
	 */
	public OnConnectionFixedLocator(Connection connection, double distanceFromEnd, boolean isSourceEnd) {
		super(connection);

		this.distanceFromEnd = distanceFromEnd;
		this.isSourceEnd = isSourceEnd;
	}

	/**
	 * Puts the figure a percentage of the connection length away from the end.
	 * 
	 * @see org.eclipse.draw2d.ConnectionLocator#getLocation(PointList)
	 */
	@Override
	protected Point getLocation(PointList points) {
		// compute the percentage on the segment, based on the length of the points
		// start by doing a copy
		PointList copyPoints = PointListUtilities.copyPoints(points);
		double dist = PointListUtilities.getPointsLength(copyPoints);
		double position = isSourceEnd()
				? getDistanceFromEnd() / dist * 100.0
				: 100.0 - (getDistanceFromEnd() / dist * 100.0);
		int percentage = Math.toIntExact(Math.round(position));
		Point p = PointListUtilities.calculatePointRelativeToLine(
				copyPoints,
				0,
				percentage,
				true);
		return p;
	}

	/**
	 * Queries the distance from the connection end.
	 * 
	 * @return the distance from the connection end, in pixels
	 */
	protected double getDistanceFromEnd() {
		return distanceFromEnd;
	}

	/**
	 * Queries whether the locator is relative to the connection's source end.
	 * 
	 * @return {@code true} if relative to the source end; {@code false} if relative to the target end
	 */
	protected boolean isSourceEnd() {
		return isSourceEnd;
	}

}
