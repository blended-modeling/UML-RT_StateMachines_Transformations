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
package org.eclipse.papyrusrt.umlrt.uml;

import java.util.List;

import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPackageImpl;
import org.eclipse.uml2.uml.Package;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Representation of the UML-RT concept of a <em>Package</em>, providing
 * a namespace for capsules, protocols, and other packages.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getNestedPackages <em>Nested Package</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getNestingPackage <em>Nesting Package</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getCapsules <em>Capsule</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getProtocols <em>Protocol</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPackage()
 * @generated
 */
public interface UMLRTPackage extends UMLRTNamedElement {

	/**
	 * Obtains the canonical instance of the package façade for a package.
	 *
	 * @param package_
	 *            a package in the UML model
	 *
	 * @return its package façade, or {@code null} if the package is not a valid UML-RT package
	 *         (for example, if it is a protocol-container package or does not
	 *         have or inherit an application of the UML-RT profile)
	 */
	public static UMLRTPackage getInstance(Package package_) {
		return UMLRTPackageImpl.getInstance(package_);
	}

	/**
	 * Returns the value of the '<em><b>Nested Package</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getNestingPackage <em>Nesting Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Packages contained with this package's namespace.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Nested Package</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPackage_NestedPackage()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getNestingPackage
	 * @generated
	 */
	List<UMLRTPackage> getNestedPackages();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage} with the specified '<em><b>Name</b></em>' from the '<em><b>Nested Package</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getNestedPackages()
	 * @generated
	 */
	UMLRTPackage getNestedPackage(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage} with the specified '<em><b>Name</b></em>' from the '<em><b>Nested Package</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getNestedPackages()
	 * @generated
	 */
	UMLRTPackage getNestedPackage(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Nesting Package</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getNestedPackages <em>Nested Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The package containing this package. Packages do not have to
	 * be contained; they may be a root of the model content graph.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Nesting Package</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPackage_NestingPackage()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage#getNestedPackages
	 * @generated
	 */
	UMLRTPackage getNestingPackage();

	/**
	 * Returns the value of the '<em><b>Capsule</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Capsules contained within this package's namespace.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Capsule</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPackage_Capsule()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getPackage
	 * @generated
	 */
	List<UMLRTCapsule> getCapsules();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule} with the specified '<em><b>Name</b></em>' from the '<em><b>Capsule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getCapsules()
	 * @generated
	 */
	UMLRTCapsule getCapsule(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule} with the specified '<em><b>Name</b></em>' from the '<em><b>Capsule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getCapsules()
	 * @generated
	 */
	UMLRTCapsule getCapsule(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Protocol</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Protocols contained within this package's namespace.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Protocol</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getPackage_Protocol()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol#getPackage
	 * @generated
	 */
	List<UMLRTProtocol> getProtocols();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol} with the specified '<em><b>Name</b></em>' from the '<em><b>Protocol</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getProtocols()
	 * @generated
	 */
	UMLRTProtocol getProtocol(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol} with the specified '<em><b>Name</b></em>' from the '<em><b>Protocol</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getProtocols()
	 * @generated
	 */
	UMLRTProtocol getProtocol(String name, boolean ignoreCase);

	/**
	 * <p>
	 * This operation redefines the following operations:
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#toUML() <em>To UML</em>}'</li>
	 * </ul>
	 * </p>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the UML package element underlying this UML-RT
	 * façade. Useful, for example, to find UML constructs such
	 * as passive classes and data types that UML-RT re-uses from
	 * UML.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	org.eclipse.uml2.uml.Package toUML();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new package within this package's namespace.
	 *
	 * @param name
	 *            A name for the new nested package
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTPackage createNestedPackage(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new capsule within this package's namespace.
	 *
	 * @param name
	 *            A name for the new capsule
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTCapsule createCapsule(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new protocol within this package's namespace.
	 *
	 * @param name
	 *            A name for the new protocol
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTProtocol createProtocol(String name);

} // UMLRTPackage
