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

import java.util.function.Function

import static org.junit.Assert.*

class TestUtils
{
	enum ExceptionTestResult { NONE, CORRECT, INCORRECT }

	static def <I,O> doTestCaseList(Iterable<? extends Pair<? extends I,? extends O>> cases, Function<I,O> function)
	{
		var i = 0
		for (pair : cases)
		{
			val input = pair.key
			val expected = pair.value
			println("* test case " + i)
			println("input         = '" + input + "'")
			println("input type    = " + input.class.name)
			println("expected      = '" + expected + "'")
			println("expected type = " + expected.class.name)
			try
			{
				val actual = function.apply(input)
				println("actual        = '" + actual + "'")
				println("actual type   = " + actual.class.name)
				assertEquals(expected, actual)
				println("* test case " + i + " passed")
			}
			catch (AssertionError e)
			{
				println("* test case " + i + " failed")
				println("** some test cases in this group failed")
				throw e;
			}
			catch (Exception e)
			{
				println("* test case " + i + " failed with an unexpected exception: " + e)
				println("** some test cases in this group failed")
				throw e;
			}
			i++
		}
		if (i === cases.length)
		{
			println("** all test cases in this group passed")
		}
		else
		{
			println("** some test cases in this group failed")
		}
	}

	static def <I,O,E> doTestExceptionCaseList(Iterable<? extends Pair<? extends I, ? extends Pair<? extends E, String>>> cases, Function<I,O> function)
	{
		var i = 0
		for (pair : cases)
		{
			val input = pair.key
			val expectedExn = pair.value.key as Class<? extends Throwable>
			val expectedMsg = pair.value.value as String
			println("* test case " + i)
			println("input              = '" + input + "'")
			println("input type         = " + input.class.name)
			println("expected exception = " + expectedExn.name )
			println("expected message   = '" + expectedMsg + "'")
			var raised = ExceptionTestResult.NONE
			var Exception exception = null
			try
			{
				val actual = function.apply(input)
				println("actual             = '" + actual + "'")
				println("actual type        = " + actual.class.name)
			}
			catch (Exception e)
			{
				println("actual exception   = " + e.class.name)
				println("actual message     = '" + e.message + "'")
				if (expectedExn.isInstance(e))
				{
					assertEquals(e.message, expectedMsg)
					raised = ExceptionTestResult.CORRECT
				}
				else
				{
					raised = ExceptionTestResult.INCORRECT
					exception = e as Exception
				}
			}
			finally
			{
				switch raised
				{
					case NONE:
					{
						println("* test case " + i + " failed")
						fail("Expecting " + expectedExn.simpleName + " to be raised but no exception was thrown when evaluating '" + input + "'.")
					}
					case INCORRECT:
					{
						println("* test case " + i + " failed")
						fail("Expecting " + expectedExn.simpleName + " to be raised but exception " + exception.class.simpleName + " was thrown when evaluating '" + input + "'.")
					}
					case CORRECT:
					{
						println("* test case " + i + " passed")
					}
				}
			}
			i++
		}
		if (i === cases.length)
		{
			println("** all test cases in this group passed")
		}
		else
		{
			println("** some test cases in this group failed")
		}
	}
	
}