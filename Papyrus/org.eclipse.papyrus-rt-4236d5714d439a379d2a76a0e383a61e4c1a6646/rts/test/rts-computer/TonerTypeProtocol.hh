/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef TONERTYPEPROTOCOL_HH
#define TONERTYPEPROTOCOL_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtcapsuleid.hh"
#include "umlrttimespec.hh"

struct UMLRTCommsPort;

class TonerTypeProtocol : public UMLRTProtocol
{
public:
    enum SignalId
    {
        signal_tonerTypeResponse = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_tonerType
    };
    class OutSignals
    {
    public:
        UMLRTOutSignal tonerType( const UMLRTCommsPort * sourcePort, int type, UMLRTCapsuleId &id, UMLRTTimespec &tm ) const;
        UMLRTInSignal tonerTypeResponse( const UMLRTCommsPort * sourcePort ) const;
    };
    class InSignals
    {
    public:
        UMLRTInSignal tonerType( const UMLRTCommsPort * sourcePort ) const;
        UMLRTOutSignal tonerTypeResponse( const UMLRTCommsPort * sourcePort, int type, UMLRTCapsuleId &id, UMLRTTimespec &tm  ) const;
    };
    typedef OutSignals Base;
    typedef InSignals Conjugate;
};
class TonerTypeProtocol_conjrole :  public UMLRTProtocol, private TonerTypeProtocol::Conjugate
{
public:
    TonerTypeProtocol_conjrole( const UMLRTCommsPort * srcPort );
    UMLRTOutSignal tonerTypeResponse( int type, UMLRTCapsuleId &id, UMLRTTimespec &tm  ) const;
    UMLRTInSignal tonerType( ) const;
};
class TonerTypeProtocol_baserole :  public UMLRTProtocol, private TonerTypeProtocol::Base
{
public:
    TonerTypeProtocol_baserole( const UMLRTCommsPort * srcPort );
    UMLRTOutSignal tonerType( int type, UMLRTCapsuleId &id, UMLRTTimespec &tm  ) const;
    UMLRTInSignal tonerTypeResponse( ) const;
};

#endif

