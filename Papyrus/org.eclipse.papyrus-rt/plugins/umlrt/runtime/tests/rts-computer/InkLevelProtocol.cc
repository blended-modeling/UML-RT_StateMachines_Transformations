/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "InkLevelProtocol.hh"

#include <string.h>
struct UMLRTCommsPort;

InkLevelProtocol::Conj::Conj ( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

InkLevelProtocol::Base::Base ( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal InkLevelProtocol::Base::inkLevel ( int level ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "inkLevel", signal_inkLevel, srcPort, &UMLRTType_int, &level );
    return signal;
}

UMLRTInSignal InkLevelProtocol::Base::inkLevelResponse ( ) const
{
    UMLRTInSignal signal;
    signal.initialize( "inkLevelResponse", signal_inkLevelResponse, srcPort);
    return signal;
}

UMLRTInSignal InkLevelProtocol::Conj::inkLevel ( ) const
{
    UMLRTInSignal signal;
    signal.initialize( "inkLevel", signal_inkLevel, srcPort);
    return signal;
}

UMLRTOutSignal InkLevelProtocol::Conj::inkLevelResponse( int level ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "inkLevel", signal_inkLevel, srcPort, &UMLRTType_int, &level);
    return signal;
}
