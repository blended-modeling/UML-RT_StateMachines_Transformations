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

package org.eclipse.papyrusrt.umlrt.core.sync.statemachine;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.sync.SyncBucket;
import org.eclipse.papyrus.infra.sync.SyncItem;
import org.eclipse.papyrus.infra.sync.SyncRegistry;
import org.eclipse.papyrusrt.umlrt.core.sync.UMLSyncFeature;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;

/**
 * A synchronization feature for matching the vertices of a region with the region that it redefines.
 */
public class RegionVerticesSyncFeature extends UMLSyncFeature<Region, Vertex> {

	public RegionVerticesSyncFeature(SyncBucket<Region, EObject, Notification> bucket) {
		super(bucket, UMLPackage.Literals.REGION__SUBVERTEX);
	}

	@Override
	protected Class<? extends SyncRegistry<Vertex, EObject, Notification>> getNestedRegistryType() {
		return VertexSyncRegistry.class;
	}

	@Override
	protected Command onTargetAdded(SyncItem<Region, EObject> from, EObject source, SyncItem<Region, EObject> to, EObject target) {
		Command result = null;
		Vertex vertex = (Vertex) target;
		Vertex superVertex = (Vertex) source;

		VertexSyncRegistry registry = (VertexSyncRegistry) getNestedRegistry();
		if (!registry.redefines(vertex, superVertex)) {
			result = registry.createSetRedefinedVertexCommand(vertex, superVertex);
		}

		// Synchronize with the corresponding vertex in the super state machine
		SyncBucket<Vertex, EObject, Notification> bucket = registry.getBucket(superVertex);
		if (bucket == null) {
			bucket = new VertexSyncBucket(superVertex);
			registry.register(bucket);
		}
		result = synchronizingWrapper(registry, vertex, result);

		return result;
	}

	@Override
	protected Command onTargetRemoved(SyncItem<Region, EObject> to, EObject target) {
		// TODO Purge synchronizations no longer needed (if that's even necessary?)
		return null;
	}
}
