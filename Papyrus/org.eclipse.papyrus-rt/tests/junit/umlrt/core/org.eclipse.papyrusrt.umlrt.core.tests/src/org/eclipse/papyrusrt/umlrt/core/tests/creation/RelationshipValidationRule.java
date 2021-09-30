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

package org.eclipse.papyrusrt.umlrt.core.tests.creation;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.eclipse.uml2.uml.Element;

/**
 * Validation rules for relationship creation.
 */
public class RelationshipValidationRule {

	public RelationshipValidationRule() {
		// empty
	}

	public void validatePostEdition(Element newRelationship, RelationshipCreationParameters parameters) throws Exception {
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
	static Supplier<RelationshipValidationRule> postEdit(BiConsumer<? super Element, ? super RelationshipCreationParameters> validator) {
		RelationshipValidationRule result = new RelationshipValidationRule() {
			@Override
			public void validatePostEdition(Element targetElement, RelationshipCreationParameters parameters) throws Exception {
				validator.accept(targetElement, parameters);
			}
		};

		return () -> result;
	}

}
