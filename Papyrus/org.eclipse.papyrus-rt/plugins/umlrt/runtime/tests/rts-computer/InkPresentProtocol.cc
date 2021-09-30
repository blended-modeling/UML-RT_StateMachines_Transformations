/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "InkPresentProtocol.hh"

#include <string.h>
struct UMLRTCommsPort;

InkPresentProtocol::Conj::Conj ( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

InkPresentProtocol::Base::Base ( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal InkPresentProtocol::Base::cartridgePresent ( int present ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "cartridgePresent", signal_cartridgePresent, srcPort, &UMLRTType_int, &present );
    return signal;
}

UMLRTInSignal InkPresentProtocol::Base::cartridgePresentResponse ( ) const
{
    UMLRTInSignal signal;
    signal.initialize( "cartridgePresentResponse", signal_cartridgePresentResponse, srcPort);
    return signal;
}

UMLRTInSignal InkPresentProtocol::Conj::cartridgePresent ( ) const
{
    UMLRTInSignal signal;
    signal.initialize( "cartridgePresent", signal_cartridgePresent, srcPort);
    return signal;
}

UMLRTOutSignal InkPresentProtocol::Conj::cartridgePresentResponse( int present ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "cartridgePresentResponse", signal_cartridgePresent, srcPort, &UMLRTType_int, &present);
    return signal;
}
