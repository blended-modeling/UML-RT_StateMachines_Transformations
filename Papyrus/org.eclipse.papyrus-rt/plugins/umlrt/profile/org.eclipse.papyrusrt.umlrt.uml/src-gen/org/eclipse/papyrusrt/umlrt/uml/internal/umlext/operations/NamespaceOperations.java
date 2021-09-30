/**
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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
package org.eclipse.papyrusrt.umlrt.uml.internal.umlext.operations;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Namespace</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtNamespace#getExcludedMembers() <em>Get Excluded Members</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NamespaceOperations extends ElementOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected NamespaceOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static EList<NamedElement> getExcludedMembers(ExtNamespace namespace) {
		Namespace uml = (Namespace) namespace.getExtendedElement();
		return (uml == null) ? ECollections.emptyEList() : getExcludedMembers(uml);
	}

	/**
	 * Computes the excluded members of a {@code namespace}.
	 *
	 * @param namespace
	 *            an UML namespace
	 * @return its members that would otherwise be inherited but are actually excluded
	 */
	public static EList<NamedElement> getExcludedMembers(Namespace namespace) {
		NamedElement[] result = namespace.getOwnedMembers().stream()
				.filter(m -> (m instanceof InternalUMLRTElement) && ((InternalUMLRTElement) m).rtIsExcluded())
				.toArray(NamedElement[]::new);
		return new BasicEList.UnmodifiableEList<>(result.length, result);
	}

} // NamespaceOperations
