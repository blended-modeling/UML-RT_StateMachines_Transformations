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

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTPseudostate;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPointKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection Point</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectionPointImpl#getRedefinedVertex <em>Redefined Vertex</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectionPointImpl#getRedefinitionContext <em>Redefinition Context</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectionPointImpl#getKind <em>Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectionPointImpl#getRedefinedConnectionPoint <em>Redefined Connection Point</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectionPointImpl#getState <em>State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTConnectionPointImpl extends UMLRTVertexImpl implements UMLRTConnectionPoint {
	static final FacadeType<Pseudostate, RTPseudostate, UMLRTConnectionPointImpl> TYPE = new FacadeType<>(
			UMLRTConnectionPointImpl.class,
			UMLPackage.Literals.PSEUDOSTATE,
			UMLRTStateMachinesPackage.Literals.RT_PSEUDOSTATE,
			UMLRTConnectionPointImpl::getInstance,
			vertex -> (UMLRTConnectionPointImpl) vertex.getRedefinedVertex(),
			UMLRTConnectionPointImpl::new);

	protected static class ConnPtAdapter<F extends UMLRTConnectionPointImpl> extends VertexAdapter<F> {

		ConnPtAdapter(F facade) {
			super(facade);
		}

		@Override
		protected void handleValueReplaced(Notification msg, int position, Object oldValue, Object newValue) {
			if (msg.getFeature() == UMLPackage.Literals.PSEUDOSTATE__KIND) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), Notification.SET,
							UMLRTUMLRTPackage.CONNECTION_POINT__KIND,
							UMLRTConnectionPointKind.valueOf((PseudostateKind) oldValue),
							UMLRTConnectionPointKind.valueOf((PseudostateKind) newValue)));
				}
			} else {
				super.handleValueReplaced(msg, position, oldValue, newValue);
			}
		}
	}

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final UMLRTConnectionPointKind KIND_EDEFAULT = UMLRTConnectionPointKind.ENTRY;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTConnectionPointImpl() {
		super();
	}

	/**
	 * Obtains the canonical instance of the façade for a connection point.
	 *
	 * @param connectionPoint
	 *            a connection point in the UML model
	 *
	 * @return its façade, or {@code null} if the class is not a valid connection point
	 */
	public static UMLRTConnectionPointImpl getInstance(Pseudostate connectionPoint) {
		// Connection points are only pseudostates that are composite state connection points
		EReference containment = connectionPoint.eContainmentFeature();
		return ((containment == UMLPackage.Literals.STATE__CONNECTION_POINT)
				|| (containment == ExtUMLExtPackage.Literals.STATE__IMPLICIT_CONNECTION_POINT))
						? getFacade(connectionPoint, TYPE)
						: null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.CONNECTION_POINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTVertex getRedefinedVertex() {
		UMLRTConnectionPoint redefinedConnectionPoint = getRedefinedConnectionPoint();
		if (redefinedConnectionPoint != null) {
			return redefinedConnectionPoint;
		}
		return super.getRedefinedVertex();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTNamedElement getRedefinitionContext() {
		if (isSetState()) {
			return getState();
		}
		return super.getRedefinitionContext();
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTConnectionPointImpl> createFacadeAdapter() {
		return new ConnPtAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTConnectionPointKind getKind() {
		return UMLRTConnectionPointKind.valueOf(toUML().getKind());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setKind(UMLRTConnectionPointKind newKind) {
		toUML().setKind(newKind.toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTConnectionPoint getRedefinedConnectionPoint() {
		UMLRTConnectionPoint result = null;

		if (toUML() instanceof InternalUMLRTElement) {
			// Pseudostates only have redefinition in our UML-RT implementation
			Pseudostate superPseudo = (Pseudostate) ((InternalUMLRTElement) toUML()).rtGetRedefinedElement();
			result = (superPseudo == null) ? null : UMLRTConnectionPoint.getInstance(superPseudo);
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
	public UMLRTState getState() {
		return UMLRTState.getInstance(toUML().getState());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean isSetState() {
		return getState() != null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Pseudostate toUML() {
		return (Pseudostate) super.toUML();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Stream<? extends UMLRTConnectionPoint> allRedefinitions() {
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
		case UMLRTUMLRTPackage.CONNECTION_POINT__KIND:
			return getKind();
		case UMLRTUMLRTPackage.CONNECTION_POINT__REDEFINED_CONNECTION_POINT:
			return getRedefinedConnectionPoint();
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
		case UMLRTUMLRTPackage.CONNECTION_POINT__KIND:
			setKind((UMLRTConnectionPointKind) newValue);
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
		case UMLRTUMLRTPackage.CONNECTION_POINT__KIND:
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
		case UMLRTUMLRTPackage.CONNECTION_POINT__REDEFINED_VERTEX:
			return isSetRedefinedVertex();
		case UMLRTUMLRTPackage.CONNECTION_POINT__REDEFINITION_CONTEXT:
			return isSetRedefinitionContext();
		case UMLRTUMLRTPackage.CONNECTION_POINT__KIND:
			return getKind() != KIND_EDEFAULT;
		case UMLRTUMLRTPackage.CONNECTION_POINT__REDEFINED_CONNECTION_POINT:
			return getRedefinedConnectionPoint() != null;
		case UMLRTUMLRTPackage.CONNECTION_POINT__STATE:
			return isSetState();
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
	public boolean isSetRedefinedVertex() {
		return super.isSetRedefinedVertex()
				|| eIsSet(UMLRTUMLRTPackage.CONNECTION_POINT__REDEFINED_CONNECTION_POINT);
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
				|| isSetState();
	}

} // UMLRTConnectionPointImpl
