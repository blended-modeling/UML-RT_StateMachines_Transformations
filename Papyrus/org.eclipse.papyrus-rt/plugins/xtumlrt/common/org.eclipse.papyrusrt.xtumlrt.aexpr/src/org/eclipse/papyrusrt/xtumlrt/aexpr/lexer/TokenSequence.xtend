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
package org.eclipse.papyrusrt.xtumlrt.aexpr.lexer

import java.util.Iterator
import java.util.NoSuchElementException
import java.util.Set
import java.util.function.Predicate
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Delimiter
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.EndOfText
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Identifier
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.QualifiedIdentifier
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Keyword
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Number
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Operator
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Token
import org.eclipse.papyrusrt.xtumlrt.aexpr.lexer.tokens.Unknown
import org.eclipse.xtend.lib.annotations.Accessors

class TokenSequence implements Iterable<Token>
{
	static val DEFAULT_QUAL_ID_SEP = "."
	static val DEFAULT_TAB_SIZE = 4
	String text
	Set<String> keywords
	Set<String> operators
	String qualifiedIdSeparator
	int tabSize
	
	new (String text)
	{
		this.text = text
		this.keywords = newHashSet
		this.operators = newHashSet
		this.qualifiedIdSeparator = DEFAULT_QUAL_ID_SEP
		this.tabSize = DEFAULT_TAB_SIZE
	}
	
	new (String text, Set<String> keywords, Set<String> operators, String qualIdSeparator, int tabSize)
	{
		this.text = text
		this.keywords = keywords
		this.operators = operators
		this.qualifiedIdSeparator = qualIdSeparator
		this.tabSize = tabSize
	}
	
	override iterator()
	{
		val it = new TokensIteratorImpl
		setSource(this.text)
		setKeywords(this.keywords)
		setOperators(this.operators)
		setQualIdSeparator(this.qualifiedIdSeparator)
		setTabSize(this.tabSize)
		it
	}
	
	static interface TokensIterator extends Iterator<Token>
	{
		def Token lookAhead()
		def Token consume()
	}
	
	static class TokensIteratorImpl implements TokensIterator
	{
		static val char TAB = '\t'
		static val char NEWLINE = '\n'
		@Accessors String source
		char[] chars
		int currentPosition
		int advancedPosition
		int currentLine
		int currentColumn
		int advancedLine
		int advancedColumn
		int tabSize
		char observedChar
		Token tokenAhead
		Set<String> recognizedKeywords
		Set<String> recognizedOperators
		Set<Character> operatorsFirstChar
		Set<Character> validOperatorChars
		String qualIdSeparator
		
		new()
		{
			setSource("")
			recognizedKeywords = newHashSet
			recognizedOperators = newHashSet(Operator.BUILT_IN_STR)
			operatorsFirstChar = newHashSet(Operator.BUILT_IN_CHAR)
			validOperatorChars = getValidOperatorChars(recognizedOperators)
			qualIdSeparator = DEFAULT_QUAL_ID_SEP
			tabSize	= DEFAULT_TAB_SIZE
		}
		
		def setKeywords(Iterable<String> kwds)
		{
			recognizedKeywords += kwds
		}
		
		def setOperators(Iterable<String> operators)
		{
			val ops = operators.filter [validOperatorSymbol]
			recognizedOperators += ops
			operatorsFirstChar.addAll(ops.map [charAt(0)])
			validOperatorChars.addAll(ops.getValidOperatorChars)
		}
		
		def setQualIdSeparator(String sep)
		{
			qualIdSeparator = sep
		}
		
		def setSource(String text)
		{
			source = text
			initialize
		}
		
		def setTabSize(int n)
		{
			tabSize = n
		}
		
		private def getValidOperatorChars(Iterable<String> ops)
		{
			val result = newHashSet
			for (op : ops)
			{
				result.addAll(op.toCharArray)
			}
			result
		}
		
		private def initialize()
		{
			chars = source.toCharArray
			currentPosition = 0
			advancedPosition = 0
			currentLine = 0
			currentColumn = 0
			observeChar
		}
		
		override hasNext()
		{
			currentPosition < chars.length
		}
		
		override next()
		{
			if (hasNext)
			{
				lookAhead
				consume
			}
			else
				throw new NoSuchElementException("Reached the end of text.")
		}
		
		override consume()
		{
			val nextToken = tokenAhead
			moveCurrentPositionForward
			tokenAhead = null
			nextToken
		}
		
		override lookAhead()
		{
			if (tokenAhead === null)
			{
				resetAdvancedPosition
				skipSpaces
				moveCurrentPositionForward
				observeChar
				tokenAhead =
					switch observedChar
					{
						case isDigit(observedChar):				nextNumber
						case isIdentifierStart(observedChar):	nextIdentifierOrKeyword
						case isOperatorStart(observedChar):		nextOperator
						case isBracket(observedChar):			nextBracket
						case isEOT(observedChar):				eot
						default:								unknown
					}
			}
			tokenAhead
		}
		
