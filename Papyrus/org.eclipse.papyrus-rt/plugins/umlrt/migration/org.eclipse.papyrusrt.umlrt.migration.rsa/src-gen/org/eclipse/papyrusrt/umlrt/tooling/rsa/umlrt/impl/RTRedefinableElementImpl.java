/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTRedefinableElement;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage;
import org.eclipse.uml2.uml.RedefinableElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RT Redefinable Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTRedefinableElementImpl#getBase_RedefinableElement <em>Base Redefinable Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTRedefinableElementImpl#getRootFragment <em>Root Fragment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RTRedefinableElementImpl extends MinimalEObjectImpl.Container implements RTRedefinableElement {
	/**
	 * The cached value of the '{@link #getBase_RedefinableElement() <em>Base Redefinable Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getBase_RedefinableElement()
	 * @generated
	 * @ordered
	 */
	protected RedefinableElement base_RedefinableElement;

	/**
	 * The cached value of the '{@link #getRootFragment() <em>Root Fragment</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRootFragment()
	 * @generated
	 * @ordered
	 */
	protected RedefinableElement rootFragment;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RTRedefinableElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRealTimePackage.Literals.RT_REDEFINABLE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RedefinableElement getBase_RedefinableElement() {
		if (base_RedefinableElement != null && base_RedefinableElement.eIsProxy()) {
			InternalEObject oldBase_RedefinableElement = (InternalEObject) base_RedefinableElement;
			base_RedefinableElement = (RedefinableElement) eResolveProxy(oldBase_RedefinableElement);
			if (base_RedefinableElement != oldBase_RedefinableElement) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UMLRealTimePackage.RT_REDEFINABLE_ELEMENT__BASE_REDEFINABLE_ELEMENT, oldBase_RedefinableElement, base_RedefinableElement));
				}
			}
		}
		return base_RedefinableElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public RedefinableElement basicGetBase_RedefinableElement() {
		return base_RedefinableElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setBase_RedefinableElement(RedefinableElement newBase_RedefinableElement) {
		RedefinableElement oldBase_RedefinableElement = base_RedefinableElement;
		base_RedefinableElement = newBase_RedefinableElement;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_REDEFINABLE_ELEMENT__BASE_REDEFINABLE_ELEMENT, oldBase_RedefinableElement, base_RedefinableElement));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RedefinableElement getRootFragment() {
		if (rootFragment != null && rootFragment.eIsProxy()) {
			InternalEObject oldRootFragment = (InternalEObject) rootFragment;
			rootFragment = (RedefinableElement) eResolveProxy(oldRootFragment);
			if (rootFragment != oldRootFragment) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UMLRealTimePackage.RT_REDEFINABLE_ELEMENT__ROOT_FRAGMENT, oldRootFragment, rootFragment));
				}
			}
		}
		return rootFragment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public RedefinableElement basicGetRootFragment() {
		return rootFragment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRootFragment(RedefinableElement newRootFragment) {
		RedefinableElement oldRootFragment = rootFragment;
		rootFragment = newRootFragment;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_REDEFINABLE_ELEMENT__ROOT_FRAGMENT, oldRootFragment, rootFragment));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UMLRealTimePackage.RT_REDEFINABLE_ELEMENT__BASE_REDEFINABLE_ELEMENT:
			if (resolve) {
				return getBase_RedefinableElement();
			}
			return basicGetBase_RedefinableElement();
		case UMLRealTimePackage.RT_REDEFINABLE_ELEMENT__ROOT_FRAGMENT:
			if (resolve) {
				return getRootFragment();
			}
			return basicGetRootFragment();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLRealTimePackage.RT_REDEFINABLE_ELEMENT__BASE_REDEFINABLE_ELEMENT:
			setBase_RedefinableElement((RedefinableElement) newValue);
			return;
		case UMLRealTimePackage.RT_REDEFINABLE_ELEMENT__ROOT_FRAGMENT:
			setRootFragment((RedefinableElement) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLRealTimePackage.RT_REDEFINABLE_ELEMENT__BASE_REDEFINABLE_ELEMENT:
			setBase_RedefinableElement((RedefinableElement) null);
			return;
		case UMLRealTimePackage.RT_REDEFINABLE_ELEMENT__ROOT_FRAGMENT:
			setRootFragment((RedefinableElement) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLRealTimePackage.RT_REDEFINABLE_ELEMENT__BASE_REDEFINABLE_ELEMENT:
			return base_RedefinableElement != null;
		case UMLRealTimePackage.RT_REDEFINABLE_ELEMENT__ROOT_FRAGMENT:
			return rootFragment != null;
		}
		return super.eIsSet(featureID);
	}

} // RTRedefinableElementImpl
