
#include "Protocol1.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_msg1[] = 
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

static UMLRTObject payload_msg1 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_msg1
};

static UMLRTObject_field fields_msg2[] = 
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

static UMLRTObject payload_msg2 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_msg2
};

static UMLRTObject_field fields_step[] = 
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

static UMLRTObject payload_step = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_step
};

Protocol1::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol1::Base::msg1() const
{
    UMLRTInSignal signal;
    signal.initialize( "msg1", signal_msg1, srcPort, &payload_msg1 );
    return signal;
}

UMLRTInSignal Protocol1::Base::msg2() const
{
    UMLRTInSignal signal;
    signal.initialize( "msg2", signal_msg2, srcPort, &payload_msg2 );
    return signal;
}

UMLRTInSignal Protocol1::Base::step() const
{
    UMLRTInSignal signal;
    signal.initialize( "step", signal_step, srcPort, &payload_step );
    return signal;
}

Protocol1::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol1::Conj::msg1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "msg1", signal_msg1, srcPort, &payload_msg1 );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::msg2() const
{
    UMLRTOutSignal signal;
    signal.initialize( "msg2", signal_msg2, srcPort, &payload_msg2 );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::step() const
{
    UMLRTOutSignal signal;
    signal.initialize( "step", signal_step, srcPort, &payload_step );
    return signal;
}


