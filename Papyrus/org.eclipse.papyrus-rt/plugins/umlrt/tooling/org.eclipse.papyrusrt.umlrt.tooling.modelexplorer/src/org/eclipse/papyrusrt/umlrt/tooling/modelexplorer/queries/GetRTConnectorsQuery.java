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
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;

/**
 * A query that obtains the RT connectors (or non-RT connectors) of a capsule.
 */
public class GetRTConnectorsQuery extends GetRTStructuralFeaturesQuery<Connector> {

	public GetRTConnectorsQuery() {
		super(Connector.class);
	}

	@Override
	protected List<Connector> getFeatures(Class capsule) {
		return capsule.getOwnedConnectors();
	}

	@Override
	protected List<? extends UMLRTConnector> getFeatures(UMLRTCapsule capsule) {
		return capsule.getConnectors(true);
	}
}
