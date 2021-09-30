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
package org.eclipse.papyrusrt.xtumlrt.aexpr.printers

import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Op
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Operator

class OpPrinter
{
	def opText(Op op)
	{
		switch op
		{
			case TIMES:		Operator.TIMES_STR
			case DIV:		Operator.DIV_STR
			case MOD:		Operator.MOD_STR
			case PLUS:		Operator.PLUS_STR
			case MINUS:		Operator.MINUS_STR
			case UMINUS:	Operator.MINUS_STR
			default:		"?"
		}
	}
}
