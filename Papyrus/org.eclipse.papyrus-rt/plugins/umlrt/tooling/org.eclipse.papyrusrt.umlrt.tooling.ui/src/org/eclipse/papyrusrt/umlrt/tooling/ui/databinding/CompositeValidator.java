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

package org.eclipse.papyrusrt.umlrt.tooling.ui.databinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * A delegating multiple validator.
 */
public class CompositeValidator implements IValidator {
	private final List<IValidator> validators;

	private CompositeValidator(IValidator aValidator, IValidator anotherValidator) {
		super();

		if ((aValidator instanceof CompositeValidator)
				&& (anotherValidator instanceof CompositeValidator)) {

			CompositeValidator cv1 = (CompositeValidator) aValidator;
			CompositeValidator cv2 = (CompositeValidator) anotherValidator;
			validators = new ArrayList<>(cv1.validators.size() + cv2.validators.size());
			validators.addAll(cv1.validators);
			validators.addAll(cv2.validators);
		} else if (aValidator instanceof CompositeValidator) {
			CompositeValidator cv1 = (CompositeValidator) aValidator;
			validators = new ArrayList<>(cv1.validators.size() + 1);
			validators.addAll(cv1.validators);
			validators.add(anotherValidator);
		} else if (anotherValidator instanceof CompositeValidator) {
			CompositeValidator cv2 = (CompositeValidator) anotherValidator;
			validators = new ArrayList<>(1 + cv2.validators.size());
			validators.add(aValidator);
			validators.addAll(cv2.validators);
		} else {
			validators = new ArrayList<>(2);
			validators.add(aValidator);
			validators.add(anotherValidator);
		}
	}

	/**
	 * Obtains a validator that composes validators, or just one of them
	 * if the other is {@code null}.
	 * 
	 * @param aValidator
	 *            a validator, or {@code null}
	 * @param anotherValidator
	 *            another validator, or {@code null}
	 * @return a composition of these validators
	 */
	public static IValidator of(IValidator aValidator, IValidator anotherValidator) {
		return (aValidator == null) ? anotherValidator
				: (anotherValidator == null) ? aValidator
						: new CompositeValidator(aValidator, anotherValidator);
	}

	/**
	 * Appends a validator to me.
	 * 
	 * @param validator
	 *            a validator to append
	 * 
	 * @return myself, as appended
	 */
	public CompositeValidator add(IValidator validator) {
		if (validator instanceof CompositeValidator) {
			validators.addAll(((CompositeValidator) validator).validators);
		} else {
			validators.add(validator);
		}

		return this;
	}

	@Override
	public IStatus validate(Object value) {
		return validators.stream()
				.map(v -> v.validate(value))
				.filter(Objects::nonNull)
				.max(this::compareStatus)
				.orElse(Status.OK_STATUS);
	}

	private int compareStatus(IStatus a, IStatus b) {
		return a.getSeverity() - b.getSeverity();
	}
}
