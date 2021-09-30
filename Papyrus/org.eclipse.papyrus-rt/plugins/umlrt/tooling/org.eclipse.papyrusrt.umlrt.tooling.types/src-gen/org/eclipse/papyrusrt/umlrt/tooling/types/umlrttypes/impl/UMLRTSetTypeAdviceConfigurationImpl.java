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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.ViewToDisplay;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.types.impl.AbstractAdviceBindingConfigurationImpl;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration;
import org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>UMLRT Set Type Advice Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTSetTypeAdviceConfigurationImpl#getElementType <em>Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTSetTypeAdviceConfigurationImpl#getNewTypeViewsToDisplay <em>New Type Views To Display</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTSetTypeAdviceConfigurationImpl#getNewTypeViews <em>New Type Views</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.impl.UMLRTSetTypeAdviceConfigurationImpl#getElementTypeLabel <em>Element Type Label</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTSetTypeAdviceConfigurationImpl extends AbstractAdviceBindingConfigurationImpl implements UMLRTSetTypeAdviceConfiguration {
	/**
	 * The default value of the '{@link #getElementType() <em>Element Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected static final String ELEMENT_TYPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getElementType() <em>Element Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected String elementType = ELEMENT_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNewTypeViewsToDisplay() <em>New Type Views To Display</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getNewTypeViewsToDisplay()
	 * @generated
	 * @ordered
	 */
	protected EList<ViewToDisplay> newTypeViewsToDisplay;

	/**
	 * The default value of the '{@link #getElementTypeLabel() <em>Element Type Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getElementTypeLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String ELEMENT_TYPE_LABEL_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getElementTypeLabel() <em>Element Type Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getElementTypeLabel()
	 * @generated
	 * @ordered
	 */
	protected String elementTypeLabel = ELEMENT_TYPE_LABEL_EDEFAULT;
	/**
	 * This is true if the Element Type Label attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected boolean elementTypeLabelESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTSetTypeAdviceConfigurationImpl() {
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
		return UMLRTTypesPackage.Literals.UMLRT_SET_TYPE_ADVICE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getElementType() {
		return elementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void setElementTypeGen(String newElementType) {
		String oldElementType = elementType;
		elementType = newElementType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE, oldElementType, elementType));
		}
	}

	/**
	 * Triggers notification of update to the {@link #getElementTypeLabel() elementTypeLabel}
	 * when it is unset and therefore derived from the element type.
	 */
	@Override
	public void setElementType(String value) {
		if (isSetElementTypeLabel()) {
			// Simple case. Just proceed
			setElementTypeGen(value);
		} else {
			// The derived label will be affected, too
			String oldLabel = getElementTypeLabel();
			setElementTypeGen(value);
			String newLabel = getElementTypeLabel();
			if (eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.UNSET, UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_LABEL, oldLabel, newLabel, false));
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<ViewToDisplay> getNewTypeViewsToDisplay() {
		if (newTypeViewsToDisplay == null) {
			newTypeViewsToDisplay = new EObjectContainmentEList<>(ViewToDisplay.class, this, UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS_TO_DISPLAY);
		}
		return newTypeViewsToDisplay;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public EList<View> getNewTypeViews() {
		EList<View> result = new UniqueEList.FastCompare<>(getNewTypeViewsToDisplay().size());
		for (ViewToDisplay next : getNewTypeViewsToDisplay()) {
			View nextView = next.getView();
			if ((nextView != null) && !result.contains(nextView)) {
				result.add(nextView);
			}
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String getElementTypeLabelGen() {
		return elementTypeLabel;
	}

	/**
	 * Implements the computation of the derived value for unset state.
	 */
	@Override
	public String getElementTypeLabel() {
		String result;

		if (isSetElementTypeLabel()) {
			result = getElementTypeLabelGen();
		} else {
			IElementType type = (getElementType() != null)
					? ElementTypeRegistry.getInstance().getType(getElementType())
					: null;
			result = (type != null) ? type.getDisplayName() : null;
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setElementTypeLabel(String newElementTypeLabel) {
		String oldElementTypeLabel = elementTypeLabel;
		elementTypeLabel = newElementTypeLabel;
		boolean oldElementTypeLabelESet = elementTypeLabelESet;
		elementTypeLabelESet = true;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_LABEL, oldElementTypeLabel, elementTypeLabel, !oldElementTypeLabelESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetElementTypeLabel() {
		String oldElementTypeLabel = elementTypeLabel;
		boolean oldElementTypeLabelESet = elementTypeLabelESet;
		elementTypeLabel = ELEMENT_TYPE_LABEL_EDEFAULT;
		elementTypeLabelESet = false;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_LABEL, oldElementTypeLabel, ELEMENT_TYPE_LABEL_EDEFAULT, oldElementTypeLabelESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetElementTypeLabel() {
		return elementTypeLabelESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS_TO_DISPLAY:
			return ((InternalEList<?>) getNewTypeViewsToDisplay()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE:
			return getElementType();
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS_TO_DISPLAY:
			return getNewTypeViewsToDisplay();
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS:
			return getNewTypeViews();
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_LABEL:
			return getElementTypeLabel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE:
			setElementType((String) newValue);
			return;
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS_TO_DISPLAY:
			getNewTypeViewsToDisplay().clear();
			getNewTypeViewsToDisplay().addAll((Collection<? extends ViewToDisplay>) newValue);
			return;
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_LABEL:
			setElementTypeLabel((String) newValue);
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
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE:
			setElementType(ELEMENT_TYPE_EDEFAULT);
			return;
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS_TO_DISPLAY:
			getNewTypeViewsToDisplay().clear();
			return;
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_LABEL:
			unsetElementTypeLabel();
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
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE:
			return ELEMENT_TYPE_EDEFAULT == null ? elementType != null : !ELEMENT_TYPE_EDEFAULT.equals(elementType);
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS_TO_DISPLAY:
			return newTypeViewsToDisplay != null && !newTypeViewsToDisplay.isEmpty();
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__NEW_TYPE_VIEWS:
			return !getNewTypeViews().isEmpty();
		case UMLRTTypesPackage.UMLRT_SET_TYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_LABEL:
			return isSetElementTypeLabel();
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
		result.append(" (elementType: "); //$NON-NLS-1$
		result.append(elementType);
		result.append(", elementTypeLabel: "); //$NON-NLS-1$
		if (elementTypeLabelESet) {
			result.append(elementTypeLabel);
		} else {
			result.append("<unset>"); //$NON-NLS-1$
		}
		result.append(')');
		return result.toString();
	}

} // UMLRTSetTypeAdviceConfigurationImpl
