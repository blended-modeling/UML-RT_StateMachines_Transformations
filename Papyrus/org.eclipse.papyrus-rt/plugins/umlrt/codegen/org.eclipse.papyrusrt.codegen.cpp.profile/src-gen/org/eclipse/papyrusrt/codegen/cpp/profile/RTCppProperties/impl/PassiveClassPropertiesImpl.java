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

import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassKind;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.ClassProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.CppFileProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.FileGenerationProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.GenerationProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.PassiveClassProperties;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Passive Class Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl#getHeaderPreface <em>Header Preface</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl#getHeaderEnding <em>Header Ending</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl#getImplementationPreface <em>Implementation Preface</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl#getImplementationEnding <em>Implementation Ending</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl#getBase_Class <em>Base Class</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl#getPrivateDeclarations <em>Private Declarations</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl#getProtectedDeclarations <em>Protected Declarations</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl#getPublicDeclarations <em>Public Declarations</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl#isGenerateHeader <em>Generate Header</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl#isGenerateImplementation <em>Generate Implementation</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl#isGenerate <em>Generate</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.impl.PassiveClassPropertiesImpl#getImplementationType <em>Implementation Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PassiveClassPropertiesImpl extends ClassGenerationPropertiesImpl implements PassiveClassProperties {
	/**
	 * The default value of the '{@link #getHeaderPreface() <em>Header Preface</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderPreface()
	 * @generated
	 * @ordered
	 */
	protected static final String HEADER_PREFACE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHeaderPreface() <em>Header Preface</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderPreface()
	 * @generated
	 * @ordered
	 */
	protected String headerPreface = HEADER_PREFACE_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeaderEnding() <em>Header Ending</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderEnding()
	 * @generated
	 * @ordered
	 */
	protected static final String HEADER_ENDING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHeaderEnding() <em>Header Ending</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderEnding()
	 * @generated
	 * @ordered
	 */
	protected String headerEnding = HEADER_ENDING_EDEFAULT;

	/**
	 * The default value of the '{@link #getImplementationPreface() <em>Implementation Preface</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationPreface()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_PREFACE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementationPreface() <em>Implementation Preface</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationPreface()
	 * @generated
	 * @ordered
	 */
	protected String implementationPreface = IMPLEMENTATION_PREFACE_EDEFAULT;

	/**
	 * The default value of the '{@link #getImplementationEnding() <em>Implementation Ending</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationEnding()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_ENDING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementationEnding() <em>Implementation Ending</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationEnding()
	 * @generated
	 * @ordered
	 */
	protected String implementationEnding = IMPLEMENTATION_ENDING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBase_Class() <em>Base Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase_Class()
	 * @generated
	 * @ordered
	 */
	protected org.eclipse.uml2.uml.Class base_Class;

	/**
	 * The default value of the '{@link #getPrivateDeclarations() <em>Private Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrivateDeclarations()
	 * @generated
	 * @ordered
	 */
	protected static final String PRIVATE_DECLARATIONS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPrivateDeclarations() <em>Private Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrivateDeclarations()
	 * @generated
	 * @ordered
	 */
	protected String privateDeclarations = PRIVATE_DECLARATIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #getProtectedDeclarations() <em>Protected Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtectedDeclarations()
	 * @generated
	 * @ordered
	 */
	protected static final String PROTECTED_DECLARATIONS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProtectedDeclarations() <em>Protected Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtectedDeclarations()
	 * @generated
	 * @ordered
	 */
	protected String protectedDeclarations = PROTECTED_DECLARATIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #getPublicDeclarations() <em>Public Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPublicDeclarations()
	 * @generated
	 * @ordered
	 */
	protected static final String PUBLIC_DECLARATIONS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPublicDeclarations() <em>Public Declarations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPublicDeclarations()
	 * @generated
	 * @ordered
	 */
	protected String publicDeclarations = PUBLIC_DECLARATIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateHeader() <em>Generate Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateHeader()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_HEADER_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerateHeader() <em>Generate Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateHeader()
	 * @generated
	 * @ordered
	 */
	protected boolean generateHeader = GENERATE_HEADER_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateImplementation() <em>Generate Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateImplementation()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_IMPLEMENTATION_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerateImplementation() <em>Generate Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateImplementation()
	 * @generated
	 * @ordered
	 */
	protected boolean generateImplementation = GENERATE_IMPLEMENTATION_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerate() <em>Generate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGenerate() <em>Generate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerate()
	 * @generated
	 * @ordered
	 */
	protected boolean generate = GENERATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final ClassKind KIND_EDEFAULT = ClassKind.CLASS;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected ClassKind kind = KIND_EDEFAULT;

	/**
	 * The default value of the '{@link #getImplementationType() <em>Implementation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationType()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementationType() <em>Implementation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationType()
	 * @generated
	 * @ordered
	 */
	protected String implementationType = IMPLEMENTATION_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PassiveClassPropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RTCppPropertiesPackage.Literals.PASSIVE_CLASS_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHeaderPreface() {
		return headerPreface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderPreface(String newHeaderPreface) {
		String oldHeaderPreface = headerPreface;
		headerPreface = newHeaderPreface;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_PREFACE, oldHeaderPreface, headerPreface));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHeaderEnding() {
		return headerEnding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderEnding(String newHeaderEnding) {
		String oldHeaderEnding = headerEnding;
		headerEnding = newHeaderEnding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_ENDING, oldHeaderEnding, headerEnding));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementationPreface() {
		return implementationPreface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementationPreface(String newImplementationPreface) {
		String oldImplementationPreface = implementationPreface;
		implementationPreface = newImplementationPreface;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_PREFACE, oldImplementationPreface, implementationPreface));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementationEnding() {
		return implementationEnding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementationEnding(String newImplementationEnding) {
		String oldImplementationEnding = implementationEnding;
		implementationEnding = newImplementationEnding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_ENDING, oldImplementationEnding, implementationEnding));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.eclipse.uml2.uml.Class getBase_Class() {
		if (base_Class != null && base_Class.eIsProxy()) {
			InternalEObject oldBase_Class = (InternalEObject)base_Class;
			base_Class = (org.eclipse.uml2.uml.Class)eResolveProxy(oldBase_Class);
			if (base_Class != oldBase_Class) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__BASE_CLASS, oldBase_Class, base_Class));
			}
		}
		return base_Class;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.eclipse.uml2.uml.Class basicGetBase_Class() {
		return base_Class;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase_Class(org.eclipse.uml2.uml.Class newBase_Class) {
		org.eclipse.uml2.uml.Class oldBase_Class = base_Class;
		base_Class = newBase_Class;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__BASE_CLASS, oldBase_Class, base_Class));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPrivateDeclarations() {
		return privateDeclarations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrivateDeclarations(String newPrivateDeclarations) {
		String oldPrivateDeclarations = privateDeclarations;
		privateDeclarations = newPrivateDeclarations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PRIVATE_DECLARATIONS, oldPrivateDeclarations, privateDeclarations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProtectedDeclarations() {
		return protectedDeclarations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProtectedDeclarations(String newProtectedDeclarations) {
		String oldProtectedDeclarations = protectedDeclarations;
		protectedDeclarations = newProtectedDeclarations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PROTECTED_DECLARATIONS, oldProtectedDeclarations, protectedDeclarations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPublicDeclarations() {
		return publicDeclarations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublicDeclarations(String newPublicDeclarations) {
		String oldPublicDeclarations = publicDeclarations;
		publicDeclarations = newPublicDeclarations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PUBLIC_DECLARATIONS, oldPublicDeclarations, publicDeclarations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateHeader() {
		return generateHeader;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateHeader(boolean newGenerateHeader) {
		boolean oldGenerateHeader = generateHeader;
		generateHeader = newGenerateHeader;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_HEADER, oldGenerateHeader, generateHeader));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateImplementation() {
		return generateImplementation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateImplementation(boolean newGenerateImplementation) {
		boolean oldGenerateImplementation = generateImplementation;
		generateImplementation = newGenerateImplementation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_IMPLEMENTATION, oldGenerateImplementation, generateImplementation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerate() {
		return generate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerate(boolean newGenerate) {
		boolean oldGenerate = generate;
		generate = newGenerate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE, oldGenerate, generate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassKind getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKind(ClassKind newKind) {
		ClassKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__KIND, oldKind, kind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementationType() {
		return implementationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementationType(String newImplementationType) {
		String oldImplementationType = implementationType;
		implementationType = newImplementationType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_TYPE, oldImplementationType, implementationType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_PREFACE:
				return getHeaderPreface();
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_ENDING:
				return getHeaderEnding();
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_PREFACE:
				return getImplementationPreface();
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_ENDING:
				return getImplementationEnding();
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__BASE_CLASS:
				if (resolve) return getBase_Class();
				return basicGetBase_Class();
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PRIVATE_DECLARATIONS:
				return getPrivateDeclarations();
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PROTECTED_DECLARATIONS:
				return getProtectedDeclarations();
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PUBLIC_DECLARATIONS:
				return getPublicDeclarations();
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_HEADER:
				return isGenerateHeader();
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_IMPLEMENTATION:
				return isGenerateImplementation();
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE:
				return isGenerate();
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__KIND:
				return getKind();
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_TYPE:
				return getImplementationType();
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
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_PREFACE:
				setHeaderPreface((String)newValue);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_ENDING:
				setHeaderEnding((String)newValue);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_PREFACE:
				setImplementationPreface((String)newValue);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_ENDING:
				setImplementationEnding((String)newValue);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__BASE_CLASS:
				setBase_Class((org.eclipse.uml2.uml.Class)newValue);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PRIVATE_DECLARATIONS:
				setPrivateDeclarations((String)newValue);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PROTECTED_DECLARATIONS:
				setProtectedDeclarations((String)newValue);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PUBLIC_DECLARATIONS:
				setPublicDeclarations((String)newValue);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_HEADER:
				setGenerateHeader((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_IMPLEMENTATION:
				setGenerateImplementation((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE:
				setGenerate((Boolean)newValue);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__KIND:
				setKind((ClassKind)newValue);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_TYPE:
				setImplementationType((String)newValue);
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
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_PREFACE:
				setHeaderPreface(HEADER_PREFACE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_ENDING:
				setHeaderEnding(HEADER_ENDING_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_PREFACE:
				setImplementationPreface(IMPLEMENTATION_PREFACE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_ENDING:
				setImplementationEnding(IMPLEMENTATION_ENDING_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__BASE_CLASS:
				setBase_Class((org.eclipse.uml2.uml.Class)null);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PRIVATE_DECLARATIONS:
				setPrivateDeclarations(PRIVATE_DECLARATIONS_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PROTECTED_DECLARATIONS:
				setProtectedDeclarations(PROTECTED_DECLARATIONS_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PUBLIC_DECLARATIONS:
				setPublicDeclarations(PUBLIC_DECLARATIONS_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_HEADER:
				setGenerateHeader(GENERATE_HEADER_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_IMPLEMENTATION:
				setGenerateImplementation(GENERATE_IMPLEMENTATION_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE:
				setGenerate(GENERATE_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_TYPE:
				setImplementationType(IMPLEMENTATION_TYPE_EDEFAULT);
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
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_PREFACE:
				return HEADER_PREFACE_EDEFAULT == null ? headerPreface != null : !HEADER_PREFACE_EDEFAULT.equals(headerPreface);
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_ENDING:
				return HEADER_ENDING_EDEFAULT == null ? headerEnding != null : !HEADER_ENDING_EDEFAULT.equals(headerEnding);
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_PREFACE:
				return IMPLEMENTATION_PREFACE_EDEFAULT == null ? implementationPreface != null : !IMPLEMENTATION_PREFACE_EDEFAULT.equals(implementationPreface);
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_ENDING:
				return IMPLEMENTATION_ENDING_EDEFAULT == null ? implementationEnding != null : !IMPLEMENTATION_ENDING_EDEFAULT.equals(implementationEnding);
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__BASE_CLASS:
				return base_Class != null;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PRIVATE_DECLARATIONS:
				return PRIVATE_DECLARATIONS_EDEFAULT == null ? privateDeclarations != null : !PRIVATE_DECLARATIONS_EDEFAULT.equals(privateDeclarations);
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PROTECTED_DECLARATIONS:
				return PROTECTED_DECLARATIONS_EDEFAULT == null ? protectedDeclarations != null : !PROTECTED_DECLARATIONS_EDEFAULT.equals(protectedDeclarations);
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PUBLIC_DECLARATIONS:
				return PUBLIC_DECLARATIONS_EDEFAULT == null ? publicDeclarations != null : !PUBLIC_DECLARATIONS_EDEFAULT.equals(publicDeclarations);
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_HEADER:
				return generateHeader != GENERATE_HEADER_EDEFAULT;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_IMPLEMENTATION:
				return generateImplementation != GENERATE_IMPLEMENTATION_EDEFAULT;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE:
				return generate != GENERATE_EDEFAULT;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__KIND:
				return kind != KIND_EDEFAULT;
			case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_TYPE:
				return IMPLEMENTATION_TYPE_EDEFAULT == null ? implementationType != null : !IMPLEMENTATION_TYPE_EDEFAULT.equals(implementationType);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == CppFileProperties.class) {
			switch (derivedFeatureID) {
				case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_PREFACE: return RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_PREFACE;
				case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_ENDING: return RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_ENDING;
				case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_PREFACE: return RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_PREFACE;
				case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_ENDING: return RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_ENDING;
				default: return -1;
			}
		}
		if (baseClass == ClassProperties.class) {
			switch (derivedFeatureID) {
				case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__BASE_CLASS: return RTCppPropertiesPackage.CLASS_PROPERTIES__BASE_CLASS;
				case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PRIVATE_DECLARATIONS: return RTCppPropertiesPackage.CLASS_PROPERTIES__PRIVATE_DECLARATIONS;
				case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PROTECTED_DECLARATIONS: return RTCppPropertiesPackage.CLASS_PROPERTIES__PROTECTED_DECLARATIONS;
				case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PUBLIC_DECLARATIONS: return RTCppPropertiesPackage.CLASS_PROPERTIES__PUBLIC_DECLARATIONS;
				default: return -1;
			}
		}
		if (baseClass == FileGenerationProperties.class) {
			switch (derivedFeatureID) {
				case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_HEADER: return RTCppPropertiesPackage.FILE_GENERATION_PROPERTIES__GENERATE_HEADER;
				case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_IMPLEMENTATION: return RTCppPropertiesPackage.FILE_GENERATION_PROPERTIES__GENERATE_IMPLEMENTATION;
				default: return -1;
			}
		}
		if (baseClass == GenerationProperties.class) {
			switch (derivedFeatureID) {
				case RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE: return RTCppPropertiesPackage.GENERATION_PROPERTIES__GENERATE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == CppFileProperties.class) {
			switch (baseFeatureID) {
				case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_PREFACE: return RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_PREFACE;
				case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__HEADER_ENDING: return RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__HEADER_ENDING;
				case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_PREFACE: return RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_PREFACE;
				case RTCppPropertiesPackage.CPP_FILE_PROPERTIES__IMPLEMENTATION_ENDING: return RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__IMPLEMENTATION_ENDING;
				default: return -1;
			}
		}
		if (baseClass == ClassProperties.class) {
			switch (baseFeatureID) {
				case RTCppPropertiesPackage.CLASS_PROPERTIES__BASE_CLASS: return RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__BASE_CLASS;
				case RTCppPropertiesPackage.CLASS_PROPERTIES__PRIVATE_DECLARATIONS: return RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PRIVATE_DECLARATIONS;
				case RTCppPropertiesPackage.CLASS_PROPERTIES__PROTECTED_DECLARATIONS: return RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PROTECTED_DECLARATIONS;
				case RTCppPropertiesPackage.CLASS_PROPERTIES__PUBLIC_DECLARATIONS: return RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__PUBLIC_DECLARATIONS;
				default: return -1;
			}
		}
		if (baseClass == FileGenerationProperties.class) {
			switch (baseFeatureID) {
				case RTCppPropertiesPackage.FILE_GENERATION_PROPERTIES__GENERATE_HEADER: return RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_HEADER;
				case RTCppPropertiesPackage.FILE_GENERATION_PROPERTIES__GENERATE_IMPLEMENTATION: return RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE_IMPLEMENTATION;
				default: return -1;
			}
		}
		if (baseClass == GenerationProperties.class) {
			switch (baseFeatureID) {
				case RTCppPropertiesPackage.GENERATION_PROPERTIES__GENERATE: return RTCppPropertiesPackage.PASSIVE_CLASS_PROPERTIES__GENERATE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (headerPreface: ");
		result.append(headerPreface);
		result.append(", headerEnding: ");
		result.append(headerEnding);
		result.append(", implementationPreface: ");
		result.append(implementationPreface);
		result.append(", implementationEnding: ");
		result.append(implementationEnding);
		result.append(", privateDeclarations: ");
		result.append(privateDeclarations);
		result.append(", protectedDeclarations: ");
		result.append(protectedDeclarations);
		result.append(", publicDeclarations: ");
		result.append(publicDeclarations);
		result.append(", generateHeader: ");
		result.append(generateHeader);
		result.append(", generateImplementation: ");
		result.append(generateImplementation);
		result.append(", generate: ");
		result.append(generate);
		result.append(", kind: ");
		result.append(kind);
		result.append(", implementationType: ");
		result.append(implementationType);
		result.append(')');
		return result.toString();
	}

} //PassiveClassPropertiesImpl
