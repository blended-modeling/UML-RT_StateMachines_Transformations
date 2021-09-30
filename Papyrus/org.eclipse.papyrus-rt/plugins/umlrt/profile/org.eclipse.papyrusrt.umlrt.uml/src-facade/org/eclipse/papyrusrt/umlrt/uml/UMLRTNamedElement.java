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

import org.eclipse.uml2.uml.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Named Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getModel <em>Model</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getInheritanceKind <em>Inheritance Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getQualifiedName <em>Qualified Name</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#isInherited <em>Is Inherited</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#isVirtualRedefinition <em>Is Virtual Redefinition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#isRedefinition <em>Is Redefinition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#isExcluded <em>Is Excluded</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinedElement <em>Redefined Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext <em>Redefinition Context</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements <em>Redefinable Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getInheritedElement <em>Inherited Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRootDefinition <em>Root Definition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getExcludedElements <em>Excluded Element</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement()
 * @extends FacadeObject
 * @generated
 */
public interface UMLRTNamedElement extends FacadeObject {
	/**
	 * Returns the value of the '<em><b>Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The model that (recursively) contains the element.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Model</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_Model()
	 * @generated
	 */
	UMLRTModel getModel();

	/**
	 * Returns the value of the '<em><b>Inheritance Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The disposition of an element with respect to inheritance
	 * from another context or redefinition of an inherited element.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Inheritance Kind</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_InheritanceKind()
	 * @generated
	 */
	UMLRTInheritanceKind getInheritanceKind();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name of an element, corresponding to the name of the
	 * underlying UML element.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_Name()
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The qualified name of an element, corresponding to the qualified
	 * name of the underlying UML element.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Qualified Name</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_QualifiedName()
	 * @generated
	 */
	String getQualifiedName();

	/**
	 * Returns the value of the '<em><b>Is Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication of whether the element is inherited from another context
	 * or redefines an inherited element. This is true for all elements that
	 * are not local (root) definitions in their containing context.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Is Inherited</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_IsInherited()
	 * @generated
	 */
	boolean isInherited();

	/**
	 * Returns the value of the '<em><b>Is Virtual Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication of whether the element is a stand-in, in the inheriting
	 * context, for an element that is actually inherited from another
	 * context. This stand-in is a virtual element in the sense that it
	 * is not really an element of the model and is not persisted in the
	 * model. It is just a distinct "proxy" for the inherited element, in
	 * and unique to the inheriting context.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Is Virtual Redefinition</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_IsVirtualRedefinition()
	 * @generated
	 */
	boolean isVirtualRedefinition();

	/**
	 * Returns the value of the '<em><b>Is Redefinition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication of whether the element redefines an inherited element
	 * but does not exclude it fron the redefining context.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Is Redefinition</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_IsRedefinition()
	 * @generated
	 */
	boolean isRedefinition();

	/**
	 * Returns the value of the '<em><b>Is Excluded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication of whether the element is a redefinition of an
	 * inherited element for the purpose of excluding it from the
	 * inheriting context.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Is Excluded</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_IsExcluded()
	 * @generated
	 */
	@Override
	boolean isExcluded();

	/**
	 * Returns the value of the '<em><b>Redefined Element</b></em>' reference.
	 * This feature is a derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If the element is inherited or a redefinition, the element in the
	 * parent context that it redefines. Unlike UML, the semantics
	 * of redefinition in UML-RT is singular: an element redefines at
	 * most one inherited element.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefined Element</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_RedefinedElement()
	 * @generated
	 */
	UMLRTNamedElement getRedefinedElement();

	/**
	 * Returns the value of the '<em><b>Redefinition Context</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements <em>Redefinable Element</em>}'.
	 * This feature is a derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The context in which an element redefines an inherited element.
	 * It is this redefinition context that inherits the redefined element.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefinition Context</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_RedefinitionContext()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements
	 * @generated
	 */
	UMLRTNamedElement getRedefinitionContext();

