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
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ConstraintRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableEObjectEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.NotificationForwarder;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.internal.impl.ConstraintImpl;

/**
 * UML-RT semantics for {@link Constraint}.
 */
public class ConstraintRTImpl extends ConstraintImpl implements InternalUMLRTConstraint {

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.NAMED_ELEMENT__NAME,
			UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY
	/* Don't include 'specification' and 'constrainedElement' because they forward for themselves */));

	private InternalUMLRTConstraint redefinedConstraint;

	public ConstraintRTImpl() {
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
		case UMLPackage.CONSTRAINT__NAME:
			return isSetName();
		case UMLPackage.CONSTRAINT__VISIBILITY:
			return isSetVisibility();
		case UMLPackage.CONSTRAINT__SPECIFICATION:
			return isSetSpecification();
		case UMLPackage.CONSTRAINT__CONSTRAINED_ELEMENT:
			return isSetConstrainedElements();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.CONSTRAINT__NAME:
			return (T) super.getName();
		case UMLPackage.CONSTRAINT__VISIBILITY:
			return (T) super.getVisibility();
		case UMLPackage.CONSTRAINT__SPECIFICATION:
			return (T) super.getSpecification();
		case UMLPackage.CONSTRAINT__CONSTRAINED_ELEMENT:
			return (T) super.getConstrainedElements();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.CONSTRAINT__NAME:
			unsetName();
			break;
		case UMLPackage.CONSTRAINT__VISIBILITY:
			unsetVisibility();
			break;
		case UMLPackage.CONSTRAINT__SPECIFICATION:
			unsetSpecification();
			break;
		case UMLPackage.CONSTRAINT__CONSTRAINED_ELEMENT:
			unsetConstrainedElements();
			break;
		default:
			super.eUnset(featureID);
			break;
		}
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof InternalUMLRTConstraint)) {
			throw new IllegalArgumentException("not a constraint: " + redefined); //$NON-NLS-1$
		}

		redefinedConstraint = (InternalUMLRTConstraint) redefined;

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

		// Check that our idea of the redefined constraint is currently valid
		if (redefinedConstraint != null) {
			EReference containment = eContainmentFeature();
			if ((containment == UMLPackage.Literals.NAMESPACE__OWNED_RULE)
					|| (containment == ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_RULE)) {

				Namespace context = getContext();
				if ((context instanceof InternalUMLRTElement)
						&& (redefinedConstraint.getContext() == ((InternalUMLRTElement) context).rtGetRedefinedElement())) {

					return (R) redefinedConstraint;
				}
			}
		} else {
			// Try to compute it from our role in our context
			EReference containment = eContainmentFeature();
			if ((containment == UMLPackage.Literals.NAMESPACE__OWNED_RULE)
					|| (containment == ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_RULE)) {

				if (getContext() instanceof InternalUMLRTTransition) {
					InternalUMLRTTransition transition = (InternalUMLRTTransition) getContext();
					InternalUMLRTTransition redefinedTransition = transition.rtGetAncestor();
					if (redefinedTransition != null) {
						// Look for the corresponding constraint
						if (transition.basicGetGuard() == this) {
							// Easy case
							redefinedConstraint = Optional.ofNullable(redefinedTransition.getGuard())
									.filter(InternalUMLRTConstraint.class::isInstance)
									.map(InternalUMLRTConstraint.class::cast)
									.orElse(null);
						} else {
							// Look for trigger guard. Note that, if we have to compute this,
							// it's because we are a real constraint loaded from persistent
							// storage (or we are a root definition), in either of which case
							// we would actually have a persistent reference to the trigger
							Optional<Trigger> trigger = Optional.ofNullable(constrainedElements)
									.flatMap(c -> c.stream()
											.filter(Trigger.class::isInstance).map(Trigger.class::cast)
											.findAny());
							redefinedConstraint = trigger.flatMap(myTriger -> redefinedTransition.getOwnedRules().stream()
									.filter(InternalUMLRTConstraint.class::isInstance).map(InternalUMLRTConstraint.class::cast)
									.filter(c -> {
										// We can use the utility here, which trips inheritance if needed
										Trigger itsTrigger = ConstraintRTOperations.getGuarded(c);
										return (itsTrigger != null) && UMLRTExtensionUtil.redefines(myTriger, itsTrigger);
									})
									.findAny())
									.orElse(null);
						}
					}
				}
			}

			result = (R) redefinedConstraint;
		}

		return result;
	}

	@Override
	public void rtRedefine(InternalUMLRTElement element) {
		// Constraints are not UML RedefinableElements

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

	void handleRedefinedConstraint(Constraint constraint) {
		if (specification instanceof InternalUMLRTValueSpecification) {
			((InternalUMLRTValueSpecification) specification).handleRedefinedConstraint(constraint);
		}
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
		Namespace context = getContext();
		if (context instanceof InternalUMLRTTransition) {
			InternalUMLRTTransition transition = (InternalUMLRTTransition) context;
			if (transition.getGuard() == this) {
				// I am the guard
				transition.setGuard(this);
			} else {
				// I am just an owned rule
				context.getOwnedRules().add(this);
			}

			rtAdjustStereotypes();
		}
	}

	@Override
	public void rtVirtualize() {
		Namespace context = getContext();
		if (context instanceof InternalUMLRTTransition) {
			InternalUMLRTTransition transition = (InternalUMLRTTransition) context;

			// I need to be an implicit owned rule again
			@SuppressWarnings("unchecked")
			EList<? super Constraint> implicitOwnedRules = (EList<? super Constraint>) context.eGet(ExtUMLExtPackage.Literals.NAMESPACE__IMPLICIT_OWNED_RULE);
			if (!implicitOwnedRules.contains(this)) {
				implicitOwnedRules.add(this);
			}

			if (transition.getGuard() == this) {
				// I am the guard
				transition.eUnset(UMLPackage.Literals.TRANSITION__GUARD);
			}

			rtAdjustStereotypes();
		}
	}

	@Override
	public boolean rtReinherit() {
		boolean result;

		Namespace context = getContext();
		if (!(context instanceof InternalUMLRTTransition)) {
			result = InternalUMLRTConstraint.super.rtReinherit();
		} else {
			result = run(() -> {
				boolean wasReal = !rtIsVirtual();

				if (wasReal) {
					rtVirtualize();

					// Strip me of all inheritable features
					rtUnsetAll();
				}

				return wasReal;
			});
		}

		return result;
	}

	@Override
	public void rtUnsetAll() {
		unsetName();
		unsetVisibility();
		unsetSpecification();
		unsetConstrainedElements();
	}

	@Override
	public Element basicGetOwner() {
		return rtOwner();
	}

	@Override
	public Namespace getContext() {
		Element rtOwner = rtOwner();
		return (rtOwner instanceof Namespace) ? (Namespace) rtOwner : null;
	}

	@Override
	public Namespace basicGetContext() {
		Element rtOwner = rtOwner();
		return (rtOwner instanceof Namespace) ? (Namespace) rtOwner : null;
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
	public ValueSpecification umlGetSpecification(boolean resolve) {
		return resolve ? super.getSpecification() : super.basicGetSpecification();
	}

	@Override
	public NotificationChain umlBasicSetSpecification(ValueSpecification newSpecification, NotificationChain msgs) {
		return super.basicSetSpecification(newSpecification, msgs);
	}

	@Override
	public ValueSpecification getSpecification() {
		return ConstraintRTOperations.getSpecification(this);
	}

	@Override
	public void setSpecification(ValueSpecification newSpecification) {
		ConstraintRTOperations.setSpecification(this, newSpecification);
	}

	public boolean isSetSpecification() {
		return ConstraintRTOperations.isSetSpecification(this);
	}

	public void unsetSpecification() {
		ConstraintRTOperations.unsetSpecification(this);
	}

	@Override
	public EList<Element> getConstrainedElements() {
		if (constrainedElements == null) {
			constrainedElements = new InheritableEObjectEList.Resolving<Element>(this, UMLPackage.CONSTRAINT__CONSTRAINED_ELEMENT) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isSet() {
					// I must be considered as set if the constraint is real because the
					// trigger that I constrain must be persisted
					return super.isSet() || !ConstraintRTImpl.this.rtIsVirtual();
				}

				@Override
				protected Element resolveInheritance(Element element) {
					// This isn't a containment list, and we don't resolve the
					// referenced elements (even triggers) to inherited elements
					// in our context
					return element;
				}

				@Override
				protected Element unresolveInheritance(Element element) {
					return (element instanceof InternalUMLRTElement)
							? ((InternalUMLRTElement) element).rtGetRootDefinition()
							: element;
				}
			};
		}

		EList<Element> inherited = inheritFeature(UMLPackage.Literals.CONSTRAINT__CONSTRAINED_ELEMENT);

		if (inherited != constrainedElements) {
			// Inherit this into our list. If it's null, that means we are the root definition
			((InheritableEList<Element>) constrainedElements).inherit(inherited);
		}

		return constrainedElements;
	}

	public boolean isSetConstrainedElements() {
		return (constrainedElements != null) && ((InheritableEList<Element>) constrainedElements).isSet();
	}

	public void unsetConstrainedElements() {
		if (constrainedElements != null) {
			((InheritableEList<Element>) constrainedElements).unset();
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
