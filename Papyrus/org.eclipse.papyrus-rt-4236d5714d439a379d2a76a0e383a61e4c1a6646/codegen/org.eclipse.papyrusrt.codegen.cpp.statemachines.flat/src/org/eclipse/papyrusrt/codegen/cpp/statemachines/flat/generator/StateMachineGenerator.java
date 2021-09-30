/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.statemachines.flat.generator;

import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.StateMachine;

import org.eclipse.papyrusrt.codegen.cpp.AbstractCppGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.codegen.cpp.statemachines.flat.FlatteningCppTransformer;

public class StateMachineGenerator extends AbstractCppGenerator
{
    private final StateMachine stateMachine;
    private final Capsule capsuleContext;

    public static class Factory implements AbstractCppGenerator.Factory<StateMachine, Capsule>
    {
        @Override
        public AbstractCppGenerator create( CppCodePattern cpp, StateMachine stateMachine, Capsule capsuleContext )
        {
            return new StateMachineGenerator( cpp, stateMachine, capsuleContext );
        }
    }

    private StateMachineGenerator( CppCodePattern cpp, StateMachine stateMachine, Capsule capsuleContext )
    {
        super( cpp );
        this.stateMachine = stateMachine;
        this.capsuleContext = capsuleContext;
    }

    @Override
    public String getLabel()
    {
        return super.getLabel() + ' ' + stateMachine.getName();
    }

    @Override
    public boolean generate()
    {
        FlatteningCppTransformer generator = new FlatteningCppTransformer( cpp, capsuleContext );

        return generator.transform( stateMachine );
    }
}
