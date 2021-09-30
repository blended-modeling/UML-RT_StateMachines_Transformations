
#include "Top.hh"

#include "Mediator.hh"
#include "Receiver.hh"
#include "Sender.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsport.hh"
#include "umlrtframeservice.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
class UMLRTRtsInterface;

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, mediator( &slot->parts[part_mediator] )
, receiver( &slot->parts[part_receiver] )
, sender( &slot->parts[part_sender] )
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
        "mediator",
        &Mediator,
        1,
        1,
        false,
        false
    },
    {
        "receiver",
        &Receiver,
        1,
        1,
        false,
        false
    },
    {
        "sender",
        &Sender,
        1,
        1,
        false,
        false
    }
};

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_mediator].slots[0]->ports[Capsule_Mediator::borderport_minp], 0, &slot->parts[Capsule_Top::part_sender].slots[0]->ports[Capsule_Sender::borderport_out], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_mediator].slots[0]->ports[Capsule_Mediator::borderport_mout], 0, &slot->parts[Capsule_Top::part_receiver].slots[0]->ports[Capsule_Receiver::borderport_inp], 0 );
    Mediator.instantiate( NULL, slot->parts[Capsule_Top::part_mediator].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_mediator].slots[0], Mediator.numPortRolesBorder ) );
    Receiver.instantiate( NULL, slot->parts[Capsule_Top::part_receiver].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_receiver].slots[0], Receiver.numPortRolesBorder ) );
    Sender.instantiate( NULL, slot->parts[Capsule_Top::part_sender].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_sender].slots[0], Sender.numPortRolesBorder ) );
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

