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

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.uml2.uml.Classifier;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Classifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An abstract representation of UML-RT concepts that are
 * classifiers in the underlying UML model, such as Capsules
 * and Protocols.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getGeneral <em>General</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getSpecifics <em>Specific</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getClassifier()
 * @generated
 */
public interface UMLRTClassifier extends UMLRTNamedElement {
	/**
	 * Returns the value of the '<em><b>General</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getSpecifics <em>Specific</em>}'.
	 * This feature is a derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The supertype, if any, of the classifier. Unlike UML, which
	 * allows multiple inheritance, UML-RT recognizes only single
	 * inheritance of classifiers.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>General</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getClassifier_General()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getSpecifics
	 * @generated
	 */
	UMLRTClassifier getGeneral();

	/**
	 * Returns the value of the '<em><b>Specific</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getGeneral <em>General</em>}'.
	 * This feature is a derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The set of classifiers specializing this classifier, from amongst
	 * the model resources that are loaded in the resource set. Thus,
	 * this is not necessarily complete: there can be specializations
	 * that are unknown because they are not loaded.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Specific</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getClassifier_Specific()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier#getGeneral
	 * @generated
	 */
	List<UMLRTClassifier> getSpecifics();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier} with the specified '<em><b>Name</b></em>' from the '<em><b>Specific</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getSpecifics()
	 * @generated
	 */
	UMLRTClassifier getSpecific(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier} with the specified '<em><b>Name</b></em>' from the '<em><b>Specific</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param eClass
	 *            The Ecore class of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getSpecifics()
	 * @generated
	 */
	UMLRTClassifier getSpecific(String name, boolean ignoreCase, EClass eClass);

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
	 * Obtains the underlying UML model element representing
	 * this classifier.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	Classifier toUML();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries the package containing this classifier. Unlike UML,
	 * which has a concept of nested classifiers, all classifiers in
	 * UML-RT are owned by packages.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	UMLRTPackage getPackage();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the chain of supertypes from and including this
	 * classifier up to the root classifier.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	List<? extends UMLRTClassifier> getAncestry();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the classifier hierarchy rooted at this classifier,
	 * as a Java stream, in breadth-first order. Note that this
	 * may only include classifiers that are currently loaded in
	 * the resource set.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	Stream<? extends UMLRTClassifier> getHierarchy();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries whether another classifier is a supertype (transitively) of this classifier.
	 *
	 * @param classifier
	 *            Some classifier in the UML-RT model
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	boolean isSuperTypeOf(UMLRTClassifier classifier);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Sets the general classifier of this classifier, replacing the current general, if any
	 *
	 * @param general
	 *            The new general classifier which, if not <code>null</code>, must be of the
	 *            same metaclass or a more general metaclass as this classifier
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	void setGeneral(UMLRTClassifier general);

} // UMLRTClassifier
