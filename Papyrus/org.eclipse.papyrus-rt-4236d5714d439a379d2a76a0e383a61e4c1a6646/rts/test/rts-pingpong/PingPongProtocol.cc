
#include "PingPongProtocol.hh"

#include "DataType1.hh"
#include "umlrtoutsignal.hh"
#include <string.h>
struct UMLRTCommsPort;


UMLRTOutSignal PingPongProtocol::OutSignals::ping ( const UMLRTCommsPort * sourcePort, const DataType1 (& param)[2] ) const
{
    UMLRTOutSignal signal;
    if (signal.initialize( signal_ping, sourcePort, RTType_DataType1->getSize(RTType_DataType1)*2))
    {
        signal.encode(RTType_DataType1, &param, 2);
    }
    return signal;
}

UMLRTInSignal PingPongProtocol::OutSignals::pong ( const UMLRTCommsPort * sourcePort ) const
{
    UMLRTInSignal signal;
    signal.initialize( signal_pong, sourcePort );
    return signal;
}


UMLRTOutSignal PingPongProtocol::InSignals::pong ( const UMLRTCommsPort * sourcePort, const DataType1 (& param)[2] ) const
{
    UMLRTOutSignal signal;
    if (signal.initialize( signal_pong, sourcePort, RTType_DataType1->getSize(RTType_DataType1)*2))
    {
        signal.encode(RTType_DataType1, &param, 2);
    }
    return signal;
}

UMLRTInSignal PingPongProtocol::InSignals::ping ( const UMLRTCommsPort * sourcePort ) const
{
    UMLRTInSignal signal;
    signal.initialize( signal_ping, sourcePort );
    return signal;
}




PingPongProtocol_baserole::PingPongProtocol_baserole ( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal PingPongProtocol_baserole::ping ( const DataType1 (& param)[2] ) const
{
    return PingPongProtocol::Base::ping( srcPort, param );
}

UMLRTInSignal PingPongProtocol_baserole::pong ( ) const
{
    return PingPongProtocol::Base::pong( srcPort );
}

PingPongProtocol_conjrole::PingPongProtocol_conjrole( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal PingPongProtocol_conjrole::pong( const DataType1 (& param)[2] ) const
{
    return PingPongProtocol::Conjugate::pong( srcPort, param );
}

UMLRTInSignal PingPongProtocol_conjrole::ping ( ) const
{
    return PingPongProtocol::Conjugate::ping( srcPort );
}

