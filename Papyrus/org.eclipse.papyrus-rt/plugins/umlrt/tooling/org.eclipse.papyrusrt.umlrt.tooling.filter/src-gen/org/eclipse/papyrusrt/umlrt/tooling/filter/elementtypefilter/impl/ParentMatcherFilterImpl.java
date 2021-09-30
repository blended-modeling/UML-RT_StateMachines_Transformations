/**
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ElementtypefilterPackage;
import org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ParentMatcherFilter;
import org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.internal.operations.ParentMatcherFilterOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parent Matcher Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.impl.ParentMatcherFilterImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.impl.ParentMatcherFilterImpl#getParentType <em>Parent Type</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.impl.ParentMatcherFilterImpl#isSubtypesCheck <em>Subtypes Check</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParentMatcherFilterImpl extends MinimalEObjectImpl.Container implements ParentMatcherFilter {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getParentType() <em>Parent Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentType()
	 * @generated
	 * @ordered
	 */
	protected static final String PARENT_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParentType() <em>Parent Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentType()
	 * @generated
	 * @ordered
	 */
	protected String parentType = PARENT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isSubtypesCheck() <em>Subtypes Check</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSubtypesCheck()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SUBTYPES_CHECK_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSubtypesCheck() <em>Subtypes Check</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSubtypesCheck()
	 * @generated
	 * @ordered
	 */
	protected boolean subtypesCheck = SUBTYPES_CHECK_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParentMatcherFilterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ElementtypefilterPackage.Literals.PARENT_MATCHER_FILTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementtypefilterPackage.PARENT_MATCHER_FILTER__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getParentType() {
		return parentType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentType(String newParentType) {
		String oldParentType = parentType;
		parentType = newParentType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementtypefilterPackage.PARENT_MATCHER_FILTER__PARENT_TYPE, oldParentType, parentType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSubtypesCheck() {
		return subtypesCheck;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubtypesCheck(boolean newSubtypesCheck) {
		boolean oldSubtypesCheck = subtypesCheck;
		subtypesCheck = newSubtypesCheck;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementtypefilterPackage.PARENT_MATCHER_FILTER__SUBTYPES_CHECK, oldSubtypesCheck, subtypesCheck));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean matches(Object input) {
		return ParentMatcherFilterOperations.matches(this, input);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ElementtypefilterPackage.PARENT_MATCHER_FILTER__NAME:
				return getName();
			case ElementtypefilterPackage.PARENT_MATCHER_FILTER__PARENT_TYPE:
				return getParentType();
			case ElementtypefilterPackage.PARENT_MATCHER_FILTER__SUBTYPES_CHECK:
				return isSubtypesCheck();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ElementtypefilterPackage.PARENT_MATCHER_FILTER__NAME:
				setName((String)newValue);
				return;
			case ElementtypefilterPackage.PARENT_MATCHER_FILTER__PARENT_TYPE:
				setParentType((String)newValue);
				return;
			case ElementtypefilterPackage.PARENT_MATCHER_FILTER__SUBTYPES_CHECK:
				setSubtypesCheck((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ElementtypefilterPackage.PARENT_MATCHER_FILTER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ElementtypefilterPackage.PARENT_MATCHER_FILTER__PARENT_TYPE:
				setParentType(PARENT_TYPE_EDEFAULT);
				return;
			case ElementtypefilterPackage.PARENT_MATCHER_FILTER__SUBTYPES_CHECK:
				setSubtypesCheck(SUBTYPES_CHECK_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ElementtypefilterPackage.PARENT_MATCHER_FILTER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ElementtypefilterPackage.PARENT_MATCHER_FILTER__PARENT_TYPE:
				return PARENT_TYPE_EDEFAULT == null ? parentType != null : !PARENT_TYPE_EDEFAULT.equals(parentType);
			case ElementtypefilterPackage.PARENT_MATCHER_FILTER__SUBTYPES_CHECK:
				return subtypesCheck != SUBTYPES_CHECK_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ElementtypefilterPackage.PARENT_MATCHER_FILTER___MATCHES__OBJECT:
				return matches(arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", parentType: "); //$NON-NLS-1$
		result.append(parentType);
		result.append(", subtypesCheck: "); //$NON-NLS-1$
		result.append(subtypesCheck);
		result.append(')');
		return result.toString();
	}

} //ParentMatcherFilterImpl
