/*******************************************************************************
* Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.codegen.cpp.statemachines.flat

import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern
import org.eclipse.papyrusrt.codegen.statemachines.transformations.FlatteningTransformer
import org.eclipse.papyrusrt.xtumlrt.common.Capsule
import org.eclipse.papyrusrt.xtumlrt.common.StateMachine

class FlatteningCppTransformer
{
    var CppCodePattern cpp
    var Capsule capsuleContext
    val FlatModel2Cpp flat2cpp = new FlatModel2Cpp

    new ( CppCodePattern cpp, Capsule capsuleContext )
    {
        this.cpp = cpp
        this.capsuleContext = capsuleContext
    }

    def boolean transform( StateMachine stateMachine )
    {
        val flattener = new FlatteningTransformer

        // First we flatten the state machine (inheritance and nesting)
        val flatteningResult = flattener.transform( stateMachine )
        if (flatteningResult === null || flatteningResult.success == false)
            return false
        // Then we transform to C++
        // FlatModel2Cpp uses the discarded state list to construct the state enum
        val ctx =
            new FlatModel2Cpp.CppGenerationTansformationContext
                (
                    this.cpp,
                    this.capsuleContext,
                    flatteningResult.discardedStates
                )
        return flat2cpp.transformInPlace( flatteningResult.stateMachine, ctx )
    }

}