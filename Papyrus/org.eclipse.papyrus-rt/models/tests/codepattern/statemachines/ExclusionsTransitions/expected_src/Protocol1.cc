
#include "Protocol1.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
#include <stdint.h>
struct UMLRTCommsPort;

static UMLRTObject_field fields_m1[] = 
{
    {
        "data",
        &UMLRTType_int8_t,
        0,
        1,
        0
    }
};

static UMLRTObject payload_m1 = 
{
    sizeof( int8_t ),
    1,
    fields_m1
};

static UMLRTObject_field fields_m2[] = 
{
    {
        "data",
        &UMLRTType_int16_t,
        0,
        1,
        0
    }
};

static UMLRTObject payload_m2 = 
{
    sizeof( int16_t ),
    1,
    fields_m2
};

static UMLRTObject_field fields_m3[] = 
{
    {
        "data",
        &UMLRTType_int32_t,
        0,
        1,
        0
    }
};

static UMLRTObject payload_m3 = 
{
    sizeof( int32_t ),
    1,
    fields_m3
};

Protocol1::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol1::Base::m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "m1", signal_m1, srcPort, &payload_m1 );
    return signal;
}

UMLRTInSignal Protocol1::Base::m2() const
{
    UMLRTInSignal signal;
    signal.initialize( "m2", signal_m2, srcPort, &payload_m2 );
    return signal;
}

UMLRTInSignal Protocol1::Base::m3() const
{
    UMLRTInSignal signal;
    signal.initialize( "m3", signal_m3, srcPort, &payload_m3 );
    return signal;
}

Protocol1::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol1::Conj::m1( const int8_t & data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "m1", signal_m1, srcPort, &payload_m1, &data );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::m2( const int16_t & data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "m2", signal_m2, srcPort, &payload_m2, &data );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::m3( const int32_t & data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "m3", signal_m3, srcPort, &payload_m3, &data );
    return signal;
}


