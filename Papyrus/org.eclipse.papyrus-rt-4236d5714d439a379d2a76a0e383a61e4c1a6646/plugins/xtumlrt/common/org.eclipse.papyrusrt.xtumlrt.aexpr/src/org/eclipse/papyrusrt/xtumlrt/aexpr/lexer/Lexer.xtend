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

import java.util.Set

class Lexer
{
	static val DEFAULT_QUAL_ID_SEP = "."
	static val DEFAULT_TAB_SIZE = 4
	Set<String> keywords
	Set<String> operators
	String qualifiedIdSeparator = DEFAULT_QUAL_ID_SEP
	int tabSize = DEFAULT_TAB_SIZE
	
	new() 
	{
		this.keywords = newHashSet
		this.operators = newHashSet
		this.qualifiedIdSeparator = DEFAULT_QUAL_ID_SEP
		this.tabSize = DEFAULT_TAB_SIZE
	}
	
	new (Iterable<String> keywords, Iterable<String> operators, String qualIdSeparator, int tabSize)
	{
		this.keywords = newHashSet(keywords)
		this.operators = newHashSet(operators)
		this.qualifiedIdSeparator = qualIdSeparator
		this.tabSize = tabSize
	}

	def getTokenSequenceFor(String text)
	{
		new TokenSequence(text, keywords, operators, qualifiedIdSeparator, tabSize)
	}
	
	def tokenize(String text)
	{
		getTokenSequenceFor(text).toList
	}
}
