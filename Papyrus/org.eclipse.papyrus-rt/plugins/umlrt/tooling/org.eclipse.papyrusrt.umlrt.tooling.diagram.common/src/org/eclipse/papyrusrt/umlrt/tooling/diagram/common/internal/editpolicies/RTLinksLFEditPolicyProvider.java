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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.papyrus.uml.diagram.linklf.providers.LinksLFEditPolicyProvider;

/**
 * Customized Link LF edit-policy provider that creates inheritance-aware edit-policies.
 */
public class RTLinksLFEditPolicyProvider extends LinksLFEditPolicyProvider {

	public RTLinksLFEditPolicyProvider() {
		super();
	}

	@Override
	protected void installGraphicalNodeEditPolicy(INodeEditPart nodeEP) {
		if (nodeEP.getEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE) != null) {
			nodeEP.installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new RTLinksLFEditPolicy());
		}
	}

}
