/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils;

import org.eclipse.draw2d.geometry.Point;


/**
 * A common protocol for iterators of locations in a diagram that progress
 * stepwise from one place to the next according to some well-defined path
 * around or within a shape.
 */
public interface ILocationIterator {

	/**
	 * Advances the iterator by the given {@code distance} along the marching
	 * path prescribed by the particular implementation.
	 * 
	 * @param distance
	 *            the distance by which to advance (can be negative to
	 *            step backwards against the current marching direction)
	 * 
	 * @return the new point on the iteration along our path
	 */
	default Point next(int distance) {
		return next((double) distance);
	}

	/**
	 * Advances the iterator by the given {@code distance} along the marching
	 * path prescribed by the particular implementation.
	 * 
	 * @param distance
	 *            the distance by which to advance (can be negative to
	 *            step backwards against the current marching direction)
	 * 
	 * @return the new point on the iteration along our path
	 */
	Point next(double distance);

	/**
	 * Queries the current location of the iteration along the path.
	 * 
	 * @return where the march currently is
	 */
	Point where();

}
