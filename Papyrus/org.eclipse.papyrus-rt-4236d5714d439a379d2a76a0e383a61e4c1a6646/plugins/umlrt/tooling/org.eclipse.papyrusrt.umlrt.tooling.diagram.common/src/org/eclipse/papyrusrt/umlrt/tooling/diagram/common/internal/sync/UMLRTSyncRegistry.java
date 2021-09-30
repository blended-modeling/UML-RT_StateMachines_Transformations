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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.sync;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.EditPartSyncRegistry;
import org.eclipse.papyrus.infra.sync.SyncBucket;
import org.eclipse.papyrusrt.umlrt.core.sync.UMLSyncRegistry;

/**
 * Synchronization registry for UML-RT diagrams.
 */
public abstract class UMLRTSyncRegistry<M extends EObject> extends EditPartSyncRegistry<M, EditPart> {
	private final Class<? extends UMLSyncRegistry<M>> semanticSyncRegistryType;

	public UMLRTSyncRegistry(Class<? extends UMLSyncRegistry<M>> semanticSyncRegistryType) {
		super();

		this.semanticSyncRegistryType = semanticSyncRegistryType;
	}

	@Override
	public void register(SyncBucket<M, EditPart, Notification> bucket) {
		if (bucket instanceof UMLRTMasterSlaveSyncBucket<?>) {
			((UMLRTMasterSlaveSyncBucket<M>) bucket).setRegistry(this);
		}

		super.register(bucket);
	}

	@Override
	public void unregister(SyncBucket<M, EditPart, Notification> bucket) {
		super.unregister(bucket);

		if (bucket instanceof UMLRTMasterSlaveSyncBucket<?>) {
			((UMLRTMasterSlaveSyncBucket<?>) bucket).setRegistry(null);
		}
	}

	protected final UMLSyncRegistry<M> getSemanticSyncRegistry() {
		return getSyncRegistry(semanticSyncRegistryType);
	}

	public final M getRedefinedElement(M model) {
		return getSemanticSyncRegistry().getModelOf(model);
	}

}
