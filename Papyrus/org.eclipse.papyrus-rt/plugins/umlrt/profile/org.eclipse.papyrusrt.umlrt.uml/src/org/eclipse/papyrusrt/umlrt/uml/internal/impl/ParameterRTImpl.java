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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.MultiplicityElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.NotificationForwarder;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.VisibilityKind;

/**
 * UML-RT semantics for {@link Parameter}.
 */
public class ParameterRTImpl extends org.eclipse.uml2.uml.internal.impl.ParameterImpl implements InternalUMLRTMultiplicityElement {

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.NAMED_ELEMENT__NAME,
			UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY,
			UMLPackage.Literals.TYPED_ELEMENT__TYPE
	/* Don't include multiplicity bounds because they forward for themselves */));

	public ParameterRTImpl() {
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
		case UMLPackage.PARAMETER__NAME:
			return isSetName();
		case UMLPackage.PARAMETER__VISIBILITY:
			return isSetVisibility();
		case UMLPackage.PARAMETER__TYPE:
			return type != null;
		case UMLPackage.PARAMETER__LOWER_VALUE:
			return isSetLowerValue();
		case UMLPackage.PARAMETER__UPPER_VALUE:
			return isSetUpperValue();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.PARAMETER__NAME:
			return (T) super.getName();
		case UMLPackage.PARAMETER__VISIBILITY:
			return (T) super.getVisibility();
		case UMLPackage.PARAMETER__TYPE:
			return (T) super.getType();
		case UMLPackage.PARAMETER__LOWER_VALUE:
			return (T) super.getLowerValue();
		case UMLPackage.PARAMETER__UPPER_VALUE:
			return (T) super.getUpperValue();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.PARAMETER__LOWER_VALUE:
			unsetLowerValue();
			break;
		case UMLPackage.PARAMETER__UPPER_VALUE:
			unsetUpperValue();
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

	@SuppressWarnings("unchecked")
	@Override
	public <R extends InternalUMLRTElement> R rtGetRedefinedElement() {
		R result = null;

		Operation operation = getOperation();
		if ((operation != null) && (operation instanceof InternalUMLRTElement)) {
			Operation redefined = ((InternalUMLRTElement) operation).rtGetRedefinedElement();
			if (redefined != null) {
				int index = operation.getOwnedParameters().indexOf(this);
				if ((index >= 0) && (index < redefined.getOwnedParameters().size())) {
					Parameter parameter = redefined.getOwnedParameters().get(index);
					if (parameter instanceof InternalUMLRTElement) {
						result = (R) parameter;
					}
				}
			}
		}

		return result;
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof Parameter)) {
			throw new IllegalArgumentException("not a parameter: " + redefined); //$NON-NLS-1$
		}

		Parameter parameter = (Parameter) redefined;
		int index = ((Operation) parameter.getOwner()).getOwnedParameters().indexOf(parameter);
		if (index >= 0) {
			EList<Parameter> parameters = ((Operation) getOwner()).getOwnedParameters();
			int myIndex = parameters.indexOf(this);
			if (myIndex != index) {
				parameters.move(index, myIndex);
			}
		}
	}

	void handleRedefinedOperation(Operation operation) {
		if (operation != null) {
			NotificationForwarder.adapt(this,
					() -> new NotificationForwarder(this,
							/* FIXME: Function? */null, rtInheritedFeatures()));
		} else if (eBasicHasAdapters()) {
			NotificationForwarder.unadapt(this);
		}

		// And let my bounds know
		Parameter redefined = rtGetRedefinedElement();
		if (lowerValue instanceof InternalUMLRTValueSpecification) {
			((InternalUMLRTValueSpecification) lowerValue).handleRedefinedMultiplicityElement(redefined);
		}
		if (upperValue instanceof InternalUMLRTValueSpecification) {
			((InternalUMLRTValueSpecification) upperValue).handleRedefinedMultiplicityElement(redefined);
		}
	}

	@Override
	public void rtReify() {
		Operation operation = getOperation();
		if (operation != null) {
			EReference containment = eContainmentFeature();
			if (containment == UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER) {
				// Either all parameters are real, or none.
				EList<Parameter> parameters = operation.getOwnedParameters();
				if (parameters instanceof InheritableEList<?>) {
					((InheritableEList<Parameter>) parameters).touch();
				}
			}
		}
	}

	@Override
	public void rtVirtualize() {
		Operation operation = getOperation();
		if (operation != null) {
			EReference containment = eContainmentFeature();
			if (containment == UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER) {
				// Either all parameters are virtual, or none.
				operation.eUnset(containment);
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
	}

	@Override
	public Element basicGetOwner() {
		return rtOwner();
	}

	@Override
	public Operation basicGetOperation() {
		Element owner = basicGetOwner();
		return (owner instanceof Operation)
				? (Operation) owner
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
