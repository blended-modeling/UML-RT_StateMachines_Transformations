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
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.VertexRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.internal.impl.PseudostateImpl;

/**
 * UML-RT semantics for {@link Pseudostate}.
 */
public class PseudostateRTImpl extends PseudostateImpl implements InternalUMLRTElement {

	private static final int KIND__SET_FLAG = 0x1 << 0;

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.NAMED_ELEMENT__NAME,
			UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY,
			UMLPackage.Literals.PSEUDOSTATE__KIND));

	private Pseudostate redefinedPseudostate;

	private int uFlags = 0x0;

	protected PseudostateRTImpl() {
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
		case UMLPackage.PSEUDOSTATE__NAME:
			return isSetName();
		case UMLPackage.PSEUDOSTATE__VISIBILITY:
			return isSetVisibility();
		case UMLPackage.PSEUDOSTATE__KIND:
			return isSetKind();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.PSEUDOSTATE__NAME:
			return (T) super.getName();
		case UMLPackage.PSEUDOSTATE__VISIBILITY:
			return (T) super.getVisibility();
		case UMLPackage.PSEUDOSTATE__KIND:
			return (T) super.getKind();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.PSEUDOSTATE__KIND:
			unsetKind();
			break;
		default:
			super.eUnset(featureID);
			break;
		}
	}

	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLPackage.PSEUDOSTATE__KIND:
			if (newValue == null) {
				unsetKind();
			} else {
				setKind((PseudostateKind) newValue);
			}
			break;
		default:
			super.eSet(featureID, newValue);
			break;
		}
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof Pseudostate)) {
			throw new IllegalArgumentException("not a pseudostate: " + redefined); //$NON-NLS-1$
		}
		redefinedPseudostate = (Pseudostate) redefined;

		// Because this isn't a modeled feature, it won't notify, and so won't
		// update the CacheAdapter automatically
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			cache.clear();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R extends InternalUMLRTElement> R rtGetRedefinedElement() {
		R result = null;

		// Check that our idea of the redefined pseudostate is currently valid
		if (redefinedPseudostate instanceof InternalUMLRTElement) {
			EReference containment = eContainmentFeature();
			if ((containment == UMLPackage.Literals.REGION__SUBVERTEX)
					|| (containment == ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX)) {
				Region region = getContainer();
				if ((region != null) && (redefinedPseudostate.getContainer() == region.getExtendedRegion())) {
					result = (R) redefinedPseudostate;
				}
			} else if ((containment == UMLPackage.Literals.STATE__CONNECTION_POINT)
					|| (containment == ExtUMLExtPackage.Literals.STATE__IMPLICIT_CONNECTION_POINT)) {
				State state = getState();
				if ((state != null) && (redefinedPseudostate.getState() == state.getRedefinedState())) {
					result = (R) redefinedPseudostate;
				}
			} else if ((containment == UMLPackage.Literals.STATE_MACHINE__CONNECTION_POINT)
					|| (containment == ExtUMLExtPackage.Literals.STATE_MACHINE__IMPLICIT_CONNECTION_POINT)) {
				StateMachine machine = getStateMachine();
				if ((machine != null) && machine.getExtendedStateMachines().contains(redefinedPseudostate.getStateMachine())) {
					result = (R) redefinedPseudostate;
				}
			}
		}

		return result;
	}

	@Override
	public void rtRedefine(InternalUMLRTElement element) {
		// Pseudostates are not UML RedefinableElements

		run(() -> {
			// First, set the UML semantics of redefinition
			umlSetRedefinedElement(element);

			// Make sure that the stereotypes end up in the right place
			rtAdjustStereotypes();
		});
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
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
		// A pseudostate cannot be reified because it is not redefinable
	}

	@Override
	public void rtVirtualize() {
		// A pseudostate cannot be reified because it is not redefinable,
		// so there is never anything to virtualize
	}

	@Override
	public void rtUnsetAll() {
		unsetName();
		unsetVisibility();
		unsetKind();
	}

	@Override
	public Element basicGetOwner() {
		return rtOwner();
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
	public PseudostateKind getKind() {
		return inheritFeature(UMLPackage.Literals.PSEUDOSTATE__KIND);
	}

	@Override
	public void setKind(PseudostateKind newKind) {
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
		PseudostateKind oldKind = getKind();
		boolean wasSet = isSetKind();

		eFlags = (eFlags & ~KIND_EFLAG) | KIND_EFLAG_DEFAULT;
		eFlags = eFlags & ~VISIBILITY_ESETFLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.PSEUDOSTATE__KIND, oldKind, getKind(), wasSet));
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
