/*******************************************************************************
 * Copyright (c) 2014-2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.codegen.UserEditableRegion.CommitResult;

/**
 * Implementations of this interface are able to find the EObject associated with a URI.
 * The implementations are contributed by extending the associated extension point.
 */
public interface IEObjectLocator {
	/**
	 * Returns null if the associated EObject cannot be found.
	 * 
	 * @param label
	 *            tag info
	 * @return EObject
	 */
	EObject getEObject(UserEditableRegion.Label label);

	/**
	 * Find EObject and save given source.
	 * 
	 * @param label
	 *            tag info
	 * @param source
	 *            user source
	 * @return EObject
	 */
	CommitResult saveSource(UserEditableRegion.Label label, String source);

	/**
	 * Initial setup.
	 */
	void initialize();

	/**
	 * Clean up.
	 */
	void cleanUp();
}
