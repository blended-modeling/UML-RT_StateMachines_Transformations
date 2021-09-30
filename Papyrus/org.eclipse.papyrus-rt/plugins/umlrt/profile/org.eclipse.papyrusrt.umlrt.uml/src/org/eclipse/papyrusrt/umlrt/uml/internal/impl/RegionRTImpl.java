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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTState;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtRegion;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionHolder;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.DerivedUnionEObjectEListExt;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.internal.impl.RegionImpl;

/**
 * UML-RT semantics for {@link Region}.
 */
public class RegionRTImpl extends RegionImpl implements InternalUMLRTRegion {

	private static final int[] EXT_OWNED_MEMBER_ESUBSETS = extendSubsets(OWNED_MEMBER_ESUBSETS,
			ExtUMLExtPackage.REGION__IMPLICIT_SUBVERTEX,
			ExtUMLExtPackage.REGION__IMPLICIT_TRANSITION);

	private final ExtensionHolder extension = new ExtensionHolder(this);

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.NAMED_ELEMENT__NAME,
			UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY));

	protected RegionRTImpl() {
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
		case UMLPackage.REGION__NAME:
			return isSetName();
		case UMLPackage.REGION__VISIBILITY:
			return isSetVisibility();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.REGION__NAME:
			return (T) super.getName();
		case UMLPackage.REGION__VISIBILITY:
			return (T) super.getVisibility();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof Region)) {
			throw new IllegalArgumentException("not a region: " + redefined); //$NON-NLS-1$
		}
		setExtendedRegion((Region) redefined);
		handleRedefinedRegion((Region) redefined);
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	void handleRedefinedRegion(Region region) {
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
		StateMachine machine = getStateMachine();
		if ((machine != null) && !machine.getRegions().contains(this)) {
			machine.getRegions().add(this);
			rtAdjustStereotypes();
		} else {
			State composite = getState();
			if ((composite != null) && !composite.getRegions().contains(this)) {
				composite.getRegions().add(this);
				rtAdjustStereotypes();
			}
		}
	}

	@Override
	public void rtVirtualize() {
		Namespace namespace = getNamespace();
		if ((namespace instanceof StateMachine) || (namespace instanceof State)) {
			EReference reference = (namespace instanceof StateMachine)
					? ExtUMLExtPackage.Literals.STATE_MACHINE__IMPLICIT_REGION
					: ExtUMLExtPackage.Literals.STATE__IMPLICIT_REGION;

			@SuppressWarnings("unchecked")
			EList<? super Region> implicitRegions = (EList<? super Region>) namespace.eGet(reference);
			if (!implicitRegions.contains(this)) {
				implicitRegions.add(this);
				rtAdjustStereotypes();
			}
		}
	}

	@Override
	public void rtUnsetAll() {
		unsetName();
		unsetVisibility();

		// Extended containments
		ElementRTOperations.destroyAll(this, getTransitions());
		ElementRTOperations.destroyAll(this, getSubvertices());
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
	public void rtInherit(InternalUMLRTRegion extended) {
		rtInherit(extended, UMLPackage.Literals.REGION__SUBVERTEX,
				ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX,
				Pseudostate.class, RTPseudostate.class);
		rtInherit(extended, UMLPackage.Literals.REGION__SUBVERTEX,
				ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX,
				State.class, RTState.class);
		rtInherit(extended, UMLPackage.Literals.REGION__TRANSITION,
				ExtUMLExtPackage.Literals.REGION__IMPLICIT_TRANSITION,
				Transition.class, null);

		// Transitions do not have a stereotype to trigger inheritance
		UMLRTExtensionUtil.<Transition> getUMLRTContents(this, UMLPackage.Literals.REGION__TRANSITION).stream()
				.filter(InternalUMLRTTransition.class::isInstance).map(InternalUMLRTTransition.class::cast)
				.forEach(t -> {
					InternalUMLRTTransition ancestor = t.rtGetAncestor();
					if (ancestor != null) {
						t.rtInherit(ancestor);
					}
				});
	}

	@Override
	public void rtDisinherit(InternalUMLRTRegion extended) {
		rtDisinherit(extended, UMLPackage.Literals.REGION__SUBVERTEX,
				ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX);
		rtDisinherit(extended, UMLPackage.Literals.REGION__SUBVERTEX,
				ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX);
		rtDisinherit(extended, UMLPackage.Literals.REGION__TRANSITION,
				ExtUMLExtPackage.Literals.REGION__IMPLICIT_TRANSITION);

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
	public Region basicGetExtendedRegion() {
		Region result = super.basicGetExtendedRegion();
		return (result instanceof InternalUMLRTRegion)
				? ((InternalUMLRTRegion) result).rtGetNearestRealDefinition()
				: result;
	}

	@Override
	public StateMachine getStateMachine() {
		Element owner = rtOwner();
		return (owner instanceof StateMachine) ? (StateMachine) owner : null;
	}

	@Override
	public StateMachine basicGetStateMachine() {
		Element owner = rtOwner();
		return (owner instanceof StateMachine) ? (StateMachine) owner : null;
	}

	@Override
	public State getState() {
		Element owner = rtOwner();
		return (owner instanceof State) ? (State) owner : null;
	}

	@Override
	public State basicGetState() {
		Element owner = rtOwner();
		return (owner instanceof State) ? (State) owner : null;
	}

	@Override
	public EList<NamedElement> getOwnedMembers() {
		EList<NamedElement> result;

		CacheAdapter cache = getCacheAdapter();
		if (cache == null) {
			result = new DerivedUnionEObjectEListExt<>(NamedElement.class,
					this, UMLPackage.REGION__OWNED_MEMBER, EXT_OWNED_MEMBER_ESUBSETS);
		} else {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			EList<NamedElement> ownedMembers = (EList<NamedElement>) cache.get(
					eResource, this, UMLPackage.Literals.NAMESPACE__OWNED_MEMBER);
			if (ownedMembers == null) {
				ownedMembers = new DerivedUnionEObjectEListExt<>(
						NamedElement.class, this,
						UMLPackage.REGION__OWNED_MEMBER, EXT_OWNED_MEMBER_ESUBSETS);
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
				|| eIsSet(ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX)
				|| eIsSet(ExtUMLExtPackage.Literals.REGION__IMPLICIT_TRANSITION);
	}

	@Override
	public Vertex getSubvertex(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand) {
		Vertex result = super.getSubvertex(name, ignoreCase, eClass, false);

		if ((result == null) && extension.hasExtension()) {
			result = extension.getExtension(ExtRegion.class)
					.getImplicitSubvertex(name, ignoreCase, eClass, false);
		}

		if ((result == null) && createOnDemand) {
			result = createSubvertex(name, eClass);
		}

		return result;
	}

	@Override
	public Transition getTransition(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand) {
		Transition result = super.getTransition(name, ignoreCase, eClass, false);

		if ((result == null) && extension.hasExtension()) {
			result = extension.getExtension(ExtRegion.class)
					.getImplicitTransition(name, ignoreCase, eClass, false);
		}

		if ((result == null) && createOnDemand) {
			result = createTransition(name, eClass);
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
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		return ElementRTOperations.toString(this, super.toString());
	}
}
