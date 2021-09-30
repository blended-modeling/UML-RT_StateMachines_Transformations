
#include "P.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_e1[] = 
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

static UMLRTObject payload_e1 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_e1
};

static UMLRTObject_field fields_e2[] = 
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

static UMLRTObject payload_e2 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_e2
};

P::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal P::Base::e1() const
{
    UMLRTInSignal signal;
    signal.initialize( "e1", signal_e1, srcPort, &payload_e1 );
    return signal;
}

UMLRTInSignal P::Base::e2() const
{
    UMLRTInSignal signal;
    signal.initialize( "e2", signal_e2, srcPort, &payload_e2 );
    return signal;
}

P::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal P::Conj::e1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "e1", signal_e1, srcPort, &payload_e1 );
    return signal;
}

UMLRTOutSignal P::Conj::e2() const
{
    UMLRTOutSignal signal;
    signal.initialize( "e2", signal_e2, srcPort, &payload_e2 );
    return signal;
}


