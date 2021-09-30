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

package org.eclipse.papyrusrt.umlrt.uml.internal.umlext.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.DelegatingEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrusrt.umlrt.uml.internal.operations.ElementRTOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtFactory;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.impl.UMLRTUMLPlugin;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.operations.ElementOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.operations.NamespaceOperations;
import org.eclipse.papyrusrt.umlrt.uml.internal.util.NotificationForwarder;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * A holder to be composed into any {@link Element} that needs to be
 * able to contain transient redefinable elements in any of the UML Extension
 * features intended for that purpose. Various {@link InternalEObject} APIs
 * are delegated to the holder.
 */
public class ExtensionHolder extends UMLUtil {

	public static final int UML_EXTENSION_FEATURE_BASE = -2048;

	private static final Map<Class<?>, Function<InternalEObject, ExtensionHolder>> holderAccess = new HashMap<>();

	private final InternalEObject owner;
	private InternalEObject extension;

	private boolean suppressForwarding;

	/**
	 * Initializes me with the object that I extend.
	 *
	 * @param my
	 *            extended object
	 */
	public ExtensionHolder(InternalEObject owner) {
		super();

		this.owner = owner;
	}

	/**
	 * Gets the extension holder of an UML-RT {@code object}.
	 * 
	 * @param object
	 *            an object in an UML-RT model
	 * @return its extension holder
	 * 
	 * @throws NullPointerException
	 *             if the {@code object} is {@code null}
	 * @throws IllegalArgumentException
	 *             if the {@code object} is not of a
	 *             type that can have an extension holder
	 */
	public static ExtensionHolder get(InternalEObject object) {
		return holderAccess.computeIfAbsent(object.getClass(), ExtensionHolder::computeHolderAccess)
				.apply(object);
	}

	private static Function<InternalEObject, ExtensionHolder> computeHolderAccess(Class<?> class_) {
		Function<InternalEObject, ExtensionHolder> result;

		try {
			Field holderField = class_.getDeclaredField("extension");
			holderField.setAccessible(true);
			result = owner -> {
				try {
					return (ExtensionHolder) holderField.get(owner);
				} catch (Exception e) {
					UMLRTUMLPlugin.INSTANCE.log(e);
					// Don't try me again because something has gone seriously wrong
					holderAccess.put(class_, __ -> null);
					return null;
				}
			};
		} catch (Exception e) {
			throw new IllegalArgumentException("Not extensible: " + class_, e); //$NON-NLS-1$
		}

		return result;
	}

	/**
	 * Obtains the UML extension metaclass corresponding to the
	 * specified UML metaclass.
	 * 
	 * @param umlMetaclass
	 *            the core UML metaclass that is extended
	 * 
	 * @return the corresponding extension metaclass
	 */
	protected static EClass getExtensionEClass(EClass umlMetaclass) {
		return (EClass) ExtUMLExtPackage.eINSTANCE.getEClassifier(umlMetaclass.getName());
	}

	/**
	 * Obtains the UML extension metaclass for my owner.
	 * 
	 * @return my UML extension metaclass
	 */
	protected final EClass getExtensionEClass() {
		return getExtensionEClass(owner.eClass());
	}

	/**
	 * Obtains the Java interface type of the extension metaclass
	 * for my owner.
	 * 
	 * @return my Java extension type
	 */
	protected final Class<?> getExtensionClass() {
		return getExtensionEClass().getInstanceClass();
	}

