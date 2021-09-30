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

class InkLevelProtocol : UMLRTProtocol
{
public:
    enum SignalId
    {
        signal_inkLevel = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_inkLevelResponse
    };
    class OutSignals : public UMLRTOutSignal
    {
    public:
        UMLRTOutSignal inkLevel( const UMLRTCommsPort * sourcePort, int level ) const;
        UMLRTInSignal inkLevelResponse( const UMLRTCommsPort * sourcePort ) const;
   };
    class InSignals : public UMLRTInSignal
    {
    public:
        UMLRTInSignal inkLevel( const UMLRTCommsPort * sourcePort ) const;
        UMLRTOutSignal inkLevelResponse( const UMLRTCommsPort * sourcePort, int level ) const;
    };
    typedef OutSignals Base;
    typedef InSignals Conjugate;
};
class InkLevelProtocol_conjrole : public UMLRTProtocol, private InkLevelProtocol::Conjugate
{
public:
    InkLevelProtocol_conjrole( const UMLRTCommsPort * srcPort );
    UMLRTInSignal inkLevel() const;
    UMLRTOutSignal inkLevelResponse( int level ) const;
};
class InkLevelProtocol_baserole :  public UMLRTProtocol, private InkLevelProtocol::Base
{
public:
    InkLevelProtocol_baserole( const UMLRTCommsPort * srcPort );
    UMLRTOutSignal inkLevel( int level ) const;
    UMLRTInSignal inkLevelResponse() const;
};

#endif

