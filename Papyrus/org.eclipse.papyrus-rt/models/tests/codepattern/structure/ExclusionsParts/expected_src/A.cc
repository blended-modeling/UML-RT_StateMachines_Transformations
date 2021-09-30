
#include "A.hh"

#include "C.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_A::Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, c1( &slot->parts[part_c1] )
, c2( &slot->parts[part_c2] )
, c3( &slot->parts[part_c3] )
{
}





void Capsule_A::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_A::unbindPort( bool isBorder, int portId, int index )
{
}

void Capsule_A::initialize( const UMLRTMessage & msg )
{
}

void Capsule_A::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "c1",
        &C,
        1,
        1,
        false,
        false
    },
    {
        "c2",
        &C,
        1,
        1,
        false,
        false
    },
    {
        "c3",
        &C,
        1,
        1,
        false,
        false
    }
};

static void instantiate_A( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    C.instantiate( NULL, slot->parts[Capsule_A::part_c1].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_A::part_c1].slots[0], C.numPortRolesBorder ) );
    C.instantiate( NULL, slot->parts[Capsule_A::part_c2].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_A::part_c2].slots[0], C.numPortRolesBorder ) );
    C.instantiate( NULL, slot->parts[Capsule_A::part_c3].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_A::part_c3].slots[0], C.numPortRolesBorder ) );
    slot->capsule = new Capsule_A( &A, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass A = 
{
    "A",
    NULL,
    instantiate_A,
    3,
    roles,
    0,
    NULL,
    0,
    NULL
};

