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
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtEncapsulatedClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtEObjectContainmentEList;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedSubsetEObjectEList;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Encapsulated Classifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtEncapsulatedClassifierImpl#getImplicitAttributes <em>Implicit Attribute</em>}</li>
 *   <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.ExtEncapsulatedClassifierImpl#getImplicitPorts <em>Implicit Port</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ExtEncapsulatedClassifierImpl extends ExtStructuredClassifierImpl implements ExtEncapsulatedClassifier {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtEncapsulatedClassifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated not
	 */
	@Override
	public EList<Property> getImplicitAttributes() {
		if (implicitAttributes == null) {
			implicitAttributes = new ExtEObjectContainmentEList<>(Property.class, this, ExtUMLExtPackage.ENCAPSULATED_CLASSIFIER__IMPLICIT_ATTRIBUTE);
		}
		return implicitAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public EList<Port> getImplicitPorts() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			EList<Port> implicitPorts = (EList<Port>) cache.get(eResource, this, ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT);
			if (implicitPorts == null) {
				cache.put(eResource, this,
						ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT,
						implicitPorts = new DerivedSubsetEObjectEList<>(Port.class,
								this,
								ExtUMLExtPackage.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT,
								IMPLICIT_PORT_ESUPERSETS));
			}
			return implicitPorts;
		}
		return new DerivedSubsetEObjectEList<>(Port.class,
				this,
				ExtUMLExtPackage.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT,
				IMPLICIT_PORT_ESUPERSETS);
	}

	/**
	 * The array of superset feature identifiers for the '{@link #getImplicitPorts() <em>Implicit Port</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitPorts()
	 * @generated
	 * @ordered
	 */
	protected static final int[] IMPLICIT_PORT_ESUPERSETS = new int[] { ExtUMLExtPackage.ENCAPSULATED_CLASSIFIER__IMPLICIT_ATTRIBUTE };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Port createImplicitPort(String name, Type type) {
		Port newImplicitPort = (Port) create(UMLPackage.Literals.PORT);
		getImplicitPorts().add(newImplicitPort);
		if (name != null) {
			newImplicitPort.setName(name);
		}
		if (type != null) {
			newImplicitPort.setType(type);
		}
		return newImplicitPort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Port getImplicitPort(String name, Type type) {
		return getImplicitPort(name, type, false, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Port getImplicitPort(String name, Type type, boolean ignoreCase, boolean createOnDemand) {
		implicitPortLoop: for (Port implicitPort : getImplicitPorts()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(implicitPort.getName()) : name.equals(implicitPort.getName()))) {
				continue implicitPortLoop;
			}
			if (type != null && !type.equals(implicitPort.getType())) {
				continue implicitPortLoop;
			}
			return implicitPort;
		}
		return createOnDemand ? createImplicitPort(name, type) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ExtUMLExtPackage.ENCAPSULATED_CLASSIFIER__IMPLICIT_ATTRIBUTE:
			return ((InternalEList<?>) getImplicitAttributes()).basicRemove(otherEnd, msgs);
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
		case ExtUMLExtPackage.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT:
			return getImplicitPorts();
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
		case ExtUMLExtPackage.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT:
			getImplicitPorts().clear();
			getImplicitPorts().addAll((Collection<? extends Port>) newValue);
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
		case ExtUMLExtPackage.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT:
			getImplicitPorts().clear();
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
		case ExtUMLExtPackage.ENCAPSULATED_CLASSIFIER__IMPLICIT_ATTRIBUTE:
			return implicitAttributes != null && !implicitAttributes.isEmpty();
		case ExtUMLExtPackage.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT:
			return !getImplicitPorts().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // EncapsulatedClassifierImpl
