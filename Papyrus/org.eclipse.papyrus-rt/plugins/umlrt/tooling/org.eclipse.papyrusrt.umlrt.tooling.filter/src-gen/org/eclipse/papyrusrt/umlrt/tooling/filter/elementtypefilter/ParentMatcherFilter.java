/**
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter;

import org.eclipse.papyrus.infra.filters.Filter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parent Matcher Filter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ParentMatcherFilter#getParentType <em>Parent Type</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ParentMatcherFilter#isSubtypesCheck <em>Subtypes Check</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ElementtypefilterPackage#getParentMatcherFilter()
 * @model
 * @generated
 */
public interface ParentMatcherFilter extends Filter {
	/**
	 * Returns the value of the '<em><b>Parent Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Type</em>' attribute.
	 * @see #setParentType(String)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ElementtypefilterPackage#getParentMatcherFilter_ParentType()
	 * @model required="true"
	 * @generated
	 */
	String getParentType();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ParentMatcherFilter#getParentType <em>Parent Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Type</em>' attribute.
	 * @see #getParentType()
	 * @generated
	 */
	void setParentType(String value);

	/**
	 * Returns the value of the '<em><b>Subtypes Check</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subtypes Check</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subtypes Check</em>' attribute.
	 * @see #setSubtypesCheck(boolean)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ElementtypefilterPackage#getParentMatcherFilter_SubtypesCheck()
	 * @model
	 * @generated
	 */
	boolean isSubtypesCheck();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.filter.elementtypefilter.ParentMatcherFilter#isSubtypesCheck <em>Subtypes Check</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subtypes Check</em>' attribute.
	 * @see #isSubtypesCheck()
	 * @generated
	 */
	void setSubtypesCheck(boolean value);

} // ParentMatcherFilter
