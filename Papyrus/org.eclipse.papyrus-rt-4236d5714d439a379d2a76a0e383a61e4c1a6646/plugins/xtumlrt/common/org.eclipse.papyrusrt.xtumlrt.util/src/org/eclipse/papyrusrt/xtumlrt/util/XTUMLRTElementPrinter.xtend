/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.util

import static extension org.eclipse.papyrusrt.xtumlrt.util.QualifiedNames.*
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.*
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTStateMachineUtil.*
import java.util.Collection
import org.eclipse.papyrusrt.xtumlrt.statemach.CompositeState
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.statemach.Pseudostate
import org.eclipse.papyrusrt.xtumlrt.statemach.State
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import org.eclipse.papyrusrt.xtumlrt.statemach.SimpleState
import org.eclipse.papyrusrt.xtumlrt.statemach.Transition
import org.eclipse.papyrusrt.xtumlrt.statemach.Vertex
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTTrigger

class XTUMLRTElementPrinter
{

    static def str( NamedElement element )
    '''
        «IF element !== null»
            «element.class.simpleName»
            {
                name  = «element.cachedFullName»
                «IF element.owner !== null»
                    owner = «element.owner.cachedFullName»
                «ENDIF»
                «element.elemAttrStr»
            }
        «ELSE»
            null
        «ENDIF»
    '''

    static dispatch def String elemAttrStr( NamedElement e )
    '''
    '''

    static dispatch def String elemAttrStr( StateMachine m )
    '''
        «IF m.top !== null»«elemAttrStr( m.top as State )»«ENDIF»
    '''

    static dispatch def String elemAttrStr( Pseudostate p )
    '''
        «elemAttrStrVertex( p )»
    '''

    static dispatch def String elemAttrStr( SimpleState s )
    '''
        «elemAttrStrState( s )»
    '''

    static dispatch def String elemAttrStr( CompositeState s )
    '''
        «elemAttrStrState( s )»
        initial =        «str( s.initial )»
        deepHistory =    «str( s.deepHistory )»
        choicePoints =   «listString( s.choicePoints )»
        junctionPoints = «listString( s.junctionPoints )»
        substates =      «listString( s.substates )»
        transitions =    «listString( s.transitions )»
    '''

    static def String elemAttrStrState( State s )
    '''
        «elemAttrStrVertex( s )»
        entryPoints = «listString( s.entryPoints?.toList )»
        exitPoints  = «listString( s.exitPoints?.toList )»
    '''

    static def String elemAttrStrVertex( Vertex v )
    '''
        incomingTransitions = «nameListString( v.directIncomingTransitions?.toList )»
        outgoingTransitions = «nameListString( v.directOutgoingTransitions?.toList )»
    '''

    static dispatch def String elemAttrStr( Transition t )
    '''
        sourceVertex = «t.sourceVertex?.cachedFullName»
        targetVertex = «t.targetVertex?.cachedFullName»
        triggers     = «listString( t.triggers )»
        guard        = «str( t.guard )»
        chain        = «nameListString( t.actionChain?.actions )»
    '''

    static dispatch def String elemAttrStr( RTTrigger t )
    '''
        ports  = «nameListString( t.ports )»
        signal = «t.signal?.name»
    '''

    static def listString( Collection<? extends NamedElement> l )
    '''
        «IF l === null || l.empty»
            []
        «ELSE»
            [
                «FOR e : l SEPARATOR ';'»
                    «str( e )»
                «ENDFOR»
            ]
        «ENDIF»
    '''

    static def nameListString( Collection<? extends NamedElement> l )
    '''
        «IF l === null || l.empty»
            []
        «ELSE»
            [«FOR e : l SEPARATOR ','»«e.cachedFullName»«ENDFOR»]
        «ENDIF»
    '''


}