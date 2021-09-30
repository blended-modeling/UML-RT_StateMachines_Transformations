
#include "A.hh"

#include "C.hh"
#include "D.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_A::Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, c( &slot->parts[part_c] )
, d( &slot->parts[part_d] )
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

static void instantiate_A( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_A::part_c].slots[0]->ports[Capsule_C::borderport_p], 0, &slot->parts[Capsule_A::part_d].slots[0]->ports[Capsule_D::borderport_p], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_A::part_c].slots[0]->ports[Capsule_C::borderport_q], 0, &slot->parts[Capsule_A::part_d].slots[0]->ports[Capsule_D::borderport_q], 0 );
    C.instantiate( NULL, slot->parts[Capsule_A::part_c].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_A::part_c].slots[0], C.numPortRolesBorder ) );
    D.instantiate( NULL, slot->parts[Capsule_A::part_d].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_A::part_d].slots[0], D.numPortRolesBorder ) );
    slot->capsule = new Capsule_A( &A, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass A = 
{
    "A",
    NULL,
    instantiate_A,
    2,
    roles,
    0,
    NULL,
    0,
    NULL
};

