/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.element;

import org.eclipse.papyrusrt.codegen.lang.cpp.Type;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.DependencyList;
import org.eclipse.papyrusrt.codegen.lang.cpp.internal.CppFormatter;

public class Parameter extends NamedElement
{
    private final Type type;

    public Parameter( Type type, String ident )
    {
        super( ident );
        this.type = type;
    }

    @Override
    public Type getType() { return type; }

    public boolean addDependencies( DependencyList deps )
    {
        return type.addDependencies( deps );
    }

    public boolean write( CppFormatter fmt )
    {
        return type.write( fmt, getName() );
    }
}
