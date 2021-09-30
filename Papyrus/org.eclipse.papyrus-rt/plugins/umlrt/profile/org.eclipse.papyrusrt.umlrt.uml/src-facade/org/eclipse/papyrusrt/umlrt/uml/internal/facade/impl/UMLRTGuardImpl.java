/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl;

import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.getUMLRTContents;
import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.remove;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTGuard;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Guard</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTGuardImpl#getRedefinitionContext <em>Redefinition Context</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTGuardImpl#getRedefinedElement <em>Redefined Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTGuardImpl#getBodies <em>Bodies</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTGuardImpl#getBodyEntries <em>Body Entry</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTGuardImpl#getTransition <em>Transition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTGuardImpl#getRedefinedGuard <em>Redefined Guard</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTGuardImpl#getTrigger <em>Trigger</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTGuardImpl extends UMLRTNamedElementImpl implements UMLRTGuard {
	static final FacadeType<Constraint, EObject, UMLRTGuardImpl> TYPE = new FacadeType<>(
			UMLRTGuardImpl.class,
			UMLPackage.Literals.CONSTRAINT,
			null,
			UMLRTGuardImpl::getInstance,
			guard -> (UMLRTGuardImpl) guard.getRedefinedElement(), // Will always be null
			UMLRTGuardImpl::maybeCreate);

	protected static class GuardAdapter<F extends UMLRTGuardImpl> extends NamedElementAdapter.WithCode<F> {
		GuardAdapter(F facade) {
			super(facade);
		}

		@Override
		protected InternalFacadeEMap<String, String> getCode() {
			return get().bodies;
		}

		//
		// The opaque expression is a contained element; we are not, ourselves, the code thing
		//

		@Override
		public void setTarget(Notifier newTarget) {
			super.setTarget(newTarget);

			if (newTarget instanceof Constraint) {
				Constraint constraint = (Constraint) newTarget;
				ValueSpecification specification = constraint.getSpecification();
				if (specification instanceof OpaqueExpression) {
					adaptAdditional(specification);
				}
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (oldTarget instanceof Constraint) {
				unadaptAdditional();
			}

			super.unsetTarget(oldTarget);
		}

		@Override
		protected void handleObjectReplaced(Notification msg, int position, EObject oldObject, EObject newObject) {
			if (msg.getFeature() == UMLPackage.Literals.CONSTRAINT__SPECIFICATION) {
				if (oldObject instanceof OpaqueExpression) {
					unadaptAdditional(oldObject);
				}
				if (newObject instanceof OpaqueExpression) {
					adaptAdditional(newObject);
				}
			} else {
				super.handleObjectReplaced(msg, position, oldObject, newObject);
			}
		}

	}

	protected InternalFacadeEMap<String, String> bodies;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTGuardImpl() {
		super();
	}

	private static UMLRTGuardImpl maybeCreate(Constraint constraint) {
		UMLRTGuardImpl result = null;

		// The only constraints recognized as UML-RT Guards are
		// the guard conditions of transition and those that are
		// stereotyped as «RTGuard»
		Namespace context = constraint.getContext();
		if (context instanceof Transition) {
			Transition transition = (Transition) context;
			if ((transition.getGuard() == constraint)
					|| (UMLUtil.getStereotypeApplication(constraint, RTGuard.class) != null)) {

				result = new UMLRTGuardImpl();
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.GUARD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinitionContext() {
		UMLRTTransition transition = getTransition();
		if (transition != null) {
			return transition;
		}
		UMLRTTrigger trigger = getTrigger();
		if (trigger != null) {
			return trigger;
		}
		return super.getRedefinitionContext();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinedElement() {
		UMLRTGuard redefinedGuard = getRedefinedGuard();
		if (redefinedGuard != null) {
			return redefinedGuard;
		}
		return super.getRedefinedElement();
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTGuardImpl> createFacadeAdapter() {
		return new GuardAdapter<>(this);
	}

	/**
	 * Obtains the canonical instance of the façade for a transition or trigger guard.
	 *
	 * @param guard
	 *            a transition or triger guard in the UML model
	 *
	 * @return its façade, or {@code null} if the class is not a valid transition or trigger guard
	 */
	public static UMLRTGuardImpl getInstance(Constraint guard) {
		UMLRTGuardImpl result = null;

		Namespace context = (guard == null) ? null : guard.getContext();
		if (context instanceof Transition) {
			Transition transition = (Transition) context;
			if (guard == transition.getGuard()) {
				// Easy case
				result = getFacade(guard, TYPE);
			} else if (UMLUtil.getStereotypeApplication(guard, RTGuard.class) != null) {
				// It purports to be a trigger guard
				List<Element> constrained = guard.getConstrainedElements();
				if (!constrained.isEmpty()) {
					Trigger trigger = (Trigger) EcoreUtil.getObjectByType(constrained, UMLPackage.Literals.TRIGGER);
					if (trigger != null) {
						// Resolve the trigger to the redefinition in our transition
						UMLRTTrigger triggerFacade = UMLRTTrigger.getInstance(trigger);
						UMLRTTransition transitionFacade = UMLRTTransition.getInstance(transition);
						if ((triggerFacade != null) && (transitionFacade != null)
								&& (transitionFacade.getRedefinitionOf(triggerFacade) != null)) {
							// That's our triger
							result = getFacade(guard, TYPE);
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Map<String, String> getBodies() {
		getBodyEntries();
		return bodies.map();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public List<Map.Entry<String, String>> getBodyEntries() {
		if (bodies == null) {
			bodies = new Code(this, UMLRTUMLRTPackage.GUARD__BODY_ENTRY) {

				private static final long serialVersionUID = 1L;

				@Override
				protected EObject getCodeOwner() {
					Constraint constraint = (Constraint) super.getCodeOwner();
					ValueSpecification spec = constraint.getSpecification();
					return (spec instanceof OpaqueExpression) ? spec : null;
				}
			};
		}

		return bodies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTTransition getTransition() {
		UMLRTTransition result = null;

		Namespace context = toUML().getContext();

		if (context instanceof Transition) {
			// A trigger guard's context also is a transition. We need to
			// be in the guard role, specifically
			Transition transition = (Transition) context;
			if (transition.getGuard() == toUML()) {
				result = UMLRTTransition.getInstance(transition);
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setTransition(UMLRTTransition newTransition) {
		if (newTransition != getTransition()) {
			Constraint uml = toUML();
			UMLRTTrigger trigger = getTrigger();
			if (trigger != null) {
				// I am becoming a transition guard
				uml.getConstrainedElements().remove(trigger.toUML());

				// This is only used for trigger guards
				RTGuard stereo = UMLUtil.getStereotypeApplication(uml, RTGuard.class);
				if (stereo != null) {
					uml.unapplyStereotype(UMLUtil.getStereotype(stereo));
				}
			}

			if (newTransition == null) {
				uml.setContext(null);
			} else {
				// Can't just set the context because we need to have the guard role, specifically
				newTransition.toUML().setGuard(uml);
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTGuard getRedefinedGuard() {
		UMLRTGuard result = null;

		// Constraint redefinition is an irregular UML-RT capability only
		if (toUML() instanceof InternalUMLRTElement) {
			Constraint superGuard = (Constraint) ((InternalUMLRTElement) toUML()).rtGetRedefinedElement();
			result = UMLRTGuard.getInstance(superGuard);
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTTrigger getTrigger() {
		UMLRTTrigger result = null;

		Constraint uml = toUML();
		RTGuard stereo = UMLUtil.getStereotypeApplication(uml, RTGuard.class);
		if (stereo != null) {
			// I expect to be a trigger guard
			result = uml.getConstrainedElements().stream()
					.filter(Trigger.class::isInstance).map(Trigger.class::cast)
					.map(this::resolve)
					.findFirst()
					.map(UMLRTTrigger::getInstance)
					.orElse(null);
		}

		return result;
	}

	/**
	 * Resolve a potentially inherited trigger to the redefinition
	 * in our owning transition's context.
	 * 
	 * @param trigger
	 *            a trigger
	 * @return the resolved local trigger, or the original {@code trigger}
	 *         if it is or there is no local trigger
	 */
	protected Trigger resolve(Trigger trigger) {
		Trigger result = trigger;

		Constraint uml = toUML();
		if ((uml != null) && (uml.getContext() instanceof Transition)) {
			Transition transition = (Transition) uml.getContext();

			if (trigger.getOwner() != transition) {
				List<Trigger> triggers = getUMLRTContents(transition, UMLPackage.Literals.TRANSITION__TRIGGER);
				result = triggers.stream()
						.filter(t -> UMLRTExtensionUtil.redefines(t, trigger))
						.findAny().orElse(trigger);
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setTrigger(UMLRTTrigger newTrigger) {
		if (newTrigger != getTrigger()) {
			Constraint uml = toUML();
			UMLRTTransition oldContext = getTransition();
			UMLRTTransition newContext = (newTrigger == null) ? null : newTrigger.getTransition();

			if (newContext != oldContext) {
				if (newContext != null) {
					// I need to be owned by the new trigger's transition
					uml.setContext(newContext.toUML());
				} else {
					// I cannot remain in the old context
					remove(oldContext.toUML(), UMLPackage.Literals.NAMESPACE__OWNED_RULE, uml);
				}
			}

			UMLRTTrigger oldTrigger = getTrigger();
			if (oldTrigger != null) {
				uml.getConstrainedElements().remove(oldTrigger.toUML());
			}
			if (newTrigger != null) {
				// This is required for trigger guards
				RTGuard stereo = UMLUtil.getStereotypeApplication(uml, RTGuard.class);
				if (stereo == null) {
					StereotypeApplicationHelper.getInstance(uml).applyStereotype(uml,
							UMLRTStateMachinesPackage.Literals.RT_GUARD);
				}

				uml.getConstrainedElements().add(0, newTrigger.toUML());
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Constraint toUML() {
		return (Constraint) super.toUML();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Stream<? extends UMLRTGuard> allRedefinitions() {
		Predicate<UMLRTNamedElement> excluded = UMLRTNamedElement::isExcluded;
		UMLRTNamedElement context = getRedefinitionContext();
		return (context == null) ? Stream.of(this) : context.allRedefinitions()
				.map(c -> c.getRedefinitionOf(this))
				.filter(Objects::nonNull)
				.filter(excluded.negate());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UMLRTUMLRTPackage.GUARD__BODIES:
			return getBodies();
		case UMLRTUMLRTPackage.GUARD__BODY_ENTRY:
			return getBodyEntries();
		case UMLRTUMLRTPackage.GUARD__TRANSITION:
			return getTransition();
		case UMLRTUMLRTPackage.GUARD__REDEFINED_GUARD:
			return getRedefinedGuard();
		case UMLRTUMLRTPackage.GUARD__TRIGGER:
			return getTrigger();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	protected Object facadeGetAll(int referenceID) {
		switch (referenceID) {
		default:
			return eGet(referenceID, true, true);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLRTUMLRTPackage.GUARD__BODY_ENTRY:
			getBodyEntries().clear();
			getBodyEntries().addAll((Collection<? extends Map.Entry<String, String>>) newValue);
			return;
		case UMLRTUMLRTPackage.GUARD__TRANSITION:
			setTransition((UMLRTTransition) newValue);
			return;
		case UMLRTUMLRTPackage.GUARD__TRIGGER:
			setTrigger((UMLRTTrigger) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLRTUMLRTPackage.GUARD__BODY_ENTRY:
			getBodyEntries().clear();
			return;
		case UMLRTUMLRTPackage.GUARD__TRANSITION:
			setTransition((UMLRTTransition) null);
			return;
		case UMLRTUMLRTPackage.GUARD__TRIGGER:
			setTrigger((UMLRTTrigger) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLRTUMLRTPackage.GUARD__REDEFINITION_CONTEXT:
			return isSetRedefinitionContext();
		case UMLRTUMLRTPackage.GUARD__REDEFINED_ELEMENT:
			return isSetRedefinedElement();
		case UMLRTUMLRTPackage.GUARD__BODIES:
			return getBodies() != null;
		case UMLRTUMLRTPackage.GUARD__BODY_ENTRY:
			return !getBodyEntries().isEmpty();
		case UMLRTUMLRTPackage.GUARD__TRANSITION:
			return getTransition() != null;
		case UMLRTUMLRTPackage.GUARD__REDEFINED_GUARD:
			return getRedefinedGuard() != null;
		case UMLRTUMLRTPackage.GUARD__TRIGGER:
			return getTrigger() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetRedefinitionContext() {
		return super.isSetRedefinitionContext()
				|| eIsSet(UMLRTUMLRTPackage.GUARD__TRANSITION)
				|| eIsSet(UMLRTUMLRTPackage.GUARD__TRIGGER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetRedefinedElement() {
		return super.isSetRedefinedElement()
				|| eIsSet(UMLRTUMLRTPackage.GUARD__REDEFINED_GUARD);
	}

} // UMLRTGuardImpl
