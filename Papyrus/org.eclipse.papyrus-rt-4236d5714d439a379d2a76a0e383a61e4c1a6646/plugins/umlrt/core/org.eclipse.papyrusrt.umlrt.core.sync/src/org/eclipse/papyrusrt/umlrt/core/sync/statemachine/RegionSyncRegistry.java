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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.core.sync.UMLSyncRegistry;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.uml2.uml.Region;

/**
 * Capsule statemachine region synchronization registry.
 */
public class RegionSyncRegistry extends UMLSyncRegistry<Region> {

	public RegionSyncRegistry() {
		super();
	}

	@Override
	public Region getModelOf(EObject backend) {
		return (backend instanceof Region) ? CapsuleUtils.getSuperRegion((Region) backend) : null;
	}
}
