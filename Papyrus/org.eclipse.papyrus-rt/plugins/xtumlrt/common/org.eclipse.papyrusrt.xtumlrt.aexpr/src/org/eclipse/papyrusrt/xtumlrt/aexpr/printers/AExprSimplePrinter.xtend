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

import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Num
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Var
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.UnaryExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.BinExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.printers.AExprPrinter

class AExprSimplePrinter implements AExprPrinter
{
	extension OpPrinter opPrinter = new OpPrinter
	extension NamePrinter namePrinter = new NamePrinter
	
	def dispatch String toText(Num e)		'''«e.value»'''
	def dispatch String toText(Var e)		'''«e.name.toText»'''
	def dispatch String toText(UnaryExpr e)	'''(«e.op.opText» «e.operand.toText»)'''
	def dispatch String toText(BinExpr e)	'''(«e.left.toText» «e.op.opText» «e.right.toText»)'''
}
