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
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtState;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtEObjectContainmentEList;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStateImpl#getImplicitOwnedMembers <em>Implicit Owned Member</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStateImpl#getImplicitRegions <em>Implicit Region</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStateImpl#getImplicitConnectionPoints <em>Implicit Connection Point</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExtStateImpl extends ExtNamespaceImpl implements ExtState {
	/**
	 * The cached value of the '{@link #getImplicitRegions() <em>Implicit Region</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitRegions()
	 * @generated
	 * @ordered
	 */
	protected EList<Region> implicitRegions;

	/**
	 * The cached value of the '{@link #getImplicitConnectionPoints() <em>Implicit Connection Point</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitConnectionPoints()
	 * @generated
	 * @ordered
	 */
	protected EList<Pseudostate> implicitConnectionPoints;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtUMLExtPackage.Literals.STATE;
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
						implicitOwnedMembers = new DerivedUnionEObjectEList<>(NamedElement.class, this, ExtUMLExtPackage.STATE__IMPLICIT_OWNED_MEMBER, IMPLICIT_OWNED_MEMBER_ESUBSETS));
			}
			return implicitOwnedMembers;
		}
		return new DerivedUnionEObjectEList<>(NamedElement.class, this, ExtUMLExtPackage.STATE__IMPLICIT_OWNED_MEMBER, IMPLICIT_OWNED_MEMBER_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getImplicitOwnedMembers() <em>Implicit Owned Member</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitOwnedMembers()
	 * @generated
	 * @ordered
	 */
	protected static final int[] IMPLICIT_OWNED_MEMBER_ESUBSETS = new int[] { ExtUMLExtPackage.STATE__IMPLICIT_OWNED_RULE, ExtUMLExtPackage.STATE__IMPLICIT_REGION, ExtUMLExtPackage.STATE__IMPLICIT_CONNECTION_POINT };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated not
	 */
	@Override
	public EList<Region> getImplicitRegions() {
		if (implicitRegions == null) {
			implicitRegions = new ExtEObjectContainmentEList<>(Region.class, this, ExtUMLExtPackage.STATE__IMPLICIT_REGION);
		}
		return implicitRegions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Region createImplicitRegion(String name) {
		Region newImplicitRegion = (Region) create(UMLPackage.Literals.REGION);
		getImplicitRegions().add(newImplicitRegion);
		if (name != null) {
			newImplicitRegion.setName(name);
		}
		return newImplicitRegion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Region getImplicitRegion(String name) {
		return getImplicitRegion(name, false, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Region getImplicitRegion(String name, boolean ignoreCase, boolean createOnDemand) {
		implicitRegionLoop: for (Region implicitRegion : getImplicitRegions()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(implicitRegion.getName()) : name.equals(implicitRegion.getName()))) {
				continue implicitRegionLoop;
			}
			return implicitRegion;
		}
		return createOnDemand ? createImplicitRegion(name) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated not
	 */
	@Override
	public EList<Pseudostate> getImplicitConnectionPoints() {
		if (implicitConnectionPoints == null) {
			implicitConnectionPoints = new ExtEObjectContainmentEList<>(Pseudostate.class, this, ExtUMLExtPackage.STATE__IMPLICIT_CONNECTION_POINT);
		}
		return implicitConnectionPoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Pseudostate createImplicitConnectionPoint(String name) {
		Pseudostate newImplicitConnectionPoint = (Pseudostate) create(UMLPackage.Literals.PSEUDOSTATE);
		getImplicitConnectionPoints().add(newImplicitConnectionPoint);
		if (name != null) {
			newImplicitConnectionPoint.setName(name);
		}
		return newImplicitConnectionPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Pseudostate getImplicitConnectionPoint(String name) {
		return getImplicitConnectionPoint(name, false, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Pseudostate getImplicitConnectionPoint(String name, boolean ignoreCase, boolean createOnDemand) {
		implicitConnectionPointLoop: for (Pseudostate implicitConnectionPoint : getImplicitConnectionPoints()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(implicitConnectionPoint.getName()) : name.equals(implicitConnectionPoint.getName()))) {
				continue implicitConnectionPointLoop;
			}
			return implicitConnectionPoint;
		}
		return createOnDemand ? createImplicitConnectionPoint(name) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ExtUMLExtPackage.STATE__IMPLICIT_REGION:
			return ((InternalEList<?>) getImplicitRegions()).basicRemove(otherEnd, msgs);
		case ExtUMLExtPackage.STATE__IMPLICIT_CONNECTION_POINT:
			return ((InternalEList<?>) getImplicitConnectionPoints()).basicRemove(otherEnd, msgs);
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
		case ExtUMLExtPackage.STATE__IMPLICIT_REGION:
			return getImplicitRegions();
		case ExtUMLExtPackage.STATE__IMPLICIT_CONNECTION_POINT:
			return getImplicitConnectionPoints();
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
		case ExtUMLExtPackage.STATE__IMPLICIT_REGION:
			getImplicitRegions().clear();
			getImplicitRegions().addAll((Collection<? extends Region>) newValue);
			return;
		case ExtUMLExtPackage.STATE__IMPLICIT_CONNECTION_POINT:
			getImplicitConnectionPoints().clear();
			getImplicitConnectionPoints().addAll((Collection<? extends Pseudostate>) newValue);
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
		case ExtUMLExtPackage.STATE__IMPLICIT_REGION:
			getImplicitRegions().clear();
			return;
		case ExtUMLExtPackage.STATE__IMPLICIT_CONNECTION_POINT:
			getImplicitConnectionPoints().clear();
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
		case ExtUMLExtPackage.STATE__IMPLICIT_OWNED_MEMBER:
			return isSetImplicitOwnedMembers();
		case ExtUMLExtPackage.STATE__IMPLICIT_REGION:
			return implicitRegions != null && !implicitRegions.isEmpty();
		case ExtUMLExtPackage.STATE__IMPLICIT_CONNECTION_POINT:
			return implicitConnectionPoints != null && !implicitConnectionPoints.isEmpty();
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
				|| eIsSet(ExtUMLExtPackage.STATE__IMPLICIT_REGION)
				|| eIsSet(ExtUMLExtPackage.STATE__IMPLICIT_CONNECTION_POINT);
	}

} // ExtStateImpl
