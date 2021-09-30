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
import org.eclipse.emf.common.notify.NotificationChain;
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
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.StateRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.VertexRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtState;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionHolder;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.DerivedUnionEObjectEListExt;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.internal.impl.StateImpl;

/**
 * UML-RT semantics for {@link State}.
 */
public class StateRTImpl extends StateImpl implements InternalUMLRTState {

	private static final int[] EXT_OWNED_MEMBER_ESUBSETS = extendSubsets(OWNED_MEMBER_ESUBSETS,
			ExtUMLExtPackage.STATE__IMPLICIT_REGION,
			ExtUMLExtPackage.STATE__IMPLICIT_CONNECTION_POINT);

	private final ExtensionHolder extension = new ExtensionHolder(this);

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.NAMED_ELEMENT__NAME,
			UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY
	/* Don't include 'entry' and 'exit' because they forward for themselves */));

	protected StateRTImpl() {
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
		case UMLPackage.STATE__NAME:
			return isSetName();
		case UMLPackage.STATE__VISIBILITY:
			return isSetVisibility();
		case UMLPackage.STATE__ENTRY:
			return isSetEntry();
		case UMLPackage.STATE__EXIT:
			return isSetExit();
		default:
			return super.eIsSet(featureID);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.STATE__NAME:
			unsetName();
			break;
		case UMLPackage.STATE__VISIBILITY:
			unsetVisibility();
			break;
		case UMLPackage.STATE__ENTRY:
			unsetEntry();
			break;
		case UMLPackage.STATE__EXIT:
			unsetExit();
			break;
		default:
			super.eUnset(featureID);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.STATE__NAME:
			return (T) super.getName();
		case UMLPackage.STATE__VISIBILITY:
			return (T) super.getVisibility();
		case UMLPackage.STATE__ENTRY:
			return (T) super.getEntry();
		case UMLPackage.STATE__EXIT:
			return (T) super.getExit();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof State)) {
			throw new IllegalArgumentException("not a state: " + redefined); //$NON-NLS-1$
		}
		setRedefinedState((State) redefined);
		handleRedefinedState((State) redefined);
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	void handleRedefinedState(State state) {
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
		Region region = getContainer();
		if ((region != null) && !region.getSubvertices().contains(this)) {
			region.getSubvertices().add(this);
			rtAdjustStereotypes();
		}
	}

	@Override
	public void rtVirtualize() {
		Region region = getContainer();
		if (region != null) {
			@SuppressWarnings("unchecked")
			EList<? super Vertex> implicitVertices = (EList<? super Vertex>) region.eGet(ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX);
			if (!implicitVertices.contains(this)) {
				implicitVertices.add(this);
				rtAdjustStereotypes();
			}
		}
	}

	@Override
	public void rtUnsetAll() {
		unsetName();
		unsetVisibility();
		unsetEntry();
		unsetExit();

		// Extended containments
		ElementRTOperations.destroyAll(this, getRegions());
		ElementRTOperations.destroyAll(this, getConnectionPoints());
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
	public void rtInherit(InternalUMLRTState redefined) {
		rtInherit(redefined, UMLPackage.Literals.STATE__CONNECTION_POINT,
				ExtUMLExtPackage.Literals.STATE__IMPLICIT_CONNECTION_POINT,
				Pseudostate.class, RTPseudostate.class);

		rtInherit(redefined, UMLPackage.Literals.STATE__REGION,
				ExtUMLExtPackage.Literals.STATE__IMPLICIT_REGION,
				Region.class, RTRegion.class);
	}

	@Override
	public void rtDisinherit(InternalUMLRTState redefined) {
		rtDisinherit(redefined, UMLPackage.Literals.STATE__CONNECTION_POINT,
				ExtUMLExtPackage.Literals.STATE__IMPLICIT_CONNECTION_POINT);

		rtDisinherit(redefined, UMLPackage.Literals.STATE__REGION,
				ExtUMLExtPackage.Literals.STATE__IMPLICIT_REGION);

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
	public State basicGetRedefinedState() {
		State result = super.basicGetRedefinedState();
		return (result instanceof InternalUMLRTState)
				? ((InternalUMLRTState) result).rtGetNearestRealDefinition()
				: result;
	}

	@Override
	public Region getContainer() {
		Element owner = rtOwner();
		return (owner instanceof Region) ? (Region) owner : null;
	}

	@Override
	public Region basicGetContainer() {
		Element owner = rtOwner();
		return (owner instanceof Region) ? (Region) owner : null;
	}

	@Override
	public EList<NamedElement> getOwnedMembers() {
		EList<NamedElement> result;

		CacheAdapter cache = getCacheAdapter();
		if (cache == null) {
			result = new DerivedUnionEObjectEListExt<>(NamedElement.class,
					this, UMLPackage.STATE__OWNED_MEMBER, EXT_OWNED_MEMBER_ESUBSETS);
		} else {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			EList<NamedElement> ownedMembers = (EList<NamedElement>) cache.get(
					eResource, this, UMLPackage.Literals.NAMESPACE__OWNED_MEMBER);
			if (ownedMembers == null) {
				ownedMembers = new DerivedUnionEObjectEListExt<>(
						NamedElement.class, this,
						UMLPackage.STATE__OWNED_MEMBER, EXT_OWNED_MEMBER_ESUBSETS);
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
				|| eIsSet(ExtUMLExtPackage.Literals.STATE__IMPLICIT_REGION);
	}

	@Override
	public Region getRegion(String name, boolean ignoreCase, boolean createOnDemand) {
		Region result = super.getRegion(name, ignoreCase, false);

		if ((result == null) && extension.hasExtension()) {
			result = extension.getExtension(ExtState.class)
					.getImplicitRegion(name, ignoreCase, false);
		}

		if ((result == null) && createOnDemand) {
			result = createRegion(name);
		}

		return result;
	}

	@Override
	public boolean isComposite() {
		boolean result = super.isComposite();

		if (!result && extension.hasExtension()) {
			result = !extension.getExtension(ExtState.class).getImplicitRegions().isEmpty();
		}

		return result;
	}

	@Override
	public boolean isSimple() {
		boolean result = super.isSimple();

		if (result && extension.hasExtension()) {
			result = extension.getExtension(ExtState.class).getImplicitRegions().isEmpty();
		}

		return result;
	}

	@Override
	public Pseudostate getConnectionPoint(String name, boolean ignoreCase, boolean createOnDemand) {
		Pseudostate result = super.getConnectionPoint(name, ignoreCase, false);

		if ((result == null) && extension.hasExtension()) {
			result = extension.getExtension(ExtState.class)
					.getImplicitConnectionPoint(name, ignoreCase, false);
		}

		if ((result == null) && createOnDemand) {
			result = createConnectionPoint(name);
		}

		return result;
	}

	@Override
	public EList<Transition> getIncomings() {
		return VertexRTOperations.getIncomings(this);
	}

	@Override
	public EList<Transition> getOutgoings() {
		return VertexRTOperations.getOutgoings(this);
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
	public Behavior umlGetEntry(boolean resolve) {
		return resolve ? super.getEntry() : super.basicGetEntry();
	}

	@Override
	public NotificationChain umlBasicSetEntry(Behavior newEntry, NotificationChain msgs) {
		return super.basicSetEntry(newEntry, msgs);
	}

	@Override
	public Behavior getEntry() {
		return StateRTOperations.getEntry(this);
	}

	@Override
	public void setEntry(Behavior newEntry) {
		StateRTOperations.setEntry(this, newEntry);
	}

	public boolean isSetEntry() {
		return StateRTOperations.isSetEntry(this);
	}

	public void unsetEntry() {
		StateRTOperations.unsetEntry(this);
	}

	@Override
	public Behavior umlGetExit(boolean resolve) {
		return resolve ? super.getExit() : super.basicGetExit();
	}

	@Override
	public NotificationChain umlBasicSetExit(Behavior newExit, NotificationChain msgs) {
		return super.basicSetExit(newExit, msgs);
	}

	@Override
	public Behavior getExit() {
		return StateRTOperations.getExit(this);
	}

	@Override
	public void setExit(Behavior newExit) {
		StateRTOperations.setExit(this, newExit);
	}

	public boolean isSetExit() {
		return StateRTOperations.isSetExit(this);
	}

	public void unsetExit() {
		StateRTOperations.unsetExit(this);
	}

	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		return ElementRTOperations.toString(this, super.toString());
	}
}
