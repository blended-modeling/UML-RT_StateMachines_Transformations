/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement;

import static org.eclipse.papyrusrt.umlrt.tooling.ui.Messages.NoTypeForTypedElement_Label;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper;
import org.eclipse.papyrus.uml.tools.util.UMLReferenceConverter;

/**
 * A specialized reference converter for UML-RT Protocol Message parameters
 * that understands the '*' notation for unspecified types.
 */
public class MessageParameterTypeReferenceConverter extends UMLReferenceConverter {

	public MessageParameterTypeReferenceConverter(INameResolutionHelper nameResolutionHelper) {
		super(nameResolutionHelper, false); // The type is a single value
	}

	@Override
	public IStatus isValidEditString(String aString) {
		return NoTypeForTypedElement_Label.equals(aString)
				? Status.OK_STATUS
				: super.isValidEditString(aString);
	}

	@Override
	public Object editToCanonicalValue(String editValue, int flag) {
		return editToNull(super.editToCanonicalValue(editValue, flag));
	}

	@Override
	public String canonicalToEditValue(Object object, int flag) {
		return nullToEdit(super.canonicalToEditValue(object, flag));
	}

	private Object editToNull(Object editValue) {
		Object result = editValue;

		if ((result instanceof String) && ((String) result).trim().equals(NoTypeForTypedElement_Label)) {
			result = ""; //$NON-NLS-1$
		}

		return result;
	}

	private String nullToEdit(String editValue) {
		String result = editValue;

		if ((result == null) || ((result instanceof String) && result.trim().isEmpty())) {
			result = NoTypeForTypedElement_Label;
		}

		return result;
	}

}
