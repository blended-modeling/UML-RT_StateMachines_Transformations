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

class UsbPortProtocol : public UMLRTProtocol
{
public:
    enum SignalId
    {
        signal_deviceIdResponse = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_deviceId
    };
    class OutSignals
    {
    public:
        UMLRTOutSignal deviceId( const UMLRTCommsPort * sourcePort, int id ) const;
        UMLRTInSignal deviceIdResponse( const UMLRTCommsPort * sourcePort ) const;
    };
    class InSignals
    {
    public:
        UMLRTInSignal deviceId( const UMLRTCommsPort * sourcePort ) const;
        UMLRTOutSignal deviceIdResponse( const UMLRTCommsPort * sourcePort, int id ) const;
    };
    typedef OutSignals Base;
    typedef InSignals Conjugate;
};
class UsbPortProtocol_conjrole :  public UMLRTProtocol, private UsbPortProtocol::Conjugate
{
public:
    UsbPortProtocol_conjrole( const UMLRTCommsPort * srcPort );
    UMLRTInSignal deviceId( ) const;
    UMLRTOutSignal deviceIdResponse( int id ) const;
};
class UsbPortProtocol_baserole :  public UMLRTProtocol, private UsbPortProtocol::Base
{
public:
    UsbPortProtocol_baserole( const UMLRTCommsPort * srcPort );
    UMLRTOutSignal deviceId( int id ) const;
    UMLRTInSignal deviceIdResponse( ) const;
};

#endif

