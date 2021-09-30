
#include "Protocol5.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_pr5_out_m1[] = 
{
    {
        "data",
        &UMLRTType_float,
        0,
        1,
        0
    }
};

static UMLRTObject payload_pr5_out_m1 = 
{
    sizeof( float ),
    1,
    fields_pr5_out_m1
};

Protocol5::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol5::Base::pr5_out_m1( float data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr5_out_m1", signal_pr5_out_m1, srcPort, &payload_pr5_out_m1, &data );
    return signal;
}

Protocol5::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol5::Conj::pr5_out_m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr5_out_m1", signal_pr5_out_m1, srcPort, &payload_pr5_out_m1 );
    return signal;
}


