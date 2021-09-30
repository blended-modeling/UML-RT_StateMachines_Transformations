
#include "Protocol2.hh"

#include "C.hh"
#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
#include <cstddef>
struct UMLRTCommsPort;

struct params_m2
{
    char * x;
    C y;
};

static UMLRTObject_field fields_m2[] = 
{
    {
        "x",
        &UMLRTType_charptr,
        offsetof( params_m2, x ),
        1,
        0
    },
    {
        "y",
        &UMLRTType_C,
        offsetof( params_m2, y ),
        1,
        0
    }
};

static UMLRTObject payload_m2 = 
{
    sizeof( params_m2 ),
    2,
    fields_m2
};

Protocol2::Base::Base( const UMLRTCommsPort * & srcPort )
: Protocol1::Base( srcPort )
{
}

UMLRTInSignal Protocol2::Base::m2() const
{
    UMLRTInSignal signal;
    signal.initialize( "m2", signal_m2, srcPort, &payload_m2 );
    return signal;
}

Protocol2::Conj::Conj( const UMLRTCommsPort * & srcPort )
: Protocol1::Conj( srcPort )
{
}

UMLRTOutSignal Protocol2::Conj::m2( char * x, const C & y ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "m2", signal_m2, srcPort, &payload_m2, &x, &y );
    return signal;
}


