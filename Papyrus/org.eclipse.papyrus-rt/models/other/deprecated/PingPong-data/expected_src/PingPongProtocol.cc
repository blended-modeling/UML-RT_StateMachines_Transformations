
#include "PingPongProtocol.hh"

#include "DataType1.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_pong[] = 
{
#ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    },
#endif
};

static UMLRTObject payload_pong = 
{
    0,
    1,
    fields_pong
};

static UMLRTObject_field fields_ping[] = 
{
    {
        "param",
        &UMLRTType_DataType1,
        0,
        1,
        0
    }
};

static UMLRTObject payload_ping = 
{
    sizeof( DataType1 ),
    1,
    fields_ping
};

PingPongProtocol::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal PingPongProtocol::Base::ping( const DataType1 & param ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "ping", signal_ping, srcPort, &payload_ping, &param );
    return signal;
}

PingPongProtocol::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal PingPongProtocol::Conj::pong() const
{
    UMLRTOutSignal signal;
    signal.initialize( "pong", signal_pong, srcPort, &payload_pong );
    return signal;
}


