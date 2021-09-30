
#include "Top.hh"

#include "Medium.hh"
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
, medium( &slot->parts[part_medium] )
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
        "medium",
        &Medium,
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
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_medium].slots[0]->ports[Capsule_Medium::borderport_m_inp], 0, &slot->parts[Capsule_Top::part_sender].slots[0]->ports[Capsule_Sender::borderport_out], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_medium].slots[0]->ports[Capsule_Medium::borderport_m_inp], 1, &slot->parts[Capsule_Top::part_sender].slots[0]->ports[Capsule_Sender::borderport_out], 1 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_medium].slots[0]->ports[Capsule_Medium::borderport_m_out], 0, &slot->parts[Capsule_Top::part_receiver].slots[0]->ports[Capsule_Receiver::borderport_inp], 0 );
    Medium.instantiate( NULL, slot->parts[Capsule_Top::part_medium].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_medium].slots[0], Medium.numPortRolesBorder ) );
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

