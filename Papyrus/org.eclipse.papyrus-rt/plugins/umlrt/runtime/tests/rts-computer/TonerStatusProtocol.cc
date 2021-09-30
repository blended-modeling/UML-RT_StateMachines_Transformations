/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "TonerStatusProtocol.hh"

#include <string.h>
struct UMLRTCommsPort;


TonerStatusProtocol::Conj::Conj ( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

TonerStatusProtocol::Base::Base ( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal TonerStatusProtocol::Base::tonerReady ( int present ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "tonerReady", signal_tonerReady, srcPort, &UMLRTType_int, &present );
    return signal;
}

UMLRTInSignal TonerStatusProtocol::Base::tonerReadyResponse ( ) const
{
    UMLRTInSignal signal;
    signal.initialize( "tonerReadyResponse", signal_tonerReadyResponse, srcPort);
    return signal;
}

UMLRTInSignal TonerStatusProtocol::Conj::tonerReady ( ) const
{
    UMLRTInSignal signal;
    signal.initialize( "tonerReady", signal_tonerReady, srcPort);
    return signal;
}

UMLRTOutSignal TonerStatusProtocol::Conj::tonerReadyResponse( int present ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "tonerReadyResponse", signal_tonerReadyResponse, srcPort, &UMLRTType_int, &present );
    return signal;
}
