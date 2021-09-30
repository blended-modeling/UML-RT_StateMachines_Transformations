/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.stereotype;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeNodeLabelDisplayEditPolicy;
import org.eclipse.uml2.uml.Element;

/**
 * Customization of the applied-stereotype node label edit-policy that accounts for
 * inheritance of views from other diagrams by not creating the label views for
 * the transient views of inherited elements.
 */
public class RTAppliedStereotypeLabelEditPolicy extends AppliedStereotypeNodeLabelDisplayEditPolicy {

	public RTAppliedStereotypeLabelEditPolicy() {
		super();
	}

	@Override
	protected Element getUMLElement() {
		return RTAppliedStereotypeEditPolicyHelper.getUMLElement((IGraphicalEditPart) getHost());
	}
}
