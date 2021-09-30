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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.DelegatingEcoreEList;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTGuard;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.AnyReceiveEvent;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Event;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trigger</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTriggerImpl#getRedefinedElement <em>Redefined Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTriggerImpl#getRedefinitionContext <em>Redefinition Context</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTriggerImpl#getProtocolMessage <em>Protocol Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTriggerImpl#getPorts <em>Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTriggerImpl#getGuard <em>Guard</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTriggerImpl#getRedefinedTrigger <em>Redefined Trigger</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTriggerImpl#isReceiveAnyMessage <em>Receive Any Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTTriggerImpl#getTransition <em>Transition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTTriggerImpl extends UMLRTNamedElementImpl implements UMLRTTrigger {
	/**
	 * The default value of the '{@link #isReceiveAnyMessage() <em>Receive Any Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isReceiveAnyMessage()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RECEIVE_ANY_MESSAGE_EDEFAULT = false;

	static final FacadeType<Trigger, EObject, UMLRTTriggerImpl> TYPE = new FacadeType<>(
			UMLRTTriggerImpl.class,
			UMLPackage.Literals.TRIGGER,
			null,
			UMLRTTriggerImpl::getInstance,
			trigger -> (UMLRTTriggerImpl) trigger.getRedefinedTrigger(),
			UMLRTTriggerImpl::new);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTTriggerImpl() {
		super();
	}

	/**
	 * Obtains the canonical instance of the façade for a transition trigger.
	 *
	 * @param guard
	 *            a transition trigger in the UML model
	 *
	 * @return its façade, or {@code null} if the class is not a valid transition trigger
	 */
	public static UMLRTTriggerImpl getInstance(Trigger trigger) {
		return getTransition(trigger)
				.map(__ -> getFacade(trigger, TYPE))
				.orElse(null);
	}

	static Optional<Transition> getTransition(Trigger trigger) {
		return Optional.ofNullable(trigger)
				.map(Trigger::getNamespace)
				.filter(Transition.class::isInstance)
				.map(Transition.class::cast);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.TRIGGER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinedElement() {
		UMLRTTrigger redefinedTrigger = getRedefinedTrigger();
		if (redefinedTrigger != null) {
			return redefinedTrigger;
		}
		return super.getRedefinedElement();
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
		return super.getRedefinitionContext();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTProtocolMessage getProtocolMessage() {
		UMLRTProtocolMessage result = null;

		Event event = toUML().getEvent();
		if (event instanceof CallEvent) {
			CallEvent callEvent = (CallEvent) event;
			result = (callEvent.getOperation() != null)
					? UMLRTProtocolMessage.getInstance(callEvent.getOperation())
					: null;
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
	public void setProtocolMessage(UMLRTProtocolMessage newProtocolMessage) {
		toUML().setEvent((newProtocolMessage == null) ? null : newProtocolMessage.toReceiveEvent());
	}

	protected static class PortList extends DelegatingEcoreEList<UMLRTPort> {

		private static final long serialVersionUID = 1L;

		protected int featureID;
		protected EList<UMLRTPort> delegateList;
		protected EList<Port> umlPorts;

		public PortList(InternalEObject owner, int featureID, EList<Port> umlPorts) {
			super(owner);

			this.featureID = featureID;
			this.umlPorts = umlPorts;

			this.delegateList = umlPorts.stream()
					.map(UMLRTPort::getInstance)
					.filter(Objects::nonNull)
					.collect(() -> new UniqueEList.FastCompare<>(umlPorts.size()), EList::add, EList::addAll);
		}

		@Override
		public int getFeatureID() {
			return featureID;
		}

		@Override
		protected List<UMLRTPort> delegateList() {
			return delegateList;
		}

		@Override
		protected void delegateAdd(int index, UMLRTPort port) {
			int delegateIndex = delegateList.indexOf(port);

			if (delegateIndex >= 0) {
				if (index != delegateIndex) {
					delegateList.move(index, port);
				}
			} else if (index < delegateList.size()) {
				delegateList.add(index, port);
			} else {
				delegateList.add(port);
			}
		}

		@Override
		protected void didAdd(int index, UMLRTPort newPort) {
			super.didAdd(index, newPort);

			if (index > umlPorts.size()) {
				throw new IllegalStateException("port list is stale"); //$NON-NLS-1$
			}

			umlPorts.add(index, newPort.toUML());
		}

		@Override
		protected void didRemove(int index, UMLRTPort oldPort) {
			super.didRemove(index, oldPort);

			if ((index >= umlPorts.size()) || (umlPorts.get(index) != oldPort.toUML())) {
				throw new IllegalStateException("port list is stale"); //$NON-NLS-1$
			}

			umlPorts.remove(index);
		}

		@Override
		protected void didClear(int size, Object[] oldObjects) {
			// Cannot call super because it goes in the wrong direction (front to back)
			if (size != umlPorts.size()) {
				throw new IllegalStateException("port list is stale"); //$NON-NLS-1$
			}

			umlPorts.clear();
		}

		@Override
		protected void didSet(int index, UMLRTPort newPort, UMLRTPort oldPort) {
			super.didSet(index, newPort, oldPort);

			if ((index >= umlPorts.size()) || (umlPorts.get(index) != oldPort.toUML())) {
				throw new IllegalStateException("port list is stale"); //$NON-NLS-1$
			}

			umlPorts.set(index, newPort.toUML());
		}

		@Override
		protected void didMove(int index, UMLRTPort movedPort, int oldIndex) {
			super.didMove(index, movedPort, oldIndex);

			if ((index >= umlPorts.size()) || (oldIndex >= umlPorts.size())
					|| (umlPorts.get(oldIndex) != movedPort.toUML())) {

				throw new IllegalStateException("port list is stale"); //$NON-NLS-1$
			}

			umlPorts.move(index, oldIndex);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTPort> getPorts() {
		List<UMLRTPort> result;

		CacheAdapter cache = getCacheAdapter();
		if (cache == null) {
			result = new PortList(this, UMLRTUMLRTPackage.TRIGGER__PORT, toUML().getPorts());
		} else {
			@SuppressWarnings("unchecked")
			List<UMLRTPort> ports = (List<UMLRTPort>) cache.get(
					this, UMLRTUMLRTPackage.Literals.TRIGGER__PORT);
			if (ports == null) {
				ports = new PortList(this, UMLRTUMLRTPackage.TRIGGER__PORT, toUML().getPorts());
				cache.put(this, UMLRTUMLRTPackage.Literals.TRIGGER__PORT, ports);
			}
			result = ports;
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
	public UMLRTPort getPort(String name) {
		return getPort(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTPort getPort(String name, boolean ignoreCase) {
		portLoop: for (UMLRTPort port : getPorts()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(port.getName()) : name.equals(port.getName()))) {
				continue portLoop;
			}
			return port;
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
	public UMLRTPort getPort() throws IllegalStateException {

		if (hasMultiplePorts()) {
			throw new IllegalStateException("trigger has two or more ports"); //$NON-NLS-1$
		} else {
			List<UMLRTPort> ports = getPorts();
			return ports.isEmpty() ? null : ports.get(0);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setPort(UMLRTPort port) {
		EList<UMLRTPort> ports = (EList<UMLRTPort>) getPorts();

		if (port == null) {
			if (!ports.isEmpty()) {
				ports.clear();
			}
		} else if ((ports.size() != 1) || (ports.get(0) != port)) {
			ECollections.setEList(ports, Collections.singletonList(port));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean hasMultiplePorts() {
		return getPorts().size() > 1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Stream<? extends UMLRTTrigger> allRedefinitions() {
		Predicate<UMLRTNamedElement> excluded = UMLRTNamedElement::isExcluded;
		UMLRTNamedElement context = getRedefinitionContext();
		return (context == null) ? Stream.of(this) : context.allRedefinitions()
				.map(c -> c.getRedefinitionOf(this))
				.filter(Objects::nonNull)
				.filter(excluded.negate());
	}

	Optional<Transition> transition() {
		return getTransition(toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTGuard getGuard() {
		return getGuard(UMLRTGuard::getInstance);
	}

	protected <T> T getGuard(Function<? super Constraint, T> transformation) {
		UMLRTTransition transition = getTransition();

		// Excluded triggers have no guard by definition
		return ((transition == null) || ((InternalUMLRTElement) toUML()).rtIsExcluded())
				? null
				: UMLRTExtensionUtil.<Constraint> getUMLRTContents(transition.toUML(), UMLPackage.Literals.NAMESPACE__OWNED_RULE).stream()
						.filter(this::isConstrainedBy)
						.filter(guard -> UMLUtil.getStereotypeApplication(guard, RTGuard.class) != null)
						.map(transformation)
						.filter(Objects::nonNull)
						.findAny().orElse(null);
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
				Constraint uml = oldGuard.toUML();

				uml.getConstrainedElements().remove(toUML());

				// A constraint in the role of a trigger guard cannot
				// be just an owned rule, so remove it
				uml.setContext(null);
			}
			if (newGuard != null) {
				Constraint uml = newGuard.toUML();

				uml.setContext(getTransition().toUML());

				// This is required for trigger guards
				RTGuard stereo = UMLUtil.getStereotypeApplication(uml, RTGuard.class);
				if (stereo == null) {
					StereotypeApplicationHelper.getInstance(uml).applyStereotype(uml,
							UMLRTStateMachinesPackage.Literals.RT_GUARD);
				}

				UMLRTTransition transition = newGuard.getTransition();
				if (transition != null) {
					// It is becoming a trigger guard
					transition.setGuard(null);
				}
				UMLRTTrigger trigger = newGuard.getTrigger();
				if (trigger != null) {
					// It's now my guard
					uml.getConstrainedElements().remove(trigger.toUML());
				}

				uml.getConstrainedElements().add(0, toUML());
			}
		}
	}

	protected boolean isConstrainedBy(Constraint constraint) {
		List<Element> constrained = constraint.getConstrainedElements();
		InternalUMLRTElement trigger = (InternalUMLRTElement) toUML();
		// Don't use the façade here because that would result in
		// stack overflow in oscillation in façade inheritance
		return constrained.contains(trigger)
				|| (trigger.rtIsVirtual() && constrained.contains(trigger.rtGetRootDefinition()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTTrigger getRedefinedTrigger() {
		UMLRTTrigger result = null;

		if (toUML() instanceof InternalUMLRTElement) {
			// These are not redefinable in UML, so this is the only valid case
			Trigger superTrigger = (Trigger) ((InternalUMLRTElement) toUML()).rtGetRedefinedElement();
			result = UMLRTTrigger.getInstance(superTrigger);
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
	public boolean isReceiveAnyMessage() {
		return toUML().getEvent() instanceof AnyReceiveEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setReceiveAnyMessage(boolean newReceiveAnyMessage) {
		if (newReceiveAnyMessage == isReceiveAnyMessage()) {
			return;
		}

		UMLRTProtocol protocol = null;

		// The port definitively indicates the protocol
		UMLRTPort port = getPort();
		if (port != null) {
			protocol = port.getType();
		} else {
			UMLRTProtocolMessage message = getProtocolMessage();
			if (message != null) {
				protocol = message.getProtocol();
			}
		}

		if (protocol == null) {
			throw new IllegalStateException("trigger has neither port nor message from which to infer the protocol"); //$NON-NLS-1$
		}

		if (newReceiveAnyMessage) {
			toUML().setEvent(protocol.getAnyReceiveEvent());
		} else {
			// Take the first available in or inout message. Hokey, yes, but caveat emptor
			toUML().setEvent(protocol.getMessages().stream()
					.filter(m -> m.getKind() != RTMessageKind.OUT)
					.findFirst().orElseThrow(() -> new IllegalStateException("protocol has only outbound messages")) //$NON-NLS-1$
					.toReceiveEvent());
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTTransition getTransition() {
		return transition().map(UMLRTTransition::getInstance).orElse(null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Trigger toUML() {
		return (Trigger) super.toUML();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTGuard createGuard(String language, String body) {
		return transition().map(t -> t.createOwnedRule(null))
				.map(guard -> {
					applyStereotype(guard, RTGuard.class);
					OpaqueExpression expr = (OpaqueExpression) guard.createSpecification(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION);
					expr.getBodies().add(body);
					if (language != null) {
						expr.getLanguages().add(language);
					}
					guard.getConstrainedElements().add(toUML());
					return guard;
				}).map(UMLRTGuard::getInstance)
				.orElse(null);
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
		case UMLRTUMLRTPackage.TRIGGER__PROTOCOL_MESSAGE:
			return getProtocolMessage();
		case UMLRTUMLRTPackage.TRIGGER__PORT:
			return getPorts();
		case UMLRTUMLRTPackage.TRIGGER__GUARD:
			return getGuard();
		case UMLRTUMLRTPackage.TRIGGER__REDEFINED_TRIGGER:
			return getRedefinedTrigger();
		case UMLRTUMLRTPackage.TRIGGER__RECEIVE_ANY_MESSAGE:
			return isReceiveAnyMessage();
		case UMLRTUMLRTPackage.TRIGGER__TRANSITION:
			return getTransition();
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
		case UMLRTUMLRTPackage.TRIGGER__PROTOCOL_MESSAGE:
			setProtocolMessage((UMLRTProtocolMessage) newValue);
			return;
		case UMLRTUMLRTPackage.TRIGGER__PORT:
			getPorts().clear();
			getPorts().addAll((Collection<? extends UMLRTPort>) newValue);
			return;
		case UMLRTUMLRTPackage.TRIGGER__GUARD:
			setGuard((UMLRTGuard) newValue);
			return;
		case UMLRTUMLRTPackage.TRIGGER__RECEIVE_ANY_MESSAGE:
			setReceiveAnyMessage((Boolean) newValue);
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
		case UMLRTUMLRTPackage.TRIGGER__PROTOCOL_MESSAGE:
			setProtocolMessage((UMLRTProtocolMessage) null);
			return;
		case UMLRTUMLRTPackage.TRIGGER__PORT:
			getPorts().clear();
			return;
		case UMLRTUMLRTPackage.TRIGGER__GUARD:
			setGuard((UMLRTGuard) null);
			return;
		case UMLRTUMLRTPackage.TRIGGER__RECEIVE_ANY_MESSAGE:
			setReceiveAnyMessage(RECEIVE_ANY_MESSAGE_EDEFAULT);
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
		case UMLRTUMLRTPackage.TRIGGER__REDEFINED_ELEMENT:
			return isSetRedefinedElement();
		case UMLRTUMLRTPackage.TRIGGER__REDEFINITION_CONTEXT:
			return isSetRedefinitionContext();
		case UMLRTUMLRTPackage.TRIGGER__PROTOCOL_MESSAGE:
			return getProtocolMessage() != null;
		case UMLRTUMLRTPackage.TRIGGER__PORT:
			return !getPorts().isEmpty();
		case UMLRTUMLRTPackage.TRIGGER__GUARD:
			return getGuard() != null;
		case UMLRTUMLRTPackage.TRIGGER__REDEFINED_TRIGGER:
			return getRedefinedTrigger() != null;
		case UMLRTUMLRTPackage.TRIGGER__RECEIVE_ANY_MESSAGE:
			return isReceiveAnyMessage() != RECEIVE_ANY_MESSAGE_EDEFAULT;
		case UMLRTUMLRTPackage.TRIGGER__TRANSITION:
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
	public boolean isSetRedefinedElement() {
		return super.isSetRedefinedElement()
				|| eIsSet(UMLRTUMLRTPackage.TRIGGER__REDEFINED_TRIGGER);
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
				|| eIsSet(UMLRTUMLRTPackage.TRIGGER__TRANSITION);
	}

	@Override
	public UMLRTInheritanceKind getInheritanceKind() {
		UMLRTInheritanceKind result = super.getInheritanceKind();

		if (result == UMLRTInheritanceKind.INHERITED) {
			// If I have a redefined guard, then I am implicitly redefined.
			// Don't use the façade here because that would result in
			// stack overflow in oscillation in façade inheritance
			Constraint guard = getGuard(Function.identity());

			if ((guard != null) && !UMLRTExtensionUtil.isVirtualElement(guard)) {
				result = UMLRTInheritanceKind.REDEFINED;
			}
		}

		return result;
	}
} // UMLRTTriggerImpl
