/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.stmt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.codegen.lang.cpp.Statement;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.DependencyBlob;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.DependencyList;
import org.eclipse.papyrusrt.codegen.lang.cpp.internal.CppFormatter;

/**
 * UserCode should be used to generate the opaque string provided for various actions
 * by the user.  This should only be used in contexts where the user code will be
 * generated into the body of a function.
 */
public class UserCode extends Statement
{
    private final DependencyBlob dependencies;
    private final String code;

    public UserCode( String code )
    {
        this( null, code );
    }

    public UserCode( DependencyBlob dependencies, String code )
    {
        this.dependencies = dependencies;
        this.code = code;
    }

    @Override
    public boolean addDependencies( DependencyList deps )
    {
        return dependencies == null ? true : deps.add( dependencies );
    }

    @Override
    public boolean write( CppFormatter fmt )
    {
        // Write the lines separately to apply the appropriate indent.
        BufferedReader reader = new BufferedReader( new StringReader( code ) );
        String line = null;
        try
        {
            while( ( line = reader.readLine() ) != null )
                if( ! fmt.writeLn( line ) )
                    return false;
        }
        catch( IOException e )
        {
            CodeGenPlugin.error( e );
            return false;
        }

        return true;
    }
}
