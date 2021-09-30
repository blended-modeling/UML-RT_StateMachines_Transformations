/*****************************************************************************
 * Copyright (c) 2016 Zeligsoft (2009) Limited and others.
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

import org.eclipse.papyrusrt.xtumlrt.common.ValueSpecification
import org.eclipse.xtext.conversion.IValueConverter
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.nodemodel.INode
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.*
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory

/**
 * @author epp
 */
class ValueSpecificationConverterImpl implements IValueConverter<ValueSpecification> {
	
	override toString(ValueSpecification value) throws ValueConverterException {
		value.stringValue
	}
	
	override toValue(String string, INode node) throws ValueConverterException {
		if (string === null || string.empty) {
			throw new ValueConverterException(string, node, null)
		}
		try {
			val first = string.charAt(0)
			switch first {
				case 'n': tryNull(string, node)
				case 't', case 'f': tryBool(string, node)
				case '"': tryString(string, node)
				case '-', case '+',
				case '0', case '1', case '2', case '3', case '4', 
				case '5', case '6', case '7', case '8', case '9': tryNumber(string, node)
			}
		} catch (Exception e) {
			throw new ValueConverterException(string, node, e)
		}
	}
	
	private def tryNull(String string, INode node) {
		if (string == "null") {
			CommonFactory.eINSTANCE.createLiteralNull
		} else {
			throw new ValueConverterException(string, node, null)
		}
	}
	
	private def tryBool(String string, INode node) {
		val value_ = Boolean.parseBoolean(string)
		CommonFactory.eINSTANCE.createLiteralBoolean => [ value = value_ ]
	}
	
	private def tryString(String string, INode node) {
		val value_ = string
		CommonFactory.eINSTANCE.createLiteralString => [ value = value_ ]
	}
	
	private def tryInt(String string, INode node) {
		val value_ = Integer.parseInt(string)
		CommonFactory.eINSTANCE.createLiteralInteger => [ value = value_ ]
	}
	
	private def tryReal(String string, INode node) {
		val value_ = Double.parseDouble(string)
		CommonFactory.eINSTANCE.createLiteralReal => [ value = value_ ]
	}
	private def tryNumber(String string, INode node) {
		if (string.indexOf('.') == -1) {
			tryInt(string, node)
		} else {
			tryReal(string, node)
		}
	}
}