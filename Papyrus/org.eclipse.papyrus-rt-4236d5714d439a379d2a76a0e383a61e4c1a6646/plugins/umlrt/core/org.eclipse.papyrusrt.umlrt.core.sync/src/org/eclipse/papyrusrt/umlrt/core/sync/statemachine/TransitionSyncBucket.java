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
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Synchronization bucket for capsule state machine transitions.
 */
public class TransitionSyncBucket extends UMLSyncBucket<Transition> {

	public TransitionSyncBucket(Transition model) {
		super(model);

		add(EObjectEAttributeSyncFeature.create(this, UMLPackage.Literals.NAMED_ELEMENT__NAME));
	}

}
