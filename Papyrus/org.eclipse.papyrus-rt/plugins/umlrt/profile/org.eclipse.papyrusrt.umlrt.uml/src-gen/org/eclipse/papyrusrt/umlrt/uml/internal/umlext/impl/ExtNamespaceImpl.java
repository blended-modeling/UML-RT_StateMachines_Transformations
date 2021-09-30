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
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.operations.NamespaceOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtEObjectContainmentEList;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Namespace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtNamespaceImpl#getImplicitOwnedMembers <em>Implicit Owned Member</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtNamespaceImpl#getImplicitOwnedElements <em>Implicit Owned Element</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtNamespaceImpl#getExcludedMembers <em>Excluded Member</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtNamespaceImpl#getImplicitOwnedRules <em>Implicit Owned Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ExtNamespaceImpl extends ExtElementImpl implements ExtNamespace {
	/**
	 * The cached value of the '{@link #getImplicitOwnedRules() <em>Implicit Owned Rule</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitOwnedRules()
	 * @generated
	 * @ordered
	 */
	protected EList<Constraint> implicitOwnedRules;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtNamespaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtUMLExtPackage.Literals.NAMESPACE;
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
						implicitOwnedMembers = new DerivedUnionEObjectEList<>(NamedElement.class, this, ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_MEMBER, IMPLICIT_OWNED_MEMBER_ESUBSETS));
			}
			return implicitOwnedMembers;
		}
		return new DerivedUnionEObjectEList<>(NamedElement.class, this, ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_MEMBER, IMPLICIT_OWNED_MEMBER_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getImplicitOwnedMembers() <em>Implicit Owned Member</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitOwnedMembers()
	 * @generated
	 * @ordered
	 */
	protected static final int[] IMPLICIT_OWNED_MEMBER_ESUBSETS = new int[] { ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_RULE };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NamedElement getImplicitOwnedMember(String name) {
		return getImplicitOwnedMember(name, false, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NamedElement getImplicitOwnedMember(String name, boolean ignoreCase, EClass eClass) {
		implicitOwnedMemberLoop: for (NamedElement implicitOwnedMember : getImplicitOwnedMembers()) {
			if (eClass != null && !eClass.isInstance(implicitOwnedMember)) {
				continue implicitOwnedMemberLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(implicitOwnedMember.getName()) : name.equals(implicitOwnedMember.getName()))) {
				continue implicitOwnedMemberLoop;
			}
			return implicitOwnedMember;
		}
		return null;
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
						implicitOwnedElements = new DerivedUnionEObjectEList<>(Element.class, this, ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_ELEMENT, IMPLICIT_OWNED_ELEMENT_ESUBSETS));
			}
			return implicitOwnedElements;
		}
		return new DerivedUnionEObjectEList<>(Element.class, this, ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_ELEMENT, IMPLICIT_OWNED_ELEMENT_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getImplicitOwnedElements() <em>Implicit Owned Element</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitOwnedElements()
	 * @generated
	 * @ordered
	 */
	protected static final int[] IMPLICIT_OWNED_ELEMENT_ESUBSETS = new int[] { ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_MEMBER };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NamedElement> getExcludedMembers() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			@SuppressWarnings("unchecked")
			EList<NamedElement> result = (EList<NamedElement>) cache.get(this, ExtUMLExtPackage.Literals.NAMESPACE__EXCLUDED_MEMBER);
			if (result == null) {
				cache.put(this, ExtUMLExtPackage.Literals.NAMESPACE__EXCLUDED_MEMBER, result = NamespaceOperations.getExcludedMembers(this));
			}
			return result;
		}
		return NamespaceOperations.getExcludedMembers(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NamedElement getExcludedMember(String name) {
		return getExcludedMember(name, false, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NamedElement getExcludedMember(String name, boolean ignoreCase, EClass eClass) {
		excludedMemberLoop: for (NamedElement excludedMember : getExcludedMembers()) {
			if (eClass != null && !eClass.isInstance(excludedMember)) {
				continue excludedMemberLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(excludedMember.getName()) : name.equals(excludedMember.getName()))) {
				continue excludedMemberLoop;
			}
			return excludedMember;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated not
	 */
	@Override
	public EList<Constraint> getImplicitOwnedRules() {
		if (implicitOwnedRules == null) {
			implicitOwnedRules = new ExtEObjectContainmentEList<>(Constraint.class, this, ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_RULE);
		}
		return implicitOwnedRules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Constraint createImplicitOwnedRule(String name, EClass eClass) {
		Constraint newImplicitOwnedRule = (Constraint) create(eClass);
		getImplicitOwnedRules().add(newImplicitOwnedRule);
		if (name != null) {
			newImplicitOwnedRule.setName(name);
		}
		return newImplicitOwnedRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Constraint createImplicitOwnedRule(String name) {
		return createImplicitOwnedRule(name, UMLPackage.Literals.CONSTRAINT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Constraint getImplicitOwnedRule(String name) {
		return getImplicitOwnedRule(name, false, null, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Constraint getImplicitOwnedRule(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand) {
		implicitOwnedRuleLoop: for (Constraint implicitOwnedRule : getImplicitOwnedRules()) {
			if (eClass != null && !eClass.isInstance(implicitOwnedRule)) {
				continue implicitOwnedRuleLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(implicitOwnedRule.getName()) : name.equals(implicitOwnedRule.getName()))) {
				continue implicitOwnedRuleLoop;
			}
			return implicitOwnedRule;
		}
		return createOnDemand && eClass != null ? createImplicitOwnedRule(name, eClass) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_RULE:
			return ((InternalEList<?>) getImplicitOwnedRules()).basicRemove(otherEnd, msgs);
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
		case ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_MEMBER:
			return getImplicitOwnedMembers();
		case ExtUMLExtPackage.NAMESPACE__EXCLUDED_MEMBER:
			return getExcludedMembers();
		case ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_RULE:
			return getImplicitOwnedRules();
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
		case ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_RULE:
			getImplicitOwnedRules().clear();
			getImplicitOwnedRules().addAll((Collection<? extends Constraint>) newValue);
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
		case ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_RULE:
			getImplicitOwnedRules().clear();
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
		case ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_MEMBER:
			return isSetImplicitOwnedMembers();
		case ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_ELEMENT:
			return isSetImplicitOwnedElements();
		case ExtUMLExtPackage.NAMESPACE__EXCLUDED_MEMBER:
			return !getExcludedMembers().isEmpty();
		case ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_RULE:
			return implicitOwnedRules != null && !implicitOwnedRules.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetImplicitOwnedMembers() {
		return eIsSet(ExtUMLExtPackage.NAMESPACE__IMPLICIT_OWNED_RULE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetImplicitOwnedElements() {
		return super.isSetImplicitOwnedElements()
				|| isSetImplicitOwnedMembers();
	}

} // NamespaceImpl
