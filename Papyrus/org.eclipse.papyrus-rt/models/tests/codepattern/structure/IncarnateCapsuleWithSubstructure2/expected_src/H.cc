
#include "H.hh"

#include "D.hh"
#include "I.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtslot.hh"
#include <cstddef>
class UMLRTRtsInterface;

Capsule_H::Capsule_H( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol2( borderPorts[borderport_protocol2] )
, protocol3( borderPorts[borderport_protocol3] )
, d( &slot->parts[part_d] )
, i( &slot->parts[part_i] )
{
}








void Capsule_H::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol2:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_protocol2], index, &slot->parts[part_d].slots[0]->ports[Capsule_D::borderport_protocol2], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_d].slots[0]->capsule, portId, index );
                break;
            }
            break;
        case borderport_protocol3:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_protocol3], index, &slot->parts[part_i].slots[0]->ports[Capsule_I::borderport_protocol3], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_i].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
}

void Capsule_H::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol2:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_d].slots[0]->capsule, portId, index );
                break;
            }
            break;
        case borderport_protocol3:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_i].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
}

void Capsule_H::initialize( const UMLRTMessage & msg )
{
}

void Capsule_H::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "d",
        &D,
        1,
        1,
        false,
        false
    },
    {
        "i",
        &I,
        1,
        1,
        false,
        false
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_H::port_protocol2,
        "Protocol2",
        "protocol2",
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
        Capsule_H::port_protocol3,
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

static void instantiate_H( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_H::borderport_protocol2], 0, &slot->parts[Capsule_H::part_d].slots[0]->ports[Capsule_D::borderport_protocol2], 0 );
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_H::borderport_protocol3], 0, &slot->parts[Capsule_H::part_i].slots[0]->ports[Capsule_I::borderport_protocol3], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_H::part_d].slots[0]->ports[Capsule_D::borderport_protocol1], 0, &slot->parts[Capsule_H::part_i].slots[0]->ports[Capsule_I::borderport_protocol1], 0 );
    D.instantiate( NULL, slot->parts[Capsule_H::part_d].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_H::part_d].slots[0], D.numPortRolesBorder ) );
    I.instantiate( NULL, slot->parts[Capsule_H::part_i].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_H::part_i].slots[0], I.numPortRolesBorder ) );
    slot->capsule = new Capsule_H( &H, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass H = 
{
    "H",
    NULL,
    instantiate_H,
    2,
    roles,
    2,
    portroles_border,
    0,
    NULL
};

