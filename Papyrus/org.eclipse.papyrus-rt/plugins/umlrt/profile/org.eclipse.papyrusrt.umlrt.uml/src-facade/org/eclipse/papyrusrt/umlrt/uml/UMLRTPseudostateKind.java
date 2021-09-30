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

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.uml2.uml.PseudostateKind;

/**
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * An enumeration of the kinds of pseudo-state vertices recognized
 * by UML-RT.
 * <!-- end-model-doc -->
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getUMLRTPseudostateKind()
 * @generated
 */
public enum UMLRTPseudostateKind implements Enumerator {
	/**
	 * The '<em><b>Initial</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #INITIAL_VALUE
	 * @generated
	 * @ordered
	 */
	INITIAL(0, "initial", "initial"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>History</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #HISTORY_VALUE
	 * @generated
	 * @ordered
	 */
	HISTORY(1, "history", "history"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Choice</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #CHOICE_VALUE
	 * @generated
	 * @ordered
	 */
	CHOICE(2, "choice", "choice"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Junction</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #JUNCTION_VALUE
	 * @generated
	 * @ordered
	 */
	JUNCTION(3, "junction", "junction"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Initial</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates an initial pseudostate.
	 * <!-- end-model-doc -->
	 *
	 * @see #INITIAL
	 * @generated
	 * @ordered
	 */
	public static final int INITIAL_VALUE = 0;

	/**
	 * The '<em><b>History</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates an history pseudostate.
	 * <!-- end-model-doc -->
	 *
	 * @see #HISTORY
	 * @generated
	 * @ordered
	 */
	public static final int HISTORY_VALUE = 1;

	/**
	 * The '<em><b>Choice</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates a choice pseudostate.
	 * <!-- end-model-doc -->
	 *
	 * @see #CHOICE
	 * @generated
	 * @ordered
	 */
	public static final int CHOICE_VALUE = 2;

	/**
	 * The '<em><b>Junction</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates a junction pseudostate.
	 * <!-- end-model-doc -->
	 *
	 * @see #JUNCTION
	 * @generated
	 * @ordered
	 */
	public static final int JUNCTION_VALUE = 3;

	/**
	 * An array of all the '<em><b>Pseudostate Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final UMLRTPseudostateKind[] VALUES_ARRAY = new UMLRTPseudostateKind[] {
			INITIAL,
			HISTORY,
			CHOICE,
			JUNCTION,
	};

	/**
	 * A public read-only list of all the '<em><b>Pseudostate Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<UMLRTPseudostateKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	private static final EnumMap<UMLRTPseudostateKind, PseudostateKind> UML = new EnumMap<>(UMLRTPseudostateKind.class);
	private static final EnumMap<PseudostateKind, UMLRTPseudostateKind> INV = new EnumMap<>(PseudostateKind.class);
	static {
		UML.put(INITIAL, PseudostateKind.INITIAL_LITERAL);
		INV.put(PseudostateKind.INITIAL_LITERAL, INITIAL);
		UML.put(CHOICE, PseudostateKind.CHOICE_LITERAL);
		INV.put(PseudostateKind.CHOICE_LITERAL, CHOICE);
		UML.put(JUNCTION, PseudostateKind.JUNCTION_LITERAL);
		INV.put(PseudostateKind.JUNCTION_LITERAL, JUNCTION);
		UML.put(HISTORY, PseudostateKind.DEEP_HISTORY_LITERAL);
		INV.put(PseudostateKind.DEEP_HISTORY_LITERAL, HISTORY);
	}

	/**
	 * Returns the '<em><b>Pseudostate Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTPseudostateKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UMLRTPseudostateKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Pseudostate Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTPseudostateKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UMLRTPseudostateKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Pseudostate Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTPseudostateKind get(int value) {
		switch (value) {
		case INITIAL_VALUE:
			return INITIAL;
		case HISTORY_VALUE:
			return HISTORY;
		case CHOICE_VALUE:
			return CHOICE;
		case JUNCTION_VALUE:
			return JUNCTION;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private UMLRTPseudostateKind(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getLiteral() {
		return literal;
	}

	public PseudostateKind toUML() {
		return UML.get(this);
	}

	public static UMLRTPseudostateKind valueOf(PseudostateKind umlKind) {
		return INV.get(umlKind);
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // PseudostateKind
