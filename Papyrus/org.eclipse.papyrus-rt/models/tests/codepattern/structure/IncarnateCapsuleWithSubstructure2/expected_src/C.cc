
#include "C.hh"

#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtslot.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_C::Capsule_C( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol2( borderPorts[borderport_protocol2] )
{
}





void Capsule_C::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol2:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol2, index, true );
            break;
        }
}

void Capsule_C::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol2:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol2, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_protocol2], index );
            break;
        }
}

void Capsule_C::initialize( const UMLRTMessage & msg )
{
}

void Capsule_C::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_C::port_protocol2,
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

static void instantiate_C( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_C( &C, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass C = 
{
    "C",
    NULL,
    instantiate_C,
    0,
    NULL,
    1,
    portroles_border,
    0,
    NULL
};

