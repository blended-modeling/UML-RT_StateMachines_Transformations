
#include "Capsule2.hh"

#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtslot.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Capsule2::Capsule_Capsule2( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol1( borderPorts[borderport_protocol1] )
, M( L/2 - 1 )
, L( 8 )
{
}





void Capsule_Capsule2::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol1, index, true );
            break;
        }
}

void Capsule_Capsule2::unbindPort( bool isBorder, int portId, int index )
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



void Capsule_Capsule2::initialize( const UMLRTMessage & msg )
{
}

void Capsule_Capsule2::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Capsule2::port_protocol1,
        "Protocol1",
        "protocol1",
        "",
        2,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    }
};

static void instantiate_Capsule2( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Capsule2( &Capsule2, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Capsule2 = 
{
    "Capsule2",
    NULL,
    instantiate_Capsule2,
    0,
    NULL,
    1,
    portroles_border,
    0,
    NULL
};

