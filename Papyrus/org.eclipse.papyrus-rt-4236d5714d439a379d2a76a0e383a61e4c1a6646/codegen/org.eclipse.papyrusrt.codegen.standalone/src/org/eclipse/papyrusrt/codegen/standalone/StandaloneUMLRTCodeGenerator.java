/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.standalone;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.codegen.cpp.GeneratorManager;
import org.eclipse.papyrusrt.codegen.standalone.internal.StandaloneGeneratorManager;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

public class StandaloneUMLRTCodeGenerator
{
    // System properties
    public static final String OUTPUT_PATH_PROPERTY     = "org.eclipse.papyrusrt.output-path";
    public static final String PLUGINS_PATH_PROPERTY    = "org.eclipse.papyrusrt.plugins-path";
    public static final String TOP_CAPSULE_PROPERTY     = "org.eclipse.papyrusrt.top-capsule";

    // Default values
    public static final String DEFAULT_LOG_LEVEL        = "OFF";
    public static final String DEFAULT_TOP              = "Top";

    // Command-line options
    private static CommandLine clargs;
    private static final Options options                = new Options();
    private static final HelpFormatter helpFormatter    = new HelpFormatter();
    private static final String CMDLINE_SYNTAX          = "umlrtgen [-h] <path>";
    private static final String OPT_HELP                = "h";
    private static final String OPT_QUIET               = "q";
    private static final String OPT_LOG_LEVEL           = "l";
    private static final String OPT_OUTPUT_PATH         = "o";
    private static final String OPT_PLUGINS_PATH        = "p";
    private static final String OPT_TOP                 = "t";
    private static final String OPT_TO_XTUMLRT          = "x";
    private static final String OPT_PRINT_STACK_TRACE   = "s";
    private static final String OPT_DEV                 = "d";

    private static ResourceSet resourceSet;

    // User-specified variables/options
    private static Path modelPath                     = null;
    private static Path outputPath                    = null;
    private static Path pluginsPath                   = null;
    private static String top                         = null;
    private static boolean printStackTrace            = false;

    public static void main(String[] args)
    {
        try
        {
            parseCmdLineArgs( args );
            if (!processCmdLineArgs()) System.exit( 1 );
            init();
            URI fullURI = URI.createFileURI( modelPath.toString() );
            Model model = loadModel( fullURI );
            IStatus result = generate( model );
            if (!clargs.hasOption(OPT_QUIET))
            {
                displayStatus(result);
            }
            System.exit( result.getSeverity() < IStatus.ERROR ? 0 : 1 );
        }
        catch (ParseException e)
        {
            System.out.println("Invalid command-line arguments.");
            helpFormatter.printHelp(CMDLINE_SYNTAX, options);
        }
        catch (InvalidPathException e)
        {
            System.out.println("Invalid path");
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e);
            e.printStackTrace();
        }

