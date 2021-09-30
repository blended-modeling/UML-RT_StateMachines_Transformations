
#include "Top.hh"

#include "Capsule1.hh"
#include "Capsule2.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, capsule1( &slot->parts[part_capsule1] )
, capsule2( &slot->parts[part_capsule2] )
, K( 2 )
, M( 4 )
{
}




void Capsule_Top::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
}



void Capsule_Top::initialize( const UMLRTMessage & msg )
{
}

void Capsule_Top::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "capsule1",
        &Capsule1,
        1,
        1,
        false,
        false
    },
    {
        "capsule2",
        &Capsule2,
        3,
        3,
        false,
        false
    }
};

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_protocol1], 0, &slot->parts[Capsule_Top::part_capsule2].slots[0]->ports[Capsule_Capsule2::borderport_protocol1], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_protocol1], 1, &slot->parts[Capsule_Top::part_capsule2].slots[0]->ports[Capsule_Capsule2::borderport_protocol1], 1 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_protocol1], 2, &slot->parts[Capsule_Top::part_capsule2].slots[1]->ports[Capsule_Capsule2::borderport_protocol1], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_protocol1], 3, &slot->parts[Capsule_Top::part_capsule2].slots[1]->ports[Capsule_Capsule2::borderport_protocol1], 1 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_protocol1], 4, &slot->parts[Capsule_Top::part_capsule2].slots[2]->ports[Capsule_Capsule2::borderport_protocol1], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_protocol1], 5, &slot->parts[Capsule_Top::part_capsule2].slots[2]->ports[Capsule_Capsule2::borderport_protocol1], 1 );
    Capsule1.instantiate( NULL, slot->parts[Capsule_Top::part_capsule1].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_capsule1].slots[0], Capsule1.numPortRolesBorder ) );
    Capsule2.instantiate( NULL, slot->parts[Capsule_Top::part_capsule2].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_capsule2].slots[0], Capsule2.numPortRolesBorder ) );
    Capsule2.instantiate( NULL, slot->parts[Capsule_Top::part_capsule2].slots[1], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_capsule2].slots[1], Capsule2.numPortRolesBorder ) );
    Capsule2.instantiate( NULL, slot->parts[Capsule_Top::part_capsule2].slots[2], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_capsule2].slots[2], Capsule2.numPortRolesBorder ) );
    slot->capsule = new Capsule_Top( &Top, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    2,
    roles,
    0,
    NULL,
    0,
    NULL
};

