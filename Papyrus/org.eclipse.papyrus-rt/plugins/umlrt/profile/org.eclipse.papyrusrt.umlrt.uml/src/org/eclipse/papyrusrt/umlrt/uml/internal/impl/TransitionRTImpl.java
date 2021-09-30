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
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.TransitionRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtTransition;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.UMLRTUMLPlugin;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionHolder;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.DerivedUnionEObjectEListExt;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.RTNotificationImpl;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.ReificationAdapter;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.SubsetSupersetEObjectContainmentWithInverseEListExt;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.internal.impl.TransitionImpl;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;

/**
 * UML-RT semantics for {@link Transition}.
 */
public class TransitionRTImpl extends TransitionImpl implements InternalUMLRTTransition {

	private static final int SOURCE__SET_FLAG = 0x1 << 0;
	private static final int TARGET__SET_FLAG = 0x1 << 1;
	private static final int KIND__SET_FLAG = 0x1 << 2;
	private static final int GUARD__SET_FLAG = 0x1 << 3;

	private static final int[] EXT_OWNED_ELEMENT_ESUBSETS = extendSubsets(OWNED_ELEMENT_ESUBSETS,
			ExtUMLExtPackage.TRANSITION__IMPLICIT_TRIGGER,
			ExtUMLExtPackage.TRANSITION__IMPLICIT_OWNED_RULE);

	private static final int[] EXT_OWNED_MEMBER_ESUBSETS = extendSubsets(OWNED_MEMBER_ESUBSETS,
			ExtUMLExtPackage.TRANSITION__IMPLICIT_OWNED_RULE);

	private int uFlags = 0x0;

