
#include "Capsule1.hh"

#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtslot.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Capsule1::Capsule_Capsule1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol1( borderPorts[borderport_protocol1] )
, N( 2*A+B )
, A( 5 )
, B( 3 )
{
}





void Capsule_Capsule1::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol1, index, true );
            break;
        }
}

void Capsule_Capsule1::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol1, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_protocol1], index );
            break;
        }
}




void Capsule_Capsule1::initialize( const UMLRTMessage & msg )
{
}

void Capsule_Capsule1::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Capsule1::port_protocol1,
        "Protocol1",
        "protocol1",
        "",
        6,
        true,
        false,
        false,
        false,
        false,
        false,
        true
    }
};

static void instantiate_Capsule1( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Capsule1( &Capsule1, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Capsule1 = 
{
    "Capsule1",
    NULL,
    instantiate_Capsule1,
    0,
    NULL,
    1,
    portroles_border,
    0,
    NULL
};

