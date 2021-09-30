/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.NotificationForwarder;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * UML-RT semantics for {@link RTPort}.
 */
public class RTPortRTImpl extends org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.impl.RTPortImpl implements InternalUMLRTElement {

	private static final int IS_NOTIFICATION__SET_FLAG = 0x1 << 0;
	private static final int IS_PUBLISH__SET_FLAG = 0x1 << 1;
	private static final int IS_WIRED__SET_FLAG = 0x1 << 2;
	private static final int REGISTRATION__SET_FLAG = 0x1 << 3;
	private static final int REGISTRATION_OVERRIDE__SET_FLAG = 0x1 << 4;

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLRealTimePackage.Literals.RT_PORT__IS_NOTIFICATION,
			UMLRealTimePackage.Literals.RT_PORT__IS_PUBLISH,
			UMLRealTimePackage.Literals.RT_PORT__IS_WIRED,
			UMLRealTimePackage.Literals.RT_PORT__REGISTRATION,
			UMLRealTimePackage.Literals.RT_PORT__REGISTRATION_OVERRIDE));

	private int uFlags = 0x0;

	protected RTPortRTImpl() {
		super();
	}

	@Override
	public EObject create(EClass eClass) {
		EPackage ePackage = eClass.getEPackage();
		return (ePackage == UMLPackage.eINSTANCE)
				? UMLRTUMLFactoryImpl.eINSTANCE.create(eClass)
				: (ePackage == UMLRealTimePackage.eINSTANCE)
						? UMLRTUMLRealTimeFactoryImpl.eINSTANCE.create(eClass)
						: ePackage.getEFactoryInstance().create(eClass);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLRealTimePackage.RT_PORT__IS_NOTIFICATION:
			return isSetNotification();
		case UMLRealTimePackage.RT_PORT__IS_PUBLISH:
			return isSetPublish();
		case UMLRealTimePackage.RT_PORT__IS_WIRED:
			return isSetWired();
		case UMLRealTimePackage.RT_PORT__REGISTRATION:
			return isSetRegistration();
		case UMLRealTimePackage.RT_PORT__REGISTRATION_OVERRIDE:
			return isSetRegistrationOverride();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLRealTimePackage.RT_PORT__IS_NOTIFICATION:
			return (T) (Boolean) super.isNotification();
		case UMLRealTimePackage.RT_PORT__IS_PUBLISH:
			return (T) (Boolean) super.isPublish();
		case UMLRealTimePackage.RT_PORT__IS_WIRED:
			return (T) (Boolean) super.isWired();
		case UMLRealTimePackage.RT_PORT__REGISTRATION:
			return (T) super.getRegistration();
		case UMLRealTimePackage.RT_PORT__REGISTRATION_OVERRIDE:
			return (T) super.getRegistrationOverride();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLRealTimePackage.RT_PORT__IS_NOTIFICATION:
			unsetIsNotification();
			break;
		case UMLRealTimePackage.RT_PORT__IS_PUBLISH:
			unsetIsPublish();
			break;
		case UMLRealTimePackage.RT_PORT__IS_WIRED:
			unsetIsWired();
			break;
		case UMLRealTimePackage.RT_PORT__REGISTRATION:
			unsetRegistration();
			break;
		case UMLRealTimePackage.RT_PORT__REGISTRATION_OVERRIDE:
			unsetRegistrationOverride();
			break;
		default:
			super.eUnset(featureID);
			break;
		}
	}

	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLRealTimePackage.RT_PORT__IS_NOTIFICATION:
			if (newValue == null) {
				unsetIsNotification();
			} else {
				setIsNotification((Boolean) newValue);
			}
			break;
		case UMLRealTimePackage.RT_PORT__IS_PUBLISH:
			if (newValue == null) {
				unsetIsPublish();
			} else {
				setIsPublish((Boolean) newValue);
			}
			break;
		case UMLRealTimePackage.RT_PORT__IS_WIRED:
			if (newValue == null) {
				unsetIsWired();
			} else {
				setIsWired((Boolean) newValue);
			}
			break;
		case UMLRealTimePackage.RT_PORT__REGISTRATION:
			if (newValue == null) {
				unsetRegistration();
			} else {
				setRegistration((PortRegistrationType) newValue);
			}
			break;
		case UMLRealTimePackage.RT_PORT__REGISTRATION_OVERRIDE:
			setRegistrationOverride((String) newValue);
			break;
		default:
			super.eSet(featureID, newValue);
			break;
		}
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof RTPort)) {
			throw new IllegalArgumentException("not an rtPort: " + redefined); //$NON-NLS-1$
		}

		RTPort rtPort = (RTPort) redefined;
		Port port = rtPort.getBase_Port();
		Port base = getBase_Port();
		if ((port instanceof InternalUMLRTElement) && (base instanceof InternalUMLRTElement)) {
			((InternalUMLRTElement) base).umlSetRedefinedElement((InternalUMLRTElement) port);
		}
	}

	@Override
	public <R extends InternalUMLRTElement> R rtGetRedefinedElement() {
		R result = null;

		Port base = getBase_Port();
		if (base instanceof InternalUMLRTElement) {
			Port redefined = ((InternalUMLRTElement) base).rtGetRedefinedElement();
			if (redefined != null) {
				RTPort redefinedRTPort = UMLUtil.getStereotypeApplication(redefined, RTPort.class);
				if (redefinedRTPort instanceof InternalUMLRTElement) {
					@SuppressWarnings("unchecked")
					R redefinedR = (R) redefinedRTPort;
					result = redefinedR;
				}
			}
		}

		return result;
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	void handleRedefinedPort(Port port) {
		if (port != null) {
			NotificationForwarder.adapt(this, () -> new NotificationForwarder.FromStereotype(this,
					UMLRealTimePackage.Literals.RT_PORT__BASE_PORT,
					UMLPackage.Literals.PORT__REDEFINED_PORT,
					rtInheritedFeatures()));
		} else if (eBasicHasAdapters()) {
			NotificationForwarder.unadapt(this);
		}
	}

	@Override
	public void rtReify() {
		Port base = getBase_Port();
		if (base instanceof InternalUMLRTElement) {
			((InternalUMLRTElement) base).rtReify();
		}
	}

	@Override
	public void rtVirtualize() {
		Port base = getBase_Port();
		if (base instanceof InternalUMLRTElement) {
			((InternalUMLRTElement) base).rtVirtualize();
		}
	}

	@Override
	public void rtUnsetAll() {
		unsetIsNotification();
		unsetIsPublish();
		unsetIsWired();
		unsetRegistration();
		unsetRegistrationOverride();
	}

	@Override
	public void setBase_Port(Port newBase_Port) {
		super.setBase_Port(newBase_Port);

		if (newBase_Port instanceof InternalUMLRTElement) {
			Port redefined = (Port) ((InternalUMLRTElement) newBase_Port).rtGetRedefinedElement();
			handleRedefinedPort(redefined);
		} else {
			handleRedefinedPort(null);
		}
	}

	public boolean isSetNotification() {
		return (uFlags & IS_NOTIFICATION__SET_FLAG) != 0;
	}

	@Override
	public boolean isNotification() {
		return inheritFeature(UMLRealTimePackage.Literals.RT_PORT__IS_NOTIFICATION);
	}

	@Override
	public void setIsNotification(boolean newIsNotification) {
		boolean wasSet = isSetNotification();
		boolean oldIsNotification = isNotification();

		isNotification = isNotification();
		uFlags = uFlags | IS_NOTIFICATION__SET_FLAG;
		isNotification = newIsNotification;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_PORT__IS_NOTIFICATION, oldIsNotification, isNotification, !wasSet));
	}

	public void unsetIsNotification() {
		boolean wasSet = isSetNotification();
		boolean oldIsNotification = isNotification();

		isNotification = IS_NOTIFICATION_EDEFAULT;
		uFlags = uFlags & ~IS_NOTIFICATION__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLRealTimePackage.RT_PORT__IS_NOTIFICATION, oldIsNotification, isNotification(), wasSet));
		}
	}

	public boolean isSetPublish() {
		return (uFlags & IS_PUBLISH__SET_FLAG) != 0;
	}

	@Override
	public boolean isPublish() {
		return inheritFeature(UMLRealTimePackage.Literals.RT_PORT__IS_PUBLISH);
	}

	@Override
	public void setIsPublish(boolean newIsPublish) {
		boolean wasSet = isSetPublish();
		boolean oldIsPublish = isPublish;

		uFlags = uFlags | IS_PUBLISH__SET_FLAG;
		isPublish = newIsPublish;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_PORT__IS_PUBLISH, oldIsPublish, isPublish, !wasSet));
	}

	public void unsetIsPublish() {
		boolean wasSet = isSetPublish();
		boolean oldIsPublish = isPublish;

		isPublish = IS_PUBLISH_EDEFAULT;
		uFlags = uFlags & ~IS_PUBLISH__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLRealTimePackage.RT_PORT__IS_PUBLISH, oldIsPublish, isPublish(), wasSet));
		}
	}

	public boolean isSetWired() {
		return (uFlags & IS_WIRED__SET_FLAG) != 0;
	}

	@Override
	public boolean isWired() {
		return inheritFeature(UMLRealTimePackage.Literals.RT_PORT__IS_WIRED);
	}

	@Override
	public void setIsWired(boolean newIsWired) {
		boolean wasSet = isSetWired();
		boolean oldIsWired = isWired();

		uFlags = uFlags | IS_WIRED__SET_FLAG;
		isWired = newIsWired;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_PORT__IS_WIRED, oldIsWired, isWired, !wasSet));
	}

	public void unsetIsWired() {
		boolean wasSet = isSetWired();
		boolean oldIsWired = isWired();

		isWired = IS_WIRED_EDEFAULT;
		uFlags = uFlags & ~IS_WIRED__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLRealTimePackage.RT_PORT__IS_WIRED, oldIsWired, isWired(), wasSet));
		}
	}

	public boolean isSetRegistration() {
		return (uFlags & REGISTRATION__SET_FLAG) != 0;
	}

	@Override
	public PortRegistrationType getRegistration() {
		return inheritFeature(UMLRealTimePackage.Literals.RT_PORT__REGISTRATION);
	}

	@Override
	public void setRegistration(PortRegistrationType newRegistration) {
		boolean wasSet = isSetRegistration();
		PortRegistrationType oldRegistration = registration;

		uFlags = uFlags | REGISTRATION__SET_FLAG;
		registration = newRegistration == null ? REGISTRATION_EDEFAULT : newRegistration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_PORT__REGISTRATION, oldRegistration, registration, !wasSet));
	}

	public void unsetRegistration() {
		boolean wasSet = isSetRegistration();
		PortRegistrationType oldRegistration = getRegistration();

		registration = REGISTRATION_EDEFAULT;
		uFlags = uFlags & ~REGISTRATION__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLRealTimePackage.RT_PORT__REGISTRATION, oldRegistration, getRegistration(), wasSet));
		}
	}

	public boolean isSetRegistrationOverride() {
		return (uFlags & REGISTRATION_OVERRIDE__SET_FLAG) != 0;
	}

	@Override
	public String getRegistrationOverride() {
		return inheritFeature(UMLRealTimePackage.Literals.RT_PORT__REGISTRATION_OVERRIDE);
	}

	@Override
	public void setRegistrationOverride(String newRegistrationOverride) {
		boolean wasSet = isSetRegistrationOverride();
		String oldRegistrationOverride = registrationOverride;

		registrationOverride = getRegistrationOverride();
		uFlags = uFlags | REGISTRATION_OVERRIDE__SET_FLAG;
		registrationOverride = newRegistrationOverride;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_PORT__REGISTRATION_OVERRIDE, oldRegistrationOverride, registrationOverride, !wasSet));
	}

	public void unsetRegistrationOverride() {
		boolean wasSet = isSetRegistrationOverride();
		String oldRegistrationOverride = registrationOverride;

		registrationOverride = REGISTRATION_OVERRIDE_EDEFAULT;
		uFlags = uFlags & ~REGISTRATION_OVERRIDE__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRealTimePackage.RT_PORT__REGISTRATION_OVERRIDE, oldRegistrationOverride, getRegistrationOverride(), wasSet));
		}
	}

}
