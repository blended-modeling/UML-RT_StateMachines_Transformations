/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.defaultlanguage;

import java.util.List;

/**
 * Interface implemented by the registry of default languages
 */
public interface IDefaultLanguageRegistry {

	/**
	 * Returns the list of currently registered {@link IDefaultLanguage}.
	 * 
	 * @return the list of currently registered {@link IDefaultLanguage} or an empty list if none is currently registered
	 */
	List<? extends IDefaultLanguage> getLanguages();

}