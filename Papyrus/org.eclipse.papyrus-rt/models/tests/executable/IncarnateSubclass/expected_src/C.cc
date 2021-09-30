
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
, control( borderPorts[borderport_control] )
, p1( borderPorts[borderport_p1] )
, p2( borderPorts[borderport_p2] )
{
}







void Capsule_C::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_control:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_control, index, true );
            break;
        case borderport_p1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p1, index, true );
            break;
        case borderport_p2:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p2, index, true );
            break;
        }
}

void Capsule_C::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_control:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_control, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_control], index );
            break;
        case borderport_p1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p1, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p1], index );
            break;
        case borderport_p2:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p2, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p2], index );
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
        Capsule_C::port_control,
        "Control",
        "control",
        "",
        1,
        true,
        false,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_C::port_p1,
        "P1",
        "p1",
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
        Capsule_C::port_p2,
        "P2",
        "p2",
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
    3,
    portroles_border,
    0,
    NULL
};

