
#include "Protocol3.hh"

#include "umlrtinoutsignal.hh"
#include "umlrtobjectclass.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_pr3_inout_m1[] = 
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

static UMLRTObject payload_pr3_inout_m1 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_pr3_inout_m1
};

Protocol3::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInOutSignal Protocol3::Base::pr3_inout_m1() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "pr3_inout_m1", signal_pr3_inout_m1, srcPort, &payload_pr3_inout_m1 );
    return signal;
}

Protocol3::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInOutSignal Protocol3::Conj::pr3_inout_m1() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "pr3_inout_m1", signal_pr3_inout_m1, srcPort, &payload_pr3_inout_m1 );
    return signal;
}


