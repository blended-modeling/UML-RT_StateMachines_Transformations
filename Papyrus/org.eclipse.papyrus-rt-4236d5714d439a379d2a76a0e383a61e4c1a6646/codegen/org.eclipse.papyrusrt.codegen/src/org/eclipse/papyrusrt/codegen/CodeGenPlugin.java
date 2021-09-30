/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

public class CodeGenPlugin extends Plugin
{
    public static final String ID = "org.eclipse.papyrusrt.codegen";

    private static CodeGenPlugin instance;
    private static boolean standalone = false;

    private static boolean printStackTrace;
    private static final Logger LOGGER = Logger.getGlobal();

    public static CodeGenPlugin getDefault()
    {
        return instance;
    }

    public CodeGenPlugin()
    {
        instance = this;
    }

    public static IStatus debug( String message )
    {
        IStatus status = new Status( IStatus.INFO, ID, "[DEBUG]" + message );
        if( standalone )
        {
            getLogger().finest( message );
        }
        return status;
    }

    /**
     * This method is intended to be used for debugging with large or complex
     * messages, so the argument is an unevaluated function which will be
     * evaluated only if the logging level is set to FINEST.
     * @param thunk : {@code Function0<String>}
     *      a function which when applied will return the actual message.
     * @return IStatus
     */
    public static IStatus debug( Function0<CharSequence> thunk )
    {
        if (!standalone || getLogger().getLevel().intValue() > Level.FINEST.intValue())
        {
            //System.out.println("[DEBUG]\n" + thunk.apply().toString() + "\n");
            return new Status( IStatus.INFO, ID, "[DEBUG] " + thunk.toString() );
        }
        String message = thunk.apply().toString();
        IStatus status = new Status( IStatus.INFO, ID, "[DEBUG]\n" + message );
        if( standalone )
        {
            getLogger().finest( message );
        }
        return status;
    }

    public static IStatus info( String message )
    {
        IStatus status = new Status( IStatus.INFO, ID, message );
        if( standalone )
        {
            getLogger().info( message );
        }
        else
        {
            getDefault().getLog().log( status );
        }
        return status;
    }

    public static IStatus warning( String message )
    {
        IStatus status = new Status( IStatus.WARNING, ID, message );
        if( standalone )
        {
            getLogger().warning( message );
        }
        else
        {
            getDefault().getLog().log( status );
        }
        return status;
    }

    public static IStatus error( String message )
    {
        return error( "Error", null );
    }

    public static IStatus error( Throwable e )
    {
        return error( "Error", e );
    }

    public static IStatus error( String message, Throwable e )
    {
        IStatus status = null;
        if (e instanceof DetailedThrowable)
        {
            status = new MultiStatus( ID, IStatus.ERROR, message, null );
            String details = e.toString();
            for (String line : details.split( System.getProperty( "line.separator" ) ))
            {
                ((MultiStatus) status).add( new Status( IStatus.ERROR, ID, line) );
            }
        }
        else
        {
            status = new Status( IStatus.ERROR, ID, message, e );
        }
        if( standalone )
        {
            getLogger().severe( status.toString() );
            if (printStackTrace && e != null)
                e.printStackTrace();
        }
        else
        {
            getDefault().getLog().log( status );
        }
        return status;
    }

    public static void setStandalone(boolean printTrace)
    {
        standalone = true;
        printStackTrace = printTrace;
    }

    public static Logger getLogger()
    {
        return LOGGER;
    }

}
