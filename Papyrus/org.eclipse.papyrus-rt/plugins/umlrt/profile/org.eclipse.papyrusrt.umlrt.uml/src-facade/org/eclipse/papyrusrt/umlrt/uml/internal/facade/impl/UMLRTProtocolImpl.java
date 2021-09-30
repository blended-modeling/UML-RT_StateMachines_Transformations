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

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Protocol;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageSet;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.CachedReference;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.FacadeContentsEList;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.AnyReceiveEvent;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Protocol</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl#getGeneral <em>General</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl#getSpecifics <em>Specific</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl#getMessages <em>Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl#getRedefinableElements <em>Redefinable Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl#getPackage <em>Package</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl#getSuperProtocol <em>Super Protocol</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl#getSubProtocols <em>Sub Protocol</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl#getInMessages <em>In Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl#getOutMessages <em>Out Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl#getInOutMessages <em>In Out Message</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl#isConjugate <em>Is Conjugate</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl#getConjugate <em>Conjugate</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTProtocolImpl#getHierarchy <em>Hierarchy</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTProtocolImpl extends UMLRTClassifierImpl implements UMLRTProtocol {
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

	private static final String ANY_RECEIVE_EVENT_NAME = "*"; //$NON-NLS-1$

	// Work around JDT compiler type inference bug: must not use the "diamond operator" here
	static final FacadeType<Collaboration, Protocol, UMLRTProtocolImpl> TYPE = new FacadeType<Collaboration, Protocol, UMLRTProtocolImpl>(
			UMLRTProtocolImpl.class,
			UMLPackage.Literals.COLLABORATION,
			UMLRealTimePackage.Literals.PROTOCOL,
			UMLRTProtocolImpl::getInstance,
			protocol -> (UMLRTProtocolImpl) protocol.getSuperProtocol(),
			UMLRTProtocolImpl::new);

	private static final Map<RTMessageKind, FacadeReference<Operation, EObject, UMLRTProtocolMessage, UMLRTProtocolMessageImpl>> MESSAGE_REFERENCES;

	private final boolean isConjugate;
	private final Supplier<UMLRTProtocol> conjugate;

	static {

		EnumMap<RTMessageKind, Integer> references = new EnumMap<>(RTMessageKind.class);
		references.put(RTMessageKind.IN, UMLRTUMLRTPackage.PROTOCOL__IN_MESSAGE);
		references.put(RTMessageKind.IN_OUT, UMLRTUMLRTPackage.PROTOCOL__IN_OUT_MESSAGE);
		references.put(RTMessageKind.OUT, UMLRTUMLRTPackage.PROTOCOL__OUT_MESSAGE);

		MESSAGE_REFERENCES = new EnumMap<>(RTMessageKind.class);
		RTMessageKind.VALUES.forEach(kind -> MESSAGE_REFERENCES.put(kind,
				new FacadeReference.Indirect<>(
						UMLRTProtocolMessageImpl.TYPE,
						references.get(kind),
						UMLRTProtocolMessage.class,
						UMLPackage.Literals.INTERFACE__OWNED_OPERATION,
						ExtUMLExtPackage.Literals.INTERFACE__IMPLICIT_OPERATION,
						UMLPackage.Literals.OPERATION,
						protocol -> requireMessageSet((Collaboration) protocol, kind))));
	}

	protected static class ProtocolAdapter<F extends UMLRTProtocolImpl> extends ClassifierAdapter<F> {

		private final Supplier<ProtocolAdapter<F>> conjugate;

		ProtocolAdapter(F facade) {
			super(facade);

			this.conjugate = new CachedReference<>(() -> {
				ProtocolAdapter<F> result = null;

				UMLRTProtocolImpl base = ProtocolAdapter.this.get();
				if (base != null) {
					Supplier<UMLRTProtocol> conjugateSupplier = base.conjugate;

					// Be careful not to create a conjugate that we don't need
					if (!(conjugateSupplier instanceof CachedReference<?>) || ((CachedReference<?>) conjugateSupplier).exists()) {
						UMLRTProtocol conj = conjugateSupplier.get();
						if (conj != null) {
							@SuppressWarnings("unchecked")
							F conjAsF = (F) conj;

							// Never synchronize the other way because the base gets events
							// from the UML model via the real adapter
							result = new ProtocolAdapter<>(conjAsF, null);
						}
					}
				}

				return result;
			});
		}

		private ProtocolAdapter(F facade, Supplier<ProtocolAdapter<F>> conjugate) {
			super(facade);

			this.conjugate = conjugate;
		}

		/**
		 * Am I the adapter for the conjugate façade?
		 * 
		 * @return whether I am the conjugate adapter
		 */
		protected final boolean isConjugate() {
			return conjugate == null;
		}

		@Override
		public void setTarget(Notifier newTarget) {
			super.setTarget(newTarget);

			if (newTarget instanceof Collaboration) {
				Collaboration protocol = (Collaboration) newTarget;
				Package container = protocol.getNearestPackage();
				if (container != null) {
					addAdapter(container);
				}
			} else if (newTarget instanceof Package) {
				// Discover the message sets
				Package container = (Package) newTarget;
				container.getOwnedTypes().stream()
						.filter(Interface.class::isInstance).map(Interface.class::cast)
						.filter(UMLRTProtocolImpl::isRTMessageSet)
						.forEach(this::addAdapter);
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (oldTarget instanceof Collaboration) {
				Collaboration protocol = (Collaboration) oldTarget;
				Package container = protocol.getNearestPackage();
				if (container != null) {
					removeAdapter(container);
				}
			} else if (oldTarget instanceof Package) {
				// Undiscover the message sets
				Package container = (Package) oldTarget;
				container.getOwnedTypes().stream()
						.filter(Interface.class::isInstance)
						.forEach(this::removeAdapter);
			}

			super.unsetTarget(oldTarget);
		}

		@Override
		protected void handleReference(Notification msg) {
			super.handleReference(msg);

			if ((msg.getFeature() == UMLPackage.Literals.INTERFACE__OWNED_OPERATION) && !isConjugate()) {
				// Synchronize the messages of a conjugate, if any
				ProtocolAdapter<F> conjugate = this.conjugate.get();
				if (conjugate != null) {
					conjugate.handleReference(msg);
				}
			}
		}

		@Override
		protected FacadeObject getFacade(EObject owner, EReference reference, EObject object) {
			FacadeObject result = super.getFacade(owner, reference, object);

			if ((result instanceof UMLRTProtocolMessage) && isConjugate()) {
				result = ((UMLRTProtocolMessage) result).getConjugate();
			} else if ((result instanceof UMLRTProtocol) && isConjugate()) {
				result = ((UMLRTProtocol) result).getConjugate();
			}

			return result;
		}

		@Override
		protected void notifyGeneral(F owner, FacadeObject oldObject, FacadeObject newObject) {
			// Notify the subset before the superset
			if (owner.eNotificationRequired()) {
				owner.eNotify(new ENotificationImpl(owner, Notification.SET, UMLRTUMLRTPackage.Literals.PROTOCOL__SUPER_PROTOCOL, oldObject, newObject));
			}
			super.notifyGeneral(owner, oldObject, newObject);
		}

		@Override
		protected List<? extends FacadeObject> getFacadeList(EObject owner, EReference reference, EObject object) {
			List<? extends FacadeObject> result;

			if ((reference == UMLPackage.Literals.INTERFACE__OWNED_OPERATION)
					|| (reference == ExtUMLExtPackage.Literals.INTERFACE__IMPLICIT_OPERATION)) {

				Element interface_ = UMLRTExtensionUtil.getUMLElement(owner);
				RTMessageSet stereo = UMLUtil.getStereotypeApplication(interface_, RTMessageSet.class);
				if (stereo != null) {
					switch (stereo.getRtMsgKind()) {
					case IN:
						result = isConjugate() ? get().outMessages : get().inMessages;
						break;
					case OUT:
						result = isConjugate() ? get().inMessages : get().outMessages;
						break;
					case IN_OUT:
						// Conjugation doesn't matter
						result = get().inOutMessages;
						break;
					default:
						result = null;
						break;
					}
				} else {
					result = null;
				}
			} else {
				result = super.getFacadeList(owner, reference, object);
			}

			return result;
		}

		@Override
		protected void handleObjectAdded(Notification msg, int position, EObject object) {
			if (msg.getFeature() == UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT) {
				if (object instanceof Interface) {
					addAdapter(object);
				}
			} else {
				super.handleObjectAdded(msg, position, object);
			}
		}

		@Override
		protected void handleObjectAdded(Notification msg, int position, FacadeObject object) {
			super.handleObjectAdded(msg, position, object);

			if (object instanceof UMLRTProtocolMessageImpl) {
				((UMLRTProtocolMessageImpl) object).kindChanged();
			}
		}

		@Override
		protected void handleObjectRemoved(Notification msg, int position, EObject object) {
			if (msg.getFeature() == UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT) {
				if (object instanceof Interface) {
					removeAdapter(object);
				}
			} else {
				super.handleObjectRemoved(msg, position, object);
			}
		}

		@Override
		protected InternalFacadeEList<? extends FacadeObject> validateObject(EObject owner, EReference reference, FacadeObject object) {
			InternalFacadeEList<? extends FacadeObject> result = null;

			if ((object instanceof UMLRTProtocolMessage)
					&& ((reference == UMLPackage.Literals.INTERFACE__OWNED_OPERATION)
							|| (reference == ExtUMLExtPackage.Literals.INTERFACE__IMPLICIT_OPERATION))) {

				// Get the message kind from the message-set interface because, in the case
				// of removal of a message, it no longer can determine its own kind
				Element interface_ = UMLRTExtensionUtil.getUMLElement(owner);
				RTMessageSet messageSet = UMLUtil.getStereotypeApplication(interface_, RTMessageSet.class);
				if (messageSet != null) {
					switch (messageSet.getRtMsgKind()) {
					case IN:
						result = isConjugate() ? get().outMessages : get().inMessages;
						break;
					case OUT:
						result = isConjugate() ? get().inMessages : get().outMessages;
						break;
					case IN_OUT:
						// Conjugation doesn't matter
						result = get().inOutMessages;
						break;
					}
				}
			} else {
				result = super.validateObject(owner, reference, object);
			}

			return result;
		}

		@Override
		public void tickle(NamedElement element) {
			if (element instanceof Interface) {
				// If we already have this interface's message list, synchronize it
				discover(element, UMLPackage.Literals.INTERFACE__OWNED_OPERATION);
			} else {
				super.tickle(element);
			}
		}
	}

	InternalFacadeEList<UMLRTProtocolMessage> inMessages;
	InternalFacadeEList<UMLRTProtocolMessage> outMessages;
	InternalFacadeEList<UMLRTProtocolMessage> inOutMessages;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	protected UMLRTProtocolImpl() {
		super();

		this.isConjugate = false;
		this.conjugate = new CachedReference<>(() -> new UMLRTProtocolImpl(this));
	}

	UMLRTProtocolImpl(UMLRTProtocolImpl base) {
		super();

		this.isConjugate = true;
		this.conjugate = () -> base;
		init(base.toUML(), base.toRT());
	}

	/**
	 * Obtains the protocol façade for the base protocol type represented by the given
	 * UML collaboration.
	 *
	 * @param protocol
	 *            a UML collaboration representing a protocol
	 * @return the base UML-RT protocol façade, or {@code null} if the collaboration
	 *         is not actually a protocol
	 */
	public static UMLRTProtocolImpl getInstance(Collaboration protocol) {
		return getFacade(protocol, TYPE);
	}

	static boolean isRTMessageSet(Interface interface_) {
		return getRTMessageSet(interface_) != null;
	}

	static RTMessageSet getRTMessageSet(Interface interface_) {
		return UMLUtil.getStereotypeApplication(interface_, RTMessageSet.class);
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTProtocolImpl> createFacadeAdapter() {
		return new ProtocolAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.PROTOCOL;
	}

	@Override
	public String getName() {
		String result = super.getName();
		if (isConjugate()) {
			result = result + "~"; //$NON-NLS-1$
		}
		return result;
	}

	@Override
	public void setName(String newName) {
		if ((newName != null) && isConjugate()) {
			if (!newName.endsWith("~")) {
				throw new IllegalArgumentException("conjugate name must end with ~"); //$NON-NLS-1$
			}

			// But it isn't in the UML
			newName = newName.substring(0, newName.length() - 1);
		}

		super.setName(newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTClassifier getGeneral() {
		UMLRTProtocol superProtocol = getSuperProtocol();
		if (superProtocol != null) {
			return superProtocol;
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
				cache.put(eResource, this, UMLRTUMLRTPackage.Literals.CLASSIFIER__SPECIFIC, specifics = new DerivedUnionEObjectEList<>(UMLRTClassifier.class, this, UMLRTUMLRTPackage.PROTOCOL__SPECIFIC, SPECIFIC_ESUBSETS));
			}
			return specifics;
		}
		return new DerivedUnionEObjectEList<>(UMLRTClassifier.class, this, UMLRTUMLRTPackage.PROTOCOL__SPECIFIC, SPECIFIC_ESUBSETS);
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
	protected static final int[] SPECIFIC_ESUBSETS = new int[] { UMLRTUMLRTPackage.PROTOCOL__SUB_PROTOCOL };

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
						redefinableElements = new DerivedUnionEObjectEList<>(UMLRTNamedElement.class, this, UMLRTUMLRTPackage.PROTOCOL__REDEFINABLE_ELEMENT, REDEFINABLE_ELEMENT_ESUBSETS));
			}
			return redefinableElements;
		}
		return new DerivedUnionEObjectEList<>(UMLRTNamedElement.class, this, UMLRTUMLRTPackage.PROTOCOL__REDEFINABLE_ELEMENT, REDEFINABLE_ELEMENT_ESUBSETS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTProtocol getSuperProtocol() {
		List<Classifier> generals = toUML().getGenerals();
		return (generals.isEmpty() || !(generals.get(0) instanceof Collaboration))
				? null
				: UMLRTProtocol.getInstance((Collaboration) generals.get(0), isConjugate());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setSuperProtocol(UMLRTProtocol newSuperProtocol) {
		setGeneral(toUML(), (newSuperProtocol == null) ? null : newSuperProtocol.toUML());
	}

	@Override
	public void setGeneral(UMLRTClassifier general) {
		setSuperProtocol((UMLRTProtocol) general);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTProtocol> getSubProtocols() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			@SuppressWarnings("unchecked")
			List<UMLRTProtocol> subProtools = (List<UMLRTProtocol>) cache.get(this, UMLRTUMLRTPackage.Literals.PROTOCOL__SUB_PROTOCOL);
			if (subProtools == null) {
				cache.put(this, UMLRTUMLRTPackage.Literals.PROTOCOL__SUB_PROTOCOL,
						subProtools = doGetSubProtocols());
			}
			return subProtools;
		}
		return doGetSubProtocols();
	}

	private List<UMLRTProtocol> doGetSubProtocols() {
		return specifics(toUML(), Collaboration.class)
				.map(UMLRTProtocolImpl::getInstance)
				.filter(Objects::nonNull)
				.map(this::forConjugation)
				.collect(collectingAndThen(Collectors.<UMLRTProtocol> toList(),
						list -> elist(UMLRTUMLRTPackage.Literals.PROTOCOL__SUB_PROTOCOL, list)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocol getSubProtocol(String name) {
		return getSubProtocol(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocol getSubProtocol(String name, boolean ignoreCase) {
		subProtocolLoop: for (UMLRTProtocol subProtocol : getSubProtocols()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(subProtocol.getName()) : name.equals(subProtocol.getName()))) {
				continue subProtocolLoop;
			}
			return subProtocol;
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
	public List<UMLRTProtocolMessage> getInMessages() {
		if (inMessages == null) {
			inMessages = (InternalFacadeEList<UMLRTProtocolMessage>) getFacades(
					MESSAGE_REFERENCES.get(forConjugation(RTMessageKind.IN)), messageConjugation());
		}
		return inMessages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocolMessage getInMessage(String name) {
		return getInMessage(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocolMessage getInMessage(String name, boolean ignoreCase) {
		inMessageLoop: for (UMLRTProtocolMessage inMessage : getInMessages()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(inMessage.getName()) : name.equals(inMessage.getName()))) {
				continue inMessageLoop;
			}
			return inMessage;
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
	public List<UMLRTProtocolMessage> getOutMessages() {
		if (outMessages == null) {
			outMessages = (InternalFacadeEList<UMLRTProtocolMessage>) getFacades(
					MESSAGE_REFERENCES.get(forConjugation(RTMessageKind.OUT)), messageConjugation());
		}
		return outMessages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocolMessage getOutMessage(String name) {
		return getOutMessage(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocolMessage getOutMessage(String name, boolean ignoreCase) {
		outMessageLoop: for (UMLRTProtocolMessage outMessage : getOutMessages()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(outMessage.getName()) : name.equals(outMessage.getName()))) {
				continue outMessageLoop;
			}
			return outMessage;
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
	public List<UMLRTProtocolMessage> getInOutMessages() {
		if (inOutMessages == null) {
			inOutMessages = (InternalFacadeEList<UMLRTProtocolMessage>) getFacades(
					MESSAGE_REFERENCES.get(forConjugation(RTMessageKind.IN_OUT)), messageConjugation());
		}
		return inOutMessages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocolMessage getInOutMessage(String name) {
		return getInOutMessage(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocolMessage getInOutMessage(String name, boolean ignoreCase) {
		inOutMessageLoop: for (UMLRTProtocolMessage inOutMessage : getInOutMessages()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(inOutMessage.getName()) : name.equals(inOutMessage.getName()))) {
				continue inOutMessageLoop;
			}
			return inOutMessage;
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
	public List<UMLRTProtocolMessage> getMessages() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			List<UMLRTProtocolMessage> messages = (List<UMLRTProtocolMessage>) cache.get(eResource, this, UMLRTUMLRTPackage.Literals.PROTOCOL__MESSAGE);
			if (messages == null) {
				cache.put(eResource, this, UMLRTUMLRTPackage.Literals.PROTOCOL__MESSAGE, messages = new DerivedUnionEObjectEList<>(UMLRTProtocolMessage.class, this, UMLRTUMLRTPackage.PROTOCOL__MESSAGE, MESSAGE_ESUBSETS));
			}
			return messages;
		}
		return new DerivedUnionEObjectEList<>(UMLRTProtocolMessage.class, this, UMLRTUMLRTPackage.PROTOCOL__MESSAGE, MESSAGE_ESUBSETS);
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getMessages() <em>Message</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMessages()
	 * @generated
	 * @ordered
	 */
	protected static final int[] MESSAGE_ESUBSETS = new int[] { UMLRTUMLRTPackage.PROTOCOL__IN_MESSAGE, UMLRTUMLRTPackage.PROTOCOL__OUT_MESSAGE, UMLRTUMLRTPackage.PROTOCOL__IN_OUT_MESSAGE };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocolMessage getMessage(String name) {
		return getMessage(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocolMessage getMessage(String name, boolean ignoreCase) {
		messageLoop: for (UMLRTProtocolMessage message : getMessages()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(message.getName()) : name.equals(message.getName()))) {
				continue messageLoop;
			}
			return message;
		}
		return null;
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
	protected static final int[] REDEFINABLE_ELEMENT_ESUBSETS = new int[] { UMLRTUMLRTPackage.PROTOCOL__MESSAGE };

	UMLRTProtocol forConjugation(UMLRTProtocol other) {
		return ((other != null) && (other != this) && isConjugate())
				? other.getConjugate()
				: other;
	}

	Stream<Interface> messageSets() {
		Package container = toUML().getNearestPackage();
		return (container == null)
				? Stream.empty()
				: container.getOwnedTypes().stream()
						.filter(Interface.class::isInstance).map(Interface.class::cast)
						.filter(UMLRTProtocolImpl::isRTMessageSet);
	}

	Optional<Interface> getMessageSet(RTMessageKind kind) {
		return messageSets().map(UMLRTProtocolImpl::getRTMessageSet)
				.filter(set -> set.getRtMsgKind() == kind)
				.findFirst()
				.map(RTMessageSet::getBase_Interface);
	}

	Interface requireMessageSet(RTMessageKind kind) {
		return getMessageSet(kind)
				.orElseThrow(() -> new IllegalArgumentException("no such message set: " + kind)); //$NON-NLS-1$
	}

	static Interface requireMessageSet(Collaboration protocol, RTMessageKind kind) {
		return UMLRTProtocolImpl.getInstance(protocol).getMessageSet(kind)
				.orElseThrow(() -> new IllegalArgumentException("no such message set: " + kind)); //$NON-NLS-1$
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
	public UMLRTProtocol getConjugate() {
		return conjugate.get();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTPackage getPackage() {
		Package protocolContainer = toUML().getNearestPackage();
		return (protocolContainer == null)
				? null
				: UMLRTPackage.getInstance(protocolContainer.getNestingPackage());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Collaboration toUML() {
		return (Collaboration) super.toUML();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public AnyReceiveEvent getAnyReceiveEvent() {
		AnyReceiveEvent result = null;

		Package protocolContainer = toUML().getNearestPackage();
		if (protocolContainer != null) {
			result = (AnyReceiveEvent) protocolContainer.getPackagedElement(
					ANY_RECEIVE_EVENT_NAME, false, UMLPackage.Literals.ANY_RECEIVE_EVENT, false);
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
	public List<UMLRTProtocol> getAncestry() {
		// TODO: Cache this
		return doGetAncestry();
	}

	private List<UMLRTProtocol> doGetAncestry() {
		return ancestry(toUML(), Collaboration.class).stream()
				.map(UMLRTProtocolImpl::getInstance)
				.filter(Objects::nonNull)
				.map(this::forConjugation)
				.collect(collectingAndThen(toList(), ECollections::unmodifiableEList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTProtocolMessage> getMessages(RTMessageKind kind) {
		switch (kind) {
		case IN:
			return getInMessages();
		case OUT:
			return getOutMessages();
		case IN_OUT:
			return getInOutMessages();
		default:
			throw new IllegalArgumentException(kind.getName());
		}
	}

	UnaryOperator<UMLRTProtocolMessage> messageConjugation() {
		return isConjugate()
				? UMLRTProtocolMessage::getConjugate
				: UnaryOperator.identity();
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
	public List<UMLRTProtocolMessage> getMessages(RTMessageKind kind, boolean withExclusions) {
		// TODO: Cache in the UML CacheAdapter?
		return getFacades(MESSAGE_REFERENCES.get(forConjugation(kind)), withExclusions, messageConjugation());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTProtocolMessage> getMessages(boolean withExclusions) {
		// TODO: Cache in the UML CacheAdapter
		return MESSAGE_REFERENCES.values().stream()
				.flatMap(ref -> ref.facades(toUML(), withExclusions))
				.map(messageConjugation())
				.collect(collectingAndThen(toList(), Collections::unmodifiableList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTProtocolMessage createMessage(RTMessageKind kind, String name) {
		UMLRTProtocolMessageImpl result = (UMLRTProtocolMessageImpl) create(MESSAGE_REFERENCES.get(forConjugation(kind)), name);
		result.initReceiveEvent();
		return messageConjugation().apply(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTProtocolMessage createMessage(RTMessageKind kind, String name, Type dataType) {
		UMLRTProtocolMessage result = createMessage(kind, name);
		result.toUML().createOwnedParameter("data", dataType);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTProtocolMessage createMessage(RTMessageKind kind, String name, List<String> parameterName, List<Type> parameterType) {
		UMLRTProtocolMessage result = createMessage(kind, name);

		if ((parameterName != null) && (parameterType != null)) {
			int count = parameterName.size();
			if (parameterType.size() != count) {
				throw new IllegalArgumentException("parameter names and types mismatched"); //$NON-NLS-1$
			}
			for (int i = 0; i < count; i++) {
				result.toUML().createOwnedParameter(parameterName.get(i), parameterType.get(i));
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
	public Stream<UMLRTProtocol> getHierarchy() {
		return hierarchy(toUML(), Collaboration.class)
				.map(UMLRTProtocol::getInstance)
				.filter(Objects::nonNull)
				.map(this::forConjugation);
	}

	@Override
	protected Stream<? extends UMLRTNamedElement> excludedElements() {
		Stream<UMLRTNamedElement> result = Stream.empty();

		Collaboration uml = toUML();

		if (uml instanceof InternalUMLRTElement) {
			result = messageSets().flatMap(set -> {
				@SuppressWarnings("unchecked")
				List<NamedElement> exclusions = (List<NamedElement>) set.eGet(ExtUMLExtPackage.Literals.NAMESPACE__EXCLUDED_MEMBER);
				return exclusions.stream()
						.map(UMLRTFactory::create)
						.filter(Objects::nonNull);
			});
		}

		return result;
	}

	UMLRTProtocol initContents() {
		// Message set interfaces
		createMessageSet(RTMessageKind.IN);
		createMessageSet(RTMessageKind.OUT);
		createMessageSet(RTMessageKind.IN_OUT);

		// Wildcard event
		toUML().getNearestPackage().createPackagedElement(
				ANY_RECEIVE_EVENT_NAME, UMLPackage.Literals.ANY_RECEIVE_EVENT);

		return this;
	}

	private void createMessageSet(RTMessageKind direction) {
		Package protocolContainer = toUML().getNearestPackage();

		String name = getName();
		switch (direction) {
		case OUT:
			name = name + "~"; //$NON-NLS-1$
			break;
		case IN_OUT:
			name = name + "IO"; //$NON-NLS-1$
			break;
		default: // IN
			// Pass
			break;

		}

		Interface messageSet = (Interface) protocolContainer.createOwnedType(name, UMLPackage.Literals.INTERFACE);
		RTMessageSet stereo = (RTMessageSet) StereotypeApplicationHelper.getInstance(messageSet).applyStereotype(messageSet, UMLRealTimePackage.Literals.RT_MESSAGE_SET);
		stereo.setRtMsgKind(direction);

		switch (direction) {
		case IN:
			toUML().createInterfaceRealization(null, messageSet);
			break;
		case OUT:
			toUML().createUsage(messageSet);
			break;
		case IN_OUT:
			toUML().createInterfaceRealization(null, messageSet);
			toUML().createUsage(messageSet);
			break;
		}
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
		case UMLRTUMLRTPackage.PROTOCOL__MESSAGE:
			return getMessages();
		case UMLRTUMLRTPackage.PROTOCOL__PACKAGE:
			return getPackage();
		case UMLRTUMLRTPackage.PROTOCOL__SUPER_PROTOCOL:
			return getSuperProtocol();
		case UMLRTUMLRTPackage.PROTOCOL__SUB_PROTOCOL:
			return getSubProtocols();
		case UMLRTUMLRTPackage.PROTOCOL__IN_MESSAGE:
			return getInMessages();
		case UMLRTUMLRTPackage.PROTOCOL__OUT_MESSAGE:
			return getOutMessages();
		case UMLRTUMLRTPackage.PROTOCOL__IN_OUT_MESSAGE:
			return getInOutMessages();
		case UMLRTUMLRTPackage.PROTOCOL__IS_CONJUGATE:
			return isConjugate();
		case UMLRTUMLRTPackage.PROTOCOL__CONJUGATE:
			return getConjugate();
		case UMLRTUMLRTPackage.PROTOCOL__HIERARCHY:
			return getHierarchy();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	protected Object facadeGetAll(int referenceID) {
		switch (referenceID) {
		case UMLRTUMLRTPackage.PROTOCOL__MESSAGE:
			return new FacadeObjectEList<>(this, UMLRTProtocolMessage.class, referenceID,
					MESSAGE_REFERENCES.values().stream()
							.flatMap(r -> r.facades(toUML(), true))
							.collect(Collectors.toList()));
		case UMLRTUMLRTPackage.PROTOCOL__IN_MESSAGE:
			return getFacades(MESSAGE_REFERENCES.get(RTMessageKind.IN), true);
		case UMLRTUMLRTPackage.PROTOCOL__OUT_MESSAGE:
			return getFacades(MESSAGE_REFERENCES.get(RTMessageKind.OUT), true);
		case UMLRTUMLRTPackage.PROTOCOL__IN_OUT_MESSAGE:
			return getFacades(MESSAGE_REFERENCES.get(RTMessageKind.IN_OUT), true);
		case UMLRTUMLRTPackage.PROTOCOL__REDEFINABLE_ELEMENT:
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
		case UMLRTUMLRTPackage.PROTOCOL__SUPER_PROTOCOL:
			setSuperProtocol((UMLRTProtocol) newValue);
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
		case UMLRTUMLRTPackage.PROTOCOL__SUPER_PROTOCOL:
			setSuperProtocol((UMLRTProtocol) null);
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
		case UMLRTUMLRTPackage.PROTOCOL__GENERAL:
			return isSetGeneral();
		case UMLRTUMLRTPackage.PROTOCOL__SPECIFIC:
			return isSetSpecifics();
		case UMLRTUMLRTPackage.PROTOCOL__MESSAGE:
			return isSetMessages();
		case UMLRTUMLRTPackage.PROTOCOL__REDEFINABLE_ELEMENT:
			return isSetRedefinableElements();
		case UMLRTUMLRTPackage.PROTOCOL__PACKAGE:
			return getPackage() != null;
		case UMLRTUMLRTPackage.PROTOCOL__SUPER_PROTOCOL:
			return getSuperProtocol() != null;
		case UMLRTUMLRTPackage.PROTOCOL__SUB_PROTOCOL:
			return !getSubProtocols().isEmpty();
		case UMLRTUMLRTPackage.PROTOCOL__IN_MESSAGE:
			return !getInMessages().isEmpty();
		case UMLRTUMLRTPackage.PROTOCOL__OUT_MESSAGE:
			return !getOutMessages().isEmpty();
		case UMLRTUMLRTPackage.PROTOCOL__IN_OUT_MESSAGE:
			return !getInOutMessages().isEmpty();
		case UMLRTUMLRTPackage.PROTOCOL__IS_CONJUGATE:
			return isConjugate() != IS_CONJUGATE_EDEFAULT;
		case UMLRTUMLRTPackage.PROTOCOL__CONJUGATE:
			return getConjugate() != null;
		case UMLRTUMLRTPackage.PROTOCOL__HIERARCHY:
			return getHierarchy() != null;
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
				|| eIsSet(UMLRTUMLRTPackage.PROTOCOL__SUPER_PROTOCOL);
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
				|| eIsSet(UMLRTUMLRTPackage.PROTOCOL__SUB_PROTOCOL);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean isSetMessages() {
		return eIsSet(UMLRTUMLRTPackage.PROTOCOL__IN_MESSAGE)
				|| eIsSet(UMLRTUMLRTPackage.PROTOCOL__OUT_MESSAGE)
				|| eIsSet(UMLRTUMLRTPackage.PROTOCOL__IN_OUT_MESSAGE);
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
				|| isSetMessages();
	}

} // UMLRTProtocolImpl
