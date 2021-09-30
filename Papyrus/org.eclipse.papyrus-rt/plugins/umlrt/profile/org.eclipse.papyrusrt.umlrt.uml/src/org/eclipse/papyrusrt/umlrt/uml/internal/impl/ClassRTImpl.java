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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ClassRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtClass;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionHolder;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.DerivedUnionEObjectEListExt;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.RTNotificationImpl;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * UML-RT semantics for {@link org.eclipse.uml2.uml.Class}.
 */
public class ClassRTImpl extends org.eclipse.uml2.uml.internal.impl.ClassImpl implements InternalUMLRTClassifier {

	private static final int CLASSIFIER_BEHAVIOR__SET_FLAG = 0x1 << 0;

	private static final int[] EXT_OWNED_MEMBER_ESUBSETS = extendSubsets(OWNED_MEMBER_ESUBSETS,
			ExtUMLExtPackage.CLASS__IMPLICIT_ATTRIBUTE,
			ExtUMLExtPackage.CLASS__IMPLICIT_CONNECTOR,
			ExtUMLExtPackage.CLASS__IMPLICIT_BEHAVIOR,
			ExtUMLExtPackage.CLASS__IMPLICIT_OPERATION);

	private int uFlags = 0x0;

	private final ExtensionHolder extension = new ExtensionHolder(this);

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.BEHAVIORED_CLASSIFIER__CLASSIFIER_BEHAVIOR
	/* Don't include 'ownedBehavior' because it forwards for itself */));

	protected ClassRTImpl() {
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

	@Override
	public void rtUnsetAll() {
		unsetClassifierBehavior();

		// Extended containments
		ElementRTOperations.destroyAll(this, getOwnedAttributes());
		ElementRTOperations.destroyAll(this, getOwnedConnectors());
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLPackage.CLASS__CLASSIFIER_BEHAVIOR:
			return isSetClassifierBehavior();
		default:
			return super.eIsSet(featureID);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.CLASS__CLASSIFIER_BEHAVIOR:
			unsetClassifierBehavior();
			break;
		case UMLPackage.CLASS__OWNED_BEHAVIOR:
			super.eUnset(featureID);
			// It's a superset of an unsettable feature (in UML-RT),
			// so unset the subset
			unsetClassifierBehavior();
		default:
			super.eUnset(featureID);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.CLASS__CLASSIFIER_BEHAVIOR:
			return (T) super.getClassifierBehavior();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	@Override
	public Function<? super EObject, ? extends EObject> rtGetInheritanceResolver(EReference reference) {
		Function<? super EObject, ? extends EObject> result = null;

		if (reference == UMLPackage.Literals.BEHAVIORED_CLASSIFIER__CLASSIFIER_BEHAVIOR) {
			result = this::resolveClassifierBehavior;
		}

		return result;
	}

	Behavior resolveClassifierBehavior(EObject inherited) {
		Behavior result = null;

		// Currently, only state machines are supported as UML-RT classifier behaviors
		// with UML-RT inheritance semantics
		if (inherited instanceof StateMachine) {
			Behavior inheritedBehavior = (Behavior) inherited;
			result = inheritedBehavior;

			// Find the owned rule that redefines this guard
			for (Behavior next : UMLRTExtensionUtil.<Behavior> getUMLRTContents(this, UMLPackage.Literals.BEHAVIORED_CLASSIFIER__OWNED_BEHAVIOR)) {
				if ((next instanceof InternalUMLRTElement)
						&& (((InternalUMLRTElement) next).rtGetRedefinedElement() == inheritedBehavior)) {

					result = next;
					break;
				}
			}
		}

		return result;
	}

	@Override
	public void rtInherit(InternalUMLRTClassifier supertype) {
		if (supertype instanceof Class) {
			ClassRTOperations.rtInherit(this, (InternalUMLRTClassifier & Class) supertype);
		}
	}

	@Override
	public void rtDisinherit(InternalUMLRTClassifier supertype) {
		if (supertype instanceof Class) {
			ClassRTOperations.rtDisinherit(this, (InternalUMLRTClassifier & Class) supertype);
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
					this, UMLPackage.CLASS__OWNED_MEMBER, EXT_OWNED_MEMBER_ESUBSETS);
		} else {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			EList<NamedElement> ownedMembers = (EList<NamedElement>) cache.get(
					eResource, this, UMLPackage.Literals.NAMESPACE__OWNED_MEMBER);
			if (ownedMembers == null) {
				ownedMembers = new DerivedUnionEObjectEListExt<>(
						NamedElement.class, this,
						UMLPackage.CLASS__OWNED_MEMBER, EXT_OWNED_MEMBER_ESUBSETS);
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
				|| eIsSet(ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE)
				|| eIsSet(ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR)
				|| eIsSet(ExtUMLExtPackage.Literals.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR)
				|| eIsSet(ExtUMLExtPackage.Literals.CLASS__IMPLICIT_OPERATION);
	}

	@Override
	public Port getOwnedPort(String name, Type type, boolean ignoreCase, boolean createOnDemand) {
		Port result = super.getOwnedPort(name, type, ignoreCase, createOnDemand);

		if ((result == null) && extension.hasExtension()) {
			result = extension.getExtension(ExtClass.class)
					.getImplicitPort(name, type, ignoreCase, false);
		}

		if ((result == null) && createOnDemand) {
			result = createOwnedPort(name, type);
		}

		return result;
	}

	@Override
	public Property getOwnedAttribute(String name, Type type, boolean ignoreCase, EClass eClass, boolean createOnDemand) {
		Property result = super.getOwnedAttribute(name, type, ignoreCase, eClass, createOnDemand);

		if ((result == null) && extension.hasExtension()) {
			result = extension.getExtension(ExtClass.class)
					.getImplicitAttribute(name, type, ignoreCase, eClass, false);
		}

		if ((result == null) && createOnDemand) {
			result = createOwnedAttribute(name, type, eClass);
		}

		return result;
	}

	@Override
	public Connector getOwnedConnector(String name, boolean ignoreCase, boolean createOnDemand) {
		Connector result = super.getOwnedConnector(name, ignoreCase, createOnDemand);

		if ((result == null) && extension.hasExtension()) {
			result = extension.getExtension(ExtClass.class)
					.getImplicitConnector(name, ignoreCase, false);
		}

		if ((result == null) && createOnDemand) {
			result = createOwnedConnector(name);
		}

		return result;
	}

	@Override
	public Operation getOwnedOperation(String name, EList<String> ownedParameterNames, EList<Type> ownedParameterTypes, boolean ignoreCase, boolean createOnDemand) {
		Operation result = super.getOwnedOperation(name, ownedParameterNames, ownedParameterTypes, ignoreCase, createOnDemand);

		if ((result == null) && extension.hasExtension()) {
			result = extension.getExtension(ExtClass.class)
					.getImplicitOperation(name, ownedParameterNames, ownedParameterTypes, ignoreCase, false);
		}

		if ((result == null) && createOnDemand) {
			result = createOwnedOperation(name, ownedParameterNames, ownedParameterTypes);
		}

		return result;
	}

	@Override
	public Behavior getOwnedBehavior(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand) {
		Behavior result = super.getOwnedBehavior(name, ignoreCase, eClass, createOnDemand);

		if ((result == null) && extension.hasExtension()) {
			result = extension.getExtension(ExtClass.class)
					.getImplicitBehavior(name, ignoreCase, eClass, false);
		}

		if ((result == null) && createOnDemand) {
			result = createOwnedBehavior(name, eClass);
		}

		return result;
	}

	@Override
	public Behavior getClassifierBehavior() {
		return inheritFeature(UMLPackage.Literals.BEHAVIORED_CLASSIFIER__CLASSIFIER_BEHAVIOR);
	}

	@Override
	public void setClassifierBehavior(Behavior newClassifierBehavior) {
		// Make sure that the notification gets the correct old value
		boolean wasSet = isSetClassifierBehavior();
		uFlags = uFlags | CLASSIFIER_BEHAVIOR__SET_FLAG;
		Behavior oldClassifierBehavior = classifierBehavior;
		classifierBehavior = newClassifierBehavior;
		if (eNotificationRequired()) {
			eNotify(new RTNotificationImpl(this, Notification.SET,
					UMLPackage.CLASS__CLASSIFIER_BEHAVIOR, oldClassifierBehavior, classifierBehavior, !wasSet));
		}

		Resource.Internal eInternalResource = eInternalResource();
		if ((eInternalResource == null) || !eInternalResource.isLoading()) {
			if (newClassifierBehavior != null) {
				EList<Behavior> ownedBehaviors = getOwnedBehaviors();
				if (!ownedBehaviors.contains(newClassifierBehavior)) {
					ownedBehaviors.add(newClassifierBehavior);
				}
			}
		}
	}

	public boolean isSetClassifierBehavior() {
		return (uFlags & CLASSIFIER_BEHAVIOR__SET_FLAG) != 0;
	}

	public void unsetClassifierBehavior() {
		// Make sure that the notification gets the correct old and new values
		Behavior oldClassifierBehavior = getClassifierBehavior();
		boolean oldClassifierBehaviorESet = (uFlags & CLASSIFIER_BEHAVIOR__SET_FLAG) != 0;
		classifierBehavior = null;
		uFlags = uFlags & ~CLASSIFIER_BEHAVIOR__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new RTNotificationImpl(this, Notification.UNSET, UMLPackage.CLASS__CLASSIFIER_BEHAVIOR, oldClassifierBehavior, getClassifierBehavior(), oldClassifierBehaviorESet));
		}
	}

	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		return ElementRTOperations.toString(this, super.toString());
	}
}
