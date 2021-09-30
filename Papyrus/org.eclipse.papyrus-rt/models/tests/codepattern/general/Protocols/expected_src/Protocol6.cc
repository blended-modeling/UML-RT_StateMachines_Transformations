
#include "Protocol6.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_pr6_inout_m1[] = 
{
    {
        "data",
        &UMLRTType_float,
        0,
        1,
        0
    }
};

static UMLRTObject payload_pr6_inout_m1 = 
{
    sizeof( float ),
    1,
    fields_pr6_inout_m1
};

Protocol6::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol6::Base::pr6_inout_m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr6_inout_m1", signal_pr6_inout_m1, srcPort, &payload_pr6_inout_m1 );
    return signal;
}

UMLRTOutSignal Protocol6::Base::pr6_inout_m1( float data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr6_inout_m1", signal_pr6_inout_m1, srcPort, &payload_pr6_inout_m1, &data );
    return signal;
}

Protocol6::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol6::Conj::pr6_inout_m1( float data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr6_inout_m1", signal_pr6_inout_m1, srcPort, &payload_pr6_inout_m1, &data );
    return signal;
}

UMLRTInSignal Protocol6::Conj::pr6_inout_m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr6_inout_m1", signal_pr6_inout_m1, srcPort, &payload_pr6_inout_m1 );
    return signal;
}


