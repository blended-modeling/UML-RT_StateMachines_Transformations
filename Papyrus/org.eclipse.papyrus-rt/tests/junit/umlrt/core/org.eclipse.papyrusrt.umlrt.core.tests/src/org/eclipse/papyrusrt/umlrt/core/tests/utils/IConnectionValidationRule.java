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

import org.eclipse.uml2.uml.Element;

/**
 * Interface implemented by validation rules for connection creation tests.
 */
public interface IConnectionValidationRule {

	/**
	 * Validates the creation, after it is executed.
	 * 
	 * @param source
	 *            the source of the link
	 * @param target
	 *            the target of the link
	 * @param newElement
	 *            the new link created
	 * @throws Exception
	 *             any exception thrown during evaluation
	 */
	void validatePostEdition(Element source, Element target, Object newElement) throws Exception;

	/**
	 * Validates the creation, after it is undone.
	 * 
	 * @param source
	 *            the source of the link
	 * @param target
	 *            the target of the link
	 * @param newElement
	 *            the new link created
	 * @throws Exception
	 *             any exception thrown during evaluation
	 */
	void validatePostUndo(Element source, Element target, Object newElement) throws Exception;


	//
	// Nested types
	//

	/**
	 * A convenience for implementation of validation rules that perform only a subset
	 * of the validation operations.
	 */
	class Stub implements IConnectionValidationRule {

		/**
		 * Constructor.
		 */
		public Stub() {
			// empty
		}

		/**
		 * Does nothing.
		 */
		@Override
		public void validatePostEdition(Element source, Element target, Object newElement) throws Exception {
			// Pass
		}

		/**
		 * Does nothing.
		 */
		@Override
		public void validatePostUndo(Element source, Element target, Object newElement) throws Exception {
			// Pass
		}
	}
}
