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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies;

import org.eclipse.gef.Handle;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.gmf.runtime.diagram.ui.tools.DragEditPartsTrackerEx;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.PortResizableEditPolicy;

/**
 * A custom resize edit-policy for ports that suppresses the resize handles.
 */
public class RTPortResizableEditPolicy extends PortResizableEditPolicy {

	/**
	 * Initializes me.
	 */
	public RTPortResizableEditPolicy() {
		super();
	}

	@Override
	public void activate() {
		// Not resizable (but still show selection handles)
		setResizeDirections(0);

		super.activate();
	}

	/**
	 * Customizes the selection handle drag behaviour to ensure that
	 * the drag always tracks around the shape on which border the
	 * port sits (never trying to drag the handle into that shape's
	 * parent compartment, for example).
	 */
	@Override
	protected void replaceHandleDragEditPartsTracker(Handle handle) {
		if (handle instanceof AbstractHandle) {
			AbstractHandle h = (AbstractHandle) handle;
			h.setDragTracker(new DragEditPartsTrackerEx(getHost()) {
				@Override
				protected boolean updateTargetUnderMouse() {
					// Don't ever try to drag off of the shape
					// whose border the item is on
					return false;
				};
			});
		}
	}

}
