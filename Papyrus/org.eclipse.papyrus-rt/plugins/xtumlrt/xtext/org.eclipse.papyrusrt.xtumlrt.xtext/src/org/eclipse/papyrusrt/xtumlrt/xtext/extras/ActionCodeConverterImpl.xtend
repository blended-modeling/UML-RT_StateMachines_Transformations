/*****************************************************************************
 * Copyright (c) 2016 Zeligsoft (2009) Ltd. and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ernesto Posse - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.xtext.extras

import org.eclipse.xtext.conversion.IValueConverter
import org.eclipse.papyrusrt.xtumlrt.common.ActionCode
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory

/**
 * @author Ernesto Posse
 *
 */
class ActionCodeConverterImpl implements IValueConverter<String> {
	
	static val CODE_START = "{|#"
	static val CODE_END   = "#|}"
	static val NEW_LINE   = "\n"
	static val DEFAULT_PREFIX = prefix("\t", 5)
	
	static def prefix( String s, int level ) {
		val prefix = new StringBuilder
		for (var i = 0; i < level; i++) { prefix.append(s) }
		prefix.toString
	}
	
	static def removeTrailingSpaces( String s ) {
		var i = s.length
		while (i > 0 && Character.isWhitespace(s.charAt(i - 1))) {
			i--;
		}
		s.substring(0, i)
	}
	
	static def firstNonSpace( String s ) {
		var i = 0
		while (i < s.length && Character.isWhitespace(s.charAt(i))) {
			i++
		}
		i
	}
	
	static def longestLeadingSpace( String text ) {
		var i = 0
		var longest_length = 0
		val lines = text.split("\n")
		while (i < lines.length) {
			val line = lines.get(i)
			val lineLeadingSpace = firstNonSpace(line)
			if (lineLeadingSpace > 0 && lineLeadingSpace > longest_length) {
				longest_length = lineLeadingSpace
			}
			i++
		}
		longest_length 
	}
	
	static def removeLeadingSpaces( String text ) {
		val n = longestLeadingSpace( text )
		val lines = text.split("\n")
		val newText = lines.map[stringFrom(n)].join("\n")
		newText
	}
	
	static def stringFrom( String s, int n ) {
		if (s === null) {
			""
		} else if (s.length < n) {
			s
		} else {
			s.substring(n)
		}
	}
	
	override toString(String value) throws ValueConverterException {
		val rawString = (value ?: "").removeTrailingSpaces
		if (rawString.contains(NEW_LINE)) {
			val lines = rawString.split(NEW_LINE).toList
			val prefix = DEFAULT_PREFIX
			lines.join(CODE_START + NEW_LINE, NEW_LINE, NEW_LINE + DEFAULT_PREFIX + CODE_END, [prefix + it])
		} else {
			CODE_START + " " + rawString.trim + " "+ CODE_END
		}
	}
	
	override toValue(String string, INode node) throws ValueConverterException {
		if (string !== null) {
			var startIndex = 0
			var endIndex = string.length
			if (string.startsWith(CODE_START)) {
				startIndex = CODE_START.length
			} 
			if (string.endsWith(CODE_END)) {
				endIndex = string.length - CODE_END.length
			}
			removeLeadingSpaces(string.substring(startIndex, endIndex))
		} else {
			""
		}
	}
	
}