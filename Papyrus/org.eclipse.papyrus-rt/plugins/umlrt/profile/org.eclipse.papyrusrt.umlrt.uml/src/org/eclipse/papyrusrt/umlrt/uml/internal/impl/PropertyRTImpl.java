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
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.MultiplicityElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.RedefinedElementsList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.RedefinedElementsListImpl;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.VisibilityKind;

/**
 * UML-RT semantics for {@link Property}.
 */
public class PropertyRTImpl extends org.eclipse.uml2.uml.internal.impl.PropertyImpl implements InternalUMLRTMultiplicityElement {

	private static final int AGGREGATION__SET_FLAG = 0x1 << 0;

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.NAMED_ELEMENT__NAME,
			UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY,
			UMLPackage.Literals.TYPED_ELEMENT__TYPE,
			UMLPackage.Literals.PROPERTY__AGGREGATION
	/* Don't include multiplicity bounds because they forward for themselves */));

	private int uFlags = 0x0;

	protected PropertyRTImpl() {
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
		case UMLPackage.PROPERTY__NAME:
			return isSetName();
		case UMLPackage.PROPERTY__VISIBILITY:
			return isSetVisibility();
		case UMLPackage.PROPERTY__TYPE:
			return type != null;
		case UMLPackage.PROPERTY__LOWER_VALUE:
			return isSetLowerValue();
		case UMLPackage.PROPERTY__UPPER_VALUE:
			return isSetUpperValue();
		case UMLPackage.PROPERTY__AGGREGATION:
			return isSetAggregation();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.PROPERTY__NAME:
			return (T) super.getName();
		case UMLPackage.PROPERTY__VISIBILITY:
			return (T) super.getVisibility();
		case UMLPackage.PROPERTY__TYPE:
			return (T) super.getType();
		case UMLPackage.PROPERTY__LOWER_VALUE:
			return (T) super.getLowerValue();
		case UMLPackage.PROPERTY__UPPER_VALUE:
			return (T) super.getUpperValue();
		case UMLPackage.PROPERTY__AGGREGATION:
			return (T) super.getAggregation();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}


	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.PROPERTY__LOWER_VALUE:
			unsetLowerValue();
			break;
		case UMLPackage.PROPERTY__UPPER_VALUE:
			unsetUpperValue();
			break;
		case UMLPackage.PROPERTY__AGGREGATION:
			unsetAggregation();
			break;
		default:
			super.eUnset(featureID);
			break;
		}
	}

	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLPackage.PROPERTY__AGGREGATION:
			if (newValue == null) {
				unsetAggregation();
			} else {
				setAggregation((AggregationKind) newValue);
			}
			break;
		default:
			super.eSet(featureID, newValue);
			break;
		}
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof Property)) {
			throw new IllegalArgumentException("not a property: " + redefined); //$NON-NLS-1$
		}

		((RedefinedElementsList<Property>) getRedefinedProperties()).setRedefinedElement((Property) redefined);
	}

	@Override
	public EList<Property> getRedefinedProperties() {
		if (redefinedProperties == null) {
			redefinedProperties = new RedefinedElementsListImpl<>(
					Property.class, this, UMLPackage.PROPERTY__REDEFINED_PROPERTY,
					this::handleRedefinedProperty);
		}
		return redefinedProperties;
	}

	void handleRedefinedProperty(Property property) {
		// Let my bounds know
		if (lowerValue instanceof InternalUMLRTValueSpecification) {
			((InternalUMLRTValueSpecification) lowerValue).handleRedefinedMultiplicityElement(property);
		}
		if (upperValue instanceof InternalUMLRTValueSpecification) {
			((InternalUMLRTValueSpecification) upperValue).handleRedefinedMultiplicityElement(property);
		}
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
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
	public void rtReify() {
		Class class_ = getClass_();
		if ((class_ != null) && !class_.getOwnedAttributes().contains(this)) {
			class_.getOwnedAttributes().add(this);
			rtAdjustStereotypes();
		}
	}

	@Override
	public void rtVirtualize() {
		Namespace namespace = getNamespace();
		if (namespace instanceof Class) {
			Class class_ = (Class) namespace;

			@SuppressWarnings("unchecked")
			EList<? super Property> implicitAttributes = (EList<? super Property>) class_.eGet(ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE);
			if (!implicitAttributes.contains(this)) {
				implicitAttributes.add(this);
				rtAdjustStereotypes();
			}
		}
	}

	@Override
	public void rtUnsetAll() {
		unsetName();
		unsetVisibility();
		setType(null);
		unsetLowerValue();
		unsetUpperValue();
		unsetAggregation();
	}

	@Override
	public Element basicGetOwner() {
		return rtOwner();
	}

	@Override
	public Class basicGetClass_() {
		Element rtOwner = rtOwner();
		return (rtOwner instanceof org.eclipse.uml2.uml.Class)
				? (org.eclipse.uml2.uml.Class) rtOwner
				: null;
	}

	@Override
	public String getName() {
		return inheritFeature(UMLPackage.Literals.NAMED_ELEMENT__NAME);
	}

	@Override
	public void setName(String newName) {
		// Make sure that the notification gets the correct old value
		name = getName();
		super.setName(newName);
	}

	@Override
	public void unsetName() {
		// Make sure that the notification gets the correct old and new values
		String oldName = getName();
		boolean oldNameESet = (eFlags & NAME_ESETFLAG) != 0;
		name = NAME_EDEFAULT;
		eFlags = eFlags & ~NAME_ESETFLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.NAMED_ELEMENT__NAME, oldName, getName(), oldNameESet));
		}
	}

	@Override
	public VisibilityKind getVisibility() {
		return inheritFeature(UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY);
	}

	@Override
	public void setVisibility(VisibilityKind newVisibility) {
		// Make sure that the notification gets the correct old value
		if (newVisibility == null) {
			newVisibility = VISIBILITY_EDEFAULT;
		}
		eFlags = eFlags | (getVisibility().ordinal() << VISIBILITY_EFLAG_OFFSET);
		super.setVisibility(newVisibility);
	}

	@Override
	public void unsetVisibility() {
		// Make sure that the notification gets the correct old and new values
		VisibilityKind oldVisibility = getVisibility();
		boolean oldVisibilityESet = (eFlags & VISIBILITY_ESETFLAG) != 0;
		eFlags = (eFlags & ~VISIBILITY_EFLAG) | VISIBILITY_EFLAG_DEFAULT;
		eFlags = eFlags & ~VISIBILITY_ESETFLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.NAMED_ELEMENT__VISIBILITY, oldVisibility, getVisibility(), oldVisibilityESet));
		}
	}

	@Override
	public Type getType() {
		return inheritFeature(UMLPackage.Literals.TYPED_ELEMENT__TYPE);
	}

	@Override
	public void setType(Type newType) {
		// Make sure that the notification gets the correct old value
		type = getType();
		super.setType(newType);
	}

	@Override
	public AggregationKind getAggregation() {
		return inheritFeature(UMLPackage.Literals.PROPERTY__AGGREGATION);
	}

	@Override
	public void setAggregation(AggregationKind newAggregation) {
		// Make sure that the notification gets the correct old value
		if (newAggregation == null) {
			newAggregation = AGGREGATION_EDEFAULT;
		}
		eFlags = eFlags | (getAggregation().ordinal() << AGGREGATION_EFLAG_OFFSET);
		uFlags = uFlags | AGGREGATION__SET_FLAG;
		super.setAggregation(newAggregation);
	}

	public boolean isSetAggregation() {
		return (uFlags & AGGREGATION__SET_FLAG) != 0;
	}

	public void unsetAggregation() {
		// Make sure that the notification gets the correct old and new values
		AggregationKind oldAggregation = getAggregation();
		boolean wasSet = isSetAggregation();

		eFlags = (eFlags & ~AGGREGATION_EFLAG) | AGGREGATION_EFLAG_DEFAULT;
		uFlags = uFlags & ~AGGREGATION__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.PROPERTY__AGGREGATION, oldAggregation, getAggregation(), wasSet));
		}
	}

	@Override
	public ValueSpecification umlGetLowerValue(boolean resolve) {
		return resolve ? super.getLowerValue() : super.basicGetLowerValue();
	}

	@Override
	public NotificationChain umlBasicSetLowerValue(ValueSpecification newLowerValue, NotificationChain msgs) {
		return super.basicSetLowerValue(newLowerValue, msgs);
	}

	@Override
	public ValueSpecification getLowerValue() {
		return MultiplicityElementRTOperations.getLowerValue(this);
	}

	@Override
	public void setLowerValue(ValueSpecification newLowerValue) {
		MultiplicityElementRTOperations.setLowerValue(this, newLowerValue);
	}

	public boolean isSetLowerValue() {
		return MultiplicityElementRTOperations.isSetLowerValue(this);
	}

	public void unsetLowerValue() {
		MultiplicityElementRTOperations.unsetLowerValue(this);
	}

	@Override
	public ValueSpecification umlGetUpperValue(boolean resolve) {
		return resolve ? super.getUpperValue() : super.basicGetUpperValue();
	}

	@Override
	public NotificationChain umlBasicSetUpperValue(ValueSpecification newUpperValue, NotificationChain msgs) {
		return super.basicSetUpperValue(newUpperValue, msgs);
	}

	@Override
	public ValueSpecification getUpperValue() {
		return MultiplicityElementRTOperations.getUpperValue(this);
	}

	@Override
	public void setUpperValue(ValueSpecification newLowerValue) {
		MultiplicityElementRTOperations.setUpperValue(this, newLowerValue);
	}

	public boolean isSetUpperValue() {
		return MultiplicityElementRTOperations.isSetUpperValue(this);
	}

	public void unsetUpperValue() {
		MultiplicityElementRTOperations.unsetUpperValue(this);
	}

	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		return ElementRTOperations.toString(this, super.toString());
	}
}
