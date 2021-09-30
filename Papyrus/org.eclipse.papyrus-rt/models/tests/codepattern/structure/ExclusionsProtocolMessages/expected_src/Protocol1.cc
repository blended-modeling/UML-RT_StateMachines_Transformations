
#include "Protocol1.hh"

#include "D.hh"
#include "umlrtinoutsignal.hh"
#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
#include <cstddef>
struct UMLRTCommsPort;

struct params_m2
{
    char * x;
    D y;
};

static UMLRTObject_field fields_m2[] = 
{
    {
        "x",
        &UMLRTType_charptr,
        offsetof( params_m2, x ),
        1,
        0
    },
    {
        "y",
        &UMLRTType_D,
        offsetof( params_m2, y ),
        1,
        0
    }
};

static UMLRTObject payload_m2 = 
{
    sizeof( params_m2 ),
    2,
    fields_m2
};

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

Protocol1::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol1::Base::m2() const
{
    UMLRTInSignal signal;
    signal.initialize( "m2", signal_m2, srcPort, &payload_m2 );
    return signal;
}

UMLRTInOutSignal Protocol1::Base::m3() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "m3", signal_m3, srcPort, &payload_m3 );
    return signal;
}

UMLRTOutSignal Protocol1::Base::m1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m1", signal_m1, srcPort, &payload_m1 );
    return signal;
}

Protocol1::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol1::Conj::m2( char * x, const D & y ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "m2", signal_m2, srcPort, &payload_m2, &x, &y );
    return signal;
}

UMLRTInOutSignal Protocol1::Conj::m3() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "m3", signal_m3, srcPort, &payload_m3 );
    return signal;
}

UMLRTInSignal Protocol1::Conj::m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "m1", signal_m1, srcPort, &payload_m1 );
    return signal;
}


