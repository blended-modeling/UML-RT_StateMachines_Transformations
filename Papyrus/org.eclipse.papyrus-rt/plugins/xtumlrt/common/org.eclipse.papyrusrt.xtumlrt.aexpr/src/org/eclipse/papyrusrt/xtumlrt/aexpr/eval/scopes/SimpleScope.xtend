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

import java.util.Map
import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes.Scope
import org.eclipse.papyrusrt.xtumlrt.aexpr.names.Name
import org.eclipse.xtend.lib.annotations.Data

@Data
class SimpleScope implements Scope
{
	Map<String, Integer> map
	
	override get(Name name)
	{
		new SimpleThunk(map.get(name.text))
	}
	
	override context()
	{
		null
	}
	
	override canonicalName(Name name)
	{
		name
	}
	
	override contextName()
	{
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}
