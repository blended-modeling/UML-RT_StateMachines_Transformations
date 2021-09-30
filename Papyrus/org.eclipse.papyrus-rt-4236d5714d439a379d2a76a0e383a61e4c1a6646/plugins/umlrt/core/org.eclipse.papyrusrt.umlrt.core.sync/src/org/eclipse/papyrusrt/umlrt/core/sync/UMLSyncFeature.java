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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.papyrus.infra.sync.EObjectEReferenceSyncFeature;
import org.eclipse.papyrus.infra.sync.SyncBucket;
import org.eclipse.papyrus.infra.sync.SyncRegistry;
import org.eclipse.papyrus.uml.tools.utils.CustomUMLUtil;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.uml2.uml.Element;

/**
 * Base UML synchronization feature.
 */
public abstract class UMLSyncFeature<M extends Element, N extends Element> extends EObjectEReferenceSyncFeature<M> {

	public UMLSyncFeature(SyncBucket<M, EObject, Notification> bucket, EReference reference, EReference... more) {
		super(bucket, reference, more);
	}

	protected abstract Class<? extends SyncRegistry<N, EObject, Notification>> getNestedRegistryType();

	protected SyncRegistry<N, EObject, Notification> getNestedRegistry() {
		return getSyncRegistry(getNestedRegistryType());
	}

	@Override
	protected Command createDeleteCommand(final EObject object) {
		return new RecordingCommand(getEditingDomain()) {

			@Override
			protected void doExecute() {
				if (object instanceof Element) {
					((Element) object).destroy();
				} else {
					CustomUMLUtil.destroy(object);
				}
			}
		};
	}

	@Override
	protected boolean match(EObject sourceModel, EObject targetModel) {
		boolean result = false;

		// One case of a match is when I already have established synchronization between these elements
		SyncRegistry<N, EObject, Notification> nestedRegistry = getNestedRegistry();
		result = nestedRegistry.getModelType().isInstance(sourceModel) && nestedRegistry.synchronizes(targetModel, nestedRegistry.getModelType().cast(sourceModel));
		result = result || CapsuleUtils.redefines(targetModel, sourceModel);

		return result;
	}
}
