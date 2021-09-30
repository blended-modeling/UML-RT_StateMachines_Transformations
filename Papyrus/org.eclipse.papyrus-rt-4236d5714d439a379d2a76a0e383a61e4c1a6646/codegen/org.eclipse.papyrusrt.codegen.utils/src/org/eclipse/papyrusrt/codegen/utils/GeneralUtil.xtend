/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.utils

import org.eclipse.emf.common.util.EList

/**
 * @author eposse
 */
class GeneralUtil
{

    static def <T> void addIfNotNull(EList<T> elist, T eobj)
    {
        if (elist !== null && eobj !== null)
        {
            elist.add( eobj )
        }
    }

    static def <T> void addIfNotPresent(EList<T> elist, T eobj)
    {
        if (elist !== null && eobj !== null && !elist.contains(eobj))
        {
            elist.add( eobj )
        }
    }

}