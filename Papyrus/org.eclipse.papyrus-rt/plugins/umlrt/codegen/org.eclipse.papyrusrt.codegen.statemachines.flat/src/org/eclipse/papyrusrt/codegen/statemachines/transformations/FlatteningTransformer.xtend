/*******************************************************************************
* Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.codegen.statemachines.transformations

import java.util.Collection

import org.eclipse.papyrusrt.codegen.CodeGenPlugin
import org.eclipse.papyrusrt.xtumlrt.util.QualifiedNames
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTElementPrinter
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.*
import org.eclipse.papyrusrt.xtumlrt.trans.from.uml.UML2xtumlrtSMTranslator
import org.eclipse.papyrusrt.xtumlrt.statemach.State
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import org.eclipse.xtend.lib.annotations.Data
import java.util.Map
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement
import java.util.Set

class FlatteningTransformer
{

    /** 
     * This maps for each transformed element the original element before it was transformed.
     * 
     * <p>This map records an entry for (a copy of) <em>every</em> elements in the original model as well as
     * all elements introduced. This is, a "new element" may be either a copy of an original element, or
     * a "really" new element introduced by the transformation, such as new deep history points, new
     * entry and exit points, new "boundary" states, new choice points representing history choices, etc.
     * 
     * <p>If the new element doesn't correspond to any element before the transformation 
     * (as it may be added by the transformation) then the corresponding table entry maps the element to
     * the element's owner, or more precisely to the original element that corresponds to the new 
     * element's owner.
     */
    val Map<CommonElement, CommonElement> newElementsMap = newHashMap;
    
    /** 
     * This set contains all and only those model elements which have been introduced by the transformation
     * that do not correspond to any element in the original model.
     */
    val Set<CommonElement> newElementsSet = newHashSet

    val containmentPreProcessor = new ContainmentChainsPreprocessor
    val qualNamesPreProcessor   = new QualifiedNamePreprocessor
    val depthPreProcessor       = new DepthPreprocessor
    val deepHistoryAdder        = new DeepHistoryAdder
    val nestingFlattener        = new StateNestingFlattener

    @Data static class FlatteningTransformationContext implements TransformationContext
    {
        FlatteningTransformer flattener
        UML2xtumlrtSMTranslator translator
    }

    def TransformationResult transform
    (
        StateMachine stateMachine,
        TransformationContext context
    )
    {
        if (stateMachine === null
            || context === null
            || !(context instanceof FlatteningTransformationContext))
            return new TransformationResult( false, null, null )
        val translator = (context as FlatteningTransformationContext).translator
        var result = false

        newElementsMap.clear
        newElementsSet.clear

        CodeGenPlugin.debug( "State machine before generating:")
        CodeGenPlugin.debug( [ XTUMLRTElementPrinter.str( stateMachine ) ] )

        // We reset the name counter so that freshly generated names are always
        // generated deterministically.
        QualifiedNames.resetCounter

        // We add a deep history pseudostate to every composite state that
        // doesn't have one. This simplifies the flattening.
        result = deepHistoryAdder.transformInPlace( stateMachine, context )
        if (!result) return new TransformationResult( result, stateMachine, null )

        CodeGenPlugin.debug( "State machine after adding history:")
        CodeGenPlugin.debug( [ XTUMLRTElementPrinter.str( stateMachine ) ] )

        // We also precompute the nesting depth of elements in order to break
        // ties between transitions enabled in a given state.
        result = depthPreProcessor.cacheAllDepths( stateMachine )
        if (!result) return new TransformationResult( result, stateMachine, null )

        // We precompute the full containment chains for all elements of the
        // state machine.
        result = containmentPreProcessor.cacheAllChains( stateMachine )
        if (!result) return new TransformationResult( result, stateMachine, null )

        // We precompute the fully qualified names for all elements of the
        // state machine so that they can be used by the C++ generator for the
        // corresponding C++ methods.
        result = qualNamesPreProcessor.cacheAllNames( stateMachine )
        if (!result) return new TransformationResult( result, stateMachine, null )

        CodeGenPlugin.debug( "State machine after caching names:")
        CodeGenPlugin.debug( [ XTUMLRTElementPrinter.str( stateMachine ) ] )

        // StateNestingFlattener adds all discarded composite states to this
        // list, so the C++ generator can use it to create enumerators for them
        var Collection<State> discardedStates = newArrayList
        val ctx1 = new StateNestingFlattener.FlatteningTransformationContext( this, translator, discardedStates )

        // Now we do the actual state flattening
        result = nestingFlattener.transformInPlace( stateMachine, ctx1 )
        if (!result) return new TransformationResult( result, stateMachine, discardedStates )

        CodeGenPlugin.debug( "State machine after flattening:")
        CodeGenPlugin.debug( [ XTUMLRTElementPrinter.str( stateMachine ) ] )

        return new TransformationResult( result, stateMachine, discardedStates )
    }

    def addNewElement( CommonElement newElement )
    {
        if (newElement !== null)
        {
            newElementsMap.put( newElement, newElement.owner )
            newElementsSet.add( newElement )
        }
    }

    def addNewElementCorrespondence( CommonElement newElement, CommonElement originalElement )
    {
        if (newElement !== null)
        {
            var CommonElement associatedElement = originalElement
            if (originalElement === null)
            {
                associatedElement = newElement.owner
            }
            newElementsMap.put( newElement, originalElement )
        }
        
    }

    def getOriginalElement( CommonElement element )
    {
        if (element !== null)
        {
            if (element.isNewElement)
            {
                newElementsMap.get( element )
            }
            else
            {
                element
            }
        }
    }

    def getOriginalOwner( CommonElement newElement )
    {
        if (newElement !== null)
        {
            if (newElement.isNewElement)
            {
                newElementsMap.get( newElement )
            }
            else
            {
                newElement.originalElement?.owner
            }
        }
    }

    def isNewElement( CommonElement element )
    {
        newElementsSet.contains( element )
    }

}
