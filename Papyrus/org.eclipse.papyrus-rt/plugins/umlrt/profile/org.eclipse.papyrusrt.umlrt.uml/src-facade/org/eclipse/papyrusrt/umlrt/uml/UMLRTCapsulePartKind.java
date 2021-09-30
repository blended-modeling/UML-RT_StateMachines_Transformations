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
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Capsule Part Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * An enumeration of the kinds of capsule parts recognized by UML-RT.
 * <!-- end-model-doc -->
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getUMLRTCapsulePartKind()
 * @generated
 */
public enum UMLRTCapsulePartKind implements Enumerator {
	/**
	 * The '<em><b>Fixed</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #FIXED_VALUE
	 * @generated
	 * @ordered
	 */
	FIXED(0, "fixed", "fixed"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Optional</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #OPTIONAL_VALUE
	 * @generated
	 * @ordered
	 */
	OPTIONAL(1, "optional", "optional"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Plug in</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #PLUG_IN_VALUE
	 * @generated
	 * @ordered
	 */
	PLUG_IN(2, "plug_in", "plug-in"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Null</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #NULL_VALUE
	 * @generated
	 * @ordered
	 */
	NULL(3, "null", "null"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Fixed</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication that a capsule-part has fixed replication with composition semantics.
	 * <!-- end-model-doc -->
	 *
	 * @see #FIXED
	 * @generated
	 * @ordered
	 */
	public static final int FIXED_VALUE = 0;

	/**
	 * The '<em><b>Optional</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication that a capsule-part has optional replication with composition semantics.
	 * <!-- end-model-doc -->
	 *
	 * @see #OPTIONAL
	 * @generated
	 * @ordered
	 */
	public static final int OPTIONAL_VALUE = 1;

	/**
	 * The '<em><b>Plug in</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication that a capsule-part has optional replication with shared aggregation semantics.
	 * <!-- end-model-doc -->
	 *
	 * @see #PLUG_IN
	 * @generated
	 * @ordered
	 */
	public static final int PLUG_IN_VALUE = 2;

	/**
	 * The '<em><b>Null</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication that the replication and aggregation specified for a capsule-part
	 * are inconsistent an do not imply any recognized capsule-part kind.
	 * <!-- end-model-doc -->
	 *
	 * @see #NULL
	 * @generated
	 * @ordered
	 */
	public static final int NULL_VALUE = 3;

	/**
	 * An array of all the '<em><b>Capsule Part Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final UMLRTCapsulePartKind[] VALUES_ARRAY = new UMLRTCapsulePartKind[] {
			FIXED,
			OPTIONAL,
			PLUG_IN,
			NULL,
	};

	/**
	 * A public read-only list of all the '<em><b>Capsule Part Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<UMLRTCapsulePartKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Capsule Part Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTCapsulePartKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UMLRTCapsulePartKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Capsule Part Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTCapsulePartKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UMLRTCapsulePartKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Capsule Part Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTCapsulePartKind get(int value) {
		switch (value) {
		case FIXED_VALUE:
			return FIXED;
		case OPTIONAL_VALUE:
			return OPTIONAL;
		case PLUG_IN_VALUE:
			return PLUG_IN;
		case NULL_VALUE:
			return NULL;
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
	private UMLRTCapsulePartKind(int value, String name, String literal) {
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

	/**
	 * Queries whether I am the {@link #NULL} object.
	 *
	 * @return whether I am the null value
	 */
	public boolean isNull() {
		return this == NULL;
	}

	/**
	 * Queries whether I am a kind of required capsule-part.
	 *
	 * @return whether capsule-parts of my kind are required
	 */
	public boolean isRequired() {
		return (this == FIXED);
	}

	/**
	 * Queries whether I am a kind of composite capsule-part.
	 *
	 * @return whether capsule-parts of my kind are composite
	 */
	public boolean isComposite() {
		return (this == FIXED) || (this == OPTIONAL);
	}

	/**
	 * Computes the kind that is like me but for capsule-parts of the given {@code required} kind.
	 *
	 * @param required
	 *            the requireness kind from which to derive a new capsule-part kind
	 * @return the new capsule-part kind, which may be {@link #NULL} if this value of {@code required}
	 *         does not make sense with the other attributes that I imply
	 */
	public UMLRTCapsulePartKind setRequired(boolean required) {
		if (required == isRequired()) {
			return this;
		} else {
			switch (this) {
			case OPTIONAL:
				return FIXED;
			case FIXED:
				return OPTIONAL;
			case PLUG_IN:
				// We don't consider aggregation if the multiplicity is required
				return FIXED;
			default:
				return NULL;
			}
		}
	}

	/**
	 * Computes the kind that is like me but for capsule-parts of the given {@code composite} kind.
	 *
	 * @param composite
	 *            the compositeness kind from which to derive a new capsule-part kind
	 * @return the new capsule-part kind, which may be {@link #NULL} if this value of {@code composite}
	 *         does not make sense with the other attributes that I imply
	 */
	public UMLRTCapsulePartKind setComposite(boolean composite) {
		if (composite == isComposite()) {
			return this;
		} else {
			switch (this) {
			case OPTIONAL:
				return PLUG_IN;
			case PLUG_IN:
				return OPTIONAL;
			default:
				return NULL;
			}
		}
	}

	/**
	 * Computes the kind of a capsule-part by its attributes.
	 *
	 * @param lowerBound
	 *            the capsule-part's lower bound
	 * @param upperBound
	 *            the capsule-part's upper bound
	 * @param aggregation
	 *            the capsule-part's aggregation
	 *
	 * @return the capsule-part kind, or {@link #NULL} if the specific attributes make no sense together
	 */
	public static UMLRTCapsulePartKind get(int lowerBound, int upperBound, AggregationKind aggregation) {
		UMLRTCapsulePartKind result = UMLRTCapsulePartKind.NULL;

		if ((lowerBound > 0) && (upperBound > 0)) {
			result = FIXED;
		} else if ((lowerBound == 0) || (upperBound == LiteralUnlimitedNatural.UNLIMITED)) {
			switch (aggregation) {
			case COMPOSITE_LITERAL:
				result = OPTIONAL;
				break;
			case SHARED_LITERAL:
				result = PLUG_IN;
				break;
			default:
				// Pass
				break;
			}
		}

		return result;
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

} // UMLRTCapsulePartKind
