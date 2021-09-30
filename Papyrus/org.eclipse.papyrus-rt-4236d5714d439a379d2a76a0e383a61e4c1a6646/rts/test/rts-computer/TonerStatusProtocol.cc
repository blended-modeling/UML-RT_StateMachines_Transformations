/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "TonerStatusProtocol.hh"

#include <string.h>
struct UMLRTCommsPort;


UMLRTOutSignal TonerStatusProtocol::OutSignals::tonerReady( const UMLRTCommsPort * sourcePort, int ready ) const
{
    UMLRTOutSignal signal;
    if (signal.initialize( signal_tonerReady, sourcePort, sizeof( ready ) ))
    {
        memcpy( signal.getPayload(), &ready, sizeof( ready ) );
    }
    return signal;
}

UMLRTInSignal TonerStatusProtocol::OutSignals::tonerReadyResponse( const UMLRTCommsPort * sourcePort ) const
{
    UMLRTInSignal signal;
    signal.initialize( signal_tonerReadyResponse, sourcePort);
    return signal;
}

UMLRTInSignal TonerStatusProtocol::InSignals::tonerReady( const UMLRTCommsPort * sourcePort ) const
{
    UMLRTInSignal signal;
    signal.initialize( signal_tonerReady, sourcePort);
    return signal;
}


UMLRTOutSignal TonerStatusProtocol::InSignals::tonerReadyResponse( const UMLRTCommsPort * sourcePort, int ready ) const
{
    UMLRTOutSignal signal;
    if (signal.initialize( signal_tonerReadyResponse, sourcePort, sizeof( ready ) ))
    {
        memcpy( signal.getPayload(), &ready, sizeof( ready ) );
    }
    return signal;
}

TonerStatusProtocol_conjrole::TonerStatusProtocol_conjrole( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal TonerStatusProtocol_conjrole::tonerReadyResponse( int ready ) const
{
    return TonerStatusProtocol::Conjugate::tonerReadyResponse( srcPort, ready );
}

UMLRTInSignal TonerStatusProtocol_conjrole::tonerReady( ) const
{
    return TonerStatusProtocol::Conjugate::tonerReady( srcPort );
}

TonerStatusProtocol_baserole::TonerStatusProtocol_baserole( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal TonerStatusProtocol_baserole::tonerReady( int ready ) const
{
    return TonerStatusProtocol::Base::tonerReady( srcPort, ready );
}

UMLRTInSignal TonerStatusProtocol_baserole::tonerReadyResponse( ) const
{
    return TonerStatusProtocol::Base::tonerReadyResponse( srcPort );
}

