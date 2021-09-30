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

import static org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.FacadeType.NO_STEREOTYPE;
import static org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl.isRTMessageSet;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationWrapper;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageSet;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.CachedReference;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Protocol Message</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolMessageImpl#getRedefinedElement <em>Redefined Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolMessageImpl#getRedefinitionContext <em>Redefinition Context</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolMessageImpl#getKind <em>Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolMessageImpl#getRedefinedMessage <em>Redefined Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolMessageImpl#getParameters <em>Parameter</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolMessageImpl#isConjugate <em>Is Conjugate</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolMessageImpl#getConjugate <em>Conjugate</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolMessageImpl#getProtocol <em>Protocol</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTProtocolMessageImpl extends UMLRTNamedElementImpl implements UMLRTProtocolMessage {
	static final FacadeType<Operation, EObject, UMLRTProtocolMessageImpl> TYPE = new FacadeType<>(
			UMLRTProtocolMessageImpl.class,
			UMLPackage.Literals.OPERATION,
			NO_STEREOTYPE,
			UMLRTProtocolMessageImpl::getInstance,
			message -> (UMLRTProtocolMessageImpl) message.getRedefinedMessage(),
			UMLRTProtocolMessageImpl::maybeCreate);

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final RTMessageKind KIND_EDEFAULT = RTMessageKind.IN;

	/**
	 * The default value of the '{@link #isConjugate() <em>Is Conjugate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isConjugate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_CONJUGATE_EDEFAULT = false;

	protected static class MessageAdapter<F extends UMLRTProtocolMessageImpl> extends NamedElementAdapter<F> {
		MessageAdapter(F facade) {
			super(facade);
		}

		@Override
		protected void handleReference(Notification msg) {
			if (msg.getFeature() == UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER) {
				if (get().eNotificationRequired()) {
					UMLRTProtocolMessageImpl message = get();

					// We don't have façades for parameters
					message.eNotify(new NotificationWrapper(message, msg) {
						@Override
						public Object getFeature() {
							return UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE__PARAMETER;
						}

					});
				}
			} else {
				super.handleReference(msg);
			}
		}
	}

	private final boolean isConjugate;
	private final Supplier<UMLRTProtocolMessage> conjugate;
	private RTMessageKind kind;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	protected UMLRTProtocolMessageImpl() {
		super();

		this.isConjugate = false;
		this.conjugate = new CachedReference<>(() -> new UMLRTProtocolMessageImpl(this));
	}

	private UMLRTProtocolMessageImpl(UMLRTProtocolMessageImpl base) {
		super();

		this.isConjugate = true;
		this.conjugate = () -> base; // The base has a hard ref from the model anyways
		init(base.toUML(), base.toRT());
	}

	@Override
	<F extends FacadeObjectImpl> F init(Element element, EObject stereotype) {
		F result = super.init(element, stereotype);

		// Initialize my kind
		getKind();

		return result;
	}

	/**
	 * Obtains the protocol message façade for the message represented by the given
	 * UML operation.
	 *
	 * @param operation
	 *            a UML operation representing a protocol message
	 * @return the base UML-RT protocol-message façade, or {@code null} if the operation
	 *         is not actually a protocol message
	 */
	public static UMLRTProtocolMessageImpl getInstance(Operation operation) {
		return getFacade(operation, TYPE);
	}

	static UMLRTProtocolMessage getInstance(Operation operation, boolean conjugate) {
		UMLRTProtocolMessage result = getInstance(operation);

		return ((result == null) || !conjugate)
				? result
				: result.getConjugate();
	}

	private static UMLRTProtocolMessageImpl maybeCreate(Operation operation) {
		return ((operation != null) && isProtocolMessage(operation))
				? new UMLRTProtocolMessageImpl()
				: null;
	}

	static boolean isProtocolMessage(Operation operation) {
		return (operation.getNamespace() instanceof Interface)
				&& isRTMessageSet((Interface) operation.getNamespace());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE;
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTProtocolMessageImpl> createFacadeAdapter() {
		return new MessageAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinedElement() {
		UMLRTProtocolMessage redefinedMessage = getRedefinedMessage();
		if (redefinedMessage != null) {
			return redefinedMessage;
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
		UMLRTProtocol protocol = getProtocol();
		if (protocol != null) {
			return protocol;
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
	public RTMessageKind getKind() {
		if (kind == null) {
			kind = forConjugation(getMessageSet().map(RTMessageSet::getRtMsgKind).orElse(null));
		}

		return kind;
	}

	void kindChanged() {
		RTMessageKind oldKind = kind;
		kind = null;
		RTMessageKind newKind = getKind();

		if ((oldKind != newKind) && eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLRTUMLRTPackage.Literals.PROTOCOL_MESSAGE__KIND, oldKind, newKind));
		}
	}

	Optional<RTMessageSet> getMessageSet() {
		return Optional.ofNullable(toUML().getNamespace())
				.filter(Interface.class::isInstance).map(Interface.class::cast)
				.map(UMLRTProtocolImpl::getRTMessageSet);
	}

	/**
	 * Obtains the 'real' conjugation to encode in the UML model according
	 * to my {@linkplain #isConjugate() conjugation}.
	 *
	 * @param kind
	 *            a message direction kind
	 * @return the UML model's value of the {@code kind}
	 *
	 * @see #isConjugate()
	 */
	private RTMessageKind forConjugation(RTMessageKind kind) {
		if ((kind != null) && isConjugate()) {
			switch (kind) {
			case IN:
				kind = RTMessageKind.OUT;
				break;
			case OUT:
				kind = RTMessageKind.IN;
				break;
			default:
				// INOUT is its own inverse
				break;
			}
		}
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setKind(RTMessageKind newKind) {
		if (getKind() != Objects.requireNonNull(newKind, "newKind")) { //$NON-NLS-1$
			((UMLRTProtocolImpl) getProtocol()).requireMessageSet(forConjugation(newKind))
					.getOwnedOperations().add(toUML());
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTProtocolMessage getRedefinedMessage() {
		UMLRTProtocolMessage result;

		if (toUML() instanceof InternalUMLRTElement) {
			Operation superOperation = (Operation) ((InternalUMLRTElement) toUML()).rtGetRedefinedElement();
			result = UMLRTProtocolMessageImpl.getInstance(superOperation, isConjugate());
		} else {
			UMLRTNamedElement redef = super.getRedefinedElement();
			result = (redef instanceof UMLRTProtocolMessage) ? (UMLRTProtocolMessage) redef : null;
			if ((result != null) && isConjugate()) {
				result = result.getConjugate();
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
	public List<Parameter> getParameters() {
		return toUML().getOwnedParameters();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Parameter getParameter(String name, Type type) {
		return getParameter(name, type, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Parameter getParameter(String name, Type type, boolean ignoreCase) {
		parameterLoop: for (Parameter parameter : getParameters()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(parameter.getName()) : name.equals(parameter.getName()))) {
				continue parameterLoop;
			}
			if (type != null && !type.equals(parameter.getType())) {
				continue parameterLoop;
			}
			return parameter;
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
	public boolean isConjugate() {
		return isConjugate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTProtocolMessage getConjugate() {
		return conjugate.get();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTProtocol getProtocol() {
		Package protocolContainer = toUML().getNearestPackage();

		return UMLRTProtocol.getInstance((Collaboration) protocolContainer.getOwnedType(
				protocolContainer.getName(), false, UMLPackage.Literals.COLLABORATION, false),
				isConjugate());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Operation toUML() {
		return (Operation) super.toUML();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public CallEvent toReceiveEvent() {
		return toUML().getNearestPackage().getPackagedElements().stream()
				.filter(CallEvent.class::isInstance).map(CallEvent.class::cast)
				.filter(ev -> ev.getOperation() == toUML())
				.findAny()
				.orElse(null);
	}

	UMLRTProtocolMessageImpl initReceiveEvent() {
		CallEvent event = (CallEvent) toUML().getNearestPackage().createPackagedElement(null, UMLPackage.Literals.CALL_EVENT);
		event.setOperation(toUML());
		return this;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Parameter createParameter(String name, Type type) {
		return toUML().createOwnedParameter(name, type);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Stream<? extends UMLRTProtocolMessage> allRedefinitions() {
		Predicate<UMLRTProtocolMessage> excluded = UMLRTProtocolMessage::isExcluded;
		UMLRTProtocol protocol = getProtocol();
		return (protocol == null) ? Stream.of(this) : protocol.getHierarchy()
				.map(p -> p.getRedefinitionOf(this))
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
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__KIND:
			return getKind();
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__REDEFINED_MESSAGE:
			return getRedefinedMessage();
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__PARAMETER:
			return getParameters();
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__IS_CONJUGATE:
			return isConjugate();
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__CONJUGATE:
			return getConjugate();
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__PROTOCOL:
			return getProtocol();
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__KIND:
			setKind((RTMessageKind) newValue);
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
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__KIND:
			setKind(KIND_EDEFAULT);
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
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__REDEFINED_ELEMENT:
			return isSetRedefinedElement();
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__REDEFINITION_CONTEXT:
			return isSetRedefinitionContext();
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__KIND:
			return getKind() != KIND_EDEFAULT;
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__REDEFINED_MESSAGE:
			return getRedefinedMessage() != null;
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__PARAMETER:
			return !getParameters().isEmpty();
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__IS_CONJUGATE:
			return isConjugate() != IS_CONJUGATE_EDEFAULT;
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__CONJUGATE:
			return getConjugate() != null;
		case UMLRTUMLRTPackage.PROTOCOL_MESSAGE__PROTOCOL:
			return getProtocol() != null;
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
				|| eIsSet(UMLRTUMLRTPackage.PROTOCOL_MESSAGE__REDEFINED_MESSAGE);
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
				|| eIsSet(UMLRTUMLRTPackage.PROTOCOL_MESSAGE__PROTOCOL);
	}

} // UMLRTProtocolMessageImpl
