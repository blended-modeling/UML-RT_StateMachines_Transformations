/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.utils;

import org.eclipse.uml2.uml.Element;

/**
 * Interface implemented by validation rule for deletion
 */
public interface IDeleteValidationRule {

	/**
	 * Validates the structure of the model after an element has been deleted
	 * 
	 * @param containerElement
	 *            the container of the deleted element
	 * @param deletedObject
	 *            the deleted object (initial target of the delete request)
	 * @param commandResults
	 *            the result of the deletion command
	 * @throws Exception
	 *             in case of troubles
	 */
	void validatePostEdition(Element containerElement, Element deletedObject, Object[] commandResults) throws Exception;

	/**
	 * Validates the structure of the model after an element has been deleted and command has been undone
	 * 
	 * @param containerElement
	 *            the container of the deleted element
	 * @param deletedObject
	 *            the deleted object (initial target of the delete request)
	 * @param commandResults
	 *            the result of the deletion command
	 * @throws Exception
	 *             in case of troubles
	 */
	void validatePostUndo(Element containerElement, Element deletedObject, Object[] commandResults) throws Exception;

}
