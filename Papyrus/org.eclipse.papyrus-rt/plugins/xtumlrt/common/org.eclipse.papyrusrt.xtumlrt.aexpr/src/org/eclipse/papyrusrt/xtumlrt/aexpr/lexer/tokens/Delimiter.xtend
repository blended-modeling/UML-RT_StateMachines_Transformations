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
class Delimiter extends Token
{
	public static val char LPAR_CHAR = '('
	public static val char RPAR_CHAR = ')'
	static val LPAR_STR = "("
	static val RPAR_STR = ")"
	
	enum Kind
	{
		UNKNOWN,
		LPAR,
		RPAR
	}

	Kind kind

	new(int line, int column, String text)
	{
		super(line, column, text)
		kind = getKind
	}

	def getKind()
	{
		if (kind === null)
		{
			kind = switch text
			{
				case LPAR_STR:	Kind.LPAR
				case RPAR_STR:	Kind.RPAR
				default:		Kind.UNKNOWN
			}
		}
		kind
	}
	
	static def isBracket(char c)
	{
		c === LPAR_CHAR || c === RPAR_CHAR
	}
}
