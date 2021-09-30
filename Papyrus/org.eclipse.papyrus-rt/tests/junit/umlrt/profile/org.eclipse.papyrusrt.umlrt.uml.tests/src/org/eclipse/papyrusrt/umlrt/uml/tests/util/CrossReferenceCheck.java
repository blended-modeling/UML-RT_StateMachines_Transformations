/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml.tests.util;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotates a test method or class to indicate whether the standard cross-reference
 * persistence check should be done on the test resource after completion of the test.
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface CrossReferenceCheck {
	/**
	 * Whether the test should conclude with a check that no cross-references in the
	 * UML model are dangling, either referencing detached objects or objects in the
	 * <em>Extension Extent</em>. Such dangling references are often indicative of
	 * bugs in the implementation of inheritable or otherwise inheritance-related
	 * reference properties. Annotating with a {@code false} value is useful for
	 * tests that for reasons of their own deliberately leave dangling references.
	 * 
	 * @return whether to perform the cross-reference persistence check after completion of the test
	 */
	boolean value() default true;
}
