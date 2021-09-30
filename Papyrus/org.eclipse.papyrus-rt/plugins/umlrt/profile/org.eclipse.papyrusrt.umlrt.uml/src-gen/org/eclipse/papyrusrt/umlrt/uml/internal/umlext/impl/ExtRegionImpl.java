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
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtEObjectContainmentEList;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Region</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtRegionImpl#getImplicitOwnedMembers <em>Implicit Owned Member</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtRegionImpl#getImplicitSubvertices <em>Implicit Subvertex</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtRegionImpl#getImplicitTransitions <em>Implicit Transition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExtRegionImpl extends ExtNamespaceImpl implements ExtRegion {
	/**
	 * The cached value of the '{@link #getImplicitSubvertices() <em>Implicit Subvertex</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitSubvertices()
	 * @generated
	 * @ordered
	 */
	protected EList<Vertex> implicitSubvertices;

	/**
	 * The cached value of the '{@link #getImplicitTransitions() <em>Implicit Transition</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> implicitTransitions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtRegionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtUMLExtPackage.Literals.REGION;
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
						implicitOwnedMembers = new DerivedUnionEObjectEList<>(NamedElement.class, this, ExtUMLExtPackage.REGION__IMPLICIT_OWNED_MEMBER, IMPLICIT_OWNED_MEMBER_ESUBSETS));
			}
			return implicitOwnedMembers;
		}
		return new DerivedUnionEObjectEList<>(NamedElement.class, this, ExtUMLExtPackage.REGION__IMPLICIT_OWNED_MEMBER, IMPLICIT_OWNED_MEMBER_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getImplicitOwnedMembers() <em>Implicit Owned Member</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitOwnedMembers()
	 * @generated
	 * @ordered
	 */
	protected static final int[] IMPLICIT_OWNED_MEMBER_ESUBSETS = new int[] { ExtUMLExtPackage.REGION__IMPLICIT_OWNED_RULE, ExtUMLExtPackage.REGION__IMPLICIT_SUBVERTEX, ExtUMLExtPackage.REGION__IMPLICIT_TRANSITION };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated not
	 */
	@Override
	public EList<Vertex> getImplicitSubvertices() {
		if (implicitSubvertices == null) {
			implicitSubvertices = new ExtEObjectContainmentEList<>(Vertex.class, this, ExtUMLExtPackage.REGION__IMPLICIT_SUBVERTEX);
		}
		return implicitSubvertices;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Vertex createImplicitSubvertex(String name, EClass eClass) {
		Vertex newImplicitSubvertex = (Vertex) create(eClass);
		getImplicitSubvertices().add(newImplicitSubvertex);
		if (name != null) {
			newImplicitSubvertex.setName(name);
		}
		return newImplicitSubvertex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Vertex getImplicitSubvertex(String name) {
		return getImplicitSubvertex(name, false, null, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Vertex getImplicitSubvertex(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand) {
		implicitSubvertexLoop: for (Vertex implicitSubvertex : getImplicitSubvertices()) {
			if (eClass != null && !eClass.isInstance(implicitSubvertex)) {
				continue implicitSubvertexLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(implicitSubvertex.getName()) : name.equals(implicitSubvertex.getName()))) {
				continue implicitSubvertexLoop;
			}
			return implicitSubvertex;
		}
		return createOnDemand && eClass != null ? createImplicitSubvertex(name, eClass) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated not
	 */
	@Override
	public EList<Transition> getImplicitTransitions() {
		if (implicitTransitions == null) {
			implicitTransitions = new ExtEObjectContainmentEList<>(Transition.class, this, ExtUMLExtPackage.REGION__IMPLICIT_TRANSITION);
		}
		return implicitTransitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Transition createImplicitTransition(String name, EClass eClass) {
		Transition newImplicitTransition = (Transition) create(eClass);
		getImplicitTransitions().add(newImplicitTransition);
		if (name != null) {
			newImplicitTransition.setName(name);
		}
		return newImplicitTransition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Transition createImplicitTransition(String name) {
		return createImplicitTransition(name, UMLPackage.Literals.TRANSITION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Transition getImplicitTransition(String name) {
		return getImplicitTransition(name, false, null, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Transition getImplicitTransition(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand) {
		implicitTransitionLoop: for (Transition implicitTransition : getImplicitTransitions()) {
			if (eClass != null && !eClass.isInstance(implicitTransition)) {
				continue implicitTransitionLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(implicitTransition.getName()) : name.equals(implicitTransition.getName()))) {
				continue implicitTransitionLoop;
			}
			return implicitTransition;
		}
		return createOnDemand && eClass != null ? createImplicitTransition(name, eClass) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ExtUMLExtPackage.REGION__IMPLICIT_SUBVERTEX:
			return ((InternalEList<?>) getImplicitSubvertices()).basicRemove(otherEnd, msgs);
		case ExtUMLExtPackage.REGION__IMPLICIT_TRANSITION:
			return ((InternalEList<?>) getImplicitTransitions()).basicRemove(otherEnd, msgs);
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
		case ExtUMLExtPackage.REGION__IMPLICIT_SUBVERTEX:
			return getImplicitSubvertices();
		case ExtUMLExtPackage.REGION__IMPLICIT_TRANSITION:
			return getImplicitTransitions();
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
		case ExtUMLExtPackage.REGION__IMPLICIT_SUBVERTEX:
			getImplicitSubvertices().clear();
			getImplicitSubvertices().addAll((Collection<? extends Vertex>) newValue);
			return;
		case ExtUMLExtPackage.REGION__IMPLICIT_TRANSITION:
			getImplicitTransitions().clear();
			getImplicitTransitions().addAll((Collection<? extends Transition>) newValue);
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
		case ExtUMLExtPackage.REGION__IMPLICIT_SUBVERTEX:
			getImplicitSubvertices().clear();
			return;
		case ExtUMLExtPackage.REGION__IMPLICIT_TRANSITION:
			getImplicitTransitions().clear();
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
		case ExtUMLExtPackage.REGION__IMPLICIT_OWNED_MEMBER:
			return isSetImplicitOwnedMembers();
		case ExtUMLExtPackage.REGION__IMPLICIT_SUBVERTEX:
			return implicitSubvertices != null && !implicitSubvertices.isEmpty();
		case ExtUMLExtPackage.REGION__IMPLICIT_TRANSITION:
			return implicitTransitions != null && !implicitTransitions.isEmpty();
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
				|| eIsSet(ExtUMLExtPackage.REGION__IMPLICIT_SUBVERTEX)
				|| eIsSet(ExtUMLExtPackage.REGION__IMPLICIT_TRANSITION);
	}

} // ExtRegionImpl
