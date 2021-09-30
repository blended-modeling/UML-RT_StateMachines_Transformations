
#include "Service.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_request[] = 
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

static UMLRTObject payload_request = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_request
};

static UMLRTObject_field fields_response[] = 
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

static UMLRTObject payload_response = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_response
};

Service::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Service::Base::request() const
{
    UMLRTInSignal signal;
    signal.initialize( "request", signal_request, srcPort, &payload_request );
    return signal;
}

UMLRTOutSignal Service::Base::response() const
{
    UMLRTOutSignal signal;
    signal.initialize( "response", signal_response, srcPort, &payload_response );
    return signal;
}

Service::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Service::Conj::request() const
{
    UMLRTOutSignal signal;
    signal.initialize( "request", signal_request, srcPort, &payload_request );
    return signal;
}

UMLRTInSignal Service::Conj::response() const
{
    UMLRTInSignal signal;
    signal.initialize( "response", signal_response, srcPort, &payload_response );
    return signal;
}


