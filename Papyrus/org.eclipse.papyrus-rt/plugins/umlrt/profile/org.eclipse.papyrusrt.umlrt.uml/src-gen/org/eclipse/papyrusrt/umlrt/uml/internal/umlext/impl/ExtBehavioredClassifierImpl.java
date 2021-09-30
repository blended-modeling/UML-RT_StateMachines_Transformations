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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtBehavioredClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;

import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.NamedElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Behaviored Classifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtBehavioredClassifierImpl#getImplicitOwnedMembers <em>Implicit Owned Member</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtBehavioredClassifierImpl#getImplicitBehaviors <em>Implicit Behavior</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ExtBehavioredClassifierImpl extends ExtNamespaceImpl implements ExtBehavioredClassifier {
	/**
	 * The cached value of the '{@link #getImplicitBehaviors() <em>Implicit Behavior</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitBehaviors()
	 * @generated
	 * @ordered
	 */
	protected EList<Behavior> implicitBehaviors;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtBehavioredClassifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtUMLExtPackage.Literals.BEHAVIORED_CLASSIFIER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NamedElement> getImplicitOwnedMembers() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			EList<NamedElement> implicitOwnedMembers = (EList<NamedElement>) cache.get(eResource, this, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			if (implicitOwnedMembers == null) {
				cache.put(eResource, this, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER,
						implicitOwnedMembers = new DerivedUnionEObjectEList<>(NamedElement.class, this, ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_OWNED_MEMBER, IMPLICIT_OWNED_MEMBER_ESUBSETS));
			}
			return implicitOwnedMembers;
		}
		return new DerivedUnionEObjectEList<>(NamedElement.class, this, ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_OWNED_MEMBER, IMPLICIT_OWNED_MEMBER_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getImplicitOwnedMembers() <em>Implicit Owned Member</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitOwnedMembers()
	 * @generated
	 * @ordered
	 */
	protected static final int[] IMPLICIT_OWNED_MEMBER_ESUBSETS = new int[] { ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_OWNED_RULE, ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Behavior> getImplicitBehaviors() {
		if (implicitBehaviors == null) {
			implicitBehaviors = new EObjectContainmentEList<>(Behavior.class, this, ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR);
		}
		return implicitBehaviors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Behavior createImplicitBehavior(String name, EClass eClass) {
		Behavior newImplicitBehavior = (Behavior) create(eClass);
		getImplicitBehaviors().add(newImplicitBehavior);
		if (name != null) {
			newImplicitBehavior.setName(name);
		}
		return newImplicitBehavior;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Behavior getImplicitBehavior(String name) {
		return getImplicitBehavior(name, false, null, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Behavior getImplicitBehavior(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand) {
		implicitBehaviorLoop: for (Behavior implicitBehavior : getImplicitBehaviors()) {
			if (eClass != null && !eClass.isInstance(implicitBehavior)) {
				continue implicitBehaviorLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(implicitBehavior.getName()) : name.equals(implicitBehavior.getName()))) {
				continue implicitBehaviorLoop;
			}
			return implicitBehavior;
		}
		return createOnDemand && eClass != null ? createImplicitBehavior(name, eClass) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR:
			return ((InternalEList<?>) getImplicitBehaviors()).basicRemove(otherEnd, msgs);
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
		case ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR:
			return getImplicitBehaviors();
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
		case ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR:
			getImplicitBehaviors().clear();
			getImplicitBehaviors().addAll((Collection<? extends Behavior>) newValue);
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
		case ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR:
			getImplicitBehaviors().clear();
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
		case ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_OWNED_MEMBER:
			return isSetImplicitOwnedMembers();
		case ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR:
			return implicitBehaviors != null && !implicitBehaviors.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetImplicitOwnedMembers() {
		return super.isSetImplicitOwnedMembers()
				|| eIsSet(ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR);
	}

} //ExtBehavioredClassifierImpl
