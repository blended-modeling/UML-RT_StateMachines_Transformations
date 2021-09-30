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

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ValueSpecificationRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.NotificationForwarder;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * UML-RT semantics for {@link LiteralString}.
 */
public class LiteralStringRTImpl extends org.eclipse.uml2.uml.internal.impl.LiteralStringImpl implements InternalUMLRTValueSpecification {

	private static final int VALUE__SET_FLAG = 0x1 << 0;

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.LITERAL_STRING__VALUE));

	private int uFlags = 0x0;

	public LiteralStringRTImpl() {
		super();
	}

	@Override
	public EObject create(EClass eClass) {
		EObject result;

		if (eClass.getEPackage() == eClass().getEPackage()) {
			result = UMLRTUMLFactoryImpl.eINSTANCE.create(eClass);
		} else {
			result = super.create(eClass);
		}

		return result;
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLPackage.LITERAL_STRING__VALUE:
			return isSetValue();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.LITERAL_STRING__VALUE:
			return (T) super.getValue();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.LITERAL_STRING__VALUE:
			unsetValue();
			break;
		default:
			super.eUnset(featureID);
			break;
		}
	}

	@Override
	public EObject eContainer() {
		Element owner = rtOwner();
		return (owner != null) ? owner : super.eContainer();
	}

	@Override
	public Resource eResource() {
		Resource result = rtResource();

		if (result instanceof ExtensionResource) {
			EObject container = eContainer();
			if (container != null) {
				result = container.eResource();
			}
		}

		return result;
	}

	@Override
	public <R extends InternalUMLRTElement> R rtGetRedefinedElement() {
		return ValueSpecificationRTOperations.rtGetRedefinedElement(this);
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		ValueSpecificationRTOperations.umlSetRedefinedElement(this, redefined);
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	@Override
	public void handleRedefinedMultiplicityElement(MultiplicityElement mult) {
		if (mult != null) {
			NotificationForwarder.adapt(this,
					() -> new NotificationForwarder(this, null, rtInheritedFeatures()));
		} else if (eBasicHasAdapters()) {
			NotificationForwarder.unadapt(this);
		}
	}

	@Override
	public void handleRedefinedConstraint(Constraint constraint) {
		if (constraint != null) {
			NotificationForwarder.adapt(this,
					() -> new NotificationForwarder(this, null, rtInheritedFeatures()));
		} else if (eBasicHasAdapters()) {
			NotificationForwarder.unadapt(this);
		}
	}

	@Override
	public void rtReify() {
		ValueSpecificationRTOperations.rtReify(this);
	}

	@Override
	public void rtVirtualize() {
		ValueSpecificationRTOperations.rtVirtualize(this);
	}

	@Override
	public void rtUnsetAll() {
		unsetValue();
	}

	@Override
	public Element basicGetOwner() {
		return rtOwner();
	}

	@Override
	public String getValue() {
		return inheritFeature(UMLPackage.Literals.LITERAL_STRING__VALUE);
	}

	@Override
	public void setValue(String newValue) {
		// Make sure that the notification gets the correct old value
		value = getValue();
		uFlags = uFlags | VALUE__SET_FLAG;
		super.setValue(newValue);
	}

	@Override
	public boolean isSetValue() {
		// I can only inherit my value if I am a virtual element
		return ((uFlags & VALUE__SET_FLAG) != 0) || !rtIsVirtual();
	}

	@Override
	public void unsetValue() {
		// Make sure that the notification gets the correct old and new values
		String oldValue = getValue();
		boolean oldValueESet = (uFlags & VALUE__SET_FLAG) != 0;
		value = VALUE_EDEFAULT;
		uFlags = uFlags & ~VALUE__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.LITERAL_STRING__VALUE, oldValue, getValue(), oldValueESet));
		}
	}

	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		return ElementRTOperations.toString(this, super.toString());
	}
}
