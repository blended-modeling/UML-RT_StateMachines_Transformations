/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.sync.statemachine;

import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.NodePositionSyncFeature;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.NodeSizeSyncFeature;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.sync.UMLRTMasterSlaveSyncBucket;
import org.eclipse.uml2.uml.FinalState;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Vertex;

/**
 * Synchronization bucket for vertices in a UML-RT Capsule state machine diagram.
 */
public class CSMDVertexSyncBucket extends UMLRTMasterSlaveSyncBucket<Vertex> {

	public CSMDVertexSyncBucket(Vertex model, EditPart master) {
		super(model, master);

		add(new NodeSizeSyncFeature<>(this));
		add(new NodePositionSyncFeature<>(this));

		// Final states don't contain regions
		if ((model instanceof State) && !(model instanceof FinalState)) {
			add(new CSMDStateMachineRegionsSyncFeature<>(this));
		}
	}

}
