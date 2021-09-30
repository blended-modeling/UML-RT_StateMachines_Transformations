
#include "Top.hh"

#include "A.hh"
#include "B.hh"
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
, a( &slot->parts[part_a] )
, b( &slot->parts[part_b] )
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
        "a",
        &A,
        1,
        1,
        false,
        false
    },
    {
        "b",
        &B,
        1,
        1,
        false,
        false
    }
};

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_a].slots[0]->ports[Capsule_A::borderport_p], 0, &slot->parts[Capsule_Top::part_b].slots[0]->ports[Capsule_B::borderport_p], 0 );
    A.instantiate( NULL, slot->parts[Capsule_Top::part_a].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_a].slots[0], A.numPortRolesBorder ) );
    B.instantiate( NULL, slot->parts[Capsule_Top::part_b].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_b].slots[0], B.numPortRolesBorder ) );
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

