
#include "Protocol1.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_pr1_in_m1[] = 
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

static UMLRTObject payload_pr1_in_m1 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_pr1_in_m1
};

Protocol1::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol1::Base::pr1_in_m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr1_in_m1", signal_pr1_in_m1, srcPort, &payload_pr1_in_m1 );
    return signal;
}

Protocol1::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol1::Conj::pr1_in_m1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr1_in_m1", signal_pr1_in_m1, srcPort, &payload_pr1_in_m1 );
    return signal;
}


