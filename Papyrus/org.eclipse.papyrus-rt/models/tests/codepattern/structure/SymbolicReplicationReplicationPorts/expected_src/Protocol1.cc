
#include "Protocol1.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_OutProtocolMessage1[] = 
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

static UMLRTObject payload_OutProtocolMessage1 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_OutProtocolMessage1
};

Protocol1::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol1::Base::OutProtocolMessage1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "OutProtocolMessage1", signal_OutProtocolMessage1, srcPort, &payload_OutProtocolMessage1 );
    return signal;
}

Protocol1::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol1::Conj::OutProtocolMessage1() const
{
    UMLRTInSignal signal;
    signal.initialize( "OutProtocolMessage1", signal_OutProtocolMessage1, srcPort, &payload_OutProtocolMessage1 );
    return signal;
}


