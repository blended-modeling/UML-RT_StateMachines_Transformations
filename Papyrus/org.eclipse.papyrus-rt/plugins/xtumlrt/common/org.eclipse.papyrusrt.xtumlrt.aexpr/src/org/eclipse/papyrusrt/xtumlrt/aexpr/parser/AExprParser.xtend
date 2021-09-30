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
package org.eclipse.papyrusrt.xtumlrt.aexpr.parser

import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.AExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Num
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Var
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.Op
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.UnaryExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.ast.BinExpr
import org.eclipse.papyrusrt.xtumlrt.aexpr.names.Name
import org.eclipse.papyrusrt.xtumlrt.aexpr.names.QualifiedName
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.Lexer
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.TokenSequence
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Delimiter
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Identifier
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.QualifiedIdentifier
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Number
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Operator
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Token

class AExprParser
{
	val lexer = new Lexer(#[], #[], "::", 4)
	var TokenSequence.TokensIterator iterator
	
	def AExpr parse(String text)
	{
		iterator = lexer.getTokenSequenceFor(text).iterator as TokenSequence.TokensIterator
		val result = parseExpr
		if (iterator.hasNext)
		{
			val token = iterator.next
			throw new UnexpectedTokenException(token)
		}
		result
	}
	
	private def AExpr parseFactor()
	{
		val token = iterator.lookAhead
		switch token
		{
			Number		:	acceptNumber(token)
			Identifier	:	acceptVar(token)
			Operator	:	parseUnaryExpr
			Delimiter	case token.kind === Delimiter.Kind.LPAR:	parseSubExpr
			default:	throw new UnexpectedTokenException(token)
		}
	}
	
	private def AExpr parseTerm()
	{
		var factor = parseFactor
		var term = factor
		if (iterator.hasNext)
		{
			var token = iterator.lookAhead
			while (iterator.hasNext && token.isMultiplicativeOperator)
			{
				iterator.next
				val operator = token.operator
				val other = parseFactor
				term = new BinExpr(operator, term, other)
				token = iterator.lookAhead
			}
		}
		term
	}
	
	private def AExpr parseExpr()
	{
		var term = parseTerm
		var expr = term
		if (iterator.hasNext)
		{
			var token = iterator.lookAhead
			while (iterator.hasNext && token.isAdditiveOperator)
			{
				iterator.next
				val operator = token.operator
				val other = parseTerm
				expr = new BinExpr(operator, expr, other)
				token = iterator.lookAhead
			}
		}
		expr
	}
	
	private def parseUnaryExpr()
	{
		if (iterator.hasNext)
		{
			var token = iterator.lookAhead
			if (token.isAdditiveOperator)
			{
				iterator.next
				val op = token.operator
				val factor = parseFactor
				new UnaryExpr(op, factor)
			}
			else
				throw new MissingExpectedTokenException(token, "+ or -")
		}
	}
	
	private def parseSubExpr()
	{
		iterator.next
		val subExpr = parseExpr
		expectRpar
		subExpr
	}
	
	private def acceptNumber(Token token)
	{
		iterator.next
		new Num((token as Number).value)
	}
	
	private def acceptVar(Token token)
	{
		iterator.next
		val name =
			if (token instanceof QualifiedIdentifier)
			{
				new QualifiedName(token.text, token.segments)
			}
			else
			{
				new Name(token.text)
			}
		new Var(name)
	}
	
	private def expectRpar()
	{
		val token = iterator.lookAhead
		if (token instanceof Delimiter && (token as Delimiter).kind === Delimiter.Kind.RPAR)
			iterator.next
		else
			throw new MissingExpectedTokenException(token, ")")
	}
	
	private def isMultiplicativeOperator(Token token)
	{
		if (token instanceof Operator)
		{
			token.kind === Operator.Kind.TIMES || token.kind === Operator.Kind.DIV || token.kind === Operator.Kind.MOD
		}
		else false
	}

	private def isAdditiveOperator(Token token)
	{
		if (token instanceof Operator)
		{
			token.kind === Operator.Kind.PLUS || token.kind === Operator.Kind.MINUS
		}
		else false
	}
	
	private def operator(Token token)
	{
		if (token instanceof Operator)
		{
			switch token.kind
			{
				case Operator.Kind.PLUS:	Op.PLUS
				case Operator.Kind.MINUS:	Op.MINUS
				case Operator.Kind.TIMES:	Op.TIMES
				case Operator.Kind.DIV:		Op.DIV
				case Operator.Kind.MOD:		Op.MOD
				default:
					throw new UnexpectedTokenException(token)
			}
			
		}
	}
}
