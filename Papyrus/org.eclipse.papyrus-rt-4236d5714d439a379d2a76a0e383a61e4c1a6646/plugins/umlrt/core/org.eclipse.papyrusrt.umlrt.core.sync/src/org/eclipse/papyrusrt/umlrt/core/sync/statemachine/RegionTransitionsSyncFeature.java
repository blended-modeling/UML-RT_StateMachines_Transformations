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
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.papyrus.infra.sync.SyncBucket;
import org.eclipse.papyrus.infra.sync.SyncItem;
import org.eclipse.papyrus.infra.sync.SyncRegistry;
import org.eclipse.papyrusrt.umlrt.core.sync.UMLSyncFeature;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;

import com.google.common.base.Predicate;

/**
 * A synchronization feature for matching the transitions of a region with the region that it redefines.
 */
public class RegionTransitionsSyncFeature extends UMLSyncFeature<Region, Transition> {

	public RegionTransitionsSyncFeature(SyncBucket<Region, EObject, Notification> bucket) {
		super(bucket, UMLPackage.Literals.REGION__TRANSITION);
	}

	@Override
	protected Class<? extends SyncRegistry<Transition, EObject, Notification>> getNestedRegistryType() {
		return TransitionSyncRegistry.class;
	}

	@Override
	protected Command onTargetAdded(SyncItem<Region, EObject> from, EObject source, SyncItem<Region, EObject> to, EObject target) {
		Command result = null;
		Transition transition = (Transition) target;
		Transition superTransition = (Transition) source;

		result = SetCommand.create(getEditingDomain(), transition, UMLPackage.Literals.TRANSITION__REDEFINED_TRANSITION, source);
		result = result.chain(SetCommand.create(getEditingDomain(), transition, UMLPackage.Literals.TRANSITION__SOURCE, findRedefiningVertex(transition.getContainer(), superTransition.getSource())));
		result = result.chain(SetCommand.create(getEditingDomain(), transition, UMLPackage.Literals.TRANSITION__TARGET, findRedefiningVertex(transition.getContainer(), superTransition.getTarget())));

		SyncRegistry<Transition, EObject, Notification> registry = getNestedRegistry();
		if (registry != null) {
			// Synchronize with the corresponding transition in the super state machine
			SyncBucket<Transition, EObject, Notification> bucket = registry.getBucket(superTransition);
			if (bucket == null) {
				bucket = new TransitionSyncBucket(superTransition);
				registry.register(bucket);
			}
			result = synchronizingWrapper(registry, transition, result);
		}

		return result;
	}

	@Override
	protected Command onTargetRemoved(SyncItem<Region, EObject> to, EObject target) {
		// TODO Purge synchronizations no longer needed (if that's even necessary?)
		return null;
	}

	protected Vertex findRedefiningVertex(Region context, Vertex superVertex) {
		Vertex result = null;
		final StateMachine stateMachine = context.getStateMachine();

		VertexSyncRegistry registry = getSyncRegistry(VertexSyncRegistry.class);
		SyncBucket<Vertex, EObject, Notification> bucket = registry.getBucket(superVertex);
		if (bucket != null) {
			result = (Vertex) bucket.findBackend(new Predicate<EObject>() {
				@Override
				public boolean apply(EObject input) {
					Vertex vertex = (Vertex) input;
					return (vertex.getContainer().containingStateMachine() == stateMachine);
				}
			});
		}

		return result;
	}
}
