
#include "Protocol2.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_m1[] = 
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

static UMLRTObject payload_m1 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_m1
};

static UMLRTObject_field fields_m2[] = 
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

static UMLRTObject payload_m2 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_m2
};

Protocol2::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol2::Base::m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "m1", signal_m1, srcPort, &payload_m1 );
    return signal;
}

UMLRTInSignal Protocol2::Base::m2() const
{
    UMLRTInSignal signal;
    signal.initialize( "m2", signal_m2, srcPort, &payload_m2 );
    return signal;
}

Protocol2::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol2::Conj::m1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m1", signal_m1, srcPort, &payload_m1 );
    return signal;
}

UMLRTOutSignal Protocol2::Conj::m2() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m2", signal_m2, srcPort, &payload_m2 );
    return signal;
}