		private def observeChar()
		{
			if (advancedPosition < chars.length)
				observedChar = chars.get(advancedPosition)
			else
				observedChar = EndOfText.EOT
		}
		
		private def resetAdvancedPosition()
		{
			advancedPosition = currentPosition
			advancedLine     = currentLine
			advancedColumn   = currentColumn
		}
		
		private def moveCurrentPositionForward()
		{
			currentPosition = advancedPosition
			currentLine     = advancedLine
			currentColumn   = advancedColumn
		}
		
		private def nextNumber()
		{
			new Number(currentLine, currentColumn, nextMultiCharToken[isDigit])
		}
		
		private def nextIdentifierOrKeyword()
		{
			var tokenStr = nextMultiCharToken[isIdentifierPart]
			var qualified = false
			while (qualIdSeparator.follows && moreQualIdSegmentsAhead)
			{
				qualified = true
				tokenStr += nextMultiCharToken[isQualIdSeparatorPart]
				tokenStr += nextMultiCharToken[isIdentifierPart]
			}
			if (qualified)
			{
				new QualifiedIdentifier(currentLine, currentColumn, tokenStr) => [ separator = qualIdSeparator ]
			}
			else if (tokenStr.isKeyword)
			{
				new Keyword(currentLine, currentColumn, tokenStr)
			}
			else
			{
				new Identifier(currentLine, currentColumn, tokenStr)
			}
		}
	
		private def nextOperator()
		{
			var tokenStr = nextMultiCharToken[isOperatorPart]
			if (tokenStr.isMultiCharOperator)
			{
				new Operator(currentLine, currentColumn, tokenStr)
			}
			else
			{
				new Unknown(currentLine, currentColumn, tokenStr)
				
			}
		}
	
		private def nextBracket()
		{
			new Delimiter(currentLine, currentColumn, nextSingleCharToken)
		}
	
		private def eot()
		{
			new EndOfText(currentLine, currentColumn)
		}
		
		private def unknown()
		{
			new Unknown(currentLine, currentColumn, nextMultiCharToken [isUnknown])
		}
	
		private def skipSpaces()
		{
			while (advancedPosition < chars.length && isSpace(observedChar)) { advance }
		}
		
		private def nextSingleCharToken()
		{
			val str = "" + observedChar
			advance
			str
		}
		
		private def nextMultiCharToken(Predicate<Character> condition)
		{
			var str = ""
			while (advancedPosition < chars.length && condition.test(observedChar))
			{
				str += observedChar
				advance
			}
			str
		}
	
		private def advance()
		{
			if (advancedPosition < chars.length)
			{
				switch observedChar
				{
					case NEWLINE:
					{
						advancedLine++
						advancedColumn = 0
					}
					case TAB:
					{
						advancedColumn += tabSize - advancedColumn % tabSize
					}
					default:
						advancedColumn++
				}
				advancedPosition++
				observeChar
			}
			else
			{
				observedChar = EndOfText.EOT
			}
		}
		
		private def follows(String expected)
		{
			var result = true
			var i = advancedPosition
			var j = 0
			if (expected === null || expected.empty || i + expected.length > chars.length)
			{
				return false
			}
			while (j < expected.length && result)
			{
				result = result && chars.get(i) === expected.charAt(j)
				i++
				j++
			}
			return result
		}
		
		private def moreQualIdSegmentsAhead()
		{
			advancedPosition + qualIdSeparator.length < chars.length
			&& isIdentifierStart(chars.get(advancedPosition + qualIdSeparator.length))
		}
		
		private def isDigit(char c) { Character.isDigit(c) }
		
		private def isIdentifierStart(char c) { Character.isJavaIdentifierStart(c) }
		
		private def isIdentifierPart(char c) { Character.isJavaIdentifierPart(c) }
		
		private def isOperatorStart(char c) { operatorsFirstChar.contains(c) }
		
		private def isOperatorPart(char c) { validOperatorChars.contains(c) }
		
		private def isMultiCharOperator(String s) { recognizedOperators.contains(s) }
		
		private def isBracket(char c) { Delimiter.isBracket(c) }
		
		private def isSpace(char c) { Character.isWhitespace(c) }
		
		private def isEOT(char c) { EndOfText.isEOT(c) }
		
		private def isUnknown(char c) { !isDigit(c) && !isIdentifierStart(c) && !isOperatorStart(c) && !isBracket(c) && !isSpace(c) && !isEOT(c) }
		
		private def isKeyword(String s) { recognizedKeywords.contains(s) }
		
		private def isQualIdSeparatorPart(char c) { qualIdSeparator.indexOf(c) !== -1 }
		
		private def validOperatorSymbol(String s)
		{
			if (s === null || s.empty) return false
			for (char c : s.toCharArray)
			{
				if (isDigit(c) 
					|| isIdentifierStart(c) 
					|| isIdentifierPart(c) 
					|| isQualIdSeparatorPart(c) 
					|| isBracket(c) 
					|| isSpace(c) 
					|| isEOT(c))
				{
					return false
				}
			}
			return true
		} 

	}
	
}
