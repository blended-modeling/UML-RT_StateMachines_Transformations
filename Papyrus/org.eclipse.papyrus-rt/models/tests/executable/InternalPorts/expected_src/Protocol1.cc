
#include "Protocol1.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_done[] = 
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

static UMLRTObject payload_done = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_done
};

static UMLRTObject_field fields_ready[] = 
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

static UMLRTObject payload_ready = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_ready
};

static UMLRTObject_field fields_start[] = 
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

static UMLRTObject payload_start = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_start
};

static UMLRTObject_field fields_stop[] = 
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

static UMLRTObject payload_stop = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_stop
};

Protocol1::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol1::Base::done() const
{
    UMLRTInSignal signal;
    signal.initialize( "done", signal_done, srcPort, &payload_done );
    return signal;
}

UMLRTInSignal Protocol1::Base::ready() const
{
    UMLRTInSignal signal;
    signal.initialize( "ready", signal_ready, srcPort, &payload_ready );
    return signal;
}

UMLRTOutSignal Protocol1::Base::start() const
{
    UMLRTOutSignal signal;
    signal.initialize( "start", signal_start, srcPort, &payload_start );
    return signal;
}

UMLRTOutSignal Protocol1::Base::stop() const
{
    UMLRTOutSignal signal;
    signal.initialize( "stop", signal_stop, srcPort, &payload_stop );
    return signal;
}

Protocol1::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol1::Conj::done() const
{
    UMLRTOutSignal signal;
    signal.initialize( "done", signal_done, srcPort, &payload_done );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::ready() const
{
    UMLRTOutSignal signal;
    signal.initialize( "ready", signal_ready, srcPort, &payload_ready );
    return signal;
}

UMLRTInSignal Protocol1::Conj::start() const
{
    UMLRTInSignal signal;
    signal.initialize( "start", signal_start, srcPort, &payload_start );
    return signal;
}

UMLRTInSignal Protocol1::Conj::stop() const
{
    UMLRTInSignal signal;
    signal.initialize( "stop", signal_stop, srcPort, &payload_stop );
    return signal;
}


