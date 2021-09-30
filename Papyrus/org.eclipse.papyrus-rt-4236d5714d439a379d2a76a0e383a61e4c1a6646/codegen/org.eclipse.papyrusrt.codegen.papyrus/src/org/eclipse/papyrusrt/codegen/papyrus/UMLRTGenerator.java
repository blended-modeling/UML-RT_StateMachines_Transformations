/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.papyrus;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.papyrus.codegen.base.codesync.ChangeObject;
import org.eclipse.papyrus.codegen.base.codesync.ManageChangeEvents;
import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.codegen.cpp.CppCodeGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.codegen.cpp.CppProjectGenerator;
import org.eclipse.papyrusrt.codegen.utils.ProjectUtils;
import org.eclipse.papyrusrt.codegen.xtumlrt.trans.UML2xtumlrtModelTranslator;

@SuppressWarnings("nls")
public class UMLRTGenerator
{

    private static boolean regenerate = false;

    public static void setRegenerate( boolean flag )
    {
        regenerate = flag;
    }

    public static IStatus generate( List<EObject> elements, String top )
    {
        UML2xtumlrtModelTranslator translator = new UML2xtumlrtModelTranslator();
        CppCodePattern cpp                    = new CppCodePattern();
        CppCodeGenerator codegen              = new CppCodeGenerator( cpp, translator );

        codegen.setTop( top );
        if ( regenerate )
            codegen.getChangeTracker().resetAll();

        final List<ChangeObject> changes = new ArrayList<ChangeObject>();
        // The set of inputs is grouped by project and each project is generated independently.
        // The 'targets' map associates each *model project folder * to the list of elements from that model
        Map<File, List<EObject>> targets = new HashMap<File, List<EObject>>();
        // The 'outputs' map associates each *model project folder* to the corresponding output folder.
        Map<File, IFolder> outputFolders = new HashMap<File, IFolder>();

        for( EObject eobj : elements )
        {
            getChanges( changes, eobj );

            File modelFolder = getModelFolder( eobj );

            List<EObject> list = targets.get( modelFolder );
            if( list == null )
            {
                list = new ArrayList<EObject>();
                targets.put( modelFolder, list );
            }
            list.add( eobj );
            IFolder outputFolder = outputFolders.get( modelFolder );
            if( outputFolder == null)
            {
                outputFolder = getOutputFolder( eobj );
                outputFolders.put( modelFolder, outputFolder );
            }
        }

        codegen.getChangeTracker().addChanges( changes );
        Collection<EObject> changedElements = codegen.getChangeTracker().getAllChanged();

        // The ErrorDialog cannot display status that is OK.  There is a bitmask check that needs
        // at least a bit to be enabled.  We use INFO instead of OK to work around this.
        MultiStatus status = new MultiStatus( CodeGenPlugin.ID, IStatus.INFO, "UML-RT Code Generator Invoked", null );

        long start = System.currentTimeMillis();
        if( targets.isEmpty() )
            status.add( CodeGenPlugin.error( "Selection must contain at least one model element" ) );
        else
        {
            try
            {
                for( Map.Entry<File, List<EObject>> entry : targets.entrySet() )
                {
                    File modelFolder = entry.getKey();
                    Path path = modelFolder.toPath();
                    IFolder outputFolder = outputFolders.get( modelFolder );

                    translator.setTargets( entry.getValue() );
                    translator.setOutputPath( path );
                    translator.setChangeSet( changedElements );

                    try { status.addAll( translator.generate() ); }
                    catch( Throwable t )
                    {
                        status.add( CodeGenPlugin.error( "Error during translation", t ) );
                        t.printStackTrace( System.err );
                        continue;
                    }

                    List<EObject> translated = translator.getAllGenerated();
                    if (translated == null || translated.isEmpty()) continue;

                    cpp.setOutputFolder( outputFolder.getLocation().toFile() );
                    cpp.setModelFolder( modelFolder );
                    codegen.setTargets( translated );

                    try { status.addAll( codegen.generate() ); }
                    catch( Throwable t )
                    {
                        status.add( CodeGenPlugin.error( "Error during generation", t ) );
                        t.printStackTrace( System.err );
                        continue;
                    }

                    long writeStart = System.currentTimeMillis();
                    if( cpp.write() )
                        status.add( CodeGenPlugin.info( "Updated generated files " + ( System.currentTimeMillis() - writeStart ) + "ms" ) );
                    else
                        status.add( CodeGenPlugin.error( "Failed to write generated model to disk" ) );

                    outputFolder.refreshLocal( IResource.DEPTH_INFINITE, null );
                }
            }
            catch( Throwable t )
            {
                CodeGenPlugin.error( "error during codegen action", t );
                status.add( CodeGenPlugin.error( t ) );
            }
        }

        String message
            = "Generation "
                + ( status.getSeverity() <= IStatus.INFO ? "complete" : "error" )
                + ", elapsed time "
                + ( System.currentTimeMillis() - start ) + " ms";

        MultiStatus result = new MultiStatus( CodeGenPlugin.ID, IStatus.INFO, message, null );
        result.addAll( status );
        return result;
    }

