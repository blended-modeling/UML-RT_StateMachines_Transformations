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

import static org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.FacadeType.NO_STEREOTYPE;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Capsule;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.ProtocolContainer;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Package</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPackageImpl#getNestedPackages <em>Nested Package</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPackageImpl#getNestingPackage <em>Nesting Package</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPackageImpl#getCapsules <em>Capsule</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTPackageImpl#getProtocols <em>Protocol</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UMLRTPackageImpl extends UMLRTNamedElementImpl implements UMLRTPackage {

	static final FacadeType<Package, EObject, UMLRTPackageImpl> TYPE = new FacadeType<>(
			UMLRTPackageImpl.class,
			UMLPackage.Literals.PACKAGE,
			NO_STEREOTYPE,
			UMLRTPackageImpl::getInstance,
			null,
			UMLRTPackageImpl::maybeCreate);

	private static final FacadeReference<Class, Capsule, UMLRTCapsule, UMLRTCapsuleImpl> CAPSULE_REFERENCE = new FacadeReference<>(
			UMLRTCapsuleImpl.TYPE,
			UMLRTUMLRTPackage.PACKAGE__CAPSULE,
			UMLRTCapsule.class,
			UMLPackage.Literals.PACKAGE__OWNED_TYPE,
			null,
			UMLPackage.Literals.CLASS);

	private static final FacadeReference<Package, EObject, UMLRTPackage, UMLRTPackageImpl> NESTED_PACKAGE_REFERENCE = new FacadeReference<>(
			UMLRTPackageImpl.TYPE,
			UMLRTUMLRTPackage.PACKAGE__NESTED_PACKAGE,
			UMLRTPackage.class,
			UMLPackage.Literals.PACKAGE__NESTED_PACKAGE,
			null,
			UMLPackage.Literals.PACKAGE);

	protected static class PackageAdapter<F extends UMLRTPackageImpl> extends NamedElementAdapter<F> {

		// Cannot attach ourselves to protocol-containers because otherwise they
		// will be mistaken for our own package façade via getFacadeAdapter(...)
		private final Nested protocolContainerAdapter = new Nested();

		PackageAdapter(F facade) {
			super(facade);
		}

		@Override
		public void setTarget(Notifier newTarget) {
			super.setTarget(newTarget);

			if ((newTarget instanceof Package) && !isProtocolContainer((Package) newTarget)) {
				// Look for protocol-containers
				((Package) newTarget).getNestedPackages().stream()
						.filter(UMLRTPackageImpl::isProtocolContainer)
						.forEach(protocolContainerAdapter::addAdapter);
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (oldTarget instanceof Package) {
				((Package) oldTarget).getNestedPackages().forEach(protocolContainerAdapter::removeAdapter);
			}

			super.unsetTarget(oldTarget);
		}

		@Override
		protected List<? extends FacadeObject> getFacadeList(EObject owner, EReference reference, EObject object) {
			List<? extends FacadeObject> result;

			if (reference == UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT) {
				result = (object instanceof Package)
						? get().nestedPackages
						: (object instanceof Class)
								? get().capsules
								: (object instanceof Collaboration)
										? get().protocols
										: null;
			} else {
				result = super.getFacadeList(owner, reference, object);
			}

			return result;
		}

		@Override
		protected InternalFacadeEList<? extends FacadeObject> validateObject(EObject owner, EReference reference, FacadeObject object) {
			InternalFacadeEList<? extends FacadeObject> result = null;

			if (object instanceof UMLRTPackage) {
				if (reference == UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT) {
					result = get().nestedPackages;
				}
			} else if (object instanceof UMLRTCapsule) {
				if (reference == UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT) {
					result = get().capsules;
				}
			} else if (object instanceof UMLRTProtocol) {
				if (reference == UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT) {
					result = get().protocols;
				}
			} else {
				result = super.validateObject(owner, reference, object);
			}

			return result;
		}

		@Override
		public void tickle(NamedElement element) {
			if (element instanceof Package) {
				if (isProtocolContainer((org.eclipse.uml2.uml.Package) element)) {
					addAdapter(element);
				}
			} else {
				super.tickle(element);
			}
		}

		//
		// Nested types
		//

		private final class Nested extends AdapterImpl {
			void addAdapter(Notifier notifier) {
				EList<Adapter> adapters = notifier.eAdapters();
				if (!adapters.contains(this)) {
					adapters.add(this);
				}
			}

			void removeAdapter(Notifier notifier) {
				notifier.eAdapters().remove(this);
			}

			@Override
			public void notifyChanged(Notification msg) {
				// Forward to the real package adapter
				PackageAdapter.this.notifyChanged(msg);
			}
		}
	}

	InternalFacadeEList<UMLRTPackage> nestedPackages;
	InternalFacadeEList<UMLRTCapsule> capsules;
	InternalFacadeEList<UMLRTProtocol> protocols;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTPackageImpl() {
		super();
	}

	/**
	 * Obtains the canonical instance of the package façade for a package.
	 *
	 * @param package_
	 *            a package in the UML model
	 *
	 * @return its package façade, or {@code null} if the package is not a valid UML-RT package
	 *         (for example, if it is a protocol-container package or does not
	 *         have or inherit an application of the UML-RT profile)
	 */
	public static UMLRTPackageImpl getInstance(Package package_) {
		return getFacade(package_, TYPE);
	}

	private static UMLRTPackageImpl maybeCreate(Package package_) {
		return (isRTPackage(package_) && !isProtocolContainer(package_))
				? new UMLRTPackageImpl()
				: null;
	}

	static boolean isRTPackage(Package package_) {
		return !(package_ instanceof Profile)
				&& (package_.getAppliedProfile("UMLRealTime", true) != null);
	}

	static boolean isProtocolContainer(Package package_) {
		return getProtocolContainer(package_) != null;
	}

	static ProtocolContainer getProtocolContainer(Package package_) {
		return UMLUtil.getStereotypeApplication(package_, ProtocolContainer.class);
	}

	static Collaboration getProtocol(Package protocolContainer) {
		return (Collaboration) protocolContainer.getMember(
				protocolContainer.getName(), false, UMLPackage.Literals.COLLABORATION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.PACKAGE;
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTPackageImpl> createFacadeAdapter() {
		return new PackageAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTPackage> getNestedPackages() {
		if (nestedPackages == null) {
			nestedPackages = (InternalFacadeEList<UMLRTPackage>) getFacades(NESTED_PACKAGE_REFERENCE);
		}
		return nestedPackages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTPackage getNestedPackage(String name) {
		return getNestedPackage(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTPackage getNestedPackage(String name, boolean ignoreCase) {
		nestedPackageLoop: for (UMLRTPackage nestedPackage : getNestedPackages()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(nestedPackage.getName()) : name.equals(nestedPackage.getName()))) {
				continue nestedPackageLoop;
			}
			return nestedPackage;
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
	public UMLRTPackage getNestingPackage() {
		Package result = toUML().getNestingPackage();
		return (result == null) ? null : getInstance(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public List<UMLRTCapsule> getCapsules() {
		if (capsules == null) {
			capsules = (InternalFacadeEList<UMLRTCapsule>) getFacades(CAPSULE_REFERENCE);
		}
		return capsules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTCapsule getCapsule(String name) {
		return getCapsule(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTCapsule getCapsule(String name, boolean ignoreCase) {
		capsuleLoop: for (UMLRTCapsule capsule : getCapsules()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(capsule.getName()) : name.equals(capsule.getName()))) {
				continue capsuleLoop;
			}
			return capsule;
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
	public List<UMLRTProtocol> getProtocols() {
		if (protocols == null) {
			List<UMLRTProtocol> protocols_ = toUML().getNestedPackages().stream()
					.filter(UMLRTPackageImpl::isProtocolContainer)
					.map(UMLRTPackageImpl::getProtocol)
					.map(UMLRTProtocol::getInstance)
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			protocols = new FacadeObjectEList<>(this, UMLRTProtocol.class, UMLRTUMLRTPackage.PACKAGE__PROTOCOL, protocols_);
		}
		return protocols;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocol getProtocol(String name) {
		return getProtocol(name, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UMLRTProtocol getProtocol(String name, boolean ignoreCase) {
		protocolLoop: for (UMLRTProtocol protocol : getProtocols()) {
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(protocol.getName()) : name.equals(protocol.getName()))) {
				continue protocolLoop;
			}
			return protocol;
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
	public org.eclipse.uml2.uml.Package toUML() {
		return (Package) super.toUML();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTPackage createNestedPackage(String name) {
		return create(NESTED_PACKAGE_REFERENCE, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTCapsule createCapsule(String name) {
		return ((UMLRTCapsuleImpl) create(CAPSULE_REFERENCE, name)).initCapsule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTProtocol createProtocol(String name) {
		// Protocol container
		Package protocolContainer = toUML().createNestedPackage(name);
		StereotypeApplicationHelper.getInstance(protocolContainer).applyStereotype(protocolContainer, UMLRealTimePackage.Literals.PROTOCOL_CONTAINER);

		// Protocol collaboration
		Collaboration result = (Collaboration) protocolContainer.createOwnedType(name, UMLPackage.Literals.COLLABORATION);
		result.setName(name);
		UMLRTProtocolImpl.TYPE.applyStereotype(result);

		return Optional.ofNullable(UMLRTProtocolImpl.getInstance(result))
				.map(UMLRTProtocolImpl::initContents)
				.orElse(null);
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
		case UMLRTUMLRTPackage.PACKAGE__NESTED_PACKAGE:
			return getNestedPackages();
		case UMLRTUMLRTPackage.PACKAGE__NESTING_PACKAGE:
			return getNestingPackage();
		case UMLRTUMLRTPackage.PACKAGE__CAPSULE:
			return getCapsules();
		case UMLRTUMLRTPackage.PACKAGE__PROTOCOL:
			return getProtocols();
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
		case UMLRTUMLRTPackage.PACKAGE__NESTED_PACKAGE:
			getNestedPackages().clear();
			getNestedPackages().addAll((Collection<? extends UMLRTPackage>) newValue);
			return;
		case UMLRTUMLRTPackage.PACKAGE__CAPSULE:
			getCapsules().clear();
			getCapsules().addAll((Collection<? extends UMLRTCapsule>) newValue);
			return;
		case UMLRTUMLRTPackage.PACKAGE__PROTOCOL:
			getProtocols().clear();
			getProtocols().addAll((Collection<? extends UMLRTProtocol>) newValue);
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
		case UMLRTUMLRTPackage.PACKAGE__NESTED_PACKAGE:
			getNestedPackages().clear();
			return;
		case UMLRTUMLRTPackage.PACKAGE__CAPSULE:
			getCapsules().clear();
			return;
		case UMLRTUMLRTPackage.PACKAGE__PROTOCOL:
			getProtocols().clear();
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
		case UMLRTUMLRTPackage.PACKAGE__NESTED_PACKAGE:
			return !getNestedPackages().isEmpty();
		case UMLRTUMLRTPackage.PACKAGE__NESTING_PACKAGE:
			return getNestingPackage() != null;
		case UMLRTUMLRTPackage.PACKAGE__CAPSULE:
			return !getCapsules().isEmpty();
		case UMLRTUMLRTPackage.PACKAGE__PROTOCOL:
			return !getProtocols().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // UMLRTPackageImpl
