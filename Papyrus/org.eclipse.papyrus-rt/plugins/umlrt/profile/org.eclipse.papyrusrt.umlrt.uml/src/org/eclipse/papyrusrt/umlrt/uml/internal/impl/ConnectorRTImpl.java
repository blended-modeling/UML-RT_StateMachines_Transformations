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
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util.ExtensionResource;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.InheritableEObjectEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.RedefinedElementsList;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.RedefinedElementsListImpl;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.ConnectorKind;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.RedefinableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;

/**
 * UML-RT semantics for {@link Connector}.
 */
public class ConnectorRTImpl extends org.eclipse.uml2.uml.internal.impl.ConnectorImpl implements InternalUMLRTElement {

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.NAMED_ELEMENT__NAME,
			UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY
	/* Don't include the 'end' list because it forwards for itself */));

	protected ConnectorRTImpl() {
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
		case UMLPackage.CONNECTOR__NAME:
			return isSetName();
		case UMLPackage.CONNECTOR__VISIBILITY:
			return isSetVisibility();
		case UMLPackage.CONNECTOR__END:
			return isSetEnds();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.CONNECTOR__NAME:
			return (T) super.getName();
		case UMLPackage.CONNECTOR__VISIBILITY:
			return (T) super.getVisibility();
		case UMLPackage.CONNECTOR__END:
			return (T) super.getEnds();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.CONNECTOR__END:
			unsetEnds();
			break;
		default:
			super.eUnset(featureID);
			break;
		}
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof Connector)) {
			throw new IllegalArgumentException("not a connector: " + redefined); //$NON-NLS-1$
		}

		((RedefinedElementsList<Connector>) getRedefinedConnectors()).setRedefinedElement(
				(Connector) redefined);
	}

	@Override
	public EList<Connector> getRedefinedConnectors() {
		if (redefinedConnectors == null) {
			redefinedConnectors = new RedefinedElementsListImpl<>(
					Connector.class, this,
					UMLPackage.CONNECTOR__REDEFINED_CONNECTOR,
					this::handleRedefinedConnector);
		}
		return redefinedConnectors;
	}

	void handleRedefinedConnector(Connector connector) {
		if (ends != null) {
			Stream.of(ends.toArray()).forEach(e -> ((ConnectorEndRTImpl) e).handleRedefinedConnector(connector));
		}
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
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

	@Override
	public void rtReify() {
		Namespace namespace = getNamespace();
		if (namespace instanceof Class) {
			Class class_ = (Class) namespace;
			if ((class_ != null) && !class_.getOwnedConnectors().contains(this)) {
				class_.getOwnedConnectors().add(this);
				rtAdjustStereotypes();
			}
		}
	}

	@Override
	public void rtVirtualize() {
		Namespace namespace = getNamespace();
		if (namespace instanceof Class) {
			Class class_ = (Class) namespace;

			@SuppressWarnings("unchecked")
			EList<? super Connector> implicitConnectors = (EList<? super Connector>) class_.eGet(ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR);
			if (!implicitConnectors.contains(this)) {
				implicitConnectors.add(this);
				rtAdjustStereotypes();
			}
		}
	}

	@Override
	public void rtUnsetAll() {
		unsetName();
		unsetVisibility();
		unsetEnds();
	}

	@Override
	public Element basicGetOwner() {
		return rtOwner();
	}

	@Override
	public Namespace basicGetNamespace() {
		EObject rtOwner = rtOwner();
		return (rtOwner instanceof org.eclipse.uml2.uml.Class)
				? (org.eclipse.uml2.uml.Class) rtOwner
				: super.basicGetNamespace();
	}

	@Override
	public String getName() {
		return inheritFeature(UMLPackage.Literals.NAMED_ELEMENT__NAME);
	}

	@Override
	public void setName(String newName) {
		// Make sure that the notification gets the correct old value
		name = getName();
		super.setName(newName);
	}

	@Override
	public void unsetName() {
		// Make sure that the notification gets the correct old and new values
		String oldName = getName();
		boolean oldNameESet = (eFlags & NAME_ESETFLAG) != 0;
		name = NAME_EDEFAULT;
		eFlags = eFlags & ~NAME_ESETFLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.NAMED_ELEMENT__NAME, oldName, getName(), oldNameESet));
		}
	}

	@Override
	public VisibilityKind getVisibility() {
		return inheritFeature(UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY);
	}

	@Override
	public void setVisibility(VisibilityKind newVisibility) {
		// Make sure that the notification gets the correct old value
		if (newVisibility == null) {
			newVisibility = VISIBILITY_EDEFAULT;
		}
		eFlags = eFlags | (getVisibility().ordinal() << VISIBILITY_EFLAG_OFFSET);
		super.setVisibility(newVisibility);
	}

	@Override
	public void unsetVisibility() {
		// Make sure that the notification gets the correct old and new values
		VisibilityKind oldVisibility = getVisibility();
		boolean oldVisibilityESet = (eFlags & VISIBILITY_ESETFLAG) != 0;
		eFlags = (eFlags & ~VISIBILITY_EFLAG) | VISIBILITY_EFLAG_DEFAULT;
		eFlags = eFlags & ~VISIBILITY_ESETFLAG;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, UMLPackage.NAMED_ELEMENT__VISIBILITY, oldVisibility, getVisibility(), oldVisibilityESet));
		}
	}

	@Override
	public EList<ConnectorEnd> getEnds() {
		if (ends == null) {
			ends = new InheritableEObjectEList.Containment<ConnectorEnd>(this, UMLPackage.CONNECTOR__END) {
				private static final long serialVersionUID = 1L;

				@Override
				protected ConnectorEnd createRedefinition(ConnectorEnd inherited) {
					ConnectorEndRTImpl result = (ConnectorEndRTImpl) create(inherited.eClass());
					// Redefinition is implied by correspondence of the ends by index
					result.handleRedefinedConnector(rtGetRedefinedElement());
					return result;
				}
			};
		}

		EList<ConnectorEnd> inherited = inheritFeature(UMLPackage.Literals.CONNECTOR__END);

		if (inherited != ends) {
			// Inherit this into our list. If it's null, that means we are the root definition
			((InheritableEList<ConnectorEnd>) ends).inherit(inherited);
		}

		return ends;
	}

	public boolean isSetEnds() {
		return (ends != null) && ((InheritableEList<ConnectorEnd>) ends).isSet();
	}

	public void unsetEnds() {
		if (ends != null) {
			((InheritableEList<ConnectorEnd>) ends).unset();
		}
	}

	@Override
	public boolean isConsistentWith(RedefinableElement redefiningElement) {
		boolean result = false;

		if (redefiningElement instanceof Connector) {
			Connector redefiningConnector = (Connector) redefiningElement;

			// FIXME: UML-RT Profile Specification has nothing to say, nor does UML
			result = redefiningConnector.getRedefinedConnectors().stream()
					.anyMatch(c -> (c == this) || this.isConsistentWith(c));
		}
		return result;
	}

	/**
	 * UML-RT specific case of pass-through connector as a kind of assembly.
	 * 
	 * @see <a href="http://eclip.se/509765">bug 509765</a>
	 */
	@Override
	public ConnectorKind getKind() {
		int relayCount = 0;

		for (ConnectorEnd end : getEnds()) {
			ConnectableElement role = end.getRole();

			if ((role instanceof Port) && (end.getPartWithPort() == null)
					&& !((Port) role).isBehavior()) {

				relayCount++;
			}
		}

		// If both ports are relays, then it's a pass-through connector and thus assembly
		return (relayCount == 1)
				? ConnectorKind.DELEGATION_LITERAL
				: ConnectorKind.ASSEMBLY_LITERAL;
	}

	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		return ElementRTOperations.toString(this, super.toString());
	}
}