	private final ExtensionHolder extension = new ExtensionHolder(this);

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.NAMED_ELEMENT__NAME,
			UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY,
			UMLPackage.Literals.TRANSITION__SOURCE,
			UMLPackage.Literals.TRANSITION__TARGET,
			UMLPackage.Literals.TRANSITION__GUARD,
			UMLPackage.Literals.TRANSITION__KIND
	/* Don't include 'ownedRule' and 'effect' because they forward for themselves */));

	protected TransitionRTImpl() {
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
		case UMLPackage.TRANSITION__NAME:
			return isSetName();
		case UMLPackage.TRANSITION__VISIBILITY:
			return isSetVisibility();
		case UMLPackage.TRANSITION__SOURCE:
			return isSetSource();
		case UMLPackage.TRANSITION__TARGET:
			return isSetTarget();
		case UMLPackage.TRANSITION__GUARD:
			return isSetGuard();
		case UMLPackage.TRANSITION__EFFECT:
			return isSetEffect();
		case UMLPackage.TRANSITION__KIND:
			return isSetKind();
		default:
			return super.eIsSet(featureID);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.TRANSITION__NAME:
			unsetName();
			break;
		case UMLPackage.TRANSITION__VISIBILITY:
			unsetVisibility();
			break;
		case UMLPackage.TRANSITION__SOURCE:
			unsetSource();
			break;
		case UMLPackage.TRANSITION__TARGET:
			unsetTarget();
			break;
		case UMLPackage.TRANSITION__GUARD:
			unsetGuard();
			break;
		case UMLPackage.TRANSITION__EFFECT:
			unsetEffect();
			break;
		case UMLPackage.TRANSITION__KIND:
			unsetKind();
			break;
		case UMLPackage.TRANSITION__OWNED_RULE:
			super.eUnset(featureID);
			// It's a superset of an unsettable feature (in UML-RT),
			// so unset the subset
			unsetGuard();
		default:
			super.eUnset(featureID);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.TRANSITION__NAME:
			return (T) super.getName();
		case UMLPackage.TRANSITION__VISIBILITY:
			return (T) super.getVisibility();
		case UMLPackage.TRANSITION__SOURCE:
			return (T) super.getSource();
		case UMLPackage.TRANSITION__TARGET:
			return (T) super.getTarget();
		case UMLPackage.TRANSITION__GUARD:
			return (T) super.getGuard();
		case UMLPackage.TRANSITION__EFFECT:
			return (T) super.getEffect();
		case UMLPackage.TRANSITION__KIND:
			return (T) super.getKind();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof Transition)) {
			throw new IllegalArgumentException("not a transition: " + redefined); //$NON-NLS-1$
		}
		setRedefinedTransition((Transition) redefined);
		handleRedefinedTransition((Transition) redefined);
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	@Override
	public Function<? super EObject, ? extends EObject> rtGetInheritanceResolver(EReference reference) {
		Function<? super EObject, ? extends EObject> result = null;

		if ((reference == UMLPackage.Literals.TRANSITION__SOURCE)
				|| (reference == UMLPackage.Literals.TRANSITION__TARGET)) {
			result = this::resolveVertex;
		} else if (reference == UMLPackage.Literals.TRANSITION__GUARD) {
			result = this::resolveGuard;
		}

		return result;
	}

	void handleRedefinedTransition(Transition transition) {
		// TODO
	}

	Constraint resolveGuard(EObject inherited) {
		Constraint result = null;

		if (inherited instanceof Constraint) {
			Constraint inheritedGuard = (Constraint) inherited;
			result = inheritedGuard;

			// Find the owned rule that redefines this guard
			for (Constraint next : UMLRTExtensionUtil.<Constraint> getUMLRTContents(this, UMLPackage.Literals.NAMESPACE__OWNED_RULE)) {
				if ((next instanceof InternalUMLRTElement)
						&& (((InternalUMLRTElement) next).rtGetRedefinedElement() == inheritedGuard)) {

					result = next;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Resolve the redefinition of an {@code inherited} vertex in my redefinition context.
	 * 
	 * @param inherited
	 *            an inherited vertex
	 * @return the resolved redefinition, or the same {@code inherited} vertex if it is not
	 *         a member of my redefinition context
	 */
	Vertex resolveVertex(EObject inherited) {
		return TransitionRTOperations.resolveVertex(this, inherited);
	}

	/**
	 * Unresolve a {@code redefinition} of an inherited vertex in my redefinition context.
	 * 
	 * @param redefinition
	 *            an possible redefinition (it could also be a local definition, which trivially resolves to itself)
	 * @return the inherited definition, or the same {@code redefinition} vertex if it is not
	 *         a member of my redefinition context or if it is not actually a redefinition
	 */
	Vertex unresolveVertex(Vertex redefinition) {
		Vertex result = redefinition;

		if (redefinition instanceof InternalUMLRTElement) {
			InternalUMLRTElement internal = (InternalUMLRTElement) redefinition;

			// Chase up to the first non-virtual definition
			while ((internal != null) && internal.rtIsVirtual()) {
				internal = internal.rtGetRedefinedElement();
			}

			if (redefinition.eClass().isInstance(internal)) {
				result = (Vertex) internal;
			}
		}

		return result;
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
		if ((region != null) && !region.getTransitions().contains(this)) {
			region.getTransitions().add(this);
			rtAdjustStereotypes();
		}
	}

	@Override
	public void rtVirtualize() {
		Region region = getContainer();
		if (region != null) {
			@SuppressWarnings("unchecked")
			EList<? super Transition> implicitTransitions = (EList<? super Transition>) region.eGet(ExtUMLExtPackage.Literals.REGION__IMPLICIT_TRANSITION);
			if (!implicitTransitions.contains(this)) {
				implicitTransitions.add(this);
				rtAdjustStereotypes();
			}
		}
	}

	@Override
	public void rtUnsetAll() {
		unsetName();
		unsetVisibility();
		unsetSource();
		unsetTarget();
		unsetGuard();
		unsetEffect();

		// Extended containments
		ElementRTOperations.destroyAll(this, getTriggers());
		ElementRTOperations.destroyAll(this, getOwnedRules());
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
	public void rtInherit(InternalUMLRTTransition redefined) {
		rtInherit(redefined, UMLPackage.Literals.TRANSITION__TRIGGER,
				ExtUMLExtPackage.Literals.TRANSITION__IMPLICIT_TRIGGER,
				Trigger.class, null);
		rtInherit(redefined, UMLPackage.Literals.NAMESPACE__OWNED_RULE,
				ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_RULE,
				Constraint.class, null);

		// For constraints, some inherited constraints may already have
		// been created before their original definitions got the «RTGuard»
		// stereotype. So, when we are kicked to re-inherit constraints
		// on the application of this stereotype to the root definition,
		// ensure that it is also applied to our inherited views
		if (extension.hasExtension() && (getNearestPackage() != null) && !ReificationAdapter.isUndoRedoInProgress(this)) {
			List<Constraint> constraints = extension.getExtension(ExtTransition.class).getImplicitOwnedRules();
			constraints.stream()
					.filter(InternalUMLRTConstraint.class::isInstance)
					.map(InternalUMLRTConstraint.class::cast)
					.forEach(InternalUMLRTConstraint::synchronizeGuardStereotype);
		}
	}

	@Override
	public void rtDisinherit(InternalUMLRTTransition redefined) {
		rtDisinherit(redefined, UMLPackage.Literals.TRANSITION__TRIGGER,
				ExtUMLExtPackage.Literals.TRANSITION__IMPLICIT_TRIGGER);
		rtDisinherit(redefined, UMLPackage.Literals.NAMESPACE__OWNED_RULE,
				ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_RULE);

		// Don't need the extension for anything, now
		rtDestroyExtension();
	}

	@Override
	public Constraint rtCreateExclusionConstraint(Element excluded) {
		if (excluded instanceof Trigger) {
			// Is there already a redefining guard constraint? If so, re-inherit it
			// to get it out of the way
			Constraint guard = TransitionRTOperations.getGuard(this, (Trigger) excluded);
			if (guard != null) {
				switch (UMLRTInheritanceKind.of(guard)) {
				case INHERITED:
					// Leave it as it is
					break;
				case REDEFINED:
				case EXCLUDED:
					// Re-inherit it
					((InternalUMLRTElement) guard).rtReinherit();
					break;
				default:
					UMLRTUMLPlugin.INSTANCE.log("Excluded trigger has a guard that is not a redefinition: " + guard);
					break;
				}
			}
		}

		Constraint result = InternalUMLRTTransition.super.rtCreateExclusionConstraint(excluded);

		if (excluded instanceof Trigger) {
			// Distinguish this as a trigger guard constraint (UML-RT Profile Spec 2016-06-13 §4.3)
			StereotypeApplicationHelper.getInstance(result).applyStereotype(
					result, UMLRTStateMachinesPackage.Literals.RT_GUARD);
		}

		return result;
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
	public Transition basicGetRedefinedTransition() {
		Transition result = super.basicGetRedefinedTransition();
		return (result instanceof InternalUMLRTTransition)
				? ((InternalUMLRTTransition) result).rtGetNearestRealDefinition()
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
	public EList<Element> getOwnedElements() {
		EList<Element> result;

		CacheAdapter cache = getCacheAdapter();
		if (cache == null) {
			result = new DerivedUnionEObjectEListExt<>(Element.class,
					this, UMLPackage.TRANSITION__OWNED_ELEMENT, EXT_OWNED_ELEMENT_ESUBSETS);
		} else {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			EList<Element> ownedElements = (EList<Element>) cache.get(
					eResource, this, UMLPackage.Literals.ELEMENT__OWNED_ELEMENT);
			if (ownedElements == null) {
				ownedElements = new DerivedUnionEObjectEListExt<>(
						Element.class, this,
						UMLPackage.TRANSITION__OWNED_ELEMENT, EXT_OWNED_ELEMENT_ESUBSETS);
				cache.put(eResource, this,
						UMLPackage.Literals.ELEMENT__OWNED_ELEMENT,
						ownedElements);
			}
			result = ownedElements;
		}

		return result;
	}

	@Override
	public boolean isSetOwnedElements() {
		return super.isSetOwnedElements()
				|| eIsSet(ExtUMLExtPackage.Literals.TRANSITION__IMPLICIT_TRIGGER);
	}

	@Override
	public Trigger getTrigger(String name, boolean ignoreCase, boolean createOnDemand) {
		Trigger result = super.getTrigger(name, ignoreCase, false);

		if ((result == null) && extension.hasExtension()) {
			result = extension.getExtension(ExtTransition.class)
					.getImplicitTrigger(name, ignoreCase, false);
		}

		if ((result == null) && createOnDemand) {
			result = createTrigger(name);
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
	public Vertex getSource() {
		return resolveVertex(inheritFeature(UMLPackage.Literals.TRANSITION__SOURCE));
	}

	@Override
	public void setSource(Vertex newSource) {
		// Make sure that the notification gets the correct old value
		source = getSource();
		uFlags = uFlags | SOURCE__SET_FLAG;

		// If the vertex is inherited, we must reference the inherited definition
		newSource = unresolveVertex(newSource);
		super.setSource(newSource);
	}

	public boolean isSetSource() {
		return (uFlags & SOURCE__SET_FLAG) != 0;
	}

	public void unsetSource() {
		// Make sure that the notification gets the correct old and new values
		Vertex oldSource = getSource();
		boolean oldSourceESet = (uFlags & SOURCE__SET_FLAG) != 0;
		source = null;
		uFlags = uFlags & ~SOURCE__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.TRANSITION__SOURCE, oldSource, getSource(), oldSourceESet));
		}
	}

	@Override
	public Vertex getTarget() {
		return resolveVertex(inheritFeature(UMLPackage.Literals.TRANSITION__TARGET));
	}

	@Override
	public void setTarget(Vertex newTarget) {
		// Make sure that the notification gets the correct old value
		target = getTarget();
		uFlags = uFlags | TARGET__SET_FLAG;

		// If the vertex is inherited, we must reference the inherited definition
		newTarget = unresolveVertex(newTarget);
		super.setTarget(newTarget);
	}

	public boolean isSetTarget() {
		return (uFlags & TARGET__SET_FLAG) != 0;
	}

	public void unsetTarget() {
		// Make sure that the notification gets the correct old and new values
		Vertex oldTarget = getTarget();
		boolean oldTargetESet = (uFlags & TARGET__SET_FLAG) != 0;
		target = null;
		uFlags = uFlags & ~TARGET__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.TRANSITION__TARGET, oldTarget, getTarget(), oldTargetESet));
		}
	}

	@Override
	public Constraint getGuard() {
		return inheritFeature(UMLPackage.Literals.TRANSITION__GUARD);
	}

	@Override
	public void setGuard(Constraint newGuard) {
		// Make sure that the notification gets the correct old value
		boolean wasSet = isSetGuard();
		uFlags = uFlags | GUARD__SET_FLAG;
		Constraint oldGuard = guard;
		guard = newGuard;
		if (eNotificationRequired()) {
			eNotify(new RTNotificationImpl(this, Notification.SET,
					UMLPackage.TRANSITION__GUARD, oldGuard, guard, !wasSet));
		}

		Resource.Internal eInternalResource = eInternalResource();
		if ((eInternalResource == null) || !eInternalResource.isLoading()) {
			if (newGuard != null) {
				EList<Constraint> ownedRules = getOwnedRules();
				if (!ownedRules.contains(newGuard)) {
					ownedRules.add(newGuard);
				}
			}
		}
	}

	public boolean isSetGuard() {
		return (uFlags & GUARD__SET_FLAG) != 0;
	}

	public void unsetGuard() {
		// Make sure that the notification gets the correct old and new values
		Constraint oldGuard = getGuard();
		boolean oldGuardESet = (uFlags & GUARD__SET_FLAG) != 0;
		guard = null;
		uFlags = uFlags & ~GUARD__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new RTNotificationImpl(this, Notification.UNSET, UMLPackage.TRANSITION__GUARD, oldGuard, getGuard(), oldGuardESet));
		}
	}

	@Override
	public TransitionKind getKind() {
		return inheritFeature(UMLPackage.Literals.TRANSITION__KIND);
	}

	@Override
	public void setKind(TransitionKind newKind) {
		// Make sure that the notification gets the correct old value
		if (newKind == null) {
			newKind = KIND_EDEFAULT;
		}
		eFlags = eFlags | (getKind().ordinal() << KIND_EFLAG_OFFSET);
		uFlags = uFlags | KIND__SET_FLAG;
		super.setKind(newKind);
	}

	public boolean isSetKind() {
		return (uFlags & KIND__SET_FLAG) != 0;
	}

	public void unsetKind() {
		// Make sure that the notification gets the correct old and new values
		TransitionKind oldKind = getKind();
		boolean wasSet = isSetKind();

		eFlags = (eFlags & ~KIND_EFLAG) | KIND_EFLAG_DEFAULT;
		uFlags = uFlags & ~KIND__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.TRANSITION__KIND, oldKind, getKind(), wasSet));
		}
	}

	@Override
	public EList<NamedElement> getOwnedMembers() {
		EList<NamedElement> result;

		CacheAdapter cache = getCacheAdapter();
		if (cache == null) {
			result = new DerivedUnionEObjectEListExt<>(NamedElement.class,
					this, UMLPackage.TRANSITION__OWNED_MEMBER, EXT_OWNED_MEMBER_ESUBSETS);
		} else {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			EList<NamedElement> ownedMembers = (EList<NamedElement>) cache.get(
					eResource, this, UMLPackage.Literals.NAMESPACE__OWNED_MEMBER);
			if (ownedMembers == null) {
				ownedMembers = new DerivedUnionEObjectEListExt<>(
						NamedElement.class, this,
						UMLPackage.TRANSITION__OWNED_MEMBER, EXT_OWNED_MEMBER_ESUBSETS);
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
				|| eIsSet(ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_RULE);
	}

	@Override
	public EList<Constraint> getOwnedRules() {
		if (ownedRules == null) {
			ownedRules = new SubsetSupersetEObjectContainmentWithInverseEListExt.Resolving<>(
					Constraint.class, this, UMLPackage.TRANSITION__OWNED_RULE, null,
					OWNED_RULE_ESUBSETS, UMLPackage.CONSTRAINT__CONTEXT);
		}
		return ownedRules;
	}

	@Override
	public Behavior umlGetEffect(boolean resolve) {
		return resolve ? super.getEffect() : super.basicGetEffect();
	}

	@Override
	public NotificationChain umlBasicSetEffect(Behavior newEffect, NotificationChain msgs) {
		return super.basicSetEffect(newEffect, msgs);
	}

	@Override
	public Behavior getEffect() {
		return TransitionRTOperations.getEffect(this);
	}

	@Override
	public void setEffect(Behavior newEffect) {
		TransitionRTOperations.setEffect(this, newEffect);
	}

	public boolean isSetEffect() {
		return TransitionRTOperations.isSetEffect(this);
	}

	public void unsetEffect() {
		TransitionRTOperations.unsetEffect(this);
	}

	@Override
	public boolean isConsistentWith(RedefinableElement redefiningElement) {
		return TransitionRTOperations.isConsistentWith(this, redefiningElement);
	}

	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		return ElementRTOperations.toString(this, super.toString());
	}

}
