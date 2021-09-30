
#include "Protocol2.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_om1[] = 
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

static UMLRTObject payload_om1 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_om1
};

static UMLRTObject_field fields_om2[] = 
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

static UMLRTObject payload_om2 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_om2
};

static UMLRTObject_field fields_om3[] = 
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

static UMLRTObject payload_om3 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_om3
};

Protocol2::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol2::Conj::om1() const
{
    UMLRTInSignal signal;
    signal.initialize( "om1", signal_om1, srcPort, &payload_om1 );
    return signal;
}

UMLRTInSignal Protocol2::Conj::om2() const
{
    UMLRTInSignal signal;
    signal.initialize( "om2", signal_om2, srcPort, &payload_om2 );
    return signal;
}

UMLRTInSignal Protocol2::Conj::om3() const
{
    UMLRTInSignal signal;
    signal.initialize( "om3", signal_om3, srcPort, &payload_om3 );
    return signal;
}

Protocol2::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol2::Base::om1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "om1", signal_om1, srcPort, &payload_om1 );
    return signal;
}

UMLRTOutSignal Protocol2::Base::om2() const
{
    UMLRTOutSignal signal;
    signal.initialize( "om2", signal_om2, srcPort, &payload_om2 );
    return signal;
}

UMLRTOutSignal Protocol2::Base::om3() const
{
    UMLRTOutSignal signal;
    signal.initialize( "om3", signal_om3, srcPort, &payload_om3 );
    return signal;
}


