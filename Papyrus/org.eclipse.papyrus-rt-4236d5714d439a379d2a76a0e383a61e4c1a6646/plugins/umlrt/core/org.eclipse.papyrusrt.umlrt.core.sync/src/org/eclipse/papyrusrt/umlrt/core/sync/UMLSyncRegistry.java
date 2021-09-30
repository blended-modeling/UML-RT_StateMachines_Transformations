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

package org.eclipse.papyrusrt.umlrt.core.sync;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.sync.SyncRegistry;

/**
 * Common protocol of the UML-RT synchronization registries.
 */
public abstract class UMLSyncRegistry<M extends EObject> extends SyncRegistry<M, EObject, Notification> {

	public UMLSyncRegistry() {
		super();
	}

	@Override
	public abstract M getModelOf(EObject backend); // Make it visible to clients
}
