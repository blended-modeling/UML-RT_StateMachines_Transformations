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
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.OperationOwner;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;

/**
 * UML-RT semantics for {@link Operation}.
 */
public class OperationRTImpl extends org.eclipse.uml2.uml.internal.impl.OperationImpl implements InternalUMLRTElement {

	private static final Set<EStructuralFeature> INHERITED_FEATURES = new HashSet<>(Arrays.asList(
			UMLPackage.Literals.NAMED_ELEMENT__NAME,
			UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY
	/* Don't include the 'ownedParameter' list because it forwards for itself */));

	/**
	 * The array of subset feature identifiers for the '{@link #getRedefinitionContexts() <em>Redefinition Context</em>}' reference list.
	 * 
	 * @see #getRedefinitionContexts()
	 */
	protected static final int[] REDEFINITION_CONTEXT_ESUBSETS = new int[] {
			UMLPackage.BEHAVIORAL_FEATURE__NAMESPACE };

	protected OperationRTImpl() {
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLPackage.OPERATION__NAME:
			return isSetName();
		case UMLPackage.OPERATION__VISIBILITY:
			return isSetVisibility();
		case UMLPackage.OPERATION__OWNED_PARAMETER:
			return isSetOwnedParameters();
		default:
			return super.eIsSet(featureID);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T umlGet(int featureID) {
		switch (featureID) {
		case UMLPackage.OPERATION__NAME:
			return (T) super.getName();
		case UMLPackage.OPERATION__VISIBILITY:
			return (T) super.getVisibility();
		case UMLPackage.OPERATION__OWNED_PARAMETER:
			return (T) super.getOwnedParameters();
		default:
			return (T) super.eGet(featureID, true, true);
		}
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLPackage.OPERATION__OWNED_PARAMETER:
			unsetOwnedParameters();
			break;
		default:
			super.eUnset(featureID);
			break;
		}
	}

	@Override
	public void umlSetRedefinedElement(InternalUMLRTElement redefined) {
		if (!(redefined instanceof Operation)) {
			throw new IllegalArgumentException("not an operation: " + redefined); //$NON-NLS-1$
		}

		((RedefinedElementsList<Operation>) getRedefinedOperations()).setRedefinedElement(
				(Operation) redefined);
	}

	@Override
	public EList<Operation> getRedefinedOperations() {
		if (redefinedOperations == null) {
			redefinedOperations = new RedefinedElementsListImpl<>(
					Operation.class, this,
					UMLPackage.OPERATION__REDEFINED_OPERATION);
		}
		return redefinedOperations;
	}

	@Override
	public Collection<? extends EStructuralFeature> rtInheritedFeatures() {
		return INHERITED_FEATURES;
	}

	@Override
	public void rtReify() {
		Element owner_ = getOwner();
		OperationOwner owner = (owner_ instanceof OperationOwner) ? (OperationOwner) owner_ : null;
		if ((owner != null) && !owner.getOwnedOperations().contains(this)) {
			owner.getOwnedOperations().add(this);
			rtAdjustStereotypes();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void rtVirtualize() {
		EList<? super Operation> implicitOperations = null;

		Element owner = getOwner();
		if (owner instanceof Class) {
			implicitOperations = (EList<? super Operation>) owner.eGet(ExtUMLExtPackage.Literals.CLASS__IMPLICIT_OPERATION);
		} else if (owner instanceof Interface) {
			implicitOperations = (EList<? super Operation>) owner.eGet(ExtUMLExtPackage.Literals.INTERFACE__IMPLICIT_OPERATION);
		}

		if ((implicitOperations != null) && !implicitOperations.contains(this)) {
			implicitOperations.add(this);
			rtAdjustStereotypes();
		}
	}

	@Override
	public void rtUnsetAll() {
		unsetName();
		unsetVisibility();
		unsetOwnedParameters();
	}

	@Override
	public Element basicGetOwner() {
		return rtOwner();
	}

	@Override
	public Namespace basicGetNamespace() {
		Element rtOwner = rtOwner();
		return (rtOwner instanceof Namespace)
				? (Namespace) rtOwner
				: null;
	}

	/**
	 * Restores the {@code BehavioralFeature} specification of the redefinition context
	 * subsets because that is what we override to get the owning classifier.
	 */
	@Override
	public EList<Classifier> getRedefinitionContexts() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Resource eResource = eResource();
			@SuppressWarnings("unchecked")
			EList<Classifier> redefinitionContexts = (EList<Classifier>) cache
					.get(eResource, this,
							UMLPackage.Literals.REDEFINABLE_ELEMENT__REDEFINITION_CONTEXT);
			if (redefinitionContexts == null) {
				redefinitionContexts = new DerivedUnionEObjectEList<>(
						Classifier.class, this,
						UMLPackage.OPERATION__REDEFINITION_CONTEXT,
						REDEFINITION_CONTEXT_ESUBSETS);
				cache.put(eResource, this,
						UMLPackage.Literals.REDEFINABLE_ELEMENT__REDEFINITION_CONTEXT,
						redefinitionContexts);
			}
			return redefinitionContexts;
		}
		return new DerivedUnionEObjectEList<>(Classifier.class, this,
				UMLPackage.OPERATION__REDEFINITION_CONTEXT,
				REDEFINITION_CONTEXT_ESUBSETS);
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
	public EList<Parameter> getOwnedParameters() {
		if (ownedParameters == null) {
			ownedParameters = new InheritableEObjectEList.Containment<Parameter>(this, UMLPackage.OPERATION__OWNED_PARAMETER) {
				private static final long serialVersionUID = 1L;

				@Override
				protected Parameter createRedefinition(Parameter inherited) {
					ParameterRTImpl result = (ParameterRTImpl) create(inherited.eClass());
					// Redefinition is implied by correspondence of the parameters by index
					result.handleRedefinedOperation(rtGetRedefinedElement());
					return result;
				}
			};
		}

		EList<Parameter> inherited = inheritFeature(UMLPackage.Literals.BEHAVIORAL_FEATURE__OWNED_PARAMETER);

		if (inherited != ownedParameters) {
			// Inherit this into our list. If it's null, that means we are the root definition
			((InheritableEList<Parameter>) ownedParameters).inherit(inherited);
		}

		return ownedParameters;
	}

	@Override
	public boolean isSetOwnedParameters() {
		return (ownedParameters != null) && ((InheritableEList<Parameter>) ownedParameters).isSet();
	}

	public void unsetOwnedParameters() {
		if (ownedParameters != null) {
			((InheritableEList<Parameter>) ownedParameters).unset();
		}
	}

	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		return ElementRTOperations.toString(this, super.toString());
	}

}
