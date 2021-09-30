/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "TonerTypeProtocol.hh"
#include "umlrtobjectclass.hh"
#include "umlrtcapsuleid.hh"
#include "umlrttimespec.hh"
#include <string.h>
struct UMLRTCommsPort;

TonerTypeProtocol::Conj::Conj ( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

TonerTypeProtocol::Base::Base ( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}


UMLRTOutSignal TonerTypeProtocol::Base::tonerType ( int type, UMLRTCapsuleId &id, UMLRTTimespec &tm ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "tonerType", signal_tonerType, srcPort, &UMLRTType_UMLRTCapsuleId, &id );
    return signal;
}

UMLRTInSignal TonerTypeProtocol::Base::tonerTypeResponse ( ) const
{
    UMLRTInSignal signal;
    signal.initialize( "tonerTypeResponse", signal_tonerTypeResponse, srcPort);
    return signal;
}

UMLRTInSignal TonerTypeProtocol::Conj::tonerType ( ) const
{
    UMLRTInSignal signal;
    signal.initialize( "tonerType", signal_tonerType, srcPort);
    return signal;
}

UMLRTOutSignal TonerTypeProtocol::Conj::tonerTypeResponse( int type, UMLRTCapsuleId &id, UMLRTTimespec &tm ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "tonerType", signal_tonerTypeResponse, srcPort, &UMLRTType_UMLRTTimespec, &tm );
    return signal;
}
