/*******************************************************************************
 * Copyright (c) 2014-2017 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Initial contribution:
 *   - Ernesto Posse
 *******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes

import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes.Thunk
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtend.lib.annotations.ToString

@ToString(singleLine=true)
@Data
class SimpleThunk implements Thunk
{
	int num
	
	override getValue() 
	{
		num
	}
	
	override getScope() 
	{
		null
	}
}
