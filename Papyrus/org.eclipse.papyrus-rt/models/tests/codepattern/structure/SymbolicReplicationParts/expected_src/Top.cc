
#include "Top.hh"

#include "Capsule1.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, capsule1( &slot->parts[part_capsule1] )
, SIZE( 2 )
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
        3,
        3,
        false,
        false
    }
};

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    Capsule1.instantiate( NULL, slot->parts[Capsule_Top::part_capsule1].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_capsule1].slots[0], Capsule1.numPortRolesBorder ) );
    Capsule1.instantiate( NULL, slot->parts[Capsule_Top::part_capsule1].slots[1], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_capsule1].slots[1], Capsule1.numPortRolesBorder ) );
    Capsule1.instantiate( NULL, slot->parts[Capsule_Top::part_capsule1].slots[2], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_capsule1].slots[2], Capsule1.numPortRolesBorder ) );
    slot->capsule = new Capsule_Top( &Top, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    1,
    roles,
    0,
    NULL,
    0,
    NULL
};

