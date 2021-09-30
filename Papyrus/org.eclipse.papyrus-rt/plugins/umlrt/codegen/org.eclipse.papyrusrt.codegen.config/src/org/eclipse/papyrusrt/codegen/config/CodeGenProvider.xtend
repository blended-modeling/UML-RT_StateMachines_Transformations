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
package org.eclipse.papyrusrt.codegen.config

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Module
import java.util.function.Supplier
import org.eclipse.papyrusrt.codegen.UMLRTCodeGenerator
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * @author epp
 */
class CodeGenProvider implements Supplier<UMLRTCodeGenerator>
{
	/** The shared instance. */
	static val INSTANCE = new CodeGenProvider
	
	val DEFAULT_MODULE = new CodeGenDefaultInjectionModule
	
	/** The code generator. */
	UMLRTCodeGenerator generator
	
	/** The injection configuration {@link Module}. */
	@Accessors AbstractModule module = DEFAULT_MODULE
	
	/** Injector. */
	Injector injector

	static def getDefault() { INSTANCE }

	/**
	 * Returns the shared code generator instance.
	 * 
	 * @return The shared code generator instance.
	 */
	override get() {
		if (generator === null) {
			generator = getInjector.getInstance(UMLRTCodeGenerator)
		}
		return generator;
	}

	def getInjector() {
		if (injector === null) {
			injector = Guice.createInjector(getModule())
		}
		injector
	}
	
}