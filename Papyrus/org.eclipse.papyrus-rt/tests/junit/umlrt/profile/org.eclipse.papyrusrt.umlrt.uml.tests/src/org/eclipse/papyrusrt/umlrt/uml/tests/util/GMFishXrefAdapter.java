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

package org.eclipse.papyrusrt.umlrt.uml.tests.util;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * An adapter that simulates, in part, the GMF {@code CrossReferenceAdapter}
 * to help provide a GMF-like environment for the tests.
 */
public class GMFishXrefAdapter extends ECrossReferenceAdapter {

	private GMFishXrefAdapter() {
		super();
	}

	public static GMFishXrefAdapter getInstance(Notifier notifier) {
		return (GMFishXrefAdapter) EcoreUtil.getExistingAdapter(notifier, GMFishXrefAdapter.class);
	}

	public static GMFishXrefAdapter demandInstance(Notifier notifier) {
		GMFishXrefAdapter result = getInstance(notifier);
		if (result == null) {
			result = new GMFishXrefAdapter();
			notifier.eAdapters().add(result);
		}
		return result;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == GMFishXrefAdapter.class;
	}

	/**
	 * Simulate the GMF adapter's scanning of cross-references to
	 * update resource <em>imports</em> and <em>exports</em>.
	 */
	@Override
	protected void handleContainment(Notification notification) {
		super.handleContainment(notification);

		Object notifier = notification.getNotifier();
		if (notifier instanceof ResourceSet) {
			// Ignore the resources list of a resource set
			return;
		}

		switch (notification.getEventType()) {
		case Notification.ADD:
			EObject newValue = (EObject) notification.getNewValue();
			if (newValue != null) {
				updateImportsAndExports(newValue, true);
			}
			break;
		case Notification.ADD_MANY:
			@SuppressWarnings("unchecked")
			Collection<? extends EObject> newValues = (Collection<? extends EObject>) notification.getNewValue();
			for (EObject next : newValues) {
				if (next != null) {
					updateImportsAndExports(next, true);
				}
			}
			break;
		case Notification.REMOVE:
			EObject oldValue = (EObject) notification.getOldValue();
			if (oldValue != null) {
				updateImportsAndExports(oldValue, false);
			}
			break;
		case Notification.REMOVE_MANY:
			@SuppressWarnings("unchecked")
			Collection<? extends EObject> oldValues = (Collection<? extends EObject>) notification.getOldValue();
			for (EObject next : oldValues) {
				if (next != null) {
					updateImportsAndExports(next, false);
				}
			}
			break;
		case Notification.SET:
		case Notification.UNSET:
		case Notification.RESOLVE:
			oldValue = (EObject) notification.getOldValue();
			if (oldValue != null) {
				updateImportsAndExports(oldValue, false);
			}
			newValue = (EObject) notification.getNewValue();
			if (newValue != null) {
				updateImportsAndExports(newValue, true);
			}
			break;
		default:
			// Pass
			break;
		}
	}

	/**
	 * Simulates the updating of resource imports and exports.
	 * 
	 * @param object
	 *            an object added/removed in a containment reference (or resource contents)
	 * @param {@code true} if the {@code object} was added; {@code false} if it was removed
	 */
	private void updateImportsAndExports(EObject object, boolean added) {
		GMFishXrefAdapter adapter = getInstance(object);

		if (added) {
			if (adapter != null) {
				// Scan its inverse references
				for (EStructuralFeature.Setting next : adapter.getInverseReferences(object)) {
					assertNotNull(next);
				}
			}
		} else {
			// Scan the navigable cross references
			for (EObject next : object.eCrossReferences()) {
				assertNotNull(next);
			}

			if (adapter != null) {
				// Scan its inverse references
				for (EStructuralFeature.Setting next : adapter.getInverseReferences(object)) {
					assertNotNull(next);
				}
			}
		}

		// Recursively
		if (adapter != null) {
			List<? extends EObject> contents = object.eContents();
			if (!resolve()) {
				contents = ((InternalEList<? extends EObject>) contents).basicList();
			}
			for (EObject next : contents) {
				updateImportsAndExports(next, added);
			}
		}
	}

}
