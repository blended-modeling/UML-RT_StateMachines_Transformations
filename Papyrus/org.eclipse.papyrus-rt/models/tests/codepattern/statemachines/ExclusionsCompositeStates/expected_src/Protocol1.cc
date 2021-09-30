
#include "Protocol1.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
#include <stdint.h>
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

static UMLRTObject_field fields_m3[] = 
{
    {
        "data",
        &UMLRTType_int64_t,
        0,
        1,
        0
    }
};

static UMLRTObject payload_m3 = 
{
    sizeof( int64_t ),
    1,
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

Protocol1::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol1::Base::m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "m1", signal_m1, srcPort, &payload_m1 );
    return signal;
}

UMLRTInSignal Protocol1::Base::m2() const
{
    UMLRTInSignal signal;
    signal.initialize( "m2", signal_m2, srcPort, &payload_m2 );
    return signal;
}

UMLRTInSignal Protocol1::Base::m3() const
{
    UMLRTInSignal signal;
    signal.initialize( "m3", signal_m3, srcPort, &payload_m3 );
    return signal;
}

UMLRTInSignal Protocol1::Base::m4() const
{
    UMLRTInSignal signal;
    signal.initialize( "m4", signal_m4, srcPort, &payload_m4 );
    return signal;
}

UMLRTInSignal Protocol1::Base::m5() const
{
    UMLRTInSignal signal;
    signal.initialize( "m5", signal_m5, srcPort, &payload_m5 );
    return signal;
}

UMLRTInSignal Protocol1::Base::m6() const
{
    UMLRTInSignal signal;
    signal.initialize( "m6", signal_m6, srcPort, &payload_m6 );
    return signal;
}

Protocol1::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol1::Conj::m1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m1", signal_m1, srcPort, &payload_m1 );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::m2() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m2", signal_m2, srcPort, &payload_m2 );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::m3( const int64_t & data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "m3", signal_m3, srcPort, &payload_m3, &data );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::m4() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m4", signal_m4, srcPort, &payload_m4 );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::m5() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m5", signal_m5, srcPort, &payload_m5 );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::m6() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m6", signal_m6, srcPort, &payload_m6 );
    return signal;
}


