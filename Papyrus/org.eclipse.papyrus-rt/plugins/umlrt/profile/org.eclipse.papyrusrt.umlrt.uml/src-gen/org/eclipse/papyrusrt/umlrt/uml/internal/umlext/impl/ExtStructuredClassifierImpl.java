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
package org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStructuredClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtEObjectContainmentEList;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Structured Classifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStructuredClassifierImpl#getImplicitOwnedMembers <em>Implicit Owned Member</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStructuredClassifierImpl#getImplicitAttributes <em>Implicit Attribute</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtStructuredClassifierImpl#getImplicitConnectors <em>Implicit Connector</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ExtStructuredClassifierImpl extends ExtNamespaceImpl implements ExtStructuredClassifier {
	/**
	 * The cached value of the '{@link #getImplicitAttributes() <em>Implicit Attribute</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> implicitAttributes;

	/**
	 * The cached value of the '{@link #getImplicitConnectors() <em>Implicit Connector</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitConnectors()
	 * @generated
	 * @ordered
	 */
	protected EList<Connector> implicitConnectors;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtStructuredClassifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NamedElement> getImplicitOwnedMembers() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			EList<NamedElement> implicitOwnedMembers = (EList<NamedElement>) cache.get(eResource, this, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER);
			if (implicitOwnedMembers == null) {
				cache.put(eResource, this, ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_MEMBER,
						implicitOwnedMembers = new DerivedUnionEObjectEList<>(NamedElement.class, this, ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_OWNED_MEMBER, IMPLICIT_OWNED_MEMBER_ESUBSETS));
			}
			return implicitOwnedMembers;
		}
		return new DerivedUnionEObjectEList<>(NamedElement.class, this, ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_OWNED_MEMBER, IMPLICIT_OWNED_MEMBER_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getImplicitOwnedMembers() <em>Implicit Owned Member</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitOwnedMembers()
	 * @generated
	 * @ordered
	 */
	protected static final int[] IMPLICIT_OWNED_MEMBER_ESUBSETS = new int[] { ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_OWNED_RULE, ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE, ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated not
	 */
	@Override
	public EList<Property> getImplicitAttributes() {
		if (implicitAttributes == null) {
			implicitAttributes = new ExtEObjectContainmentEList<>(Property.class, this, ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE);
		}
		return implicitAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Property createImplicitAttribute(String name, Type type, EClass eClass) {
		Property newImplicitAttribute = (Property) create(eClass);
		getImplicitAttributes().add(newImplicitAttribute);
		if (name != null) {
			newImplicitAttribute.setName(name);
		}
		if (type != null) {
			newImplicitAttribute.setType(type);
		}
		return newImplicitAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Property createImplicitAttribute(String name, Type type) {
		return createImplicitAttribute(name, type, UMLPackage.Literals.PROPERTY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Property getImplicitAttribute(String name, Type type) {
		return getImplicitAttribute(name, type, false, null, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Property getImplicitAttribute(String name, Type type, boolean ignoreCase, EClass eClass, boolean createOnDemand) {
		implicitAttributeLoop: for (Property implicitAttribute : getImplicitAttributes()) {
			if (eClass != null && !eClass.isInstance(implicitAttribute)) {
				continue implicitAttributeLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(implicitAttribute.getName()) : name.equals(implicitAttribute.getName()))) {
				continue implicitAttributeLoop;
			}
			if (type != null && !type.equals(implicitAttribute.getType())) {
				continue implicitAttributeLoop;
			}
			return implicitAttribute;
		}
		return createOnDemand && eClass != null ? createImplicitAttribute(name, type, eClass) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated not
	 */
	@Override
	public EList<Connector> getImplicitConnectors() {
		if (implicitConnectors == null) {
			implicitConnectors = new ExtEObjectContainmentEList<>(Connector.class, this, ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR);
		}
		return implicitConnectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Connector createImplicitConnector(String name) {
		Connector newImplicitConnector = (Connector) create(UMLPackage.Literals.CONNECTOR);
		getImplicitConnectors().add(newImplicitConnector);
		if (name != null) {
			newImplicitConnector.setName(name);
		}
		return newImplicitConnector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Connector getImplicitConnector(String name) {
		return getImplicitConnector(name, false, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Connector getImplicitConnector(String name, boolean ignoreCase, boolean createOnDemand) {
		implicitConnectorLoop: for (Connector implicitConnector : getImplicitConnectors()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(implicitConnector.getName()) : name.equals(implicitConnector.getName()))) {
				continue implicitConnectorLoop;
			}
			return implicitConnector;
		}
		return createOnDemand ? createImplicitConnector(name) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE:
			return ((InternalEList<?>) getImplicitAttributes()).basicRemove(otherEnd, msgs);
		case ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR:
			return ((InternalEList<?>) getImplicitConnectors()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE:
			return getImplicitAttributes();
		case ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR:
			return getImplicitConnectors();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE:
			getImplicitAttributes().clear();
			getImplicitAttributes().addAll((Collection<? extends Property>) newValue);
			return;
		case ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR:
			getImplicitConnectors().clear();
			getImplicitConnectors().addAll((Collection<? extends Connector>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE:
			getImplicitAttributes().clear();
			return;
		case ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR:
			getImplicitConnectors().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_OWNED_MEMBER:
			return isSetImplicitOwnedMembers();
		case ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE:
			return implicitAttributes != null && !implicitAttributes.isEmpty();
		case ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR:
			return implicitConnectors != null && !implicitConnectors.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetImplicitOwnedMembers() {
		return super.isSetImplicitOwnedMembers()
				|| eIsSet(ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE)
				|| eIsSet(ExtUMLExtPackage.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR);
	}

} // StructuredClassifierImpl
