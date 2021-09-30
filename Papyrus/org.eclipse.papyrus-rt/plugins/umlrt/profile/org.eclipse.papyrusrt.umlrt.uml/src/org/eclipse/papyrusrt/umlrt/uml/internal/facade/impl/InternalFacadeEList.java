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

import org.eclipse.emf.ecore.util.InternalEList;

/**
 * An internal protocol for maintenance of list features
 * in façade objects.
 */
public interface InternalFacadeEList<E> extends InternalEList<E> {

	boolean facadeAdd(E object);

	void facadeAdd(int index, E object);

	E facadeSet(int index, E object);

	boolean facadeRemove(Object object);

	void facadeMove(int index, E object);
}
