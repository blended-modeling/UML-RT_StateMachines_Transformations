/*******************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *******************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.properties.providers;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 * Interface for table selection provider.
 * 
 * @author ysroh
 *
 */
public interface IRTTableSelectionProvider extends ISelectionProvider {

	/**
	 * Get table manager.
	 * 
	 * @return manager
	 */
	INattableModelManager getTableManager();
}
