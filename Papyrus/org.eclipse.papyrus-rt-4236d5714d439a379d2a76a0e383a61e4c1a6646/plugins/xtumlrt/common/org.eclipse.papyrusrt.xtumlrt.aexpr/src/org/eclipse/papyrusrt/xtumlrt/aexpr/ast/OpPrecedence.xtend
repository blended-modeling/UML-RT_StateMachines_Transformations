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
package org.eclipse.papyrusrt.xtumlrt.aexpr.ast

/**
 * Declaration of operator precedence and operations for determining their relative precedence.
 */
class OpPrecedence
{
	def precedence(Op op)
	{
		switch op
		{
			case UMINUS:	6
			case TIMES:		5
			case DIV:		4
			case MOD:		3
			case PLUS:		2
			case MINUS:		1
			default:		0
		}
	}
	
	def bindsTighter(Op op1, Op op2) { op1.precedence >= op2.precedence }
}
