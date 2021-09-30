/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTRegion;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.StateMachineRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionHolder;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.DerivedUnionEObjectEListExt;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritanceAdapter;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.RedefinedElementsList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.RedefinedElementsListImpl;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.SubsetSupersetRedefinedElementsList;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.internal.impl.StateMachineImpl;

/**
 * UML-RT semantics for {@link StateMachine}.
 */
public class StateMachineRTImpl extends StateMachineImpl implements InternalUMLRTStateMachine {

	private static final int[] EXT_OWNED_MEMBER_ESUBSETS = extendSubsets(OWNED_MEMBER_ESUBSETS,
			ExtUMLExtPackage.STATE_MACHINE__IMPLICIT_REGION,
			ExtUMLExtPackage.STATE_MACHINE__IMPLICIT_CONNECTION_POINT);

	private final ExtensionHolder extension = new ExtensionHolder(this);

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.NAMED_ELEMENT__NAME,
			UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY));

	protected StateMachineRTImpl() {
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLPackage.STATE_MACHINE__NAME:
			return isSetName();
		case UMLPackage.STATE_MACHINE__VISIBILITY:
			return isSetVisibility();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.STATE_MACHINE__NAME:
			return (T) super.getName();
		case UMLPackage.STATE_MACHINE__VISIBILITY:
			return (T) super.getVisibility();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof StateMachine)) {
			throw new IllegalArgumentException("not a state machine: " + redefined); //$NON-NLS-1$
		}

		StateMachine redefinedSM = (StateMachine) redefined;
		((RedefinedElementsList<Behavior>) getRedefinedBehaviors()).setRedefinedElement(redefinedSM);

		if (!getRedefinedClassifiers().contains(redefinedSM)) {
			// Work around bug 511674
			getRedefinedClassifiers().add(redefinedSM);
		}

		InheritanceAdapter.getInstance(this);
	}

	@Override
	public EList<StateMachine> getExtendedStateMachines() {
		if (extendedStateMachines == null) {
			extendedStateMachines = new RedefinedElementsListImpl<>(
					StateMachine.class, this, UMLPackage.STATE_MACHINE__EXTENDED_STATE_MACHINE,
					this::handleRedefinedStateMachine);
		}
		return extendedStateMachines;
	}

	@Override
	public EList<Classifier> getRedefinedClassifiers() {
		if (redefinedClassifiers == null) {
			redefinedClassifiers = new SubsetSupersetRedefinedElementsList<Classifier>(
					Classifier.class, this,
					UMLPackage.BEHAVIOR__REDEFINED_CLASSIFIER, null,
					REDEFINED_CLASSIFIER_ESUBSETS);
		}
		return redefinedClassifiers;
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	void handleRedefinedStateMachine(StateMachine stateMachine) {
		// TODO
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
	public void rtReify() {
		BehavioredClassifier context = getContext();
		if (context instanceof InternalUMLRTClassifier) {
			if (context.getClassifierBehavior() == this) {
				// I am the classifier behavior
				context.setClassifierBehavior(this);
			} else {
				// I am just an owned behavior
				context.getOwnedBehaviors().add(this);
			}

			rtAdjustStereotypes();
		}
	}

	@Override
	public void rtVirtualize() {
		BehavioredClassifier context = getContext();
		if (context instanceof InternalUMLRTClassifier) {
			// I need to be an implicit owned behavior again
			@SuppressWarnings("unchecked")
			EList<? super Behavior> implicitBehaviors = (EList<? super Behavior>) context.eGet(ExtUMLExtPackage.Literals.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR);
			if (!implicitBehaviors.contains(this)) {
				implicitBehaviors.add(this);
			}

			if (context.getClassifierBehavior() == this) {
				// I am the classifier behavior
				context.eUnset(UMLPackage.Literals.BEHAVIORED_CLASSIFIER__CLASSIFIER_BEHAVIOR);
			}

			rtAdjustStereotypes();
		}
	}

	@Override
	public void rtUnsetAll() {
		unsetName();
		unsetVisibility();

		// Extended containments
		ElementRTOperations.destroyAll(this, getRegions());
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
	public void rtInherit(InternalUMLRTStateMachine extended) {
		rtInherit(extended, UMLPackage.Literals.STATE_MACHINE__CONNECTION_POINT,
				ExtUMLExtPackage.Literals.STATE_MACHINE__IMPLICIT_CONNECTION_POINT,
				Pseudostate.class, RTPseudostate.class);

		rtInherit(extended, UMLPackage.Literals.STATE_MACHINE__REGION,
				ExtUMLExtPackage.Literals.STATE_MACHINE__IMPLICIT_REGION,
				Region.class, RTRegion.class);
	}

	@Override
	public void rtDisinherit(InternalUMLRTStateMachine extended) {
		rtDisinherit(extended, UMLPackage.Literals.STATE_MACHINE__CONNECTION_POINT,
				ExtUMLExtPackage.Literals.STATE_MACHINE__IMPLICIT_CONNECTION_POINT);

		rtDisinherit(extended, UMLPackage.Literals.STATE_MACHINE__REGION,
				ExtUMLExtPackage.Literals.STATE_MACHINE__IMPLICIT_REGION);

		// Don't need the extension for anything, now
		rtDestroyExtension();
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
	public Element basicGetOwner() {
		return rtOwner();
	}

	@Override
	public EList<NamedElement> getOwnedMembers() {
		EList<NamedElement> result;

		CacheAdapter cache = getCacheAdapter();
		if (cache == null) {
			result = new DerivedUnionEObjectEListExt<>(NamedElement.class,
					this, UMLPackage.STATE_MACHINE__OWNED_MEMBER, EXT_OWNED_MEMBER_ESUBSETS);
		} else {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			EList<NamedElement> ownedMembers = (EList<NamedElement>) cache.get(
					eResource, this, UMLPackage.Literals.NAMESPACE__OWNED_MEMBER);
			if (ownedMembers == null) {
				ownedMembers = new DerivedUnionEObjectEListExt<>(
						NamedElement.class, this,
						UMLPackage.STATE_MACHINE__OWNED_MEMBER, EXT_OWNED_MEMBER_ESUBSETS);
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
				|| eIsSet(ExtUMLExtPackage.Literals.STATE_MACHINE__IMPLICIT_REGION);
	}

	@Override
	public Region getRegion(String name, boolean ignoreCase, boolean createOnDemand) {
		Region result = super.getRegion(name, ignoreCase, false);

		if ((result == null) && extension.hasExtension()) {
			result = extension.getExtension(ExtStateMachine.class)
					.getImplicitRegion(name, ignoreCase, false);
		}

		if ((result == null) && createOnDemand) {
			result = createRegion(name);
		}

		return result;
	}

	@Override
	public Pseudostate getConnectionPoint(String name, boolean ignoreCase, boolean createOnDemand) {
		Pseudostate result = super.getConnectionPoint(name, ignoreCase, false);

		if ((result == null) && extension.hasExtension()) {
			result = extension.getExtension(ExtStateMachine.class)
					.getImplicitConnectionPoint(name, ignoreCase, false);
		}

		if ((result == null) && createOnDemand) {
			result = createConnectionPoint(name);
		}

		return result;
	}

	@Override
	public String getName() {
		return inheritFeature(UMLPackage.Literals.NAMED_ELEMENT__NAME);
	}

	@Override
	public void setName(String newName) {
		// Make sure that the notification gets the correct old value
		name = getName();
		super.setName(newName);
	}

	@Override
	public void unsetName() {
		// Make sure that the notification gets the correct old and new values
		String oldName = getName();
		boolean oldNameESet = (eFlags & NAME_ESETFLAG) != 0;
		name = NAME_EDEFAULT;
		eFlags = eFlags & ~NAME_ESETFLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.NAMED_ELEMENT__NAME, oldName, getName(), oldNameESet));
		}
	}

	@Override
	public VisibilityKind getVisibility() {
		return inheritFeature(UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY);
	}

	@Override
	public void setVisibility(VisibilityKind newVisibility) {
		// Make sure that the notification gets the correct old value
		if (newVisibility == null) {
			newVisibility = VISIBILITY_EDEFAULT;
		}
		eFlags = eFlags | (getVisibility().ordinal() << VISIBILITY_EFLAG_OFFSET);
		super.setVisibility(newVisibility);
	}

	@Override
	public void unsetVisibility() {
		// Make sure that the notification gets the correct old and new values
		VisibilityKind oldVisibility = getVisibility();
		boolean oldVisibilityESet = (eFlags & VISIBILITY_ESETFLAG) != 0;
		eFlags = (eFlags & ~VISIBILITY_EFLAG) | VISIBILITY_EFLAG_DEFAULT;
		eFlags = eFlags & ~VISIBILITY_ESETFLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.NAMED_ELEMENT__VISIBILITY, oldVisibility, getVisibility(), oldVisibilityESet));
		}
	}

	@Override
	public boolean isRedefinitionContextValid(RedefinableElement redefinedElement) {
		return StateMachineRTOperations.isRedefinitionContextValid(this, redefinedElement);
	}

	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		return ElementRTOperations.toString(this, super.toString());
	}
}