	/**
	 * Obtains the extension object for my owner, creating it on
	 * demand, if necessary.
	 * 
	 * @return my extension instance (never {@code null})
	 */
	protected final InternalEObject demandExtension() {
		if (extension == null) {
			extension = (InternalEObject) ExtUMLExtFactory.eINSTANCE.create(getExtensionEClass());

			// Add it to the extension extent before linking it to me so that
			// this linkage may be recorded for undo/redo
			ExtensionResource extent = ExtensionResource.demandExtensionExtent(owner);
			extent.run(owner, () -> {
				extent.getContents().add(extension);
				((ExtElement) extension).setExtendedElement((Element) owner);
			});

			// Forward notifications
			extension.eAdapters().add(new AdapterImpl() {
				@Override
				public void notifyChanged(Notification msg) {
					// Don't ever forward the extendedElement reference because it isn't
					// an extension feature, and we don't want a ChangeDescription to
					// try to manipulate it via the UML element
					if (!suppressForwarding
							&& (msg.getFeature() != ExtUMLExtPackage.Literals.ELEMENT__EXTENDED_ELEMENT)) {

						NotificationForwarder.forward(owner, msg);
					}
				}
			});
		}

		return extension;
	}

	/**
	 * Creates my extension instance, if it does not already exist.
	 */
	public void createExtension() {
		demandExtension();
	}

	/**
	 * Sets my extension instance.
	 * 
	 * @param extension
	 *            my extension
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code extension} is not an extension of my owner
	 */
	public void setExtension(EObject extension) {
		if (this.extension == extension) {
			// Nothing to do
			return;
		}

		if ((extension != null) && !(extension instanceof ExtElement)) {
			throw new IllegalArgumentException("not an extension: " + extension);
		}

		ExtElement ext = (ExtElement) extension;
		if ((ext != null) && (ext.getExtendedElement() != owner)) {
			throw new IllegalArgumentException("not an extension of owner: " + extension);
		}

		if ((this.extension != null) && (extension != null)) {
			// The extension was prematurely demand-created (usually by application
			// of a change description), so forget it and take up the new one,
			// which should end up taking over all of the previous one's contents
			EcoreUtil.remove(this.extension);
		}

		this.extension = (InternalEObject) ext;

		if (ext != null) {
			// Discover what we've got, to notify through the extended element
			for (EReference containment : ext.eClass().getEAllContainments()) {
				// Don't notify on computed containments that wouldn't notify, anyways
				if (!containment.isDerived() && containment.isChangeable() && ext.eIsSet(containment)) {
					if (containment.isMany()) {
						List<?> value = (List<?>) ext.eGet(containment);
						switch (value.size()) {
						case 0:
							// Pass
							break;
						case 1:
							NotificationForwarder.forward(owner,
									new ENotificationImpl(this.extension, Notification.ADD, containment, null, value.get(0), 0));
							break;
						default:
							NotificationForwarder.forward(owner,
									new ENotificationImpl(this.extension, Notification.ADD_MANY, containment, null, value, 0));
							break;
						}
					} else {
						Object value = ext.eGet(containment);
						if (value != null) {
							NotificationForwarder.forward(owner,
									new ENotificationImpl(this.extension, Notification.SET, containment, null, value));
						}
					}
				}
			}
		}
	}

	/**
	 * Destroys my extension instance, if it exists.
	 */
	public void destroyExtension() {
		if (extension != null) {
			ElementRTOperations.delete(extension);
			extension = null;
		}
	}

	/**
	 * Queries whether the extension instance exists.
	 * 
	 * @return whether the extension exists
	 */
	public boolean hasExtension() {
		return extension != null;
	}

	/**
	 * Obtains the extension element, if it exists.
	 * 
	 * @param metaclass
	 *            the type of extension to expect
	 * @return the extension, or {@code null} if it does not exist
	 * 
	 * @see #hasExtension()
	 */
	public <T extends ExtElement> T getExtension(Class<T> metaclass) {
		return metaclass.isInstance(extension) ? metaclass.cast(extension) : null;
	}

	/**
	 * Suppresses all forwarding of notifications from extension features
	 * via my extended UML model element for the duration of the given
	 * {@code action}.
	 * 
	 * @param action
	 *            an action to execute without notification forwarding
	 */
	public void suppressForwardingWhile(Runnable action) {
		boolean wasSuppressingForwarding = suppressForwarding;
		suppressForwarding = true;
		try {
			action.run();
		} finally {
			suppressForwarding = wasSuppressingForwarding;
		}
	}

