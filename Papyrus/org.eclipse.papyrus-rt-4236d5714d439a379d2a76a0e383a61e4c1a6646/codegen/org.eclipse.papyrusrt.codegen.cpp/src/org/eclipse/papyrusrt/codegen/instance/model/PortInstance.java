/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.instance.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrusrt.codegen.utils.XTUMLRTUtil;
import org.eclipse.papyrusrt.xtumlrt.common.Port;

public class PortInstance implements IPortInstance
{
    private final ICapsuleInstance container;
    private final Port type;
    private final List<FarEnd> farEnds;
    private int unconnectedFarEnds;
    private boolean isRelay = false;

    public PortInstance( ICapsuleInstance container, Port type )
    {
        this.container = container;
        this.type = type;
        this.unconnectedFarEnds = XTUMLRTUtil.getUpperBound( type );
        this.farEnds = new ArrayList<FarEnd>( unconnectedFarEnds );
    }

    @Override public ICapsuleInstance getContainer() { return container; }
    @Override public Port getType() { return type; }
    @Override public String getName() { return type.getName(); }
    @Override public Iterable<? extends IPortInstance.IFarEnd> getFarEnds() { return farEnds; }
    @Override public boolean isRelay() { return isRelay; }

    /**
     * Mark this as a release port.  Disconnect and return the current far end.
     */
    public FarEnd convertToRelay()
    {
        // TODO This flag should not be required.
        isRelay = true;

        // If a far end has already been created, then remove and disconnect
        // it from this port.  If there aren't any farEnds, then try to make a new
        // one.  If that fails, then this cannot become a relay.

        if( farEnds.isEmpty() )
        {
            if( unconnectedFarEnds <= 0 )
                throw new RuntimeException( "out of port instances, cannot create relay port for " + type.getName() );
            return createFarEnd();
        }

        FarEnd far = farEnds.remove( 0 );
        far.disconnectFrom( this );
        return far;
    }

    /** Create and return a new FarEnd (for this port) if possible and null otherwise. */
    public FarEnd createFarEnd()
    {
        if( unconnectedFarEnds <= 0 )
            return null;

        --unconnectedFarEnds;
        return new FarEnd( farEnds.size() );
    }

    public class FarEnd implements IPortInstance.IFarEnd
    {
        private final int index;
        public FarEnd( int index ) { this.index = index; }

        @Override public int getIndex() { return index; }
        @Override public ICapsuleInstance getContainer() { return container; }
        @Override public Port getType() { return type; }
        @Override public void connectWith( IFarEnd other ) { farEnds.add( (FarEnd)other ); }

        @Override public IPortInstance getOwner() { return PortInstance.this; }

        private boolean isOwnedBy( IPortInstance port ) { return port == PortInstance.this; }

        private FarEnd disconnectFrom( PortInstance other )
        {
            for( FarEnd far : farEnds )
                if( far.isOwnedBy( other ) )
                {
                    farEnds.remove( far );
                    return far;
                }

            return null;
        }

        @Override public String toString() { return PortInstance.this.toString() + ".far[" + index + ']'; }
    }

    @Override public String toString() { return container.toString() + '#' + type.getName(); }
}
