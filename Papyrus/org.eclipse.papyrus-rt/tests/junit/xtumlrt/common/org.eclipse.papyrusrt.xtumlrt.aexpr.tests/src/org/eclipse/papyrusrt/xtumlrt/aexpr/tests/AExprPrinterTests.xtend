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
package org.eclipse.papyrusrt.xtumlrt.aexpr.tests

import org.junit.Test
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Num
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Var
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Op
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.UnaryExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.BinExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.printers.AExprPrinterExtender
import org.eclipse.papyrusrt.xtumlrt.aexpr.printers.AExprSimplePrinter
import org.eclipse.papyrusrt.xtumlrt.aexpr.printers.AExprAltSimplePrinter
import org.eclipse.papyrusrt.xtumlrt.aexpr.printers.AExprPrettyPrinter
import static org.eclipse.papyrusrt.xtumlrt.aexpr.tests.TestUtils.*

class AExprPrinterTests
{
	@Test
	def void test0astPrinterTest()
	{
		println("== astPrinterTest ============================================")
		val cases =
		#[
			new Num(1)									->	"Num [value = 1]",
			new Var("x")								->	"Var [name = Name [text = \"x\"]]",
			new UnaryExpr(Op.UMINUS, new Num(1))		->	"UnaryExpr [op = UMINUS, operand = Num [value = 1]]",
			new BinExpr
			(
				Op.TIMES, 
				new Var("x"), 
				new UnaryExpr(Op.UMINUS, new Num(1))
			)											->	"BinExpr [\n  op = TIMES\n  left = Var [name = Name [text = \"x\"]]\n  right = UnaryExpr [op = UMINUS, operand = Num [value = 1]]\n]",
			new BinExpr
			(
				Op.PLUS, 
				new BinExpr
				(
					Op.TIMES, 
					new Var("x"), 
					new UnaryExpr(Op.UMINUS, new Num(1))
				), 
				new Num(2)
			) 											-> "BinExpr [\n  op = PLUS\n  left = BinExpr [\n    op = TIMES\n    left = Var [name = Name [text = \"x\"]]\n    right = UnaryExpr [op = UMINUS, operand = Num [value = 1]]\n  ]\n  right = Num [value = 2]\n]",
			new BinExpr
			(
				Op.MINUS, 
				new Var("x"), 
				new Num(1)
			)											-> "BinExpr [\n  op = MINUS\n  left = Var [name = Name [text = \"x\"]]\n  right = Num [value = 1]\n]",
			new BinExpr
			(
				Op.DIV, 
				new BinExpr
				(
					Op.MINUS, 
					new Var("x"), 
					new Num(1)
				), 
				new Num(2)
			) 											-> "BinExpr [\n  op = DIV\n  left = BinExpr [\n    op = MINUS\n    left = Var [name = Name [text = \"x\"]]\n    right = Num [value = 1]\n  ]\n  right = Num [value = 2]\n]"
		]
		doTestCaseList(cases, [toString])
	}
	
	@Test
	def void test1simplePrinterTest()
	{
		println("== simplePrinterTest =========================================")
		val cases =
		#[
			new Num(1)									->	"1",
			new Var("x")								->	"x",
			new UnaryExpr(Op.UMINUS, new Num(1))		->	"(- 1)",
			new BinExpr
			(
				Op.TIMES, 
				new Var("x"), 
				new UnaryExpr(Op.UMINUS, new Num(1))
			)											->	"(x * (- 1))",
			new BinExpr
			(
				Op.PLUS, 
				new BinExpr
				(
					Op.TIMES, 
					new Var("x"), 
					new UnaryExpr(Op.UMINUS, new Num(1))
				), 
				new Num(2)
			) 											-> "((x * (- 1)) + 2)",
			new BinExpr
			(
				Op.MINUS, 
				new Var("x"), 
				new Num(1)
			)											-> "(x - 1)",
			new BinExpr
			(
				Op.DIV, 
				new BinExpr
				(
					Op.MINUS, 
					new Var("x"), 
					new Num(1)
				), 
				new Num(2)
			) 											-> "((x - 1) / 2)"
		]
		extension val aprinterExt = new AExprPrinterExtender(new AExprSimplePrinter) 
		doTestCaseList(cases, [text])
	}
	
	@Test
	def void test2altSimplePrinterTest()
	{
		println("== altSimplePrinterTest ======================================")
		val cases =
		#[
			new Num(1)									->	"1",
			new Var("x")								->	"x",
			new UnaryExpr(Op.UMINUS, new Num(1))		->	"-(1)",
			new BinExpr
			(
				Op.TIMES, 
				new Var("x"), 
				new UnaryExpr(Op.UMINUS, new Num(1))
			)											->	"(x) * (-(1))",
			new BinExpr
			(
				Op.PLUS, 
				new BinExpr
				(
					Op.TIMES, 
					new Var("x"), 
					new UnaryExpr(Op.UMINUS, new Num(1))
				), 
				new Num(2)
			) 											-> "((x) * (-(1))) + (2)",
			new BinExpr
			(
				Op.MINUS, 
				new Var("x"), 
				new Num(1)
			)											-> "(x) - (1)",
			new BinExpr
			(
				Op.DIV, 
				new BinExpr
				(
					Op.MINUS, 
					new Var("x"), 
					new Num(1)
				), 
				new Num(2)
			) 											-> "((x) - (1)) / (2)"
		]
		extension val aprinterExt = new AExprPrinterExtender(new AExprAltSimplePrinter) 
		doTestCaseList(cases, [text])
	}
	
	@Test
	def void test3prettyPrintingTest()
	{
		println("== prettyPrintingTest ========================================")
		val cases =
		#[
			new Num(1)									->	"1",
			new Var("x")								->	"x",
			new UnaryExpr(Op.UMINUS, new Num(1))		->	"-1",
			new BinExpr
			(
				Op.TIMES, 
				new Var("x"), 
				new UnaryExpr(Op.UMINUS, new Num(1))
			)											->	"x * -1",
			new BinExpr
			(
				Op.PLUS, 
				new BinExpr
				(
					Op.TIMES, 
					new Var("x"), 
					new UnaryExpr(Op.UMINUS, new Num(1))
				), 
				new Num(2)
			) 											-> "x * -1 + 2",
			new BinExpr
			(
				Op.MINUS, 
				new Var("x"), 
				new Num(1)
			)											-> "x - 1",
			new BinExpr
			(
				Op.DIV, 
				new BinExpr
				(
					Op.MINUS, 
					new Var("x"), 
					new Num(1)
				), 
				new Num(2)
			) 											-> "(x - 1) / 2"
		]
		extension val aprinterExt = new AExprPrinterExtender(new AExprPrettyPrinter) 
		doTestCaseList(cases, [text])
	}
	
}