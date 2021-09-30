/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ValueSpecificationRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableEcoreEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.NotificationForwarder;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * UML-RT semantics for {@link OpaqueExpression}.
 */
public class OpaqueExpressionRTImpl extends org.eclipse.uml2.uml.internal.impl.OpaqueExpressionImpl implements InternalUMLRTValueSpecification {

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.OPAQUE_EXPRESSION__BODY,
			UMLPackage.Literals.OPAQUE_EXPRESSION__LANGUAGE));

	public OpaqueExpressionRTImpl() {
		super();

		class InheritableValueList<E> extends InheritableEcoreEList<E> {
			private static final long serialVersionUID = 1L;

			InheritableValueList(int featureID) {
				super(OpaqueExpressionRTImpl.this, featureID);
			}

			@Override
			public boolean isSet() {
				// I can only inherit my value if I am a virtual element
				return super.isSet() || !rtIsVirtual();
			}
		}

		this.bodies = new InheritableValueList<>(UMLPackage.OPAQUE_EXPRESSION__BODY);
		this.languages = new InheritableValueList<>(UMLPackage.OPAQUE_EXPRESSION__LANGUAGE);
	}

	@Override
	public EObject create(EClass eClass) {
		EObject result;

		if (eClass.getEPackage() == eClass().getEPackage()) {
			result = UMLRTUMLFactoryImpl.eINSTANCE.create(eClass);
		} else {
			result = super.create(eClass);
		}

		return result;
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLPackage.OPAQUE_EXPRESSION__BODY:
			return isSetBodies();
		case UMLPackage.OPAQUE_EXPRESSION__LANGUAGE:
			return isSetLanguages();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.OPAQUE_EXPRESSION__BODY:
			return (T) super.getBodies();
		case UMLPackage.OPAQUE_EXPRESSION__LANGUAGE:
			return (T) super.getLanguages();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.OPAQUE_EXPRESSION__BODY:
			unsetBodies();
			break;
		case UMLPackage.OPAQUE_EXPRESSION__LANGUAGE:
			unsetLanguages();
			break;
		default:
			super.eUnset(featureID);
			break;
		}
	}

	@Override
	public EObject eContainer() {
		Element owner = rtOwner();
		return (owner != null) ? owner : super.eContainer();
	}

	@Override
	public Resource eResource() {
		Resource result = rtResource();

		if (result instanceof ExtensionResource) {
			EObject container = eContainer();
			if (container != null) {
				result = container.eResource();
			}
		}

		return result;
	}

	@Override
	public <R extends InternalUMLRTElement> R rtGetRedefinedElement() {
		return ValueSpecificationRTOperations.rtGetRedefinedElement(this);
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		ValueSpecificationRTOperations.umlSetRedefinedElement(this, redefined);
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	@Override
	public void handleRedefinedMultiplicityElement(MultiplicityElement mult) {
		if (mult != null) {
			NotificationForwarder.adapt(this,
					() -> new NotificationForwarder(this, null, rtInheritedFeatures()));
		} else if (eBasicHasAdapters()) {
			NotificationForwarder.unadapt(this);
		}
	}

	@Override
	public void handleRedefinedConstraint(Constraint constraint) {
		if (constraint != null) {
			NotificationForwarder.adapt(this,
					() -> new NotificationForwarder(this, null, rtInheritedFeatures()));
		} else if (eBasicHasAdapters()) {
			NotificationForwarder.unadapt(this);
		}
	}

	@Override
	public void rtReify() {
		ValueSpecificationRTOperations.rtReify(this);
	}

	@Override
	public void rtVirtualize() {
		ValueSpecificationRTOperations.rtVirtualize(this);
	}

	@Override
	public void rtUnsetAll() {
		unsetBodies();
		unsetLanguages();
	}

	@Override
	public Element basicGetOwner() {
		return rtOwner();
	}

	@Override
	public EList<String> getBodies() {
		EList<String> result = inheritFeature(UMLPackage.Literals.OPAQUE_EXPRESSION__BODY);

		if (result != bodies) {
			// Inherit this into our list. If it's null, that means we are the root definition
			((InheritableEList<String>) bodies).inherit(result);
			result = bodies;
		}

		return bodies;
	}

	@Override
	public EList<String> getLanguages() {
		EList<String> result = inheritFeature(UMLPackage.Literals.OPAQUE_EXPRESSION__LANGUAGE);

		if (result != languages) {
			// Inherit this into our list. If it's null, that means we are the root definition
			((InheritableEList<String>) languages).inherit(result);
			result = languages;
		}

		return languages;
	}

	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		return ElementRTOperations.toString(this, super.toString());
	}
}
