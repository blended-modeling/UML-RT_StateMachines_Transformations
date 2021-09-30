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


UsbPortProtocol::Conj::Conj ( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UsbPortProtocol::Base::Base ( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal UsbPortProtocol::Base::deviceId ( int id ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "deviceId", signal_deviceId, srcPort, &UMLRTType_int, &id );
    return signal;
}

UMLRTInSignal UsbPortProtocol::Base::deviceIdResponse ( ) const
{
    UMLRTInSignal signal;
    signal.initialize( "deviceIdResponse", signal_deviceIdResponse, srcPort);
    return signal;
}

UMLRTInSignal UsbPortProtocol::Conj::deviceId ( ) const
{
    UMLRTInSignal signal;
    signal.initialize( "deviceId", signal_deviceId, srcPort);
    return signal;
}

UMLRTOutSignal UsbPortProtocol::Conj::deviceIdResponse( int id ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "deviceId", signal_deviceId, srcPort, &UMLRTType_int, &id );
    return signal;
}
