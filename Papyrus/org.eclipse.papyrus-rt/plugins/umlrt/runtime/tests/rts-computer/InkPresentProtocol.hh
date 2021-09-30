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

namespace InkPresentProtocol
{
    class Base : public UMLRTProtocol
    {
    public:
        Base ( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal cartridgePresent ( int present ) const;
        UMLRTInSignal cartridgePresentResponse ( ) const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj ( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal cartridgePresent ( ) const;
        UMLRTOutSignal cartridgePresentResponse ( int present ) const;
    };
    enum SignalId
    {
        signal_cartridgePresentResponse = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_cartridgePresent
    };
};

#endif

