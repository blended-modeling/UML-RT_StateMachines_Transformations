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
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.RTPort;
import org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.UMLRealTimePackage;
import org.eclipse.uml2.uml.Port;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RT Port</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTPortImpl#getBase_Port <em>Base Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTPortImpl#isIsConjugate <em>Is Conjugate</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTPortImpl#isIsNotification <em>Is Notification</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTPortImpl#isIsPublish <em>Is Publish</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTPortImpl#isIsWired <em>Is Wired</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTPortImpl#getRegistration <em>Registration</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.rsa.umlrt.impl.RTPortImpl#getRegistrationOverride <em>Registration Override</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RTPortImpl extends MinimalEObjectImpl.Container implements RTPort {
	/**
	 * The cached value of the '{@link #getBase_Port() <em>Base Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getBase_Port()
	 * @generated
	 * @ordered
	 */
	protected Port base_Port;

	/**
	 * The default value of the '{@link #isIsConjugate() <em>Is Conjugate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isIsConjugate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_CONJUGATE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsConjugate() <em>Is Conjugate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isIsConjugate()
	 * @generated
	 * @ordered
	 */
	protected boolean isConjugate = IS_CONJUGATE_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsNotification() <em>Is Notification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isIsNotification()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_NOTIFICATION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsNotification() <em>Is Notification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isIsNotification()
	 * @generated
	 * @ordered
	 */
	protected boolean isNotification = IS_NOTIFICATION_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsPublish() <em>Is Publish</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isIsPublish()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_PUBLISH_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsPublish() <em>Is Publish</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isIsPublish()
	 * @generated
	 * @ordered
	 */
	protected boolean isPublish = IS_PUBLISH_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsWired() <em>Is Wired</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isIsWired()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_WIRED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isIsWired() <em>Is Wired</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isIsWired()
	 * @generated
	 * @ordered
	 */
	protected boolean isWired = IS_WIRED_EDEFAULT;

	/**
	 * The default value of the '{@link #getRegistration() <em>Registration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRegistration()
	 * @generated
	 * @ordered
	 */
	protected static final PortRegistrationType REGISTRATION_EDEFAULT = PortRegistrationType.AUTOMATIC;

	/**
	 * The cached value of the '{@link #getRegistration() <em>Registration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRegistration()
	 * @generated
	 * @ordered
	 */
	protected PortRegistrationType registration = REGISTRATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getRegistrationOverride() <em>Registration Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRegistrationOverride()
	 * @generated
	 * @ordered
	 */
	protected static final String REGISTRATION_OVERRIDE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRegistrationOverride() <em>Registration Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRegistrationOverride()
	 * @generated
	 * @ordered
	 */
	protected String registrationOverride = REGISTRATION_OVERRIDE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RTPortImpl() {
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
		return UMLRealTimePackage.Literals.RT_PORT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Port getBase_Port() {
		if (base_Port != null && base_Port.eIsProxy()) {
			InternalEObject oldBase_Port = (InternalEObject) base_Port;
			base_Port = (Port) eResolveProxy(oldBase_Port);
			if (base_Port != oldBase_Port) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UMLRealTimePackage.RT_PORT__BASE_PORT, oldBase_Port, base_Port));
				}
			}
		}
		return base_Port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Port basicGetBase_Port() {
		return base_Port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setBase_Port(Port newBase_Port) {
		Port oldBase_Port = base_Port;
		base_Port = newBase_Port;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_PORT__BASE_PORT, oldBase_Port, base_Port));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isIsConjugate() {
		return isConjugate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setIsConjugate(boolean newIsConjugate) {
		boolean oldIsConjugate = isConjugate;
		isConjugate = newIsConjugate;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_PORT__IS_CONJUGATE, oldIsConjugate, isConjugate));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isIsNotification() {
		return isNotification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setIsNotification(boolean newIsNotification) {
		boolean oldIsNotification = isNotification;
		isNotification = newIsNotification;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_PORT__IS_NOTIFICATION, oldIsNotification, isNotification));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isIsPublish() {
		return isPublish;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setIsPublish(boolean newIsPublish) {
		boolean oldIsPublish = isPublish;
		isPublish = newIsPublish;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_PORT__IS_PUBLISH, oldIsPublish, isPublish));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isIsWired() {
		return isWired;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setIsWired(boolean newIsWired) {
		boolean oldIsWired = isWired;
		isWired = newIsWired;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_PORT__IS_WIRED, oldIsWired, isWired));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public PortRegistrationType getRegistration() {
		return registration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRegistration(PortRegistrationType newRegistration) {
		PortRegistrationType oldRegistration = registration;
		registration = newRegistration == null ? REGISTRATION_EDEFAULT : newRegistration;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_PORT__REGISTRATION, oldRegistration, registration));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getRegistrationOverride() {
		return registrationOverride;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRegistrationOverride(String newRegistrationOverride) {
		String oldRegistrationOverride = registrationOverride;
		registrationOverride = newRegistrationOverride;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_PORT__REGISTRATION_OVERRIDE, oldRegistrationOverride, registrationOverride));
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
		case UMLRealTimePackage.RT_PORT__BASE_PORT:
			if (resolve) {
				return getBase_Port();
			}
			return basicGetBase_Port();
		case UMLRealTimePackage.RT_PORT__IS_CONJUGATE:
			return isIsConjugate();
		case UMLRealTimePackage.RT_PORT__IS_NOTIFICATION:
			return isIsNotification();
		case UMLRealTimePackage.RT_PORT__IS_PUBLISH:
			return isIsPublish();
		case UMLRealTimePackage.RT_PORT__IS_WIRED:
			return isIsWired();
		case UMLRealTimePackage.RT_PORT__REGISTRATION:
			return getRegistration();
		case UMLRealTimePackage.RT_PORT__REGISTRATION_OVERRIDE:
			return getRegistrationOverride();
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
		case UMLRealTimePackage.RT_PORT__BASE_PORT:
			setBase_Port((Port) newValue);
			return;
		case UMLRealTimePackage.RT_PORT__IS_CONJUGATE:
			setIsConjugate((Boolean) newValue);
			return;
		case UMLRealTimePackage.RT_PORT__IS_NOTIFICATION:
			setIsNotification((Boolean) newValue);
			return;
		case UMLRealTimePackage.RT_PORT__IS_PUBLISH:
			setIsPublish((Boolean) newValue);
			return;
		case UMLRealTimePackage.RT_PORT__IS_WIRED:
			setIsWired((Boolean) newValue);
			return;
		case UMLRealTimePackage.RT_PORT__REGISTRATION:
			setRegistration((PortRegistrationType) newValue);
			return;
		case UMLRealTimePackage.RT_PORT__REGISTRATION_OVERRIDE:
			setRegistrationOverride((String) newValue);
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
		case UMLRealTimePackage.RT_PORT__BASE_PORT:
			setBase_Port((Port) null);
			return;
		case UMLRealTimePackage.RT_PORT__IS_CONJUGATE:
			setIsConjugate(IS_CONJUGATE_EDEFAULT);
			return;
		case UMLRealTimePackage.RT_PORT__IS_NOTIFICATION:
			setIsNotification(IS_NOTIFICATION_EDEFAULT);
			return;
		case UMLRealTimePackage.RT_PORT__IS_PUBLISH:
			setIsPublish(IS_PUBLISH_EDEFAULT);
			return;
		case UMLRealTimePackage.RT_PORT__IS_WIRED:
			setIsWired(IS_WIRED_EDEFAULT);
			return;
		case UMLRealTimePackage.RT_PORT__REGISTRATION:
			setRegistration(REGISTRATION_EDEFAULT);
			return;
		case UMLRealTimePackage.RT_PORT__REGISTRATION_OVERRIDE:
			setRegistrationOverride(REGISTRATION_OVERRIDE_EDEFAULT);
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
		case UMLRealTimePackage.RT_PORT__BASE_PORT:
			return base_Port != null;
		case UMLRealTimePackage.RT_PORT__IS_CONJUGATE:
			return isConjugate != IS_CONJUGATE_EDEFAULT;
		case UMLRealTimePackage.RT_PORT__IS_NOTIFICATION:
			return isNotification != IS_NOTIFICATION_EDEFAULT;
		case UMLRealTimePackage.RT_PORT__IS_PUBLISH:
			return isPublish != IS_PUBLISH_EDEFAULT;
		case UMLRealTimePackage.RT_PORT__IS_WIRED:
			return isWired != IS_WIRED_EDEFAULT;
		case UMLRealTimePackage.RT_PORT__REGISTRATION:
			return registration != REGISTRATION_EDEFAULT;
		case UMLRealTimePackage.RT_PORT__REGISTRATION_OVERRIDE:
			return REGISTRATION_OVERRIDE_EDEFAULT == null ? registrationOverride != null : !REGISTRATION_OVERRIDE_EDEFAULT.equals(registrationOverride);
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
		result.append(" (isConjugate: ");
		result.append(isConjugate);
		result.append(", isNotification: ");
		result.append(isNotification);
		result.append(", isPublish: ");
		result.append(isPublish);
		result.append(", isWired: ");
		result.append(isWired);
		result.append(", registration: ");
		result.append(registration);
		result.append(", registrationOverride: ");
		result.append(registrationOverride);
		result.append(')');
		return result.toString();
	}

} // RTPortImpl
