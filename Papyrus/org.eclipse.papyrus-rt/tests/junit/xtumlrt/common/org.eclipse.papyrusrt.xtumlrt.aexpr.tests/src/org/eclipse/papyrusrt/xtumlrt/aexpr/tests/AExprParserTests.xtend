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
import org.eclipse.papyrusrt.xtumlrt.aexpr.parser.AExprParser
import org.eclipse.papyrusrt.xtumlrt.aexpr.parser.MissingExpectedTokenException
import org.eclipse.papyrusrt.xtumlrt.aexpr.parser.UnexpectedTokenException
import static org.eclipse.papyrusrt.xtumlrt.aexpr.tests.TestUtils.*

class AExprParserTests
{
	@Test
	def void test0parseTest()
	{
		println("== parseTest =================================================")

		val cases =
		#[
			"6"					->	new Num(6),
			"54"				->	new Num(54),
			"x"					->	new Var("x"),
			"Buya7"				->	new Var("Buya7"),
			"(6174)"			->	new Num(6174),
			"(z3)"				->	new Var("z3"),
			"((8))"				->	new Num(8),
			"-4"				->	new UnaryExpr(Op.MINUS, new Num(4)),
			"-(4)"				->	new UnaryExpr(Op.MINUS, new Num(4)),
			"(-4)"				->	new UnaryExpr(Op.MINUS, new Num(4)),
			"-1*2"				->	new BinExpr(Op.TIMES, new UnaryExpr(Op.MINUS, new Num(1)), new Num(2)),
			"(-1)*2"			->	new BinExpr(Op.TIMES, new UnaryExpr(Op.MINUS, new Num(1)), new Num(2)),
			"-(1*2)"			->	new UnaryExpr(Op.MINUS, new BinExpr(Op.TIMES, new Num(1), new Num(2))),
			"2* -1"				->	new BinExpr(Op.TIMES, new Num(2), new UnaryExpr(Op.MINUS, new Num(1))),
			"2*(-1)"			->	new BinExpr(Op.TIMES, new Num(2), new UnaryExpr(Op.MINUS, new Num(1))),
			"(2* -1)"			->	new BinExpr(Op.TIMES, new Num(2), new UnaryExpr(Op.MINUS, new Num(1))),
			"6 * x3"			->	new BinExpr(Op.TIMES, new Num(6), new Var("x3")),
			"(6*x4)"			->	new BinExpr(Op.TIMES, new Num(6), new Var("x4")),
			"6 + x3"			->	new BinExpr(Op.PLUS, new Num(6), new Var("x3")),
			"(6+x4)"			->	new BinExpr(Op.PLUS, new Num(6), new Var("x4")),
			"8+yu* 7"			->	new BinExpr(Op.PLUS, new Num(8), new BinExpr(Op.TIMES, new Var("yu"), new Num(7))),
			"(8+yu)*7"			->	new BinExpr(Op.TIMES, new BinExpr(Op.PLUS, new Num(8), new Var("yu")), new Num(7)),
			"3*qwe-g2/3"		->	new BinExpr
									(
										Op.MINUS,
										new BinExpr
										(
											Op.TIMES,
											new Num(3),
											new Var("qwe")
										),
										new BinExpr
										(
											Op.DIV,
											new Var("g2"),
											new Num(3)
										)
									),
			"(3+qwe)*(g2-3)"	->	new BinExpr
									(
										Op.TIMES,
										new BinExpr
										(
											Op.PLUS,
											new Num(3),
											new Var("qwe")
										),
										new BinExpr
										(
											Op.MINUS,
											new Var("g2"),
											new Num(3)
										)
									),
			"K*M/2"				->	new BinExpr
									(
										Op.DIV,
										new BinExpr
										(
											Op.TIMES,
											new Var("K"),
											new Var("M")
										),
										new Num(2)
									),
			"a*b/2+c*d/3-e/f*4"	->	new BinExpr
									(
										Op.MINUS,
										new BinExpr
										(
											Op.PLUS,
											new BinExpr
											(
												Op.DIV,
												new BinExpr
												(
													Op.TIMES,
													new Var("a"),
													new Var("b")
												),
												new Num(2)
											),
											new BinExpr
											(
												Op.DIV,
												new BinExpr
												(
													Op.TIMES,
													new Var("c"),
													new Var("d")
												),
												new Num(3)
											)
										),
										new BinExpr
										(
											Op.TIMES,
											new BinExpr
											(
												Op.DIV,
												new Var("e"),
												new Var("f")
											),
											new Num(4)
										)
									)
		]
		
		extension val parser = new AExprParser
		doTestCaseList(cases, [parse])
	}
	
	@Test
	def void test1parseFailureTest()
	{
		println("== parseFailureTest ==========================================")

		val cases =
		#[
			"1x"	-> {UnexpectedTokenException -> "Unexpected 'x' at line 0, column 1"},
			"(1x"	-> {MissingExpectedTokenException -> "Expected ')' but found 'x' at line 0, column 2"},
			"1*"	-> {UnexpectedTokenException -> "Unexpected end of text at line 0, column 2"},
			"-2+"	-> {UnexpectedTokenException -> "Unexpected end of text at line 0, column 3"},
			"2*-1"	-> {UnexpectedTokenException -> "Unexpected '*-' at line 0, column 1"}
		]
		
		extension val parser = new AExprParser
		doTestExceptionCaseList(cases, [parse])
	}
}