/*****************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Ernesto Posse - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.util

import org.eclipse.uml2.uml.Constraint
import org.eclipse.uml2.uml.OpaqueExpression
import org.eclipse.uml2.uml.OpaqueBehavior
import org.eclipse.uml2.uml.Operation

/**
 * @author Ernesto Posse
 */
class ActionLangUtils
{
	static val DEFAULT_LANG = "C++"

	static dispatch def String getCode(String actionLanguage, Constraint element)
	{
		getCode(actionLanguage ?: DEFAULT_LANG, element?.specification)
	}

	static dispatch def String getCode(String actionLanguage, OpaqueExpression element)
	{
		if (element !== null)
		{
			val index = element.languages.indexOf(actionLanguage ?: DEFAULT_LANG)
			if (index !== -1)
			{
				element.bodies.get(index)
			}
		}
	}

	static dispatch def String getCode(String actionLanguage, OpaqueBehavior element)
	{
		if (element !== null)
		{
			val index = element.languages.indexOf(actionLanguage ?: DEFAULT_LANG)
			if (index !== -1)
			{
				element.bodies.get(index)
			}
		}
	}

	static dispatch def String getCode(String actionLanguage, Operation element)
	{
		element?.methods?.map[getCode(actionLanguage, it)].filterNull.head
	}
}
