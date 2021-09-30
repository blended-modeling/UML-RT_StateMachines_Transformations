
#include "Protocol2.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_m3[] = 
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

static UMLRTObject payload_m3 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_m3
};

static UMLRTObject_field fields_m4[] = 
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

static UMLRTObject payload_m4 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_m4
};

Protocol2::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol2::Base::m3() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m3", signal_m3, srcPort, &payload_m3 );
    return signal;
}

UMLRTOutSignal Protocol2::Base::m4() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m4", signal_m4, srcPort, &payload_m4 );
    return signal;
}

Protocol2::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol2::Conj::m3() const
{
    UMLRTInSignal signal;
    signal.initialize( "m3", signal_m3, srcPort, &payload_m3 );
    return signal;
}

UMLRTInSignal Protocol2::Conj::m4() const
{
    UMLRTInSignal signal;
    signal.initialize( "m4", signal_m4, srcPort, &payload_m4 );
    return signal;
}


