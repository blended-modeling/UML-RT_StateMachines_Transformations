/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.types.matcher;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;

/**
 * 
 */
public class RTMessageMatcher implements IElementMatcher {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(EObject eObject) {
		return RTMessageUtils.isRTMessage(eObject);
	}

}
