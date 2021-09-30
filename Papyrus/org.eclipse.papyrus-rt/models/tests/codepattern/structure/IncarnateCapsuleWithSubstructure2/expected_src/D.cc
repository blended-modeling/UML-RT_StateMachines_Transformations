
#include "D.hh"

#include "J.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_D::Capsule_D( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol1( borderPorts[borderport_protocol1] )
, protocol2( borderPorts[borderport_protocol2] )
, j( &slot->parts[part_j] )
{
}







void Capsule_D::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_protocol1], index, &slot->parts[part_j].slots[0]->ports[Capsule_J::borderport_protocol1], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_j].slots[0]->capsule, portId, index );
                break;
            }
            break;
        case borderport_protocol2:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_protocol2], index, &slot->parts[part_j].slots[0]->ports[Capsule_J::borderport_protocol2], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_j].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
}

void Capsule_D::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_j].slots[0]->capsule, portId, index );
                break;
            }
            break;
        case borderport_protocol2:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_j].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
}

void Capsule_D::initialize( const UMLRTMessage & msg )
{
}

void Capsule_D::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "j",
        &J,
        1,
        1,
        false,
        false
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_D::port_protocol1,
        "Protocol1",
        "protocol1",
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
        Capsule_D::port_protocol2,
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
    }
};

static void instantiate_D( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_D::borderport_protocol1], 0, &slot->parts[Capsule_D::part_j].slots[0]->ports[Capsule_J::borderport_protocol1], 0 );
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_D::borderport_protocol2], 0, &slot->parts[Capsule_D::part_j].slots[0]->ports[Capsule_J::borderport_protocol2], 0 );
    J.instantiate( NULL, slot->parts[Capsule_D::part_j].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_D::part_j].slots[0], J.numPortRolesBorder ) );
    slot->capsule = new Capsule_D( &D, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass D = 
{
    "D",
    NULL,
    instantiate_D,
    1,
    roles,
    2,
    portroles_border,
    0,
    NULL
};

