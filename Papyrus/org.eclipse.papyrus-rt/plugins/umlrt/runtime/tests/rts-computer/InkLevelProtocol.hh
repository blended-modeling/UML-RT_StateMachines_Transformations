/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef INKLEVELPROTOCOL_HH
#define INKLEVELPROTOCOL_HH

#include "umlrtprotocol.hh"
#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"

struct UMLRTCommsPort;

namespace InkLevelProtocol
{
    class Base : public UMLRTProtocol
    {
    public:
        Base ( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal inkLevel ( int level ) const;
        UMLRTInSignal inkLevelResponse ( ) const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj ( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal inkLevel ( ) const;
        UMLRTOutSignal inkLevelResponse ( int level ) const;
    };
    enum SignalId
    {
        signal_inkLevel = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_inkLevelResponse
    };
};
#endif

