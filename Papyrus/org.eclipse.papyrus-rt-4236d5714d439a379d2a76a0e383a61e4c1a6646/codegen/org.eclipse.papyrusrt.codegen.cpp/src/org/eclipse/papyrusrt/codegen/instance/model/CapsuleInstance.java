/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.instance.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.papyrusrt.codegen.cpp.ConnectorReporter;
import org.eclipse.papyrusrt.codegen.utils.QualifiedNames;
import org.eclipse.papyrusrt.codegen.utils.XTUMLRTUtil;
import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart;
import org.eclipse.papyrusrt.xtumlrt.common.Connector;
import org.eclipse.papyrusrt.xtumlrt.common.ConnectorEnd;
import org.eclipse.papyrusrt.xtumlrt.common.Port;
import org.eclipse.papyrusrt.xtumlrt.common.Protocol;
import org.eclipse.papyrusrt.xtumlrt.common.Signal;

public class CapsuleInstance implements ICapsuleInstance
{
    private final Capsule type;
    private final CapsulePart part;
    private final ICapsuleInstance container;
    private final Integer index;
    private final boolean dynamic;

    private final Map<CapsulePart, List<CapsuleInstance>> contained = new HashMap<CapsulePart, List<CapsuleInstance>>();
    private final Map<Port, PortInstance> ports = new HashMap<Port, PortInstance>();

    /** Create the top-level capsule instance. */
    public CapsuleInstance( Capsule top )
    {
        this.type = top;
        this.part = null;
        this.container = null;
        this.index = null;
        this.dynamic = false;

        constructPortInstances();
        constructCapsuleInstances();
    }

    @Override public Capsule getType() { return type; }
    @Override public CapsulePart getCapsulePart() { return part; }
    @Override public ICapsuleInstance getContainer() { return container; }
    @Override public int getIndex() { return index == null ? 0 : index.intValue(); }
    @Override public boolean isDynamic() { return dynamic; }
    @Override public List<? extends ICapsuleInstance> getContained( CapsulePart part ) { return contained.get( part ); }
    @Override public List<IPortInstance> getPorts() { return new ArrayList<IPortInstance>( ports.values() ); }
    @Override public IPortInstance getPort( Port port ) { return ports.get( port ); }

    /** Create a normal capsule instance. */
    private CapsuleInstance( ICapsuleInstance container, Integer index, CapsulePart part, boolean dynamic )
    {
        this.type = part.getType();
        this.part = part;
        this.container = container;
        this.index = index;
        this.dynamic = dynamic;

        constructPortInstances();
        constructCapsuleInstances();
    }

    private void constructPortInstances()
    {
        for( Port port : XTUMLRTUtil.getAllRTPorts( type ) )
            ports.put( port, new PortInstance( this, port ) );
    }

    private void constructCapsuleInstances()
    {
        for( CapsulePart part : XTUMLRTUtil.getAllCapsuleParts( type ) )
        {
            List<CapsuleInstance> instances = new ArrayList<CapsuleInstance>();

            int lower = XTUMLRTUtil.getLowerBound( part );
            int upper = XTUMLRTUtil.getUpperBound( part );
            boolean dynamic = lower <= 0;
            if( upper == 1 )
                instances.add( new CapsuleInstance( this, null, part, dynamic ) );
            else
                for( int i = 0; i < upper; ++i )
                {
                    if( i == lower )
                        dynamic = true;
    
                    instances.add( new CapsuleInstance( this, i, part, dynamic ) );
                }

            contained.put( part, instances );
        }
    }

    public void connect( ConnectorReporter connReporter, boolean shallow )
    {
        // Connect all of the instances for this capsule's parts.
        for( Connector connector : XTUMLRTUtil.getAllConnectors( type ) )
            connect( connReporter, connector );

        // Now connect all of the contained capsules.
        if( ! shallow )
            for( List<CapsuleInstance> capsuleInstances : contained.values() )
                for( CapsuleInstance capsuleInstance : capsuleInstances )
                    capsuleInstance.connect( connReporter == null ? null : connReporter.createInner( capsuleInstance ), false );
    }

    public static class End
    {
        public final CapsulePart part;
        public final Port port;
        public final int numParts;
        public final int numPortInstances;

        public End( CapsulePart containingPart, ConnectorEnd connectorEnd )
        {
            CapsulePart p = connectorEnd.getPartWithPort();

            this.part = p == null ? containingPart : p;
            this.port = connectorEnd.getRole();
            this.numParts = ( part == null ? 1 : XTUMLRTUtil.getUpperBound( part ) );
            this.numPortInstances = numParts * XTUMLRTUtil.getUpperBound( port );
        }

        @Override
        public String toString()
        {
            StringBuilder str = new StringBuilder();
            str.append( part.getName() );
            str.append( '.' );
            str.append( port.getName() );
            str.append( "{numPortInstances:" );
            str.append( numPortInstances );
            str.append( ", numParts:" );
            str.append( numParts );
            str.append( '}' );
            return str.toString();
        }
    }

    private static class ConnectorBuilder
    {
        public final End primary;
        public final End secondary;

        public ConnectorBuilder( End end0, End end1 )
        {
            // The end with the most actual port instances is the primary side.
            if( end0.numPortInstances >= end1.numPortInstances )
            {
                this.primary = end0;
                this.secondary = end1;
            }
            else
            {
                this.primary = end1;
                this.secondary = end0;
            }
        }
    }

    private Iterable<CapsuleInstance> getInstancesFor( CapsulePart part )
    {
        Iterable<CapsuleInstance> instances = contained.get( part );
        return instances == null ? java.util.Collections.singletonList( this ) : instances;
    }

