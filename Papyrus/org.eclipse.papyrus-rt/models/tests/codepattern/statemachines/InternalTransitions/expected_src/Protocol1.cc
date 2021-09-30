
#include "Protocol1.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_im1[] = 
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

static UMLRTObject payload_im1 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_im1
};

static UMLRTObject_field fields_im2[] = 
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

static UMLRTObject payload_im2 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_im2
};

Protocol1::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol1::Base::im1() const
{
    UMLRTInSignal signal;
    signal.initialize( "im1", signal_im1, srcPort, &payload_im1 );
    return signal;
}

UMLRTInSignal Protocol1::Base::im2() const
{
    UMLRTInSignal signal;
    signal.initialize( "im2", signal_im2, srcPort, &payload_im2 );
    return signal;
}

Protocol1::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol1::Conj::im1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "im1", signal_im1, srcPort, &payload_im1 );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::im2() const
{
    UMLRTOutSignal signal;
    signal.initialize( "im2", signal_im2, srcPort, &payload_im2 );
    return signal;
}


