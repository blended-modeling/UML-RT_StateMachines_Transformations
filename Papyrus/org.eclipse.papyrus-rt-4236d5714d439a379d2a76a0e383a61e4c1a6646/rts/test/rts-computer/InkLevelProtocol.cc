/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "InkLevelProtocol.hh"

#include <string.h>
struct UMLRTCommsPort;


UMLRTOutSignal InkLevelProtocol::OutSignals::inkLevel( const UMLRTCommsPort * sourcePort, int level ) const
{
    UMLRTOutSignal signal;
    if (signal.initialize( signal_inkLevel, sourcePort, sizeof( level ) ))
    {
        memcpy( signal.getPayload(), &level, sizeof( level ) );
    }
    return signal;
}

UMLRTInSignal InkLevelProtocol::OutSignals::inkLevelResponse( const UMLRTCommsPort * sourcePort ) const
{
    UMLRTInSignal signal;
    signal.initialize( signal_inkLevelResponse, sourcePort );
    return signal;
}



UMLRTInSignal InkLevelProtocol::InSignals::inkLevel( const UMLRTCommsPort * sourcePort ) const
{
    UMLRTInSignal signal;
    signal.initialize( signal_inkLevel, sourcePort);
    return signal;
}

UMLRTOutSignal InkLevelProtocol::InSignals::inkLevelResponse( const UMLRTCommsPort * sourcePort, int level ) const
{
    UMLRTOutSignal signal;
    if (signal.initialize( signal_inkLevelResponse, sourcePort, sizeof( level ) ))
    {
        memcpy( signal.getPayload(), &level, sizeof( level ) );
    }
    return signal;
}




InkLevelProtocol_conjrole::InkLevelProtocol_conjrole( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}


UMLRTInSignal InkLevelProtocol_conjrole::inkLevel( ) const
{
    return InkLevelProtocol::Conjugate::inkLevel( srcPort );
}

UMLRTOutSignal InkLevelProtocol_conjrole::inkLevelResponse( int level ) const
{
    return InkLevelProtocol::Conjugate::inkLevelResponse( srcPort, level );
}

InkLevelProtocol_baserole::InkLevelProtocol_baserole( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal InkLevelProtocol_baserole::inkLevel( int level ) const
{
    return InkLevelProtocol::Base::inkLevel( srcPort, level );
}


UMLRTInSignal InkLevelProtocol_baserole::inkLevelResponse( ) const
{
    return InkLevelProtocol::Base::inkLevelResponse( srcPort );
}

