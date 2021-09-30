/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeCompartmentEditPart;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;

/**
 * A custom class composite compartment controller that recognizes requests to create
 * internal ports and delegates them to the containing class controller.
 */
public class RTClassCompositeCompartmentEditPart extends ClassCompositeCompartmentEditPart {

	/**
	 * Initializes me with my view.
	 * 
	 * @param view
	 *            my view
	 */
	public RTClassCompositeCompartmentEditPart(View view) {
		super(view);
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		EditPart result;

		if (isInternalPortCreation(request)) {
			// Delegate to the class that contains me
			result = getParent();
		} else {
			result = super.getTargetEditPart(request);
		}

		return result;
	}

	/**
	 * Queries whether a {@code request} is for creation of an internal behavior
	 * <tt>RTPort</tt>.
	 * 
	 * @param request
	 *            a creation request
	 * @return whether it is for an internal port
	 */
	private boolean isInternalPortCreation(Request request) {
		boolean result = request instanceof CreateViewAndElementRequest;

		if (result) {
			IElementType typeToCreate = ((CreateViewAndElementRequest) request).getViewAndElementDescriptor().getElementAdapter().getAdapter(IElementType.class);

			result = (RTPortUtils.getPortKind(typeToCreate) == UMLRTPortKind.INTERNAL_BEHAVIOR);
		}

		return result;
	}

}
