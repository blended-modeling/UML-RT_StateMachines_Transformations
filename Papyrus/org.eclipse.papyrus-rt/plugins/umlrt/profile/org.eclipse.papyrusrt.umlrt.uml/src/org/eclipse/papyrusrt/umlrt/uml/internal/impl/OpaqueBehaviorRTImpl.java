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
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableEcoreEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.NotificationForwarder;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.RedefinedElementsList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.SubsetSupersetRedefinedElementsList;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.internal.impl.OpaqueBehaviorImpl;

/**
 * UML-RT semantics for {@link OpaqueBehavior}.
 */
public class OpaqueBehaviorRTImpl extends OpaqueBehaviorImpl implements InternalUMLRTOpaqueBehavior {

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.NAMED_ELEMENT__NAME,
			UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY,
			UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY,
			UMLPackage.Literals.OPAQUE_BEHAVIOR__LANGUAGE));

	public OpaqueBehaviorRTImpl() {
		super();

		class InheritableValueList<E> extends InheritableEcoreEList<E> {
			private static final long serialVersionUID = 1L;

			InheritableValueList(int featureID) {
				super(OpaqueBehaviorRTImpl.this, featureID);
			}

			@Override
			public boolean isSet() {
				// If I am a containment, then I can only inherit
				// my value if I am a virtual element, otherwise
				// my contained objects would be real also
				return super.isSet() || (isContainment() && !rtIsVirtual());
			}
		}

		this.bodies = new InheritableValueList<>(UMLPackage.OPAQUE_BEHAVIOR__BODY);
		this.languages = new InheritableValueList<>(UMLPackage.OPAQUE_BEHAVIOR__LANGUAGE);
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
		case UMLPackage.OPAQUE_BEHAVIOR__NAME:
			return isSetName();
		case UMLPackage.OPAQUE_BEHAVIOR__VISIBILITY:
			return isSetVisibility();
		case UMLPackage.OPAQUE_BEHAVIOR__BODY:
			return isSetBodies();
		case UMLPackage.OPAQUE_BEHAVIOR__LANGUAGE:
			return isSetLanguages();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.OPAQUE_BEHAVIOR__NAME:
			return (T) super.getName();
		case UMLPackage.OPAQUE_BEHAVIOR__VISIBILITY:
			return (T) super.getVisibility();
		case UMLPackage.OPAQUE_BEHAVIOR__BODY:
			return (T) super.getBodies();
		case UMLPackage.OPAQUE_BEHAVIOR__LANGUAGE:
			return (T) super.getLanguages();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.OPAQUE_BEHAVIOR__BODY:
			unsetBodies();
			break;
		case UMLPackage.OPAQUE_BEHAVIOR__LANGUAGE:
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
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof OpaqueBehavior)) {
			throw new IllegalArgumentException("not an opaque behavior: " + redefined); //$NON-NLS-1$
		}

		((RedefinedElementsList<Behavior>) getRedefinedBehaviors()).setRedefinedElement((OpaqueBehavior) redefined);
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	@Override
	public void handleRedefinedTransition(Transition transition) {
		if (transition != null) {
			NotificationForwarder.adapt(this,
					() -> new NotificationForwarder(this, null, rtInheritedFeatures()));
		} else if (eBasicHasAdapters()) {
			NotificationForwarder.unadapt(this);
		}
	}

	@Override
	public void handleRedefinedState(State state) {
		if (state != null) {
			NotificationForwarder.adapt(this,
					() -> new NotificationForwarder(this, null, rtInheritedFeatures()));
		} else if (eBasicHasAdapters()) {
			NotificationForwarder.unadapt(this);
		}
	}

	@Override
	public void rtReify() {
		Element owner = getOwner();
		if ((owner instanceof Transition) || (owner instanceof State)) {
			EReference containment = eContainmentFeature();

			// This will implicitly reify the owner, too, which is good
			owner.eSet(containment, this);
			rtAdjustStereotypes();
		}
	}

	@Override
	public void rtVirtualize() {
		Element owner = getOwner();
		if ((owner instanceof Transition) || (owner instanceof State)) {
			EReference containment = eContainmentFeature();
			if (owner.eIsSet(containment)) {
				owner.eUnset(containment);
				rtAdjustStereotypes();
			}
		}
	}

	@Override
	public void rtUnsetAll() {
		unsetName();
		unsetVisibility();
		unsetBodies();
		unsetLanguages();
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
	public EList<Behavior> getRedefinedBehaviors() {
		if (redefinedBehaviors == null) {
			redefinedBehaviors = new SubsetSupersetRedefinedElementsList<Behavior>(
					Behavior.class, this, UMLPackage.BEHAVIOR__REDEFINED_BEHAVIOR,
					REDEFINED_BEHAVIOR_ESUPERSETS, null);
		}
		return redefinedBehaviors;
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
	public EList<String> getBodies() {
		EList<String> result = inheritFeature(UMLPackage.Literals.OPAQUE_BEHAVIOR__BODY);

		if (result != bodies) {
			// Inherit this into our list. If it's null, that means we are the root definition
			((InheritableEList<String>) bodies).inherit(result);
			result = bodies;
		}

		return bodies;
	}

	@Override
	public EList<String> getLanguages() {
		EList<String> result = inheritFeature(UMLPackage.Literals.OPAQUE_BEHAVIOR__LANGUAGE);

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
