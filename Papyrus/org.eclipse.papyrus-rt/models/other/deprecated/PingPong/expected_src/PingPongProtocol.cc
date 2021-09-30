
#include "PingPongProtocol.hh"

#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_pong[] = 
{
    {
        "param",
        &UMLRTType_int,
        0,
        1,
        0
    }
};

static UMLRTObject payload_pong = 
{
    sizeof( int ),
    1,
    fields_pong
};

static UMLRTObject_field fields_ping[] = 
{
    {
        "param",
        &UMLRTType_int,
        0,
        1,
        0
    }
};

static UMLRTObject payload_ping = 
{
    sizeof( int ),
    1,
    fields_ping
};

PingPongProtocol::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal PingPongProtocol::Base::ping( int param ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "ping", signal_ping, srcPort, &payload_ping, &param );
    return signal;
}

PingPongProtocol::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal PingPongProtocol::Conj::pong( int param ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pong", signal_pong, srcPort, &payload_pong, &param );
    return signal;
}


