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
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.HistoryKind;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTHistorystate;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage;
import org.eclipse.uml2.uml.Pseudostate;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RT Historystate</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTHistorystateImpl#getBase_Pseudostate <em>Base Pseudostate</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTHistorystateImpl#getHistoryKind <em>History Kind</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RTHistorystateImpl extends MinimalEObjectImpl.Container implements RTHistorystate {
	/**
	 * The cached value of the '{@link #getBase_Pseudostate() <em>Base Pseudostate</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getBase_Pseudostate()
	 * @generated
	 * @ordered
	 */
	protected Pseudostate base_Pseudostate;

	/**
	 * The default value of the '{@link #getHistoryKind() <em>History Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getHistoryKind()
	 * @generated
	 * @ordered
	 */
	protected static final HistoryKind HISTORY_KIND_EDEFAULT = HistoryKind.DEEP;

	/**
	 * The cached value of the '{@link #getHistoryKind() <em>History Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getHistoryKind()
	 * @generated
	 * @ordered
	 */
	protected HistoryKind historyKind = HISTORY_KIND_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RTHistorystateImpl() {
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
		return UMLRealTimePackage.Literals.RT_HISTORYSTATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Pseudostate getBase_Pseudostate() {
		if (base_Pseudostate != null && base_Pseudostate.eIsProxy()) {
			InternalEObject oldBase_Pseudostate = (InternalEObject) base_Pseudostate;
			base_Pseudostate = (Pseudostate) eResolveProxy(oldBase_Pseudostate);
			if (base_Pseudostate != oldBase_Pseudostate) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UMLRealTimePackage.RT_HISTORYSTATE__BASE_PSEUDOSTATE, oldBase_Pseudostate, base_Pseudostate));
				}
			}
		}
		return base_Pseudostate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Pseudostate basicGetBase_Pseudostate() {
		return base_Pseudostate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setBase_Pseudostate(Pseudostate newBase_Pseudostate) {
		Pseudostate oldBase_Pseudostate = base_Pseudostate;
		base_Pseudostate = newBase_Pseudostate;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_HISTORYSTATE__BASE_PSEUDOSTATE, oldBase_Pseudostate, base_Pseudostate));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public HistoryKind getHistoryKind() {
		return historyKind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setHistoryKind(HistoryKind newHistoryKind) {
		HistoryKind oldHistoryKind = historyKind;
		historyKind = newHistoryKind == null ? HISTORY_KIND_EDEFAULT : newHistoryKind;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_HISTORYSTATE__HISTORY_KIND, oldHistoryKind, historyKind));
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
		case UMLRealTimePackage.RT_HISTORYSTATE__BASE_PSEUDOSTATE:
			if (resolve) {
				return getBase_Pseudostate();
			}
			return basicGetBase_Pseudostate();
		case UMLRealTimePackage.RT_HISTORYSTATE__HISTORY_KIND:
			return getHistoryKind();
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
		case UMLRealTimePackage.RT_HISTORYSTATE__BASE_PSEUDOSTATE:
			setBase_Pseudostate((Pseudostate) newValue);
			return;
		case UMLRealTimePackage.RT_HISTORYSTATE__HISTORY_KIND:
			setHistoryKind((HistoryKind) newValue);
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
		case UMLRealTimePackage.RT_HISTORYSTATE__BASE_PSEUDOSTATE:
			setBase_Pseudostate((Pseudostate) null);
			return;
		case UMLRealTimePackage.RT_HISTORYSTATE__HISTORY_KIND:
			setHistoryKind(HISTORY_KIND_EDEFAULT);
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
		case UMLRealTimePackage.RT_HISTORYSTATE__BASE_PSEUDOSTATE:
			return base_Pseudostate != null;
		case UMLRealTimePackage.RT_HISTORYSTATE__HISTORY_KIND:
			return historyKind != HISTORY_KIND_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (historyKind: ");
		result.append(historyKind);
		result.append(')');
		return result.toString();
	}

} // RTHistorystateImpl
