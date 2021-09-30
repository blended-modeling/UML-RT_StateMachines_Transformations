/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;

/**
 * Specialization of the containment list implementation that checks
 * the <em>internal container</em> for lists larger than the threshold
 * (4 elements as in EMF).
 */
public class ExtEObjectContainmentEList<E> extends EObjectContainmentEList<E> {

	private static final long serialVersionUID = 1L;

	public ExtEObjectContainmentEList(Class<?> dataClass, InternalEObject owner, int featureID) {
		super(dataClass, owner, featureID);
	}

	@Override
	public boolean contains(Object object) {
		if (isEObject()) {
			if (size > 4) {
				if (!isInstance(object)) {
					return false;
				} else if (isContainment()) {
					InternalEObject eObject = (InternalEObject) object;
					EObject eContainer = eObject.eInternalContainer();
					boolean result = (eContainer == owner) &&
							(hasNavigableInverse() ? eObject.eBaseStructuralFeatureID(eObject.eContainerFeatureID(), getInverseFeatureClass()) == getInverseFeatureID() : InternalEObject.EOPPOSITE_FEATURE_BASE - eObject.eContainerFeatureID() == getFeatureID());

					if (hasProxies() && !result && (eContainer == null) && (eObject.eDirectResource() != null)) {
						for (int i = 0; i < size; ++i) {
							EObject containedEObject = resolveProxy((EObject) data[i]);
							if (containedEObject == object) {
								return true;
							}
						}
					}

					return result;
				}

				// We can also optimize single valued reverse.
				//
				else if (hasNavigableInverse() && !hasManyInverse()) {
					Object opposite = ((EObject) object).eGet(getInverseEReference());
					if (opposite == owner) {
						return true;
					} else if ((opposite == null) || !((EObject) opposite).eIsProxy()) {
						return false;
					}
				}
			}

			boolean result = super.contains(object);

			if (hasProxies() && !result) {
				for (int i = 0; i < size; ++i) {
					EObject eObject = resolveProxy((EObject) data[i]);

					if (eObject == object) {
						return true;
					}
				}
			}

			return result;
		} else {
			return super.contains(object);
		}
	}

	//
	// Nested types
	//

	/**
	 * Resolving variant of the extension-compliant containment list.
	 */
	public static class Resolving<E> extends ExtEObjectContainmentEList<E> {
		private static final long serialVersionUID = 1L;

		public Resolving(Class<?> dataClass, InternalEObject owner, int featureID) {
			super(dataClass, owner, featureID);
		}

		@Override
		protected boolean hasProxies() {
			return true;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected E resolve(int index, E object) {
			return (E) resolve(index, (EObject) object);
		}

	}

	/**
	 * Unsettable variant of the extension-compliant containment list.
	 */
	public static class Unsettable<E> extends ExtEObjectContainmentEList<E> {

		private static final long serialVersionUID = 1L;

		protected boolean isSet;

		public Unsettable(Class<?> dataClass, InternalEObject owner, int featureID) {
			super(dataClass, owner, featureID);
		}

		@Override
		protected void didChange() {
			isSet = true;
		}

		@Override
		public boolean isSet() {
			return isSet;
		}

		@Override
		public void unset() {
			super.unset();

			if (isNotificationRequired()) {
				boolean oldIsSet = isSet;
				isSet = false;
				owner.eNotify(createNotification(Notification.UNSET, oldIsSet, false));
			} else {
				isSet = false;
			}
		}

		/**
		 * Resolving variant of the extension-compliant unsettable containment list.
		 */
		public static class Resolving<E> extends ExtEObjectContainmentEList.Unsettable<E> {
			private static final long serialVersionUID = 1L;

			public Resolving(Class<?> dataClass, InternalEObject owner, int featureID) {
				super(dataClass, owner, featureID);
			}

			@Override
			protected boolean hasProxies() {
				return true;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected E resolve(int index, E object) {
				return (E) resolve(index, (EObject) object);
			}
		}
	}
}
