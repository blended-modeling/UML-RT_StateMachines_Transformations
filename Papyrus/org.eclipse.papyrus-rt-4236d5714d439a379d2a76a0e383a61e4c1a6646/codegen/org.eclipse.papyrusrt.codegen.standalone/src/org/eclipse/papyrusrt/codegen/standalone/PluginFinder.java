/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.standalone;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/**
 * This class is based on a similar class by Ed Willink part of org.eclipse.ocl.
 *
 * The main difference is that this class doesn't rely on .project files
 * being available, which may not be the case in a deployed application, looks
 * for plugin ids in MANIFEST.MF files, and searches not only the paths in
 * the java class path but also in additional folders provided.
 *
 * The user search paths are added before class path entries so they take
 * priority during the search.
 *
 * @author Erenesto Posse
 */
public class PluginFinder
{
    private final Set<PluginData> requiredMappings;
    private final Map<PluginData, String> resolvedMappings = new HashMap<PluginData, String>();
    private final List<String> searchPaths = new ArrayList<String>();

    private class PluginData
    {
        public String name;
        public String version;  // if version==null, then any version is accepted
        public PluginData( String name, String version )
        {
            this.name = name;
            this.version = version;
        }
        @Override
        public int hashCode()
        {
            return name.hashCode();
        }
        @Override
        public boolean equals( Object other )
        {
            if (! (other instanceof PluginData)) return false;
            return ((PluginData)other).name.equals( name )
                    && ( (version == null && ((PluginData)other).version == null)
                         || ((PluginData)other).version.equals( version ) );
        }
        @Override
        public String toString()
        {
            return "( " + name + ", " + version + ")";
        }
    }

    public PluginFinder( String... requiredProjects )
    {
        this.requiredMappings = new HashSet<PluginData>();
        for (String requiredProject : requiredProjects)
        {
            addRequiredProject( requiredProject );
        }
    }

    public void addRequiredPlugin( String name )
    {
        addRequiredPlugin( name, null );
    }

    public void addRequiredPlugin( String name, String version)
    {
        if (!resolvedMappings.isEmpty())
        {
            throw new IllegalStateException("Cannot addRequiredProject to PluginFinder after resolve()"); //$NON-NLS-1$
        }
        if (name != null)
            requiredMappings.add( new PluginData( name, version ) );
    }

    public void addRequiredProject( String name )
    {
        addRequiredPlugin( name, null );
    }

    public void addSearchPath( Path path )
    {
        if (!resolvedMappings.isEmpty())
        {
            throw new IllegalStateException("Cannot addSearchPath to PluginFinder after resolve()"); //$NON-NLS-1$
        }
        if (path != null && !searchPaths.contains( path ) )
        {
            searchPaths.add( path.toAbsolutePath().toString() );
        }
    }

    public String get( String pluginId )
    {
        String latestVersion = null;
        String resolvedPath = null;
        for (Map.Entry<PluginData, String> mapping : resolvedMappings.entrySet())
        {
            PluginData found = mapping.getKey();
            String path = mapping.getValue();
            if (found.name.equals( pluginId ))
            {
                if (found.version == null)
                    return path;
                else if (latestVersion == null || found.version.compareTo( latestVersion ) > 0)
                {
                    latestVersion = found.version;
                    resolvedPath = path;
                }
            }
        }
        return resolvedPath;
    }

    public String get( String pluginId, String version )
    {
        PluginData plugin = new PluginData( pluginId, version );
        return resolvedMappings.get( plugin );
    }

    private Path getManifestPath( File file )
    {
        Path path = file.toPath();
        if (path == null || path.toString().endsWith( ".jar" )) return null;
        Path mfpath = path.resolve( "META-INF").resolve( "MANIFEST.MF" );
        return mfpath;
    }

    private String getSymbolicName( Manifest manifest )
    {
        if (manifest != null)
        {
            String name = manifest.getMainAttributes().getValue( "Bundle-SymbolicName" ); //$NON-NLS-1$
            if (name != null)
            {
                int indexOf = name.indexOf( ';' );
                if (indexOf > 0)
                {
                    return name.substring( 0, indexOf );
                }
            }
        }
        return null;
    }

    private String getVersion( Manifest manifest )
    {
        if (manifest != null)
        {
            return manifest.getMainAttributes().getValue( "Bundle-Version" ); //$NON-NLS-1$
        }
        return null;
    }

    private boolean isJarBundle( File file )
    {
        if (!file.exists() || file.isDirectory()) return false;
        Path path = file.toPath();
        return path != null && path.toString().endsWith( ".jar" );
    }

    private boolean isNonJarBundle( File file )
    {
        if (!file.exists() || !file.isDirectory()) return false;
        Path path = getManifestPath( file );
        return path != null && Files.exists( path );
    }

