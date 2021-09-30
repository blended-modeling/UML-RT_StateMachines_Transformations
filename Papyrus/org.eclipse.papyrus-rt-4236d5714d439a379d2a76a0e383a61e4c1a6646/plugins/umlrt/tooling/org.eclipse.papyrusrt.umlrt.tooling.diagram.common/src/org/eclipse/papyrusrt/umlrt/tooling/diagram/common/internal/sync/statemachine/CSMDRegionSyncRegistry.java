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

import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.sync.UMLRTSyncRegistry;
import org.eclipse.uml2.uml.Region;

/**
 * Synchronization registry for regions in state machine diagrams.
 */
public class CSMDRegionSyncRegistry extends UMLRTSyncRegistry<Region> {

	public CSMDRegionSyncRegistry() {
		super(org.eclipse.papyrusrt.umlrt.core.sync.statemachine.RegionSyncRegistry.class);
	}
}
