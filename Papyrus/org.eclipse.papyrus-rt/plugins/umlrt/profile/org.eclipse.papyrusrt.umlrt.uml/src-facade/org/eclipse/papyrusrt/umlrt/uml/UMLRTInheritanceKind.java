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
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Element;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Inheritance Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * An enumeration of the various dispositions of an UML-RT
 * element with respect to inheritance.
 *
 * <!-- end-model-doc -->
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getUMLRTInheritanceKind()
 * @generated
 */
public enum UMLRTInheritanceKind implements Enumerator {
	/**
	 * The '<em><b>Inherited</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #INHERITED_VALUE
	 * @generated
	 * @ordered
	 */
	INHERITED(0, "inherited", "inherited"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Redefined</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #REDEFINED_VALUE
	 * @generated
	 * @ordered
	 */
	REDEFINED(1, "redefined", "redefined"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Excluded</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #EXCLUDED_VALUE
	 * @generated
	 * @ordered
	 */
	EXCLUDED(2, "excluded", "excluded"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>None</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(3, "none", "none"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Inherited</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication that an element represents a purely inherited
	 * element, being a "shadow" of or "proxy" for the inherited
	 * definition.
	 * <!-- end-model-doc -->
	 *
	 * @see #INHERITED
	 * @generated
	 * @ordered
	 */
	public static final int INHERITED_VALUE = 0;

	/**
	 * The '<em><b>Redefined</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication that an element redefines an inherited element.
	 * <!-- end-model-doc -->
	 *
	 * @see #REDEFINED
	 * @generated
	 * @ordered
	 */
	public static final int REDEFINED_VALUE = 1;

	/**
	 * The '<em><b>Excluded</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication that an element is an exclusion, being a
	 * redefinition of an inherited element for the purpose
	 * of excluding it from ("undefining" it in) the inheriting context.
	 * <!-- end-model-doc -->
	 *
	 * @see #EXCLUDED
	 * @generated
	 * @ordered
	 */
	public static final int EXCLUDED_VALUE = 2;

	/**
	 * The '<em><b>None</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indication that an element is not inherited and not a redefinition
	 * of an inherited element: it is a new definition, possible the root
	 * of a redefinition tree in inheriting contexts.
	 * <!-- end-model-doc -->
	 *
	 * @see #NONE
	 * @generated
	 * @ordered
	 */
	public static final int NONE_VALUE = 3;

	/**
	 * An array of all the '<em><b>Inheritance Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final UMLRTInheritanceKind[] VALUES_ARRAY = new UMLRTInheritanceKind[] {
			INHERITED,
			REDEFINED,
			EXCLUDED,
			NONE,
	};

	/**
	 * A public read-only list of all the '<em><b>Inheritance Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<UMLRTInheritanceKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Inheritance Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTInheritanceKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UMLRTInheritanceKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Inheritance Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTInheritanceKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UMLRTInheritanceKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Inheritance Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UMLRTInheritanceKind get(int value) {
		switch (value) {
		case INHERITED_VALUE:
			return INHERITED;
		case REDEFINED_VALUE:
			return REDEFINED;
		case EXCLUDED_VALUE:
			return EXCLUDED;
		case NONE_VALUE:
			return NONE;
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
	private UMLRTInheritanceKind(int value, String name, String literal) {
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
	 * Queries the inheritance kind of an {@link element}.
	 *
	 * @param element
	 *            an element in the UML-RT model
	 *
	 * @return its inheritance kind: never {@code null}, but maybe {@link #NONE}
	 */
	public static UMLRTInheritanceKind of(Element element) {
		UMLRTInheritanceKind result = NONE;

		if (UMLRTExtensionUtil.isInherited(element)) {
			// Must check for exclusion first because virtual
			// elements may be excluded by an exclusion constraint
			// (case of pseudostates, which cannot be reified)
			if (UMLRTExtensionUtil.isExcluded(element)) {
				result = EXCLUDED;
			} else if (UMLRTExtensionUtil.isVirtualElement(element)) {
				result = INHERITED;
			} else {
				result = REDEFINED;
			}
		}

		return result;
	}

	/**
	 * Queries the inheritance kind of an {@link element}.
	 *
	 * @param element
	 *            an element in the UML-RT model
	 *
	 * @return its inheritance kind: never {@code null}, but maybe {@link #NONE}
	 */
	public static UMLRTInheritanceKind of(UMLRTNamedElement element) {
		return nullSafe(element.getInheritanceKind());
	}

	static UMLRTInheritanceKind nullSafe(UMLRTInheritanceKind kind) {
		return (kind == null) ? NONE : kind;
	}

	/**
	 * Obtains a comparator that sorts model elements by inheritance kind,
	 * in the order of the kind values, and then (for
	 * {@linkplain #INHERITED inherited} elements only) by depth of
	 * of inheritance from farthest (highest) to nearest (lowest) supertype.
	 *
	 * @return an inheritance-based comparator
	 */
	public static Comparator<Element> comparator() {
		Comparator<Element> firstStage = Comparator.comparing(UMLRTInheritanceKind::of);
		return firstStage.thenComparing(byInheritanceDepth());
	}

	/**
	 * Obtains a comparator that sorts model elements by inheritance kind,
	 * in the order of the kind values, and then (for
	 * {@linkplain #INHERITED inherited} elements only) by depth of
	 * of inheritance from farthest (highest) to nearest (lowest) supertype.
	 *
	 * @return an inheritance-based comparator
	 */
	public static Comparator<UMLRTNamedElement> facadeComparator() {
		return Comparator.comparing(UMLRTNamedElement::toUML, comparator());
	}

	/**
	 * A comparator by depth of redefinition, for elements that are inherited.
	 * Elements inherited from farther up the hierarchy precede elements inherited
	 * from nearer supertypes.
	 *
	 * @return the comparator by inheritance depth
	 */
	public static Comparator<Element> byInheritanceDepth() {
		return Comparator.comparingInt(inheritanceDepth());
	}

	private static ToIntFunction<Element> inheritanceDepth() {
		return element -> {
			// Redefined and excluded elements are local definitions; they have no depth
			int result = 0;

			if (UMLRTExtensionUtil.isVirtualElement(element)) {
				// It couldn't be virtual without implementing this interface
				InternalUMLRTElement rt = (InternalUMLRTElement) element;
				try {
					result = -inheritanceDepth(rt, 0);
				} catch (IllegalStateException e) {
					// This will cause problems elsewhere, so no need to log it. Just
					// return a zero depth
				}
			}

			return result;
		};
	}

	private static int inheritanceDepth(InternalUMLRTElement element, int depthSoFar) {
		int count = 0;

		for (InternalUMLRTElement rt = element.rtGetRedefinedElement(); rt != null; rt = rt.rtGetRedefinedElement()) {
			depthSoFar = depthSoFar + 1;

			// Since the cycle is detected by checking if we hit 'element' again, after many iterations
			// we'll call this method recursively in case we started with something that wasn't part of
			// a cycle but later traversed up to a cycle. Note that this technique is susceptible to
			// missing cycles of depth greater than a hundred thousand elements, but that seems an
			// acceptable risk
			if (++count > 100000) {
				return inheritanceDepth(rt, depthSoFar);
			} else if (rt == element) {
				throw new IllegalStateException("Redefinition cycle including " + rt); //$NON-NLS-1$
			}
		}

		return depthSoFar;
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

} // UMLRTInheritanceKind
