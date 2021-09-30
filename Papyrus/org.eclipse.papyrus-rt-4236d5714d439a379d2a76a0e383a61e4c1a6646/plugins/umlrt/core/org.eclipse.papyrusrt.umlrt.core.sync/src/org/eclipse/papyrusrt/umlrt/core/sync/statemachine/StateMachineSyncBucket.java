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

import org.eclipse.papyrus.infra.sync.EObjectEAttributeSyncFeature;
import org.eclipse.papyrusrt.umlrt.core.sync.UMLSyncBucket;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Root synchronization bucket for capsule state machines.
 */
public class StateMachineSyncBucket extends UMLSyncBucket<StateMachine> {

	public StateMachineSyncBucket(StateMachine model) {
		super(model);

		add(new StateMachineRegionsSyncFeature<StateMachine>(this, UMLPackage.Literals.STATE_MACHINE__REGION));
		add(EObjectEAttributeSyncFeature.create(this, UMLPackage.Literals.NAMED_ELEMENT__NAME));
	}

}
