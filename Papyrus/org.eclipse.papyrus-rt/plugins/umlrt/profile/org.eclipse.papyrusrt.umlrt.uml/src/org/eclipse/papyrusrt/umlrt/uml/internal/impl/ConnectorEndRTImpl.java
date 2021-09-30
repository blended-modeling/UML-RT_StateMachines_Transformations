/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml.internal.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.MultiplicityElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.NotificationForwarder;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * UML-RT semantics for {@link ConnectorEnd}.
 */
public class ConnectorEndRTImpl extends org.eclipse.uml2.uml.internal.impl.ConnectorEndImpl implements InternalUMLRTMultiplicityElement {

	private static final int ROLE__SET_FLAG = 0x1 << 0;
	private static final int PART_WITH_PORT__SET_FLAG = 0x1 << 1;

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.CONNECTOR_END__ROLE,
			UMLPackage.Literals.CONNECTOR_END__PART_WITH_PORT
	/* Don't include multiplicity bounds because they forward for themselves */));

	private int uFlags = 0x0;

	public ConnectorEndRTImpl() {
		super();
	}

	@Override
	public EObject create(EClass eClass) {
		EObject result;

		if (eClass.getEPackage() == eClass().getEPackage()) {
			result = UMLRTUMLFactoryImpl.eINSTANCE.create(eClass);
		} else {
			result = super.create(eClass);
		}

		return result;
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLPackage.CONNECTOR_END__ROLE:
			return isSetRole();
		case UMLPackage.CONNECTOR_END__PART_WITH_PORT:
			return isSetPartWithPort();
		case UMLPackage.CONNECTOR_END__LOWER_VALUE:
			return isSetLowerValue();
		case UMLPackage.CONNECTOR_END__UPPER_VALUE:
			return isSetUpperValue();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.CONNECTOR_END__ROLE:
			return (T) super.getRole();
		case UMLPackage.CONNECTOR_END__PART_WITH_PORT:
			return (T) super.getPartWithPort();
		case UMLPackage.CONNECTOR_END__LOWER_VALUE:
			return (T) super.getLowerValue();
		case UMLPackage.CONNECTOR_END__UPPER_VALUE:
			return (T) super.getUpperValue();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.CONNECTOR_END__ROLE:
			unsetRole();
			break;
		case UMLPackage.CONNECTOR_END__PART_WITH_PORT:
			unsetPartWithPort();
			break;
		case UMLPackage.CONNECTOR_END__LOWER_VALUE:
			unsetLowerValue();
			break;
		case UMLPackage.CONNECTOR_END__UPPER_VALUE:
			unsetUpperValue();
			break;
		default:
			super.eUnset(featureID);
			break;
		}
	}

	@Override
	public EObject eContainer() {
		Element owner = rtOwner();
		return (owner != null) ? owner : super.eContainer();
	}

	@Override
	public Resource eResource() {
		Resource result = rtResource();

		if (result instanceof ExtensionResource) {
			EObject container = eContainer();
			if (container != null) {
				result = container.eResource();
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R extends InternalUMLRTElement> R rtGetRedefinedElement() {
		R result = null;

		Connector connector = (Connector) getOwner();
		if (connector instanceof InternalUMLRTElement) {
			Connector redefined = ((InternalUMLRTElement) connector).rtGetRedefinedElement();
			if (redefined != null) {
				int index = connector.getEnds().indexOf(this);
				if ((index >= 0) && (index < redefined.getEnds().size())) {
					ConnectorEnd end = redefined.getEnds().get(index);
					if (end instanceof InternalUMLRTElement) {
						result = (R) end;
					}
				}
			}
		}

		return result;
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	@Override
	public Function<? super EObject, ? extends EObject> rtGetInheritanceResolver(EReference reference) {
		return (reference == UMLPackage.Literals.CONNECTOR_END__ROLE)
				? this::resolveRole
				: (reference == UMLPackage.Literals.CONNECTOR_END__PART_WITH_PORT)
						? this::resolvePartWithPort
						: null;
	}

	/**
	 * Resolve the redefinition of an {@code inherited} role in my redefinition context
	 * or in the context of the connected part-with-port.
	 * 
	 * @param inherited
	 *            an inherited role
	 * @return the resolved redefinition, or the same {@code inherited} role if it is not
	 *         a member of my redefinition context
	 */
	EObject resolveRole(EObject inherited) {
		return resolveConnectable(inherited, true);
	}

	/**
	 * Resolve the redefinition of an {@code inherited} role in my redefinition context
	 * or in the context of the connected part-with-port.
	 * 
	 * @param inherited
	 *            an inherited connectable element
	 * @param isRole
	 *            whether the connectable to be resolved is the role (otherwise it is
	 *            the part-with-port)
	 * @return the resolved redefinition, or the same {@code inherited} role if it is not
	 *         a member of my redefinition context
	 */
	EObject resolveConnectable(EObject inherited, boolean isRole) {
		EObject result = inherited;

		if (inherited instanceof ConnectableElement) {
			ConnectableElement inheritedConnectable = (ConnectableElement) inherited;
			Element owner = getOwner();
			if (owner instanceof Connector) {
				// The part-with-port, of course, is only resolvable in the context
				// my owning classifier
				Property part = isRole ? getPartWithPort() : null;
				Classifier redefinitionContext = (part == null)
						? (Classifier) owner.getOwner()
						: (Classifier) part.getType();

				if ((redefinitionContext != null) && (inheritedConnectable.getOwner() != redefinitionContext)) {
					Optional<EObject> resolved = redefinitionContext.getMembers().stream()
							.filter(InternalUMLRTElement.class::isInstance).map(InternalUMLRTElement.class::cast)
							.filter(e -> (e == inheritedConnectable) || (e.rtGetRedefinedElement() == inheritedConnectable))
							.findAny()
							.map(EObject.class::cast);
					result = resolved.orElse(inherited);
				}
			}
		}

		return result;
	}

	/**
	 * Resolve the redefinition of an {@code inherited} part-with-port in my redefinition context.
	 * 
	 * @param inherited
	 *            an inherited part-with-port
	 * @return the resolved redefinition, or the same {@code inherited} part if it is not
	 *         a member of my redefinition context
	 */
	EObject resolvePartWithPort(EObject inherited) {
		return resolveConnectable(inherited, false);
	}

	/**
	 * Unresolve a {@code redefinition} of an inherited port in my redefinition context or
	 * in the context of the connected part-with-port.
	 * 
	 * @param redefinition
	 *            an possible redefinition (it could also be a local definition, which trivially resolves to itself)
	 * @return the inherited definition, or the same {@code redefinition} port if it is not
	 *         a member of the appropriate redefinition context or if it is not actually a redefinition
	 */
	<T extends ConnectableElement> T unresolveConnectable(T redefinition) {
		T result = redefinition;

		if (redefinition instanceof InternalUMLRTElement) {
			InternalUMLRTElement internal = (InternalUMLRTElement) redefinition;

			// Chase up to the first non-virtual definition
			while ((internal != null) && internal.rtIsVirtual()) {
				internal = internal.rtGetRedefinedElement();
			}

			if (redefinition.eClass().isInstance(internal)) {
				@SuppressWarnings("unchecked")
				T resultAsT = (T) internal;
				result = resultAsT;
			}
		}

		return result;
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof ConnectorEnd)) {
			throw new IllegalArgumentException("not a connector end: " + redefined); //$NON-NLS-1$
		}

		ConnectorEnd end = (ConnectorEnd) redefined;
		int index = ((Connector) end.getOwner()).getEnds().indexOf(end);
		if (index >= 0) {
			EList<ConnectorEnd> ends = ((Connector) getOwner()).getEnds();
			int myIndex = ends.indexOf(this);
			if (myIndex != index) {
				ends.move(index, myIndex);
			}
		}
	}

	void handleRedefinedConnector(Connector connector) {
		if (connector != null) {
			NotificationForwarder.adapt(this,
					() -> new NotificationForwarder(this,
							/* FIXME: Function? */null, rtInheritedFeatures()));
		} else if (eBasicHasAdapters()) {
			NotificationForwarder.unadapt(this);
		}

		// And let my bounds know
		ConnectorEnd redefined = rtGetRedefinedElement();
		if (lowerValue instanceof InternalUMLRTValueSpecification) {
			((InternalUMLRTValueSpecification) lowerValue).handleRedefinedMultiplicityElement(redefined);
		}
		if (upperValue instanceof InternalUMLRTValueSpecification) {
			((InternalUMLRTValueSpecification) upperValue).handleRedefinedMultiplicityElement(redefined);
		}
	}

	@Override
	public void rtReify() {
		Element owner = getOwner();
		if (owner instanceof Connector) {
			EReference containment = eContainmentFeature();
			if (containment == UMLPackage.Literals.CONNECTOR__END) {
				// Either all ends are real, or none.
				EList<ConnectorEnd> ends = ((Connector) owner).getEnds();
				if (ends instanceof InheritableEList<?>) {
					((InheritableEList<ConnectorEnd>) ends).touch();
				}
			}
		}
	}

	@Override
	public void rtVirtualize() {
		Element owner = getOwner();
		if (owner instanceof Connector) {
			EReference containment = eContainmentFeature();
			if (containment == UMLPackage.Literals.CONNECTOR__END) {
				// Either all ends are virtual, or none.
				owner.eUnset(containment);
			}
		}
	}

	@Override
	public void rtUnsetAll() {
		unsetRole();
		unsetPartWithPort();
		unsetLowerValue();
		unsetUpperValue();
	}

	@Override
	public Element basicGetOwner() {
		return rtOwner();
	}

	@Override
	public ConnectableElement getRole() {
		return inheritFeature(UMLPackage.Literals.CONNECTOR_END__ROLE);
	}

	@Override
	public void setRole(ConnectableElement newRole) {
		// Make sure that the notification gets the correct old value
		role = getRole();
		uFlags = uFlags | ROLE__SET_FLAG;

		// If the role is inherited, we must reference the inherited definition
		newRole = unresolveConnectable(newRole);
		super.setRole(newRole);
	}

	public boolean isSetRole() {
		return (uFlags & ROLE__SET_FLAG) != 0;
	}

	public void unsetRole() {
		// Make sure that the notification gets the correct old and new values
		ConnectableElement oldRole = getRole();
		boolean oldRoleESet = (uFlags & ROLE__SET_FLAG) != 0;
		role = null;
		uFlags = uFlags & ~ROLE__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.CONNECTOR_END__ROLE, oldRole, getRole(), oldRoleESet));
		}
	}

	@Override
	public Property getPartWithPort() {
		return inheritFeature(UMLPackage.Literals.CONNECTOR_END__PART_WITH_PORT);
	}

	@Override
	public void setPartWithPort(Property newPartWithPort) {
		// Make sure that the notification gets the correct old value
		partWithPort = getPartWithPort();
		uFlags = uFlags | PART_WITH_PORT__SET_FLAG;

		// If the part-with-port is inherited, we must reference the inherited definition
		newPartWithPort = unresolveConnectable(newPartWithPort);
		super.setPartWithPort(newPartWithPort);
	}

	public boolean isSetPartWithPort() {
		return (uFlags & PART_WITH_PORT__SET_FLAG) != 0;
	}

	public void unsetPartWithPort() {
		// Make sure that the notification gets the correct old and new values
		Property oldPartWithPort = getPartWithPort();
		boolean oldPartWithPortESet = (uFlags & PART_WITH_PORT__SET_FLAG) != 0;
		partWithPort = null;
		uFlags = uFlags & ~PART_WITH_PORT__SET_FLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.CONNECTOR_END__PART_WITH_PORT, oldPartWithPort, getPartWithPort(), oldPartWithPortESet));
		}
	}

	@Override
	public ValueSpecification umlGetLowerValue(boolean resolve) {
		return resolve ? super.getLowerValue() : super.basicGetLowerValue();
	}

	@Override
	public NotificationChain umlBasicSetLowerValue(ValueSpecification newLowerValue, NotificationChain msgs) {
		return super.basicSetLowerValue(newLowerValue, msgs);
	}

	@Override
	public ValueSpecification getLowerValue() {
		return MultiplicityElementRTOperations.getLowerValue(this);
	}

	@Override
	public void setLowerValue(ValueSpecification newLowerValue) {
		MultiplicityElementRTOperations.setLowerValue(this, newLowerValue);
	}

	public boolean isSetLowerValue() {
		return MultiplicityElementRTOperations.isSetLowerValue(this);
	}

	public void unsetLowerValue() {
		MultiplicityElementRTOperations.unsetLowerValue(this);
	}

	@Override
	public ValueSpecification umlGetUpperValue(boolean resolve) {
		return resolve ? super.getUpperValue() : super.basicGetUpperValue();
	}

	@Override
	public NotificationChain umlBasicSetUpperValue(ValueSpecification newUpperValue, NotificationChain msgs) {
		return super.basicSetUpperValue(newUpperValue, msgs);
	}

	@Override
	public ValueSpecification getUpperValue() {
		return MultiplicityElementRTOperations.getUpperValue(this);
	}

	@Override
	public void setUpperValue(ValueSpecification newLowerValue) {
		MultiplicityElementRTOperations.setUpperValue(this, newLowerValue);
	}

	public boolean isSetUpperValue() {
		return MultiplicityElementRTOperations.isSetUpperValue(this);
	}

	public void unsetUpperValue() {
		MultiplicityElementRTOperations.unsetUpperValue(this);
	}

	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		return ElementRTOperations.toString(this, super.toString());
	}
}
