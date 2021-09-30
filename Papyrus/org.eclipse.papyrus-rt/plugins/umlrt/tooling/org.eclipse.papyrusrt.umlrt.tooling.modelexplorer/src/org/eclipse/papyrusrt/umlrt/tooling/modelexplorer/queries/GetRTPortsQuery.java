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
package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.queries;

import java.util.List;

import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Port;

/**
 * A query that obtains the RT ports (or non-RT ports) of a capsule.
 */
public class GetRTPortsQuery extends GetRTStructuralFeaturesQuery<Port> {

	public GetRTPortsQuery() {
		super(Port.class);
	}

	@Override
	protected List<Port> getFeatures(Class capsule) {
		return capsule.getOwnedPorts();
	}

	@Override
	protected List<? extends UMLRTPort> getFeatures(UMLRTCapsule capsule) {
		return capsule.getPorts(true);
	}
}
