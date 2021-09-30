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
package org.eclipse.papyrusrt.xtumlrt.aexpr.uml

import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.AExprEvaluatorException
import org.eclipse.papyrusrt.xtumlrt.aexpr.parser.AExprParsingException
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement
import org.eclipse.papyrusrt.xtumlrt.common.MultiplicityElement
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.common.LiteralInteger
import org.eclipse.papyrusrt.xtumlrt.common.LiteralNatural
import org.eclipse.papyrusrt.xtumlrt.common.OpaqueExpression
import org.eclipse.papyrusrt.xtumlrt.util.QualifiedNames
import org.eclipse.papyrusrt.xtumlrt.util.UndefinedValueException
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTLogger
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.firstNamespaceContainer
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.papyrusrt.xtumlrt.trans.from.uml.UML2xtumlrtTranslator

class XTUMLRTBoundsEvaluator
{
	static val evaluator = new XTUMLRTAExprEvaluator
	
	@Accessors static UML2xtumlrtTranslator translator
	
	static def setTranslator(UML2xtumlrtTranslator trans)
	{
		translator = trans
		evaluator.setTranslator(translator)
	}

	/**
	 * Evaluates a given bound (multiplicity) specification given as a string. This specification can be
	 * an arbitrary arithmetic expression involving constants which are given by either unqualified or 
	 * qualified identifiers.
	 * 
	 * <p>The {@link NamedElement} provided is used to define the scope in which identifiers are resolved.
	 * 
	 * <p>Unqualified identifiers are resolved according to UML Namespace semantics, looking up the chain of
	 * nesting namespaces.
	 * 
	 * <p>Qualified names are of the form <em>{@code Model}</em>{@code ::}<em>{@code package_1}</em>{@code ::}...{@code ::}<em>{@code package_n}</em>{@code ::}<em>{@code classifier_1}</em>{@code ::}...{@code ::}<em>{@code classifier_n}</em>{@code ::}<em>{@code attribute}</em>
	 * where <em>{@code Model}</em> is resolved by looking at the current resouce set, and the first so-named model found is used.
	 * 
	 * @param element - A {@link NamedElement} the element to which the bound is applied, e.g. an {@link Attribute}, {@link Port} or {@link Part}.
	 * @param boundSpec - A {@link String}; the specification to evaluate.
	 * @param which - A {@link String} 
	 */
	static dispatch def evaluateBound(CommonElement element,
		LiteralInteger valSpec, boolean upper)
	{
		valSpec.value
	}

	static dispatch def evaluateBound(CommonElement element,
		LiteralNatural valSpec, boolean upper)
	{
		valSpec.value
	}

	static dispatch def evaluateBound(CommonElement element,
		OpaqueExpression valSpec, boolean upper)
	{
		evaluateBoundString(element, valSpec.body, upper)
	}

	static def evaluateBoundString(CommonElement element, String boundSpec,
		boolean upper)
	{
		val who = element.firstNamespaceContainer
		var msg = (if (upper) "Upper" else "Lower") + " bound specified for " +
			QualifiedNames.fullName(who) + " could not be determined"
		try
		{
			val umlElement = translator.getSource(who) as org.eclipse.uml2.uml.NamedElement
			val bound = evaluator.eval(boundSpec, new UMLScope(umlElement))
			if (bound < 0)
				throw new UndefinedValueException(msg)
			return bound
		}
		catch (ClassCastException e)
		{
			msg += " because it was not possible to determine the UML element for '" +
				who.name + "'."
			msg += "The error was: '" + e.message + "'."
			XTUMLRTLogger.error(msg)
			throw new UndefinedValueException(msg)
		}
		catch (NumberFormatException e)
		{
			msg +=
				" due to a number format exception in the expression '" +
					boundSpec + "'. "
			msg += "The error was: '" + e.message + "'."
			XTUMLRTLogger.error(msg)
			throw new UndefinedValueException(msg)
		}
		catch (AExprEvaluatorException e)
		{
			msg +=
				" due to an evaluation error in the expression '" + boundSpec +
					"'. "
			msg += "The error was: '" + e.message + "'."
			XTUMLRTLogger.error(msg)
			throw new UndefinedValueException(msg)
		}
		catch (AExprParsingException e)
		{
			msg +=
				" due to a parsing error in the expression '" +
					boundSpec + "'. "
			msg += "The error was: '" + e.message + "'."
			XTUMLRTLogger.error(msg)
			throw new UndefinedValueException(msg)
		}
	}

	/**
	 * Find and return the given element's bound.
	 * The bound is the greater of the upper and lower bound values.
	 */
	static def int getBound(MultiplicityElement element)
	throws UndefinedValueException
	{
		val lowerBoundSpec = element.lowerBound
		val upperBoundSpec = element.upperBound
		var int lowerBound = -1
		var int upperBound = -1
		var int bound = -1
		var evaluatedLowerBound = true
		try
		{
			lowerBound = evaluateBound(element, lowerBoundSpec, false)
		}
		catch (UndefinedValueException e)
		{
			evaluatedLowerBound = false
		}
		upperBound = evaluateBound(element, upperBoundSpec, true)
		bound = upperBound
		if (evaluatedLowerBound && lowerBound >
			upperBound)
			bound = lowerBound
		return bound
	}

	static def <T extends MultiplicityElement & NamedElement> String getBoundString(
		T element)
	{
		val lowerBoundSpec = element.lowerBound
		val upperBoundSpec = element.upperBound
		var int lowerBound = -1
		var int upperBound = -1
		var int bound
		var evaluatedLowerBound = true
		try
		{
			lowerBound = evaluateBound(element, lowerBoundSpec, false)
		}
		catch (UndefinedValueException e)
		{
			evaluatedLowerBound = false
		}
		try
		{
			upperBound = evaluateBound(element, upperBoundSpec, true)
			bound = upperBound
			if (evaluatedLowerBound && lowerBound > upperBound)
				bound = lowerBound
			return bound.toString
		}
		catch (UndefinedValueException e)
		{
			return switch upperBoundSpec
			{
				LiteralInteger:
					upperBoundSpec.value.toString
				LiteralNatural:
					upperBoundSpec.value.toString
				OpaqueExpression:
					upperBoundSpec.
						body
				default:
					"?"
			}
		}
	}

	/**
	 * Find and return the given element's lower bound.  Throws an exception is the
	 * element has an invalid lower bound.
	 */
	static def <T extends MultiplicityElement & NamedElement> int getLowerBound(
		T element)
	{
		val boundSpec = element.lowerBound
		evaluateBound(element, boundSpec,
			false)
	}

	/**
	 * Find and return the given element's upper bound.  Throws an exception is the
	 * element has an invalid upper bound.
	 */
	static def <T extends MultiplicityElement & NamedElement> int getUpperBound(
		T element)
	{
		val boundSpec = element.upperBound
		evaluateBound(element, boundSpec, true)
	}
}
