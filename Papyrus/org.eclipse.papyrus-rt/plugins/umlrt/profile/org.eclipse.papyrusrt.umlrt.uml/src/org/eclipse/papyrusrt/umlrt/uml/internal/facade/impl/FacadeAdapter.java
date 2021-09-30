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

package org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl;

import java.util.Optional;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;

/**
 * Interface for the internal adapter that maintains the UML-RT façade for
 * elements in the UML model.
 */
public interface FacadeAdapter extends Adapter {
	/**
	 * Gets the existing facade adapter on an element.
	 * 
	 * @param notifier
	 *            usually an element in the UML model
	 * 
	 * @return maybe the {@code notifier}'s attached facade adapter
	 */
	static Optional<FacadeAdapter> getInstance(Notifier notifier) {
		return Optional.ofNullable(EcoreUtil.getExistingAdapter(notifier, FacadeAdapter.class))
				.filter(FacadeAdapter.class::isInstance).map(FacadeAdapter.class::cast);
	}

	/**
	 * Notifies the façade that an element it contains in the UML model is changed
	 * in a way that may affect its membership in the façade's derived references.
	 * 
	 * @param element
	 *            an element in the UML model that has had its UML-RT-ness changed
	 */
	void tickle(NamedElement element);

	/**
	 * Notifies the façade that an element in contains in the UML model has been
	 * excluded.
	 * 
	 * @param element
	 *            an excluded element in the UML model
	 */
	void excluded(Element element);

	/**
	 * Notifies the façade that an element in contains in the UML model has been
	 * re-inherited.
	 * 
	 * @param element
	 *            a re-inherited element in the UML model
	 */
	void reinherited(Element element);
}
