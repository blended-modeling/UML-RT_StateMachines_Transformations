/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.internal;

import org.eclipse.papyrusrt.codegen.cpp.AbstractCppGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.codegen.cpp.rts.UMLRTRuntime;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberFunction;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Parameter;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType;
import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement;

public class EmptyStateMachineGenerator extends AbstractCppGenerator
{
    private final NamedElement element;

    public EmptyStateMachineGenerator( CppCodePattern cpp, NamedElement element )
    {
        super( cpp );
        this.element = element;
    }

    @Override public String getLabel() { return super.getLabel() + ' ' + element.getName(); }

    @Override
    public boolean generate()
    {
        CppClass cls = cpp.getCppClass( CppCodePattern.Output.CapsuleClass, element );

        if (!(element instanceof Capsule)) return false;
        Capsule capsule = (Capsule)element;
        if (capsule.getRedefines() != null) return true;

        MemberFunction initialize = new MemberFunction( PrimitiveType.VOID, "initialize" );
        initialize.setVirtual();
        initialize.add( new Parameter( UMLRTRuntime.UMLRTInMessage.getType().const_().ref(), "msg" ) );
        cls.addMember( CppClass.Visibility.PUBLIC, initialize );

        MemberFunction inject = new MemberFunction( PrimitiveType.VOID, "inject" );
        inject.setVirtual();
        inject.add( new Parameter( UMLRTRuntime.UMLRTInMessage.getType().const_().ref(), "msg" ) );
        cls.addMember( CppClass.Visibility.PUBLIC, inject );

        return true;
    }
}
