/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.element;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrusrt.codegen.lang.cpp.CppWriter;
import org.eclipse.papyrusrt.codegen.lang.cpp.HeaderFile;
import org.eclipse.papyrusrt.codegen.lang.cpp.IUserElement;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.DependencyBlob;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.ElementDependencies;
import org.eclipse.papyrusrt.codegen.lang.cpp.name.FileName;

public class ElementList extends HeaderFile
{
    private final ElementDependencies extraDeps = new ElementDependencies( this );
    private final List<IUserElement> elements = new ArrayList<IUserElement>();

    public ElementList( FileName name )
    {
        super( name );
    }

    public void addElement( IUserElement... elements )
    {
        for( IUserElement element : elements )
        {
            element.setDefinedIn( this );
            this.elements.add( element );
        }
    }

    /**
     * For debugging and early development.  This can be used to work around problems
     * in the language model where some dependency is not properly captured.
     *
     * @deprecated
     */
    @Deprecated
    public void addDeclDependency( DependencyBlob blob ) { extraDeps.decl().add( blob ); }
    /** @deprecated */
    @Deprecated
    public void addDefnDependency( DependencyBlob blob ) { extraDeps.defn().add( blob ); }

    /**
     * Create and return a formatter that will write to the given memory-based output
     * stream.  Mostly for the test suite.
     * @see #write(String) This method will be removed too.
     */
    public boolean write( CppWriter out )
    {
        String protectionMacro = getName().getAbsolutePath().replace( '/', '_' ).toUpperCase() + "_HH";
        if( ! out.decl().newline()
         || ! out.decl().writeLn( "#ifndef " + protectionMacro )
         || ! out.decl().writeLn( "#define " + protectionMacro )
         || ! out.decl().newline()
         || ! out.defn().newline()
         || ! writeInclude( out.defn() )
         || ! out.defn().newline() )
            return false;

        // Enable writes to both streams.  If there are any actual writes then the preceding
        // content will be flushed out to the file before the actual write.  On the other hand,
        // if we get through all of the elements and the files are still in provisional mode,
        // then it does not need to be written.
        out.decl().enableWrites();
        out.defn().enableWrites();

        ElementDependencies deps = new ElementDependencies( this );

        // Collect dependencies for all contained UserElements.  This is done as part of the
        // write operation because we need the full transitive closure of the dependencies.
        // It is possible for this closure to change after an element is added.
        for( IUserElement element : elements )
            element.addDependencies( deps );
        if( ! deps.write( out ) )
            return false;

        // The special list of extra dependencies is written last.  It is not added to the
        // main dependency list because there isn't a way to sort and filter these.
        if( ! extraDeps.write( out ) )
            return false;

        // write the elements
        for( IUserElement element : elements )
            if( ! element.write( out ) )
                return false;

        // If the header is still in provisional mode, then nothing else needs to be
        // done.
        if( out.decl().isProvisional() )
            return true;

        // Otherwise the header has real content and the guard macro must be terminated.
        return out.decl().newline()
            && out.decl().writeLn( "#endif" )
            && out.decl().newline();
    }
}
