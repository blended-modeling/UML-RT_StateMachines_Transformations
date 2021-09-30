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

import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Var
import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes.Scope
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtend.lib.annotations.ToString
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.AExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.UnaryExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.BinExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.printers.OpPrinter

@ToString(singleLine=true)
@Data
class UnrecognizedOperatorException extends AExprEvaluatorException
{
	Scope scope
	static extension OpPrinter opPrinter = new OpPrinter
	
	override getMessage()
	'''Unrecognized operator '«expr.op»' found when attempting to evaluate variable '«(expr as Var).name.toText»' in scope context «scope.contextName.toText»'''
	
	def op(AExpr expr)
	{
		if (expr instanceof UnaryExpr)
			expr.op.opText
		else if (expr instanceof BinExpr)
			expr.op.opText
		else 
			"<unrecognized expression type>"
	}
}
