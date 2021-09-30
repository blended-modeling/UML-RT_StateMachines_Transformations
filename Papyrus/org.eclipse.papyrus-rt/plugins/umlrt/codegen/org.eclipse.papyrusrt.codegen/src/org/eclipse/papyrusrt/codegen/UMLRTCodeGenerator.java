/*****************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Ltd. and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ernesto Posse - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.codegen;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

public interface UMLRTCodeGenerator {
	public IStatus generate(List<EObject> elements, String top, boolean uml);

	public void setStandalone(boolean standalone);

	public void setRegenerate(boolean flag);

	public boolean getRegenerate();
}
