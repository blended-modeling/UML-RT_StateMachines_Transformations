/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.validation.java;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTConnector;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorKind;
import org.eclipse.uml2.uml.Port;

/**
 * @author ysroh
 *         Validate that the given connector instance is valid
 *         by checking its protocol and conjugation compatibility.
 *
 */
public class ConnectorPortsCompatibilityConstraint extends AbstractModelConstraint {

	/**
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 *
	 * @param ctx
	 * @return
	 */
	@Override
	public IStatus validate(IValidationContext ctx) {
		RTConnector target = (RTConnector) ctx.getTarget();
		Connector connector = target.getBase_Connector();
		if (connector.getEnds().size() == 2) {
			Port port1 = (Port) connector.getEnds().get(0).getRole();
			Port port2 = (Port) connector.getEnds().get(1).getRole();

			String arg = "'" + port1.getQualifiedName() + "' and '" + port2.getQualifiedName() + "'";
			if (!port1.getType().equals(port2.getType())) {
				return ctx.createFailureStatus(arg, "Ports must be typed with compatible protocol.");
			}

			if (connector.getKind().equals(ConnectorKind.ASSEMBLY_LITERAL)) {
				if (port1.isConjugated() == port2.isConjugated()) {
					return ctx.createFailureStatus(arg, "Assembly connector must have different conjugation between connected ports.");
				}
			} else {
				if (port1.isConjugated() != port2.isConjugated()) {
					return ctx.createFailureStatus(arg, "Delegation connector must have same conjugation between connected ports.");
				}
			}
		}
		return ctx.createSuccessStatus();
	}

}
