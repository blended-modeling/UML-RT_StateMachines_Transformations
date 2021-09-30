/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.standalone;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.codegen.cpp.CppCodeGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.codegen.cpp.CppProjectGenerator;
import org.eclipse.papyrusrt.codegen.utils.ProjectUtils;
import org.eclipse.papyrusrt.codegen.xtumlrt.trans.UML2xtumlrtModelTranslator;

public class UMLRTGenerator {

    private static Path commonOutputPath;
    private static UML2xtumlrtModelTranslator translator = new UML2xtumlrtModelTranslator();
    private static CppCodePattern cpp                    = new CppCodePattern();
    private static CppCodeGenerator codegen              = new CppCodeGenerator( cpp, translator );

    public static IStatus generate(List<EObject> elements, String top)
    {
        codegen.setTop( top );
        // The set of inputs is grouped by project and each project is generated independently.
        // The 'targets' map associates each *model project folder * to the list of elements from that model
        Map<File, List<EObject>> targets = new HashMap<File, List<EObject>>();
        // The 'outputs' map associates each *model project folder* to the corresponding output folder.
        Map<File, File> outputFolders = new HashMap<File, File>();

        for( EObject eobj : elements )
        {
            File modelFolder = StandaloneUMLRTCodeGenerator.getModelPath().getParent().toFile();
            List<EObject> list = targets.get( modelFolder );
            if( list == null )
            {
                list = new ArrayList<EObject>();
                targets.put( modelFolder, list );
            }
            list.add( eobj );
            File outputFolder = outputFolders.get( modelFolder );
            if( outputFolder == null)
            {
                outputFolder = getOutputFolder( eobj );
                outputFolders.put( modelFolder, outputFolder );
            }
        }

        MultiStatus status = new MultiStatus( CodeGenPlugin.ID, IStatus.INFO, "UML-RT code generator invoked", null );

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
                    File outputFolder = outputFolders.get( modelFolder );

                    translator.setTargets( entry.getValue() );
                    translator.setOutputPath( path );

                    status.addAll( translator.generate() );

                    List<EObject> translated = translator.getAllGenerated();
                    if (translated == null || translated.isEmpty()) continue;

                    cpp.setOutputFolder( outputFolder );
                    cpp.setModelFolder( modelFolder );
                    codegen.setTargets( translated );

                    status.addAll( codegen.generate() );

                    long writeStart = System.currentTimeMillis();
                    if( cpp.write() )
                        status.add( CodeGenPlugin.info( "Updated generated files " + ( System.currentTimeMillis() - writeStart ) + "ms" ) );
                    else
                        status.add( CodeGenPlugin.error( "Failed to write generated model to disk" ) );
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

        CodeGenPlugin.info( message );
        MultiStatus result = new MultiStatus( CodeGenPlugin.ID, IStatus.INFO, message, null );
        result.addAll( status );
        return result;
    }

    public static IStatus generateXTUMLRT( org.eclipse.uml2.uml.Model model )
    {
        File folder = getOutputFolder( model );
        MultiStatus status = new MultiStatus( CodeGenPlugin.ID, IStatus.INFO, "UML2-to-xtUMLrt Translator Invoked", null );

        long start = System.currentTimeMillis();
        if( model == null )
            status.add( CodeGenPlugin.error( "No input model given" ) );
        else
        {
            try
            {
                List<EObject> targets = new ArrayList<EObject>();
                targets.add( model );
                UML2xtumlrtModelTranslator translator = new UML2xtumlrtModelTranslator( targets, folder.toPath() );
                status.addAll( translator.generate() );

                long writeStart = System.currentTimeMillis();
                if( translator.write() )
                    status.add( CodeGenPlugin.info( "Updated generated files " + ( System.currentTimeMillis() - writeStart ) + "ms" ) );
                else
                    status.add( CodeGenPlugin.error( "Failed to write generated model to disk" ) );
            }
            catch( Throwable t )
            {
                CodeGenPlugin.error( "error during translation action", t );
                status.add( CodeGenPlugin.error( t ) );
            }
        }

        String message
            = "Translation "
                + ( status.getSeverity() <= IStatus.INFO ? "complete" : "error" )
                + ", elapsed time "
                + ( System.currentTimeMillis() - start ) + " ms";

        CodeGenPlugin.info( message );
        MultiStatus result = new MultiStatus( CodeGenPlugin.ID, IStatus.INFO, message, null );
        result.addAll( status );
        return result;
    }

    private static File getOutputFolder( EObject eobj )
    {
        if (commonOutputPath != null)
        {
            return commonOutputPath.toFile();
        }
        Path outputPath = StandaloneUMLRTCodeGenerator.getOutputPath();
        if (outputPath != null)
        {
            // If the project path was set either via the
            // "com.zeligsoft.umlrt.output-path" system
            // property, or through the "-o" command-line option, then use this
            // path to create CDT project.
            File project = outputPath.toFile();
            if (!project.exists() && !project.mkdirs())
            {
                CodeGenPlugin.error( "Failed to create project" );
                return null;
            }
            String projectName = project.getName();
            String projectPath = project.getParentFile().getPath();
            if (!new CppProjectGenerator().generate( projectPath, projectName ))
            {
                CodeGenPlugin.error( "Failed to create project" );
                return null;
            }
            File src = new File( project, "src" );
            if (!src.exists() && !src.mkdir())
            {
                CodeGenPlugin.error( "Failed to create source folder" );
                return null;
            }

            commonOutputPath = src.toPath();
            return src;
        }
        // If outputPath is null, then we fallback to the default
        URI uri = eobj.eResource().getURI();

        String projectName = ProjectUtils.getProjectName( eobj );
        projectName = projectName + "_CDTProject";

        Path elementPath = Paths.get( uri.path() );
        Path projectPath = elementPath.getParent().resolveSibling( projectName );
        if (!new CppProjectGenerator().generate( projectPath.getParent().toString(), projectName ))
        {
            CodeGenPlugin.error( "Failed to create project" );
            return null;
        }
        File project = projectPath.toFile();
        File src = new File( project, "src" );
        if (!src.exists() && !src.mkdir())
        {
            CodeGenPlugin.error( "Failed to create source folder" );
            return null;
        }
        return src;
    }

}