    // Bug 242: Ports with the same conjugation are compatible if their protocols both have only
    //          symmetric signals.
    private static boolean isSameConjugationCompatible( Port port0, Port port1 )
    {
        Protocol t0 = port0.getType();
        if( t0 == null )
            throw new RuntimeException( "invalid attempt to generate ports " + QualifiedNames.fullName( port0 ) + " without Protocol" );

        Protocol t1 = port1.getType();
        if( t1 == null )
            throw new RuntimeException( "invalid attempt to generate ports " + QualifiedNames.fullName( port1 ) + " without Protocol" );

        Iterator<Signal> outSignals0 = XTUMLRTUtil.getOutSignals( t0 ).iterator();
        if( outSignals0.hasNext() )
            return false;

        Iterator<Signal> outSignals1 = XTUMLRTUtil.getOutSignals( t1 ).iterator();
        if( outSignals1.hasNext() )
            return false;

        return true;
    }

    private void connect( ConnectorReporter connReporter, Connector connector )
    {
        ConnectorEnd[] ends = connector.getEnds().toArray( new ConnectorEnd[2] );
        if( ends.length != 2 )
            return;

        ConnectorBuilder cb = new ConnectorBuilder( new End( part, ends[0] ), new End( part, ends[1] ) );

        // If both ports have the same conjugation AND exactly one of the ports does not
        // have a part, then the part-less port becomes a relay for the other.  This means
        // this port instance is deleted and it's farEnd is used for the connection.
        // Otherwise the ports are connected.
        boolean isRelay0 = false;
        boolean isRelay1 = false;
        if( cb.primary.port.isConjugate() == cb.secondary.port.isConjugate() )
        {
            // Charles' demo model turns two border ports into relays.  I'm not sure if this should
            // be supported, but this will get the demo to work.
            if( cb.primary.part == part )
                isRelay0 = ports.containsKey( cb.primary.port );
            if( cb.secondary.part == part )
                isRelay1 = ports.containsKey( cb.secondary.port );

            if( ! isRelay0 && ! isRelay1
             && ! isSameConjugationCompatible( cb.primary.port, cb.secondary.port ) )
                throw new RuntimeException( "invalid attempt to connect ports with incompatible conjugation" );
        }

        int perPrimaryRole = cb.secondary.numPortInstances / cb.primary.numParts;
        Iterator<CapsuleInstance> secondaryCapsuleIterator = getInstancesFor( cb.secondary.part ).iterator();
        CapsuleInstance secondaryCapsule = secondaryCapsuleIterator.hasNext() ? secondaryCapsuleIterator.next() : null;
        for( CapsuleInstance cap0 : getInstancesFor( cb.primary.part ) )
            for( int i = 0; secondaryCapsule != null && i < perPrimaryRole; ++i )
            {
                PortInstance.FarEnd farEnd0 = cap0.createFarEnd( cb.primary.port, isRelay0 );
                PortInstance.FarEnd farEnd1 = secondaryCapsule.createFarEnd( cb.secondary.port, isRelay1 );

                // If all far ends have been consumed, then advance to the next the secondary capsule instance.
                if( farEnd1 == null )
                {
                    // Earlier checks confirm that there should be enough instances.
                    if( ! secondaryCapsuleIterator.hasNext() )
                        throw new RuntimeException( "not enough secondary capsule instances to connect " + cb.primary.toString() + " and " + cb.secondary.toString() + " with " + connector.getName() );

                    secondaryCapsule = secondaryCapsuleIterator.next();
                    farEnd1 = secondaryCapsule.createFarEnd( cb.secondary.port, isRelay1 );
                }

                farEnd0.connectWith( farEnd1 );
                farEnd1.connectWith( farEnd0 );

                if( connReporter != null )
                    connReporter.record( connector, farEnd0, farEnd1 );
            }
    }

    private PortInstance.FarEnd createFarEnd( Port umlPort, boolean isRelay )
    {
        PortInstance portInstance = ports.get( umlPort );
        if( portInstance == null )
            return null;

        PortInstance.FarEnd far = null;

        // If we need  a relay port and the existing instance can be converted to a
        // relay then do so.
        if( isRelay )
            far = portInstance.convertToRelay();

        // Otherwise either we don't want a relay, or the existing instance cannot be
        // converted.  In both cases try to create a new end.
        return far == null ? portInstance.createFarEnd() : far;
    }

    @Override
    public String getQualifiedName( char sep )
    {
        String base = null;
        if( part != null )
            base = part.getName();
        else
        {
            // NOTE: The previous instance model did not have lower-case char, so the new one
            //       cannot either.  The problem is the strings in the allocations file.
            String name = type.getName();
            if( name == null || name.isEmpty() )
                base = "Top";
//            else if( Character.isLowerCase( name.charAt( 0 ) ) )
                base = name;
//            else
//                base = Character.toLowerCase( name.charAt( 0 ) )
//                   + ( name.length() > 1 ? name.substring( 1 ) : "" );
        }

        if( container != null )
            base = container.getQualifiedName( sep ) + sep + base;
        if( index == null )
            return base;

        switch( sep )
        {
        case '.':
            return base + '[' + index + ']';
        case '_':
        default:
            return base + sep + index;
        }
    }

    @Override
    public List<ICapsuleInstance> getContained()
    {
        List<ICapsuleInstance> list = new ArrayList<ICapsuleInstance>();
        for( List<CapsuleInstance> capsules : contained.values() )
            list.addAll( capsules );
        return list;
    }

    @Override public String toString() { return getQualifiedName( '.' ); }
}
