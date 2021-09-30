
#include "Protocol1.hh"

#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_msg1[] = 
{
};

static UMLRTObject payload_msg1 = 
{
    0,
    0,
    fields_msg1
};

Protocol1::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol1::Base::msg1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "msg1", signal_msg1, srcPort, &payload_msg1 );
    return signal;
}

Protocol1::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}


