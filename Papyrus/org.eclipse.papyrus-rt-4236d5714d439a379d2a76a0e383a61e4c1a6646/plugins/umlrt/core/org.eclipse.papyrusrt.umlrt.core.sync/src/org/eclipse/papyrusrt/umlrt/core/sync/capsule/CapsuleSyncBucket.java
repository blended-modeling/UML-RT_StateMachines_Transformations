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

import org.eclipse.papyrusrt.umlrt.core.sync.UMLSyncBucket;

/**
 * Root synchronization bucket for capsules.
 */
public class CapsuleSyncBucket extends UMLSyncBucket<org.eclipse.uml2.uml.Class> {

	public CapsuleSyncBucket(org.eclipse.uml2.uml.Class model) {
		super(model);

		add(new CapsuleStateMachineSyncFeature(this));
	}

}
