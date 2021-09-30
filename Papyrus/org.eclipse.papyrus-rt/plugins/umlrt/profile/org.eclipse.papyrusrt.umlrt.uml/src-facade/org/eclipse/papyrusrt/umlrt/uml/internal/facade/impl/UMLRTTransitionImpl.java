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

import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.remove;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTGuard;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTRegion;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.FacadeContentsEList;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl#getRedefinitionContext <em>Redefinition Context</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl#getRedefinableElements <em>Redefinable Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl#getRedefinedElement <em>Redefined Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl#getStateMachine <em>State Machine</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl#getSource <em>Source</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl#getTarget <em>Target</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl#getTriggers <em>Trigger</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl#getGuard <em>Guard</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl#getRedefinedTransition <em>Redefined Transition</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl#getKind <em>Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl#isInternal <em>Internal</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl#getEffect <em>Effect</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTransitionImpl#getState <em>State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTTransitionImpl extends UMLRTNamedElementImpl implements UMLRTTransition {
	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final TransitionKind KIND_EDEFAULT = TransitionKind.EXTERNAL_LITERAL;

	/**
	 * The default value of the '{@link #isInternal() <em>Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isInternal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INTERNAL_EDEFAULT = false;

	static final FacadeType<Transition, EObject, UMLRTTransitionImpl> TYPE = new FacadeType<>(
			UMLRTTransitionImpl.class,
			UMLPackage.Literals.TRANSITION,
			null,
			UMLRTTransitionImpl::getInstance,
			transition -> (UMLRTTransitionImpl) transition.getRedefinedTransition(),
			UMLRTTransitionImpl::maybeCreate);

	private static final FacadeReference<Trigger, EObject, UMLRTTrigger, UMLRTTriggerImpl> TRIGGER_REFERENCE = new FacadeReference<>(
			UMLRTTriggerImpl.TYPE,
			UMLRTUMLRTPackage.TRANSITION__TRIGGER,
			UMLRTTrigger.class,
			UMLPackage.Literals.TRANSITION__TRIGGER,
			ExtUMLExtPackage.Literals.TRANSITION__IMPLICIT_TRIGGER,
			UMLPackage.Literals.TRIGGER);

	protected static class TransitionAdapter<F extends UMLRTTransitionImpl> extends NamedElementAdapter<F> {

		TransitionAdapter(F facade) {
			super(facade);
		}

		@Override
		protected List<? extends FacadeObject> getFacadeList(EObject owner, EReference reference, EObject object) {
			List<? extends FacadeObject> result;

			if ((reference == UMLPackage.Literals.TRANSITION__TRIGGER)
					|| (reference == ExtUMLExtPackage.Literals.TRANSITION__IMPLICIT_TRIGGER)) {

				result = get().triggers;
			} else {
				result = super.getFacadeList(owner, reference, object);
			}

			return result;
		}

		@Override
		protected InternalFacadeEList<? extends FacadeObject> validateObject(EObject owner, EReference reference, FacadeObject object) {
			InternalFacadeEList<? extends FacadeObject> result = null;

			if (object instanceof UMLRTTrigger) {
				if ((reference == UMLPackage.Literals.TRANSITION__TRIGGER)
						|| (reference == ExtUMLExtPackage.Literals.TRANSITION__IMPLICIT_TRIGGER)) {
					result = get().triggers;
				}
			} else {
				result = super.validateObject(owner, reference, object);
			}

			return result;
		}

		@Override
		protected void handleObjectReplaced(Notification msg, int position, FacadeObject oldObject, FacadeObject newObject) {
			if (msg.getFeature() == UMLPackage.Literals.TRANSITION__GUARD) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), Notification.SET, UMLRTUMLRTPackage.Literals.TRANSITION__GUARD, oldObject, newObject));
				}
			} else if (msg.getFeature() == UMLPackage.Literals.TRANSITION__SOURCE) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), Notification.SET, UMLRTUMLRTPackage.Literals.TRANSITION__SOURCE, oldObject, newObject));
				}
			} else if (msg.getFeature() == UMLPackage.Literals.TRANSITION__TARGET) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), Notification.SET, UMLRTUMLRTPackage.Literals.TRANSITION__TARGET, oldObject, newObject));
				}
			} else {
				super.handleObjectReplaced(msg, position, oldObject, newObject);
			}
		}

		@Override
		protected void handleValueReplaced(Notification msg, int position, Object oldValue, Object newValue) {
			if (msg.getFeature() == UMLPackage.Literals.TRANSITION__KIND) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), Notification.SET, UMLRTUMLRTPackage.Literals.TRANSITION__KIND, oldValue, newValue));

					// Does it imply a change in the isInternal property?
					if ((oldValue == TransitionKind.INTERNAL_LITERAL) || (newValue == TransitionKind.INTERNAL_LITERAL)) {
						get().eNotify(new ENotificationImpl(get(), Notification.SET, UMLRTUMLRTPackage.Literals.TRANSITION__INTERNAL,
								oldValue == TransitionKind.INTERNAL_LITERAL, newValue == TransitionKind.INTERNAL_LITERAL));
					}
				}
			} else {
				super.handleValueReplaced(msg, position, oldValue, newValue);
			}
		}
	}

	InternalFacadeEList<UMLRTTrigger> triggers;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTTransitionImpl() {
		super();
	}

	/**
	 * Obtains the canonical instance of the façade for a transition.
	 *
	 * @param transition
	 *            a transition in the UML model
	 *
	 * @return its façade, or {@code null} if the class is not a valid transition
	 */
	public static UMLRTTransitionImpl getInstance(Transition transition) {
		return getFacade(transition, TYPE);
	}

	/**
	 * Transitions don't have stereotypes of their own, so guard on the
	 * stereotype of the container.
	 *
	 * @param transition
	 *            a transition
	 * @return maybe a façade for it, if it's in an RT region
	 */
	private static UMLRTTransitionImpl maybeCreate(Transition transition) {
		Region container = transition.getContainer();

		return ((container != null) && (UMLUtil.getStereotypeApplication(container, RTRegion.class) != null))
				? new UMLRTTransitionImpl()
				: null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.TRANSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinitionContext() {
		UMLRTStateMachine stateMachine = getStateMachine();
		if (stateMachine != null) {
			return stateMachine;
		}
		UMLRTState state = getState();
		if (state != null) {
			return state;
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
	public List<UMLRTNamedElement> getRedefinableElements() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			List<UMLRTNamedElement> redefinableElements = (List<UMLRTNamedElement>) cache.get(eResource, this, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT);
			if (redefinableElements == null) {
				cache.put(eResource, this, UMLRTUMLRTPackage.Literals.NAMED_ELEMENT__REDEFINABLE_ELEMENT,
						redefinableElements = new DerivedUnionEObjectEList<>(UMLRTNamedElement.class, this, UMLRTUMLRTPackage.TRANSITION__REDEFINABLE_ELEMENT, REDEFINABLE_ELEMENT_ESUBSETS));
			}
			return redefinableElements;
		}
		return new DerivedUnionEObjectEList<>(UMLRTNamedElement.class, this, UMLRTUMLRTPackage.TRANSITION__REDEFINABLE_ELEMENT, REDEFINABLE_ELEMENT_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getRedefinableElements() <em>Redefinable Element</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRedefinableElements()
	 * @generated
	 * @ordered
	 */
	protected static final int[] REDEFINABLE_ELEMENT_ESUBSETS = new int[] { UMLRTUMLRTPackage.TRANSITION__TRIGGER, UMLRTUMLRTPackage.TRANSITION__GUARD, UMLRTUMLRTPackage.TRANSITION__EFFECT };

	@Override
	protected BasicFacadeAdapter<? extends UMLRTTransitionImpl> createFacadeAdapter() {
		return new TransitionAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinedElement() {
		UMLRTTransition redefinedTransition = getRedefinedTransition();
		if (redefinedTransition != null) {
			return redefinedTransition;
		}
		return super.getRedefinedElement();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTStateMachine getStateMachine() {
		Element owner = toUML().getOwner();
		if (owner instanceof Region) {
			owner = owner.getOwner();
		}
		return (owner instanceof StateMachine)
				? UMLRTStateMachine.getInstance((StateMachine) owner)
				: null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTVertex getSource() {
		Vertex result = toUML().getSource();
		return (result == null) ? null : UMLRTVertex.getInstance(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setSource(UMLRTVertex newSource) {
		toUML().setSource((newSource == null) ? null : newSource.toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTVertex getTarget() {
		Vertex result = toUML().getTarget();
		return (result == null) ? null : UMLRTVertex.getInstance(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setTarget(UMLRTVertex newTarget) {
		toUML().setTarget((newTarget == null) ? null : newTarget.toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTTrigger> getTriggers() {
		if (triggers == null) {
			triggers = (InternalFacadeEList<UMLRTTrigger>) getFacades(TRIGGER_REFERENCE);
		}
		return triggers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTrigger getTrigger(String name) {
		return getTrigger(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTTrigger getTrigger(String name, boolean ignoreCase) {
		triggerLoop: for (UMLRTTrigger trigger : getTriggers()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(trigger.getName()) : name.equals(trigger.getName()))) {
				continue triggerLoop;
			}
			return trigger;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTGuard getGuard() {
		Constraint result = toUML().getGuard();
		return (result == null) ? null : UMLRTGuard.getInstance(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setGuard(UMLRTGuard newGuard) {
		UMLRTGuard oldGuard = getGuard();

		if (newGuard != oldGuard) {
			if (oldGuard != null) {
				// A constraint that functions as a UML-RT transition guard
				// cannot just be an owned rule; it is either the guard or
				// nothing
				remove(toUML(), UMLPackage.Literals.NAMESPACE__OWNED_RULE, oldGuard.toUML());
			}
			if (newGuard != null) {
				Constraint uml = newGuard.toUML();

				UMLRTTrigger trigger = newGuard.getTrigger();
				if (trigger != null) {
					// It is becoming a transition guard
					uml.getConstrainedElements().remove(trigger.toUML());

					// This is only used for trigger guards
					RTGuard stereo = UMLUtil.getStereotypeApplication(uml, RTGuard.class);
					if (stereo != null) {
						uml.unapplyStereotype(UMLUtil.getStereotype(stereo));
					}
				}

				toUML().setGuard(uml);
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
	public UMLRTTransition getRedefinedTransition() {
		UMLRTTransition result;

		if (toUML() instanceof InternalUMLRTElement) {
			Transition superTransition = (Transition) ((InternalUMLRTElement) toUML()).rtGetRedefinedElement();
			result = UMLRTTransition.getInstance(superTransition);
		} else {
			Transition superTransition = toUML().getRedefinedTransition();
			if (superTransition != null) {
				result = UMLRTTransition.getInstance(superTransition);
			} else {
				UMLRTNamedElement redef = super.getRedefinedElement();
				result = (redef instanceof UMLRTTransition) ? (UMLRTTransition) redef : null;
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
	public TransitionKind getKind() {
		return toUML().getKind();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setKind(TransitionKind newKind) {
		toUML().setKind(newKind);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isInternal() {
		return getKind() == TransitionKind.INTERNAL_LITERAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTOpaqueBehavior getEffect() {
		Behavior result = toUML().getEffect();
		return (result instanceof OpaqueBehavior) ? UMLRTOpaqueBehavior.getInstance((OpaqueBehavior) result) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setEffect(UMLRTOpaqueBehavior newEffect) {
		if (newEffect != getEffect()) {
			if (newEffect == null) {
				toUML().setEffect(null);
			} else {
				toUML().setEffect(newEffect.toUML());
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
	public UMLRTState getState() {
		Element owner = toUML().getOwner();
		if (owner instanceof Region) {
			owner = owner.getOwner();
		}
		return (owner instanceof State)
				? UMLRTState.getInstance((State) owner)
				: null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Transition toUML() {
		return (Transition) super.toUML();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTStateMachine containingStateMachine() {
		UMLRTStateMachine result = getStateMachine();

		if (result == null) {
			UMLRTState state = getState();
			if (state != null) {
				result = state.containingStateMachine();
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
	public UMLRTTrigger createTrigger(UMLRTProtocolMessage protocolMessage, UMLRTPort port) {
		Trigger trigger = toUML().createTrigger((protocolMessage == null) ? null : protocolMessage.getName());
		if (port != null) {
			trigger.getPorts().add(port.toUML());
		}
		if (protocolMessage != null) {
			trigger.setEvent(protocolMessage.toReceiveEvent());
		}

		UMLRTTrigger result = UMLRTTrigger.getInstance(trigger);
		result.setReceiveAnyMessage(protocolMessage == null);

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTGuard createGuard(String language, String body) {
		Constraint guard = toUML().getGuard();
		if (guard == null) {
			guard = toUML().createGuard(null);
			guard.createSpecification(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION);
		}

		UMLRTGuard result = UMLRTGuard.getInstance(guard);
		Map<String, String> bodies = result.getBodies();
		bodies.put(language, body);

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTOpaqueBehavior createEffect(String language, String body) {
		return UMLRTOpaqueBehaviorImpl.createAction(this, UMLPackage.Literals.TRANSITION__EFFECT, language, body);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Stream<? extends UMLRTTransition> allRedefinitions() {
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
		case UMLRTUMLRTPackage.TRANSITION__STATE_MACHINE:
			return getStateMachine();
		case UMLRTUMLRTPackage.TRANSITION__SOURCE:
			return getSource();
		case UMLRTUMLRTPackage.TRANSITION__TARGET:
			return getTarget();
		case UMLRTUMLRTPackage.TRANSITION__TRIGGER:
			return getTriggers();
		case UMLRTUMLRTPackage.TRANSITION__GUARD:
			return getGuard();
		case UMLRTUMLRTPackage.TRANSITION__REDEFINED_TRANSITION:
			return getRedefinedTransition();
		case UMLRTUMLRTPackage.TRANSITION__KIND:
			return getKind();
		case UMLRTUMLRTPackage.TRANSITION__INTERNAL:
			return isInternal();
		case UMLRTUMLRTPackage.TRANSITION__EFFECT:
			return getEffect();
		case UMLRTUMLRTPackage.TRANSITION__STATE:
			return getState();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	protected Object facadeGetAll(int referenceID) {
		switch (referenceID) {
		case UMLRTUMLRTPackage.TRANSITION__TRIGGER:
			return getFacades(TRIGGER_REFERENCE, true);
		case UMLRTUMLRTPackage.CAPSULE__REDEFINABLE_ELEMENT:
			return new FacadeContentsEList<>(this, true, REDEFINABLE_ELEMENT_ESUBSETS);
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLRTUMLRTPackage.TRANSITION__SOURCE:
			setSource((UMLRTVertex) newValue);
			return;
		case UMLRTUMLRTPackage.TRANSITION__TARGET:
			setTarget((UMLRTVertex) newValue);
			return;
		case UMLRTUMLRTPackage.TRANSITION__GUARD:
			setGuard((UMLRTGuard) newValue);
			return;
		case UMLRTUMLRTPackage.TRANSITION__KIND:
			setKind((TransitionKind) newValue);
			return;
		case UMLRTUMLRTPackage.TRANSITION__EFFECT:
			setEffect((UMLRTOpaqueBehavior) newValue);
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
		case UMLRTUMLRTPackage.TRANSITION__SOURCE:
			setSource((UMLRTVertex) null);
			return;
		case UMLRTUMLRTPackage.TRANSITION__TARGET:
			setTarget((UMLRTVertex) null);
			return;
		case UMLRTUMLRTPackage.TRANSITION__GUARD:
			setGuard((UMLRTGuard) null);
			return;
		case UMLRTUMLRTPackage.TRANSITION__KIND:
			setKind(KIND_EDEFAULT);
			return;
		case UMLRTUMLRTPackage.TRANSITION__EFFECT:
			setEffect((UMLRTOpaqueBehavior) null);
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
		case UMLRTUMLRTPackage.TRANSITION__REDEFINITION_CONTEXT:
			return isSetRedefinitionContext();
		case UMLRTUMLRTPackage.TRANSITION__REDEFINABLE_ELEMENT:
			return isSetRedefinableElements();
		case UMLRTUMLRTPackage.TRANSITION__REDEFINED_ELEMENT:
			return isSetRedefinedElement();
		case UMLRTUMLRTPackage.TRANSITION__STATE_MACHINE:
			return getStateMachine() != null;
		case UMLRTUMLRTPackage.TRANSITION__SOURCE:
			return getSource() != null;
		case UMLRTUMLRTPackage.TRANSITION__TARGET:
			return getTarget() != null;
		case UMLRTUMLRTPackage.TRANSITION__TRIGGER:
			return !getTriggers().isEmpty();
		case UMLRTUMLRTPackage.TRANSITION__GUARD:
			return getGuard() != null;
		case UMLRTUMLRTPackage.TRANSITION__REDEFINED_TRANSITION:
			return getRedefinedTransition() != null;
		case UMLRTUMLRTPackage.TRANSITION__KIND:
			return getKind() != KIND_EDEFAULT;
		case UMLRTUMLRTPackage.TRANSITION__INTERNAL:
			return isInternal() != INTERNAL_EDEFAULT;
		case UMLRTUMLRTPackage.TRANSITION__EFFECT:
			return getEffect() != null;
		case UMLRTUMLRTPackage.TRANSITION__STATE:
			return getState() != null;
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
				|| eIsSet(UMLRTUMLRTPackage.TRANSITION__STATE_MACHINE)
				|| eIsSet(UMLRTUMLRTPackage.TRANSITION__STATE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetRedefinableElements() {
		return super.isSetRedefinableElements()
				|| eIsSet(UMLRTUMLRTPackage.TRANSITION__TRIGGER)
				|| eIsSet(UMLRTUMLRTPackage.TRANSITION__GUARD)
				|| eIsSet(UMLRTUMLRTPackage.TRANSITION__EFFECT);
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
				|| eIsSet(UMLRTUMLRTPackage.TRANSITION__REDEFINED_TRANSITION);
	}

} // UMLRTTransitionImpl
