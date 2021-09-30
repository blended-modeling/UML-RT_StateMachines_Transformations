/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.structure.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.papyrusrt.codegen.cpp.ConnectorReporter;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.codegen.instance.model.CapsuleInstance;
import org.eclipse.papyrusrt.codegen.instance.model.ICapsuleInstance;
import org.eclipse.papyrusrt.xtumlrt.common.Capsule;

public class Deployment
{
    private final ControllerAllocations controllerAllocations;
    private final Map<String, Controller> controllers = new TreeMap<String, Controller>();
    private final Map<ICapsuleInstance, Controller> capsules = new LinkedHashMap<ICapsuleInstance, Controller>();

    private Deployment( ControllerAllocations controllerAllocations )
    {
        this.controllerAllocations = controllerAllocations;
    }

    /**
     * Build a deployment instance by examining the top capsule and creating the structural
     * connections for all related capsule instances.
     */
    public static Deployment build( CppCodePattern cpp, Capsule topType, ControllerAllocations controllerAllocations )
    {
        Deployment deployment = new Deployment( controllerAllocations );

        // Create the instance model and then examine all capsule instances to create
        // controllers.
        CapsuleInstance topInstance = new CapsuleInstance( topType );
        ConnectorReporter connReporter = new ConnectorReporter( topInstance );
        topInstance.connect( connReporter, false );
        deployment.allocate( topInstance );
        connReporter.log( cpp.getOutputFolder() );

        return deployment;
    }

    public Iterable<Controller> getControllers() { return controllers.values(); }

    private void allocate( ICapsuleInstance capsule )
    {
        Controller controller = getController( capsule );
        controller.add( capsule );
        capsules.put( capsule, controller );

        for( ICapsuleInstance contained : capsule.getContained() )
            allocate( contained );
    }

    // Return the controller for this capsule instance.
    public Controller getController( ICapsuleInstance capsule )
    {
        if( capsule == null )
            return new Controller( "DefaultController" );

        Controller controller = capsules.get( capsule );
        if( controller != null )
            return controller;

        // First look for a controller allocation for this instance.
        String controllerName = controllerAllocations.getController( capsule );

        // If there isn't a controller for this capsule instance, then use the one
        // for its container.
        if( controllerName == null )
        {
            controller = getController( capsule.getContainer() );
            capsules.put( capsule, controller );
            return controller;
        }

        // Otherwise either create a new controller or reuse one that has already
        // been created for this name.
        controller = controllers.get( controllerName );
        if( controller == null )
        {
            controller = new Controller( controllerName );
            controllers.put( controllerName, controller );
        }

        capsules.put( capsule, controller );
        return controller;
    }
}
