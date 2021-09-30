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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.EditPartMasterSlaveSyncBucket;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.EditPartSyncItem;
import org.eclipse.papyrus.infra.sync.SyncItem;

/**
 * A specialized master-slave sync bucket that maps slave diagram edit-parts to the element that is redefined by the
 * model element that they visualize.
 */
public abstract class UMLRTMasterSlaveSyncBucket<M extends EObject> extends EditPartMasterSlaveSyncBucket<M, EditPart> {

	private UMLRTSyncRegistry<M> registry;

	public UMLRTMasterSlaveSyncBucket(M model, EditPart master) {
		super(model, master);
	}

	void setRegistry(UMLRTSyncRegistry<M> registry) {
		this.registry = registry;
	}

	@Override
	protected SyncItem<M, EditPart> encapsulate(EditPart element) {
		SyncItem<M, EditPart> result;

		EObject model = ((View) element.getModel()).getElement();
		if (model == getModel()) {
			// This is an encapsulation of the master edit-part. Do as usual
			result = super.encapsulate(element);
		} else {
			result = encapsulateRedefinedElement(element);
		}

		return result;
	}

	protected SyncItem<M, EditPart> encapsulateRedefinedElement(EditPart editPart) {
		return new EditPartSyncItem<M, EditPart>(editPart) {
			@Override
			public M getModel() {
				return getRedefinedElement(super.getModel());
			}
		};
	}

	protected M getRedefinedElement(M model) {
		return registry.getRedefinedElement(model);
	}
}
