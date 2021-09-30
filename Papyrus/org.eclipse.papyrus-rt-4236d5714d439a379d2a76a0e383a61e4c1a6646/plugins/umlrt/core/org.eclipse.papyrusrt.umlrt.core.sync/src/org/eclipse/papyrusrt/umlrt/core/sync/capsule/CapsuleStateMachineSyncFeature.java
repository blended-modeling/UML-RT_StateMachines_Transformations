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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.papyrus.infra.sync.SyncBucket;
import org.eclipse.papyrus.infra.sync.SyncItem;
import org.eclipse.papyrus.infra.sync.SyncRegistry;
import org.eclipse.papyrusrt.umlrt.core.sync.UMLSyncFeature;
import org.eclipse.papyrusrt.umlrt.core.sync.statemachine.StateMachineSyncBucket;
import org.eclipse.papyrusrt.umlrt.core.sync.statemachine.StateMachineSyncRegistry;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A synchronization feature for matching the regions of a state machine with the state machine that it redefines.
 */
public class CapsuleStateMachineSyncFeature extends UMLSyncFeature<org.eclipse.uml2.uml.Class, StateMachine> {

	public CapsuleStateMachineSyncFeature(CapsuleSyncBucket bucket) {
		super(bucket, UMLPackage.Literals.BEHAVIORED_CLASSIFIER__CLASSIFIER_BEHAVIOR);
	}

	@Override
	protected Class<? extends SyncRegistry<StateMachine, EObject, Notification>> getNestedRegistryType() {
		return StateMachineSyncRegistry.class;
	}

	@Override
	protected Command onTargetAdded(SyncItem<org.eclipse.uml2.uml.Class, EObject> from, EObject source, SyncItem<org.eclipse.uml2.uml.Class, EObject> to, EObject target) {
		Command result = null;

		if (target instanceof StateMachine) {
			StateMachine stateMachine = (StateMachine) target;

			if (source instanceof StateMachine) {
				StateMachine superStateMachine = (StateMachine) source;

				// Ensure the redefinition semantics
				if (!stateMachine.getRedefinedBehaviors().contains(superStateMachine)) {
					result = AddCommand.create(getEditingDomain(), stateMachine, UMLPackage.Literals.BEHAVIOR__REDEFINED_BEHAVIOR, superStateMachine);
				}

				SyncRegistry<StateMachine, EObject, Notification> registry = getNestedRegistry();
				if (registry != null) {
					// Synchronize with the super-capsule's state machine
					SyncBucket<StateMachine, EObject, Notification> bucket = registry.getBucket(superStateMachine);
					if (bucket == null) {
						bucket = new StateMachineSyncBucket(superStateMachine);
						registry.register(bucket);
					}
					result = synchronizingWrapper(registry, stateMachine, result);
				}
			}
		}

		return result;
	}

	@Override
	protected Command onTargetRemoved(SyncItem<org.eclipse.uml2.uml.Class, EObject> to, EObject target) {
		// TODO Purge synchronizations no longer needed (if that's even necessary?)
		return null;
	}
}