	/**
	 * Obtains the extension feature identified by the specified ID for
	 * the given UML metaclass.
	 * 
	 * @param eClass
	 *            an extended UML metaclass (not the extension metaclass)
	 * @param featureID
	 *            the ID of an extension feature, in terms of the extended
	 *            UML metaclass (not the extension metaclass)
	 * 
	 * @return the extension feature
	 */
	public static EStructuralFeature getExtensionFeature(EClass eClass, int featureID) {
		if (featureID <= UML_EXTENSION_FEATURE_BASE) {
			featureID = UML_EXTENSION_FEATURE_BASE - featureID;
		}

		return getExtensionEClass(eClass).getEStructuralFeature(featureID);
	}

	/**
	 * Obtains the contents of the given {@code object}, including any extension
	 * features that are containments.
	 * 
	 * @param object
	 *            an extended UML model element
	 * 
	 * @return its contents, including extension containments
	 */
	public <T> EList<T> getContents(InternalEObject object) {
		EStructuralFeature[] realContainments = ((EClassImpl.FeatureSubsetSupplier) object.eClass().getEAllStructuralFeatures()).containments();
		EStructuralFeature[] extContainments = (extension == null)
				? null
				: ((EClassImpl.FeatureSubsetSupplier) extension.eClass().getEAllStructuralFeatures()).containments();

		EStructuralFeature[] allContainments = ((extContainments == null) || (extContainments.length == 0))
				? realContainments
				: ((realContainments == null) || (realContainments.length == 0))
						? extContainments
						: concat(realContainments, extContainments);

		return (allContainments == null) ? EContentsEList.<T> emptyContentsEList() : new EContentsEList<>(object, allContainments);
	}

