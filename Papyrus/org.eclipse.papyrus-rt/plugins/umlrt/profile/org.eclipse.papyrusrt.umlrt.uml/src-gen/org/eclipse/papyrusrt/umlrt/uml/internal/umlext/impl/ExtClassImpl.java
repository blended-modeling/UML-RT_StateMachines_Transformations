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

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtBehavioredClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtClass;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtEObjectContainmentEList;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtClassImpl#getImplicitOwnedMembers <em>Implicit Owned Member</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtClassImpl#getImplicitBehaviors <em>Implicit Behavior</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtClassImpl#getImplicitOperations <em>Implicit Operation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExtClassImpl extends ExtEncapsulatedClassifierImpl implements ExtClass {
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
	 * The cached value of the '{@link #getImplicitOperations() <em>Implicit Operation</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitOperations()
	 * @generated
	 * @ordered
	 */
	protected EList<Operation> implicitOperations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtUMLExtPackage.Literals.CLASS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Behavior> getImplicitBehaviors() {
		if (implicitBehaviors == null) {
			implicitBehaviors = new EObjectContainmentEList<>(Behavior.class, this, ExtUMLExtPackage.CLASS__IMPLICIT_BEHAVIOR);
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
	public EList<NamedElement> getImplicitOwnedMembers() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			EList<NamedElement> implicitOwnedMembers = (EList<NamedElement>) cache.get(eResource, this, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			if (implicitOwnedMembers == null) {
				cache.put(eResource, this, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER,
						implicitOwnedMembers = new DerivedUnionEObjectEList<>(NamedElement.class, this, ExtUMLExtPackage.CLASS__IMPLICIT_OWNED_MEMBER, IMPLICIT_OWNED_MEMBER_ESUBSETS));
			}
			return implicitOwnedMembers;
		}
		return new DerivedUnionEObjectEList<>(NamedElement.class, this, ExtUMLExtPackage.CLASS__IMPLICIT_OWNED_MEMBER, IMPLICIT_OWNED_MEMBER_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getImplicitOwnedMembers() <em>Implicit Owned Member</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitOwnedMembers()
	 * @generated
	 * @ordered
	 */
	protected static final int[] IMPLICIT_OWNED_MEMBER_ESUBSETS = new int[] { ExtUMLExtPackage.CLASS__IMPLICIT_OWNED_RULE, ExtUMLExtPackage.CLASS__IMPLICIT_ATTRIBUTE, ExtUMLExtPackage.CLASS__IMPLICIT_CONNECTOR, ExtUMLExtPackage.CLASS__IMPLICIT_BEHAVIOR,
			ExtUMLExtPackage.CLASS__IMPLICIT_OPERATION };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public EList<Operation> getImplicitOperations() {
		if (implicitOperations == null) {
			implicitOperations = new ExtEObjectContainmentEList<>(Operation.class, this, ExtUMLExtPackage.CLASS__IMPLICIT_OPERATION);
		}
		return implicitOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Operation createImplicitOperation(String name, EList<String> ownedParameterNames, EList<Type> ownedParameterTypes) {
		Operation newImplicitOperation = (Operation) create(UMLPackage.Literals.OPERATION);
		getImplicitOperations().add(newImplicitOperation);
		if (name != null) {
			newImplicitOperation.setName(name);
		}
		int ownedParameterListSize = 0;
		int ownedParameterNamesSize = ownedParameterNames == null ? 0 : ownedParameterNames.size();
		if (ownedParameterNamesSize > ownedParameterListSize) {
			ownedParameterListSize = ownedParameterNamesSize;
		}
		int ownedParameterTypesSize = ownedParameterTypes == null ? 0 : ownedParameterTypes.size();
		if (ownedParameterTypesSize > ownedParameterListSize) {
			ownedParameterListSize = ownedParameterTypesSize;
		}
		for (int i = 0; i < ownedParameterListSize; i++) {
			newImplicitOperation.createOwnedParameter(i < ownedParameterNamesSize ? (String) ownedParameterNames.get(i) : null, i < ownedParameterTypesSize ? (Type) ownedParameterTypes.get(i) : null);
		}
		return newImplicitOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Operation getImplicitOperation(String name, EList<String> ownedParameterNames, EList<Type> ownedParameterTypes) {
		return getImplicitOperation(name, ownedParameterNames, ownedParameterTypes, false, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Operation getImplicitOperation(String name, EList<String> ownedParameterNames, EList<Type> ownedParameterTypes, boolean ignoreCase, boolean createOnDemand) {
		implicitOperationLoop: for (Operation implicitOperation : getImplicitOperations()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(implicitOperation.getName()) : name.equals(implicitOperation.getName()))) {
				continue implicitOperationLoop;
			}
			EList<Parameter> ownedParameterList = implicitOperation.getOwnedParameters();
			int ownedParameterListSize = ownedParameterList.size();
			if (ownedParameterNames != null && ownedParameterNames.size() != ownedParameterListSize || (ownedParameterTypes != null && ownedParameterTypes.size() != ownedParameterListSize)) {
				continue implicitOperationLoop;
			}
			for (int j = 0; j < ownedParameterListSize; j++) {
				Parameter ownedParameter = ownedParameterList.get(j);
				if (ownedParameterNames != null && !(ignoreCase ? (ownedParameterNames.get(j)).equalsIgnoreCase(ownedParameter.getName()) : ownedParameterNames.get(j).equals(ownedParameter.getName()))) {
					continue implicitOperationLoop;
				}
				if (ownedParameterTypes != null && !ownedParameterTypes.get(j).equals(ownedParameter.getType())) {
					continue implicitOperationLoop;
				}
			}
			return implicitOperation;
		}
		return createOnDemand ? createImplicitOperation(name, ownedParameterNames, ownedParameterTypes) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ExtUMLExtPackage.CLASS__IMPLICIT_BEHAVIOR:
			return ((InternalEList<?>) getImplicitBehaviors()).basicRemove(otherEnd, msgs);
		case ExtUMLExtPackage.CLASS__IMPLICIT_OPERATION:
			return ((InternalEList<?>) getImplicitOperations()).basicRemove(otherEnd, msgs);
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
		case ExtUMLExtPackage.CLASS__IMPLICIT_BEHAVIOR:
			return getImplicitBehaviors();
		case ExtUMLExtPackage.CLASS__IMPLICIT_OPERATION:
			return getImplicitOperations();
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
		case ExtUMLExtPackage.CLASS__IMPLICIT_BEHAVIOR:
			getImplicitBehaviors().clear();
			getImplicitBehaviors().addAll((Collection<? extends Behavior>) newValue);
			return;
		case ExtUMLExtPackage.CLASS__IMPLICIT_OPERATION:
			getImplicitOperations().clear();
			getImplicitOperations().addAll((Collection<? extends Operation>) newValue);
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
		case ExtUMLExtPackage.CLASS__IMPLICIT_BEHAVIOR:
			getImplicitBehaviors().clear();
			return;
		case ExtUMLExtPackage.CLASS__IMPLICIT_OPERATION:
			getImplicitOperations().clear();
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
		case ExtUMLExtPackage.CLASS__IMPLICIT_OWNED_MEMBER:
			return isSetImplicitOwnedMembers();
		case ExtUMLExtPackage.CLASS__IMPLICIT_BEHAVIOR:
			return implicitBehaviors != null && !implicitBehaviors.isEmpty();
		case ExtUMLExtPackage.CLASS__IMPLICIT_OPERATION:
			return implicitOperations != null && !implicitOperations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ExtBehavioredClassifier.class) {
			switch (derivedFeatureID) {
			case ExtUMLExtPackage.CLASS__IMPLICIT_BEHAVIOR:
				return ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ExtBehavioredClassifier.class) {
			switch (baseFeatureID) {
			case ExtUMLExtPackage.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR:
				return ExtUMLExtPackage.CLASS__IMPLICIT_BEHAVIOR;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetImplicitOwnedMembers() {
		return super.isSetImplicitOwnedMembers()
				|| eIsSet(ExtUMLExtPackage.CLASS__IMPLICIT_BEHAVIOR)
				|| eIsSet(ExtUMLExtPackage.CLASS__IMPLICIT_OPERATION);
	}

} //ClassImpl
