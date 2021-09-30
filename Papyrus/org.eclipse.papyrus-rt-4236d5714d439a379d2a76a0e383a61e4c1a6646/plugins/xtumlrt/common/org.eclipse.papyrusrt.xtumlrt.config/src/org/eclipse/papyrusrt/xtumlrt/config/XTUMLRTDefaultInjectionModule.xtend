/*****************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Ltd. and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ernesto Posse - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.config

import com.google.inject.AbstractModule
import org.eclipse.papyrusrt.xtumlrt.trans.from.uml.UML2xtumlrtTranslator
import org.eclipse.papyrusrt.xtumlrt.trans.from.uml.UML2xtumlrtModelTranslator

class XTUMLRTDefaultInjectionModule extends AbstractModule
{
	
	override protected configure()
	{
		bind(UML2xtumlrtTranslator).to(UML2xtumlrtModelTranslator)
	}
	
}