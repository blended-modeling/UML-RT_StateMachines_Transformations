/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.util

import java.util.List
import org.eclipse.emf.ecore.ENamedElement
import java.util.function.Function

/**
 * @author eposse
 */
class GeneralUtil
{

	static def <T> void addIfNotNull(List<T> list, T obj)
	{
		if (list !== null && obj !== null)
		{
			list.add(obj)
		}
	}

	static def <T> void addIfNotPresent(List<T> list, T obj)
	{
		if (list !== null && obj !== null && !list.contains(obj))
		{
			list.add(obj)
		}
	}

	static def <T> addAllNonPresent(List<T> targetList,
		List<? extends T> sourceList)
	{
		sourceList.forEach[targetList.addIfNotPresent(it)]
	}

	static def <P, R, T extends R> transformList(List<? extends P> sourceList,
		Function<? super P, ? extends R> transform, Class<T> type)
	{
		sourceList.map[transform].filter(type).toList
	}

	static dispatch def String getName(ENamedElement eobj)
	{
		eobj.name
	}

	static dispatch def String getName(org.eclipse.uml2.uml.NamedElement eobj)
	{
		eobj.name
	}

	static dispatch def String getName(
		org.eclipse.papyrusrt.xtumlrt.common.NamedElement eobj)
	{
		eobj.name
	}

	static def <T> Pair<Iterable<T>, Iterable<T>> difference(Iterable<T> l1, Iterable<T> l2)
	{
		val left = newArrayList
		val right = newArrayList
		var i = 0
		while (i < l1.size && i < l2.size && l1.get(i) == l2.get(i))
		{
			i++
		}
		while (i < l1.size && i < l2.size)
		{
			if (i < l1.size)
			{
				left.add(l1.get(i))
			}
			if (i < l2.size)
			{
				right.add(l2.get(i))
			}
			i++
		}
		new Pair(left, right)
	}
	
	static def <T> Iterable<T> remainder(Iterable<T> l1, Iterable<T> l2)
	{
		val diff = difference(l1, l2)
		if (l1.size < l2.size) {
			diff.value
		} else {
			diff.key
		}
	}

	static def <T> Iterable<T> longestCommonPrefix(Iterable<T> l1, Iterable<T> l2)
	{
		val prefix = newArrayList
		var i = 0
		while (i < l1.size && i < l2.size && l1.get(i) == l2.get(i))
		{
			prefix.add(l1.get(i))
			i++
		}
		prefix
	}

	static def <T> Iterable<T> longestCommonPrefix(Iterable<? extends Iterable<T>> listOfLists)
	{
		if (listOfLists === null || listOfLists.empty) return null
		val prefix = newArrayList
		var listsMatch = true
		var itemIndex = 0
		while (listsMatch)
		{
			val firstList = listOfLists.get(0)
			var listIndex = 1
			while (listIndex < listOfLists.size 
				&& itemIndex < firstList.size 
				&& itemIndex < listOfLists.get(listIndex).size 
				&& firstList.get(itemIndex) == listOfLists.get(listIndex).get(itemIndex))
			{
				listIndex++
			}
			if (listIndex === listOfLists.size) {
				prefix.add(firstList.get(itemIndex))
			} else {
				listsMatch = false
			}
			itemIndex++
		}
		prefix
	}

	static def longestCommonPrefix(String s1, String s2)
	{
		val prefix = new StringBuilder
		var i = 0
		while (s1.charAt(i) == s2.charAt(i))
		{
			prefix.append(s1.charAt(i))
			i++
		}
		prefix.toString
	}

}
