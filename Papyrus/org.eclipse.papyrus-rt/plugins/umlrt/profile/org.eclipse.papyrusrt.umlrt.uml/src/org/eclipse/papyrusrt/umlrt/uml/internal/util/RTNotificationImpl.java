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

package org.eclipse.papyrusrt.umlrt.uml.internal.util;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * Custom notification used for features that in UML are not unsettable but
 * that are in UML-RT because they are inheritable (inherited in the unset state).
 */
public class RTNotificationImpl extends ENotificationImpl {

	private final boolean isSetChange;

	public RTNotificationImpl(InternalEObject notifier, int eventType, EStructuralFeature feature, Object oldValue, Object newValue, boolean isSetChange) {
		super(notifier, eventType, feature, oldValue, newValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, int featureID, Object oldValue, Object newValue, boolean isSetChange) {
		super(notifier, eventType, featureID, oldValue, newValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, int featureID, boolean oldBooleanValue, boolean newBooleanValue, boolean isSetChange) {
		super(notifier, eventType, featureID, oldBooleanValue, newBooleanValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, EStructuralFeature feature, boolean oldBooleanValue, boolean newBooleanValue, boolean isSetChange) {
		super(notifier, eventType, feature, oldBooleanValue, newBooleanValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, int featureID, byte oldByteValue, byte newByteValue, boolean isSetChange) {
		super(notifier, eventType, featureID, oldByteValue, newByteValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, EStructuralFeature feature, byte oldByteValue, byte newByteValue, boolean isSetChange) {
		super(notifier, eventType, feature, oldByteValue, newByteValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, int featureID, char oldCharValue, char newCharValue, boolean isSetChange) {
		super(notifier, eventType, featureID, oldCharValue, newCharValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, EStructuralFeature feature, char oldCharValue, char newCharValue, boolean isSetChange) {
		super(notifier, eventType, feature, oldCharValue, newCharValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, int featureID, double oldDoubleValue, double newDoubleValue, boolean isSetChange) {
		super(notifier, eventType, featureID, oldDoubleValue, newDoubleValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, EStructuralFeature feature, double oldDoubleValue, double newDoubleValue, boolean isSetChange) {
		super(notifier, eventType, feature, oldDoubleValue, newDoubleValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, int featureID, float oldFloatValue, float newFloatValue, boolean isSetChange) {
		super(notifier, eventType, featureID, oldFloatValue, newFloatValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, EStructuralFeature feature, float oldFloatValue, float newFloatValue, boolean isSetChange) {
		super(notifier, eventType, feature, oldFloatValue, newFloatValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, int featureID, int oldIntValue, int newIntValue, boolean isSetChange) {
		super(notifier, eventType, featureID, oldIntValue, newIntValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, EStructuralFeature feature, int oldIntValue, int newIntValue, boolean isSetChange) {
		super(notifier, eventType, feature, oldIntValue, newIntValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, int featureID, long oldLongValue, long newLongValue, boolean isSetChange) {
		super(notifier, eventType, featureID, oldLongValue, newLongValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, EStructuralFeature feature, long oldLongValue, long newLongValue, boolean isSetChange) {
		super(notifier, eventType, feature, oldLongValue, newLongValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, int featureID, short oldShortValue, short newShortValue, boolean isSetChange) {
		super(notifier, eventType, featureID, oldShortValue, newShortValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, EStructuralFeature feature, short oldShortValue, short newShortValue, boolean isSetChange) {
		super(notifier, eventType, feature, oldShortValue, newShortValue, isSetChange);

		this.isSetChange = isSetChange;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, EStructuralFeature feature, Object oldValue, Object newValue, int position, boolean wasSet) {
		super(notifier, eventType, feature, oldValue, newValue, position, wasSet);

		this.isSetChange = !wasSet;
	}

	public RTNotificationImpl(InternalEObject notifier, int eventType, int featureID, Object oldValue, Object newValue, int position, boolean wasSet) {
		super(notifier, eventType, featureID, oldValue, newValue, position, wasSet);

		this.isSetChange = !wasSet;
	}

	/**
	 * We are implicitly an unsettable feature, no matter what the Ecore declares.
	 */
	@Override
	protected boolean isFeatureUnsettable() {
		return true;
	}

	/**
	 * We can set an unset (inherited) list at some non-negative index, which breaks the
	 * superclass's encoding of {@code wasSet} in the {@code position} field.
	 */
	@Override
	public boolean wasSet() {
		switch (eventType) {
		case SET:
			return !isSetChange;
		case UNSET:
			return isSetChange;
		default:
			return super.wasSet();
		}
	}

}
