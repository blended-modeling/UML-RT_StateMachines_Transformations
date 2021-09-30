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
import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.uml2.uml.Element;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Element</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExtension() <em>Get Extension</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement#getExcludedElements() <em>Get Excluded Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ElementOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElementOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static ExtElement getExtension(ExtElement element) {
		return element;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static EList<Element> getExcludedElements(ExtElement element) {
		Element uml = element.getExtendedElement();
		return getExcludedElements(uml);
	}

	/**
	 * Computes the excluded (owned) elements of an {@code element}.
	 *
	 * @param element
	 *            an UML element
	 * @return its owned elements that would otherwise be inherited but are actually excluded
	 */
	public static EList<Element> getExcludedElements(Element element) {
		Element[] result = element.getOwnedElements().stream()
				.filter(m -> (m instanceof InternalUMLRTElement) && ((InternalUMLRTElement) m).rtIsExcluded())
				.toArray(Element[]::new);
		return new BasicEList.UnmodifiableEList<>(result.length, result);
	}

} // ElementOperations
