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

import java.util.Map;

import org.eclipse.emf.common.util.EMap;

/**
 * An internal protocol for maintenance of map features
 * in façade objects.
 */
public interface InternalFacadeEMap<K, V> extends EMap<K, V>, InternalFacadeEList<Map.Entry<K, V>> {

	V facadePut(K key, V value);

	V facadeRemoveKey(Object key);

}
