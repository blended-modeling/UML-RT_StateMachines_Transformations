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

import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.sync.DiagramEdgesSyncFeature;
import org.eclipse.papyrus.infra.sync.SyncBucket;
import org.eclipse.papyrus.infra.sync.SyncItem;

import com.google.common.collect.MapMaker;

/**
 * Synchronization feature for the edit-parts visualizing connections in an UML-RT diagram.
 */
public abstract class UMLRTDiagramEdgesSyncFeature<M extends EObject, N extends EObject> extends DiagramEdgesSyncFeature<M, N, EditPart> {
	private final Map<N, N> lastKnownMatch = new MapMaker().weakKeys().weakValues().makeMap();

	public UMLRTDiagramEdgesSyncFeature(SyncBucket<M, EditPart, Notification> bucket) {
		super(bucket);
	}

	@Override
	protected abstract Class<? extends UMLRTSyncRegistry<N>> getNestedSyncRegistryType();

	@Override
	protected UMLRTSyncRegistry<N> getNestedSyncRegistry() {
		// This cast is safe because we narrowed the result type of the getNestedSyncRegistryType() method
		return (UMLRTSyncRegistry<N>) super.getNestedSyncRegistry();
	}

	protected abstract SyncBucket<N, EditPart, Notification> createNestedSyncBucket(N model, EditPart editPart);

	protected abstract Iterable<? extends N> getModelContents(M model);

	@Override
	protected boolean match(EObject sourceModel, EObject targetModel) {
		final UMLRTSyncRegistry<N> nestedRegistry = getNestedSyncRegistry();

		// Is the source object redefined by the target object? Or was it when last the source model was attached?
		N matched = nestedRegistry.getRedefinedElement(nestedRegistry.getModelType().cast(targetModel));
		return (sourceModel.eResource() == null)
				? lastKnownMatch.get(targetModel) == sourceModel
				: matched == sourceModel;
	}

	/**
	 * Finds and returns the model object in the {@code to} side of a synchronization object that corresponds to
	 * the given source object in the {@code from} side.
	 * 
	 * @param from
	 *            the source sync-item of a synchronization operation
	 * @param to
	 *            the target sync-item of a synchronization operation
	 * @param sourceModel
	 *            an object added to the {@link SyncItem#getModel() model} of the {@code from} item
	 * @return the corresponding object in the {@code model} of the {@code to} item
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected EObject getTargetModel(SyncItem<M, EditPart> from, SyncItem<M, EditPart> to, EObject sourceModel) {
		EObject result = sourceModel;

		for (N target : getModelContents((M) getModelOf(to.getBackend()))) {
			if (match(sourceModel, target)) {
				result = target;
				break;
			}
		}

		return result;
	}

	@Override
	protected Command onTargetAdded(SyncItem<M, EditPart> from, EObject source, SyncItem<M, EditPart> to, EditPart target) {
		final UMLRTSyncRegistry<N> nestedRegistry = getNestedSyncRegistry();

		N nested = nestedRegistry.getModelOf(target);
		N masterNested = (nested == null) ? null : nestedRegistry.getRedefinedElement(nested);

		if (masterNested != null) {
			SyncItem<M, EditPart> master = getMaster();
			for (EditPart next : getContents(master.getBackend())) {
				N matchNested = nestedRegistry.getModelOf(next);
				if (matchNested == masterNested) {
					// Remember this pairing in case the underlying model elements are later deleted
					lastKnownMatch.put(nested, masterNested);

					// Synchronize our new child with this master edit-part
					SyncBucket<N, EditPart, Notification> bucket = nestedRegistry.getBucket(masterNested);
					if (bucket == null) {
						bucket = createNestedSyncBucket(masterNested, next);
						nestedRegistry.register(bucket);
					}
					bucket.add(target);
					break;
				}
			}
		}

		return null;
	}

	@Override
	protected Command onTargetRemoved(SyncItem<M, EditPart> to, EditPart target) {
		final UMLRTSyncRegistry<N> nestedRegistry = getNestedSyncRegistry();

		N nested = nestedRegistry.getModelOf(target);
		N masterNested = (nested == null) ? null : nestedRegistry.getRedefinedElement(nested);

		if (masterNested != null) {
			SyncItem<M, EditPart> master = getMaster();
			for (EditPart next : getContents(master.getBackend())) {
				N matchNested = nestedRegistry.getModelOf(next);
				if (matchNested == masterNested) {
					SyncBucket<N, EditPart, Notification> bucket = nestedRegistry.getBucket(masterNested);
					if (bucket != null) {
						bucket.remove(target);
					}
					break;
				}
			}
		}

		return null;
	}
}
