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
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.infra.sync.SyncBucket;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.sync.UMLRTChildNodesSyncFeature;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.sync.UMLRTSyncRegistry;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;

import com.google.common.collect.Iterables;

/**
 * Synchronization feature for the edit-parts visualizing the regions of a state machine
 */
public class CSMDStateMachineRegionsSyncFeature<M extends Element> extends UMLRTChildNodesSyncFeature<M, Region> {

	public CSMDStateMachineRegionsSyncFeature(SyncBucket<M, EditPart, Notification> bucket) {
		super(bucket);
	}

	@Override
	protected Class<? extends UMLRTSyncRegistry<Region>> getNestedSyncRegistryType() {
		return CSMDRegionSyncRegistry.class;
	}

	@Override
	protected SyncBucket<Region, EditPart, Notification> createNestedSyncBucket(Region model, EditPart editPart) {
		return new CSMDRegionSyncBucket(model, editPart);
	}

	@Override
	protected Iterable<? extends Region> getModelContents(M model) {
		return (model instanceof StateMachine)
				? ((StateMachine) model).getRegions()
				: (model instanceof State)
						? ((State) model).getRegions()
						: ECollections.<Region> emptyEList();
	}

	/**
	 * Regions fill the entire contents of a state machine or composite state, so new regions must be dropped
	 * on the shape compartment of some existing region because there is no state machine or state
	 * area uncovered on which they could be dropped.
	 */
	@Override
	protected EditPart getTargetEditPart(EditPart parentEditPart, DropObjectsRequest dropObjectsRequest) {
		EditPart result;

		if (getModelOf(parentEditPart) instanceof StateMachine) {
			// Have to dig in through the initial region
			parentEditPart = (EditPart) parentEditPart.getChildren().get(0);
			result = Iterables.getFirst(Iterables.filter(parentEditPart.getChildren(), ShapeCompartmentEditPart.class), parentEditPart);
		} else {
			// The state-machine diagram expects to drop on the State, not its compartment
			result = parentEditPart.getParent();// super.getTargetEditPart(parentEditPart, dropObjectsRequest);
		}

		return result;
	}
}
