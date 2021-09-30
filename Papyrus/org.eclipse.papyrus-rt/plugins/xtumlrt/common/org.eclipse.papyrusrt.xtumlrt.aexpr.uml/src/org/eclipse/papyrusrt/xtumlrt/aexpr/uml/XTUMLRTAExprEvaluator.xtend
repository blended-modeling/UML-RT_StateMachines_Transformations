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

import java.util.Stack
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Var
import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.AExprEvaluator
import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes.Scope
import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes.Thunk
import org.eclipse.papyrusrt.xtumlrt.aexpr.names.Name
import org.eclipse.papyrusrt.xtumlrt.common.LiteralInteger
import org.eclipse.papyrusrt.xtumlrt.common.LiteralNatural
import org.eclipse.papyrusrt.xtumlrt.common.LiteralString
import org.eclipse.papyrusrt.xtumlrt.common.OpaqueExpression
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.papyrusrt.xtumlrt.trans.from.uml.UML2xtumlrtTranslator
import org.eclipse.papyrusrt.xtumlrt.common.ValueSpecification
import java.util.Optional

class XTUMLRTAExprEvaluator extends AExprEvaluator
{
	@Accessors UML2xtumlrtTranslator translator
	
	def setTranslator(UML2xtumlrtTranslator trans)
	{
		translator = trans
		XTUMLRTScope.setTranslator(translator)
	}
	
	override protected evalThunk(Thunk thunk, Name canonicalName, Var expr, Scope scope, Stack<Name> seen)
	{
		val thunkValue = thunk.value
		val thunkScope = thunk.scope
		var result = evalThunkValue(thunkValue, thunkScope, canonicalName, expr, scope, seen)
		if (result.isPresent)
		{
			result.get
		}
		else
		{
			super.evalThunk(thunk, canonicalName, expr, scope, seen)
		}
	}
	
	private dispatch def Optional<Integer> evalThunkValue(Object thunkValue, Scope thunkScope, Name canonicalName, Var expr, Scope scope, Stack<Name> seen)
	{
		Optional.empty
	}

	private dispatch def Optional<Integer> evalThunkValue(LiteralInteger thunkValue, Scope thunkScope, Name canonicalName, Var expr, Scope scope, Stack<Name> seen)
	{
		Optional.of(thunkValue.value)
	}
	
	private dispatch def Optional<Integer> evalThunkValue(LiteralNatural thunkValue, Scope thunkScope, Name canonicalName, Var expr, Scope scope, Stack<Name> seen)
	{
		Optional.of(thunkValue.value)
	}

	private dispatch def Optional<Integer> evalThunkValue(LiteralString thunkValue, Scope thunkScope, Name canonicalName, Var expr, Scope scope, Stack<Name> seen)
	{
		val parsedExpr = parser.parse(thunkValue.value)
		seen.push(canonicalName)
		val result = eval(parsedExpr, thunkScope, seen)
		seen.pop()
		return Optional.of(result)
	}

	private dispatch def Optional<Integer> evalThunkValue(OpaqueExpression thunkValue, Scope thunkScope, Name canonicalName, Var expr, Scope scope, Stack<Name> seen)
	{
		val parsedExpr = parser.parse(thunkValue.body)
		seen.push(canonicalName)
		val result = eval(parsedExpr, thunkScope, seen)
		seen.pop()
		return Optional.of(result)
	}

	private dispatch def Optional<Integer> evalThunkValue(org.eclipse.uml2.uml.ValueSpecification thunkValue, Scope thunkScope, Name canonicalName, Var expr, Scope scope, Stack<Name> seen)
	{
		val translatedThunkValue = translator.translate(thunkValue)
		if (translatedThunkValue instanceof ValueSpecification)
		{
			evalThunkValue(translatedThunkValue, thunkScope, canonicalName, expr, scope, seen)
		}
		else
		{
			Optional.empty
		}
	}
}
