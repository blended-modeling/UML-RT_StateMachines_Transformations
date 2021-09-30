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

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Port Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * A enumeration of the kinds of ports recognized by UML-RT.
 * <!-- end-model-doc -->
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getUMLRTPortKind()
 * @generated
 */
public enum UMLRTPortKind implements Enumerator {
	/**
	 * The '<em><b>External Behavior</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #EXTERNAL_BEHAVIOR_VALUE
	 * @generated
	 * @ordered
	 */
	EXTERNAL_BEHAVIOR(0, "externalBehavior", "externalBehavior"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Internal Behavior</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #INTERNAL_BEHAVIOR_VALUE
	 * @generated
	 * @ordered
	 */
	INTERNAL_BEHAVIOR(1, "internalBehavior", "internalBehavior"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Relay</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #RELAY_VALUE
	 * @generated
	 * @ordered
	 */
	RELAY(2, "relay", "relay"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>SAP</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #SAP_VALUE
	 * @generated
	 * @ordered
	 */
	SAP(3, "SAP", "SAP"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>SPP</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #SPP_VALUE
	 * @generated
	 * @ordered
	 */
	SPP(4, "SPP", "SPP"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Null</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #NULL_VALUE
	 * @generated
	 * @ordered
	 */
	NULL(5, "null", "null"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>External Behavior</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication of a service port that provides behavior.
	 * <!-- end-model-doc -->
	 *
	 * @see #EXTERNAL_BEHAVIOR
	 * @generated
	 * @ordered
	 */
	public static final int EXTERNAL_BEHAVIOR_VALUE = 0;

	/**
	 * The '<em><b>Internal Behavior</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication of an internal behavior port.
	 * <!-- end-model-doc -->
	 *
	 * @see #INTERNAL_BEHAVIOR
	 * @generated
	 * @ordered
	 */
	public static final int INTERNAL_BEHAVIOR_VALUE = 1;

	/**
	 * The '<em><b>Relay</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication of a relay port: a service port that delegates behavior.
	 * <!-- end-model-doc -->
	 *
	 * @see #RELAY
	 * @generated
	 * @ordered
	 */
	public static final int RELAY_VALUE = 2;

	/**
	 * The '<em><b>SAP</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication of a service access port: an internal port that binds
	 * to a service provider port in another architectural layer.
	 * <!-- end-model-doc -->
	 *
	 * @see #SAP
	 * @generated
	 * @ordered
	 */
	public static final int SAP_VALUE = 3;

	/**
	 * The '<em><b>SPP</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication of a service provider port: a port that provides
	 * service to ports in other architectural layers.
	 * <!-- end-model-doc -->
	 *
	 * @see #SPP
	 * @generated
	 * @ordered
	 */
	public static final int SPP_VALUE = 4;

	/**
	 * The '<em><b>Null</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication of a port that whose configuration is inconsistent
	 * and does not imply a recognized port kind.
	 * <!-- end-model-doc -->
	 *
	 * @see #NULL
	 * @generated
	 * @ordered
	 */
	public static final int NULL_VALUE = 5;

	/**
	 * An array of all the '<em><b>Port Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final UMLRTPortKind[] VALUES_ARRAY = new UMLRTPortKind[] {
			EXTERNAL_BEHAVIOR,
			INTERNAL_BEHAVIOR,
			RELAY,
			SAP,
			SPP,
			NULL,
	};

	/**
	 * A public read-only list of all the '<em><b>Port Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<UMLRTPortKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Port Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTPortKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UMLRTPortKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Port Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTPortKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UMLRTPortKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Port Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTPortKind get(int value) {
		switch (value) {
		case EXTERNAL_BEHAVIOR_VALUE:
			return EXTERNAL_BEHAVIOR;
		case INTERNAL_BEHAVIOR_VALUE:
			return INTERNAL_BEHAVIOR;
		case RELAY_VALUE:
			return RELAY;
		case SAP_VALUE:
			return SAP;
		case SPP_VALUE:
			return SPP;
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
	private UMLRTPortKind(int value, String name, String literal) {
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
	 * Queries whether I am a kind of an external-facing port.
	 * The {@link #NULL} kind is neither external nor internal.
	 *
	 * @return whether ports of my kind are external facing
	 */
	public boolean isExternal() {
		return (this == EXTERNAL_BEHAVIOR) || (this == RELAY) || (this == SPP);
	}

	/**
	 * Queries whether I am a kind of an internal-facing port.
	 * The {@link #NULL} kind is neither internal nor external.
	 *
	 * @return whether ports of my kind are internal facing
	 */
	public boolean isInternal() {
		return (this == INTERNAL_BEHAVIOR) || (this == SAP);
	}

	/**
	 * Queries whether I am a kind of a service port.
	 *
	 * @return whether ports of my kind are service ports
	 */
	public boolean isService() {
		return (this == EXTERNAL_BEHAVIOR) || (this == RELAY) || (this == SPP);
	}

	/**
	 * Queries whether I am a kind of a wired port.
	 * The {@link #NULL} kind is neither wired nor non-wired.
	 *
	 * @return whether ports of my kind are wired
	 */
	public boolean isWired() {
		return (this == EXTERNAL_BEHAVIOR) || (this == INTERNAL_BEHAVIOR)
				|| (this == RELAY);
	}

	/**
	 * Queries whether I am a kind of a non-wired port.
	 * The {@link #NULL} kind is neither non-wired nor wired.
	 *
	 * @return whether ports of my kind are unwired
	 */
	public boolean isUnwired() {
		return (this == SPP) || (this == SAP);
	}

	/**
	 * Queries whether I am a kind of a behaviour port.
	 *
	 * @return whether ports of my kind are behaviour ports
	 */
	public boolean isBehavior() {
		return (this == EXTERNAL_BEHAVIOR) || (this == INTERNAL_BEHAVIOR)
				|| (this == SPP) || (this == SAP);
	}

	/**
	 * Queries whether I am a kind of a published port.
	 *
	 * @return whether ports of my kind are published
	 */
	public boolean isPublish() {
		return (this == SPP);
	}

	/**
	 * Computes the kind that is like me but for ports of the given {@code service} kind.
	 *
	 * @param service
	 *            the service kind from which to derive a new port kind
	 * @return the new port kind, which may be {@link #NULL} if this value of {@code service}
	 *         does not make sense with the other attributes that I imply
	 */
	public UMLRTPortKind setService(boolean service) {
		if (service == isService()) {
			return this;
		} else {
			switch (this) {
			case EXTERNAL_BEHAVIOR:
				return INTERNAL_BEHAVIOR;
			case INTERNAL_BEHAVIOR:
				return EXTERNAL_BEHAVIOR;
			case SPP:
				return SAP;
			case SAP:
				return SPP;
			default:
				return NULL;
			}
		}
	}

	/**
	 * Computes the kind that is like me but for ports of the given {@code behavior} kind.
	 *
	 * @param behavior
	 *            the behavior kind from which to derive a new port kind
	 * @return the new port kind, which may be {@link #NULL} if this value of {@code behavior}
	 *         does not make sense with the other attributes that I imply
	 */
	public UMLRTPortKind setBehavior(boolean behavior) {
		if (behavior == isBehavior()) {
			return this;
		} else {
			switch (this) {
			case EXTERNAL_BEHAVIOR:
				return RELAY;
			case RELAY:
				return EXTERNAL_BEHAVIOR;
			default:
				return NULL;
			}
		}
	}

	/**
	 * Computes the kind that is like me but for ports of the given {@code wired} kind.
	 *
	 * @param wired
	 *            the wiredness kind from which to derive a new port kind
	 * @return the new port kind, which may be {@link #NULL} if this value of {@code wired}
	 *         does not make sense with the other attributes that I imply
	 */
	public UMLRTPortKind setWired(boolean wired) {
		if (wired == isWired()) {
			return this;
		} else {
			switch (this) {
			case EXTERNAL_BEHAVIOR:
				return SPP;
			case SPP:
				return EXTERNAL_BEHAVIOR;
			case INTERNAL_BEHAVIOR:
				return SAP;
			case SAP:
				return INTERNAL_BEHAVIOR;
			default:
				return NULL;
			}
		}
	}

	/**
	 * Computes the kind that is like me but for ports of the given {@code publish} kind.
	 *
	 * @param publish
	 *            the publication kind from which to derive a new port kind
	 * @return the new port kind, which may be {@link #NULL} if this value of {@code publish}
	 *         does not make sense with the other attributes that I imply
	 */
	public UMLRTPortKind setPublish(boolean publish) {
		if (publish == isPublish()) {
			return this;
		} else {
			switch (this) {
			case SPP:
				return SAP;
			case SAP:
				return SPP;
			default:
				return NULL;
			}
		}
	}

	/**
	 * Computes the kind of a port by its attributes.
	 *
	 * @param service
	 *            whether the port is a service port
	 * @param behavior
	 *            whether the port is a behavior port
	 * @param wired
	 *            whether the port is wired
	 * @param publish
	 *            whether the port is published
	 *
	 * @return the port kind, or {@link #NULL} if the specific attributes make no sense together
	 */
	public static UMLRTPortKind get(boolean service, boolean behavior, boolean wired, boolean publish) {
		UMLRTPortKind result = NULL;

		if (service && wired && behavior && !publish) {
			result = EXTERNAL_BEHAVIOR;
		} else if (behavior && publish && !wired) {
			result = SPP; // isService won't be checked here => Cf. bug 477033
		} else if (wired && behavior && !service && !publish) {
			result = INTERNAL_BEHAVIOR;
		} else if (service && wired && !behavior && !publish) {
			result = RELAY;
		} else if (behavior && !wired && !publish) {
			result = SAP; // isService won't be checked here => Cf. bug 477033
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

} // UMLRTPortKind
