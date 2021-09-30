
#include "F.hh"

#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtslot.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_F::Capsule_F( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol3( borderPorts[borderport_protocol3] )
{
}





void Capsule_F::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol3:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol3, index, true );
            break;
        }
}

void Capsule_F::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol3:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol3, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_protocol3], index );
            break;
        }
}

void Capsule_F::initialize( const UMLRTMessage & msg )
{
}

void Capsule_F::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_F::port_protocol3,
        "Protocol3",
        "protocol3",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    }
};

static void instantiate_F( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_F( &F, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass F = 
{
    "F",
    NULL,
    instantiate_F,
    0,
    NULL,
    1,
    portroles_border,
    0,
    NULL
};

