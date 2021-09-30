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

import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.Lexer
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Delimiter
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.EndOfText
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Identifier
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Keyword
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Number
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Operator
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Token
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Unknown
import org.junit.Test

import static org.eclipse.papyrusrt.xtumlrt.aexpr.tests.TestUtils.*
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.QualifiedIdentifier

class LexerTests
{
	@Test
	def void lexerTest1()
	{
		println("== LexerTest =================================================")
		val cases =
		#[
			"1"					->	#[ new Number(0, 0, "1") ],
			"6174"				->	#[ new Number(0, 0, "6174") ],
			"x"					->	#[ new Identifier(0, 0, "x") ],
			"Buya8"				->	#[ new Identifier(0, 0, "Buya8") ],
			"jump"				->	#[ new Keyword(0, 0, "jump") ],
			"jumpy"				->	#[ new Identifier(0, 0, "jumpy") ],
			"buya"				->	#[ new Keyword(0, 0, "buya") ],
			"buya8"				->	#[ new Identifier(0, 0, "buya8") ],
			"a::b"				->	#[ new QualifiedIdentifier(0, 0, "a::b") => [ separator = "::" ] ],
			"buya::b"			->	#[ new QualifiedIdentifier(0, 0, "buya::b") ],
			"a1::b_2::cc"		->	#[ new QualifiedIdentifier(0, 0, "a1::b_2::cc") => [ separator = "::" ] ],
			"("					->	#[ new Delimiter(0, 0, "(") ],
			")"					->	#[ new Delimiter(0, 0, ")") ],
			"+"					->	#[ new Operator(0, 0, "+") ],
			"-"					->	#[ new Operator(0, 0, "-") ],
			"*"					->	#[ new Operator(0, 0, "*") ],
			"/"					->	#[ new Operator(0, 0, "/") ],
			"%"					->	#[ new Operator(0, 0, "%") ],
			" 6174"				->	#[ new Number(0, 1, "6174") ],
			"	6174"			->	#[ new Number(0, 4, "6174") ],
			"\t6174"			->	#[ new Number(0, 4, "6174") ],
			"\n6174"			->	#[ new Number(1, 0, "6174") ],
			"  6174"			->	#[ new Number(0, 2, "6174") ],
			" 	6174"			->	#[ new Number(0, 4, "6174") ],
			"	 6174"			->	#[ new Number(0, 5, "6174") ],
			" \n6174"			->	#[ new Number(1, 0, "6174") ],
			"\n  6174"			->	#[ new Number(1, 2, "6174") ],
			"&"					->	#[ new Unknown(0, 0, "&") ],
			"&&&"				->	#[ new Operator(0, 0, "&&&") ],
			"!!3"				->	#[ new Operator(0, 0, "!!"), new Number(0, 2, "3") ],
			"1@a"				->	#[ new Number(0, 0, "1"), new Operator(0, 1, "@"), new Identifier(0, 2, "a") ],
			""					->	#[],
			"	xy3* -1 \n  +25\n/  \t6174"	->
									#[
										new Identifier(0, 4, "xy3"), 
										new Operator(0, 7, "*"),
										new Operator(0, 9, "-"),
										new Number(0, 10, "1"),
										new Operator(1, 2, "+"),
										new Number(1, 3, "25"),
										new Operator(2, 0, "/"),
										new Number(2, 4, "6174")
									]
		]
		val keywords = #[ "jump", "buya" ]
		val operators = #[ "@", "!!", "&&&" ]
		val sep = "::"
		val tab = 4
		extension val lexer = new Lexer(keywords, operators, sep, tab)
		doTestCaseList(cases, [tokenize])
	}
}