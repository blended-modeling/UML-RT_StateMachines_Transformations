
#include "Protocol4.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_p4_in_m1[] = 
{
    {
        "data",
        &UMLRTType_float,
        0,
        1,
        0
    }
};

static UMLRTObject payload_p4_in_m1 = 
{
    sizeof( float ),
    1,
    fields_p4_in_m1
};

Protocol4::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol4::Base::p4_in_m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "p4_in_m1", signal_p4_in_m1, srcPort, &payload_p4_in_m1 );
    return signal;
}

Protocol4::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol4::Conj::p4_in_m1( float data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "p4_in_m1", signal_p4_in_m1, srcPort, &payload_p4_in_m1, &data );
    return signal;
}