    private boolean isRequired( PluginData plugin )
    {
        for (PluginData required : requiredMappings)
        {
            if (required.name.equals( plugin.name )
                && (required.version == null || required.version.equals( plugin.version )))
                return true;
        }
        return false;
    }

    private boolean registerFromManifest( File f, Manifest manifest, String type )
    {
        if (manifest != null)
        {
            String name = getSymbolicName( manifest );
            if (name == null) return false;
            String version = getVersion( manifest );
            PluginData plugin = new PluginData( name, version );
            if (isRequired(plugin))
            {
                String uri = type + (!type.equals("") ? ":" : "") + f.toURI() + (type.equals("jar") ? "!/" : "");
                resolvedMappings.put( plugin, uri ); //$NON-NLS-1$ //$NON-NLS-2$
                return true;
            }
        }
        return false;
    }

    private boolean registerJarBundle( File f ) throws IOException
    {
        JarFile jarFile = new JarFile( f );
        try
        {
            Manifest manifest = jarFile.getManifest();
            return registerFromManifest( f, manifest, "jar" );
        }
        finally
        {
            jarFile.close();
        }
    }

    private boolean registerNonJarBundle( File file ) throws IOException
    {
        Path path = getManifestPath( file );
        if (path != null)
        {
            FileInputStream fis = null;
            try
            {
                fis = new FileInputStream( path.toAbsolutePath().toString() );
                Manifest manifest = new Manifest( fis );
                return registerFromManifest( file, manifest, "" );
            }
            catch (IOException e)
            {
            }
            finally
            {
                if (fis != null)
                    fis.close();
            }
        }
        return false;
    }

    private boolean registerProject( File file )
    {
        FileInputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream( file );
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( inputStream );
            String project = document.getDocumentElement().getElementsByTagName("name").item(0).getTextContent(); //$NON-NLS-1$
            PluginData plugin = new PluginData( project, null );
            if (requiredMappings.contains( plugin ))
            {
                resolvedMappings.put( plugin, file.getParentFile().getCanonicalPath() + File.separator );
                return true;
            }
        }
        catch (Exception e)
        {
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e) {}
            }
        }
        return false;
    }

    public void resolve()
    {
        String property = System.getProperty( "java.class.path" ); //$NON-NLS-1$
        if (property == null)
        {
            return;
        }
        String separator = System.getProperty( "path.separator" ); //$NON-NLS-1$
        String[] cpEntries = property.split( separator );
        List<String> entries = new ArrayList<String>();
        entries.addAll( searchPaths );
        entries.addAll( Arrays.asList( cpEntries ) );
        for (String entry : entries)
        {
            File fileEntry = new File( entry );
            try
            {
                File f = fileEntry.getCanonicalFile();
                if (!f.exists()) continue;
                if (isJarBundle( f ))
                {
                    registerJarBundle( f );
                }
                else if (isNonJarBundle( f ))
                {
                    registerNonJarBundle( f );
                }
                else if (f.isDirectory())
                {
                    scanFolder( f, new HashSet<String>(), 0 );
                }
                else
                {
                    // eclipse bin folder?
                    File parentFile = f.getParentFile();
                    File dotProject = new File( parentFile, ".project" ); //$NON-NLS-1$
                    if (dotProject.exists())
                    {
                        registerProject( dotProject );
                    }
                }
                if (resolvedMappings.size() >= requiredMappings.size())
                {
                    break;
                }
            }
            catch (Exception e) {}
        }
    }

    private boolean scanFolder( File f, Set<String> alreadyVisited, int depth ) throws IOException
    {
        if (!alreadyVisited.add( f.getCanonicalPath() ))
        {
            return true;
        }
        File[] files = f.listFiles();
        boolean containsProject = false;
        File dotProject = null;
        if (files != null)
        {
            for (File file : files)
            {
                if (!file.exists())
                    continue;
                if (isJarBundle( file ))
                {
                    registerJarBundle( file );
                }
                else if (isNonJarBundle( file ))
                {
                    registerNonJarBundle( file );
                }
                else if (".project".equals( file.getName() ))
                {
                    dotProject = file;
                }
                else if (file.isDirectory() && (depth < 2) && !file.getName().startsWith("."))
                {
                    containsProject |= scanFolder( file, alreadyVisited, depth + 1 );
                }
            }
        }
        if (!containsProject && dotProject != null)
            registerProject(dotProject);
        return containsProject || dotProject != null;
    }

    public void printResolvedMappings()
    {
        System.out.println("resolved mapping start");
        for (Map.Entry<PluginData, String> entry : resolvedMappings.entrySet())
        {
            System.out.println(entry.getKey() + " |-> " + entry.getValue());
        }
        System.out.println("resolved mapping end");
        System.out.flush();
    }

    static String indent( int n )
    {
        String r = "";
        for (int i = 0; i < n; i++) r = r + " ";
        return r;
    }

}
