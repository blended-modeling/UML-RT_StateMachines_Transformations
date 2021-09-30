/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef TONERSTATUSPROTOCOL_HH
#define TONERSTATUSPROTOCOL_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"

struct UMLRTCommsPort;

class TonerStatusProtocol : public UMLRTProtocol
{
public:
    enum SignalId
    {
        signal_tonerReadyResponse  = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_tonerReady
    };
    class OutSignals
    {
    public:
        UMLRTOutSignal tonerReady( const UMLRTCommsPort * sourcePort, int ready ) const;
        UMLRTInSignal tonerReadyResponse( const UMLRTCommsPort * sourcePort ) const;
    };
    class InSignals
    {
    public:
        UMLRTInSignal tonerReady( const UMLRTCommsPort * sourcePort ) const;
        UMLRTOutSignal tonerReadyResponse( const UMLRTCommsPort * sourcePort, int ready ) const;
    };
    typedef OutSignals Base;
    typedef InSignals Conjugate;
};
class TonerStatusProtocol_conjrole :  public UMLRTProtocol, private TonerStatusProtocol::Conjugate
{
public:
    TonerStatusProtocol_conjrole( const UMLRTCommsPort * srcPort );
    UMLRTOutSignal tonerReadyResponse( int ready ) const;
    UMLRTInSignal tonerReady( ) const;
};
class TonerStatusProtocol_baserole :  public UMLRTProtocol, private TonerStatusProtocol::Base
{
public:
    TonerStatusProtocol_baserole( const UMLRTCommsPort * srcPort );
    UMLRTOutSignal tonerReady( int ready ) const;
    UMLRTInSignal tonerReadyResponse( ) const;
};

#endif

