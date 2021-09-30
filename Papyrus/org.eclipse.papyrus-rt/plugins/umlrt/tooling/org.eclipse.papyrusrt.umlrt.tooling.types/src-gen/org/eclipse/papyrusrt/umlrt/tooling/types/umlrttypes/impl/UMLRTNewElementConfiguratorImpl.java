/**
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
 */
package org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.impl.RuntimeValuesAdviceConfigurationImpl;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTNewElementConfigurator;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>UMLRT
 * New Element Configurator</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTNewElementConfiguratorImpl#getDialogTitlePattern <em>Dialog Title Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTNewElementConfiguratorImpl extends RuntimeValuesAdviceConfigurationImpl
		implements UMLRTNewElementConfigurator {
	/**
	 * The default value of the '{@link #getDialogTitlePattern() <em>Dialog
	 * Title Pattern</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #getDialogTitlePattern()
	 * @generated
	 * @ordered
	 */
	protected static final String DIALOG_TITLE_PATTERN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDialogTitlePattern() <em>Dialog Title Pattern</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getDialogTitlePattern()
	 * @generated
	 * @ordered
	 */
	protected String dialogTitlePattern = DIALOG_TITLE_PATTERN_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTNewElementConfiguratorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTTypesPackage.Literals.UMLRT_NEW_ELEMENT_CONFIGURATOR;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getDialogTitlePattern() {
		return dialogTitlePattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setDialogTitlePattern(String newDialogTitlePattern) {
		String oldDialogTitlePattern = dialogTitlePattern;
		dialogTitlePattern = newDialogTitlePattern;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRTTypesPackage.UMLRT_NEW_ELEMENT_CONFIGURATOR__DIALOG_TITLE_PATTERN, oldDialogTitlePattern, dialogTitlePattern));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UMLRTTypesPackage.UMLRT_NEW_ELEMENT_CONFIGURATOR__DIALOG_TITLE_PATTERN:
			return getDialogTitlePattern();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLRTTypesPackage.UMLRT_NEW_ELEMENT_CONFIGURATOR__DIALOG_TITLE_PATTERN:
			setDialogTitlePattern((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLRTTypesPackage.UMLRT_NEW_ELEMENT_CONFIGURATOR__DIALOG_TITLE_PATTERN:
			setDialogTitlePattern(DIALOG_TITLE_PATTERN_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLRTTypesPackage.UMLRT_NEW_ELEMENT_CONFIGURATOR__DIALOG_TITLE_PATTERN:
			return DIALOG_TITLE_PATTERN_EDEFAULT == null ? dialogTitlePattern != null : !DIALOG_TITLE_PATTERN_EDEFAULT.equals(dialogTitlePattern);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (dialogTitlePattern: "); //$NON-NLS-1$
		result.append(dialogTitlePattern);
		result.append(')');
		return result.toString();
	}

} // UMLRTNewElementConfiguratorImpl
