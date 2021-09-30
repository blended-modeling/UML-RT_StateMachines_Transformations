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
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostate;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPseudostateKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pseudostate</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPseudostateImpl#getRedefinedVertex <em>Redefined Vertex</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPseudostateImpl#getKind <em>Kind</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPseudostateImpl#getRedefinedPseudostate <em>Redefined Pseudostate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTPseudostateImpl extends UMLRTVertexImpl implements UMLRTPseudostate {
	static final FacadeType<Pseudostate, RTPseudostate, UMLRTPseudostateImpl> TYPE = new FacadeType<>(
			UMLRTPseudostateImpl.class,
			UMLPackage.Literals.PSEUDOSTATE,
			UMLRTStateMachinesPackage.Literals.RT_PSEUDOSTATE,
			UMLRTPseudostateImpl::getInstance,
			vertex -> (UMLRTPseudostateImpl) vertex.getRedefinedVertex(),
			UMLRTPseudostateImpl::new);

	protected static class PseudostateAdapter<F extends UMLRTPseudostateImpl> extends VertexAdapter<F> {

		PseudostateAdapter(F facade) {
			super(facade);
		}

		@Override
		protected void handleValueReplaced(Notification msg, int position, Object oldValue, Object newValue) {
			if (msg.getFeature() == UMLPackage.Literals.PSEUDOSTATE__KIND) {
				if (get().eNotificationRequired()) {
					get().eNotify(new ENotificationImpl(get(), Notification.SET,
							UMLRTUMLRTPackage.CONNECTION_POINT__KIND,
							UMLRTPseudostateKind.valueOf((PseudostateKind) oldValue),
							UMLRTPseudostateKind.valueOf((PseudostateKind) newValue)));
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
	protected static final UMLRTPseudostateKind KIND_EDEFAULT = UMLRTPseudostateKind.INITIAL;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTPseudostateImpl() {
		super();
	}

	/**
	 * Obtains the canonical instance of the façade for a pseudostate.
	 *
	 * @param pseudostate
	 *            a pseudostate in the UML model
	 *
	 * @return its façade, or {@code null} if the class is not a valid pseudostate
	 */
	public static UMLRTPseudostateImpl getInstance(Pseudostate pseudostate) {
		// Pseudostates are only pseudostates that are in a region
		EReference containment = pseudostate.eContainmentFeature();
		return ((containment == UMLPackage.Literals.REGION__SUBVERTEX)
				|| (containment == ExtUMLExtPackage.Literals.REGION__IMPLICIT_SUBVERTEX))
						? getFacade(pseudostate, TYPE)
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
		return UMLRTUMLRTPackage.Literals.PSEUDOSTATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTVertex getRedefinedVertex() {
		UMLRTPseudostate redefinedPseudostate = getRedefinedPseudostate();
		if (redefinedPseudostate != null) {
			return redefinedPseudostate;
		}
		return super.getRedefinedVertex();
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTPseudostateImpl> createFacadeAdapter() {
		return new PseudostateAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTPseudostateKind getKind() {
		return UMLRTPseudostateKind.valueOf(toUML().getKind());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setKind(UMLRTPseudostateKind newKind) {
		toUML().setKind(newKind.toUML());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTPseudostate getRedefinedPseudostate() {
		UMLRTPseudostate result = null;

		if (toUML() instanceof InternalUMLRTElement) {
			// Pseudostates only have redefinition in our UML-RT implementation
			Pseudostate superPseudo = (Pseudostate) ((InternalUMLRTElement) toUML()).rtGetRedefinedElement();
			result = (superPseudo == null) ? null : UMLRTPseudostate.getInstance(superPseudo);
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
	public Stream<? extends UMLRTPseudostate> allRedefinitions() {
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
		case UMLRTUMLRTPackage.PSEUDOSTATE__KIND:
			return getKind();
		case UMLRTUMLRTPackage.PSEUDOSTATE__REDEFINED_PSEUDOSTATE:
			return getRedefinedPseudostate();
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
		case UMLRTUMLRTPackage.PSEUDOSTATE__KIND:
			setKind((UMLRTPseudostateKind) newValue);
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
		case UMLRTUMLRTPackage.PSEUDOSTATE__KIND:
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
		case UMLRTUMLRTPackage.PSEUDOSTATE__REDEFINED_VERTEX:
			return isSetRedefinedVertex();
		case UMLRTUMLRTPackage.PSEUDOSTATE__KIND:
			return getKind() != KIND_EDEFAULT;
		case UMLRTUMLRTPackage.PSEUDOSTATE__REDEFINED_PSEUDOSTATE:
			return getRedefinedPseudostate() != null;
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
				|| eIsSet(UMLRTUMLRTPackage.PSEUDOSTATE__REDEFINED_PSEUDOSTATE);
	}

} // UMLRTPseudostateImpl
