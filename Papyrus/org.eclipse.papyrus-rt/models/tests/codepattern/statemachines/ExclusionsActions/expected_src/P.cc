
#include "P.hh"

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

P::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal P::Base::m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "m1", signal_m1, srcPort, &payload_m1 );
    return signal;
}

UMLRTInSignal P::Base::m2() const
{
    UMLRTInSignal signal;
    signal.initialize( "m2", signal_m2, srcPort, &payload_m2 );
    return signal;
}

UMLRTInSignal P::Base::m3() const
{
    UMLRTInSignal signal;
    signal.initialize( "m3", signal_m3, srcPort, &payload_m3 );
    return signal;
}

P::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal P::Conj::m1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m1", signal_m1, srcPort, &payload_m1 );
    return signal;
}

UMLRTOutSignal P::Conj::m2() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m2", signal_m2, srcPort, &payload_m2 );
    return signal;
}

UMLRTOutSignal P::Conj::m3() const
{
    UMLRTOutSignal signal;
    signal.initialize( "m3", signal_m3, srcPort, &payload_m3 );
    return signal;
}


