/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.codegen.cpp.AbstractCppGenerator;

@SuppressWarnings( "rawtypes" )
public class GeneratorDescriptor
{
    private static final String Attr_Type = "type";
    private static final String Attr_Class = "class";

    private final IConfigurationElement element;

    private String type;
    private AbstractCppGenerator.Factory factory;

    public GeneratorDescriptor( IConfigurationElement element )
    {
        this.element = element;
        this.type = element.getAttribute( Attr_Type );
    }

    public String getType() { return type; }

    public AbstractCppGenerator.Factory getFactory()
    {
        if( factory == null )
            synchronized( this )
            {
                if( factory == null )
                {
                    try
                    {
                        factory = (AbstractCppGenerator.Factory)element.createExecutableExtension( Attr_Class );
                    }
                    catch( CoreException e )
                    {
                        String id = element.getDeclaringExtension().getNamespaceIdentifier() + '.' + element.getDeclaringExtension().getSimpleIdentifier();
                        CodeGenPlugin.error( "Error in class attribute of " + id, e );
                    }
                }
            }

        return factory;
    }
}
