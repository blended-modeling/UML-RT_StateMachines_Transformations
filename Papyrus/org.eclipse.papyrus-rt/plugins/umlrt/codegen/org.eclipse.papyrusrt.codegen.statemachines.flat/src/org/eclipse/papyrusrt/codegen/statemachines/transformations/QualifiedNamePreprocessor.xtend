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
import org.eclipse.papyrusrt.xtumlrt.statemach.Trigger
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTTrigger
import static extension org.eclipse.papyrusrt.xtumlrt.util.QualifiedNames.*

/**
 * This visitor goes through the state machine before flattening to pre-compute the fully
 * qualified names of all elements. This is needed because the flattening process changes, by
 * definition the containment relationships and therefore the qualified names of elements, but
 * the original qualified names are needed to disambiguate element in the generated code.
 *
 * <p>This performs the computation only for the elements whose original fully qualified names
 * are needed by the generator: vertices (both states and pseudostates), transitions, triggers,
 * guards and actions.
 *
 * <p> This is implemented as a simple visitor on state machine elements which invokes the
 * {@link org.eclipse.papyrusrt.codegen.utils.QualifiedNames.cachedFullName} method on each element.
 * Since that method caches the name, subsequent invocations of that method in the generator,
 * will return the original qualified name.
 *
 * @author Ernesto Posse
 */
class QualifiedNamePreprocessor
{

    public static val INSTANCE = new QualifiedNamePreprocessor

    static def boolean doCacheAllNames( StateMachine stateMachine )
    {
        return INSTANCE.cacheAllNames( stateMachine )
    }

    def boolean cacheAllNames( StateMachine stateMachine )
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
        stateMachine.cachedFullName
        if (stateMachine.top !== null)
            visit( stateMachine.top )
    }

    dispatch def void visit( CompositeState state )
    {
        visitState( state )
        state.initial?.cachedFullSMName
        state.deepHistory?.cachedFullSMName
        state.choicePoints.forEach[ it.cachedFullSMName ]
        state.junctionPoints.forEach[ it.cachedFullSMName ]
        state.substates.forEach[ visit(it) ]
        state.transitions.forEach[ visit(it) ]
    }

    dispatch def void visit( SimpleState state )
    {
        visitState( state )
    }

    protected def void visitState( State state )
    {
        state.cachedFullSMName
        state.entryPoints.forEach[ it.cachedFullSMName ]
        state.exitPoints.forEach[ it.cachedFullSMName ]
    }

    dispatch def void visit( Transition transition )
    {
        if (transition.name === null || transition.name.empty)
        {
            transition.name = transition.elementName
        }
        transition.cachedFullSMName
        transition.triggers.forEach[ name = elementName; cachedFullSMName ]
        transition.guard?.cachedFullSMName
        transition.actionChain?.cachedFullSMName
    }

    dispatch def void visit( NamedElement element )
    {
        element?.cachedFullSMName
    }

    protected dispatch def String elementName( NamedElement element )
    {
        val name = element?.name
        if (name !== null && !name.empty)
            name
        else
            "unnamed"
    }

    protected dispatch def String elementName( Transition transition )
    {
        val name = transition?.name
        if (name !== null && !name.empty)
        {
            name
        }
        else if (transition !== null)
        {
            val sourceName = transition.sourceVertex?.elementName ?: "unknown_source_vertex"
            val targetName = transition.targetVertex?.elementName ?: "unknown_target_vertex"
            "from_" + sourceName + "_to_" + targetName
        }
        else
        {
            "unnamed"
        }
    }


    protected dispatch def String elementName( RTTrigger trigger )
    {
        if (trigger !== null)
        {
            if (trigger.name !== null && !trigger.name.empty)
            {
                trigger.name
            }
            else
            {
                if (trigger.signal !== null && trigger.signal.name !== null && !trigger.signal.name.empty)
                {
                    trigger.signal.name
                }
                else
                {
                    "unknown"
                }
            }
        }
        else
        {
            "unnamed"
        }
    }

}