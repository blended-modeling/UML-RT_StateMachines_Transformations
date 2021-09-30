
#include "G.hh"

#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtslot.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_G::Capsule_G( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol1( borderPorts[borderport_protocol1] )
, protocol2( borderPorts[borderport_protocol2] )
{
}






void Capsule_G::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol1, index, true );
            break;
        case borderport_protocol2:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol2, index, true );
            break;
        }
}

void Capsule_G::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol1, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_protocol1], index );
            break;
        case borderport_protocol2:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol2, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_protocol2], index );
            break;
        }
}

void Capsule_G::initialize( const UMLRTMessage & msg )
{
}

void Capsule_G::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_G::port_protocol1,
        "Protocol1",
        "protocol1",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_G::port_protocol2,
        "Protocol2",
        "protocol2",
        "",
        1,
        true,
        false,
        false,
        false,
        false,
        false,
        true
    }
};

static void instantiate_G( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_G( &G, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass G = 
{
    "G",
    NULL,
    instantiate_G,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};

