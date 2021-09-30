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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.papyrus.infra.sync.SyncBucket;
import org.eclipse.papyrus.infra.sync.SyncItem;
import org.eclipse.papyrus.infra.sync.SyncRegistry;
import org.eclipse.papyrusrt.umlrt.core.sync.UMLSyncFeature;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A synchronization feature for matching the regions of a state machine with the state machine that it redefines.
 */
public class StateMachineRegionsSyncFeature<M extends Element> extends UMLSyncFeature<M, Region> {

	public StateMachineRegionsSyncFeature(SyncBucket<M, EObject, Notification> bucket, EReference regionsReference) {
		super(bucket, regionsReference);
	}

	@Override
	protected Class<? extends SyncRegistry<Region, EObject, Notification>> getNestedRegistryType() {
		return RegionSyncRegistry.class;
	}

	@Override
	protected Command onTargetAdded(SyncItem<M, EObject> from, EObject source, SyncItem<M, EObject> to, EObject target) {
		Command result = null;

		Region region = (Region) target;
		Region superRegion = (Region) source;

		// Ensure the redefinition semantics
		if (region.getExtendedRegion() != superRegion) {
			result = SetCommand.create(getEditingDomain(), region, UMLPackage.Literals.REGION__EXTENDED_REGION, superRegion);
		}

		SyncRegistry<Region, EObject, Notification> registry = getNestedRegistry();
		if (registry != null) {
			// Synchronize with the super-statemachine's region
			SyncBucket<Region, EObject, Notification> bucket = registry.getBucket(superRegion);
			if (bucket == null) {
				bucket = new RegionSyncBucket(superRegion);
				registry.register(bucket);
			}
			result = synchronizingWrapper(registry, region, result);
		}

		return result;
	}

	@Override
	protected Command onTargetRemoved(SyncItem<M, EObject> to, EObject target) {
		// TODO Purge synchronizations no longer needed (if that's even necessary?)
		return null;
	}
}
