/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.core.internal.expressions;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.BehavioredClassifier;

/**
 * XML expressions property tester for UML-RT {@link Behavior}s.
 */
public class BehaviorPropertyTester extends PropertyTester {
	private static final String CONTEXT_PROPERTY = "context"; //$NON-NLS-1$

	private static final String NULL = String.valueOf((Object) null);

	private static final String UML_PREFIX = "uml::"; //$NON-NLS-1$

	public BehaviorPropertyTester() {
		super();
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		Behavior behavior = PlatformHelper.getAdapter(receiver, Behavior.class);

		switch (property) {
		case CONTEXT_PROPERTY:
			return testContext(behavior, String.valueOf(expectedValue));
		default:
			return false;
		}
	}

	protected boolean testContext(Behavior behavior, String expectedValue) {
		boolean result;
		BehavioredClassifier context = behavior.getContext();

		if (expectedValue.equals(NULL)) {
			result = context == null;
		} else if (expectedValue.isEmpty()) {
			result = false;
		} else if (expectedValue.toLowerCase().startsWith(UML_PREFIX)) {
			result = (context != null) && context.eClass().getName().equals(expectedValue.substring(UML_PREFIX.length()));
		} else {
			result = (context != null) && (context.getAppliedStereotype(expectedValue) != null);
		}

		return result;
	}
}
