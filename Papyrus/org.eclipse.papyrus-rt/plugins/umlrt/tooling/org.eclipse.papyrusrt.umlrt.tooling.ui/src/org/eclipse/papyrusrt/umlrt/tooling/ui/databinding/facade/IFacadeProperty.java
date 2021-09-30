/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.ui.databinding.facade;

import org.eclipse.core.databinding.property.IProperty;

/**
 * A data-binding property for façade objects.
 */
public interface IFacadeProperty extends IProperty {

	/**
	 * Obtains the name of the property, used for labelling edit commands.
	 * 
	 * @return the user-presentable property name
	 */
	String getPropertyName();

}
