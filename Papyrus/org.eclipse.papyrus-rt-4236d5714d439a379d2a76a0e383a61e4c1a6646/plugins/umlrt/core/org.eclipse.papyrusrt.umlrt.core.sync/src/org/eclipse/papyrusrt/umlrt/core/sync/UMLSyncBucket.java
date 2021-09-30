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
import org.eclipse.papyrus.infra.sync.EObjectMasterSlaveSyncBucket;
import org.eclipse.papyrus.infra.sync.SyncItem;
import org.eclipse.uml2.uml.Element;

/**
 * A synchronization bucket for UML semantic model synchronization, in which the back-end is the model element, itself
 * (not a notation or something else).
 */
public class UMLSyncBucket<M extends Element> extends EObjectMasterSlaveSyncBucket<M, EObject, Notification> {

	public UMLSyncBucket(M model) {
		super(model, model);
	}

	@Override
	protected SyncItem<M, EObject> encapsulate(EObject element) {
		return new UMLSyncItem<M>(getModel(), element);
	}

}
