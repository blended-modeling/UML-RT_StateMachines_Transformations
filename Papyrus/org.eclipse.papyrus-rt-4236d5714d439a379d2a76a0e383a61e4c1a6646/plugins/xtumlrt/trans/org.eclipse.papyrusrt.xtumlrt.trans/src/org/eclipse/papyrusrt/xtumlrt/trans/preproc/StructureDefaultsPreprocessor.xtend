/*******************************************************************************
* Copyright (c) 2014-2016 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.trans.preproc

import org.eclipse.papyrusrt.xtumlrt.common.Attribute
import org.eclipse.papyrusrt.xtumlrt.common.Behaviour
import org.eclipse.papyrusrt.xtumlrt.common.Capsule
import org.eclipse.papyrusrt.xtumlrt.common.CapsuleKind
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory
import org.eclipse.papyrusrt.xtumlrt.common.Entity
import org.eclipse.papyrusrt.xtumlrt.common.Model
import org.eclipse.papyrusrt.xtumlrt.common.MultiplicityElement
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.common.Operation
import org.eclipse.papyrusrt.xtumlrt.common.Package
import org.eclipse.papyrusrt.xtumlrt.common.Parameter
import org.eclipse.papyrusrt.xtumlrt.common.Port
import org.eclipse.papyrusrt.xtumlrt.common.Protocol
import org.eclipse.papyrusrt.xtumlrt.common.Signal
import org.eclipse.papyrusrt.xtumlrt.common.StructuredType
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import org.eclipse.papyrusrt.xtumlrt.trans.Transformation
import org.eclipse.papyrusrt.xtumlrt.trans.TransformationContext

import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.getCopy
import static org.eclipse.papyrusrt.xtumlrt.external.predefined.RTSModelLibraryUtils.*

class StructureDefaultsPreprocessor implements Transformation<NamedElement, NamedElement>
{
    static val behaviourPreprocessor = new StateMachineDefaultsPreprocessor
    static val ZERO = CommonFactory.eINSTANCE.createLiteralInteger => [ value = 0 ]
    static val ONE  = CommonFactory.eINSTANCE.createLiteralInteger => [ value = 1 ]
    
    override transform( NamedElement input, TransformationContext context )
    {
        visit( input )
        return input    // This is an in-place transformation
    }

    dispatch def void visit( Model model )
    {
        model.entities.forEach[ visit ]
        model.protocols.forEach[ visit ]
        model.packages.forEach[ visit ]
    }

    dispatch def void visit( Entity entity )
    {
        visitEntity( entity )
    }

    protected def void visitStructuredType( StructuredType structuredType )
    {
        structuredType.attributes.forEach[ visit ]
        structuredType.operations.forEach[ visit ]
    }
    
    protected def void visitEntity( Entity entity )
    {
        visitStructuredType( entity )
        if (entity.behaviour !== null)
            visit( entity.behaviour )
    }
    
    dispatch def void visit( Capsule capsule )
    {
        visitEntity( capsule )
        capsule.ports.forEach[ visit ]
        capsule.parts.forEach[ visit ]
    }
    
    dispatch def void visit( Protocol protocol )
    {
        protocol.protocolBehaviourFeatures.forEach[ visit ]
        if (protocol.redefines === null && !isSystemElement(protocol)){

            val baseCommProtocol = getTextualBaseCommProtocol(protocol)
            if (baseCommProtocol !== null && protocol !== baseCommProtocol)
            {
                protocol.redefines = baseCommProtocol
            }
        }
    }
    
    dispatch def void visit( Signal signal )
    {
        signal.parameters.forEach[ visit ]
    }
    
    dispatch def void visit( Package packge )
    {
        packge.entities.forEach[ visit ]
        packge.protocols.forEach[ visit ]
        packge.packages.forEach[ visit ]
    }
    
    dispatch def void visit( Port port )
    {
        visitMultiplicityElement( port )
    }
    
    dispatch def void visit( CapsulePart part )
    {
        visitMultiplicityElement( part )
        if (part.kind == CapsuleKind.OPTIONAL || part.kind == CapsuleKind.PLUGIN)
        {
            part.lowerBound = ZERO.getCopy
        }
    }
    
    dispatch def void visit( Attribute attribute )
    {
        visitMultiplicityElement( attribute )
    }
    
    dispatch def void visit( Operation operation )
    {
        operation.parameters.forEach[ visit ]
    }
    
    dispatch def void visit( Parameter parameter )
    {
        visitMultiplicityElement( parameter )
    }
    
    protected def void visitMultiplicityElement( MultiplicityElement element )
    {
        val lowerBound = element.lowerBound
        val upperBound = element.upperBound
        // We make lower and upper bounds the same, as they should represent the replication value of the port
        if (lowerBound === null && upperBound === null)
        {
            element.lowerBound = ONE.getCopy
            element.upperBound = ONE.getCopy
        }
        else if (lowerBound === null && upperBound !== null)
        {
            element.lowerBound = element.upperBound.getCopy
        }
        else if (lowerBound !== null && upperBound === null)
        {
            element.upperBound = element.lowerBound.getCopy
        }
        else
        {
            element.lowerBound = element.upperBound.getCopy
        }
    }

    dispatch def void visit( Behaviour behaviour )
    {
    }

    dispatch def void visit( StateMachine machine )
    {
        behaviourPreprocessor.transform( machine, null )
    }

}