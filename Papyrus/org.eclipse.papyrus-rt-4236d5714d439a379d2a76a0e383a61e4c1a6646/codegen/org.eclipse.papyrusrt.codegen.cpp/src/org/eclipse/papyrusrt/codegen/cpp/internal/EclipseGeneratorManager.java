/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.codegen.cpp.AbstractCppGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodeGenPlugin;
import org.eclipse.papyrusrt.codegen.cpp.CppCodeGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.codegen.cpp.GeneratorManager;

import org.eclipse.papyrusrt.xtumlrt.common.NamedElement;

public class EclipseGeneratorManager extends GeneratorManager
{
    private static final String EXTENSION_POINT = "generator"; //$NON-NLS-1$

    private Map<String, GeneratorDescriptor> generators;

    public EclipseGeneratorManager()
    {
        generators = loadExtensions();
    }

    private static Map<String, GeneratorDescriptor> loadExtensions()
    {
        Map<String, GeneratorDescriptor> generators = new HashMap<String, GeneratorDescriptor>();

        IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor( CppCodeGenPlugin.ID, EXTENSION_POINT );
        for( IConfigurationElement element : elements )
        {
            GeneratorDescriptor desc = new GeneratorDescriptor( element );
            if( generators.containsKey( desc.getType() ) )
                CodeGenPlugin.error( "Ignoring duplicate generator for " + desc.getType() );
            else
                generators.put( desc.getType(), desc );
        }

        return generators;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public AbstractCppGenerator getGenerator( CppCodeGenerator.Kind kind, CppCodePattern cpp, NamedElement element, NamedElement context )
    {
        // Look for a registered generator for the given kind.
        GeneratorDescriptor desc = generators.get( kind.id );
        if( desc != null )
            return desc.getFactory().create( cpp, element, context );

        // If a generator has not been registered, then fall-back to built in defaults where possible.
        return super.getGenerator( kind, cpp, element, context );
    }
}
