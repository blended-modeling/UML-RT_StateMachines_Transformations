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
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.MultiplicityElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.RedefinedElementsList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.SubsetSupersetRedefinedElementsList;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * UML-RT semantics for {@link Port}.
 */
public class PortRTImpl extends org.eclipse.uml2.uml.internal.impl.PortImpl implements InternalUMLRTMultiplicityElement {

	private static final int AGGREGATION__SET_FLAG = 0x1 << 0;
	private static final int IS_SERVICE__SET_FLAG = 0x1 << 1;
	private static final int IS_BEHAVIOR__SET_FLAG = 0x1 << 2;
	private static final int IS_CONJUGATED__SET_FLAG = 0x1 << 3;

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.NAMED_ELEMENT__NAME,
			UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY,
			UMLPackage.Literals.TYPED_ELEMENT__TYPE,
			UMLPackage.Literals.PROPERTY__AGGREGATION,
			UMLPackage.Literals.PORT__IS_SERVICE,
			UMLPackage.Literals.PORT__IS_BEHAVIOR,
			UMLPackage.Literals.PORT__IS_CONJUGATED
	/* Don't include multiplicity bounds because they forward for themselves */));

	private int uFlags = 0x0;

	public PortRTImpl() {
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
		case UMLPackage.PORT__NAME:
			return isSetName();
		case UMLPackage.PORT__VISIBILITY:
			return isSetVisibility();
		case UMLPackage.PORT__TYPE:
			return type != null;
		case UMLPackage.PORT__AGGREGATION:
			return isSetAggregation();
		case UMLPackage.PORT__IS_SERVICE:
			return isSetService();
		case UMLPackage.PORT__IS_BEHAVIOR:
			return isSetBehavior();
		case UMLPackage.PORT__IS_CONJUGATED:
			return isSetConjugated();
		case UMLPackage.PORT__LOWER_VALUE:
			return isSetLowerValue();
		case UMLPackage.PORT__UPPER_VALUE:
			return isSetUpperValue();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.PORT__NAME:
			return (T) super.getName();
		case UMLPackage.PORT__VISIBILITY:
			return (T) super.getVisibility();
		case UMLPackage.PORT__TYPE:
			return (T) super.getType();
		case UMLPackage.PORT__AGGREGATION:
			return (T) super.getAggregation();
		case UMLPackage.PORT__IS_SERVICE:
			return (T) (Boolean) super.isService();
		case UMLPackage.PORT__IS_BEHAVIOR:
			return (T) (Boolean) super.isBehavior();
		case UMLPackage.PORT__IS_CONJUGATED:
			return (T) (Boolean) super.isConjugated();
		case UMLPackage.PORT__LOWER_VALUE:
			return (T) super.getLowerValue();
		case UMLPackage.PORT__UPPER_VALUE:
			return (T) super.getUpperValue();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.PORT__LOWER_VALUE:
			unsetLowerValue();
			break;
		case UMLPackage.PORT__UPPER_VALUE:
			unsetUpperValue();
			break;
		case UMLPackage.PORT__AGGREGATION:
			unsetAggregation();
			break;
		case UMLPackage.PORT__IS_SERVICE:
			unsetIsService();
			break;
		case UMLPackage.PORT__IS_BEHAVIOR:
			unsetIsBehavior();
			break;
		case UMLPackage.PORT__IS_CONJUGATED:
			unsetIsConjugated();
			break;
		default:
			super.eUnset(featureID);
			break;
		}
	}

	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLPackage.PORT__AGGREGATION:
			if (newValue == null) {
				unsetAggregation();
			} else {
				setAggregation((AggregationKind) newValue);
			}
			break;
		case UMLPackage.PORT__IS_SERVICE:
			if (newValue == null) {
				unsetIsService();
			} else {
				setIsService((Boolean) newValue);
			}
			break;
		case UMLPackage.PORT__IS_BEHAVIOR:
			if (newValue == null) {
				unsetIsBehavior();
			} else {
				setIsBehavior((Boolean) newValue);
			}
			break;
		case UMLPackage.PORT__IS_CONJUGATED:
			if (newValue == null) {
				unsetIsConjugated();
			} else {
				setIsConjugated((Boolean) newValue);
			}
			break;
		default:
			super.eSet(featureID, newValue);
			break;
		}
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof Port)) {
			throw new IllegalArgumentException("not a port: " + redefined); //$NON-NLS-1$
		}
		((RedefinedElementsList<Port>) getRedefinedPorts()).setRedefinedElement((Port) redefined);
	}

	@Override
	public EList<Property> getRedefinedProperties() {
		if (redefinedProperties == null) {
			redefinedProperties = new SubsetSupersetRedefinedElementsList<>(
					Property.class, this, UMLPackage.PORT__REDEFINED_PROPERTY,
					null, REDEFINED_PROPERTY_ESUBSETS);
		}
		return redefinedProperties;
	}

	@Override
	public EList<Port> getRedefinedPorts() {
		if (redefinedPorts == null) {
			redefinedPorts = new SubsetSupersetRedefinedElementsList<>(
					Port.class, this, UMLPackage.PORT__REDEFINED_PORT,
					REDEFINED_PORT_ESUPERSETS, null,
					this::handleRedefinedPort);
		}
		return redefinedPorts;
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	void handleRedefinedPort(Port port) {
		// Let my stereotype application know
		RTPort stereo = UMLUtil.getStereotypeApplication(this, RTPort.class);
		if (stereo instanceof RTPortRTImpl) {
			((RTPortRTImpl) stereo).handleRedefinedPort(port);
		}

		// And my bounds
		if (lowerValue instanceof InternalUMLRTValueSpecification) {
			((InternalUMLRTValueSpecification) lowerValue).handleRedefinedMultiplicityElement(port);
		}
		if (upperValue instanceof InternalUMLRTValueSpecification) {
			((InternalUMLRTValueSpecification) upperValue).handleRedefinedMultiplicityElement(port);
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
	public void rtReify() {
		Class class_ = getClass_();
		if ((class_ != null) && !class_.getOwnedPorts().contains(this)) {
			class_.getOwnedPorts().add(this);
			rtAdjustStereotypes();
		}
	}

	@Override
	public void rtVirtualize() {
		Namespace namespace = getNamespace();
		if (namespace instanceof Class) {
			Class class_ = (Class) namespace;

			@SuppressWarnings("unchecked")
			EList<? super Port> implicitPorts = (EList<? super Port>) class_.eGet(ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT);
			if (!implicitPorts.contains(this)) {
				implicitPorts.add(this);
				rtAdjustStereotypes();
			}
		}
	}

	@Override
	public void rtUnsetAll() {
		unsetName();
		unsetVisibility();
		setType(null);
		unsetAggregation();
		unsetLowerValue();
		unsetUpperValue();
		unsetIsBehavior();
		unsetIsConjugated();
		unsetIsService();

		getStereotypeApplications().stream()
				.filter(InternalUMLRTElement.class::isInstance)
				.map(InternalUMLRTElement.class::cast)
				.forEach(InternalUMLRTElement::rtUnsetAll);
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.PORT__AGGREGATION, oldAggregation, getAggregation(), wasSet));
		}
	}

	public boolean isSetService() {
		return (uFlags & IS_SERVICE__SET_FLAG) != 0;
	}

	@Override
	public boolean isService() {
		return inheritFeature(UMLPackage.Literals.PORT__IS_SERVICE);
	}

	@Override
	public void setIsService(boolean newIsService) {
		boolean wasSet = isSetService();
		boolean oldIsService = isService();

		eFlags = newIsService ? (eFlags | IS_SERVICE_EFLAG) : (eFlags & ~IS_SERVICE_EFLAG);
		uFlags = uFlags | IS_SERVICE__SET_FLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UMLPackage.PORT__IS_SERVICE, oldIsService, newIsService, !wasSet));
	}

	public void unsetIsService() {
		boolean wasSet = isSetService();
		boolean oldIsService = isService();

		eFlags = IS_SERVICE_EDEFAULT ? (eFlags | IS_SERVICE_EFLAG) : (eFlags & ~IS_SERVICE_EFLAG);
		uFlags = uFlags & ~IS_SERVICE__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.PORT__IS_SERVICE, oldIsService, isService(), wasSet));
		}
	}

	public boolean isSetBehavior() {
		return (uFlags & IS_BEHAVIOR__SET_FLAG) != 0;
	}

	@Override
	public boolean isBehavior() {
		return inheritFeature(UMLPackage.Literals.PORT__IS_BEHAVIOR);
	}

	@Override
	public void setIsBehavior(boolean newIsBehavior) {
		boolean wasSet = isSetBehavior();
		boolean oldIsBehavior = isBehavior();

		eFlags = newIsBehavior ? (eFlags | IS_BEHAVIOR_EFLAG) : (eFlags & ~IS_BEHAVIOR_EFLAG);
		uFlags = uFlags | IS_BEHAVIOR__SET_FLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UMLPackage.PORT__IS_BEHAVIOR, oldIsBehavior, newIsBehavior, !wasSet));
	}

	public void unsetIsBehavior() {
		boolean wasSet = isSetBehavior();
		boolean oldIsBehavior = isBehavior();

		eFlags = IS_BEHAVIOR_EDEFAULT ? (eFlags | IS_BEHAVIOR_EFLAG) : (eFlags & ~IS_BEHAVIOR_EFLAG);
		uFlags = uFlags & ~IS_BEHAVIOR__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.PORT__IS_BEHAVIOR, oldIsBehavior, isBehavior(), wasSet));
		}
	}

	public boolean isSetConjugated() {
		return (uFlags & IS_CONJUGATED__SET_FLAG) != 0;
	}

	@Override
	public boolean isConjugated() {
		return inheritFeature(UMLPackage.Literals.PORT__IS_CONJUGATED);
	}

	@Override
	public void setIsConjugated(boolean newIsConjugated) {
		boolean wasSet = isSetConjugated();
		boolean oldIsConjugated = isConjugated();

		eFlags = newIsConjugated ? (eFlags | IS_CONJUGATED_EFLAG) : (eFlags & ~IS_CONJUGATED_EFLAG);
		uFlags = uFlags | IS_CONJUGATED__SET_FLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UMLPackage.PORT__IS_CONJUGATED, oldIsConjugated, newIsConjugated, !wasSet));
	}

	public void unsetIsConjugated() {
		boolean wasSet = isSetConjugated();
		boolean oldIsConjugated = isConjugated();

		eFlags = IS_CONJUGATED_EDEFAULT ? (eFlags | IS_CONJUGATED_EFLAG) : (eFlags & ~IS_CONJUGATED_EFLAG);
		uFlags = uFlags & ~IS_CONJUGATED__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.PORT__IS_CONJUGATED, oldIsConjugated, isConjugated(), wasSet));
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
	public void setUpperValue(ValueSpecification newUpperValue) {
		MultiplicityElementRTOperations.setUpperValue(this, newUpperValue);
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
