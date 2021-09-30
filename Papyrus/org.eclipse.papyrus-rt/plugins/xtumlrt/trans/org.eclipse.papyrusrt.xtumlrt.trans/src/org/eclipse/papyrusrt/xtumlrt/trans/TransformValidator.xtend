/*******************************************************************************
* Copyright (c) 2017 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.trans

import org.eclipse.core.runtime.MultiStatus

/**
 * 
 * @author ysroh
 */
public interface TransformValidator<T> {

	/**
	 * Validate given element for transformation.
	 * 
	 * @param context
	 *            model to be validated
	 * @return multi status
	 */
	def MultiStatus validate(T element);
	
}