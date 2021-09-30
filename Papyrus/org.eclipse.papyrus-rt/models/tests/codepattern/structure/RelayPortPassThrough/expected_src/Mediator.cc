
#include "Mediator.hh"

#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtslot.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Mediator::Capsule_Mediator( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, minp( borderPorts[borderport_minp] )
, mout( borderPorts[borderport_mout] )
{
}






void Capsule_Mediator::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_minp:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectFarEnds( borderPorts[borderport_minp], index, borderPorts[borderport_mout], 0 );
                UMLRTFrameService::sendBoundUnboundFarEnd( borderPorts[borderport_minp], index, true );
                UMLRTFrameService::sendBoundUnboundFarEnd( borderPorts[borderport_mout], 0, true );
                break;
            }
            break;
        case borderport_mout:
            switch( index )
            {
            }
            break;
        }
}

void Capsule_Mediator::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_minp:
            switch( index )
            {
            case 0:
                UMLRTFrameService::sendBoundUnboundForPortIndex( borderPorts[borderport_minp], index, false );
                UMLRTFrameService::disconnectPort( borderPorts[borderport_minp], index );
                break;
            }
            break;
        case borderport_mout:
            switch( index )
            {
            case 0:
                UMLRTFrameService::sendBoundUnboundForPortIndex( borderPorts[borderport_mout], index, false );
                UMLRTFrameService::disconnectPort( borderPorts[borderport_mout], index );
                break;
            }
            break;
        }
}

void Capsule_Mediator::initialize( const UMLRTMessage & msg )
{
}

void Capsule_Mediator::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Mediator::port_minp,
        "Protocol1",
        "minp",
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
        Capsule_Mediator::port_mout,
        "Protocol1",
        "mout",
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

static void instantiate_Mediator( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectFarEnds( borderPorts[Capsule_Mediator::borderport_minp], 0, borderPorts[Capsule_Mediator::borderport_mout], 0 );
    slot->capsule = new Capsule_Mediator( &Mediator, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Mediator = 
{
    "Mediator",
    NULL,
    instantiate_Mediator,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};

