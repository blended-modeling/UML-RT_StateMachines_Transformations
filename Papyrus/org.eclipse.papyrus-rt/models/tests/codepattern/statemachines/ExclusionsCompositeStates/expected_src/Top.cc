
#include "Top.hh"

#include "A.hh"
#include "B.hh"
#include "C.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtframeservice.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, a( &slot->parts[part_a] )
, b( &slot->parts[part_b] )
, c( &slot->parts[part_c] )
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
    },
    {
        "c",
        &C,
        1,
        1,
        false,
        false
    }
};

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    A.instantiate( NULL, slot->parts[Capsule_Top::part_a].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_a].slots[0], A.numPortRolesBorder ) );
    B.instantiate( NULL, slot->parts[Capsule_Top::part_b].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_b].slots[0], B.numPortRolesBorder ) );
    C.instantiate( NULL, slot->parts[Capsule_Top::part_c].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_c].slots[0], C.numPortRolesBorder ) );
    slot->capsule = new Capsule_Top( &Top, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    3,
    roles,
    0,
    NULL,
    0,
    NULL
};

