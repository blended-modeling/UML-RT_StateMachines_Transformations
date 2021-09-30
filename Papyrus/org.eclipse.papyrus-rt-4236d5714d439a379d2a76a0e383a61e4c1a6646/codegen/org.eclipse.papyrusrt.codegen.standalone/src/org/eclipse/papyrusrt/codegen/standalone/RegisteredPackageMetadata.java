/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.standalone;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.UMLPlugin;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings( "restriction" )
public abstract class RegisteredPackageMetadata
{
    public abstract String getPackageId();
    public abstract String getNS_URI();
    public abstract EPackage getPackage();
    public abstract String getPathmap();
    public abstract String getRootId();
    public abstract String getRootURI();
    public abstract String getPackageSubdirectory();
    public abstract String getPackageModel();

    private static final PluginFinder pluginFinder = new PluginFinder();

    public void registerProfile( ResourceSet resourceSet )
    {
        // Register profile package in the resourceSet package registry
        String ns_uri = getNS_URI();
        EPackage pkg = getPackage();
        String root_uri = getRootURI();
        if ( ns_uri != null && pkg != null )
            resourceSet.getPackageRegistry().put( ns_uri, pkg );
        // Register the profile's NS_URI to its pathmap location
        if (ns_uri != null && root_uri != null)
            UMLPlugin.getEPackageNsURIToProfileLocationMap().put( ns_uri, URI.createURI( root_uri ) );
        // Register the actual profile location with the URIConverter
        registerPackage( resourceSet );
    }

    public void registerPackage( ResourceSet resourceSet )
    {
        String pathmap = getPathmap();
        String loc = getPackageLocation();
        if (pathmap != null && loc != null)
            resourceSet.getURIConverter()
                .getURIMap()
                .put( URI.createURI( pathmap ),
                      URI.createURI( loc ) );
    }

    public void registerPackage( ResourceSet resourceSet, EPackage pkg )
    {
        String ns_uri = getNS_URI();
        // Register profile package in the resourceSet package registry
        if ( ns_uri != null && pkg != null )
            resourceSet.getPackageRegistry().put( ns_uri, pkg );
        // Register the actual profile location with the URIConverter
        registerPackage( resourceSet );
    }

    public static void searchPlugins( String... pkgIds )
    {
        for (String pkgId : pkgIds)
            pluginFinder.addRequiredPlugin( pkgId );
        pluginFinder.addSearchPath( StandaloneUMLRTCodeGenerator.getPluginsPath() );
        pluginFinder.resolve();
//        pluginFinder.printResolvedMappings();
    }

    /**
     * Find the plugin that contains the profile
     */
    public String getPackageLocation()
    {
        String pkgId = getPackageId();
        if (pkgId == null) return null;
        String packageLoc = pluginFinder.get( pkgId );
        if (packageLoc != null)
        {
            Path path = Paths.get( packageLoc );
            String subdir = getPackageSubdirectory();
            if (subdir != null)
                path = path.resolve( subdir );
            String pkgModel = getPackageModel();
            if (pkgModel != null)
                path = path.resolve( pkgModel );
            return path.toString();
        }
        return null;
    }

    public URI getPathmapURI()
    {
        return URI.createURI( getPathmap() );
    }

    public URI getPackageLocationURI()
    {
        return URI.createURI( getPackageLocation() );
    }

}
