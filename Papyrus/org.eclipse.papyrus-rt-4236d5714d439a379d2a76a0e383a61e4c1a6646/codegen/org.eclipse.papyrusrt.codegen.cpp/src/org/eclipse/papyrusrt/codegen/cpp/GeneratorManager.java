/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp;

import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.codegen.cpp.internal.CapsuleGenerator;
import org.eclipse.papyrusrt.codegen.cpp.internal.EclipseGeneratorManager;
import org.eclipse.papyrusrt.codegen.cpp.internal.EmptyStateMachineGenerator;
import org.eclipse.papyrusrt.codegen.cpp.internal.EnumGenerator;
import org.eclipse.papyrusrt.codegen.cpp.internal.ProtocolGenerator;
import org.eclipse.papyrusrt.codegen.cpp.internal.SerializableClassGenerator;
import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.Enumeration;
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement;
import org.eclipse.papyrusrt.xtumlrt.common.Protocol;
import org.eclipse.papyrusrt.xtumlrt.common.StructuredType;
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPassiveClass;

public class GeneratorManager
{
    private static GeneratorManager INSTANCE;

    public static void setInstance( GeneratorManager instance )
    {
        if( INSTANCE != null )
            throw new RuntimeException( "Invalid attempt to replace GeneratorManager" );
        INSTANCE = instance;
    }

    public static GeneratorManager getInstance()
    {
        if( INSTANCE == null )
            INSTANCE = new EclipseGeneratorManager();
        return INSTANCE;
    }

    public AbstractCppGenerator getGenerator( CppCodeGenerator.Kind kind, CppCodePattern cpp, NamedElement element, NamedElement context )
    {
        // Default behaviour is to fall-back to built in defaults where possible.
        switch( kind )
        {
        case BasicClass:
            if( element instanceof StructuredType )
                return new SerializableClassGenerator( cpp, (StructuredType)element );
            break;
        case Enum:
            if( element instanceof Enumeration )
                return new EnumGenerator( cpp, (Enumeration)element );
            break;
        case Protocol:
            if( element instanceof Protocol )
                return new ProtocolGenerator( cpp, (Protocol)element );
            break;
        case Capsule:
            if( element instanceof Capsule )
                return new CapsuleGenerator( cpp, (Capsule)element );
            break;
        case EmptyStateMachine:
            return new EmptyStateMachineGenerator( cpp, element );
        default:
            break;
        }

        CodeGenPlugin.error( "ignoring request for unknown generator " + kind.id );
        throw new RuntimeException( "cannot find generator id " + kind.id );
    }
}
