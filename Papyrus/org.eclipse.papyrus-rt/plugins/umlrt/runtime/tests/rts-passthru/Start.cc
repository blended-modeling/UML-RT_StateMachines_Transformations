
#include "Start.hh"

#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_begin[] = 
{
};

static UMLRTObject payload_begin = 
{
    0,
    0,
    fields_begin
};

Start::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

Start::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Start::Base::begin() const
{
    UMLRTOutSignal signal;
    signal.initialize( "begin", signal_begin, srcPort, &payload_begin );
    return signal;
}


