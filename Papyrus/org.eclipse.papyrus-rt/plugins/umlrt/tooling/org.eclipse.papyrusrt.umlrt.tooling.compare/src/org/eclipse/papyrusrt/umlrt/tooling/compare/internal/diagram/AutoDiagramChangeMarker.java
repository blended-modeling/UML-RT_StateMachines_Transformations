/*****************************************************************************
 * Copyright (c) 2017 EclipseSource Services GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Philip Langer - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal.diagram;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * Adapter for marking diffs to be an automatically created diagram change.
 * 
 * @author Philip Langer <planger@eclipsesource.com>
 */
public class AutoDiagramChangeMarker extends AdapterImpl implements IAutoDiagramChangeMarker {

	@Override
	public boolean isAdapterForType(Object type) {
		return type == IAutoDiagramChangeMarker.class;
	}

}
