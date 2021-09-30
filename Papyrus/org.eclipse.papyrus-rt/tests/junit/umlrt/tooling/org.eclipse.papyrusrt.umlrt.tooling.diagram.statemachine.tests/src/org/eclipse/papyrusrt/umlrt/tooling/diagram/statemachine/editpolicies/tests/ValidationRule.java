/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.editpolicies.tests;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.uml2.uml.Element;

/**
 * Validation rules for element creation
 */
public class ValidationRule {

	public ValidationRule() {
		// empty
	}

	public void validatePostEdition(Element newElement) throws Exception {
		// empty impl, to override
	}

	/**
	 * Obtain a supplier of validation rules based on a simplified protocol that includes only
	 * post-edit validation.
	 * 
	 * @param validator
	 *            a post-edit validation block
	 * 
	 * @return a supplier of a rule based on the {@code validator} block
	 */
	static Supplier<ValidationRule> postEdit(Consumer<? super Element> validator) {
		ValidationRule result = new ValidationRule() {
			@Override
			public void validatePostEdition(Element targetElement) throws Exception {
				validator.accept(targetElement);
			}
		};

		return () -> result;
	}

}
