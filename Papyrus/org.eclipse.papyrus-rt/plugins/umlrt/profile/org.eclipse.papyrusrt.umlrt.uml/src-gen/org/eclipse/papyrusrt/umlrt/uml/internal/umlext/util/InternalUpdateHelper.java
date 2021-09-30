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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * <p>
 * A strategy for execution of "internal updates", which are modifications to
 * the internal realization of the view of inherited elements (in the extensions)
 * that need to be done lazily when models are loaded or otherwise read.
 * </p>
 * <p>
 * For example, in a transactional environment such as managed by the
 * EMF Transaction API's editing domain, internal updates may be triggered in a
 * context that is read-only, and it is necessary to account for this when
 * performing those updates.
 * </p>
 */
public interface InternalUpdateHelper {
	/** An internal update helper that just runs the update. */
	InternalUpdateHelper DEFAULT = (__, update) -> update.run();

	/**
	 * Performs an {@code update} in the given {@code context}.
	 * 
	 * @param context
	 *            an object that is the context of an update. It may be
	 *            the {@link EObject} being updated or some other related object,
	 *            such as a {@link Resource}, from which the relevant context must
	 *            be inferred
	 * @param update
	 *            encapsulation the update to be performed in the extensions
	 *            to the model
	 */
	void run(Object context, Runnable update);

	//
	// Nested types
	//

	/**
	 * Factory protocol for {@link InternalUpdateHelper}s.
	 */
	@FunctionalInterface
	interface Factory {
		/** A factory that just supplies the {@linkplain InternalUpdateHelper#DEFAULT default} helper. */
		Factory DEFAULT = __ -> InternalUpdateHelper.DEFAULT;

		/**
		 * Creates a new internal update helper for the given extension resource.
		 * 
		 * @param extensionResource
		 *            a resource in which UML extensions for UML-RT are managed
		 * 
		 * @return the most appropriate helper, or {@code null} if this factory does not
		 *         handle the EMF context of the resource (for example, if it isn't in
		 *         a transactional editing domain)
		 */
		InternalUpdateHelper create(Resource extensionResource);
	}
}
