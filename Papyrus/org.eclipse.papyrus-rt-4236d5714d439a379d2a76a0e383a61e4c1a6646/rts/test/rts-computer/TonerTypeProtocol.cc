/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "TonerTypeProtocol.hh"
#include "umlrtobjectclass.hh"
#include <string.h>
struct UMLRTCommsPort;


UMLRTOutSignal TonerTypeProtocol::OutSignals::tonerType( const UMLRTCommsPort * sourcePort, int type, UMLRTCapsuleId &id, UMLRTTimespec &tm ) const
{
    UMLRTOutSignal signal;
    if (signal.initialize( signal_tonerType, sourcePort, sizeof( type ) ))
    {
        signal.encode(UMLRTType_int, &type);
        signal.encode(UMLRTType_UMLRTCapsuleId, &id);
        signal.encode(UMLRTType_UMLRTTimespec, &tm);
    }
    return signal;
}

UMLRTInSignal TonerTypeProtocol::OutSignals::tonerTypeResponse( const UMLRTCommsPort * sourcePort ) const
{
    UMLRTInSignal signal;
    signal.initialize( signal_tonerTypeResponse, sourcePort );
    return signal;
}

UMLRTOutSignal TonerTypeProtocol::InSignals::tonerTypeResponse( const UMLRTCommsPort * sourcePort, int type, UMLRTCapsuleId &id, UMLRTTimespec &tm ) const
{
    UMLRTOutSignal signal;
    if (signal.initialize( signal_tonerTypeResponse, sourcePort, sizeof( type ) ))
    {
        signal.encode(UMLRTType_int, &type);
        signal.encode(UMLRTType_UMLRTCapsuleId, &id);
        signal.encode(UMLRTType_UMLRTTimespec, &tm);
    }
    return signal;
}

UMLRTInSignal TonerTypeProtocol::InSignals::tonerType( const UMLRTCommsPort * sourcePort ) const
{
    UMLRTInSignal signal;
    signal.initialize( signal_tonerType, sourcePort );
    return signal;
}




TonerTypeProtocol_conjrole::TonerTypeProtocol_conjrole( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal TonerTypeProtocol_conjrole::tonerType( ) const
{
    return TonerTypeProtocol::Conjugate::tonerType( srcPort );
}

UMLRTOutSignal TonerTypeProtocol_conjrole::tonerTypeResponse( int type, UMLRTCapsuleId &id, UMLRTTimespec &tm ) const
{
    return TonerTypeProtocol::Conjugate::tonerTypeResponse( srcPort, type, id, tm );
}

UMLRTOutSignal TonerTypeProtocol_baserole::tonerType( int type, UMLRTCapsuleId &id, UMLRTTimespec &tm ) const
{
    return TonerTypeProtocol::Base::tonerType( srcPort, type, id, tm );
}

UMLRTInSignal TonerTypeProtocol_baserole::tonerTypeResponse( ) const
{
    return TonerTypeProtocol::Base::tonerTypeResponse( srcPort );
}

TonerTypeProtocol_baserole::TonerTypeProtocol_baserole( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}

