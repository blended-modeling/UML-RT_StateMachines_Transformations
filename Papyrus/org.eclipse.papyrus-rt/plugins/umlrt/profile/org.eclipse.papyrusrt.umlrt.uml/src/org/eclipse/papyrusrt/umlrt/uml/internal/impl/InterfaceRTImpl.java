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

import static org.eclipse.papyrusrt.umlrt.uml.internal.util.DerivedUnionEObjectEListExt.extendSubsets;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.InterfaceRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtInterface;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionHolder;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.DerivedUnionEObjectEListExt;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * UML-RT semantics for {@link org.eclipse.uml2.uml.Interface}.
 */
public class InterfaceRTImpl extends org.eclipse.uml2.uml.internal.impl.InterfaceImpl implements InternalUMLRTClassifier {

	private static final int[] EXT_OWNED_MEMBER_ESUBSETS = extendSubsets(OWNED_MEMBER_ESUBSETS,
			ExtUMLExtPackage.INTERFACE__IMPLICIT_OPERATION);

	private final ExtensionHolder extension = new ExtensionHolder(this);

	protected InterfaceRTImpl() {
		super();
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		return (T) super.eGet(featureID, true, true);
	}

	@Override
	public void rtUnsetAll() {
		// Extended containments
		ElementRTOperations.destroyAll(this, getOwnedOperations());
	}

	@Override
	public void rtCreateExtension() {
		extension.createExtension();
	}

	@Override
	public void rtDestroyExtension() {
		extension.destroyExtension();
	}

	@Override
	public boolean rtHasExtension() {
		return extension.hasExtension();
	}

	@Override
	public <T extends EObject> T rtExtension(java.lang.Class<T> extensionClass) {
		return extension.getExtension(extensionClass.asSubclass(ExtElement.class));
	}

	@Override
	public void rtSuppressForwardingWhile(Runnable action) {
		extension.suppressForwardingWhile(action);
	}

	@Override
	public void rtInherit(InternalUMLRTClassifier supertype) {
		if (supertype instanceof Interface) {
			InterfaceRTOperations.rtInherit(this, (InternalUMLRTClassifier & Interface) supertype);
		}
	}

	@Override
	public void rtDisinherit(InternalUMLRTClassifier supertype) {
		if (supertype instanceof Interface) {
			InterfaceRTOperations.rtDisinherit(this, (InternalUMLRTClassifier & Interface) supertype);
		}
	}

	@Override
	public int eDerivedStructuralFeatureID(EStructuralFeature eStructuralFeature) {
		return extension.getDerivedStructuralFeatureID(eStructuralFeature);
	}

	@Override
	public boolean eDynamicIsSet(int featureID) {
		return (featureID <= ExtensionHolder.UML_EXTENSION_FEATURE_BASE)
				? extension.isSet(featureID)
				: super.eDynamicIsSet(featureID);
	}

	@Override
	public boolean eOpenIsSet(EStructuralFeature eFeature) {
		return extension.isSet(eFeature);
	}

	@Override
	public Object eDynamicGet(int featureID, boolean resolve, boolean coreType) {
		return (featureID <= ExtensionHolder.UML_EXTENSION_FEATURE_BASE)
				? extension.get(featureID, resolve)
				: super.eDynamicGet(featureID, resolve, coreType);
	}

	@Override
	public Object eOpenGet(EStructuralFeature eFeature, boolean resolve) {
		return extension.get(eFeature, resolve);
	}

	@Override
	public void eDynamicSet(int featureID, Object newValue) {
		if (featureID <= ExtensionHolder.UML_EXTENSION_FEATURE_BASE) {
			extension.set(featureID, newValue);
		} else {
			super.eDynamicSet(featureID, newValue);
		}
	}

	@Override
	public void eOpenSet(EStructuralFeature eFeature, Object newValue) {
		extension.set(eFeature, newValue);
	}

	@Override
	public void eDynamicUnset(int featureID) {
		if (featureID <= ExtensionHolder.UML_EXTENSION_FEATURE_BASE) {
			extension.unset(featureID);
		} else {
			super.eDynamicUnset(featureID);
		}
	}

	@Override
	public void eOpenUnset(EStructuralFeature eFeature) {
		extension.unset(eFeature);
	}

	@Override
	public Setting eSetting(EStructuralFeature eFeature) {
		int featureID = eDerivedStructuralFeatureID(eFeature);
		return (featureID <= ExtensionHolder.UML_EXTENSION_FEATURE_BASE)
				? extension.setting(eFeature)
				: super.eSetting(eFeature);
	}

	@Override
	public EList<EObject> eContents() {
		return extension.getContents(this);
	}

	@Override
	public EList<NamedElement> getOwnedMembers() {
		EList<NamedElement> result;

		CacheAdapter cache = getCacheAdapter();
		if (cache == null) {
			result = new DerivedUnionEObjectEListExt<>(NamedElement.class,
					this, UMLPackage.INTERFACE__OWNED_MEMBER, EXT_OWNED_MEMBER_ESUBSETS);
		} else {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			EList<NamedElement> ownedMembers = (EList<NamedElement>) cache.get(
					eResource, this, UMLPackage.Literals.NAMESPACE__OWNED_MEMBER);
			if (ownedMembers == null) {
				ownedMembers = new DerivedUnionEObjectEListExt<>(
						NamedElement.class, this,
						UMLPackage.INTERFACE__OWNED_MEMBER, EXT_OWNED_MEMBER_ESUBSETS);
				cache.put(eResource, this,
						UMLPackage.Literals.NAMESPACE__OWNED_MEMBER,
						ownedMembers);
			}
			result = ownedMembers;
		}

		return result;
	}

	@Override
	public boolean isSetOwnedMembers() {
		return super.isSetOwnedMembers()
				|| eIsSet(ExtUMLExtPackage.Literals.INTERFACE__IMPLICIT_OPERATION);
	}

	@Override
	public Operation getOwnedOperation(String name, EList<String> ownedParameterNames, EList<Type> ownedParameterTypes, boolean ignoreCase, boolean createOnDemand) {
		Operation result = super.getOwnedOperation(name, ownedParameterNames, ownedParameterTypes, ignoreCase, createOnDemand);

		if ((result == null) && extension.hasExtension()) {
			result = extension.getExtension(ExtInterface.class)
					.getImplicitOperation(name, ownedParameterNames, ownedParameterTypes, ignoreCase, false);
		}

		if ((result == null) && createOnDemand) {
			result = createOwnedOperation(name, ownedParameterNames, ownedParameterTypes);
		}

		return result;
	}

	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		return ElementRTOperations.toString(this, super.toString());
	}

}
