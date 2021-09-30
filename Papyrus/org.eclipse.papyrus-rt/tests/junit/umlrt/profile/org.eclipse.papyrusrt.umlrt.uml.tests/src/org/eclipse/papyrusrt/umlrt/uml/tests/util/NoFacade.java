/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml.tests.util;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotates a test method or class that must not cause any objects of the
 * façade API to be created.
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface NoFacade {
	/**
	 * Whether the test must not trigger the façade API. Annotating with
	 * a {@code false} value is useful to override the suite-level annotation
	 * for a specific test.
	 * 
	 * @return whether the test must not trigger the façade API
	 */
	boolean value() default true;
}
