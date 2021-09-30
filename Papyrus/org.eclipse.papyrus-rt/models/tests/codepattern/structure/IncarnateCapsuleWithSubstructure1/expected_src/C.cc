
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
, p( borderPorts[borderport_p] )
, q( borderPorts[borderport_q] )
{
}






void Capsule_C::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p, index, true );
            break;
        case borderport_q:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_q, index, true );
            break;
        }
}

void Capsule_C::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p], index );
            break;
        case borderport_q:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_q, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_q], index );
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
        Capsule_C::port_p,
        "P",
        "p",
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
        Capsule_C::port_q,
        "Q",
        "q",
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
    2,
    portroles_border,
    0,
    NULL
};

