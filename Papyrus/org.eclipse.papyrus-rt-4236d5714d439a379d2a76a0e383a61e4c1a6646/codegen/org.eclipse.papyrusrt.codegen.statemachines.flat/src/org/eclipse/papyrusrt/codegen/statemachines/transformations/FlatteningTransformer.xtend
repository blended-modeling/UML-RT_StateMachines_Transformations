/*******************************************************************************
* Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.codegen.statemachines.transformations

import java.util.Collection

import org.eclipse.papyrusrt.xtumlrt.common.State
import org.eclipse.papyrusrt.xtumlrt.common.StateMachine
import org.eclipse.papyrusrt.codegen.utils.QualifiedNames
import org.eclipse.papyrusrt.codegen.CodeGenPlugin
import org.eclipse.papyrusrt.codegen.utils.XTUMLRTElementPrinter

class FlatteningTransformer
{

    val qualNamesPreProcessor   = new QualifiedNamePreprocessor
    val depthPreProcessor       = new DepthPreprocessor
    val deepHistoryAdder        = new DeepHistoryAdder
    val inheritanceFlattener    = new StateMachineInheritanceFlattener
    val nestingFlattener        = new StateNestingFlattener

    def TransformationResult transform( StateMachine stateMachine )
    {
        var result = false

        CodeGenPlugin.debug( "State machine before generating:")
        CodeGenPlugin.debug( [ XTUMLRTElementPrinter.str( stateMachine ) ] )

        // We reset the name counter so that freshly generated names are always
        // generated deterministically.
        QualifiedNames.resetCounter

        // We flatten (expand) the inheritance hierarchy
        val expandedStateMachine = inheritanceFlattener.transform( stateMachine, null )
        result = expandedStateMachine !== null
        if (!result) return new TransformationResult( result, expandedStateMachine, null )

        CodeGenPlugin.debug( "State machine after flattening inheritance:")
        CodeGenPlugin.debug( [ XTUMLRTElementPrinter.str( expandedStateMachine ) ] )

        // We add a deep history pseudostate to every composite state that
        // doesn't have one. This simplifies the flattening.
        result = deepHistoryAdder.transformInPlace( expandedStateMachine, null )
        if (!result) return new TransformationResult( result, expandedStateMachine, null )

        CodeGenPlugin.debug( "State machine after adding history:")
        CodeGenPlugin.debug( [ XTUMLRTElementPrinter.str( expandedStateMachine ) ] )

        // We also precompute the nesting depth of elements in order to break
        // ties between transitions enabled in a given state.
        result = depthPreProcessor.cacheAllDepths( expandedStateMachine )
        if (!result) return new TransformationResult( result, expandedStateMachine, null )

        // We precompute the fully qualified names for all elements of the
        // state machine so that they can be used by the C++ generator for the
        // corresponding C++ methods.
        result = qualNamesPreProcessor.cacheAllNames( expandedStateMachine )
        if (!result) return new TransformationResult( result, expandedStateMachine, null )

        CodeGenPlugin.debug( "State machine after caching names:")
        CodeGenPlugin.debug( [ XTUMLRTElementPrinter.str( expandedStateMachine ) ] )

        // StateNestingFlattener adds all discarded composite states to this
        // list, so the C++ generator can use it to create enumerators for them
        var Collection<State> discardedStates = newArrayList
        val ctx1 = new StateNestingFlattener.FlatteningTransformationContext( discardedStates )

        // Now we do the actual state flattening
        result = nestingFlattener.transformInPlace( expandedStateMachine, ctx1 )
        if (!result) return new TransformationResult( result, expandedStateMachine, discardedStates )

        CodeGenPlugin.debug( "State machine after flattening:")
        CodeGenPlugin.debug( [ XTUMLRTElementPrinter.str( expandedStateMachine ) ] )

        return new TransformationResult( result, expandedStateMachine, discardedStates )
    }

}
