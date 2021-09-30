/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef USBPORTPROTOCOL_HH
#define USBPORTPROTOCOL_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"

struct UMLRTCommsPort;

namespace UsbPortProtocol
{
    class Base : public UMLRTProtocol
    {
    public:
        Base ( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal deviceId ( int id ) const;
        UMLRTInSignal deviceIdResponse ( ) const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj ( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal deviceId ( ) const;
        UMLRTOutSignal deviceIdResponse ( int id ) const;
    };
    enum SignalId
    {
        signal_deviceId = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_deviceIdResponse
    };
};


#endif

