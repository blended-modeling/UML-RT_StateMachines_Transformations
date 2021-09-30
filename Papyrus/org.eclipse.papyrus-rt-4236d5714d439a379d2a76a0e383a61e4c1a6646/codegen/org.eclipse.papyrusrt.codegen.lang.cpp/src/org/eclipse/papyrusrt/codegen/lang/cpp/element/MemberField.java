/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.element;

import org.eclipse.papyrusrt.codegen.lang.cpp.CppWriter;
import org.eclipse.papyrusrt.codegen.lang.cpp.Expression;
import org.eclipse.papyrusrt.codegen.lang.cpp.IGeneratable;
import org.eclipse.papyrusrt.codegen.lang.cpp.IGeneratableElement;
import org.eclipse.papyrusrt.codegen.lang.cpp.Type;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.DependencyList;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.ElementDependencies;
import org.eclipse.papyrusrt.codegen.lang.cpp.internal.CppFormatter;

public class MemberField extends NamedElement implements IGeneratable, IGeneratableElement
{
    private final Type type;
    private final Expression initializer;

    public MemberField( Type type, String ident )
    {
        this( type, ident, null );
    }

    public MemberField( Type type, String ident, Expression initializer )
    {
        super( ident );
        this.type = type;
        this.initializer = initializer;
    }

    @Override public Type getType() { return type; }
    public Expression getInitializer() { return initializer; }

    @Override
    public boolean addDependencies( DependencyList deps )
    {
        return type.addDependencies( deps );
    }

    @Override
    public boolean addDependencies( ElementDependencies deps )
    {
        return addDependencies( deps.decl() );
    }

    @Override
    public boolean write( CppFormatter fmt )
    {
        return type.write( fmt, getName() )
            && fmt.terminate();
    }

    @Override
    public boolean write( CppWriter out )
    {
        return write( out.decl() );
    }
}
