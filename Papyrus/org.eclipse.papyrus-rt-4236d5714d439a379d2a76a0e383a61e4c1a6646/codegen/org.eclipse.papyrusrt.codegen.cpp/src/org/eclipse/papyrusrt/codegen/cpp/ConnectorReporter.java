/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.papyrusrt.codegen.instance.model.ICapsuleInstance;
import org.eclipse.papyrusrt.codegen.instance.model.PortInstance;

import org.eclipse.papyrusrt.codegen.cpp.ConnectorReporter;
import org.eclipse.papyrusrt.xtumlrt.common.Connector;

/**
 * This utility class is used to report on connections that are allocated while
 * generating the InstanceModel.
 */
public class ConnectorReporter
{
    private final ICapsuleInstance capsule;
    private final Map<Connector, List<Connection>> connections = new LinkedHashMap<Connector, List<Connection>>();
    private final List<ConnectorReporter> inners = new ArrayList<ConnectorReporter>();

    public ConnectorReporter( ICapsuleInstance capsule )
    {
        this.capsule = capsule;
    }

    private static class Connection
    {
        public final PortInstance.FarEnd far0;
        public final PortInstance.FarEnd far1;
        public Connection( PortInstance.FarEnd far0, PortInstance.FarEnd far1 )
        {
            this.far0 = far0;
            this.far1 = far1;
        }
    }

    public void record( Connector conn, PortInstance.FarEnd far0, PortInstance.FarEnd far1 )
    {
        List<Connection> conns = connections.get( conn );
        if( conns == null )
        {
            conns = new ArrayList<ConnectorReporter.Connection>();
            connections.put( conn, conns );
        }

        conns.add( new Connection( far0, far1 ) );
    }

    public ConnectorReporter createInner( ICapsuleInstance sub )
    {
        ConnectorReporter inner = new ConnectorReporter( sub );
        inners.add( inner );
        return inner;
    }

    public void log( PrintStream stm )
    {
        for( Map.Entry<Connector, List<Connection>> entry : connections.entrySet() )
        {
            stm.append( capsule.getQualifiedName( '.' ) );
            stm.append( '.' );
            stm.append( entry.getKey().getName() );
            stm.append( '\n' );
            for( Connection conn : entry.getValue() )
            {
                stm.append( "    " );
                stm.append( conn.far0.toString() );
                stm.append( " <-> ");
                stm.append( conn.far1.toString() );
                stm.append( '\n' );
            }
        }

        for( ConnectorReporter inner : inners )
            inner.log( stm );
    }

    public void log( File outFolder )
    {
        PrintStream stm = null;
        try
        {
            File file = new File( outFolder, capsule.getType().getName() + "-connections.log" );
            stm = new PrintStream( new BufferedOutputStream( new FileOutputStream( file ) ) );
            log( stm );
        }
        catch( IOException e ) { }
        finally
        {
            if( stm != null )
                stm.close();
        }
    }
}
