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
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTStateImpl;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation of the UML-RT concept of <em>State</em> in a state machine.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getSubtransitions <em>Subtransition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getEntry <em>Entry</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getExit <em>Exit</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getConnectionPoints <em>Connection Point</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getEntryPoints <em>Entry Point</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getExitPoints <em>Exit Point</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getRedefinedState <em>Redefined State</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#isComposite <em>Composite</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#isSimple <em>Simple</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getSubvertices <em>Subvertex</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getState()
 * @generated
 */
public interface UMLRTState extends UMLRTVertex {
	/**
	 * Obtains the canonical instance of the façade for a state.
	 *
	 * @param state
	 *            a state in the UML model
	 *
	 * @return its façade, or {@code null} if the state is not a valid UML-RT state
	 */
	public static UMLRTState getInstance(State state) {
		return UMLRTStateImpl.getInstance(state);
	}

	/**
	 * Returns the value of the '<em><b>Subtransition</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getState <em>State</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The transitions nested within a composite state. These are not the
	 * transitions <code>incoming</code> to and <code>outgoing</code>
	 * from the state.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Subtransition</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getState_Subtransition()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition#getState
	 * @generated
	 */
	List<UMLRTTransition> getSubtransitions();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>' from the '<em><b>Subtransition</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getSubtransitions()
	 * @generated
	 */
	UMLRTTransition getSubtransition(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>' from the '<em><b>Subtransition</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getSubtransitions()
	 * @generated
	 */
	UMLRTTransition getSubtransition(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Connection Point</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connection Point</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Connection Point</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getState_ConnectionPoint()
	 * @generated
	 */
	List<UMLRTConnectionPoint> getConnectionPoints();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} with the specified '<em><b>Name</b></em>' from the '<em><b>Connection Point</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getConnectionPoints()
	 * @generated
	 */
	UMLRTConnectionPoint getConnectionPoint(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} with the specified '<em><b>Name</b></em>' from the '<em><b>Connection Point</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getConnectionPoints()
	 * @generated
	 */
	UMLRTConnectionPoint getConnectionPoint(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Entry Point</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getConnectionPoints() <em>Connection Point</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The entry points of the composite state.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Entry Point</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getState_EntryPoint()
	 * @generated
	 */
	List<UMLRTConnectionPoint> getEntryPoints();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} with the specified '<em><b>Name</b></em>' from the '<em><b>Entry Point</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getEntryPoints()
	 * @generated
	 */
	UMLRTConnectionPoint getEntryPoint(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} with the specified '<em><b>Name</b></em>' from the '<em><b>Entry Point</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getEntryPoints()
	 * @generated
	 */
	UMLRTConnectionPoint getEntryPoint(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Exit Point</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getConnectionPoints() <em>Connection Point</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The exit points of the composite state.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Exit Point</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getState_ExitPoint()
	 * @generated
	 */
	List<UMLRTConnectionPoint> getExitPoints();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} with the specified '<em><b>Name</b></em>' from the '<em><b>Exit Point</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getExitPoints()
	 * @generated
	 */
	UMLRTConnectionPoint getExitPoint(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} with the specified '<em><b>Name</b></em>' from the '<em><b>Exit Point</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getExitPoints()
	 * @generated
	 */
	UMLRTConnectionPoint getExitPoint(String name, boolean ignoreCase);

	/**
	 * Returns the value of the '<em><b>Redefined State</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getRedefinedVertex() <em>Redefined Vertex</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The state in the redefined state machine or composite state
	 * that this state redefines.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefined State</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getState_RedefinedState()
	 * @generated
	 */
	UMLRTState getRedefinedState();

	/**
	 * Returns the value of the '<em><b>Composite</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composite</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Composite</em>' attribute.
	 * @see #setComposite(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getState_Composite()
	 * @generated
	 */
	boolean isComposite();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#isComposite <em>Composite</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Composite</em>' attribute.
	 * @see #isComposite()
	 * @generated
	 */
	void setComposite(boolean value);

	/**
	 * Returns the value of the '<em><b>Simple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Simple</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Simple</em>' attribute.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getState_Simple()
	 * @generated
	 */
	boolean isSimple();

	/**
	 * Returns the value of the '<em><b>Entry</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getEnteredState <em>Entered State</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Entry</em>' reference.
	 * @see #setEntry(UMLRTOpaqueBehavior)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getState_Entry()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getEnteredState
	 * @generated
	 */
	UMLRTOpaqueBehavior getEntry();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getEntry <em>Entry</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Entry</em>' reference.
	 * @see #getEntry()
	 * @generated
	 */
	void setEntry(UMLRTOpaqueBehavior value);

	/**
	 * Returns the value of the '<em><b>Exit</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getExitedState <em>Exited State</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Exit</em>' reference.
	 * @see #setExit(UMLRTOpaqueBehavior)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getState_Exit()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior#getExitedState
	 * @generated
	 */
	UMLRTOpaqueBehavior getExit();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTState#getExit <em>Exit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Exit</em>' reference.
	 * @see #getExit()
	 * @generated
	 */
	void setExit(UMLRTOpaqueBehavior value);

	/**
	 * Returns the value of the '<em><b>Subvertex</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getState <em>State</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinableElements() <em>Redefinable Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The sub-vertices of the composite state.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Subvertex</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getState_Subvertex()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#getState
	 * @generated
	 */
	List<UMLRTVertex> getSubvertices();

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} with the specified '<em><b>Name</b></em>' from the '<em><b>Subvertex</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getSubvertices()
	 * @generated
	 */
	UMLRTVertex getSubvertex(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} with the specified '<em><b>Name</b></em>' from the '<em><b>Subvertex</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *            Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param eClass
	 *            The Ecore class of the {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getSubvertices()
	 * @generated
	 */
	UMLRTVertex getSubvertex(String name, boolean ignoreCase, EClass eClass);

	/**
	 * <p>
	 * This operation redefines the following operations:
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex#toUML() <em>To UML</em>}'</li>
	 * </ul>
	 * </p>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the underlying UML representation of this state.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	State toUML();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new sub-state in this composite state.
	 *
	 * @param name
	 *            The name of the state to create.
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTState createSubstate(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new sub-pseudostate in this composite state.
	 *
	 * @param kind
	 *            The kind of pseudostate to create
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTPseudostate createSubpseudostate(UMLRTPseudostateKind kind);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new entry point to or exit point from this composite state.
	 *
	 * @param kind
	 *            The kind of connection point to create
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTConnectionPoint createConnectionPoint(UMLRTConnectionPointKind kind);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the singular UML region of this state, if it is composite.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	Region toRegion();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new entry behavior for this state, if it does not already have one.
	 *
	 * @param language
	 *            An optional language for the entry behavior
	 * @param body
	 *            The entry code snippet
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTOpaqueBehavior createEntry(String language, String body);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a new exit behavior for this state, if it does not already have one.
	 *
	 * @param language
	 *            An optional language for the exit behavior
	 * @param body
	 *            The exit code snippet
	 *            <!-- end-model-doc -->
	 * @generated
	 */
	UMLRTOpaqueBehavior createExit(String language, String body);

	@Override
	Stream<? extends UMLRTState> allRedefinitions();

} // UMLRTState
