
#include "B.hh"

#include "E.hh"
#include "F.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_B::Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol1( borderPorts[borderport_protocol1] )
, e( &slot->parts[part_e] )
, f( &slot->parts[part_f] )
{
}







void Capsule_B::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_protocol1], index, &slot->parts[part_e].slots[0]->ports[Capsule_E::borderport_protocol1], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_e].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
}

void Capsule_B::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_e].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
}

void Capsule_B::initialize( const UMLRTMessage & msg )
{
}

void Capsule_B::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "e",
        &E,
        1,
        1,
        false,
        false
    },
    {
        "f",
        &F,
        0,
        1,
        false,
        true
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_B::port_protocol1,
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
    }
};

static void instantiate_B( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_B::borderport_protocol1], 0, &slot->parts[Capsule_B::part_e].slots[0]->ports[Capsule_E::borderport_protocol1], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_B::part_e].slots[0]->ports[Capsule_E::borderport_protocol3], 0, &slot->parts[Capsule_B::part_f].slots[0]->ports[Capsule_F::borderport_protocol3], 0 );
    E.instantiate( NULL, slot->parts[Capsule_B::part_e].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_B::part_e].slots[0], E.numPortRolesBorder ) );
    slot->capsule = new Capsule_B( &B, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass B = 
{
    "B",
    NULL,
    instantiate_B,
    2,
    roles,
    1,
    portroles_border,
    0,
    NULL
};

