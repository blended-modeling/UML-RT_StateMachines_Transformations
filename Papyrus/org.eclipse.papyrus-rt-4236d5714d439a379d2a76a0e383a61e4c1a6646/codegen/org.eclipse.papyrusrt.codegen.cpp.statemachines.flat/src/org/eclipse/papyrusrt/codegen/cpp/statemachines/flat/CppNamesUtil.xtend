/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.statemachines.flat

import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.common.State

import static extension org.eclipse.papyrusrt.codegen.utils.QualifiedNames.*
import static extension org.eclipse.papyrusrt.codegen.utils.XTUMLRTUtil.*
import org.eclipse.papyrusrt.codegen.statemachines.transformations.GlobalConstants
import org.eclipse.papyrusrt.xtumlrt.common.CommonFactory
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelFactory

class CppNamesUtil
{
    static def getFuncName( NamedElement element ) '''«element.prefix»«partSep»«element.ffqn»'''

    static def getPartSep() '''«GlobalConstants.FUNC_NAME_PART_SEP»'''

    static def getPrefix( NamedElement element )
    {
        switch (element.eClass)
        {
            case CommonFactory.eINSTANCE.commonPackage.simpleState:     GlobalConstants.SIMPLE_STATE_FUNC_PREFIX
            case CommonFactory.eINSTANCE.commonPackage.compositeState:  GlobalConstants.COMPOSITE_STATE_FUNC_PREFIX
            case CommonFactory.eINSTANCE.commonPackage.state:           GlobalConstants.STATE_FUNC_PREFIX
            case CommonFactory.eINSTANCE.commonPackage.transition:      GlobalConstants.TRANS_ACTION_FUNC_PREFIX
            case CommonFactory.eINSTANCE.commonPackage.choicePoint:     GlobalConstants.CHOICE_FUNC_PREFIX
            case CommonFactory.eINSTANCE.commonPackage.junctionPoint:   GlobalConstants.JUNCTION_FUNC_PREFIX
            case CommonFactory.eINSTANCE.commonPackage.guard:           GlobalConstants.GUARD_FUNC_PREFIX
            case CommonFactory.eINSTANCE.commonPackage.actionChain:     GlobalConstants.ACTION_CHAIN_FUNC_PREFIX
            case SmflatmodelFactory.eINSTANCE.smflatmodelPackage.entryAction:       GlobalConstants.ENTRY_ACTION_FUNC_PREFIX
            case SmflatmodelFactory.eINSTANCE.smflatmodelPackage.exitAction:        GlobalConstants.EXIT_ACTION_FUNC_PREFIX
            case SmflatmodelFactory.eINSTANCE.smflatmodelPackage.transitionAction:  GlobalConstants.TRANS_ACTION_FUNC_PREFIX
            case CommonFactory.eINSTANCE.commonPackage.actionCode:
            {
                val owner = element.owner
                switch (owner.eClass)
                {
                    case CommonFactory.eINSTANCE.commonPackage.state:
                    {
                        if (element == (owner as State).entryAction)
                            GlobalConstants.ENTRY_ACTION_FUNC_PREFIX
                        else if (element == (owner as State).exitAction)
                            GlobalConstants.EXIT_ACTION_FUNC_PREFIX
                        else
                            GlobalConstants.ACTION_FUNC_PREFIX
                    }
                    case CommonFactory.eINSTANCE.commonPackage.actionChain:
                        GlobalConstants.TRANS_ACTION_FUNC_PREFIX
                    default:
                        GlobalConstants.ACTION_FUNC_PREFIX
                }
            }
            default:
                GlobalConstants.ACTION_FUNC_PREFIX 
        }
    }

    static def String getFfqn( NamedElement element )
    {
        var txt = element.cachedFullSMName.makeValidCName
        txt.replace( GlobalConstants.QUAL_NAME_SEP, GlobalConstants.FUNC_NAME_QUAL_NAME_SEP )
    }

}