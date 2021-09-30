/**
 * Copyright (c) 2016 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes;

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.ViewToDisplay;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>UMLRT Set Type Advice Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getElementType <em>Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getNewTypeViewsToDisplay <em>New Type Views To Display</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getNewTypeViews <em>New Type Views</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getElementTypeLabel <em>Element Type Label</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage#getUMLRTSetTypeAdviceConfiguration()
 * @model
 * @generated
 */
public interface UMLRTSetTypeAdviceConfiguration extends AbstractAdviceBindingConfiguration {

	/**
	 * Returns the value of the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Identifier of the element type to create or select for the value of the
	 * edited element's type.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Element Type</em>' attribute.
	 * @see #setElementType(String)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage#getUMLRTSetTypeAdviceConfiguration_ElementType()
	 * @model required="true"
	 * @generated
	 */
	String getElementType();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getElementType <em>Element Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Element Type</em>' attribute.
	 * @see #getElementType()
	 * @generated
	 */
	void setElementType(String value);

	/**
	 * Returns the value of the '<em><b>New Type Views To Display</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.emf.types.ui.advices.values.ViewToDisplay}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Views to display in the dialog that edits the newly created
	 * type. If none, then no dialog is shown and it is accepted
	 * as created by its element-type (there is no validation in
	 * this case).
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>New Type Views To Display</em>' containment reference list.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage#getUMLRTSetTypeAdviceConfiguration_NewTypeViewsToDisplay()
	 * @model containment="true"
	 * @generated
	 */
	EList<ViewToDisplay> getNewTypeViewsToDisplay();

	/**
	 * Returns the value of the '<em><b>New Type Views</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.properties.contexts.View}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Derived collection of the views to present in the dialog,
	 * from the owned views-to-display.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>New Type Views</em>' reference list.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage#getUMLRTSetTypeAdviceConfiguration_NewTypeViews()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	EList<View> getNewTypeViews();

	/**
	 * Returns the value of the '<em><b>Element Type Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Optional label to use for the referenced elementType in the case that its own
	 * name is not user-friendly or should be customized for the specific context in
	 * which the advice is used. If unset, the value is derived from the elementType's
	 * name.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Element Type Label</em>' attribute.
	 * @see #isSetElementTypeLabel()
	 * @see #unsetElementTypeLabel()
	 * @see #setElementTypeLabel(String)
	 * @see org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTTypesPackage#getUMLRTSetTypeAdviceConfiguration_ElementTypeLabel()
	 * @model unsettable="true"
	 * @generated
	 */
	String getElementTypeLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getElementTypeLabel <em>Element Type Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Element Type Label</em>' attribute.
	 * @see #isSetElementTypeLabel()
	 * @see #unsetElementTypeLabel()
	 * @see #getElementTypeLabel()
	 * @generated
	 */
	void setElementTypeLabel(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getElementTypeLabel <em>Element Type Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSetElementTypeLabel()
	 * @see #getElementTypeLabel()
	 * @see #setElementTypeLabel(String)
	 * @generated
	 */
	void unsetElementTypeLabel();

	/**
	 * Returns whether the value of the '{@link org.eclipse.papyrusrt.umlrt.tooling.types.umlrttypes.UMLRTSetTypeAdviceConfiguration#getElementTypeLabel <em>Element Type Label</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return whether the value of the '<em>Element Type Label</em>' attribute is set.
	 * @see #unsetElementTypeLabel()
	 * @see #getElementTypeLabel()
	 * @see #setElementTypeLabel(String)
	 * @generated
	 */
	boolean isSetElementTypeLabel();
} // UMLRTSetTypeAdviceConfiguration
