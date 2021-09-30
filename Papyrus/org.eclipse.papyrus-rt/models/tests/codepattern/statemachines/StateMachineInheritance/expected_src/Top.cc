
#include "Top.hh"

#include "Pinger.hh"
#include "Ponger.hh"
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
, pinger( &slot->parts[part_pinger] )
, ponger( &slot->parts[part_ponger] )
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
        "pinger",
        &Pinger,
        1,
        1,
        false,
        false
    },
    {
        "ponger",
        &Ponger,
        1,
        1,
        false,
        false
    }
};

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_pinger].slots[0]->ports[Capsule_Pinger::borderport_pingPort], 0, &slot->parts[Capsule_Top::part_ponger].slots[0]->ports[Capsule_Ponger::borderport_pongPort], 0 );
    Pinger.instantiate( NULL, slot->parts[Capsule_Top::part_pinger].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_pinger].slots[0], Pinger.numPortRolesBorder ) );
    Ponger.instantiate( NULL, slot->parts[Capsule_Top::part_ponger].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_ponger].slots[0], Ponger.numPortRolesBorder ) );
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

