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

namespace TonerStatusProtocol
{
    class Base : public UMLRTProtocol
    {
    public:
        Base ( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal tonerReady ( int ready ) const;
        UMLRTInSignal tonerReadyResponse ( ) const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj ( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal tonerReady ( ) const;
        UMLRTOutSignal tonerReadyResponse ( int ready ) const;
    };
    enum SignalId
    {
        signal_tonerReady = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_tonerReadyResponse
    };
};
#endif

