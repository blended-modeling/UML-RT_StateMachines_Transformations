/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.statemachines.transformations

import org.eclipse.papyrusrt.codegen.CodeGenPlugin
import org.eclipse.papyrusrt.xtumlrt.statemach.CompositeState
import org.eclipse.papyrusrt.xtumlrt.statemach.SimpleState
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import org.eclipse.papyrusrt.xtumlrt.statemach.StatemachFactory

/**
 * This preprocessor adds a deep history point to every composite state that doesn't have one.
 * This is needed to ensure the correctness of the flattening transformation.
 *
 * <p>It performs the transformation in-place.
 *
 * @author Ernesto Posse
 */
class DeepHistoryAdder extends InPlaceTransformation
{

    public static val INSTANCE = new DeepHistoryAdder

    static def boolean applyTransformation
    (
        StateMachine stateMachine,
        TransformationContext context
    )
    {
        return INSTANCE.transformInPlace( stateMachine, context )
    }

    override boolean transformInPlace
    (
        StateMachine stateMachine,
        TransformationContext context
    )
    {
        setup( stateMachine, context )
        try
        {
            visit( stateMachine )
            return true
        }
        catch (Exception e)
        {
            CodeGenPlugin.error("[DeepHistoryPreprocessor] error preprocessing state machine", e)
            return false
        }
    } 

    dispatch def void visit( StateMachine stateMachine )
    {
        if (stateMachine.top !== null)
            visit( stateMachine.top )
    }

    dispatch def void visit( CompositeState state )
    {
        if (state.deepHistory === null)
        {
            state.deepHistory = StatemachFactory.eINSTANCE.createDeepHistory
            flattener.addNewElement( state.deepHistory )
        }
        state.substates.forEach[ visit(it) ]
    }

    dispatch def void visit( SimpleState state )
    {
    }

}