	<T> T[] concat(T[] first, T[] second) {
		@SuppressWarnings("unchecked")
		T[] result = (T[]) Array.newInstance(first.getClass().getComponentType(), first.length + second.length);
		System.arraycopy(first, 0, result, 0, first.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	/**
	 * Computes the derived structural feature ID for a feature of my extended
	 * element, which feature may be a core UML feature or an extension.
	 * 
	 * @param eStructuralFeature
	 *            an UML feature or extension feature
	 * @return its derived feature ID for my owner
	 */
	public int getDerivedStructuralFeatureID(EStructuralFeature eStructuralFeature) {
		int result;

		Class<?> containerClass = eStructuralFeature.getContainerClass();
		if (containerClass == null) {
			result = owner.eClass().getFeatureID(eStructuralFeature);
		} else if (containerClass.isAssignableFrom(getExtensionClass())) {
			int featureID = (extension == null)
					? getExtensionEClass().getFeatureID(eStructuralFeature)
					: extension.eDerivedStructuralFeatureID(eStructuralFeature.getFeatureID(), containerClass);
			result = UML_EXTENSION_FEATURE_BASE - featureID;
		} else {
			assert owner.eClass().getEAllStructuralFeatures().contains(eStructuralFeature) : "The feature '" + eStructuralFeature.getName() + "' is not a valid feature";
			result = owner.eDerivedStructuralFeatureID(eStructuralFeature.getFeatureID(), containerClass);
		}

		return result;
	}

	/**
	 * Queries whether the given feature is set for my owner.
	 * 
	 * @param eFeature
	 *            a feature, which may be a core UML feature or an extension
	 * 
	 * @return whether the feature is set in my owner
	 */
	public boolean isSet(EStructuralFeature eFeature) {
		boolean result;

		Class<?> containerClass = eFeature.getContainerClass();
		if (containerClass.isAssignableFrom(getExtensionClass())) {
			result = (extension != null) && extension.eIsSet(extension.eDerivedStructuralFeatureID(eFeature.getFeatureID(), containerClass));
		} else {
			throw new IllegalArgumentException(String.format("The feature '%s' is not a valid feature", eFeature.getName()));
		}

		return result;
	}

	/**
	 * Queries whether the given feature ID is set for my owner.
	 * 
	 * @param featureID
	 *            the derived (for my owner) ID of a feature, which
	 *            may be a core UML feature or an extension
	 * 
	 * @return whether the feature is set in my owner
	 */
	public boolean isSet(int featureID) {
		featureID = UML_EXTENSION_FEATURE_BASE - featureID;
		return (extension != null) && extension.eIsSet(featureID);
	}

	/**
	 * Obtains the value of the given feature for my owner.
	 * 
	 * @param eFeature
	 *            a feature, which may be a core UML feature or an extension
	 * @param resolve
	 *            whether to resolve any proxies
	 * 
	 * @return the value of the feature for my owner
	 */
	public Object get(EStructuralFeature eFeature, boolean resolve) {
		Object result;

		Class<?> containerClass = eFeature.getContainerClass();
		if (containerClass.isAssignableFrom(getExtensionClass())) {
			if (extension != null) {
				result = extension.eGet(eFeature, resolve);
			} else if (eFeature.isMany()) {
				result = (eFeature == ExtUMLExtPackage.Literals.ELEMENT__EXCLUDED_ELEMENT)
						// Special case for this, which is computed and does not require the extension
						? ElementOperations.getExcludedElements((Element) owner)
						: (eFeature == ExtUMLExtPackage.Literals.NAMESPACE__EXCLUDED_MEMBER)
								// Special case for this, which is computed and does not require the extension
								? NamespaceOperations.getExcludedMembers((Namespace) owner)
								: new DeferredEList<>(owner, eFeature);
			} else {
				result = (eFeature == ExtUMLExtPackage.Literals.ELEMENT__EXTENDED_ELEMENT)
						// Special case for this, which is computed and does not require the extension
						? owner
						: null;
			}
		} else {
			throw new IllegalArgumentException(String.format("The feature '%s' is not a valid feature", eFeature.getName()));
		}

		return result;
	}

	/**
	 * Obtains the value of the given feature for my owner.
	 * 
	 * @param featureID
	 *            the derived (for my owner) ID of a feature, which
	 *            may be a core UML feature or an extension
	 * @param resolve
	 *            whether to resolve any proxies
	 * 
	 * @return the value of the feature for my owner
	 */
	public Object get(int featureID, boolean resolve) {
		featureID = UML_EXTENSION_FEATURE_BASE - featureID;
		return extension.eGet(featureID, resolve, true);
	}

	/**
	 * Sets the value of the given feature for my owner.
	 * 
	 * @param eFeature
	 *            a feature, which may be a core UML feature or an extension
	 * @param newValue
	 *            the new value to set in the feature
	 */
	public void set(EStructuralFeature eFeature, Object newValue) {
		Class<?> containerClass = eFeature.getContainerClass();
		if (containerClass.isAssignableFrom(getExtensionClass())) {
			int featureID = demandExtension().eDerivedStructuralFeatureID(eFeature.getFeatureID(), containerClass);
			extension.eSet(featureID, newValue);
		} else {
			throw new IllegalArgumentException(String.format("The feature '%s' is not a valid changeable feature", eFeature.getName()));
		}
	}

	/**
	 * Sets the value of the given feature for my owner.
	 * 
	 * @param featureID
	 *            the derived (for my owner) ID of a feature, which
	 *            may be a core UML feature or an extension
	 * @param newValue
	 *            the new value to set in the feature
	 */
	public void set(int featureID, Object newValue) {
		featureID = UML_EXTENSION_FEATURE_BASE - featureID;
		demandExtension().eSet(featureID, newValue);
	}

	/**
	 * Unsets the value of the given feature for my owner.
	 * 
	 * @param eFeature
	 *            a feature, which may be a core UML feature or an extension
	 */
	public void unset(EStructuralFeature eFeature) {
		Class<?> containerClass = eFeature.getContainerClass();
		if (containerClass.isAssignableFrom(getExtensionClass())) {
			if (extension != null) {
				extension.eUnset(extension.eDerivedStructuralFeatureID(eFeature.getFeatureID(), containerClass));
			}
		} else {
			throw new IllegalArgumentException(String.format("The feature '%s' is not a valid changeable feature", eFeature.getName()));
		}
	}

	/**
	 * Unsets the value of the given feature for my owner.
	 * 
	 * @param featureID
	 *            the derived (for my owner) ID of a feature, which
	 *            may be a core UML feature or an extension
	 */
	public void unset(int featureID) {
		featureID = UML_EXTENSION_FEATURE_BASE - featureID;
		if (extension != null) {
			extension.eUnset(featureID);
		}
	}

	/**
	 * Obtains the setting of the given feature for my owner.
	 * 
	 * @param eFeature
	 *            a feature, which may be a core UML feature or an extension
	 */
	public EStructuralFeature.Setting setting(EStructuralFeature eFeature) {
		EStructuralFeature.Setting result;

		Class<?> containerClass = eFeature.getContainerClass();
		if (containerClass.isAssignableFrom(getExtensionClass())) {
			if (extension != null) {
				EStructuralFeature.Setting setting = extension.eSetting(eFeature);
				result = new EStructuralFeature.Setting() {

					@Override
					public EObject getEObject() {
						return owner;
					}

					@Override
					public EStructuralFeature getEStructuralFeature() {
						return setting.getEStructuralFeature();
					}

					@Override
					public Object get(boolean resolve) {
						return setting.get(resolve);
					}

					@Override
					public boolean isSet() {
						return setting.isSet();
					}

					@Override
					public void set(Object newValue) {
						setting.set(newValue);
					}

					@Override
					public void unset() {
						setting.unset();
					}
				};
			} else if (eFeature.isMany()) {
				result = new DeferredEList<>(owner, eFeature);
			} else {
				result = new DeferredSetting(owner, eFeature);
			}
		} else {
			throw new IllegalArgumentException(String.format("The feature '%s' is not a valid feature", eFeature.getName()));
		}

		return result;
	}

	//
	// Nested types
	//

	/**
	 * A deferred list implementing the feature setting of a multi-valued extension
	 * feature for an object that does not yet have the extension created. Any
	 * operation that would modify the feature will cause the extension to be created
	 * and this setting then to delegate to the extension's actual feature setting.
	 */
	private final class DeferredEList<E> extends DelegatingEList<E>
			implements InternalEList.Unsettable<E>, EStructuralFeature.Setting {

		private static final long serialVersionUID = 1L;

		private InternalEObject owner;
		private final EStructuralFeature feature;
		private List<E> delegate = ECollections.emptyEList();

		DeferredEList(InternalEObject owner, EStructuralFeature feature) {
			super();

			this.owner = owner;
			this.feature = feature;
		}

		@Override
		public EObject getEObject() {
			return owner;
		}

		@Override
		public EStructuralFeature getEStructuralFeature() {
			return feature;
		}

		//
		// Delegation
		//

		@Override
		protected List<E> delegateList() {
			return delegate;
		}

		@SuppressWarnings("unchecked")
		void demandList() {
			if (owner == ExtensionHolder.this.owner) {
				owner = demandExtension();
				delegate = (EList<E>) owner.eGet(getEStructuralFeature());
			}
		}

		@Override
		protected void delegateAdd(E object) {
			demandList();
			super.delegateAdd(object);
		}

		@Override
		protected void delegateAdd(int index, E object) {
			demandList();
			super.delegateAdd(index, object);
		}

		@Override
		protected void delegateClear() {
			demandList();
			super.delegateClear();
		}

		@Override
		protected E delegateRemove(int index) {
			demandList();
			return super.delegateRemove(index);
		}

		@Override
		protected E delegateSet(int index, E object) {
			demandList();
			return super.delegateSet(index, object);
		}

		@Override
		protected E delegateMove(int targetIndex, int sourceIndex) {
			demandList();
			return super.delegateMove(targetIndex, sourceIndex);
		}

		//
		// Internals
		//

		@Override
		public List<E> basicList() {
			return super.basicList();
		}

		@Override
		public Iterator<E> basicIterator() {
			return super.basicIterator();
		}

		@Override
		public ListIterator<E> basicListIterator() {
			return super.basicListIterator();
		}

		@Override
		public ListIterator<E> basicListIterator(int index) {
			return super.basicListIterator(index);
		}

		@Override
		public E basicGet(int index) {
			return super.basicGet(index);
		}

		@Override
		public Object[] basicToArray() {
			return (delegate instanceof InternalEList<?>)
					? ((InternalEList<E>) delegate).basicToArray()
					: delegate.toArray();
		}

		@Override
		public <T> T[] basicToArray(T[] array) {
			return (delegate instanceof InternalEList<?>)
					? ((InternalEList<E>) delegate).basicToArray(array)
					: delegate.toArray(array);
		}

		@Override
		public int basicIndexOf(Object object) {
			return (delegate instanceof InternalEList<?>)
					? ((InternalEList<E>) delegate).basicIndexOf(object)
					: delegate.indexOf(object);
		}

		@Override
		public int basicLastIndexOf(Object object) {
			return (delegate instanceof InternalEList<?>)
					? ((InternalEList<E>) delegate).basicLastIndexOf(object)
					: delegate.lastIndexOf(object);
		}

		@Override
		public boolean basicContains(Object object) {
			return (delegate instanceof InternalEList<?>)
					? ((InternalEList<E>) delegate).basicContains(object)
					: delegate.contains(object);
		}

		@Override
		public boolean basicContainsAll(Collection<?> collection) {
			return (delegate instanceof InternalEList<?>)
					? ((InternalEList<E>) delegate).basicContainsAll(collection)
					: delegate.containsAll(collection);
		}

		@Override
		public NotificationChain basicRemove(Object object, NotificationChain notifications) {
			demandList();
			return ((InternalEList<E>) delegate).basicRemove(object, notifications);
		}

		@Override
		public NotificationChain basicAdd(E object, NotificationChain notifications) {
			demandList();
			return ((InternalEList<E>) delegate).basicAdd(object, notifications);
		}

		@Override
		public Object get(boolean resolve) {
			return (delegate instanceof EStructuralFeature.Setting)
					? ((EStructuralFeature.Setting) delegate).get(resolve)
					: this;
		}

		@Override
		public void set(Object newValue) {
			demandList();
			((EStructuralFeature.Setting) delegate).set(newValue);
		}

		@Override
		public boolean isSet() {
			return (delegate instanceof EStructuralFeature.Setting)
					? ((EStructuralFeature.Setting) delegate).isSet()
					: false;
		}

		@Override
		public void unset() {
			demandList();
			((EStructuralFeature.Setting) delegate).unset();
		}
	}

	/**
	 * A deferred setting implementing the feature setting of a single-valued extension
	 * feature for an object that does not yet have the extension created. Any
	 * operation that would modify the feature will cause the extension to be created
	 * and this setting then to delegate to the extension's actual feature setting.
	 */
	private final class DeferredSetting implements EStructuralFeature.Setting {
		private InternalEObject owner;
		private final EStructuralFeature feature;

		DeferredSetting(InternalEObject owner, EStructuralFeature feature) {
			super();

			this.owner = owner;
			this.feature = feature;
		}

		@Override
		public EObject getEObject() {
			return owner;
		}

		@Override
		public EStructuralFeature getEStructuralFeature() {
			return feature;
		}

		@Override
		public Object get(boolean resolve) {
			return (extension == null)
					? feature.getDefaultValue()
					: extension.eGet(feature, resolve);
		}

		@Override
		public void set(Object newValue) {
			demandExtension().eSet(feature, newValue);
		}

		@Override
		public boolean isSet() {
			return (extension != null) && extension.eIsSet(feature);
		}

		@Override
		public void unset() {
			if (extension != null) {
				extension.eUnset(feature);
			}
		}
	}

}
