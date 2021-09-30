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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTOpaqueBehavior;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.UMLRTUMLFactoryImpl;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Opaque Behavior</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTOpaqueBehaviorImpl#getRedefinitionContext <em>Redefinition Context</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTOpaqueBehaviorImpl#getRedefinedElement <em>Redefined Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTOpaqueBehaviorImpl#getBodies <em>Bodies</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTOpaqueBehaviorImpl#getBodyEntries <em>Body Entry</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTOpaqueBehaviorImpl#getEnteredState <em>Entered State</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTOpaqueBehaviorImpl#getExitedState <em>Exited State</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTOpaqueBehaviorImpl#getRedefinedBehavior <em>Redefined Behavior</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTOpaqueBehaviorImpl#getTransition <em>Transition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTOpaqueBehaviorImpl extends UMLRTNamedElementImpl implements UMLRTOpaqueBehavior {
	static final FacadeType<OpaqueBehavior, EObject, UMLRTOpaqueBehaviorImpl> TYPE = new FacadeType<>(
			UMLRTOpaqueBehaviorImpl.class,
			UMLPackage.Literals.OPAQUE_BEHAVIOR,
			null,
			UMLRTOpaqueBehaviorImpl::getInstance,
			behavior -> (UMLRTOpaqueBehaviorImpl) behavior.getRedefinedElement(), // Will always be null
			UMLRTOpaqueBehaviorImpl::maybeCreate);

	protected static class BehaviorAdapter<F extends UMLRTOpaqueBehaviorImpl> extends NamedElementAdapter.WithCode<F> {
		BehaviorAdapter(F facade) {
			super(facade);
		}

		@Override
		protected InternalFacadeEMap<String, String> getCode() {
			return get().bodies;
		}
	}

	protected InternalFacadeEMap<String, String> bodies;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTOpaqueBehaviorImpl() {
		super();
	}

	private static UMLRTOpaqueBehaviorImpl maybeCreate(OpaqueBehavior behavior) {
		UMLRTOpaqueBehaviorImpl result = null;

		// The only bvehaviors recognized as UML-RT OpaqueBehaviors are
		// the effects of transitions and entry/exit behaviors of states
		EReference containment = behavior.eContainmentFeature();
		if ((containment == UMLPackage.Literals.TRANSITION__EFFECT)
				|| (containment == UMLPackage.Literals.STATE__ENTRY)
				|| (containment == UMLPackage.Literals.STATE__EXIT)) {

			result = new UMLRTOpaqueBehaviorImpl();
		}

		return result;
	}

	/**
	 * Creates a new opaque behavior to serve as an <em>action</em> in a state machine, for example
	 * a {@linkplain UMLRTState#createEntry(String, String) state entry action} or a
	 * {@linkplain UMLRTTransition#createEffect(String, String) transition effect}.
	 *
	 * @param owner
	 *            the façade owner object
	 * @param umlReference
	 *            the UML metamodel property (of the {@code owner}'s underlying UML element) in which
	 *            to create the action
	 * @param language
	 *            an optional language (not required even if the {@code body} is provided)
	 * @param body
	 *            an optional action body text
	 *
	 * @return the new action, or {@code null} if for some reason it could not or should not be created
	 */
	static UMLRTOpaqueBehavior createAction(UMLRTNamedElement owner, EReference umlReference, String language, String body) {
		Object behavior = owner.toUML().eGet(umlReference);
		OpaqueBehavior action = (behavior instanceof OpaqueBehavior) ? (OpaqueBehavior) behavior : null;

		if (action == null) {
			action = UMLRTUMLFactoryImpl.eINSTANCE.createOpaqueBehavior();
			if (umlReference.isMany()) {
				@SuppressWarnings("unchecked")
				List<Behavior> behaviors = (List<Behavior>) owner.toUML().eGet(umlReference);
				behaviors.add(action);
			} else {
				// Yes, this replaces some other kind of behavior if there was one.
				// But that is what the client is asking for
				owner.toUML().eSet(umlReference, action);
			}
		}

		UMLRTOpaqueBehavior result = UMLRTOpaqueBehavior.getInstance(action);
		if (result != null) {
			if ((umlReference.getEContainingClass() == UMLPackage.Literals.TRANSITION)
					|| (umlReference.getEContainingClass() == UMLPackage.Literals.STATE)) {

				// Action behaviors are not re-entrant (cf. http://eclip.se/513261)
				action.setIsReentrant(false);
			}

			if (body != null) {
				Map<String, String> bodies = result.getBodies();
				bodies.put(language, body); // Language can be null, that's fine
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
		return UMLRTUMLRTPackage.Literals.OPAQUE_BEHAVIOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinitionContext() {
		UMLRTState enteredState = getEnteredState();
		if (enteredState != null) {
			return enteredState;
		}
		UMLRTState exitedState = getExitedState();
		if (exitedState != null) {
			return exitedState;
		}
		UMLRTTransition transition = getTransition();
		if (transition != null) {
			return transition;
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
		UMLRTOpaqueBehavior redefinedBehavior = getRedefinedBehavior();
		if (redefinedBehavior != null) {
			return redefinedBehavior;
		}
		return super.getRedefinedElement();
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTOpaqueBehaviorImpl> createFacadeAdapter() {
		return new BehaviorAdapter<>(this);
	}

	/**
	 * Obtains the canonical instance of the façade for a transition effect or state entry/exit behavior.
	 *
	 * @param behavior
	 *            an opaque behavior in the UML model
	 *
	 * @return its façade, or {@code null} if the state is not a valid UML-RT transition effect or
	 *         state entry/exit behavior
	 */
	public static UMLRTOpaqueBehaviorImpl getInstance(OpaqueBehavior behavior) {
		return getFacade(behavior, TYPE);
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
			bodies = new Code(this, UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__BODY_ENTRY);
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
	public UMLRTState getEnteredState() {
		State result = null;

		OpaqueBehavior uml = toUML();
		if (uml.eContainmentFeature() == UMLPackage.Literals.STATE__ENTRY) {
			result = (State) uml.eContainer();
		}

		return (result == null) ? null : UMLRTState.getInstance(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setEnteredState(UMLRTState newEnteredState) {
		newEnteredState.toUML().setEntry(toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTState getExitedState() {
		State result = null;

		OpaqueBehavior uml = toUML();
		if (uml.eContainmentFeature() == UMLPackage.Literals.STATE__EXIT) {
			result = (State) uml.eContainer();
		}

		return (result == null) ? null : UMLRTState.getInstance(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setExitedState(UMLRTState newExitedState) {
		newExitedState.toUML().setExit(toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTOpaqueBehavior getRedefinedBehavior() {
		UMLRTOpaqueBehavior result;

		if (toUML() instanceof InternalUMLRTElement) {
			OpaqueBehavior superBehavior = (OpaqueBehavior) ((InternalUMLRTElement) toUML()).rtGetRedefinedElement();
			result = UMLRTOpaqueBehavior.getInstance(superBehavior);
		} else {
			OpaqueBehavior superBehavior = toUML().getRedefinedBehaviors().isEmpty()
					? null
					: toUML().getRedefinedBehaviors().stream()
							.filter(OpaqueBehavior.class::isInstance).map(OpaqueBehavior.class::cast)
							.findFirst().orElse(null);
			if (superBehavior != null) {
				result = UMLRTOpaqueBehavior.getInstance(superBehavior);
			} else {
				UMLRTNamedElement redef = super.getRedefinedElement();
				result = (redef instanceof UMLRTOpaqueBehavior) ? (UMLRTOpaqueBehavior) redef : null;
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
	public UMLRTTransition getTransition() {
		Transition result = null;

		OpaqueBehavior uml = toUML();
		if (uml.eContainmentFeature() == UMLPackage.Literals.TRANSITION__EFFECT) {
			result = (Transition) uml.eContainer();
		}

		return (result == null) ? null : UMLRTTransition.getInstance(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setTransition(UMLRTTransition newTransition) {
		newTransition.toUML().setEffect(toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public OpaqueBehavior toUML() {
		return (OpaqueBehavior) super.toUML();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Stream<? extends UMLRTOpaqueBehavior> allRedefinitions() {
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
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__BODIES:
			return getBodies();
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__BODY_ENTRY:
			return getBodyEntries();
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__ENTERED_STATE:
			return getEnteredState();
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__EXITED_STATE:
			return getExitedState();
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__REDEFINED_BEHAVIOR:
			return getRedefinedBehavior();
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__TRANSITION:
			return getTransition();
		}
		return super.eGet(featureID, resolve, coreType);
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
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__BODY_ENTRY:
			getBodyEntries().clear();
			getBodyEntries().addAll((Collection<? extends Map.Entry<String, String>>) newValue);
			return;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__ENTERED_STATE:
			setEnteredState((UMLRTState) newValue);
			return;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__EXITED_STATE:
			setExitedState((UMLRTState) newValue);
			return;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__TRANSITION:
			setTransition((UMLRTTransition) newValue);
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
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__BODY_ENTRY:
			getBodyEntries().clear();
			return;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__ENTERED_STATE:
			setEnteredState((UMLRTState) null);
			return;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__EXITED_STATE:
			setExitedState((UMLRTState) null);
			return;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__TRANSITION:
			setTransition((UMLRTTransition) null);
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
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__REDEFINITION_CONTEXT:
			return isSetRedefinitionContext();
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__REDEFINED_ELEMENT:
			return isSetRedefinedElement();
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__BODIES:
			return getBodies() != null;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__BODY_ENTRY:
			return !getBodyEntries().isEmpty();
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__ENTERED_STATE:
			return getEnteredState() != null;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__EXITED_STATE:
			return getExitedState() != null;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__REDEFINED_BEHAVIOR:
			return getRedefinedBehavior() != null;
		case UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__TRANSITION:
			return getTransition() != null;
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
				|| eIsSet(UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__ENTERED_STATE)
				|| eIsSet(UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__EXITED_STATE)
				|| eIsSet(UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__TRANSITION);
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
				|| eIsSet(UMLRTUMLRTPackage.OPAQUE_BEHAVIOR__REDEFINED_BEHAVIOR);
	}

} // UMLRTOpaqueBehaviorImpl
