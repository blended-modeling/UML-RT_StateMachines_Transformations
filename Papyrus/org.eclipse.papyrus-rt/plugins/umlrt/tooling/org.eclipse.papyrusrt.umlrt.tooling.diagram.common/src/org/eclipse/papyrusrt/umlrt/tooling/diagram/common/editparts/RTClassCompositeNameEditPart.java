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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeNameEditPart;
import org.eclipse.swt.graphics.Image;

/**
 * Work-around for the non-extensibility of icon image provision for the
 * composite structure frame.
 */
public class RTClassCompositeNameEditPart extends ClassCompositeNameEditPart implements IRTNameEditPart {

	/**
	 * Initializes me with my notation view.
	 *
	 * @param view
	 *            my view
	 */
	public RTClassCompositeNameEditPart(View view) {
		super(view);
	}

	@Override
	protected Image getLabelIcon() {
		return getRTLabelIcon();
	}
}
