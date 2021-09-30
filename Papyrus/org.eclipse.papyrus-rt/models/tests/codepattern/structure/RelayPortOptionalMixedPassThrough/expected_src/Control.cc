
#include "Control.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_begin[] = 
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

static UMLRTObject payload_begin = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_begin
};

Control::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Control::Conj::begin() const
{
    UMLRTInSignal signal;
    signal.initialize( "begin", signal_begin, srcPort, &payload_begin );
    return signal;
}

Control::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Control::Base::begin() const
{
    UMLRTOutSignal signal;
    signal.initialize( "begin", signal_begin, srcPort, &payload_begin );
    return signal;
}


