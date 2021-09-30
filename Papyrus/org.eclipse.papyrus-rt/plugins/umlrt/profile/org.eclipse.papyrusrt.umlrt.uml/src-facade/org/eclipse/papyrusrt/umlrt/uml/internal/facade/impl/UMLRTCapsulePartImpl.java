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
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Capsule Part</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsulePartImpl#getRedefinedElement <em>Redefined Element</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsulePartImpl#getRedefinitionContext <em>Redefinition Context</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsulePartImpl#getKind <em>Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsulePartImpl#isOptional <em>Optional</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsulePartImpl#getRedefinedPart <em>Redefined Part</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsulePartImpl#getCapsule <em>Capsule</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTCapsulePartImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTCapsulePartImpl extends UMLRTReplicatedElementImpl implements UMLRTCapsulePart {
	static final FacadeType<Property, CapsulePart, UMLRTCapsulePartImpl> TYPE = new FacadeType<>(
			UMLRTCapsulePartImpl.class,
			UMLPackage.Literals.PROPERTY,
			UMLRealTimePackage.Literals.CAPSULE_PART,
			UMLRTCapsulePartImpl::getInstance,
			capsulePart -> (UMLRTCapsulePartImpl) capsulePart.getRedefinedPart(),
			UMLRTCapsulePartImpl::new);

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final UMLRTCapsulePartKind KIND_EDEFAULT = UMLRTCapsulePartKind.FIXED;

	/**
	 * The default value of the '{@link #isOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isOptional()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OPTIONAL_EDEFAULT = false;

	protected static class CapsulePartAdapter<F extends UMLRTCapsulePartImpl> extends ReplicationAdapter<F> {
		CapsulePartAdapter(F facade) {
			super(facade);
		}

		@Override
		protected void handleObjectReplaced(Notification msg, int position, FacadeObject oldObject, FacadeObject newObject) {
			if (msg.getFeature() == UMLPackage.Literals.TYPED_ELEMENT__TYPE) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), msg.getEventType(), UMLRTUMLRTPackage.Literals.CAPSULE_PART__TYPE, oldObject, newObject));
				}
			} else {
				super.handleObjectReplaced(msg, position, oldObject, newObject);
			}
		}

		@Override
		protected void handleLowerBoundChanged(Object oldValue, Object newValue) {
			super.handleLowerBoundChanged(oldValue, newValue);

			int oldInt = asIntegerBound(oldValue);
			int newInt = asIntegerBound(newValue);

			if ((oldInt != newInt) && get().eNotificationRequired()) {
				UMLRTCapsulePartImpl part = get();
				Property uml = part.toUML();

				if (uml.getAggregation() == AggregationKind.COMPOSITE_LITERAL) {
					part.eNotify(new ENotificationImpl(part, Notification.SET, UMLRTUMLRTPackage.Literals.CAPSULE_PART__OPTIONAL,
							oldInt == 0, newInt == 0));
				}

				// Infer what the kind previously was
				UMLRTCapsulePartKind kind = part.getKind();
				part.eNotify(new ENotificationImpl(part, Notification.SET, UMLRTUMLRTPackage.Literals.CAPSULE_PART__KIND,
						UMLRTCapsulePartKind.get(oldInt, uml.getUpper(), uml.getAggregation()), kind));
			}
		}

		@Override
		protected void handleUpperBoundChanged(Object oldValue, Object newValue) {
			super.handleUpperBoundChanged(oldValue, newValue);

			int oldInt = asIntegerBound(oldValue);
			int newInt = asIntegerBound(newValue);

			if ((oldInt != newInt) && get().eNotificationRequired()) {
				UMLRTCapsulePartImpl part = get();
				Property uml = part.toUML();

				// Infer what the kind previously was
				UMLRTCapsulePartKind kind = part.getKind();
				part.eNotify(new ENotificationImpl(part, Notification.SET, UMLRTUMLRTPackage.Literals.CAPSULE_PART__KIND,
						UMLRTCapsulePartKind.get(uml.getLower(), oldInt, uml.getAggregation()), kind));
			}
		}

		@Override
		protected void handleValueReplaced(Notification msg, int position, Object oldValue, Object newValue) {
			if (msg.getFeature() == UMLPackage.Literals.PROPERTY__AGGREGATION) {
				if (get().eNotificationRequired()) {
					UMLRTCapsulePartImpl part = get();
					Property uml = part.toUML();

					// Infer what the kind previously was
					UMLRTCapsulePartKind kind = part.getKind();
					part.eNotify(new ENotificationImpl(part, Notification.SET, UMLRTUMLRTPackage.Literals.CAPSULE_PART__KIND,
							UMLRTCapsulePartKind.get(uml.getLower(), uml.getUpper(), (AggregationKind) oldValue), kind));
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
	protected UMLRTCapsulePartImpl() {
		super();
	}

	/**
	 * Obtains the canonical instance of the capsule-part façade for a property.
	 *
	 * @param part
	 *            a property in the UML model
	 *
	 * @return its capsule-part façade, or {@code null} if the property is not a valid capsule-part
	 */
	public static UMLRTCapsulePartImpl getInstance(Property part) {
		return getFacade(part, TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.CAPSULE_PART;
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTCapsulePartImpl> createFacadeAdapter() {
		return new CapsulePartAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinedElement() {
		UMLRTCapsulePart redefinedPart = getRedefinedPart();
		if (redefinedPart != null) {
			return redefinedPart;
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
	public UMLRTCapsulePartKind getKind() {
		Property uml = toUML();
		return UMLRTCapsulePartKind.get(uml.getLower(), uml.getUpper(), uml.getAggregation());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setKind(UMLRTCapsulePartKind newKind) {
		if (Objects.requireNonNull(newKind, "newKind").isNull()) { //$NON-NLS-1$
			throw new IllegalArgumentException(newKind.getName());
		}

		setOptional(!newKind.isRequired());
		if ((toUML().getAggregation() == AggregationKind.COMPOSITE_LITERAL) != newKind.isComposite()) {
			if (newKind.isComposite()) {
				toUML().setAggregation(AggregationKind.COMPOSITE_LITERAL);
			} else {
				toUML().setAggregation(AggregationKind.SHARED_LITERAL);
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
	public boolean isOptional() {
		return toUML().getLower() == 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setOptional(boolean newOptional) {
		if (newOptional != isOptional()) {
			if (newOptional) {
				toUML().setLower(0);
			} else {
				Property property = toUML();
				ValueSpecification upper = toUML().getUpperValue();
				if (upper == null) {
					// Same for the lower, then
					ValueSpecification lower = property.getLowerValue();
					if (lower != null) {
						lower.destroy();
					}
				} else {
					// Copy the upper
					new UMLSwitch<ValueSpecification>() {
						@Override
						public ValueSpecification caseLiteralUnlimitedNatural(LiteralUnlimitedNatural object) {
							// Copy the value, but as a literal integer, of course
							property.setLower(object.getValue());
							return property.getLowerValue();
						}

						@Override
						public ValueSpecification caseValueSpecification(ValueSpecification object) {
							ValueSpecification result = property.createLowerValue(null, null, object.eClass());
							new EcoreUtil.Copier() {
								private static final long serialVersionUID = 1L;

								@Override
								protected EObject createCopy(EObject eObject) {
									return (eObject == object) ? result : super.createCopy(eObject);
								};
							}.copy(object);
							return result;
						}
					}.doSwitch(upper);
				}

				if (toUML().getAggregation() != AggregationKind.COMPOSITE_LITERAL) {
					toUML().setAggregation(AggregationKind.COMPOSITE_LITERAL);
				}
			}
		}
	}

	@Override
	public void setReplicationFactor(int newReplicationFactor) {
		if (isSymbolicReplicationFactor() || (getReplicationFactor() != newReplicationFactor)) {
			Property property = toUML();

			if (!isOptional()) {
				property.setLower(newReplicationFactor);
			}
			property.setUpper(newReplicationFactor);
		}
	}

	@Override
	public void setReplicationFactorAsString(String newReplicationFactorAsString) {
		if (!Objects.equals(newReplicationFactorAsString, getReplicationFactorAsString())) {
			try {
				// Maybe it is an integer value, actually
				int asInt = Integer.parseInt(newReplicationFactorAsString);
				setReplicationFactor(asInt);
			} catch (Exception e) {
				// Okay, no, it is a symbolic
				Property property = toUML();

				if (!isOptional()) {
					setReplicationFactor(property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE, newReplicationFactorAsString);
				}
				setReplicationFactor(property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, newReplicationFactorAsString);
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
	public UMLRTCapsulePart getRedefinedPart() {
		UMLRTCapsulePart result;

		if (toUML() instanceof InternalUMLRTElement) {
			Property superPart = (Property) ((InternalUMLRTElement) toUML()).rtGetRedefinedElement();
			result = UMLRTCapsulePart.getInstance(superPart);
		} else {
			UMLRTNamedElement redef = super.getRedefinedElement();
			result = (redef instanceof UMLRTCapsulePart) ? (UMLRTCapsulePart) redef : null;
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
	public UMLRTCapsule getType() {
		Type result = toUML().getType();
		return (result instanceof Class) ? UMLRTCapsule.getInstance((Class) result)
				: null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setType(UMLRTCapsule newType) {
		if (newType == null) {
			toUML().setType(null);
		} else {
			toUML().setType(newType.toUML());
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTConnector> getConnectorsOf(UMLRTPort port) {
		List<UMLRTConnector> result;

		UMLRTCapsule capsule = getCapsule();
		if (capsule == null) {
			// Deleted capsule-parts have no connectors
			result = Collections.emptyList();
		} else {
			Predicate<UMLRTConnector> excluded = UMLRTConnector::isExcluded;

			// Get the connectors that are defined within my capsule
			result = allConnectorsOf((UMLRTPort) port.getRootDefinition())
					.map(capsule::getRedefinitionOf)
					.filter(Objects::nonNull)
					.filter(excluded.negate())
					.distinct()
					.collect(collectingAndThen(toList(), Collections::unmodifiableList));
		}

		return result;
	}

	Stream<UMLRTConnector> allConnectorsOf(UMLRTPort portOnPart) {
		CacheAdapter cache = CacheAdapter.getCacheAdapter(toUML());

		return getRedefinitionChain().stream()
				.map(UMLRTNamedElement::toUML)
				.flatMap(e -> cache.getNonNavigableInverseReferences(e).stream())
				.filter(s -> s.getEStructuralFeature() == UMLPackage.Literals.CONNECTOR_END__PART_WITH_PORT)
				.map(EStructuralFeature.Setting::getEObject)
				.map(ConnectorEnd.class::cast)
				.filter(end -> redefines(end.getRole(), portOnPart))
				.map(Element::getOwner)
				.map(Connector.class::cast)
				.map(UMLRTConnector::getInstance)
				.filter(Objects::nonNull);
	}

	private static boolean redefines(ConnectableElement role, UMLRTPort port) {
		UMLRTPort rtRole = (role instanceof Port) ? UMLRTPort.getInstance((Port) role) : null;
		return (rtRole != null) && rtRole.redefines(port);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Stream<? extends UMLRTCapsulePart> allRedefinitions() {
		Predicate<UMLRTCapsulePart> excluded = UMLRTCapsulePart::isExcluded;
		UMLRTCapsule capsule = getCapsule();
		return (capsule == null) ? Stream.empty() : capsule.getHierarchy()
				.map(c -> c.getRedefinitionOf(this))
				.filter(excluded.negate());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTConnector> getConnectorsOfPorts() {
		List<UMLRTConnector> result;

		UMLRTCapsule capsule = getCapsule();
		if (capsule == null) {
			// Deleted capsule-parts have no connectors
			result = Collections.emptyList();
		} else {
			Predicate<UMLRTConnector> excluded = UMLRTConnector::isExcluded;

			// Get the connectors that are defined within my capsule
			result = allConnectorsOfPorts()
					.map(capsule::getRedefinitionOf)
					.filter(Objects::nonNull)
					.filter(excluded.negate())
					.distinct()
					.collect(collectingAndThen(toList(), Collections::unmodifiableList));
		}

		return result;
	}

	Stream<UMLRTConnector> allConnectorsOfPorts() {
		return getRedefinitionChain().stream()
				.map(UMLRTCapsulePartImpl.class::cast)
				.flatMap(UMLRTCapsulePartImpl::getEnds)
				.map(Element::getOwner)
				.map(Connector.class::cast)
				.map(UMLRTConnector::getInstance)
				.filter(Objects::nonNull);
	}

	Stream<ConnectorEnd> getEnds() {
		CacheAdapter cache = CacheAdapter.getCacheAdapter(toUML());
		return cache.getNonNavigableInverseReferences(toUML()).stream()
				.filter(s -> s.getEStructuralFeature() == UMLPackage.Literals.CONNECTOR_END__PART_WITH_PORT)
				.map(EStructuralFeature.Setting::getEObject)
				.map(ConnectorEnd.class::cast);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTCapsule getCapsule() {
		Class class_ = toUML().getClass_();
		return (class_ == null) ? null : UMLRTCapsule.getInstance(class_);
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
		case UMLRTUMLRTPackage.CAPSULE_PART__KIND:
			return getKind();
		case UMLRTUMLRTPackage.CAPSULE_PART__OPTIONAL:
			return isOptional();
		case UMLRTUMLRTPackage.CAPSULE_PART__REDEFINED_PART:
			return getRedefinedPart();
		case UMLRTUMLRTPackage.CAPSULE_PART__CAPSULE:
			return getCapsule();
		case UMLRTUMLRTPackage.CAPSULE_PART__TYPE:
			return getType();
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
		case UMLRTUMLRTPackage.CAPSULE_PART__KIND:
			setKind((UMLRTCapsulePartKind) newValue);
			return;
		case UMLRTUMLRTPackage.CAPSULE_PART__OPTIONAL:
			setOptional((Boolean) newValue);
			return;
		case UMLRTUMLRTPackage.CAPSULE_PART__TYPE:
			setType((UMLRTCapsule) newValue);
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
		case UMLRTUMLRTPackage.CAPSULE_PART__KIND:
			setKind(KIND_EDEFAULT);
			return;
		case UMLRTUMLRTPackage.CAPSULE_PART__OPTIONAL:
			setOptional(OPTIONAL_EDEFAULT);
			return;
		case UMLRTUMLRTPackage.CAPSULE_PART__TYPE:
			setType((UMLRTCapsule) null);
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
		case UMLRTUMLRTPackage.CAPSULE_PART__REDEFINED_ELEMENT:
			return isSetRedefinedElement();
		case UMLRTUMLRTPackage.CAPSULE_PART__REDEFINITION_CONTEXT:
			return isSetRedefinitionContext();
		case UMLRTUMLRTPackage.CAPSULE_PART__KIND:
			return getKind() != KIND_EDEFAULT;
		case UMLRTUMLRTPackage.CAPSULE_PART__OPTIONAL:
			return isOptional() != OPTIONAL_EDEFAULT;
		case UMLRTUMLRTPackage.CAPSULE_PART__REDEFINED_PART:
			return getRedefinedPart() != null;
		case UMLRTUMLRTPackage.CAPSULE_PART__CAPSULE:
			return getCapsule() != null;
		case UMLRTUMLRTPackage.CAPSULE_PART__TYPE:
			return getType() != null;
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
				|| eIsSet(UMLRTUMLRTPackage.CAPSULE_PART__REDEFINED_PART);
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
				|| eIsSet(UMLRTUMLRTPackage.CAPSULE_PART__CAPSULE);
	}

} // UMLRTCapsulePartImpl
