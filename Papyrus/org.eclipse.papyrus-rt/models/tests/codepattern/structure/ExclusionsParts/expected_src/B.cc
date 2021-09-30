
#include "B.hh"

#include "A.hh"
#include "C.hh"
#include "D.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtframeservice.hh"
#include "umlrtslot.hh"
#include <cstddef>
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_B::Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_A( cd, st, border, internal, isStat )
, c1( &slot->parts[part_c1] )
, c2( &slot->parts[part_c2] )
{
}




void Capsule_B::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_B::unbindPort( bool isBorder, int portId, int index )
{
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
        "c1",
        &C,
        1,
        1,
        false,
        false
    },
    {
        "c2",
        &D,
        1,
        1,
        false,
        false
    }
};

static void instantiate_B( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    C.instantiate( NULL, slot->parts[Capsule_B::part_c1].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_B::part_c1].slots[0], C.numPortRolesBorder ) );
    D.instantiate( NULL, slot->parts[Capsule_B::part_c2].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_B::part_c2].slots[0], D.numPortRolesBorder ) );
    slot->capsule = new Capsule_B( &B, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass B = 
{
    "B",
    &A,
    instantiate_B,
    2,
    roles,
    0,
    NULL,
    0,
    NULL
};