    private static void getChanges( final List<ChangeObject> changes,
            EObject object )
    {
        TransactionalEditingDomain domain = TransactionUtil
                .getEditingDomain( object );

        if (domain != null)
        {

            EList<ChangeObject> changeList = ManageChangeEvents
                    .getChangeList( domain );
            ManageChangeEvents.stopRecording( domain );
            ManageChangeEvents.initChangeList( domain, true );
            if (changeList != null)
            {
                changes.addAll( changeList );
            }
        }
    }

    private static File getModelFolder( EObject eobj )
    {
        if( eobj == null )
            return null;

        URI eobjUri = EcoreUtil.getURI( eobj );
        if( eobjUri == null )
            return null;

        IPath path = new org.eclipse.core.runtime.Path( eobjUri.toPlatformString( true ) );
        IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile( path );
        if( file == null
         || ! file.exists() )
            return null;

        IContainer modelContainer = file.getParent();
        if( modelContainer == null
         || ! modelContainer.exists() )
            return null;

        return modelContainer.getLocation().makeAbsolute().toFile();
    }

    private static IFolder getOutputFolder( EObject eobj )
    {
        String projectName = ProjectUtils.getProjectName( eobj );
        projectName = projectName + "_CDTProject";

        String rootPath = ResourcesPlugin.getWorkspace().getRoot()
                .getLocation().toOSString();
        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject( projectName );

        if (!project.exists())
        {

            boolean result = new CppProjectGenerator().generate( rootPath,
                                                                 projectName );
            if (!result)
            {
                CodeGenPlugin.error( "Failed to create output project" );
                return null;
            }

            try
            {
                IProjectDescription description = ResourcesPlugin
                        .getWorkspace()
                        .loadProjectDescription(
                            new org.eclipse.core.runtime.Path(
                               rootPath + "/"
                               + projectName
                               + "/.project" ) );
                project = ResourcesPlugin.getWorkspace().getRoot()
                        .getProject( description.getName() );
                project.create( description, null );
            }
            catch (CoreException e1)
            {
                CodeGenPlugin.error( "Failed to create output project" );
            }
        }

        if (!project.isOpen())
        {
            try
            {
                project.open( new NullProgressMonitor() );
            }
            catch (CoreException e)
            {
                CodeGenPlugin.error( "Failed to open project" );
            }
        }

        // Bug 109: Refresh the folder before checking if it exists.
        IFolder folder = project.getFolder( "src" );
        try
        {
            folder.refreshLocal( IResource.DEPTH_ZERO, null );
        }
        catch (CoreException e)
        {
            CodeGenPlugin.error( "could not refresh output folder", e );
        }

        if (!folder.exists()) try
        {
            folder.create( true, true, null );
        }
        catch (CoreException e)
        {
            CodeGenPlugin.error( "could not create output folder", e );
        }

        return folder;
    }

}
