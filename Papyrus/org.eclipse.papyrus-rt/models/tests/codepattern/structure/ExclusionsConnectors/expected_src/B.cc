
#include "B.hh"

#include "A.hh"
#include "C.hh"
#include "D.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtframeservice.hh"
#include "umlrtslot.hh"
#include <cstddef>
class UMLRTRtsInterface;

Capsule_B::Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_A( cd, st, border, internal, isStat )
, c( &slot->parts[part_c] )
, d( &slot->parts[part_d] )
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
        1,
        1,
        false,
        false
    }
};

static void instantiate_B( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_B::part_c].slots[0]->ports[Capsule_C::borderport_p], 0, &slot->parts[Capsule_B::part_d].slots[0]->ports[Capsule_D::borderport_p], 0 );
    C.instantiate( NULL, slot->parts[Capsule_B::part_c].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_B::part_c].slots[0], C.numPortRolesBorder ) );
    D.instantiate( NULL, slot->parts[Capsule_B::part_d].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_B::part_d].slots[0], D.numPortRolesBorder ) );
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

