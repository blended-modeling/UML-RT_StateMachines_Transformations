
#include "B.hh"

#include "C.hh"
#include "D.hh"
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
, p( borderPorts[borderport_p] )
, c( &slot->parts[part_c] )
, d( &slot->parts[part_d] )
{
}







void Capsule_B::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_p], index, &slot->parts[part_c].slots[0]->ports[Capsule_C::borderport_p], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_c].slots[0]->capsule, portId, index );
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
        case borderport_p:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_c].slots[0]->capsule, portId, index );
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
        "c",
        &C,
        1,
        1,
        false,
        false
    },
    {
        "d",
        &D,
        0,
        1,
        true,
        false
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_B::port_p,
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
    }
};

static void instantiate_B( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_B::borderport_p], 0, &slot->parts[Capsule_B::part_c].slots[0]->ports[Capsule_C::borderport_p], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_B::part_c].slots[0]->ports[Capsule_C::borderport_q], 0, &slot->parts[Capsule_B::part_d].slots[0]->ports[Capsule_D::borderport_q], 0 );
    C.instantiate( NULL, slot->parts[Capsule_B::part_c].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_B::part_c].slots[0], C.numPortRolesBorder ) );
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

