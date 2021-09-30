
#include "Q.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_m[] = 
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

static UMLRTObject payload_m = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_m
};

Q::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Q::Base::m() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m", signal_m, srcPort, &payload_m );
    return signal;
}

Q::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Q::Conj::m() const
{
    UMLRTInSignal signal;
    signal.initialize( "m", signal_m, srcPort, &payload_m );
    return signal;
}


