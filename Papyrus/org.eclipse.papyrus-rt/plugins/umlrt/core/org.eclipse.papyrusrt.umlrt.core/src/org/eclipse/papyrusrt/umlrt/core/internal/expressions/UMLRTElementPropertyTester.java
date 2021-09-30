/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

import java.util.Optional;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Trigger;

/**
 * XML expressions property tester for UML-RT {@link Element}s.
 */
public class UMLRTElementPropertyTester extends PropertyTester {
	private static final String IS_INHERITED = "isInherited";
	private static final String IS_REDEFINITION = "isRedefinition"; // $NON-NLS-1 //$NON-NLS-1$ $
	private static final String IS_EXCLUDED = "isExcluded"; //$NON-NLS-1$
	private static final String CAN_EXCLUDE = "canExclude"; //$NON-NLS-1$
	private static final String CAN_REINHERIT = "canReinherit"; //$NON-NLS-1$

	public UMLRTElementPropertyTester() {
		super();
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		Element element = PlatformHelper.getAdapter(receiver, Element.class);

		switch (property) {
		case IS_INHERITED:
			return isInherited(element) == asBoolean(expectedValue, true);
		case IS_REDEFINITION:
			return isRedefinition(element) == asBoolean(expectedValue, true);
		case IS_EXCLUDED:
			return isExcluded(element) == asBoolean(expectedValue, true);
		case CAN_EXCLUDE:
			return canExclude(element) == asBoolean(expectedValue, true);
		case CAN_REINHERIT:
			return canReinherit(element) == asBoolean(expectedValue, true);
		default:
			return false;
		}
	}

	boolean asBoolean(Object value, boolean defaultValue) {
		return (value instanceof Boolean)
				? (Boolean) value
				: (value == null)
						? defaultValue
						: Boolean.valueOf(String.valueOf(value));
	}

	protected Optional<UMLRTNamedElement> toUMLRT(Element element) {
		return Optional.ofNullable(element)
				.filter(NamedElement.class::isInstance).map(NamedElement.class::cast)
				.map(UMLRTFactory::create);
	}

	protected boolean isInherited(Element element) {
		return UMLRTInheritanceKind.of(element) == UMLRTInheritanceKind.INHERITED;
	}

	protected boolean isRedefinition(Element element) {
		return (UMLRTInheritanceKind.of(element) == UMLRTInheritanceKind.REDEFINED)
				// Triggers can have an implied redefined state
				|| Optional.ofNullable(TypeUtils.as(element, Trigger.class))
						.map(UMLRTTrigger::getInstance)
						.map(UMLRTTrigger::getInheritanceKind)
						.filter(UMLRTInheritanceKind.REDEFINED::equals)
						.isPresent();
	}

	protected boolean isExcluded(Element element) {
		return UMLRTInheritanceKind.of(element) == UMLRTInheritanceKind.EXCLUDED;
	}

	protected boolean canExclude(Element element) {
		return isInherited(element) || isRedefinition(element);
	}

	protected boolean canReinherit(Element element) {
		return isExcluded(element) || isRedefinition(element);
	}

}
