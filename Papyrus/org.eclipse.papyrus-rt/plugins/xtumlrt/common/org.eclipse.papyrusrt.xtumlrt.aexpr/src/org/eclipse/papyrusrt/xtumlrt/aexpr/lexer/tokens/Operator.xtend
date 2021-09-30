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
class Operator extends Token
{
	static val char PLUS_CHAR  = '+'
	static val char MINUS_CHAR = '-'
	static val char TIMES_CHAR = '*'
	static val char DIV_CHAR   = '/'
	static val char MOD_CHAR   = '%'
	public static val PLUS_STR  = "+"
	public static val MINUS_STR = "-"
	public static val TIMES_STR = "*"
	public static val DIV_STR   = "/"
	public static val MOD_STR   = "%"
	public static val BUILT_IN_CHAR = #[ PLUS_CHAR, MINUS_CHAR, TIMES_CHAR, DIV_CHAR, MOD_CHAR ]
	public static val BUILT_IN_STR  = #[ PLUS_STR, MINUS_STR, TIMES_STR, DIV_STR, MOD_STR ]
	
	enum Kind
	{
		UNKNOWN,
		PLUS,
		MINUS,
		TIMES,
		DIV,
		MOD
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
				case PLUS_STR:	Kind.PLUS
				case MINUS_STR:	Kind.MINUS
				case TIMES_STR:	Kind.TIMES
				case DIV_STR:	Kind.DIV
				case MOD_STR:	Kind.MOD
				default:		Kind.UNKNOWN
			}
		}
		kind
	}

	static def isOperator(char c)
	{
		c === PLUS_CHAR 
		|| c === MINUS_CHAR
		|| c === TIMES_CHAR
		|| c === DIV_CHAR
		|| c === MOD_CHAR
	}
}
