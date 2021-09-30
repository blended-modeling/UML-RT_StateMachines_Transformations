/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.internal;

import org.eclipse.papyrusrt.codegen.cpp.AbstractCppGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppEnum;
import org.eclipse.papyrusrt.xtumlrt.common.Enumeration;
import org.eclipse.papyrusrt.xtumlrt.common.EnumerationLiteral;

public class EnumGenerator extends AbstractCppGenerator
{
    private final Enumeration element;

    public EnumGenerator( CppCodePattern cpp, Enumeration element )
    {
        super( cpp );
        this.element = element;
    }

    @Override
    public boolean generate()
    {
        CppEnum enm = cpp.getWritableCppEnum( CppCodePattern.Output.UserEnum, element );
        for( EnumerationLiteral enumerator : element.getLiterals() )
            enm.add( enumerator.getName() );

        return true;
    }
}
