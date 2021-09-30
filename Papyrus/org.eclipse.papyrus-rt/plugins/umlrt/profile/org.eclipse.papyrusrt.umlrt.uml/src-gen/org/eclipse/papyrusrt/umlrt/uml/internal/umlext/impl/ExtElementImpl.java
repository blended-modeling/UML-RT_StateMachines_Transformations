/**
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;

import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.operations.ElementOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionHolder;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Extension Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtElementImpl#getImplicitOwnedElements <em>Implicit Owned Element</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtElementImpl#getExtendedElement <em>Extended Element</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtElementImpl#getExtension <em>Extension</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtElementImpl#getExcludedElements <em>Excluded Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ExtElementImpl extends MinimalEObjectImpl.Container implements ExtElement {
	/**
	 * The cached value of the '{@link #getExtendedElement() <em>Extended Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedElement()
	 * @generated
	 * @ordered
	 */
	protected Element extendedElement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtUMLExtPackage.Literals.ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Element> getImplicitOwnedElements() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			EList<Element> implicitOwnedElements = (EList<Element>) cache.get(eResource, this, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT);
			if (implicitOwnedElements == null) {
				cache.put(eResource, this, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT, implicitOwnedElements = new DerivedUnionEObjectEList<>(Element.class, this, ExtUMLExtPackage.ELEMENT__IMPLICIT_OWNED_ELEMENT, null));
			}
			return implicitOwnedElements;
		}
		return new DerivedUnionEObjectEList<>(Element.class, this, ExtUMLExtPackage.ELEMENT__IMPLICIT_OWNED_ELEMENT, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Element getExtendedElement() {
		return extendedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtendedElementGen(Element newExtendedElement) {
		Element oldExtendedElement = extendedElement;
		extendedElement = newExtendedElement;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExtUMLExtPackage.ELEMENT__EXTENDED_ELEMENT, oldExtendedElement, extendedElement));
		}
	}

	@Override
	public void setExtendedElement(Element newExtendedElement) {
		Element oldExtendedElement = extendedElement;
		
		if (oldExtendedElement == newExtendedElement) {
		setExtendedElementGen(newExtendedElement);
		} else {
			if (oldExtendedElement instanceof InternalUMLRTElement) {
				// Disconnect its extension holder
				ExtensionHolder holder = ExtensionHolder.get((InternalUMLRTElement) oldExtendedElement);
				holder.setExtension(null);
			}

		setExtendedElementGen(newExtendedElement);
		
			if (newExtendedElement instanceof InternalUMLRTElement) {
				// Reconnect its extension holder
				ExtensionHolder holder = ExtensionHolder.get((InternalUMLRTElement) newExtendedElement);
					holder.setExtension(this);
			}
		}
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExtElement getExtension() {
		return ElementOperations.getExtension(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Element> getExcludedElements() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			@SuppressWarnings("unchecked")
			EList<Element> result = (EList<Element>) cache.get(this, ExtUMLExtPackage.Literals.ELEMENT__EXCLUDED_ELEMENT);
			if (result == null) {
				cache.put(this, ExtUMLExtPackage.Literals.ELEMENT__EXCLUDED_ELEMENT, result = ElementOperations.getExcludedElements(this));
			}
			return result;
		}
		return ElementOperations.getExcludedElements(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ExtUMLExtPackage.ELEMENT__IMPLICIT_OWNED_ELEMENT:
			return getImplicitOwnedElements();
		case ExtUMLExtPackage.ELEMENT__EXTENDED_ELEMENT:
			return getExtendedElement();
		case ExtUMLExtPackage.ELEMENT__EXTENSION:
			return getExtension();
		case ExtUMLExtPackage.ELEMENT__EXCLUDED_ELEMENT:
			return getExcludedElements();
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
		case ExtUMLExtPackage.ELEMENT__EXTENDED_ELEMENT:
			setExtendedElement((Element) newValue);
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
		case ExtUMLExtPackage.ELEMENT__EXTENDED_ELEMENT:
			setExtendedElement((Element) null);
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
		case ExtUMLExtPackage.ELEMENT__IMPLICIT_OWNED_ELEMENT:
			return isSetImplicitOwnedElements();
		case ExtUMLExtPackage.ELEMENT__EXTENDED_ELEMENT:
			return extendedElement != null;
		case ExtUMLExtPackage.ELEMENT__EXTENSION:
			return getExtension() != null;
		case ExtUMLExtPackage.ELEMENT__EXCLUDED_ELEMENT:
			return !getExcludedElements().isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * Creates a new instance of the specified Ecore class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the instance to create.
	 * @return The new instance.
	 * @generated
	 */
	protected EObject create(EClass eClass) {
		return EcoreUtil.create(eClass);
	}

	/**
	 * Retrieves the cache adapter for this '<em><b>Element</b></em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The cache adapter for this '<em><b>Element</b></em>'.
	 * @generated
	 */
	protected CacheAdapter getCacheAdapter() {
		return CacheAdapter.getCacheAdapter(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetImplicitOwnedElements() {
		return false;
	}

} //ExtensionElementImpl
