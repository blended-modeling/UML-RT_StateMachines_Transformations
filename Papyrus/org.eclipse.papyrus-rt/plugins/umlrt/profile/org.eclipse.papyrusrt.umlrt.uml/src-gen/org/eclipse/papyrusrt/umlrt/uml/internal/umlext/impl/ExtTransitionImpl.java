/**
 * Copyright (c) 2017 Christian W. Damus and others.
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

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtTransition;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtEObjectContainmentEList;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtTransitionImpl#getImplicitOwnedElements <em>Implicit Owned Element</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtTransitionImpl#getImplicitTriggers <em>Implicit Trigger</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExtTransitionImpl extends ExtNamespaceImpl implements ExtTransition {
	/**
	 * The cached value of the '{@link #getImplicitTriggers() <em>Implicit Trigger</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitTriggers()
	 * @generated
	 * @ordered
	 */
	protected EList<Trigger> implicitTriggers;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtTransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtUMLExtPackage.Literals.TRANSITION;
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
				cache.put(eResource, this, ExtUMLExtPackage.Literals.ELEMENT__IMPLICIT_OWNED_ELEMENT,
						implicitOwnedElements = new DerivedUnionEObjectEList<>(Element.class, this, ExtUMLExtPackage.TRANSITION__IMPLICIT_OWNED_ELEMENT, IMPLICIT_OWNED_ELEMENT_ESUBSETS));
			}
			return implicitOwnedElements;
		}
		return new DerivedUnionEObjectEList<>(Element.class, this, ExtUMLExtPackage.TRANSITION__IMPLICIT_OWNED_ELEMENT, IMPLICIT_OWNED_ELEMENT_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getImplicitOwnedElements() <em>Implicit Owned Element</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitOwnedElements()
	 * @generated
	 * @ordered
	 */
	protected static final int[] IMPLICIT_OWNED_ELEMENT_ESUBSETS = new int[] { ExtUMLExtPackage.TRANSITION__IMPLICIT_OWNED_MEMBER, ExtUMLExtPackage.TRANSITION__IMPLICIT_TRIGGER };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated not
	 */
	@Override
	public EList<Trigger> getImplicitTriggers() {
		if (implicitTriggers == null) {
			implicitTriggers = new ExtEObjectContainmentEList<>(Trigger.class, this, ExtUMLExtPackage.TRANSITION__IMPLICIT_TRIGGER);
		}
		return implicitTriggers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Trigger createImplicitTrigger(String name) {
		Trigger newImplicitTrigger = (Trigger) create(UMLPackage.Literals.TRIGGER);
		getImplicitTriggers().add(newImplicitTrigger);
		if (name != null) {
			newImplicitTrigger.setName(name);
		}
		return newImplicitTrigger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Trigger getImplicitTrigger(String name) {
		return getImplicitTrigger(name, false, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Trigger getImplicitTrigger(String name, boolean ignoreCase, boolean createOnDemand) {
		implicitTriggerLoop: for (Trigger implicitTrigger : getImplicitTriggers()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(implicitTrigger.getName()) : name.equals(implicitTrigger.getName()))) {
				continue implicitTriggerLoop;
			}
			return implicitTrigger;
		}
		return createOnDemand ? createImplicitTrigger(name) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ExtUMLExtPackage.TRANSITION__IMPLICIT_TRIGGER:
			return ((InternalEList<?>) getImplicitTriggers()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ExtUMLExtPackage.TRANSITION__IMPLICIT_TRIGGER:
			return getImplicitTriggers();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ExtUMLExtPackage.TRANSITION__IMPLICIT_TRIGGER:
			getImplicitTriggers().clear();
			getImplicitTriggers().addAll((Collection<? extends Trigger>) newValue);
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
		case ExtUMLExtPackage.TRANSITION__IMPLICIT_TRIGGER:
			getImplicitTriggers().clear();
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
		case ExtUMLExtPackage.TRANSITION__IMPLICIT_OWNED_ELEMENT:
			return isSetImplicitOwnedElements();
		case ExtUMLExtPackage.TRANSITION__IMPLICIT_TRIGGER:
			return implicitTriggers != null && !implicitTriggers.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetImplicitOwnedElements() {
		return super.isSetImplicitOwnedElements()
				|| eIsSet(ExtUMLExtPackage.TRANSITION__IMPLICIT_TRIGGER);
	}

} // ExtTransitionImpl
