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

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.composite.custom.edit.policies.BehaviorPortEditPolicy;
import org.eclipse.uml2.uml.Port;

/**
 * Specialized behavior-port edit-policy that understands inheritance.
 */
public class RTBehaviorPortEditPolicy extends BehaviorPortEditPolicy {

	/**
	 * Initializes me.
	 */
	public RTBehaviorPortEditPolicy() {
		super();
	}

	@Override
	protected Port getUMLElement() {
		return TypeUtils.as(((IGraphicalEditPart) getHost()).resolveSemanticElement(), Port.class);
	}
}
