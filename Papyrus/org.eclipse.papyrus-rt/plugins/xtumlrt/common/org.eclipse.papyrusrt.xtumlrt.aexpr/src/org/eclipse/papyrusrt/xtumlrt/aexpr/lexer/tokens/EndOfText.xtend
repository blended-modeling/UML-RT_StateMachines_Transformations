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
package org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens

import org.eclipse.xtend.lib.annotations.ToString

@ToString(singleLine=true)
class EndOfText extends Token
{
	public static val char EOT = 0 as char

	new(int line, int column)
	{
		super(line, column, "" + EOT)
	}
	
	static def isEOT(char c) { c === EOT }
}