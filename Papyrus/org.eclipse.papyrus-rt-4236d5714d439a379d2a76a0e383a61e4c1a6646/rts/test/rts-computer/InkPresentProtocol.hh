/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef INKPRESENTPROTOCOL_HH
#define INKPRESENTPROTOCOL_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"

struct UMLRTCommsPort;

class InkPresentProtocol : public UMLRTProtocol
{
public:
    enum SignalId
    {
        signal_cartridgePresentResponse = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_cartridgePresent
    };
    class OutSignals
    {
    public:
        UMLRTOutSignal cartridgePresent( const UMLRTCommsPort * sourcePort, int present ) const;
        UMLRTInSignal cartridgePresentResponse( const UMLRTCommsPort * sourcePort ) const;
    };
    class InSignals
    {
    public:
        UMLRTInSignal cartridgePresent( const UMLRTCommsPort * sourcePort ) const;
        UMLRTOutSignal cartridgePresentResponse( const UMLRTCommsPort * sourcePort, int present ) const;
    };
    typedef OutSignals Base;
    typedef InSignals Conjugate;
};
class InkPresentProtocol_conjrole : public UMLRTProtocol, private InkPresentProtocol::Conjugate
{
public:
    InkPresentProtocol_conjrole( const UMLRTCommsPort * srcPort );
    UMLRTOutSignal cartridgePresentResponse( int present ) const;
    UMLRTInSignal cartridgePresent( ) const;
};
class InkPresentProtocol_baserole : public UMLRTProtocol, private InkPresentProtocol::Base
{
public:
    InkPresentProtocol_baserole( const UMLRTCommsPort * srcPort );
    UMLRTOutSignal cartridgePresent( int present ) const;
    UMLRTInSignal cartridgePresentResponse( ) const;
};

#endif

