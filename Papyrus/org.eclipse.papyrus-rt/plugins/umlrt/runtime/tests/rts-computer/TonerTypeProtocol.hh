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

namespace TonerTypeProtocol
{
    class Base : public UMLRTProtocol
    {
    public:
        Base ( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal tonerType ( int type, UMLRTCapsuleId &id, UMLRTTimespec &tm ) const;
        UMLRTInSignal tonerTypeResponse ( ) const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj ( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal tonerType ( ) const;
        UMLRTOutSignal tonerTypeResponse ( int type, UMLRTCapsuleId &id, UMLRTTimespec &tm ) const;
    };
    enum SignalId
    {
        signal_tonerType = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_tonerTypeResponse
    };
};

#endif