        System.exit( 1 );
    }

    public static Path getModelPath()
    {
        return modelPath;
    }

    public static Path getOutputPath()
    {
        return outputPath;
    }

    public static Path getPluginsPath()
    {
        return pluginsPath;
    }

    public static String getTop()
    {
        return top;
    }

    private static void parseCmdLineArgs(String[] args) throws ParseException
    {
        options.addOption(OPT_HELP,                 "help",     false, "Prints this message.");
        options.addOption(OPT_QUIET,                "quiet",    false, "Inhibits printing messages during generation.");
        options.addOption(OPT_LOG_LEVEL,            "loglevel", true,  "Set the level of logging (OFF, SEVERE, INFO, WARNING, CONFIG, FINE, FINER, FINEST). The default is OFF");
        options.addOption(OPT_PRINT_STACK_TRACE,    "prtrace",  false, "Print the stack trace for exceptions");
        options.addOption(OPT_OUTPUT_PATH,          "outdir",   true,  "Specifies the output folder. By default it is 'gen' in the same folder as the input model.");
        options.addOption(OPT_PLUGINS_PATH,         "plugins",  true,  "Specifies the plugins folder of the PapyrusRT installation.");
        options.addOption(OPT_TOP,                  "top",      true,  "Specify the name of the top capsule. By default it is \"Top\"");
        options.addOption(OPT_TO_XTUMLRT,           "toxr",     false, "Translate an input UML2 model into an xtUMLrt model instead of generating code.");
        options.addOption(OPT_DEV,                  "dev",      false, "Development mode: this is to be used only when invoking the generator from a development environment.");
        CommandLineParser parser = new BasicParser();
        clargs = parser.parse(options, args);
        if ( clargs.getArgList().isEmpty() && !clargs.hasOption( OPT_HELP ) )
        {
            throw new ParseException("There must be at least one argument.");
        }
    }

    private static boolean processCmdLineArgs()
            throws InvalidPathException, FileNotFoundException
    {
        if (clargs.hasOption(OPT_HELP))
        {
            helpFormatter.printHelp(CMDLINE_SYNTAX, options);
            return false;
        }
        printStackTrace = clargs.hasOption( OPT_PRINT_STACK_TRACE );
        configureLogging();
        outputPath =
            validateOutputPath
            (
                clargs.getOptionValue
                (
                    OPT_OUTPUT_PATH,
                    System.getProperty( OUTPUT_PATH_PROPERTY )
                )
            );
        modelPath = validateModelPath( clargs.getArgs()[0] );
        setPluginsPathPropertyDefault();
        pluginsPath =
            validatePluginsPath
            (
                clargs.getOptionValue
                (
                    OPT_PLUGINS_PATH,
                    System.getProperty( PLUGINS_PATH_PROPERTY )
                )
            );
        top =
            clargs.getOptionValue
            (
                OPT_TOP,
                System.getProperty( TOP_CAPSULE_PROPERTY, DEFAULT_TOP )
            );
        return true;
    }

    private static Path validateOutputPath( String outputPath )
            throws InvalidPathException
    {
        if (outputPath != null)
        {
            return Paths.get( outputPath );
        }
        return null;
    }

    private static Path validateModelPath( String modelPathString )
            throws InvalidPathException, FileNotFoundException
    {
        Path modelPath = Paths.get( modelPathString );
        if (!Files.exists( modelPath ))
            throw new FileNotFoundException( "Model file '" + modelPathString + "' not found" );
        return modelPath;
    }

    private static Path validatePluginsPath( String pluginsPathString )
            throws InvalidPathException, FileNotFoundException
    {
        if (pluginsPathString == null)
            return null;
        Path pluginsPath = Paths.get( pluginsPathString );
        if (!Files.exists( pluginsPath ))
            throw new FileNotFoundException( "Plugins folder '" + pluginsPathString + "' not found" );
        return pluginsPath;
    }

    private static void configureLogging()
    {
        // TODO: add a FileHandler if an appropriate option is provided. Also a Formatter.
        Level level = Level.parse( clargs.getOptionValue( OPT_LOG_LEVEL, DEFAULT_LOG_LEVEL ).toUpperCase() );
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel( level );
        CodeGenPlugin.getLogger().addHandler( consoleHandler );
        CodeGenPlugin.getLogger().setLevel( level );
        CodeGenPlugin codegenPlugin = new CodeGenPlugin();
        CodeGenPlugin.setStandalone( printStackTrace );
    }

    private static void setPluginsPathPropertyDefault()
    {
        if (clargs.hasOption( OPT_PLUGINS_PATH )) return;
        // We try to guess the default value for the plugins path by looking for
        // the folder that contains this class.
        if (System.getProperty( PLUGINS_PATH_PROPERTY ) == null)
        {
            Class<?> thisClass = StandaloneUMLRTCodeGenerator.class;
            URL url = thisClass.getResource( "StandaloneUMLRTCodeGenerator.class" );
            // If this class is not in a jar file, it should be in:
            //   <plugins>/org.eclipse.papyrusrt.codegen.standalone/bin/org/eclipse/papyrusrt/codegen/standalone/
            // If it is in a jar file, then it should be in:
            //   <plugins>/org.eclipse.papyrusrt.codegen.standalone.jar!/org/eclipse/papyrusrt/codegen/standalone/
            // But we'll continue to go up as many folders as necessary.
            Path thisClassPath = Paths.get( url.getPath() );
            Path base = thisClassPath;
            String toLookFor = "plugins";
            if (clargs.hasOption( OPT_DEV ))
                toLookFor = "org.eclipse.papyrus-rt";
            while ( base != null && ! base.endsWith( toLookFor ) )
                base = base.getParent();
            if (base != null)
                System.setProperty( PLUGINS_PATH_PROPERTY, base.toString() );
            else
                CodeGenPlugin.warning( "plugins folder not found. The code generator might fail to find the RTS model library. If the model contains references to model library elements, generation can fail with errors. To avoid this warning, please provide the path to the plugins folder using the -p option." );
        }
    }

    private static void updateClassPath()
    {
        String classpath = System.getProperty( "java.class.path" );
        String separator = System.getProperty( "path.separator" );
        String pluginsPathStr = pluginsPath.toAbsolutePath().toString();
        classpath = pluginsPathStr + separator + classpath;
        System.setProperty( "java.class.path", classpath );
    }

    private static void init()
    {
        //printPaths();
        //updateClassPath();
        // Create the resource set
        resourceSet = new ResourceSetImpl();
        // Initialize global registry
        UMLResourcesUtil.init( resourceSet );
        RegisteredPackageMetadata.searchPlugins
        (
            UMLRTProfileMetadata.INSTANCE.getPackageId(),
            RTCppPropertiesProfileMetadata.INSTANCE.getPackageId(),
            PapyrusCppProfileMetadata.INSTANCE.getPackageId(),
            RTSModelLibraryMetadata.INSTANCE.getPackageId()
        );
        // Register UMLRT profile
        UMLRTProfileMetadata.INSTANCE.registerProfile( resourceSet );
        // Register Papyrus C++ profile
        PapyrusCppProfileMetadata.INSTANCE.registerProfile( resourceSet );
        // Load and register the RTS Model library
        // We load it only if we can find it. If we cannot find it, code generation
        // will go ahead, but if the model refers to the library, it will fail.
        // If it cannot find it but the model doesn't use the library, code
        // generation may succeed.
        if (pluginsPath != null)
        {
            loadModelLibrary();
            RTSModelLibraryMetadata.INSTANCE.registerPackage( resourceSet );
            loadRTCppPropertiesProfile();
            RTCppPropertiesProfileMetadata.INSTANCE.registerProfile( resourceSet );
        }
    }

    private static org.eclipse.uml2.uml.Package loadPackage(URI fullURI)
    {
        Resource resource = resourceSet.getResource( fullURI, true );
        EList<EObject> contents = resource.getContents();
        org.eclipse.uml2.uml.Package pkg = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType( contents, UMLPackage.Literals.PACKAGE );
        // displayAllStereotypes( mdl, 0 );
        return pkg;
    }

    private static Model loadModel(URI fullURI)
    {
        return (Model) loadPackage( fullURI );
    }

    private static Profile loadProfile(URI fullURI)
    {
        return (Profile) loadPackage( fullURI );
    }

    private static Model loadModelLibrary()
    {
        URI modelLibLocationURI = RTSModelLibraryMetadata.INSTANCE.getPackageLocationURI();
        Model lib = loadModel( modelLibLocationURI );
        return lib;
    }

    private static Profile loadRTCppPropertiesProfile()
    {
        URI profileLocationURI = RTCppPropertiesProfileMetadata.INSTANCE.getPackageLocationURI();
        Profile lib = loadProfile( profileLocationURI );
        return lib;
    }

    private static IStatus generate(Model model)
    {
        IStatus status = null;
        if (clargs.hasOption( OPT_TO_XTUMLRT ))
        {
            status = UMLRTGenerator.generateXTUMLRT( model );
        }
        else
        {
            List<EObject> targets = new ArrayList<EObject>();
            targets.add( model );
            GeneratorManager.setInstance( new StandaloneGeneratorManager() );
            status = UMLRTGenerator.generate( targets, top );
        }
        return status;
    }

    private static void displayStatus(IStatus status)
    {
        String result = "";
        switch (status.getSeverity())
        {
            case IStatus.CANCEL:    result = "Generation cancelled"; break;
            case IStatus.ERROR:     result = "Generation failed with an error"; break;
            case IStatus.OK:
            case IStatus.INFO:      result = "Generation successful"; break;
            case IStatus.WARNING:   result = "Generation successful but with warnings"; break;
        }
        System.out.println(result);

        if (!clargs.hasOption( OPT_QUIET ))
            displayAll( System.out, "", status );
    }

    private static void displayAll( PrintStream out, String indent, IStatus status )
    {
        out.print( indent );
        out.println( status.getMessage() );
        for( IStatus child : status.getChildren() )
            displayAll( out, indent + "    ", child );
    }

    private static void displayAllStereotypes( EObject eobj, int depth )
    {
        if (eobj instanceof Element)
        {
            Element el = (Element) eobj;
            System.out.println(indent( depth ) + "* " + el );
            EList<Stereotype> as = el.getAppliedStereotypes();
            if (as.size() > 0)
            {
                System.out.println(indent( depth ) + "  - Applied stereotypes:");
                for (Stereotype s: as)
                {
                    System.out.println(indent( depth ) + "      " + s);
                }
            }
            EList<EObject> sas = el.getStereotypeApplications();
            if (sas.size() > 0)
            {
                System.out.println(indent( depth ) + "  - Stereotype applications:");
                for (EObject sa : sas)
                {
                    System.out.println(indent( depth ) + "      " + sa);
                }
            }
            EList<Element> contents = el.getOwnedElements();
            for (Element sub: contents)
            {
                displayAllStereotypes( sub, depth + 4 );
            }
        }
    }

    static String indent( int n )
    {
        String r = "";
        for (int i = 0; i < n; i++) r = r + " ";
        return r;
    }

    static void printPaths()
    {
        String mp = modelPath.toString();
        String op = outputPath.toString();
        String pp = pluginsPath.toString();
        System.out.println("model path:   '" + mp + "'");
        System.out.println("output path:  '" + op + "'");
        System.out.println("plugins path: '" + pp + "'");
        System.out.flush();
    }

}
