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
package org.eclipse.papyrusrt.xtumlrt.aexpr.uml

import java.lang.RuntimeException
import org.eclipse.xtend.lib.annotations.ToString
import org.eclipse.xtend.lib.annotations.Data 

@ToString(singleLine=true)
@Data
class NoResourceFound extends RuntimeException
{
	String element
	override getMessage()
	'''Unable to determine a resource containing «element»'''
}
