/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "InkPresentProtocol.hh"

#include <string.h>
struct UMLRTCommsPort;


UMLRTOutSignal InkPresentProtocol::OutSignals::cartridgePresent( const UMLRTCommsPort * sourcePort, int present ) const
{
    UMLRTOutSignal signal;
    if (signal.initialize( signal_cartridgePresent, sourcePort, sizeof( present ) ))
    {
        memcpy( signal.getPayload(), &present, sizeof( present ) );
    }
    return signal;
}


UMLRTInSignal InkPresentProtocol::OutSignals::cartridgePresentResponse( const UMLRTCommsPort * sourcePort ) const
{
    UMLRTInSignal signal;
    signal.initialize( signal_cartridgePresentResponse, sourcePort);
    return signal;
}


UMLRTInSignal InkPresentProtocol::InSignals::cartridgePresent( const UMLRTCommsPort * sourcePort ) const
{
    UMLRTInSignal signal;
    signal.initialize( signal_cartridgePresent, sourcePort);
    return signal;
}

UMLRTOutSignal InkPresentProtocol::InSignals::cartridgePresentResponse( const UMLRTCommsPort * sourcePort, int present ) const
{
    UMLRTOutSignal signal;
    if (signal.initialize( signal_cartridgePresentResponse, sourcePort, sizeof( present ) ))
    {
        memcpy( signal.getPayload(), &present, sizeof( present ) );
    }
    return signal;
}




InkPresentProtocol_conjrole::InkPresentProtocol_conjrole( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal InkPresentProtocol_conjrole::cartridgePresentResponse( int present ) const
{
    return InkPresentProtocol::Conjugate::cartridgePresentResponse( srcPort, present );
}

UMLRTInSignal InkPresentProtocol_conjrole::cartridgePresent( ) const
{
    return InkPresentProtocol::Conjugate::cartridgePresent( srcPort );
}

InkPresentProtocol_baserole::InkPresentProtocol_baserole( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal InkPresentProtocol_baserole::cartridgePresent( int present ) const
{
    return InkPresentProtocol::Base::cartridgePresent( srcPort, present );
}

UMLRTInSignal InkPresentProtocol_baserole::cartridgePresentResponse( ) const
{
    return InkPresentProtocol::Base::cartridgePresentResponse( srcPort );
}


