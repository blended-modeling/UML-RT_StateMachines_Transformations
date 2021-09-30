/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.core.sync;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.papyrus.infra.sync.service.AbstractSyncTrigger;
import org.eclipse.papyrus.infra.sync.service.CascadeTriggers;
import org.eclipse.papyrus.infra.sync.service.ISyncAction;
import org.eclipse.papyrus.infra.sync.service.ISyncService;
import org.eclipse.uml2.uml.UMLPackage;

import com.google.common.base.Function;

/**
 * Sync trigger to initialize synchronization of UML-RT models.
 */
public class UMLRTSyncTrigger extends AbstractSyncTrigger {
	private static final EStructuralFeature[] SYNCHRONIZABLE_PACKAGE_CONTENTS = {
			UMLPackage.Literals.PACKAGE__NESTED_PACKAGE,
			UMLPackage.Literals.PACKAGE__OWNED_TYPE, };

	public UMLRTSyncTrigger() {
		super();
	}

	@Override
	public ISyncAction trigger(ISyncService syncService, Object object) {
		return new CascadeTriggers(cascadeFunction());
	}

	protected Function<Object, Iterable<? extends EObject>> cascadeFunction() {
		return new Function<Object, Iterable<? extends EObject>>() {
			@Override
			public Iterable<? extends EObject> apply(final Object input) {
				return new Iterable<EObject>() {
					private final Iterable<EObject> delegate = new EContentsEList<EObject>((EObject) input, SYNCHRONIZABLE_PACKAGE_CONTENTS);

					@Override
					public Iterator<EObject> iterator() {
						return delegate.iterator();
					}
				};
			}
		};
	}
}
