
#include "Top.hh"

#include "Capsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtframeservice.hh"
#include <cstddef>
class UMLRTRtsInterface;

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
{
}


const UMLRTCapsulePart * Capsule_Top::part1() const
{
    return &slot->parts[part_part1];
}

const UMLRTCapsulePart * Capsule_Top::part2() const
{
    return &slot->parts[part_part2];
}

const UMLRTCapsulePart * Capsule_Top::part3() const
{
    return &slot->parts[part_part3];
}

const UMLRTCapsulePart * Capsule_Top::part4() const
{
    return &slot->parts[part_part4];
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
        "part1",
        &Capsule,
        1,
        1,
        false,
        false
    },
    {
        "part2",
        &Capsule,
        1,
        1,
        false,
        false
    },
    {
        "part3",
        &Capsule,
        1,
        1,
        false,
        false
    },
    {
        "part4",
        &Capsule,
        1,
        1,
        false,
        false
    }
};

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_part3].slots[0]->ports[Capsule_Capsule::borderport_left], 0, &slot->parts[Capsule_Top::part_part4].slots[0]->ports[Capsule_Capsule::borderport_right], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_part3].slots[0]->ports[Capsule_Capsule::borderport_right], 0, &slot->parts[Capsule_Top::part_part2].slots[0]->ports[Capsule_Capsule::borderport_left], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_part1].slots[0]->ports[Capsule_Capsule::borderport_left], 0, &slot->parts[Capsule_Top::part_part2].slots[0]->ports[Capsule_Capsule::borderport_right], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_part1].slots[0]->ports[Capsule_Capsule::borderport_right], 0, &slot->parts[Capsule_Top::part_part4].slots[0]->ports[Capsule_Capsule::borderport_left], 0 );
    Capsule.instantiate( NULL, slot->parts[Capsule_Top::part_part3].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_part3].slots[0], Capsule.numPortRolesBorder ) );
    Capsule.instantiate( NULL, slot->parts[Capsule_Top::part_part1].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_part1].slots[0], Capsule.numPortRolesBorder ) );
    Capsule.instantiate( NULL, slot->parts[Capsule_Top::part_part4].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_part4].slots[0], Capsule.numPortRolesBorder ) );
    Capsule.instantiate( NULL, slot->parts[Capsule_Top::part_part2].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_part2].slots[0], Capsule.numPortRolesBorder ) );
    slot->capsule = new Capsule_Top( &Top, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    4,
    roles,
    0,
    NULL,
    0,
    NULL
};

