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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Capsule;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTConnector;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.FacadeContentsEList;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Capsule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl#getGeneral <em>General</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl#getSpecifics <em>Specific</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl#getRedefinableElements <em>Redefinable Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl#getSuperclass <em>Superclass</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl#getSubclasses <em>Subclass</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl#getPorts <em>Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl#getCapsuleParts <em>Capsule Part</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl#getConnectors <em>Connector</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl#getHierarchy <em>Hierarchy</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl#getStateMachine <em>State Machine</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsuleImpl#getPackage <em>Package</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTCapsuleImpl extends UMLRTClassifierImpl implements UMLRTCapsule {

	static final FacadeType<Class, Capsule, UMLRTCapsuleImpl> TYPE = new FacadeType<>(
			UMLRTCapsuleImpl.class,
			UMLPackage.Literals.CLASS,
			UMLRealTimePackage.Literals.CAPSULE,
			UMLRTCapsuleImpl::getInstance,
			capsule -> (UMLRTCapsuleImpl) capsule.getSuperclass(),
			UMLRTCapsuleImpl::new);

	private static final FacadeReference<Port, RTPort, UMLRTPort, UMLRTPortImpl> PORT_REFERENCE = new FacadeReference<>(
			UMLRTPortImpl.TYPE,
			UMLRTUMLRTPackage.CAPSULE__PORT,
			UMLRTPort.class,
			UMLPackage.Literals.ENCAPSULATED_CLASSIFIER__OWNED_PORT,
			ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT,
			UMLPackage.Literals.PORT);

	private static final FacadeReference<Property, CapsulePart, UMLRTCapsulePart, UMLRTCapsulePartImpl> CAPSULE_PART_REFERENCE = new FacadeReference<>(
			UMLRTCapsulePartImpl.TYPE,
			UMLRTUMLRTPackage.CAPSULE__CAPSULE_PART,
			UMLRTCapsulePart.class,
			UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE,
			ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE,
			UMLPackage.Literals.PROPERTY);

	private static final FacadeReference<Connector, RTConnector, UMLRTConnector, UMLRTConnectorImpl> CONNECTOR_REFERENCE = new FacadeReference<>(
			UMLRTConnectorImpl.TYPE,
			UMLRTUMLRTPackage.CAPSULE__CONNECTOR,
			UMLRTConnector.class,
			UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR,
			ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR,
			UMLPackage.Literals.CONNECTOR);

	protected static class CapsuleAdapter<F extends UMLRTCapsuleImpl> extends ClassifierAdapter<F> {

		CapsuleAdapter(F facade) {
			super(facade);
		}

		@Override
		protected void notifyGeneral(F owner, FacadeObject oldObject, FacadeObject newObject) {
			// Notify the subset before the superset
			if (owner.eNotificationRequired()) {
				owner.eNotify(new ENotificationImpl(owner, Notification.SET, UMLRTUMLRTPackage.Literals.CAPSULE__SUPERCLASS, oldObject, newObject));
			}
			super.notifyGeneral(owner, oldObject, newObject);
		}

		@Override
		protected List<? extends FacadeObject> getFacadeList(EObject owner, EReference reference, EObject object) {
			List<? extends FacadeObject> result;

			if ((reference == UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE)
					|| (reference == ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE)) {

				result = (object instanceof Port) ? get().ports : get().capsuleParts;
			} else if ((reference == UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR)
					|| (reference == ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR)) {

				result = get().connectors;
			} else if ((reference == UMLPackage.Literals.ENCAPSULATED_CLASSIFIER__OWNED_PORT)
					|| (reference == ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT)) {

				result = get().ports;
			} else {
				result = super.getFacadeList(owner, reference, object);
			}

			return result;
		}

		@Override
		protected InternalFacadeEList<? extends FacadeObject> validateObject(EObject owner, EReference reference, FacadeObject object) {
			InternalFacadeEList<? extends FacadeObject> result = null;

			if (object instanceof UMLRTPort) {
				if ((reference == UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE)
						|| (reference == ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE)) {
					result = get().ports;
				}
			} else if (object instanceof UMLRTCapsulePart) {
				if ((reference == UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE)
						|| (reference == ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE)) {
					result = get().capsuleParts;
				}
			} else if (object instanceof UMLRTConnector) {
				if ((reference == UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR)
						|| (reference == ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR)) {
					result = get().connectors;
				}
			} else {
				result = super.validateObject(owner, reference, object);
			}

			return result;
		}

		@Override
		protected void handleObjectReplaced(Notification msg, int position, FacadeObject oldObject, FacadeObject newObject) {
			if (msg.getFeature() == UMLPackage.Literals.BEHAVIORED_CLASSIFIER__CLASSIFIER_BEHAVIOR) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), Notification.SET, UMLRTUMLRTPackage.Literals.CAPSULE__STATE_MACHINE, oldObject, newObject));
				}
			} else {
				super.handleObjectReplaced(msg, position, oldObject, newObject);
			}
		}
	}

	InternalFacadeEList<UMLRTPort> ports;
	InternalFacadeEList<UMLRTCapsulePart> capsuleParts;
	InternalFacadeEList<UMLRTConnector> connectors;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTCapsuleImpl() {
		super();
	}

	/**
	 * Obtains the canonical instance of the capsule façade for a class.
	 *
	 * @param class_
	 *            a class in the UML model
	 *
	 * @return its capsule façade, or {@code null} if the class is not a valid capsule
	 */
	public static UMLRTCapsuleImpl getInstance(Class class_) {
		return getFacade(class_, TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.CAPSULE;
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTCapsuleImpl> createFacadeAdapter() {
		return new CapsuleAdapter<>(this);
	}

	UMLRTCapsuleImpl initCapsule() {
		// Capsules are active classes
		toUML().setIsActive(true);

		return this;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTClassifier getGeneral() {
		UMLRTCapsule superclass = getSuperclass();
		if (superclass != null) {
			return superclass;
		}
		return super.getGeneral();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public List<UMLRTClassifier> getSpecifics() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			List<UMLRTClassifier> specifics = (List<UMLRTClassifier>) cache.get(eResource, this, UMLRTUMLRTPackage.Literals.CLASSIFIER__SPECIFIC);
			if (specifics == null) {
				cache.put(eResource, this, UMLRTUMLRTPackage.Literals.CLASSIFIER__SPECIFIC, specifics = new DerivedUnionEObjectEList<>(UMLRTClassifier.class, this, UMLRTUMLRTPackage.CAPSULE__SPECIFIC, SPECIFIC_ESUBSETS));
			}
			return specifics;
		}
		return new DerivedUnionEObjectEList<>(UMLRTClassifier.class, this, UMLRTUMLRTPackage.CAPSULE__SPECIFIC, SPECIFIC_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getSpecifics() <em>Specific</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSpecifics()
	 * @generated
	 * @ordered
	 */
	protected static final int[] SPECIFIC_ESUBSETS = new int[] { UMLRTUMLRTPackage.CAPSULE__SUBCLASS };

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
						redefinableElements = new DerivedUnionEObjectEList<>(UMLRTNamedElement.class, this, UMLRTUMLRTPackage.CAPSULE__REDEFINABLE_ELEMENT, REDEFINABLE_ELEMENT_ESUBSETS));
			}
			return redefinableElements;
		}
		return new DerivedUnionEObjectEList<>(UMLRTNamedElement.class, this, UMLRTUMLRTPackage.CAPSULE__REDEFINABLE_ELEMENT, REDEFINABLE_ELEMENT_ESUBSETS);
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
	protected static final int[] REDEFINABLE_ELEMENT_ESUBSETS = new int[] { UMLRTUMLRTPackage.CAPSULE__PORT, UMLRTUMLRTPackage.CAPSULE__CAPSULE_PART, UMLRTUMLRTPackage.CAPSULE__CONNECTOR, UMLRTUMLRTPackage.CAPSULE__STATE_MACHINE };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTCapsule getSuperclass() {
		List<Class> superclasses = toUML().getSuperClasses();
		return superclasses.isEmpty() ? null : getInstance(superclasses.get(0));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setSuperclass(UMLRTCapsule newSuperclass) {
		setGeneral(toUML(), (newSuperclass == null) ? null : newSuperclass.toUML());
	}

	@Override
	public void setGeneral(UMLRTClassifier general) {
		setSuperclass((UMLRTCapsule) general);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTCapsule> getSubclasses() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			@SuppressWarnings("unchecked")
			List<UMLRTCapsule> subclasses = (List<UMLRTCapsule>) cache.get(this, UMLRTUMLRTPackage.Literals.CAPSULE__SUBCLASS);
			if (subclasses == null) {
				cache.put(this, UMLRTUMLRTPackage.Literals.CAPSULE__SUBCLASS,
						subclasses = doGetSubclasses());
			}
			return subclasses;
		}
		return doGetSubclasses();
	}

	private List<UMLRTCapsule> doGetSubclasses() {
		return specifics(toUML(), Class.class)
				.map(UMLRTCapsuleImpl::getInstance)
				.filter(Objects::nonNull)
				.collect(collectingAndThen(Collectors.<UMLRTCapsule> toList(),
						list -> elist(UMLRTUMLRTPackage.Literals.CAPSULE__SUBCLASS, list)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTCapsule getSubclass(String name) {
		return getSubclass(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTCapsule getSubclass(String name, boolean ignoreCase) {
		subclassLoop: for (UMLRTCapsule subclass : getSubclasses()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(subclass.getName()) : name.equals(subclass.getName()))) {
				continue subclassLoop;
			}
			return subclass;
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
	public List<UMLRTPort> getPorts() {
		if (ports == null) {
			ports = (InternalFacadeEList<UMLRTPort>) getFacades(PORT_REFERENCE);
		}
		return ports;
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
	public List<UMLRTConnector> getConnectors() {
		if (connectors == null) {
			connectors = (InternalFacadeEList<UMLRTConnector>) getFacades(CONNECTOR_REFERENCE);
		}
		return connectors;
	}

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
	 * @generated NOT
	 */
	@Override
	public List<UMLRTCapsulePart> getCapsuleParts() {
		if (capsuleParts == null) {
			capsuleParts = (InternalFacadeEList<UMLRTCapsulePart>) getFacades(CAPSULE_PART_REFERENCE);
		}
		return capsuleParts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTCapsulePart getCapsulePart(String name) {
		return getCapsulePart(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTCapsulePart getCapsulePart(String name, boolean ignoreCase) {
		capsulePartLoop: for (UMLRTCapsulePart capsulePart : getCapsuleParts()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(capsulePart.getName()) : name.equals(capsulePart.getName()))) {
				continue capsulePartLoop;
			}
			return capsulePart;
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
	public UMLRTPackage getPackage() {
		return UMLRTPackage.getInstance(toUML().getNearestPackage());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public org.eclipse.uml2.uml.Class toUML() {
		return (Class) super.toUML();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTCapsule> getAncestry() {
		// TODO: Cache this
		return doGetAncestry();
	}

	private List<UMLRTCapsule> doGetAncestry() {
		return ancestry(toUML(), Class.class).stream()
				.map(UMLRTCapsuleImpl::getInstance)
				.filter(Objects::nonNull)
				.collect(collectingAndThen(toList(), ECollections::unmodifiableEList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTPort> getPorts(boolean withExclusions) {
		return getFacades(PORT_REFERENCE, withExclusions);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTCapsulePart> getCapsuleParts(boolean withExclusions) {
		return getFacades(CAPSULE_PART_REFERENCE, withExclusions);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTConnector> getConnectors(boolean withExclusions) {
		return getFacades(CONNECTOR_REFERENCE, withExclusions);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTPort createPort(UMLRTProtocol type) {
		return createPort(type, UMLRTPortKind.EXTERNAL_BEHAVIOR);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTPort createPort(UMLRTProtocol type, UMLRTPortKind kind) {
		String baseProtocolName = type.isConjugate()
				? type.getConjugate().getName() // want the base protocol name
				: type.getName();
		String portName = initialLower(baseProtocolName);
		UMLRTPort result = create(PORT_REFERENCE, portName);
		result.setType(type);
		result.setKind(kind);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTCapsulePart createCapsulePart(UMLRTCapsule type) {
		String partName = initialLower(type.getName());
		UMLRTCapsulePart result = create(CAPSULE_PART_REFERENCE, partName);
		result.setType(type);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTConnector createConnector(String name,
			UMLRTPort source, UMLRTCapsulePart sourcePartWithPort,
			UMLRTPort target, UMLRTCapsulePart targetPartWithPort) {

		UMLRTConnector result = create(CONNECTOR_REFERENCE, name);
		ConnectorEnd sourceEnd = result.toUML().createEnd();
		sourceEnd.setRole(source.toUML());
		if (sourcePartWithPort != null) {
			sourceEnd.setPartWithPort(sourcePartWithPort.toUML());
		}

		ConnectorEnd targetEnd = result.toUML().createEnd();
		targetEnd.setRole(target.toUML());
		if (targetPartWithPort != null) {
			targetEnd.setPartWithPort(targetPartWithPort.toUML());
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
	public UMLRTStateMachine createStateMachine() {
		UMLRTStateMachine result = getStateMachine();

		Class class_ = toUML();

		if (class_.getClassifierBehavior() == null) {
			StateMachine stateMachine = (StateMachine) class_.createClassifierBehavior("StateMachine", UMLPackage.Literals.STATE_MACHINE); //$NON-NLS-1$
			stateMachine.setIsReentrant(false);
			applyStereotype(stateMachine, RTStateMachine.class);

			result = getStateMachine();

			if (result != null) {
				result.ensureDefaultContents();
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
	public Stream<UMLRTCapsule> getHierarchy() {
		return hierarchy(toUML(), Class.class)
				.map(UMLRTCapsule::getInstance)
				.filter(Objects::nonNull);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTStateMachine getStateMachine() {
		Behavior behavior = toUML().getClassifierBehavior();
		return (behavior instanceof StateMachine)
				? UMLRTFactory.createStateMachine((StateMachine) behavior)
				: null;
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
		case UMLRTUMLRTPackage.CAPSULE__SUPERCLASS:
			return getSuperclass();
		case UMLRTUMLRTPackage.CAPSULE__SUBCLASS:
			return getSubclasses();
		case UMLRTUMLRTPackage.CAPSULE__PORT:
			return getPorts();
		case UMLRTUMLRTPackage.CAPSULE__CAPSULE_PART:
			return getCapsuleParts();
		case UMLRTUMLRTPackage.CAPSULE__CONNECTOR:
			return getConnectors();
		case UMLRTUMLRTPackage.CAPSULE__HIERARCHY:
			return getHierarchy();
		case UMLRTUMLRTPackage.CAPSULE__STATE_MACHINE:
			return getStateMachine();
		case UMLRTUMLRTPackage.CAPSULE__PACKAGE:
			return getPackage();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	protected Object facadeGetAll(int referenceID) {
		switch (referenceID) {
		case UMLRTUMLRTPackage.CAPSULE__PORT:
			return getFacades(PORT_REFERENCE, true);
		case UMLRTUMLRTPackage.CAPSULE__CAPSULE_PART:
			return getFacades(CAPSULE_PART_REFERENCE, true);
		case UMLRTUMLRTPackage.CAPSULE__CONNECTOR:
			return getFacades(CONNECTOR_REFERENCE, true);
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
		case UMLRTUMLRTPackage.CAPSULE__SUPERCLASS:
			setSuperclass((UMLRTCapsule) newValue);
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
		case UMLRTUMLRTPackage.CAPSULE__SUPERCLASS:
			setSuperclass((UMLRTCapsule) null);
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
		case UMLRTUMLRTPackage.CAPSULE__GENERAL:
			return isSetGeneral();
		case UMLRTUMLRTPackage.CAPSULE__SPECIFIC:
			return isSetSpecifics();
		case UMLRTUMLRTPackage.CAPSULE__REDEFINABLE_ELEMENT:
			return isSetRedefinableElements();
		case UMLRTUMLRTPackage.CAPSULE__SUPERCLASS:
			return getSuperclass() != null;
		case UMLRTUMLRTPackage.CAPSULE__SUBCLASS:
			return !getSubclasses().isEmpty();
		case UMLRTUMLRTPackage.CAPSULE__PORT:
			return !getPorts().isEmpty();
		case UMLRTUMLRTPackage.CAPSULE__CAPSULE_PART:
			return !getCapsuleParts().isEmpty();
		case UMLRTUMLRTPackage.CAPSULE__CONNECTOR:
			return !getConnectors().isEmpty();
		case UMLRTUMLRTPackage.CAPSULE__HIERARCHY:
			return getHierarchy() != null;
		case UMLRTUMLRTPackage.CAPSULE__STATE_MACHINE:
			return getStateMachine() != null;
		case UMLRTUMLRTPackage.CAPSULE__PACKAGE:
			return getPackage() != null;
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
	public boolean isSetGeneral() {
		return super.isSetGeneral()
				|| eIsSet(UMLRTUMLRTPackage.CAPSULE__SUPERCLASS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetSpecifics() {
		return super.isSetSpecifics()
				|| eIsSet(UMLRTUMLRTPackage.CAPSULE__SUBCLASS);
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
				|| eIsSet(UMLRTUMLRTPackage.CAPSULE__PORT)
				|| eIsSet(UMLRTUMLRTPackage.CAPSULE__CAPSULE_PART)
				|| eIsSet(UMLRTUMLRTPackage.CAPSULE__CONNECTOR)
				|| eIsSet(UMLRTUMLRTPackage.CAPSULE__STATE_MACHINE);
	}

} // UMLRTCapsuleImpl
