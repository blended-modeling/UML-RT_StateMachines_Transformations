
#include "Protocol2.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_pr2_out_m1[] = 
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

static UMLRTObject payload_pr2_out_m1 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_pr2_out_m1
};

Protocol2::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol2::Base::pr2_out_m1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr2_out_m1", signal_pr2_out_m1, srcPort, &payload_pr2_out_m1 );
    return signal;
}

Protocol2::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol2::Conj::pr2_out_m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr2_out_m1", signal_pr2_out_m1, srcPort, &payload_pr2_out_m1 );
    return signal;
}


