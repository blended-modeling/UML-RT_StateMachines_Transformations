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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.sync.SyncBucket;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.sync.UMLRTChildNodesSyncFeature;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.sync.UMLRTSyncRegistry;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.Vertex;

/**
 * Synchronization feature for the edit-parts visualizing the regions of a state machine
 */
public class CSMDRegionVerticesSyncFeature extends UMLRTChildNodesSyncFeature<Region, Vertex> {

	public CSMDRegionVerticesSyncFeature(SyncBucket<Region, EditPart, Notification> bucket) {
		super(bucket);
	}

	@Override
	protected Class<? extends UMLRTSyncRegistry<Vertex>> getNestedSyncRegistryType() {
		return CSMDVertexSyncRegistry.class;
	}

	@Override
	protected SyncBucket<Vertex, EditPart, Notification> createNestedSyncBucket(Vertex model, EditPart editPart) {
		return new CSMDVertexSyncBucket(model, editPart);
	}

	@Override
	protected Iterable<? extends Vertex> getModelContents(Region model) {
		return model.getSubvertices();
	}

}
