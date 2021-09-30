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

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ArtifactProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;

import org.eclipse.uml2.uml.Artifact;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Artifact Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ArtifactPropertiesImpl#getBase_Artifact <em>Base Artifact</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ArtifactPropertiesImpl#getIncludeFile <em>Include File</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.ArtifactPropertiesImpl#getSourceFile <em>Source File</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArtifactPropertiesImpl extends MinimalEObjectImpl.Container implements ArtifactProperties {
	/**
	 * The cached value of the '{@link #getBase_Artifact() <em>Base Artifact</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_Artifact()
	 * @generated
	 * @ordered
	 */
	protected Artifact base_Artifact;

	/**
	 * The default value of the '{@link #getIncludeFile() <em>Include File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludeFile()
	 * @generated
	 * @ordered
	 */
	protected static final String INCLUDE_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIncludeFile() <em>Include File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludeFile()
	 * @generated
	 * @ordered
	 */
	protected String includeFile = INCLUDE_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSourceFile() <em>Source File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceFile()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourceFile() <em>Source File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceFile()
	 * @generated
	 * @ordered
	 */
	protected String sourceFile = SOURCE_FILE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArtifactPropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.ARTIFACT_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Artifact getBase_Artifact() {
		if (base_Artifact != null && base_Artifact.eIsProxy()) {
			InternalEObject oldBase_Artifact = (InternalEObject)base_Artifact;
			base_Artifact = (Artifact)eResolveProxy(oldBase_Artifact);
			if (base_Artifact != oldBase_Artifact) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RTCppPropertiesPackage.ARTIFACT_PROPERTIES__BASE_ARTIFACT, oldBase_Artifact, base_Artifact));
			}
		}
		return base_Artifact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Artifact basicGetBase_Artifact() {
		return base_Artifact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase_Artifact(Artifact newBase_Artifact) {
		Artifact oldBase_Artifact = base_Artifact;
		base_Artifact = newBase_Artifact;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.ARTIFACT_PROPERTIES__BASE_ARTIFACT, oldBase_Artifact, base_Artifact));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIncludeFile() {
		return includeFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIncludeFile(String newIncludeFile) {
		String oldIncludeFile = includeFile;
		includeFile = newIncludeFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.ARTIFACT_PROPERTIES__INCLUDE_FILE, oldIncludeFile, includeFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSourceFile() {
		return sourceFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceFile(String newSourceFile) {
		String oldSourceFile = sourceFile;
		sourceFile = newSourceFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.ARTIFACT_PROPERTIES__SOURCE_FILE, oldSourceFile, sourceFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES__BASE_ARTIFACT:
				if (resolve) return getBase_Artifact();
				return basicGetBase_Artifact();
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES__INCLUDE_FILE:
				return getIncludeFile();
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES__SOURCE_FILE:
				return getSourceFile();
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
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES__BASE_ARTIFACT:
				setBase_Artifact((Artifact)newValue);
				return;
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES__INCLUDE_FILE:
				setIncludeFile((String)newValue);
				return;
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES__SOURCE_FILE:
				setSourceFile((String)newValue);
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
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES__BASE_ARTIFACT:
				setBase_Artifact((Artifact)null);
				return;
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES__INCLUDE_FILE:
				setIncludeFile(INCLUDE_FILE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES__SOURCE_FILE:
				setSourceFile(SOURCE_FILE_EDEFAULT);
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
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES__BASE_ARTIFACT:
				return base_Artifact != null;
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES__INCLUDE_FILE:
				return INCLUDE_FILE_EDEFAULT == null ? includeFile != null : !INCLUDE_FILE_EDEFAULT.equals(includeFile);
			case RTCppPropertiesPackage.ARTIFACT_PROPERTIES__SOURCE_FILE:
				return SOURCE_FILE_EDEFAULT == null ? sourceFile != null : !SOURCE_FILE_EDEFAULT.equals(sourceFile);
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
		result.append(" (includeFile: ");
		result.append(includeFile);
		result.append(", sourceFile: ");
		result.append(sourceFile);
		result.append(')');
		return result.toString();
	}

} //ArtifactPropertiesImpl
