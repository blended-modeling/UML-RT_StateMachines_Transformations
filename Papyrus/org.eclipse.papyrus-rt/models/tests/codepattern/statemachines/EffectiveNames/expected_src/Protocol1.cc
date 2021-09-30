
#include "Protocol1.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_InProtocolMessage1[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_InProtocolMessage1 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_InProtocolMessage1
};

static UMLRTObject_field fields_InProtocolMessage2[] = 
{
    {
        "data",
        &UMLRTType_int,
        0,
        1,
        0
    }
};

static UMLRTObject payload_InProtocolMessage2 = 
{
    sizeof( int ),
    1,
    fields_InProtocolMessage2
};

static UMLRTObject_field fields_InProtocolMessage3[] = 
{
    {
        "data",
        &UMLRTType_char,
        0,
        1,
        0
    }
};

static UMLRTObject payload_InProtocolMessage3 = 
{
    sizeof( char ),
    1,
    fields_InProtocolMessage3
};

Protocol1::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol1::Base::InProtocolMessage1() const
{
    UMLRTInSignal signal;
    signal.initialize( "InProtocolMessage1", signal_InProtocolMessage1, srcPort, &payload_InProtocolMessage1 );
    return signal;
}

UMLRTInSignal Protocol1::Base::InProtocolMessage2() const
{
    UMLRTInSignal signal;
    signal.initialize( "InProtocolMessage2", signal_InProtocolMessage2, srcPort, &payload_InProtocolMessage2 );
    return signal;
}

UMLRTInSignal Protocol1::Base::InProtocolMessage3() const
{
    UMLRTInSignal signal;
    signal.initialize( "InProtocolMessage3", signal_InProtocolMessage3, srcPort, &payload_InProtocolMessage3 );
    return signal;
}

Protocol1::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol1::Conj::InProtocolMessage1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "InProtocolMessage1", signal_InProtocolMessage1, srcPort, &payload_InProtocolMessage1 );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::InProtocolMessage2( int data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "InProtocolMessage2", signal_InProtocolMessage2, srcPort, &payload_InProtocolMessage2, &data );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::InProtocolMessage3( char data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "InProtocolMessage3", signal_InProtocolMessage3, srcPort, &payload_InProtocolMessage3, &data );
    return signal;
}


