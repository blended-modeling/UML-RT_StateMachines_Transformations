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

package org.eclipse.papyrusrt.umlrt.core.sync.capsule;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.sync.SyncBucket;
import org.eclipse.papyrus.infra.sync.service.AbstractSyncTrigger;
import org.eclipse.papyrus.infra.sync.service.ISyncAction;
import org.eclipse.papyrus.infra.sync.service.ISyncService;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;

/**
 * Sync trigger to initialize synchronization of capsules.
 */
public class CapsuleSyncTrigger extends AbstractSyncTrigger {

	public CapsuleSyncTrigger() {
		super();
	}

	@Override
	public ISyncAction trigger(ISyncService syncService, Object object) {
		return new ISyncAction() {

			@Override
			public IStatus perform(ISyncService syncService, Object object) {
				// This cast is safe because it is assured by our enablement expression
				org.eclipse.uml2.uml.Class capsule = (org.eclipse.uml2.uml.Class) object;

				CapsuleSyncRegistry registry = syncService.getSyncRegistry(CapsuleSyncRegistry.class);
				if (registry != null) {
					// Look for a super-capsule to synchronize with
					org.eclipse.uml2.uml.Class superCapsule = CapsuleUtils.getSuperCapsule(capsule);
					if (superCapsule != null) {
						SyncBucket<org.eclipse.uml2.uml.Class, EObject, Notification> bucket = registry.getBucket(superCapsule);
						if (bucket == null) {
							bucket = new CapsuleSyncBucket(superCapsule);
							registry.register(bucket);
						}
						registry.synchronize(capsule);
					}
				}

				return Status.OK_STATUS;
			}
		};
	}

}