	/**
	 * Returns the value of the '<em><b>Redefinable Element</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext <em>Redefinition Context</em>}'.
	 * This feature is a derived union.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The collection of all owned elements that can be
	 * redefined by specializing (inheriting) contexts.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefinable Element</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_RedefinableElement()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext
	 * @generated
	 */
	List<UMLRTNamedElement> getRedefinableElements();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} with the specified '<em><b>Name</b></em>' from the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getRedefinableElements()
	 * @generated
	 */
	UMLRTNamedElement getRedefinableElement(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} with the specified '<em><b>Name</b></em>' from the '<em><b>Redefinable Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param eClass
	 *            The Ecore class of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getRedefinableElements()
	 * @generated
	 */
	UMLRTNamedElement getRedefinableElement(String name, boolean ignoreCase, EClass eClass);

	/**
	 * Returns the value of the '<em><b>Inherited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * For an element that is a redefinition, the nearest element
	 * in the chain of redefinitions that is not a virtual element
	 * representing the inherited element, but is a real element
	 * in the model. Thus, the element that is inherited. This
	 * may be the root definition, or else some redefinition of it.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Inherited Element</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_InheritedElement()
	 * @generated
	 */
	UMLRTNamedElement getInheritedElement();

	/**
	 * Returns the value of the '<em><b>Root Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element that is the highest-level definition of an element
	 * in the hierarchy. For an element that is not a redefinition of
	 * some inherited element, this is itself, otherwise it is (recursively)
	 * the root definition of the element that it redefines.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Root Definition</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_RootDefinition()
	 * @generated
	 */
	UMLRTNamedElement getRootDefinition();

	/**
	 * Returns the value of the '<em><b>Excluded Element</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The elements that the named element would inherit
	 * from parent contexts but which it excludes by redefinition.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Excluded Element</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getNamedElement_ExcludedElement()
	 * @generated
	 */
	List<UMLRTNamedElement> getExcludedElements();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} with the specified '<em><b>Name</b></em>' from the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getExcludedElements()
	 * @generated
	 */
	UMLRTNamedElement getExcludedElement(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} with the specified '<em><b>Name</b></em>' from the '<em><b>Excluded Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param eClass
	 *            The Ecore class of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getExcludedElements()
	 * @generated
	 */
	UMLRTNamedElement getExcludedElement(String name, boolean ignoreCase, EClass eClass);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the element in the underlying UML model
	 * that represents this façade object.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	NamedElement toUML();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries whether the receiver is a redefinition of a
	 * given <code>element</code>. As a special case,
	 * an element is considered to redefine itself.
	 *
	 * @param element
	 *            An UML-RT element
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	boolean redefines(UMLRTNamedElement element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If the receiver is a redefinition context in the UML sense,
	 * obtains its redefinition of a given <code>element</code>
	 * if it does, in fact, inherit that element.
	 *
	 * @param element
	 *            An UML-RT element, usually one that is inherited by
	 *            the receiver
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	<T extends UMLRTNamedElement> T getRedefinitionOf(T element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries the elements that the receiver redefines, from
	 * and including itself to the root of the redefinition chain.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	List<UMLRTNamedElement> getRedefinitionChain();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries a specific item that is excluded from the receiver's
	 * context.
	 *
	 * @param name
	 *            The name of the ecluded element to find, or <code>null</code>
	 *            to find any name.
	 * @param type
	 *            The Java interface/class of the excluded element to find,
	 *            or <code>null</code> to find an element of any metaclass.
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	<T extends UMLRTNamedElement> T getExcludedElement(String name, Class<T> type);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries the elements that redefine the receiver, from
	 * and including itself down the hierarchy.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	Stream<? extends UMLRTNamedElement> allRedefinitions();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Excludes the receiver from the context in which it
	 * would otherwise have been inherited.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	boolean exclude();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Restores an excluded element to the condition of
	 * being inherited.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	boolean reinherit();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * <p>
	 * For an element that is a "virtual redefinition" representing an
	 * element that is inherited from some other context, causes it
	 * to become a real redefinition that is (in the UML model
	 * underlying the UML-RT façade) persisted as such.
	 * </p>
	 * <p>
	 * <b>Note</b> that it should be exceedingly rare that a client
	 * of the Façade API needs to call this operation. This reification
	 * is more normally done automatically by the model when an
	 * inherited element is modified in some way to make it different
	 * to the element that it redefines.
	 * </p>
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	void reify();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Destroys the underlying UML model element using the
	 * appropriate UML operation.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	void destroy();

} // UMLRTNamedElement
