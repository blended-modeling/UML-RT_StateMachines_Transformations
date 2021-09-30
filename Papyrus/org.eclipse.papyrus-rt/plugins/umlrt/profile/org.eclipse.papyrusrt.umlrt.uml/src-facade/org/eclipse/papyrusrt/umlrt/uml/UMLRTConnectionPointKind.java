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
 * An enumeration of the kinds of connection-point vertices recognized
 * by UML-RT.
 * <!-- end-model-doc -->
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getUMLRTConnectionPointKind()
 * @generated
 */
public enum UMLRTConnectionPointKind implements Enumerator {
	/**
	 * The '<em><b>Entry</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #ENTRY_VALUE
	 * @generated
	 * @ordered
	 */
	ENTRY(0, "entry", "entry"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Exit</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #EXIT_VALUE
	 * @generated
	 * @ordered
	 */
	EXIT(1, "exit", "exit"),; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Entry</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates an entry point.
	 * <!-- end-model-doc -->
	 *
	 * @see #ENTRY
	 * @generated
	 * @ordered
	 */
	public static final int ENTRY_VALUE = 0;

	/**
	 * The '<em><b>Exit</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates an exit point.
	 * <!-- end-model-doc -->
	 *
	 * @see #EXIT
	 * @generated
	 * @ordered
	 */
	public static final int EXIT_VALUE = 1;

	/**
	 * An array of all the '<em><b>Connection Point Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final UMLRTConnectionPointKind[] VALUES_ARRAY = new UMLRTConnectionPointKind[] {
			ENTRY,
			EXIT,
	};

	/**
	 * A public read-only list of all the '<em><b>Connection Point Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<UMLRTConnectionPointKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	private static final EnumMap<UMLRTConnectionPointKind, PseudostateKind> UML = new EnumMap<>(UMLRTConnectionPointKind.class);
	private static final EnumMap<PseudostateKind, UMLRTConnectionPointKind> INV = new EnumMap<>(PseudostateKind.class);
	static {
		UML.put(ENTRY, PseudostateKind.ENTRY_POINT_LITERAL);
		INV.put(PseudostateKind.ENTRY_POINT_LITERAL, ENTRY);
		UML.put(EXIT, PseudostateKind.EXIT_POINT_LITERAL);
		INV.put(PseudostateKind.EXIT_POINT_LITERAL, EXIT);
	}

	/**
	 * Returns the '<em><b>Connection Point Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTConnectionPointKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UMLRTConnectionPointKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Connection Point Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTConnectionPointKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UMLRTConnectionPointKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Connection Point Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTConnectionPointKind get(int value) {
		switch (value) {
		case ENTRY_VALUE:
			return ENTRY;
		case EXIT_VALUE:
			return EXIT;
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
	private UMLRTConnectionPointKind(int value, String name, String literal) {
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

	public static UMLRTConnectionPointKind valueOf(PseudostateKind umlKind) {
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

} // ConnectionPointKind
