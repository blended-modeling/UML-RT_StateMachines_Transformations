
#include "E.hh"

#include "G.hh"
#include "H.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtslot.hh"
#include <cstddef>
class UMLRTRtsInterface;

Capsule_E::Capsule_E( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol1( borderPorts[borderport_protocol1] )
, protocol3( borderPorts[borderport_protocol3] )
, g( &slot->parts[part_g] )
, h( &slot->parts[part_h] )
{
}








void Capsule_E::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_protocol1], index, &slot->parts[part_g].slots[0]->ports[Capsule_G::borderport_protocol1], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_g].slots[0]->capsule, portId, index );
                break;
            }
            break;
        case borderport_protocol3:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_protocol3], index, &slot->parts[part_h].slots[0]->ports[Capsule_H::borderport_protocol3], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_h].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
}

void Capsule_E::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_g].slots[0]->capsule, portId, index );
                break;
            }
            break;
        case borderport_protocol3:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_h].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
}

void Capsule_E::initialize( const UMLRTMessage & msg )
{
}

void Capsule_E::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "g",
        &G,
        1,
        1,
        false,
        false
    },
    {
        "h",
        &H,
        0,
        1,
        true,
        false
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_E::port_protocol1,
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
        Capsule_E::port_protocol3,
        "Protocol3",
        "protocol3",
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

static void instantiate_E( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_E::borderport_protocol1], 0, &slot->parts[Capsule_E::part_g].slots[0]->ports[Capsule_G::borderport_protocol1], 0 );
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_E::borderport_protocol3], 0, &slot->parts[Capsule_E::part_h].slots[0]->ports[Capsule_H::borderport_protocol3], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_E::part_g].slots[0]->ports[Capsule_G::borderport_protocol2], 0, &slot->parts[Capsule_E::part_h].slots[0]->ports[Capsule_H::borderport_protocol2], 0 );
    G.instantiate( NULL, slot->parts[Capsule_E::part_g].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_E::part_g].slots[0], G.numPortRolesBorder ) );
    slot->capsule = new Capsule_E( &E, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass E = 
{
    "E",
    NULL,
    instantiate_E,
    2,
    roles,
    2,
    portroles_border,
    0,
    NULL
};

