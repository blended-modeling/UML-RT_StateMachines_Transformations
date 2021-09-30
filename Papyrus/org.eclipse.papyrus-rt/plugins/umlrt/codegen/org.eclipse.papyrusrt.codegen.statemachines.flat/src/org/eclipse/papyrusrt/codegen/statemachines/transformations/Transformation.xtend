/*******************************************************************************
* Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package org.eclipse.papyrusrt.codegen.statemachines.transformations

import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import org.eclipse.papyrusrt.xtumlrt.trans.from.uml.UML2xtumlrtSMTranslator

class Transformation
{

    var FlatteningTransformer flattener
    var UML2xtumlrtSMTranslator translator

    def boolean setup
    (
        StateMachine stateMachine,
        TransformationContext context
    )
    {
        if (stateMachine === null
            || context === null
            || !(context instanceof FlatteningTransformer.FlatteningTransformationContext))
            return false
        this.flattener = (context as FlatteningTransformer.FlatteningTransformationContext).flattener
        this.translator = (context as FlatteningTransformer.FlatteningTransformationContext).translator
        return false
    }

    def getTranslator() { return translator }

    def getFlattener() { return flattener }

}