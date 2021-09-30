/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.element;

import org.eclipse.papyrusrt.codegen.lang.cpp.CppWriter;
import org.eclipse.papyrusrt.codegen.lang.cpp.HeaderFile;
import org.eclipse.papyrusrt.codegen.lang.cpp.Type;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.ElementDependencies;

public class Function extends AbstractFunction
{
    private final LinkageSpec linkage;

    public Function( LinkageSpec linkage, Type returnType, String ident )
    {
        this( null, linkage, returnType, ident );
    }

    public Function( HeaderFile header, LinkageSpec linkage, Type returnType, String ident )
    {
        super( header, returnType, ident );
        this.linkage = linkage;
    }

    public LinkageSpec getLinkage() { return linkage; }

    @Override
    public boolean addDependencies( ElementDependencies deps )
    {
        if( linkage == LinkageSpec.STATIC )
            return addSignatureDependencies( deps.defn() )
                && addBodyDependencies( deps.defn() );

        return super.addDependencies( deps );
    }

    @Override
    public boolean write( CppWriter out )
    {
        // statics are generated only to the defn
        if( linkage == LinkageSpec.STATIC )
            return linkage.write( out.defn() )
                    && out.defn().space()
                    && writeSignature( out.defn() )
                    && writeBody( out.defn() );

        // otherwise split the variable between the .hh and .cc
        return linkage.write( out.decl() )
            && out.decl().space()
            && writeSignature( out.decl() )
            && out.decl().terminate()
            && writeSignature( out.defn() )
            && writeBody( out.defn() );
    }
}
