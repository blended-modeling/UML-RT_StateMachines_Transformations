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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.infra.sync.SyncBucket;
import org.eclipse.papyrus.infra.sync.service.AbstractSyncTrigger;
import org.eclipse.papyrus.infra.sync.service.ISyncAction;
import org.eclipse.papyrus.infra.sync.service.ISyncService;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.Activator;
import org.eclipse.uml2.uml.StateMachine;

/**
 * Capsule state machine diagram synchronization trigger.
 */
public class CapsuleStatemachineDiagramSyncTrigger extends AbstractSyncTrigger {

	public CapsuleStatemachineDiagramSyncTrigger() {
		super();
	}

	@Override
	public ISyncAction trigger(ISyncService syncService, Object object) {
		ISyncAction result = null;

		if (object instanceof DiagramEditPart) {
			// We're interested in the state machine frame edit part
			EditPart stateMachine = ((DiagramEditPart) object).getPrimaryChildEditPart();
			if (stateMachine != null) {
				result = synchronizeStateMachineAction();
			}
		}

		return result;
	}

	protected ISyncAction synchronizeStateMachineAction() {
		return new ISyncAction() {

			@Override
			public IStatus perform(ISyncService syncService, Object object) {
				IStatus result = Status.OK_STATUS;

				// We're interested in the state machine frame edit part
				EditPart stateMachineFrame = ((DiagramEditPart) object).getPrimaryChildEditPart();
				StateMachine stateMachine = AdapterUtils.adapt(stateMachineFrame, StateMachine.class, null);
				if (stateMachine == null) {
					result = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Edit-part does not visualize a StateMachine");
				} else {
					// Set up the state machine diagram as a sync master for any potential redefining machines
					CSMDStateMachineSyncRegistry registry = syncService.getSyncRegistry(CSMDStateMachineSyncRegistry.class);
					if (registry != null) {
						SyncBucket<StateMachine, EditPart, Notification> bucket = registry.getBucket(stateMachine);
						if (bucket == null) {
							bucket = new CSMDStateMachineSyncBucket(stateMachine, stateMachineFrame);
							registry.register(bucket);
						}

						// Look for a master state machine to synchronize with
						StateMachine master = registry.getRedefinedElement(stateMachine);
						if (master != null) {
							SyncBucket<StateMachine, EditPart, Notification> masterBucket = registry.getBucket(master);
							if (masterBucket != null) {
								masterBucket.add(stateMachineFrame);
							}
						}
					}
				}

				return result;
			}
		};
	}
}
