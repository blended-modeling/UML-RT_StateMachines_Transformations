/*******************************************************************************
* Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.codegen.statemachines.transformations

import java.util.Comparator
import org.eclipse.papyrusrt.xtumlrt.statemach.Transition
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTStateMachineUtil.*


class TransitionDepthComparator implements Comparator<Transition>
{

    /**
     * Sorts in descending order so that transitions whose source is deeper
     * appear before transitions with shallower source state.
     */
    override compare( Transition o1, Transition o2 )
    {
        val d1 = o1.cachedDepth
        val d2 = o2.cachedDepth
        if (d1 < d2) 1
        else if (d1 == d2) 0
        else -1
    }

}