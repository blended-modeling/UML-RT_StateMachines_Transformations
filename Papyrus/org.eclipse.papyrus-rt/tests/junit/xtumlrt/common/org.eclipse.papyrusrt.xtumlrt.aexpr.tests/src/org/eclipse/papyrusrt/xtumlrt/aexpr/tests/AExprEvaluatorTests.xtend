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
import java.util.Map
import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.AExprEvaluator
import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes.SimpleScope
import static org.eclipse.papyrusrt.xtumlrt.aexpr.tests.TestUtils.*

class AExprEvaluatorTests
{
	@Test
	def void test0simpleScopeEvaluationTest()
	{
		println("== evaluationTest ============================================")
		
		val cases =
		#[
			#["1",				null]				->	1,
			#["x",				#{"x"->2}]			->	2,
			#["n+1",			#{"n"->3}]			->	4,
			#["a+2*b",			#{"a"->-1,"b"->-1}]	->	-3,
			#["(i - 1) % 3",	#{"i"->6}]			->	2
		]
		val evaluator = new AExprEvaluator
		doTestCaseList(cases, 
		[
			val text = get(0) as String
			val scope = new SimpleScope(get(1) as Map<String,Integer>)
			evaluator.eval(text, scope)
		])
	}
	
}