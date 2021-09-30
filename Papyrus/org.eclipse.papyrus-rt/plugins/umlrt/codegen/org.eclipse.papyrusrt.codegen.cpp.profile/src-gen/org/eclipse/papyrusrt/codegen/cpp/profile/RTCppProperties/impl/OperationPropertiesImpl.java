/**
 * Copyright (c) 2014 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationKind;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.OperationProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;

import org.eclipse.uml2.uml.Operation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.OperationPropertiesImpl#isInline <em>Inline</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.OperationPropertiesImpl#isPolymorphic <em>Polymorphic</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.OperationPropertiesImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.OperationPropertiesImpl#isGenerateDefinition <em>Generate Definition</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.OperationPropertiesImpl#getBase_Operation <em>Base Operation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationPropertiesImpl extends MinimalEObjectImpl.Container implements OperationProperties {
	/**
	 * The default value of the '{@link #isInline() <em>Inline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInline()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INLINE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInline() <em>Inline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInline()
	 * @generated
	 * @ordered
	 */
	protected boolean inline = INLINE_EDEFAULT;

	/**
	 * The default value of the '{@link #isPolymorphic() <em>Polymorphic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPolymorphic()
	 * @generated
	 * @ordered
	 */
	protected static final boolean POLYMORPHIC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPolymorphic() <em>Polymorphic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPolymorphic()
	 * @generated
	 * @ordered
	 */
	protected boolean polymorphic = POLYMORPHIC_EDEFAULT;

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final OperationKind KIND_EDEFAULT = OperationKind.MEMBER;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected OperationKind kind = KIND_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateDefinition() <em>Generate Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateDefinition()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_DEFINITION_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerateDefinition() <em>Generate Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateDefinition()
	 * @generated
	 * @ordered
	 */
	protected boolean generateDefinition = GENERATE_DEFINITION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBase_Operation() <em>Base Operation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_Operation()
	 * @generated
	 * @ordered
	 */
	protected Operation base_Operation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationPropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.OPERATION_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInline() {
		return inline;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInline(boolean newInline) {
		boolean oldInline = inline;
		inline = newInline;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.OPERATION_PROPERTIES__INLINE, oldInline, inline));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPolymorphic() {
		return polymorphic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPolymorphic(boolean newPolymorphic) {
		boolean oldPolymorphic = polymorphic;
		polymorphic = newPolymorphic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.OPERATION_PROPERTIES__POLYMORPHIC, oldPolymorphic, polymorphic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationKind getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKind(OperationKind newKind) {
		OperationKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.OPERATION_PROPERTIES__KIND, oldKind, kind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateDefinition() {
		return generateDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateDefinition(boolean newGenerateDefinition) {
		boolean oldGenerateDefinition = generateDefinition;
		generateDefinition = newGenerateDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.OPERATION_PROPERTIES__GENERATE_DEFINITION, oldGenerateDefinition, generateDefinition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operation getBase_Operation() {
		if (base_Operation != null && base_Operation.eIsProxy()) {
			InternalEObject oldBase_Operation = (InternalEObject)base_Operation;
			base_Operation = (Operation)eResolveProxy(oldBase_Operation);
			if (base_Operation != oldBase_Operation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RTCppPropertiesPackage.OPERATION_PROPERTIES__BASE_OPERATION, oldBase_Operation, base_Operation));
			}
		}
		return base_Operation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operation basicGetBase_Operation() {
		return base_Operation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase_Operation(Operation newBase_Operation) {
		Operation oldBase_Operation = base_Operation;
		base_Operation = newBase_Operation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.OPERATION_PROPERTIES__BASE_OPERATION, oldBase_Operation, base_Operation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__INLINE:
				return isInline();
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__POLYMORPHIC:
				return isPolymorphic();
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__KIND:
				return getKind();
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__GENERATE_DEFINITION:
				return isGenerateDefinition();
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__BASE_OPERATION:
				if (resolve) return getBase_Operation();
				return basicGetBase_Operation();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__INLINE:
				setInline((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__POLYMORPHIC:
				setPolymorphic((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__KIND:
				setKind((OperationKind)newValue);
				return;
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__GENERATE_DEFINITION:
				setGenerateDefinition((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__BASE_OPERATION:
				setBase_Operation((Operation)newValue);
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
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__INLINE:
				setInline(INLINE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__POLYMORPHIC:
				setPolymorphic(POLYMORPHIC_EDEFAULT);
				return;
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__GENERATE_DEFINITION:
				setGenerateDefinition(GENERATE_DEFINITION_EDEFAULT);
				return;
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__BASE_OPERATION:
				setBase_Operation((Operation)null);
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
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__INLINE:
				return inline != INLINE_EDEFAULT;
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__POLYMORPHIC:
				return polymorphic != POLYMORPHIC_EDEFAULT;
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__KIND:
				return kind != KIND_EDEFAULT;
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__GENERATE_DEFINITION:
				return generateDefinition != GENERATE_DEFINITION_EDEFAULT;
			case RTCppPropertiesPackage.OPERATION_PROPERTIES__BASE_OPERATION:
				return base_Operation != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (Inline: ");
		result.append(inline);
		result.append(", Polymorphic: ");
		result.append(polymorphic);
		result.append(", kind: ");
		result.append(kind);
		result.append(", generateDefinition: ");
		result.append(generateDefinition);
		result.append(')');
		return result.toString();
	}

} //OperationPropertiesImpl
