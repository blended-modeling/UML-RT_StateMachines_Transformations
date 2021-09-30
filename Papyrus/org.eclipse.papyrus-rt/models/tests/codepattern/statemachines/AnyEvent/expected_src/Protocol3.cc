
#include "Protocol3.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_m5[] = 
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

static UMLRTObject payload_m5 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_m5
};

static UMLRTObject_field fields_m6[] = 
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

static UMLRTObject payload_m6 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_m6
};

Protocol3::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol3::Conj::m5() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m5", signal_m5, srcPort, &payload_m5 );
    return signal;
}

UMLRTOutSignal Protocol3::Conj::m6() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m6", signal_m6, srcPort, &payload_m6 );
    return signal;
}

Protocol3::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol3::Base::m5() const
{
    UMLRTInSignal signal;
    signal.initialize( "m5", signal_m5, srcPort, &payload_m5 );
    return signal;
}

UMLRTInSignal Protocol3::Base::m6() const
{
    UMLRTInSignal signal;
    signal.initialize( "m6", signal_m6, srcPort, &payload_m6 );
    return signal;
}


