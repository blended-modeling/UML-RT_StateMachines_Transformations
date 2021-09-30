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
import org.eclipse.uml2.uml.Transition;

/**
 * Synchronization registry for the transitions in a state machine diagrams.
 */
public class CSMDTransitionSyncRegistry extends UMLRTSyncRegistry<Transition> {

	public CSMDTransitionSyncRegistry() {
		super(org.eclipse.papyrusrt.umlrt.core.sync.statemachine.TransitionSyncRegistry.class);
	}
}
