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

import static org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTReplicatedElementImpl.ReplicationAdapter.asIntegerBound;
import static org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTReplicatedElementImpl.ReplicationAdapter.valueOf;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTConnector;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl#getRedefinedElement <em>Redefined Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl#getRedefinitionContext <em>Redefinition Context</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl#getRedefinedConnector <em>Redefined Connector</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl#getCapsule <em>Capsule</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl#getSource <em>Source</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl#getSourcePartWithPort <em>Source Part With Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl#getSourceReplicationFactor <em>Source Replication Factor</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl#getTarget <em>Target</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl#getTargetPartWithPort <em>Target Part With Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl#getTargetReplicationFactor <em>Target Replication Factor</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTConnectorImpl extends UMLRTNamedElementImpl implements UMLRTConnector {
	/**
	 * The default value of the '{@link #getSourceReplicationFactor() <em>Source Replication Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSourceReplicationFactor()
	 * @generated
	 * @ordered
	 */
	protected static final int SOURCE_REPLICATION_FACTOR_EDEFAULT = 0;
	/**
	 * The default value of the '{@link #getTargetReplicationFactor() <em>Target Replication Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getTargetReplicationFactor()
	 * @generated
	 * @ordered
	 */
	protected static final int TARGET_REPLICATION_FACTOR_EDEFAULT = 0;

	static final FacadeType<Connector, RTConnector, UMLRTConnectorImpl> TYPE = new FacadeType<>(
			UMLRTConnectorImpl.class,
			UMLPackage.Literals.CONNECTOR,
			UMLRealTimePackage.Literals.RT_CONNECTOR,
			UMLRTConnectorImpl::getInstance,
			connector -> (UMLRTConnectorImpl) connector.getRedefinedConnector(),
			UMLRTConnectorImpl::new);

	protected static class ConnectorAdapter<F extends UMLRTConnectorImpl> extends NamedElementAdapter<F> {
		ConnectorAdapter(F facade) {
			super(facade);
		}

		@Override
		public void setTarget(Notifier newTarget) {
			super.setTarget(newTarget);

			if (newTarget instanceof Connector) {
				((Connector) newTarget).getEnds().forEach(this::adaptAdditional);
			} else if (newTarget instanceof MultiplicityElement) {
				MultiplicityElement mult = (MultiplicityElement) newTarget;
				if (mult.getUpperValue() != null) {
					adaptAdditional(mult.getUpperValue());
				}
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (oldTarget instanceof Connector) {
				// Remove additional adapters on unload
				unadaptAdditional();
			}

			super.unsetTarget(oldTarget);
		}

		@Override
		protected void handleObjectAdded(Notification msg, int position, EObject object) {
			if (msg.getFeature() == UMLPackage.Literals.CONNECTOR__END) {
				adaptAdditional(object);
			}

			super.handleObjectAdded(msg, position, object);
		}

		@Override
		protected void handleObjectRemoved(Notification msg, int position, EObject object) {
			super.handleObjectRemoved(msg, position, object);

			if (msg.getFeature() == UMLPackage.Literals.CONNECTOR__END) {
				unadaptAdditional(object);
			}
		}

		@Override
		protected void handleObjectReplaced(Notification msg, int position, EObject oldObject, EObject newObject) {
			if (msg.getFeature() == UMLPackage.Literals.CONNECTOR__END) {
				unadaptAdditional(oldObject);
				adaptAdditional(newObject);
			} else if (msg.getFeature() == UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE) {
				if (oldObject != null) {
					unadaptAdditional(oldObject);
				}
				if (newObject != null) {
					adaptAdditional(newObject);
				}

				boolean isSource = msg.getNotifier() == get().umlEnd(0);
				handleUpperBoundChanged(isSource, asIntegerBound(valueOf((ValueSpecification) oldObject)),
						asIntegerBound(valueOf((ValueSpecification) newObject)));
			} else {
				super.handleObjectReplaced(msg, position, oldObject, newObject);
			}
		}

		@Override
		protected void handleObjectReplaced(Notification msg, int position, FacadeObject oldObject, FacadeObject newObject) {
			if (msg.getFeature() == UMLPackage.Literals.CONNECTOR_END__ROLE) {
				if (get().eNotificationRequired()) {
					boolean isSource = msg.getNotifier() == get().umlEnd(0);
					EStructuralFeature feature = isSource
							? UMLRTUMLRTPackage.Literals.CONNECTOR__SOURCE
							: UMLRTUMLRTPackage.Literals.CONNECTOR__TARGET;
					get().eNotify(new ENotificationImpl(get(), msg.getEventType(), feature, oldObject, newObject));
				}
			} else if (msg.getFeature() == UMLPackage.Literals.CONNECTOR_END__PART_WITH_PORT) {
				if (get().eNotificationRequired()) {
					boolean isSource = msg.getNotifier() == get().umlEnd(0);
					EStructuralFeature feature = isSource
							? UMLRTUMLRTPackage.Literals.CONNECTOR__SOURCE_PART_WITH_PORT
							: UMLRTUMLRTPackage.Literals.CONNECTOR__TARGET_PART_WITH_PORT;
					get().eNotify(new ENotificationImpl(get(), msg.getEventType(), feature, oldObject, newObject));
				}
			} else {
				super.handleObjectReplaced(msg, position, oldObject, newObject);
			}
		}

		@Override
		protected void handleValueReplaced(Notification msg, int position, Object oldValue, Object newValue) {
			if ((msg.getFeature() == UMLPackage.Literals.LITERAL_INTEGER__VALUE)
					|| (msg.getFeature() == UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE)
					|| (msg.getFeature() == UMLPackage.Literals.LITERAL_STRING__VALUE)
					|| ((msg.getFeature() == UMLPackage.Literals.OPAQUE_EXPRESSION__BODY)
							&& (position == 0))) {

				ValueSpecification bound = (ValueSpecification) msg.getNotifier();
				MultiplicityElement mult = (MultiplicityElement) bound.getOwner();
				boolean isSource = mult == get().umlEnd(0);

				handleUpperBoundChanged(isSource, asIntegerBound(oldValue), asIntegerBound(newValue));
			} else {
				super.handleValueReplaced(msg, position, oldValue, newValue);
			}
		}

		@Override
		protected void handleValueAdded(Notification msg, int position, Object value) {
			if ((msg.getFeature() == UMLPackage.Literals.OPAQUE_EXPRESSION__BODY)
					&& (position == 0)) {

				ValueSpecification bound = (ValueSpecification) msg.getNotifier();
				MultiplicityElement mult = (MultiplicityElement) bound.getOwner();
				boolean isSource = mult == get().umlEnd(0);

				handleUpperBoundChanged(isSource, 1, asIntegerBound(value));
			} else {
				super.handleValueAdded(msg, position, value);
			}
		}

		@Override
		protected void handleValueRemoved(Notification msg, int position, Object value) {
			if ((msg.getFeature() == UMLPackage.Literals.OPAQUE_EXPRESSION__BODY)
					&& ((OpaqueExpression) msg.getNotifier()).getBodies().isEmpty()) {

				ValueSpecification bound = (ValueSpecification) msg.getNotifier();
				MultiplicityElement mult = (MultiplicityElement) bound.getOwner();
				boolean isSource = mult == get().umlEnd(0);

				handleUpperBoundChanged(isSource, asIntegerBound(value), mult.getUpper());
			} else {
				super.handleValueRemoved(msg, position, value);
			}
		}

		protected void handleUpperBoundChanged(boolean isSource, int oldValue, int newValue) {
			if (get().eNotificationRequired()) {
				EStructuralFeature feature = isSource
						? UMLRTUMLRTPackage.Literals.CONNECTOR__SOURCE_REPLICATION_FACTOR
						: UMLRTUMLRTPackage.Literals.CONNECTOR__TARGET_REPLICATION_FACTOR;

				get().eNotify(new ENotificationImpl(get(), Notification.SET, feature,
						oldValue, newValue));
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTConnectorImpl() {
		super();
	}

	/**
	 * Obtains the canonical instance of the connector façade for a connector.
	 *
	 * @param connector
	 *            a connector in the UML model
	 *
	 * @return its connector façade, or {@code null} if the connector is not a valid UML-RT connector
	 */
	public static UMLRTConnectorImpl getInstance(Connector connector) {
		return getFacade(connector, TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.CONNECTOR;
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTConnectorImpl> createFacadeAdapter() {
		return new ConnectorAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinedElement() {
		UMLRTConnector redefinedConnector = getRedefinedConnector();
		if (redefinedConnector != null) {
			return redefinedConnector;
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
		UMLRTCapsule capsule = getCapsule();
		if (capsule != null) {
			return capsule;
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
	public UMLRTConnector getRedefinedConnector() {
		UMLRTConnector result;

		if (toUML() instanceof InternalUMLRTElement) {
			Connector superConnector = (Connector) ((InternalUMLRTElement) toUML()).rtGetRedefinedElement();
			result = UMLRTConnector.getInstance(superConnector);
		} else {
			UMLRTNamedElement redef = super.getRedefinedElement();
			result = (redef instanceof UMLRTConnector) ? (UMLRTConnector) redef : null;
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
	public UMLRTCapsule getCapsule() {
		return UMLRTCapsule.getInstance((org.eclipse.uml2.uml.Class) toUML().getNamespace());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTPort getSource() {
		ConnectorEnd sourceEnd = umlEnd(0);
		return resolvePort(sourceEnd);
	}

	ConnectorEnd umlEnd(int index) {
		ConnectorEnd result = null;
		EList<ConnectorEnd> ends = toUML().getEnds();

		if (ends.size() > index) {
			result = ends.get(index);
		}

		return result;
	}

	private UMLRTPort resolvePort(ConnectorEnd end) {
		Port uml = (end == null)
				? null
				: (end.getRole() instanceof Port)
						? (Port) end.getRole()
						: null;
		UMLRTPort result = (uml == null) ? null : UMLRTPort.getInstance(uml);

		if ((result != null) && (result.getCapsule() != getCapsule())) {
			// Resolve the inherited port in my capsule
			UMLRTPort inherited = result;
			result = getCapsule().getPorts(true).stream()
					.filter(p -> p.redefines(inherited))
					.findAny()
					.orElse(result);
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
	public void setSource(UMLRTPort newSource) {
		ConnectorEnd sourceEnd = umlEnd(0);
		if (sourceEnd == null) {
			throw new IllegalStateException("no source end"); //$NON-NLS-1$
		}
		sourceEnd.setRole((newSource == null) ? null : newSource.toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTCapsulePart getSourcePartWithPort() {
		ConnectorEnd sourceEnd = umlEnd(0);
		return resolvePartWithPort(sourceEnd);
	}

	private UMLRTCapsulePart resolvePartWithPort(ConnectorEnd end) {
		UMLRTCapsulePart result = null;

		if ((end != null) && (end.getPartWithPort() != null)) {
			result = UMLRTCapsulePart.getInstance(end.getPartWithPort());

			if ((result != null) && (result.getCapsule() != getCapsule())) {
				// Resolve the inherited port in my capsule
				UMLRTCapsulePart inherited = result;
				result = getCapsule().getCapsuleParts(true).stream()
						.filter(p -> p.redefines(inherited))
						.findAny()
						.orElse(result);
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
	public void setSourcePartWithPort(UMLRTCapsulePart newSourcePartWithPort) {
		ConnectorEnd sourceEnd = umlEnd(0);
		if (sourceEnd == null) {
			throw new IllegalStateException("no source end"); //$NON-NLS-1$
		}
		sourceEnd.setPartWithPort((newSourcePartWithPort == null) ? null : newSourcePartWithPort.toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public int getSourceReplicationFactor() {
		ConnectorEnd sourceEnd = umlEnd(0);
		return UMLRTReplicatedElementImpl.getReplicationFactor(
				(sourceEnd == null) ? null : sourceEnd.getUpperValue());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setSourceReplicationFactor(int newSourceReplicationFactor) {
		ConnectorEnd sourceEnd = umlEnd(0);
		if (sourceEnd == null) {
			throw new IllegalStateException("no source end"); //$NON-NLS-1$
		}
		sourceEnd.setLower(newSourceReplicationFactor);
		sourceEnd.setUpper(newSourceReplicationFactor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTPort getTarget() {
		ConnectorEnd targetEnd = umlEnd(1);
		return resolvePort(targetEnd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setTarget(UMLRTPort newTarget) {
		ConnectorEnd targetEnd = umlEnd(1);
		if (targetEnd == null) {
			throw new IllegalStateException("no target end"); //$NON-NLS-1$
		}
		targetEnd.setRole((newTarget == null) ? null : newTarget.toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTCapsulePart getTargetPartWithPort() {
		ConnectorEnd targetEnd = umlEnd(1);
		return resolvePartWithPort(targetEnd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setTargetPartWithPort(UMLRTCapsulePart newTargetPartWithPort) {
		ConnectorEnd targetEnd = umlEnd(1);
		if (targetEnd == null) {
			throw new IllegalStateException("no target end"); //$NON-NLS-1$
		}
		targetEnd.setPartWithPort((newTargetPartWithPort == null) ? null : newTargetPartWithPort.toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public int getTargetReplicationFactor() {
		ConnectorEnd targetEnd = umlEnd(1);
		return UMLRTReplicatedElementImpl.getReplicationFactor(
				(targetEnd == null) ? null : targetEnd.getUpperValue());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setTargetReplicationFactor(int newTargetReplicationFactor) {
		ConnectorEnd targetEnd = umlEnd(1);
		if (targetEnd == null) {
			throw new IllegalStateException("no target end"); //$NON-NLS-1$
		}
		targetEnd.setLower(newTargetReplicationFactor);
		targetEnd.setUpper(newTargetReplicationFactor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Connector toUML() {
		return (Connector) super.toUML();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Stream<? extends UMLRTConnector> allRedefinitions() {
		Predicate<UMLRTConnector> excluded = UMLRTConnector::isExcluded;
		UMLRTCapsule capsule = getCapsule();
		return (capsule == null) ? Stream.of(this) : capsule.getHierarchy()
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
		case UMLRTUMLRTPackage.CONNECTOR__REDEFINED_CONNECTOR:
			return getRedefinedConnector();
		case UMLRTUMLRTPackage.CONNECTOR__CAPSULE:
			return getCapsule();
		case UMLRTUMLRTPackage.CONNECTOR__SOURCE:
			return getSource();
		case UMLRTUMLRTPackage.CONNECTOR__SOURCE_PART_WITH_PORT:
			return getSourcePartWithPort();
		case UMLRTUMLRTPackage.CONNECTOR__SOURCE_REPLICATION_FACTOR:
			return getSourceReplicationFactor();
		case UMLRTUMLRTPackage.CONNECTOR__TARGET:
			return getTarget();
		case UMLRTUMLRTPackage.CONNECTOR__TARGET_PART_WITH_PORT:
			return getTargetPartWithPort();
		case UMLRTUMLRTPackage.CONNECTOR__TARGET_REPLICATION_FACTOR:
			return getTargetReplicationFactor();
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
		case UMLRTUMLRTPackage.CONNECTOR__SOURCE:
			setSource((UMLRTPort) newValue);
			return;
		case UMLRTUMLRTPackage.CONNECTOR__SOURCE_PART_WITH_PORT:
			setSourcePartWithPort((UMLRTCapsulePart) newValue);
			return;
		case UMLRTUMLRTPackage.CONNECTOR__SOURCE_REPLICATION_FACTOR:
			setSourceReplicationFactor((Integer) newValue);
			return;
		case UMLRTUMLRTPackage.CONNECTOR__TARGET:
			setTarget((UMLRTPort) newValue);
			return;
		case UMLRTUMLRTPackage.CONNECTOR__TARGET_PART_WITH_PORT:
			setTargetPartWithPort((UMLRTCapsulePart) newValue);
			return;
		case UMLRTUMLRTPackage.CONNECTOR__TARGET_REPLICATION_FACTOR:
			setTargetReplicationFactor((Integer) newValue);
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
		case UMLRTUMLRTPackage.CONNECTOR__SOURCE:
			setSource((UMLRTPort) null);
			return;
		case UMLRTUMLRTPackage.CONNECTOR__SOURCE_PART_WITH_PORT:
			setSourcePartWithPort((UMLRTCapsulePart) null);
			return;
		case UMLRTUMLRTPackage.CONNECTOR__SOURCE_REPLICATION_FACTOR:
			setSourceReplicationFactor(SOURCE_REPLICATION_FACTOR_EDEFAULT);
			return;
		case UMLRTUMLRTPackage.CONNECTOR__TARGET:
			setTarget((UMLRTPort) null);
			return;
		case UMLRTUMLRTPackage.CONNECTOR__TARGET_PART_WITH_PORT:
			setTargetPartWithPort((UMLRTCapsulePart) null);
			return;
		case UMLRTUMLRTPackage.CONNECTOR__TARGET_REPLICATION_FACTOR:
			setTargetReplicationFactor(TARGET_REPLICATION_FACTOR_EDEFAULT);
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
		case UMLRTUMLRTPackage.CONNECTOR__REDEFINED_ELEMENT:
			return isSetRedefinedElement();
		case UMLRTUMLRTPackage.CONNECTOR__REDEFINITION_CONTEXT:
			return isSetRedefinitionContext();
		case UMLRTUMLRTPackage.CONNECTOR__REDEFINED_CONNECTOR:
			return getRedefinedConnector() != null;
		case UMLRTUMLRTPackage.CONNECTOR__CAPSULE:
			return getCapsule() != null;
		case UMLRTUMLRTPackage.CONNECTOR__SOURCE:
			return getSource() != null;
		case UMLRTUMLRTPackage.CONNECTOR__SOURCE_PART_WITH_PORT:
			return getSourcePartWithPort() != null;
		case UMLRTUMLRTPackage.CONNECTOR__SOURCE_REPLICATION_FACTOR:
			return getSourceReplicationFactor() != SOURCE_REPLICATION_FACTOR_EDEFAULT;
		case UMLRTUMLRTPackage.CONNECTOR__TARGET:
			return getTarget() != null;
		case UMLRTUMLRTPackage.CONNECTOR__TARGET_PART_WITH_PORT:
			return getTargetPartWithPort() != null;
		case UMLRTUMLRTPackage.CONNECTOR__TARGET_REPLICATION_FACTOR:
			return getTargetReplicationFactor() != TARGET_REPLICATION_FACTOR_EDEFAULT;
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
				|| eIsSet(UMLRTUMLRTPackage.CONNECTOR__REDEFINED_CONNECTOR);
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
				|| eIsSet(UMLRTUMLRTPackage.CONNECTOR__CAPSULE);
	}

} // UMLRTConnectorImpl
