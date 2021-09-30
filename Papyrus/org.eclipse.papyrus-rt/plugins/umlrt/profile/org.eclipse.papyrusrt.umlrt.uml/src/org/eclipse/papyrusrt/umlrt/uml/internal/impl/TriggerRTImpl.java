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
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableEObjectEList;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Event;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.internal.impl.TriggerImpl;

/**
 * UML-RT semantics for {@link Trigger}.
 */
public class TriggerRTImpl extends TriggerImpl implements InternalUMLRTElement {

	private static final int EVENT__SET_FLAG = 0x1 << 0;

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.NAMED_ELEMENT__NAME,
			UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY,
			UMLPackage.Literals.TRIGGER__EVENT
	/* Don't include the 'port' list because it forwards for itself */));

	private Trigger redefinedTrigger;

	private int uFlags = 0x0;

	public TriggerRTImpl() {
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
		case UMLPackage.TRIGGER__NAME:
			return isSetName();
		case UMLPackage.TRIGGER__VISIBILITY:
			return isSetVisibility();
		case UMLPackage.TRIGGER__EVENT:
			return isSetEvent();
		case UMLPackage.TRIGGER__PORT:
			return isSetPorts();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.TRIGGER__NAME:
			return (T) super.getName();
		case UMLPackage.TRIGGER__VISIBILITY:
			return (T) super.getVisibility();
		case UMLPackage.TRIGGER__EVENT:
			return (T) super.getEvent();
		case UMLPackage.TRIGGER__PORT:
			return (T) super.getPorts();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.TRIGGER__NAME:
			unsetName();
			break;
		case UMLPackage.TRIGGER__VISIBILITY:
			unsetVisibility();
			break;
		case UMLPackage.TRIGGER__EVENT:
			unsetEvent();
			break;
		case UMLPackage.TRIGGER__PORT:
			unsetPorts();
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
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof Trigger)) {
			throw new IllegalArgumentException("not a trigger: " + redefined); //$NON-NLS-1$
		}
		redefinedTrigger = (Trigger) redefined;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R extends InternalUMLRTElement> R rtGetRedefinedElement() {
		R result = null;

		// Check that our idea of the redefined trigger is currently valid
		if (redefinedTrigger instanceof InternalUMLRTElement) {
			EReference containment = eContainmentFeature();
			if ((containment == UMLPackage.Literals.TRANSITION__TRIGGER)
					|| (containment == ExtUMLExtPackage.Literals.TRANSITION__IMPLICIT_TRIGGER)) {
				Transition transition = (Transition) rtOwner();
				if ((transition != null) && (redefinedTrigger.getOwner() == transition.getRedefinedTransition())) {
					result = (R) redefinedTrigger;
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
			element.rtAdjustStereotypes();
		});
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	/**
	 * Resolve the redefinition of an {@code inherited} port in my redefinition context.
	 * 
	 * @param inherited
	 *            an inherited port
	 * @return the resolved redefinition, or the same {@code inherited} port if it is not
	 *         a member of my redefinition context
	 */
	EObject resolvePort(EObject inherited) {
		EObject result = inherited;

		if ((inherited instanceof Port) && (inherited instanceof InternalUMLRTElement)) {
			InternalUMLRTElement inheritedPort = (InternalUMLRTElement) inherited;

			Element owner = rtOwner();
			if (owner instanceof Transition) {
				Transition transition = (Transition) getOwner();
				StateMachine machine = transition.containingStateMachine();
				// The state machine can be null for a deleted transition
				BehavioredClassifier context = (machine == null) ? null : machine.getContext();
				if ((context instanceof org.eclipse.uml2.uml.Class) && (context instanceof InternalUMLRTClassifier)) {
					// Could be a capsule. Resolve within its ports
					EList<Port> ports = UMLRTExtensionUtil.getUMLRTContents(context, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER__OWNED_PORT);
					for (Port next : ports) {
						if ((next instanceof InternalUMLRTElement)
								&& ((InternalUMLRTElement) next).rtRedefines(inheritedPort)) {

							result = next;
							break;
						}
					}
				}
			}
		}

		return result;
	}

	@Override
	public void rtReify() {
		// A trigger cannot be reified because it is not redefinable
	}

	@Override
	public void rtVirtualize() {
		// A trigger cannot be reified because it is not redefinable,
		// so there is never anything to virtualize
	}

	@Override
	public void rtUnsetAll() {
		unsetName();
		unsetVisibility();
		unsetEvent();
		unsetPorts();
	}

	@Override
	public Element basicGetOwner() {
		return rtOwner();
	}

	@Override
	public Namespace basicGetNamespace() {
		Element owner = rtOwner();
		return (owner instanceof Namespace) ? (Namespace) owner : null;
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
	public Event getEvent() {
		return inheritFeature(UMLPackage.Literals.TRIGGER__EVENT);
	}

	@Override
	public void setEvent(Event newEvent) {
		// Make sure that the notification gets the correct old value
		event = getEvent();
		uFlags = uFlags | EVENT__SET_FLAG;
		super.setEvent(newEvent);
	}

	public boolean isSetEvent() {
		return (uFlags & EVENT__SET_FLAG) != 0;
	}

	public void unsetEvent() {
		// Make sure that the notification gets the correct old and new values
		Event oldEvent = getEvent();
		boolean oldEventESet = (uFlags & EVENT__SET_FLAG) != 0;
		event = null;
		uFlags = uFlags & ~EVENT__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.TRIGGER__EVENT, oldEvent, getEvent(), oldEventESet));
		}
	}

	@Override
	public EList<Port> getPorts() {
		if (ports == null) {
			ports = new InheritableEObjectEList.Resolving<Port>(this, UMLPackage.TRIGGER__PORT) {
				private static final long serialVersionUID = 1L;

				@Override
				protected Port resolveInheritance(Port element) {
					return (Port) resolvePort(element);
				}

				@Override
				protected Port unresolveInheritance(Port element) {
					return (element instanceof InternalUMLRTElement)
							? ((InternalUMLRTElement) element).rtGetRootDefinition()
							: element;
				}
			};
		}

		EList<Port> inherited = inheritFeature(UMLPackage.Literals.TRIGGER__PORT);

		if (inherited != ports) {
			// Inherit this into our list. If it's null, that means we are the root definition
			((InheritableEList<Port>) ports).inherit(inherited);
		}

		return ports;
	}

	public boolean isSetPorts() {
		return (ports != null) && ((InheritableEList<Port>) ports).isSet();
	}

	public void unsetPorts() {
		if (ports != null) {
			((InheritableEList<Port>) ports).unset();
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
