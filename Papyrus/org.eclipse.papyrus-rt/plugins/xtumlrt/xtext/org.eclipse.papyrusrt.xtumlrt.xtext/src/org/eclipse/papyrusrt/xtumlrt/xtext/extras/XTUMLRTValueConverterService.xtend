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

import org.eclipse.xtext.common.services.Ecore2XtextTerminalConverters
import org.eclipse.xtext.conversion.ValueConverter
import org.eclipse.xtext.conversion.IValueConverter
import org.eclipse.papyrusrt.xtumlrt.common.ValueSpecification

/**
 * @author Ernesto Posse
 */
class XTUMLRTValueConverterService extends Ecore2XtextTerminalConverters {
	
	@ValueConverter(rule="ActionCodeSource")
	def IValueConverter<String> getActionCodeConverter() {
		new ActionCodeConverterImpl
	}
	
	@ValueConverter(rule="ValueSpecification")
	def IValueConverter<ValueSpecification> getValueSpecificationConverter() {
		new ValueSpecificationConverterImpl
	}
}

