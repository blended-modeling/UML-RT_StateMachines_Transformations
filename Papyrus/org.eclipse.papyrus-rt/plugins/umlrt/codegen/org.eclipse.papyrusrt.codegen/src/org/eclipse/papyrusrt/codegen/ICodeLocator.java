/*******************************************************************************
 * Copyright (c) 2014-2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen;

import org.eclipse.core.resources.IFile;

/**
 * Interface class for code locator.
 * 
 * @author ysroh
 *
 */
public interface ICodeLocator {
	/**
	 * Returns the line number of the argument file on which the argument object's code
	 * begins. Return -1 if no code can be found for the argument object.
	 * 
	 * @param file
	 *            source file
	 * @param label
	 *            label info
	 * @return line number
	 */
	int getLineNumber(IFile file, UserEditableRegion.Label label);
}
