/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.structure.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.codegen.instance.model.ICapsuleInstance;

public class ControllerAllocations
{
    // CapsuleName -> ControllerName
    private final Map<String, String> allocations = new HashMap<String, String>();
    private final List<Controller> controllers = new ArrayList<Controller>();

    public static final ControllerAllocations Default = new ControllerAllocations()
    {
        @Override public String getController( ICapsuleInstance capsule ) {  return "DefaultController"; }
    };

    public Iterable<Controller> getControllers() { return controllers; }

    public static ControllerAllocations load( File allocationsFile )
    {
        if( allocationsFile == null
         || ! allocationsFile.exists() )
            return null;

        Properties properties = new Properties();
        FileReader reader = null;
        try
        {
            reader = new FileReader( allocationsFile );
            properties.load( reader );
        }
        catch( IOException e )
        {
            CodeGenPlugin.error( e );
            return null;
        }
        finally
        {
            if( reader != null )
                try { reader.close(); }
                catch( IOException e ) { CodeGenPlugin.error( e ); }
        }

        Set<String> controllerNames = new HashSet<String>();
        ControllerAllocations allocs = new ControllerAllocations();
        for( String key : properties.stringPropertyNames() )
        {
            String controllerName = properties.getProperty( key );
            allocs.allocations.put( key, controllerName );

            if( controllerNames.add( controllerName ) )
                allocs.controllers.add( new Controller( controllerName ) );
        }

        return allocs;
    }

    /**
     * Return the controller allocated to this capsule instance or null if there is
     * no such allocation.
     */
    public String getController( ICapsuleInstance capsule )
    {
        // Look for an allocation for this specific instance.
        String controllerName = allocations.get( capsule.getQualifiedName( '.' ) );
        if( controllerName != null )
            return controllerName;

        // Otherwise look for a generic allocation for this capsule type.
        String typeName = capsule.getType().getName();
        controllerName = allocations.get( typeName );
        if( controllerName != null )
            return controllerName;

        // Otherwise there is no allocation so return null.
        return null;
    }
}
