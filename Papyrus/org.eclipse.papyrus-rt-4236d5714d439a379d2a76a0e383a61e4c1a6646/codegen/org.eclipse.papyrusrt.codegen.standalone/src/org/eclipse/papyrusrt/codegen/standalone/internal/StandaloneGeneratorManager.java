/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.standalone.internal;

import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement;
import org.eclipse.papyrusrt.xtumlrt.common.StateMachine;

import org.eclipse.papyrusrt.codegen.cpp.AbstractCppGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodeGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.codegen.cpp.GeneratorManager;
import org.eclipse.papyrusrt.codegen.cpp.structure.CompositionGenerator;
import org.eclipse.papyrusrt.codegen.cpp.statemachines.flat.generator.StateMachineGenerator;

public class StandaloneGeneratorManager extends GeneratorManager
{
    @Override
    public AbstractCppGenerator getGenerator
    (
        CppCodeGenerator.Kind kind,
        CppCodePattern cpp,
        NamedElement element,
        NamedElement context
    )
    {
        if (element == null) return null;
        switch( kind )
        {
            case Structural:
                if (!(element instanceof Capsule)) return null;
                return new CompositionGenerator
                            .Factory()
                            .create( cpp, (Capsule) element, null );
            case StateMachine:
                if (!(element instanceof StateMachine) || !(context instanceof Capsule)) return null;
                return new StateMachineGenerator
                            .Factory()
                            .create( cpp, (StateMachine) element, (Capsule)context );
            default:
                return super.getGenerator( kind, cpp, element, context );
        }
    }

}
