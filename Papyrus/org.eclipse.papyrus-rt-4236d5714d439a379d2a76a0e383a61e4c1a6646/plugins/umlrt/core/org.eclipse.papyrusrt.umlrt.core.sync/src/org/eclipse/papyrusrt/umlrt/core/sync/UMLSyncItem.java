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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.sync.EObjectSyncItem;
import org.eclipse.uml2.uml.Element;

/**
 * A sync-item for UML model-to-model synchronizations.
 */
public class UMLSyncItem<M extends Element> extends EObjectSyncItem<M, EObject> {

	private final M model;

	public UMLSyncItem(M model, EObject backend) {
		super(backend);

		this.model = model;
	}

	@Override
	public M getModel() {
		return model;
	}

}
