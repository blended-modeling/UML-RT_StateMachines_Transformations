/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.statemachines.transformations

import org.eclipse.papyrusrt.codegen.CodeGenPlugin
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.statemach.CompositeState
import org.eclipse.papyrusrt.xtumlrt.statemach.SimpleState
import org.eclipse.papyrusrt.xtumlrt.statemach.State
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import org.eclipse.papyrusrt.xtumlrt.statemach.Transition
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTStateMachineUtil.*

/**
 * This preprocessor traverses the state machine computing and caching the depth
 * of state machine elements. This is necessary for resolving the precedence of
 * transitions during code generation.
 *
 * @author Ernesto Posse 
 */
class DepthPreprocessor
{

    public static val INSTANCE = new DepthPreprocessor

    static def boolean doCacheAllDepths( StateMachine stateMachine )
    {
        return INSTANCE.cacheAllDepths( stateMachine )
    }

    def boolean cacheAllDepths( StateMachine stateMachine )
    {
        try
        {
            visit( stateMachine )
            return true
        }
        catch (Exception e)
        {
            CodeGenPlugin.error("[QualifiedNamePreprocessor] error preprocessing state machine", e)
            return false
        }
    } 

    dispatch def void visit( StateMachine stateMachine )
    {
        stateMachine.getCachedDepth
        if (stateMachine.top !== null)
            visit( stateMachine.top )
    }

    dispatch def void visit( SimpleState state )
    {
        visitState( state )
    }

    dispatch def void visit( CompositeState state )
    {
        visitState( state )
        state.initial?.getCachedDepth
        state.deepHistory.getCachedDepth
        state.choicePoints.forEach[ it.getCachedDepth ]
        state.junctionPoints.forEach[ it.getCachedDepth ]
        state.substates.forEach[ visit(it) ]
        state.transitions.forEach[ visit(it) ]
    }
    
    protected def visitState( State state )
    {
        state.getCachedDepth
        state.entryPoints.forEach[ it.getCachedDepth ]
        state.exitPoints.forEach[ it.getCachedDepth ]
    }

    dispatch def void visit( Transition transition )
    {
        transition.getCachedDepth
        transition.triggers.forEach[ it.getCachedDepth ]
        transition.guard?.getCachedDepth
        transition.actionChain?.getCachedDepth
    }

    dispatch def void visit( NamedElement element )
    {
        element.getCachedDepth
    }

}