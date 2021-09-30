/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 493866
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.utils;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.eclipse.uml2.uml.Element;

/**
 * Interface implemented by validation rules
 */
public interface IValidationRule {

	void validatePostEdition(Element targetElement, Object[] commandResults) throws Exception;

	void validatePostUndo(Element targetElement, Object[] commandResults) throws Exception;

	/**
	 * Obtain a supplier of validation rules based on a simplified protocol that includes only
	 * post-edit validation.
	 * 
	 * @param validator
	 *            a post-edit validation block
	 * 
	 * @return a supplier of a rule based on the {@code validator} block
	 */
	static Supplier<IValidationRule> postEdit(BiConsumer<? super Element, ? super Object[]> validator) {
		IValidationRule result = new Stub() {
			@Override
			public void validatePostEdition(Element targetElement, Object[] commandResults) throws Exception {
				validator.accept(targetElement, commandResults);
			}
		};

		return () -> result;
	}

	/**
	 * Obtain a supplier of validation rules based on a simplified protocol that includes only
	 * post-edit validation and asserts that the edit command yields a single result of a given type.
	 *
	 * @param singleResultType
	 *            the expected type of the one expected result
	 * @param validator
	 *            a post-edit validation block
	 * 
	 * @return a supplier of a rule based on the {@code validator} block
	 */
	static <T> Supplier<IValidationRule> postEdit(Class<T> singleResultType, BiConsumer<? super Element, ? super T> validator) {
		IValidationRule result = new Stub() {
			@Override
			public void validatePostEdition(Element targetElement, Object[] commandResults) throws Exception {
				assertThat("Wrong number of results from command execution", commandResults.length, is(1));
				assertThat("Wrong type of command result", Arrays.asList(commandResults), hasItem(instanceOf(singleResultType)));
				T resultObject = singleResultType.cast(commandResults[0]);
				validator.accept(targetElement, resultObject);
			}
		};

		return () -> result;
	}

	//
	// Nested types
	//

	/**
	 * A convenience for implementation of validation rules that perform only a subset
	 * of the validation operations.
	 */
	class Stub implements IValidationRule {
		/**
		 * Does nothing.
		 */
		@Override
		public void validatePostEdition(Element targetElement, Object[] commandResults) throws Exception {
			// Pass
		}

		/**
		 * Does nothing.
		 */
		@Override
		public void validatePostUndo(Element targetElement, Object[] commandResults) throws Exception {
			// Pass
		}
	}
}
