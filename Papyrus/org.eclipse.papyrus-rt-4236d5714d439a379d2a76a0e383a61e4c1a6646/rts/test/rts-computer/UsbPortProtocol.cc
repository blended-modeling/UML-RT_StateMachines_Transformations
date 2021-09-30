/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "UsbPortProtocol.hh"

#include <string.h>
struct UMLRTCommsPort;


UMLRTOutSignal UsbPortProtocol::OutSignals::deviceId( const UMLRTCommsPort * sourcePort, int id ) const
{
    UMLRTOutSignal signal;
    if (signal.initialize( signal_deviceId, sourcePort, sizeof( id ) ))
    {
        memcpy( signal.getPayload(), &id, sizeof( id ) );
    }
    return signal;
}

UMLRTInSignal UsbPortProtocol::OutSignals::deviceIdResponse( const UMLRTCommsPort * sourcePort ) const
{
    UMLRTInSignal signal;
    signal.initialize( signal_deviceIdResponse, sourcePort);
    return signal;
}

UMLRTOutSignal UsbPortProtocol::InSignals::deviceIdResponse( const UMLRTCommsPort * sourcePort, int id ) const
{
    UMLRTOutSignal signal;
    if (signal.initialize( signal_deviceIdResponse, sourcePort, sizeof( id ) ))
    {
        memcpy( signal.getPayload(), &id, sizeof( id ) );
    }
    return signal;
}

UMLRTInSignal UsbPortProtocol::InSignals::deviceId( const UMLRTCommsPort * sourcePort ) const
{
    UMLRTInSignal signal;
    signal.initialize( signal_deviceId, sourcePort);
    return signal;
}




UsbPortProtocol_conjrole::UsbPortProtocol_conjrole( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal UsbPortProtocol_conjrole::deviceIdResponse( int id ) const
{
    return UsbPortProtocol::Conjugate::deviceIdResponse( srcPort, id );
}

UMLRTInSignal UsbPortProtocol_conjrole::deviceId(  ) const
{
    return UsbPortProtocol::Conjugate::deviceId( srcPort );
}

UsbPortProtocol_baserole::UsbPortProtocol_baserole( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal UsbPortProtocol_baserole::deviceId( int id ) const
{
    return UsbPortProtocol::Base::deviceId( srcPort, id );
}

UMLRTInSignal UsbPortProtocol_baserole::deviceIdResponse(  ) const
{
    return UsbPortProtocol::Base::deviceIdResponse( srcPort );
}


