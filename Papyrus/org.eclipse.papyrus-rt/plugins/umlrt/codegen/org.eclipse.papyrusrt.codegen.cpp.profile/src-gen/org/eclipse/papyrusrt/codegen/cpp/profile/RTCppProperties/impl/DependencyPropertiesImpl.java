/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyKind;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.DependencyProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;

import org.eclipse.uml2.uml.Dependency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dependency Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.DependencyPropertiesImpl#getKindInHeader <em>Kind In Header</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.DependencyPropertiesImpl#getKindInImplementation <em>Kind In Implementation</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.DependencyPropertiesImpl#getBase_Dependency <em>Base Dependency</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DependencyPropertiesImpl extends MinimalEObjectImpl.Container implements DependencyProperties {
	/**
	 * The default value of the '{@link #getKindInHeader() <em>Kind In Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKindInHeader()
	 * @generated
	 * @ordered
	 */
	protected static final DependencyKind KIND_IN_HEADER_EDEFAULT = DependencyKind.FORWARD_REFERENCE;

	/**
	 * The cached value of the '{@link #getKindInHeader() <em>Kind In Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKindInHeader()
	 * @generated
	 * @ordered
	 */
	protected DependencyKind kindInHeader = KIND_IN_HEADER_EDEFAULT;

	/**
	 * The default value of the '{@link #getKindInImplementation() <em>Kind In Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKindInImplementation()
	 * @generated
	 * @ordered
	 */
	protected static final DependencyKind KIND_IN_IMPLEMENTATION_EDEFAULT = DependencyKind.FORWARD_REFERENCE;

	/**
	 * The cached value of the '{@link #getKindInImplementation() <em>Kind In Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKindInImplementation()
	 * @generated
	 * @ordered
	 */
	protected DependencyKind kindInImplementation = KIND_IN_IMPLEMENTATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBase_Dependency() <em>Base Dependency</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_Dependency()
	 * @generated
	 * @ordered
	 */
	protected Dependency base_Dependency;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DependencyPropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.DEPENDENCY_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependencyKind getKindInHeader() {
		return kindInHeader;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKindInHeader(DependencyKind newKindInHeader) {
		DependencyKind oldKindInHeader = kindInHeader;
		kindInHeader = newKindInHeader == null ? KIND_IN_HEADER_EDEFAULT : newKindInHeader;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__KIND_IN_HEADER, oldKindInHeader, kindInHeader));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependencyKind getKindInImplementation() {
		return kindInImplementation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKindInImplementation(DependencyKind newKindInImplementation) {
		DependencyKind oldKindInImplementation = kindInImplementation;
		kindInImplementation = newKindInImplementation == null ? KIND_IN_IMPLEMENTATION_EDEFAULT : newKindInImplementation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__KIND_IN_IMPLEMENTATION, oldKindInImplementation, kindInImplementation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dependency getBase_Dependency() {
		if (base_Dependency != null && base_Dependency.eIsProxy()) {
			InternalEObject oldBase_Dependency = (InternalEObject)base_Dependency;
			base_Dependency = (Dependency)eResolveProxy(oldBase_Dependency);
			if (base_Dependency != oldBase_Dependency) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__BASE_DEPENDENCY, oldBase_Dependency, base_Dependency));
			}
		}
		return base_Dependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dependency basicGetBase_Dependency() {
		return base_Dependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase_Dependency(Dependency newBase_Dependency) {
		Dependency oldBase_Dependency = base_Dependency;
		base_Dependency = newBase_Dependency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__BASE_DEPENDENCY, oldBase_Dependency, base_Dependency));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__KIND_IN_HEADER:
				return getKindInHeader();
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__KIND_IN_IMPLEMENTATION:
				return getKindInImplementation();
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__BASE_DEPENDENCY:
				if (resolve) return getBase_Dependency();
				return basicGetBase_Dependency();
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
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__KIND_IN_HEADER:
				setKindInHeader((DependencyKind)newValue);
				return;
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__KIND_IN_IMPLEMENTATION:
				setKindInImplementation((DependencyKind)newValue);
				return;
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__BASE_DEPENDENCY:
				setBase_Dependency((Dependency)newValue);
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
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__KIND_IN_HEADER:
				setKindInHeader(KIND_IN_HEADER_EDEFAULT);
				return;
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__KIND_IN_IMPLEMENTATION:
				setKindInImplementation(KIND_IN_IMPLEMENTATION_EDEFAULT);
				return;
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__BASE_DEPENDENCY:
				setBase_Dependency((Dependency)null);
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
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__KIND_IN_HEADER:
				return kindInHeader != KIND_IN_HEADER_EDEFAULT;
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__KIND_IN_IMPLEMENTATION:
				return kindInImplementation != KIND_IN_IMPLEMENTATION_EDEFAULT;
			case RTCppPropertiesPackage.DEPENDENCY_PROPERTIES__BASE_DEPENDENCY:
				return base_Dependency != null;
		}
		return super.eIsSet(featureID);
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
		result.append(" (KindInHeader: ");
		result.append(kindInHeader);
		result.append(", KindInImplementation: ");
		result.append(kindInImplementation);
		result.append(')');
		return result.toString();
	}

} //DependencyPropertiesImpl
