/*******************************************************************************
* Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.codegen.cpp.statemachines.flat

import java.util.Comparator
import org.eclipse.papyrusrt.xtumlrt.common.ActionChain
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import static extension org.eclipse.papyrusrt.codegen.utils.XTUMLRTUtil.*
import static extension org.eclipse.papyrusrt.codegen.utils.QualifiedNames.*

class ChainNameComparator implements Comparator<ActionChain>
{

    override compare( ActionChain o1, ActionChain o2 )
    {
        (o1.owner as NamedElement).cachedFullName.compareTo( (o2.owner as NamedElement).cachedFullName )
    }

}