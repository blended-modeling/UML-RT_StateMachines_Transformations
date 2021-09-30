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

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Port</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#getRedefinedElement <em>Redefined Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#getConnectors <em>Connector</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#getRedefinitionContext <em>Redefinition Context</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#getKind <em>Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#getRedefinedPort <em>Redefined Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#getPartsWithPort <em>Parts With Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#isService <em>Service</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#isBehavior <em>Behavior</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#isConjugated <em>Conjugated</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#isWired <em>Wired</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#isPublish <em>Publish</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#isNotification <em>Notification</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#getRegistration <em>Registration</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#getRegistrationOverride <em>Registration Override</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#isConnected <em>Is Connected</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#isConnectedInside <em>Is Connected Inside</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#isConnectedOutside <em>Is Connected Outside</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#getInsideConnectors <em>Inside Connector</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#getOutsideConnectors <em>Outside Connector</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPortImpl#getCapsule <em>Capsule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTPortImpl extends UMLRTReplicatedElementImpl implements UMLRTPort {
	static final FacadeType<Port, RTPort, UMLRTPortImpl> TYPE = new FacadeType<>(
			UMLRTPortImpl.class,
			UMLPackage.Literals.PORT,
			UMLRealTimePackage.Literals.RT_PORT,
			UMLRTPortImpl::getInstance,
			port -> (UMLRTPortImpl) port.getRedefinedPort(),
			UMLRTPortImpl::new);

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final UMLRTPortKind KIND_EDEFAULT = UMLRTPortKind.EXTERNAL_BEHAVIOR;

	/**
	 * The default value of the '{@link #isService() <em>Service</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isService()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SERVICE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isBehavior() <em>Behavior</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isBehavior()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BEHAVIOR_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isConjugated() <em>Conjugated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isConjugated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONJUGATED_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isWired() <em>Wired</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isWired()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WIRED_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isPublish() <em>Publish</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isPublish()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PUBLISH_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isNotification() <em>Notification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isNotification()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NOTIFICATION_EDEFAULT = false;

	/**
	 * The default value of the '{@link #getRegistration() <em>Registration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRegistration()
	 * @generated
	 * @ordered
	 */
	protected static final PortRegistrationType REGISTRATION_EDEFAULT = PortRegistrationType.AUTOMATIC;

	/**
	 * The default value of the '{@link #getRegistrationOverride() <em>Registration Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRegistrationOverride()
	 * @generated
	 * @ordered
	 */
	protected static final String REGISTRATION_OVERRIDE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #isConnected() <em>Is Connected</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isConnected()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_CONNECTED_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isConnectedInside() <em>Is Connected Inside</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isConnectedInside()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_CONNECTED_INSIDE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isConnectedOutside() <em>Is Connected Outside</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isConnectedOutside()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_CONNECTED_OUTSIDE_EDEFAULT = false;

	protected static class PortAdapter<F extends UMLRTPortImpl> extends ReplicationAdapter<F> {
		PortAdapter(F facade) {
			super(facade);
		}

		@Override
		public void setTarget(Notifier newTarget) {
			super.setTarget(newTarget);

			if (newTarget instanceof Port) {
				RTPort stereotype = UMLUtil.getStereotypeApplication((Port) newTarget, RTPort.class);
				if (stereotype != null) {
					addAdapter(stereotype);
				}
			}
		}

		@Override
		public void unsetTarget(Notifier newTarget) {
			if (newTarget instanceof Port) {
				RTPort stereotype = UMLUtil.getStereotypeApplication((Port) newTarget, RTPort.class);
				if (stereotype != null) {
					removeAdapter(stereotype);
				}
			}

			super.unsetTarget(newTarget);
		}

		@Override
		protected FacadeObject getFacade(EObject owner, EReference reference, EObject object) {
			FacadeObject result = super.getFacade(owner, reference, object);

			if ((result instanceof UMLRTProtocol) && get().isConjugated()) {
				result = ((UMLRTProtocol) result).getConjugate();
			}

			return result;
		}

		@Override
		protected void handleObjectReplaced(Notification msg, int position, FacadeObject oldObject, FacadeObject newObject) {
			if (msg.getFeature() == UMLPackage.Literals.TYPED_ELEMENT__TYPE) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__TYPE, oldObject, newObject));
				}
			} else {
				super.handleObjectReplaced(msg, position, oldObject, newObject);
			}
		}

		@Override
		protected void handleValueReplaced(Notification msg, int position, Object oldValue, Object newValue) {
			if (msg.getFeature() == UMLPackage.Literals.PORT__IS_CONJUGATED) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__CONJUGATED, oldValue, newValue));
					UMLRTProtocol type = get().getType();

					if (type != null) {
						// It is now the conjugate of what it was
						get().eNotify(new ENotificationImpl(get(), msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__TYPE,
								type.getConjugate(), type));
					}
				}
			} else if (msg.getFeature() == UMLPackage.Literals.PORT__IS_SERVICE) {
				if (get().eNotificationRequired()) {
					UMLRTPortImpl port = get();
					port.eNotify(new ENotificationImpl(port, msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__SERVICE, oldValue, newValue));

					// Infer what the kind previously was
					UMLRTPortKind kind = port.getKind();
					port.eNotify(new ENotificationImpl(port, msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__KIND,
							UMLRTPortKind.get((boolean) oldValue, port.isBehavior(), port.isWired(), port.isPublish()),
							kind));
				}
			} else if (msg.getFeature() == UMLPackage.Literals.PORT__IS_BEHAVIOR) {
				if (get().eNotificationRequired()) {
					UMLRTPortImpl port = get();
					port.eNotify(new ENotificationImpl(port, msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__BEHAVIOR, oldValue, newValue));

					// Infer what the kind previously was
					UMLRTPortKind kind = port.getKind();
					port.eNotify(new ENotificationImpl(port, msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__KIND,
							UMLRTPortKind.get(port.isService(), (boolean) oldValue, port.isWired(), port.isPublish()),
							kind));
				}
			} else if (msg.getFeature() == UMLRealTimePackage.Literals.RT_PORT__IS_WIRED) {
				if (get().eNotificationRequired()) {
					UMLRTPortImpl port = get();
					port.eNotify(new ENotificationImpl(port, msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__WIRED, oldValue, newValue));

					// Infer what the kind previously was
					UMLRTPortKind kind = port.getKind();
					port.eNotify(new ENotificationImpl(port, msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__KIND,
							UMLRTPortKind.get(port.isService(), port.isBehavior(), (boolean) oldValue, port.isPublish()),
							kind));
				}
			} else if (msg.getFeature() == UMLRealTimePackage.Literals.RT_PORT__IS_PUBLISH) {
				if (get().eNotificationRequired()) {
					UMLRTPortImpl port = get();
					port.eNotify(new ENotificationImpl(port, msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__PUBLISH, oldValue, newValue));

					// Infer what the kind previously was
					UMLRTPortKind kind = port.getKind();
					get().eNotify(new ENotificationImpl(get(), msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__KIND,
							UMLRTPortKind.get(port.isService(), port.isBehavior(), port.isWired(), (boolean) oldValue),
							kind));
				}
			} else if (msg.getFeature() == UMLRealTimePackage.Literals.RT_PORT__IS_NOTIFICATION) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__NOTIFICATION, oldValue, newValue));
				}
			} else if (msg.getFeature() == UMLRealTimePackage.Literals.RT_PORT__REGISTRATION) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__REGISTRATION, oldValue, newValue));
				}
			} else if (msg.getFeature() == UMLRealTimePackage.Literals.RT_PORT__REGISTRATION_OVERRIDE) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), msg.getEventType(), UMLRTUMLRTPackage.Literals.PORT__REGISTRATION_OVERRIDE, oldValue, newValue));
				}
			} else {
				super.handleValueReplaced(msg, position, oldValue, newValue);
			}
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTPortImpl() {
		super();
	}

	/**
	 * Obtains the canonical instance of the port façade for a port.
	 *
	 * @param port
	 *            a port in the UML model
	 *
	 * @return its port façade, or {@code null} if the port is not a valid UML-RT port
	 */
	public static UMLRTPortImpl getInstance(Port port) {
		return getFacade(port, TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.PORT;
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTPortImpl> createFacadeAdapter() {
		return new PortAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinedElement() {
		UMLRTPort redefinedPort = getRedefinedPort();
		if (redefinedPort != null) {
			return redefinedPort;
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
	public List<UMLRTConnector> getConnectors() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			List<UMLRTConnector> connectors = (List<UMLRTConnector>) cache.get(eResource, this, UMLRTUMLRTPackage.Literals.PORT__CONNECTOR);
			if (connectors == null) {
				cache.put(eResource, this, UMLRTUMLRTPackage.Literals.PORT__CONNECTOR, connectors = new DerivedUnionEObjectEList<>(UMLRTConnector.class, this, UMLRTUMLRTPackage.PORT__CONNECTOR, CONNECTOR_ESUBSETS));
			}
			return connectors;
		}
		return new DerivedUnionEObjectEList<>(UMLRTConnector.class, this, UMLRTUMLRTPackage.PORT__CONNECTOR, CONNECTOR_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getConnectors() <em>Connector</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getConnectors()
	 * @generated
	 * @ordered
	 */
	protected static final int[] CONNECTOR_ESUBSETS = new int[] { UMLRTUMLRTPackage.PORT__INSIDE_CONNECTOR, UMLRTUMLRTPackage.PORT__OUTSIDE_CONNECTOR };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTConnector getConnector(String name) {
		return getConnector(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTConnector getConnector(String name, boolean ignoreCase) {
		connectorLoop: for (UMLRTConnector connector : getConnectors()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(connector.getName()) : name.equals(connector.getName()))) {
				continue connectorLoop;
			}
			return connector;
		}
		return null;
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
	public boolean isConnected() {
		boolean result;
		CacheAdapter cache = getCacheAdapter();

		if (cache == null) {
			result = isConnectedInside() || isConnectedOutside();
		} else {
			Boolean cached = (Boolean) cache.get(this, UMLRTUMLRTPackage.Literals.PORT__IS_CONNECTED);
			if (cached == null) {
				cached = isConnectedInside() || isConnectedOutside();
				cache.put(this, UMLRTUMLRTPackage.Literals.PORT__IS_CONNECTED, cached);
			}
			result = cached;
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
	public UMLRTPortKind getKind() {
		return UMLRTPortKind.get(isService(), isBehavior(), isWired(), isPublish());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setKind(UMLRTPortKind newKind) {
		if (Objects.requireNonNull(newKind, "kind").isNull()) { //$NON-NLS-1$
			throw new IllegalArgumentException(newKind.getName());
		}

		switch (newKind) {
		case EXTERNAL_BEHAVIOR:
			setService(true);
			setWired(true);
			setBehavior(true);
			setPublish(false);
			toUML().setVisibility(VisibilityKind.PUBLIC_LITERAL);
			break;
		case INTERNAL_BEHAVIOR:
			setService(false);
			setWired(true);
			setBehavior(true);
			setPublish(false);
			toUML().setVisibility(VisibilityKind.PROTECTED_LITERAL);
			break;
		case RELAY:
			setService(true);
			setWired(true);
			setBehavior(false);
			setPublish(false);
			toUML().setVisibility(VisibilityKind.PUBLIC_LITERAL);
			break;
		case SAP:
			setService(false);
			setWired(false);
			setBehavior(true);
			setPublish(false);
			toUML().setVisibility(VisibilityKind.PROTECTED_LITERAL);
			break;
		case SPP:
			setService(true);
			setWired(false);
			setBehavior(true);
			setPublish(true);
			toUML().setVisibility(VisibilityKind.PUBLIC_LITERAL);
			break;
		default:
			throw new IllegalArgumentException("invalid port kind: " + newKind); //$NON-NLS-1$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTPort getRedefinedPort() {
		UMLRTPort result;

		if (toUML() instanceof InternalUMLRTElement) {
			Port superPort = (Port) ((InternalUMLRTElement) toUML()).rtGetRedefinedElement();
			result = UMLRTPort.getInstance(superPort);
		} else {
			UMLRTNamedElement redef = super.getRedefinedElement();
			result = (redef instanceof UMLRTPort) ? (UMLRTPort) redef : null;
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
	public UMLRTProtocol getType() {
		Type result = toUML().getType();
		return (result instanceof Collaboration) ? UMLRTProtocol.getInstance((Collaboration) result, isConjugated())
				: null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setType(UMLRTProtocol newType) {
		if (newType == null) {
			toUML().setType(null);
		} else {
			toUML().setType(newType.toUML());
			setConjugated(newType.isConjugate());
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTCapsulePart> getPartsWithPort() {
		Predicate<UMLRTCapsulePart> excluded = UMLRTCapsulePart::isExcluded;

		return allPartsWithPort()
				.filter(excluded.negate())
				.distinct()
				.collect(collectingAndThen(Collectors.<UMLRTCapsulePart> toList(),
						list -> elist(UMLRTUMLRTPackage.Literals.PORT__PARTS_WITH_PORT, list)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTCapsulePart getPartsWithPort(String name) {
		return getPartsWithPort(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTCapsulePart getPartsWithPort(String name, boolean ignoreCase) {
		partsWithPortLoop: for (UMLRTCapsulePart partsWithPort : getPartsWithPort()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(partsWithPort.getName()) : name.equals(partsWithPort.getName()))) {
				continue partsWithPortLoop;
			}
			return partsWithPort;
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
	public boolean isService() {
		return toUML().isService();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setService(boolean newService) {
		toUML().setIsService(newService);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isBehavior() {
		return toUML().isBehavior();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setBehavior(boolean newBehavior) {
		toUML().setIsBehavior(newBehavior);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isConjugated() {
		return toUML().isConjugated();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setConjugated(boolean newConjugated) {
		toUML().setIsConjugated(newConjugated);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isWired() {
		return toRT().isWired();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setWired(boolean newWired) {
		toRT().setIsWired(newWired);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isPublish() {
		return toRT().isPublish();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setPublish(boolean newPublish) {
		toRT().setIsPublish(newPublish);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isNotification() {
		return toRT().isNotification();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setNotification(boolean newNotification) {
		toRT().setIsNotification(newNotification);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public PortRegistrationType getRegistration() {
		return toRT().getRegistration();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setRegistration(PortRegistrationType newRegistration) {
		toRT().setRegistration(newRegistration);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public String getRegistrationOverride() {
		return toRT().getRegistrationOverride();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setRegistrationOverride(String newRegistrationOverride) {
		toRT().setRegistrationOverride(newRegistrationOverride);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isConnectedInside() {
		boolean result;
		CacheAdapter cache = getCacheAdapter();

		if (cache == null) {
			result = insideConnectors().findAny().isPresent();
		} else {
			Boolean cached = (Boolean) cache.get(this, UMLRTUMLRTPackage.Literals.PORT__IS_CONNECTED_INSIDE);
			if (cached == null) {
				cached = insideConnectors().findAny().isPresent();
				cache.put(this, UMLRTUMLRTPackage.Literals.PORT__IS_CONNECTED_INSIDE, cached);
			}
			result = cached;
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
	public boolean isConnectedOutside() {
		boolean result;
		CacheAdapter cache = getCacheAdapter();

		if (cache == null) {
			result = outsideConnectors().findAny().isPresent();
		} else {
			Boolean cached = (Boolean) cache.get(this, UMLRTUMLRTPackage.Literals.PORT__IS_CONNECTED_OUTSIDE);
			if (cached == null) {
				cached = outsideConnectors().findAny().isPresent();
				cache.put(this, UMLRTUMLRTPackage.Literals.PORT__IS_CONNECTED_OUTSIDE, cached);
			}
			result = cached;
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
	public List<UMLRTConnector> getInsideConnectors() {
		List<UMLRTConnector> result;
		CacheAdapter cache = getCacheAdapter();

		if (cache == null) {
			// Get the connectors that are defined within my capsule
			result = insideConnectors().collect(
					collectingAndThen(toList(),
							list -> elist(UMLRTUMLRTPackage.Literals.PORT__INSIDE_CONNECTOR, list)));
		} else {
			@SuppressWarnings("unchecked")
			List<UMLRTConnector> cached = (List<UMLRTConnector>) cache.get(this, UMLRTUMLRTPackage.Literals.PORT__INSIDE_CONNECTOR);
			if (cached == null) {
				cached = insideConnectors().collect(
						collectingAndThen(toList(),
								list -> elist(UMLRTUMLRTPackage.Literals.PORT__INSIDE_CONNECTOR, list)));
				cache.put(this, UMLRTUMLRTPackage.Literals.PORT__INSIDE_CONNECTOR, cached);
			}
			result = cached;
		}

		return result;
	}

	/**
	 * Obtains a stream over my inside connectors, those which are owned by my
	 * context {@link #getCapsule() capsule}.
	 *
	 * @return my inside connectors
	 */
	protected Stream<UMLRTConnector> insideConnectors() {
		Stream<UMLRTConnector> result;

		UMLRTCapsule capsule = getCapsule();
		if (capsule == null) {
			// Deleted ports have no connectors
			result = Stream.empty();
		} else {
			Predicate<UMLRTConnector> excluded = UMLRTConnector::isExcluded;

			result = allConnectors()
					.map(capsule::getRedefinitionOf)
					.filter(Objects::nonNull)
					.filter(excluded.negate())
					.distinct();
		}

		return result;
	}

	/**
	 * Obtains a stream over all of my connectors, both {@linkplain #insideConnectors() inside} and
	 * {@linkplain #outsideConnectors() outside}.
	 *
	 * @return all of my connectors
	 */
	protected Stream<UMLRTConnector> allConnectors() {
		Stream<UMLRTConnector> result;

		UMLRTCapsule capsule = getCapsule();
		if (capsule == null) {
			// Deleted ports have no connectors
			result = Stream.empty();
		} else {
			result = getRedefinitionChain().stream()
					.map(UMLRTPortImpl.class::cast)
					.flatMap(UMLRTPortImpl::getEnds)
					.map(Element::getOwner)
					.map(Connector.class::cast)
					.map(UMLRTConnector::getInstance)
					.filter(Objects::nonNull);
		}

		return result;
	}

	Stream<ConnectorEnd> getEnds() {
		return toUML().getEnds().stream();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTConnector getInsideConnector(String name) {
		return getInsideConnector(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTConnector getInsideConnector(String name, boolean ignoreCase) {
		insideConnectorLoop: for (UMLRTConnector insideConnector : getInsideConnectors()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(insideConnector.getName()) : name.equals(insideConnector.getName()))) {
				continue insideConnectorLoop;
			}
			return insideConnector;
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
	public List<UMLRTConnector> getOutsideConnectors() {
		List<UMLRTConnector> result;
		CacheAdapter cache = getCacheAdapter();

		if (cache == null) {
			// Get the connectors that are defined without my capsule
			result = outsideConnectors().collect(
					collectingAndThen(toList(),
							list -> elist(UMLRTUMLRTPackage.Literals.PORT__OUTSIDE_CONNECTOR, list)));
		} else {
			@SuppressWarnings("unchecked")
			List<UMLRTConnector> cached = (List<UMLRTConnector>) cache.get(this, UMLRTUMLRTPackage.Literals.PORT__OUTSIDE_CONNECTOR);
			if (cached == null) {
				cached = outsideConnectors().collect(
						collectingAndThen(toList(),
								list -> elist(UMLRTUMLRTPackage.Literals.PORT__OUTSIDE_CONNECTOR, list)));
				cache.put(this, UMLRTUMLRTPackage.Literals.PORT__OUTSIDE_CONNECTOR, cached);
			}
			result = cached;
		}

		return result;
	}

	/**
	 * Obtains a stream over my outside connectors, those that are owned by capsules (and connect) parts
	 * of my {@link #getCapsule() capsule} type. This includes connectors that I inherit in addition
	 * to connectors inherited from me.
	 *
	 * @return my outside connectors
	 *
	 * @see #allPartsWithPort()
	 */
	protected Stream<UMLRTConnector> outsideConnectors() {
		Stream<UMLRTConnector> result;

		UMLRTCapsule capsule = getCapsule();
		if (capsule == null) {
			// Deleted ports have no connectors
			result = Stream.empty();
		} else {
			// Get my root definition in order to inherit connectors from my redefinition chain
			result = ((UMLRTPort) getRootDefinition()).getPartsWithPort().stream()
					.flatMap(part -> part.getConnectorsOf(this).stream())
					.distinct();
		}

		return result;
	}

	/**
	 * Obtains a stream over all of the currently loaded capsule-parts that expose me as a port.
	 *
	 * @return all known parts that feature me
	 */
	protected Stream<? extends UMLRTCapsulePart> allPartsWithPort() {
		CacheAdapter cache = CacheAdapter.getCacheAdapter(toUML());

		return allRedefinitions()
				.map(UMLRTPort::getCapsule)
				.map(UMLRTNamedElement::toUML)
				.flatMap(e -> cache.getNonNavigableInverseReferences(e).stream())
				.filter(s -> s.getEStructuralFeature() == UMLPackage.Literals.TYPED_ELEMENT__TYPE)
				.map(EStructuralFeature.Setting::getEObject)
				.filter(Property.class::isInstance).map(Property.class::cast)
				.map(UMLRTCapsulePart::getInstance)
				.filter(Objects::nonNull)
				.map(UMLRTCapsulePartImpl.class::cast)
				.flatMap(UMLRTCapsulePartImpl::allRedefinitions)
				.distinct();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Stream<? extends UMLRTPort> allRedefinitions() {
		Predicate<UMLRTPort> excluded = UMLRTPort::isExcluded;
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
	public UMLRTConnector getOutsideConnector(String name) {
		return getOutsideConnector(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTConnector getOutsideConnector(String name, boolean ignoreCase) {
		outsideConnectorLoop: for (UMLRTConnector outsideConnector : getOutsideConnectors()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(outsideConnector.getName()) : name.equals(outsideConnector.getName()))) {
				continue outsideConnectorLoop;
			}
			return outsideConnector;
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
	public UMLRTCapsule getCapsule() {
		return UMLRTCapsule.getInstance(toUML().getClass_());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Port toUML() {
		return (Port) super.toUML();
	}

	@Override
	RTPort toRT() {
		return (RTPort) super.toRT();
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
		case UMLRTUMLRTPackage.PORT__CONNECTOR:
			return getConnectors();
		case UMLRTUMLRTPackage.PORT__KIND:
			return getKind();
		case UMLRTUMLRTPackage.PORT__REDEFINED_PORT:
			return getRedefinedPort();
		case UMLRTUMLRTPackage.PORT__TYPE:
			return getType();
		case UMLRTUMLRTPackage.PORT__PARTS_WITH_PORT:
			return getPartsWithPort();
		case UMLRTUMLRTPackage.PORT__SERVICE:
			return isService();
		case UMLRTUMLRTPackage.PORT__BEHAVIOR:
			return isBehavior();
		case UMLRTUMLRTPackage.PORT__CONJUGATED:
			return isConjugated();
		case UMLRTUMLRTPackage.PORT__WIRED:
			return isWired();
		case UMLRTUMLRTPackage.PORT__PUBLISH:
			return isPublish();
		case UMLRTUMLRTPackage.PORT__NOTIFICATION:
			return isNotification();
		case UMLRTUMLRTPackage.PORT__REGISTRATION:
			return getRegistration();
		case UMLRTUMLRTPackage.PORT__REGISTRATION_OVERRIDE:
			return getRegistrationOverride();
		case UMLRTUMLRTPackage.PORT__IS_CONNECTED:
			return isConnected();
		case UMLRTUMLRTPackage.PORT__IS_CONNECTED_INSIDE:
			return isConnectedInside();
		case UMLRTUMLRTPackage.PORT__IS_CONNECTED_OUTSIDE:
			return isConnectedOutside();
		case UMLRTUMLRTPackage.PORT__INSIDE_CONNECTOR:
			return getInsideConnectors();
		case UMLRTUMLRTPackage.PORT__OUTSIDE_CONNECTOR:
			return getOutsideConnectors();
		case UMLRTUMLRTPackage.PORT__CAPSULE:
			return getCapsule();
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
		case UMLRTUMLRTPackage.PORT__KIND:
			setKind((UMLRTPortKind) newValue);
			return;
		case UMLRTUMLRTPackage.PORT__TYPE:
			setType((UMLRTProtocol) newValue);
			return;
		case UMLRTUMLRTPackage.PORT__PARTS_WITH_PORT:
			getPartsWithPort().clear();
			getPartsWithPort().addAll((Collection<? extends UMLRTCapsulePart>) newValue);
			return;
		case UMLRTUMLRTPackage.PORT__SERVICE:
			setService((Boolean) newValue);
			return;
		case UMLRTUMLRTPackage.PORT__BEHAVIOR:
			setBehavior((Boolean) newValue);
			return;
		case UMLRTUMLRTPackage.PORT__CONJUGATED:
			setConjugated((Boolean) newValue);
			return;
		case UMLRTUMLRTPackage.PORT__WIRED:
			setWired((Boolean) newValue);
			return;
		case UMLRTUMLRTPackage.PORT__PUBLISH:
			setPublish((Boolean) newValue);
			return;
		case UMLRTUMLRTPackage.PORT__NOTIFICATION:
			setNotification((Boolean) newValue);
			return;
		case UMLRTUMLRTPackage.PORT__REGISTRATION:
			setRegistration((PortRegistrationType) newValue);
			return;
		case UMLRTUMLRTPackage.PORT__REGISTRATION_OVERRIDE:
			setRegistrationOverride((String) newValue);
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
		case UMLRTUMLRTPackage.PORT__KIND:
			setKind(KIND_EDEFAULT);
			return;
		case UMLRTUMLRTPackage.PORT__TYPE:
			setType((UMLRTProtocol) null);
			return;
		case UMLRTUMLRTPackage.PORT__PARTS_WITH_PORT:
			getPartsWithPort().clear();
			return;
		case UMLRTUMLRTPackage.PORT__SERVICE:
			setService(SERVICE_EDEFAULT);
			return;
		case UMLRTUMLRTPackage.PORT__BEHAVIOR:
			setBehavior(BEHAVIOR_EDEFAULT);
			return;
		case UMLRTUMLRTPackage.PORT__CONJUGATED:
			setConjugated(CONJUGATED_EDEFAULT);
			return;
		case UMLRTUMLRTPackage.PORT__WIRED:
			setWired(WIRED_EDEFAULT);
			return;
		case UMLRTUMLRTPackage.PORT__PUBLISH:
			setPublish(PUBLISH_EDEFAULT);
			return;
		case UMLRTUMLRTPackage.PORT__NOTIFICATION:
			setNotification(NOTIFICATION_EDEFAULT);
			return;
		case UMLRTUMLRTPackage.PORT__REGISTRATION:
			setRegistration(REGISTRATION_EDEFAULT);
			return;
		case UMLRTUMLRTPackage.PORT__REGISTRATION_OVERRIDE:
			setRegistrationOverride(REGISTRATION_OVERRIDE_EDEFAULT);
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
		case UMLRTUMLRTPackage.PORT__REDEFINED_ELEMENT:
			return isSetRedefinedElement();
		case UMLRTUMLRTPackage.PORT__CONNECTOR:
			return isSetConnectors();
		case UMLRTUMLRTPackage.PORT__REDEFINITION_CONTEXT:
			return isSetRedefinitionContext();
		case UMLRTUMLRTPackage.PORT__KIND:
			return getKind() != KIND_EDEFAULT;
		case UMLRTUMLRTPackage.PORT__REDEFINED_PORT:
			return getRedefinedPort() != null;
		case UMLRTUMLRTPackage.PORT__TYPE:
			return getType() != null;
		case UMLRTUMLRTPackage.PORT__PARTS_WITH_PORT:
			return !getPartsWithPort().isEmpty();
		case UMLRTUMLRTPackage.PORT__SERVICE:
			return isService() != SERVICE_EDEFAULT;
		case UMLRTUMLRTPackage.PORT__BEHAVIOR:
			return isBehavior() != BEHAVIOR_EDEFAULT;
		case UMLRTUMLRTPackage.PORT__CONJUGATED:
			return isConjugated() != CONJUGATED_EDEFAULT;
		case UMLRTUMLRTPackage.PORT__WIRED:
			return isWired() != WIRED_EDEFAULT;
		case UMLRTUMLRTPackage.PORT__PUBLISH:
			return isPublish() != PUBLISH_EDEFAULT;
		case UMLRTUMLRTPackage.PORT__NOTIFICATION:
			return isNotification() != NOTIFICATION_EDEFAULT;
		case UMLRTUMLRTPackage.PORT__REGISTRATION:
			return getRegistration() != REGISTRATION_EDEFAULT;
		case UMLRTUMLRTPackage.PORT__REGISTRATION_OVERRIDE:
			return REGISTRATION_OVERRIDE_EDEFAULT == null ? getRegistrationOverride() != null : !REGISTRATION_OVERRIDE_EDEFAULT.equals(getRegistrationOverride());
		case UMLRTUMLRTPackage.PORT__IS_CONNECTED:
			return isConnected() != IS_CONNECTED_EDEFAULT;
		case UMLRTUMLRTPackage.PORT__IS_CONNECTED_INSIDE:
			return isConnectedInside() != IS_CONNECTED_INSIDE_EDEFAULT;
		case UMLRTUMLRTPackage.PORT__IS_CONNECTED_OUTSIDE:
			return isConnectedOutside() != IS_CONNECTED_OUTSIDE_EDEFAULT;
		case UMLRTUMLRTPackage.PORT__INSIDE_CONNECTOR:
			return !getInsideConnectors().isEmpty();
		case UMLRTUMLRTPackage.PORT__OUTSIDE_CONNECTOR:
			return !getOutsideConnectors().isEmpty();
		case UMLRTUMLRTPackage.PORT__CAPSULE:
			return getCapsule() != null;
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
				|| eIsSet(UMLRTUMLRTPackage.PORT__REDEFINED_PORT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean isSetConnectors() {
		return eIsSet(UMLRTUMLRTPackage.PORT__INSIDE_CONNECTOR)
				|| eIsSet(UMLRTUMLRTPackage.PORT__OUTSIDE_CONNECTOR);
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
				|| eIsSet(UMLRTUMLRTPackage.PORT__CAPSULE);
	}

} // UMLRTPortImpl
