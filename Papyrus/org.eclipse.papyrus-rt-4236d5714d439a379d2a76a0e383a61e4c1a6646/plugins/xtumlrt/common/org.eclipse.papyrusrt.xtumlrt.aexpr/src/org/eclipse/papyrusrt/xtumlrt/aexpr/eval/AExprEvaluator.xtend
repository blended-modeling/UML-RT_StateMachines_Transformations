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
package org.eclipse.papyrusrt.xtumlrt.aexpr.eval

import java.util.Map
import java.util.Stack
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Num
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Var
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.AExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.UnaryExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Op
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.BinExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes.Scope
import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes.SimpleScope
import org.eclipse.papyrusrt.xtumlrt.aexpr.names.Name
import org.eclipse.papyrusrt.xtumlrt.aexpr.parser.AExprParser
import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes.Thunk

class AExprEvaluator
{
	protected val parser = new AExprParser
	
	def int eval(String expr)
	{
		val parsed = parser.parse(expr)
		eval(parsed, new SimpleScope(#{} as Map<String,Integer>), new Stack)
	}
	
	def int eval(String expr, Scope scope)
	{
		val parsed = parser.parse(expr)
		eval(parsed, scope, new Stack)
	}
	
	dispatch def int eval(Num expr, Scope scope, Stack<Name> seen)
	{
		expr.value
	}
	
	dispatch def int eval(Var expr, Scope scope, Stack<Name> seen)
	{
		val canonicalName = scope.canonicalName(expr.name)
		if (seen.contains(canonicalName))
		{
			throw new CycleDetectedException(expr, scope)
		}
		val thunk = scope?.get(expr.name)
		if (thunk === null)
		{
			throw new UnresolvedVariableException(expr, scope)
		}
		evalThunk(thunk, canonicalName, expr, scope, seen)
	}
	
	dispatch def int eval(UnaryExpr expr, Scope scope, Stack<Name> seen)
	{
		switch expr.op
		{
			case Op.PLUS:	eval(expr.operand, scope, seen)
			case Op.MINUS:	-eval(expr.operand, scope, seen)
			default: throw new UnrecognizedOperatorException(expr, scope)
		}
	}
	
	dispatch def int eval(BinExpr expr, Scope scope, Stack<Name> seen)
	{
		switch expr.op
		{
			case Op.PLUS:	eval(expr.left, scope, seen) + eval(expr.right, scope, seen)
			case Op.MINUS:	eval(expr.left, scope, seen) - eval(expr.right, scope, seen)
			case Op.TIMES:	eval(expr.left, scope, seen) * eval(expr.right, scope, seen)
			case Op.DIV:	eval(expr.left, scope, seen) / eval(expr.right, scope, seen)
			case Op.MOD:	eval(expr.left, scope, seen) % eval(expr.right, scope, seen)
			default: throw new UnrecognizedOperatorException(expr, scope)
		}
	}
	
	protected def evalThunk(Thunk thunk, Name canonicalName, Var expr, Scope scope, Stack<Name> seen)
	{
		val object = thunk.value
		if (object instanceof Integer)
		{
			object.intValue
		}
		else if (object instanceof String)
		{
			val parsedExpr = parser.parse(object)
			seen.push(canonicalName)
			val result = eval(parsedExpr, thunk.scope, seen)
			seen.pop()
			return result
		}
		else if (object instanceof AExpr)
		{
			seen.push(canonicalName)
			val result = eval(object, thunk.scope, seen)
			seen.pop()
			return result
		}
		else
		{
			throw new InvalidVariableValueException(expr, scope)
		}
	}
}
