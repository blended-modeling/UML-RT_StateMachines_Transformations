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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicEList.UnmodifiableEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.DerivedUnionEObjectEList;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Classifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTClassifierImpl#getGeneral <em>General</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTClassifierImpl#getSpecifics <em>Specific</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class UMLRTClassifierImpl extends UMLRTNamedElementImpl implements UMLRTClassifier {

	protected static class ClassifierAdapter<F extends UMLRTClassifierImpl> extends NamedElementAdapter<F> {

		ClassifierAdapter(F facade) {
			super(facade);
		}

		@Override
		public void setTarget(Notifier newTarget) {
			super.setTarget(newTarget);

			if (newTarget instanceof Classifier) {
				Classifier classifier = (Classifier) newTarget;
				List<Generalization> generalizations = classifier.getGeneralizations();
				if (!generalizations.isEmpty()) {
					// UML-RT supports single inheritance only
					addAdapter(generalizations.get(0));
				}
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (oldTarget instanceof Classifier) {
				Classifier classifier = (Classifier) oldTarget;
				List<Generalization> generalizations = classifier.getGeneralizations();
				if (!generalizations.isEmpty()) {
					generalizations.forEach(this::removeAdapter);
				}
			}

			super.unsetTarget(oldTarget);
		}

		@Override
		protected void handleObjectAdded(Notification msg, int position, EObject object) {
			if (msg.getFeature() == UMLPackage.Literals.CLASSIFIER__GENERALIZATION) {
				if (get().toUML().getGeneralizations().indexOf(object) == 0) {
					// UML-RT only supports single generalization
					addAdapter(object);
				}
			} else {
				super.handleObjectAdded(msg, position, object);
			}
		}

		@Override
		protected void handleObjectRemoved(Notification msg, int position, EObject object) {
			if (msg.getFeature() == UMLPackage.Literals.CLASSIFIER__GENERALIZATION) {
				removeAdapter(object);
			} else {
				super.handleObjectRemoved(msg, position, object);
			}
		}

		@Override
		protected FacadeObject getFacade(EObject umlOwner, EReference umlReference, EObject object) {
			if (object instanceof Generalization) {
				Classifier general = ((Generalization) object).getGeneral();
				return (general == null) ? null : UMLRTFactory.create(general);
			} else {
				return super.getFacade(umlOwner, umlReference, object);
			}
		}

		@Override
		protected void handleObjectReplaced(Notification msg, int position, FacadeObject oldObject, FacadeObject newObject) {
			if (msg.getFeature() == UMLPackage.Literals.CLASSIFIER__GENERALIZATION) {
				if (position == 0) {
					notifyGeneral(get(), oldObject, newObject);
				}
			} else if (msg.getFeature() == UMLPackage.Literals.GENERALIZATION__GENERAL) {
				notifyGeneral(get(), oldObject, newObject);
			} else {
				super.handleObjectReplaced(msg, position, oldObject, newObject);
			}
		}

		protected void notifyGeneral(F owner, FacadeObject oldObject, FacadeObject newObject) {
			if (owner.eNotificationRequired()) {
				owner.eNotify(new ENotificationImpl(owner, Notification.SET, UMLRTUMLRTPackage.Literals.CLASSIFIER__GENERAL, oldObject, newObject));
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTClassifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.CLASSIFIER;
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTClassifierImpl> createFacadeAdapter() {
		return new ClassifierAdapter<>(this);
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
				cache.put(eResource, this, UMLRTUMLRTPackage.Literals.CLASSIFIER__SPECIFIC, specifics = new DerivedUnionEObjectEList<>(UMLRTClassifier.class, this, UMLRTUMLRTPackage.CLASSIFIER__SPECIFIC, null));
			}
			return specifics;
		}
		return new DerivedUnionEObjectEList<>(UMLRTClassifier.class, this, UMLRTUMLRTPackage.CLASSIFIER__SPECIFIC, null);
	}

	static <C extends Classifier> Stream<C> specifics(C class_, Class<C> metaclass) {
		Stream<Generalization> generalizations = class_.getTargetDirectedRelationships().stream()
				.filter(Generalization.class::isInstance).map(Generalization.class::cast);
		Stream<C> result = generalizations.map(Generalization::getSpecific)
				.filter(metaclass::isInstance).map(metaclass::cast);
		return result;
	}

	static <C extends Classifier> Set<C> collectSpecificClosure(C class_, Class<C> metaclass, Set<C> result) {
		specifics(class_, metaclass).forEach(classifier -> {
			if (result.add(classifier)) {
				collectSpecificClosure(classifier, metaclass, result);
			}
		});
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTClassifier getSpecific(String name) {
		return getSpecific(name, false, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTClassifier getSpecific(String name, boolean ignoreCase, EClass eClass) {
		specificLoop: for (UMLRTClassifier specific : getSpecifics()) {
			if (eClass != null && !eClass.isInstance(specific)) {
				continue specificLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(specific.getName()) : name.equals(specific.getName()))) {
				continue specificLoop;
			}
			return specific;
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
	public UMLRTClassifier getGeneral() {
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Classifier toUML() {
		return (Classifier) super.toUML();
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
	public List<? extends UMLRTClassifier> getAncestry() {
		throw new UnsupportedOperationException();
	}

	static <C extends Classifier> List<C> ancestry(C class_, Class<C> metaclass) {
		Set<C> result = new LinkedHashSet<>();
		for (C next = class_; next != null;) {
			if (!result.add(next)) {
				// Cycle
				break;
			}
			List<Classifier> generals = next.getGenerals();
			if (!generals.isEmpty() && metaclass.isInstance(generals.get(0))) {
				next = metaclass.cast(generals.get(0));
			} else {
				next = null;
			}
		}
		return new UnmodifiableEList.FastCompare<>(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Stream<? extends UMLRTClassifier> getHierarchy() {
		throw new UnsupportedOperationException();
	}

	static <C extends Classifier> Stream<C> hierarchy(C class_, Class<C> metaclass) {
		return Stream.concat(Stream.of(class_),
				collectSpecificClosure(class_, metaclass, new LinkedHashSet<>()).stream());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isSuperTypeOf(UMLRTClassifier classifier) {
		return (classifier != null) && classifier.getAncestry().contains(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setGeneral(UMLRTClassifier general) {
		throw new UnsupportedOperationException();
	}

	protected static <C extends Classifier> void setGeneral(C specific, C general) {
		EList<Classifier> generals = specific.getGenerals();
		if (general == null) {
			new ArrayList<>(specific.getGeneralizations()).forEach(Element::destroy);
		} else if (generals.isEmpty()) {
			generals.add(general);
		} else if (generals.get(0) != general) {
			generals.set(0, general);
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
		case UMLRTUMLRTPackage.CLASSIFIER__GENERAL:
			return getGeneral();
		case UMLRTUMLRTPackage.CLASSIFIER__SPECIFIC:
			return getSpecifics();
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLRTUMLRTPackage.CLASSIFIER__GENERAL:
			return isSetGeneral();
		case UMLRTUMLRTPackage.CLASSIFIER__SPECIFIC:
			return isSetSpecifics();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean isSetGeneral() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean isSetSpecifics() {
		return false;
	}

} // UMLRTClassifierImpl
